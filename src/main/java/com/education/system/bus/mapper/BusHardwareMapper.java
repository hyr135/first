package com.education.system.bus.mapper;

import com.education.system.bus.entity.BusHardware;

public interface BusHardwareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusHardware record);

    int insertSelective(BusHardware record);

    BusHardware selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusHardware record);

    int updateByPrimaryKey(BusHardware record);
}