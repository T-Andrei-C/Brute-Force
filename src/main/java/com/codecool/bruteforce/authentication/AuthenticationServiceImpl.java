package com.codecool.bruteforce.authentication;

import com.codecool.bruteforce.users.repository.UserRepository;

public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean authenticate(String userName, String password) {
        return !userRepository.getAll().stream().filter(user ->
                user.userName().equals(userName) && user.password().equals(password)).toList().isEmpty();
    }
}
