package org.devhamzat.authorg.utils;

import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class UserIdGenerator {
    private static final String PREFIX = "user";

    public String generateUserId() {
        Random random = new Random();


        int randomNumber = 100 + random.nextInt(900);


        String randomNumberString = String.valueOf(randomNumber);


        return PREFIX + randomNumberString;
    }
}
