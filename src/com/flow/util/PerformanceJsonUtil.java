package com.flow.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import com.base.model.Device;
import com.base.model.DicContent;
import com.base.model.View;
import com.base.service.DeviceService;
import com.base.service.DicContentService;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.view.dao.ViewDAO;
import com.view.dto.Router;
import com.view.util.MyXmlUtil;


public class PerformanceJsonUtil {
	public static void columnToJsonData(JspWriter out,String viewName) throws Exception{
		File performanceFile = null;
		BufferedReader br = null;
		FileWriter fw = null;
		int sum,temp,max=0;
		String[] outArr,inArr;
		String pic;
		String path = Constants.webRealPath + "file/performance/config/";
		StringBuffer columbuffer = new StringBuffer();
		columbuffer.append("{'columModle':[").append("{'id':'name','header': '设备名称','dataIndex': 'ipname','width':120,'sortable':'true'},");
		columbuffer.append("{'header': 'name','dataIndex': 'name','sortable':'true','width':60,'hidden':'true'},");
		StringBuffer fieldbuffer = new StringBuffer();
		fieldbuffer.append("],'fieldsNames':[{name: 'ipde'},").append("{name: 'ipv6'}, {name: 'ipname'},");
		StringBuffer databuffer = new StringBuffer();
		databuffer.append("],'data':[");
		//数据字典性能参数列表
		List<DicContent> dicDetailList= new DicContentService().getByDicTypeId("performancePara");
		
		if(("ALL_-1").equals(viewName)){
			//显示所有设备的性能图形，注：一种方式是查找所有视图及其包含的设备，需要读所有的视图文件；另一种方式是查询数据库，获得所有的设备，这里采用第二种方式
			List<Device> devices = new DeviceService().getAllDevice();
			if(devices!=null){
				for(Device device:devices){
					sum = 0;
					//如果页面要显示ＩＰ地址信息，可根据router的ID找到数据库相应对象，获得ＩＰ地址信息
					databuffer.append("{'ipname':'"+device.getName()+"',");				
					for(DicContent dic:dicDetailList){
						//判断配置文件是否存在					
						String fileStr =  "perfPara_" + dic.getDicContenId() + "_" + device.getId() ;
						performanceFile = new File(path +fileStr+ ".txt");
						if(performanceFile.exists()){
							//读取该文件内容，在页面动态输出grid表头(以后修改，直接查找所有文件的最大记录数，按最大记录数输出表头)
							BufferedReader input = new BufferedReader( new FileReader(  path +fileStr + ".txt") );
		    				String configStr = input.readLine();//第一行内容显示设备的IP地址和community,最后再加
		    				while ( ( configStr = input.readLine() ) != null ){
		    					//可把行中内容取出赋给表格对象进行显示，注：每行内容格式为：oid||描述
		    					outArr = configStr.split("\\|\\|");
		    					if(outArr.length>=2){
		    						inArr = outArr[0].split(":");
		    						pic = "/file/performance/dat/pic/" + fileStr + "_" + inArr[1] + ".gif";
			    					databuffer.append("'pic"+(sum+1)+"':'"+pic+"',");
			    					sum++;
		    					}
		    				}
						}
					}//for(DicContent dic:dicDetailList)
					if(sum>max){
						max = sum;
					}
					databuffer.append("'name':'"+device.getId()+"'},");
				}//End of for(Device device:devices)
			}
		}else{
			String viewId;
			View view;
			if(viewName!=null && !"".equals(viewName)){
				viewId = viewName.substring(viewName.lastIndexOf("_")+1,viewName.length());
				//根据视图名称找到相应视图
				view = new ViewService().findById(Long.parseLong(viewId));
			}else{
				view = new ViewDAO().getViewMain();
			}
			//显示所选择的某一视图所包含的设备的性能图形
			//首先是查找视图下的所有设备		
			String filePath = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
			File file=new File(filePath+view.getName()+".xml");
			if(file.exists()){
				List<Router> nodes = MyXmlUtil.getNodeIdList(filePath+view.getName()+".xml");
				for(Router router:nodes){
					sum = 0;
					//如果页面要显示ＩＰ地址信息，可根据router的ID找到数据库相应对象，获得ＩＰ地址信息
					//databuffer.append("{'ipname':'"+router.getName()+"<br>"+router.getIpv4()+"<br>"+router.getIpv6()+"',");				
					databuffer.append("{'ipname':'"+router.getName()+"',");				
					for(DicContent dic:dicDetailList){
						//判断配置文件是否存在					
						String fileStr =  "perfPara_" + dic.getDicContenId() + "_" + router.getId() ;
						performanceFile = new File(path +fileStr+ ".txt");
						if(performanceFile.exists()){
							//读取该文件内容，在页面动态输出grid表头(以后修改，直接查找所有文件的最大记录数，按最大记录数输出表头)
							BufferedReader input = new BufferedReader( new FileReader(  path +fileStr + ".txt") );
		    				String configStr = input.readLine();//第一行内容显示设备的IP地址和community,最后再加
		    				while ( ( configStr = input.readLine() ) != null ){
		    					//可把行中内容取出赋给表格对象进行显示，注：每行内容格式为：oid||描述
		    					outArr = configStr.split("\\|\\|");
		    					if(outArr.length>=2){
		    						inArr = outArr[0].split(":");
		    						pic = "/file/performance/dat/pic/" + fileStr + "_" + inArr[1] + ".gif";
			    					databuffer.append("'pic"+(sum+1)+"':'"+pic+"',");
			    					sum++;
		    					}
		    				}
						}
					}//for(DicContent dic:dicDetailList)
					if(sum>max){
						max = sum;
					}
					databuffer.append("'name':'"+router.getId()+"'},");
				}//for(Router router:nodes)
			}
		}
		
		if(max>0){
			for(int i=0;i<max;i++){
				columbuffer.append("{'header': '"+"性能图形"+(i+1)+"','dataIndex': 'pic"+(i+1)+"','renderer':renderPic,'width':230},");
				fieldbuffer.append("{name: 'pic"+(i+1)+"'}, ");
			}
		}
		fieldbuffer.append("{name: 'name'}]}");
		//将databuffer最后一个,字符截掉
		String data = databuffer.toString();
		String dot = data.substring(data.length()-1);
		if(dot.equals(",")){
			data = data.substring(0,data.length()-1);
		}
		//将columbuffer最后一个,字符截掉
		String colum = columbuffer.toString();
		String columDot = colum.substring(colum.length()-1);
		if(columDot.equals(",")){
			colum = colum.substring(0,colum.length()-1);
		}
		//将columbuffer、fieldbuffer和fieldbuffer三个变量联系起来
		out.println(colum+data+fieldbuffer.toString());
	}
	public static void columnToJsonData2(JspWriter out,String dicContentId,String viewName) throws Exception{
		File performanceFile = null;
		BufferedReader br = null;
		FileWriter fw = null;
		int sum,temp,max=0;
		String[] outArr,inArr;
		String pic;
		String path = Constants.webRealPath + "file/performance/config/";
		StringBuffer columbuffer = new StringBuffer();
		columbuffer.append("{'columModle':[").append("{'id':'name','header': '设备名称','dataIndex': 'ipname','width':120,'sortable':'true'},");
		columbuffer.append("{'header': 'name','dataIndex': 'name','sortable':'true','width':60,'hidden':'true'},");
		StringBuffer fieldbuffer = new StringBuffer();
		fieldbuffer.append("],'fieldsNames':[{name: 'ipde'},").append("{name: 'ipv6'}, {name: 'ipname'},");
		StringBuffer databuffer = new StringBuffer();
		databuffer.append("],'data':[");
		//数据字典性能参数列表
		//List<DicContent> dicDetailList= new DicContentService().getByDicTypeId("performancePara");
		//需要知道数据字典详细内容的Id，需要从页面传过来相应的参数
		if(("ALL_-1").equals(viewName)){
			List<Device> devices = new DeviceService().getAllDevice();
			if(devices!=null){
				for(Device device:devices){
					sum = 0;
					//如果页面要显示ＩＰ地址信息，可根据router的ID找到数据库相应对象，获得ＩＰ地址信息
					databuffer.append("{'ipname':'"+device.getName()+"',");				
					//判断配置文件是否存在					
					String fileStr =  "perfPara_" + dicContentId + "_" + device.getId() ;
					performanceFile = new File(path +fileStr+ ".txt");
					if(performanceFile.exists()){
						//读取该文件内容，在页面动态输出grid表头(以后修改，直接查找所有文件的最大记录数，按最大记录数输出表头)
						BufferedReader input = new BufferedReader( new FileReader(  path +fileStr + ".txt") );
	    				String configStr = input.readLine();//第一行内容显示设备的IP地址和community,最后再进行修改
	    				while ( ( configStr = input.readLine() ) != null ){
	    					//可把行中内容取出赋给表格对象进行显示，注：每行内容格式为：oid||描述
	    					outArr = configStr.split("\\|\\|");
	    					if(outArr.length>=2){
	    						inArr = outArr[0].split(":");
	    						pic = "/file/performance/dat/pic/" + fileStr + "_" + inArr[1] + ".gif";
		    					databuffer.append("'pic"+(sum+1)+"':'"+pic+"',");
		    					sum++;
	    					}
	    				}
					}
					if(sum>max){
						max = sum;
					}
					databuffer.append("'name':'"+device.getId()+"'},");
				
				}//Endof for(Device device:devices)
			}
		}else{
			String viewId;
			View view;
			if(viewName!=null && !"".equals(viewName)){
				viewId = viewName.substring(viewName.lastIndexOf("_")+1,viewName.length());
				//根据视图名称找到相应视图
				view = new ViewService().findById(Long.parseLong(viewId));
			}else{
				view = new ViewDAO().getViewMain();
			}
			String filePath = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
			File file=new File(filePath+view.getName()+".xml");
			if(file.exists()){
				List<Router> nodes = MyXmlUtil.getNodeIdList(filePath+view.getName()+".xml");
				for(Router router:nodes){
					sum = 0;
					//如果页面要显示ＩＰ地址信息，可根据router的ID找到数据库相应对象，获得ＩＰ地址信息
					databuffer.append("{'ipname':'"+router.getName()+"',");				
					//判断配置文件是否存在					
					String fileStr =  "perfPara_" + dicContentId + "_" + router.getId() ;
					performanceFile = new File(path +fileStr+ ".txt");
					if(performanceFile.exists()){
						//读取该文件内容，在页面动态输出grid表头(以后修改，直接查找所有文件的最大记录数，按最大记录数输出表头)
						BufferedReader input = new BufferedReader( new FileReader(  path +fileStr + ".txt") );
	    				String configStr= input.readLine();	//第一行内容显示设备的IP地址和community,最后再进行修改
	    				while ( ( configStr = input.readLine() ) != null ){
	    					//可把行中内容取出赋给表格对象进行显示，注：每行内容格式为：oid||描述
	    					outArr = configStr.split("\\|\\|");
	    					if(outArr.length>=2){
	    						inArr = outArr[0].split(":");
	    						pic = "/file/performance/dat/pic/" + fileStr + "_" + inArr[1] + ".gif";
		    					databuffer.append("'pic"+(sum+1)+"':'"+pic+"',");
		    					sum++;
	    					}
	    				}
					}
					if(sum>max){
						max = sum;
					}
					databuffer.append("'name':'"+router.getId()+"'},");
				}//for(Router router:nodes)
			}
		}
		
		if(max>0){
			for(int i=0;i<max;i++){
				columbuffer.append("{'header': '"+"性能图形"+(i+1)+"','dataIndex': 'pic"+(i+1)+"','renderer':renderPic,'width':230},");
				fieldbuffer.append("{name: 'pic"+(i+1)+"'}, ");
			}
		}
		fieldbuffer.append("{name: 'name'}]}");
		String data = databuffer.toString();
		String dot = data.substring(data.length()-1);
		if(dot.equals(",")){
			data = data.substring(0,data.length()-1);
		}
		//将columbuffer最后一个,字符截掉
		String colum = columbuffer.toString();
		String columDot = colum.substring(colum.length()-1);
		if(columDot.equals(",")){
			colum = colum.substring(0,colum.length()-1);
		}
		out.println(colum+data+fieldbuffer.toString());
	}	
	public static void main(String[] args){
		String test = "1223_a";
		test = test.substring(test.lastIndexOf("_")+1,test.length());
		System.out.println(test);
	}
}
