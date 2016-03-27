package com.savi.config.action;

import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.savi.base.model.Savibindingtablehis;
import com.savi.base.model.Subnet;
import com.savi.base.model.Switchbasicinfo;
import com.savi.base.model.Switchcur;
import com.savi.base.util.SnmpSet;
import com.savi.collection.dao.SavibindingtablehisDao;
import com.savi.collection.dao.SwitchBasicInfoDao;
import com.savi.collection.util.CollectionFactory;
import com.savi.config.utils.IPUitls;
import com.savi.show.dao.SubnetDao;
import com.savi.show.dao.SwitchDao;

@SuppressWarnings("serial")
public class SwitchConfigAction extends BaseAction {
	private static final int MAX_BATCH_ADD_COUNT = 100;
	
	private SwitchDao switchDao = new SwitchDao();
	private SubnetDao subnetDao = new SubnetDao();
	private SavibindingtablehisDao savibindingtablehisDao=new SavibindingtablehisDao();

	private String ipVersions;
	private String ids;
	private String ipv4Start;
	private String ipv4End;
	private String ipv6Start;
	private String ipv6End;
	private Switchbasicinfo switchbasicinfo;
	private Long switchbasicinfoId;
	private Switchcur switchcur;
	private String subnetId;
	private String addSwitchScript;
	public String getAddSwitchScript() {
		return addSwitchScript;
	}

	public void setAddSwitchScript(String addSwitchScript) {
		this.addSwitchScript = addSwitchScript;
	}

	private boolean success;
	private boolean failure;
	private String errMsg = "";

	// 删除交换机
	@JSON(serialize = false)
	public String deleteSwitchbasicinfo() throws Exception {
		success = false;
		failure = true;

		if (ids == null) {
			errMsg = getText("SwitchConfigAction.requestIncorrect");
			return SUCCESS;
		}

		String[] idArr = ids.split("\\|");
		Switchbasicinfo switchbasicinfo=null;
		SwitchBasicInfoDao switchBasicInfoDao=new SwitchBasicInfoDao();
		for (int i = 0; i < idArr.length; i++) {
			String id = idArr[i];
			switchbasicinfo=switchBasicInfoDao.getSwitchBasicInfo(Long.parseLong(id));
			if (id.equals("")|| switchbasicinfo== null) {
				errMsg = getText("SwitchConfigAction.switchNotFound");
				return SUCCESS;
			}
			CollectionFactory.stopCollection(switchbasicinfo);
			List<Savibindingtablehis> list=savibindingtablehisDao.getSwitchhisOnline(switchbasicinfo);
			Date d = new Date();
			long longtime = d.getTime();
			for(int j=0;j<list.size();j++){
				Savibindingtablehis savibindingtablehis=list.get(j);
				savibindingtablehis.setEndTime(longtime);
				savibindingtablehis.setStatus(0);
				savibindingtablehisDao.update(savibindingtablehis);
			}
			switchDao.deleteSwitchbasicinfo(switchbasicinfo);	
		}

		success = true;
		failure = false;

		return SUCCESS;
	}

