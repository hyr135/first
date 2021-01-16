package com.education.system.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.education.system.common.entity.PageInfo;
import com.education.system.sys.entity.SysResource;
import com.education.system.sys.entity.SysRole;
import com.education.system.sys.entity.SysUserRole;
@Repository
public interface SysRoleMapper {
  
	/**
	 * 通过角色 Id 查询资源路径列表路径
	 * 
	 * @param roleId
	 * @return
	 */
	List<Map<Integer, String>> findRoleResourceListByRoleId(Integer roleId);
	/**
	 * 查询角色对应菜单列表
	 * 根据角色查询到该角色的对应资源id，在拿到这个id进行查询数据库里面对应的资源
	 * @param i
	 * @return 可能这个角色可以使用的资源很多，所以是一个使用有序的集合来存放数据，因为要通过
	 * foreach来遍历循环，来循环查询。
	 */
	List<SysResource> findResourceIdListByRoleIdAndType(Integer i);
	
	/**
	 * 根据分页条件查询角色列表.
	 * @param pageInfo 分页信息
	 * @return 返回来的数据是一个资源角色list
	 */
	List<?> findRolePageCondition(PageInfo pageInfo);
	
	/**
	 * 统计有多少个角色在里面.
	 * 为的是进行计算每一页的开始或者判断是否是最后一页.
	 * @param pageInfo
	 * @return 返回一个整数类型.
	 */
	int findRolePageCount(PageInfo pageInfo);
	/**
	 * 
	 * @param role 存入的实体类数据,进行数据的
	 * @return
	 */
	int insert(SysRole role);
	/**
	 * 根据ID删除用户功能.
	 * @param id 根据前端传过来的ID值进行删除,可能会进行批量删除,这里采用的是单个删除,多个删除在服务层进行循环删除.
	 * @return 影响的行数
	 */
	int deleteRoleById(Integer id);
	/**
	 * 根据角色id查询全部的在资源表中对应的资源要进行删除.
	 * @param roleId
	 * @return
	 */
	List<Integer> findRoleResourceIdListByRoleId(Integer roleId);
	/**
	 *  根据前端传过来的数据进行对相应的账户进行查找
	 * @param id 前端传输的值
	 * @return 返回一个实体类封装的数据
	 */
	SysRole findRoleById(Integer id);
	/**
	 * 根据前端传输过来的角色数据进行数据的更新
	 * @param role
	 * @return
	 */
	int updateRole(SysRole role);
	
	/**
	 * 通过角色的ID去查询资源的ID,看是否是一斤授权过的,在页面进行显示的时候是要进行区别对待.
	 * 查询的数据表是资源于角色的中间表.
	 * @param id
	 * @return 返回的是资源ID的一个list集合.
	 */
	List<Integer> findResourceIdListByRoleId(Integer id);
	
	
	/**
	 * 加载权限树
	 */
	List<SysRole> findRoleAll();
	
	
	/**
	 * 用户添加时保存用户角色关联,这个应该写在userrole里面
	 * 
	 * @param userRole
	 * @return
	 */
	int inserts(SysUserRole userRole);
	
	
	
	
}