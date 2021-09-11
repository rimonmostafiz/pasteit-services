package com.miu.pasteit;

import com.miu.pasteit.model.dto.PasteModel;

import com.miu.pasteit.model.entity.common.Language;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.db.nosql.Paste;

import com.miu.pasteit.repository.mongo.PasteRepository;
import com.miu.pasteit.repository.mongo.activity.ActivityPasteRepository;
import com.miu.pasteit.service.paste.PasteService;
import com.miu.pasteit.service.user.UserService;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


import static org.mockito.BDDMockito.given;

class PasteServiceTest {
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

    @Before("")
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void getAllPasteByStatus() throws Exception{
        Paste paste= Paste.of("1111","int var=11;","hashc","java code",
                "http://lcoalost","detail code",PasteStatus.PRIVATE,Language.JAVA,
                "C:/", 100L,1111L,LocalDateTime.now(),"public",null,1);
        Paste paste1=Paste.of("2222","int var=22;","hashc","java code",
                "http://lcoalost","detail code",PasteStatus.PUBLIC,Language.JAVA,
                "C:/", 100L,1111L,LocalDateTime.now(),"public",null,2);
        Paste paste2=Paste.of("2222","int var=22;","hashc","java code",
                "http://lcoalost","detail code",PasteStatus.PRIVATE,Language.JAVA,
                "C:/", 100L,1111L,LocalDateTime.now(),"public",null,1);
        List<Paste> pastePublic= new LinkedList<>();
        pastePublic.add(paste);pastePublic.add(paste1);pastePublic.add(paste2);

        given(pasteRepository.findAllByStatus(PasteStatus.PUBLIC)).willReturn(List.of(paste,paste2));
        List<PasteModel> result =  pasteService.getAllPasteByStatus(PasteStatus.PUBLIC);

        Assertions.assertThat(result.size()).isEqualTo(2);


    }
}