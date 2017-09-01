package com.haoting.client;

import com.haoting.mvc.exception.ServiceException;
import com.haoting.mvc.util.CookieUtils;
import com.haoting.mvc.util.StringUtils;
import com.haoting.rpc.RpcUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 单点登录及Token验证Filter
 *
 * @author Joe
 */
public class SsoFilter extends ClientFilter {

    // sso授权回调参数token名称
    public static final String SSO_TOKEN_NAME = "__vt_param__";

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        String requestToken = request.getParameter(SSO_TOKEN_NAME);
        if (requestToken != null) {

            String token = getParameterToken(request);

            if(!StringUtils.isBlank(token)){
                CookieUtils.addTokenInCookie(token, request, response);
                // 再跳转一次当前URL，以便去掉URL中token参数
                response.sendRedirect(request.getRequestURL().toString());
                return;
            }
        }

        String token = CookieUtils.getCookie(request, "token");

        if (token == null) {
            redirectLogin(request, response);
            return;
        }

        if (isLogined(token)) {
            try {
                chain.doFilter(request, response);
            }finally {
                SessionHolder.clearSession();
            }
        } else {
            redirectLogin(request, response);
        }
    }

    /**
     * 获取服务端回传token参数且验证
     *
     * @param request
     * @return
     * @throws IOException
     */
    private String getParameterToken(HttpServletRequest request) throws IOException {
        String token = request.getParameter(SSO_TOKEN_NAME);
        if (token != null) {
            RpcUser rpcUser = authenticationRpcService.findAuthInfo(token);
            if (rpcUser != null) {
                // invokeAuthenticationInfoInSession(request, token, rpcUser.getAccount());
                return token;
            }
        }
        return null;
    }

    /**
     * 跳转登录
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void redirectLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isAjaxRequest(request)) {
            throw new ServiceException(SsoResultCode.SSO_TOKEN_ERROR, "未登录或已超时");
        } else {
            String ssoLoginUrl = new StringBuilder().append(ssoServerUrl).append("/login?backUrl=").append(request.getRequestURL()).append("&appCode=").append(ssoAppCode).toString();

            response.sendRedirect(ssoLoginUrl);
        }
    }

    /**
     * 是否已登录
     *
     * @param token
     * @return
     */
    private boolean isLogined(String token) {
        RpcUser rpcUser =  authenticationRpcService.findAuthInfo(token);

        if(rpcUser!=null){

            SessionUser sessionUser = new SessionUser();
            sessionUser.setUserId(rpcUser.getUserId());
            sessionUser.setAccount(rpcUser.getAccount());
            SessionHolder.setSession(sessionUser);

            return true;
        }
        return false;
    }

    /**
     * 是否Ajax请求
     *
     * @param request
     * @return
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
    }
}
