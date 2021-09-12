package com.miu.pasteit.service.feedback;

import com.miu.pasteit.component.exception.EntityNotFoundException;
import com.miu.pasteit.model.dto.PasteModel;
import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.db.nosql.Feedback;
import com.miu.pasteit.model.entity.db.nosql.Paste;
import com.miu.pasteit.repository.mongo.FeedbackRepository;
import com.miu.pasteit.repository.mongo.PasteRepository;
import com.miu.pasteit.repository.mongo.activity.ActivityPasteRepository;
import com.miu.pasteit.service.paste.PasteService;
import com.miu.pasteit.service.user.UserService;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class FeedbackServiceTest {
    @Mock
    FeedbackRepository feedbackRepository;


    @InjectMocks
    FeedbackService feedbackService;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void shouldReturnAllFeedbackByFeedbackForPaste(){
        Feedback feedback1= Feedback.of("11","Test comment for code",
                LocalDateTime.now(),"11",11L,"eyob");
        Feedback feedback2= Feedback.of("12","Java comment",
                LocalDateTime.now(),"11",12L,"eyob");

        given(feedbackRepository.findAllByPasteId("11")).willReturn((List.of(feedback1,feedback2)));
        List<Feedback> result =  feedbackService.getAllFeedbackForPaste("11");
        Assertions.assertThat(result.size()).isEqualTo(2);
    }
    @Test
    public void getFeedBackForBestNotFoundTest(){
        given(feedbackRepository.findAllByPasteId("33")).willThrow(new EntityNotFoundException(HttpStatus.BAD_REQUEST,
                "pasteId", "error.feedback.not.found"));
        assertThrows(EntityNotFoundException.class,()->feedbackService.getAllFeedbackForPaste("33"));

    }
    @Test
    public  void shouldCreateFeedback(){

    }
}