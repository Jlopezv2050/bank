package com.iobuilders.bank.infrastructure.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iobuilders.bank.domain.ports.input.UsersInputPort;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.UUID;

@WebMvcTest(UsersController.class)
public class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UsersInputPort usersInputPort;

    @Test
    public void postCreateUser_givenUuid_shouldReturnCreated() throws Exception {
        String name = "Name";
        UserInfo userInfo = new UserInfo(UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                BigDecimal.ZERO,
                name);
        Mockito.when(usersInputPort.create(ArgumentMatchers.any(UserRequest.class)))
                .thenReturn(userInfo);
        UserRequest userRequest = new UserRequest();
        userRequest.setName(name);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(userRequest);

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/users")
                                .param("amount", "1.50")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(json)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        CoreMatchers.is(userRequest.getName())))
                .andDo(MockMvcResultHandlers.print());
    }

}