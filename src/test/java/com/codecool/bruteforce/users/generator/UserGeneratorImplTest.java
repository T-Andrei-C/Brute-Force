package com.codecool.bruteforce.users.generator;

import com.codecool.bruteforce.logger.ConsoleLogger;
import com.codecool.bruteforce.logger.Logger;
import com.codecool.bruteforce.passwords.generator.PasswordGenerator;
import com.codecool.bruteforce.passwords.generator.PasswordGeneratorImpl;
import com.codecool.bruteforce.passwords.model.AsciiTableRange;
import com.codecool.bruteforce.users.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserGeneratorImplTest {

    AsciiTableRange[] characterSets = new AsciiTableRange[]{new AsciiTableRange(60, 70)};
    PasswordGenerator passwordGenerator = new PasswordGeneratorImpl(characterSets);
    Logger logger = new ConsoleLogger();
    UserGenerator userGenerator = new UserGeneratorImpl(logger, List.of(passwordGenerator));

    @Test
    void numberOfGeneratedUsersIsCorrect(){
        int passwordLength = 3;
        int count = 10;

        assertEquals(10, userGenerator.generate(count, passwordLength).size());
    }

    @Test
    void allUsersHaveTheCorrectPasswordLength(){
        int passwordLength = 3;
        int count = 10;
        List<User> users = userGenerator.generate(count, passwordLength);

        assertTrue(users.stream()
                .allMatch(user -> user.password().length() == 1 || user.password().length() == 2));
    }
}