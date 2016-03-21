package com.savi.show.chart;

import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import com.savi.show.dao.SwitchDao;

/**
* A simple demonstration application showing how to create a bar chart.
*/
public class SwitchUserNumChart {
	private SwitchDao switchDao = new SwitchDao();
	private Long subnetId;
	
	public SwitchUserNumChart(Long subnetId) {
		this.subnetId = subnetId;
	}
	
	/**
     * Returns a sample dataset.
     *
     * @return The dataset.
     */
	@SuppressWarnings({"unchecked"})
	private CategoryDataset createDataset(Long subnetId) {
		List list = switchDao.getSwitchUserNum(subnetId);
		Iterator it = list.iterator();
		// row keys...
		String[] series = new String[10];
		// column keys...
		String category1 = "";
		// store data
		int[] data = new int[10];
		int len = 0;
		while(it.hasNext() && len<10){
			Object[] obj = (Object[])it.next();
			Integer ipVersion=(Integer)obj[2];
			if(ipVersion.intValue()==1){
				series[len]= (String) obj[1]+"-v4";
			}else{
				series[len]= (String) obj[1]+"-v6";
			}
			try{
				data[len] = Integer.parseInt(obj[3].toString());
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
			len++;
		}
		//create the dataset...
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=0;i< len ;i++){
			dataset.addValue(data[i], series[i], category1);
		}
		/*
		for(int j=len;j<10;j++){
			dataset.addValue(0, "  ", category1);
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
		CategoryDataset dataset = createDataset(subnetId);
		// create the chart...
		JFreeChart chart = ChartFactory.createBarChart(
				"", // chart title
				"", // domain axis label
				"", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
		);
		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		// set the background color for the chart...
		chart.setBackgroundPaint(Color.white);
		// get a reference to the plot for further customisation...
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinesVisible(true);   //设置是否显示垂直网格线
		plot.setRangeGridlinePaint(Color.lightGray);  //设置垂直条纹颜色	
		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		CategoryAxis domainAxis=plot.getDomainAxis();
		domainAxis.setLowerMargin(0.05);//第一条柱状条与数值轴之间的空白间隔距
		//disable bar outlines...
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false); 
		renderer.setItemMargin(0);  //设置柱状条之间的距离为零
		renderer.setMaximumBarWidth(0.1); //设置柱状条的最大宽度是10%
		//renderer.setShadowVisible(false);
		AbstractRenderer r1 = (AbstractRenderer) plot.getRenderer(0);
		// change the paint for series 0, 1 and 2...
		r1.setSeriesPaint(0, new Color(71,117,181));
		r1.setSeriesPaint(1, new Color(181,73,74));
		r1.setSeriesPaint(2, new Color(140,166,82));
		r1.setSeriesPaint(3, new Color(115,89,148));
		r1.setSeriesPaint(4, new Color(66,154,181));
		r1.setSeriesPaint(5, new Color(231,134,66));
		r1.setSeriesPaint(6, new Color(148,170,214));
		r1.setSeriesPaint(7, new Color(222,150,156));
		r1.setSeriesPaint(8, new Color(189,207,148));
		r1.setSeriesPaint(9, new Color(215,210,28));
	    chart.setBackgroundPaint(Color.white);  //设置图形背景色
		chart.setBorderVisible(false);          //设置图形外边框不显示
		chart.getLegend().setBorder(0, 0, 0, 0);//设置图示外边框宽度为0
	    //chart.getLegend().setPosition(RectangleEdge.RIGHT); //设置图示在图表中的右侧
		chart.getLegend().setBackgroundPaint(Color.white);//设置图示的背景色
	    //设置底部图标的大小和字体
	    chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 10));
	    return chart;
	}
	
	public void saveChartPicture(Long subnetId,JFreeChart chart){
		String path = "D:\\savi\\pic\\";
		FileOutputStream fos_jpg = null; 
		try { 
			fos_jpg=new FileOutputStream(path + "交换机用户数" + subnetId + ".jpg"); 
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