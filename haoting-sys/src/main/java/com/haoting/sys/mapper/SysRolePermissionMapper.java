package com.haoting.sys.mapper;

import com.haoting.sys.model.SysRolePermission;

import java.util.List;

/**
 * @Author: haoting.wang
 * @Date: Created in 下午5:09 2017/8/21
 */
public interface SysRolePermissionMapper {
    List<SysRolePermission> findByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);

    void insert(SysRolePermission sysRolePermission);

    void deleteByRoleIds(Long[] ids);

    void deleteByPermissionId(Long id);

    void deleteByAppIds(Long[] ids);
}
