package com.savi.show.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.savi.base.model.Deviceinfo;
import com.savi.base.model.SaviFilterTableCur;
import com.savi.base.model.Savibindingtablehis;
import com.savi.base.model.Switchbasicinfo;
import com.savi.base.model.Switchcur;
import com.savi.base.util.Constants;
import com.savi.cernet.soa.WSUtil;
import com.savi.collection.dao.SwitchBasicInfoDao;
import com.savi.collection.util.CollectionFactory;
import com.savi.collection.util.CollectionTask;
import com.savi.show.dao.DeviceDao;
import com.savi.show.dao.IfInterfaceDao;
import com.savi.show.dao.SaviFilterTableCurDao;
import com.savi.show.dao.SwitchDao;
import com.savi.show.dto.BindingTableInfo;
import com.savi.show.dto.FiveTableInfo;
import com.savi.show.dto.InterfaceInfo;
import com.savi.show.dto.OnlineInfo;
import com.savi.show.dto.SaviFilterTableCurInfo;

@SuppressWarnings("serial")
public class RealTimeAction extends BaseAction {
	private SwitchDao switchDao = new SwitchDao();
	private SwitchBasicInfoDao switchBasicInfoDao = new SwitchBasicInfoDao();
	private IfInterfaceDao interfaceDao = new IfInterfaceDao();

	private Long switchbasicinfoId;
	private Integer ipVersion;

	private Integer bindingType;
	private String ifIndex;

	private List<InterfaceInfo> interfaceList;// 交换机端口列表
	private List<BindingTableInfo> bindingTableInfoList;// 绑定表信息列表
	private List<FiveTableInfo> fiveInfoList;//五元组信息
    private List<OnlineInfo> onlineInfoList;
	
	private int totalCount;
	private String start;
	private String limit;

	private String errMsg = "";
	
