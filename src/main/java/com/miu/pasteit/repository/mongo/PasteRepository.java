package com.miu.pasteit.repository.mongo;

import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.db.nosql.Paste;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Rimon Mostafiz
 */
@Repository
public interface PasteRepository extends MongoRepository<Paste, String> {
    List<Paste> findAllByPasteUser(Long pasteUser);

    List<Paste> findAllByStatus(PasteStatus status);

    Optional<Paste> findByUrl(String url);
}
