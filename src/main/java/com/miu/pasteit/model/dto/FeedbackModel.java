package com.miu.pasteit.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.db.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Abdi Wako Jilo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackModel {
    private String id;
    private String comment;
    private LocalDateTime dateTime;
    private UserModel user;
}
