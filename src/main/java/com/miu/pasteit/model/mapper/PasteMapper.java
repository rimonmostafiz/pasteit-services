package com.miu.pasteit.model.mapper;

import com.miu.pasteit.model.dto.PasteModel;
import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.db.nosql.Paste;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.entity.db.Feedback;
import com.miu.pasteit.model.request.FeedbackCreateRequest;
import com.miu.pasteit.model.request.PasteCreateRequest;
import com.miu.pasteit.model.request.PasteEditRequest;

import java.time.LocalDateTime;

/**
 * @author Rimon Mostafiz
 */
public class PasteMapper {

    public static PasteModel mapper(Paste entity) {
        PasteModel model = new PasteModel();
        model.setId(entity.getId());
        model.setContent(entity.getContent());
        model.setTitle(entity.getTitle());
        model.setUrl(entity.getUrl());
        model.setDescription(entity.getDescription());
        model.setStatus(entity.getStatus());
        model.setLanguage(entity.getLanguage());
        model.setFolder(entity.getFolder());
        model.setPasteUser(entity.getPasteUser());
        model.setPasteDateTime(entity.getPasteDateTime());
        return model;
    }

    public static Paste createRequestToEntity(PasteCreateRequest pasteCreateRequest, String createdBy, User user) {
        Paste entity = new Paste();

        entity.setContent(pasteCreateRequest.getContent());
        entity.setTitle(pasteCreateRequest.getTitle());
        entity.setDescription(pasteCreateRequest.getDescription());
        entity.setStatus(PasteStatus.getStatus(pasteCreateRequest.getStatus()));
        entity.setLanguage(Language.getLanguage(pasteCreateRequest.getLanguage()));
        entity.setPasteUser(user.getId());
        entity.setFolder(pasteCreateRequest.getFolder());
        entity.setPasteDateTime(LocalDateTime.now());

        entity.setCreatedBy(createdBy);
        return entity;
    }

    public static void updateRequestToEntity(Paste entity, PasteEditRequest pasteEditRequest, String createdBy, User user) {

        if (pasteEditRequest.getDescription() != null) {
            entity.setDescription(pasteEditRequest.getDescription());
        }
        if (pasteEditRequest.getStatus() != null) {
            entity.setStatus(PasteStatus.getStatus(pasteEditRequest.getStatus()));
        }
        if (user != null) {
            entity.setPasteUser(user.getId());
        }
        entity.setCreatedBy(createdBy);
    }

    public static void addFeedbackToEntity(Paste entity, Feedback feedback, String createdBy, Long user) {

        if (feedback.getComment() != null) {
            entity.addFeedBack(feedback);
        }
        entity.setCreatedBy(createdBy);
    }

}
