package com.miu.pasteit.repository.mongo.activity;

import com.miu.pasteit.model.entity.activity.nosql.ActivityPaste;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rimon Mostafiz
 */
@Repository
public interface ActivityPasteRepository extends MongoRepository<ActivityPaste, String> {
}
