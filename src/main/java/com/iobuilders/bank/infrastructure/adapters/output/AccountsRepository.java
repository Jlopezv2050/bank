package com.iobuilders.bank.infrastructure.adapters.output;

import com.iobuilders.bank.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, String> {
    Optional<Account> findByUuid(String uuid);
    Optional<Account> findByUserUuid(String userUuid);
}
