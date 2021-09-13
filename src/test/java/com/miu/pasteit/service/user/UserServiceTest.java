package com.miu.pasteit.service.user;

import com.miu.pasteit.component.exception.EntityNotFoundException;
import com.miu.pasteit.model.entity.activity.sql.ActivityUser;
import com.miu.pasteit.model.entity.common.ActivityAction;
import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.entity.db.sql.UserRoles;
import com.miu.pasteit.model.mapper.UserMapper;
import com.miu.pasteit.model.mapper.UserRoleMapper;
import com.miu.pasteit.model.request.UserCreateRequest;
import com.miu.pasteit.model.request.UserUpdateRequest;
import com.miu.pasteit.repository.mysql.UserRepository;
import com.miu.pasteit.repository.mysql.activity.ActivityUserRepository;
import com.miu.pasteit.service.role.UserRoleService;
import com.miu.pasteit.utils.RoleEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ActivityUserRepository activityUserRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRoleService userRoleService;

    @InjectMocks
    UserService userservice;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUser() throws Exception {
        UserCreateRequest userCreateRequest = UserCreateRequest.of("nadiamimoun", "1hj",
                "nadia@gmail.com", "nadia", "mimoun");
        given(passwordEncoder.encode(any())).willReturn(userCreateRequest.getPassword());
        User user = UserMapper.mapUserCreateRequest(userCreateRequest, "user", passwordEncoder.encode(userCreateRequest.getPassword()));

        given(userRepository.saveAndFlush(any())).willReturn(user);
        List<UserRoles> listOfRoles = Collections
                .singletonList(UserRoleMapper
                        .createEntity(user.getId(), RoleEnum.USER.getRoleId(), RoleEnum.USER.name()
                        )
                );
        given(userRoleService.saveAllRolesForUser(any())).willReturn(listOfRoles);

        User result = userservice.createUser(userCreateRequest, "user");

        Assertions.assertThat(result.getEmail()).isEqualTo("nadia@gmail.com");
        Assertions.assertThat(result.getPassword()).isEqualTo("1hj");
    }

    @Test
    public void findById() throws Exception {
        User user = User.of(245L, "user", "111", "nadia@gmail.com",
                "nadia", "mimoun", Status.ACTIVE, null);
        given(userservice.findById(245L)).willReturn(Optional.of(user));
        Optional<User> result = userservice.findById(245L);
        Assertions.assertThat(result.get().getId()).isEqualTo(245L);
        Assertions.assertThat(result.get().getEmail()).isEqualTo("nadia@gmail.com");
    }


    @Test
    public void getUserByUsername() throws Exception {
        User user = User.of(245L, "user", "111", "nadia@gmail.com",
                "nadia", "mimoun", Status.ACTIVE, null);
        given(userRepository.findByUsername("user")).willReturn(Optional.of(user));
        User result = userservice.getUserByUsername("user");
        Assertions.assertThat(result.getId()).isEqualTo(245L);
        Assertions.assertThat(result.getEmail()).isEqualTo("nadia@gmail.com");
    }
    @Test
    public void usernotfound() throws Exception{
        given(userRepository.findByUsername("user")).willThrow( new EntityNotFoundException(HttpStatus.BAD_REQUEST, "userId", "error.user.not.found"));
        assertThrows(EntityNotFoundException.class, () -> userservice.getUserByUsername("user"));

    }

    @Test
    public void getUserByUsername_NotFound_Test() {
        given(userRepository.findByUsername("user")).willThrow(new EntityNotFoundException(HttpStatus.BAD_REQUEST, "245L", "error.user.not.found"));
        assertThrows(EntityNotFoundException.class, () -> userservice.getUserByUsername("user"));

    }
    @Test
    public void UpdateUser()throws Exception{
        User user = User.of(245L, "user", "111", "nadia@gmail.com",
                "nadia", "mimoun", Status.ACTIVE, null);
        given(userservice.findById(any())).willReturn(Optional.of(user));
        UserUpdateRequest userUpdateRequest=new UserUpdateRequest("nadia@gmail.com","nadia","mimoun");
        given(userRepository.saveAndFlush(UserMapper.mapUserUpdateRequest(user, userUpdateRequest,"user"))).willReturn(user);
        User result= userservice.updateUser(245L,userUpdateRequest,"user");

        Assertions.assertThat(result.getId()).isEqualTo(245L);
        Assertions.assertThat(result.getEmail()).isEqualTo("nadia@gmail.com");


    }


}

