package com.haoting.sys.mapper;

import com.haoting.sys.model.SysRole;
import com.haoting.sys.dto.SysRoleSearchDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
* @Author: smili
*/
public interface SysRoleMapper{

    int deleteByPrimaryKey(Long id);

    int deleteBatch(Long[] ids);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    //TODO修改Mapper
    List<SysRole> selectPage(SysRoleSearchDTO sysRoleSearchDTO);


    void updateEnable(@Param("isEnable") Boolean isEnable,@Param("ids") Long[] ids);

    List<SysRole> findByAppId(@Param("isEnable") Boolean isEnable,@Param("appId") Long appId);

    void deleteByAppIds(Long[] ids);
}
