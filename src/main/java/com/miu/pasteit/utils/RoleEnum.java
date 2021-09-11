package com.miu.pasteit.utils;

import com.miu.pasteit.component.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

/**
 * @author Rimon Mostafiz
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN(1L),
    USER(2L),
    MODERATOR(3L);

    public long roleId;

    public static RoleEnum getRoleName(String name) {
        return Arrays.stream(RoleEnum.values())
                .filter(v -> v.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ValidationException(HttpStatus.BAD_REQUEST, "status", "invalid.role.name"));
    }
}
