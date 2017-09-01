package com.haoting.sys.service;

import com.haoting.mvc.model.Result;
import com.haoting.sys.dto.SysUserSearchDTO;
import com.haoting.sys.model.SysUser;

import com.haoting.mvc.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
* @Author: smili
*/
public interface SysUserService {

    void add(SysUser sysUser);

    void delete(Long id);

    void deleteBatch(Long[] ids);

    void update(SysUser sysUser);

    void updateSelective(SysUser sysUser);

    SysUser selectByPrimaryKey(Long id);

    Pagination<SysUser> searchForPage(SysUserSearchDTO sysUserSearchDTO);

    void save(SysUser sysUser);

    void updateEnable(Boolean isEnable, Long[] ids);

    void resetPassword(Long[] ids);

    Result<SysUser> login(String ipAddr, String appCode, String account, String encrypt);
}
