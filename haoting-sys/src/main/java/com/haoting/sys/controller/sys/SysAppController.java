package com.haoting.sys.controller.sys;

import com.haoting.mvc.controller.BaseController;
import com.haoting.mvc.model.Result;
import com.haoting.mvc.pagination.Pagination;
import com.haoting.mvc.validator.annotation.ValidateParam;
import com.haoting.sys.dto.SysAppSearchDTO;
import com.haoting.sys.model.SysApp;
import com.haoting.sys.service.SysAppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

import static com.haoting.mvc.validator.Validator.NOT_BLANK;

/**
 * @author haoting.wang
 */
@Controller
@RequestMapping("/admin/app")
public class SysAppController extends BaseController {

    @Resource
    private SysAppService sysAppService;

    @RequestMapping(method = RequestMethod.GET)
    public String execute() {
        return "/admin/app";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result<Pagination> list(SysAppSearchDTO appSearchDTO) {

        Pagination<SysApp> pagination = sysAppService.findPagination(appSearchDTO);

        return Result.valueOfSuccess(pagination);
    }

    @RequestMapping("/edit")
    public String edit(Long id, Model model) {
        SysApp app;
        if (id == null) {
            app = new SysApp();
        } else {
            app = sysAppService.selectByPrimaryKey(id);
        }
        model.addAttribute("app", app);
        return "/admin/appEdit";
    }

    @RequestMapping(value = "/validateCode", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> validateCode(@ValidateParam(value = { NOT_BLANK }, name = "应用编码") String code) {
        SysApp db = sysAppService.findByCode(code);
        if (null != db) {
            return Result.valueOfError("应用编码已存在");
        }
        return Result.valueOfSuccess();
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result<String> save(Long id, @ValidateParam(value = { NOT_BLANK }, name = "应用名称") String name,
                               @ValidateParam(value = { NOT_BLANK }, name = "应用编码") String code,
                               @ValidateParam(value = { NOT_BLANK }, name = "启用状态") Boolean isEnable,
                               @ValidateParam(value = { NOT_BLANK }, name = "排序") Integer sort) {

        SysApp app = new SysApp();
        if (id == null) {
            app.setGmtCreatedTime(new Date());
        } else {
            app = sysAppService.selectByPrimaryKey(id);
        }
        app.setName(name);
        app.setCode(code);
        app.setIsEnable(isEnable);
        app.setSort(sort);
        sysAppService.save(app);

        return Result.valueOfSuccess();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result<String> delete(@ValidateParam(value = { NOT_BLANK }, name = "ids") Long[] ids) {

        if (ids == null) {
            return Result.valueOfError("请选择需要删除的行");
        }

        sysAppService.deleteBatch(ids);

        return Result.valueOfSuccess("删除成功");

    }

    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    @ResponseBody
    public Result updateEnable(@ValidateParam({ NOT_BLANK }) Long[] ids,
                               @ValidateParam({ NOT_BLANK }) Boolean isEnable) {

        sysAppService.updateEnable(isEnable, ids);
        if (isEnable) {
            return Result.valueOfSuccess("启用成功");
        }
        return Result.valueOfSuccess("禁用成功");
    }

}
