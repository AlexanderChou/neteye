package com.base.model;

public class Attachment extends BaseEntity{
	private Long ticket;
	private String title;
	private String content;
	private String fileName;
	private String filePath;
	public Long getTicket() {
		return ticket;
	}
	public void setTicket(Long ticket) {
		this.ticket = ticket;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
