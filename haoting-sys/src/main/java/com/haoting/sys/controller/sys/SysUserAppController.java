package com.haoting.sys.controller.sys;

import com.google.common.collect.Lists;
import com.haoting.mvc.enums.TrueFalseEnum;
import com.haoting.mvc.model.Result;
import com.haoting.mvc.validator.annotation.ValidateParam;
import com.haoting.sys.model.SysApp;
import com.haoting.sys.model.SysUserApp;
import com.haoting.sys.service.SysAppService;
import com.haoting.sys.service.SysUserAppService;
import com.haoting.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/admin/userApp")
public class SysUserAppController {

    @Autowired
    private SysAppService     sysAppService;

    @Autowired
    private SysUserService    sysUserService;

    @Autowired
    private SysUserAppService sysUserAppService;

    @RequestMapping(value = "/allocate", method = RequestMethod.GET)
    public String edit(@ValidateParam({ NOT_BLANK }) Long userId, Model model) {

        model.addAttribute("userId", userId);
        model.addAttribute("appList", getAppList(userId));
        return "/admin/userApp";
    }

    @RequestMapping("/allocateSave")
    @ResponseBody
    public Result<String> allocateSave(@ValidateParam({NOT_BLANK}) Long userId,Long[] appIds){

        List<SysUserApp> list = Lists.newArrayList();

        for(Long appId : appIds){
            SysUserApp sysUserApp = new SysUserApp();
            sysUserApp.setAppId(appId);
            sysUserApp.setUserId(userId);
            list.add(sysUserApp);
        }

        sysUserAppService.allocateSave(userId,list);

        return Result.valueOfSuccess("授权成功");

    }

    private List<SysApp> getAppList(Long userId) {
        List<SysApp> list = sysAppService.findAll();

        List<SysUserApp> userAppList = sysUserAppService.findUserAppByUserId(userId);

        for (SysApp app : list) {
            boolean flag = false;
            for (SysUserApp sysUserApp : userAppList) {
                if (sysUserApp.getAppId().equals(app.getId())) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                app.setIsChecked(true);
            } else {
                app.setIsChecked(false);
            }

        }
        return list;
    }
}
