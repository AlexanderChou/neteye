package com.savi.show.chart;

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import com.savi.show.dao.SavibindingtableDao;

/**
* A simple demonstration application showing how to create a bar chart.
*/
public class UserOnlineTimeSortChartBackup {
	private static SavibindingtableDao bindingTableDao = new SavibindingtableDao();
	private Long switchId;
	
	public UserOnlineTimeSortChartBackup(Long switchId) {
		this.switchId = switchId;
	}
	
	/**
     * Returns a sample dataset.
     *
     * @return The dataset.
     */
	@SuppressWarnings({"unchecked"})
	private CategoryDataset createDataset(Long switchId) {
		String[] name = new String[10];
		String series = "";
		Long[] time = new Long[10];
		List list = bindingTableDao.getSwitchhisOnlineTimeUsers(switchId);
		Iterator it = list.iterator();
		int len = 0;
		while((it.hasNext())&&(len<10)){
			Object[] obj = (Object[])it.next();
			long t = Long.parseLong(obj[0].toString());
			if(t<=0L)time[len]=0L; //确保最小值是0
			else time[len]=t;
			String ip = obj[1].toString();
			//目前没有用户表时，用户名直接指定为IP地址
			name[len]=ip;
			len++;
			//将来有用户表了使用下面这段
			/*
			if(userDao.getOnlineUserName(ip).size()>0){
				String username =userDao.getOnlineUserName(ip).get(0).toString();	
				if(username!=null){
					name[len]=username;
					len++;
				}	
			}
			*/
		}
		insertionSort(time,name,len);//根据time对time和name从大到小排序
		//create the dataset...
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=0;i< len ;i++){
			dataset.addValue((double)(time[i]/3600000.0), series, name[i]);
		}
		/*
		for(int j=len;j<10;j++){
			dataset.addValue(0, series, " ");
		}
		*/
		return dataset;
	}

	/**
     * Creates a sample chart.
     *
     * @param dataset the dataset.
     *
     * @return The chart.
     */
	public JFreeChart createChart() {
		CategoryDataset dataset = createDataset(switchId);
		// create the chart...
		JFreeChart chart = ChartFactory.createBarChart(
				"", // chart title
				"", // domain axis label
				"", // range axis label
				dataset, // data
				PlotOrientation.HORIZONTAL, // orientation
				false, // include legend
				true, // tooltips?
				false // URLs?
		);
		chart.setBackgroundPaint(Color.white);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinesVisible(true);   //设置是否显示垂直网格线
		plot.setRangeGridlinePaint(Color.lightGray);  //设置垂直条纹颜色
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		if(rangeAxis.getUpperBound()<=1){			
			rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
			rangeAxis.setUpperBound(1.0);
			rangeAxis.setTickUnit(new NumberTickUnit(0.2));
		}else{
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		}
		rangeAxis.setUpperMargin(0.05);
		CategoryAxis domainAxis = plot.getDomainAxis(); 
		domainAxis.setLowerMargin(0.05); 
		domainAxis.setUpperMargin(0.05); 
		BarRenderer renderer = (BarRenderer)plot.getRenderer();
		renderer.setDrawBarOutline(false);
		// set up gradient paints for series...
		GradientPaint gp0 = new GradientPaint(
				300.0f, 270.0f, new Color(169,1,148).darker(),
				0.0f, 0.0f,Color.lightGray.brighter(),true
		);
		renderer.setSeriesPaint(0, gp0);
		renderer.setItemLabelsVisible(true);
		renderer.setMaximumBarWidth(0.1); //设置柱状条的最大宽度是10%
		//renderer.setShadowVisible(false);
		return chart;
	}
	
	private void insertionSort(Long[] data,String[] category,int len){
		for(int j=1;j<len;j++){
			Long max = data[j];
			String pos = category[j];
			int i = j-1;
			while((i>=0)&&(data[i]<max)){
				data[i+1]=data[i];
				category[i+1]=category[i];
				i = i-1;
			}
			data[i+1]=max;
			category[i+1]=pos;
		}
	}
	
	
	public void saveChartPicture(Long switchId,JFreeChart chart){
		String path = "D:\\savi\\pic\\";
		FileOutputStream fos_jpg = null; 
		try { 
			fos_jpg=new FileOutputStream(path + "用户在线时间排名 switch_" + switchId + ".jpg"); 
			ChartUtilities.writeChartAsJPEG(fos_jpg,chart,250,200); 		
		} 
		catch (Exception e){} 
		finally {
            try {
            	fos_jpg.close(); 
            }
            catch (Exception e){}
        }
	}
}