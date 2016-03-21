package com.base.model;

import java.io.Serializable;

/**
 * 基础数据类型表<br>
 */
public class DicType implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典类型ID
	 */
	private String dicTypeId;
	/**
	 * 字典类型名称
	 */
	private String dicTypeName = "";
	/**
	 * 字典类型描述
	 */
	private String dicTypeDesc = "";

	public String getDicTypeDesc() {
		return dicTypeDesc;
	}

	public void setDicTypeDesc(String dicTypeDesc) {
		this.dicTypeDesc = dicTypeDesc;
	}

	public String getDicTypeId() {
		return dicTypeId;
	}

	public void setDicTypeId(String dicTypeId) {
		this.dicTypeId = dicTypeId;
	}

	public String getDicTypeName() {
		return dicTypeName;
	}

	public void setDicTypeName(String dicTypeName) {
		this.dicTypeName = dicTypeName;
	}

}
