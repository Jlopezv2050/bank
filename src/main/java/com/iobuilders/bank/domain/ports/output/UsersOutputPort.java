package com.iobuilders.bank.domain.ports.output;

import com.iobuilders.bank.domain.model.User;

import java.util.Optional;

public interface UsersOutputPort {
    public Optional<User> findById();
}
