package com.haoting.sys.controller.sys;

import com.haoting.mvc.model.Result;
import com.haoting.mvc.pagination.Pagination;
import com.haoting.mvc.provider.PasswordProvider;
import com.haoting.mvc.validator.annotation.ValidateParam;
import com.haoting.sys.dto.SysUserSearchDTO;
import com.haoting.sys.model.SysApp;
import com.haoting.sys.model.SysUser;
import com.haoting.sys.service.SysAppService;
import com.haoting.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static com.haoting.mvc.validator.Validator.NOT_BLANK;

/**
 * @Author: smili
 */
@Controller
@RequestMapping("/admin/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysAppService sysAppService;

    @RequestMapping(method = RequestMethod.GET)
    public String execute(Model model) {

        model.addAttribute("appList", getAppList());
        return "/admin/user";
    }

    private List<SysApp> getAppList() {
        return sysAppService.findAll();
    }

    @RequestMapping("/edit")
    public String edit(Long id, Model model) {
        SysUser sysUser;
        if (id == null) {
            sysUser = new SysUser();
        } else {
            sysUser = sysUserService.selectByPrimaryKey(id);
        }
        model.addAttribute("user", sysUser);
        return "/admin/userEdit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result<String> save(Long id, @ValidateParam(value = { NOT_BLANK }, name = "帐号") String account,
                               @ValidateParam(value = { NOT_BLANK }, name = "密码") String password,
                               @ValidateParam(value = { NOT_BLANK }, name = "是否启用") Boolean isEnable) {

        SysUser sysUser = new SysUser();
        if (id == null) {
            sysUser.setGmtCreatedTime(new Date());
        } else {
            sysUser = sysUserService.selectByPrimaryKey(id);
        }
        sysUser.setAccount(account);
        sysUser.setPassword(PasswordProvider.encrypt(password));
        sysUser.setIsEnable(isEnable);

        sysUserService.save(sysUser);

        return Result.valueOfSuccess();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Result<String> delete(@ValidateParam(value = { NOT_BLANK }, name = "ids")Long[] ids) {

        if (ids == null) {
            return Result.valueOfError("请选择需要删除的资源");
        }

        sysUserService.deleteBatch(ids);

        return Result.valueOfSuccess("删除成功");
    }

    /**
     * 分页查询
     *
     * @param sysUserSearchDTO
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result<Pagination<SysUser>> listPage(SysUserSearchDTO sysUserSearchDTO) {

        Pagination<SysUser> pagination = sysUserService.searchForPage(sysUserSearchDTO);

        return Result.valueOfSuccess(pagination);
    }


    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    @ResponseBody
    public Result updateEnable(@ValidateParam({ NOT_BLANK }) Long[] ids, @ValidateParam({ NOT_BLANK }) Boolean isEnable) {

        sysUserService.updateEnable(isEnable, ids);
        if(isEnable){
            return Result.valueOfSuccess("启用成功");
        }
        return Result.valueOfSuccess("禁用成功");
    }

    @RequestMapping("/resetPassword")
    @ResponseBody
    public Result<String> resetPassword(@ValidateParam(value = {NOT_BLANK},name = "ids") Long[] ids){


        sysUserService.resetPassword(ids);

        return Result.valueOfSuccess("重置密码成功");
    }

}
