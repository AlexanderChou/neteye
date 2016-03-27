package com.savi.show.action;

import org.jfree.chart.JFreeChart;

import com.savi.show.chart.ACUserNumChart;

@SuppressWarnings("serial")
public class HuaSanChartAction extends BaseAction{
	private String acId;
	private JFreeChart chart;
	public String getAcId() {
		return acId;
	}
	public void setAcId(String acId) {
		this.acId = acId;
	}
	public JFreeChart getChart() {
		return chart;
	}
	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
	//交换机用户数TopN
	public String genAcChart(){
		long id = Long.parseLong(acId);
		ACUserNumChart freeChart = new ACUserNumChart(id);
		chart = freeChart.createChart();
		
		return SUCCESS;
	}
}
