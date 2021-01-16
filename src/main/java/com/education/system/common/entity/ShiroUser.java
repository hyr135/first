package com.education.system.common.entity;

import java.io.Serializable;
import java.util.List;

public class ShiroUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 202011111501L;
	
	public Integer id;
	public String loginName;
	public String userName;
	public List<Integer> roleList;
	
	//生成有参数构造器
	public ShiroUser(Integer id, String loginName, String userName, List<Integer> roleList) {
		this.id = id;
		this.loginName = loginName;
		this.userName = userName;
		this.roleList = roleList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Integer> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Integer> roleList) {
		this.roleList = roleList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * 重写toString
	 * 
	 */
	@Override
	public String toString() {
		return "ShiroUser [loginName=" + loginName + "]";
	}
	

}
