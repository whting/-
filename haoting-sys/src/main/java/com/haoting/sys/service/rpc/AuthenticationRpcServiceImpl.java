package com.haoting.sys.service.rpc;

import com.haoting.client.SessionPermission;
import com.haoting.mvc.model.Result;
import com.haoting.mvc.util.StringUtils;
import com.haoting.rpc.AuthenticationRpcService;
import com.haoting.rpc.RpcPermission;
import com.haoting.rpc.RpcUser;
import com.haoting.sys.common.LoginUser;
import com.haoting.sys.common.PermissionManager;
import com.haoting.sys.common.TokenManager;
import com.haoting.sys.model.SysApp;
import com.haoting.sys.service.SysAppService;
import com.haoting.sys.service.SysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service("authenticationRpcService")
public class AuthenticationRpcServiceImpl implements AuthenticationRpcService {

    @Resource
    private SysPermissionService sysPermissionService;

    @Resource
    private TokenManager         tokenManager;

    @Resource
    private PermissionManager    permissionManager;

    @Resource
    private SysAppService        sysAppService;

    @Override
    public boolean validate(String token) {
        return tokenManager.validate(token) != null;
    }

    @Override
    public RpcUser findAuthInfo(String token) {
        LoginUser user = tokenManager.validate(token);
        if (user != null) {
            return new RpcUser(user.getUserId(), user.getAccount());
        }
        return null;
    }

    @Override
    public Result<List<RpcPermission>> findPermissionList(String token, String appCode) {

        SysApp app = sysAppService.findByCode(appCode);
        if (app == null) {
            return Result.valueOfError("没有对应的应用");
        }

        if (StringUtils.isBlank(token)) {
            List<RpcPermission> permissionList = sysPermissionService.findListById(app.getId(), null);
            return Result.valueOfSuccess(permissionList);
        } else {
            LoginUser user = tokenManager.validate(token);
            if (user != null) {
                List<RpcPermission> permissionList = sysPermissionService.findListById(app.getId(), user.getUserId());
                return Result.valueOfSuccess(permissionList);
            } else {
                return Result.valueOfSuccess(Collections.EMPTY_LIST);
            }
        }
    }

    @Override
    public Result validatePermission(Long userId, String ssoAppCode, String path) {

        SysApp app = sysAppService.findByCode(ssoAppCode);
        if (app == null) {
            return Result.valueOfError("没有对应的应用");
        }

        SessionPermission applicationPermission = permissionManager.getPermission(null, app.getId());

        SessionPermission userPermission = permissionManager.getPermission(userId, app.getId());

        boolean hasPerm = userPermission.getPermissionSet().contains(path);

        if (hasPerm) {
            return Result.valueOfSuccess();
        }


        boolean isAppPerm = applicationPermission.getPermissionSet().contains(path);

        if (isAppPerm) {
            return Result.valueOfError("没有权限,请联系管理员,开通操作权限");
        }

        return Result.valueOfSuccess();
    }

}
