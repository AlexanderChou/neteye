package com.savi.show.action;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.savi.show.dao.SubnetDao;
import com.savi.show.dao.SwitchDao;
import com.savi.show.dto.SubnetForGlobalView;
import com.savi.show.dto.SwitchInfo;
import com.savi.show.dto.SwitchcurTemp;

@SuppressWarnings("serial")
public class SearchAction extends BaseAction {
	private SwitchDao switchDao = new SwitchDao();
	private SubnetDao subnetDao = new SubnetDao();

	private String keywords;
	private List<SubnetForGlobalView> subnetList;
	private List<SwitchInfo> switchList;

	private List<SwitchcurTemp> saviList;//SAVI列表
	private int totalCount;
	private String start;
	private String limit;

	@JSON(serialize = false)
	public String searchSubnet() throws Exception {
		String[] keywordArr = keywords.trim().split(" ");
		subnetList = subnetDao.searchSubnet(keywordArr, start, limit);
		totalCount = subnetDao.getSearchResultCount(keywordArr);
		return SUCCESS;
	}

	@JSON(serialize = false)
	public String searchSwitch() throws Exception {
		String[] keywordArr = keywords.trim().split(" ");
		switchList = switchDao.searchSwitch(keywordArr, start, limit);
		totalCount = switchDao.getSearchResultCount(keywordArr);
		return SUCCESS;
	}
	
	@JSON(serialize = false)
	public String listSearchSwitchSAVI() throws Exception {
		String[] keywordArr = keywords.trim().split(" ");
		saviList = switchDao.listSearchSwitchSAVI(keywordArr, start, limit);
		totalCount = switchDao.getSearchSwitchSAVICount(keywordArr);
		return SUCCESS;
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

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public List<SubnetForGlobalView> getSubnetList() {
		return subnetList;
	}

	public void setSubnetList(List<SubnetForGlobalView> subnetList) {
		this.subnetList = subnetList;
	}
	public List<SwitchInfo> getSwitchList() {
		return switchList;
	}

	public void setSwitchList(List<SwitchInfo> switchList) {
		this.switchList = switchList;
	}
	public List<SwitchcurTemp> getSaviList() {
		return saviList;
	}

	public void setSaviList(List<SwitchcurTemp> saviList) {
		this.saviList = saviList;
	}
}
