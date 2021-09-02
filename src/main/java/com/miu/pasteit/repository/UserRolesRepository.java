package com.miu.pasteit.repository;

import com.miu.pasteit.model.entity.db.UserRoles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rimon Mostafiz
 */
@Repository
public interface UserRolesRepository extends MongoRepository<UserRoles, String> {
}
