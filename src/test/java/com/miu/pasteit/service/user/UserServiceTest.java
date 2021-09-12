package com.miu.pasteit.service.user;

import com.miu.pasteit.component.exception.EntityNotFoundException;
import com.miu.pasteit.model.entity.activity.sql.ActivityUser;
import com.miu.pasteit.model.entity.common.ActivityAction;
import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.mapper.UserMapper;
import com.miu.pasteit.model.request.UserCreateRequest;
import com.miu.pasteit.repository.mongo.PasteRepository;
import com.miu.pasteit.repository.mysql.UserRepository;
import com.miu.pasteit.repository.mysql.activity.ActivityUserRepository;
import com.miu.pasteit.service.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class UserServiceTest {
    @InjectMocks
    UserService userservice;


    @Mock
    UserRepository userRepository;
    @Mock
    ActivityUserRepository activityUserRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }


    @Test
    public void createUser() throws Exception {
        UserCreateRequest userCreateRequest = new UserCreateRequest("nadiamimoun", "1hj",
                "nadia@gmail.com", "nadia", "mimoun", Status.ACTIVE);
        given(passwordEncoder.encode(any())).willReturn(userCreateRequest.getPassword());
        User user = UserMapper.mapUserCreateRequest(userCreateRequest, "user", passwordEncoder.encode(userCreateRequest.getPassword()));

        given(userRepository.save(any())).willReturn(user);
        ActivityUser activityUser = ActivityUser.of(user, "user", ActivityAction.INSERT);
        given(activityUserRepository.save(any())).willReturn(activityUser);
        User result = userservice.createUser(userCreateRequest, "user");


        Assertions.assertThat(result.getEmail()).isEqualTo("nadia@gmail.com");
        Assertions.assertThat(result.getPassword()).isEqualTo("1hj");


    }

    @Test
    public void findById() throws Exception{
        User user = User.of(245L,"user","111","nadia@gmail.com",
                "nadia","mimoun",Status.ACTIVE,null);
        given(userservice.findById(245L)).willReturn(Optional.of(user));
        Optional<User> result =userservice.findById(245L);
        Assertions.assertThat(result.get().getId()).isEqualTo(245L);
        Assertions.assertThat(result.get().getEmail()).isEqualTo("nadia@gmail.com");
    }
    @Test
    public void getUserByUsername() throws Exception{
        User user = User.of(245L,"user","111","nadia@gmail.com",
                "nadia","mimoun",Status.ACTIVE,null);
        given(userRepository.findByUsername("user")).willReturn(Optional.of(user));
        User result =userservice.getUserByUsername("user");
        Assertions.assertThat(result.getId()).isEqualTo(245L);
        Assertions.assertThat(result.getEmail()).isEqualTo("nadia@gmail.com");
    }
    @Test
    public void getUserByUsername_NotFound_Test(){
        given(userRepository.findByUsername("user")).willThrow(new EntityNotFoundException(HttpStatus.BAD_REQUEST, "245L", "error.user.not.found"));
        assertThrows(EntityNotFoundException.class,()->userservice.getUserByUsername("user"));

    }


}

