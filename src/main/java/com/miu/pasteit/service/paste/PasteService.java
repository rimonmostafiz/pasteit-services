package com.miu.pasteit.service.paste;

import com.miu.pasteit.component.exception.EntityNotFoundException;
import com.miu.pasteit.component.exception.ValidationException;
import com.miu.pasteit.model.dto.PasteModel;
import com.miu.pasteit.model.entity.activity.ActivityPaste;
import com.miu.pasteit.model.entity.common.ActivityAction;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.db.Paste;
import com.miu.pasteit.model.entity.db.User;
import com.miu.pasteit.model.mapper.PasteMapper;
import com.miu.pasteit.model.request.PasteCreateRequest;
import com.miu.pasteit.model.request.PasteEditRequest;
import com.miu.pasteit.repository.PasteRepository;
import com.miu.pasteit.repository.activity.ActivityPasteRepository;
import com.miu.pasteit.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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
    public static final Supplier<EntityNotFoundException> pasteNotFound = () ->
            new EntityNotFoundException(HttpStatus.BAD_REQUEST, "pasteId", "error.paste.not.found");
    public static final Supplier<ValidationException> notOwnPaste = () ->
            new ValidationException(HttpStatus.UNAUTHORIZED, "pasteId", "error.paste.user.not.authorized");
    private final UserService userservice;
    private final PasteRepository pasteRepository;
    private final ActivityPasteRepository activityPasteRepository;

    public PasteModel createPaste(PasteCreateRequest PasteCreateRequest, String requestUser) {
        User user = userservice.getUserByUsername(requestUser);
        Paste Paste = PasteMapper.createRequestToEntity(PasteCreateRequest, requestUser, user);
        Paste savedPaste = pasteRepository.save(Paste);

        ActivityPaste activityPaste = ActivityPaste.of(savedPaste, requestUser, ActivityAction.INSERT);
        activityPasteRepository.save(activityPaste);

        return PasteMapper.mapper(savedPaste);
    }

    public PasteModel getPaste(String id) {
        return pasteRepository.findById(id)
                .map(PasteMapper::mapper)
                .orElseThrow(pasteNotFound);
    }

    public PasteModel getPasteForUser(String id, String username) {
        Predicate<Paste> isOwnPaste = Paste -> Paste.getCreatedBy().equals(username);

        return Optional.of(pasteRepository.findById(id))
                .orElseThrow(pasteNotFound)
                .filter(isOwnPaste)
                .map(PasteMapper::mapper)
                .orElseThrow(notOwnPaste);
    }

    public List<PasteModel> getAllPastes() {
        return pasteRepository.findAll()
                .stream()
                .map(PasteMapper::mapper)
                .collect(Collectors.toList());
    }

    public List<PasteModel> getAllPasteByUser(String userId) {
        List<Paste> Pastes = userservice.findById(userId)
                .map(pasteRepository::findAllByPasteUser)
                .orElseThrow(UserService.userNotFound);

        return Pastes.stream()
                .map(PasteMapper::mapper)
                .collect(Collectors.toList());
    }

    public List<Paste> findAllByLanguage(String Language) {
        return null;
    }

    public List<PasteModel> getAllPasteByProject(Long projectId) {
        return null;
    }

    public List<PasteModel> getAllExpiredPaste() {
        return null;
    }

    public List<PasteModel> getAllPasteByStatus(PasteStatus status) {
        return pasteRepository.findAllByStatus(status)
                .stream()
                .map(PasteMapper::mapper)
                .collect(Collectors.toList());
    }

    public void deletePaste(String id, String requestUser) {
        Function<Paste, ActivityPaste> mapToActivity = Paste -> ActivityPaste.of(Paste, requestUser, ActivityAction.DELETE);

        ActivityPaste activityPaste = pasteRepository.findById(id)
                .map(mapToActivity)
                .orElseThrow(pasteNotFound);

        pasteRepository.deleteById(id);
        activityPasteRepository.save(activityPaste);
    }

    public PasteModel updatePaste(String id, PasteEditRequest pasteEditRequest, String requestUser) {
        Paste paste = pasteRepository.findById(id)
                .orElseThrow(pasteNotFound);

        if (paste.getStatus() == PasteStatus.DELETED) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "status", "error.paste.status.deleted.not.editable");
        }

        PasteMapper.updateRequestToEntity(paste, pasteEditRequest, requestUser, paste.getPasteUser());

        Paste savedPaste = pasteRepository.save(paste);

        ActivityPaste activityPaste = ActivityPaste.of(savedPaste, requestUser, ActivityAction.UPDATE);
        activityPasteRepository.save(activityPaste);

        return PasteMapper.mapper(savedPaste);
    }
}
