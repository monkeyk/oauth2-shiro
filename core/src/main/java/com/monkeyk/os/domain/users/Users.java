package com.monkeyk.os.domain.users;

import com.monkeyk.os.domain.AbstractDomain;
import com.monkeyk.os.domain.shared.BeanProvider;

import java.util.Date;
import java.util.List;

/**
 * 2016/6/3
 * <p/>
 * Table: users
 * <p/>
 * 用户(账号)信息
 *
 * @author Shengzhao Li
 */
public class Users extends AbstractDomain {
    private static final long serialVersionUID = -3990278799194556012L;

    private transient UsersRepository usersRepository = BeanProvider.getBean(UsersRepository.class);

    private String username;
    private String password;

    /**
     * 密码 salt,  盐值
     *
     * @since 2.0.0
     */
    private String passwordSalt;

    private boolean defaultUser;
    private Date lastLoginTime;

    public Users() {
    }

    /**
     * @return salt or null
     * @since 2.0.0
     */
    public String passwordSalt() {
        return passwordSalt;
    }

    /**
     * @param passwordSalt salt
     * @return this
     * @since 2.0.0
     */
    public Users passwordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
        return this;
    }

    public List<Roles> rolesList() {
        return usersRepository.findUsersRolesList(this.guid);
    }


    public String username() {
        return username;
    }

    public Users username(String username) {
        this.username = username;
        return this;
    }

    public String password() {
        return password;
    }

    public Users password(String password) {
        this.password = password;
        return this;
    }

    public boolean defaultUser() {
        return defaultUser;
    }

    public Users defaultUser(boolean defaultUser) {
        this.defaultUser = defaultUser;
        return this;
    }

    public Date lastLoginTime() {
        return lastLoginTime;
    }

    public Users lastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }
}
