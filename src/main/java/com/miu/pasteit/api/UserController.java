package com.miu.pasteit.api;

import com.miu.pasteit.model.common.RestResponse;
import com.miu.pasteit.model.dto.UserModel;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.mapper.UserMapper;
import com.miu.pasteit.model.request.UserCreateRequest;
import com.miu.pasteit.model.request.UserUpdateRequest;
import com.miu.pasteit.service.user.UserService;
import com.miu.pasteit.utils.ResponseUtils;
import com.miu.pasteit.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Samson Hailu
 * @author Rimon Mostafiz
 * @author Abdi Wako Jilo
 */

@Slf4j
@RestController
@Api(tags = "User")
@RequestMapping({"/v1"})
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping({"/user"})
    @ApiOperation(value = "Create User")
    public ResponseEntity<RestResponse<UserModel>> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        User user = userService.createUser(userCreateRequest, userCreateRequest.getUsername());
        UserModel userModel = UserMapper.mapToUserModel(user);
        return ResponseUtils.buildSuccessResponse(HttpStatus.CREATED, userModel);
    }

    @PatchMapping("/user/{id}")
    @ApiOperation(value = "Edit User")
    public ResponseEntity<RestResponse<UserModel>> editUser(@PathVariable Long id,
                                                            @RequestBody UserUpdateRequest userUpdateRequest) {
        String requestUser = Utils.getRequestOwner();
        User user = userService.updateUser(id, userUpdateRequest, requestUser);
        UserModel userModel = UserMapper.mapToUserModel(user);
        return ResponseUtils.buildSuccessResponse(HttpStatus.OK, userModel);
    }
}
