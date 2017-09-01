package com.haoting.sys.service;

import com.haoting.sys.dto.SysRoleSearchDTO;
import com.haoting.sys.model.SysRole;

import com.haoting.mvc.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
* @Author: smili
*/
public interface SysRoleService {

    void deleteBatch(Long[] ids,Long appId);

    void update(SysRole sysRole);

    void updateSelective(SysRole sysRole);

    SysRole selectByPrimaryKey(Long id);

    Pagination<SysRole> searchForPage(SysRoleSearchDTO sysRoleSearchDTO);

    void save(SysRole sysRole);

    void updateEnable(Boolean isEnable, Long[] ids);

    List<SysRole> findByAppId(Boolean value, Long appId);

    void deleteByAppIds(Long[] ids);
}
