package com.iobuilders.bank.application.exceptions;

public class UserNotfoundExceptions extends RuntimeException{
    String uuid;
    public UserNotfoundExceptions(String uuid){
        super("User not found with uuid: " + uuid);
    }
}