package com.report.action;

import com.report.bpo.ReportBPO;
import com.report.dto.ReportTemp;
import com.opensymphony.xwork2.ActionSupport;

public class ReportAdd extends ActionSupport {
    private String faultIP;
    private String flowIP;
    private String webIP;
    private String dataIP;
    private String template;
    private String comment;//点评
    private String events;//重要事件
    private String fault;//主干线路故障率
    private String usability;//核心节点可用性
    private String faultOfLine;//接入线路故障率
    private String usabilityOfCNGI;//CNGI-6IX核心链路可用率
    private String sumOfLine;//主干传输线路事件统计
    private String sumOfDevice;//主干运行设备事件统计
    private String flowOfInput;//主干网入流量分布
    private String flowOfOutput;//主干网出流量分布
    private String flowOfBorder;//CERNET2边界流量
    private String flowOfInternet;//流量曲线
    private String flowOfIntranet;//CERNET2网内流量
    private String flowOfBJ;//北京交换中心流量
    private String flowOfSH;//上海交换中心流量
    private String faultTitle;
    private String usabilityTitle;
    private String faultOfLineTitle;
    private String usabilityOfCNGITitle;
    private String sumOfLineTitle;
    private String sumOfDeviceTitle;
    private String flowOfInputTitle;
    private String flowOfOutputTitle;
    private String flowOfBorderTitle;
    private String flowOfInternetTitle;
    private String flowOfIntranetTitle;
    private String flowOfBJTitle;
    private String flowOfSHTitle;
    
	public  String execute() throws Exception{
		ReportTemp temp = new ReportTemp();
		temp.setComment(comment);
		temp.setDataIP(dataIP);
		temp.setWebIP(webIP);
		temp.setEvents(events);
		temp.setFault(fault);
		temp.setFaultIP(faultIP);
		temp.setFaultOfLine(faultOfLine);
		temp.setFaultOfLineTitle(faultOfLineTitle);
		temp.setFaultTitle(faultTitle);
		temp.setFlowIP(flowIP);
		temp.setFlowOfBJ(flowOfBJ);
		temp.setFlowOfBJTitle(flowOfBJTitle);
		temp.setFlowOfBorder(flowOfBorder);
		temp.setFlowOfBorderTitle(flowOfBorderTitle);
		temp.setFlowOfInput(flowOfInput);
		temp.setFlowOfInputTitle(flowOfInputTitle);
		temp.setFlowOfInternet(flowOfInternet);
		temp.setFlowOfInternetTitle(flowOfInternetTitle);
		temp.setFlowOfIntranet(flowOfIntranet);
		temp.setFlowOfIntranetTitle(flowOfIntranetTitle);
		temp.setFlowOfOutput(flowOfOutput);
		temp.setFlowOfOutputTitle(flowOfOutputTitle);
		temp.setFlowOfSH(flowOfSH);
		temp.setFlowOfSHTitle(flowOfSHTitle);
		temp.setSumOfDevice(sumOfDevice);
		temp.setSumOfDeviceTitle(sumOfDeviceTitle);
		temp.setSumOfLine(sumOfLine);
		temp.setSumOfLineTitle(sumOfLineTitle);
		temp.setUsability(usability);
		temp.setUsabilityOfCNGI(usabilityOfCNGI);
		temp.setUsabilityOfCNGITitle(usabilityOfCNGITitle);
		temp.setUsabilityTitle(usabilityTitle);
		temp.setTemplate(template);
		new ReportBPO().reportConfig(temp);
		addActionMessage("恭喜恭喜，创建报告成功！");
		return SUCCESS;
	}
    
	public String getFlowOfBorder() {
		return flowOfBorder;
	}

	public void setFlowOfBorder(String flowOfBorder) {
		this.flowOfBorder = flowOfBorder;
	}

	public String getFlowOfInternet() {
		return flowOfInternet;
	}

	public void setFlowOfInternet(String flowOfInternet) {
		this.flowOfInternet = flowOfInternet;
	}

	public String getFlowOfIntranet() {
		return flowOfIntranet;
	}

	public void setFlowOfIntranet(String flowOfIntranet) {
		this.flowOfIntranet = flowOfIntranet;
	}

	public String getFlowOfBJ() {
		return flowOfBJ;
	}

	public void setFlowOfBJ(String flowOfBJ) {
		this.flowOfBJ = flowOfBJ;
	}

	public String getFlowOfSH() {
		return flowOfSH;
	}

