package com.base.model;


public class Asset implements java.io.Serializable {
	private String deviceNumber;
	private String deviceClasses;
	private long departmentId;
	private String deviceName;
	private String deviceConfig;
	private String deviceMobel;
	private java.math.BigDecimal purchaseMoney;
	private String vender;
	private String produceNumber;
	private String purchaseDate;
	private String establishDate;
	private String deviceUses;
	private String domainName;
	private long adminId;
	private long backupAdminId;
	private String ownAdminId;
	private String deviceLocation;
	private String deviceArrange;
	private String deviceStatus;
	private String maintainRecord;
	private String discardDate;
	private String fundSource;
	private String description;
	private String IP;
	private long id;

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public void setDeviceClasses(String deviceClasses) {
		this.deviceClasses = deviceClasses;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public void setDeviceConfig(String deviceConfig) {
		this.deviceConfig = deviceConfig;
	}

	public void setDeviceMobel(String deviceMobel) {
		this.deviceMobel = deviceMobel;
	}

	public void setPurchaseMoney(java.math.BigDecimal purchaseMoney) {
		this.purchaseMoney = purchaseMoney;
	}

	public void setVender(String vender) {
		this.vender = vender;
	}

	public void setProduceNumber(String produceNumber) {
		this.produceNumber = produceNumber;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public void setEstablishDate(String establishDate) {
		this.establishDate = establishDate;
	}

	public void setDeviceUses(String deviceUses) {
		this.deviceUses = deviceUses;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public void setDeviceLocation(String deviceLocation) {
		this.deviceLocation = deviceLocation;
	}

	public void setDeviceArrange(String deviceArrange) {
		this.deviceArrange = deviceArrange;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public void setMaintainRecord(String maintainRecord) {
		this.maintainRecord = maintainRecord;
	}

	public void setDiscardDate(String discardDate) {
		this.discardDate = discardDate;
	}

	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIP(String IP) {
		this.IP = IP;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public String getDeviceClasses() {
		return deviceClasses;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getDeviceConfig() {
		return deviceConfig;
	}

	public String getDeviceMobel() {
		return deviceMobel;
	}

	public java.math.BigDecimal getPurchaseMoney() {
		return purchaseMoney;
	}

	public String getVender() {
		return vender;
	}

	public String getProduceNumber() {
		return produceNumber;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public String getEstablishDate() {
		return establishDate;
	}

	public String getDeviceUses() {
		return deviceUses;
	}

	public String getDomainName() {
		return domainName;
	}

	public String getDeviceLocation() {
		return deviceLocation;
	}

	public String getDeviceArrange() {
		return deviceArrange;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public String getMaintainRecord() {
		return maintainRecord;
	}

	public String getDiscardDate() {
		return discardDate;
	}

	public String getFundSource() {
		return fundSource;
	}

	public String getDescription() {
		return description;
	}

	public String getIP() {
		return IP;
	}

	public long getId() {
		return id;
	}

	public long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

	public long getBackupAdminId() {
		return backupAdminId;
	}

	public void setBackupAdminId(long backupAdminId) {
		this.backupAdminId = backupAdminId;
	}

	public String getOwnAdminId() {
		return ownAdminId;
	}

	public void setOwnAdminId(String ownAdminId) {
		this.ownAdminId = ownAdminId;
	}
}
