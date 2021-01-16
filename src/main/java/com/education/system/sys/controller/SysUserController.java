package com.education.system.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.education.system.common.shiro.ShiroMD5;
import com.education.system.sys.entity.SysRole;
import com.education.system.sys.entity.SysUser;
import com.education.system.sys.service.impl.SysUserServiceImpl;
import com.education.system.sys.vo.SysUserVo;

/**
 * @function It is a web controller to option user table 
 * @author Dell
 *
 */
@Controller
@RequestMapping("user")
public class SysUserController extends BaseContorller{
	//注入服务层依赖
	@Autowired
	private SysUserServiceImpl sysUserService;
	/**
	 * @function it is a controller that according to requestURL to decide use function
	 * @param model cache data 
	 * @param request cache data when request was dispatched to forword another jsp page or controller 
	 * @return
	 */
	@MySysLog(value="查看用户列表")
	@RequestMapping(value = "/personFile", method = RequestMethod.GET)
	public String personFile(Model model,HttpServletRequest request) {
		//通过登录用户的id进行查询一个视图对象
		SysUserVo userVo = sysUserService.findSysUserVoById(getUserId());
		
		//获取用户列表
		List<SysRole> rolesList = userVo.getRolesList();
		 ArrayList<String> names = new ArrayList<>();
		//判断执行回来的数据是否为空，为空则不进行遍历
		if(rolesList != null && rolesList.size() >0) {
			//进行遍历角色
			for(SysRole role : rolesList) {
				names.add(role.getRoleName());
			}
			
		}
		//将查询到的信息存储到request作用域中
		model.addAttribute("user",userVo);
		request.setAttribute("roleNames",names);
		
		return "user/user";
		
	}
	@MySysLog(value="编辑用户角色")
	@RequestMapping("/personFileEdit")
	@ResponseBody
	public Result editPersonFile(SysUserVo userVo) {
		Result result = new Result();
		try {
			int updatePersonFileFlag = sysUserService.updatePersonFile(userVo);
			if(updatePersonFileFlag >0) {
				result.setSuccess(true);
				result.setMsg("编辑成功！");
				return result;
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			result.setMsg("编辑失败！");
			return result;
		}
		return result;
	
		
	}
	
	/**
	 * 修改密码页
	 *
	 * @return
	 */
	@RequestMapping(value = "/editPwdPage", method = RequestMethod.GET)
	public String editPwdPage() {
		return "userEditPwd";
	}
	
	/**
	 * @function edit user password and return result to the jsp page that through ajax .
	 * @param request
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	@MySysLog(value="修改密码")
	@RequestMapping("/editUserPwd")
	@ResponseBody
	public Result editUserPwd(HttpServletRequest request, String oldPwd, String newPwd) {
		Result result = new Result();
		if (getCurrentUser().getLoginName().equals("test")) {
			result.setMsg("测试账户不允许修改密码！");
			return result;
		}
		
		if (!getCurrentUser().getUserPassword().equals(ShiroMD5.GetPwd(getCurrentUser().getLoginName(), oldPwd))) {
			result.setMsg("原密码不正确!");
			return result;
		}
		try {
			int updateflag = sysUserService.updateSysUserPwdById(getUserId(), ShiroMD5.GetPwd(getCurrentUser().getLoginName(), newPwd));
			if(updateflag >0) {
				result.setSuccess(true);
				result.setMsg("密码修改成功！");
				return result;
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			result.setMsg("密码修改失败！");
			return result;
		}
		
		//ending return
		return result;
	}
	
	
	@RequiresPermissions("user:list")
	@RequestMapping("/manager")
	public String manage() {
		return "user/userList";
	}
	/**
	 * 使用分页查询数据
	 * @param userVo
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return 分页实体类
	 */
	@MySysLog(value="获取用户列表")
	@RequiresPermissions("user:list")
	@RequestMapping(value = "/dataGrid", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo dataGrid(SysUserVo userVo, Integer page, Integer rows, String sort, String order) {
		//将条件放入实体类
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		// 查询的参数列表,是查询条件,从前端的搜索功能的参数
		Map<String, Object> condition = new HashMap<String, Object>();
		
		if (userVo.getOrgId() != null) {
			//使用字符数组存放组织的ID
			String[] organizationIds  = userVo.getOrgId().split(",");
			//将id 存放到集合中list
			List<String> batch = new ArrayList<>();
			for (String string : organizationIds) {
				//存放数据
				batch.add(string);
			}
			//将idlist集合方法道map中
			condition.put("orgIds", batch);
			
		}
		//判断传入的搜索条件是否为空
		if (StringUtils.isNoneBlank(userVo.getUserName())) {
			condition.put("userName", userVo.getUserName());
		}
		if (userVo.getCreateDateStart() != null) {
			condition.put("startTime", userVo.getCreateDateStart());
		}
		if (userVo.getCreateDateEnd() != null) {
			condition.put("endTime", userVo.getCreateDateEnd());
		}
		//在分页实体类中的哪一个条件是map类型
		pageInfo.setCondition(condition);
		sysUserService.findDataGrid(pageInfo);
		return pageInfo;
	}
	
	@RequiresPermissions("user:add")
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String userAdd(HttpServletRequest request) {
		
		return "user/userAdd";
		
	}
	/**
	 * 机构用户的添加
	 * @return
	 */
	@MySysLog(value="机构用户添加")
	@RequiresPermissions("user:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Result userSave(SysUserVo vo) {
		Result result = new Result();
		SysUser u = sysUserService.findSysUserByLoginName(vo.getLoginName());
		
		if (u != null) {
			result.setMsg("用户名已经存在");
			return result;
		}
		try {
			// 加密
			vo.setUserPassword(ShiroMD5.GetPwd(vo.getLoginName(), vo.getUserPassword()));
			sysUserService.addSysUser(vo);
			result.setSuccess(true);
			result.setMsg("添加成功！");
			return result;
		} catch (RuntimeException e) {
			e.printStackTrace();
			result.setMsg("添加失败！");
			return result;
		}
		
		
	}
	@MySysLog(value="删除机构")
	@RequiresPermissions("user:delete")
	@RequestMapping(value="/delete")
	@ResponseBody
	public Result userDeleteByUserId(Integer id ) {
		
		Result result = new Result();
		try {
			sysUserService.deleteSysUserById(id);
			result.setMsg("用户删除成功");
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("用户删除失败");
			return result;
		}
			
		
	}
	
	/**
	 * 用户编辑页面
	 *
	 */
	@RequiresPermissions("user:edit")
	@RequestMapping("/editPage")
	public String edit(Integer id, Model model) {
		//根据用户ID查询所有的用户信息显示在编辑页上,使用的是spring封装的Model 数据模型
		SysUserVo userVo = sysUserService.findSysUserVoById(id);
		List<SysRole> rolesList = userVo.getRolesList();
		List<Integer> ids = new ArrayList<Integer>();
		for (SysRole role : rolesList) {
			ids.add(role.getId());
		}
		model.addAttribute("roleIds", ids);
		model.addAttribute("user", userVo);
		return "user/userEdit";
	}
	
	
	//用户的更新的话,关系到用户表和用户与角色的中间表
	@RequiresPermissions("user:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Result userEdit(SysUserVo userVo) {
		Result result = new Result();
		try {
			sysUserService.updateSysUser(userVo);
			result.setMsg("编辑成功");
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			
			result.setMsg("编辑失败");
			return result;
			
		}
		
	}
	
	
	
	
}
