package com.agency04.devcademy.staycation.service;

import com.agency04.devcademy.staycation.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User deleteUser(long id);
    User getUser(long id);

    User addUser(User user);
    User updateUser(long id,User user);

    List<User> getAllUsers();
    List<User> deleteAllUsers();
    User findById(Long id);

    Optional<User> findByUsername(String username);
    public Optional<User> getCurrentUser();
}
