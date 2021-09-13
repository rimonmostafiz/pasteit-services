package com.miu.pasteit.service.role;

import com.miu.pasteit.model.entity.db.sql.UserRoles;
import com.miu.pasteit.repository.mysql.UserRolesRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * @author Nadia Mimoun
 */

public class UserRoleServiceTest {
    @Mock
    UserRolesRepository userRolesRepository;

    @InjectMocks
    UserRoleService userRoleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveAllRolesForUser() throws Exception {
        UserRoles userRoles = UserRoles.of(222L, 245L, 23L, "role1");
        given(userRolesRepository.saveAllAndFlush(any())).willReturn(List.of(userRoles));
        List<UserRoles> result = userRoleService.saveAllRolesForUser(List.of(userRoles));
        Assertions.assertThat(result.get(0).getRoleId()).isEqualTo(23L);


    }
}
