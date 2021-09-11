package com.miu.pasteit.repository.mysql;

import com.miu.pasteit.model.entity.db.sql.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rimon Mostafiz
 */
@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
}
