package com.miu.pasteit.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.miu.pasteit.model.dto.FeedbackModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Abdi Wako Jilo
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackResponse {
    private List<FeedbackModel> feedbacks;

    private FeedbackModel feedback;

    public static FeedbackResponse of(List<FeedbackModel> feedbacks) {
        FeedbackResponse response = new FeedbackResponse();
        response.setFeedbacks(feedbacks);
        return response;
    }

    public static FeedbackResponse of(FeedbackModel feedback) {
        FeedbackResponse response = new FeedbackResponse();
        response.setFeedback(feedback);
        return response;
    }
}
