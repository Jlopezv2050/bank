package com.iobuilders.bank.application.useCases;

import com.iobuilders.bank.domain.model.Account;
import com.iobuilders.bank.domain.model.MovementType;
import com.iobuilders.bank.domain.model.User;
import com.iobuilders.bank.infrastructure.adapters.output.AccountsRepository;
import com.iobuilders.bank.infrastructure.adapters.output.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class AccountsServiceLockTest {

    @Autowired
    private AccountsService accountsService;
    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private UsersRepository usersRepository;

    private final List<BigDecimal> savings = Arrays.asList(
            new BigDecimal("10.4"),
            new BigDecimal("2.4"),
            new BigDecimal("6.0"));


    private final List<BigDecimal> transfers = Arrays.asList(
            new BigDecimal("10.0"),
            new BigDecimal("10.0"),
            new BigDecimal("10.0"));


    @Test
    void givenUuidAndAmountAndMovementType_whenModifyBalanceConcurrent_thenModifyCorrect() throws InterruptedException {

        User user = new User("test");
        usersRepository.save(user);
        Assertions.assertNotNull(user.getUuid());

        Account account = new Account(user);
        accountsRepository.save(account);
        Assertions.assertEquals(new BigDecimal("0.0"), account.getBalance());

        final ExecutorService executor = Executors.newFixedThreadPool(savings.size());

        String uuid = account.getUuid();

        savings.forEach(saving -> {
            executor.execute(() -> {
                    accountsService.modifyBalanceConcurrent(uuid, saving, MovementType.DEPOSIT);
            });
        });

        executor.shutdown();
        AssertionErrors.assertTrue("", executor.awaitTermination(1, TimeUnit.MINUTES));

        Assertions.assertEquals(new BigDecimal("18.80"),
                accountsRepository.findByUuid(account.getUuid()).get().getBalance());
    }

    @Test
    void givenAccountUuidOriginDestinationAndAmount_whenTransferToAccountInThread_thenTransferCorrect() throws InterruptedException {

        User user = new User("test2");
        usersRepository.save(user);
        Assertions.assertNotNull(user.getUuid());

        User user2 = new User("test3");
        usersRepository.save(user2);

        Account account = new Account(user);
        account.setBalance(new BigDecimal("50.0"));
        accountsRepository.save(account);
        Assertions.assertEquals(new BigDecimal("50.0"), account.getBalance());

        Account account2 = new Account(user2);
        accountsRepository.save(account2);
        Assertions.assertEquals(new BigDecimal("0.0"), account2.getBalance());


        final ExecutorService executor = Executors.newFixedThreadPool(transfers.size());

        String uuid = account.getUuid();
        String uuidDestination = account2.getUuid();

        transfers.forEach(transfer -> {
            executor.execute(() -> {
                accountsService.transferToAccount(uuid, uuidDestination, transfer);
            });
        });

        executor.shutdown();
        AssertionErrors.assertTrue("", executor.awaitTermination(1, TimeUnit.MINUTES));

        Assertions.assertEquals(new BigDecimal("20.00"),
                accountsRepository.findByUuid(account.getUuid()).get().getBalance());
    }

}