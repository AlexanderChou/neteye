package com.asset.action;

import java.util.List;

import com.asset.dao.AssetInfoDAO;
import com.asset.dto.AssetSum;
import com.base.util.BaseAction;

public class AssetSumList extends BaseAction {
	private static final long serialVersionUID = 1L;
	private List<AssetSum> assetSums;
	private List<AssetSum> assetSumModels;
	private List<AssetSum> assetSumVenders;
	private List<AssetSum> assetSumStatuss;
	private List<AssetSum> assetSumDeparts;
	private boolean success;
	
	public String assetSumList() throws Exception {
	
		AssetInfoDAO dao = new AssetInfoDAO();
		
		
		
		assetSums = dao.getAssetInfoSum("deviceModel");
		
		assetSumVenders = dao.getAssetInfoSum("deviceVender");
		
		assetSumStatuss = dao.getAssetInfoSum("deviceStatus");
		
		assetSumDeparts = dao.getAssetInfoSum("departmentId");
		
	
	assetSums.addAll(assetSumVenders);
		assetSums.addAll(assetSumStatuss);
		assetSums.addAll(assetSumDeparts);
		
		
		
		

		success = true;
        
		return SUCCESS;
	}

	public List<AssetSum> getAssetSums() {
		return assetSums;
	}

	public void setAssetSums(List<AssetSum> assetSums) {
		this.assetSums = assetSums;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<AssetSum> getAssetSumModels() {
		return assetSumModels;
	}

	public void setAssetSumModels(List<AssetSum> assetSumModels) {
		this.assetSumModels = assetSumModels;
	}

	public List<AssetSum> getAssetSumVenders() {
		return assetSumVenders;
	}

	public void setAssetSumVenders(List<AssetSum> assetSumVenders) {
		this.assetSumVenders = assetSumVenders;
	}

	public List<AssetSum> getAssetSumStatuss() {
		return assetSumStatuss;
	}

	public void setAssetSumStatuss(List<AssetSum> assetSumStatuss) {
		this.assetSumStatuss = assetSumStatuss;
	}

	public List<AssetSum> getAssetSumDeparts() {
		return assetSumDeparts;
	}

	public void setAssetSumDeparts(List<AssetSum> assetSumDeparts) {
		this.assetSumDeparts = assetSumDeparts;
	}


}