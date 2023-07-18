package com.iobuilders.bank.application.useCases;

import com.iobuilders.bank.domain.model.Account;
import com.iobuilders.bank.domain.model.Movement;
import com.iobuilders.bank.domain.model.MovementType;
import com.iobuilders.bank.infrastructure.adapters.output.MovementsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class MovementsServiceTest {

    @Mock
    MovementsRepository movementsRepository;
    @InjectMocks
    MovementsService movementsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenAccountAndMovementTypeAndAmount_whenAddMovementByAccount_thenReturnMovement() {
        Movement movement = new Movement();
        movement.setMovementType(MovementType.RECEPTION);
        Account account = new Account();
        account.setUuid(UUID.randomUUID().toString());

        Mockito.doReturn(movement).when(movementsRepository).save(ArgumentMatchers.any(Movement.class));

        ArgumentCaptor<Movement> movementArgumentCaptor = ArgumentCaptor.forClass(Movement.class);
        Movement movementResult = movementsService.addMovementByAccount(account, MovementType.RECEPTION, BigDecimal.ONE);

        Mockito.verify(movementsRepository, Mockito.times(1)).save(movementArgumentCaptor.capture());
        Movement movementCaptorValue = movementArgumentCaptor.getValue();

        Assertions.assertEquals(movement.getMovementType(), movementCaptorValue.getMovementType());
        Assertions.assertEquals(movementResult.getAccount().getUuid(), account.getUuid());
    }

    @Test
    public void givenPageable_whenGetAllMovements_thenReturnMovementPage() {
        Movement movement = new Movement();
        Movement movement2 = new Movement();
        Movement movement3 = new Movement();
        List<Movement> movementList = List.of(movement, movement2, movement3);
        Pageable pageRequest = PageRequest.of(0, 2);
        Page<Movement> movementPage = new PageImpl<>(movementList, pageRequest, movementList.size());

        Mockito.doReturn(movementPage).when(movementsRepository).findAll(ArgumentMatchers.any(Pageable.class));
        Page<Movement> movementPageResult = movementsService.getAllMovements(Pageable.ofSize(1));
        Assertions.assertEquals(2, movementPageResult.getTotalPages());
    }

}
