package com.miu.pasteit.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Rimon Mostafiz
 * @author Samson Hailu
 */
@Slf4j
public class Utils {

    public static final String HASH_ALGORITHM = "MD5";

    public static String getRequestOwner() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static String getContentHash(String content) {
        try {
            MessageDigest instance = MessageDigest.getInstance(HASH_ALGORITHM);
            instance.update(content.getBytes());
            byte[] digest = instance.digest();
            return DatatypeConverter.printHexBinary(digest)
                    .toUpperCase();
        } catch (NoSuchAlgorithmException ex) {
            log.debug("NoSuchAlgo Exception while creating MessageDigest");
            return null;
        }
    }
}
