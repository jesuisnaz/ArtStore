package com.nklymok.artstore.service;

import com.nklymok.artstore.exception.UserAlreadyExistsException;
import com.nklymok.artstore.model.User;
import com.nklymok.artstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User saveUser(User user) throws UserAlreadyExistsException {
        if (existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException();
        }
        return repository.save(user);
    }

    boolean existsByEmail(String email) {
        return repository.findByEmail(email) != null;
    }

}
