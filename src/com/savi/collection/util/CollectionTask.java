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

import com.savi.base.model.User;
import com.savi.collection.dao.UserDao;
import com.savi.base.model.Ifinterfacehis;
import com.savi.base.model.Ifinterfacecur;
import com.savi.base.model.SaviFilterTableCur;
import com.savi.base.model.Savibindingtablecur;
import com.savi.base.model.Savibindingtablehis;
import com.savi.base.model.Switchbasicinfo;
import com.savi.base.model.Switchcur;
import com.savi.base.model.Switchhis;
import com.savi.base.util.Constants;
import com.savi.base.util.SnmpGetTable;
import com.savi.cernet.soa.WSUtil;
import com.savi.collection.dao.SavibindingtablehisDao;
import com.savi.collection.dao.SwitchBasicInfoDao;
import com.savi.collection.dao.SwitchCurDao;
import com.savi.collection.dao.SwitchHisDao;
import com.savi.show.dao.SaviFilterTableCurDao;
import com.savi.show.dao.SavibindingtableDao;
import com.savi.show.dto.OnlineInfo;

public class CollectionTask extends TimerTask{
	private Long switchBasicInfoId;
	private SnmpGetTable snmpGetTable;
	private Timer timer;
	private long period = 5 * 60*1000;
	private String debugFileName;
	private String ipv4address = "";
	
