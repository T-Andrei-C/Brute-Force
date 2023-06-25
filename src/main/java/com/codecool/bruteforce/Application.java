package com.codecool.bruteforce;

import com.codecool.bruteforce.authentication.AuthenticationService;
import com.codecool.bruteforce.authentication.AuthenticationServiceImpl;
import com.codecool.bruteforce.logger.ConsoleLogger;
import com.codecool.bruteforce.logger.Logger;
import com.codecool.bruteforce.passwords.breaker.PasswordBreakerImpl;
import com.codecool.bruteforce.passwords.generator.PasswordGenerator;
import com.codecool.bruteforce.passwords.generator.PasswordGeneratorImpl;
import com.codecool.bruteforce.passwords.model.AsciiTableRange;
import com.codecool.bruteforce.users.generator.UserGenerator;
import com.codecool.bruteforce.users.generator.UserGeneratorImpl;
import com.codecool.bruteforce.users.model.User;
import com.codecool.bruteforce.users.repository.UserRepository;
import com.codecool.bruteforce.users.repository.UserRepositoryImpl;

import java.util.List;
import java.util.Random;

public class Application {

    private static Logger logger = new ConsoleLogger();

    private static final AsciiTableRange lowercaseChars = new AsciiTableRange(97, 122);
    private static final AsciiTableRange uppercaseChars = new AsciiTableRange(65, 90);
    private static final AsciiTableRange numbers = new AsciiTableRange(48, 57);

    public static void main(String[] args) {

        String dbFile = "src/main/resources/Users.db";

        UserRepository userRepository = new UserRepositoryImpl(dbFile, logger);
        userRepository.deleteAll();

        List<PasswordGenerator> passwordGenerators = createPasswordGenerators();
        UserGenerator userGenerator = new UserGeneratorImpl(logger, passwordGenerators);
        int userCount = 10;
        int maxPwLength = 3;

        addUsersToDb(userCount, maxPwLength, userGenerator, userRepository);

        logger.logInfo(String.format("Database initialized with %d users; maximum password length: %d%n", userCount, maxPwLength));

        AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository);

        breakUsers(userCount, maxPwLength, authenticationService);

    }

    private static void addUsersToDb(int count, int maxPwLength, UserGenerator userGenerator, UserRepository userRepository) {
        for (User user : userGenerator.generate(count, maxPwLength)){
            userRepository.add(user.userName(), user.password());
        }
    }

    private static List<PasswordGenerator> createPasswordGenerators() {
        var lowercasePwGen = new PasswordGeneratorImpl(lowercaseChars);
        var uppercasePwGen = new PasswordGeneratorImpl(lowercaseChars, uppercaseChars);
        PasswordGenerator numbersPwGen = new PasswordGeneratorImpl(lowercaseChars, uppercaseChars, numbers); // lowercase + uppercase + numbers

        return List.of(lowercasePwGen, uppercasePwGen, numbersPwGen);
    }

    private static void breakUsers(int userCount, int maxPwLength, AuthenticationService authenticationService) {
        var passwordBreaker = new PasswordBreakerImpl();
        logger.logInfo("Initiating password breaker...\n");
        for (int i = 1; i <= userCount; i++) {
            String user = "User" + i;
            for (int j = 1; j <= maxPwLength; j++) {
                logger.logInfo(String.format("Trying to break %s with all possible password combinations with length = %d...%n", user, j));

                // start measuring time
                long startTime = System.currentTimeMillis();

                // Get all pw combinations
                List<String> pwCombinations = passwordBreaker.getCombinations(j);
                boolean broken = false;

                for (String pw : pwCombinations) {
                    if (authenticationService.authenticate(user, pw)){
                        long endTime = System.currentTimeMillis();
                        long elapsedTime = endTime - startTime;
                        logger.logInfo("User password guessed correctly in " + elapsedTime);
                        broken = true;
                        break;
                    }
                }

                if (broken) {
                    break;
                }
            }
        }
    }



}
