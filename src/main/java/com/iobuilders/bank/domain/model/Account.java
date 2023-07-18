package com.iobuilders.bank.domain.model;

import com.iobuilders.bank.domain.exception.NotEnoughFoundsException;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "accounts")
@Entity
@DynamicUpdate
@OptimisticLocking(type = OptimisticLockType.DIRTY)
public class Account {
    @Id
    private String uuid;

    @JoinColumn(name = "user_uuid")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private BigDecimal balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movement> movementList;

    public Account(User user) {
        this.uuid = "account-" + UUID.randomUUID();
        this.user = user;
        this.balance = new BigDecimal("0.0");
    }

    public Account() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Movement> getMovementsList() {
        return movementList;
    }

    public void setMovementsList(List<Movement> movementList) {
        this.movementList = movementList;
    }

    public void deposit(BigDecimal amount) {
        this.balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        validateEnoughFounds(amount);
        this.balance = balance.subtract(amount);
    }

    private void validateEnoughFounds(BigDecimal amount) {
        if ((balance.subtract(amount)).compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughFoundsException(this.uuid);
        }
    }

}