	public void setFlowOfSH(String flowOfSH) {
		this.flowOfSH = flowOfSH;
	}

	public String getFaultTitle() {
		return faultTitle;
	}

	public void setFaultTitle(String faultTitle) {
		this.faultTitle = faultTitle;
	}

	public String getUsabilityTitle() {
		return usabilityTitle;
	}

	public void setUsabilityTitle(String usabilityTitle) {
		this.usabilityTitle = usabilityTitle;
	}

	public String getFaultOfLineTitle() {
		return faultOfLineTitle;
	}

	public void setFaultOfLineTitle(String faultOfLineTitle) {
		this.faultOfLineTitle = faultOfLineTitle;
	}

	public String getUsabilityOfCNGITitle() {
		return usabilityOfCNGITitle;
	}

	public void setUsabilityOfCNGITitle(String usabilityOfCNGITitle) {
		this.usabilityOfCNGITitle = usabilityOfCNGITitle;
	}

	public String getSumOfLineTitle() {
		return sumOfLineTitle;
	}

	public void setSumOfLineTitle(String sumOfLineTitle) {
		this.sumOfLineTitle = sumOfLineTitle;
	}

	public String getSumOfDeviceTitle() {
		return sumOfDeviceTitle;
	}

	public void setSumOfDeviceTitle(String sumOfDeviceTitle) {
		this.sumOfDeviceTitle = sumOfDeviceTitle;
	}

	public String getFlowOfInputTitle() {
		return flowOfInputTitle;
	}

	public void setFlowOfInputTitle(String flowOfInputTitle) {
		this.flowOfInputTitle = flowOfInputTitle;
	}

	public String getFlowOfOutputTitle() {
		return flowOfOutputTitle;
	}

	public void setFlowOfOutputTitle(String flowOfOutputTitle) {
		this.flowOfOutputTitle = flowOfOutputTitle;
	}

	public String getFlowOfBorderTitle() {
		return flowOfBorderTitle;
	}

	public void setFlowOfBorderTitle(String flowOfBorderTitle) {
		this.flowOfBorderTitle = flowOfBorderTitle;
	}

	public String getFlowOfInternetTitle() {
		return flowOfInternetTitle;
	}

	public void setFlowOfInternetTitle(String flowOfInternetTitle) {
		this.flowOfInternetTitle = flowOfInternetTitle;
	}

	public String getFlowOfIntranetTitle() {
		return flowOfIntranetTitle;
	}

	public void setFlowOfIntranetTitle(String flowOfIntranetTitle) {
		this.flowOfIntranetTitle = flowOfIntranetTitle;
	}

	public String getFlowOfBJTitle() {
		return flowOfBJTitle;
	}

	public void setFlowOfBJTitle(String flowOfBJTitle) {
		this.flowOfBJTitle = flowOfBJTitle;
	}

	public String getFlowOfSHTitle() {
		return flowOfSHTitle;
	}

	public void setFlowOfSHTitle(String flowOfSHTitle) {
		this.flowOfSHTitle = flowOfSHTitle;
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
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	public String getFault() {
		return fault;
	}

	public void setFault(String fault) {
		this.fault = fault;
	}

	public String getUsability() {
		return usability;
	}

	public void setUsability(String usability) {
		this.usability = usability;
	}

	public String getFaultOfLine() {
		return faultOfLine;
	}

	public void setFaultOfLine(String faultOfLine) {
		this.faultOfLine = faultOfLine;
	}

	public String getUsabilityOfCNGI() {
		return usabilityOfCNGI;
	}

	public void setUsabilityOfCNGI(String usabilityOfCNGI) {
		this.usabilityOfCNGI = usabilityOfCNGI;
	}

	public String getSumOfLine() {
		return sumOfLine;
	}

	public void setSumOfLine(String sumOfLine) {
		this.sumOfLine = sumOfLine;
	}

	public String getSumOfDevice() {
		return sumOfDevice;
	}

	public void setSumOfDevice(String sumOfDevice) {
		this.sumOfDevice = sumOfDevice;
	}

	public String getFlowOfInput() {
		return flowOfInput;
	}

	public void setFlowOfInput(String flowOfInput) {
		this.flowOfInput = flowOfInput;
	}

	public String getFlowOfOutput() {
		return flowOfOutput;
	}

	public void setFlowOfOutput(String flowOfOutput) {
		this.flowOfOutput = flowOfOutput;
	}	
	
}
