package com.g2.bank.model.Dto;

import java.math.BigDecimal;

public class Account {

    private Long id;
    private User user;
    private Short pin;
    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Short getPin() {
        return pin;
    }

    public void setPin(Short pin) {
        this.pin = pin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
