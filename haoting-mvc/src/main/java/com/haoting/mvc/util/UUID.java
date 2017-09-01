package com.haoting.mvc.util;

/**
 * @Author: haoting.wang
 * @Date: Created in 下午3:08 2017/8/22
 */
public class UUID {

    /**
     * Description:通过uuid生成唯一的记录id
     * @author haoting.wang
     * @return 生成的id
     */
    public static String createUUIDId() {
        java.util.UUID uuid = java.util.UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
