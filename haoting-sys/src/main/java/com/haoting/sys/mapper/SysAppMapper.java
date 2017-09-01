package com.haoting.sys.mapper;

import com.haoting.sys.dto.SysAppSearchDTO;
import com.haoting.sys.model.SysApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: haoting.wang
 * @Date: Created in 下午3:11 2017/8/17
 */
public interface SysAppMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysApp record);

    int insertSelective(SysApp record);

    SysApp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysApp record);

    int updateByPrimaryKey(SysApp record);

    //TODO修改Mapper
    List<SysApp> selectPage(SysAppSearchDTO sysAppSearchDTO);


    SysApp findByCode(String code);

    void deleteBatch(Long[] ids);

    void updateEnable(@Param("isEnable") Boolean isEnable, @Param("ids") Long[] ids);

    List<SysApp> findAll();

    List<SysApp> findByUserId(@Param("isEnable") Boolean isEnable,@Param("userId") Long userId);

}
