package com.miu.pasteit.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private String pasteId;
    private Long userId;
    private String userName;
}
