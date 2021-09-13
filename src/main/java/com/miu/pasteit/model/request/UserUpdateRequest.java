package com.miu.pasteit.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

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
@AllArgsConstructor
public class UserUpdateRequest {

    @NotBlank(message = "{error.user.email.blank}")
    @Email(message = "{error.user.email.invalid}")
    @Size(max = MAX_EMAIL_SIZE, message = "{error.email.max.size}")
    private String email;

    @Size(max = MAX_FIRST_NAME_SIZE, message = "{error.user.firstName.max.size")
    @Pattern(regexp = ALPHABET_SPACE_HYPHEN_DOT, message = "{error.user.firstName.invalid}")
    private String firstName;

    @Size(max = MAX_LAST_NAME_SIZE, message = "{error.user.lastName.max.size")
    @Pattern(regexp = ALPHABET_SPACE_HYPHEN_DOT, message = "{error.user.lastName.invalid}")
    private String LastName;
}
