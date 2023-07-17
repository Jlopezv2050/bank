package com.iobuilders.bank.application.useCases;

import com.iobuilders.bank.domain.model.Account;
import com.iobuilders.bank.domain.model.Movement;
import com.iobuilders.bank.domain.ports.input.MovementsInputPort;
import com.iobuilders.bank.infrastructure.adapters.input.rest.AccountDisplay;
import com.iobuilders.bank.infrastructure.adapters.output.AccountsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AccountsServiceTest {

    @Mock
    AccountsRepository accountsRepository;
    @Mock
    MovementsInputPort movementsInputPort;

    @Spy
    @InjectMocks
    AccountsService accountsService;

    @BeforeEach
    void setUp() {
     MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenAccountUuid_whenGetAccountDisplay_thenReturnAccountDisplay (){

        Movement movement = new Movement();
        Movement movement2 = new Movement();
        Movement movement3 = new Movement();

        List<Movement> movementList = new ArrayList<>();
        movementList.add(movement);
        movementList.add(movement2);
        movementList.add(movement3);

        Account account = new Account();
        account.setBalance(BigDecimal.TEN);
        Mockito.doReturn(account).when(accountsService).getByUuid(ArgumentMatchers.anyString());
        BDDMockito.given(movementsInputPort.getAllMovements(Pageable.unpaged())).willReturn(new PageImpl<>(movementList));

        AccountDisplay accountDisplay = accountsService.getAccountDisplay("uuid", Pageable.unpaged());

        Assertions.assertEquals(account.getUuid(),accountDisplay.getAccountUuid());
        Assertions.assertEquals(BigDecimal.TEN,accountDisplay.getBalance());
        Assertions.assertEquals(3,accountDisplay.getMovementList().getSize());
    }




}
