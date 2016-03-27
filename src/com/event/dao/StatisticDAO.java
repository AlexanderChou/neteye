package com.event.dao;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Rotation;

import com.base.service.EventService;
import com.base.util.Constants;
/*
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
*/
/**
 * <p>Title: 对事件进行统计处理的业务操作类</p>
 * <p>Description: 根据用户的输入，对事件进行统计，并将统计结果采用JFreeChar生成相应的图形</p>
 * @version 1.0
 * @author 郭玺
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class StatisticDAO {
	private EventService eventService = new EventService();
	private String path = Constants.webRealPath +"images/";
	public String createStatisticImage(String moduleId,String fromTime,String toTime) throws Exception{
		String imageName = "";
		String title = "";
		List result = null;
		JFreeChart chart = null;
		if(moduleId!=null && !"".equals(moduleId)){
			result = eventService.getFieldStatistic(moduleId, fromTime, toTime);
			PieDataset dataset = createPieDataset(result);
			if(moduleId.equals("moduleId")){
				imageName = "moduleId"+".jpg";
				//title = "事件统计(按模块分)";
				title = "EventStatistic(by module)";
			}else if(moduleId.equals("title")){
				imageName = "title"+".jpg";
				//title = "事件统计(按事件主题分)";
				title = "EventStatistic(by topic)";
			}else{
				imageName = "typeValue"+".jpg";
				//title = "事件统计(按事件值分)";
				title = "EventStatistic(by value)";
			}
			
			
			chart = createChart(dataset,title);
		}else{
			result = eventService.getTop10Ip(fromTime, toTime);
			imageName = "topIP"+".jpg";
			chart = createChart(createBarDataset(result),result);
		}
		createFile(chart,imageName);
		
		//根据结果生成相应的图形
		return imageName; 
	}
	//创建三维饼状图结果集
	private static PieDataset createPieDataset(List list) {        
        DefaultPieDataset result = new DefaultPieDataset();
        for(int i=0;i<list.size();i++){
        	List temp = (List)list.get(i);
        	result.setValue(temp.get(0).toString()+"("+temp.get(1).toString()+")", new Double(temp.get(1).toString()));
        }
        return result;
        
    }
	//创建三维柱状图结果集
	private static CategoryDataset createBarDataset(List list) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        for(int i=0;i<list.size();i++){
//			Object[] test = ((List)list.get(i)).toArray();
//			dataset.addValue(Long.valueOf(test[2].toString()), "top10IP", test[0].toString());   
//		}
        Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Object[] obj = (Object[]) iter.next();
		    	dataset.addValue(Long.valueOf(obj[1].toString()), "top10IP", obj[0].toString()); 
		  }
        return dataset;
    }
	//生成三维饼状图
	private static JFreeChart createChart(PieDataset dataset,String title) {
        JFreeChart chart = ChartFactory.createPieChart3D(
            title,  // chart title
            dataset,                // data
            true,                   // include legend
            true,
            false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 0, 1000, Color.green));
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setNoDataMessage("No data to display");
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
        "{0} ({2})"));
        return chart;
        
    }
	 private static JFreeChart createChart(CategoryDataset dataset,List list) {
	        
	        JFreeChart chart = ChartFactory.createBarChart3D(
	            "top10 IP",      // chart title
	            "eventTotal",               // domain axis label 事件总数
	            "IP",                  // range axis label IP地址
	            dataset,                  // data
	            PlotOrientation.HORIZONTAL, // orientation
	            false,                     // include legend
	            true,                     // tooltips
	            false                     // urls
	        );

	        CategoryPlot plot = (CategoryPlot) chart.getPlot();
	        CustomBarRenderer3D renderer = new CustomBarRenderer3D();
	        plot.setRenderer(renderer);
	        /*注：下面几个语句的作用是设置一个最小阈值
	        ValueMarker marker = new ValueMarker(0.70, new Color(200, 200, 255), 
	                new BasicStroke(1.0f), new Color(200, 200, 255), 
	                new BasicStroke(1.0f), 1.0f);
	        marker.setLabel("Minimum grade to pass");
	        marker.setLabelPaint(Color.red);
	        marker.setLabelAnchor(RectangleAnchor.BOTTOM_LEFT);
	        marker.setLabelTextAnchor(TextAnchor.TOP_LEFT);
	        plot.addRangeMarker(marker, Layer.BACKGROUND);
	        */
	        renderer.setItemLabelsVisible(true);
	        renderer.setMaximumBarWidth(0.05);
	        String labelFlag = "";//设置一个阈值，若某一IP的事件总数超过30，用红色进行显示，同时会取第一个IP地址的值作为提示标签的位置
	        if(list!=null && list.size()>0){
	        	labelFlag = ((Object[])list.get(0))[0].toString();
	        }
	        CategoryTextAnnotation a = new CategoryTextAnnotation("event over 30", labelFlag, 50);//事件总数超过30阈值
	        a.setCategoryAnchor(CategoryAnchor.START);
	        a.setFont(new Font("SansSerif", Font.PLAIN, 12));
	        a.setTextAnchor(TextAnchor.BOTTOM_LEFT);
	        plot.addAnnotation(a);
	        
	        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setNumberFormatOverride(NumberFormat.getNumberInstance());
	        return chart;

	    }
	//输出生成的jpg文件
	public void createFile(JFreeChart chart,String fileName) throws Exception{
		FileOutputStream fos = null;
		fos = new FileOutputStream(path+fileName);
		ChartUtilities.writeChartAsJPEG(
			fos, 
			1, 
			chart, 
			676, 
			361,
			null 
			);
		fos.close();
	}
    /**
     * A custom renderer that returns a different color for each item in a 
     * single series.
     * 注：设置一个阈值，若某一IP的事件总数超过30，用红色进行显示，否则用绿色进行显示
     */
    static class CustomBarRenderer3D extends BarRenderer3D {

        /**
         * Creates a new renderer.
         */
        public CustomBarRenderer3D() {
        }

        /**
         * Returns the paint for an item.  Overrides the default behaviour 
         * inherited from AbstractSeriesRenderer.
         *
         * @param row  the series.
         * @param column  the category.
         *
         * @return The item color.
         */
        public Paint getItemPaint(int row, int column) {
            CategoryDataset dataset = getPlot().getDataset();
            double value = dataset.getValue(row, column).doubleValue();
            if (value <= 30) {
                return Color.green;   
            }
            else {
                return Color.red;   
            }
        }
    }
}
