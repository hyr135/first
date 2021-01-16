package com.education.system.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.education.system.common.config.Config;
import com.education.system.common.entity.PageInfo;
import com.education.system.common.entity.Tree;
import com.education.system.sys.entity.SysResource;
import com.education.system.sys.entity.SysRole;
import com.education.system.sys.entity.SysRoleResource;
import com.education.system.sys.mapper.SysResourceMapper;
import com.education.system.sys.mapper.SysRoleMapper;
import com.education.system.sys.mapper.SysRoleResourceMapper;
import com.education.system.sys.mapper.SysUserRoleMapper;
import com.education.system.sys.service.ISysRoleService;
@Service
public class SysRoleServiceImpl implements ISysRoleService{
	//注入mapper层依赖
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	//注入user层mapper依赖
	@Autowired 
	private SysRoleMapper sysRoleMapper; 
	
	//注入role and resource 中间表
	@Autowired
	private SysRoleResourceMapper sysRoleResourceMapper;
	@Autowired
	private SysResourceMapper sysResourceMapper;
	
	@Override
	public List<Map<Integer, String>> findRoleResourceListByRoleId(Integer roleId) {
		//调用mapper层方法	
		return sysRoleMapper.findRoleResourceListByRoleId(roleId);
	}

	@Override
	public List<Integer> findRoleIdListByUserId(Integer userId) {
		
		return sysUserRoleMapper.findRoleIdListByUserId(userId);
	}

	@Override
	public void findDataGrid(PageInfo pageInfo) {
		//查询角色信息
		pageInfo.setRows(sysRoleMapper.findRolePageCondition(pageInfo));
		//查询信息总条数
		pageInfo.setTotal(sysRoleMapper.findRolePageCount(pageInfo));
	}

	@Override
	public int addRole(SysRole role) {
		int insert = sysRoleMapper.insert(role);
		return insert;
	}
	@Transactional
	@Override
	public int deletRole(Integer id) {
		//在这里最好使用事务进行控制,因为一般出现哪一个环节出现问题的话应该要让事务进行回归
		//删除角色信息的时候同时要把角色资源中的对应信息进行删除.
		List<Integer> resourceId = sysRoleMapper.findRoleResourceIdListByRoleId(id);
		System.out.println("列举资源ID列表");
		for (Integer integer : resourceId) {
			System.out.println(integer);
		}
		
		int batchDelete =0;
		System.out.println(resourceId.size());
		System.out.println("是否是空的:"+resourceId.isEmpty());
		System.out.println("是否是null:"+resourceId);
		
		if (resourceId !=null && (!(resourceId.isEmpty()))) {
			//进行批量删除
			batchDelete = sysRoleResourceMapper.batchDelete(resourceId);
		}
	
			int deleteRoleById = sysRoleMapper.deleteRoleById(id);
			
			
			return deleteRoleById;
			
	}

	@Override
	public SysRole findRoleById(Integer id) {
		//调用mapper接口方法
		SysRole roleMessage = sysRoleMapper.findRoleById(id);
		return roleMessage;
	}
	@Transactional
	@Override
	public int updateRole(SysRole role) {
		//调用mapper层的接口
		int updateRole = sysRoleMapper.updateRole(role);
		return updateRole;
	}

	@Override
	public List<Integer> findResourceIdListByRoleId(Integer id) {
		
		return sysRoleMapper.findResourceIdListByRoleId(id);
	}

