package com.octo.bankoperations.utils;

import java.time.Instant;
import java.util.Random;

public class VirementUtils {
    public static String generateReference(){
        return Instant.now().toString() + new Random().nextLong();
    }
}
