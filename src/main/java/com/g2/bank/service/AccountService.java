package com.g2.bank.service;

import com.g2.bank.exception.ResourceNotFoundException;
import com.g2.bank.model.Dto.Account;

public interface AccountService {
    Account findById(Long accountId) throws ResourceNotFoundException;
    Account createAccount(Account dto);
    void closeAccount(Long id) throws ResourceNotFoundException;
}
