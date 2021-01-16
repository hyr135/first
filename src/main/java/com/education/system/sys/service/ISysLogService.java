package com.education.system.sys.service;

import java.util.List;

import com.education.system.common.entity.PageInfo;
import com.education.system.sys.entity.SysLog;
/**
 * logService 的服务层
 * @author Dell
 *
 */
public interface ISysLogService {
	/**
	 * 查询日志表的数据,在前端以数据网格的形式显示.
	 * @param pageInfo
	 */
	void findDataGrid(PageInfo pageInfo);
	/**
	 * 添加日志
	 * @param sysLog
	 */
	void insertLog(SysLog sysLog);
	
	/**
	 * 根据id来查询对应id的日志记录信息.
	 * 使用的是sysLog来作为实体类进行存放数据,查询回来的数据由mybatis进行自动映射.但是一般是数据库表与实体类的字段 不完全匹配
	 * 所以得使用resultMap去手动匹配.
	 * @param id
	 * @return
	 */
	SysLog selectById(Integer id);
	/**
	 * 批量删除
	 * @param ids
	 */
	void batchDelete(List<String> ids);
	/**
	 * 删除数据通过时间.
	 * @param date 日期
	 * @return 返回删除的数
	 */
	int delByDate(String date);
}
