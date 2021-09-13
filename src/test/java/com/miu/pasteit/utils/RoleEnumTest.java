package com.miu.pasteit.utils;

import com.miu.pasteit.component.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

/**
 * @author Rimon Mostafiz
 */
class RoleEnumTest {

    @Test
    void getRoleName() {
        assertThat(RoleEnum.getRoleName("USER")).isInstanceOf(RoleEnum.class);
        assertThat(RoleEnum.getRoleName("USER")).isEqualTo(RoleEnum.USER);
        assertThat(RoleEnum.getRoleName("USER").getRoleId()).isEqualTo(2L);
        Throwable thrown = catchThrowable(() -> {
            RoleEnum.getRoleName("xyz");
        });

        assertThat(thrown).hasMessageContaining("invalid.role.name");

        assertThatThrownBy(() -> {
            RoleEnum.getRoleName("xyz");
        }).isInstanceOf(ValidationException.class);
    }
}