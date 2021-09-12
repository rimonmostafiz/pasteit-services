package com.miu.pasteit.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.miu.pasteit.PasteItApplication;
import com.miu.pasteit.api.UserController;
import com.miu.pasteit.config.WebSecurityConfig;
import com.miu.pasteit.model.entity.common.Status;
import com.miu.pasteit.model.entity.db.sql.User;
import com.miu.pasteit.model.entity.db.sql.UserRoles;
import com.miu.pasteit.model.mapper.UserMapper;
import com.miu.pasteit.model.request.UserCreateRequest;
import com.miu.pasteit.repository.mysql.UserRepository;
import com.miu.pasteit.security.JWTAuthenticationFilter;
import com.miu.pasteit.security.JWTAuthorizationFilter;
import com.miu.pasteit.service.user.SessionService;
import com.miu.pasteit.service.user.UserDetailsServiceImpl;
import com.miu.pasteit.service.user.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Matchers.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = {PasteItApplication.class, UserController.class})
@AutoConfigureMockMvc
@TestPropertySource(
        locations = {"classpath:application.properties", "classpath:application-dev.properties"},
        properties = {"spring.flyway.enabled=false"})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenValidInput_thenReturn200() throws Exception {

        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setEmail("nosy@gmail.com");
        userCreateRequest.setUsername("blablabla");
        userCreateRequest.setPassword("blabla");
        userCreateRequest.setStatus(Status.ACTIVE);
        mockMvc.perform(
              post("/v1/user")
                      .contentType("application/json")
                      .content(objectMapper.writeValueAsString(userCreateRequest))

        ).andExpect(status().is2xxSuccessful());
    }


}
