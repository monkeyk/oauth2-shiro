package com.monkeyk.os.infrastructure;

import org.springframework.core.NamedThreadLocal;

/**
 * @author Shengzhao Li
 */
public abstract class ThreadLocalHolder {

    private static NamedThreadLocal<String> clientIpThreadLocal = new NamedThreadLocal<>("clientIpThreadLocal");


    /*
    * 设置当前访问的IP地址
    * */
    public static void clientIp(String clientIp) {
        clientIpThreadLocal.set(clientIp);
    }

    /*
    * 获取访问的IP地址
    * */
    public static String clientIp() {
        return clientIpThreadLocal.get();
    }


    private ThreadLocalHolder() {
    }

}