package com.education.system.bus.mapper;

import com.education.system.bus.entity.BusClass;

public interface BusClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusClass record);

    int insertSelective(BusClass record);

    BusClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusClass record);

    int updateByPrimaryKey(BusClass record);
}