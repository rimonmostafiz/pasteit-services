package com.miu.pasteit.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.miu.pasteit.model.entity.common.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rimon Mostafiz
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModel {
    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private Status status;
}