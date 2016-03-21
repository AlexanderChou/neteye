package com.base.service;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.base.util.Constants;
import com.base.util.EncodeUtil;
import com.base.util.FileUtil;
import com.base.util.JDOMXML;

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
 * <p>Title: 数据备份与恢复</p>
 * <p>Description: 包括对数据库、配置文件及rrd文件的备份和恢复</p>
 * @version 1.0
 * @author 郭玺
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class DataManageService {
	private Connection connection = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSetMetaData rsmd = null;
	private ResultSet rs1 = null;
	private ResultSet rs2 = null;
	private String tableName = "";
	private int totalNum = 0;
	private int limitNum = 20000;
	private String backupPath = null;
	private String webPath = null;
	public static void main(String[] args) throws Exception{
		DataManageService service = new DataManageService();
		//service.backupDataBase("1","guoxitest");
		service.restoreDatabase("1","guoxitest");
		
	}
	/**
	 * 以备份名称为目录，将数据库、配置文件及rrd文件进行备份
	 * @param flag 0:用户进行的任意时刻的备份 1:每天晚上12：00进行的定时备份
	 * @param name 备份名称
	 * @return 备份成功，返回true 否则返回false
	 * @throws Exception
	 */
	public boolean backupDataBase(String flag,String name) throws Exception{
		if(backupPath==null){
			backupPath = Constants.BACKUP_PATH;
		}
		if(webPath==null){
			webPath = Constants.WEB_PATH;
		}
		
		//创建备份目录
		String path = null;
		if(flag.equals("0")){
			path = backupPath + Constants.BACKUP_FOLDER + File.separator + "custom" + File.separator + name + File.separator ;
		}else{
			path = backupPath + Constants.BACKUP_FOLDER + File.separator + "fix" + File.separator + name + File.separator ;
		}
		
        boolean isSuccess = FileUtil.createFolder(path);
        if(isSuccess){
	        //first:进行文件备份（将file下的目录及文件拷贝到新建的目录下）
			String srcPath = webPath + "file" + File.separator;
			if(flag.equals("0")){
//				System.out.println("用户备份");
				FileUtil.copyDirectiory(path+"file"+File.separator,srcPath);
			}
	        //second:进行数据库备份
			try {
				//connection = DBUtil.getNetEyeConnection();
				Class.forName("com.mysql.jdbc.Driver");
		        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/neteye", "root", "network");
	            stmt = connection.createStatement(); 
	            //获得数据库所有数据表的名称
	            rs1 = stmt.executeQuery("SHOW tables; "); 
	            while (rs1.next()) { 
	            	tableName = rs1.getString(1); 
	            	//获得该数据表的记录数
	            	pstmt = connection.prepareStatement("SELECT count(*) as NUM FROM " +tableName);
	            	rs2 = pstmt.executeQuery();
	                if (rs2.next()) {
	                	totalNum = rs2.getInt("NUM");
	                }
	            	int start = 0;
	                if(totalNum<=limitNum){
	                	writeXML(start,null,path);
	                }else{
	                	getXMLData(totalNum,0,path);
	                }
	            }
	            rs1.close();
	            rs2.close();
	            pstmt.close();
	            stmt.close();
	            connection.close();
	        }catch (ClassNotFoundException cnfex) {
	            cnfex.printStackTrace();
	        }catch (SQLException sqlex) {
	            sqlex.printStackTrace();
	        }
        }
        return isSuccess;
	}
	/**
	 * 采用递归方法，将任意一个数据库表备份为一个或多个xml文件
	 * @param num 某个数据库表的总记录数
	 * @param flag 如果某个数据库表的记录数超过规定的域值，就将该数据库表拆分为多个xml文件存储 flag表示是第几个文件（从0开始计数，文件名的格式 table[flag].xml）
	 * @param path 备份文件的存放路径
	 * @throws Exception
	 */
	public void getXMLData(int num,Integer flag,String path) throws Exception{
		int start = flag*limitNum;
		if(num<=limitNum){
			writeXML(start,flag,path);
		}else{
			writeXML(start,flag,path);
			num -= limitNum;
			flag ++;
			getXMLData(num,flag,path);
		}
	}
	/**
	 * 生成备份文件
	 * @param start 数据库查询的起始索引
	 * @param flag  如果某个数据库表的记录数没有超过规定的域值，falg为空，否则flag为拆分的xml文件的数目（从0开始计数）
	 * @param path  备份文件路径
	 * @throws Exception
	 */
	public void writeXML(int start,Integer flag,String path) throws Exception{
		//获得每个数据库表结构
        pstmt = (PreparedStatement) connection.prepareStatement("select * from "+tableName+" limit "+start+","+limitNum);
        rsmd = (ResultSetMetaData) pstmt.getMetaData();
        Document document = DocumentHelper.createDocument();
		Element root = document.addElement(tableName);
        Element results = root.addElement("results");
        //获得每个数据库内容
        rs2 = pstmt.executeQuery();
        String value = "";
        while(rs2.next()){
        	Element result = results.addElement("result");
        	for(int j=1;j<=rsmd.getColumnCount();j++){
            	value = rs2.getString(j);
            	/*if(rsmd.getColumnName(j).equals("mac")){
            		if(value!=null && value.indexOf("0x00")==-1){
            			value = null;
            		}
            	}*/
            	//采用64位编码
            	if(value!=null){
            		//result.addElement(rsmd.getColumnName(j)).addText((new sun.misc.BASE64Encoder()).encode( value.getBytes() ));
            		result.addElement(rsmd.getColumnName(j)).addText(EncodeUtil.encode(value));
            		//result.addElement(rsmd.getColumnName(j)).addText(value);
            	}else{
            		result.addElement(rsmd.getColumnName(j));
            	}
        	}
        }
		if(flag==null){
			JDOMXML.saveXml(path+ tableName + ".xml", document);
		}else{
			JDOMXML.saveXml(path+ tableName+"["+flag+"]" + ".xml", document);
		}
	}
	/**
	 * 根据某次备份的名称进行数据库恢复
	 * @param flag 0:对用户进行的任意时刻的备份数据进行恢复 1:对每天晚上12：00进行的定时备份数据进行恢复
	 * @param name 某次备份的名称
	 * @return  备份成功，返回true 否则返回false
	 * @throws Exception
	 */
	public boolean restoreDatabase(String flag,String name) throws Exception{
		if(backupPath==null){
			backupPath = Constants.BACKUP_PATH;
		}
		if(webPath==null){
			webPath = Constants.WEB_PATH;
		}
		 boolean isSuccess = false;
		//first:restore database
		String[] tables = {"department","user","resource_group","user_group","popedom","resource","user_popedom","user_log",
		          "devicetype","device","ifinterface","link","image","view","service","servicemanage","trafficmeasurement","device_icon",
		          "event","alarm","eventseq","eventstatus","user_message",
		          "faultcurrent","faulthistory","faultlog","trap","report","configure"};
       try {
    	   //connection = DBUtil.getNetEyeConnection();
      	  Class.forName("com.mysql.jdbc.Driver");

      	  connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/neteye?useUnicode=true&characterEncoding=utf8", "root", "network");
          stmt = connection.createStatement(); 
          for(int i=tables.length-1;i>=0;i--){
          	//删除数据库中所有数据
          	pstmt = connection.prepareStatement("DELETE FROM "+tables[i]);
          	pstmt.executeUpdate();
          }
          long start= System.currentTimeMillis();
          //获得数据库所有数据表的名称
          String path = null;
          if(flag.equals("0")){
        	  path = backupPath + Constants.BACKUP_FOLDER + File.separator + "custom" + File.separator;
          }else{
        	  path = backupPath + Constants.BACKUP_FOLDER + File.separator + "fix" + File.separator;
          }
          for(int i=0;i<tables.length;i++){
          	tableName = tables[i]; 
 //         	System.out.println("tableName="+tableName);
          	//获得每个数据库表结构
	            pstmt = (PreparedStatement) connection.prepareStatement("select * from "+tableName);
	            rsmd = (ResultSetMetaData) pstmt.getMetaData();
	            File[] contents = new FileUtil().getFiles(path,tableName,"[","xml");
	            Document document = null;
	            if(contents.length==0){
	            	if(flag.equals("0")){
	            	 document = JDOMXML.readXML(path+name+File.separator+tableName+".xml");
	            	}else{
	            		document = JDOMXML.readXML(path+name+File.separator+tableName+".xml");
	            	}
	            	 getInsertingData(document);
	            }else{
	            	for (File f : contents) {
	            		document = JDOMXML.readXML(f);
	            		getInsertingData(document);
		    		}
	            }
	            document.clearContent();
          }
          long end= System.currentTimeMillis();
          pstmt.close();
          stmt.close();
          connection.close();
          //second:restore folder(删除应用下的目录，然后将备份目录拷到该目录下)
          String destPath = webPath + "file" + File.separator;
          if(flag.equals("0")){
	          if(FileUtil.createFolder(destPath)){
	        	    String srcPath = path + name + File.separator + "file" + File.separator;
			        FileUtil.copyDirectiory(destPath,srcPath);
			        isSuccess = true;
	          }
          }
      }catch (ClassNotFoundException cnfex) {
          cnfex.printStackTrace();
      }catch (SQLException sqlex) {
          sqlex.printStackTrace();
      } 
      return isSuccess;
	}
	/**
	 * first:首先读取备份的xml文件，对数据库进行恢复操作 second:恢复相应的配置文件及rrd文件
	 * @param document  备份的xml文件对应的jdom文档
	 * @throws Exception
	 * 注：为提高系统性能，数据库恢复时，采用批量插入的方式进行操作，但若一次提交的数目太多，则会对系统的性能产生较大影响，有可能出现JVM内存溢出问题，
	 * 为避免该情况出现，当记录超过1000时，才进行事务提交
	 */
	public void getInsertingData(Document document)throws Exception{
		String sql = "";
		String tagName = "";
		String tagValueOld = "";
		String mysqlType = "";
	 	Element root = document.getRootElement();
	 	List<Element> results = root.element("results").elements();
	 	sql = "INSERT into "+tableName+"(";
		String tmp_1 = "";
		String tmp_2 = "";
	 	for (int j=1; j <= rsmd.getColumnCount(); j++) {
         	tagName = rsmd.getColumnName(j);
         	tmp_1 +=tableName+"."+tagName+",";
         	tmp_2 += "?,";
	 	 }
	 	sql += tmp_1.substring(0,tmp_1.length()-1) +") values(" + tmp_2.substring(0,tmp_2.length()-1) +")";
	 	pstmt = connection.prepareStatement(sql); 

	 	int k = 0;
 		for (Element result : results) {
 			k++;
 			 for (int j=1; j <= rsmd.getColumnCount(); j++) {
	            	tagName = rsmd.getColumnName(j);
	            	mysqlType = rsmd.getColumnTypeName(j);
	            	tagValueOld = result.elementText(tagName);//当文件中没有tagName标签时，tagValue值为空；有tagName标签，但值为空时，tagName的值为""
	            	String tagValue = null;
	            	if(tagValueOld!=null){
	            		tagValue = EncodeUtil.decode(tagValueOld);
	            	}
	            	if( "BIGINT".equals(mysqlType) || "INTEGER".equals(mysqlType) || "DOUBLE".equals(mysqlType) || "BIT".equals(mysqlType)){
		 				if(tagValue!=null && !tagValue.equals("")){
		 					pstmt.setDouble(j, Double.valueOf(tagValue));
			 			}else{
			 				pstmt.setNull(j, 1);
			 			}
		 			}else if("TINYINT".equals(mysqlType)){
		 				if(tagValue!=null && !tagValue.equals("")){
		 					if(tagValue.equals("0")){
		 						pstmt.setBoolean(j, false);
		 					}else{
		 						pstmt.setBoolean(j, true);
		 					}
			 			}else{
			 				pstmt.setBoolean(j, true);
			 			}
	            	}else if("VARCHAR".equals(mysqlType) || "DATETIME".equals(mysqlType)){
			 			if(tagValue!=null && !tagValue.equals("")){
			 				pstmt.setString(j, tagValue);
			 			}else{
			 				pstmt.setNull(j, 1);
			 			}
		 			}
	         }
     		pstmt.addBatch();
     		if(k>1000){
     			k =0;
     			pstmt.executeBatch();
     		}
 		}//endof for
 		if(k!=0){
 			pstmt.executeBatch();
 		} 
	}
	// 将 BASE64 编码的字符串 s 进行解码 
	public static String getFromBASE64(String s) { 
		if (s == null) return null; 
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder(); 
		try { 
			byte[] b = decoder.decodeBuffer(s); 
			return new String(b); 
		} catch (Exception e) { 
			return null; 
		} 
	}
}
