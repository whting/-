package com.haoting.sys.service;

import com.haoting.sys.model.SysUserApp;

import java.util.List;

/**
 * @Author: haoting.wang
 * @Date: Created in 上午10:55 2017/8/21
 */
public interface SysUserAppService {

    List<SysUserApp> findUserAppByUserId(Long userId);

    void allocateSave(Long userId, List<SysUserApp> list);

    List<SysUserApp> findByUserId(Boolean value, Long userId);

    void deleteByUserIds(Long[] ids);

    void deleteByAppIds(Long[] ids);
}
