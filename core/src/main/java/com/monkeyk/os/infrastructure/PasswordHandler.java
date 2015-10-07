package com.monkeyk.os.infrastructure;

import com.monkeyk.os.domain.shared.GuidGenerator;
import org.springframework.util.StringUtils;

/**
 * @author Shengzhao Li
 */
public abstract class PasswordHandler {


    /**
     * Encrypt factor, use it for  reversible  password
     */
    private static final int ENCRYPT_FACTOR = 7;

    /**
     * Return a random password from {@link java.util.UUID},
     * the length is 8.
     *
     * @return Random password
     */
    public static String randomPassword() {
        String uuid = GuidGenerator.generate();
        return uuid.substring(0, 8);
    }


    /**
     * Encrypt the reversible password
     *
     * @param originalPassword originalPassword
     * @return encrypted password
     */
    public static String encryptReversiblePassword(String originalPassword) {
        if (!StringUtils.hasText(originalPassword)) {
            return originalPassword;
        }
        byte[] bytes = originalPassword.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            bytes[i] = (byte) (b ^ ENCRYPT_FACTOR);
        }
        return new String(bytes);
    }

    /**
     * Decrypt the encrypted password.
     *
     * @param encryptedPassword encryptedPassword
     * @return decrypted password
     */
    public static String decryptReversiblePassword(String encryptedPassword) {
        return encryptReversiblePassword(encryptedPassword);
    }
}