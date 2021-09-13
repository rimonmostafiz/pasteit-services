package com.miu.pasteit.model.mapper;

import com.miu.pasteit.model.dto.FeedbackModel;
import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.nosql.Feedback;
import com.miu.pasteit.model.entity.db.nosql.Paste;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.request.FeedbackCreateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author Nadia Mimoun
 */

public class FeedBackMapperTest {


    @Test
    public void mapper() {
        Feedback feedback = Feedback.of("123", "comment", LocalDateTime.now(), "1234", 12L, "username");
        FeedbackModel result = FeedbackMapper.mapper(feedback);
        Assertions.assertEquals(result.getId(), feedback.getId());
        Assertions.assertEquals(result.getComment(), feedback.getComment());

    }

    @Test
    public void createRequestToEntity() {
        FeedbackCreateRequest feedbackCreateRequest = new FeedbackCreateRequest("comment");
        User user = User.of(245L, "user", "111", "nadia@gmail.com",
                "nadia", "mimoun", Status.ACTIVE, null);
        Paste paste = Paste.of("1234", "content", "lkj", "paste1",
                "/paste/1", "desc", PasteStatus.PRIVATE, Language.JAVA, "folder",
                23L, 234L, "username", LocalDateTime.now(), "share", null, 100);
        Feedback result = FeedbackMapper.createRequestToEntity(feedbackCreateRequest, "user", user, paste);
        Assertions.assertEquals(result.getComment(), feedbackCreateRequest.getComment());
        Assertions.assertEquals(result.getUserId(), user.getId());


    }


}
