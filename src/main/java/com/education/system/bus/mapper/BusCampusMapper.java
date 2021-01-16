package com.education.system.bus.mapper;

import com.education.system.bus.entity.BusCampus;

public interface BusCampusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusCampus record);

    int insertSelective(BusCampus record);

    BusCampus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusCampus record);

    int updateByPrimaryKey(BusCampus record);
}