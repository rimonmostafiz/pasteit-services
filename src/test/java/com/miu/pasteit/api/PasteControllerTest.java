package com.miu.pasteit.api;

import com.miu.pasteit.model.dto.PasteModel;
import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.request.PasteCreateRequest;
import com.miu.pasteit.service.paste.PasteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;

class PasteControllerTest {
    @Mock
    PasteService pasteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkPasteCreatedByUserRequest() {
        PasteCreateRequest pasteCreateRequest = PasteCreateRequest.builder().build();
        User user = User.of(1111L, "eyobmbt", "pass", "eyob@gmailcom",
                "Eyob", "Beyene", Status.ACTIVE, null);

        PasteModel pasteModel = PasteModel.of("2222", "int var=22;", "hashc", "java code",
                "http://lcoalost", "detail code", PasteStatus.PRIVATE, Language.JAVA,
                "C:/", LocalDateTime.now().plusDays(1), 1111L, "username", LocalDateTime.now(), "public", null);

        given(pasteService.createPaste(pasteCreateRequest, user.getFirstName())).willReturn(pasteModel);
        Assertions.assertEquals(user.getId(), pasteModel.getPasteUser());

    }


}