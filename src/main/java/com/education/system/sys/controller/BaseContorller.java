package com.education.system.sys.controller;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.education.system.common.entity.ShiroUser;
import com.education.system.common.util.DateUtilEx;
import com.education.system.common.util.StringEscapeEditor;
import com.education.system.sys.entity.SysUser;
import com.education.system.sys.service.ISysUserService;
import com.education.system.sys.service.impl.SysUserServiceImpl;



public class BaseContorller {
	
	//注入业务层依赖,需要注意的是，这一个层注入的是实现类而不是接口，要不然接口是一对多的所以要明确注入的是哪一个
	@Autowired
	private SysUserServiceImpl userService; 
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 使用自定义的 DATE数据绑定类
		binder.registerCustomEditor(Date.class, new DateUtilEx());
		/**
		 * 防止XSS攻击
		 */
		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
	}
	/**
	 * 获取当前登录对象
	 * @return
	 */
	public SysUser getCurrentUser() {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		SysUser currentUser = userService.findSysUserById(shiroUser.id);
		return currentUser;
	}
	
	/**
	 * 获取当前登录用户id
	 * 
	 * @return
	 */
	public Integer getUserId() {
		return this.getCurrentUser().getId();
	}
	
	/**
	 * 获取当前登录账号
	 * 
	 * @return
	 */
	public String getStaffName() {
		return this.getCurrentUser().getLoginName();
	}
	
	/**
	 * 获取当前用户 性别
	 */
	public Integer getUserGender() {
		return this.getCurrentUser().getUserGender();
	}
}
