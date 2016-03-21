package com.report.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRXmlUtils;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.w3c.dom.Document;

import com.base.util.Constants;
import com.report.bpo.ReportBPO;
import com.report.util.FileUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class ReportOutput implements Action,ServletResponseAware{
	private HttpServletResponse response;
	private String type;
	private String year;
	private String number;
	private String template;
	
	public String execute() throws Exception{
		String path =  Constants.webRealPath+"file/report/";
		String typeValue = "CERNET2"+year+"年第"+number+"周运行周报";
		if(type.trim().equals("year")){
			typeValue = "CERNET2"+year+"年运行年报";
		}else if(type.trim().equals("month")){
			typeValue = "CERNET2"+year+"年"+number+"月运行月报";
		}
		
		new ReportBPO().createReport(template,type,year,number);
		
		JasperCompileManager.compileReportToFile(path+"CustomersReport.jrxml" , path+template+".jasper");
		JasperCompileManager.compileReportToFile(path+"flow_report.jrxml" , path+"flow_report.jasper");
		JasperCompileManager.compileReportToFile(path+"run_report.jrxml" , path+"run_report.jasper");
		JasperCompileManager.compileReportToFile(path+"run12_report.jrxml" , path+"run12_report.jasper");
		JasperCompileManager.compileReportToFile(path+"run13_report.jrxml" , path+"run13_report.jasper");
		JasperCompileManager.compileReportToFile(path+"curves_report.jrxml" , path+"curves_report.jasper");
		Document document = JRXmlUtils.parse(JRLoader.getLocationInputStream(path+template+".xml"));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
		params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
		params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.##");
		params.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.ENGLISH);
		params.put(JRParameter.REPORT_LOCALE, Locale.US);	
		params.put("path", path);
		params.put("reportType", typeValue);
		JasperFillManager.fillReportToFile(path+template+".jasper",path+template+".jrprint",params);				
		File sourceFile = new File(path+template+".jrprint");
		JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);			
		File destFile = new File(sourceFile.getParent(), template + ".rtf");		
		JRRtfExporter exporter = new JRRtfExporter();		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());		
		exporter.exportReport();
		
		JRHtmlExporter exporter1 = new JRHtmlExporter();
		ActionContext ctx = ActionContext.getContext();		
		ctx.getSession().put(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		exporter1.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter1.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		exporter1.setParameter(JRHtmlExporterParameter.IMAGES_URI, "/inetboss/servlets/image?image=");
		exporter1.exportReport();
		
		new FileUtil().deleteFile(template);
		return SUCCESS;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
}
