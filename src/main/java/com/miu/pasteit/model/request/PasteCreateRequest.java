package com.miu.pasteit.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Rimon Mostafiz
 * @author Abdi Wako Jilo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasteCreateRequest {

    @NotBlank(message = "{error.paste.content.blank}")
    private String content;

    private String title;

    private String description;

    private String status;

    private String language;

    private String folder;
}
