package com.monkeyk.os.domain.shared;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.util.ByteSource;

/**
 * @author Shengzhao Li
 */
public abstract class GuidGenerator {


    /**
     * salt generator
     *
     * @since 2.0.0
     */
    private static final SecureRandomNumberGenerator SALT_NUMBER_GENERATOR = new SecureRandomNumberGenerator();

    private GuidGenerator() {
    }


    /**
     * 生成一个随机的 salt
     *
     * @return ByteSource
     * @since 2.0.0
     */
    public static ByteSource nextSalt() {
        return SALT_NUMBER_GENERATOR.nextBytes();
    }

    /**
     * 生成一个随机的 salt, hex
     *
     * @return hex string
     * @since 2.0.0
     */
    public static String nextSaltHex() {
        return nextSalt().toHex();
    }

    public static String generate() {
        return RandomStringUtils.random(32, true, true);
    }


    public static String generateClientId() {
        return RandomStringUtils.random(20, true, true);
    }

    public static String generateClientSecret() {
        return RandomStringUtils.random(20, true, true);
    }

}