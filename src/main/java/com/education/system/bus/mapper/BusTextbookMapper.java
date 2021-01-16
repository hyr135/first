package com.education.system.bus.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.education.system.bus.entity.BusTextbook;
import com.education.system.common.entity.PageInfo;

@Repository
public interface BusTextbookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusTextbook record);

    int insertSelective(BusTextbook record);

    BusTextbook selectByPrimaryKey(Integer id);
    /**
     * 根据前端传过来的数据进行数据的对数据的显示查询,可能要涉及到分页查询.
     * 使用list进行接收,
     * @return
     */
    @SuppressWarnings("rawtypes")
	List findData(PageInfo pageInfo);	
    
    /**
     * 查询总数,进行分页的时候使用.
     * @param pageInfo
     * @return
     */
    int findDataCount(PageInfo pageInfo);
    
    
    

    int updateByPrimaryKeySelective(BusTextbook record);

    int updateByPrimaryKeyWithBLOBs(BusTextbook record);

    int updateByPrimaryKey(BusTextbook record);
    
    
}