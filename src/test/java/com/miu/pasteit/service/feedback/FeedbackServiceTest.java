package com.miu.pasteit.service.feedback;

import com.miu.pasteit.component.exception.EntityNotFoundException;
import com.miu.pasteit.model.dto.FeedbackModel;
import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.nosql.Feedback;
import com.miu.pasteit.model.entity.db.nosql.Paste;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.mapper.FeedbackMapper;
import com.miu.pasteit.model.request.FeedbackCreateRequest;
import com.miu.pasteit.repository.mongo.FeedbackRepository;
import com.miu.pasteit.repository.mongo.PasteRepository;
import com.miu.pasteit.service.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * @author Nadia Mimoun
 */

class FeedbackServiceTest {
    @Mock
    FeedbackRepository feedbackRepository;

    @Mock
    UserService userService;

    @Mock
    PasteRepository pasteRepository;

    @InjectMocks
    FeedbackService feedbackService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createFeedback() throws Exception {

        User user = User.of(234L, "user", "111", "nadia@gmail.com",
                "nadia", "mimoun", Status.ACTIVE, null);
        Paste paste = Paste.of("1234", "content", "lkj", "paste1",
                "/paste/1", "desc", PasteStatus.PRIVATE, Language.JAVA, "folder",
                23L, 234L, "username", LocalDateTime.now(), "share", null, 100);
        FeedbackCreateRequest feedbackCreateRequest = new FeedbackCreateRequest("comment");
        given(userService.getUserByUsername(any())).willReturn(user);
        Feedback feedback = FeedbackMapper.createRequestToEntity(feedbackCreateRequest, "user", user, paste);
        paste.setFeedback(new ArrayList<>());
        given(pasteRepository.findById(any())).willReturn(Optional.of(paste));

        given(pasteRepository.save(any())).willReturn(paste);
        given(feedbackRepository.findAllByPasteId(any())).willReturn(List.of(feedback));
        List<FeedbackModel> feedbackModels = feedbackService.createFeedback("234l", feedbackCreateRequest, "user");

        Assertions.assertThat(feedbackModels.size()).isEqualTo(1);


    }

    @Test
    void shouldReturnAllFeedbackByFeedbackForPaste() {
        Feedback feedback1 = Feedback.of("11", "Test comment for code",
                LocalDateTime.now(), "11", 11L, "eyob");
        Feedback feedback2 = Feedback.of("12", "Java comment",
                LocalDateTime.now(), "11", 12L, "eyob");

        given(feedbackRepository.findAllByPasteId("11")).willReturn((List.of(feedback1, feedback2)));
        List<Feedback> result = feedbackService.getAllFeedbackForPaste("11");
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void getFeedBackForBestNotFoundTest() {
        given(feedbackRepository.findAllByPasteId("33")).willThrow(new EntityNotFoundException(HttpStatus.BAD_REQUEST,
                "pasteId", "error.feedback.not.found"));
        assertThrows(EntityNotFoundException.class, () -> feedbackService.getAllFeedbackForPaste("33"));

    }

    @Test
    public void shouldCreateFeedback() {

    }

    @Test
    void deleteAllFeedbackForPaste() {
        given(feedbackRepository.findAllByPasteId("123")).willReturn(Collections.emptyList());
        feedbackService.deleteAllFeedbackForPaste("123");
    }
}