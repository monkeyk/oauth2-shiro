package com.monkeyk.os.domain.users.password;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 2023/9/25 16:23
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
public class MD5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(String rawPassword, ByteSource salt) {
        Md5Hash md5Hash = new Md5Hash(rawPassword, salt);
        return md5Hash.toHex();
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword, ByteSource salt) {
        String encode = this.encode(rawPassword, salt);
        return MessageDigest.isEqual(encode.getBytes(StandardCharsets.UTF_8), encodedPassword.getBytes(StandardCharsets.UTF_8));
    }
}
