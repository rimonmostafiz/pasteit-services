package com.miu.pasteit.service.paste;

import com.miu.pasteit.component.exception.EntityNotFoundException;
import com.miu.pasteit.component.exception.ValidationException;
import com.miu.pasteit.model.dto.FeedbackModel;
import com.miu.pasteit.model.dto.PasteModel;
import com.miu.pasteit.model.entity.activity.nosql.ActivityPaste;
import com.miu.pasteit.model.entity.common.ActivityAction;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.db.nosql.Paste;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.mapper.FeedbackMapper;
import com.miu.pasteit.model.mapper.PasteMapper;
import com.miu.pasteit.model.request.PasteCreateRequest;
import com.miu.pasteit.model.request.PasteUpdateRequest;
import com.miu.pasteit.repository.mongo.PasteRepository;
import com.miu.pasteit.repository.mongo.activity.ActivityPasteRepository;
import com.miu.pasteit.service.feedback.FeedbackService;
import com.miu.pasteit.service.user.UserService;
import com.miu.pasteit.utils.UrlGenerationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Rimon Mostafiz
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PasteService {
    public static final Supplier<EntityNotFoundException> pasteNotFound = () -> new EntityNotFoundException(HttpStatus.BAD_REQUEST, "pasteId", "error.paste.not.found");
    public static final Supplier<ValidationException> notOwnPaste = () -> new ValidationException(HttpStatus.UNAUTHORIZED, "pasteId", "error.paste.not.public.or.user.not.authorized");
    public static final Consumer<Paste> urlAlreadyExists = (paste) -> { throw new RuntimeException(String.format("Generated URL [%s] already exists for pasteId [%s]", paste.getUrl(), paste.getId())); };

    private final UserService userservice;
    private final PasteRepository pasteRepository;
    private final ActivityPasteRepository activityPasteRepository;
    private final FeedbackService feedbackService;

    @Retryable(value = RuntimeException.class, maxAttempts = 10)
    public PasteModel createPaste(PasteCreateRequest PasteCreateRequest, String requestUser) {
        String randomUrl = UrlGenerationUtil.getInstance().generateRandomURL();
        pasteRepository.findByUrl(randomUrl)
                .ifPresent(urlAlreadyExists);

        User user = userservice.getUserByUsername(requestUser);
        Paste paste = PasteMapper.createRequestToEntity(PasteCreateRequest, requestUser, user, randomUrl);
        Paste savedPaste = pasteRepository.save(paste);
        ActivityPaste activityPaste = ActivityPaste.of(savedPaste, requestUser, ActivityAction.INSERT);
        activityPasteRepository.save(activityPaste);

        return PasteMapper.mapper(savedPaste);
    }

    public PasteModel getPaste(String id) {
        return pasteRepository.findById(id)
                .map(PasteMapper::mapper)
                .orElseThrow(pasteNotFound);
    }

    public PasteModel getPasteForUser(String url, String username) {
        Predicate<Paste> isOwnPaste = paste -> paste.getCreatedBy().equals(username);
        Predicate<Paste> isPastePublic = paste -> paste.getStatus() == PasteStatus.PUBLIC;

        PasteModel pasteModel = Optional.of(pasteRepository.findByUrl(url))
                .orElseThrow(pasteNotFound)
                .filter(isOwnPaste.or(isPastePublic))
                .map(PasteMapper::mapper)
                .orElseThrow(notOwnPaste);

        List<FeedbackModel> feedbacks = feedbackService.getAllFeedbackForPaste(pasteModel.getId())
                .stream()
                .map(FeedbackMapper::mapper)
                .collect(Collectors.toList());

        pasteModel.setFeedbacks(feedbacks);
        return pasteModel;
    }

    public List<PasteModel> getAllPastes() {
        return pasteRepository.findAll()
                .stream()
                .map(PasteMapper::mapper)
                .collect(Collectors.toList());
    }

    public List<PasteModel> getAllPasteByUser(Long userId) {
        User user = userservice.findById(userId)
                .orElseThrow(UserService.userNotFound);

        return pasteRepository.findAllByPasteUser(user.getId())
                .stream()
                .map(PasteMapper::mapper)
                .collect(Collectors.toList());
    }

    public void deletePaste(String id, String requestUser) {
        Paste paste = pasteRepository.findById(id)
                .orElseThrow(pasteNotFound);

        if (!paste.getPasteUserName().equals(requestUser)) {
            throw new ValidationException(HttpStatus.UNAUTHORIZED, "pasteId", "error.not.own.paste");
        }
        ActivityPaste activityPaste = ActivityPaste.of(paste, requestUser, ActivityAction.DELETE);

        pasteRepository.deleteById(id);
        activityPasteRepository.save(activityPaste);
        feedbackService.deleteAllFeedbackForPaste(paste.getId());
    }

    public PasteModel updatePaste(String id, PasteUpdateRequest pasteUpdateRequest, String requestUser) {
        Paste paste = pasteRepository.findById(id)
                .orElseThrow(pasteNotFound);

        if (!paste.getPasteUserName().equals(requestUser)) {
            throw new ValidationException(HttpStatus.UNAUTHORIZED, "pasteId", "error.not.own.paste");
        }

        userservice.findById(paste.getPasteUser())
                .ifPresent(pasteUser ->
                        PasteMapper.updateRequestToEntity(paste, pasteUpdateRequest, requestUser, pasteUser));

        Paste updatedPaste = pasteRepository.save(paste);

        ActivityPaste activityPaste = ActivityPaste.of(updatedPaste, requestUser, ActivityAction.UPDATE);
        activityPasteRepository.save(activityPaste);

        return PasteMapper.mapper(updatedPaste);
    }
}
