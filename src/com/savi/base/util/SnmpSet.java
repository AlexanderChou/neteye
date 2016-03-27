package com.savi.base.util;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.adventnet.snmp.beans.DataException;
import com.adventnet.snmp.beans.SnmpTarget;
import com.adventnet.snmp.mibs.MibException;
import com.savi.base.model.Switchbasicinfo;
import com.savi.collection.dao.SwitchBasicInfoDao;

public class SnmpSet {
	private Switchbasicinfo switchBasicInfo;
	private SnmpTarget snmpTarget;
	private int ipVersion;
	public int getIpVersion() {
		return ipVersion;
	}
	public void setIpVersion(int ipVersion) {
		this.ipVersion = ipVersion;
	}
	public SnmpSet(Switchbasicinfo switchBasicInfo){
		this.switchBasicInfo=switchBasicInfo;
   		synchronized(Constants.lock){
   			initializeSnmpTarget();
   		}
	}
	private void initializeSnmpTarget(){
    	snmpTarget = new SnmpTarget();
    	String SNMPVersion=switchBasicInfo.getSnmpVersion();
    	if(switchBasicInfo.getIpv4address()==null||switchBasicInfo.getIpv4address().equals("")){
    		snmpTarget.setTargetHost(switchBasicInfo.getIpv6address());
    		ipVersion=6;
    	}else{
    		snmpTarget.setTargetHost(switchBasicInfo.getIpv4address());
    		ipVersion=4;
    	}   	 
    	snmpTarget.setTargetPort(161);
    	snmpTarget.setRetries(3);
    	snmpTarget.setTimeout(3);
    	if(SNMPVersion.equals("1")){
    	   	//snmpTarget.setLoadFromCompiledMibs(true);
        	//snmpTarget.setReadDesc(false);
    		snmpTarget.setSnmpVersion( SnmpTarget.VERSION1 ) ;
    		snmpTarget.setCommunity(switchBasicInfo.getReadCommunity());
    		snmpTarget.setWriteCommunity(switchBasicInfo.getWriteCommunity()); 
    	}else if(SNMPVersion.equals("2c")){
    	   	//snmpTarget.setLoadFromCompiledMibs(true);
        	//snmpTarget.setReadDesc(false);
    		snmpTarget.setSnmpVersion( SnmpTarget.VERSION2C ) ;
    		snmpTarget.setCommunity(switchBasicInfo.getReadCommunity());
    		snmpTarget.setWriteCommunity(switchBasicInfo.getWriteCommunity()); 
    	}else{
    	   	//snmpTarget.setLoadFromCompiledMibs(false);
    	   	//snmpTarget.setReadDesc(true);
    		snmpTarget.setSnmpVersion( SnmpTarget.VERSION3 ) ;
    		snmpTarget.setSecurityLevel(SnmpTarget.AUTH_PRIV);
    		snmpTarget.setContextName(switchBasicInfo.getWriteCommunity());
    		snmpTarget.setPrincipal(switchBasicInfo.getReadCommunity());
    		snmpTarget.setAuthProtocol(SnmpTarget.MD5_AUTH);
    		snmpTarget.setAuthPassword(switchBasicInfo.getAuthKey());
    		snmpTarget.setPrivProtocol(SnmpTarget.CBC_DES);
    		snmpTarget.setPrivPassword(switchBasicInfo.getPrivateKey());
    		snmpTarget.create_v3_tables();
    	}
        try {	
			while(true){
				if(Constants.webRealPath!=null)break;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
			//Constants.webRealPath="D:/workspace_europa/savimanager/WebContent/";
    		if(Constants.loadMibCount==0){
            	snmpTarget.setMibPath(Constants.webRealPath+"mib/");
                snmpTarget.loadMibs("SAVI-MIB");
                Constants.loadMibCount=1;
    		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MibException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 第一个参数为修改值，第二个参数为修改项所在列的名字（主键不能修改），第三个参数为一个列表，依次为修改项所在行的主键值
	 * eg.如果想修改saviObjectsSystemTable表中的saviObjectsSystemMode，而修改项所在的行主键saviObjectsSystemIPVersion值为2，调用方法如下：
	 * SnmpSet snmpSetTask=new SnmpSet(switchBasicInfo);
	 * String indexes[]=new String[1];
	 * indexes[0]="2";
	 * boolean flag=snmpSetTask.snmpSet("2","saviObjectsSystemMode",indexes);
	 * 返回true表示修改成功，false表示修改失败	
	 */
	/*
	 * 其中各个表中的列名以及主键如下所示：
	 * 1.saviObjectsSystemTable：
	 *   列名有：saviObjectsSystemIPVersion、saviObjectsSystemMode、saviObjectsSystemMaxDadDelay、saviObjectsSystemMaxDadPrepareDelay
	 *   主键为：saviObjectsSystemIPVersion
	 * 2.saviObjectsIfTable:
	 *   列名有：saviObjectsIfIPVersion、saviObjectsIfIfIndex、saviObjectsIfValidationStatus、saviObjectsIfTrustStatus、saviObjectsIfFilteringNum
	 *   主键为：saviObjectsIfIPVersion、saviObjectsIfIfIndex
	 */
	//TODO:修改某个表中的某项值
	public boolean snmpSet(String value,String columnName,String indexes[]){
		String index="",result="";
		for(int i=0;i<indexes.length;i++){
			index+=indexes[i];
			if(i!=indexes.length-1){
				index+=".";
			}
		}
		snmpTarget.setObjectID(columnName+"."+index);
		try {
			result=snmpTarget.snmpSet(value);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //sets the new value
        if (result == null) {
        	if(this.ipVersion==6&&switchBasicInfo.getIpv4address()!=null&&!switchBasicInfo.getIpv4address().equals("")){
        		snmpTarget.setTargetHost(switchBasicInfo.getIpv4address());
        		this.ipVersion=4;
        		try {
        			result=snmpTarget.snmpSet(value);
				} catch (DataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}else if(this.ipVersion==4&&switchBasicInfo.getIpv6address()!=null&&!switchBasicInfo.getIpv6address().equals("")){
        		snmpTarget.setTargetHost(switchBasicInfo.getIpv6address());
        		this.ipVersion=6;
        		try {
        			result=snmpTarget.snmpSet(value);
				} catch (DataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
		if(result==null){
			return false;
		}else{
			return true;
		}
	}
	public static void main(String[] args) {
		SwitchBasicInfoDao switchBasicInfoDao=new SwitchBasicInfoDao();
		Switchbasicinfo switchBasicInfo=switchBasicInfoDao.getSwitchBasicInfo(new Long(948));
		SnmpSet snmpSetTask=new SnmpSet(switchBasicInfo);
		String indexes[]=new String[1];
		indexes[0]="2";
		//indexes[1]="1";
		String value="200";
		boolean flag=snmpSetTask.snmpSet(value, "saviObjectsSystemMaxDadDelay", indexes);
		System.out.println(flag);
	}
}
