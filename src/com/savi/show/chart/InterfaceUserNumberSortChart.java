package com.savi.show.chart;

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import com.savi.show.dao.IfInterfaceDao;
import com.savi.show.dao.SavibindingtableDao;

/**
* A simple demonstration application showing how to create a bar chart.
*/
public class InterfaceUserNumberSortChart {
	private static IfInterfaceDao interfaceDao = new IfInterfaceDao();
	private static SavibindingtableDao bindingTableDao = new SavibindingtableDao();
	private Long switchId;
	
	public InterfaceUserNumberSortChart(Long switchId) {
		this.switchId = switchId;
	}
	
	/**
     * Returns a sample dataset.
     *
     * @return The dataset.
     */
	@SuppressWarnings({"unchecked"})
	private CategoryDataset createDataset(Long switchId) {
		// row keys...
		ArrayList<Integer> category=new ArrayList<Integer>();
		//int[] category = new int[10];
		// column keys...
		String series = "";
		// store data
		ArrayList<Integer> data=new ArrayList<Integer>();
		//int[] data = new int[10];
		List list = interfaceDao.getInterfaceIndex(switchId);
		Iterator it = list.iterator();
		while(it.hasNext()){
			Object[] obj = (Object[])it.next();
			int ifinterfacecurID = Integer.parseInt(obj[0].toString());
			category.add(Integer.parseInt(obj[1].toString()));
			int usernum = bindingTableDao.getInterfaceUserNum(ifinterfacecurID);
			if(usernum <= 0)data.add(0); //确保最小值是0
			else data.add(usernum);
		}
		insertionSort(data,category,data.size());//根据data的对category和data从大到小排序
		//create the dataset...
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int num = 0;
		if(data.size() < 10)num = data.size();
		else num = 10;
		for(int i=0;i< num ;i++){
			dataset.addValue(data.get(i), series, Integer.toString(category.get(i)));
		}
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
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setUpperMargin(0.1);
		CategoryAxis domainAxis = plot.getDomainAxis(); 
		domainAxis.setLowerMargin(0.05); 
		domainAxis.setUpperMargin(0.05); 
		BarRenderer renderer = (BarRenderer)plot.getRenderer();
		renderer.setDrawBarOutline(false);
		// set up gradient paints for series...
		GradientPaint gp0 = new GradientPaint(
				0.0f, 200.0f, new Color(11,179,11).darker(),
				500.0f, 200.0f, Color.lightGray.brighter()
		);
		renderer.setSeriesPaint(0, gp0);
		//renderer.setItemMargin(0.1);
		renderer.setItemLabelsVisible(true);
		renderer.setMaximumBarWidth(0.1); //设置柱状条的最大宽度是10%
		//renderer.setShadowVisible(false);
		return chart;
	}
	
	private void insertionSort(ArrayList<Integer> data,ArrayList<Integer> category,int len){
		for(int j=1;j<len;j++){
			int max = data.get(j);
			int pos = category.get(j);
			int i = j-1;
			while((i>=0)&&(data.get(i)<max)){
				data.set(i+1,data.get(i));
				category.set(i+1, category.get(i));
				i = i-1;
			}
			data.set(i+1, max);
			category.set(i+1, pos);
		}
	}
	
	
	public void saveChartPicture(Long switchId,JFreeChart chart){
		String path = "D:\\savi\\pic\\";
		FileOutputStream fos_jpg = null; 
		try { 
			fos_jpg=new FileOutputStream(path + "端口用户数排名 switch_" + switchId + ".jpg"); 
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