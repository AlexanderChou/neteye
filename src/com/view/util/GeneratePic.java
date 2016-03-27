package com.view.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.base.util.Constants;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

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
public class GeneratePic {
	public static void generatelosspic( String value,String ip) {
		try {
			File filePath=new File( Constants.webRealPath + "images/pic/"); 
			if(!filePath.exists()){
				filePath.mkdirs();
			}
			if(ip.contains(":")){
				ip=ip.replace(":","-");
			}
			FileOutputStream fos = new FileOutputStream(Constants.webRealPath+"images/pic/"+ip+"_loss.png");
			System.setProperty("java.awt.headless","true");
			File _file = new File(Constants.webRealPath+"images/input.png"); 
			
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
			Font f = new Font(null, Font.BOLD, 10);
			g.setFont(f);
			g.drawString("%", x  - 2, y + 25);
			g.drawString("loss", x - 8 - "loss".length(), y + 42);

			int i1 = 0;
			for (double c = -1.57 / 2; i1 < 11; c = c + 3.1415926 / 10 * 3, i1 = i1 + 2) {
				g.drawString("" + Integer.toString((int) i1 * 10),
						(int) (x - (R - 10) * Math.cos(c)) - 6,
						(int) (y - (R - 10) * Math.sin(c)) + 4);

			}
			g.setColor(Color.BLUE);
			double offset;
			offset = -1.57 / 2 + (Double.parseDouble(value))*3.1416 * 3/200;			
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
	public static void generaterttpic( String value,String ip) {
		try {
			if(ip.contains(":")){
				ip=ip.replace(":","-");
			}
			File filePath=new File( Constants.webRealPath + "images/pic/"); 
			if(!filePath.exists()){
				filePath.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(Constants.webRealPath+"images/pic/"+ip+"_rtt.png");
			System.setProperty("java.awt.headless","true");
			File _file = new File(Constants.webRealPath+"/images/input.png"); 		
			Image image = javax.imageio.ImageIO.read(_file); 
			int width = image.getWidth(null); 
			int height = image.getHeight(null); 
			int prefixvalue = 0;
			int newvalue = (int)(Double.parseDouble(value));
			prefixvalue = (newvalue/100)*100;
			newvalue = newvalue - prefixvalue;
			BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
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
			Font f = new Font(null, Font.BOLD, 10);
			g.setFont(f);
			g.drawString("ms", x  - 6, y + 25);
			g.drawString("rtt", x - 8 - "rtt".length(), y + 42);

			int i1 = 0;
			int leftoffset = 6;
			if(prefixvalue >= 100){
				leftoffset = 9;
				f = new Font(null, Font.BOLD, 9);
				g.setFont(f);
			}
			for (double c = -1.57 / 2; i1 < 11; c = c + 3.1415926 / 10 * 3, i1 = i1 + 2) {
				g.drawString("" + Integer.toString((int) i1 * 10 + prefixvalue),
						(int) (x - (R - 10) * Math.cos(c)) - leftoffset,
						(int) (y - (R - 10) * Math.sin(c)) + 4);

			}
			g.setColor(Color.BLUE);
			double offset;
			offset = -1.57 / 2 + (newvalue)*3.1416 * 3/200;
			
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
	public static void generatetemperaturepic( String value,String ip) {
		try {

			File filePath=new File( Constants.webRealPath + "images/pic/"); 
			if(!filePath.exists()){
				filePath.mkdirs();
			}
			if(ip.contains(":")){
				ip=ip.replace(":","-");
			}
			FileOutputStream fos = new FileOutputStream(Constants.webRealPath+"images/pic/"+ip+"_temperature.png");
			System.setProperty("java.awt.headless","true");
			File _file = new File(Constants.webRealPath+"/images/input.png"); 
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
			Font f = new Font(null, Font.BOLD, 10);
			g.setFont(f);
			g.drawOval(x-5,y + 15,3,3);
			g.drawString("C", x  - 2, y + 25);
			g.drawString("temperature", x - 27 - "temperature".length(), y + 42);

			int i1 = 0;
			for (double c = -1.57 / 2; i1 < 11; c = c + 3.1415926 / 10 * 3, i1 = i1 + 2) {
				g.drawString("" + Integer.toString((int) i1 * 10),
						(int) (x - (R - 10) * Math.cos(c)) - 6,
						(int) (y - (R - 10) * Math.sin(c)) + 4);

			}
			g.setColor(Color.BLUE);
			double offset;
			offset = -1.57 / 2 + (Double.parseDouble(value))*3.1416 * 3/200;
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
	public static void generatecpuandmempic(String cpu,String mem,String ip) {
		try {
			if(ip.contains(":")){
				ip=ip.replace(":","-");
			}
			File filePath=new File( Constants.webRealPath + "images/pic/"); 
			if(!filePath.exists()){
				filePath.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(Constants.webRealPath+"images/pic/"+ip+"_cpu_mem.png");
			System.setProperty("java.awt.headless","true");
			File _file = new File(Constants.webRealPath+"/images/input.png"); 
			
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
			
			g.setColor(Color.BLACK);
			Font f = new Font(null, Font.BOLD, 10);
			g.setFont(f);
			g.drawString("%", x  - 2, y + 25);
			g.setColor(Color.BLUE);
			g.drawString("cpu", x - 20 - "cpu".length(), y + 42);
			g.setColor(Color.RED);
                      	  	g.drawString("-mem", x - 5, y + 42);
                      	  	


			g.setColor(Color.BLACK);
			int i1 = 0;
			for (double c = -1.57 / 2; i1 < 11; c = c + 3.1415926 / 10 * 3, i1 = i1 + 2) {
				g.drawString("" + Integer.toString((int) i1 * 10),
						(int) (x - (R - 10) * Math.cos(c)) - 6,
						(int) (y - (R - 10) * Math.sin(c)) + 4);

			}



			g.setColor(Color.BLUE);
			double offset;
			offset = -1.57 / 2 + (Double.parseDouble(cpu))*3.1416 * 3/200;
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

			 g.setColor(Color.RED);
			offset = -1.57 / 2 + (Double.parseDouble(mem))*3.1416 * 3/200;
			endx = x - R * Math.cos(offset);
			endy = y - R * Math.sin(offset);
			xs[0] = x - (int) (r * Math.cos(offset - 3.1415926 / 2.0));
			xs[1] = (int) endx;
			xs[2] = x + (int) (r * Math.cos(offset - 3.1415926 / 2.0));
			ys[0] = y - (int) (r * Math.sin(offset - 3.1415926 / 2.0));
			ys[1] = (int) endy;
			ys[2] = y + (int) (r * Math.sin(offset - 3.1415926 / 2.0));
			g.fillPolygon(xs, ys, 3);
			r = 3;
			g.fillOval(x - r , y - r, 2*r,2* r);
			g.dispose();
			ImageIO.write(tag, "png", fos);
			fos.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	public static String generateInPic(String value,String name,String ip,String ifIndex,String flag) {
		String ret = "";
		FileOutputStream fos;
		try {
			if(ip.contains(":")){
				ip=ip.replace(":","-");
			}
			//if(flag.equals("in")){
			fos = new FileOutputStream(Constants.webRealPath+"images/pic/"+ip+"_"+ifIndex+name);
	//	}else{
	//	     	fos = new FileOutputStream(Constants.webRealPath+"images/pic/"+ip+name);
	//		}
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			System.setProperty("java.awt.headless","true");
			File _file = new File(Constants.webRealPath+"/images/input.png");  
			Image image = javax.imageio.ImageIO.read(_file); 

			int width = image.getWidth(null); 
			int height = image.getHeight(null); 

			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();

			g.drawImage(image, 0, 0, width, height, null); 
			int x, y;
			int r = 4, R = 34;

			double startx, starty, endx, endy;

			x = width / 2;
			y = height / 2;

			g.setColor(Color.BLUE);
			g.setColor(Color.BLACK);
			Font f = new Font(null, Font.BOLD, 10);
			g.setFont(f);
			g.drawString("%", x  - 2, y + 25);
			g.drawString(flag, x - 8 - flag.length(), y + 42);

			int i1 = 0;
			for (double c = -1.57 / 2; i1 < 11; c = c + 3.1415926 / 10 * 3, i1 = i1 + 2) {
				g.drawString("" + Integer.toString((int) i1 * 10),
						(int) (x - (R - 10) * Math.cos(c)) - 6,
						(int) (y - (R - 10) * Math.sin(c)) + 4);

			}
			g.setColor(Color.GREEN);
			g.fillOval(x - r/2 , y - r/2, r, r);
			double offset;
			offset = -1.57 / 2 + Math.abs((Double.parseDouble(value)))*3.1416 * 3/200;
			startx = x + 10 * Math.cos(offset);
			starty = y + 10 * Math.sin(offset);
			
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
			//g.drawLine((int)startx,(int)starty,(int)endx,(int)endy);
			g.fillPolygon(xs, ys, 3);
			ImageIO.write(tag, "png", fos);
	//	encoder.encode(tag);
			bos.close();

		} catch (FileNotFoundException fnfe) {
			ret = ret + fnfe.getMessage();
		} catch (IOException ioe) {
			ret = ret + ioe.getMessage();
		}
		return ret;
	
	}
}
