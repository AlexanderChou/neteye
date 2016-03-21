package com.savi.show.chart;
//此文件不使用了
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.RectangleEdge;
import com.savi.show.dao.SavibindingtableDao;

/**
* A simple demonstration application showing how to create a bar chart.
*/
public class BindingTableRecordNumChart {
	private static SavibindingtableDao bindingDao = new SavibindingtableDao();
	private Long switchId;
	
	public BindingTableRecordNumChart(Long switchId) {
		this.switchId = switchId;
	}

	/**
     * Creates a sample chart.
     *
     * @param dataset the dataset.
     *
     * @return The chart.
     */
	public JFreeChart createChart() {	
		long maxbindingnum = bindingDao.getInterfaceBindingNum(switchId);
		//寻找最大刻度范围
		long range = maxbindingnum+bindingDao.getInterfaceFilteringNum(switchId);	
		int k=0;
		while(true){
			if((range>=0)&&(range<10))break;
			range/=10;
			k++;
		}
		long maxRange = range;
		//如果过半显示10的整倍数
		if(range<1) maxRange = 1;
		if((range>=1)&&(range<5)){
			maxRange = 5;
		}
		if((range>=5)&&(range<10)){
		    maxRange = 10;
		}	
		for(int j=0;j<k;j++){
			maxRange *= 10;
		}
		//保证表盘值不会超过表盘的最大范围
		if(maxRange<maxbindingnum){
			maxRange=maxbindingnum;
		}
		DefaultValueDataset dataset = new DefaultValueDataset(maxbindingnum);
        MeterPlot plot = new MeterPlot(dataset);
        plot.setRange(new Range(0, maxRange));
        //显示刻度线上的数字，此循环使表盘显示5个刻度线
        for(int i=0;i<5;i++){
        	float start = ((float)i/5)*maxRange;
        	float end = ((float)(i+1)/5)*maxRange;
            plot.addInterval(new MeterInterval("",new Range(start, end),Color.darkGray,new BasicStroke(1.8F),new Color(153,193,245)));
        }
        
        plot.setNeedlePaint(Color.blue);
        plot.setDialBackgroundPaint(Color.lightGray);
        plot.setDialOutlinePaint(Color.black);
        plot.setDialShape(DialShape.CIRCLE);
        plot.setMeterAngle(280);
        plot.setTickLabelsVisible(true);
        plot.setTickLabelFont(new Font("Dialog", Font.BOLD, 16));
        plot.setTickLabelPaint(Color.darkGray);
        plot.setTickSize(maxRange/20.0);    
        plot.setTickPaint(Color.darkGray);    
        plot.setValuePaint(Color.black);
        plot.setValueFont(new Font("Dialog", Font.BOLD, 0));//不显示表盘值
        
        JFreeChart chart = new JFreeChart("", 
                JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        chart.setBackgroundPaint(Color.white); //设置图形背景色
        //增设一个副标题在图形的下方
        TextTitle subtitle = new TextTitle("绑定表项数");
        subtitle.setFont(new Font("黑体", Font.BOLD, 18));
        subtitle.setPaint(Color.black);
        subtitle.setPosition(RectangleEdge.BOTTOM);
        chart.addSubtitle(subtitle);
        //设置消除字体的锯齿渲染（解决中文问题）
        //chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        return chart;
	}
	
	public void saveChartPicture(Long switchId,JFreeChart chart){
		String path = "D:\\savi\\pic\\";
		FileOutputStream fos_jpg = null; 
		try { 
			fos_jpg=new FileOutputStream(path + "绑定表项数 Switch_" + switchId + ".jpg"); 
			ChartUtilities.writeChartAsJPEG(fos_jpg,chart,200,240); 		
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