package com.education.system.sys.service;

import java.util.List;

import com.education.system.common.entity.LeftMenu;
import com.education.system.common.entity.Tree;
import com.education.system.sys.entity.SysResource;
import com.education.system.sys.entity.SysUser;

public interface ISysResourceService {
	/**
	 * 通过用户的参数进行查找菜单，这个用户可能是管理员，用户，所以可以看到的左边菜单是不一样的
	 * 进而对用户进行区别对待，增加一定的权限。
	 * @param user一个SysUser的用户类型
	 * @return 返回一个做菜单的列表
	 */
	List<LeftMenu> findLeftMenu(SysUser user);
	
	/**
	 * 
	 * @return 资源的list集合
	 */
	List<SysResource> findResourceAll();
	/**
	 * 查询所有的资源数，用以添加资源,使用的方法是已经进行编写过的方法
	 * @return 返回值是一个list集合存放的一个Tree类对象
	 */
	List<Tree> findAllTree();
	/**
	 * 添加资源
	 * @param resource 传入的实体类参数
	 * @return 返回整形数，如果大于零就是操作成功
	 */
	int addResource(SysResource resource);
	/**
	 * 这个返回值也可不适用，在进行调用的时候如果出现异常就代表操作失败使用try -catch进行捕获异常。
	 * @param id
	 * @return
	 */
	int deleteResourceById(Integer id);
	/**
	 * 根据用户ID进行查询资源表数据
	 * @param id
	 * @return
	 */
	
	SysResource findResourceById(Integer id);
	/**
	 * 查询菜单树
	 * @param user
	 * @return
	 */
	List<Tree> findMenuTree(SysUser user);
	/**
	 * 资源的更新
	 * @param resource
	 * @return
	 */
	int updateResource(SysResource resource);
	

}