	public CollectionTask(Long switchBasicInfoId){
		System.out.println("para1");
		this.switchBasicInfoId=switchBasicInfoId;
		timer = new Timer();
		/*
		 * 安排指定的任务在指定的时间开始进行重复的固定延迟执行。
		 */
		if(Constants.pollingInterval!=-1){
			this.period=Constants.pollingInterval*1000;
		}
		long delay=(long)(Math.random()*period);
		timer.schedule(this, 15000, this.period);
		//timer.schedule(this, 5000, 15000);
	}
	public CollectionTask(Long switchBasicInfoId, Long flag){
		System.out.println("para2");
		this.switchBasicInfoId=switchBasicInfoId;
	}
	public boolean cancel(){
		/*
		 * 当用户删除一个交换机时，系统会调用此函数，首先此函数会将在*cur表中与switchBasicInfo对应的所有记录删除，
		 * 然后再调用父类的cancel方法停止此轮询任务。
		 */
		/*
		 * 取消此计时器任务。如果任务安排为一次执行且还未运行，或者尚未安排，则永远不会运行。
		 * 如果任务安排为重复执行，则永远不会再运行。
		 * （如果发生此调用时任务正在运行，则任务将运行完，但永远不会再运行。） 
         * 注意，从重复的计时器任务的 run 方法中调用此方法绝对保证计时器任务永远不会再运行。 
         * 此方法可以反复调用；第二次和以后的调用无效。
         * 如果此任务安排为一次执行且尚未运行，或者此任务安排为重复执行，则返回 true。
         * 如果此任务安排为一次执行且已经运行，或者此任务尚未安排，或者此任务已经取消，则返回 false。
         * （一般来说，如果此方法阻止发生一个或多个安排执行，则返回 true。）
		 */
		/*
		 * 终止此计时器，丢弃所有当前已安排的任务。这不会干扰当前正在执行的任务（如果存在）。一旦终止了计时器，那么它的执行线程也会终止，并且无法根据它安排更多的任务。 
         * 注意，在此计时器调用的计时器任务的 run 方法内调用此方法，就可以绝对确保正在执行的任务是此计时器所执行的最后一个任务。 
         * 可以重复调用此方法；但是第二次和后续调用无效
		 */
		this.timer.cancel();
		SwitchCurDao switchCurDao=new SwitchCurDao();
		SwitchBasicInfoDao switchBasicInfoDao=new SwitchBasicInfoDao();
		Switchbasicinfo switchBasicInfo=switchBasicInfoDao.getSwitchBasicInfo(switchBasicInfoId);
		List<Switchcur> switchCurList=switchCurDao.getSwitchCurList(switchBasicInfo);
		if(switchCurList!=null){
			for(int i=0;i<switchCurList.size();i++){
				Switchcur switchCur=switchCurList.get(i);
				switchCurDao.delete(switchCur);
			}
		}
		return true;	
	}
	public void run() {
		Date begintimeDate = new Date();
		System.out.println("savi begin runtime:" + begintimeDate);
		
		long beforeGetSnmpTime=0,afterGetSnmpTime=0,beforeResolveData=0,afterResolveData=0;
		int haveSystemTableResult=0,haveIfTableResult=0,haveBindingTableResult=0,haveFilteringTableResult=0;
		System.out.println(" get savibindingtableDao be");
		SavibindingtableDao savibindingtableDao = new SavibindingtableDao();
		System.out.println(" get savibindingtableDao en");
		try{
			
			SwitchBasicInfoDao switchBasicInfoDao=new SwitchBasicInfoDao();
			Switchbasicinfo switchBasicInfo=switchBasicInfoDao.getSwitchBasicInfo(switchBasicInfoId);
			ipv4address = switchBasicInfo.getIpv4address();
			System.out.println("switchBasicInfo  ipv4address:"+ipv4address);
			System.out.println("Constants.debug:"+Constants.debug);
			System.out.println("Constants.webRealPath:"+Constants.webRealPath);
			System.out.println("Constants.webserviceURLWLAN:"+Constants.webserviceURLWLAN);
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
				this.debugFileName=Constants.webRealPath+"log/"+switchBasicInfo.getName()+".txt";
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
			snmpGetTable=new SnmpGetTable(switchBasicInfo);
			Constants.loadMibCount=0;
			System.out.println("snmpGetTable:"+snmpGetTable);
			String systemTableResult[][]=snmpGetTable.getTableData("saviObjectsSystemTable");
			String ifTableResult[][]=snmpGetTable.getTableData("saviObjectsIfTable");
			String bindingTableResult[][]=snmpGetTable.getTableData("saviObjectsBindingTable");
			String filteringTableResult[][]=snmpGetTable.getTableData("saviObjectsFilteringTable");
			if(systemTableResult!=null){
				System.out.println("systemTableResult.length:"+systemTableResult.length);
			}
			if(bindingTableResult!=null){
				System.out.println("bindingTableResult.length:"+bindingTableResult.length);

			}
			if(filteringTableResult!=null){
				System.out.println("filteringTableResult.length:"+filteringTableResult.length);
			}
			if(Constants.debug==1){
				Date d = new Date();
				afterGetSnmpTime=d.getTime();
				if(systemTableResult!=null){
					haveSystemTableResult=systemTableResult.length;
				}
				if(ifTableResult!=null){
					haveIfTableResult=ifTableResult.length;
				}
				if(bindingTableResult!=null){
					haveBindingTableResult=bindingTableResult.length;
				}
				if(filteringTableResult!=null){
					haveFilteringTableResult=filteringTableResult.length;
				}
				this.debugFileName=Constants.webRealPath+"log/"+switchBasicInfo.getName()+".txt";
				File switchCurFile=new File(debugFileName);
				if(!switchCurFile.exists()){
					switchCurFile.createNewFile();
				}
				try {
					BufferedWriter bw=new BufferedWriter(new FileWriter(switchCurFile,true));
					bw.write((afterGetSnmpTime-beforeGetSnmpTime)+"");
					bw.write("   ");
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//再取一遍，防止数据不同步
			switchBasicInfo=switchBasicInfoDao.getSwitchBasicInfo(switchBasicInfoId);
			//首先将switchBasicInfo对应的上一次轮询的cur表中的信息转存到his表中，然后将上一次的信息从cur表中删除
			if(Constants.debug==1){
				Date d=new Date();
				beforeResolveData=d.getTime();
			}
			//moveDataFromCurToHis(switchBasicInfo);
			//以下是从该交换机获取所有savi信息
			SwitchCurDao switchCurDao=new SwitchCurDao();
			Set<Switchcur> switchCurSet=new LinkedHashSet<Switchcur>();
			boolean ipv4Flag=false,ipv6Flag=false;
			//如果systemTableResult不为空，说明本次轮询获得到相应的savi信息，则对信息进行处理，并存到cur表中
			if(systemTableResult!=null){
				for(int i=1;i<systemTableResult.length;i++){
					Switchcur switchCur=new Switchcur();
					switchCur.setSwitchbasicinfo(switchBasicInfo);
					switchCur.setIpVersion(Integer.parseInt(systemTableResult[i][0]));
					switchCur.setSystemMode(Integer.parseInt(systemTableResult[i][1]));
					switchCur.setMaxDadDelay(Integer.parseInt(systemTableResult[i][2]));
					switchCur.setMaxDadPrepareDelay(Integer.parseInt(systemTableResult[i][3]));
					Set<Ifinterfacecur> ifInterfaceCurSet=new LinkedHashSet<Ifinterfacecur>();
					if(ifTableResult!=null){
						for(int j=1;j<ifTableResult.length;j++){//存储ifInterfaceTable相关信息
							if(Integer.parseInt(ifTableResult[j][0])==switchCur.getIpVersion().intValue()){
								Ifinterfacecur ifInterfaceCur=new Ifinterfacecur();
								ifInterfaceCur.setSwitchcur(switchCur);
								ifInterfaceCur.setIpVersion(Integer.parseInt(ifTableResult[j][0]));
								ifInterfaceCur.setIfIndex(Integer.parseInt(ifTableResult[j][1]));
								ifInterfaceCur.setIfValidationStatus(Integer.parseInt(ifTableResult[j][2]));
								ifInterfaceCur.setIfTrustStatus(Integer.parseInt(ifTableResult[j][3]));
								ifInterfaceCur.setIfFilteringNum(Long.parseLong(ifTableResult[j][4]));
								Set<Savibindingtablecur> bTableCurSet=new LinkedHashSet<Savibindingtablecur>();
								if(bindingTableResult!=null){
									for(int t=1;t<bindingTableResult.length;t++){
										if(Integer.parseInt(bindingTableResult[t][0])==switchCur.getIpVersion().intValue()&&
												Integer.parseInt(bindingTableResult[t][2])==ifInterfaceCur.getIfIndex().intValue()){
											Savibindingtablecur savibindingtablecur=new Savibindingtablecur();
											savibindingtablecur.setIpAddressType(Integer.parseInt(bindingTableResult[t][0]));
											savibindingtablecur.setBindingType(Integer.parseInt(bindingTableResult[t][1]));
											savibindingtablecur.setIfIndex(Integer.parseInt(bindingTableResult[t][2]));
											savibindingtablecur.setIpAddress(bindingTableResult[t][3]);
											//savibindingtablecur.setUserName(WSUtil.getUserName(savibindingtablecur.getIpAddress()));
											savibindingtablecur.setMacAddress(bindingTableResult[t][4]);
											savibindingtablecur.setBindingState(Integer.parseInt(bindingTableResult[t][5]));
											savibindingtablecur.setBindingLifetime(Integer.parseInt(bindingTableResult[t][6]));
											int isInFilteringTable=justify(savibindingtablecur,filteringTableResult);
											savibindingtablecur.setIsInFilteringTable(isInFilteringTable);
											savibindingtablecur.setIfinterfacecur(ifInterfaceCur);
											bTableCurSet.add(savibindingtablecur);
										}
									}
								}
								ifInterfaceCur.setSaviBindingTableCurs(bTableCurSet);
								ifInterfaceCurSet.add(ifInterfaceCur);
							}
						}
					}
					switchCur.setIfInterfaceCurs(ifInterfaceCurSet);
					int staticNum=getStatisticData(switchCur,1);
					int slaacNum=getStatisticData(switchCur,2);
					int dhcpNum=getStatisticData(switchCur,3);
					switchCur.setStaticNum(staticNum);
					switchCur.setSlaacNum(slaacNum);
					switchCur.setDhcpNum(dhcpNum);
					SavibindingtablehisDao bTableHisDao=new SavibindingtablehisDao();
					Date d = new Date();
					long longtime = (d.getTime()/period)*period;
					//long longtime = d.getTime();
					List<Savibindingtablehis> bindingTableHisList=bTableHisDao.getSwitchhisOnline(switchCur.getSwitchbasicinfo().getId(), switchCur.getIpVersion());
					List<Savibindingtablecur> bindingTableCurList=getBindingTableCurList(switchCur);
					List<Savibindingtablehis> newBTableHisList=ProcessBindingTableDate(bindingTableHisList,bindingTableCurList,longtime);
					saveNewInforation(switchBasicInfo,switchCur,newBTableHisList,longtime);
					List<Switchcur> switchLastCurList=switchCurDao.getSwitchCur(switchBasicInfo,switchCur.getIpVersion());
					if(switchLastCurList!=null){
						for(int x=0;x<switchLastCurList.size();x++)
							if(switchLastCurList.get(x)!=null){
								switchCurDao.delete(switchLastCurList.get(x));
							}
					}
					//switchCurDao.saveSwitchCurInfo(switchCur);
					if(switchCur.getIpVersion()==2){
						ipv6Flag=true;
					}else{
						ipv4Flag=true;
					}
					switchCurSet.add(switchCur);
				}
				switchBasicInfo.setSwitchcurs(switchCurSet);
				//轮询成功，则将该switchBasicInfo状态改为1，表示该交换机现在已连通
				switchBasicInfo.setStatus(1);
				switchBasicInfoDao.update(switchBasicInfo);
				//对于新获得的值，如果上一次有关于此ipversion信息，这一次没有，那么上一次的信息需要在这里删除。
				if(!ipv4Flag){
					List<Switchcur> switchLastCurList=switchCurDao.getSwitchCur(switchBasicInfo,1);
					if(switchLastCurList!=null){
						for(int x=0;x<switchLastCurList.size();x++)
							if(switchLastCurList.get(x)!=null){
								switchCurDao.delete(switchLastCurList.get(x));
							}
					}
				}
				if(!ipv6Flag){
					List<Switchcur> switchLastCurList=switchCurDao.getSwitchCur(switchBasicInfo,2);
					if(switchLastCurList!=null){
						for(int x=0;x<switchLastCurList.size();x++)
							if(switchLastCurList.get(x)!=null){
								switchCurDao.delete(switchLastCurList.get(x));
							}
					}
				}
			}else{//如果systemTableResult为空，说明此次轮询失败，此时需要将switchBasicInfo的status设成0，表示该交换机已断开
				//switchBasicInfo.setSwitchcurs(null);
				switchBasicInfo.setStatus(0);
				switchBasicInfoDao.update(switchBasicInfo);
				List<Switchcur> switchLastList=switchCurDao.getSwitchCurList(switchBasicInfo);
				//如果此次轮询，该交换机没有获得到数据，那么应该删除上一次的所有cur表相应记录。
				if(switchLastList!=null){//此判断为了保证程序的健壮性，如果没有与switchBasicInfo对应的switchCur，那么switchCurList也不为空，长度为0
					if(switchLastList.size()>0){
						for(int i=0;i<switchLastList.size();i++){
							Switchcur switchLast=switchLastList.get(i);
							switchCurDao.delete(switchLast);
						}
					}
				}
			}
			if(Constants.debug==1){
				Date d=new Date();
				afterResolveData=d.getTime();
				UserDao userDao=new UserDao();
				User user=userDao.getUser();
				int num=0;
				if(user!=null){
					num=user.getStatus();
				}
				//int num=bingTableHisDao.calculateTotalNum();
				this.debugFileName=Constants.webRealPath+"log/"+switchBasicInfo.getName()+".txt";
				File switchCurFile=new File(debugFileName);
				try {
					BufferedWriter bw=new BufferedWriter(new FileWriter(switchCurFile,true));
					bw.write((afterResolveData-beforeResolveData)+"   ");
					bw.write(haveSystemTableResult+"   ");
					bw.write(haveIfTableResult+"   ");
					bw.write(haveBindingTableResult+"   ");
					bw.write(haveFilteringTableResult+"   ");
					bw.write(num+"");
					bw.newLine();
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch(Exception ex){
			SwitchBasicInfoDao switchBasicInfoDao=new SwitchBasicInfoDao();
			Switchbasicinfo switchBasicInfo=switchBasicInfoDao.getSwitchBasicInfo(switchBasicInfoId);
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
				String time=now.get(Calendar.YEAR)+"-"+
								(now.get(Calendar.MONTH)+1)+"-"+
								 now.get(Calendar.DAY_OF_MONTH)+" "+
								 now.get(Calendar.HOUR_OF_DAY)+":"+
								 now.get(Calendar.MINUTE)+":"+
								 now.get(Calendar.SECOND);
				String time2=now.get(Calendar.YEAR)+"-"+
								(now.get(Calendar.MONTH)+1)+"-"+
								 now.get(Calendar.DAY_OF_MONTH)+""+
								 now.get(Calendar.HOUR_OF_DAY)+"_"+
								 now.get(Calendar.MINUTE)+"_"+
								 now.get(Calendar.SECOND);
				//this.debugFileName=Constants.webRealPath+"log/"+switchBasicInfo.getName()+"_"+switchBasicInfo.getId()+"/"+time2+".txt";
				this.debugFileName=Constants.webRealPath+"log/"+time2+".txt";
				File switchDirectory=new File(Constants.webRealPath+"log/");
				boolean flag=false;
				if(!switchDirectory.exists()){
					flag=switchDirectory.mkdir();
				}else{
					flag=true;
				}
				if(flag){
					File switchCurFile=new File(debugFileName);
					try {
						if(switchCurFile.createNewFile()){
							BufferedWriter bw=new BufferedWriter(new FileWriter(switchCurFile,true));
							bw.write(switchBasicInfo.getName()+"_"+switchBasicInfo.getIpv4address()+"_"+switchBasicInfo.getIpv6address());
							bw.newLine();
							now=Calendar.getInstance(); 
							time=now.get(Calendar.YEAR)+"-"+
											(now.get(Calendar.MONTH)+1)+"-"+
											 now.get(Calendar.DAY_OF_MONTH)+" "+
											 now.get(Calendar.HOUR_OF_DAY)+":"+
											 now.get(Calendar.MINUTE)+":"+
											 now.get(Calendar.SECOND)+":"+
											 now.get(Calendar.MILLISECOND);
							bw.write("time when get exception:"+time);
							bw.newLine();
							bw.close();
							PrintWriter s=new PrintWriter(new FileWriter(switchCurFile,true));
							ex.printStackTrace(s);
							s.flush();
							s.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		//更新Savibindingtablecur中的userName信息，用SOA获取
		//List<Savibindingtablecur> savibindingtableList = savibindingtableDao.getListUserNameisNull();
		//if(null != savibindingtableList && savibindingtableList.size() > 0){
		//	for(int index = 0;index < savibindingtableList.size();index++){
		//		Savibindingtablecur sbTableCur = savibindingtableList.get(index);
		//		String ipadd = sbTableCur.getIpAddress();//IP地址，
				//System.out.println(ipadd);
		//		String macadd = sbTableCur.getMacAddress();//MAC地址
				//根据IP地址取出在线list列表
		//		List<OnlineInfo> onlineList = WSUtil.getUserNameByIp(ipadd, new ArrayList<Integer>());
		//		if(null != onlineList && onlineList.size() > 0){
		//			for(int j = 0;j < onlineList.size();j++){
		//				String onlineIpv6 = onlineList.get(j).getIpv6();//IP地址
		//				String onlineMac = onlineList.get(j).getMac();//MAC地址格式为：9C-D2-1E-18-79-CF需要将-转为" "，大写转为小写
		//				if(ipadd.equals(onlineIpv6) && macadd.equals(onlineMac)){//根据IP与MAC找出当前在线用户
		//					sbTableCur.setUserName(onlineList.get(j).getLogin());//找到一条就设置userName并且退出循环，因为列表onlineList是按时间倒排序，第一条就是最新登录用户
		//					savibindingtableDao.saveSaviBindingTableCur(sbTableCur);
		//					System.out.println("找到IP地址为："+ipadd+"  MAC地址为："+onlineMac+"  用户名为："+onlineList.get(j).getLogin());
		//					break;
		//				}
		//			}
		//		}
		//	}
		//}
		Date endtimeDate = new Date();
		System.out.println("savi end runtime:" + endtimeDate);
		Long cha =  endtimeDate.getTime() - begintimeDate.getTime();
		System.out.println("savi end - begin:" + cha);
		System.out.println("------------------");
	}
	private int getStatisticData(Switchcur switchCur,int bindingType){
		int count=0;
		Iterator<Ifinterfacecur> interfaceIterator=switchCur.getIfInterfaceCurs().iterator();
		while(interfaceIterator.hasNext()){
			Ifinterfacecur ifInterfaceCur=interfaceIterator.next();
			Iterator<Savibindingtablecur> bTableIterator=ifInterfaceCur.getSaviBindingTableCurs().iterator();
			while(bTableIterator.hasNext()){
				Savibindingtablecur bTableCur=bTableIterator.next();
				if(bTableCur.getBindingType().intValue()==bindingType){
					count++;
				}
			}
		}
		return count;
	}
	private int justify(Savibindingtablecur savibindingtablecur,String filteringTableResult[][]){
		if(filteringTableResult!=null&&savibindingtablecur!=null){
			for(int i=1;i<filteringTableResult.length;i++){
				if(Integer.parseInt(filteringTableResult[i][0])==savibindingtablecur.getIpAddressType().intValue()&&
						Integer.parseInt(filteringTableResult[i][1])==savibindingtablecur.getIfIndex().intValue()&&
						filteringTableResult[i][2].equals(savibindingtablecur.getIpAddress())&&
						filteringTableResult[i][3].equals(savibindingtablecur.getMacAddress())){
					return 1;
				}
			}
			return 0;
		}else{
			return 0;
		}
	}
	//此函数不用了
	private void moveDataFromCurToHis(Switchbasicinfo switchBasicInfo){//Switchbasicinfo必须有主键
		SavibindingtablehisDao bTableHisDao=new SavibindingtablehisDao();
		SwitchCurDao switchCurDao=new SwitchCurDao();
		Date d = new Date();
		long longtime = (d.getTime()/period)*period-period;
		List<Switchcur> switchCurList=switchCurDao.getSwitchCurList(switchBasicInfo);
		/*
		 * 如果switchCurList为空，则在switchCur表中没有switchBasicInfo对应的记录，即该交换机或者是一个新加入的，或者在上一次轮询中其不可达。
		 * 如果switchCurList不为空，则在switchCur表中有switchBasicInfo对应的记录，此时需要首先将*Cur表中该记录转存到*His表中，然后将原来的
		 * *cur表中针对该交换机的记录全部删掉。
		 */
		if(switchCurList!=null){//此判断为了保证程序的健壮性，如果没有与switchBasicInfo对应的switchCur，那么switchCurList也不为空，长度为0
			if(switchCurList.size()>0){
				for(int i=0;i<switchCurList.size();i++){
					Switchcur switchCur=switchCurList.get(i);
					List<Savibindingtablehis> bindingTableHisList=bTableHisDao.getSwitchhisOnline(switchCur.getSwitchbasicinfo().getId(), switchCur.getIpVersion());
					List<Savibindingtablecur> bindingTableCurList=getBindingTableCurList(switchCur);
					List<Savibindingtablehis> newBTableHisList=ProcessBindingTableDate(bindingTableHisList,bindingTableCurList,longtime);
					saveNewInforation(switchBasicInfo,switchCur,newBTableHisList,longtime);
					switchCurDao.delete(switchCur);
				}
			}else{/*此部分正常流程不会出现，因为每次轮询时如果这一次没有数据，那么既然his表中有status为1的记录，那么一定是之前cur表中
			       *有过数据，所以从第一个没有数据那一刻开始就会正确的处理his表中status为1的记录
			       */
				//List<Savibindingtablehis> bindingTableHisList=bTableHisDao.getSwitchhisOnline(switchBasicInfo);
				//ProcessBindingTableDate(bindingTableHisList,new ArrayList<Savibindingtablecur>(),longtime);
			}
		}
	}
	/*
	 * 该函数将switchCur存成对应的switchHis对象，注意由于bindingTable要做归并，所以不能直接从switchCur获得，而应该使用之前
	 * ProcessBindingTableDate函数返回的bTableHisList。
	 */
	private void saveNewInforation(Switchbasicinfo switchBasicInfo,Switchcur switchCur,List<Savibindingtablehis> bTableHisList,Long timeStamp){
		SwitchHisDao switchHisDao=new SwitchHisDao();
		Switchhis switchHis=new Switchhis();
		switchHis.setSwitchbasicinfo(switchBasicInfo);
		switchHis.setIpVersion(switchCur.getIpVersion());
		switchHis.setSystemMode(switchCur.getSystemMode());
		switchHis.setMaxDadDelay(switchCur.getMaxDadDelay());
		switchHis.setMaxDadPrepareDelay(switchCur.getMaxDadPrepareDelay());
		switchHis.setTimeStamp(timeStamp);
		switchHis.setStaticNum(switchCur.getStaticNum());
		switchHis.setSlaacNum(switchCur.getSlaacNum());
		switchHis.setDhcpNum(switchCur.getDhcpNum());
		Set<Ifinterfacehis> ifInterfaceHisSet=new LinkedHashSet<Ifinterfacehis>();
		Iterator<Ifinterfacecur> interfaceIterator=switchCur.getIfInterfaceCurs().iterator();
		while(interfaceIterator.hasNext()){
			Ifinterfacecur ifInterfaceCur=interfaceIterator.next();
			if(ifInterfaceCur.getSaviBindingTableCurs()!=null&&!ifInterfaceCur.getSaviBindingTableCurs().isEmpty()){
				Ifinterfacehis ifinterfaceHis=new Ifinterfacehis();
				ifinterfaceHis.setSwitchhis(switchHis);
				ifinterfaceHis.setIpVersion(ifInterfaceCur.getIpVersion());
				ifinterfaceHis.setIfIndex(ifInterfaceCur.getIfIndex());
				ifinterfaceHis.setIfValidationStatus(ifInterfaceCur.getIfValidationStatus());
				ifinterfaceHis.setIfTrustStatus(ifInterfaceCur.getIfTrustStatus());
				ifinterfaceHis.setIfFilteringNum(ifInterfaceCur.getIfFilteringNum());
				if(saveBindingTableHis(ifinterfaceHis,bTableHisList)){
					ifInterfaceHisSet.add(ifinterfaceHis);
				}
			}
		}
		switchHis.setIfinterfacehises(ifInterfaceHisSet);
		if(ifInterfaceHisSet.size()!=0){
			switchHisDao.saveSwitchHisInfo(switchHis);
		}
		//获得统计数据使用，正常删掉
		synchronized(Constants.lock4){
			UserDao userDao=new UserDao();
			User user=userDao.getUser();
			if(user!=null){
				int oldNum=0;
				if(user.getStatus()!=null){
					oldNum=user.getStatus();
				}
				int addNum=0;
				if(bTableHisList!=null){
					addNum=bTableHisList.size();
				}
				int totalNum=oldNum+addNum;
				user.setStatus(totalNum);
				userDao.update(user);
			}else{
				User user2=new User();
				user2.setStatus(0);
				userDao.update(user2);
			}
		}
	}
	//该函数的作用是将所有新生成的bingTableHis项与其对应的ifinterfaceHis项相关联
	private boolean saveBindingTableHis(Ifinterfacehis ifinterfaceHis,List<Savibindingtablehis> bTableHisList){
		if(bTableHisList!=null){
			Set<Savibindingtablehis> bTableHisSet=new LinkedHashSet<Savibindingtablehis>();
			int flag=0;
			for(int i=0;i<bTableHisList.size();i++){
				//注意这里判断相等时必须使用Integer.intValue()
				if(ifinterfaceHis.getIfIndex().intValue()==bTableHisList.get(i).getIfIndex().intValue()){
					bTableHisList.get(i).setIfinterfacehis(ifinterfaceHis);
					bTableHisSet.add(bTableHisList.get(i));
					flag=1;
				}
			}
			if(flag==1){
				ifinterfaceHis.setSavibindingtablehises(bTableHisSet);
				return true;
			}
			return false;
		}
		return false;
	}
	/* 该函数的输入bindingTableHisList是符合某个switchBasicInfo，且符合某个ipVersion，且符合状态为在线（status=1）的所有Savibindingtablehis项
	 * 的列表；bindingTableCurList是符合某个switchBasicInfo，且符合某个ipVersion（与bindingTableHisList符合条件一致）的
	 * 所有bindingTableCurList项列表，该函数的作用是比较上两个列表中的所有元素，做相应的处理，最后返回的是所有应该新创建的Savibindingtablehis
	 * 项的列表，该列表中的元素的属性ifinterfacehis还没有对应的值，需要以后赋值。
	 */
	
	private List<Savibindingtablehis> ProcessBindingTableDate(List<Savibindingtablehis> bindingTableHisList,List<Savibindingtablecur> bindingTableCurList,Long time){
		List<Savibindingtablehis> newBindingTableHisList=new ArrayList<Savibindingtablehis>();
		SavibindingtablehisDao bTableHisDao=new SavibindingtablehisDao();
		if(bindingTableHisList==null&&bindingTableCurList==null)return null;
		/*
		 * timeoutFlag是为了保证：当本次操作中只要有一次向认证服务器请求用户名超时的话，之后的获取用户名操作全都停止，只设置用户名为空即可。
		 */
		ArrayList<Integer> timeoutFlag=new ArrayList<Integer>();
		timeoutFlag.add(0);
		/*
		 * 如果这两个列表都不为空，那么我们需要判断在CurList中的项哪些需要新添加到bindingTableHis表中,
		 * HisList中的项哪些需要将状态做相应的改变。
		 */
		if(bindingTableHisList.size()!=0&&bindingTableCurList.size()!=0){
			for(int i=0;i<bindingTableHisList.size();i++){
				int flag=0;
				Savibindingtablehis bTableHis=bindingTableHisList.get(i);
				if(bTableHis!=null){
					for(int j=0;j<bindingTableCurList.size();j++){
						Savibindingtablecur bTableCur=bindingTableCurList.get(j);
						//System.out.println("历史IP："+ bTableHis.getIpAddress() + "  当前IP:" + bTableCur.getIpAddress());
						//System.out.println("历史mac："+ bTableHis.getMacAddress() + "  当前mac:" + bTableCur.getMacAddress());
						//System.out.println("历史端口号："+ bTableHis.getIfIndex().intValue() + "  当前端口号:" + bTableCur.getIfIndex().intValue());
						if(bTableHis.getIpAddress().equals(bTableCur.getIpAddress())&&
								bTableHis.getMacAddress().equals(bTableCur.getMacAddress())&&
								bTableHis.getIfIndex().intValue()==bTableCur.getIfIndex().intValue()){
							//每次判断一下his的绑定表中该项的用户名是否为空，如果为空则赋予cur表中的用户名
							//原因是有可能用户刚开始上线时没有认证，此时为空，等过一会他又认证了。
							//只有用户名为空同时ip地址不是本地链路地址才会发请求用户名。
							
							if(bTableHis.getUserName()==null){
								//System.out.println("userName"+ bTableHis.getUserName());
								bTableHis.setUserName("");
								bTableHisDao.update(bTableHis);
								//System.out.println("btablehis:username:"+ bTableHis.getUserName());
							}else if(bTableHis.getUserName().equals("")&&
									!bTableCur.getIpAddress().startsWith("fe80")){
								//System.out.println("userNamea"+ bTableHis.getUserName());
								//System.out.println("ip"+ bTableCur.getIpAddress());
								if(timeoutFlag.get(0)==0){
									System.out.println("bTableCur ip mac ifindex" + bTableCur.getIpAddress() + "      " +bTableCur.getMacAddress() + "      " + bTableCur.getIfIndex());
									List<OnlineInfo> onlineList = WSUtil.getSaviUserNameByIp(bTableCur.getIpAddress(),bTableCur.getMacAddress(),bTableCur.getIfIndex(), ipv4address, timeoutFlag);
									
									if(null != onlineList && onlineList.size() > 0){
										System.out.println("username数：" + onlineList.size());
										bTableHis.setUserName(onlineList.get(0).getLogin());
									}
									//bTableHis.setUserName(WSUtil.getUserName(bTableCur.getIpAddress(),timeoutFlag));
								}else{
									bTableHis.setUserName("");
								}
								bTableHisDao.update(bTableHis);
							}
							//bTableHisDao.update(bTableHis);
							flag=1;
							break;
						}
					}
					if(flag==0){
						/*
						 * 说明此bindingTablehis项在这个时刻有没有任何bindingTablecur项相对应，即此his项对应的用户已经下线。
						 * 因此记录这个his项的结束时间，同时标记status为0，说明该用户已经离线。
						 * 此处可能会造成某些误差，因为SNMP是不可靠的，如果此次获得cur的操作失败，那么该交换机的所有用户都会被标记成
						 * 已经下线。
						 */
						Date d = new Date();
						bTableHis.setEndTime(d.getTime());
						bTableHis.setStatus(0);
						bTableHisDao.update(bTableHis);
						//根据IP,MAC设置ENDTIME
						System.out.println("bTableHis ip mac endTime" + bTableHis.getIpAddress() + "    " + bTableHis.getMacAddress() + "     " + bTableHis.getEndTime());
						String ifSuccString = WSUtil.setEndTime(bTableHis.getIpAddress(),bTableHis.getMacAddress(),bTableHis.getEndTime(),new ArrayList<Integer>());
						if(ifSuccString.equals("1")){
							System.out.println("成功更新ip = " + bTableHis.getIpAddress() +"  getEndTime=" + bTableHis.getEndTime());
						}
					}else{
						//说明此bindingTablehis项在这个时刻有一个bindingTablecur项相对应，即此his项对应的用户还没有下线。
						flag=0;
					}
				}
			}
			
			for(int i=0;i<bindingTableCurList.size();i++){
				int flag=0;
				Savibindingtablecur bTableCur=bindingTableCurList.get(i);
				if(bTableCur!=null){
					for(int j=0;j<bindingTableHisList.size();j++){
						
						Savibindingtablehis bTableHis=bindingTableHisList.get(j);
						//System.out.println("new历史IP："+ bTableHis.getIpAddress() + "  当前IP:" + bTableCur.getIpAddress());
						//System.out.println("new历史mac："+ bTableHis.getMacAddress() + "  当前mac:" + bTableCur.getMacAddress());
						//System.out.println("new历史端口号："+ bTableHis.getIfIndex().intValue() + "  当前端口号:" + bTableCur.getIfIndex().intValue());
						if(bTableCur.getIpAddress().equals(bTableHis.getIpAddress())&&
								bTableCur.getMacAddress().equals(bTableHis.getMacAddress())&&
								bTableCur.getIfIndex().intValue()==bTableHis.getIfIndex().intValue()){
							flag=1;
							break;
						}
					}
					if(flag==0){
						/*
						 * 说明此bindingTablecur项在这个时刻有没有任何bindingTablehis项相对应，即此cur项对应的是一个新用户。
						 * 因此创建一个与该cur项对应的Savibindingtablehis持久化类实例，并赋值。
						 * 此处可能会造成某些误差，因为SNMP是不可靠的，如果上次获得cur的操作失败，那么该交换机的在线用户会被标记
						 * 成为一个新用户。
						 */
						Savibindingtablehis newBTableHis=new Savibindingtablehis();
						newBTableHis.setIpAddressType(bTableCur.getIpAddressType());
						newBTableHis.setBindingType(bTableCur.getBindingType());
						newBTableHis.setIfIndex(bTableCur.getIfIndex());
						newBTableHis.setIpAddress(bTableCur.getIpAddress());
						if(!bTableCur.getIpAddress().startsWith("fe80")){
							//System.out.println("new username"+ newBTableHis.getUserName());
							//System.out.println("new ip"+ bTableCur.getIpAddress());
							if(timeoutFlag.get(0)==0){
								System.out.println("bTableCur ip mac ifindex" + bTableCur.getIpAddress() + "      " +bTableCur.getMacAddress() + "      " + bTableCur.getIfIndex());
								List<OnlineInfo> onlineList = WSUtil.getSaviUserNameByIp(bTableCur.getIpAddress(),bTableCur.getMacAddress(),bTableCur.getIfIndex(), ipv4address, timeoutFlag);
								if(null != onlineList && onlineList.size() > 0){
									
									newBTableHis.setUserName(onlineList.get(0).getLogin());
								}
								//newBTableHis.setUserName(WSUtil.getUserName(bTableCur.getIpAddress(),timeoutFlag));
							}else{
								//System.out.println("new userName"+ newBTableHis.getUserName());
								newBTableHis.setUserName("");
							}
						}else{
							newBTableHis.setUserName("");
						}
						newBTableHis.setMacAddress(bTableCur.getMacAddress());
						newBTableHis.setStartTime(time);
						newBTableHis.setStatus(1);
						newBTableHis.setIsInFilteringTable(bTableCur.getIsInFilteringTable());
						//将新创建的持久化对象放入列表，此列表在该函数结束时返回。
						newBindingTableHisList.add(newBTableHis);
					}else{
						//说明此bindingTablecur项在这个时刻有一个bindingTablehis项相对应，即此cur项对应的用户还没有下线。
						flag=0;
					}
				}
			}	
		}else if(bindingTableHisList.size()!=0&&bindingTableCurList.size()==0){
			/*
			 * 如果CurList为空，说明本次轮询的交换机下没有任何用户，此时需要把bindingTableHisList对应的记录都标识为下线
			 */
			for(int i=0;i<bindingTableHisList.size();i++){
				Savibindingtablehis bTableHis=bindingTableHisList.get(i);
				if(bTableHis!=null){
					Date d = new Date();
					bTableHis.setEndTime(d.getTime());
					bTableHis.setStatus(0);
					bTableHisDao.update(bTableHis);
					System.out.println("bTableHis ip mac endTime" + bTableHis.getIpAddress() + "    " + bTableHis.getMacAddress() + "     " + bTableHis.getEndTime());
					String ifSuccString = WSUtil.setEndTime(bTableHis.getIpAddress(),bTableHis.getMacAddress(),bTableHis.getEndTime(),new ArrayList<Integer>());
					if(ifSuccString.equals("1")){
						System.out.println("成功更新ip = " + bTableHis.getIpAddress() +"  getEndTime=" + bTableHis.getEndTime());
					}
				}
			}
			newBindingTableHisList=null;
		}else if(bindingTableHisList.size()==0&&bindingTableCurList.size()!=0){
			/*
			 * 如果HisList为空，表示之前一个周期该交换机下没有在线的用户，而如果CurList不为空的话，我们需要把所有的
			 * 在bindingTableCurList列表中的元素都转化成相应的Savibindingtablehis类实例，并存入bindingTableHis数据库中
			 */
			for(int i=0;i<bindingTableCurList.size();i++){
				Savibindingtablecur bTableCur=bindingTableCurList.get(i);
				if(bTableCur!=null){
					Savibindingtablehis newBTableHis=new Savibindingtablehis();
					newBTableHis.setIpAddressType(bTableCur.getIpAddressType());
					newBTableHis.setBindingType(bTableCur.getBindingType());
					newBTableHis.setIfIndex(bTableCur.getIfIndex());
					if(!bTableCur.getIpAddress().startsWith("fe80")){
						if(timeoutFlag.get(0)==0){
							System.out.println("bTableCur ip mac ifindex" + bTableCur.getIpAddress() + "      " +bTableCur.getMacAddress() + "      " + bTableCur.getIfIndex());
							List<OnlineInfo> onlineList = WSUtil.getSaviUserNameByIp(bTableCur.getIpAddress(),bTableCur.getMacAddress(),bTableCur.getIfIndex(), ipv4address, timeoutFlag);
							if(null != onlineList && onlineList.size() > 0){
								newBTableHis.setUserName(onlineList.get(0).getLogin());
							}
							//newBTableHis.setUserName(WSUtil.getUserName(bTableCur.getIpAddress(),timeoutFlag));
						}else{
							newBTableHis.setUserName("");
						}
					}else{
						newBTableHis.setUserName("");
					}
					newBTableHis.setIpAddress(bTableCur.getIpAddress());
					newBTableHis.setMacAddress(bTableCur.getMacAddress());
					newBTableHis.setStartTime(time);
					newBTableHis.setStatus(1);
					newBTableHis.setIsInFilteringTable(bTableCur.getIsInFilteringTable());
					//将新创建的持久化对象放入列表，此列表在该函数结束时返回。
					newBindingTableHisList.add(newBTableHis);
				}
			}
		}else if(bindingTableHisList.size()==0&&bindingTableCurList.size()==0){
			newBindingTableHisList=null;
		}
		return newBindingTableHisList;
	}
	/*
	 * 该函数是获得与给定switchCur的所有Savibindingtablecur项，然后以列表形式返回
	 */
	private List<Savibindingtablecur> getBindingTableCurList(Switchcur switchCur){
		List<Savibindingtablecur> bindingTableCurList=new ArrayList<Savibindingtablecur>();
		Iterator<Ifinterfacecur> interfaceIterator=switchCur.getIfInterfaceCurs().iterator();
		while(interfaceIterator.hasNext()){
			Ifinterfacecur ifInterfaceCur=interfaceIterator.next();
			Iterator<Savibindingtablecur> bindingTableIterator=ifInterfaceCur.getSaviBindingTableCurs().iterator();
			while(bindingTableIterator.hasNext()){
				bindingTableCurList.add(bindingTableIterator.next());
			}
		}
		return bindingTableCurList;
	}
	
	public Long getSwitchBasicInfoId() {
		return switchBasicInfoId;
	}
	public void setSwitchBasicInfoId(Long switchBasicInfoId) {
		this.switchBasicInfoId = switchBasicInfoId;
	}
}
