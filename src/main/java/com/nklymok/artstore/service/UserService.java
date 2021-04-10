package com.nklymok.artstore.service;

import com.nklymok.artstore.exception.UserAlreadyExistsException;
import com.nklymok.artstore.model.Role;
import com.nklymok.artstore.model.User;
import com.nklymok.artstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User saveUser(User user) throws UserAlreadyExistsException {
        if (existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>());
        user.getRoles().add(new Role("ROLE_USER"));
        return repository.save(user);
    }

    public boolean existsByEmail(String email) {
        return repository.findByEmail(email) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = repository.findByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), rulesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> rulesToAuthorities(Collection<Role> roles) {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
