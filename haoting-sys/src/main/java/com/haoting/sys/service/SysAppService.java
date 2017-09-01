package com.haoting.sys.service;

import com.haoting.mvc.pagination.Pagination;
import com.haoting.sys.dto.SysAppSearchDTO;
import com.haoting.sys.model.SysApp;

import java.util.List;

/**
 * 应用服务接口
 * 
 * @author Joe
 */
public interface SysAppService {

    Pagination<SysApp> findPagination(SysAppSearchDTO appSearchDTO);

    void save(SysApp app);

    SysApp findByCode(String code);

    SysApp selectByPrimaryKey(Long id);

    void deleteBatch(Long[] ids);

    void updateEnable(Boolean isEnable, Long[] ids);

    List<SysApp> findAll();

    List<SysApp> findByUserId(Boolean value, Long userId);
}
