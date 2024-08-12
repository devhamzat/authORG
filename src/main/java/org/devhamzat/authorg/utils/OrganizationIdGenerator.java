package org.devhamzat.authorg.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class OrganizationIdGenerator {
    private static final String PREFIX = "0rg";

    public String generateOrganizationId() {
        Random random = new Random();


        int randomNumber = 1000 + random.nextInt(9000);


        String randomNumberString = String.valueOf(randomNumber);


        return PREFIX + randomNumberString;
    }
}
