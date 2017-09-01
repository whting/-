package com.haoting.mvc.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: haoting.wang
 * @Date: Created in 下午2:47 2017/8/22
 */
public class IPUtils {

    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            }
            else {
                return ip;
            }
        }
        else {
            return request.getRemoteAddr();
        }
    }
}
