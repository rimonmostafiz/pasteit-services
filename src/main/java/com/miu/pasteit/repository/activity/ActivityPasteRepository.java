package com.miu.pasteit.repository.activity;

import com.miu.pasteit.model.entity.activity.ActivityPaste;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rimon Mostafiz
 */
@Repository
public interface ActivityPasteRepository extends MongoRepository<ActivityPaste, String> {
}
