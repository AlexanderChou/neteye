package com.savi.config.action;

import com.savi.base.model.Switchbasicinfo;
import com.savi.base.util.SnmpCreate;
import com.savi.base.util.SnmpDelete;
import com.savi.show.dao.SwitchDao;

@SuppressWarnings("serial")
public class BindingTableAction extends BaseAction {
	private SwitchDao switchDao = new SwitchDao();

	private Long switchbasicinfoId;
	private String ipVersion;
	private String ifIndex;
	private String ipAddress;
	private String macAddress;
	private Integer lifeTime;

	private boolean success;
	private boolean failure;
	private String errMsg = "";

	public String update() throws Exception {
		success = false;
		failure = true;

		Switchbasicinfo switchbasicinfo = switchDao
				.getSwitchbasicinfo(switchbasicinfoId);

		SnmpCreate snmpCreateTask = new SnmpCreate(switchbasicinfo);
		if(lifeTime==null){
			lifeTime=10000000;
		}
		lifeTime = lifeTime * 100;
		if (!snmpCreateTask.snmpCreate(ipVersion, ifIndex, ipAddress,
				macAddress, lifeTime.toString())) {
			errMsg = getText("BindingTableAction.createFailed");
			return SUCCESS;
		}

		success = true;
		failure = false;
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		success = false;
		failure = true;

		Switchbasicinfo switchbasicinfo = switchDao
				.getSwitchbasicinfo(switchbasicinfoId);

		SnmpDelete snmpDeleteTask=new SnmpDelete(switchbasicinfo);

		if (!snmpDeleteTask.snmpDelete(ipVersion, ifIndex, ipAddress)) {
			errMsg = getText("BindingTableAction.deleteFailed");
			return SUCCESS;
		}

		success = true;
		failure = false;
		return SUCCESS;
	}

	public Long getSwitchbasicinfoId() {
		return switchbasicinfoId;
	}

	public void setSwitchbasicinfoId(Long switchbasicinfoId) {
		this.switchbasicinfoId = switchbasicinfoId;
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

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getIpVersion() {
		return ipVersion;
	}

	public void setIpVersion(String ipVersion) {
		this.ipVersion = ipVersion;
	}

	public String getIfIndex() {
		return ifIndex;
	}

	public void setIfIndex(String ifIndex) {
		this.ifIndex = ifIndex;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public Integer getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(Integer lifeTime) {
		this.lifeTime = lifeTime;
	}

}
