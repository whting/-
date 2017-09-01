package com.haoting.sys.service.impl;

import com.haoting.sys.common.PermissionManager;
import com.haoting.sys.mapper.SysRolePermissionMapper;
import com.haoting.sys.model.SysRolePermission;
import com.haoting.sys.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: haoting.wang
 * @Date: Created in 下午5:09 2017/8/21
 */
@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionService {


    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private PermissionManager permissionManager;
    @Override
    public List<SysRolePermission> findByRoleId(Long roleId) {


       return sysRolePermissionMapper.findByRoleId(roleId);
    }

    @Override
    public void allocateSave(List<SysRolePermission> list, Long roleId,Long appId) {

        sysRolePermissionMapper.deleteByRoleId(roleId);

        for(SysRolePermission sysRolePermission : list){
            sysRolePermissionMapper.insert(sysRolePermission);
        }
        permissionManager.deleteAppUserPerm(appId);

    }

    @Override
    public void deleteByRoleIds(Long[] ids) {
        sysRolePermissionMapper.deleteByRoleIds(ids);
    }

    @Override
    public void deleteByPermissionId(Long id) {

        sysRolePermissionMapper.deleteByPermissionId(id);
    }

    @Override
    public void deleteByAppIds(Long[] ids) {
        sysRolePermissionMapper.deleteByAppIds(ids);
    }
}
