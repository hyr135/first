package com.education.system.common.entity;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PageInfo {
	
	//默认显示的记录数
	private static final int PAGESIZE = 10;
	
	private int total; // 总记录,使用它来计算当前页的页码数
	private List rows; // 显示的记录
	
	// @JsonIgnore 在json序列化时将java bean中的一些属性忽略掉，序列化和反序列化都受影响
	
	@JsonIgnore
	private int from;
	@JsonIgnore
	private int size;
	@JsonIgnore
	private int nowpage; // 当前页
	@JsonIgnore
	private int pagesize; // 每页显示的条数
	@JsonIgnore
	private Map<String, Object> condition; // 查询条件

	@JsonIgnore
	private String sort = "seq"; // 排序字段
	@JsonIgnore
	private String order = "asc";// asc，desc mybatis Order 关键字
	public PageInfo() {
		super();
	}
	
	//使用默认的构造函数
	public PageInfo(int nowpage,int pagesize) {
		//计算当前页
		if (nowpage <= 0) {
			this.nowpage = 1;
		}else {
			this.nowpage =nowpage;
			
		}
		
		//记录每页显示的记录数量
		if (pagesize < 0) {
			
			this.pagesize = PAGESIZE;
		}else {
			
			this.pagesize = pagesize;
		}
		//计算开始的记录和结束的记录,当前页数-1 乘以每页的开始数.
		this.from = (this.nowpage -1) * this.pagesize;
		this.size = this.pagesize;
	}
	
	//构造函数
	public PageInfo(int nowpage, int pagesize, String sort, String order) {
		// 计算当前页
		if (nowpage < 0) {
			this.nowpage = 1;
		} else {
			// 当前页
			this.nowpage = nowpage;
		}
		// 记录每页显示的记录数
		if (pagesize < 0) {
			this.pagesize = PAGESIZE;
		} else {
			this.pagesize = pagesize;
		}
		// 计算开始的记录和结束的记录
		this.from = (this.nowpage - 1) * this.pagesize;
		this.size = this.pagesize;
		// 排序字段，正序还是反序
		this.sort = sort;
		this.order = order;
	}
	
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getNowpage() {
		return nowpage;
	}
	public void setNowpage(int nowpage) {
		this.nowpage = nowpage;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public Map<String, Object> getCondition() {
		return condition;
	}
	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	
	
}
