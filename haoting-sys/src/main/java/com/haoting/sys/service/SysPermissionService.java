package com.haoting.sys.service;

import com.haoting.rpc.RpcPermission;
import com.haoting.sys.dto.SysPermissionSearchDTO;
import com.haoting.sys.model.SysPermission;

import com.haoting.mvc.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
* @Author: smili
*/
public interface SysPermissionService {


    void delete(Long id,Long appId);

    void update(SysPermission sysPermission);

    void updateSelective(SysPermission sysPermission);

    SysPermission selectByPrimaryKey(Long id);

    Pagination<SysPermission> searchForPage(SysPermissionSearchDTO sysPermissionSearchDTO);

    List<SysPermission> search(SysPermissionSearchDTO sysPermissionSearchDTO);

    void save(SysPermission sysPermission);

    List<RpcPermission> findListById(Long appId, Long userId);

    void deleteByAppIds(Long[] ids);
}
