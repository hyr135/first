package com.education.system.bus.mapper;

import com.education.system.bus.entity.BusStudent;

public interface BusStudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusStudent record);

    int insertSelective(BusStudent record);

    BusStudent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusStudent record);

    int updateByPrimaryKey(BusStudent record);
}