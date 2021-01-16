package com.education.system.sys.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.system.common.config.Config;
import com.education.system.common.entity.LeftMenu;
import com.education.system.common.entity.Tree;
import com.education.system.sys.entity.SysResource;
import com.education.system.sys.entity.SysUser;
import com.education.system.sys.mapper.SysResourceMapper;
import com.education.system.sys.mapper.SysRoleMapper;
import com.education.system.sys.mapper.SysUserMapper;
import com.education.system.sys.mapper.SysUserRoleMapper;
import com.education.system.sys.service.ISysResourceService;

@Service
public class SysResoureServiceImpl implements ISysResourceService{
	//进行依赖注入
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private SysResourceMapper sysResourceMapper;
	
	
	@Override
	public List<LeftMenu> findLeftMenu(SysUser user) {
		//根据不同的角色进行菜单分级显示
		ArrayList<LeftMenu> leftMenus = new ArrayList<>();
		//给admin最高的权限
		if(user.getLoginName().equals("admin")) {
			//寻找父资源,传入资源类型
			List<SysResource> resourePMenu = sysResourceMapper.findResourceAllByTypeAndPidNull(Config.RESOURE_MENU);
			if (resourePMenu == null) {
				return null;
			}
			//遍历
			for (SysResource sysResource : resourePMenu) {
				//这个是放一个菜单列表，仅仅是一个菜单，后面大一级的菜单有放在一个ArrayList<LeftMenu>集合中
				LeftMenu menuOne = new LeftMenu();
				menuOne.setId(sysResource.getId());
				menuOne.setTitle(sysResource.getResourceName());
				menuOne.setIcon(sysResource.getResourceIcon());
				menuOne.setHref(sysResource.getResourceUrl());
				menuOne.setSpread(true);
				//查询子菜单
				
				//注意这里的父级的资源是以父级资源的id命名的，所以获取资源就是获取id
				List<SysResource> chirldLeftMenu = sysResourceMapper.findResourceAllByTypeAndPid(sysResource.getResourceType(), sysResource.getId());
				if (chirldLeftMenu != null) {
					//有子级的菜单
					ArrayList<LeftMenu> leftmenu = new ArrayList<>();
					//遍历子级的菜单结果
					for (SysResource sysResource2 : chirldLeftMenu) {
						//新建一个菜单实体类来存放，多个菜单，每一个菜单的内容又是一个
						//单独的菜单各自的信息存放在里面包括id还有其他信息，每一菜单可能又有
						//下一级菜单
						LeftMenu menuOne1 =  new LeftMenu();
						menuOne1.setId(sysResource2.getId());
						menuOne1.setTitle(sysResource2.getResourceName());
						menuOne1.setIcon(sysResource2.getResourceIcon());
						menuOne1.setHref(sysResource2.getResourceUrl());
						//这一个的意思是设置为true来判断，上一级的和这一级的数据就是拿来判断就可以选择样式
						menuOne1.setSpread(true);
						//以为是多个菜单的数据，所以使用list集合来存储数据
						//多个菜单放在一个有序数组里面
						leftmenu.add(menuOne1);
					}
					menuOne.setChildrens(leftmenu);
				}else {
					//没有子级菜单这个spread就是为false，为了在js中判断到底该使用
					//何种的样式，是否有下拉框
					menuOne.setSpread(false);
				}
				//遍历结束的时候就将数据存放完成，有多少个菜单就会存放多少个菜单
				leftMenus.add(menuOne);
			}
			//将菜单返回，这个就是超级管理员账号。执行了超级管理员的就不会去执行另一个用户权限的判断。
			return leftMenus;
		}
		
		//其他用户或者管理员不同角色进行权限的分配
		List<SysResource> IdList = new ArrayList<SysResource>();
		List<Integer> roleIdList = sysUserRoleMapper.findRoleIdListByUserId(user.getId());
		for (Integer i : roleIdList) {
			List<SysResource> IdLists = sysRoleMapper.findResourceIdListByRoleIdAndType(i);
			for (SysResource resource : IdLists) {
				IdList.add(resource);
			}
		}
		for (SysResource resource : IdList) {
			if (resource != null && resource.getResourcePid() == null) {
				LeftMenu menuOne = new LeftMenu();
				menuOne.setId(resource.getId());
				menuOne.setTitle(resource.getResourceName());
				menuOne.setIcon(resource.getResourceIcon());
				menuOne.setHref(resource.getResourceUrl());
				menuOne.setSpread(true);

				List<LeftMenu> leftMenu = new ArrayList<LeftMenu>();
				for (SysResource resourceTwo : IdList) {
					if (resourceTwo.getResourcePid() != null && resource.getId().intValue() == resourceTwo.getResourcePid().intValue()) {
						LeftMenu menuTwo = new LeftMenu();
						menuTwo.setId(resourceTwo.getId());
						menuTwo.setTitle(resourceTwo.getResourceName());
						menuTwo.setIcon(resourceTwo.getResourceIcon());
						menuTwo.setHref(resourceTwo.getResourceUrl());
						menuTwo.setSpread(true);
						leftMenu.add(menuTwo);
					}
				}
				menuOne.setChildrens(leftMenu);;
				leftMenus.add(menuOne);
			}
		}
		return leftMenus;
		
	}


