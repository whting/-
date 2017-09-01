package com.haoting.sys.common;

import com.haoting.client.SessionPermission;
import com.haoting.mvc.cache.RedisCache;
import com.haoting.mvc.model.Result;
import com.haoting.rpc.RpcPermission;
import com.haoting.sys.mapper.SysAppMapper;
import com.haoting.sys.model.SysApp;
import com.haoting.sys.service.SysPermissionService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: haoting.wang
 * @Date: Created in 上午11:21 2017/8/30
 */
public class PermissionManager {

    // 令牌有效期，单位为秒，默认30分钟
    protected int                         permissionTimeout   = 1800;

    private static String                 USER_PERMISSION_PRE = "user_u_";

    private static String                 APP_PERMISSION_PRE  = "user_a_";

    @Resource
    private RedisCache<SessionPermission> redisCache;

    @Resource
    private SysPermissionService          sysPermissionService;

    @Resource
    private SysAppMapper sysAppService;

    public SessionPermission getPermission(Long userId, Long appId) {

        String key = warpKey(userId, appId);
        SessionPermission sessionPermission = redisCache.get(key);

        if (sessionPermission == null) {

            List<RpcPermission> permissionList = sysPermissionService.findListById(appId, userId);

            List<RpcPermission> menuList = new ArrayList<RpcPermission>();
            Set<String> operateSet = new HashSet<String>();
            for (RpcPermission menu : permissionList) {
                if (menu.getIsMenu()) {
                    menuList.add(menu);
                }
                if (menu.getUrl() != null) {
                    operateSet.add(menu.getUrl());
                }
            }

            sessionPermission = new SessionPermission();
            // 设置登录用户菜单列表
            sessionPermission.setMenuList(menuList);

            // 保存登录用户权限列表
            sessionPermission.setPermissionSet(operateSet);

            if(userId !=null){
                // 保存登录用户没有权限的URL，方便前端去隐藏相应操作按钮
                Set<String> noPermissionSet = new HashSet<String>(redisCache.get(warpKey(null,appId)).getPermissionSet());
                noPermissionSet.removeAll(operateSet);
                sessionPermission.setNoPermissions(StringUtils.arrayToDelimitedString(noPermissionSet.toArray(), ","));

            }

            redisCache.set(key, sessionPermission, permissionTimeout);
        }

        return sessionPermission;
    }

    public SessionPermission getPermission(Long userId, String appCode) {

        SysApp app = sysAppService.findByCode(appCode);
        if (app == null) {
            return null;
        }
        return getPermission(userId,app.getId());
    }

    public void deletePermissionCache(Long userId, Long appId) {
        String key = warpKey(userId, appId);
        redisCache.delete(key);
    }


    public void deleteAppUserPerm(Long appId) {
        String key = warpKey(appId);
        redisCache.deletePattern(key);
    }

    public void deleteAppPermission(Long appId) {
        String key = warpKey(null,appId);
        redisCache.deletePattern(key);
    }





    public String warpKey(Long userId, Long appId) {

        if (userId == null) {
            return APP_PERMISSION_PRE + appId;
        }
        return USER_PERMISSION_PRE + userId + "_" + appId;
    }

    public String warpKey(Long appCode) {

        return USER_PERMISSION_PRE + "*_" + appCode;
    }

    public int getPermissionTimeout() {
        return permissionTimeout;
    }

    public void setPermissionTimeout(int permissionTimeout) {
        this.permissionTimeout = permissionTimeout;
    }


}
