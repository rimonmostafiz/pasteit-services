package com.miu.pasteit.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.miu.pasteit.utils.ValidationConstants.*;

/**
 * @author Rimon Mostafiz
 * @author Abdi Wako Jilo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
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

    @NotBlank(message = "{error.user.firstName.blank}")
    @Size(max = MAX_FIRST_NAME_SIZE, message = "{error.user.firstName.max.size")
    @Pattern(regexp = ALPHABET_SPACE_HYPHEN_DOT, message = "{error.user.lastName.invalid}")
    private String firstName;

    @NotBlank(message = "{error.user.lastName.blank}")
    @Size(max = MAX_LAST_NAME_SIZE, message = "{error.user.LastName.max.size")
    @Pattern(regexp = ALPHABET_SPACE_HYPHEN_DOT, message = "{error.user.lastName.invalid}")
    private String LastName;
}
