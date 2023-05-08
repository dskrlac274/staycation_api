package com.agency04.devcademy.staycation.service;

import com.agency04.devcademy.staycation.exception.IdDoesNotExist;
import com.agency04.devcademy.staycation.model.User;
import com.agency04.devcademy.staycation.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User deleteUser(long id) {
        User user = new User();

        user = userRepository.findById(id).orElseThrow(() -> new IdDoesNotExist("Id does not exist!"));

        userRepository.deleteById(user.getId());
        return user;
    }

    @Override
    public User getUser(long id) {
        User user = new User();

        user = userRepository.findById(id).orElseThrow(() -> new IdDoesNotExist("Id does not exist!"));

        return user;
    }

    @Override
    public User addUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(long id, User updateUser) {
        User user = userRepository.findById(id).map(
                usr->{
                    usr.mapFrom(updateUser);
                    return userRepository.save(usr);
                }).orElseThrow(() -> new IdDoesNotExist("Id does not exist!"));
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();

    }
    @Override
    public Optional<User> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return this.findByUsername(username);
    }

    @Override
    public List<User> deleteAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        userRepository.deleteAll();
        return users;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new IdDoesNotExist("Id does not exist"));

    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }
}
