package com.savi.show.action;

import java.util.LinkedList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.savi.base.model.Savibindingtablecur;
import com.savi.base.model.Switchcur;
import com.savi.show.chart.BindingTableRecordNumChart1;
import com.savi.show.chart.FilteringTableRecordNumChart1;
import com.savi.show.chart.IfTrustNumMeterChart;
import com.savi.show.chart.IfValidationNumMeterChart;
import com.savi.show.dao.SwitchDao;
import com.savi.show.dto.BindingTableInfo;
import com.savi.show.dto.InterfaceInfo;
import com.savi.show.dto.PreUserInfo;
import com.savi.show.dto.SwitchInfo;
import com.savi.show.dto.SwitchInfoForDetail;
import com.savi.show.dto.SwitchcurTemp;
import com.savi.show.dto.UserInfo;

@SuppressWarnings("serial")
public class SwitchShowAction extends BaseAction {
	private SwitchDao switchDao = new SwitchDao();

	private List<SwitchInfo> switchList;
	private List<SwitchcurTemp> saviList;

	private String ids;
	private String sids;
	private String switchcurId;
	private Integer ipVersion;
	private Long switchbasicinfoId;
	private Long subnetId=null;

	private List<InterfaceInfo> interfaceList;
	private List<Savibindingtablecur> savibindingList;

	private SwitchInfoForDetail switchInfoForDetail;
	private List<UserInfo> userInfoList;// 交换机用户变化信息
	private List<BindingTableInfo> bindingTableInfoList;// 交换机绑定表信息

	private int totalCount;
	private String start;
	private String limit;

	@JSON(serialize = false)
	public String listShowSwitches() throws Exception {
		if (sids == null||sids.equals("")) {
			if(subnetId!=null){
				String[] subnetIds =new String[1];
				subnetIds[0]=subnetId.toString();
				switchList = switchDao.getExistSwitchesBySubnetIds(subnetIds, start, limit);
				totalCount = switchDao.getExistSwitchesBySubnetIdsCount(subnetIds);
			}else{
				switchList = switchDao.getAllExistSwitches(start, limit);
				totalCount = switchDao.getExistSwitchesCount();
			}
			return SUCCESS;
		}
		String[] subnetIds = sids.split("\\|");
		switchList=switchDao.getExistSwitchesBySubnetIds(subnetIds,start,limit);
		totalCount=switchDao.getExistSwitchesBySubnetIdsCount(subnetIds);
		return SUCCESS;
	}

	@JSON(serialize = false)
	public String listSaviTable() throws Exception {
		if (sids == null||sids.equals("")) {
			if(subnetId!=null){
				String[] subnetIds =new String[1];
				subnetIds[0]=subnetId.toString();
				saviList = switchDao.getSavisBySubnetIds(subnetIds, start, limit);
				totalCount = switchDao.getSavisBySubnetIdsCount(subnetIds);
				return SUCCESS;
			}
			saviList = switchDao.getSwitchcurs(start, limit);
			totalCount = switchDao.getSwitchcursCount();
			return SUCCESS;
		}
		String[] subnetIds = sids.split("\\|");
		saviList=switchDao.getSavisBySubnetIds(subnetIds,start,limit);
		totalCount=switchDao.getSavisBySubnetIdsCount(subnetIds);
		return SUCCESS;
	}

