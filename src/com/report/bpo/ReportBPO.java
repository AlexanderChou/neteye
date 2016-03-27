package com.report.bpo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlrpc.XmlRpcClient;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.TextAnchor;

import com.base.model.Configure;
import com.base.model.FaultHistory;
import com.base.model.Report;
import com.base.util.Constants;
import com.base.util.DateUtil;
import com.base.util.FileUtil;
import com.base.util.HibernateUtil;
import com.base.util.JDOMXML;
import com.report.dao.ConfigureDAO;
import com.report.dao.ReportDAO;
import com.report.dto.ReportSelfTemp;
import com.report.dto.ReportTemp;
import com.report.util.ImageUtil;
import com.report.util.StringUtil;
import com.report.util.WeekUtil;

public class ReportBPO {
	private ReportDAO dao = new ReportDAO();
	private ConfigureDAO configureDAO = new ConfigureDAO();
	private ImageUtil imageUtil = new ImageUtil();
	private DateUtil dateUtil = new DateUtil();
	private String path = Constants.webRealPath + "file/report/";
	private String webIP = "";
	private String dataIP = "";
	private String PerformanceXmlRpcURL = "";
	private String FaultPerlRpcURL = "";

	public void reportSelfConfig(ReportSelfTemp temp) throws Exception {
		if (temp.getEventsTitle() != null) {// 描述性事务
			for (int i = 0; i < temp.getEventsTitle().length; i++) {
				if (temp.getEventsTitle()[i] != null
						&& !temp.getEventsTitle()[i].equals("")) {
					Report report = new Report();
					report.setName(temp.getEventsTitle()[i]);
					report.setContent(temp.getEvents()[i]);
					report.setMyTemplate(temp.getTemplate());
					report.setFlag("S1");
					report.setTemplateType("2");// 2:用户自定义的模板类型
					dao.create(report);
				}
			}
		}
		if (temp.getSumsTitle() != null) {// 事件统计
			for (int i = 0; i < temp.getSumsTitle().length; i++) {
				if (temp.getSumsTitle()[i] != null
						&& !temp.getSumsTitle()[i].equals("")) {
					Report report = new Report();
					report.setName(temp.getSumsTitle()[i]);
					report.setContent(temp.getSums()[i]);
					report.setMyTemplate(temp.getTemplate());
					report.setFlag("S2_1");
					report.setTemplateType("2");
					report.setImages(temp.getSumsImage()[i]);
					dao.create(report);
				}
			}

		}
		if (temp.getFaultsTitle() != null) {// 故障率和可用性
			for (int i = 0; i < temp.getFaultsTitle().length; i++) {
				if (temp.getFaultsTitle()[i] != null
						&& !temp.getFaultsTitle()[i].equals("")) {
					Report report = new Report();
					report.setName(temp.getFaultsTitle()[i]);
					report.setContent(temp.getFaults()[i]);
					report.setMyTemplate(temp.getTemplate());
					report.setFlag("S2_2");
					report.setTemplateType("2");
					if (temp.getFaultsImage() == null) {
						report.setImages("1");
					}
					dao.create(report);
				}
			}
		}
		if (temp.getOutputsTitle() != null) {// 入出流量
			for (int i = 0; i < temp.getOutputsTitle().length; i++) {
				if (temp.getOutputsTitle()[i] != null
						&& !temp.getOutputsTitle()[i].equals("")) {
					Report report = new Report();
					report.setName(temp.getOutputsTitle()[i]);
					report.setContent(temp.getOutputs()[i]);
					report.setMyTemplate(temp.getTemplate());
					report.setFlag("S2_3");
					report.setTemplateType("2");
					if (temp.getOutputsImage() == null) {
						report.setImages("1");
					}
					dao.create(report);
				}
			}
		}
		if (temp.getCurversTitle() != null) {// 流量曲线
			for (int i = 0; i < temp.getCurversTitle().length; i++) {
				if (temp.getCurversTitle()[i] != null
						&& !temp.getCurversTitle()[i].equals("")) {
					Report report = new Report();
					report.setName(temp.getCurversTitle()[i]);
					report.setContent(temp.getCurvers()[i]);
					report.setMyTemplate(temp.getTemplate());
					report.setFlag("S3");
					report.setTemplateType("2");
					dao.create(report);
				}
			}
		}
		// 同时将数据来源的IP地址值写入configure表中
		Configure configure = configureDAO.hasTemplate(temp.getTemplate(), "2");// 根据template查看该记录是否存在
		if (configure != null) {// 更新
			configure.setDataIP(temp.getDataIP());
			configure.setFaultIP(temp.getFaultIP());
			configure.setFlowIP(temp.getFlowIP());
			configure.setWebIP(temp.getWebIP());
			configureDAO.updateConfigure(configure);
		} else {// 添加
			configure = new Configure();
			configure.setMyTemplate(temp.getTemplate());
			configure.setDataIP(temp.getDataIP());
			configure.setFaultIP(temp.getFaultIP());
			configure.setFlowIP(temp.getFlowIP());
			configure.setWebIP(temp.getWebIP());
			configure.setTemplateType("2");
			configureDAO.saveConfigure(configure);
		}
	}

