package com.codecool.bruteforce.passwords.generator;

import com.codecool.bruteforce.passwords.model.AsciiTableRange;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PasswordGeneratorImplTest {
    AsciiTableRange[] characterSets = new AsciiTableRange[]{new AsciiTableRange(60, 70)};
    PasswordGenerator passwordGenerator = new PasswordGeneratorImpl(characterSets);
    @Test
    void passwordIsTheCorrectLength (){
        int passwordLength = 3;
        assertEquals(passwordLength, passwordGenerator.generate(passwordLength).length());
    }

    @Test
    void passwordContainsThreeCharFromTheAsciiTableProvided(){
        String password = passwordGenerator.generate(3);

        char[] asciiChars = new char[10];
        for (int i = 0; i < asciiChars.length; i++) {
            asciiChars[i] = (char) (characterSets[0].start() + i);
        }

        int actual = 0;

        for (int i = 0; i < password.length(); i++) {
            for (char asciiChar : asciiChars) {
                if (password.toCharArray()[i] == asciiChar) {
                    actual++;
                }
            }
        }

        assertEquals(3, actual);
    }
}