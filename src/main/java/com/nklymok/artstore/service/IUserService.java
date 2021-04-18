package com.nklymok.artstore.service;

import com.nklymok.artstore.exception.UserAlreadyExistsException;
import com.nklymok.artstore.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    User saveUser(User user) throws UserAlreadyExistsException;

    boolean existsByEmail(String email);

}
