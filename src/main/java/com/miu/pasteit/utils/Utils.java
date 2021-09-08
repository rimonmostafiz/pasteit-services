package com.miu.pasteit.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Rimon Mostafiz
 */
@Slf4j
public class Utils {

    public static final String HASH_ALGORITHM = "MD5";


    public static UserDetails extractUserDetails(HttpServletRequest request) {
        return (UserDetails) request.getSession().getAttribute(SessionKey.USER_DETAILS);
    }

    public static String getUserNameFromRequest(HttpServletRequest request) {
        return extractUserDetails(request).getUsername();
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
