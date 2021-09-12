package com.miu.pasteit.api;

import com.miu.pasteit.model.common.RestResponse;
import com.miu.pasteit.model.dto.PasteModel;
import com.miu.pasteit.model.request.PasteCreateRequest;
import com.miu.pasteit.model.request.PasteUpdateRequest;
import com.miu.pasteit.model.response.PasteResponse;
import com.miu.pasteit.service.paste.PasteService;
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
 * @author Rimon Mostafiz
 */
@Slf4j
@RestController
@Api(tags = "Paste")
@RequestMapping("/v1")
@RequiredArgsConstructor
public class PasteController {

    private final PasteService pasteService;

    @PostMapping("/paste")
    @PreAuthorize("hasAnyAuthority('USER')")
    @ApiOperation(value = "Create Paste", code = 201)
    public ResponseEntity<RestResponse<PasteResponse>> createPaste(@RequestBody @Valid PasteCreateRequest PasteCreateRequest) {
        String requestUser = Utils.getRequestOwner();
        PasteModel Paste = pasteService.createPaste(PasteCreateRequest, requestUser);
        PasteResponse pasteResponse = PasteResponse.of(Paste);
        return ResponseUtils.buildSuccessResponse(HttpStatus.CREATED, pasteResponse);
    }

    @PatchMapping("/paste/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    @ApiOperation(value = "Edit Paste")
    public ResponseEntity<RestResponse<PasteResponse>> editPaste(@PathVariable String id,
                                                                 @RequestBody PasteUpdateRequest pasteUpdateRequest) {
        String requestUser = Utils.getRequestOwner();
        PasteModel paste = pasteService.updatePaste(id, pasteUpdateRequest, requestUser);
        PasteResponse pasteResponse = PasteResponse.of(paste);
        return ResponseUtils.buildSuccessResponse(HttpStatus.OK, pasteResponse);
    }

    @GetMapping("/paste/{url}")
    @ApiOperation(
            value = "Get Paste By URL",
            notes = "Need to pass valid paste url to get details of the Paste"
    )
    public ResponseEntity<RestResponse<PasteResponse>> getPaste(@PathVariable String url) {
        final String username = Utils.getRequestOwner();
        PasteModel paste = pasteService.getPasteForUser(url, username);
        PasteResponse pasteResponse = PasteResponse.of(paste);
        return ResponseUtils.buildSuccessResponse(HttpStatus.OK, pasteResponse);
    }

    @DeleteMapping("/paste/{id}")
    @ApiOperation(
            value = "Delete Paste",
            notes = "Need to pass valid paste id")
    public ResponseEntity<RestResponse<String>> deletePaste(@PathVariable String id) {
        final String username = Utils.getRequestOwner();
        pasteService.deletePaste(id, username);
        return ResponseUtils.buildSuccessResponse(HttpStatus.OK, "Paste deleted successfully");
    }

    @GetMapping("/paste/search-by-user/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation(
            value = "Get all Pastes by user",
            notes = "An ADMIN User has the privilege to call this API"
    )
    public ResponseEntity<RestResponse<PasteResponse>> searchAllByUser(@PathVariable Long userId) {
        List<PasteModel> pastes = pasteService.getAllPasteByUser(userId);
        PasteResponse pasteResponse = PasteResponse.of(pastes);
        return ResponseUtils.buildSuccessResponse(HttpStatus.OK, pasteResponse);
    }
}
