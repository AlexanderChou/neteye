package com.savi.collection.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.poi.util.SystemOutLogger;

import com.savi.base.model.User;
import com.savi.collection.dao.UserDao;
import com.savi.base.model.Apinfo;
import com.savi.base.model.Deviceinfo;
import com.savi.base.model.FilterCountCur;
import com.savi.base.model.Ifinterfacehis;
import com.savi.base.model.Ifinterfacecur;
import com.savi.base.model.SaviFilterTableCur;
import com.savi.base.model.Savibindingtablecur;
import com.savi.base.model.Savibindingtablehis;
import com.savi.base.model.Switchbasicinfo;
import com.savi.base.model.Switchcur;
import com.savi.base.model.Switchhis;
import com.savi.base.util.Constants;
import com.savi.base.util.SnmpCreate;
import com.savi.base.util.SnmpGetTable;
import com.savi.base.util.Snmph3cGetTable;
import com.savi.cernet.soa.WSUtil;
import com.savi.collection.dao.SavibindingtablehisDao;
import com.savi.collection.dao.SwitchBasicInfoDao;
import com.savi.collection.dao.SwitchCurDao;
import com.savi.collection.dao.SwitchHisDao;
import com.savi.show.dao.ApinfoDao;
import com.savi.show.dao.DeviceDao;
import com.savi.show.dao.FilterCountCurDao;
import com.savi.show.dao.SaviFilterTableCurDao;
import com.savi.show.dto.OnlineInfo;
import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

public class CollectionTaskh3c extends TimerTask{
	private Long deviceInfoId;
	private Snmph3cGetTable snmph3cGetTable;
	private Timer timer;
	private long period = 5 * 60*1000;
	private String debugFileName;
	public CollectionTaskh3c(Long deviceInfoId){
		this.deviceInfoId=deviceInfoId;
		timer = new Timer();
		/*
		 * 安排指定的任务在指定的时间开始进行重复的固定延迟执行。
		 */
		if(Constants.pollingInterval!=-1){
			this.period=Constants.pollingInterval*1000;
		}
		long delay=(long)(Math.random()*period);
		
		
		timer.schedule(this, delay, this.period);
	}
	
