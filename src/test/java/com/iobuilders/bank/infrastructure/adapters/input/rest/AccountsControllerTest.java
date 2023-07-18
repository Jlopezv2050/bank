package com.iobuilders.bank.infrastructure.adapters.input.rest;

import com.iobuilders.bank.application.exceptions.AccountNotfoundExceptions;
import com.iobuilders.bank.domain.model.MovementType;
import com.iobuilders.bank.domain.ports.input.AccountsInputPort;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.UUID;

@WebMvcTest(AccountsController.class)
public class AccountsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AccountsInputPort accountsInputPort;

    AccountResponse accountResponse;

    @BeforeEach
    public void setup(){
        accountResponse = new AccountResponse(
                UUID.randomUUID().toString(),
                3L,
                BigDecimal.TWO,
                BigDecimal.TEN);
    }

    @Test
    public void postDeposit_givenUuid_shouldReturnOk() throws Exception {
        Mockito.when(accountsInputPort.modifyBalanceConcurrent(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(BigDecimal.class),
                        ArgumentMatchers.any(MovementType.class)))
                .thenReturn(accountResponse);
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/accounts/{uuid}/deposit", "uuid")
                                .param("amount", "1.50"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuid", CoreMatchers.is(accountResponse.getUuid())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void postDeposit_givenWrongUuid_shouldReturnNotFound() throws Exception {
        Mockito.when(accountsInputPort.modifyBalanceConcurrent(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(BigDecimal.class),
                        ArgumentMatchers.any(MovementType.class)))
                .thenThrow(new AccountNotfoundExceptions("uuid"));
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/accounts/{uuid}/deposit", "uuid")
                                .param("amount", "1.50"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void postTransferToAccount_givenAccountsUuidsAndAmount_shouldReturnCreated() throws Exception {
        Mockito.when(accountsInputPort.transferToAccount(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(BigDecimal.class)))
                .thenReturn(accountResponse);
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/accounts/{uuid}/transfer/{toUuid}", "uuid", "touuid")
                                .param("amount", "1.50"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}