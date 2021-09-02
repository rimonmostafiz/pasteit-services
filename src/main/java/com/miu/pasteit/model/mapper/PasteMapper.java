package com.miu.pasteit.model.mapper;

import com.miu.pasteit.model.dto.PasteModel;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.db.Paste;
import com.miu.pasteit.model.entity.db.User;
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
        model.setDescription(entity.getDescription());
        model.setStatus(entity.getStatus());
        model.setPasteUser(UserMapper.mapperForInternal(entity.getPasteUser()));
        model.setPasteDateTime(entity.getPasteDateTime());
        return model;
    }

    public static Paste createRequestToEntity(PasteCreateRequest pasteCreateRequest, String createdBy, User user) {
        Paste entity = new Paste();

        entity.setDescription(pasteCreateRequest.getDescription());
        entity.setStatus(PasteStatus.getStatus(pasteCreateRequest.getStatus()));
        entity.setPasteUser(user);
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
            entity.setPasteUser(user);
        }
        entity.setCreatedBy(createdBy);
    }
}
