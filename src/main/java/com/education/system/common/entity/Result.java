package com.education.system.common.entity;

import java.io.Serializable;
/**
 * 实体类，用来承装登录时候的信息，用于在前端进行数据的提取，这是一个通用的响应结果的
 * 存放结果的实体类.在哪个层都可以使用来进行数据的返回.主要是对那些进行增删改之后成功或者失败信息
 * 的反馈.
 * 方便数据的呈现
 * @author Dell
 *
 */
public class Result implements Serializable {
	private static final int SUCCESS = 1;
	private static final int FAILURE = -1;

	private static final long serialVersionUID = 5576237395711742681L;

	private boolean success = false;

	private String msg = "";

	private Object obj = null;
	//如果变量类型是boolean类型的话就会使得其生成的get方式是is开头的
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public static int getSuccess() {
		return SUCCESS;
	}

	public static int getFailure() {
		return FAILURE;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	

}
