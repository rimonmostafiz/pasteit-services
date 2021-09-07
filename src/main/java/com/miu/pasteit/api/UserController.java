package com.miu.pasteit.api;

import com.miu.pasteit.model.common.RestResponse;
import com.miu.pasteit.model.dto.UserModel;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.mapper.UserMapper;
import com.miu.pasteit.model.request.UserCreateRequest;
import com.miu.pasteit.service.user.UserService;
import com.miu.pasteit.utils.ResponseUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Samson Hailu
 * @author Rimon Mostafiz
 */

@Slf4j
@RestController
@Api(tags = "User")
@RequestMapping({"/v1/api/user"})
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping({"/create"})
    public ResponseEntity<RestResponse<UserModel>> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        User user = userService.createUser(userCreateRequest, userCreateRequest.getUsername());
        UserModel userModel = UserMapper.mapToUserModel(user);
        return ResponseUtils.buildSuccessResponse(HttpStatus.CREATED, userModel);
    }
}
