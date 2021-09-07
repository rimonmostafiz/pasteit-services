package com.miu.pasteit.model.entity.db.nosql;

import com.miu.pasteit.model.entity.common.EntityCommon;
import com.miu.pasteit.model.entity.db.sql.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.OneToMany;
import java.time.LocalDateTime;

/**
 * @author Abdi Wako Jilo
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Document("FEEDBACK")
@EqualsAndHashCode(callSuper = true)
public class Feedback extends EntityCommon {

    @Id
    private Long id;
    private String comment;
    private LocalDateTime dateTime;

    @OneToMany
    private User user;

    public Feedback(String comment, LocalDateTime dateTime, User user) {
        this.comment = comment;
        this.dateTime = dateTime;
        this.user = user;
    }
}
