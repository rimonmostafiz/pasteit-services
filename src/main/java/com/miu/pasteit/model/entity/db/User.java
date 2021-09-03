package com.miu.pasteit.model.entity.db;

import com.miu.pasteit.model.entity.common.EntityCommon;
import com.miu.pasteit.model.entity.common.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

import static com.miu.pasteit.utils.ValidationConstants.*;

/**
 * @author Rimon Mostafiz
 */
@Data
@NoArgsConstructor
@Document("USER")
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper = true)
public class User extends EntityCommon {
    @Id
    private String id;

    @NotBlank(message = "{error.user.username.blank}")
    @Size(max = MAX_USERNAME_SIZE, message = "{error.user.username.max.size}")
    @Pattern(regexp = ALPHANUMERIC_UNDERSCORE_DOT, message = "{error.user.username.invalid}")
    private String username;

    @NotBlank(message = "{error.user.password.blank}")
    @Size(min = MIN_PASSWORD_SIZE, max = MAX_PASSWORD_SIZE, message = "{error.user.password.size}")
    private String password;

    @NotBlank(message = "{error.user.email.blank}")
    @Email(message = "{error.user.email.invalid}")
    @Size(max = MAX_EMAIL_SIZE, message = "{error.email.max.size}")
    private String email;

    @Size(max = MAX_FIRST_NAME_SIZE, message = "{error.user.firstName.max.size")
    private String firstName;

    @Size(max = MAX_LAST_NAME_SIZE, message = "{error.user.lastName.max.size")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{error.user.status.null}")
    private Status status;

    private String share;

    //TODO: Fetch EAGER
    private List<UserRoles> roles = new ArrayList<>();
}
