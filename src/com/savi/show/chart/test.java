package com.savi.show.chart;

import org.jfree.chart.JFreeChart;

import com.savi.show.chart.SwitchUserNumChart;
//测试时使用
public class test {
	public static void main(String[] args) {
		//String path = "D:\\savi\\pic\\";
		/*
		//交换机运行状态分布：
		SwitchRunningStateChart runstatechart = new SwitchRunningStateChart(1L);
		JFreeChart chart3 = runstatechart.createChart();
		runstatechart.saveChartPicture(1L, chart3);
		//端口信任类型分布：
		InterfaceTrustTypeChart interfacetrustchart = new InterfaceTrustTypeChart(1L);
		JFreeChart chart4 = interfacetrustchart.createChart();
		interfacetrustchart.saveChartPicture(1L, chart4);
		//端口验证类型分布：
		InterfaceValidationStatusChart interfacevalidationchart = new InterfaceValidationStatusChart(3L);
		JFreeChart chart5 = interfacevalidationchart.createChart();
		interfacevalidationchart.saveChartPicture(1L, chart5);
		//IP绑定类型分布：
		IPBindingTypeStateChart bindingtype = new IPBindingTypeStateChart(3L);
		JFreeChart chart6 = bindingtype.createChart();
		bindingtype.saveChartPicture(1L, chart6);
		*/
		
		
		//用户数变化曲线：
		UserNumberChangeChart userchangechart = new UserNumberChangeChart(1L);
		JFreeChart chart6 = userchangechart.createChart();
		userchangechart.saveChartPicture(1L, chart6);
		
		//用户在线时间（CCDF分布）：
		UserOnlineTimeChart useronlinechart = new UserOnlineTimeChart(1L);
		JFreeChart chart1 = useronlinechart.createChart();
		useronlinechart.saveChartPicture(1L, chart1);
		
		//IP绑定状态变化曲线：
		IPBindingStateChart bindingstatechart = new IPBindingStateChart(1L);
		JFreeChart chart2 = bindingstatechart.createChart();
		bindingstatechart.saveChartPicture(1L, chart2);
		
		//交换机用户数TopN：
		SwitchUserNumChart swtichchart = new SwitchUserNumChart(1L);
		JFreeChart chart7 = swtichchart.createChart();
		swtichchart.saveChartPicture(1L, chart7);
		
		
		
		//"高级信息面板"第一个表盘：
		MaxFilteringNumMeterChart maxfilterchart = new MaxFilteringNumMeterChart(637L);
		JFreeChart chart11 = maxfilterchart.createChart();
		maxfilterchart.saveChartPicture(1L, chart11);
		
		//"高级信息面板"第二个表盘：
		FilteringTableRecordNumChart filternumchart = new FilteringTableRecordNumChart(637L);
		JFreeChart chart22 = filternumchart.createChart();
		filternumchart.saveChartPicture(1L, chart22);
		
		//"高级信息面板"第三个表盘：
		UserNumberMeterChart usernumchart = new UserNumberMeterChart(637L);
		JFreeChart chart3 = usernumchart.createChart();
		usernumchart.saveChartPicture(1L, chart3);
		
		//"高级信息面板"第四个表盘：
		BindingTableRecordNumChart bindingnumchart = new BindingTableRecordNumChart(637L);
		JFreeChart chart4 = bindingnumchart.createChart();
		bindingnumchart.saveChartPicture(1L, chart4);
		
		
		//交换机视图中用户变化曲线面板：
		SwitchUserNumberChangeChart switchusernumchart = new SwitchUserNumberChangeChart(6L);
		JFreeChart chart5 = switchusernumchart.createChart();
		switchusernumchart.saveChartPicture(1L, chart5);
		
		//交换机视图中用户在线时间曲线面板：
		SwitchUserOnlineTimeChart switchuseronlinechart = new SwitchUserOnlineTimeChart(6L);
		JFreeChart chart66 = switchuseronlinechart.createChart();
		switchuseronlinechart.saveChartPicture(1L, chart66);
		
		//交换机视图中端口用户排名面板：
		InterfaceUserNumberSortChart interfaceusersortchart = new InterfaceUserNumberSortChart(6L);
		JFreeChart chart77 = interfaceusersortchart.createChart();
		interfaceusersortchart.saveChartPicture(1L, chart77);
		
		//交换机视图中用户在线时间排名面板：
		UserOnlineTimeSortChart useronlinetimesortchart = new UserOnlineTimeSortChart(6L);
		JFreeChart chart8 = useronlinetimesortchart.createChart();
		useronlinetimesortchart.saveChartPicture(1L, chart8);
		
	}
}