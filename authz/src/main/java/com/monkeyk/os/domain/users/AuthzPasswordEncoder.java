package com.monkeyk.os.domain.users;

import com.monkeyk.os.domain.users.password.MD5PasswordEncoder;
import com.monkeyk.os.domain.users.password.PasswordEncoder;
import com.monkeyk.os.domain.users.password.ShaPasswordEncoder;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.util.Assert;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 2023/9/25 17:15
 * <p>
 * 对各类密码的 加密,校验封装
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
@Component
public class AuthzPasswordEncoder implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(AuthzPasswordEncoder.class);

    /**
     * 存储凭证（如密码）的加密算法（如 MD5,SHA-256,SHA-384,SHA-512）
     * 默认 SHA-256
     * <p>
     * 若是旧版本升级后继续使用MD5，请将配置值更新为MD5
     * <code>
     * authz.store.credentials.alg=MD5
     * </code>
     *
     * @since 2.0.0
     */
    @Value("${authz.store.credentials.alg:" + Sha256Hash.ALGORITHM_NAME + "}")
    private String storeCredentialsAlg;


    private PasswordEncoder passwordEncoder;


    public AuthzPasswordEncoder() {
    }


    /**
     * 加密
     *
     * @param rasPassword 原始密码
     * @param salt        盐 or null
     * @return 加密后的
     */
    public String encode(String rasPassword, ByteSource salt) {
        return this.passwordEncoder.encode(rasPassword, salt);
    }

    /**
     * 校验密码是否正确
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密的密码
     * @param salt            盐 or null
     * @return true 校验正确，其他 false
     */
    public boolean matches(String rawPassword, String encodedPassword, ByteSource salt) {
        return this.passwordEncoder.matches(rawPassword, encodedPassword, salt);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.storeCredentialsAlg, "storeCredentialsAlg is null");

        switch (this.storeCredentialsAlg) {
            case Sha256Hash.ALGORITHM_NAME:
                this.passwordEncoder = new ShaPasswordEncoder(256);
                break;
            case Sha384Hash.ALGORITHM_NAME:
                this.passwordEncoder = new ShaPasswordEncoder(384);
                break;
            case Sha512Hash.ALGORITHM_NAME:
                this.passwordEncoder = new ShaPasswordEncoder(512);
                break;
            case Sha1Hash.ALGORITHM_NAME:
                this.passwordEncoder = new ShaPasswordEncoder(1);
                if (LOG.isWarnEnabled()) {
                    LOG.warn("[Deprecated]  alg: {} is unsafe, not recommended for use (try use SHA-256 or SHA-512 much better)", Sha1Hash.ALGORITHM_NAME);
                }
                break;
            case Md5Hash.ALGORITHM_NAME:
                this.passwordEncoder = new MD5PasswordEncoder();
                if (LOG.isWarnEnabled()) {
                    LOG.warn("[Deprecated]  alg: {} is unsafe, not recommended for use (try use SHA-256 or SHA-512 much better)", Md5Hash.ALGORITHM_NAME);
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupport storeCredentialsAlg: "
                        + this.storeCredentialsAlg + ", please checking property 'authz.store.credentials.alg' ");
        }
    }
}
