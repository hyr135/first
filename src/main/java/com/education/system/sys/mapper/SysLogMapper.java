package com.education.system.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.education.system.common.entity.PageInfo;
import com.education.system.sys.entity.SysLog;
@Repository
public interface SysLogMapper {
	/**
	 * 分页查询
	 * 使用自己动手映射的resultMap将多条结果,放到一个list集合中,将集合的值放给pageInfo然后在返回到页面.
	 * @param pageInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findDataGrid(PageInfo pageInfo);

	/**
	 * 查找数据网格计数,为了分页.
	 * 
	 * @param pageInfo	
	 * @return
	 */
	int findDataGridCount(PageInfo pageInfo);
	
	/**
	 * 添加日志信息
	 * 
	 * @param record
	 * @return
	 */
	int insert(SysLog record);
	
	
	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	SysLog selectById(Integer id);
	
	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	int batchDelete(List ids);
	
	/**
	 * 删除日志计数
	 * 
	 * @param date
	 * @return
	 */
	int delLogCount(String date);
	
	/**
	 * 根据日期删除
	 * 
	 * @param date
	 */
	int delByDate(String date);
	
	
}