	@Override
	public List<SysResource> findResourceAll() {
		
		return sysResourceMapper.findResourceAll();
	}


	@Override
	public List<Tree> findAllTree() {
		//创建List集合存放数据
		ArrayList<Tree> trees = new ArrayList<>();
		new Config();
		List<SysResource> resources = sysResourceMapper.findResourceAllByTypeAndPidNull(Config.RESOURE_MENU);
		if (resources == null) {
			return null;
		}
		if (resources != null) {
			//遍历所有的一级菜单查询一级菜单下的二级菜单
			for (SysResource resourceOne : resources) {
				//创建一个实体类来存放菜单的值
				Tree treeOne = new Tree();
				treeOne.setId(resourceOne.getId());
				treeOne.setText(resourceOne.getResourceName());
				treeOne.setIconCls(resourceOne.getResourceIcon());
				treeOne.setAttributes(resourceOne.getResourceUrl());
				//调用方法查询一级菜单下的所有二级菜单
				List<SysResource> resourceTwo = sysResourceMapper.findResourceAllByTypeAndPid(Config.RESOURE_MENU, resourceOne.getId());
				
				if (resourceTwo != null) {
					List<Tree> tree = new ArrayList<>();
					//注意这里循环遍历的是哪一个资源
					for (SysResource resourceTwo1 : resourceTwo) {
						//第一层for循环
						//为没有子菜单下面才的信息存储
						Tree treeTwo = new Tree();
						treeTwo.setId(resourceTwo1.getId());
						treeTwo.setText(resourceTwo1.getResourceName());
						treeTwo.setIconCls(resourceTwo1.getResourceIcon());
						treeTwo.setAttributes(resourceTwo1.getResourceUrl());
						//添加到集合中，每一个菜单就是集合中的一个元素
						tree.add(treeTwo);
					}
					//第一层for循环
					treeOne.setChildren(tree);
				}else {
					//继续设置一级菜单的其他属性
					treeOne.setState("closed");
					
				}
				
				trees.add(treeOne);
				
			}
		}//这是if判断的结尾
		
		return trees;
	}


	@Override
	public int addResource(SysResource resource) {
		
		return sysResourceMapper.insert(resource);
	}


	@Override
	public int deleteResourceById(Integer id) {
		
		return sysResourceMapper.deleteResourceById(id);
		
	}


	
	@Override
	public SysResource findResourceById(Integer id) {
		//调用mapper层方法
		return sysResourceMapper.findResourceById(id);
	}


