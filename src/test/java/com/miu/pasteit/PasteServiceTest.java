package com.miu.pasteit;

import com.miu.pasteit.model.dto.PasteModel;
import com.miu.pasteit.model.entity.common.PasteStatus;
import com.miu.pasteit.model.entity.db.nosql.Paste;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.mapper.PasteMapper;
import com.miu.pasteit.model.request.PasteCreateRequest;
import com.miu.pasteit.repository.mongo.PasteRepository;
import com.miu.pasteit.service.paste.PasteService;
import com.miu.pasteit.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PasteServiceTest {


    @Autowired
    private UserService userService;
    @Autowired
    private PasteService pasteService;
    @Autowired
    private PasteRepository pasteRepository;

    @Autowired
    PasteCreateRequest pasteCreateRequest;


    @Test
    void createPaste() {
        pasteCreateRequest=new
                PasteCreateRequest("int var=5;","java code","java code description","PUBLIC","Java","C:\\");
        User user=userService.getUserByUsername("eyob");
        Paste paste=PasteMapper.createRequestToEntity(pasteCreateRequest,"eyob",user);
        Paste savedPaste=pasteRepository.save(paste);

        assertEquals(paste.getId(),savedPaste.getId());
    }

    @Test
    void getAllPasteByStatus(PasteStatus aPublic) {
        List<PasteCreateRequest> listPastesByStatus= new LinkedList<>();
        User user=userService.getUserByUsername("eyob");
        listPastesByStatus.add(new
                PasteCreateRequest("int var=10;","java code","java code description","PUBLIC","Java","C:\\"));
        listPastesByStatus.add(new
                PasteCreateRequest("int var=12;","java Code","Java code description","PRIVATE","Java","C:\\"));
        listPastesByStatus.add(new
                PasteCreateRequest("int var=15;","C++ code","C++ code description","PUBLIC","Java","C:\\"));


        for (PasteCreateRequest pm:listPastesByStatus)
        {
            Paste paste=PasteMapper.createRequestToEntity(pasteCreateRequest,"eyob",user);
            Paste savedPaste=pasteRepository.save(paste);

        }
        List<PasteModel> listofPastes=pasteService.getAllPasteByStatus(PasteStatus.PUBLIC);
        assertEquals(listofPastes.get(0).getStatus(),listPastesByStatus.get(0).getStatus());
        assertFalse(listofPastes.get(0).getStatus().equals(listPastesByStatus.get(1).getStatus()));
        assertEquals(listofPastes.get(0).getStatus(),listPastesByStatus.get(1).getStatus());

    }
}
