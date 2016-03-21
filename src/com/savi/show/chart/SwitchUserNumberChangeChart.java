package com.savi.show.chart;

import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class SwitchUserNumberChangeChart{
	private static SavibindingtableDao savibindingtableDao = new SavibindingtableDao();
	private Long switchId;
	
	public SwitchUserNumberChangeChart(Long switchId) {
		this.switchId = switchId;
	}
/**
* Creates a sample dataset.
*
* @return The dataset.
*/
@SuppressWarnings({"unchecked"})
private XYDataset createDataset(Long switchId) {
    TimeSeries staticnum = new TimeSeries("STATIC ", Minute.class);
    TimeSeries dhcpnum = new TimeSeries("DHCP ", Minute.class);
    TimeSeries slaacnum = new TimeSeries("SLAAC ", Minute.class);
    List list = savibindingtableDao.getSwitchOnlineTimeUsers(switchId);
    int pretime = 0;
    boolean preday = true;
    int temp=86400/Constants.pollingInterval;
    for(int i=temp;i>=0;i--){
    	long period = Constants.pollingInterval * 1000;
    	long time = (System.currentTimeMillis()/period)*period - period*i;
    	int stnum = 0, dhnum = 0, slnum = 0;
    	stnum = searchOnTimeList(list,time,1); //寻找list列表中在time时间点上的static用户数
    	slnum = searchOnTimeList(list,time,2); //寻找list列表中在time时间点上的slaac用户数
    	dhnum = searchOnTimeList(list,time,3); //寻找list列表中在time时间点上的dhcp用户数  	
		int daytime = (int)(time%86400000)/60000; //一天中总的分钟数
		int hour = daytime/60 + 8; //加上8,转换为北京时间
		int minute =  daytime%60;
		if(daytime < pretime) preday = false;
    	try{
    		// 正式运行时使用这段：
    		if((preday)&&(daytime!=0)){
    			Date predate = new Date(System.currentTimeMillis() - 1000 * 3600 * 24); 
    			staticnum.addOrUpdate(new Minute(minute,new Hour(hour,new Day(predate))), stnum);
            	dhcpnum.addOrUpdate(new Minute(minute,new Hour(hour,new Day(predate))), dhnum);
            	slaacnum.addOrUpdate(new Minute(minute,new Hour(hour,new Day(predate))), slnum);
    		}else{
            	staticnum.addOrUpdate(new Minute(minute,new Hour(hour,new Day())), stnum);
            	dhcpnum.addOrUpdate(new Minute(minute,new Hour(hour,new Day())), dhnum);
            	slaacnum.addOrUpdate(new Minute(minute,new Hour(hour,new Day())), slnum);
    		}
    		/*
    		//测试使用start：
    		if((preday)&&(daytime!=0)){
    			Date predate = new Date(1271164000000L - 1000 * 3600 * 24);
    			//System.out.println("preDate=" + predate);
    			staticnum.add(new Minute(minute,new Hour(hour,new Day(predate))), stnum);
            	dhcpnum.add(new Minute(minute,new Hour(hour,new Day(predate))), dhnum);
            	slaacnum.add(new Minute(minute,new Hour(hour,new Day(predate))), slnum);
    		}else{
    			Date curdate = new Date(1271164000000L);
    			//System.out.println("curdate=" + curdate);
            	staticnum.add(new Minute(minute,new Hour(hour,new Day(curdate))), stnum);
            	dhcpnum.add(new Minute(minute,new Hour(hour,new Day(curdate))), dhnum);
            	slaacnum.add(new Minute(minute,new Hour(hour,new Day(curdate))), slnum);
    		}
    		//测试使用end.
    		*/
    		pretime = daytime;
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
	XYDataset dataset = createDataset(switchId);
    JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "", 
            "", 
            "",
            dataset, 
            true, 
            true, 
            false
        );
        XYItemRenderer renderer = chart.getXYPlot().getRenderer();
        StandardXYToolTipGenerator g = new StandardXYToolTipGenerator(
            StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
            new SimpleDateFormat("HH"), NumberFormat.getIntegerInstance());
        renderer.setToolTipGenerator(g);
        XYPlot localXYPlot = (XYPlot)chart.getPlot();
        localXYPlot.setBackgroundPaint(Color.white);
        localXYPlot.setDomainGridlinesVisible(true);  //设置是否显示垂直网格线
        localXYPlot.setRangeGridlinesVisible(true);   //设置是否显示水平网格线
        localXYPlot.setRangeGridlinePaint(Color.lightGray);  //设置横向条纹颜色
        localXYPlot.setDomainGridlinePaint(Color.lightGray);     //设置纵向条纹颜色
        //设置横坐标从原点开始，最大值结束
        DateAxis domainAxis = new DateAxis();
        SimpleDateFormat frm = new SimpleDateFormat("H");
        domainAxis.setDateFormatOverride(frm);//设置横轴上的日期刻度格式
        domainAxis.setTickUnit(new DateTickUnit(DateTickUnit.HOUR, 2,frm)); //设置时间轴间距是2小时 
        domainAxis.setLowerMargin(0.0);
        domainAxis.setUpperMargin(0.0);
        localXYPlot.setDomainAxis(domainAxis);
        //设置纵坐标数值间隔为整数
        NumberAxis numAxis = (NumberAxis)localXYPlot.getRangeAxis();
        numAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        numAxis.setAutoRangeIncludesZero(true);//从0开始 不会出现负数
        //numAxis.setLowerBound(0);  //设置纵轴的最小值是0
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

public void saveChartPicture(Long switchId,JFreeChart chart){
	String path = "D:\\savi\\pic\\";
	FileOutputStream fos_jpg = null; 
	try { 
		fos_jpg=new FileOutputStream(path + "用户数变化曲线 Switch_" + switchId + ".jpg"); 
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
private int searchOnTimeList(List list,long time,int mode){
	Iterator it = list.iterator();
	int num = 0;
	while(it.hasNext()){
		Savibindingtablehis sa = (Savibindingtablehis) it.next();
		if(sa.getEndTime()!=null){
			if(time>=sa.getStartTime()&&time<sa.getEndTime()&& mode==sa.getBindingType()){
    			num++;
    		}
		}else{
			if(time>=sa.getStartTime()&& mode==sa.getBindingType()){
    			num++;
    		}
		}
	}
	return num;
}
}