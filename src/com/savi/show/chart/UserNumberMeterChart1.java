package com.savi.show.chart;
//此文件不使用了
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import com.savi.show.dao.SwitchDao;

/**
* A simple demonstration application showing how to create a bar chart.
*/
public class UserNumberMeterChart1 {
	private static SwitchDao switchDao = new SwitchDao();
	private Long switchId;
	
	public UserNumberMeterChart1(Long switchId) {
		this.switchId = switchId;
	}

	/**
     * Creates a sample chart.
     *
     * @param dataset the dataset.
     *
     * @return The chart.
     */
	public void createChart() {	
		Long maxfilternum = switchDao.getSwitchFilteringRecordNum(switchId);
		generateMaxFilteringNumPic(maxfilternum.toString());
	}
	
	public static void generateMaxFilteringNumPic(String val) {
		try {
			String path = "D:\\savi\\pic\\";
			File filePath=new File(path); 
			if(!filePath.exists()){
				filePath.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(path+"User Number.png");
			System.setProperty("java.awt.headless","true");
			File _file = new File(path+"input.png"); 
			
			Image image = javax.imageio.ImageIO.read(_file); 

			int width = image.getWidth(null); 
			int height = image.getHeight(null); 
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			
			Graphics g = tag.getGraphics();

			g.drawImage(image, 0, 0, width, height, null); 
			int x, y;
			int r = 4, R = 34;

			double endx, endy;

			x = width / 2;
			y = height / 2;

			g.setColor(Color.BLUE);
			g.fillOval(x - r/2 , y - r/2, r, r);
			g.setColor(Color.BLACK);
			Font f = new Font("Dialog", Font.BOLD, 10);
			g.setFont(f);
			Double value = Double.parseDouble(val);
			if(value>=1000000000){
				g.drawString("B", x  - 2, y + 28); //billion
				value = value/1000000000;
			}else if(value>=1000000){
				g.drawString("M", x  - 2, y + 28); //million
				value = value/1000000;
			}else if(value>=1000){
				g.drawString("T", x  - 2, y + 28); //thousand
				value = value/1000;
			}
			Font f1 = new Font("Dialog", Font.BOLD, 10);
			g.setFont(f1);
			g.drawString("User Number", x - 21 - "User Number".length(), y + 42);
			g.setFont(f);
			int k = 0;
			Double range = value;
			int mag = 1; //数量级
			//计算值的数量级K
			while(true){
				if((range>=0)&&(range<10))break;
				range/=10;
				k++;
			}
			for(int j=0;j<k;j++){
				mag *= 10;
			}
			int i1 = 0;
			//根据值的数量级不同，绘制的每个刻度的位置也不同
			if(mag<=10){
				for (double c = -1.57 / 2; i1 < 11; c = c + 3.1415926 / 10 * 3, i1 = i1 + 2) {
					g.drawString("" + Integer.toString((int) i1 * mag),
							(int) (x - (R - 10) * Math.cos(c)) - 3,
							(int) (y - (R - 10) * Math.sin(c)) + 4);

				}
			}else if(mag<=100){
				for (double c = -1.57 / 2; i1 < 11; c = c + 3.1415926 / 10 * 3, i1 = i1 + 2) {
					g.drawString("" + Integer.toString((int) i1 * mag),
							(int) (x - (R - 10) * Math.cos(c)) - 5,
							(int) (y - (R - 10) * Math.sin(c)) + 4);

				}
			}else{
				for (double c = -1.57 / 2; i1 < 11; c = c + 3.1415926 / 10 * 3, i1 = i1 + 2) {
					g.drawString("" + Integer.toString((int) i1 * mag),
							(int) (x - (R - 10) * Math.cos(c)) - 7,
							(int) (y - (R - 10) * Math.sin(c)) + 4);

				}
			}

			g.setColor(Color.BLUE);
			double offset;
			offset = -1.57 / 2 + (value)*3.1416 * 3/(20*mag);	
			endx = x - R * Math.cos(offset);
			endy = y - R * Math.sin(offset);

			int xs[] = new int[3];
			int ys[] = new int[3];

			xs[0] = x - (int) (r * Math.cos(offset - 3.1415926 / 2.0));
			xs[1] = (int) endx;
			xs[2] = x + (int) (r * Math.cos(offset - 3.1415926 / 2.0));

			ys[0] = y - (int) (r * Math.sin(offset - 3.1415926 / 2.0));
			ys[1] = (int) endy;
			ys[2] = y + (int) (r * Math.sin(offset - 3.1415926 / 2.0));
			g.fillPolygon(xs, ys, 3);
			g.dispose();
			
			ImageIO.write(tag, "png", fos);
			fos.close();
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}