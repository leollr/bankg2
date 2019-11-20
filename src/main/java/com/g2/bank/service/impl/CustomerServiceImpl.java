package com.g2.bank.service.impl;

import com.g2.bank.exception.ResourceNotFoundException;
import com.g2.bank.model.Dto.User;
import com.g2.bank.model.Entities.UsersEntity;
import com.g2.bank.repository.CustomerRepository;
import com.g2.bank.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public User create(User dto) {
        UsersEntity entity = new UsersEntity();
        BeanUtils.copyProperties(dto, entity);
        customerRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    @Override
    public User findById(Long id) throws ResourceNotFoundException {
        User dto = new User();
        BeanUtils.copyProperties(findCustomerById(id), dto);
        return dto;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        UsersEntity entity = this.findCustomerById(id);
        entity.setActive(Boolean.FALSE);
        customerRepository.save(entity);
    }

    private UsersEntity findCustomerById(Long id) throws ResourceNotFoundException {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + id));
    }
}
