package com.haoting.sys.service;

import com.haoting.sys.model.SysRolePermission;

import java.util.List;

/**
 * @Author: haoting.wang
 * @Date: Created in 下午5:07 2017/8/21
 */
public interface SysRolePermissionService {

    List<SysRolePermission> findByRoleId(Long roleId);

    void allocateSave(List<SysRolePermission> list, Long roleId,Long appId);

    void deleteByRoleIds(Long[] ids);

    void deleteByPermissionId(Long id);

    void deleteByAppIds(Long[] ids);
}
