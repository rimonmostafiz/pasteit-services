package com.miu.pasteit.service.user;

import com.miu.pasteit.component.exception.EntityNotFoundException;
import com.miu.pasteit.model.entity.activity.ActivityUser;
import com.miu.pasteit.model.entity.common.ActivityAction;
import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.User;
import com.miu.pasteit.model.entity.db.UserRoles;
import com.miu.pasteit.model.mapper.UserMapper;
import com.miu.pasteit.model.request.UserCreateRequest;
import com.miu.pasteit.repository.UserRepository;
import com.miu.pasteit.repository.activity.ActivityUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Rimon Mostafiz
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    public static final Predicate<User> isNotInactive = user -> !user.getStatus().equals(Status.INACTIVE);
    public static final Supplier<EntityNotFoundException> userNotFound = () ->
            new EntityNotFoundException(HttpStatus.BAD_REQUEST, "userId", "error.user.not.found");
    public static final Supplier<DisabledException> userIsDisabled = () ->
            new DisabledException("error.user.is.disabled");
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ActivityUserRepository activityUserRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public User createUser(UserCreateRequest userCreateRequest, String requestUser) {
        User user = UserMapper.mapUserCreateRequest(userCreateRequest, requestUser, bCryptPasswordEncoder.encode(userCreateRequest.getPassword()));
        User savedUser = userRepository.save(user);

        ActivityUser activityUser = ActivityUser.of(savedUser, requestUser, ActivityAction.INSERT);
        log.debug("activity User: {}", activityUser);
        activityUserRepository.save(activityUser);

        return savedUser;
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(userNotFound);
    }

    public void ifDisableThrowException(User user) {
        Optional.ofNullable(user)
                .filter(isNotInactive)
                .orElseThrow(userIsDisabled);
    }

    public List<String> getUserRoles(User user) {
        return user.getRoles()
                .stream()
                .map(UserRoles::getRoleName)
                .collect(Collectors.toList());
    }
}
