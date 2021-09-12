package com.miu.pasteit.model.mapper;

import com.miu.pasteit.model.dto.FeedbackModel;
import com.miu.pasteit.model.entity.db.nosql.Feedback;
import com.miu.pasteit.model.entity.db.nosql.Paste;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.request.FeedbackCreateRequest;

import java.time.LocalDateTime;

/**
 * @author Abdi Wako Jilo
 * @author Rimon Mostafiz
 */
public class FeedbackMapper {

    public static FeedbackModel mapper(Feedback entity) {
        FeedbackModel model = new FeedbackModel();
        model.setId(entity.getId());
        model.setComment(entity.getComment());
        model.setDateTime(entity.getDateTime());
        model.setUserId(entity.getUserId());
        model.setUserName(entity.getUserName());
        return model;
    }

    public static Feedback createRequestToEntity(FeedbackCreateRequest feedbackCreateRequest, String createdBy, User user, Paste paste) {
        Feedback entity = new Feedback();

        entity.setComment(feedbackCreateRequest.getComment());
        entity.setDateTime(LocalDateTime.now());
        entity.setUserId(user.getId());
        entity.setUserName(user.getUsername());
        entity.setPasteId(paste.getId());

        entity.setCreatedBy(createdBy);
        return entity;
    }
}
