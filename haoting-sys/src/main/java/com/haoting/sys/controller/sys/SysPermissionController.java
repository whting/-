package com.haoting.sys.controller.sys;

import com.haoting.mvc.model.Result;
import com.haoting.mvc.validator.annotation.ValidateParam;
import com.haoting.sys.dto.SysPermissionSearchDTO;
import com.haoting.sys.model.SysApp;
import com.haoting.sys.model.SysPermission;
import com.haoting.sys.service.SysAppService;
import com.haoting.sys.service.SysPermissionService;
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
 * @Date: Created in 下午3:27 2017/8/21
 */
@Controller
@RequestMapping("/admin/permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysAppService        sysAppService;

    @RequestMapping(method = RequestMethod.GET)
    public String execute(Model model) {
        model.addAttribute("appList", getAppList());
        return "/admin/permission";
    }

    @RequestMapping("/nodes")
    @ResponseBody
    public List<SysPermission> nodes(SysPermissionSearchDTO sysPermissionSearchDTO) {

        List<SysPermission> list = sysPermissionService.search(sysPermissionSearchDTO);
        SysPermission permission = new SysPermission();
        permission.setId(null);
        permission.setParentId(-1L);
        permission.setName("根节点");
        permission.setIsEnable(true);
        permission.setAppId(sysPermissionSearchDTO.getAppId());
        list.add(0, permission);
        return list;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> delete(@ValidateParam({ NOT_BLANK }) Long id, @ValidateParam({ NOT_BLANK }) Long appId) {

        sysPermissionService.delete(id,appId);

        return Result.valueOfSuccess("删除成功");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(Long id, @ValidateParam({ NOT_BLANK }) Long appId, Long parentId, String icon,
                       @ValidateParam({ NOT_BLANK }) String name, String url,
                       @ValidateParam({ NOT_BLANK }) Integer sort, @ValidateParam({ NOT_BLANK }) Boolean isMenu,
                       @ValidateParam({ NOT_BLANK }) Boolean isEnable) {
        SysPermission sysPermission;
        if (id == null) {
            sysPermission = new SysPermission();
            sysPermission.setGmtCreatedTime(new Date());
        } else {
            sysPermission = sysPermissionService.selectByPrimaryKey(id);
        }
        sysPermission.setAppId(appId);
        sysPermission.setParentId(parentId);
        sysPermission.setIcon(icon);
        sysPermission.setName(name);
        sysPermission.setUrl(url);
        sysPermission.setSort(sort);
        sysPermission.setIsMenu(isMenu);
        sysPermission.setIsEnable(isEnable);
        sysPermissionService.save(sysPermission);

        return Result.valueOfSuccess("保存成功");
    }

    private List<SysApp> getAppList() {
        return sysAppService.findAll();
    }
}
