package com.g2.bank.service.impl;

import com.g2.bank.exception.ResourceNotFoundException;
import com.g2.bank.model.Dto.Account;
import com.g2.bank.model.Entities.AccountEntity;
import com.g2.bank.repository.AccountRepository;
import com.g2.bank.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Account findById(Long id) throws ResourceNotFoundException {
        Account dto = new Account();
        BeanUtils.copyProperties(findAccountById(id), dto);
        return dto;
    }

    @Override
    public Account createAccount(Account dto) {
        AccountEntity entity = new AccountEntity();
        BeanUtils.copyProperties(dto, entity);
        accountRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    @Override
    public void closeAccount(Long id) throws ResourceNotFoundException {
        AccountEntity entity = this.findAccountById(id);
        entity.setActive(Boolean.FALSE);
        accountRepository.save(entity);
    }

    private AccountEntity findAccountById(Long id) throws ResourceNotFoundException {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + id));
    }
}
