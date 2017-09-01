package com.haoting.sys.service.impl;

import com.haoting.mvc.pagination.PageParameter;
import com.haoting.mvc.pagination.Pagination;
import com.haoting.mvc.provider.IdProvider;
import com.haoting.sys.common.PermissionManager;
import com.haoting.sys.dto.SysRoleSearchDTO;
import com.haoting.sys.mapper.SysRoleMapper;
import com.haoting.sys.model.SysRole;
import com.haoting.sys.service.SysRolePermissionService;
import com.haoting.sys.service.SysRoleService;
import com.haoting.sys.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: smili
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private PermissionManager permissionManager;


    public void deleteBatch(Long[] ids,Long appId) {

        sysUserRoleService.deleteByRoleIds(ids);
        sysRolePermissionService.deleteByRoleIds(ids);
        sysRoleMapper.deleteBatch(ids);

        permissionManager.deleteAppUserPerm(appId);
    }

    @Override
    public void update(SysRole sysRole) {

        // TODO设置gmtUpdate等
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);

    }

    @Override
    public void updateSelective(SysRole sysRole) {

        // TODO设置gmtUpdate等
        sysRoleMapper.updateByPrimaryKey(sysRole);

    }

    @Override
    public SysRole selectByPrimaryKey(Long id) {

        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public Pagination<SysRole> searchForPage(SysRoleSearchDTO sysRoleSearchDTO) {

        Pagination<SysRole> pagination = new Pagination<>();

        PageParameter pageParameter = new PageParameter();
        pageParameter.setCurrentPage(sysRoleSearchDTO.getPageNo());
        pageParameter.setPageSize(sysRoleSearchDTO.getPageSize());
        sysRoleSearchDTO.setPage(pageParameter);

        List<SysRole> sysRoleList = sysRoleMapper.selectPage(sysRoleSearchDTO);

        pagination.setPageIndex(sysRoleSearchDTO.getPageNo());
        pagination.setPageSize(sysRoleSearchDTO.getPageSize());
        pagination.setList(sysRoleList);
        pagination.setTotalCount(pageParameter.getTotalCount());
        pagination.setTotalPage(pageParameter.getTotalPage());

        return pagination;
    }

    @Override
    public void save(SysRole sysRole) {
        if (sysRole.getId() == null) {
            sysRole.setId(IdProvider.getUid());
            sysRole.setGmtModifiedTime(new Date());
            sysRoleMapper.insert(sysRole);
        } else {
            sysRole.setGmtModifiedTime(new Date());
            sysRoleMapper.updateByPrimaryKey(sysRole);

            permissionManager.deleteAppUserPerm(sysRole.getAppId());
        }
    }

    @Override
    public void updateEnable(Boolean isEnable, Long[] ids) {
        sysRoleMapper.updateEnable(isEnable, ids);
    }

    @Override
    public List<SysRole> findByAppId(Boolean isEnable, Long appId) {

       return sysRoleMapper.findByAppId(isEnable,appId);

    }

    @Override
    public void deleteByAppIds(Long[] ids) {
        sysRoleMapper.deleteByAppIds(ids);
    }

}
