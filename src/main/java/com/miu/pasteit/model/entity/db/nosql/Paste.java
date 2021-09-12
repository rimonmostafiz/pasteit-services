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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rimon Mostafiz
 * @author Abdi Wako Jilo
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

    private Long pasteUser;

    private String pasteUserName;

    private LocalDateTime pasteDateTime;

    private String share;

    //@OneToMany(cascade = CascadeType.ALL)
    private List<Feedback> feedback = new ArrayList<>();

    private int likes;

    public void addFeedBack(Feedback feedback) {
        this.feedback.add(feedback);
    }

}
