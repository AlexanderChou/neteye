package com.asset.action;

import java.io.PrintWriter;
import java.util.List;

import com.asset.dao.AssetInfoDAO;
import com.asset.dto.AssetList;
import com.base.model.AssetInfo;
import com.base.util.BaseAction;

public class AssetInfoManageAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String totalCount;
	private boolean success;
	private boolean failure;
	private List<AssetInfo> assetInfos;
	private List<AssetList> assetLists;
	private AssetList assetList;
	private List<AssetInfo> assetInfoQuerys;
	private AssetInfo assetInfo;
	// private AssetInfo assetQuery;
	private String deviceNumber;
	private String deviceClasses;
	private String deviceName;
	private Long departmentId;
	private String deviceConfig;
	private String deviceModel;
	private Float purchaseMoney;
	private String deviceVender;
	private String produceNumber;
	private String purchaseDate;
	private String deviceUses;
	private String domainName;
	private String establishDate;
	private Long adminId;
	private String ownAdminId;
	private Long backupAdminId;
	private String deviceLocation;
	private String maintainRecord;
	private String discardDate;
	private String fundSource;
	private String description;
	private String deviceIP;
	private String deviceArrange;
	private String deviceStatus;

	private AssetInfoDAO assetInfoDAO = new AssetInfoDAO();

	public String listAssetInfo() throws Exception {
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		assetLists = assetInfoDAO.getAssetInfos(start, limit);
		totalCount = String.valueOf(assetInfoDAO.getAssetInfosCount());

		success = true;
		return SUCCESS;
	}

	public String listAssetInfoQuery() throws Exception {
        AssetInfo assetQuery = new AssetInfo();
		if(adminId!=null){
			assetQuery.setAdminId(adminId);
		};
		if (backupAdminId!=null){
			
			   assetQuery.setBackupAdminId(backupAdminId);
		};
		if (departmentId!=null){
			
			   assetQuery.setDepartmentId(departmentId);
			
		};
		if (purchaseMoney!=null){
			 assetQuery.setPurchaseMoney(purchaseMoney);
		};
        assetQuery.setDescription(description);
        assetQuery.setDeviceArrange(deviceArrange);
        assetQuery.setDeviceClasses(deviceClasses);
        assetQuery.setDeviceConfig(deviceConfig);
        assetQuery.setDeviceIP(deviceIP);
        assetQuery.setDeviceLocation(deviceLocation);
        assetQuery.setDeviceModel(deviceModel);
        assetQuery.setDeviceName(deviceName);
        assetQuery.setDeviceNumber(deviceNumber);
        assetQuery.setDeviceStatus(deviceStatus);
        assetQuery.setDeviceUses(deviceUses);
        assetQuery.setDeviceVender(deviceVender);
        assetQuery.setDiscardDate(discardDate);
        assetQuery.setDomainName(domainName);
        assetQuery.setEstablishDate(establishDate);
        assetQuery.setFundSource(fundSource);
        assetQuery.setMaintainRecord(maintainRecord);
        assetQuery.setOwnAdminId(ownAdminId);
        assetQuery.setProduceNumber(produceNumber);
        assetQuery.setPurchaseDate(purchaseDate);
      
        

		assetInfoQuerys = assetInfoDAO.getAssetInfoQuerys(assetQuery);

		totalCount = String.valueOf(assetInfoQuerys.size());
		success = true;
		return SUCCESS;
	}

	public String deleteAssetInfo() throws Exception {
		String[] assetInfoIds = this.getRequest().getParameter("assetInfoIds")
				.trim().split(";");
		for (String assetInfoId : assetInfoIds) {
			assetInfoDAO.delete(Long.parseLong(assetInfoId));
		}
		PrintWriter writer = this.getResponse().getWriter();
		writer.print("ok");
		writer.close();
		return null;
	}

	public String modifyAssetInfo() throws Exception {
		
		String assetInfoId = this.getRequest().getParameter("assetInfoId");
		
		 
	 AssetInfo assetInfo = assetInfoDAO.getassetInfoById(Long
				.parseLong(assetInfoId));

//		boolean assetInfoNameIsHave = assetInfoDAO
//				.checkassetInfoNameIsExist(adminId);
//		if (!(assetInfo.getDeviceName() == (adminId))) {
//			if (assetInfoNameIsHave) {
//				PrintWriter w = this.getResponse().getWriter();
//				w.print("same");
//				w.close();
//				return null;
//			}
//		}
	    	assetInfo.setAdminId(adminId);
		    assetInfo.setBackupAdminId(backupAdminId);
		    assetInfo.setDepartmentId(departmentId);
		    assetInfo.setPurchaseMoney(purchaseMoney);
		    assetInfo.setDescription(description);
	        assetInfo.setDeviceArrange(deviceArrange);
	        assetInfo.setDeviceClasses(deviceClasses);
	        assetInfo.setDeviceConfig(deviceConfig);
	        assetInfo.setDeviceIP(deviceIP);
	        assetInfo.setDeviceLocation(deviceLocation);
	        assetInfo.setDeviceModel(deviceModel);
	        assetInfo.setDeviceName(deviceName);
	        assetInfo.setDeviceNumber(deviceNumber);
	        assetInfo.setDeviceStatus(deviceStatus);
	        assetInfo.setDeviceUses(deviceUses);
	        assetInfo.setDeviceVender(deviceVender);
	        assetInfo.setDiscardDate(discardDate);
	        assetInfo.setDomainName(domainName);
	        assetInfo.setEstablishDate(establishDate);
	        assetInfo.setFundSource(fundSource);
	        assetInfo.setMaintainRecord(maintainRecord);
	        assetInfo.setOwnAdminId(ownAdminId);
	        assetInfo.setProduceNumber(produceNumber);
	        assetInfo.setPurchaseDate(purchaseDate);
		
		assetInfoDAO.save(assetInfo);
		PrintWriter write = this.getResponse().getWriter();
		write.print("ok");
		write.close();
		return null;
	}

	public String addAssetInfo() throws Exception {
		boolean assetInfoNameIsHave = assetInfoDAO
				.checkassetInfoNameIsExist(assetInfo.getDeviceName());
		if (assetInfoNameIsHave) {
			failure = true;
			return SUCCESS;
		}
		assetInfoDAO.save(assetInfo);
		success = true;
		return SUCCESS;

	}
	public static void main(String[] args) throws Exception {
		AssetInfoDAO dao = new AssetInfoDAO();
	
		List temp = dao.getAssetInfos("0", "24");
		System.out.println("id="+temp.size());
	}

	// 统计列表


	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
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

	public List<AssetInfo> getAssetInfos() {
		return assetInfos;
	}

	public void setAssetInfos(List<AssetInfo> assetInfos) {
		this.assetInfos = assetInfos;
	}

	public AssetInfo getAssetInfo() {
		return assetInfo;
	}

	public void setAssetInfo(AssetInfo assetInfo) {
		this.assetInfo = assetInfo;
	}

	public void setAssetInfoQuerys(List<AssetInfo> assetInfoQuerys) {
		this.assetInfoQuerys = assetInfoQuerys;
	}

	public List<AssetInfo> getAssetInfoQuerys() {
		return assetInfoQuerys;
	}



	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getDeviceClasses() {
		return deviceClasses;
	}

	public void setDeviceClasses(String deviceClasses) {
		this.deviceClasses = deviceClasses;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDeviceConfig() {
		return deviceConfig;
	}

	public void setDeviceConfig(String deviceConfig) {
		this.deviceConfig = deviceConfig;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public Float getPurchaseMoney() {
		return purchaseMoney;
	}

	public void setPurchaseMoney(Float purchaseMoney) {
		this.purchaseMoney = purchaseMoney;
	}

	public String getDeviceVender() {
		return deviceVender;
	}

	public void setDeviceVender(String deviceVender) {
		this.deviceVender = deviceVender;
	}

	public String getProduceNumber() {
		return produceNumber;
	}

	public void setProduceNumber(String produceNumber) {
		this.produceNumber = produceNumber;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getDeviceUses() {
		return deviceUses;
	}

	public void setDeviceUses(String deviceUses) {
		this.deviceUses = deviceUses;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getEstablishDate() {
		return establishDate;
	}

	public void setEstablishDate(String establishDate) {
		this.establishDate = establishDate;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getOwnAdminId() {
		return ownAdminId;
	}

	public void setOwnAdminId(String ownAdminId) {
		this.ownAdminId = ownAdminId;
	}

	public Long getBackupAdminId() {
		return backupAdminId;
	}

	public void setBackupAdminId(Long backupAdminId) {
		this.backupAdminId = backupAdminId;
	}

	public String getDeviceLocation() {
		return deviceLocation;
	}

	public void setDeviceLocation(String deviceLocation) {
		this.deviceLocation = deviceLocation;
	}

	public String getMaintainRecord() {
		return maintainRecord;
	}

	public void setMaintainRecord(String maintainRecord) {
		this.maintainRecord = maintainRecord;
	}

	public String getDiscardDate() {
		return discardDate;
	}

	public void setDiscardDate(String discardDate) {
		this.discardDate = discardDate;
	}

	public String getFundSource() {
		return fundSource;
	}

	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public String getDeviceArrange() {
		return deviceArrange;
	}

	public void setDeviceArrange(String deviceArrange) {
		this.deviceArrange = deviceArrange;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public void setAssetLists(List<AssetList> assetLists) {
		this.assetLists = assetLists;
	}

	public List<AssetList> getAssetLists() {
		return assetLists;
	}

	public void setAssetList(AssetList assetList) {
		this.assetList = assetList;
	}

	public AssetList getAssetList() {
		return assetList;
	}




}
