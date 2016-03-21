package com.base.model;

import com.base.model.BaseEntity;

public class Report extends BaseEntity implements Comparable{
	private String name;//名称
	private String description;//描述
	private String images;//ͼ图片类型
	private String content;//内容
	private String myTemplate;//模板名称
	private String flag;//内容类型
	private String templateType;//模板类型

	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMyTemplate() {
		return myTemplate;
	}
	public void setMyTemplate(String myTemplate) {
		this.myTemplate = myTemplate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int compareTo(Object object) 
	{
		if(!(object instanceof Report))
			return 0;
		Report report = (Report)object;
		
		return this.getName().compareTo(report.getName());
	}
}
