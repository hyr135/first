package com.education.system.common.entity;

import java.io.Serializable;
import java.util.List;

public class LeftMenu implements Serializable{
	
	/**
	 * "title" : "二级菜单演示", "icon" : "&#xe61c;", "href" : "", "spread" : false,
	 * "children" : [
	 */	
	private Integer id;
	private String title;
	private String icon;
	private String href;
	private boolean spread;
	private List<LeftMenu> childrens;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public boolean isSpread() {
		return spread;
	}
	public void setSpread(boolean spread) {
		this.spread = spread;
	}
	public List<LeftMenu> getChildrens() {
		return childrens;
	}
	public void setChildrens(List<LeftMenu> childrens) {
		this.childrens = childrens;
	}
	@Override
	public String toString() {
		return "LeftMenu [id=" + id + ", title=" + title + ", icon=" + icon + ", href=" + href + ", spread=" + spread
				+ ", childrens=" + childrens + "]";
	}
	
	
	
}
