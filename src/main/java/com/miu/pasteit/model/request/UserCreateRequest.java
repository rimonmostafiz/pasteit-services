package com.miu.pasteit.model.request;

import com.miu.pasteit.model.entity.common.Status;
import lombok.Data;

import javax.validation.constraints.*;

import static com.miu.pasteit.utils.ValidationConstants.*;
import static com.miu.pasteit.utils.ValidationConstants.MAX_PASSWORD_SIZE;

/**
 * @author Rimon Mostafiz
 * @author Abdi Wako Jilo
 */
@Data
public class UserCreateRequest {
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

    @Size(max = MAX_LAST_NAME_SIZE, message = "{error.user.LastName.max.size")
    private String LastName;
}
