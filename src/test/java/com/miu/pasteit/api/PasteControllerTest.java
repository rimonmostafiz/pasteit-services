package com.miu.pasteit.api;
import com.miu.pasteit.model.dto.PasteModel;
import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.nosql.Paste;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.request.PasteCreateRequest;
import com.miu.pasteit.service.paste.PasteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.BDDMockito.given;

class PasteControllerTest {
    @Mock
    PasteService pasteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnExpiredPastesOnly() {
        Paste paste2 = Paste.of("2222", "int var=22;", "hashc", "java code",
                "http://lcoalost", "detail code", PasteStatus.PRIVATE, Language.JAVA,
                "C:/", 100L, 1111L, LocalDateTime.now(), "public", null, 1);

        LocalDateTime expireTime = LocalDateTime.now();
        PasteModel pasteModel = new PasteModel();
        pasteModel.setId("1111");
        pasteModel.setPasteDateTime(LocalDateTime.now());
        pasteModel.setExpiryDateTime(LocalDateTime.now().plusDays(1l));

        given(pasteService.getAllExpiredPaste()).willReturn(List.of(pasteModel));

        Assertions.assertTrue(pasteModel.getExpiryDateTime().isAfter(expireTime));

    }

    @Test
    void checkPasteCreatedByUserRequest() {
        PasteCreateRequest pasteCreateRequest = PasteCreateRequest.builder().build();
        User user = User.of(1111L, "eyobmbt", "pass", "eyob@gmailcom",
                "Eyob", "Beyene", Status.ACTIVE, null);

        PasteModel pasteModel = PasteModel.of("2222", "int var=22;", "hashc", "java code",
                "http://lcoalost", "detail code", PasteStatus.PRIVATE, Language.JAVA,
                "C:/", LocalDateTime.now().plusDays(1), 1111L, LocalDateTime.now(), "public", null);

        given(pasteService.createPaste(pasteCreateRequest, user.getFirstName())).willReturn(pasteModel);
        Assertions.assertEquals(user.getId(), pasteModel.getPasteUser());

    }


}