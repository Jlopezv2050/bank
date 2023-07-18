package com.iobuilders.bank.domain.exception;

import java.io.Serial;

public class NotEnoughFoundsException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public NotEnoughFoundsException(String uuid){
        super("Not enough founds in account with uuid: " + uuid);
    }

}