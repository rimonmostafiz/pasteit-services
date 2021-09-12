package com.miu.pasteit.service.feedback;

import com.miu.pasteit.component.exception.ValidationException;
import com.miu.pasteit.model.dto.PasteModel;
import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.nosql.Feedback;
import com.miu.pasteit.model.entity.db.nosql.Paste;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.mapper.FeedbackMapper;
import com.miu.pasteit.model.mapper.PasteMapper;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class FeedbackServiceTest {

    @InjectMocks
    FeedbackService feedbackservice;
    @Mock
    UserService userService;
    @Mock
    FeedbackRepository feedbackrepository;
    @Mock
    PasteRepository pasterepository;
    @Mock
    PasteMapper pastemapper;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

    }


@Test
    public void createFeedback() throws Exception{

       User user= User.of(234L,"user","111","nadia@gmail.com",
               "nadia","mimoun",Status.ACTIVE,null);
    Paste paste = Paste.of("1234","content","lkj","paste1",
            "/paste/1","desc", PasteStatus.PRIVATE, Language.JAVA,"folder",
            23L, 234L, LocalDateTime.now(),"share",null,100);
    FeedbackCreateRequest feedbackCreateRequest=new FeedbackCreateRequest("comment");
   given(userService.getUserByUsername(any())).willReturn(user);
    Feedback feedback = FeedbackMapper.createRequestToEntity(feedbackCreateRequest, "user",user);
    paste.setFeedback(new ArrayList<>());
       given( pasterepository.findById(any())).willReturn(Optional.of(paste));
    PasteMapper.addFeedbackToEntity(paste, feedback,"user", paste.getPasteUser());

    given(pasterepository.save(any())).willReturn(paste);
    given(feedbackrepository.findAllByUser(user)).willReturn(List.of(feedback));
    List<Feedback> result= feedbackservice.createFeedback("234l",feedbackCreateRequest,"user");

    Assertions.assertThat(result.size()).isEqualTo(1);


    }


}
