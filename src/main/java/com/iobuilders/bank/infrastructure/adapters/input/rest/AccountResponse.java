package com.iobuilders.bank.infrastructure.adapters.input.rest;

import java.math.BigDecimal;

public class AccountResponse {
    private String uuid;
    private Long movement_uuid;
    private BigDecimal amount;
    private BigDecimal balance;

    public AccountResponse(){}
    public AccountResponse(String uuid, Long movement_uuid, BigDecimal amount, BigDecimal balance) {
        this.uuid = uuid;
        this.movement_uuid = movement_uuid;
        this.amount = amount;
        this.balance = balance;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getMovement_uuid() {
        return movement_uuid;
    }

    public void setMovement_uuid(Long movement_uuid) {
        this.movement_uuid = movement_uuid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
