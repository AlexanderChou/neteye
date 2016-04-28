package com.savi.collection.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.savi.base.model.Deviceinfo;
import com.savi.base.model.Switchbasicinfo;
import com.savi.base.util.Constants;
import com.savi.collection.dao.SwitchBasicInfoDao;

public class CollectionFactory {
	private static Map<Long,CollectionTask> mapStore = new HashMap<Long,CollectionTask>();
	private static Map<Long,CollectionTaskh3c> mapStoreh3c = new HashMap<Long,CollectionTaskh3c>();
	//输入参数必须为一个从数据库中取出的Switchbasicinfo持久化类对象
	//TODO:新添加一个交换机后调用该方法开始对其进行数据周期采集
	static public void resgisterForPoll(Switchbasicinfo switchBasicInfo){
		System.out.println("resgisterForPoll switchBasicInfo id" + switchBasicInfo.getId());
		CollectionTask collectionTask=new CollectionTask(switchBasicInfo.getId());
		mapStore.put(switchBasicInfo.getId(), collectionTask);
	}
	//输入参数必须为一个从数据库中取出的Deviceinfo持久化类对象
		//TODO:新添加一个设备后调用该方法开始对其进行数据周期采集
	static public void resgisterApInfoForPoll(Deviceinfo deviceInfo){
		CollectionTaskh3c collectionTaskh3c=new CollectionTaskh3c(deviceInfo.getId());
		mapStoreh3c.put(deviceInfo.getId(), collectionTaskh3c);
	}
	
	public static void beginTaskExcu(Deviceinfo deviceinfo) throws Exception {
		CollectionTaskExcu collectionTaskExcu = new CollectionTaskExcu(deviceinfo.getId());
		collectionTaskExcu.beginTask();
		
	}
	
	//输入参数必须为一个从数据库中取出的Switchbasicinfo持久化类对象
	//TODO:删除一个交换机后调用该方法结束对其进行数据周期采集
	static public boolean stopCollection(Switchbasicinfo switchBasicInfo){
		CollectionTask collectionTask=mapStore.get(switchBasicInfo.getId());
		if(collectionTask!=null){
			boolean result=collectionTask.cancel();
			mapStore.remove(switchBasicInfo.getId());
			return result;
		}else{
			return false;
		}
	}
	public static void main(String[] args) {
		
		//Constants.realPath = "F:\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\neteye\WEB-INF";
		System.out.println(Constants.realPath );
	   // Constants.webRealPath = "F:\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\neteye\";
	  //  System.out.println(Constants.webRealPath );
	     Constants.webRealPath = "F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/neteye/";
	     System.out.println(Constants.webRealPath );
	     
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
						if(line.startsWith("webserviceURLWLAN")){
							Constants.webserviceURLWLAN=line.substring(line.indexOf('=')+1).trim();
						}
					}
				} catch (IOException e) {
					Constants.pollingInterval=300;
					Constants.debug=2;
					e.printStackTrace();
				}
	        }else{
	        	Constants.debug=2;
	        	Constants.pollingInterval=300;
	        }	
		//SwitchBasicInfoDao switchBasicInfoDao=new SwitchBasicInfoDao();
		//Switchbasicinfo switchBasicInfo=switchBasicInfoDao.getSwitchBasicInfo(new Long(1089));
		//CollectionFactory.resgisterForPoll(switchBasicInfo);
		CollectionTask collectionTask=new CollectionTask(new Long(1094),new Long(0));
		collectionTask.run();
	}	
}