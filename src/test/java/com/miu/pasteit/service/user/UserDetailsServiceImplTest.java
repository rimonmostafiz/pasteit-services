package com.miu.pasteit.service.user;

import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.entity.db.sql.UserRoles;
import com.miu.pasteit.model.mapper.UserRoleMapper;
import com.miu.pasteit.repository.mysql.UserRepository;
import com.miu.pasteit.utils.RoleEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;

/**
 * @author Rimon Mostafiz
 */
class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername() {
        UserRoles entity = UserRoleMapper.createEntity(245L, RoleEnum.USER.getRoleId(), RoleEnum.USER.name());
        User user = User.of(245L, "user", "111", "nadia@gmail.com",
                "nadia", "mimoun", Status.ACTIVE, Collections.singletonList(entity));

        given(userRepository.findByUsername(Mockito.anyString())).willReturn((Optional.of(user)));
        UserDetails userDetails = userDetailsService.loadUserByUsername("xyz");
        Assertions.assertThat(userDetails).isNotNull();
        Assertions.assertThat(userDetails.getUsername()).isNotNull();
        Assertions.assertThat(userDetails.getUsername()).isEqualTo("user");
        Assertions.assertThat(userDetails.getPassword()).isNotNull();
        Assertions.assertThat(userDetails.getPassword()).isEqualTo("111");
        Assertions.assertThat(userDetails.getAuthorities()).isNotEmpty();
        Assertions.assertThat(userDetails.getAuthorities().size()).isOne();
        List<RoleEnum> collect = userDetails.getAuthorities().stream().map(x -> RoleEnum.getRoleName(x.getAuthority())).collect(Collectors.toList());
        Assertions.assertThat(collect).contains(RoleEnum.USER);
    }
}