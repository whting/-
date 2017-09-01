package com.haoting.sys.service.impl;

import com.haoting.sys.mapper.SysUserAppMapper;
import com.haoting.sys.model.SysUserApp;
import com.haoting.sys.service.SysUserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: haoting.wang
 * @Date: Created in 上午10:57 2017/8/21
 */
@Service
public class SysUserAppServiceImpl implements SysUserAppService {


    @Autowired
    private SysUserAppMapper sysUserAppMapper;

    @Override
    public List<SysUserApp> findUserAppByUserId(Long userId) {
        return sysUserAppMapper.findUserAppByUserId(userId);
    }

    @Override
    public void allocateSave(Long userId, List<SysUserApp> list) {

        sysUserAppMapper.deleteByUserId(userId);

        for(SysUserApp sysUserApp : list){
            sysUserAppMapper.insert(sysUserApp);
        }
    }

    @Override
    public List<SysUserApp> findByUserId(Boolean value, Long userId) {

       return sysUserAppMapper.findUserAppByUserId(userId);
    }

    @Override
    public void deleteByUserIds(Long[] ids) {
        sysUserAppMapper.deleteByUserIds(ids);
    }

    @Override
    public void deleteByAppIds(Long[] ids) {
        sysUserAppMapper.deleteByAppIds(ids);
    }

}
