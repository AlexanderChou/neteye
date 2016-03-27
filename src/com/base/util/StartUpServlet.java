package com.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.data.util.BackupTask;
import com.base.util.Constants;
import com.savi.base.model.Switchbasicinfo;
import com.savi.collection.dao.SwitchBasicInfoDao;
import com.savi.collection.util.CollectionFactory;
import com.totalIP.util.CollegeDayIPTask;
import com.totalIP.util.CollegeIPTask;
import com.totalIP.util.PerformanceTask;
import com.totalIP.util.TotalIPMonthTask;
import com.totalIP.util.TotalIPWeekTask;

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
public class StartUpServlet extends HttpServlet {
	static JbpmConfiguration jbpmConfiguration = null;

	static {

	jbpmConfiguration = JbpmConfiguration.getInstance();

	}
    public void init() throws ServletException {
        Constants.realPath = getServletContext().getRealPath("/WEB-INF/");
        Constants.webRealPath = getServletContext().getRealPath("/");
        Constants.webRealPath = Constants.webRealPath.replace('\\', '/');
        JbpmContext context=jbpmConfiguration.createJbpmContext();
		FileInputStream fis;
		try {
			
			fis = new FileInputStream(Constants.webRealPath + "file/ticket/" +"processdefinition.xml");
//			System.out.println("fis="+fis);
			ProcessDefinition processDefinition = ProcessDefinition.parseXmlInputStream(fis);;
			context.deployProcessDefinition(processDefinition);
			context.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		startPerformanceTest();
//		startCollegeIPDay();
//		startCollegeIP();
//       startBackup();
//		startTotalIP();
//		startTotalIPDay();
//		startTotalIPWeek();
//		startTotalIPMonth();
		//添加配置文件，赋给相应的全局变量(file/syscfg.xml)
		initIPCfg();
		System.out.println("guoxi测试20131105");
		
		com.savi.base.util.Constants.realPath = getServletContext().getRealPath("/WEB-INF/");
		com.savi.base.util.Constants.webRealPath = getServletContext().getRealPath("/");
		com.savi.base.util.Constants.webRealPath = com.savi.base.util.Constants.webRealPath.replace('\\', '/');
	     System.out.println("com.savi.base.util.Constants.webRealPath" +com.savi.base.util.Constants.webRealPath);
		 File configFile=new File(com.savi.base.util.Constants.webRealPath+"sys.conf");
	        if(configFile.exists()){
	        	try {
					BufferedReader bw=new BufferedReader(new FileReader(configFile));
					String line="";
					while(true){
						line=bw.readLine();
						if(line==null||line.equals("")){
							break;
						}
						if(line.startsWith("debug")){
							String debug=line.substring(line.indexOf('=')+1);
							if(debug.equals("true")){
								com.savi.base.util.Constants.debug=1;
							}else{
								com.savi.base.util.Constants.debug=2;
							}
						}
						if(line.startsWith("pollingInterval")){
							String pollingInterval=line.substring(line.indexOf('=')+1);
							com.savi.base.util.Constants.pollingInterval=Integer.parseInt(pollingInterval);
						}
						if(line.startsWith("deployMode")){
							String deployMode=line.substring(line.indexOf('=')+1);
							if(deployMode.equals("independent")){
								com.savi.base.util.Constants.deployMode=1;
							}else{
								com.savi.base.util.Constants.deployMode=2;
							}
							
						}
						if(line.startsWith("webserviceURL")){
							com.savi.base.util.Constants.webServiceURL=line.substring(line.indexOf('=')+1).trim();
						}
						if(line.startsWith("webserviceURLWLAN")){
							com.savi.base.util.Constants.webserviceURLWLAN=line.substring(line.indexOf('=')+1).trim();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					com.savi.base.util.Constants.pollingInterval=300;
					com.savi.base.util.Constants.debug=2;
					e.printStackTrace();
				}
	        }else{
	        	com.savi.base.util.Constants.debug=2;
	        	com.savi.base.util.Constants.pollingInterval=300;
	        }
	        //自动刷新
	        SwitchBasicInfoDao switchBasicInfoDao = new SwitchBasicInfoDao();
	        List<Switchbasicinfo> baseinfoList = new ArrayList<Switchbasicinfo>();
			baseinfoList = switchBasicInfoDao.getSwitchBasicInfoListByDel(new Integer(0));
			if(null != baseinfoList && baseinfoList.size() > 0){
				Switchbasicinfo switchbasicinfo = baseinfoList.get(0);
				CollectionFactory.resgisterForPoll(switchbasicinfo);
				
			}
    }
    public void initIPCfg(){
		String path = Constants.webRealPath + "file/syscfg.xml";
		Document doc = W3CXML.loadXMLDocumentFromFile(path);
		NodeList iconWidth = doc.getElementsByTagName("iconWidth");
		NodeList iconHeight = doc.getElementsByTagName("iconHeight");
		NodeList netflowWebIP = doc.getElementsByTagName("netflowIP");
		NodeList netflowWebPort = doc.getElementsByTagName("netflowPort");
		NodeList analysisIP = doc.getElementsByTagName("analysisIP");
		NodeList analysisPort = doc.getElementsByTagName("analysisPort");
		if(iconWidth.item(0)!=null){
			Constants.ICON_WIDTH = iconWidth.item(0).getTextContent();
		}
		if(iconHeight.item(0)!=null){
			Constants.ICON_HEIGHT = iconHeight.item(0).getTextContent();
		}		
		if(netflowWebIP.item(0)!=null){
			Constants.NETFLOW_IP = netflowWebIP.item(0).getTextContent();
		}
		if(netflowWebPort.item(0)!=null){
			Constants.NETFLOW_PORT = netflowWebPort.item(0).getTextContent();
		}		
		if(analysisIP.item(0)!=null){
			Constants.ANALYSIS_IP = analysisIP.item(0).getTextContent();
		}
		if(analysisPort.item(0)!=null){
			Constants.ANALYSIS_PORT = analysisPort.item(0).getTextContent();
		}
	}
    
    /**
     * 注：下面这几句代码在windows下输入的格式是：yyyy-MM-dd，但在linux下的输入却是类似于Wed Dec 09 10:28:47 CST 2009 的格式
     * Calendar calendar = Calendar.getInstance();
	 * Date date = calendar.getTime();    
	 * DateFormat df = DateFormat.getDateInstance();    
	 * String current = df.format(date); 
     */
    /*private void startReachability() {
    	Timer timer = new Timer(true);
    	long intervalHour = 60 * 60 * 1000;
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.HOUR_OF_DAY, 1);
    	calendar.set(Calendar.MINUTE, 30);
    	calendar.set(Calendar.SECOND, 0);
    	timer.schedule(new ReachabilityTask(), calendar.getTime(),intervalHour);
    }*/
    private void startPerformanceTest() {
    	Timer timer = new Timer(true);
    	long intervalHour = 60 * 60 * 1000;
    	timer.schedule(new PerformanceTask(), 0,intervalHour);
    }
    private void startBackup() {
        Timer timer = new Timer(true);
        long day = 24 * 60 * 60 * 1000;
		Date date = new Date(new Long(System.currentTimeMillis())-7*day);
		String  preDate = MessageFormat.format("{0,date,yyyy-MM-dd}" ,date);
		String backupPath = Constants.webRealPath + Constants.BACKUP_FOLDER + File.separator + "fix" + File.separator + preDate + File.separator ;
		File backupFolder = new File(backupPath);
        if (backupFolder.exists() && backupFolder.isDirectory()) {
        	//删除该文件夹及其包含的数据
        	FileUtil.deleteFolder(backupFolder);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        timer.schedule(new BackupTask(MessageFormat.format("{0,date,yyyy-MM-dd}" ,new Date(new Long(System.currentTimeMillis())))), calendar.getTime(), day);
    }
    private void startCollegeIP() {
    	Timer timer = new Timer(true);
    	long intervalHour = 60 * 60 * 1000;
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.HOUR_OF_DAY, 1);
    	calendar.set(Calendar.MINUTE, 30);
    	calendar.set(Calendar.SECOND, 0);
    	timer.schedule(new CollegeIPTask(), calendar.getTime(),intervalHour);
//    	new ReadCollegeAction().readIPNum("hour");
    }
    private void startCollegeIPDay(){
    	Timer timer = new Timer(true);
    	 long day =  24 * 60 * 60 * 1000;
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 25);
        calendar.set(Calendar.SECOND, 0);
        timer.schedule(new CollegeDayIPTask(), calendar.getTime(), day);
//        new ReadCollegeAction().readIPNum("day");
    }
    /*
    private void startTotalIP(){
    	System.out.println("startTotalIP");
    	Timer timer = new Timer(true);
    	 long intervalHour = 60 * 60 * 1000;
		Calendar ca = Calendar.getInstance();		
		int hour=ca.get(Calendar.HOUR_OF_DAY);//小时	
		
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
//        timer.schedule(new TotalIPTask(), calendar.getTime(),intervalHour);
        new ReadTotalAction().readHourIPNum();
    }
    private void startTotalIPDay(){
    	System.out.println("startTotalIPDay");
    	Timer timer = new Timer(true);
    	 long day =  24 * 60 * 60 * 1000;
		
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 25);
        calendar.set(Calendar.SECOND, 0);
        timer.schedule(new TotalIPDayTask(), calendar.getTime(), day);
    }
    */
    private void startTotalIPWeek(){
    	System.out.println("startTotalIPWeek");
    	Timer timer = new Timer(true);
    	 long week = 7 * 24 * 60 * 60 * 1000;
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.DAY_OF_WEEK, 2);
		calendar.add(calendar.WEEK_OF_YEAR,1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        timer.schedule(new TotalIPWeekTask(), calendar.getTime(), week);
//        new ReadTotalAction().readWeekIPNum();
    }
    private void startTotalIPMonth(){
    	System.out.println("startTotalIPMonth");
    	Timer timer = new Timer(true);
    	 long day = 24 * 60 * 60 * 1000;
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        timer.schedule(new TotalIPMonthTask(), calendar.getTime(), day);
        
//        new ReadTotalAction().readMonthIPNum();
    }
    public static void main(String[] args) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.HOUR_OF_DAY, 1);
    	calendar.set(Calendar.MINUTE, 30);
    	calendar.set(Calendar.SECOND, 0);
    	System.out.println(MessageFormat.format("{0,date,yyyyMMddHH}" ,calendar.getTime()));
    }
    
    
}
