package com.haoting.sys.controller;

import com.haoting.client.SessionUser;
import com.haoting.client.SsoFilter;
import com.haoting.mvc.captcha.CaptchaHelper;
import com.haoting.mvc.model.Result;
import com.haoting.mvc.provider.PasswordProvider;
import com.haoting.mvc.util.CookieUtils;
import com.haoting.mvc.util.IPUtils;
import com.haoting.mvc.util.StringUtils;
import com.haoting.mvc.util.UUID;
import com.haoting.mvc.validator.annotation.ValidateParam;
import com.haoting.sys.common.LoginUser;
import com.haoting.sys.common.TokenManager;
import com.haoting.sys.model.SysUser;
import com.haoting.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.haoting.mvc.validator.Validator.NOT_BLANK;

/**
 * @Author: haoting.wang
 * @Date: Created in 下午2:21 2017/8/22
 */
@Controller
@RequestMapping("/login")
public class LoginController {


    // 登录页
    private static final String LOGIN_PATH = "/login";

    @Resource
    private TokenManager tokenManager;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(method = RequestMethod.GET)
    public String login(String backUrl, String appCode, HttpServletRequest request) {

        String token = CookieUtils.getCookie(request, "token");
        if (token == null) {
            return goLoginPath(backUrl, appCode, request);
        } else {
            LoginUser loginuser = tokenManager.validate(token);
            if (loginuser != null) {
                return "redirect:" + authBackUrl(backUrl, token);
            } else {
                return goLoginPath(backUrl, appCode, request);
            }
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    public String login(
            @ValidateParam({NOT_BLANK}) String backUrl,
            @ValidateParam({NOT_BLANK}) String appCode,
            @ValidateParam({NOT_BLANK}) String account,
            @ValidateParam({NOT_BLANK}) String password,
            @ValidateParam({NOT_BLANK}) String captcha,
            HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        if (!CaptchaHelper.validate(request, captcha)) {
            request.setAttribute("errorMessage", "验证码不正确");
            return goLoginPath(backUrl, appCode, request);
        }
        Result<SysUser> result = sysUserService.login(IPUtils.getIpAddr(request), appCode, account, PasswordProvider.encrypt(password));
        if (!result.isSuccess()) {
            request.setAttribute("errorMessage", result.getMessage());
            return goLoginPath(backUrl, appCode, request);
        } else {
            SysUser user = result.getData();

            LoginUser loginUser = new LoginUser(user.getId(), user.getAccount());

            String token = CookieUtils.getCookie(request, "token");
            if (StringUtils.isBlank(token) || tokenManager.validate(token) == null) {// 没有登录的情况
                token = createToken(loginUser);
                CookieUtils.addTokenInCookie(token, request, response);
            }

            // 跳转到原请求
            backUrl = URLDecoder.decode(backUrl, "utf-8");
            return "redirect:" + authBackUrl(backUrl, token);
        }
    }

    private String createToken(LoginUser loginUser) {
        // 生成token
        String token = UUID.createUUIDId();

        // 缓存中添加token对应User
        tokenManager.addToken(token, loginUser);
        return token;
    }


    private String authBackUrl(String backUrl, String token) {
        if(StringUtils.isBlank(backUrl)){
            backUrl = "/admin/admin";
        }
        StringBuilder sbf = new StringBuilder(backUrl);
        if (backUrl.indexOf("?") > 0) {
            sbf.append("&");
        } else {
            sbf.append("?");
        }
        sbf.append(SsoFilter.SSO_TOKEN_NAME).append("=").append(token);
        return sbf.toString();
    }

    private String goLoginPath(String backUrl, String appCode, HttpServletRequest request) {
        request.setAttribute("backUrl", backUrl);
        request.setAttribute("appCode", appCode);
        return LOGIN_PATH;
    }
}