	@Override
	public List<Tree> FindAllRoleTrees() {
		//创建一个集合来存储所有的一级树,一级树下面在创建一个集合放置二级树,二级树下面创建一个在存放三级树
		List<Tree>  treeOneList = new ArrayList<>();
		List<SysResource> resources = sysResourceMapper.findResourceAllByTypeAndPidNull(Config.RESOURE_MENU);
		
		if (resources == null) {
			return null;
		}
		//将所有的一级菜单资源存放到Tree中,信息存放
		for (SysResource resourceOne : resources) {
			//创建一个引用对象,将数据放到该对象中
			Tree treeOne = new Tree();
			
			treeOne.setId(resourceOne.getId());
			treeOne.setText(resourceOne.getResourceName());
			treeOne.setIconCls(resourceOne.getResourceIcon());
			treeOne.setAttributes(resourceOne.getResourceUrl());
			//设置子菜单,是要查询每一级菜单下面的所有子菜单
			List<SysResource> resourcesTwo = sysResourceMapper.findResourceAllByTypeAndPid(Config.RESOURE_MENU, resourceOne.getId());
			
			if (resourcesTwo == null) {
				treeOne.setState("close");
			}else {
				List<Tree>  treeTwoList = new ArrayList<>();
				//将所有的子菜单放到Tree中
				for (SysResource resourceTwo : resourcesTwo) {
					Tree treeTwo = new Tree();
					
					treeTwo.setId(resourceTwo.getId());
					treeTwo.setText(resourceTwo.getResourceName());
					treeTwo.setIconCls(resourceTwo.getResourceIcon());
					treeTwo.setAttributes(resourceTwo.getResourceUrl());
					//查询每一个二级树下面的三级菜单
					List<SysResource> resourcesThree = sysResourceMapper.findResourceAllByTypeAndPid(Config.RESOURE_BUTTON, resourceTwo.getId());
					if(resourcesThree == null) {
						treeTwo.setState("close");
					}else {
						List<Tree>  treeThreeList = new ArrayList<>();
						//将所有的子菜单放到Tree中
						
						for (SysResource resourceThree : resourcesThree) {
							Tree treeThree = new Tree();
							
							treeThree.setId(resourceThree.getId());
							treeThree.setText(resourceThree.getResourceName());
							treeThree.setIconCls(resourceThree.getResourceIcon());
							treeThree.setAttributes(resourceThree.getResourceUrl());
							//放到集合里
							treeThreeList.add(treeThree);
							
						}
						//将每一个二级菜单中的菜单数放到二级菜单的一个条目里
						treeTwo.setChildren(treeThreeList);
						
					}
					//放入到二级菜单列表
					treeTwoList.add(treeTwo);
					
				}
				//为一级菜单添加子菜单
				treeOne.setChildren(treeTwoList);
			}
			//放入到一级菜单列表
			treeOneList.add(treeOne);	
		}	
		return treeOneList;
	}
	/**
	 * 声明式事务,就是在方法中,一般事务是在service 的实现类中进行事务的声明.
	 * 使用aop定义切点,将切点的中定义通知.
	 */
	@Transactional
	@Override
	public int updateRoleResource(Integer id, String resourceIds) {
		//根据角色ID查询到已有资源id,删除资源后再进行添加
		List<Integer> resourceIdList = sysRoleMapper.findResourceIdListByRoleId(id);
		if (resourceIdList != null && (!resourceIdList.isEmpty())) {
			//进行批量删除
			int batchDelete = sysRoleResourceMapper.batchDelete(resourceIdList);
		}
		
		//进行新增权限的添加
		
		
		//先对传进来的id值进行判断
		if(resourceIds != null && !(resourceIds.equals("")) ) {
			//进行批量新增
			List<SysRoleResource> batch = new ArrayList<SysRoleResource>();
			String[] resourcesId = resourceIds.split(",");
			//将id一一进行存储到实体类中
			for (String string : resourcesId) {
				SysRoleResource sysRoleResource = new SysRoleResource();
				sysRoleResource.setRoleId(id);
				sysRoleResource.setResourceId(Integer.parseInt(string));
				
				batch.add(sysRoleResource);
				
			}
			int batchInsert = sysRoleResourceMapper.batchInsert(batch);
			//返回的是影响的行数
			return batchInsert;
			
		}
		//如果返回的是零说明没有进行任何的修改.
		return 0;
	}

	

	@Override
	public List<Tree> findTree() {
		//服务层进行数据的处理
		ArrayList<Tree> trees = new ArrayList<>();
		List<SysRole> SysRole = sysRoleMapper.findRoleAll();
		
		//遍历
		for (SysRole sysRole2 : SysRole) {
			//创建Tree树形,实体类是树
			Tree tree = new Tree();
			tree.setId(sysRole2.getId());
			tree.setText(sysRole2.getRoleName());
			trees.add(tree);
		}
		
		return trees;
	}
}
