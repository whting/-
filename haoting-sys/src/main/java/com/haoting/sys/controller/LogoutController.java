package com.haoting.sys.controller;

import com.haoting.mvc.util.CookieUtils;
import com.haoting.mvc.util.StringUtils;
import com.haoting.sys.common.TokenManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Joe
 */
@Controller
@RequestMapping("/logout")
public class LogoutController {
	
	@Resource
	private TokenManager tokenManager;

	@RequestMapping(method = RequestMethod.GET)
	public String logout( String backUrl, HttpServletRequest request) {
		String token = CookieUtils.getCookie(request, "token");
		if (StringUtils.isNotBlank(token)) {
			tokenManager.remove(token);
		}

		return "redirect:" + (StringUtils.isBlank(backUrl) ? "/admin/admin" : backUrl);
	}
}