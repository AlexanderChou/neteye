package com.savi.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;
import org.jbpm.JbpmConfiguration;

import com.savi.base.model.Administrator;
import com.savi.base.model.Deviceinfo;
import com.savi.base.model.Switchbasicinfo;
import com.savi.collection.dao.SwitchBasicInfoDao;
import com.savi.collection.util.CollectionFactory;
import com.savi.show.dao.DeviceDao;
import com.savi.user.dao.AdminDao;



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
	/***
	static JbpmConfiguration jbpmConfiguration = null;

	static {

		jbpmConfiguration = JbpmConfiguration.getInstance();

	}***/
    public void init() throws ServletException {
        Constants.realPath = getServletContext().getRealPath("/WEB-INF/");
        Constants.webRealPath = getServletContext().getRealPath("/");
        Constants.webRealPath = Constants.webRealPath.replace('\\', '/');
        File configFile=new File(Constants.webRealPath+"sys.conf");
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
							Constants.debug=1;
						}else{
							Constants.debug=2;
						}
					}
					if(line.startsWith("pollingInterval")){
						String pollingInterval=line.substring(line.indexOf('=')+1);
						Constants.pollingInterval=Integer.parseInt(pollingInterval);
					}
					if(line.startsWith("deployMode")){
						String deployMode=line.substring(line.indexOf('=')+1);
						if(deployMode.equals("independent")){
							Constants.deployMode=1;
						}else{
							Constants.deployMode=2;
						}
						
					}
					if(line.startsWith("webserviceURL")){
						Constants.webServiceURL=line.substring(line.indexOf('=')+1).trim();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Constants.pollingInterval=300;
				Constants.debug=2;
				e.printStackTrace();
			}
        }else{
        	Constants.debug=2;
        	Constants.pollingInterval=300;
        }
        /*
        //guoxi
        JbpmContext context=jbpmConfiguration.createJbpmContext();
		FileInputStream fis;
		try {
			
			fis = new FileInputStream(Constants.webRealPath + "file/ticket/" +"processdefinition.xml");
			System.out.println("fis="+fis);
			ProcessDefinition processDefinition = ProcessDefinition.parseXmlInputStream(fis);;
			context.deployProcessDefinition(processDefinition);
			context.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//guoxi
        
       
		AdminDao adminDao=new AdminDao();
		Administrator administrator=adminDao.getAdmin("admin");
		if(administrator==null){
			Administrator newRootUser=new Administrator();
			newRootUser.setName("admin");
			newRootUser.setPassword("nmstsinghua");
			newRootUser.setRole(3);
			adminDao.save(newRootUser);
		}
		SwitchBasicInfoDao switchBasicInfoDao=new SwitchBasicInfoDao();
		List<Switchbasicinfo> switchbasicinfoList=switchBasicInfoDao.getSwitchBasicInfoList(0);
		//ArrayList<Long> list=new ArrayList<Long>();
		if(switchbasicinfoList!=null){
			for(int i=0;i<switchbasicinfoList.size();i++){
				//list.add(switchbasicinfoList.get(i).getId());
				CollectionFactory.resgisterForPoll(switchbasicinfoList.get(i));
			}
			//boolean result=sort(list);
		}
		//取出所有iddelete为0的AC列表
		DeviceDao deviceInfoDao=new DeviceDao();
		List<Deviceinfo> deviceInfoList=deviceInfoDao.getDeviceInfoList(0);
		if(deviceInfoList != null){
			for(int j = 0;j<deviceInfoList.size();j++){
				CollectionFactory.resgisterApInfoForPoll(deviceInfoList.get(j));
			}
		}
		System.setProperty("webappRoot", getServletContext().getRealPath("/"));        
		//PropertyConfigurator.configure(getServletContext().getRealPath("/") + getInitParameter("configfile"));  
    }
    boolean sort(ArrayList<Long> list){
    	for(int i=0;i<list.size();i++){
    		Long temp=list.get(i);
    		for(int j=i+1;j<list.size();j++){
    			if(temp.longValue()==list.get(j).longValue()){
    				return true;
    			}
    		}
    	}
    	return false;
    }
}
