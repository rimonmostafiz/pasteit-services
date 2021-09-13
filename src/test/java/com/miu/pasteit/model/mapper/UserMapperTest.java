package com.miu.pasteit.model.mapper;

import com.miu.pasteit.model.dto.UserModel;
import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.sql.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * @author Rimon Mostafiz
 */
class UserMapperTest {

    @Test
    void mapToUserModel() {
        User user = User.of(245L, "user", "111", "nadia@gmail.com",
                "nadia", "mimoun", Status.ACTIVE, Collections.emptyList());
        UserModel userModel = UserMapper.mapToUserModel(user);
        Assertions.assertThat(userModel).isNotNull();
        Assertions.assertThat(userModel.getId()).isEqualTo(245L);

    }
}