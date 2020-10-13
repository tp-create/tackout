package cn.htzb.easybuy.entity;

import java.io.Serializable;


public class ProductCategory implements Serializable{
	private Long id;//ID
	private String name;//名称
	private Long parentId;//父级ID

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
