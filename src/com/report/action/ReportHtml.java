package com.report.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.base.util.Constants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class ReportHtml implements Action,ServletResponseAware{
	private HttpServletResponse response;
	private String submitNodes;

	public String execute() throws Exception{
		String path = Constants.webRealPath+"file/report/";
		File sourceFile = new File(path+submitNodes+".jrprint");
		JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);
		
		JRHtmlExporter exporter = new JRHtmlExporter();
		ActionContext ctx = ActionContext.getContext();		
		ctx.getSession().put(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "/servlets/image?image=");
		exporter.exportReport();
		return SUCCESS;
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String getSubmitNodes() {
		return submitNodes;
	}
	public void setSubmitNodes(String submitNodes) {
		this.submitNodes = submitNodes;
	}
}
