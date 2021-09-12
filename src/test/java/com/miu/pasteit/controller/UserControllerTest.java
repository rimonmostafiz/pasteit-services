//package com.miu.pasteit.controller;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.miu.pasteit.PasteItApplication;
//import com.miu.pasteit.api.UserController;
//import com.miu.pasteit.model.entity.common.Status;
//import com.miu.pasteit.model.request.UserCreateRequest;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = {PasteItApplication.class, UserController.class})
//@AutoConfigureMockMvc
//@TestPropertySource(
//        locations = {"classpath:application.properties", "classpath:application-dev.properties"},
//        properties = {"spring.flyway.enabled=false"})
//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    @Disabled
//    void whenValidInput_thenReturn200() throws Exception {
//
//        UserCreateRequest userCreateRequest = new UserCreateRequest();
//        userCreateRequest.setEmail("nosy@gmail.com");
//        userCreateRequest.setUsername("blablabla");
//        userCreateRequest.setPassword("blabla");
//        userCreateRequest.setStatus(Status.ACTIVE);
//        mockMvc.perform(
//              post("/v1/user")
//                      .contentType("application/json")
//                      .content(objectMapper.writeValueAsString(userCreateRequest))
//
//        ).andExpect(status().is2xxSuccessful());
//    }
//}
