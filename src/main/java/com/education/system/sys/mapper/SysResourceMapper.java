package com.education.system.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.education.system.sys.entity.SysResource;
@Repository
public interface SysResourceMapper {
	/**
	 * 查询一级资源，条件查询是使用的是上一级资源名称为空，这个需要根据数据库的设计来使用
	 * @param resourceMenu
	 * @return
	 */
	List<SysResource> findResourceAllByTypeAndPidNull(Integer resourceMenu);
	
	
	/**
	 * 查询菜单资源
	 *
	 * @param resourceType
	 * @param pid
	 * @return 
	 */
	List<SysResource> findResourceAllByTypeAndPid(@Param("resourceType") Integer resourceType, @Param("pid") Integer pid);
	/**
	 * 通过查询所有的资源集合进行查找资源
	 * @return 资源集合
	 */
	List<SysResource> findResourceAll();

	
	/**
	 * 
	 * @param sysResource 实体类封装了前端传过来的参数，这些参数使用ajax传过来并且已经封装在实体类中，里面存放着全部的信息
	 * 
	 * 
	 * @return 如果对数据库操作成功会返回影响的行数，是一个整形的数据。
	 */
	int insert(SysResource sysResource);
	/**
	 * 根据资源的参数进行指定资源的删除。
	 * @param id
	 * @return 整形数据反映了操作数据的结果
	 */
	int deleteResourceById(Integer id);
	/**
	 * 根据资源ID进行数据库的资源查询,这里书写的代码就相当是接口,xml文件就相当于实现类.
	 * 
	 * @param id 根据id查询数据库
	 * @return 返回一个实体类,进行数据的存放;;
	 */
	
	SysResource findResourceById(Integer id);
	/**
	 * 资源的更新,及修改
	 * @param resource 前段的数据
	 * @return 操作数据库的结果
	 */
	int updateResource(SysResource resource);
	
}