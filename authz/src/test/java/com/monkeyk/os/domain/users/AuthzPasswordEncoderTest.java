package com.monkeyk.os.domain.users;

import com.monkeyk.os.domain.users.password.ShaPasswordEncoder;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 2023/9/25 17:38
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
class AuthzPasswordEncoderTest {


    @Test
    void initPwd() {

        //默认 SHA-256
        ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);

        String adminPwd = "Admin@2015#";
        String salt = "75fbe11d6f70e77b256121d7c3d5c412";
        ByteSource bytesx = ByteSource.Util.bytes(Base64.decode(salt));
        String encode = passwordEncoder.encode(adminPwd, bytesx);
        assertNotNull(encode);
        assertEquals("7242fb20c63882a6664742e1f9e1ed77e13b74d601cbe5fb11430d24768e808b", encode);
//        System.out.println(encode);
        assertTrue(passwordEncoder.matches(adminPwd, encode, bytesx));

        String testPwd = "Test@2015#";
        String testSalt = "5602aa9866ca612e66dbb7f7c9a1d3b7";
        ByteSource bytes = ByteSource.Util.bytes(Base64.decode(testSalt));
        String encode2 = passwordEncoder.encode(testPwd, bytes);
        assertNotNull(encode2);
        assertEquals("beef64b3218e0c93051119bde87782ed7b932169228c091464055369336f5044", encode2);
//        System.out.println(encode2);
        assertTrue(passwordEncoder.matches(testPwd, encode2, bytes));

    }

}