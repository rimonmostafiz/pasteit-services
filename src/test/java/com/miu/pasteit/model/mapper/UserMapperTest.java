package com.miu.pasteit.model.mapper;

import com.miu.pasteit.model.dto.UserModel;
import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.request.UserCreateRequest;
import com.miu.pasteit.model.request.UserUpdateRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

/**
 * @author Rimon Mostafiz
 */
class UserMapperTest {

    @Test
    void mapToUserModel() {
        User user = User.of(245L, "user", "111", "nadia@gmail.com",
                "nadia", "mimoun", Status.ACTIVE, Collections.emptyList());
        UserModel userModel = UserMapper.mapToUserModel(user);
        Assertions.assertThat(userModel).isNotNull();
        Assertions.assertThat(userModel.getId()).isEqualTo(245L);

    }

    @Test
    void whenValidUserCreateRequest_ReturnUser() {
        UserCreateRequest userCreateRequest = UserCreateRequest.of(
                "samisino",
                "mypassword",
                "nosy@gmail.com",
                "samsamrr",
                "desdededs"

        );

        User user = UserMapper.mapUserCreateRequest(userCreateRequest, "samisino", "mypassword");

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getUsername()).isEqualTo(userCreateRequest.getUsername());


    }

    @Test
    void whenUpdateUserRequest_ReturnUserUpdate() {
        UserUpdateRequest updateUserRequest = new UserUpdateRequest(
                "nosy@gmail.com",
                "samsamrr",
                "desdededs"

        );

        User user = User.of(245L, "user", "111", "anonymous@gmail.com",
                "anonymous", "anonymour", Status.ACTIVE, null);

        User userReturned = UserMapper.mapUserUpdateRequest(user, updateUserRequest, "Admin" );

        Assertions.assertThat(userReturned).isNotNull();

        Assertions.assertThat(updateUserRequest.getEmail()).isEqualTo(userReturned.getEmail());
        Assertions.assertThat(updateUserRequest.getLastName()).isEqualTo(userReturned.getLastName());
    }
}