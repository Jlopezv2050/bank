package com.iobuilders.bank.domain.ports.input;

import com.iobuilders.bank.domain.model.Account;
import com.iobuilders.bank.domain.model.MovementType;
import com.iobuilders.bank.infrastructure.adapters.input.rest.AccountDisplay;
import com.iobuilders.bank.infrastructure.adapters.input.rest.AccountResponse;
import com.iobuilders.bank.domain.model.User;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface AccountsInputPort {
    Account getByUuid(String uuid);
    Account create(User user);
    AccountResponse modifyBalanceConcurrent(String accountUuid, BigDecimal amount, MovementType movementType);
    AccountResponse transferToAccount(String originUuid, String destinationUuid, BigDecimal amount);
    AccountDisplay getAccountDisplay(String accountUuid, Pageable pageable);

}
