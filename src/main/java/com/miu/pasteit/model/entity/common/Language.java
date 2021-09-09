package com.miu.pasteit.model.entity.common;

import com.miu.pasteit.component.exception.ValidationException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

/**
 * @author Rimon Mostafiz
 */
public enum Language {
    NONE, C, CPP, JAVA, PYTHON, JAVASCRIPT, GOLANG, TEXT, BASH;

    public static Language getLanguage(String language) {
        return Arrays.stream(Language.values())
                .filter(v -> v.name().equalsIgnoreCase(language))
                .findFirst()
                .orElseThrow(() -> new ValidationException(HttpStatus.BAD_REQUEST, "status", "error.language.invalid.status"));
    }
}
