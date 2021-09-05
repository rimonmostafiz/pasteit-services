package com.miu.pasteit.api;

import com.miu.pasteit.model.common.RestResponse;
import com.miu.pasteit.model.dto.PasteModel;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.request.PasteCreateRequest;
import com.miu.pasteit.model.request.PasteEditRequest;
import com.miu.pasteit.model.response.PasteResponse;
import com.miu.pasteit.service.paste.PasteService;
import com.miu.pasteit.utils.ResponseUtils;
import com.miu.pasteit.utils.RoleUtils;
import com.miu.pasteit.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @ApiOperation(
            value = "Create Paste",
            code = 201
    )
    public ResponseEntity<RestResponse<PasteResponse>> createPaste(HttpServletRequest request,
                                                                   @RequestBody @Valid PasteCreateRequest PasteCreateRequest) {
        String requestUser = Utils.getUserNameFromRequest(request);
        PasteModel Paste = pasteService.createPaste(PasteCreateRequest, requestUser);
        PasteResponse pasteResponse = PasteResponse.of(Paste);
        return ResponseUtils.buildSuccessResponse(HttpStatus.CREATED, pasteResponse);
    }

    @PatchMapping("/paste/{id}")
    @ApiOperation(value = "Edit Paste")
    public ResponseEntity<RestResponse<PasteResponse>> editPaste(HttpServletRequest request,
                                                                 @PathVariable String id,
                                                                 @RequestBody PasteEditRequest pasteEditRequest) {
        String requestUser = Utils.getUserNameFromRequest(request);
        PasteModel paste = pasteService.updatePaste(id, pasteEditRequest, requestUser);
        PasteResponse pasteResponse = PasteResponse.of(paste);
        return ResponseUtils.buildSuccessResponse(HttpStatus.OK, pasteResponse);
    }

    @GetMapping("/paste/{id}")
    @ApiOperation(
            value = "Get Paste By ID",
            notes = "Need to pass valid Paste id to get details of the Paste"
    )
    public ResponseEntity<RestResponse<PasteResponse>> getPaste(HttpServletRequest request, @PathVariable String id) {
        PasteModel paste;
        final String username = Utils.getUserNameFromRequest(request);
        final boolean isAdmin = RoleUtils.hasPrivilege(request, RoleUtils.ADMIN_ROLE);
        paste = isAdmin ? pasteService.getPaste(id) : pasteService.getPasteForUser(id, username);
        PasteResponse pasteResponse = PasteResponse.of(paste);
        return ResponseUtils.buildSuccessResponse(HttpStatus.OK, pasteResponse);
    }

    @GetMapping("/paste/search-by-status-expired")
    @ApiOperation(value = "Search Paste - Get expired Pastes(due date in the past)")
    public ResponseEntity<RestResponse<PasteResponse>> getExpiredPastes() {
        List<PasteModel> expiredPastes = pasteService.getAllExpiredPaste();
        PasteResponse pasteResponse = PasteResponse.of(expiredPastes);
        return ResponseUtils.buildSuccessResponse(HttpStatus.OK, pasteResponse);
    }

    @GetMapping("/paste/search-by-status/{status}")
    @ApiOperation(
            value = "Search Paste - By status",
            notes = "Status should be public/private/delete"
    )
    public ResponseEntity<RestResponse<PasteResponse>> searchByStatus(@PathVariable String status) {
        PasteStatus pasteStatus = PasteStatus.getStatus(status);
        List<PasteModel> pastes = pasteService.getAllPasteByStatus(pasteStatus);
        PasteResponse pasteResponse = PasteResponse.of(pastes);
        return ResponseUtils.buildSuccessResponse(HttpStatus.OK, pasteResponse);
    }

    @GetMapping("/paste/search-by-user/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation(
            value = "Get all Pastes by user",
            notes = "An ADMIN User has the privilege to call this API"
    )
    public ResponseEntity<RestResponse<PasteResponse>> searchAllByUser(@PathVariable Long userId) {
        List<PasteModel> Pastes = pasteService.getAllPasteByUser(userId);
        PasteResponse pasteResponse = PasteResponse.of(Pastes);
        return ResponseUtils.buildSuccessResponse(HttpStatus.OK, pasteResponse);
    }
}
