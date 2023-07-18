package com.iobuilders.bank.domain.ports.input;

import com.iobuilders.bank.domain.model.Account;
import com.iobuilders.bank.domain.model.Movement;
import com.iobuilders.bank.domain.model.MovementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface MovementsInputPort {
    Movement addMovementByAccount(Account account_uuid, MovementType movementType, BigDecimal amount);
    Page<Movement> getAllMovements(Pageable pageable);
    List<Movement> getAll();

    }
