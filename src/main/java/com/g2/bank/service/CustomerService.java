package com.g2.bank.service;

import com.g2.bank.exception.ResourceNotFoundException;
import com.g2.bank.model.Dto.User;

public interface CustomerService {
    User create(User dto);
    User findById(Long id) throws ResourceNotFoundException;
    void delete(Long id) throws ResourceNotFoundException;
}