	// 添加或更新交换机
	@JSON(serialize = false)
	public String saveSwitchbasicinfo() throws Exception {
		success = false;
		failure = true;

		if (switchbasicinfo == null) {
			errMsg = getText("SwitchConfigAction.requestIncorrect");
			return SUCCESS;
		}

		boolean isNew = true;
		if (switchbasicinfo.getId() != null)
			isNew = false;

		Switchbasicinfo temp = null;
		if (!isNew
				&& (temp = switchDao
						.getSwitchbasicinfo(switchbasicinfo.getId())) == null) {
			errMsg = getText("SwitchConfigAction.switchNotExist");
			return SUCCESS;
		}
		Subnet subnet = subnetDao.getSubnet(subnetId);
		if(subnet==null){
			errMsg = getText("SwitchConfigAction.switchNotExist");
			return SUCCESS;
		}
		switchbasicinfo.setIsDelete(0);

		if (isNew)
			switchbasicinfo.setStatus(0);
		else
			switchbasicinfo.setStatus(temp.getStatus());

		switchbasicinfo.setSubnet(subnet);
		
		switchDao.saveSwitchbasicinfo(switchbasicinfo);
		if(isNew)
			CollectionFactory.resgisterForPoll(switchbasicinfo);
		
		success = true;
		failure = false;

		return SUCCESS;
	}
	@JSON(serialize = false)
	public String batchAddSwitchbasicinfoScript(){
		success = true;
		SubnetDao subnetDao=new SubnetDao();
		//所属子网|ipV4地址|ipV6地址|设备名称|设备型号|SNMP版本|读共同体(用户名)|写共同体(上下文)|认证密钥|私有密钥|描述
		errMsg = getText("SwitchConfigAction.requestIncorrect");
		String[] scriptArray=addSwitchScript.split("\n");
		String[] firstSwitchInfo=new String[11];
		for(int i=0;i<11;i++){
			firstSwitchInfo[i]="";
		}
		String[] firstSwitchInfoTemp=scriptArray[0].trim().split("\\|");
		if(firstSwitchInfoTemp.length>11){
			success=false;
			errMsg+="format error";
			return SUCCESS;
		}
		for(int i=0;i<firstSwitchInfoTemp.length;i++){
			firstSwitchInfo[i]=firstSwitchInfoTemp[i];
		}
		for(int i=0;i<scriptArray.length;i++){
			String[] switchInfo=new String[11];
			for(int j=0;j<11;j++){
				switchInfo[j]="";
			}
			String[] switchInfoTemp=scriptArray[i].trim().split("\\|");
			if(switchInfoTemp.length>11){
				success=false;
				errMsg+="format error";
				return SUCCESS;
			}
			for(int j=0;j<switchInfoTemp.length;j++){
				switchInfo[j]=switchInfoTemp[j];
			}
			Switchbasicinfo switchbasicinfo=new Switchbasicinfo();
			switchbasicinfo.setName(switchInfo[3]);
			switchbasicinfo.setEquipmentType(switchInfo[4]);
			if(switchInfo[5].equals("")){
				switchbasicinfo.setSnmpVersion(firstSwitchInfo[5]);
			}else{
				switchbasicinfo.setSnmpVersion(switchInfo[5]);
			}
			switchbasicinfo.setIpv4address(switchInfo[1]);
			switchbasicinfo.setIpv6address(switchInfo[2]);
			if(switchInfo[6].equals("")){
				switchbasicinfo.setReadCommunity(firstSwitchInfo[6]);
			}else{
				switchbasicinfo.setReadCommunity(switchInfo[6]);
			}
			if(switchInfo[7].equals("")){
				switchbasicinfo.setWriteCommunity(firstSwitchInfo[7]);
			}else{
				switchbasicinfo.setWriteCommunity(switchInfo[7]);
			}
			if(switchInfo[8].equals("")){
				switchbasicinfo.setAuthKey(firstSwitchInfo[8]);
			}else{
				switchbasicinfo.setAuthKey(switchInfo[8]);
			}
			if(switchInfo[9].equals("")){
				switchbasicinfo.setPrivateKey(firstSwitchInfo[9]);
			}else{
				switchbasicinfo.setPrivateKey(switchInfo[9]);
			}
			Subnet subnet=null;
			//如果子网一项为空，则默认为第一条记录所指的子网。
			if(switchInfo[0].equals("")){
				subnet=subnetDao.getSubnetByName(firstSwitchInfo[0]);
				if(subnet==null){
					//此分支只有当第一条记录执行时，而且第一条记录的子网也为空时才会执行
					subnet=new Subnet();
					subnet.setIsDelete(0);
					subnetDao.save(subnet);
				}
			}else{
				subnet=subnetDao.getSubnetByName(switchInfo[0]);
				if(subnet==null){
					//此分支是当该条记录的子网名称没有在数据库中对应项，此时默认用户想要新建一个子网
					subnet=new Subnet();
					subnet.setName(switchInfo[0]);
					subnet.setIsDelete(0);
					subnetDao.save(subnet);
				}
			}		
			switchbasicinfo.setSubnet(subnet);
			switchbasicinfo.setDescription(switchInfo[10]);
			switchbasicinfo.setIsDelete(0);
			switchbasicinfo.setStatus(0);
			switchDao.saveSwitchbasicinfo(switchbasicinfo);
			CollectionFactory.resgisterForPoll(switchbasicinfo);
		}
		
		return SUCCESS;
	}
	// 批量添加交换机
	@JSON(serialize = false)
	public String batchAddSwitchbasicinfo() throws Exception {
		success = false;
		failure = true;
		if (switchbasicinfo == null) {
			errMsg = getText("SwitchConfigAction.requestIncorrect");
			return SUCCESS;
		}
		
		char[] ipv4StartBinary;
		char[] ipv4EndBinary;
		char[] ipv6StartBinary;
		char[] ipv6EndBinary;
		char[] tempIPv4;
		char[] tempIPv6;
		
		Subnet subnet = subnetDao.getSubnet(subnetId);
		if(subnet==null){
			errMsg = getText("SwitchConfigAction.requestIncorrect");
			return SUCCESS;
		}
		switchbasicinfo.setIsDelete(0);
		switchbasicinfo.setStatus(0);
		switchbasicinfo.setSubnet(subnet);
		
		if(ipv4Start != null && ipv4End != null && ipv6Start != null && ipv6End != null &&
				!ipv4Start.isEmpty() && !ipv4End.isEmpty() && !ipv6Start.isEmpty() && !ipv6End.isEmpty()){
			ipv4StartBinary = IPUitls.ipv4ToBinary(ipv4Start);
			ipv4EndBinary = IPUitls.ipv4ToBinary(ipv4End);
			ipv6StartBinary = IPUitls.ipv6ToBinary(ipv6Start);
			ipv6EndBinary = IPUitls.ipv6ToBinary(ipv6End);
			
			tempIPv4 = ipv4StartBinary;
			tempIPv6 = ipv6StartBinary;
			
			int count = 0;
			while(!IPUitls.equal(tempIPv4, ipv4EndBinary) && !IPUitls.equal(tempIPv6, ipv6EndBinary)){
				if(count >= MAX_BATCH_ADD_COUNT) break;
				count++;
				
				Switchbasicinfo switchSave = new Switchbasicinfo(switchbasicinfo);
				switchSave.setIpv4address(IPUitls.binaryToIPv4(tempIPv4));
				switchSave.setIpv6address(IPUitls.binaryToIPv6(tempIPv6));
				switchSave.setName(switchSave.getName() + count);
				
				switchDao.saveSwitchbasicinfo(switchSave);
				CollectionFactory.resgisterForPoll(switchSave);
							
				tempIPv4 = IPUitls.increment(tempIPv4);
				tempIPv6 = IPUitls.increment(tempIPv6);
			}
			
			boolean noIPv4 = false;
			boolean noIPv6 = false;
			
			if((IPUitls.equal(tempIPv4, ipv4EndBinary) || IPUitls.equal(tempIPv6, ipv6EndBinary)) && count < MAX_BATCH_ADD_COUNT){
				
				Switchbasicinfo switchSave = new Switchbasicinfo(switchbasicinfo);
				switchSave.setIpv4address(IPUitls.binaryToIPv4(tempIPv4));
				switchSave.setIpv6address(IPUitls.binaryToIPv6(tempIPv6));
				switchSave.setName(switchSave.getName() + count);
				
				switchDao.saveSwitchbasicinfo(switchSave);
				CollectionFactory.resgisterForPoll(switchSave);
						
				if(!IPUitls.equal(tempIPv4, ipv4EndBinary))
					tempIPv4 = IPUitls.increment(tempIPv4);
				else noIPv4 = true;
				
				if(!IPUitls.equal(tempIPv6, ipv6EndBinary))
					tempIPv6 = IPUitls.increment(tempIPv6);
				else noIPv6 = true;
			}
			
			while(count < MAX_BATCH_ADD_COUNT){
				count++;
				Switchbasicinfo switchSave = new Switchbasicinfo(switchbasicinfo);
				if(!noIPv4){
					if(IPUitls.equal(tempIPv4, ipv4EndBinary)) noIPv4 = true;
					switchSave.setIpv4address(IPUitls.binaryToIPv4(tempIPv4));
					switchSave.setName(switchSave.getName() + count);
					tempIPv4 = IPUitls.increment(tempIPv4);
				}else if(!noIPv6){
					if(IPUitls.equal(tempIPv6, ipv6EndBinary)) noIPv6 = true;
					switchSave.setIpv6address(IPUitls.binaryToIPv6(tempIPv6));
					switchSave.setName(switchSave.getName() + count);
					tempIPv6 = IPUitls.increment(tempIPv6);
				}else break;
				
				switchDao.saveSwitchbasicinfo(switchSave);
				CollectionFactory.resgisterForPoll(switchSave);
			}
			
			

		}else if(ipv4Start != null && ipv4End != null && !ipv4Start.isEmpty() && !ipv4End.isEmpty()){
			ipv4StartBinary = IPUitls.ipv4ToBinary(ipv4Start);
			ipv4EndBinary = IPUitls.ipv4ToBinary(ipv4End);
			
			tempIPv4 = ipv4StartBinary;
			
			boolean noIPv4 = false;
			int count = 0;
			while(!noIPv4){
				if(count >= MAX_BATCH_ADD_COUNT) break;
				if(IPUitls.equal(tempIPv4, ipv4EndBinary)) noIPv4 = true;
				
				Switchbasicinfo switchSave = new Switchbasicinfo(switchbasicinfo);
				switchSave.setIpv4address(IPUitls.binaryToIPv4(tempIPv4));
				switchSave.setName(switchSave.getName() + (count + 1));
				
				switchDao.saveSwitchbasicinfo(switchSave);
				CollectionFactory.resgisterForPoll(switchSave);
				
				count++;
				tempIPv4 = IPUitls.increment(tempIPv4);
			}
			
		}else if(ipv6Start != null && ipv6End != null && !ipv6Start.isEmpty() && !ipv6End.isEmpty()){
			ipv6StartBinary = IPUitls.ipv6ToBinary(ipv6Start);
			ipv6EndBinary = IPUitls.ipv6ToBinary(ipv6End);
			
			tempIPv6 = ipv6StartBinary;
			
			boolean noIPv6 = false;
			int count = 0;
			while(!noIPv6){
				if(count >= MAX_BATCH_ADD_COUNT) break;
				if(IPUitls.equal(tempIPv6, ipv6EndBinary)) noIPv6 = true;
				
				Switchbasicinfo switchSave = new Switchbasicinfo(switchbasicinfo);
				switchSave.setIpv6address(IPUitls.binaryToIPv6(tempIPv6));
				switchSave.setName(switchSave.getName() + (count + 1));
				
				switchDao.saveSwitchbasicinfo(switchSave);
				CollectionFactory.resgisterForPoll(switchSave);
				
				count++;
				tempIPv6 = IPUitls.increment(tempIPv6);
			}
		}else{
			errMsg = getText("SwitchConfigAction.requestIncorrect");
			return SUCCESS;
		}	
		
		success = true;
		failure = false;

		return SUCCESS;
	}

