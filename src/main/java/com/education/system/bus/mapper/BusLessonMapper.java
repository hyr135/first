package com.education.system.bus.mapper;

import com.education.system.bus.entity.BusLesson;

public interface BusLessonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusLesson record);

    int insertSelective(BusLesson record);

    BusLesson selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusLesson record);

    int updateByPrimaryKey(BusLesson record);
}