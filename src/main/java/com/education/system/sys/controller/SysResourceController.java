package com.education.system.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.education.system.common.aop.MySysLog;
import com.education.system.common.entity.LeftMenu;
import com.education.system.common.entity.Result;
import com.education.system.common.entity.Tree;
import com.education.system.sys.entity.SysResource;
import com.education.system.sys.entity.SysUser;
import com.education.system.sys.service.impl.SysResoureServiceImpl;
import com.education.system.sys.service.impl.SysRoleServiceImpl;

/**
 * 系统资源控制器，对某一类的请求进行处理，这一个类继承一个基础的控制器
 * 一个类型的资源就要使用一样的控制器进行管理。避免重复生成
 * @author Dell
 *
 */
@Controller
@RequestMapping("/resource")
public class SysResourceController extends BaseContorller {
	//注入依赖
	@SuppressWarnings("unused")
	@Autowired
	private SysResoureServiceImpl sysResourceServiceImpl;
	@Autowired
	private SysRoleServiceImpl sysRoleServiceImpl;
	
	//创建路径请求方法
	/**
	 * 加载左边菜单的功能
	 * @return
	 */
	@RequestMapping(value="/findLeftMenu", method =RequestMethod.GET)
	@ResponseBody
	public List<LeftMenu> findLeftMenu(){
		//获取当前用户对象
		SysUser user = getCurrentUser();
		//调用服务层方法
		List<LeftMenu> findLeftMenu = sysResourceServiceImpl.findLeftMenu(user);
		
		return findLeftMenu;
	}
	
	/**
	 * 权限要求必须要有
	 * @return 返回的字符串是一个jsp地址
	 */
	@RequestMapping(value="/manager",method=RequestMethod.GET)
	@RequiresPermissions("resource:list")
	public String manager() {
		
		return "resource";
	}
	
	/**
	 * 
	 * @return 返回实体类封装的数据，一个json格式直接将数据返回。使用了类似response响应形式
	 */
	@MySysLog(value="获取资源菜单树")
	@RequiresPermissions("resource:list") // 权限
	@RequestMapping(value="/treeGrid",method=RequestMethod.POST)
	@ResponseBody
	public List<SysResource> treeGrid(){
		//调用服务层对象
		List<SysResource> resource = sysResourceServiceImpl.findResourceAll();
		//为了能够将资源图标进行生成使用转义
		for(SysResource resou : resource) {
			String icon = StringEscapeUtils.escapeHtml(resou.getResourceIcon());
			resou.setResourceIcon(icon);
		}
		return resource;
	}
	
