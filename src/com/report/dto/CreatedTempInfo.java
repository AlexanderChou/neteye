package com.report.dto;

import java.io.Serializable;

public class CreatedTempInfo implements Serializable {
	private String createdFile;

	public String getCreatedFile() {
		return createdFile;
	}

	public void setCreatedFile(String createdFile) {
		this.createdFile = createdFile;
	}
}
