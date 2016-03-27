package com.view.dto;

import java.io.Serializable;

public class Icon implements Serializable{
	private Long id;
	private String iconFile;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIconFile() {
		return iconFile;
	}
	public void setIconFile(String iconFile) {
		this.iconFile = iconFile;
	}
}