	// 更新SAVI信息
	@JSON(serialize = false)
	public String updateSAVI() throws Exception {
		success = false;
		failure = true;

		Switchcur switchcurDB = switchDao.getSwitchcurByIPVersionAndSwitchId
			(switchcur.getIpVersion(), switchbasicinfoId);
		
		if (switchcurDB.getId() == null) {
			errMsg = getText("SwitchConfigAction.saviNotExist");
			return SUCCESS;
		}
		Switchbasicinfo switchbasicinfo = switchDao.getSwitchbasicinfo(switchbasicinfoId);
		SnmpSet snmpSetTask=new SnmpSet(switchbasicinfo);

		String indexes[]=new String[1];
		indexes[0] = switchcurDB.getIpVersion().toString();
		
		if((int)switchcurDB.getSystemMode() != (int)switchcur.getSystemMode()){
			switchcurDB.setSystemMode(switchcur.getSystemMode());
			if(!snmpSetTask.snmpSet(switchcurDB.getSystemMode().toString(), "saviObjectsSystemMode", indexes)){
				errMsg = getText("SwitchConfigAction.saviObjectsSystemMode");
				return SUCCESS;
			}
			switchDao.updateSwitchcur(switchcurDB);
		}
		
		if((int)(switchcurDB.getMaxDadDelay() / 100) != (int)switchcur.getMaxDadDelay()){
			switchcurDB.setMaxDadDelay(switchcur.getMaxDadDelay() * 100);
			if(!snmpSetTask.snmpSet(switchcurDB.getMaxDadDelay().toString(), "saviObjectsSystemMaxDadDelay", indexes)){
				errMsg = getText("SwitchConfigAction.saviObjectsSystemMaxDadDelay");
				return SUCCESS;
			}
			switchDao.updateSwitchcur(switchcurDB);
		}
		
		if((int)(switchcurDB.getMaxDadPrepareDelay() / 100) != (int)switchcur.getMaxDadPrepareDelay()){
			switchcurDB.setMaxDadPrepareDelay(switchcur.getMaxDadPrepareDelay() * 100);
			if(!snmpSetTask.snmpSet(switchcurDB.getMaxDadPrepareDelay().toString(), "saviObjectsSystemMaxDadPrepareDelay", indexes)){
				errMsg = getText("SwitchConfigAction.saviObjectsSystemMaxDadPrepareDelay");
				return SUCCESS;
			}
			switchDao.updateSwitchcur(switchcurDB);
		}
		
//		switchcurDB.setMaxDadPrepareDelay(switchcur.getMaxDadPrepareDelay());
//		switchcurDB.setMaxDadDelay(switchcur.getMaxDadDelay());
//		switchcurDB.setSystemMode(switchcur.getSystemMode());
//		switchDao.updateSwitchcur(switchcurDB);
		
		success = true;
		failure = false;
		return SUCCESS;
	}
	
