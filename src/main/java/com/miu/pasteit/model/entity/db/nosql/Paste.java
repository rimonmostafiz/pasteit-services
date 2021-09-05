package com.miu.pasteit.model.entity.db.nosql;

import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.NoSqlEntityCommon;
import com.miu.pasteit.model.entity.common.PasteStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

/**
 * @author Rimon Mostafiz
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Document("PASTE")
@EqualsAndHashCode(callSuper = true)
public class Paste extends NoSqlEntityCommon {
    @Id
    private String id;

    private String content;

    private String contentHash;

    private String title;

    private String url;

    private String description;

    @Enumerated(EnumType.STRING)
    private PasteStatus status;

    @Enumerated(EnumType.STRING)
    private Language language;

    private String folder;

    private Long validity;

    //TODO: fetch lazily
    private Long pasteUser;

    private LocalDateTime pasteDateTime;

    private String share;
}
