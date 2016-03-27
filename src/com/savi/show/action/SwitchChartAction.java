package com.savi.show.action;

import org.jfree.chart.JFreeChart;

import com.savi.base.model.SaviFilterTableCur;
import com.savi.base.model.Switchcur;
import com.savi.show.chart.APUserOnlineTimeChart;
import com.savi.show.chart.APUserOnlineTimeMonthChart;
import com.savi.show.chart.BindingTableRecordNumChart;
import com.savi.show.chart.FilteringTableRecordNumChart;
import com.savi.show.chart.InterfaceUserNumberSortChart;
import com.savi.show.chart.MaxFilteringNumMeterChart;
import com.savi.show.chart.SwitchUserNumberChangeChart;
import com.savi.show.chart.SwitchUserOnlineTimeChart;
import com.savi.show.chart.UserNumberMeterChart;
import com.savi.show.chart.UserOnlineTimeSortChart;
import com.savi.show.dao.DeviceDao;
import com.savi.show.dao.SwitchDao;

@SuppressWarnings("serial")
public class SwitchChartAction extends BaseAction {
	private SwitchDao switchDao = new SwitchDao();

	private Long switchbasicinfoId;
	private Integer ipVersion;
	private JFreeChart chart;

	public String genMaxFilteringNumMeterChart() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
				ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		MaxFilteringNumMeterChart freeChart = new MaxFilteringNumMeterChart(
				switchcur.getId());
		chart = freeChart.createChart();
		return SUCCESS;
	}

	public String genFilteringTableRecordNumChart() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
				ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		FilteringTableRecordNumChart freeChart = new FilteringTableRecordNumChart(
				switchcur.getId());
		chart = freeChart.createChart();
		return SUCCESS;
	}

	public String genUserNumberMeterChart() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
				ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		UserNumberMeterChart freeChart = new UserNumberMeterChart(switchcur
				.getId());
		chart = freeChart.createChart();
		return SUCCESS;
	}

	public String genBindingTableRecordNumChart() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
				ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		BindingTableRecordNumChart freeChart = new BindingTableRecordNumChart(
				switchcur.getId());
		chart = freeChart.createChart();
		return SUCCESS;
	}

	public String genSwitchUserNumberChangeChart() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
				ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		SwitchUserNumberChangeChart freeChart = new SwitchUserNumberChangeChart(
				switchcur.getId());
		chart = freeChart.createChart();
		return SUCCESS;
	}

	public String genSwitchUserOnlineTimeChart() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
				ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		SwitchUserOnlineTimeChart freeChart = new SwitchUserOnlineTimeChart(
				switchcur.getId());
		chart = freeChart.createChart();
		return SUCCESS;
	}
	
	public String genAPUserOnlineTimeChart() throws Exception {
		
		DeviceDao device=new DeviceDao();
		SaviFilterTableCur cur=device.getSaviFilterTableCur(switchbasicinfoId);
		
		while(cur==null){
			Thread.sleep(300);
			cur =device.getSaviFilterTableCur(switchbasicinfoId);
		}
		APUserOnlineTimeChart freeChart = new APUserOnlineTimeChart(
				cur.getApid());
		chart = freeChart.createChart();
		return SUCCESS;
	}
	
	public String genAPUserOnlineTimeMonthChart() throws Exception {
		
		DeviceDao device=new DeviceDao();
		SaviFilterTableCur cur=device.getSaviFilterTableCur(switchbasicinfoId);
		
		while(cur==null){
			Thread.sleep(300);
			cur =device.getSaviFilterTableCur(switchbasicinfoId);
		}
		APUserOnlineTimeMonthChart freeChart = new APUserOnlineTimeMonthChart(
				cur.getApid());
		chart = freeChart.createChart();
		return SUCCESS;
	}
	

	public String genInterfaceUserNumberSortChart() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
				ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		InterfaceUserNumberSortChart freeChart = new InterfaceUserNumberSortChart(
				switchcur.getId());
		chart = freeChart.createChart();
		return SUCCESS;
	}

	public String genUserOnlineTimeSortChart() throws Exception {
		Switchcur switchcur=switchDao.getSwitchcurByIPVersionAndSwitchId(
				ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur= switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		UserOnlineTimeSortChart freeChart = new UserOnlineTimeSortChart(
				switchcur.getId());
		chart = freeChart.createChart();
		return SUCCESS;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public Long getSwitchbasicinfoId() {
		return switchbasicinfoId;
	}

	public void setSwitchbasicinfoId(Long switchbasicinfoId) {
		this.switchbasicinfoId = switchbasicinfoId;
	}

	public Integer getIpVersion() {
		return ipVersion;
	}

	public void setIpVersion(Integer ipVersion) {
		this.ipVersion = ipVersion;
	}

}
