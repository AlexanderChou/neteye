package com.savi.show.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import org.apache.struts2.json.annotations.JSON;
import org.jfree.chart.JFreeChart;

import com.fault.dao.FaultListDao;
import com.fault.dto.FaultNode;
import com.fault.dto.FaultNodeList;
import com.savi.base.model.Apinfo;
import com.savi.base.model.Deviceinfo;
import com.savi.base.model.SaviFilterTableCur;
import com.savi.base.model.Subnet;
import com.savi.base.model.Switchbasicinfo;
import com.savi.base.model.Switchcur;
import com.savi.collection.util.CollectionFactory;
import com.savi.show.chart.ACUserNumChart;
import com.savi.show.chart.BindingTableRecordNumChart1;
import com.savi.show.chart.FilteringTableRecordNumChart1;
import com.savi.show.chart.IfTrustNumMeterChart;
import com.savi.show.chart.IfValidationNumMeterChart;
import com.savi.show.chart.SwitchUserNumChart;
import com.savi.show.dao.ApinfoDao;
import com.savi.show.dao.DeviceDao;
import com.savi.show.dao.FilterCountCurDao;
import com.savi.show.dao.SaviFilterTableCurDao;
import com.savi.show.dto.AcApList;
import com.savi.show.dto.AcForGlobalView;
import com.savi.show.dto.Ap;
import com.savi.show.dto.ApForGlobalView;
import com.savi.show.dto.BindingTableInfo;
import com.savi.show.dto.HuaSanBindingTableInfo;
import com.savi.show.dto.HuaSanUserInfo;
import com.savi.show.dto.PreHuaSanUserInfo;
import com.savi.show.dto.PreUserInfo;
import com.savi.show.dto.SubnetForGlobalView;
import com.savi.show.dto.UserInfo;
import com.savi.base.util.Constants;

@SuppressWarnings("serial")
public class HuaSanAction  extends BaseAction{
	private static final long serialVersionUID = 1L;
	private  List<AcApList> acApLists;
	private Integer count;
	private String start;
	private String limit;
	private boolean success;
	private boolean failure;
	private Deviceinfo deviceinfo;
	
	private String errMsg = "";
	private AcForGlobalView ac;
	private String acId;
	private JFreeChart chart;
	private List<HuaSanUserInfo> userInfoList;
	private List<HuaSanBindingTableInfo> bindingTableInfoList;
	private int totalCount;
	private Apinfo ap;
	private String apid;
	
