package com.savi.base.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.base.util.BaseAction;
import com.base.util.Constants;
import com.base.util.JDOMXML;
import com.savi.base.model.Savibindingtablehis;

public class ClearHis extends BaseAction{
	private int nowYear;
	private int nowMonth;
	private int oldMonth;
	private int oldYear;
	private int usedMonth;
	private long recordsSave;
	private boolean success;
	private boolean failure;
	private HibernateUtil hibernateUtil = new HibernateUtil();
	
	//获取当前时间
	public String ClearHisSavi() throws Exception{
		
	 Date curDate = new Date();
	 nowYear =curDate.getYear();
	 nowMonth =curDate.getMonth();
//	 nowMonth =1;
		
	//获取保留时间
	usedMonth =1;
	//设置保留的条数
	
	if(nowMonth<4){
		oldMonth = nowMonth -usedMonth +12;
		oldYear =nowYear-1;
		
	}else{
		oldMonth = nowMonth -usedMonth;	
		oldYear =nowYear;
		
	}
    long temp =curDate.getTime();
    Date oldDate =new Date();
    oldDate.setYear(oldYear);
    oldDate.setMonth(oldMonth);
    oldDate.setDate(1);
    oldDate.setHours(0);
    oldDate.setMinutes(0);
    oldDate.setSeconds(0);
    long oldtime =oldDate.getTime();
 //   System.out.println("newDate="+temp);
 //   System.out.println("olddate="+oldtime);
	//查询非保留保留时间的记录
    List<Object[]> oldList =getOlddata(oldtime);
    //对非非保留记录做统计处理
    List<Object[]> showlist= new ArrayList<Object[]>();
    for(Object[] temphis:oldList){
    	Object[] tempsavishow  = new Object[3];
    	tempsavishow[0]=String.valueOf(temphis[0]);
    	tempsavishow[1]=String.valueOf(temphis[1]);
    	tempsavishow[2]=String.valueOf(temphis[2]);
    	showlist.add(tempsavishow);
  //  	 System.out.println("tempsavishow="+tempsavishow.toString());
    }
	//保存非保留数据
    
 //.out.println("showlist="+showlist.size());
 //   String filename = oldDate.toString();
    String filename = String.valueOf(oldDate.getYear()+1900)+String.valueOf(oldDate.getMonth()+1);
    WriteXml(showlist,filename);
	
	//清除数据库数据
    File filexml =new File(Constants.webRealPath+"file/backup/savi/"+filename+".xml");
    if(filexml.exists()){
    	System.out.println("File is ready! Del records with Mysql!");
    	zip(Constants.webRealPath+"file/backup/savi/"+filename+".xml");
//    	delOlddata(oldtime);
    	
    }
    
    setSuccess(true);
	return SUCCESS;
    
    
	
	}
	
	
	public static void zip(String filenames) throws IOException{
		FileOutputStream f = new FileOutputStream(filenames+".zip");
		CheckedOutputStream csum =new CheckedOutputStream(f,new Adler32());
		ZipOutputStream zos = new ZipOutputStream(csum);
		BufferedOutputStream out = new BufferedOutputStream(zos);
		zos.setComment("test for zip");
		System.out.println("Write "+filenames);
		BufferedReader in = new BufferedReader(new FileReader(filenames));
		zos.putNextEntry(new ZipEntry(filenames));
		int c ;
		while((c =in.read())!=-1){
			out.write(c);}
		    in.close();
		    out.close();
	  System.out.println("Checksum:  "+csum.getChecksum().getValue());
		
	}
	
	
	public List getOlddata(long time){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String hql ="select id,macAddress,sum(endTime-startTime) from Savibindingtablehis where startTime < "+time+ " group by 	macAddress";
		Query query = session.createQuery(hql);
		List<Savibindingtablehis> list = query.list();
		transaction.commit();
		return list;
		
	}
	public Long getOlddataNum(long time){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String hql ="select * from Savibindingtablehis where startTime > "+time;
		Query query = session.createQuery(hql);
		List<Savibindingtablehis> list = query.list();
		Long count =(long) list.size();
		transaction.commit();
		return count;
		
	}
	public void delOlddata(long time){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String hql ="delete * from Savibindingtablehis where startTime < "+time;
		Query query = session.createQuery(hql);
//		List<Savibindingtablehis> list = query.list();
		transaction.commit();
	//	return list;
		
	}
	
	public void WriteXml(List<Object[]> savishow,String filename) throws Exception{
		if(savishow!=null){
			String filePath = Constants.webRealPath+"file/backup/savi";
			File fileDir=new File(filePath);
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}else if(!fileDir.isDirectory()){
				fileDir.delete();
				fileDir.mkdirs();
			}
			
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("saviolddata");
			for(int i=0;i<savishow.size();i++){
				Object[] savitemp= (Object[]) savishow.get(i);
			    Element savi = root.addElement("savi");
			    Element id = savi.addElement("id");
				Element macAddress = savi.addElement("macAddress");
				Element time = savi.addElement("time");
				id.addText(savitemp[0].toString());
				macAddress.addText(savitemp[1].toString());
				time.addText(savitemp[2].toString());
				
			}
			JDOMXML.saveXml(filePath+ "/"+filename+".xml", document);
		}
	}
			
	
	public static void main(String[] args) throws Exception{
		ClearHis temp=new ClearHis();
		temp.ClearHisSavi();
		
		
		
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public boolean isFailure() {
		return failure;
	}


	public void setFailure(boolean failure) {
		this.failure = failure;
	}

}
