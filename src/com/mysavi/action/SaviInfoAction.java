package com.mysavi.action;

import java.util.List;

import com.mysavi.dao.SaviInfoDao;
import com.mysavi.model.SaviInfo;
import com.tdrouting.action.BaseAction;

public class SaviInfoAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SaviInfo> saviInfoList;

	public String getSaviInfo() {
		SaviInfoDao dao = new SaviInfoDao();
		saviInfoList = dao.getSaviInfo();
		return SUCCESS;
	}

	public List<SaviInfo> getSaviInfoList() {
		return saviInfoList;
	}
}
