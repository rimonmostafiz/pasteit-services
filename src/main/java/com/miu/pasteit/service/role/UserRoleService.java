package com.miu.pasteit.service.role;

import com.miu.pasteit.model.entity.db.sql.UserRoles;
import com.miu.pasteit.repository.mysql.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rimon Mostafiz
 */
@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRolesRepository userRolesRepository;

    public List<UserRoles> saveAllRolesForUser(List<UserRoles> userRoles) {
        return userRolesRepository.saveAllAndFlush(userRoles);
    }
}
