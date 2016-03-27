package com.savi.show.action;

import org.jfree.chart.JFreeChart;

import com.savi.show.chart.ACUserNumChart;
import com.savi.show.chart.ACUserNumberChangeChart;
import com.savi.show.chart.IPBindingStateChart;
import com.savi.show.chart.IPBindingTypeStateChart;
import com.savi.show.chart.InterfaceTrustTypeChart;
import com.savi.show.chart.InterfaceValidationStatusChart;
import com.savi.show.chart.SwitchRunningStateChart;
import com.savi.show.chart.SwitchUserNumChart;
import com.savi.show.chart.UserNumberChangeChart;
import com.savi.show.chart.UserOnlineTimeChart;

@SuppressWarnings("serial")
public class SubnetChartAction extends BaseAction{
	private String subnetId;
	private JFreeChart chart;
	
	//端口信任类型分布
	public String genInterfaceTrustTypeChart(){
		long id = Long.parseLong(subnetId);
		InterfaceTrustTypeChart freeChart = new InterfaceTrustTypeChart(id);
		chart = freeChart.createChart();
		
		return SUCCESS;
	}
	
	//端口验证类型分布
	public String genInterfaceValidatonStatusChart(){
		long id = Long.parseLong(subnetId);
		InterfaceValidationStatusChart freeChart = new InterfaceValidationStatusChart(id);
		chart = freeChart.createChart();
		
		return SUCCESS;
	}
	
	//IP绑定状态变化曲线
	public String genIPBindingStateChart(){
		long id = Long.parseLong(subnetId);
		IPBindingStateChart freeChart = new IPBindingStateChart(id);
		chart = freeChart.createChart();
		
		return SUCCESS;
	}
	
	//IP绑定类型分布
	public String genIPBindingTypeStateChart(){
		long id = Long.parseLong(subnetId);
		IPBindingTypeStateChart freeChart = new IPBindingTypeStateChart(id);
		chart = freeChart.createChart();
		
		return SUCCESS;
	}
	
	//交换机运行状态分布
	public String genSwitchRunningStateChart(){
		long id = Long.parseLong(subnetId);
		SwitchRunningStateChart freeChart = new SwitchRunningStateChart(id);
		chart = freeChart.createChart();
		
		return SUCCESS;
	}
	
	//交换机用户数TopN
	public String genSwitchUserNumChart(){
		long id = Long.parseLong(subnetId);
		SwitchUserNumChart freeChart = new SwitchUserNumChart(id);
		chart = freeChart.createChart();
		
		return SUCCESS;
	}
	
	//AC交换机用户数TopN
	public String genAcChart(){
		long id = Long.parseLong(subnetId);
		ACUserNumChart freeChart = new ACUserNumChart(id);
		chart = freeChart.createChart();
		
		return SUCCESS;
	}
	
	//AC用户数变化曲线
	public String genACUserNumberChangeChart(){
		long id = Long.parseLong(subnetId);
		ACUserNumberChangeChart freeChart = new ACUserNumberChangeChart(id);
		chart = freeChart.createChart();
		
		return SUCCESS;
	}
	
	//用户数变化曲线
	public String genUserNumberChangeChart(){
		long id = Long.parseLong(subnetId);
		UserNumberChangeChart freeChart = new UserNumberChangeChart(id);
		chart = freeChart.createChart();
		
		return SUCCESS;
	}
	
	//用户在线时间（CDF分布）
	public String genUserOnlineTimeChart(){
		long id = Long.parseLong(subnetId);
		UserOnlineTimeChart freeChart = new UserOnlineTimeChart(id);
		chart = freeChart.createChart();
		
		return SUCCESS;
	}
	
	public String getSubnetId() {
		return subnetId;
	}

	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
}