	//批量更行SAVI信息
	// 更新SAVI信息
	@JSON(serialize = false)
	public String batchUpdateSAVI() throws Exception {
		success = false;
		failure = true;
		
		String[] switchIdArr =  ids.split("\\|");
		String[] ipVersionArr = ipVersions.split("\\|");

		for(int i=0; i < switchIdArr.length; i++ ){
			long switchbasicinfoId = Long.parseLong(switchIdArr[i]);
			int ipVersion = Integer.parseInt(ipVersionArr[i]);
			
			Switchcur switchcurDB = switchDao.getSwitchcurByIPVersionAndSwitchId(ipVersion, switchbasicinfoId);
			
			if (switchcurDB.getId() == null) {
				errMsg = getText("SwitchConfigAction.saviNotExist");
				return SUCCESS;
			}
			Switchbasicinfo switchbasicinfo = switchDao.getSwitchbasicinfo(switchbasicinfoId);
			SnmpSet snmpSetTask=new SnmpSet(switchbasicinfo);
	
			String indexes[]=new String[1];
			indexes[0] = switchcurDB.getIpVersion().toString();
			
			if((int)switchcurDB.getSystemMode() != (int)switchcur.getSystemMode()){
				switchcurDB.setSystemMode(switchcur.getSystemMode());
				if(!snmpSetTask.snmpSet(switchcurDB.getSystemMode().toString(), "saviObjectsSystemMode", indexes)){
					errMsg = getText("SwitchConfigAction.saviObjectsSystemMode");
					return SUCCESS;
				}
				switchDao.updateSwitchcur(switchcurDB);
			}
			
			if((int)(switchcurDB.getMaxDadDelay() / 100) != (int)switchcur.getMaxDadDelay()){
				switchcurDB.setMaxDadDelay(switchcur.getMaxDadDelay() * 100);
				if(!snmpSetTask.snmpSet(switchcurDB.getMaxDadDelay().toString(), "saviObjectsSystemMaxDadDelay", indexes)){
					errMsg = getText("SwitchConfigAction.saviObjectsSystemMaxDadDelay");
					return SUCCESS;
				}
				switchDao.updateSwitchcur(switchcurDB);
			}
			
			if((int)(switchcurDB.getMaxDadPrepareDelay() / 100) != (int)switchcur.getMaxDadPrepareDelay()){
				switchcurDB.setMaxDadPrepareDelay(switchcur.getMaxDadPrepareDelay() * 100);
				if(!snmpSetTask.snmpSet(switchcurDB.getMaxDadPrepareDelay().toString(), "saviObjectsSystemMaxDadPrepareDelay", indexes)){
					errMsg = getText("SwitchConfigAction.saviObjectsSystemMaxDadPrepareDelay");
					return SUCCESS;
				}
				switchDao.updateSwitchcur(switchcurDB);
			}
		}
		
		success = true;
		failure = false;
		return SUCCESS;
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	//此处必须将该anotation，只要该switchbasicinfo在action里面与hibernate持久化过，jason在
	//向前端传时就会调用getSubnet等方法。
	@JSON(serialize = false)
	public Switchbasicinfo getSwitchbasicinfo() {
		return switchbasicinfo;
	}
	
	public void setSwitchbasicinfo(Switchbasicinfo switchbasicinfo) {
		this.switchbasicinfo = switchbasicinfo;
	}

	public Switchcur getSwitchcur() {
		return switchcur;
	}

	public void setSwitchcur(Switchcur switchcur) {
		this.switchcur = switchcur;
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

	public String getSubnetId() {
		return subnetId;
	}

	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}
	
	public Long getSwitchbasicinfoId() {
		return switchbasicinfoId;
	}

	public void setSwitchbasicinfoId(Long switchbasicinfoId) {
		this.switchbasicinfoId = switchbasicinfoId;
	}

	public String getIpv4Start() {
		return ipv4Start;
	}

	public void setIpv4Start(String ipv4Start) {
		this.ipv4Start = ipv4Start;
	}

	public String getIpv4End() {
		return ipv4End;
	}

	public void setIpv4End(String ipv4End) {
		this.ipv4End = ipv4End;
	}

	public String getIpv6Start() {
		return ipv6Start;
	}

	public void setIpv6Start(String ipv6Start) {
		this.ipv6Start = ipv6Start;
	}

	public String getIpv6End() {
		return ipv6End;
	}

	public void setIpv6End(String ipv6End) {
		this.ipv6End = ipv6End;
	}

	public String getIpVersions() {
		return ipVersions;
	}

	public void setIpVersions(String ipVersions) {
		this.ipVersions = ipVersions;
	}
	public static void main(String[] args) {
		//设备名称|设备型号|SNMP版本|ipV4地址|ipV6地址|读共同体|写共同体|认证密钥|私有密钥|所属子网|描述
		String addSwitchScript="|紫荆14-205.2|锐捷|2c|59.66.205.2||tUnEtswItch|tUnEtswItch|||紫荆14\n";
		String[] scriptArray=addSwitchScript.split("\n");
		for(int i=0;i<scriptArray.length;i++){
			System.out.println(scriptArray[i]);
			String[] switchInfo=scriptArray[i].split("\\|");
			System.out.println("length:"+switchInfo.length);
			for(int j=0;j<switchInfo.length;j++){
				System.out.println(switchInfo[j]+"....");
			}
			
			
		}
	}
}
