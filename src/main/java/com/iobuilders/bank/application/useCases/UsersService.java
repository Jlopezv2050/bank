package com.iobuilders.bank.application.useCases;

import com.iobuilders.bank.application.exceptions.AlreadyCreatedUser;
import com.iobuilders.bank.application.exceptions.UserNotfoundExceptions;
import com.iobuilders.bank.domain.model.Account;
import com.iobuilders.bank.domain.model.User;
import com.iobuilders.bank.domain.ports.input.AccountsInputPort;
import com.iobuilders.bank.domain.ports.input.UsersInputPort;
import com.iobuilders.bank.infrastructure.adapters.input.rest.UserInfo;
import com.iobuilders.bank.infrastructure.adapters.input.rest.UserRequest;
import com.iobuilders.bank.infrastructure.adapters.output.UsersRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

public class UsersService implements UsersInputPort {
    UsersRepository usersRepository;
    AccountsInputPort accountsService;
    public UsersService(UsersRepository usersRepository, AccountsInputPort accountsService){
        this.usersRepository = usersRepository;
        this.accountsService = accountsService;
    }

    @Override
    public User getById(String uuid) {
        return usersRepository.findById(uuid).orElseThrow(() -> new UserNotfoundExceptions(uuid));
    }

    @Override
    @Transactional
    public UserInfo create(UserRequest userRequest) {
        User user;
        try {
            user = usersRepository.saveAndFlush(new User(userRequest.getName()));
        } catch (DataIntegrityViolationException die) {
            throw new AlreadyCreatedUser(userRequest.getName());
        }

        Account account = accountsService.create(user);
        user.setAccount(account);
        return new UserInfo(user.getUuid(), account.getUuid(), account.getBalance(), user.getName());
    }

}
