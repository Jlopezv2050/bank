package com.iobuilders.bank.infrastructure.adapters.input.rest;

import com.iobuilders.bank.domain.model.Movement;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
public class AccountDisplay {
    private String accountUuid;
    private BigDecimal balance;
    private Page<Movement> movementList;

    public AccountDisplay() {
    }

    public AccountDisplay(String accountUuid, BigDecimal balance, Page<Movement> movementList) {
        this.accountUuid = accountUuid;
        this.balance = balance;
        this.movementList = movementList;
    }

    public String getAccountUuid() {
        return accountUuid;
    }

    public void setAccountUuid(String accountUuid) {
        this.accountUuid = accountUuid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Page<Movement> getMovementList() {
        return movementList;
    }

    public void setMovementList(Page<Movement> movementList) {
        this.movementList = movementList;
    }
}
