package com.miu.pasteit.model.entity.activity;

import com.miu.pasteit.model.entity.common.ActivityAction;
import com.miu.pasteit.model.entity.common.ActivityCommon;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.db.Paste;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Rimon Mostafiz
 */
@Data
@NoArgsConstructor
@Document("ACTIVITY_PASTE")
@EqualsAndHashCode(callSuper = true)
public class ActivityPaste extends ActivityCommon {
    @Id
    private String activityId;

    //pasteId
    private String id;

    private String description;

    @Enumerated(EnumType.STRING)
    private PasteStatus status;

    private String pasteUser;

    private LocalDateTime pasteDateTime;

    private static ActivityPaste of(Paste entity) {
        ActivityPaste activity = new ActivityPaste();

        activity.setId(entity.getId());
        activity.setDescription(entity.getDescription());
        activity.setStatus(entity.getStatus());
        if (entity.getPasteUser() != null) {
            activity.setPasteUser(entity.getPasteUser().getId());
        }
        activity.setPasteDateTime(entity.getPasteDateTime());

        ActivityCommon.mapper(activity, entity);

        return activity;
    }

    public static ActivityPaste of(Paste paste, String activityUser, ActivityAction activityAction) {
        ActivityPaste activity = of(paste);
        ActivityCommon.mapper(activity, activityUser, activityAction);
        return activity;
    }
}