	public void run() {
		Date begintimeDate = new Date();
		System.out.println("h3c begin runtime:" + begintimeDate);
		System.out.println("acid:"+deviceInfoId);
		long beforeGetSnmpTime=0;
		try {
			DeviceDao deviceInfoDao=new DeviceDao();
			Deviceinfo deviceinfo=deviceInfoDao.getDeviceinfo(deviceInfoId);
			while(true){
				if(Constants.debug!=0&&Constants.webRealPath!=null)break;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(Constants.debug==1){
				Calendar now=Calendar.getInstance(); 
				String time=now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+
				 	now.get(Calendar.DAY_OF_MONTH)+" "+
				 	now.get(Calendar.HOUR_OF_DAY)+":"+
				 	now.get(Calendar.MINUTE)+":"+
				 	now.get(Calendar.SECOND)+":"+
				 	now.get(Calendar.MILLISECOND);
				this.debugFileName=Constants.webRealPath+"log/"+deviceinfo.getName()+".txt";
				File switchCurFile=new File(debugFileName);
				if(!switchCurFile.exists()){
					switchCurFile.createNewFile();
				}
				try {
					BufferedWriter bw=new BufferedWriter(new FileWriter(switchCurFile,true));
					bw.write(time);
					bw.write("   ");
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Date d = new Date();
				beforeGetSnmpTime=d.getTime();			    
			}
			snmph3cGetTable = new Snmph3cGetTable(deviceinfo);
			//apinfo
			 Constants.loadMibCount=0;
			String apobjectTableResult[][] = snmph3cGetTable.getTableData("hh3cDot11APObjectTable");
			
			//输出MIB中数据
			//if(apobjectTableResult!=null){
			//	for(int i=0;i<apobjectTableResult.length;i++){
			//		System.out.print("apname:" + apobjectTableResult[i][7]+"           ");
			//		System.out.print("\n");
			//	}
			//}
			
			//对应savifiltertablecur、savifiltertablehis
			String stationAPRelationTableResult[][] = snmph3cGetTable.getTableData("hh3cDot11StationAPRelationTable");
			String stationAssociateTableResult[][] = snmph3cGetTable.getTableData("hh3cDot11StationAssociateTable");
			String saviWlanObjectsFilteringTableResult[][] = snmph3cGetTable.getTableData("saviWlanObjectsFilteringTable");
			
			//对应filtercountcur表
			String servicePolicyTableResult[][] = snmph3cGetTable.getTableData("hh3cDot11ServicePolicyTable");
			String saviWlanObjectsCountTableResult[][] = snmph3cGetTable.getTableData("saviWlanObjectsCountTable");
			
			ApinfoDao apinfoDao = new ApinfoDao();
			apinfoDao.setStatusByAcid(deviceinfo.getId());//根据deviceInfoID查询APList列表，全置STATUS为0
			
			SaviFilterTableCurDao saviFilterTableCurDao = new SaviFilterTableCurDao();
			FilterCountCurDao filterCountCurDao = new FilterCountCurDao();
			
			
			if(null != stationAPRelationTableResult){
				saviFilterTableCurDao.updateEndTime();//将所有结束时间为空的置为当前时间
			}
			
			if(null != apobjectTableResult){
				for(int i = 1;i < apobjectTableResult.length;i++){
					//根据ACID,APID查询APINFO
					//int count = 0;
					Apinfo apinfo = apinfoDao.getApinfoByAcidApid(deviceinfo.getId(),Integer.parseInt(apobjectTableResult[i][14]));//apobjectTableResult第14列为APID
					if(null == apinfo){
						apinfo = new Apinfo();
					}
					apinfo.setAcid(deviceinfo.getId());
					apinfo.setApid(Integer.parseInt(apobjectTableResult[i][14]));
					apinfo.setIpv4Address(apobjectTableResult[i][1]);
					apinfo.setIpv6Address(apobjectTableResult[i][16]);
					apinfo.setApname(apobjectTableResult[i][7]);
					apinfo.setStatus(1);
					apinfoDao.saveApInfo(apinfo);
					
					//保存在线数据，通过saviWlanObjectsFilteringTable ,hh3cDot11StationAssociateTable ，hh3cDot11StationAPRelationTable获取
					//保存到表savifiltertablecur
					if(null != stationAPRelationTableResult){
						for(int j = 1;j < stationAPRelationTableResult.length;j++){
							if(apobjectTableResult[i][0].equals(stationAPRelationTableResult[j][1])){//主键相同
								if(null != stationAssociateTableResult){
									for(int m = 1;m<stationAssociateTableResult.length;m++){
										if(stationAPRelationTableResult[j][0].equals(stationAssociateTableResult[m][0])){//intvalue相同
											if(null != saviWlanObjectsFilteringTableResult){
												for(int n = 1;n < saviWlanObjectsFilteringTableResult.length;n++){
													if(saviWlanObjectsFilteringTableResult[n][3].equals(stationAssociateTableResult[j][22])){//MAC地址 相比较
														//根据MAC和IP、apid地址查一下数据库，存在将结束时间置空，不存在插入并且结束时间为空
														List<SaviFilterTableCur> saviFilterTableCursList = saviFilterTableCurDao.getListByMacIp(saviWlanObjectsFilteringTableResult[n][3],saviWlanObjectsFilteringTableResult[n][2],apinfo.getApid());
														if(null == saviFilterTableCursList || saviFilterTableCursList.size() == 0){
															SaviFilterTableCur saviFilterTableCur = new SaviFilterTableCur();
															saviFilterTableCur.setApid(new Long(apinfo.getApid()));//apid取自apinfo的apid
															saviFilterTableCur.setIpAddressType(Integer.parseInt(saviWlanObjectsFilteringTableResult[n][0]));
															saviFilterTableCur.setIpAddress(saviWlanObjectsFilteringTableResult[n][2]);
															saviFilterTableCur.setMacAddress(saviWlanObjectsFilteringTableResult[n][3]);
															saviFilterTableCur.setServiceName(Integer.parseInt(saviWlanObjectsFilteringTableResult[n][1]));
															saviFilterTableCur.setSSIDName(stationAssociateTableResult[m][11]);
															Long time = Long.parseLong(stationAssociateTableResult[m][4]);
															Long curtime = new Date().getTime();
															saviFilterTableCur.setStartTime(curtime - time);
															saviFilterTableCur.setApName(apinfo.getApname());
															//saviFilterTableCur.setUserName(userName);
															saviFilterTableCurDao.saveSaviFilterTableCur(saviFilterTableCur);
															//count++;
														}else{
															SaviFilterTableCur saviFilterTableCur = saviFilterTableCursList.get(0);
															saviFilterTableCur.setEndTime(null);
															saviFilterTableCur.setApName(apinfo.getApname());
															saviFilterTableCurDao.saveSaviFilterTableCur(saviFilterTableCur);
															//count++;
														}
														
																
													}
												}
											}
										}
											
									}
								}
								
							}
						}
					}
					//System.out.println(apinfo.getApname()+","+apinfo.getIpv4Address() + ":" + count);
			
					
					
				}
			}
			//保存filtercountcur表数据通过servicePolicyTableResult、saviWlanObjectsCountTableResult
			//不需要取servicePolicyTableResult表中数据，因为界面不显示，只显示COUNT值
			filterCountCurDao.deleteByAcid(deviceinfo.getId());//插入之前，先删除AC下的所有filterCountCur，再重新插入
			if(null != saviWlanObjectsCountTableResult){
				for(int j = 1;j < saviWlanObjectsCountTableResult.length;j++){
					FilterCountCur filterCountCur = new FilterCountCur();
					filterCountCur.setAcID(deviceinfo.getId());
					filterCountCur.setIfFilteringCount(Long.parseLong(saviWlanObjectsCountTableResult[j][2]));
					filterCountCur.setIpVersion(Integer.parseInt(saviWlanObjectsCountTableResult[j][0]));
					Long optimeLong = new Date().getTime();
					filterCountCur.setOpTime(optimeLong);
					//filterCountCur.setServicePolicyID(Long.parseLong(servicePolicyTableResult[j][0]));
					filterCountCurDao.saveFilterCountCur(filterCountCur);
				}
			}
			
			//更新saviFilterTableCur中的userName信息，用SOA获取
			//取出saviFilterTableCur表中userName为空的记录并且开始时间在24小时以内的
			List<SaviFilterTableCur> saviFilterTableCurList = saviFilterTableCurDao.getListUserNameisNull();
			if(null != saviFilterTableCurList && saviFilterTableCurList.size() > 0){
				System.out.println("2小时以内的userName为空的并且endTime为空的在线用户数：" + saviFilterTableCurList.size());
				for(int index = 0;index < saviFilterTableCurList.size();index++){
					SaviFilterTableCur sfTableCur = saviFilterTableCurList.get(index);
					String ipadd = sfTableCur.getIpAddress();//IP地址，
					//System.out.println(ipadd);
					String macadd = sfTableCur.getMacAddress();//MAC地址
					//根据IP地址取出在线list列表
					List<OnlineInfo> onlineList = WSUtil.getUserNameByIp(ipadd, new ArrayList<Integer>());
					if(null != onlineList && onlineList.size() > 0){
						for(int j = 0;j < onlineList.size();j++){
							String onlineIpv6 = onlineList.get(j).getIpv6();//IP地址
							String onlineMac = onlineList.get(j).getMac();//MAC地址格式为：9C-D2-1E-18-79-CF需要将-转为" "，大写转为小写
							onlineMac = onlineMac.replace("-", " ");
							onlineMac = onlineMac.toLowerCase();
							if(ipadd.equals(onlineIpv6) && macadd.equals(onlineMac)){//根据IP与MAC找出当前在线用户
								sfTableCur.setUserName(onlineList.get(j).getLogin());//找到一条就设置userName并且退出循环，因为列表onlineList是按时间倒排序，第一条就是最新登录用户
								saviFilterTableCurDao.saveSaviFilterTableCur(sfTableCur);
								System.out.println("找到IP地址为："+ipadd+"  MAC地址为："+onlineMac+"  用户名为："+onlineList.get(j).getLogin());
								break;
							}
						}
					}
					
				}
				
			}
			
			//将SaviFilterTableCur表中的apname更新到服务端
			//从服务端取出APNAME为空且24小时以内数据
			List<OnlineInfo> onlineList = WSUtil.getOnlineListBy24Hours( new ArrayList<Integer>());
			if(null != onlineList && onlineList.size() > 0){
				//获取所有的在线用户数据saviFilterTableCurList，按时间倒排序,apname不为null，且apname不为“”
				List<SaviFilterTableCur> curList = saviFilterTableCurDao.getSaviFilterTableCurListOrderByTimer();
				if(null != curList && curList.size() > 0){
					for(int m = 0;m < onlineList.size();m++){
						String onlineIPV6 = onlineList.get(m).getIpv6();
						String onlineMac = onlineList.get(m).getMac();
						onlineMac = onlineMac.replace("-", " ");
						onlineMac = onlineMac.toLowerCase();
						for(int n = 0;n < curList.size();n++){
							String saviIPADD = curList.get(n).getIpAddress();
							String macAdd = curList.get(n).getMacAddress();
							if(onlineIPV6.equals(saviIPADD) && onlineMac.equals(macAdd)){//根据IP与MAC找出在线用户SaviFilterTableCur中的APNAME
								Long onlineId = onlineList.get(m).getId();
								String apname = curList.get(n).getApName();
								String flag = WSUtil.setApname(onlineId,apname,new ArrayList<Integer>());//根据onlineId设置对应APNAME
								if(flag.equals("1")){
									System.out.println("成功更新onlineId = " + onlineId +"  apname="+apname);
								}
								break;
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date endtimeDate = new Date();
		System.out.println("h3c end runtime:" + endtimeDate);
		Long cha =  endtimeDate.getTime() - begintimeDate.getTime();
		System.out.println("h3c end - begin:" + cha);
		System.out.println("------------------");
	}

	public Long getDeviceInfoId() {
		return deviceInfoId;
	}

	public void setDeviceInfoId(Long deviceInfoId) {
		this.deviceInfoId = deviceInfoId;
	}

	
	
}
