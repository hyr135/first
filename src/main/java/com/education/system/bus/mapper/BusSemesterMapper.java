package com.education.system.bus.mapper;

import com.education.system.bus.entity.BusSemester;

public interface BusSemesterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusSemester record);

    int insertSelective(BusSemester record);

    BusSemester selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusSemester record);

    int updateByPrimaryKey(BusSemester record);
}