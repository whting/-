package com.haoting.sys.controller;

import com.haoting.client.SessionHolder;
import com.haoting.client.SessionPermission;
import com.haoting.client.SessionUser;
import com.haoting.mvc.config.ConfigUtils;
import com.haoting.mvc.model.Result;
import com.haoting.sys.common.PermissionManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Joe
 */
@Controller
@RequestMapping("/admin/admin")
public class AdminController {

	@Autowired
	private PermissionManager permissionManager;

	@ApiOperation("初始页")
	@RequestMapping(method = RequestMethod.GET)
	public String execute(Model model) {

		return "admin";
	}

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public @ResponseBody
	Result menu(HttpServletRequest request) {

		SessionUser session = SessionHolder.getSession();
		String appCode = ConfigUtils.getProperty("sso.app.code");
		SessionPermission sessionPermission = permissionManager.getPermission(session.getUserId(),appCode);

		if(sessionPermission == null){
			sessionPermission = permissionManager.getPermission(null,appCode);
		}
		return  Result.valueOfSuccess(sessionPermission);

	}

}