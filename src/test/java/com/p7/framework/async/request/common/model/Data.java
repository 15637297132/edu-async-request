package com.p7.framework.async.request.common.model;

/**
 * @author Yangzhen
 * @Description
 * @date 2018-12-24 9:41
 **/
public class Data {

	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Data [id=" + id + ", name=" + name + "]";
	}

}
