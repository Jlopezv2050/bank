package com.iobuilders.bank.application.useCases;

import com.iobuilders.bank.domain.model.Account;
import com.iobuilders.bank.domain.model.Movement;
import com.iobuilders.bank.domain.model.MovementType;
import com.iobuilders.bank.domain.ports.input.AccountsInputPort;
import com.iobuilders.bank.domain.ports.input.MovementsInputPort;
import com.iobuilders.bank.infrastructure.adapters.output.MovementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public class MovementsService implements MovementsInputPort {

    @Autowired
    MovementsRepository movementsRepository;
    @Override
    public Movement addMovementByAccount(Account account, MovementType movementType, BigDecimal amount) {
        Movement movement = new Movement(account, movementType, amount);
        movementsRepository.save(movement);
        return movement;
    }

    @Override
    public Page<Movement> getAllMovements(Pageable pageable){
        return movementsRepository.findAll(pageable);
    }

    @Override
    public List<Movement> getAll(){
        return movementsRepository.findAll();
    }

}
