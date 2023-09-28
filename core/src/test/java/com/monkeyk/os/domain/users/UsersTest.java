package com.monkeyk.os.domain.users;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 2023/9/25 22:18
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
class UsersTest {


    @Test
    void passwordSalt() {

        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        ByteSource byteSource = randomNumberGenerator.nextBytes();
        assertNotNull(byteSource);
        assertNotNull(byteSource.toHex());
//        System.out.println(byteSource.toHex());


        ByteSource bytes = ByteSource.Util.bytes(byteSource.toHex());
        assertNotNull(bytes);

    }

}