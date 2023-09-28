package com.monkeyk.os.domain.users.password;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 2023/9/25 16:47
 * <p>
 * SHA-xx 算法实现抽象，
 * 如SHA-256
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
public class ShaPasswordEncoder implements PasswordEncoder {

    private static final String SHA_PREFIX = "SHA-";

    /**
     * 如 256, 384, 512
     */
    private int length;

    /**
     * Alg
     */
    private final String alg;

    /**
     * SHA算法
     *
     * @param length 如 256, 384, 512
     */
    public ShaPasswordEncoder(int length) {
        this.alg = SHA_PREFIX + length;

    }


    /**
     * SHA算法
     *
     * @param alg 如 SHA-256, SHA-384, SHA-512
     */
    public ShaPasswordEncoder(String alg) {
        this.alg = alg;
    }

    @Override
    public String encode(String rawPassword, ByteSource salt) {
        char[] pwdChar = rawPassword.toCharArray();
        SimpleHash simpleHash = new SimpleHash(this.alg, pwdChar, salt);
        return simpleHash.toHex();
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword, ByteSource salt) {
        String encode = this.encode(rawPassword, salt);
        return MessageDigest.isEqual(
                encode.getBytes(StandardCharsets.UTF_8),
                encodedPassword.getBytes(StandardCharsets.UTF_8));
    }
}
