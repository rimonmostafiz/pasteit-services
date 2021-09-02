package com.miu.pasteit.component.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Rimon Mostafiz
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
