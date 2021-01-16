package com.education.system.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.education.system.common.aop.MySysLog;
import com.education.system.common.entity.PageInfo;
import com.education.system.common.entity.Result;
import com.education.system.common.entity.Tree;
import com.education.system.sys.entity.SysRole;
import com.education.system.sys.service.impl.SysRoleServiceImpl;
@Controller
@RequestMapping("role")
public class SysRoleController extends BaseContorller{

	//依赖注入服务层 ,最好名称要跟类名一样这样效率高一点.
	@Autowired
	private SysRoleServiceImpl sysRoleServiceImpl;
	
	//创建方法
	/**
	 * 权限管理页
	 * @return
	 */
	@MySysLog(value="获取角色列表")
	@RequiresPermissions("role:list")
	@RequestMapping(value="/manager",method =RequestMethod.GET)
	public String manager() {
		//这个语句的输出的时候是输出的标准的错误信息.
		System.err.println("标准错误");
		
		return "role/role";
	}
	
	@RequiresPermissions("role:list")
	@RequestMapping(value = "/dataGrid", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo dataGrid(SysRole role, Integer page, Integer rows, String sort, String order) {
	    PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		Map<String, Object> condition = new HashMap<String, Object>();
		pageInfo.setCondition(condition);
	    
	    sysRoleServiceImpl.findDataGrid(pageInfo);
	   
		return pageInfo;
	}
	@RequiresPermissions("role:add")
	@RequestMapping("/addPage")
	public String addPage() {
		
		return "role/addPage";
	}
	@MySysLog(value="添加角色")
	@RequiresPermissions("role:add")
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Result addRole(SysRole role) {
		Result result = new Result();
		
		//使用服务层
		try {
			int addRole = sysRoleServiceImpl.addRole(role);
			if (addRole >0) {
				result.setSuccess(true);
				result.setMsg("创建角色成功");
			}
			
		} catch (Exception e) {
			result.setMsg("创建角色失败");
		}
		return result;
	} 
	@MySysLog(value="删除角色")
	@RequiresPermissions("role:delete")
	@RequestMapping("/delete")
	@ResponseBody
	public Result deleteRole(Integer id) {
		Result result = new Result();
		
		try {
			int deletRoleFlay = sysRoleServiceImpl.deletRole(id);
			result.setSuccess(true);
			result.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("删除失败");
		}
		return result;
		
		
	}
	
	@MySysLog(value="跳转到角色编辑页")
	@RequiresPermissions("role:edit")
	@RequestMapping(value="/editPage")
	public String editPage(Integer id,HttpServletRequest request) {
		SysRole roleMessage = sysRoleServiceImpl.findRoleById(id);
		//将查询回来的数据存放到request作用域中
		request.setAttribute("role", roleMessage);
		
		return "role/roleEdit";
	}
	
	/**
	 * @param role 参数是从前端穿过来的数据,这里只要在这里防止的数据类型是能接收的就能够进行接收.
	 * @return 将操作的结果以json的格式返回或者说是响应到前端或者是浏览器,使用js进行调用
	 */
	@MySysLog(value="编辑角色")
	@RequiresPermissions("role:edit")
	@RequestMapping("/edit")
	@ResponseBody
	public Result editRoleMsg(SysRole role) {
		Result result = new Result();
			//调用服务层方法
			try {
				int updateRole = sysRoleServiceImpl.updateRole(role);
				if (updateRole >0) {
					result.setSuccess(true);
					result.setMsg("编辑成功");
					return result;
				}
			} catch (Exception e) {
				
				result.setMsg("编辑失败");
				e.printStackTrace();
				return result;
				
			}
		return result;
	}
	
	/**
	 * 
	 * @param request
	 * @param model 
	 * @return
	 * 
	 * model 这个类型的参数是spring自己封装的一个模型,是为了解耦原生servlet 中的request,session,
	 * 一般session这个作用域希望通过request.getSession(), 来进行获取,当然也可以直接在参数中进行声明.
	 */
	@RequiresPermissions("role:grant")
	@RequestMapping("/grantPage")
	public String grantRole(HttpServletRequest request, Model model, Integer id) {
		//将选中的角色的id通过request作用域,或者session作用域进行值得传递,传递到了jsp中,jsp是一个特殊的servlet.
		model.addAttribute("roleId",id);
		//将返回权限授权页面,其页面的内容,即授权选项在通过js 中使用ajax技术进行当页面加载的时候就让js立即加载,就可以得到请求数据.
		return "role/roleGrant";
	}
	@MySysLog(value="角色资源查询")
	@RequestMapping("/findResourceIdListByRoleId")
	@ResponseBody
	public Result findResourceByRoleId(HttpServletRequest request, Integer id, Model model) {
		Result result = new Result();
			try {
				List<Integer> resources  = sysRoleServiceImpl.findResourceIdListByRoleId(id);
				result.setSuccess(true);
				result.setSuccess(true);
				result.setMsg("资源查询成功");
				result.setObj(resources);
				return result;
			} catch (Exception e) {
				result.setMsg("资源查询失败");
				e.printStackTrace();
				return result;
			}
		
	}
	@MySysLog(value="权限添加")
	@RequiresPermissions("role:grant")
	@RequestMapping("/grant")
	@ResponseBody
	public Result grant(String resourceIds ,Integer id) {
		//创建一个对象,来讲操作的信息返回到前端.
		Result result = new Result();
		try {
			
			//调用服务层的方法进行,操作
			int updateRoleResourceFlag = sysRoleServiceImpl.updateRoleResource(id, resourceIds);
			if (updateRoleResourceFlag > 0) {
				result.setSuccess(true);
				String msg="添加了"+updateRoleResourceFlag+"个权限成功";
				System.out.println(msg);
				result.setMsg(msg);
				
				return result;
			}else {
				result.setMsg("本次操作没有选择权限进行添加");
				return result;
				
			}
			
		} catch (Exception e) {
			result.setMsg("本次操作出现了未知异常,请联系管理员");
			e.printStackTrace();
			return result;
		}
		
		
	}
	
	
	/**
	 * 权限树
	 * 
	 * @return
	 */
	@MySysLog(value="获取权限树")
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public List<Tree> tree() {
		//业务操作已经在业务层进行了处理,这里就不用进行处理了.
		return sysRoleServiceImpl.findTree();
	}
	
}



