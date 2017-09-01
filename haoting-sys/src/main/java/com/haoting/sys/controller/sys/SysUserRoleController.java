package com.haoting.sys.controller.sys;

import com.google.common.collect.Lists;
import com.haoting.mvc.enums.TrueFalseEnum;
import com.haoting.mvc.model.Result;
import com.haoting.mvc.validator.annotation.ValidateParam;
import com.haoting.sys.model.SysApp;
import com.haoting.sys.model.SysRole;
import com.haoting.sys.model.SysUserApp;
import com.haoting.sys.model.SysUserRole;
import com.haoting.sys.service.*;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.haoting.mvc.validator.Validator.NOT_BLANK;

/**
 * @Author: haoting.wang
 * @Date: Created in 上午10:41 2017/8/21
 */
@Controller
@RequestMapping("/admin/userRole")
public class SysUserRoleController {

    @Autowired
    private SysAppService     sysAppService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @RequestMapping(value = "/allocate", method = RequestMethod.GET)
    public String edit(@ValidateParam({ NOT_BLANK }) Long userId, Model model) {

        List<SysApp> appList = sysAppService.findByUserId(TrueFalseEnum.TRUE.getValue(), userId);

        List<SysRole> roleList = getRoleList(userId, CollectionUtils.isEmpty(appList) ? null : appList.get(0).getId());

        model.addAttribute("userId", userId);
        model.addAttribute("appList", appList);
        model.addAttribute("roleList", roleList);
        return "/admin/userRole";
    }

    @RequestMapping(value = "/change", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<SysRole>> changeApp(
           @ValidateParam({ NOT_BLANK }) Long appId,
           @ValidateParam({ NOT_BLANK }) Long userId) {


        return Result.valueOfSuccess(getRoleList(userId, appId));
    }

    private List<SysRole> getRoleList(Long userId,Long appId) {

        List<SysRole> list = sysRoleService.findByAppId(TrueFalseEnum.TRUE.getValue(), appId);

        List<SysUserRole> userRoleList = sysUserRoleService.findByUserId(userId);

        for(SysRole sysRole : list){

            boolean flag = false;
            for(SysUserRole sysUserRole : userRoleList){
                if(sysRole.getId().equals(sysUserRole.getRoleId())){
                    flag = true;
                    break;
                }
            }

            if (flag) {
                sysRole.setIsChecked(true);
            } else {
                sysRole.setIsChecked(false);
            }
        }

        return list;
    }

    @RequestMapping("/allocateSave")
    @ResponseBody
    public Result<String> allocateSave(@ValidateParam({NOT_BLANK}) Long userId,Long[] roleIds,Long appId){

        List<SysUserRole> list = Lists.newArrayList();

        for(Long roleId : roleIds){

            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setAppId(appId);
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            list.add(sysUserRole);
        }

        sysUserRoleService.allocateSave(userId,list,appId);

        return Result.valueOfSuccessMsg("授权成功");

    }
}
