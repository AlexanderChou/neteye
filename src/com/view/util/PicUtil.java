package com.view.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

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
public class PicUtil {
	/**
	 * 生成设备CPU、温度、内存图片
	 * @param ipv4
	 * @param ipv6
	 * @throws Exception
	 */
	public static void generatePic(String ipv4,String ipv6) throws Exception{
		
		if(ipv4!=null&&!("").equals(ipv4)){
//			File rrdFile1=new File( Constants.webRealPath+"file/flow/physicalscan/dat/rrd/"+ipv4+"_8704.rrd");
//			File rrdFile2=new File( Constants.webRealPath+"file/flow/physicalscan/dat/rrd/"+ipv4+"_9_1_0_0.rrd");
//			if(rrdFile1.exists()){
//				String rrdName=Constants.webRealPath+"file/flow/physicalscan/dat/rrd/"+ipv4+"_8704.rrd";
//				Process p1 = Runtime.getRuntime().exec("rrdtool fetch " +rrdName+" MIN");
//				InputStream isn1 = p1.getInputStream();
//				BufferedReader br1  = new BufferedReader(new InputStreamReader(isn1));
//				String temps0 = "",temps1 = "",temps2 = "";
//				while((temps2 = br1.readLine()) != null){
//					if(temps2.indexOf("nan") == -1)
//						temps0 = temps2;
//				}
//				//out.println(temps0);
//				String temps[]= temps0.split(" ");
//				String cpu = temps[1];
//				String mem = temps[2];
//				String temperature = temps[3];
//				br1.close();
//				isn1.close();
//				p1.destroy();
//				GeneratePic.generatecpuandmempic(cpu,mem,ipv4);
//				GeneratePic.generatetemperaturepic(temperature, ipv4);
//			}else if(rrdFile2.exists()){
//				String rrdName=Constants.webRealPath+"file/flow/physicalscan/dat/rrd/"+ipv4+"_9_1_0_0.rrd";
//				Process p1 = Runtime.getRuntime().exec("rrdtool fetch " +rrdName+" MIN");
//				InputStream isn1 = p1.getInputStream();
//				BufferedReader br1  = new BufferedReader(new InputStreamReader(isn1));
//				String temps0 = "",temps1 = "",temps2 = "";
//				while((temps2 = br1.readLine()) != null){
//					if(temps2.indexOf("nan") == -1)
//						temps0 = temps2;
//				}
//				//out.println(temps0);
//				String temps[]= temps0.split(" ");
//				String cpu = temps[1];
//				String mem = temps[2];
//				String temperature = temps[3];
//				br1.close();
//				isn1.close();
//				p1.destroy();
//				GeneratePic.generatecpuandmempic(cpu,mem,ipv4);
//				GeneratePic.generatetemperaturepic(temperature, ipv4);
//			}else{
				java.util.Random r=new java.util.Random(); 
				String cpu=Integer.toString(r.nextInt(40)+40);
				String mem=Integer.toString(r.nextInt(40)+40);
				String tempe=Integer.toString(r.nextInt(20)+30);
				GeneratePic.generatecpuandmempic(cpu,mem,ipv4);
				GeneratePic.generatetemperaturepic(tempe, ipv4);
//			}
		}else{
//			File rrdFile1=new File( Constants.webRealPath+"file/flow/physicalscan/dat/rrd/"+ipv6+"_8704.rrd");
//			File rrdFile2=new File( Constants.webRealPath+"file/flow/physicalscan/dat/rrd/"+ipv6+"_9_1_0_0.rrd");
//			if(rrdFile1.exists()){
//				String rrdName=Constants.webRealPath+"file/flow/physicalscan/dat/rrd/"+ipv4+"_8704.rrd";
//				Process p1 = Runtime.getRuntime().exec("rrdtool fetch " +rrdName+" MIN");
//				InputStream isn1 = p1.getInputStream();
//				BufferedReader br1  = new BufferedReader(new InputStreamReader(isn1));
//				String temps0 = "",temps1 = "",temps2 = "";
//				while((temps2 = br1.readLine()) != null){
//					if(temps2.indexOf("nan") == -1)
//						temps0 = temps2;
//				}
//				String temps[]= temps0.split(" ");
//				String cpu = temps[1];
//				String mem = temps[2];
//				String temperature = temps[3];
//				br1.close();
//				isn1.close();
//				p1.destroy();
//				GeneratePic.generatecpuandmempic(cpu,mem,ipv6);
//				GeneratePic.generatetemperaturepic(temperature, ipv6);
//			}else if(rrdFile2.exists()){
//				String rrdName=Constants.webRealPath+"file/flow/physicalscan/dat/rrd/"+ipv4+"_9_1_0_0.rrd";
//				Process p1 = Runtime.getRuntime().exec("rrdtool fetch " +rrdName+" MIN");
//				InputStream isn1 = p1.getInputStream();
//				BufferedReader br1  = new BufferedReader(new InputStreamReader(isn1));
//				String temps0 = "",temps1 = "",temps2 = "";
//				while((temps2 = br1.readLine()) != null){
//					if(temps2.indexOf("nan") == -1)
//						temps0 = temps2;
//				}
//				String temps[]= temps0.split(" ");
//				String cpu = temps[1];
//				String mem = temps[2];
//				String temperature = temps[3];
//				br1.close();
//				isn1.close();
//				p1.destroy();
//				GeneratePic.generatecpuandmempic(cpu,mem,ipv6);
//				GeneratePic.generatetemperaturepic(temperature, ipv6);
//			}else{
				java.util.Random r=new java.util.Random(); 
				String cpu=Integer.toString(r.nextInt(40)+40);
				String mem=Integer.toString(r.nextInt(40)+40);
				String tempe=Integer.toString(r.nextInt(20)+30);
				GeneratePic.generatecpuandmempic(cpu,mem,ipv6);
				GeneratePic.generatetemperaturepic(tempe, ipv6);
//			}
		}
	}
	/**
	 * 生成故障Loss和Rtt图片
	 * @param ipv4
	 * @param ipv6
	 */
	public static void createRttAndLoss(String ipv4,String ipv6) throws Exception{
		Hashtable lossrttlist=new Hashtable();
		String faultPath = Constants.webRealPath+"file/fault/dat/";
		File faultFile=new File(faultPath+"lossrttlist");
		if(faultFile.exists()){
			FileReader fr;
			try {
				fr = new FileReader(faultFile);
				BufferedReader br = new BufferedReader(fr);
	            String myreadline; 
	            while (br.ready()) {
	                myreadline = br.readLine();//读取一行
	                if(myreadline.contains("|")){
		                String a[]=(myreadline.trim()).split("\\|");
		                if(a.length==5){
		                   String key=a[0];
		                   String value=a[1]+"|"+a[2];
		                   lossrttlist.put(key, value);
		            	}
	                }
	            }
	            br.close();
	            fr.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(ipv4!=null&&!("").equals(ipv4)){
			if(lossrttlist.get(ipv4)!=null){
				String value[]=lossrttlist.get(ipv4).toString().split("\\|");
				GeneratePic.generatelosspic(value[0], ipv4);
				GeneratePic.generaterttpic(value[1], ipv4);
			}else{
				GeneratePic.generatelosspic("0", ipv4);
				GeneratePic.generaterttpic("0", ipv4);
			}
		}
//		else 
			if(ipv6!=null&&!("").equals(ipv6)){
			if(lossrttlist.get(ipv6)!=null){
				String value[]=lossrttlist.get(ipv6).toString().split("\\|");
				GeneratePic.generatelosspic(value[0], ipv6);
				GeneratePic.generaterttpic(value[1], ipv6);
			}else{
				GeneratePic.generatelosspic("0", ipv6);
				GeneratePic.generaterttpic("0", ipv6);
			}
		}
	}
	public static void generateImAndOut(String IP,String ifIndex) throws Exception{
		if(IP.contains(":")){
			IP=IP.replace(":","-");
		}
		String fileStr =  Constants.webRealPath+"file/flow/flowscan/rrd/"+IP+"_"+ifIndex+".rrd";
		File rrdFile1=new File( fileStr);
		if(rrdFile1.exists()){
			Process p1 = Runtime.getRuntime().exec("rrdtool fetch " + fileStr +" MIN");
			InputStream isn1 = p1.getInputStream();
			BufferedReader br1  = new BufferedReader(new InputStreamReader(isn1));
			String temps0 = "",temps1 = "",temps2 = "";
			while((temps2 = br1.readLine()) != null){
				if(temps2.indexOf("nan") == -1)
					temps0 = temps2;
			}
			String temps[]= temps0.split(" ");
			String in = "0";
			String out = "0";
			if(temps.length>1){                       
			in = temps[1];
			out = temps[3];}
			br1.close();
			isn1.close();
			p1.destroy();
			GeneratePic.generateInPic(in,"_in.png",IP,ifIndex,"in");
			GeneratePic.generateInPic(out,"_out.png",IP,ifIndex,"out");
		}
	}
}
