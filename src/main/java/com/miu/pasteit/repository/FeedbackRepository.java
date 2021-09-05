package com.miu.pasteit.repository;

import com.miu.pasteit.model.entity.db.Feedback;
import com.miu.pasteit.model.entity.db.sql.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Abdi Wako Jilo
 */
@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, String> {
    List<Feedback> findAllByUser(User user);
}
