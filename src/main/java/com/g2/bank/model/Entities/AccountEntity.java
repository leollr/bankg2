package com.g2.bank.model.Entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ACCOUNTS")
@SequenceGenerator(name = "SQ_ACCOUNT ", sequenceName = "SQ_ACCOUNT ", allocationSize = 1)
public class AccountEntity {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "PIN")
    private Short pin;
    @Column(name = "BALANCE")
    private BigDecimal balance;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
