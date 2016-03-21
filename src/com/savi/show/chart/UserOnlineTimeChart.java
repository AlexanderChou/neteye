package com.savi.show.chart;

import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import com.savi.base.model.Savibindingtablehis;
import com.savi.base.util.Constants;
import com.savi.show.dao.SavibindingtableDao;

/**
* A simple demonstration application showing how to create a line chart using data from a
* {@link CategoryDataset}.
*/
public class UserOnlineTimeChart{
	private SavibindingtableDao bingTableDao = new SavibindingtableDao();
	private Long subnetId;
	
	public UserOnlineTimeChart(Long subnetId) {
		this.subnetId = subnetId;
	}
/**
* Creates a sample dataset.
*
* @return The dataset.
*/
@SuppressWarnings("unchecked")
private XYDataset createDataset(Long subnetId) {
    TimeSeries staticnum = new TimeSeries("STATIC ", Minute.class);
    TimeSeries dhcpnum = new TimeSeries("DHCP ", Minute.class);
    TimeSeries slaacnum = new TimeSeries("SLAAC ", Minute.class);
    List staticlist = bingTableDao.getSwitchhisStaticUsers(subnetId);
    List slaaclist = bingTableDao.getSwitchhisSlaacUsers(subnetId);
    List dhcplist = bingTableDao.getSwitchhisDhcpUsers(subnetId);
    int staticcount = staticlist.size();
    int slaaccount = slaaclist.size();
    int dhcpcount = dhcplist.size();
    int temp=86400/Constants.pollingInterval;
    for(int i=0;i<=temp;i++){
    	long period = Constants.pollingInterval * 1000;
    	long time = (System.currentTimeMillis()/period)*period - period*i;
    	//long time = 1271164000000L - 300000*i;
    	int staticOnline = searchOnTimeList(staticlist,time);
    	int slaacOnline = searchOnTimeList(slaaclist,time);
    	int dhcpOnline = searchOnTimeList(dhcplist,time);
		int totalminute = (Constants.pollingInterval/60)*i;
		int hour = totalminute/60;
		int minute = totalminute%60;
    	try{
    		// 正式运行时使用这段：
    		if(staticcount>0){
    			staticnum.addOrUpdate(new Minute(minute,new Hour(hour,new Day())), ((double)staticOnline)/staticcount);
    		}else{
    			staticnum.addOrUpdate(new Minute(minute,new Hour(hour,new Day())), 0);
    		}
    		if(slaaccount>0){
    			slaacnum.addOrUpdate(new Minute(minute,new Hour(hour,new Day())), ((double)slaacOnline)/slaaccount);
    		}else{
    			slaacnum.addOrUpdate(new Minute(minute,new Hour(hour,new Day())), 0);
    		}
    		if(dhcpcount>0){
    			dhcpnum.addOrUpdate(new Minute(minute,new Hour(hour,new Day())), ((double)dhcpOnline)/dhcpcount);
    		}else{
    			dhcpnum.addOrUpdate(new Minute(minute,new Hour(hour,new Day())), 0);
    		}
    		/*
    		//测试使用start：
    		Date curdate = new Date(1271164000000L);
    		if(staticOnline>0){
    			staticnum.add(new Minute(minute,new Hour(hour,new Day(curdate))), ((double)staticOnline)/staticcount);
    		}
    		if(slaacOnline>0){
    			slaacnum.add(new Minute(minute,new Hour(hour,new Day(curdate))), ((double)slaacOnline)/slaaccount);
    		}
    		if(dhcpOnline>0){
    			dhcpnum.add(new Minute(minute,new Hour(hour,new Day(curdate))), ((double)dhcpOnline)/dhcpcount);
    		}
    		//测试使用end.
    		*/
    	}catch (Exception e) {
    		e.printStackTrace();
            //System.err.println(e.getMessage());
        } 
    }    
    TimeSeriesCollection dataset = new TimeSeriesCollection();
    dataset.addSeries(staticnum);
    dataset.addSeries(dhcpnum);
    dataset.addSeries(slaacnum);
    return dataset;
}

/**
* Creates a sample chart.
*
* @param dataset a dataset.
*
* @return The chart.
*/
public JFreeChart createChart() {
	XYDataset dataset = createDataset(subnetId);
    JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "", 
            "", 
            "",
            dataset, 
            true, 
            true, 
            false
        );
        //得到所有数据点的集合
        XYPlot xyplot = (XYPlot)chart.getPlot();
        XYItemRenderer renderer = xyplot.getRenderer();
        xyplot.setDomainGridlinesVisible(true);  //设置是否显示垂直网格线
        xyplot.setRangeGridlinesVisible(true);   //设置是否显示水平网格线
        xyplot.setRangeGridlinePaint(Color.lightGray);  //设置横向条纹颜色
        xyplot.setDomainGridlinePaint(Color.lightGray);     //设置纵向条纹颜色
        xyplot.setBackgroundPaint(Color.white);
        //设置横坐标从原点开始，最大值结束
        DateAxis domainAxis = new DateAxis();
        SimpleDateFormat frm = new SimpleDateFormat("H");
        domainAxis.setDateFormatOverride(frm);//设置横轴上的日期刻度格式
        domainAxis.setTickUnit(new DateTickUnit(DateTickUnit.HOUR, 2,frm)); //设置时间轴间距是2小时 
        domainAxis.setLowerMargin(0.0);
        domainAxis.setUpperMargin(0.0);
        xyplot.setDomainAxis(domainAxis);
        //得到纵轴
        NumberAxis numAxis = (NumberAxis)xyplot.getRangeAxis();
        NumberFormat nf =NumberFormat.getPercentInstance();
        numAxis.setNumberFormatOverride(nf);//设置y轴以百分比方式显示
        //numAxis.setLowerBound(0);  //设置纵轴的最小值是0%
        //numAxis.setUpperBound(1);  //设置纵轴的最小值是100%
        StandardXYToolTipGenerator g = new StandardXYToolTipGenerator(
            StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
            new SimpleDateFormat("HH"), nf);
        renderer.setToolTipGenerator(g);
        chart.setBackgroundPaint(Color.white); //设置图形背景色
        renderer.setSeriesPaint(0, new Color(71,166,7)); //设置第一条线static的绿色
        renderer.setSeriesPaint(1, new Color(218,209,39));//设置第二条线dhcp的黄色
        renderer.setSeriesPaint(2, new Color(207,129,204)); //设置第三条线slaac的紫色
	    chart.setBorderVisible(false);          //设置图形外边框不显示
	    chart.getLegend().setBorder(0, 0, 0, 0);//设置图形外边框宽度为0
	    chart.getLegend().setBackgroundPaint(Color.white);//设置底部底部图标的背景色
        //设置底部图标的大小和字体
        chart.getLegend().setItemFont(new Font("Dialog", Font.CENTER_BASELINE, 10));
        return chart;
}

public void saveChartPicture(Long subnetId,JFreeChart chart){
	String path = "D:\\savi\\pic\\";
	FileOutputStream fos_jpg = null; 
	try { 
		fos_jpg=new FileOutputStream(path + "用户在线时间" + subnetId + ".jpg"); 
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
	@SuppressWarnings({"unchecked"})
    private int searchOnTimeList(List list,long time){
    	Iterator it = list.iterator();
    	int count = 0;
    	while(it.hasNext()){
    		Savibindingtablehis sbtable = (Savibindingtablehis)it.next();
    		if(sbtable.getStartTime() <= time){
    			count++;
    		}
    	}
    	return count;
    }
}