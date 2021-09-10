package com.miu.pasteit.model.entity.activity.nosql;

import com.miu.pasteit.model.entity.common.ActivityAction;
import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.NoSqlActivityCommon;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.db.nosql.Paste;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

/**
 * @author Rimon Mostafiz
 */
@Data
@NoArgsConstructor
@Document("ACTIVITY_PASTE")
@EqualsAndHashCode(callSuper = true)
public class ActivityPaste extends NoSqlActivityCommon {
    @Id
    private String activityId;

    //pasteId
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

    private LocalDateTime pasteDateTime;

    private String share;

    private static ActivityPaste of(Paste entity) {
        ActivityPaste activity = new ActivityPaste();

        activity.setId(entity.getId());
        activity.setDescription(entity.getDescription());
        activity.setStatus(entity.getStatus());
        if (entity.getPasteUser() != null) {
            activity.setPasteUser(entity.getPasteUser());
        }
        activity.setPasteDateTime(entity.getPasteDateTime());

        NoSqlActivityCommon.mapper(activity, entity);

        return activity;
    }

    public static ActivityPaste of(Paste paste, String activityUser, ActivityAction activityAction) {
        ActivityPaste activity = of(paste);
        NoSqlActivityCommon.mapper(activity, activityUser, activityAction);
        return activity;
    }
}
