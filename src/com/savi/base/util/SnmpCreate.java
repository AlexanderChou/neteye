package com.savi.base.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.adventnet.snmp.beans.DataException;
import com.adventnet.snmp.beans.SnmpTarget;
import com.adventnet.snmp.mibs.MibException;
import com.adventnet.snmp.mibs.MibOperations;
import com.adventnet.snmp.snmp2.SnmpOID;
import com.savi.base.model.Switchbasicinfo;
import com.savi.collection.dao.SwitchBasicInfoDao;

public class SnmpCreate {
	private Switchbasicinfo switchBasicInfo;
	private SnmpTarget snmpTarget;
	private int ipVersion;
	public int getIpVersion() {
		return ipVersion;
	}
	public void setIpVersion(int ipVersion) {
		this.ipVersion = ipVersion;
	}
	public SnmpCreate(Switchbasicinfo switchBasicInfo){
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
	 * 各参数的意义与SAVI表中基本一样，其中ipVersion对应的是ipAddressType
	 * 返回true表示创建成功，false表示创建失败	
	 */
	//TODO:在绑定表中创建一个静态绑定（增加一行）
	public boolean snmpCreate(String ipVersion,String ifIndex,String ipAddress,String macAddress,String lifeTime){
		MibOperations mibOps = snmpTarget.getMibOperations();
		String result[]=null;
		SnmpOID OIDs[]=new SnmpOID[4];
		String ipAddressOctalString=toOctal(ipAddress,ipVersion);
		if(Integer.parseInt(ipVersion)==2){
			String index=ipVersion+".1."+ifIndex+".16."+ipAddressOctalString;
			OIDs[0] = mibOps.getSnmpOID("saviObjectsBindingMacAddr."+index);
		    OIDs[1] = mibOps.getSnmpOID("saviObjectsBindingState."+index);
		    OIDs[2] = mibOps.getSnmpOID("saviObjectsBindingLifetime."+index);
		    OIDs[3] = mibOps.getSnmpOID("saviObjectsBindingRowStatus."+index);
		}else{
			String index=ipVersion+".1."+ifIndex+".4."+ipAddressOctalString;
			OIDs[0] = mibOps.getSnmpOID("saviObjectsBindingMacAddr."+index);
		    OIDs[1] = mibOps.getSnmpOID("saviObjectsBindingState."+index);
		    OIDs[2] = mibOps.getSnmpOID("saviObjectsBindingLifetime."+index);
		    OIDs[3] = mibOps.getSnmpOID("saviObjectsBindingRowStatus."+index);
		}
	    snmpTarget.setSnmpOIDList(OIDs);
	    String[] values ={ macAddress, "5", lifeTime,"4"};
	    try	{
	    	result=snmpTarget.snmpSetList(values);
	    }catch (DataException e) {
			e.printStackTrace();
		}
        if (result == null) {
        	if(this.ipVersion==6&&switchBasicInfo.getIpv4address()!=null&&!switchBasicInfo.getIpv4address().equals("")){
        		snmpTarget.setTargetHost(switchBasicInfo.getIpv4address());
        		this.ipVersion=4;
        		try {
					result=snmpTarget.snmpSetList(values);
				} catch (DataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}else if(this.ipVersion==4&&switchBasicInfo.getIpv6address()!=null&&!switchBasicInfo.getIpv6address().equals("")){
        		snmpTarget.setTargetHost(switchBasicInfo.getIpv6address());
        		this.ipVersion=6;
        		try {
					result=snmpTarget.snmpSetList(values);
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
	private String toOctal(String ipAddress,String ipVersion){
		if(Integer.parseInt(ipVersion)==2){
			String octalString="";
			int middle=ipAddress.indexOf("::");
			if(middle!=-1){
				String stringHead=ipAddress.substring(0,middle);
				String headHexTemp[]=stringHead.split(":");
				List<String> headOctTemp=new ArrayList<String>();
				for(int i=0;i<headHexTemp.length;i++){
					if(headHexTemp[i].length()!=4){
						headHexTemp[i]="000"+headHexTemp[i];
						headHexTemp[i]=headHexTemp[i].substring(headHexTemp[i].length()-4);
					}
					String low=headHexTemp[i].substring(2,4);
					String high=headHexTemp[i].substring(0,2);
					headOctTemp.add(Integer.valueOf(high,16).toString());
					headOctTemp.add(Integer.valueOf(low,16).toString());
				}
				String stringTail=ipAddress.substring(middle+2);
				String tailHexTemp[]=stringTail.split(":");
				List<String> tailOctTemp=new ArrayList<String>();
				for(int i=0;i<tailHexTemp.length;i++){
					if(tailHexTemp[i].length()!=4){
						tailHexTemp[i]="000"+tailHexTemp[i];
						tailHexTemp[i]=tailHexTemp[i].substring(tailHexTemp[i].length()-4);
					}
					String low=tailHexTemp[i].substring(2,4);
					String high=tailHexTemp[i].substring(0,2);
					tailOctTemp.add(Integer.valueOf(high,16).toString());
					tailOctTemp.add(Integer.valueOf(low,16).toString());
				}
				for(int i=0;i<headOctTemp.size();i++){
					octalString+=headOctTemp.get(i)+".";
				}
				for(int i=0;i<16-headOctTemp.size()-tailOctTemp.size();i++){
					octalString+="0.";
				}
				for(int i=0;i<tailOctTemp.size();i++){
					if(i!=tailOctTemp.size()-1){
						octalString+=tailOctTemp.get(i)+".";
					}else{
						octalString+=tailOctTemp.get(i);
					}
				}
				return octalString;
			}else{
				String hexTemp[]=ipAddress.split(":");
				List<String> octTemp=new ArrayList<String>();
				for(int i=0;i<hexTemp.length;i++){
					String low=hexTemp[i].substring(2,4);
					String high=hexTemp[i].substring(0,2);
					octTemp.add(Integer.valueOf(high,16).toString());
					octTemp.add(Integer.valueOf(low,16).toString());
				}
				for(int i=0;i<octTemp.size();i++){
					if(i!=octTemp.size()-1){
						octalString+=octTemp.get(i)+".";
					}else{
						octalString+=octTemp.get(i);
					}
				}
				return octalString;
			}
		}else{
			return ipAddress;
		}
	}
	public static void main(String[] args) {
		SwitchBasicInfoDao switchBasicInfoDao=new SwitchBasicInfoDao();
		Switchbasicinfo switchBasicInfo=switchBasicInfoDao.getSwitchBasicInfo(new Long(1));
		SnmpCreate snmpCreateTask=new SnmpCreate(switchBasicInfo);
		String indexes[]=new String[2];
		indexes[0]="2";
		indexes[1]="21";
		boolean flag=snmpCreateTask.snmpCreate("2", "21", "2001:1221:0043:1000:9876:2222:8888:0008", "00:50:BF:07:ED:2D", "1000");
		System.out.println(flag);
	}
}
