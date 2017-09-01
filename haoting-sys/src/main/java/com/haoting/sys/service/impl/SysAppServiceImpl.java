package com.haoting.sys.service.impl;

import com.haoting.mvc.pagination.PageParameter;
import com.haoting.mvc.pagination.Pagination;
import com.haoting.sys.common.PermissionManager;
import com.haoting.sys.dto.SysAppSearchDTO;
import com.haoting.sys.mapper.SysAppMapper;
import com.haoting.sys.model.SysApp;
import com.haoting.sys.model.SysPermission;
import com.haoting.sys.model.SysUserApp;
import com.haoting.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("appService")
public class SysAppServiceImpl implements SysAppService {

    @Autowired
    private SysAppMapper sysAppMapper;

    @Autowired
    private SysUserAppService sysUserAppService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private PermissionManager permissionManager;

    @Override
    public Pagination<SysApp> findPagination(SysAppSearchDTO appSearchDTO) {

        Pagination<SysApp> pagination = new Pagination<>();

        PageParameter pageParameter = new PageParameter();
        pageParameter.setCurrentPage(appSearchDTO.getPageNo());
        pageParameter.setPageSize(appSearchDTO.getPageSize());
        appSearchDTO.setPage(pageParameter);

        List<SysApp> sysAppList = sysAppMapper.selectPage(appSearchDTO);

        pagination.setPageIndex(appSearchDTO.getPageNo());
        pagination.setPageSize(appSearchDTO.getPageSize());
        pagination.setList(sysAppList);
        pagination.setTotalCount(pageParameter.getTotalCount());
        pagination.setTotalPage(pageParameter.getTotalPage());

        return pagination;
    }

    @Override
    public void save(SysApp app) {

        if (app.getId() == null) {
            app.setGmtModifiedTime(new Date());
            sysAppMapper.insert(app);
        } else {
            sysAppMapper.updateByPrimaryKey(app);
        }

    }

    @Override
    public SysApp findByCode(String code) {

        return sysAppMapper.findByCode(code);
    }

    @Override
    public SysApp selectByPrimaryKey(Long id) {

        return sysAppMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {

        sysUserAppService.deleteByAppIds(ids);
        sysUserRoleService.deleteByAppIds(ids);
        sysRolePermissionService.deleteByAppIds(ids);
        sysRoleService.deleteByAppIds(ids);
        sysPermissionService.deleteByAppIds(ids);
        sysAppMapper.deleteBatch(ids);

        for(Long id :ids){
            permissionManager.deleteAppUserPerm(id);
            permissionManager.deleteAppPermission(id);
        }
    }

    @Override
    public void updateEnable(Boolean isEnable, Long[] ids) {

        sysAppMapper.updateEnable(isEnable, ids);
    }

    @Override
    public List<SysApp> findAll() {

        return sysAppMapper.findAll();
    }

    @Override
    public List<SysApp> findByUserId(Boolean isEnable, Long userId) {

        return sysAppMapper.findByUserId(isEnable,userId);
    }

}
