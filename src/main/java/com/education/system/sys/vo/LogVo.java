package com.education.system.sys.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 次实体类pojo是一个视图对象,为了简化在sql语句进行结果集响应是的麻烦,直接创建一个可以承接的实体类比较方便.
 * @author Dell
 *
 */
public class LogVo implements Serializable{
	private static final long serialVersionUID = -4388138342174328269L;
	/**
	 * 日志id
	 */
	private Long id;
	/**
	 * 用户账号
	 */
	private String loginName;
	/**
	 * 请求名称
	 */
	private String operationName;
	/**
	 * 请求类
	 */
	private String operationClass;
	/**
	 * 请求路径
	 */
	private String operationAddress;
	/**
	 * 请求参数
	 */
	private String operationParams;
	/**
	 * 访问ip地址
	 */
	private String operationIp;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 修改人
	 */
	private Integer updatePerson;
	/**
	 * 起始时间
	 */
	private Date createdateStart;
	/**
	 * 终止时间
	 */
	private Date createdateEnd;
	/**
	 * 选择的日期(年 / 月 / 日)
	 */
	private String time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName == null ? null : loginName.trim();
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName == null ? null : operationName.trim();
	}

	public String getOperationClass() {
		return operationClass;
	}

	public void setOperationClass(String operationClass) {
		this.operationClass = operationClass == null ? null : operationClass.trim();
	}

	public String getOperationAddress() {
		return operationAddress;
	}

	public void setOperationAddress(String operationAddress) {
		this.operationAddress = operationAddress == null ? null : operationAddress.trim();
	}

	public String getOperationParams() {
		return operationParams;
	}

	public void setOperationParams(String operationParams) {
		this.operationParams = operationParams;
	}

	public String getOperationIp() {
		return operationIp;
	}

	public void setOperationIp(String operationIp) {
		this.operationIp = operationIp;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(Integer updatePerson) {
		this.updatePerson = updatePerson;
	}

	public Date getCreatedateStart() {
		return createdateStart;
	}

	public void setCreatedateStart(Date createdateStart) {
		this.createdateStart = createdateStart;
	}

	public Date getCreatedateEnd() {
		return createdateEnd;
	}

	public void setCreatedateEnd(Date createdateEnd) {
		this.createdateEnd = createdateEnd;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