	@JSON(serialize = false)
	public String listSwitchInterface() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
				ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		List<Integer> list = new LinkedList<Integer>();
		interfaceList = switchDao.getRealTimeInterfaceList(switchcur.getId(),
				start, limit, list);
		totalCount = list.get(0);
		return SUCCESS;
	}

	@JSON(serialize = false)
	public String listSwitchBindingTableInfo() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
				ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		List<Integer> list = new LinkedList<Integer>();
		bindingTableInfoList = switchDao.getRealTimeBindingTableInfoList(
				switchcur.getId(), start, limit, list);
		totalCount = list.get(0);
		return SUCCESS;
	}
	
	
	@JSON(serialize = false)
	public String listFiveInfo() throws Exception {
		System.out.println("listFiveInfo method begin");
		List<Integer> list = new LinkedList<Integer>();
		fiveInfoList = switchDao.getFiveInfoList(start, limit, list);
		totalCount = list.get(0);
		System.out.println("fiveInfoList size" + fiveInfoList.size());
		System.out.println("totalCount" + totalCount);
		
		return SUCCESS;
	}
	
	@JSON(serialize = false)
	public String refreshSwitchBindingTableInfo() throws Exception {
		initParams();
		//手动刷新
		CollectionTask collectionTask=new CollectionTask(switchbasicinfoId,new Long(0));
		collectionTask.run();
		collectionTask.run();
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
				ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		List<Integer> list = new LinkedList<Integer>();
		bindingTableInfoList = switchDao.getRealTimeBindingTableInfoList(
				switchcur.getId(), start, limit, list);
		totalCount = list.get(0);
		return SUCCESS;
	}
	
	@JSON(serialize = false)
	public String refreshBingdingInfo() throws Exception {
		
		initParams();
			try {
				//取出第一个交换机ID
				List<Switchbasicinfo> baseinfoList = new ArrayList<Switchbasicinfo>();
				baseinfoList = switchBasicInfoDao.getSwitchBasicInfoListByDel(new Integer(0));
				if(null != baseinfoList && baseinfoList.size() > 0){
					switchbasicinfoId = baseinfoList.get(0).getId();
					CollectionTask collectionTask=new CollectionTask(switchbasicinfoId,new Long(0));
					collectionTask.run();
					collectionTask.run();	
				}
				
				//手动刷新
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				setErrMsg("更新错误, 错误如下："+e.getMessage());
				return SUCCESS;
			}
			setErrMsg("更新成功，可以继续操作");
	     return SUCCESS;
	}
	
	private void initParams() {
		Constants.realPath = getRequest().getSession().getServletContext().getRealPath("/WEB-INF/");
		System.out.println(Constants.realPath );
	    Constants.webRealPath = getRequest().getSession().getServletContext().getRealPath("/");
	    System.out.println(Constants.webRealPath );
	     Constants.webRealPath = Constants.webRealPath.replace('\\', '/');
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
					// TODO Auto-generated catch block
					Constants.pollingInterval=300;
					Constants.debug=2;
					e.printStackTrace();
				}
	        }else{
	        	Constants.debug=2;
	        	Constants.pollingInterval=300;
	        }
	        System.out.println(" init Constants.webserviceURLWLAN" + Constants.webserviceURLWLAN);
		
	}
	
	@SuppressWarnings("unchecked")
	@JSON(serialize = false)
	public String wlanOnlineList() throws Exception {
		List<OnlineInfo> onlineInfoListnew = WSUtil.getOnlineList(new ArrayList<Integer>());
		int sta=Integer.parseInt(start);
		int end=Integer.parseInt(start)+Integer.parseInt(limit)-1;
		int counter=0;
		if(null != onlineInfoListnew && onlineInfoListnew.size() > 0){
			onlineInfoList = new ArrayList<OnlineInfo>();
			for(int i = 0;i< onlineInfoListnew.size();i++){
				if(counter >= sta&&counter <= end){
					onlineInfoList.add(onlineInfoListnew.get(i));
				}
				counter++;
			}
		}
		//onlineInfoList = WSUtil.getOnlineList(new ArrayList<Integer>());
		if(null != onlineInfoListnew){
			totalCount = onlineInfoListnew.size();
		}else{
			totalCount = 0;
		}
		//显示24小时以内的所有在线数据
		//List<Integer> list = new LinkedList<Integer>();
		//SaviFilterTableCurDao saviFilterTableCurDao = new SaviFilterTableCurDao();
		//onlineInfoList = saviFilterTableCurDao.getAllList(0, start, limit, list);
		//if(null != onlineInfoList){
		//	totalCount = list.get(0);
		//}
		return SUCCESS;
	}

	@JSON(serialize = false)
	public String listSwitchBindingTableInfoByType() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
				ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		List<Integer> list = new LinkedList<Integer>();
		bindingTableInfoList = switchDao.getRealTimeBindingTableInfoListByType(
				switchcur.getId(), bindingType, start, limit, list);
		totalCount = list.get(0);
		return SUCCESS;
	}

	@JSON(serialize = false)
	public String listInterfaceBindingTableInfo() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
				ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		List<Integer> list = new LinkedList<Integer>();
		bindingTableInfoList = interfaceDao.getRealTimeBindingTableInfoList(
				ifIndex, switchcur.getId(), start, limit, list);
		totalCount = list.get(0);
		return SUCCESS;
	}

	@JSON(serialize = false)
	public String listInterfaceBindingTableInfoByType() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
				ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		List<Integer> list = new LinkedList<Integer>();
		bindingTableInfoList = interfaceDao
				.getRealTimeBindingTableInfoListByType(ifIndex, bindingType,
						switchcur.getId(), start, limit, list);
		totalCount = list.get(0);
		return SUCCESS;
	}

	public String getIfIndex() {
		return ifIndex;
	}

	public void setIfIndex(String ifIndex) {
		this.ifIndex = ifIndex;
	}

	public List<InterfaceInfo> getInterfaceList() {
		return interfaceList;
	}

	public void setInterfaceList(List<InterfaceInfo> interfaceList) {
		this.interfaceList = interfaceList;
	}

	public List<BindingTableInfo> getBindingTableInfoList() {
		return bindingTableInfoList;
	}

	public void setBindingTableInfoList(
			List<BindingTableInfo> bindingTableInfoList) {
		this.bindingTableInfoList = bindingTableInfoList;
	}

	public Integer getBindingType() {
		return bindingType;
	}

	public void setBindingType(Integer bindingType) {
		this.bindingType = bindingType;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public Long getSwitchbasicinfoId() {
		return switchbasicinfoId;
	}

	public void setSwitchbasicinfoId(Long switchbasicinfoId) {
		this.switchbasicinfoId = switchbasicinfoId;
	}

	public Integer getIpVersion() {
		return ipVersion;
	}

	public void setIpVersion(Integer ipVersion) {
		this.ipVersion = ipVersion;
	}

	public List<OnlineInfo> getOnlineInfoList() {
		return onlineInfoList;
	}

	public void setOnlineInfoList(List<OnlineInfo> onlineInfoList) {
		this.onlineInfoList = onlineInfoList;
	}

	//public List<Savibindingtablehis> getFiveInfoList() {
	//	List<Integer> list = new LinkedList<Integer>();
	//	fiveInfoList = switchDao.getFiveInfoList(start, limit, list);
	//	totalCount = list.get(0);
		//this.setTotalCount(totalCount);
		//Savibindingtablehis savibindingtablehis = new Savibindingtablehis();
		//savibindingtablehis.setMacAddress("00000");
		//fiveInfoList.add(savibindingtablehis);
		//totalCount = 0;
	//	return fiveInfoList;
	//}

	//public void setFiveInfoList(List<Savibindingtablehis> fiveInfoList) {
	//	this.fiveInfoList = fiveInfoList;
	//}

	public String getErrMsg() {
		return errMsg;
	}

	public List<FiveTableInfo> getFiveInfoList() {
		return fiveInfoList;
	}

	public void setFiveInfoList(List<FiveTableInfo> fiveInfoList) {
		this.fiveInfoList = fiveInfoList;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	

	

	

	



}
