package com.haoting.sys.service.impl;

import com.haoting.mvc.config.ConfigUtils;
import com.haoting.mvc.pagination.PageParameter;
import com.haoting.mvc.pagination.Pagination;
import com.haoting.mvc.provider.IdProvider;
import com.haoting.rpc.RpcPermission;
import com.haoting.sys.common.PermissionManager;
import com.haoting.sys.dto.SysPermissionSearchDTO;
import com.haoting.sys.mapper.SysPermissionMapper;
import com.haoting.sys.model.SysPermission;
import com.haoting.sys.service.SysPermissionService;
import com.haoting.sys.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: smili
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;


    @Autowired
    private PermissionManager permissionManager;


    @Override
    public void delete(Long id,Long appId) {

        sysRolePermissionService.deleteByPermissionId(id);

        sysPermissionMapper.deleteByPrimaryKey(id);

        permissionManager.deleteAppUserPerm(appId);
        permissionManager.deleteAppPermission(appId);

    }

    @Override
    public void update(SysPermission sysPermission) {

        // TODO设置gmtUpdate等
        sysPermissionMapper.updateByPrimaryKeySelective(sysPermission);

    }

    @Override
    public void updateSelective(SysPermission sysPermission) {

        // TODO设置gmtUpdate等
        sysPermissionMapper.updateByPrimaryKey(sysPermission);

    }

    @Override
    public SysPermission selectByPrimaryKey(Long id) {

        return sysPermissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public Pagination<SysPermission> searchForPage(SysPermissionSearchDTO sysPermissionSearchDTO) {

        Pagination<SysPermission> pagination = new Pagination<>();

        PageParameter pageParameter = new PageParameter();
        pageParameter.setCurrentPage(sysPermissionSearchDTO.getPageNo());
        pageParameter.setPageSize(sysPermissionSearchDTO.getPageSize());
        sysPermissionSearchDTO.setPage(pageParameter);

        List<SysPermission> sysPermissionList = sysPermissionMapper.selectPage(sysPermissionSearchDTO);

        pagination.setPageIndex(sysPermissionSearchDTO.getPageNo());
        pagination.setPageSize(sysPermissionSearchDTO.getPageSize());
        pagination.setList(sysPermissionList);
        pagination.setTotalCount(pageParameter.getTotalCount());
        pagination.setTotalPage(pageParameter.getTotalPage());

        return pagination;
    }

    @Override
    public List<SysPermission> search(SysPermissionSearchDTO sysPermissionSearchDTO) {

        return sysPermissionMapper.search(sysPermissionSearchDTO);
    }

    @Override
    public void save(SysPermission sysPermission) {

        if (sysPermission.getId() == null) {
            sysPermission.setId(IdProvider.getUid());
            sysPermission.setGmtModifiedTime(new Date());
            sysPermissionMapper.insert(sysPermission);

            Long appId = sysPermission.getAppId();
            permissionManager.deleteAppPermission(appId);
        } else {
            sysPermission.setGmtModifiedTime(new Date());
            sysPermissionMapper.updateByPrimaryKey(sysPermission);

            Long appId = sysPermission.getAppId();
            permissionManager.deleteAppUserPerm(appId);
            permissionManager.deleteAppPermission(appId);
        }

    }

    @Override
    public List<RpcPermission> findListById(Long appId, Long userId) {

        return sysPermissionMapper.findListById(appId,userId);
    }

    @Override
    public void deleteByAppIds(Long[] ids) {

        sysPermissionMapper.deleteByAppIds(ids);

    }

}
