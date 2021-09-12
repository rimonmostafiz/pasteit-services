package com.miu.pasteit.api;

import com.miu.pasteit.model.common.RestResponse;
import com.miu.pasteit.model.dto.FeedbackModel;
import com.miu.pasteit.model.request.FeedbackCreateRequest;
import com.miu.pasteit.model.response.FeedbackResponse;
import com.miu.pasteit.service.feedback.FeedbackService;
import com.miu.pasteit.utils.ResponseUtils;
import com.miu.pasteit.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Abdi Wako Jilo
 * @author Rimon Mostafiz
 */
@Slf4j
@RestController
@Api(tags = "Feedback")
@RequestMapping("/v1")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/feedback/{pasteId}")
    @PreAuthorize("hasAnyAuthority('USER')")
    @ApiOperation(value = "Create Feedback", code = 201)
    public ResponseEntity<RestResponse<FeedbackResponse>> createPaste(@PathVariable String pasteId,
                                                                      @RequestBody @Valid FeedbackCreateRequest feedbackCreateRequest) {
        String requestUser = Utils.getRequestOwner();
        List<FeedbackModel> feedbacks = feedbackService.createFeedback(pasteId, feedbackCreateRequest, requestUser);
        FeedbackResponse feedbackResponse = FeedbackResponse.of(feedbacks);
        return ResponseUtils.buildSuccessResponse(HttpStatus.CREATED, feedbackResponse);
    }

}
