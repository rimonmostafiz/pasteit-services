package com.miu.pasteit.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Abdi Wako Jilo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackEditRequest implements Serializable {

    @NotBlank(message = "{error.feedback.comment.blank}")
    private String comment;
}