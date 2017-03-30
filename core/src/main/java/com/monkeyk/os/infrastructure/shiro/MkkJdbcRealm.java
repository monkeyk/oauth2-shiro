package com.monkeyk.os.infrastructure.shiro;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.springframework.beans.factory.InitializingBean;

/**
 * 15-6-10
 * <p/>
 * 扩展默认的 JdbcRealm, 在查询用户密码是增加条件 archived = 0 (用于实现逻辑删除)
 * <p/>
 * 该类是一个扩展实现 Realm 的参考
 *
 * @author Shengzhao Li
 */
public class MkkJdbcRealm extends JdbcRealm implements InitializingBean {

    public static final String AUTHENTICATION_QUERY = "select password from users where archived = 0 and username = ?";

    public static final String USER_ROLES_QUERY = "select r.role_name from user_roles ur,users u,roles r  where ur.users_id = u.id and ur.roles_id = r.id and u.username = ?";

    public static final String PERMISSIONS_QUERY = "select rp.permission from roles_permissions rp,roles r where r.id = rp.roles_id and r.role_name = ?";


    public MkkJdbcRealm() {
        super();
    }


    /**
     * 根据实现的需要, 可以修改具体使用时的查询语句
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //override
        setAuthenticationQuery(AUTHENTICATION_QUERY);
        setUserRolesQuery(USER_ROLES_QUERY);
        setPermissionsQuery(PERMISSIONS_QUERY);
    }
}
