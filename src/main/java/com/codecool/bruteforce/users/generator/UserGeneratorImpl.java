package com.codecool.bruteforce.users.generator;

import com.codecool.bruteforce.logger.Logger;
import com.codecool.bruteforce.passwords.generator.PasswordGenerator;
import com.codecool.bruteforce.users.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserGeneratorImpl implements UserGenerator {
    private static final Random random = new Random();
    private Logger logger;
    private final List<PasswordGenerator> passwordGenerators;
    private int userCount;

    public UserGeneratorImpl(Logger logger, List<PasswordGenerator> passwordGenerators) {
        this.logger = logger;
        this.passwordGenerators = passwordGenerators;
    }

    @Override
    public List<User> generate(int count, int maxPasswordLength) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String password = getRandomPasswordGenerator().generate(getRandomPasswordLength(maxPasswordLength));
            users.add(new User(0, "User" + (i + 1), password));
        }
        return users;
    }

    private PasswordGenerator getRandomPasswordGenerator() {
        return passwordGenerators.get(random.nextInt(passwordGenerators.size()));
    }

    private static int getRandomPasswordLength(int maxPasswordLength) {
        return random.nextInt(1, maxPasswordLength);
    }
}
