package com.monkeyk.os.domain.users;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 2016/3/25
 * <p/>
 * 账号密码的加密 处理类
 * 使用MD5
 *
 * @author Shengzhao Li
 * @deprecated use AuthzPasswordEncoder.java replaced from 2.0.0
 */
public abstract class PasswordHandler {


    private PasswordHandler() {
    }


    public static String md5(String password) {

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        byte[] bytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return String.format("%032x", new BigInteger(1, bytes));
    }
}
