package com.education.system.bus.mapper;

import com.education.system.bus.entity.BusLevel;

public interface BusLevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusLevel record);

    int insertSelective(BusLevel record);

    BusLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusLevel record);

    int updateByPrimaryKey(BusLevel record);
}