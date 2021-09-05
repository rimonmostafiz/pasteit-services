package com.miu.pasteit.model.entity.activity.sql;

import com.miu.pasteit.model.entity.common.ActivityAction;
import com.miu.pasteit.model.entity.common.ActivityCommon;
import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.sql.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * @author Rimon Mostafiz
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "ACTIVITY_USER")
@EqualsAndHashCode(callSuper = true)
public class ActivityUser extends ActivityCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTIVITY_ID")
    private Long activityId;

    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private Status status;

    private static ActivityUser of(User entity) {
        ActivityUser activity = new ActivityUser();

        activity.setId(entity.getId());
        activity.setUsername(entity.getUsername());
        activity.setEmail(entity.getEmail());
        activity.setPassword(entity.getPassword());
        activity.setFirstName(entity.getFirstName());
        activity.setLastName(entity.getLastName());
        activity.setStatus(entity.getStatus());

        ActivityCommon.mapper(activity, entity);
        return activity;
    }

    public static ActivityUser of(User user, String activityUser, ActivityAction activityAction) {
        ActivityUser activity = of(user);
        ActivityCommon.mapper(activity, activityUser, activityAction);
        return activity;
    }

}
