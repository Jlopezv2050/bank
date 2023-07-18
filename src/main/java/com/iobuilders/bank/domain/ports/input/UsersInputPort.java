package com.iobuilders.bank.domain.ports.input;

import com.iobuilders.bank.domain.model.User;
import com.iobuilders.bank.infrastructure.adapters.input.rest.UserInfo;
import com.iobuilders.bank.infrastructure.adapters.input.rest.UserRequest;

public interface UsersInputPort {
    User getById(String uuid);
    UserInfo create(UserRequest userRequest);
}
