package com.education.system.bus.mapper;

import com.education.system.bus.entity.BusTeacher;

public interface BusTeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusTeacher record);

    int insertSelective(BusTeacher record);

    BusTeacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusTeacher record);

    int updateByPrimaryKey(BusTeacher record);
}