package com.education.system.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.education.system.sys.entity.SysUserRole;
@Repository
public interface SysUserRoleMapper {
	/**
	 * 根据用户id查询角色
	 * @param userId
	 * @return
	 */
	List<Integer> findRoleIdListByUserId(Integer userId);
	
	
	/**
	 * 根据用户id查询
	 * @param userId
	 * @return
	 */
	List<Integer> findUserRoleIdByUserId(Integer userId);

	/**
	 * 批量删除
	 * 
	 * @param userRolesIdList
	 * @return
	 */
	int batchDelete(List userRolesIdList);
	
	/**
	 * 批量插入
	 * 
	 * @param userRolesList
	 * @return
	 */
	int batchInsert(List userRolesList);
}