	@Override
	public List<Tree> findMenuTree(SysUser user) {
		//创建一个List来存放数据
		ArrayList<Tree> trees = new ArrayList<>();
		//判断用户对象
		if (user.getLoginName().equals("admin")) {
			//找寻父资源
			List<SysResource> resourceFather = sysResourceMapper.findResourceAllByTypeAndPidNull(Config.RESOURE_MENU);
			
			//判断查询结果
			if (resourceFather == null) {
				return null;
			}
			//进行每一个父级菜单进行查询子菜单
			for (SysResource resourceOne : resourceFather) {
				//存放每一数据
				Tree treeOne = new Tree();
				treeOne.setId(resourceOne.getId());
				treeOne.setText(resourceOne.getResourceName());
				treeOne.setIconCls(resourceOne.getResourceIcon());
				treeOne.setAttributes(resourceOne.getResourceUrl());
				//由于子菜单有是一个list集合,所以将查询之后的数据进行遍历之后才可以放入
				List<SysResource> resourceSon = sysResourceMapper.findResourceAllByTypeAndPid(Config.RESOURE_MENU,resourceOne.getId());
				//判断子菜单是否为空
				if (resourceSon != null) {
					//创建一个集合来多个子菜单
					List<Tree> tree = new ArrayList<Tree>();
					for (SysResource resourceTwo : resourceSon) {
						//每一个菜单又有多个属性值,将值存放到实体类中
						Tree treeTwo = new Tree();
						//给每一个属性赋值
						treeTwo.setId(resourceTwo.getId());
						treeTwo.setText(resourceTwo.getResourceName());
						treeTwo.setIconCls(resourceTwo.getResourceIcon());
						treeTwo.setAttributes(resourceTwo.getResourceUrl());
						//将一个菜单放入集合当中
						tree.add(treeTwo);
					}
					//一级菜单中要给子菜单属性中赋值
					treeOne.setChildren(tree);
				}else {
					//否则就是没有子菜单,没有子菜单,前端样式就不会有下拉
					treeOne.setState("closed");
				}
				//将多个一级菜单放入到集合中.这样就可以显示多个一级菜单
				trees.add(treeOne);
			}
			return trees;
			
		}//这是用户是管理员的时候进行的数据运算.
		
		
		// 普通用户
				Set<SysResource> IdList = new HashSet<SysResource>();
				List<Integer> roleIdList = sysUserRoleMapper.findRoleIdListByUserId(user.getId());
				for (Integer i : roleIdList) {
					List<SysResource> IdLists = sysRoleMapper.findResourceIdListByRoleIdAndType(i);
					for (SysResource resource : IdLists) {
						IdList.add(resource);
					}
				}
				for (SysResource resource : IdList) {
					if (resource != null && resource.getResourcePid() == null) {
						Tree treeOne = new Tree();
						treeOne.setId(resource.getId());
						treeOne.setText(resource.getResourceName());
						treeOne.setIconCls(resource.getResourceIcon());
						treeOne.setAttributes(resource.getResourceUrl());
						List<Tree> tree = new ArrayList<Tree>();
						for (SysResource resourceTwo : IdList) {
							if (resourceTwo.getResourcePid() != null && resource.getId().intValue() == resourceTwo.getResourcePid().intValue()) {
								Tree treeTwo = new Tree();
								treeTwo.setId(resourceTwo.getId());
								treeTwo.setText(resourceTwo.getResourceName());
								treeTwo.setIconCls(resourceTwo.getResourceIcon());
								treeTwo.setAttributes(resourceTwo.getResourceUrl());
								tree.add(treeTwo);
							}
						}	
						treeOne.setChildren(tree);
						trees.add(treeOne);
					}
				}
		return trees;
	}


	@Override
	public int updateResource(SysResource resource) {
		//调用mapper层接口
		
		int updateResourceFlag = sysResourceMapper.updateResource(resource);
		if (updateResourceFlag != 1) {
			
			throw new RuntimeException("更新失败！");
		}
		
		return updateResourceFlag;
	}
	
	

	
	
}
