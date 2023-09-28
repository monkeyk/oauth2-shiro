package com.monkeyk.os.domain.users.password;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 2023/9/25 16:28
 *
 * @author Shengzhao Li
 */
class MD5PasswordEncoderTest {

    @Test
    void encode() {

        MD5PasswordEncoder passwordEncoder = new MD5PasswordEncoder();
        String rawPwd = "Admin@2023&";
        String salt = "75fbe11d6f70e77b256121d7c3d5c412";
        ByteSource saltBytes = ByteSource.Util.bytes(Base64.decode(salt));
        String encode = passwordEncoder.encode(rawPwd, saltBytes);

        assertNotNull(encode);
//        System.out.println(encode);
        assertEquals("fa1250c1ddefb1645a5507e8a7d76c99", encode);

        boolean matches = passwordEncoder.matches(rawPwd, encode, saltBytes);
        assertTrue(matches);

    }


    @Test
    void encodeHex() {

        Md5Hash md5Hash = new Md5Hash("admin");
        String hex = md5Hash.toHex();
        assertNotNull(hex);

//        System.out.println(md5Hash.toBase64());
        assertEquals("21232f297a57a5a743894a0e4a801fc3", hex);

    }

}