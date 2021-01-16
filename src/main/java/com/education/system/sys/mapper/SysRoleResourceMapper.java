package com.education.system.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.education.system.sys.entity.SysRoleResource;
@Repository
public interface SysRoleResourceMapper {
	/**
	 * 根据资源ID批量删除资源
	 * @param ids
	 * @return
	 */
	int batchDelete(List ids);
	/**
	 * 插入选择的权限资源在中间表中进行操作
	 * @param roleResource
	 * @return 影响的行数
	 */
	int batchInsert(List<SysRoleResource> roleResource);
}