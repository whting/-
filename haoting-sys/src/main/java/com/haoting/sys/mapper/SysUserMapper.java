package com.haoting.sys.mapper;

import com.haoting.sys.model.SysUser;
import com.haoting.sys.dto.SysUserSearchDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Author: smili
*/
public interface SysUserMapper{

    int deleteByPrimaryKey(Long id);

    int deleteBatch(Long[] ids);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    //TODO修改Mapper
    List<SysUser> selectPage(SysUserSearchDTO sysUserSearchDTO);


    void updateEnable(@Param("isEnable") Boolean isEnable,@Param("ids") Long[] ids);

    void resetPassword(@Param("ids") Long[] ids,@Param("password") String password);

    SysUser findByAccount(String account);

}
