package com.iobuilders.bank.infrastructure.adapters.output;

import com.iobuilders.bank.domain.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementsRepository extends JpaRepository<Movement, Long> {
}
