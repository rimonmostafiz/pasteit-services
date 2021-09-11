package com.miu.pasteit.service.paste;

import com.miu.pasteit.model.dto.PasteModel;
import com.miu.pasteit.model.dto.UserModel;
import com.miu.pasteit.model.entity.activity.ActivityPaste;
import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.request.PasteCreateRequest;
import com.miu.pasteit.repository.PasteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PasteServiceTest {

    @Autowired
    private PasteService pasteService;

    @MockBean
    private ActivityPaste pasteModel;

    @Test
    void createPaste() {

    }

    @Test
    void getPaste() {
        LocalDateTime now=LocalDateTime.now();
        LocalDateTime expire=now.plusDays(2);
        PasteModel paste= new PasteModel();
        paste.setId("123");
        paste.setTitle("Java Code");
        paste.setUrl("http://lcoalhost");
        paste.setContent("public static int=10;");
        paste.setPasteDateTime(now);
        paste.setExpiryDateTime(expire);
        paste.setPasteUser(new UserModel());
        paste.setShare("public");

        Mockito.when(pasteModel.getId()).thenReturn(paste.getId());
        assertEquals(pasteService.getPaste("123").getId(),paste.getId());
    }
}