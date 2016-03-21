package com.savi.show.chart;

import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.savi.show.dao.SwitchDao;

/**
* A simple demonstration application showing how to create a bar chart.
*/
public class IPBindingTypeStateChart {
	private static SwitchDao switchDao = new SwitchDao();
	private Long subnetId;
	
	public IPBindingTypeStateChart(Long subnetId) {
		this.subnetId = subnetId;
	}
	
	/**
     * Returns a sample dataset.
     *
     * @return The dataset.
     */
	private DefaultPieDataset createDataset(Long subnetId) {
		int pie1 = switchDao.getStaticNumCount(subnetId);
		int pie2 = switchDao.getSlaacNumCount(subnetId);
		int pie3 = switchDao.getDhcpNumCount(subnetId);
	    DefaultPieDataset defaultpiedataset = new DefaultPieDataset(); 
	    defaultpiedataset.setValue("STATIC", pie1); 
	    defaultpiedataset.setValue("SLAAC ", pie2); 
	    defaultpiedataset.setValue("DHCP  ", pie3); 
	    return defaultpiedataset; 
	}

	/**
     * Creates a sample chart.
     *
     * @param dataset the dataset.
     *
     * @return The chart.
     */
	@SuppressWarnings({ "deprecation"})
	public JFreeChart createChart() {
		PieDataset p = createDataset(subnetId);
		JFreeChart a =ChartFactory.createPieChart("",p, true, true, false); 
	    PiePlot pie=(PiePlot)a.getPlot(); 
	    //pie.setLabelFont(new Font("SansSerif",Font.BOLD,12)); 
	    pie.setBackgroundPaint(Color.white);
	    pie.setNoDataMessage("No data available"); 
	    pie.setCircular(true); 
	    pie.setLabelGap(0.01D);//每个扇形标记之间的间距 
	    pie.setLabelGenerator(null); //分类标签的格式，设置成null则整个标签包括连接线都不显示
	    pie.setSectionPaint(0, new Color(128,232,119));
	    pie.setSectionPaint(1, new Color(207,129,204));
	    pie.setSectionPaint(2, new Color(243,243,20));
	    
	    //pie.setSectionOutlinesVisible(false);//设置Pie的边框是否可见
        // 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例   
	    pie.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(" {0}                    {2}", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%"))); 
	    /*------这句代码解决了标题乱码的问题-----------*/ 
	    a.getTitle().setFont(new Font("宋体", Font.BOLD,16)); 
	    a.setBackgroundPaint(Color.white); //设置图形背景色
	    a.setBorderVisible(false);//设置图形外边框不显示
	    a.getLegend().setBorder(0, 0, 0, 0);
	    /*------这句代码解决了底部汉字乱码的问题-----------*/ 
	    a.getLegend().setItemFont(new Font("Dialog", Font.CENTER_BASELINE, 9));
	    a.getLegend().setHeight(50);
	    return a; 
	}
	
	public void saveChartPicture(Long subnetId,JFreeChart chart){
		String path = "D:\\savi\\pic\\";
		FileOutputStream fos_jpg = null; 
		try { 
			fos_jpg=new FileOutputStream(path + "IP绑定类型分布" + subnetId + ".jpg"); 
			ChartUtilities.writeChartAsJPEG(fos_jpg,chart,160,215); 		
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