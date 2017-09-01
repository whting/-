package com.haoting.client;

import com.haoting.mvc.exception.ServiceException;
import com.haoting.mvc.model.Result;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限控制Filter
 * 
 * @author Joe
 */
public class PermissionFilter extends ClientFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String path = request.getServletPath();
        SessionUser sessionUser = SessionHolder.getSession();

        Result result = authenticationRpcService.validatePermission(sessionUser.getUserId(), ssoAppCode, path);

        if (result.isSuccess()) {
            chain.doFilter(request, response);
        } else {
            throw new ServiceException(SsoResultCode.SSO_PERMISSION_ERROR, "没有访问权限");
        }
    }
}
