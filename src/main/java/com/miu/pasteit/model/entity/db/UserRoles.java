package com.miu.pasteit.model.entity.db;

import com.miu.pasteit.model.entity.common.EntityCommon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Rimon Mostafiz
 */
@Data
@NoArgsConstructor
@Document("USER_ROLES")
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper = true)
public class UserRoles extends EntityCommon {
    @Id
    private String id;

    private String userId;

    private String roleId;

    private String roleName;
}
