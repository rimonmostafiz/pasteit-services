package com.miu.pasteit.model.entity.common;

import com.miu.pasteit.component.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Rimon Mostafiz
 */
@Getter
@AllArgsConstructor
public enum PasteStatus {
    PUBLIC("public"),
    PRIVATE("private"),
    DELETED("deleted");

    String value;

    public static PasteStatus getStatus(String status) {
        Optional<PasteStatus> optTaskStatus = Arrays.stream(PasteStatus.values())
                .filter(v -> v.getValue().equals(status))
                .findFirst();

        return optTaskStatus.orElseThrow(() ->
                new ValidationException(HttpStatus.BAD_REQUEST, "status", "invalid.paste.status"));
    }
}
