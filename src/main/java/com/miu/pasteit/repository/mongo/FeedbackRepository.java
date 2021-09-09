package com.miu.pasteit.repository.mongo;

import com.miu.pasteit.model.entity.db.nosql.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Abdi Wako Jilo
 * @author Rimon Mostafiz
 */
@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, String> {
    List<Feedback> findAllByUserId(String userId);

    List<Feedback> findAllByPasteId(String pasteId);

}
