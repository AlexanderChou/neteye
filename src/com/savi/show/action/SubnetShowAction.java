package com.savi.show.action;


import java.util.LinkedList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.savi.show.dao.SubnetDao;
import com.savi.show.dto.BindingTableInfo;
import com.savi.show.dto.PreUserInfo;
import com.savi.show.dto.SubnetForGlobalView;
import com.savi.show.dto.SubnetTemp;
import com.savi.show.dto.UserInfo;

@SuppressWarnings("serial")
public class SubnetShowAction extends BaseAction {
	private SubnetDao subnetDao = new SubnetDao();

	private String subnetId;
	private SubnetForGlobalView subnet;
	private List<SubnetForGlobalView> subnetList;
	private List<SubnetTemp> subnetListForCombo;
	private List<UserInfo> userInfoList;
	private List<BindingTableInfo> bindingTableInfoList;
	private int totalCount;
	private String start;
	private String limit;

	@JSON(serialize=false)
	public String listShowSubnets() throws Exception {
		subnetList = subnetDao.listSubnets(start, limit);
		totalCount = subnetDao.getSubnetsCount();
		return SUCCESS;
	}
	@JSON(serialize=false)
	public String listShowSubnetsForCombo()throws Exception{
		subnetListForCombo = subnetDao.listSubnetsForCombo(start, limit);
		totalCount = subnetDao.getSubnetsCount();
		return SUCCESS;
	}
	@JSON(serialize=false)
	public String listShowSubnet(){
		subnet=subnetDao.getSubnetForGlobalView(subnetId);
		return SUCCESS;
	}
	
	@JSON(serialize=false)
	public String listUserChangeInfo() throws Exception {
		List<PreUserInfo> returnList=subnetDao.getUserChangeInfo(subnetId);
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
		//userInfoList = subnetDao.getUserChangeInfo(subnetId, start, limit);
		//totalCount = subnetDao.getUserChangeInfoNum(subnetId);
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
	@JSON(serialize=false)
	public String listBindingTableInfo() throws Exception {
		bindingTableInfoList = subnetDao.getBindingTableInfo(subnetId, start, limit);
		totalCount = subnetDao.getBindingTableInfoNum(subnetId);
		return SUCCESS;
	}
	
	public String getSubnetId() {
		return subnetId;
	}

	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}

	public SubnetForGlobalView getSubnet() {
		return subnet;
	}

	public void setSubnet(SubnetForGlobalView subnet) {
		this.subnet = subnet;
	}

	public List<SubnetForGlobalView> getSubnetList() {
		return subnetList;
	}

	public void setSubnetList(List<SubnetForGlobalView> subnetList) {
		this.subnetList = subnetList;
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

	public void setBindingTableInfoList(List<BindingTableInfo> bindingTableInfoList) {
		this.bindingTableInfoList = bindingTableInfoList;
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
	public List<SubnetTemp> getSubnetListForCombo() {
		return subnetListForCombo;
	}
	public void setSubnetListForCombo(List<SubnetTemp> subnetListForCombo) {
		this.subnetListForCombo = subnetListForCombo;
	}
}
