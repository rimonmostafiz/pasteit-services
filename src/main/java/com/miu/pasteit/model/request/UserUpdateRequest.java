package com.miu.pasteit.model.request;

import lombok.Data;

/**
 * @author Rimon Mostafiz
 */
@Data
public class UserUpdateRequest {
    private String email;

    private String firstName;

    private String LastName;
}
