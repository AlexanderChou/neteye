package com.base.model;

import java.io.Serializable;

/**
 * 数据字典详细表<br>
 */
public class DicContent implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典类容ID
	 */
	private Long dicContenId;
	/**
	 * 字典类型ID
	 */
	private String dicTypeId;

	/**
	 * 字典类容名称
	 */
	private String dicContentName = "";
	/**
	 * 字典类容描述
	 */
	private String dicContentDesc = "";
	/**
	 * 排序
	 */
	private long dicContentOrder = 0;

	public long getDicContentOrder() {
		return dicContentOrder;
	}

	public void setDicContentOrder(long dicContentOrder) {
		this.dicContentOrder = dicContentOrder;
	}

	public String getDicContentDesc() {
		return dicContentDesc;
	}

	public void setDicContentDesc(String dicContentDesc) {
		this.dicContentDesc = dicContentDesc;
	}

	public String getDicContentName() {
		return dicContentName;
	}

	public void setDicContentName(String dicContentName) {
		this.dicContentName = dicContentName;
	}

	public Long getDicContenId() {
		return dicContenId;
	}

	public void setDicContenId(Long dicContenId) {
		this.dicContenId = dicContenId;
	}

	public String getDicTypeId() {
		return dicTypeId;
	}

	public void setDicTypeId(String dicTypeId) {
		this.dicTypeId = dicTypeId;
	}

}
