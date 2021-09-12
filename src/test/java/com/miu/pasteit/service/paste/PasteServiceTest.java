package com.miu.pasteit.service.paste;

import com.miu.pasteit.component.exception.EntityNotFoundException;
import com.miu.pasteit.component.exception.ValidationException;
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
import com.miu.pasteit.service.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
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

    @Test
    @Disabled
    public void getPasteForUser() throws Exception{
        Paste paste = Paste.of("12345","mmmmm","lkj","paste1",
                "/paste/1","desc", PasteStatus.PUBLIC, Language.JAVA,"folder",
                new Long(23),new Long(234), LocalDateTime.now() ,"share",null,100);
        paste.setCreatedBy("nadia");
        given(pasteRepository.findById("12345")).willReturn(Optional.of(paste));

        PasteModel result = pasteService.getPasteForUser("12345","nadia");

        Assertions.assertThat(result.getId()).isEqualTo("12345");
        Assertions.assertThat(result.getTitle()).isEqualTo("paste1");
        Assertions.assertThat(result.getLanguage()).isEqualTo(Language.JAVA);
    }

    @Test
    @Disabled
    public void getPasteForUser_NotFound_Test(){
        given(pasteRepository.findById("12345")).willThrow(new EntityNotFoundException(HttpStatus.BAD_REQUEST,
                "pasteId", "error.paste.not.found"));
        assertThrows(EntityNotFoundException.class,()->pasteService.getPasteForUser("12345","nadia"));

    }
    @Test
    public void getPasteForUser_notOwnPaste_Test(){
        given(pasteRepository.findById("12345")).willThrow(new ValidationException(HttpStatus.UNAUTHORIZED,
                "pasteId", "error.paste.user.not.authorized"));
        assertThrows(ValidationException.class,()->pasteService.getPasteForUser("12345","nadia"));

    }
    @Test
    public void getAllPastes() throws Exception{
        Paste paste1 = Paste.of("12345","mmmmm","lkj","paste1",
                "/paste/1","desc", PasteStatus.PUBLIC, Language.JAVA,"folder",
                245L,245L, LocalDateTime.now() ,"share",null,100);
        Paste paste2 = Paste.of("123456","mmmmm","lkj","paste1",
                "/paste/1","desc", PasteStatus.PUBLIC, Language.JAVA,"folder",
                245L,245L, LocalDateTime.now() ,"share",null,100);


        given(pasteRepository.findAll()).willReturn((List.of(paste1,paste2)));

        List<PasteModel> result =  pasteService.getAllPastes();

        Assertions.assertThat(result.size()).isEqualTo(2);
    }
    @Test
    public void getAllPasteByUser() throws Exception{

        Paste paste1 = Paste.of("12345","mmmmm","lkj","paste1",
                "/paste/1","desc", PasteStatus.PUBLIC, Language.JAVA,"folder",
                23L,234L, LocalDateTime.now() ,"share",null,100);
        Paste paste2 = Paste.of("123456","mmmmm","lkj","paste1",
                "/paste/1","desc", PasteStatus.PUBLIC, Language.JAVA,"folder",
                23L,234L, LocalDateTime.now() ,"share",null,100);
        paste1.setCreatedBy("user");
        paste2.setCreatedBy("user");
        User user = User.of(245L, "user", "111", "nadia@gmail.com",
                "nadia", "mimoun", Status.ACTIVE, null);
        given(userservice.findById(245L)).willReturn(Optional.of(user));

        given(pasteRepository.findAllByPasteUser(245L)).willReturn((List.of(paste1, paste2)));
        List<PasteModel> result = pasteService.getAllPasteByUser(245L);
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void getAllPasteByStatus() throws Exception {
        Paste paste = Paste.of("1111", "int var=11;", "hashc", "java code",
                "http://lcoalost", "detail code", PasteStatus.PRIVATE, Language.JAVA,
                "C:/", 100L, 1111L, LocalDateTime.now(), "public", null, 1);
        Paste paste1 = Paste.of("2222", "int var=22;", "hashc", "java code",
                "http://lcoalost", "detail code", PasteStatus.PUBLIC, Language.JAVA,
                "C:/", 100L, 1111L, LocalDateTime.now(), "public", null, 2);
        Paste paste2 = Paste.of("2222", "int var=22;", "hashc", "java code",
                "http://lcoalost", "detail code", PasteStatus.PRIVATE, Language.JAVA,
                "C:/", 100L, 1111L, LocalDateTime.now(), "public", null, 1);
        List<Paste> pastePublic = new LinkedList<>();
        pastePublic.add(paste);
        pastePublic.add(paste1);
        pastePublic.add(paste2);

        given(pasteRepository.findAllByStatus(PasteStatus.PUBLIC)).willReturn(List.of(paste, paste2));
        List<PasteModel> result = pasteService.getAllPasteByStatus(PasteStatus.PUBLIC);

        Assertions.assertThat(result.size()).isEqualTo(2);


    }
}