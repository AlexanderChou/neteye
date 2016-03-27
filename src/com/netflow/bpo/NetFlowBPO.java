package com.netflow.bpo;

import java.io.FileOutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;

import com.base.util.Constants;

public class NetFlowBPO {
	public void category3DImage(CategoryDataset dataset,String fileName,String type) throws Exception{
		String title = "全网"+type+"TopN显示";
		 JFreeChart chart = ChartFactory.createBarChart3D(
		            title,      // chart title
		            "统计类别",               // domain axis label
		            "统计值",                  // range axis label
		            dataset,                  // data
		            PlotOrientation.VERTICAL, // orientation
		            true,                     // include legend
		            true,                     // tooltips
		            false                     // urls
		        );

		        CategoryPlot plot = chart.getCategoryPlot();
		        plot.setDomainGridlinesVisible(true);
		        CategoryAxis axis = plot.getDomainAxis();
		        axis.setCategoryLabelPositions(
		                CategoryLabelPositions.createUpRotationLabelPositions(
		                        Math.PI / 8.0));
		        BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
		        renderer.setDrawBarOutline(false);
		        String dir = Constants.webRealPath+"file/netflow/downLoadFile/pic/";
//		        String dir = "d:/";
		       
		        
		        FileOutputStream fos = new FileOutputStream(dir+fileName);
			   	ChartUtilities.writeChartAsJPEG(
			   			 fos, 
			   			 1, 
			   			 chart, 
			   			 400, 
			   			 270,
			   			 null 
			   	 );
				fos.close();
	}
}
