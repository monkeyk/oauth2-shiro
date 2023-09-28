package com.monkeyk.os.domain.users.password;

import org.apache.shiro.util.ByteSource;

/**
 * 2023/9/25 16:19
 * <p>
 * 密码加密 与  验证
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
public interface PasswordEncoder {


    /**
     * 加密密码
     *
     * @param rawPassword 原始密码
     * @param salt        盐 or null
     * @return 加密后的密码
     */
    String encode(String rawPassword, ByteSource salt);


    /**
     * 校验加密的密码是否正确
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密的密码
     * @param salt            盐 or null
     * @return true 校验正确，其他 false
     */
    boolean matches(String rawPassword, String encodedPassword, ByteSource salt);

}
