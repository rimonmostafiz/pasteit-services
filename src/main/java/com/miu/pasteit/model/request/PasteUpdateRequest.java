package com.miu.pasteit.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rimon Mostafiz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasteUpdateRequest {
    private String content;

    private String description;

    private String status;

    private String language;

    private String folder;
}
