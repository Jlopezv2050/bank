package com.iobuilders.bank.infrastructure.adapters.output;

import com.iobuilders.bank.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, String> {
}
