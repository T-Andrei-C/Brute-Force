package com.codecool.bruteforce.passwords.generator;

import com.codecool.bruteforce.logger.Logger;
import com.codecool.bruteforce.passwords.model.AsciiTableRange;

import java.util.Random;

public class PasswordGeneratorImpl implements PasswordGenerator {
    private static final Random random = new Random();
    private final AsciiTableRange[] characterSets;

    public PasswordGeneratorImpl(AsciiTableRange... characterSets) {
        this.characterSets = characterSets;
    }

    @Override
    public String generate(int length) {
        String randomPassword = "";
        for (int i = 0; i < length; i++) {
            randomPassword += getRandomCharacter(getRandomCharacterSet());
        }

        return randomPassword;
    }

    private AsciiTableRange getRandomCharacterSet() {
        return characterSets[random.nextInt(characterSets.length)];
    }

    private static char getRandomCharacter(AsciiTableRange characterSet) {
        return (char) random.nextInt(characterSet.start(), characterSet.end());
    }
}
