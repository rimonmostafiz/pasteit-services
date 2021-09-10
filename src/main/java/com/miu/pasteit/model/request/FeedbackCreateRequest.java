package com.miu.pasteit.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Abdi Wako Jilo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackCreateRequest implements Serializable {
    private String comment;
}