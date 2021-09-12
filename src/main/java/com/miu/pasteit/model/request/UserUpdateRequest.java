package com.miu.pasteit.model.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.miu.pasteit.utils.ValidationConstants.*;

/**
 * @author Rimon Mostafiz
 * @author Abdi Wako Jilo
 */
@Data
public class UserUpdateRequest {

    @NotBlank(message = "{error.user.email.blank}")
    @Email(message = "{error.user.email.invalid}")
    @Size(max = MAX_EMAIL_SIZE, message = "{error.email.max.size}")
    private String email;

    @Size(max = MAX_FIRST_NAME_SIZE, message = "{error.user.firstName.max.size")
    private String firstName;

    @Size(max = MAX_LAST_NAME_SIZE, message = "{error.user.lastName.max.size")
    private String LastName;
}
