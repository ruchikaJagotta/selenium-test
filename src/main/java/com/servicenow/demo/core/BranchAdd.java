package com.servicenow.demo.core;

public class BranchAdd {
	private String name;
	private String code;

	public BranchAdd() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String toString() {
		return "BranchAdd [ name =" + name + ", code = " + code + "]";
	}
}