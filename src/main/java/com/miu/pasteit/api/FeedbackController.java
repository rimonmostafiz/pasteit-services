package com.miu.pasteit.api;

import com.miu.pasteit.model.common.RestResponse;
import com.miu.pasteit.model.entity.db.Feedback;
import com.miu.pasteit.model.request.FeedbackCreateRequest;
import com.miu.pasteit.service.feedback.FeedbackService;
import com.miu.pasteit.service.paste.PasteService;
import com.miu.pasteit.utils.ResponseUtils;
import com.miu.pasteit.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Abdi Wako Jilo
 */
@Slf4j
@RestController
@Api(tags = "Feedback")
@RequestMapping("/v1")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final PasteService pasteService;

    @PostMapping("/feedback/{id}")
    @ApiOperation(
            value = "Create Feedback",
            code = 201
    )
    public ResponseEntity<RestResponse<List<Feedback>>> createPaste(HttpServletRequest request,
                                                                      @PathVariable String id,
                                                                      @RequestBody @Valid FeedbackCreateRequest feedbackCreateRequest) {
        String requestUser = Utils.getUserNameFromRequest(request);
        List<Feedback> feedback = feedbackService.createFeedback(id, feedbackCreateRequest, requestUser);
        return ResponseUtils.buildSuccessResponse(HttpStatus.CREATED, feedback);
    }

}
