package com.education.system.sys.service;



import com.education.system.common.entity.PageInfo;
import com.education.system.sys.entity.SysUser;
import com.education.system.sys.vo.SysUserVo;


public interface ISysUserService {
	//根据用户id进行查询
	SysUser findSysUserById(Integer id);
	//根据用户名进行查询
	SysUser findSysUserByLoginName(String username);
	//根据用户id进行查询视图对象
	SysUserVo findSysUserVoById(Integer id);
	/**
	 * 
	 * @param sysUserVo 将视图对象的数据传入，传入后在进行数据的分离，将需要更新的数据的内容剥离
	 * 在进行对应表格的数据更新，数据库的创建数据表的原则是单一原则。所以数据库的内容就是分表。
	 * @return 更新的结果
	 */
	int updatePersonFile(SysUserVo sysUserVo);
	/**
	 * Acording to userId to update the user message 
	 * @param sysUserId
	 * @param pwd
	 * @return update result ,if update's option is success return int date which
	 * is more than zero.
	 */
	int updateSysUserPwdById(Integer sysUserId, String pwd);
	/**
	 * 分页查询所有机构用户
	 * @param pageInfo
	 */
	void findDataGrid(PageInfo pageInfo);
	
	/**
	 * 插入数据
	 * @param sysUserVo
	 */
	void addSysUser(SysUserVo sysUserVo);
	
	/**
	 * 根据用户ID进行删除数据
	 * @param id
	 */
	void deleteSysUserById(Integer id);
	/**
	 * 更新用户表的数据,以及其关联的中间表的数据
	 * @param sysUserVo
	 */
	void updateSysUser(SysUserVo sysUserVo);
	
	
	
	
}
