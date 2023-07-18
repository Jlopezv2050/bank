package com.iobuilders.bank.application.useCases;

import com.iobuilders.bank.application.exceptions.AlreadyCreatedUser;
import com.iobuilders.bank.domain.model.Account;
import com.iobuilders.bank.domain.model.User;
import com.iobuilders.bank.domain.ports.input.AccountsInputPort;
import com.iobuilders.bank.infrastructure.adapters.input.rest.UserRequest;
import com.iobuilders.bank.infrastructure.adapters.output.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @Mock
    UsersRepository usersRepository;
    @Mock
    AccountsInputPort accountsInputPort;

    @InjectMocks
    UsersService usersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenUserRequest_whenCreate_thenUserInfoReturn() {
        String nameToTest = "TestName";
        UserRequest userRequest = new UserRequest();
        userRequest.setName(nameToTest);
        Account account = new Account();
        User user = new User();

        Mockito.doReturn(account).when(accountsInputPort).create(ArgumentMatchers.any(User.class));
        Mockito.doReturn(user).when(usersRepository).saveAndFlush(ArgumentMatchers.any(User.class));
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        usersService.create(userRequest);
        Mockito.verify(usersRepository, Mockito.times(1))
                .saveAndFlush(userArgumentCaptor.capture());
        User userArgumentCaptorValue = userArgumentCaptor.getValue();

        Assertions.assertEquals(nameToTest, userArgumentCaptorValue.getName());
    }

    @Test
    void givenUserRequestRepeated_whenCreate_thenThrowException() {
        UserRequest userRequest = new UserRequest();
        Mockito.when(usersRepository.saveAndFlush(ArgumentMatchers.any(User.class)))
                .thenThrow(DataIntegrityViolationException.class);
        Assertions.assertThrows(AlreadyCreatedUser.class,
                () -> usersService.create(userRequest));
    }

}