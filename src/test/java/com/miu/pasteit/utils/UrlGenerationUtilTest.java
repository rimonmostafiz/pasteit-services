package com.miu.pasteit.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Rimon Mostafiz
 */
class UrlGenerationUtilTest {

    @Test
    void shouldGenerateRandomURL() {
        String randomURL = UrlGenerationUtil.getInstance().generateRandomURL();
        Assertions.assertNotNull(randomURL, "Random URL should not be null");
    }

    @Test
    void shouldReturnRandomUrlOfLengthEqualsToUrlLen() {
        String randomURL = UrlGenerationUtil.getInstance().generateRandomURL();
        Assertions.assertEquals(UrlGenerationUtil.URL_LEN, randomURL.length(), "Url length is not same");
    }

    @Test
    void shouldGenerateRandomUrlOfBase62() {
        String randomURL = UrlGenerationUtil.getInstance().generateRandomURL();
        for (char ch : randomURL.toCharArray()) {
            String character = String.valueOf(ch);
            Assertions.assertTrue(UrlGenerationUtil.CHARACTERS.contains(character), "Character is unknown");
        }
    }

    @Test
    void shouldReturnInstanceThatNotNull() {
        Assertions.assertNotNull(UrlGenerationUtil.getInstance(), "Instance is null");
    }
}