package com.savi.collection.listener;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.savi.base.model.Switchbasicinfo;
import com.savi.base.model.Switchcur;
import com.savi.collection.dao.SwitchBasicInfoDao;
import com.savi.collection.dao.SwitchCurDao;

public class GarbageCollection extends TimerTask implements ServletContextListener{
	private long period = 10 * 60*1000;
	private Timer timer;
	private static Logger LOG = Logger.getLogger(GarbageCollection.class.getName());
	public void contextDestroyed(ServletContextEvent arg0) {
		LOG.info("stop GetSwitchInfo");
		timer.cancel();
	}
	public void contextInitialized(ServletContextEvent arg0) {
		LOG.info("start GarbageCollection");
		timer = new Timer(true);
		timer.schedule(this, 0, period);
	}
	public void run() {
		try{
			SwitchBasicInfoDao switchBasicInfoDao=new SwitchBasicInfoDao();
			SwitchCurDao switchCurDao=new SwitchCurDao();
			List<Switchbasicinfo> switchbasicinfoList=switchBasicInfoDao.getSwitchBasicInfoList(1);
			if(switchbasicinfoList!=null){
				for(int i=0;i<switchbasicinfoList.size();i++){
					List<Switchcur> switchCurList=switchCurDao.getSwitchCurList(switchbasicinfoList.get(i));
					if(switchCurList!=null){
						for(int j=0;j<switchCurList.size();j++){
							switchCurDao.delete(switchCurList.get(j));
						}
					}
				}
			}
		}catch(Exception e){
			e.getStackTrace();
		}
	}
	public static void main(String[] args) {
		
    }
}
