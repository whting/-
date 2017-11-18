package com.haoting.sys.controller.sys;

import com.google.common.collect.Lists;
import com.haoting.mvc.model.Result;
import com.haoting.mvc.pagination.Pagination;
import com.haoting.mvc.validator.annotation.ValidateParam;
import com.haoting.sys.common.PermissionManager;
import com.haoting.sys.dto.SysRoleSearchDTO;
import com.haoting.sys.model.SysApp;
import com.haoting.sys.model.SysRole;
import com.haoting.sys.model.SysRolePermission;
import com.haoting.sys.service.SysAppService;
import com.haoting.sys.service.SysRolePermissionService;
import com.haoting.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

import static com.haoting.mvc.validator.Validator.NOT_BLANK;

/**
 * @Author: haoting.wang
 * @Date: Created in 下午12:00 2017/8/21
 */
@Controller
@RequestMapping("/admin/role")
public class SysRoleController {

    @Autowired
    private SysRoleService           sysRoleService;

    @Autowired
    private SysAppService            sysAppService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private PermissionManager permissionManager;

    @RequestMapping(method = RequestMethod.GET)
    public String execute(Model model) {
        model.addAttribute("appList", getAppList());
        return "/admin/role";
    }

    @RequestMapping("/edit")
    public String edit(Long id, Model model) {
        SysRole sysRole;
        if (id == null) {
            sysRole = new SysRole();
        } else {
            sysRole = sysRoleService.selectByPrimaryKey(id);
        }
        model.addAttribute("role", sysRole);
        model.addAttribute("appList", getAppList());
        return "/admin/roleEdit";
    }

    private List<SysApp> getAppList() {
        return sysAppService.findAll();
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result<String> save(Long id, @ValidateParam(value = { NOT_BLANK }, name = "角色名称") String name,
                               @ValidateParam(value = { NOT_BLANK }, name = "应用") Long appId, String description,
                               Integer sort, @ValidateParam(value = { NOT_BLANK }, name = "是否启用") Boolean isEnable) {

        SysRole sysRole = new SysRole();
        if (id == null) {
            sysRole.setGmtCreatedTime(new Date());
        } else {
            sysRole = sysRoleService.selectByPrimaryKey(id);
        }

        sysRole.setName(name);
        sysRole.setAppId(appId);
        sysRole.setIsEnable(isEnable);
        sysRole.setDescription(description);
        sysRole.setSort(sort);

        sysRoleService.save(sysRole);

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
    public Result<String> delete(@ValidateParam(value = { NOT_BLANK }, name = "ids") Long[] ids,Long appId) {

        if (ids == null) {
            return Result.valueOfErrorMsg("请选择需要删除的资源");
        }

        sysRoleService.deleteBatch(ids,appId);

        return Result.valueOfSuccess("删除成功");
    }

    /**
     * 分页查询
     *
     * @param sysRoleSearchDTO
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result<Pagination<SysRole>> listPage(SysRoleSearchDTO sysRoleSearchDTO) {

        Pagination<SysRole> pagination = sysRoleService.searchForPage(sysRoleSearchDTO);

        return Result.valueOfSuccess(pagination);
    }

    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    @ResponseBody
    public Result updateEnable(@ValidateParam({ NOT_BLANK }) Long[] ids,
                               @ValidateParam({ NOT_BLANK }) Boolean isEnable) {

        sysRoleService.updateEnable(isEnable, ids);

        if (isEnable) {
            return Result.valueOfSuccess("启用成功");
        }
        return Result.valueOfSuccess("禁用成功");

    }

    /**
     * 获取角色已经拥有的权限
     * 
     * @param roleId
     * @return
     */
    @RequestMapping("/allocate")
    @ResponseBody
    public Result<List<SysRolePermission>> allocate(@ValidateParam({ NOT_BLANK }) Long roleId) {

        List<SysRolePermission> permissionList = sysRolePermissionService.findByRoleId(roleId);

        return Result.valueOfSuccess(permissionList);
    }

    @RequestMapping("/allocateSave")
    @ResponseBody
    public Result<String> allocateSave(@ValidateParam({ NOT_BLANK }) Long roleId,
                                       @ValidateParam({ NOT_BLANK }) Long[] permissionIds,
                                       @ValidateParam({ NOT_BLANK }) Long appId) {

        List<SysRolePermission> list = Lists.newArrayList();
        for (Long permissionId : permissionIds) {

            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermission.setRoleId(roleId);
            sysRolePermission.setAppId(appId);
            list.add(sysRolePermission);
        }
        sysRolePermissionService.allocateSave(list, roleId,appId);

        return Result.valueOfSuccess("授权成功");
    }
}
