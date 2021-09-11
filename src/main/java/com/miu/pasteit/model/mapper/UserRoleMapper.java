package com.miu.pasteit.model.mapper;

import com.miu.pasteit.model.entity.db.sql.UserRoles;

/**
 * @author Rimon Mostafiz
 */
public class UserRoleMapper {
    public static UserRoles createEntity(Long userId, Long roleId, String roleName) {
        UserRoles entity = new UserRoles();

        entity.setUserId(userId);
        entity.setRoleId(roleId);
        entity.setRoleName(roleName);

        entity.setCreatedBy("System");
        return entity;
    }
}