	/**
	 * 
	 * @return 放回到一个jsp页面，进行的是请求转发，请求转发响应速度快，并且安全系数比较高
	 */
	@MySysLog(value="资源添加")
	@RequiresPermissions("resource:add")
	@RequestMapping("/addPage")
	public String addResource() {
		//已经经过了视图解析器进行了路径的统一配置
		return "resource/resourceAdd";
	}
	/**
	 * 获取所有的菜单
	 * @return 是一个结果集将结果以json的形式传回给前端进行数据的显示
	 */
	@RequestMapping("/allTree")
	@ResponseBody
	public List<Tree> allTree(){
		List<Tree> findAllTree = sysResourceServiceImpl.findAllTree();
		return findAllTree;
	}
	/**
	 * 使用result实体类将操作的信息传回给页面进行数据的渲染，如果成功返回的信息就是自定义的数据了
	 * @param 传进来的参数应该是跟前端传过来的参数是对应的。
	 * @return
	 */
	@MySysLog(value="资源添加")
	@RequiresPermissions("resource:add")
	@RequestMapping("/add")
	@ResponseBody
	public Result addResources(SysResource resource) {
		Result result = new Result();
		//invoke service model function
		try {
			int insertFlag = sysResourceServiceImpl.addResource(resource);
			if(insertFlag > 0) {
				//想实体类中存放响应数据
				result.setSuccess(true);
				result.setMsg("添加成功！");
				return result;
			}
		}catch(Exception e){
			result.setMsg("添加失败");
			e.printStackTrace();
			return result;
		}
		
		
		return result;
	} 
	/**
	 * 指定资源ID进行资源的删除
	 * @param id 前端传过来的数据
	 * @return 返回一个自定义的实体类，后面转成一个json格式的一个数据返回前端进行数据的渲染。
	 */
	@MySysLog(value="资源删除")
	@RequiresPermissions("resource:delete")//权限
	@RequestMapping("/delete")
	@ResponseBody
	public Result deleteResource(Integer id) {
		Result result = new Result();
		//invoke service model function
		try {
			int insertFlag = sysResourceServiceImpl.deleteResourceById(id);
			if(insertFlag > 0) {
				//想实体类中存放响应数据
				result.setSuccess(true);
				result.setMsg("删除成功！");
				return result;
			}
		}catch(Exception e){
			result.setMsg("删除失败");
			e.printStackTrace();
			return result;
		}
		
		
		return result;
	} 
	/**
	 * 跳转页面,点击编辑之后再将数据进行查询回来之后跳转到边界页面.
	 * @param request 一个作用域对象,用于存储区查询数据回来之后存储到作用域中,前端通过el表达式进行数据的获取
 	 * @param id 从前端传回来的数据,只要在这里进行声明springMVC就可进行复制,如果参数名对应不上就可以使用注解进行
 	 * 参数的修改.
	 * @return 返回的是一个物理视图名称.
	 * @attention :如果是自定义视图的话不能使用任何的前缀比如:forward,和redirect,这样子就会走默认视图配置
	 * 所以这样子就得有的考量.
	 * 凡是使用@RequestMapping("")注解的不管返回值是什么,都是人为是视图名称.
	 * 使用另一种注解才可以使用输出流返回前端.
	 * 
	 */
	@MySysLog(value="编辑资源")
	@RequiresPermissions("resource:edit")
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request,Integer id) {
		//调用服务层方法
		SysResource resource = sysResourceServiceImpl.findResourceById(id);
		//将查询的结果放到作用域中存储起来
		// 图标转义回去
		String icon = StringEscapeUtils.escapeHtml(resource.getResourceIcon());
		resource.setResourceIcon(icon);
		request.setAttribute("resource",resource);
		
		return "resource/resourceEdit";		
	}
	/**
	 * 
	 * @return 以输出流的方式,且结果是json格式发送回前段进行数据的渲染.
 	 */
	@RequestMapping(value = "/findMenuTree", method = RequestMethod.POST)
	@ResponseBody
	public List<Tree> findTree2(){
		//获取当前用户,这是通过基础控制器去拿到的数据
		SysUser currentUser = getCurrentUser();
		//获取菜单树
		List<Tree> findMenuTree = sysResourceServiceImpl.findMenuTree(currentUser);
		return findMenuTree;
		
	}
	/**
	 * 编辑资源
	 * @param resource
	 * @return
	 */
	@MySysLog(value="编辑资源")
	@RequiresPermissions("resource:edit")
	@RequestMapping("/edit")
	@ResponseBody
	public Result edit(SysResource resource) {
		Result result = new Result();
		try {
			// 带有&#的图标符号需要转义
			String icon = StringEscapeUtils.unescapeHtml(resource.getResourceIcon());
			resource.setResourceIcon(icon);
			sysResourceServiceImpl.updateResource(resource);
			result.setSuccess(true);
			result.setMsg("编辑成功！");
			return result;
		} catch (RuntimeException e) {
			result.setMsg("编辑失败");
			e.printStackTrace();
			return result;
		}
	}
	
	

	@RequestMapping(value="/FindAllRoleTrees",method=RequestMethod.POST)
	@ResponseBody
	public List<Tree> FindAllGrandTree(){
		//调用service层方法
		List<Tree> roleTrees = sysRoleServiceImpl.FindAllRoleTrees();
		return  roleTrees;
	}
	
	
	
}	
