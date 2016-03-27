package com.savi.config.action;

import com.savi.base.model.Ifinterfacecur;
import com.savi.base.model.Switchbasicinfo;
import com.savi.base.util.SnmpSet;
import com.savi.show.dao.SwitchDao;

@SuppressWarnings("serial")
public class InterfaceAction extends BaseAction {
	private SwitchDao switchDao = new SwitchDao();

	private String ifIndexes; 
	private Ifinterfacecur interfacecur;
	private Ifinterfacecur interfaceOld;
	private Long switchbasicinfoId;

	private boolean success;
	private boolean failure;
	private String errMsg = "";

	public String update() throws Exception {
		success = false;
		failure = true;

		Switchbasicinfo switchbasicinfo = switchDao
				.getSwitchbasicinfo(switchbasicinfoId);

		SnmpSet snmpSetTask = new SnmpSet(switchbasicinfo);

		String indexes[] = new String[2];
		indexes[0] = interfacecur.getIpVersion().toString();
		indexes[1] = interfacecur.getIfIndex().toString();
		if((long)interfacecur.getIfFilteringNum() != (long)interfaceOld.getIfFilteringNum()){
			if(!snmpSetTask.snmpSet(interfacecur.getIfFilteringNum().toString(), "saviObjectsIfFilteringNum", indexes)){
				errMsg = getText("InterfaceAction.saviObjectsIfFilteringNum");
				return SUCCESS;
			}
		}
		
		if((int)interfacecur.getIfValidationStatus() != (int)interfaceOld.getIfValidationStatus()){
			if(!snmpSetTask.snmpSet(interfacecur.getIfValidationStatus().toString(), "saviObjectsIfValidationStatus", indexes)){
				errMsg =  getText("InterfaceAction.saviObjectsIfValidationStatus");
				return SUCCESS;
			}
		}
		
		if((int)interfacecur.getIfTrustStatus() != (int)interfaceOld.getIfTrustStatus()){
			if(!snmpSetTask.snmpSet(interfacecur.getIfTrustStatus().toString(), "saviObjectsIfTrustStatus", indexes)){
				errMsg = getText("InterfaceAction.saviObjectsIfTrustStatus");
				return SUCCESS;
			}
		}
		
		success = true;
		failure = false;
		return SUCCESS;
	}

	public String batchUpdate() throws Exception {
		success = false;
		failure = true;
		
		String[] indexArr = ifIndexes.split("\\|");

		Switchbasicinfo switchbasicinfo = switchDao
				.getSwitchbasicinfo(switchbasicinfoId);

		SnmpSet snmpSetTask = new SnmpSet(switchbasicinfo);
		
		String indexes[] = new String[2];
		indexes[0] = interfacecur.getIpVersion().toString();
		
		for(int i=0; i < indexArr.length; i++){
			indexes[1] = indexArr[i];
			if(!snmpSetTask.snmpSet(interfacecur.getIfFilteringNum().toString(), "saviObjectsIfFilteringNum", indexes)){
					errMsg = getText("InterfaceAction.saviObjectsIfFilteringNum");
					return SUCCESS;
			}
			if(!snmpSetTask.snmpSet(interfacecur.getIfValidationStatus().toString(), "saviObjectsIfValidationStatus", indexes)){
					errMsg =  getText("InterfaceAction.saviObjectsIfValidationStatus");
					return SUCCESS;
			}
			if(!snmpSetTask.snmpSet(interfacecur.getIfTrustStatus().toString(), "saviObjectsIfTrustStatus", indexes)){
					errMsg = getText("InterfaceAction.saviObjectsIfTrustStatus");
					return SUCCESS;
			}
		}
		success = true;
		failure = false;
		return SUCCESS;
	}
	
	public Ifinterfacecur getInterfacecur() {
		return interfacecur;
	}

	public void setInterfacecur(Ifinterfacecur interfacecur) {
		this.interfacecur = interfacecur;
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

	public Ifinterfacecur getInterfaceOld() {
		return interfaceOld;
	}

	public void setInterfaceOld(Ifinterfacecur interfaceOld) {
		this.interfaceOld = interfaceOld;
	}

	public String getIfIndexes() {
		return ifIndexes;
	}

	public void setIfIndexes(String ifIndexes) {
		this.ifIndexes = ifIndexes;
	}

}