	public void createSelfReport(String template, String type, String start,
			String end) throws Exception {
		int count = 0;
		String firstName = "";
		String secondName = "";
		// 根据configure表获得webIP和dataIP的值
		Configure configure = configureDAO.hasTemplate(template, "2");
		if (configure != null) {
			webIP = configure.getWebIP();
			dataIP = configure.getDataIP();
			PerformanceXmlRpcURL = "http://" + configure.getFlowIP() + ":9000";
			FaultPerlRpcURL = "http://" + configure.getFaultIP() + ":9988";
		}
		StringBuffer xml = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		xml.append("<report>\n");
		xml.append("<project>");
		xml.append("<projectName>重要事件</projectName>");
		xml
				.append("<projectName1>运行统计</projectName1><projectName2>流量曲线</projectName2>");
		xml
				.append("<projectName12>运行统计12</projectName12><projectName13>运行统计13</projectName13>");
		String sql = "from Report rp where (rp.flag='S1' and rp.templateType='2' and rp.myTemplate='"
				+ template + "')";
		List<Report> reportsOfDesc = dao.getReports(sql);
		if (reportsOfDesc != null && reportsOfDesc.size() > 0) {
			count = reportsOfDesc.size();
			xml.append("<runTitle>");
			xml.append(StringUtil.changeNum(count) + "运行统计");
			xml.append("</runTitle>");
			xml.append("<curvesTitle>");
			xml.append(StringUtil.changeNum(count + 1) + "流量曲线");
			xml.append("</curvesTitle>");
		} else {
			xml.append("<runTitle>");
			xml.append("一、运行统计");
			xml.append("</runTitle>");
			xml.append("<curvesTitle>");
			xml.append("二、流量曲线");
			xml.append("</curvesTitle>");
		}
		xml.append("</project>");
		count = 0;
		if (reportsOfDesc != null && reportsOfDesc.size() > 0) {
			for (Report report : reportsOfDesc) {
				if (report.getFlag().equals("S1")) {// 重要事件
					xml.append("<descs>");
					xml.append("<projectName>重要事件</projectName>");
					xml.append("<eventsTitle>");
					// 由count数值转化为相应的中文数字
					xml.append(StringUtil.changeNum(count) + report.getName());
					xml.append("</eventsTitle>");
					xml.append("<events>");
					xml.append(report.getContent());
					xml.append("</events>");
					xml.append("</descs>");
					count++;
				}
			}
		}
		String sql0 = " from Report rp where ((rp.flag='S2_1' or rp.flag='S2_2' or rp.flag='S2_3') and rp.templateType='2' and rp.myTemplate='"
				+ template + "')";
		List reports = dao.getReports(sql0);
		for (int i = 0; i < reports.size(); i++) {
			int createFlag = 0;
			Report report = (Report) reports.get(i);
			String fileName = template + Math.random() + ".jpg";
			if (report.getFlag().equals("S2_1")
					&& report.getImages().equals("1")) {// 主干线路故障率(柱状图)
				String[] contents = report.getContent().split("\n");
				String tagName = "";
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				boolean placeFlag = true;
				for (int j = 0; j < contents.length; j++) {
					if (!contents[j].equals("")) {
						String[] content = contents[j].split("\\|\\|");
						if (content.length == 2) {
							if (placeFlag) {
								tagName = content[0];// 超过一定阀值时显示标签的位置
								placeFlag = false;
							}
							if (!content[1].trim().equals("")) {
								Double value = new Double(Double
										.valueOf(content[1]));
								dataset.addValue(value, "", content[0]);
							} else {
								dataset.addValue(0, "", content[0]);
							}
						}
					}
				}// Endof for
				categoryImage(report.getName(), fileName, dataset, tagName, "0");
				createFlag = 1;
			} else if (report.getFlag().equals("S2_2")
					&& report.getImages().equals("1")
					&& (report.getName().indexOf("故障率") != -1)) {// 接入线路故障率(柱状图)
				String[] contents = report.getContent().split("\n");
				String tagName = "";
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				boolean placeFlag = true;
				for (int j = 0; j < contents.length; j++) {
					if (!contents[j].equals("")) {
						String[] content = contents[j].split("\\|\\|");
						if (content.length >= 2) {
							if (placeFlag) {
								tagName = content[0];// 超过一定阀值时显示标签的位置
								placeFlag = false;
							}
							String IP = content[1].trim();
							if (IP != null && !"".equals(IP)) {
								// 构造数据集（由IP地址直接查询数据库faulthistory表获得该IP地址在本段时间内的故障总时间）
								List<FaultHistory> list = dao.getFaultTime(IP);
								double faultTime = 0;
								for (FaultHistory fault : list) {
									String time = fault.getPersistTime();
									if (time != null && !"".equals(time)) {
										faultTime += Double.valueOf(time);
									}
								}
								if (faultTime != 0) {
									dataset.addValue(faultTime, "", content[0]);
								} else {
									dataset.addValue(0, "", content[0]);
								}
							}
						}
					}
				}// Endof for
				categoryImage(report.getName(), fileName, dataset, tagName, "0");
				createFlag = 1;
			} else if (report.getFlag().equals("S2_2")
					&& report.getImages().equals("1")
					&& (report.getName().indexOf("可用性") != -1 || report
							.getName().indexOf("可用率") != -1)) {// 核心节点可用性||CNGI-6IX核心链路可用率
				String[] contents = report.getContent().split("\n");
				String tagName = "";
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				// Hashtable faultValues =
				// this.GetFaultValues(type,start,end,FaultPerlRpcURL);
				boolean placeFlag = true;
				for (int j = 0; j < contents.length; j++) {
					if (!contents[j].equals("")) {
						String[] content = contents[j].split("\\|\\|");
						if (content.length == 2) {
							if (placeFlag) {
								tagName = content[0];// 超过一定阀值时显示标签的位置
								placeFlag = false;
							}
							String IP = content[1].trim();
							if (IP != null && !"".equals(IP)) {
								// 构造数据集（由IP地址直接查询数据库faulthistory表获得该IP地址在本段时间内的故障总时间）
								List<FaultHistory> list = dao.getFaultTime(IP);
								double faultTime = 0;
								for (FaultHistory fault : list) {
									String time = fault.getPersistTime();
									if (time != null && !"".equals(time)) {
										faultTime += Double.valueOf(time);
									}
								}
								if (faultTime != 0) {
									dataset.addValue(100 - faultTime, "",
											content[0]);
								} else {
									dataset.addValue(0, "", content[0]);
								}
							}

						}
					}
				}// Endof for
				categoryImage(report.getName(), fileName, dataset, tagName, "1");
				createFlag = 1;
			} else if (report.getFlag().equals("S2_1")
					&& report.getImages().equals("2")) {// 主干传输线路事件统计||主干运行设备事件统计(饼状图)
				DefaultPieDataset dataset = new DefaultPieDataset();
				String[] contents = report.getContent().split("\n");
				for (int j = 0; j < contents.length; j++) {
					if (!contents[j].equals("")) {
						String[] content = contents[j].split("\\|\\|");
						if (content.length == 2) {
							if (!content[1].trim().equals("")) {
								dataset.setValue(content[0], Double
										.valueOf(content[1]));
							} else {
								dataset.setValue(content[0], 0);
							}
						}
					}
				}// Endof for
				pieImage(report.getName(), fileName, dataset);
				createFlag = 2;
			} else if (report.getFlag().equals("S2_3")) {// 主干网入/出流量分布（饼状图）
				DefaultPieDataset dataset = new DefaultPieDataset();
				// Hashtable flowValues =
				// this.GetFlowValues(type,start,end,PerformanceXmlRpcURL);
				String[] contents = report.getContent().split("\n");

				for (int j = 0; j < contents.length; j++) {
					if (!contents[j].equals("")) {
						String[] content = contents[j].split("\\|\\|");
						if (content.length == 2) {
							if (j == 0) {
								firstName = content[0];
							} else if (j == 1) {
								secondName = content[0];
							}
							String[] values = content[1].split("\\;");
							double value = 0;
							Document document = DocumentHelper.createDocument();
							Element root = document.addElement("datas");
							for (int m = 0; m < values.length; m++) {
								if (!values[m].equals("")) {
									String[] distrbute = values[m].split(",");
									if (distrbute.length >= 2) {
										String IP = distrbute[0];
										if (IP.indexOf(":") != -1) {// 如果是IPv6，将其转化IPv4
											IP = StringUtil.changeIP(IP);
										}
										Element results = root
												.addElement("data");
										Element IPResult = results
												.addElement("IP");
										IPResult.addText(IP);
										Element result = results
												.addElement("ifIndexs");
										for (int n = 1; n < distrbute.length; n++) {
											if (!distrbute[n].equals("")) {
												Element subresult = result
														.addElement("ifIndex");
												subresult.addText(distrbute[n]);
											}
										}
									}
								}
							}// Endof
							// 生成参数文件
							String statisticsFile = Constants.webRealPath
									+ "file/flow/flowscan/dat/tmp/"
									+ "reportStatic.xml";
							File file = new File(statisticsFile);
							if (file.exists()) {
								file.delete();
							}
							JDOMXML.saveXml(statisticsFile, document);
							// 参考流量统计的数据格式，生成XML文件，执行Perl命令，获得相应数据
							StringBuffer startTime = new StringBuffer();
							StringBuffer endTime = new StringBuffer();
							if ("year".equals(type)) {
								startTime.append(start);
								startTime.append("0101");

								endTime.append(start);
								endTime.append("1231");
							} else if ("month".equals(type)) {
								startTime.append(start);
								if (end.length() == 1) {
									end = "0" + end;
								}
								startTime.append(end);
								startTime.append("01");

								java.util.GregorianCalendar date = new java.util.GregorianCalendar(
										Integer.parseInt(start), Integer
												.parseInt(end), 1);
								date.add(Calendar.DATE, -1);
								endTime.append(start + end
										+ date.get(Calendar.DAY_OF_MONTH));
							} else if ("week".equals(type)) {
								startTime.append(dateUtil.getYearWeekFirstDay(
										Integer.parseInt(start), Integer
												.parseInt(end)));
								endTime.append(dateUtil.getYearWeekEndDay(
										Integer.parseInt(start), Integer
												.parseInt(end)));
							}
							String cmd = " flowstats --time " + type + "/"
									+ startTime + "/" + endTime;
							cmd += " --file " + statisticsFile;
							try {
								Process ps = java.lang.Runtime.getRuntime()
										.exec(cmd);// 执行后台命令，生成相应的统计数据
								ps.getErrorStream();
								ps.waitFor();
							} catch (java.io.IOException e) {
								e.printStackTrace();
							}
							// 解析后台生成的文件，获得相应的数值，构造数据集
							String resultFile = Constants.webRealPath
									+ "file/flow/flowscan/dat/flowstats/"
									+ "performance_Stat_" + startTime + "--"
									+ endTime + "--" + "reportStatic.txt";
							File resultDataFile = new File(resultFile);
							if (resultDataFile.exists()) {
								try {
									BufferedReader reader = new BufferedReader(
											new InputStreamReader(
													new FileInputStream(
															resultFile)));
									String line = reader.readLine();
									boolean firstList = true;
									while (StringUtils.isNotEmpty(line)) {
										if (firstList) {
											firstList = false;
											line = reader.readLine();
											continue;
										}
										String[] strArr = line.split("\t");
										if (report.getName().indexOf("入流量分布") != -1) {
											value += Double.valueOf(strArr[4]);
										} else if (report.getName().indexOf(
												"出流量分布") != -1) {
											value += Double.valueOf(strArr[5]);
										}
										line = reader.readLine();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							dataset.setValue(content[0], value);
						}
					}
				}// Endof for
				// pieImage(report.getName(),fileName,dataset,firstName,secondName);
				pieImage(report.getName(), fileName, dataset);
				createFlag = 2;
			} else {
				continue;
			}
			if (createFlag == 1) {
				xml.append("<run>");
				xml.append("<projectName1>运行统计</projectName1>");
				xml.append("<imageFile>");
				xml.append(path + fileName);
				xml.append("</imageFile>");
				xml.append("</run>");
			} else if (createFlag == 2) {
				xml.append("<run12>");
				xml.append("<projectName12>运行统计12</projectName12>");
				xml.append("<imageFile>");
				xml.append(path + fileName);
				xml.append("</imageFile>");
				xml.append("</run12>");
			}
		}// Endof for
		// 路由信息统计
		xml.append(this.getRouterModule(type, start, end, template, webIP,
				dataIP));// BGP
		String sqlOfFlow = "from Report rp where (rp.flag='S3' and rp.templateType='2' and rp.myTemplate='"
				+ template + "')";
		List<Report> reportsOfFlow = dao.getReports(sqlOfFlow);
		for (Report report : reportsOfFlow) {
			xml.append("<flow>");
			xml.append("<projectName2>");
			xml.append("流量曲线");
			xml.append("</projectName2>");
			xml.append("<modelName>");
			xml.append(report.getName());
			xml.append("</modelName>");
			xml.append("</flow>");
			String[] contents = report.getContent().split("\n");
			for (int j = 0; j < contents.length; j++) {
				if (!contents[j].equals("")) {
					String[] content = contents[j].split("\\|\\|");
					if (content.length == 2) {
						xml.append(this.getFlowModule(report.getName(),
								content[0], content[1], type, start, end,
								template, PerformanceXmlRpcURL));
					}
				}
			}// Endof for
		}
		xml.append("</report>");
		File newFile = new File(path + template + ".xml");
		FileWriter newFileStream = new FileWriter(newFile);
		newFileStream.write(xml.toString());
		newFileStream.close();
	}

	public void reportConfig(ReportTemp temp) throws Exception {
		if (temp.getComment() != null && !temp.getComment().equals("")) {
			Report report = new Report();
			report.setName("点评");
			report.setContent(temp.getComment());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("1");
			report.setTemplateType("1");
			dao.create(report);
		}
		if (temp.getEvents() != null && !temp.getEvents().equals("")) {
			Report report = new Report();
			report.setName("重要事件");
			report.setContent(temp.getEvents());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("2");
			report.setTemplateType("1");
			dao.create(report);
		}
		// 主干线路故障率
		if (temp.getFaultTitle() != null && !temp.getFaultTitle().equals("")) {
			Report report = new Report();
			report.setName(temp.getFaultTitle());
			report.setContent(temp.getFault());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("3_1");
			report.setTemplateType("1");
			dao.create(report);
		}
		// 核心节点可用性
		if (temp.getUsabilityTitle() != null
				&& !temp.getUsabilityTitle().equals("")) {
			Report report = new Report();
			report.setName(temp.getUsabilityTitle());
			report.setContent(temp.getUsability());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("3_2");
			report.setTemplateType("1");
			dao.create(report);
		}
		// 接入线路故障率
		if (temp.getFaultOfLineTitle() != null
				&& !temp.getFaultOfLineTitle().equals("")) {
			Report report = new Report();
			report.setName(temp.getFaultOfLineTitle());
			report.setContent(temp.getFaultOfLine());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("3_3");
			report.setTemplateType("1");
			dao.create(report);
		}
		// CNGI-6IX核心链路可用率
		if (temp.getUsabilityOfCNGITitle() != null
				&& !temp.getUsabilityOfCNGITitle().equals("")) {
			Report report = new Report();
			report.setName(temp.getUsabilityOfCNGITitle());
			report.setContent(temp.getUsabilityOfCNGI());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("3_4");
			report.setTemplateType("1");
			dao.create(report);
		}
		// 主干传输线路事件统计
		if (temp.getSumOfLineTitle() != null
				&& !temp.getSumOfLineTitle().equals("")) {
			Report report = new Report();
			report.setName(temp.getSumOfLineTitle());
			report.setContent(temp.getSumOfLine());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("3_5");
			report.setTemplateType("1");
			dao.create(report);
		}
		// 主干运行设备事件统计
		if (temp.getSumOfDeviceTitle() != null
				&& !temp.getSumOfDeviceTitle().equals("")) {
			Report report = new Report();
			report.setName(temp.getSumOfDeviceTitle());
			report.setContent(temp.getSumOfDevice());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("3_6");
			report.setTemplateType("1");
			dao.create(report);
		}
		// 主干网入流量分布
		if (temp.getFlowOfInputTitle() != null
				&& !temp.getFlowOfInputTitle().equals("")) {
			Report report = new Report();
			report.setName(temp.getFlowOfInputTitle());
			report.setContent(temp.getFlowOfInput());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("3_7");
			report.setTemplateType("1");
			dao.create(report);
		}
		// 主干网出流量分布
		if (temp.getFlowOfOutputTitle() != null
				&& !temp.getFlowOfOutputTitle().equals("")) {
			Report report = new Report();
			report.setName(temp.getFlowOfOutputTitle());
			report.setContent(temp.getFlowOfOutput());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("3_8");
			report.setTemplateType("1");
			dao.create(report);
		}
		// CERNET2边界流量
		if (temp.getFlowOfBorderTitle() != null
				&& !temp.getFlowOfBorderTitle().equals("")) {
			Report report = new Report();
			report.setName(temp.getFlowOfBorderTitle());
			report.setContent(temp.getFlowOfBorder());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("4_1");
			report.setTemplateType("1");
			dao.create(report);
		}
		// CERNET2网间流量
		if (temp.getFlowOfInternetTitle() != null
				&& !temp.getFlowOfInternetTitle().equals("")) {
			Report report = new Report();
			report.setName(temp.getFlowOfInternetTitle());
			report.setContent(temp.getFlowOfInternet());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("4_2");
			report.setTemplateType("1");
			dao.create(report);
		}
		// CERNET2网内流量
		if (temp.getFlowOfIntranetTitle() != null
				&& !temp.getFlowOfIntranetTitle().equals("")) {
			Report report = new Report();
			report.setName(temp.getFlowOfIntranetTitle());
			report.setContent(temp.getFlowOfIntranet());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("4_3");
			report.setTemplateType("1");
			dao.create(report);
		}
		// 北京交换中心流量
		if (temp.getFlowOfBJTitle() != null
				&& !temp.getFlowOfBJTitle().equals("")) {
			Report report = new Report();
			report.setName(temp.getFlowOfBJTitle());
			report.setContent(temp.getFlowOfBJ());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("4_4");
			report.setTemplateType("1");
			dao.create(report);
		}
		// 上海交换中心流量
		if (temp.getFlowOfSHTitle() != null
				&& !temp.getFlowOfSHTitle().equals("")) {
			Report report = new Report();
			report.setName(temp.getFlowOfSHTitle());
			report.setContent(temp.getFlowOfSH());
			report.setMyTemplate(temp.getTemplate());
			report.setFlag("4_5");
			report.setTemplateType("1");
			dao.create(report);
		}
		// 同时将数据来源的IP地址值写入configure表中
		Configure configure = configureDAO.hasTemplate(temp.getTemplate(), "1");// 根据templat查看该记录是否存在
		if (configure != null) {// 更新
			configure.setDataIP(temp.getDataIP());
			configure.setFaultIP(temp.getFaultIP());
			configure.setFlowIP(temp.getFlowIP());
			configure.setWebIP(temp.getWebIP());
			configureDAO.updateConfigure(configure);
		} else {// 添加
			configure = new Configure();
			configure.setMyTemplate(temp.getTemplate());
			configure.setDataIP(temp.getDataIP());
			configure.setFaultIP(temp.getFaultIP());
			configure.setFlowIP(temp.getFlowIP());
			configure.setWebIP(temp.getWebIP());
			configure.setTemplateType("1");
			configureDAO.saveConfigure(configure);
		}
	}

	public void createReport(String template, String type, String start,
			String end) throws Exception {
		int count = 0;
		boolean commentFlag = false;
		boolean eventsFlag = false;
		String firstName = "";
		String secondName = "";
		// 根据configure表获得webIP和dataIP的值
		Configure configure = configureDAO.hasTemplate(template, "1");
		if (configure != null) {
			webIP = configure.getWebIP();
			dataIP = configure.getDataIP();
			PerformanceXmlRpcURL = "http://" + configure.getFlowIP() + ":9000";
			FaultPerlRpcURL = "http://" + configure.getFaultIP() + ":9988";
		}
		StringBuffer xml = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
		xml.append("<report>\n");
		xml.append("<project>");
		xml
				.append("<projectName1>运行统计</projectName1><projectName2>流量曲线</projectName2>");
		xml
				.append("<projectName12>运行统计12</projectName12><projectName13>运行统计13</projectName13>");
		String sql = "from Report rp where ((rp.flag='1' or rp.flag='2') and rp.templateType='1' and rp.myTemplate='"
				+ template + "')";
		List<Report> reportsOfDesc = dao.getReports(sql);
		if (reportsOfDesc != null && reportsOfDesc.size() > 0) {
			for (Report report : reportsOfDesc) {
				if (report.getFlag().equals("1")) {// 点评
					xml.append("<commentTitle>");
					xml.append("一、点评");
					xml.append("</commentTitle>");
					xml.append("<comment>");
					xml.append(report.getContent());
					xml.append("</comment>");
					commentFlag = true;
					count++;
				} else if (report.getFlag().equals("2")) {// 重要事件
					xml.append("<eventsTitle>");
					if (count == 0) {
						xml.append("一、重要事件");
					} else {
						xml.append("二、重要事件");
					}
					xml.append("</eventsTitle>");
					xml.append("<events>");
					xml.append(report.getContent());
					xml.append("</events>");
					eventsFlag = true;
					count++;
				}
			}
			if (!commentFlag) {
				xml.append("<commentTitle/>");
				xml.append("<comment/>");
			} else if (!eventsFlag) {
				xml.append("<eventsTitle/>");
				xml.append("<events/>");
			}
			if (count == 0) {
				xml.append("<runTitle>");
				xml.append("一、运行统计");
				xml.append("</runTitle>");
				xml.append("<curvesTitle>");
				xml.append("二、流量曲线");
				xml.append("</curvesTitle>");
			} else if (count == 1) {
				xml.append("<runTitle>");
				xml.append("二、运行统计");
				xml.append("</runTitle>");
				xml.append("<curvesTitle>");
				xml.append("三、流量曲线");
				xml.append("</curvesTitle>");
			} else if (count == 2) {
				xml.append("<runTitle>");
				xml.append("三、运行统计");
				xml.append("</runTitle>");
				xml.append("<curvesTitle>");
				xml.append("四、流量曲线");
				xml.append("</curvesTitle>");
			}
		} else {
			xml.append("<commentTitle/>");
			xml.append("<comment/>");
			xml.append("<eventsTitle/>");
			xml.append("<events/>");
			xml.append("<runTitle>");
			xml.append("一、运行统计");
			xml.append("</runTitle>");
			xml.append("<curvesTitle>");
			xml.append("二、流量曲线");
			xml.append("</curvesTitle>");
		}
		xml.append("</project>");
		List reports = dao.getReports(" from Report rp where rp.myTemplate='"
				+ template + "' and rp.templateType='1'");
		for (int i = 0; i < reports.size(); i++) {
			int createFlag = 0;
			Report report = (Report) reports.get(i);
			String fileName = template + Math.random() + ".jpg";
			if (report.getFlag().equals("3_1")) {// 主干线路故障率
				// 生成图片
				String[] contents = report.getContent().split("\n");
				String tagName = "";
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				boolean placeFlag = true;
				for (int j = 0; j < contents.length; j++) {
					if (!contents[j].equals("")) {
						String[] content = contents[j].split("\\|\\|");
						if (content.length == 2) {
							if (placeFlag) {
								tagName = content[0];// 超过一定阀值时显示标签的位置
								placeFlag = false;
							}
							if (!content[1].trim().equals("")) {
								Double value = new Double(Double
										.valueOf(content[1]));
								dataset.addValue(value, "", content[0]);
							} else {
								dataset.addValue(0, "", content[0]);
							}
						}
					}
				}// Endof for
				categoryImage(report.getName(), fileName, dataset, tagName, "0");
				createFlag = 1;
			} else if (report.getFlag().equals("3_3")) {// 接入线路故障率
				String[] contents = report.getContent().split("\n");
				String tagName = "";
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				Hashtable faultValues = this.GetFaultValues(type, start, end,
						FaultPerlRpcURL);
				boolean placeFlag = true;
				for (int j = 0; j < contents.length; j++) {
					if (!contents[j].equals("")) {
						String[] content = contents[j].split("\\|\\|");
						if (content.length >= 2) {
							if (placeFlag) {
								tagName = content[0];// 超过一定阀值时显示标签的位置
								placeFlag = false;
							}
							String IP = content[1].trim();
							String temp0 = "";
							String temp1 = "";
							if (faultValues != null) {
								if (faultValues.get(IP) != null) {
									temp0 = IP.toUpperCase();
									temp1 = IP.toLowerCase();
								} else {
									String changedIP = "";
									if (IP.indexOf("::") != -1) {
										changedIP = IP.replace("::", ":0:0:0:");
									}
									if (!changedIP.equals("")) {
										temp0 = changedIP.toUpperCase();
										temp1 = changedIP.toLowerCase();
									}
								}
								if (faultValues.get(temp0) != null) {
									String[] values = faultValues.get(temp0)
											.toString().split("\\|");
									dataset.addValue(Double.valueOf(values[3]),
											"", content[0]);
								} else if (faultValues.get(temp1) != null) {
									String[] values = faultValues.get(temp1)
											.toString().split("\\|");
									dataset.addValue(Double.valueOf(values[3]),
											"", content[0]);
								} else {
									dataset.addValue(0, "", content[0]);
								}
							} else {
								dataset.addValue(0, "", content[0]);
							}
						}
					}
				}// Endof for
				categoryImage(report.getName(), fileName, dataset, tagName, "0");
				createFlag = 1;
			} else if (report.getFlag().equals("3_2")
					|| report.getFlag().equals("3_4")) {// 核心节点可用性||CNGI-6IX核心链路可用率
				String[] contents = report.getContent().split("\n");
				String tagName = "";
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				Hashtable faultValues = this.GetFaultValues(type, start, end,
						FaultPerlRpcURL);
				boolean placeFlag = true;
				for (int j = 0; j < contents.length; j++) {
					if (!contents[j].equals("")) {
						String[] content = contents[j].split("\\|\\|");
						if (content.length == 2) {
							if (placeFlag) {
								tagName = content[0];// 超过一定阀值时显示标签的位置
								placeFlag = false;
							}
							String IP = content[1].trim();
							String temp0 = "";
							String temp1 = "";
							if (faultValues != null) {
								if (faultValues.get(IP) != null) {
									temp0 = IP.toUpperCase();
									temp1 = IP.toLowerCase();
								} else {
									String changedIP = "";
									if (IP.indexOf("::") != -1) {
										changedIP = IP.replace("::", ":0:0:0:");
									}
									if (!changedIP.equals("")) {
										temp0 = changedIP.toUpperCase();
										temp1 = changedIP.toLowerCase();
									}
								}
								if (faultValues.get(temp0) != null) {
									String[] values = faultValues.get(temp0)
											.toString().split("\\|");
									dataset
											.addValue(100 - Double
													.valueOf(values[3]), "",
													content[0]);
								} else if (faultValues.get(temp1) != null) {
									String[] values = faultValues.get(temp1)
											.toString().split("\\|");
									dataset
											.addValue(100 - Double
													.valueOf(values[3]), "",
													content[0]);
								} else {
									dataset.addValue(0, "", content[0]);
								}
							} else {
								dataset.addValue(0, "", content[0]);
							}

						}
					}
				}// Endof for
				categoryImage(report.getName(), fileName, dataset, tagName, "1");
				createFlag = 1;
			} else if (report.getFlag().equals("3_5")
					|| report.getFlag().equals("3_6")) {// 主干传输线路事件统计||主干运行设备事件统计
				DefaultPieDataset dataset = new DefaultPieDataset();
				String[] contents = report.getContent().split("\n");
				for (int j = 0; j < contents.length; j++) {
					if (!contents[j].equals("")) {
						String[] content = contents[j].split("\\|\\|");
						if (content.length == 2) {
							if (!content[1].trim().equals("")) {
								dataset.setValue(content[0], Double
										.valueOf(content[1]));
							} else {
								dataset.setValue(content[0], 0);
							}
						}
					}
				}// Endof for
				pieImage(report.getName(), fileName, dataset);
				createFlag = 2;
			} else if (report.getFlag().equals("3_7")
					|| report.getFlag().equals("3_8")) {// 主干网入/出流量分布
				DefaultPieDataset dataset = new DefaultPieDataset();
				Hashtable flowValues = this.GetFlowValues(type, start, end,
						PerformanceXmlRpcURL);
				String[] contents = report.getContent().split("\n");
				for (int j = 0; j < contents.length; j++) {
					if (!contents[j].equals("")) {
						String[] content = contents[j].split("\\|\\|");
						if (content.length == 2) {
							if (j == 0) {
								firstName = content[0];
							} else if (j == 1) {
								secondName = content[0];
							}
							String[] values = content[1].split("\\;");
							double value = 0;
							for (int m = 0; m < values.length; m++) {
								if (!values[m].equals("")) {
									String[] distrbute = values[m].split(",");
									if (distrbute.length >= 2) {
										String IP = distrbute[0];
										if (IP.indexOf(":") != -1) {// 如果是IPv6，将其转化IPv4
											IP = StringUtil.changeIP(IP);
										}
										for (int n = 1; n < distrbute.length; n++) {
											if (!distrbute[n].equals("")) {
												String ifIP = IP + "_"
														+ distrbute[n];
												if (flowValues != null) {
													if (flowValues.get(ifIP) != null) {
														String[] XMLValues = flowValues
																.get(ifIP)
																.toString()
																.split("\\|");
														if (report.getFlag()
																.equals("3_7")) {
															value += Double
																	.valueOf(XMLValues[2]);
														} else if (report
																.getFlag()
																.equals("3_8")) {
															value += Double
																	.valueOf(XMLValues[3]);
														}
													}
												}
											}
										}
									}
								}
							}
							dataset.setValue(content[0], value);
						}
					}
				}// Endof for
				// pieImage(report.getName(),fileName,dataset,firstName,secondName);
				pieImage(report.getName(), fileName, dataset);
				createFlag = 2;
			} else {
				continue;
			}
			if (createFlag == 1) {
				xml.append("<run>");
				xml.append("<projectName1>运行统计</projectName1>");
				xml.append("<imageFile>");
				xml.append(path + fileName);
				xml.append("</imageFile>");
				xml.append("</run>");
			} else if (createFlag == 2) {
				xml.append("<run12>");
				xml.append("<projectName12>运行统计12</projectName12>");
				xml.append("<imageFile>");
				xml.append(path + fileName);
				xml.append("</imageFile>");
				xml.append("</run12>");
			}
		}// Endof for
		// 路由信息统计
		xml.append(this.getRouterModule(type, start, end, template, webIP,
				dataIP));
		String sqlOfFlow = "from Report rp where ((rp.flag='4_1' or rp.flag='4_2' or rp.flag='4_3' or rp.flag='4_4' or rp.flag='4_5') and rp.templateType='1' and rp.myTemplate='"
				+ template + "')";
		List<Report> reportsOfFlow = dao.getReports(sqlOfFlow);
		for (Report report : reportsOfFlow) {
			xml.append("<flow>");
			xml.append("<projectName2>");
			xml.append("流量曲线");
			xml.append("</projectName2>");
			xml.append("<modelName>");
			xml.append(report.getName());
			xml.append("</modelName>");
			xml.append("</flow>");
			String[] contents = report.getContent().split("\n");
			for (int j = 0; j < contents.length; j++) {
				if (!contents[j].equals("")) {
					String[] content = contents[j].split("\\|\\|");
					if (content.length == 2) {
						xml.append(this.getFlowModule(report.getName(),
								content[0], content[1], type, start, end,
								template, PerformanceXmlRpcURL));
					}
				}
			}// Endof for
		}
		xml.append("</report>");
		File newFile = new File(path + template + ".xml");
		FileWriter newFileStream = new FileWriter(newFile);
		newFileStream.write(xml.toString());
		newFileStream.close();
	}

	public String getRouterModule(Report report, String type, String start,
			String end, String template, String webIP, String dataIP)
			throws Exception {
		StringBuffer xml = new StringBuffer();
		String[] contents = report.getContent().split(";");
		String fileName = "";
		String startParam = "";
		if (type.equals("year")) {
			GregorianCalendar cal = new GregorianCalendar();
			if (cal.isLeapYear(Integer.parseInt(start))) {
				end = "366";
			} else
				end = "365";
			startParam = start + "0101";
		} else if (type.equals("month")) {
			startParam = start + end + "01";
			end = String.valueOf(WeekUtil.getDaysOfMonth(start, end));
		} else {
			Date date = WeekUtil.getFirstDayOfWeek(Integer.parseInt(start),
					Integer.parseInt(end) - 1);
			String dateTime = MessageFormat.format(
					"{0,date,yyyy-MM-dd HH:mm:ss}", date);
			String temp = dateTime.substring(0, 10);
			startParam = temp.replaceAll("-", "");
			end = "7";
		}
		for (int j = 0; j < contents.length; j++) {
			if (!contents[j].equals("")) {
				String[] content = contents[j].split("\\:");
				if (!content[0].equals("")) {
					if (!content[1].equals("")) {
						fileName = template + Math.random();
						Vector vec = imageUtil.getBGPImages(webIP, dataIP,
								startParam, end, fileName);
						for (int k = 0; k < vec.size(); k++) {
							File file = new File(path + vec.get(k).toString());
							if (file.exists()) {
								xml.append("<model>");
								xml.append("<projectID>");
								xml.append("3");
								xml.append("</projectID>");
								xml.append("<modelName>");
								xml.append(content[0]);
								xml.append("</modelName>");
								xml.append("<modelDescription>");
								xml.append("注:" + report.getName());
								xml.append("</modelDescription>");
								xml.append("<modelFile>");
								xml.append(path + file.getName());
								xml.append("</modelFile>");
								xml.append("</model>");
							}
						}
					}
				}
			}
		}
		return xml.toString();
	}

	public String getRouterModule(String type, String start, String end,
			String template, String webIP, String dataIP) throws Exception {
		StringBuffer xml = new StringBuffer();
		String fileName = template + Math.random();
		String startParam = "";
		if (type.equals("year")) {
			GregorianCalendar cal = new GregorianCalendar();
			if (cal.isLeapYear(Integer.parseInt(start))) {
				end = "366";
			} else
				end = "365";
			startParam = start + "0101";
		} else if (type.equals("month")) {
			startParam = start + end + "01";
			end = String.valueOf(WeekUtil.getDaysOfMonth(start, end));
		} else {
			Date date = WeekUtil.getFirstDayOfWeek(Integer.parseInt(start),
					Integer.parseInt(end) - 1);
			String dateTime = MessageFormat.format(
					"{0,date,yyyy-MM-dd HH:mm:ss}", date);
			String temp = dateTime.substring(0, 10);
			startParam = temp.replaceAll("-", "");
			end = "7";
		}
		Vector vec = imageUtil.getBGPImages(webIP, dataIP, startParam, end,
				fileName);
		for (int k = 0; k < vec.size(); k++) {
			File file = new File(path + vec.get(k).toString());
			if (file.exists()) {
				xml.append("<run13>");
				xml.append("<projectName13>运行统计13</projectName13>");
				xml.append("<imageFile>");
				xml.append(path + file.getName());
				xml.append("</imageFile>");
				xml.append("</run13>");
			}
		}
		return xml.toString();
	}

	public String getFlowModule(String reportName, String content0,
			String IPAndIfindex, String type, String start, String end,
			String template, String flowIP) throws Exception {
		StringBuffer xml = new StringBuffer();
		String fileName = template + "_" + Math.random();
		/*
		 * 产业化项目时用到，采用web service方式实现 Vector para =
		 * imageUtil.getVectorParam(IPAndIfindex,type,start,end); Vector vec =
		 * imageUtil.getFlowImages(para,fileName,flowIP);
		 * System.out.println("vec="+vec.get(0).toString());
		 * System.out.println("path="+path);
		 */

		// 需要查到某一时间段生成的流量曲线图（此处采用比特图）
		String time = null;
		String length = null;
		if (type.equals("year")) {
			GregorianCalendar cal = new GregorianCalendar();
			if (cal.isLeapYear(Integer.parseInt(start))) {
				length = "366";
			} else
				length = "365";
			time = start + "0101";
		} else if (type.equals("month")) {
			if (end.length() == 1) {
				end = "0" + end;
			}
			time = start + end + "01";
			length = String.valueOf(WeekUtil.getDaysOfMonth(start, end));
		} else {
			time = dateUtil.getYearWeekFirstDay(Integer.parseInt(start),
					Integer.parseInt(end));
			length = "7";
		}
		String IP = IPAndIfindex.split("_")[0];
		String ifIndex = IPAndIfindex.split("_")[1];
		String cmd = new String();
		cmd = "period " + IP + "  " + ifIndex + " " + time + " " + length;
		Process ps = java.lang.Runtime.getRuntime().exec(cmd);
		ps.waitFor();
		// 查看生成的文件是否存在，若存在，由于该文件下次运行时将被覆盖，需要将其拷贝到file/report目录下并改名，提供给报表进行显示
		String srcPath = Constants.webRealPath + "file/flow/flowscan/dat/tmp/";
		String srcName = IPAndIfindex + "_tmp_flow_bit.gif";
		String destNmae = fileName + "0.gif";
		File srcFile = new File(srcPath + srcName);
		if (srcFile.exists()) {
			// 拷贝到report目录
			FileUtil.copyFileAndReName(srcPath, path, srcName, destNmae);
			String flowFileStr = path + IPAndIfindex + "_tmp_flow_bit.gif";
			File flowFile = new File(flowFileStr);
			if (flowFile.exists()) {
				xml.append("<model>");
				xml.append("<modelName>");
				xml.append(reportName);
				xml.append("</modelName>");
				xml.append("<modelTitle>");
				xml.append(content0);
				xml.append("</modelTitle>");
				xml.append("<modelImage>");
				xml.append(path + destNmae);
				xml.append("</modelImage>");
				xml.append("</model>");
			}
			// 可以此处删除掉原目录文件
		}
		return xml.toString();
	}

	public void category3DImage(String imageTitle, String fileName,
			CategoryDataset dataset, String tagName, String alerm)
			throws Exception {
		JFreeChart chart = ChartFactory.createBarChart3D(imageTitle, // chart
				// title
				"", // domain axis label
				"Value", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				false, // include legend
				true, // tooltips
				false // URLs?
				);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setDomainGridlinesVisible(true);
		CategoryAxis axis = plot.getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 2));
		axis.setLabelFont(new Font("SansSerif", Font.BOLD, 12));
		CustomBarRenderer3D renderer = new CustomBarRenderer3D(alerm);
		plot.setRenderer(renderer);
		ValueMarker marker = null;
		if (alerm.equals("0")) {
			marker = new ValueMarker(70, new Color(200, 200, 255),
					new BasicStroke(1.0f), new Color(200, 200, 255),
					new BasicStroke(1.0f), 1.0f);
		} else {
			marker = new ValueMarker(30, new Color(200, 200, 255),
					new BasicStroke(1.0f), new Color(200, 200, 255),
					new BasicStroke(1.0f), 1.0f);
		}
		plot.addRangeMarker(marker, Layer.BACKGROUND);
		renderer.setItemLabelsVisible(true);
		renderer.setMaximumBarWidth(0.1);

		CategoryTextAnnotation a = null;
		if (alerm.equals("0")) {
			a = new CategoryTextAnnotation("Max fault value", tagName, 70);
		} else {
			a = new CategoryTextAnnotation("Minimum usability value", tagName,
					30);
		}
		a.setCategoryAnchor(CategoryAnchor.START);
		a.setFont(new Font("SansSerif", Font.PLAIN, 12));
		a.setTextAnchor(TextAnchor.BOTTOM_LEFT);
		plot.addAnnotation(a);

		// ValueAxis rangeAxis = (ValueAxis) plot.getRangeAxis();
		// rangeAxis.setNumberFormatOverride(NumberFormat.getPercentInstance());
		// rangeAxis.setAutoRange(true);
		FileOutputStream fos = new FileOutputStream(path + fileName);
		ChartUtilities.writeChartAsJPEG(fos, 1, chart, 676, 361, null);
		fos.close();
	}

	static class CustomBarRenderer3D extends BarRenderer3D {
		private String alermFlag = "";

		/**
		 * Creates a new renderer.
		 */
		public CustomBarRenderer3D(String alermFlag) {
			this.alermFlag = alermFlag;
		}

		/**
		 * Returns the paint for an item. Overrides the default behaviour
		 * inherited from AbstractSeriesRenderer.
		 * 
		 * @param row
		 *            the series.
		 * @param column
		 *            the category.
		 * 
		 * @return The item color.
		 */
		public Paint getItemPaint(int row, int column) {
			CategoryDataset dataset = getPlot().getDataset();
			double value = dataset.getValue(row, column).doubleValue();
			if (alermFlag.equals("1")) {
				if (value >= 30) {
					return Color.green;
				} else {
					return Color.red;
				}
			} else {
				if (value >= 70) {
					return Color.red;
				} else {
					return Color.yellow;
				}
			}
		}
	}

	public void pieImage(String imageTitle, String fileName,
			PieDataset dataset, String firstName, String secondName)
			throws Exception {
		JFreeChart chart = ChartFactory.createPieChart(imageTitle, // chart
				// title
				dataset, // dataset
				false, // include legend
				true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 0, 1000,
				Color.green));
		plot.setNoDataMessage("No data available");
		plot.setExplodePercent(firstName, 0.5);
		plot.setExplodePercent(secondName, 0.5);

		plot
				.setLabelGenerator(new StandardPieSectionLabelGenerator(
						"{0} ({2})"));
		plot.setLabelBackgroundPaint(new Color(220, 220, 220));
		plot
				.setLegendLabelToolTipGenerator(new StandardPieSectionLabelGenerator(
						"Tooltip for legend item {0}"));
		FileOutputStream fos = new FileOutputStream(path + fileName);
		ChartUtilities.writeChartAsJPEG(fos, 1, chart, 676, 441, null);
		fos.close();
	}

