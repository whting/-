package com.haoting.sys.service.impl;

import com.haoting.mvc.config.ConfigUtils;
import com.haoting.mvc.enums.TrueFalseEnum;
import com.haoting.mvc.model.Result;
import com.haoting.mvc.pagination.PageParameter;
import com.haoting.mvc.pagination.Pagination;
import com.haoting.mvc.provider.IdProvider;
import com.haoting.mvc.provider.PasswordProvider;
import com.haoting.sys.dto.SysUserSearchDTO;
import com.haoting.sys.mapper.SysUserMapper;
import com.haoting.sys.model.SysApp;
import com.haoting.sys.model.SysUser;
import com.haoting.sys.service.SysAppService;
import com.haoting.sys.service.SysUserAppService;
import com.haoting.sys.service.SysUserRoleService;
import com.haoting.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author: smili
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysAppService sysAppService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    private SysUserAppService sysUserAppService;

    @Override
    public void add(SysUser sysUser) {

        // TODO设置uuid，gmtCreate等
        sysUserMapper.insertSelective(sysUser);
    }

    @Override
    public void delete(Long id) {

        sysUserMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatch(Long[] ids) {

        sysUserRoleService.deleteByUserIds(ids);
        sysUserAppService.deleteByUserIds(ids);

        sysUserMapper.deleteBatch(ids);

    }

    @Override
    public void update(SysUser sysUser) {

        // TODO设置gmtUpdate等
        sysUserMapper.updateByPrimaryKeySelective(sysUser);

    }

    @Override
    public void updateSelective(SysUser sysUser) {

        // TODO设置gmtUpdate等
        sysUserMapper.updateByPrimaryKey(sysUser);

    }

    @Override
    public SysUser selectByPrimaryKey(Long id) {

        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public Pagination<SysUser> searchForPage(SysUserSearchDTO sysUserSearchDTO) {

        Pagination<SysUser> pagination = new Pagination<>();

        PageParameter pageParameter = new PageParameter();
        pageParameter.setCurrentPage(sysUserSearchDTO.getPageNo());
        pageParameter.setPageSize(sysUserSearchDTO.getPageSize());
        sysUserSearchDTO.setPage(pageParameter);

        List<SysUser> sysUserList = sysUserMapper.selectPage(sysUserSearchDTO);

        pagination.setPageIndex(sysUserSearchDTO.getPageNo());
        pagination.setPageSize(sysUserSearchDTO.getPageSize());
        pagination.setList(sysUserList);
        pagination.setTotalCount(pageParameter.getTotalCount());
        pagination.setTotalPage(pageParameter.getTotalPage());

        return pagination;
    }

    @Override
    public void save(SysUser sysUser) {
        if (sysUser.getId() == null) {
            sysUser.setId(IdProvider.getUid());
            sysUser.setGmtModifiedTime(new Date());
            sysUserMapper.insert(sysUser);
        } else {
            sysUser.setGmtModifiedTime(new Date());
            sysUserMapper.updateByPrimaryKey(sysUser);
        }

    }

    @Override
    public void updateEnable(Boolean isEnable, Long[] ids) {

        sysUserMapper.updateEnable(isEnable, ids);
    }

    @Override
    public void resetPassword(Long[] ids) {
        String password = PasswordProvider.encrypt(ConfigUtils.getProperty("system.init.password"));

        sysUserMapper.resetPassword(ids, password);
    }

    @Override
    public Result<SysUser> login(String ipAddr, String appCode, String account, String password) {

        SysUser sysUser = sysUserMapper.findByAccount(account);
        if (sysUser == null) {
            return Result.valueOfError("登录名不存在");
        }
        if (!sysUser.getPassword().equals(password)) {
            return Result.valueOfError("密码不正确");
        }
        if (TrueFalseEnum.FALSE.getValue().equals(sysUser.getIsEnable())) {
            return Result.valueOfError("已被管理员禁用");
        }

        List<SysApp> list = sysAppService.findByUserId(TrueFalseEnum.TRUE.getValue(), sysUser.getId());
        if (CollectionUtils.isEmpty(list)) {
            return Result.valueOfError("不存在可操作应用");
        }

        boolean isAppExist = false;
        for (SysApp sysApp : list) {
            if (sysApp.getCode().equals(appCode)) {
                isAppExist = true;
                break;
            }
        }
        if (!isAppExist) {
            return Result.valueOfError("没有应用操作权限");
        }

        sysUser.setLastLoginIp(ipAddr);
        sysUser.setLastLoginTime(new Date());

        sysUserMapper.updateByPrimaryKeySelective(sysUser);

        return Result.valueOfSuccess(sysUser);

    }

}
