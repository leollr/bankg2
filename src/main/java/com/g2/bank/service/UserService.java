package com.g2.bank.service;

import java.util.List;

import com.userfront.domain.User;

public interface UserService {
	User findByUsername(String username);

    boolean checkUserExists(String username);

    boolean checkUsernameExists(String username);

    void save(User user);

    User createUser(User user);

    User saveUser(User user);

    List<User> findUserList();

    void enableUser(String username);

    void disableUser(String username);
}
