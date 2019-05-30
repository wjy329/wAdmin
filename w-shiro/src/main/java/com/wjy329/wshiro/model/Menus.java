package com.wjy329.wshiro.model;

import java.util.List;

public class Menus {

	private String title;
	private String icon;
	private String href = "";
	private List<Menus> children ;
	private Long pid;
	private Long id;
	private String order ;

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

	public List<Menus> getChildren() {
		return children;
	}

	public void setChildren(List<Menus> children) {
		this.children = children;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
