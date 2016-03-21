package com.report.action;

import com.opensymphony.xwork2.ActionSupport;
import com.report.bpo.ReportBPO;
import com.report.dto.ReportSelfTemp;

public class ReportSelfAdd extends ActionSupport {
	private String faultIP;
	private String flowIP;
	private String webIP;
	private String dataIP;
	private String template;
	private String[] events;
	private String[] eventsTitle;
	private String[] faults;
	private String[] faultsTitle;
	private String[] faultsImage;
	private String[] sums;
	private String[] sumsTitle;
	private String[] sumsImage;
	private String[] outputs;
	private String[] outputsTitle;
	private String[] outputsImage;
	private String[] curvers;
	private String[] curversTitle;
	private boolean success;

	public String execute() throws Exception {
		ReportSelfTemp temp = new ReportSelfTemp();
		temp.setDataIP(dataIP);
		temp.setWebIP(webIP);
		temp.setFaultIP(faultIP);
		temp.setFlowIP(flowIP);
		temp.setEvents(events);
		temp.setEventsTitle(eventsTitle);
		temp.setFaults(faults);
		temp.setFaultsTitle(faultsTitle);
		temp.setFaultsImage(faultsImage);
		temp.setSums(sums);
		temp.setSumsTitle(sumsTitle);
		temp.setSumsImage(sumsImage);
		temp.setOutputs(outputs);
		temp.setOutputsTitle(outputsTitle);
		temp.setOutputsImage(outputsImage);
		temp.setCurvers(curvers);
		temp.setCurversTitle(curversTitle);
		temp.setTemplate(template);
		new ReportBPO().reportSelfConfig(temp);
		success = true;
		return SUCCESS;
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

	public String[] getEvents() {
		return events;
	}

	public void setEvents(String[] events) {
		this.events = events;
	}

	public String[] getEventsTitle() {
		return eventsTitle;
	}

	public void setEventsTitle(String[] eventsTitle) {
		this.eventsTitle = eventsTitle;
	}

	public String[] getFaults() {
		return faults;
	}

	public void setFaults(String[] faults) {
		this.faults = faults;
	}

	public String[] getFaultsTitle() {
		return faultsTitle;
	}

	public void setFaultsTitle(String[] faultsTitle) {
		this.faultsTitle = faultsTitle;
	}

	public String[] getFaultsImage() {
		return faultsImage;
	}

	public void setFaultsImage(String[] faultsImage) {
		this.faultsImage = faultsImage;
	}

	public String[] getSums() {
		return sums;
	}

	public void setSums(String[] sums) {
		this.sums = sums;
	}

	public String[] getSumsTitle() {
		return sumsTitle;
	}

	public void setSumsTitle(String[] sumsTitle) {
		this.sumsTitle = sumsTitle;
	}

	public String[] getSumsImage() {
		return sumsImage;
	}

	public void setSumsImage(String[] sumsImage) {
		this.sumsImage = sumsImage;
	}

	public String[] getOutputs() {
		return outputs;
	}

	public void setOutputs(String[] outputs) {
		this.outputs = outputs;
	}

	public String[] getOutputsTitle() {
		return outputsTitle;
	}

	public void setOutputsTitle(String[] outputsTitle) {
		this.outputsTitle = outputsTitle;
	}

	public String[] getOutputsImage() {
		return outputsImage;
	}

	public void setOutputsImage(String[] outputsImage) {
		this.outputsImage = outputsImage;
	}

	public String[] getCurvers() {
		return curvers;
	}

	public void setCurvers(String[] curvers) {
		this.curvers = curvers;
	}

	public String[] getCurversTitle() {
		return curversTitle;
	}

	public void setCurversTitle(String[] curversTitle) {
		this.curversTitle = curversTitle;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
