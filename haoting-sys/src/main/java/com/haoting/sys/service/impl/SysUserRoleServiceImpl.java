package com.haoting.sys.service.impl;

import com.haoting.sys.common.PermissionManager;
import com.haoting.sys.mapper.SysUserRoleMapper;
import com.haoting.sys.model.SysRole;
import com.haoting.sys.model.SysUserRole;
import com.haoting.sys.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: haoting.wang
 * @Date: Created in 上午9:56 2017/8/22
 */

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private PermissionManager permissionManager;
    @Override
    public List<SysUserRole> findByUserId(Long userId) {
        return sysUserRoleMapper.findUserRoleByUserId(userId);
    }

    @Override
    public void allocateSave(Long userId, List<SysUserRole> list,Long appId) {

        sysUserRoleMapper.deleteByUserId(userId);

        for(SysUserRole sysUserRole : list){
            sysUserRoleMapper.insert(sysUserRole);
        }

        permissionManager.deletePermissionCache(userId,appId);

    }

    @Override
    public void deleteByRoleIds(Long[] ids) {
        sysUserRoleMapper.deleteByRoleIds(ids);
    }

    @Override
    public void deleteByUserIds(Long[] ids) {
        sysUserRoleMapper.deleteByUserIds(ids);
    }

    @Override
    public void deleteByAppIds(Long[] ids) {

        sysUserRoleMapper.deleteByAppIds(ids);
    }
}
