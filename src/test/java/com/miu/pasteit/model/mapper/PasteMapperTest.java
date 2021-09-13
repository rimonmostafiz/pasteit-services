package com.miu.pasteit.model.mapper;

import com.miu.pasteit.model.dto.FeedbackModel;
import com.miu.pasteit.model.dto.PasteModel;
import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.nosql.Feedback;
import com.miu.pasteit.model.entity.db.nosql.Paste;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.request.PasteCreateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author Nadia Mimoun
 */
public class PasteMapperTest {
    @Test
    public void mapper() {
        Paste paste = Paste.of("1234", "content", "lkj", "paste1",
                "/paste/1", "desc", PasteStatus.PRIVATE, Language.JAVA, "folder",
                23L, 234L, "username", LocalDateTime.now(), "share", null, 100);
        PasteModel result =PasteMapper.mapper(paste);
        Assertions.assertEquals(result.getId(),paste.getId());
        Assertions.assertEquals(result.getContent(),paste.getContent());

    }
    @Test
    public void createRequestToEntity(){
        PasteCreateRequest pasteCreateRequest =new PasteCreateRequest("content","title","description","public","JAVA","folder");
        User user = User.of(245L, "user", "111", "nadia@gmail.com",
                "nadia", "mimoun", Status.ACTIVE, null);
        Paste result=PasteMapper.createRequestToEntity(pasteCreateRequest,"user",user,"/paste");
        Assertions.assertEquals(result.getLanguage(),Language.JAVA);
        Assertions.assertEquals(result.getContent(),pasteCreateRequest.getContent());
    }
}
