package com.iobuilders.bank.application.useCases;

import com.iobuilders.bank.application.exceptions.AccountNotfoundExceptions;
import com.iobuilders.bank.domain.model.*;
import com.iobuilders.bank.domain.ports.input.AccountsInputPort;
import com.iobuilders.bank.domain.ports.input.MovementsInputPort;
import com.iobuilders.bank.infrastructure.adapters.input.rest.AccountDisplay;
import com.iobuilders.bank.infrastructure.adapters.input.rest.AccountResponse;
import com.iobuilders.bank.infrastructure.adapters.output.AccountsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


public class AccountsService implements AccountsInputPort {

    AccountsRepository accountsRepository;
    MovementsInputPort movementsInputPort;
    public AccountsService(AccountsRepository accountsRepository, MovementsInputPort movementsInputPort) {
        this.accountsRepository = accountsRepository;
        this.movementsInputPort = movementsInputPort;
    }

    public AccountsService() {
    }

    @Override
    public Account getByUuid(String uuid){
        return accountsRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new AccountNotfoundExceptions(uuid));
    }

    @Override
    public Account create(User user) {
        Account account = new Account(user);
        return accountsRepository.save(account);
    }

    @Override
    public AccountResponse modifyBalanceConcurrent(String accountUuid, BigDecimal amount, MovementType movementType) {
        try {
            return this.modifyBalance(accountUuid, amount, movementType);
        } catch (ObjectOptimisticLockingFailureException e){
            return this.modifyBalanceConcurrent(accountUuid,amount, movementType);
        }
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AccountResponse modifyBalance(String accountUuid, BigDecimal amount, MovementType movementType) {
        Account account = this.getByUuid(accountUuid);
        if (MovementType.DEPOSIT.equals(movementType) || MovementType.RECEPTION.equals(movementType) )
            account.deposit(amount);
        if (MovementType.WITHDRAW.equals(movementType) || MovementType.TRANSFER.equals(movementType) )
            account.withdraw(amount);
        accountsRepository.save(account);
        Movement movement = movementsInputPort.addMovementByAccount(account, movementType, amount);

        return new AccountResponse(accountUuid,movement.getId(),amount,account.getBalance());
    }



    public AccountResponse transferToAccount(String accountUuid, String destinationUuid, BigDecimal amount) throws ObjectOptimisticLockingFailureException {

            Account originAccount = this.getByUuid(accountUuid);
            this.getByUuid(destinationUuid);

            AccountResponse accountResponse = this.modifyBalanceConcurrent(accountUuid, amount, MovementType.TRANSFER);
            this.modifyBalanceConcurrent(destinationUuid, amount, MovementType.RECEPTION);

            return new AccountResponse(accountUuid, accountResponse.getMovement_uuid(), amount,originAccount.getBalance());

    }

    @Override
    public AccountDisplay getAccountDisplay(String accountUuid, Pageable pageable) {
        Account account = this.getByUuid(accountUuid);
        Page<Movement> movementList = movementsInputPort.getAllMovements(pageable);
        return new AccountDisplay(account.getUuid(),account.getBalance(), movementList);
    }

}