	@JSON(serialize = false)
	public String listInterfaces() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(ipVersion, switchbasicinfoId);
		while(switchcur==null){
			switchcur =switchDao.getSwitchcurByIPVersionAndSwitchId(ipVersion, switchbasicinfoId);
			Thread.sleep(300);
		}
		interfaceList = switchDao.getSwitchInterfaces(switchcur.getId(), start, limit);
		totalCount = switchDao.getSwitchInterfacesCount(switchcur.getId());
		return SUCCESS;
	}

	@JSON(serialize = false)
	public String listShowSwitchcur() throws Exception {
		switchInfoForDetail = switchDao.getSwitchcurTempByIPVersionAndSwitchId(ipVersion, switchbasicinfoId);
		return SUCCESS;
	}

	@JSON(serialize = false)
	public String listUserChangeInfo() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(ipVersion, switchbasicinfoId);
		while(switchcur==null){
			switchcur =switchDao.getSwitchcurByIPVersionAndSwitchId(ipVersion, switchbasicinfoId);
			Thread.sleep(300);
		}
		List<PreUserInfo> returnList=switchDao.getUserChangeInfo(switchcur.getId(), start, limit);
		List<UserInfo> userInfoListTotal = new LinkedList<UserInfo>();
		for(int i = 0; i < returnList.size(); i++){
			//User user = (User) list.get(i);
			PreUserInfo user =  returnList.get(i);
			if(user.getStatus()==1){
				UserInfo userInfo = new UserInfo(user,1);
				userInfoListTotal.add(userInfo);
			}else {
				UserInfo userInfo1 = new UserInfo(user,1);
				UserInfo userInfo2 = new UserInfo(user,2);
				userInfoListTotal.add(userInfo1);
				userInfoListTotal.add(userInfo2);
			}
		}
		//orderByTime(userInfoListTotal);
		userInfoList = new LinkedList<UserInfo>();
		for(int i=Integer.parseInt(start);i<Integer.parseInt(start)+Integer.parseInt(limit);i++){
			if(i>=userInfoListTotal.size())break;
			userInfoList.add(userInfoListTotal.get(i));
		}
		totalCount = userInfoListTotal.size();
		return SUCCESS;
	}
	/*
	private void orderByTime(List<UserInfo> showList){
		for(int j=1;j<showList.size(); j++){
			UserInfo key=showList.get(j);
			int i=j-1;
			while(i>=0&&showList.get(i).getPrivateTime()<key.getPrivateTime()){
				showList.set(i+1,showList.get(i));
				i--;
			}
			showList.set(i+1,key);
		}
	}	
	*/
	@JSON(serialize = false)
	public String listBindingTableInfo() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(ipVersion, switchbasicinfoId);
		while(switchcur==null){
			switchcur =switchDao.getSwitchcurByIPVersionAndSwitchId(ipVersion, switchbasicinfoId);
			Thread.sleep(300);
		}
		bindingTableInfoList = switchDao.getBindingTableInfo(switchcur.getId(),
				start, limit);
		totalCount = switchDao.getBindingTableInfoNum(switchcur.getId());
		return SUCCESS;
	}

	@JSON(serialize = false)
	public String batchGetSwitch() throws Exception {
		String[] idArr = ids.split("\\|");
		switchList = switchDao.batchGetSwitchbasicinfo(idArr);
		totalCount = switchList.size();
		return SUCCESS;
	}
	
	@JSON(serialize = false)
	public String batchGetSAVI() throws Exception {
		String[] idArr = ids.split("\\|");
		saviList = switchDao.batchGetSwitchcur(idArr);
		totalCount = saviList.size();
		return SUCCESS;
	}
	
	public String genMeterChart() throws Exception {
		Switchcur switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(ipVersion, switchbasicinfoId);
		while(switchcur==null){
			Thread.sleep(300);
			switchcur = switchDao.getSwitchcurByIPVersionAndSwitchId(
					ipVersion, switchbasicinfoId);
		}
		IfValidationNumMeterChart validationchart= new IfValidationNumMeterChart(switchcur.getId());
		validationchart.createChart(ipVersion, switchbasicinfoId,getText("SwitchShowAction.validationchart"));
		IfTrustNumMeterChart trustchart= new IfTrustNumMeterChart(switchcur.getId());
		trustchart.createChart(ipVersion, switchbasicinfoId,getText("SwitchShowAction.trustchart"));
		FilteringTableRecordNumChart1 filterchart= new FilteringTableRecordNumChart1(switchcur.getId());
		filterchart.createChart(ipVersion, switchbasicinfoId,getText("SwitchShowAction.filterchart"));
		BindingTableRecordNumChart1 bindingchart= new BindingTableRecordNumChart1(switchcur.getId());
		bindingchart.createChart(ipVersion, switchbasicinfoId,getText("SwitchShowAction.bindingchart"));
		return SUCCESS;
	}
	
	public List<UserInfo> getUserInfoList() {
		return userInfoList;
	}

	public void setUserInfoList(List<UserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}

	public List<BindingTableInfo> getBindingTableInfoList() {
		return bindingTableInfoList;
	}

	public void setBindingTableInfoList(
			List<BindingTableInfo> bindingTableInfoList) {
		this.bindingTableInfoList = bindingTableInfoList;
	}

	public List<com.savi.show.dto.SwitchInfo> getSwitchList() {
		return switchList;
	}

	public void setSwitchList(List<com.savi.show.dto.SwitchInfo> switchList) {
		this.switchList = switchList;
	}

	public List<SwitchcurTemp> getSaviList() {
		return saviList;
	}

	public void setSaviList(List<SwitchcurTemp> saviList) {
		this.saviList = saviList;
	}

	public List<InterfaceInfo> getInterfaceList() {
		return interfaceList;
	}

	public void setInterfaceList(List<InterfaceInfo> interfaceList) {
		this.interfaceList = interfaceList;
	}

	public List<Savibindingtablecur> getSavibindingList() {
		return savibindingList;
	}

	public void setSavibindingList(List<Savibindingtablecur> savibindingList) {
		this.savibindingList = savibindingList;
	}

	public String getSwitchcurId() {
		return switchcurId;
	}

	public void setSwitchcurId(String switchcurId) {
		this.switchcurId = switchcurId;
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

	public Integer getIpVersion() {
		return ipVersion;
	}

	public void setIpVersion(Integer ipVersion) {
		this.ipVersion = ipVersion;
	}

	public Long getSwitchbasicinfoId() {
		return switchbasicinfoId;
	}

	public void setSwitchbasicinfoId(Long switchbasicinfoId) {
		this.switchbasicinfoId = switchbasicinfoId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getSids() {
		return sids;
	}

	public void setSids(String sids) {
		this.sids = sids;
	}
	public Long getSubnetId() {
		return subnetId;
	}

	public void setSubnetId(Long subnetId) {
		this.subnetId = subnetId;
	}
	public SwitchInfoForDetail getSwitchInfoForDetail() {
		return switchInfoForDetail;
	}

	public void setSwitchInfoForDetail(SwitchInfoForDetail switchInfoForDetail) {
		this.switchInfoForDetail = switchInfoForDetail;
	}

}
