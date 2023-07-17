package com.iobuilders.bank.application.exceptions;

public class AlreadyCreatedUser extends RuntimeException{
    String name;
    public AlreadyCreatedUser(String name){
        super("Already user created with name: " + name);
    }
}
