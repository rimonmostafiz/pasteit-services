package com.miu.pasteit.utils;

import java.util.Random;

/**
 * @author Rimon Mostafiz
 */
public class UrlGenerationUtil {
    public static final Integer URL_LEN = 6;
    public static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final Random random = new Random();

    public static UrlGenerationUtil getInstance() {
        return UrlGenerator.KLASS;
    }

    public String generateRandomURL() {
        StringBuilder urlBuilder = new StringBuilder();
        for (int i = 0; i < URL_LEN; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length() - 1);
            urlBuilder.append(CHARACTERS.charAt(randomIndex));
        }
        return urlBuilder.toString();
    }

    private static class UrlGenerator {
        private static final UrlGenerationUtil KLASS = new UrlGenerationUtil();
    }
}
