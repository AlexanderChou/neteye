package com.base.model;

public class Configure extends BaseEntity implements Comparable{
	private String myTemplate;
	private String faultIP;
	private String flowIP;
	private String webIP;
	private String dataIP;
	private String templateType;//模板类型
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getMyTemplate() {
		return myTemplate;
	}
	public void setMyTemplate(String myTemplate) {
		this.myTemplate = myTemplate;
	}
	public String getFaultIP() {
		return faultIP;
	}
	public void setFaultIP(String faultIP) {
		this.faultIP = faultIP;
	}
	public String getFlowIP() {
		return flowIP;
	}
	public void setFlowIP(String flowIP) {
		this.flowIP = flowIP;
	}
	public String getWebIP() {
		return webIP;
	}
	public void setWebIP(String webIP) {
		this.webIP = webIP;
	}
	public String getDataIP() {
		return dataIP;
	}
	public void setDataIP(String dataIP) {
		this.dataIP = dataIP;
	}
	public int compareTo(Object object) 
	{
		if(!(object instanceof Report))
			return 0;
		Configure configure = (Configure)object;
		
		return this.getMyTemplate().compareTo(configure.getMyTemplate());
	}
}
