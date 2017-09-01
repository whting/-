package com.haoting.sys.mapper;

import com.haoting.rpc.RpcPermission;
import com.haoting.sys.model.SysPermission;
import com.haoting.sys.dto.SysPermissionSearchDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
* @Author: smili
*/
public interface SysPermissionMapper{

    int deleteByPrimaryKey(Long id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    //TODO修改Mapper
    List<SysPermission> selectPage(SysPermissionSearchDTO sysPermissionSearchDTO);


    List<SysPermission> search(SysPermissionSearchDTO sysPermissionSearchDTO);

    List<RpcPermission> findListById(@Param("appId") Long appId, @Param("userId") Long userId);

    void deleteByAppIds(Long[] ids);
}
