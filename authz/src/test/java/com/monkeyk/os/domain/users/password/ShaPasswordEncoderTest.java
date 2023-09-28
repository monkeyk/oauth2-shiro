package com.monkeyk.os.domain.users.password;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 2023/9/25 17:04
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
class ShaPasswordEncoderTest {


    @Test
    void encode256() {

        String rawPwd = "Admin@2023&";
        String salt = "5602aa9866ca612e66dbb7f7c9a1d3b7";
        ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
        ByteSource bytes = ByteSource.Util.bytes(Base64.decode(salt));
        String encode = passwordEncoder.encode(rawPwd, bytes);

        assertNotNull(encode);
        assertEquals("34f37824cf8008f47d777ce9954eff34f2de984f2dc6019952b7bc8d72794ddf", encode);
//        System.out.println(encode);

        boolean matches = passwordEncoder.matches(rawPwd, encode, bytes);
        assertTrue(matches);

    }

    @Test
    void encode384() {

        String rawPwd = "Admin@2023&";
        ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(384);
        String encode = passwordEncoder.encode(rawPwd, null);

        assertNotNull(encode);
        assertEquals("0a32b1b88f37ad21ac65aae125cec7814e5535d1077a4a7e6ccbf48a16800603e04c6909d9a19c68080a248b7ca11034", encode);
//        System.out.println(encode);

        boolean matches = passwordEncoder.matches(rawPwd, encode, null);
        assertTrue(matches);

    }

    @Test
    void encode512() {

        String rawPwd = "Admin@2023&";
        ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(512);
        String encode = passwordEncoder.encode(rawPwd, null);

        assertNotNull(encode);
        assertEquals(
                "21dc4b7e4c58ece1d581644231d19d3a09ab5e1047222f28835a05efc3d9293b3d4df18654d0fd985aad592f154cd7fcefab513997ccc1cf9833f362590e63b8",
                encode);
//        System.out.println(encode);

        boolean matches = passwordEncoder.matches(rawPwd, encode, null);
        assertTrue(matches);

    }

}