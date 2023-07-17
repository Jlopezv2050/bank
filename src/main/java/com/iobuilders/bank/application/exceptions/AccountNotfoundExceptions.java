package com.iobuilders.bank.application.exceptions;

import java.io.Serial;

public class AccountNotfoundExceptions extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public AccountNotfoundExceptions(String uuid){
        super("Account not found with uuid: " + uuid);
    }
}
