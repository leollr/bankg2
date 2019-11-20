package com.g2.bank.repository;

import com.g2.bank.model.Entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<UsersEntity, Long> {
}
