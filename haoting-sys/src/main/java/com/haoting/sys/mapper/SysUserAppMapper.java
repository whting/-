package com.haoting.sys.mapper;

import com.haoting.sys.model.SysUserApp;

import java.util.List;

/**
 * @Author: haoting.wang
 * @Date: Created in 上午10:58 2017/8/21
 */
public interface SysUserAppMapper {

    List<SysUserApp> findUserAppByUserId(Long userId);

    void deleteByUserId(Long userId);

    void insert(SysUserApp sysUserApp);

    void deleteByUserIds(Long[] ids);

    void deleteByAppIds(Long[] ids);
}
