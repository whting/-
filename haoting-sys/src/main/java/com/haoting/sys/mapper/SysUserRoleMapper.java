package com.haoting.sys.mapper;

import com.haoting.sys.model.SysRole;
import com.haoting.sys.model.SysUserRole;

import java.util.List;

/**
 * @Author: haoting.wang
 * @Date: Created in 上午9:57 2017/8/22
 */
public interface SysUserRoleMapper {
    List<SysUserRole> findUserRoleByUserId(Long userId);

    void deleteByUserId(Long userId);

    void insert(SysUserRole sysUserRole);

    void deleteByRoleIds(Long[] ids);

    void deleteByUserIds(Long[] ids);

    void deleteByAppIds(Long[] ids);
}
