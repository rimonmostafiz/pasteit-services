package com.miu.pasteit.service;

import com.miu.pasteit.component.exception.EntityNotFoundException;
import com.miu.pasteit.model.dto.PasteModel;
import com.miu.pasteit.model.entity.activity.nosql.ActivityPaste;
import com.miu.pasteit.model.entity.common.ActivityAction;
import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.nosql.Paste;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.request.PasteCreateRequest;
import com.miu.pasteit.repository.mongo.PasteRepository;
import com.miu.pasteit.repository.mongo.activity.ActivityPasteRepository;
import com.miu.pasteit.service.paste.PasteService;
import com.miu.pasteit.service.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * @author Nadia Mimoun
 */
public class PasteServiceTest {

    @Mock
    PasteRepository pasteRepository;

    @Mock
    UserService userservice;

    @Mock
    ActivityPasteRepository activitypasterepository;

    @InjectMocks
    PasteService pasteService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void getPaste() throws Exception{
        Paste paste = Paste.of("1234","mmmmm","lkj","paste1",
                "/paste/1","desc", PasteStatus.PUBLIC, Language.JAVA,"folder",
                23L, 234L, LocalDateTime.now() ,"share",null,100);
        given(pasteRepository.findById("1234")).willReturn(Optional.of(paste));

        PasteModel result = pasteService.getPaste("1234");

        Assertions.assertThat(result.getId()).isEqualTo("1234");
        Assertions.assertThat(result.getTitle()).isEqualTo("paste1");
        Assertions.assertThat(result.getLanguage()).isEqualTo(Language.JAVA);
    }
    @Test
    public void getPaste_NotFound_Test(){
        given(pasteRepository.findById("1234")).willThrow(new EntityNotFoundException(HttpStatus.BAD_REQUEST,
                "pasteId", "error.paste.not.found"));
        assertThrows(EntityNotFoundException.class,()->pasteService.getPaste("1234"));

    }
    @Test
    public void createPaste() throws Exception{

        User user = User.of(245L,"user","111","nadia@gmail.com",
                "nadia","mimoun",Status.ACTIVE,null);
        given(userservice.getUserByUsername("user")).willReturn(user);

        Paste savedPaste = Paste.of("1234","content","lkj","paste1",
                "/paste/1","desc", PasteStatus.PRIVATE, Language.JAVA,"folder",
                23L, 234L, LocalDateTime.now(),"share",null,100);

        PasteCreateRequest pasteCreateRequest= new PasteCreateRequest("content","title",
                "des","public","JAVA","folder");

        given(pasteRepository.save(any())).willReturn(savedPaste);


        ActivityPaste activityPaste = ActivityPaste.of(savedPaste, "user", ActivityAction.INSERT);
        given(activitypasterepository.save(activityPaste)).willReturn(activityPaste);

        PasteModel result = pasteService.createPaste(pasteCreateRequest,"user");
        Assertions.assertThat(result.getId()).isEqualTo("1234");
        Assertions.assertThat(result.getContent()).isEqualTo("content");
    }
}