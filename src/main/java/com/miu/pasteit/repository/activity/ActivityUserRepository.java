package com.miu.pasteit.repository.activity;

import com.miu.pasteit.model.entity.activity.ActivityUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rimon Mostafiz
 */
@Repository
public interface ActivityUserRepository extends MongoRepository<ActivityUser, String> {
}