	private List<Apinfo> aplist;
	
    
	public String getViewList() throws Exception{
		 
	     return SUCCESS;
	}
	public String collectionTaskExcu(){
		initParams();
		
	     //取出所有iddelete为0的AC列表
			try {
				DeviceDao deviceInfoDao=new DeviceDao();
				List<Deviceinfo> deviceInfoList=deviceInfoDao.getDeviceInfoList(0);
				if(deviceInfoList != null){
					for(int j = 0;j<deviceInfoList.size();j++){
						CollectionFactory.beginTaskExcu(deviceInfoList.get(j));
					}
				}
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
		
	}

	public String getApiStutas() throws Exception{
		
		 Set<AcApList> faultstmp =new HashSet<AcApList>(); 
		
		 DeviceDao device = new DeviceDao();
		 List deviceinfo=device.batchGetDeviceinfo(null);
		 for(int i=0;i<deviceinfo.size();i++){
			 int usernum=0;
			 Deviceinfo info=(Deviceinfo) deviceinfo.get(i);
			 AcApList acaptemp=new AcApList();
			 acaptemp.setId(info.getId());
			 acaptemp.setName(info.getName());
			 acaptemp.setStatus(info.getStatus().toString());
			 List<Apinfo> Apinfotmp =new ArrayList<Apinfo>();
			 ApinfoDao apinfo=new ApinfoDao();
			 List<Apinfo> Apinfotmpnew=apinfo.getApinfoList(info.getId());
			 int sum=0;
			 for(Apinfo ap:Apinfotmpnew){
				 SaviFilterTableCurDao savi=new SaviFilterTableCurDao();
				 Integer apid=ap.getApid();
				 List list=savi.getApinfoSizeByapId(apid);
				 sum=list.size();
				 ap.setUsernum(sum+"");
				 usernum+=sum;
				 Apinfotmp.add(ap);
			 }
			 
			 FilterCountCurDao curcountdao=new FilterCountCurDao();
			 Integer listcount=curcountdao.getApinfoList(info.getId());
			 
			 String infocount=Apinfotmp.size()+"";
			 acaptemp.setAplist(Apinfotmp);
			 //acaptemp.setNodecount(infocount);
			 acaptemp.setNodecount(usernum+"");
			 acaptemp.setFiltercount(listcount+"");
			 faultstmp.add(acaptemp);
		 }
		 acApLists= new ArrayList<AcApList>(faultstmp);
		 count=acApLists.size();
		 System.out.println(acApLists+"::::"+count);
		 
	     return SUCCESS;

		}

	@JSON(serialize=false)
	public String listShowHuaSanSubnet() throws Exception {
//		subnetList = subnetDao.listSubnets(start, limit);
//		totalCount = subnetDao.getSubnetsCount();
		return SUCCESS;
	}
	
	// 添加或更新交换机
	@JSON(serialize = false)
	public String saveDeviceinfo() throws Exception {
		success = false;
		failure = true;
		DeviceDao device = new DeviceDao();
        if(deviceinfo!=null){
        	deviceinfo.setStatus(0);
        	deviceinfo.setIsDelete(0);
        	device.saveDeviceinfo(deviceinfo);
        }
		CollectionFactory.resgisterApInfoForPoll(deviceinfo);
		success = true;
		failure = false;

		return SUCCESS;
	}
	
	public String getPaList() throws Exception{
		ApinfoDao apinfo=new ApinfoDao();
		List<Apinfo> list=apinfo.getALLpA();
		
		aplist =new ArrayList<Apinfo>();
		
		 int sum=0;
		 for(Apinfo ap:list){
			 SaviFilterTableCurDao savi=new SaviFilterTableCurDao();
			 Integer apid=ap.getApid();
			 List lists=savi.getApinfoSizeByapId(apid);
			 sum=lists.size();
			 ap.setUsernum(sum+"");
			 aplist.add(ap);
		 }
		 
		return SUCCESS;
	}
	
	@JSON(serialize=false)
	public String listAC() throws Exception{
		AcForGlobalView acForGlobalView=new AcForGlobalView();
		int userNum=0;
		DeviceDao device=new DeviceDao();
		Deviceinfo info=device.getAcView(acId);
		acForGlobalView=new AcForGlobalView();
		acForGlobalView.setId(info.getId());
		acForGlobalView.setName(info.getName());
		List<ApForGlobalView> apForGlobalViewList=new ArrayList<ApForGlobalView>();
		//Iterator<Apinfo> apinfoIterator=device.getAcForGlobalView(acId);
		
		 ApinfoDao apinfo=new ApinfoDao();
		 List<Apinfo> Apinfotmpnew=apinfo.getApinfoList(info.getId());
		 int sum=0;
		 
		 for(Apinfo ap:Apinfotmpnew){
			 ApForGlobalView apForGlobalView=new ApForGlobalView();
			 SaviFilterTableCurDao savi=new SaviFilterTableCurDao();
			List list=savi.getApinfoSizeByapId(ap.getApid());
			Integer count=list.size();
			apForGlobalView.setApid(ap.getApid());
			apForGlobalView.setApname(ap.getApname());
			apForGlobalView.setUserNum(count);
			apForGlobalViewList.add(apForGlobalView);
			userNum+=count;
		 }
		
		SaviFilterTableCurDao savi=new SaviFilterTableCurDao();
//		while(apinfoIterator.hasNext()){
//		Apinfo apinfo=apinfoIterator.next();
//		if(info.getIsDelete()!=null&&info.getIsDelete().intValue()==1){
//			continue;
//		}
//		ApForGlobalView apForGlobalView=new ApForGlobalView();
//		List list=savi.getApinfoList(apinfo.getApid());
//		Integer count=list.size();
//		apForGlobalView.setApid(apinfo.getApid());
//		apForGlobalView.setApname(apinfo.getApname());
//		apForGlobalView.setUserNum(count);
//		apForGlobalViewList.add(apForGlobalView);
//		userNum+=count;
//		
//	    }
		acForGlobalView.setUserNum(userNum);
		acForGlobalView.setApForGlobalViewList(apForGlobalViewList);
		ac=acForGlobalView;
		System.out.println(ac);
		return SUCCESS;
	}
	//用户上下线24小时
	@JSON(serialize=false)
	public String listUserChangeInfo() throws Exception {
		
		DeviceDao device = new DeviceDao();
		
		List<PreHuaSanUserInfo> returnList=device.getUserChangeInfo(acId);
		
		List<HuaSanUserInfo> userInfoListTotal = new LinkedList<HuaSanUserInfo>();
		for(int i = 0; i < returnList.size(); i++){
			PreHuaSanUserInfo user =  returnList.get(i);
			if(user.getStatus()==1){
				HuaSanUserInfo userInfo = new HuaSanUserInfo(user,1);
				userInfoListTotal.add(userInfo);
			}else {
				HuaSanUserInfo userInfo1 = new HuaSanUserInfo(user,1);
				HuaSanUserInfo userInfo2 = new HuaSanUserInfo(user,2);
				userInfoListTotal.add(userInfo1);
				userInfoListTotal.add(userInfo2);
			}
		}
		userInfoList = new LinkedList<HuaSanUserInfo>();
		for(int i=Integer.parseInt(start);i<Integer.parseInt(start)+Integer.parseInt(limit);i++){
			if(i>=userInfoListTotal.size())break;
			userInfoList.add(userInfoListTotal.get(i));
		}
		totalCount = userInfoListTotal.size();
		
		return SUCCESS;
	}
	//PA
	//用户上下线24小时
	@JSON(serialize=false)
	public String listPAUserChangeInfo() throws Exception {
		
		DeviceDao device = new DeviceDao();
		
		List<PreHuaSanUserInfo> returnList=device.getPAUserChangeInfo(acId);
		
		List<HuaSanUserInfo> userInfoListTotal = new LinkedList<HuaSanUserInfo>();
		for(int i = 0; i < returnList.size(); i++){
			PreHuaSanUserInfo user =  returnList.get(i);
			if(user.getStatus()==1){
				HuaSanUserInfo userInfo = new HuaSanUserInfo(user,1);
				userInfoListTotal.add(userInfo);
			}else {
				HuaSanUserInfo userInfo1 = new HuaSanUserInfo(user,1);
				HuaSanUserInfo userInfo2 = new HuaSanUserInfo(user,2);
				userInfoListTotal.add(userInfo1);
				userInfoListTotal.add(userInfo2);
			}
		}
		userInfoList = new LinkedList<HuaSanUserInfo>();
		for(int i=Integer.parseInt(start);i<Integer.parseInt(start)+Integer.parseInt(limit);i++){
			if(i>=userInfoListTotal.size())break;
			userInfoList.add(userInfoListTotal.get(i));
		}
		totalCount = userInfoListTotal.size();
		
		return SUCCESS;
	}
	
	
	
	@JSON(serialize=false)
	public String listHuaSanBindingInfo() throws Exception {
		DeviceDao device = new DeviceDao();
		bindingTableInfoList = device.getHuaSanBindingTableInfo(acId, start, limit);
		System.out.println("ceeeee::::::::"+bindingTableInfoList);
		totalCount = bindingTableInfoList.size();
		return SUCCESS;
	}
	//PA
	@JSON(serialize=false)
	public String listHuaSanPABindingInfo() throws Exception {
		DeviceDao device = new DeviceDao();
		bindingTableInfoList = device.getHuaSanPABindingTableInfo(acId, start, limit);
		System.out.println("ceeeee::::::::"+bindingTableInfoList);
		totalCount = bindingTableInfoList.size();
		return SUCCESS;
	}
	//PA
	@JSON(serialize=false)
	public String listHuaSanAPBindingInfo() throws Exception {
		DeviceDao device = new DeviceDao();
		bindingTableInfoList = device.getHuaSanBindingTableInfo(acId, start, limit);
		System.out.println("ceeeee::::::::"+bindingTableInfoList);
		totalCount = bindingTableInfoList.size();
		return SUCCESS;
	}

	
	public String genMeterChart() throws Exception {
//		ApinfoDao apinfo=new ApinfoDao();
//		ap=apinfo.getApinfoByAcidIp(Long.parseLong(apid));
//		while(ap==null){
//			Thread.sleep(300);
//			ap =apinfo.getApinfoByAcidIp(Long.parseLong(apid));
//		}
//	
//		FilteringTableRecordNumChart1 filterchart= new FilteringTableRecordNumChart1(switchcur.getId());
//		filterchart.createChart(ipVersion, switchbasicinfoId,getText("SwitchShowAction.filterchart"));
//		
//		BindingTableRecordNumChart1 bindingchart= new BindingTableRecordNumChart1(switchcur.getId());
//		bindingchart.createChart(ipVersion, switchbasicinfoId,getText("SwitchShowAction.bindingchart"));
		return SUCCESS;
	}
	
	
	@JSON(serialize = false)
	public String listApinfo() throws Exception {
		ApinfoDao apinfo=new ApinfoDao();
		SaviFilterTableCurDao savi=new SaviFilterTableCurDao();
		ap=apinfo.getApinfoByAcidIp(Long.parseLong(apid));
		System.out.println(ap);
		List list=savi.getApinfoList(Long.parseLong(apid));
		count=list.size();
		return SUCCESS;
	}
	
	
	

	public List<Apinfo> getAplist() {
		return aplist;
	}

	public void setAplist(List<Apinfo> aplist) {
		this.aplist = aplist;
	}

	public List<AcApList> getAcApLists() {
		return acApLists;
	}

	public void setAcApLists(List<AcApList> acApLists) {
		this.acApLists = acApLists;
	}

	public Integer getCount() {
		return count;
	}



	public void setCount(Integer count) {
		this.count = count;
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

	public Deviceinfo getDeviceinfo() {
		return deviceinfo;
	}

	public void setDeviceinfo(Deviceinfo deviceinfo) {
		this.deviceinfo = deviceinfo;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public AcForGlobalView getAc() {
		return ac;
	}

	public void setAc(AcForGlobalView ac) {
		this.ac = ac;
	}

	public String getAcId() {
		return acId;
	}

	public void setAcId(String acId) {
		this.acId = acId;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	

	public List<HuaSanUserInfo> getUserInfoList() {
		return userInfoList;
	}

	public void setUserInfoList(List<HuaSanUserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<HuaSanBindingTableInfo> getBindingTableInfoList() {
		return bindingTableInfoList;
	}

	public void setBindingTableInfoList(
			List<HuaSanBindingTableInfo> bindingTableInfoList) {
		this.bindingTableInfoList = bindingTableInfoList;
	}

	

	public Apinfo getAp() {
		return ap;
	}

	public void setAp(Apinfo ap) {
		this.ap = ap;
	}

	public String getApid() {
		return apid;
	}

	public void setApid(String apid) {
		this.apid = apid;
	}
    
   






}
