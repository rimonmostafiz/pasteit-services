package com.miu.pasteit.service.user;

import com.miu.pasteit.component.exception.EntityNotFoundException;
import com.miu.pasteit.component.exception.ValidationException;
import com.miu.pasteit.model.entity.activity.sql.ActivityUser;
import com.miu.pasteit.model.entity.common.ActivityAction;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Rimon Mostafiz
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    public static final Supplier<EntityNotFoundException> userNotFound = () -> new EntityNotFoundException(HttpStatus.BAD_REQUEST, "userId", "error.user.not.found");
    private final UserRepository userRepository;
    private final ActivityUserRepository activityUserRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserRoleService userRoleService;

    public User createUser(UserCreateRequest userCreateRequest, String requestUser) {
        userRepository.findByUsername(userCreateRequest.getUsername())
                .ifPresent(user -> {
                    throw new ValidationException(HttpStatus.BAD_REQUEST, "username", "Username already exists");
                });

        userRepository.findByEmail(userCreateRequest.getEmail())
                .ifPresent(user -> {
                    throw new ValidationException(HttpStatus.BAD_REQUEST, "email", "Email already exists");
                });

        String encodedPassword = bCryptPasswordEncoder.encode(userCreateRequest.getPassword());
        User user = UserMapper.mapUserCreateRequest(userCreateRequest, requestUser, encodedPassword);
        User savedUser = userRepository.saveAndFlush(user);
        List<UserRoles> listOfRoles = Collections
                .singletonList(UserRoleMapper
                        .createEntity(savedUser.getId(), RoleEnum.USER.getRoleId(), RoleEnum.USER.name()
                        )
                );
        List<UserRoles> userRoles = userRoleService.saveAllRolesForUser(listOfRoles);
        savedUser.setRoles(userRoles);

        ActivityUser activityUser = ActivityUser.of(savedUser, requestUser, ActivityAction.INSERT);
        activityUserRepository.save(activityUser);

        return savedUser;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(userNotFound);
    }

    public User updateUser(Long id, UserUpdateRequest userUpdateRequest, String requestUser) {
        userRepository.findByEmail(userUpdateRequest.getEmail())
                .ifPresent(user -> {
                    throw new ValidationException(HttpStatus.BAD_REQUEST, "email", "Email already exists");
                });

        User user = this.findById(id).orElseThrow(userNotFound);
        User updatedUser = userRepository.saveAndFlush(UserMapper.mapUserUpdateRequest(user, userUpdateRequest, requestUser));

        ActivityUser activityUser = ActivityUser.of(updatedUser, requestUser, ActivityAction.UPDATE);
        activityUserRepository.save(activityUser);
        return updatedUser;
    }
}
