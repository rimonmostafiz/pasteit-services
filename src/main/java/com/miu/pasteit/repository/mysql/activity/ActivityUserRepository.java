package com.miu.pasteit.repository.mysql.activity;

import com.miu.pasteit.model.entity.activity.sql.ActivityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rimon Mostafiz
 */
@Repository
public interface ActivityUserRepository extends JpaRepository<ActivityUser, String> {
}
