package com.haoting.sys.service;

import com.haoting.sys.model.SysRole;
import com.haoting.sys.model.SysUserRole;

import java.util.List;

/**
 * @Author: haoting.wang
 * @Date: Created in 上午9:38 2017/8/22
 */
public interface SysUserRoleService {
    List<SysUserRole> findByUserId(Long userId);

    void allocateSave(Long userId, List<SysUserRole> list,Long appId);

    void deleteByRoleIds(Long[] ids);

    void deleteByUserIds(Long[] ids);

    void deleteByAppIds(Long[] ids);
}
