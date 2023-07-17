package com.iobuilders.bank.infrastructure.adapters.input.rest;

import java.math.BigDecimal;

public class UserInfo {
    private String uuid;
    private String accountUuid;
    private BigDecimal balance;
    private String name;

    public UserInfo() {
    }

    public UserInfo(String uuid, String accountUuid, BigDecimal balance, String name) {
        this.uuid = uuid;
        this.accountUuid = accountUuid;
        this.balance = balance;
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getAccountUuid() {
        return accountUuid;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

}