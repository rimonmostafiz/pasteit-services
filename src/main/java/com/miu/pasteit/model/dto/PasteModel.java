package com.miu.pasteit.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.PasteStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rimon Mostafiz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasteModel {
    private String id;

    private String content;

    private String contentHash;

    private String title;

    private String url;

    private String description;

    private PasteStatus status;

    private Language language;

    private String folder;

    private LocalDateTime expiryDateTime;

    private Long pasteUser;

    private String pasteUserName;

    private LocalDateTime pasteDateTime;

    private String share;

    private List<FeedbackModel> feedbacks;
}
