package com.miu.pasteit.model.entity.db;

import com.miu.pasteit.model.entity.common.EntityCommon;
import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.PasteStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Rimon Mostafiz
 */

@ToString
@NoArgsConstructor
@Document("PASTE")
@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = true)
public class Paste extends EntityCommon {
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
    private User pasteUser;

    private LocalDateTime pasteDateTime;

    private String share;
}
