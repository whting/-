package com.haoting.sys.dto;

import com.haoting.mvc.pagination.PageParam;

/**
 * @Author: haoting.wang
 * @Date: Created in 下午2:56 2017/8/17
 */
public class SysAppSearchDTO extends PageParam {

    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
