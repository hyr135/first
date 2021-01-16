package com.education.system.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.education.system.common.entity.PageInfo;
import com.education.system.common.entity.Tree;
import com.education.system.sys.entity.SysRole;

public interface ISysRoleService {
	/**
	 * 通过角色id进行角色资源的查询
	 * @param roleId 角色id
	 * @return 返回值是一个list集合是一个有序集合，他的泛型是一个map
	 */
	List<Map<Integer, String>> findRoleResourceListByRoleId(Integer roleId);
	
	/**
	 * 通过用户id查询到角色id
	 * @param userId
	 * @return
	 */
	List<Integer> findRoleIdListByUserId(Integer userId);
	/**
	 * 
	 * @param pageInfo
	 */
	 void findDataGrid(PageInfo pageInfo); 
	 /**
	     *  添加角色     
	  * @param role
	  * @return
	  */
	 int addRole(SysRole role);
	 
	 /**
	  * 根据ID删除指定用户 
	  * @param id
	  * @return
	  */
	 int deletRole(Integer id);
	 /**
	  * 根据ID查询角色表中的角色所有信息,数据是封装在SysRole类中
	  * @param id
	  * @return
	  */
	 SysRole findRoleById(Integer id);
	 /**
	  * 根据传输过来的内容进行数据的变更
	  * @param role
	  * @return 返回的是影响的行数,行数是数据库的行数.
	  */
	 int updateRole(SysRole role);
	 
	 /**
	  * 根据角色id进行资源id的查询
	  * @param id
	  * @return
	  */
	 List<Integer> findResourceIdListByRoleId(Integer id);
	 
	 /**
	  * 查询三级树的方法 ,用于查找三级树(角色管理中的授权树)
	  * @return
	  */
	 List<Tree> FindAllRoleTrees();
	 /**
	  * 根据前端传入的用户角色id,选择表中的资源,进行已有的资源ID进行删除.(如果可以的话,在前端的时候就知道那个是已经选过了就不发送的话那就有点好了)
	  * @param id 选择的角色
	  * @param resourceIds 选择授权的资源
	  */
	 int updateRoleResource(Integer id, String resourceIds);
	 
	 /**
	  * 
	  * @return
	  */
	 List<Tree> findTree();
}
