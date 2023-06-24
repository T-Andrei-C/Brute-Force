package com.codecool.bruteforce.logger;

import java.time.LocalDate;

public class ConsoleLogger implements Logger{
    @Override
    public void logError(String message) {
        System.out.println(LocalDate.now() + " type ERROR: " + message);
    }

    @Override
    public void logInfo(String message) {
        System.out.println(LocalDate.now() + " type INFO: " + message);
    }
}
