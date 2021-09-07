package com.miu.pasteit.model.mapper;

import com.miu.pasteit.model.dto.FeedbackModel;
import com.miu.pasteit.model.entity.db.nosql.Feedback;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.request.FeedbackCreateRequest;
import com.miu.pasteit.model.request.FeedbackEditRequest;

import java.time.LocalDateTime;

/**
 * @author Abdi Wako Jilo
 */
public class FeedbackMapper {

    public static FeedbackModel mapper(Feedback entity) {
        FeedbackModel model = new FeedbackModel();
        model.setId(entity.getId());
        model.setComment(entity.getComment());
        model.setDateTime(entity.getDateTime());
        model.setUser(UserMapper.mapperForInternal(entity.getUser()));
        return model;
    }

    public static Feedback createRequestToEntity(FeedbackCreateRequest feedbackCreateRequest, String createdBy, User user) {
        Feedback entity = new Feedback();

        entity.setComment(feedbackCreateRequest.getComment());
        entity.setUser(user);
        entity.setDateTime(LocalDateTime.now());

        entity.setCreatedBy(createdBy);
        return entity;
    }

    public static void updateRequestToEntity(Feedback entity, FeedbackEditRequest feedbackEditRequest, String createdBy, User user) {

        if (feedbackEditRequest.getComment() != null) {
            entity.setComment(feedbackEditRequest.getComment());
        }
        if (user != null) {
            entity.setUser(user);
        }
        entity.setDateTime(LocalDateTime.now());
        entity.setCreatedBy(createdBy);
    }
}
