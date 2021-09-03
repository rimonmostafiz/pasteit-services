package com.miu.pasteit.api;

import com.miu.pasteit.model.entity.db.User;
import com.miu.pasteit.model.request.UserCreateRequest;
import com.miu.pasteit.repository.UserRepository;
import com.miu.pasteit.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Author Samson Hailu
 */

@RestController
@RequestMapping({"/v1/api/user"})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping({"/create"})
    public ResponseEntity<User> createUser(@RequestBody UserCreateRequest userCreateRequest) {

        User user = userService.createUser(userCreateRequest, userCreateRequest.getUsername());

        user.setPassword(this.bCryptPasswordEncoder.encode(userCreateRequest.getPassword()));

        return ResponseEntity.ok(user);
    }
}
