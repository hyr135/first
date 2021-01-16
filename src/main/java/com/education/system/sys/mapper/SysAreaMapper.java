package com.education.system.sys.mapper;

import org.springframework.stereotype.Repository;

import com.education.system.sys.entity.SysArea;
@Repository
public interface SysAreaMapper {
    int insert(SysArea record);

    int insertSelective(SysArea record);
}