	public void pieImage(String imageTitle, String fileName, PieDataset dataset)
			throws Exception {
		JFreeChart chart = ChartFactory.createPieChart(imageTitle, // chart
				// title
				dataset, // dataset
				false, // include legend
				true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 0, 1000,
				Color.green));
		plot.setNoDataMessage("No data available");

		plot
				.setLabelGenerator(new StandardPieSectionLabelGenerator(
						"{0} ({2})"));
		plot.setLabelBackgroundPaint(new Color(220, 220, 220));
		plot
				.setLegendLabelToolTipGenerator(new StandardPieSectionLabelGenerator(
						"Tooltip for legend item {0}"));
		FileOutputStream fos = new FileOutputStream(path + fileName);
		ChartUtilities.writeChartAsJPEG(fos, 1, chart, 676, 441, null);
		fos.close();
	}

	public void importFile(JFreeChart chart, String fileName) throws Exception {
		FileOutputStream fos = null;
		fos = new FileOutputStream(path + fileName);
		ChartUtilities.writeChartAsJPEG(fos, 1, chart, 676, 361, null);
		fos.close();
	}

	public Hashtable GetFaultValues(String type, String begin, String end,
			String faultIP) {
		Hashtable vector = null;
		try {
			XmlRpcClient client = new XmlRpcClient(faultIP);
			Vector vec = new Vector();
			vec.addElement(type);
			vec.addElement(begin);
			vec.addElement(end);
			vector = (Hashtable) client.execute("ServerManager.GetStatFile",
					vec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vector;
	}

	public Hashtable GetFlowValues(String type, String begin, String end,
			String flowIP) {
		Hashtable vector = null;
		try {
			XmlRpcClient client = new XmlRpcClient(flowIP);
			Vector vec = new Vector();
			vec.addElement(type);
			vec.addElement(begin);
			vec.addElement(end);
			vector = (Hashtable) client.execute("ServerManager.ReturnStatFile",
					vec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vector;
	}

	public void categoryImage(String imageTitle, String fileName,
			CategoryDataset dataset, String tagName, String alerm)
			throws Exception {
		JFreeChart chart = ChartFactory.createBarChart(imageTitle, // chart
				// title
				"", // domain axis label
				"Value", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				false, // include legend
				true, // tooltips
				false // URLs?
				);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setDomainGridlinesVisible(true);
		CategoryAxis axis = plot.getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 2));
		axis.setLabelFont(new Font("黑体", Font.BOLD, 12));
		axis.setLabelPaint(Color.black);
		CustomBarRenderer renderer = new CustomBarRenderer(alerm);
		plot.setRenderer(renderer);
		ValueMarker marker = null;
		if (alerm.equals("0")) {
			marker = new ValueMarker(70, new Color(200, 200, 255),
					new BasicStroke(1.0f), new Color(200, 200, 255),
					new BasicStroke(1.0f), 1.0f);
		} else {
			marker = new ValueMarker(30, new Color(200, 200, 255),
					new BasicStroke(1.0f), new Color(200, 200, 255),
					new BasicStroke(1.0f), 1.0f);
		}
		plot.addRangeMarker(marker, Layer.BACKGROUND);
		renderer.setItemLabelsVisible(true);
		renderer.setMaximumBarWidth(0.1);

		CategoryTextAnnotation a = null;
		if (alerm.equals("0")) {
			a = new CategoryTextAnnotation("Max fault value", tagName, 70);
		} else {
			a = new CategoryTextAnnotation("Minimum usability value", tagName,
					30);
		}
		a.setCategoryAnchor(CategoryAnchor.START);
		a.setFont(new Font("SansSerif", Font.PLAIN, 12));
		a.setTextAnchor(TextAnchor.BOTTOM_LEFT);
		plot.addAnnotation(a);

		// CategoryItemRenderer rendererFlag = plot.getRenderer();
		// rendererFlag.setItemLabelGenerator(new
		// StandardCategoryItemLabelGenerator());
		// rendererFlag.setSeriesItemLabelsVisible(0, Boolean.TRUE);

		// ValueAxis rangeAxis = (ValueAxis) plot.getRangeAxis();
		// rangeAxis.setNumberFormatOverride(NumberFormat.getPercentInstance());
		// rangeAxis.setAutoRange(true);
		FileOutputStream fos = new FileOutputStream(path + fileName);
		ChartUtilities.writeChartAsJPEG(fos, 1, chart, 676, 361, null);
		fos.close();
	}

	static class CustomBarRenderer extends BarRenderer {
		private String alermFlag = "";

		/**
		 * Creates a new renderer.
		 */
		public CustomBarRenderer(String alermFlag) {
			this.alermFlag = alermFlag;
		}

		/**
		 * Returns the paint for an item. Overrides the default behaviour
		 * inherited from AbstractSeriesRenderer.
		 * 
		 * @param row
		 *            the series.
		 * @param column
		 *            the category.
		 * 
		 * @return The item color.
		 */
		public Paint getItemPaint(int row, int column) {
			CategoryDataset dataset = getPlot().getDataset();
			double value = dataset.getValue(row, column).doubleValue();
			if (alermFlag.equals("1")) {
				if (value >= 30) {
					return Color.green;
				} else {
					return Color.red;
				}
			} else {
				if (value >= 70) {
					return Color.red;
				} else {
					return Color.yellow;
				}
			}
		}
	}

	/**
	 * 删除与该模板相关的数据库记录和文件
	 * 
	 * @param template
	 */
	public boolean deleteTemplate(String template) {
		boolean flag = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		String sql1 = "delete Configure cf where cf.myTemplate='"
				+ template.trim() + "'";
		String sql2 = "delete Report rp where rp.myTemplate='"
				+ template.trim() + "'";
		try {
			tx = session.beginTransaction();
			session.createQuery(sql1).executeUpdate();
			session.createQuery(sql2).executeUpdate();
			tx.commit();
			// 删除文件
			File f = new File(path);
			if (f.isDirectory()) {
				File[] files = f.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isFile()) {
						File temp = files[i];
						if (temp.getName().equals(template + ".rtf")
								|| temp.getName().equals(template + ".jrprint")) {
							files[i].delete();
						} else {
							continue;
						}
					}// Endof if(files[i].isFile())
				}// Endof for
			}
			flag = true;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return flag;
	}
}
