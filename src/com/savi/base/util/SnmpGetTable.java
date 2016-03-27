package com.savi.base.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import com.adventnet.snmp.beans.SnmpTarget;
import com.adventnet.snmp.mibs.MibException;
import com.adventnet.snmp.mibs.MibNode;
import com.adventnet.snmp.mibs.MibOperations;
import com.adventnet.snmp.snmp2.SnmpAPI;
import com.adventnet.snmp.snmp2.SnmpOID;
import com.adventnet.snmp.snmp2.SnmpString;
import com.adventnet.snmp.snmp2.SnmpVar;
import com.adventnet.snmp.snmp2.SnmpVarBind;
import com.savi.base.model.Switchbasicinfo;
import com.savi.collection.dao.SwitchBasicInfoDao;

public class SnmpGetTable {
	private Switchbasicinfo switchBasicInfo;
	private SnmpTarget snmpTarget;
	private int ipVersion;
	public int getIpVersion() {
		return ipVersion;
	}
	public void setIpVersion(int ipVersion) {
		this.ipVersion = ipVersion;
	}
	public SnmpGetTable(Switchbasicinfo switchBasicInfo){
		this.switchBasicInfo=switchBasicInfo;
   		synchronized(Constants.lock){
   			initializeSnmpTarget();
   		}
	}
	private void initializeSnmpTarget(){
    	snmpTarget = new SnmpTarget();
    	//snmpTarget.setOverwriteCMI(true);
    	String SNMPVersion=switchBasicInfo.getSnmpVersion();
    	if(switchBasicInfo.getIpv4address()==null||switchBasicInfo.getIpv4address().equals("")){
    		snmpTarget.setTargetHost(switchBasicInfo.getIpv6address());
    		ipVersion=6;
    	}else{
    		snmpTarget.setTargetHost(switchBasicInfo.getIpv4address());
    		ipVersion=4;
    	}   	 
    	snmpTarget.setTargetPort(161);
    	snmpTarget.setRetries(2);
    	snmpTarget.setTimeout(2);
    	snmpTarget.setMaxNumRows(3000);
    	if(SNMPVersion.equals("1")){
    	   	//snmpTarget.setLoadFromCompiledMibs(true);
        	//snmpTarget.setReadDesc(false);
    		snmpTarget.setSnmpVersion( SnmpTarget.VERSION1 ) ;
    		snmpTarget.setCommunity(switchBasicInfo.getReadCommunity()); 
    	}else if(SNMPVersion.equals("2c")){
    	   	//snmpTarget.setLoadFromCompiledMibs(true);
        	//snmpTarget.setReadDesc(false); 
    		snmpTarget.setSnmpVersion( SnmpTarget.VERSION2C ) ;
    		snmpTarget.setCommunity(switchBasicInfo.getReadCommunity());
    	}else{
    		//snmpTarget.setLoadFromCompiledMibs(true);
    		//snmpTarget.setReadDesc(false);
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
    		 			
    		//System.out.println(Constants.webRealPath);	
    		//Constants.webRealPath="D:/workspace_europa/savimanager/WebContent/";
    		//Constants.webRealPath="D:/workspace_europa/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/savimanager/";
    		//snmpTarget.setMibPath(Constants.webRealPath+"mib/");
    		if(Constants.loadMibCount==0){
    			//这里对于SnmpTarget类，只要有一个实例调用了loadMibs方法，该类关联的static属性将被正确设置，因此无需
    			//调用第二次，当然多次调用应该其他的调用不会产生效果（这个是推测），对于设置snmpTarget.setLoadFromCompiledMibs(true);
    			//当snmp版本号相同的时候可以这样设置，否则让一个v3版本的snmpTarget去解析v2版本的.cmi预编译文件会出错。
    			//而如果我们设置当v2版本的时候用snmpTarget.setLoadFromCompiledMibs(true)，而v3版本的时候用
    			//snmpTarget.setLoadFromCompiledMibs(false)也不行，理论上是可行的，多个线程都在跑如果恰好第一个load的
    			//线程是v2版本的则解析.cmi预编译文件，如果是v3版本的就解析源文件，但事实却会发生错误，分析的原因可能是对于
    			//一个应用，最好只调用一次snmpTarget.setLoadFromCompiledMibs（）方法，否则多个线程也会出错。
            	snmpTarget.setMibPath(Constants.webRealPath+"mib/");
            	snmpTarget.loadMibs("HH3C-DOT11-REF-MIB");
                snmpTarget.loadMibs("HH3C-DOT11-ACMT-MIB");
            	snmpTarget.loadMibs("HH3C-DOT11-APMT-MIB");
            	snmpTarget.loadMibs("HH3C-DOT11-CFG-MIB");
            	snmpTarget.loadMibs("HH3C-DOT11-STATION-MIB");
            	snmpTarget.loadMibs("HH3C-OID-MIB");
            	snmpTarget.loadMibs("SAVI-WLAN-MIB");
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
	private int[] findMaxZeroLength(String ipv6StringTemp[]){
		int start=0,length=0,tempStart=0,tempLength=0,flag=1;
		for(int i=0;i<ipv6StringTemp.length;i++){
			if(flag==1&&ipv6StringTemp[i].equals("0")){
				tempStart=i;
				flag=0;
				tempLength++;
			}else if(flag==0&&ipv6StringTemp[i].equals("0")){
				tempLength++;
			}else if(flag==0&&!ipv6StringTemp[i].equals("0")){
				flag=1;
				if(length<tempLength){
					length=tempLength;
					start=tempStart;
				}
			}
		}
		int result[]={start,length};
		return result ;
	}
	private String covertToIPv6Format(String ipv6StringTemp[]){
		String result="";
		int zeroStartEnd[]=findMaxZeroLength(ipv6StringTemp);
		if(zeroStartEnd[1]<=1){
			for(int i=0;i<ipv6StringTemp.length;i++){
				if(i==ipv6StringTemp.length-1){
					result+=ipv6StringTemp[i];
				}else{
					result+=ipv6StringTemp[i]+":";
				}
			}
		}else{
			for(int i=0;i<zeroStartEnd[0];i++){
				result+=ipv6StringTemp[i]+":";
			}
			result+=":";
			for(int i=zeroStartEnd[0]+zeroStartEnd[1];i<ipv6StringTemp.length;i++){
				if(i==ipv6StringTemp.length-1){
					result+=ipv6StringTemp[i];
				}else{
					result+=ipv6StringTemp[i]+":";
				}
			}
		}
		return result;
	}
	private String[][] parseIndex(String tableName,String combinedIndexes[]){
		if(tableName.equals("saviObjectsBindingTable")){
			String parseResult[][]=new String[combinedIndexes.length][4];
			for(int i=0;i<combinedIndexes.length;i++){
				String recordTemp[]=combinedIndexes[i].split("\\.");
				parseResult[i][0]=recordTemp[0];
				parseResult[i][1]=recordTemp[1];
				parseResult[i][2]=recordTemp[2];
				if(recordTemp[3].equals("16")){
					String ipv6StringTemp[]=new String[8];
					int count=0;
					int flag=0;
					for(int j=4;j<recordTemp.length;j+=2){
						String high=Integer.toHexString(Integer.parseInt(recordTemp[j]));
						if(high.equals("0")){
							high="";
							flag=1;
						}
						String low= Integer.toHexString(Integer.parseInt(recordTemp[j+1]));
						if(flag==0&&low.length()<2){
							low="0"+low;
						}
						if(flag==1)flag=0;
						ipv6StringTemp[count]=high+low;
						count++;
					}
					parseResult[i][3]=covertToIPv6Format(ipv6StringTemp);
				}else{
					for(int j=4;j<recordTemp.length;j++){
						if(j==recordTemp.length-1){
							parseResult[i][3]+=recordTemp[j];
						}else{
							parseResult[i][3]+=recordTemp[j]+".";
						}
					}
				}
			}
			return parseResult;
		}else if(tableName.equals("saviObjectsSystemTable")){
			String parseResult[][]=new String[combinedIndexes.length][1];
			for(int i=0;i<combinedIndexes.length;i++){
				parseResult[i][0]=combinedIndexes[i];
			}
			return parseResult;
		}else if(tableName.equals("saviObjectsIfTable")){
			String parseResult[][]=new String[combinedIndexes.length][2];
			for(int i=0;i<combinedIndexes.length;i++){
				String recordTemp[]=combinedIndexes[i].split("\\.");
				parseResult[i][0]=recordTemp[0];
				parseResult[i][1]=recordTemp[1];
			}
			return parseResult;
		}else if(tableName.equals("saviObjectsFilteringTable")){
			String parseResult[][]=new String[combinedIndexes.length][3];
			for(int i=0;i<combinedIndexes.length;i++){
				String recordTemp[]=combinedIndexes[i].split("\\.");
				parseResult[i][0]=recordTemp[0];
				parseResult[i][1]=recordTemp[1];
				if(recordTemp[2].equals("16")){
					String ipv6StringTemp[]=new String[8];
					int count=0;
					int flag=0;
					for(int j=3;j<recordTemp.length;j+=2){
						String high=Integer.toHexString(Integer.parseInt(recordTemp[j]));
						if(high.equals("0")){
							high="";
							flag=1;
						}
						String low= Integer.toHexString(Integer.parseInt(recordTemp[j+1]));
						if(flag==0&&low.length()<2){
							low="0"+low;
						}
						if(flag==1)flag=0;
						ipv6StringTemp[count]=high+low;
						count++;
					}
					parseResult[i][2]=covertToIPv6Format(ipv6StringTemp);
				}else{
					for(int j=3;j<recordTemp.length;j++){
						if(j==recordTemp.length-1){
							parseResult[i][2]+=recordTemp[j];
						}else{
							parseResult[i][2]+=recordTemp[j]+".";
						}
					}
				}
			}
			return parseResult;
		}
		return null;
	}
	@SuppressWarnings({"unchecked"})
	private Vector getIndexNames(String tableName){
		Vector indexNames=new Vector();
		if(tableName.equals("saviObjectsBindingTable")){
			indexNames.add("saviObjectsBindingIpAddressType");
			indexNames.add("saviObjectsBindingType");
			indexNames.add("saviObjectsBindingIfIndex");
			indexNames.add("saviObjectsBindingIpAddress");
			return indexNames;
		}else if(tableName.equals("saviObjectsSystemTable")){
			indexNames.add("saviObjectsSystemIPVersion");
			return indexNames;
		}else if(tableName.equals("saviObjectsIfTable")){
			indexNames.add("saviObjectsIfIPVersion");
			indexNames.add("saviObjectsIfIfIndex");
			return indexNames;
		}else if(tableName.equals("saviObjectsFilteringTable")){
			indexNames.add("saviObjectsFilteringIpAddressType");
			indexNames.add("saviObjectsFilteringIfIndex");
			indexNames.add("saviObjectsFilteringIpAddress");
			return indexNames;
		}
		return null;
	}
	@SuppressWarnings({"unchecked"})
	public String[][] getTableData(String tableName){
	    MibOperations mibOps = snmpTarget.getMibOperations();
	    SnmpOID tableOID = mibOps.getSnmpOID(tableName);   
	    MibNode tableNode = mibOps.getMibNode(tableOID);
        if (tableNode == null) { // could not get table MIB node
        	System.err.println("Cannot find MIB node for table.  Correct MIB must be loaded:");  
        	return null;
        }
        Vector columns = tableNode.getTableItems();
        //Vector indexNames=tableNode.getIndexNames();
        Vector indexNames=getIndexNames(tableName);
        if ( (columns == null) || (columns.size() == 0) ) {
        	System.err.println("Not a table.No columns:");  
        	return null;
        } 
        // We need to confirm the first column is read-accessible
        while (columns.size() > 0) {
        	SnmpOID firstOID = mibOps.getSnmpOID((String)columns.elementAt(0));
        	MibNode firstNode = mibOps.getMibNode(firstOID);
        	if((firstNode.getAccess()!= SnmpAPI.RONLY)&&(firstNode.getAccess()!= SnmpAPI.RWRITE)&&(firstNode.getAccess()!= SnmpAPI.RCREATE) ) { 
        		columns.removeElementAt(0);
        	} else break;
        }
        // create OID array from table columns
        String oids[] = new String[columns.size()];
        for (int i=0;i<oids.length;i++) oids[i] = (String)columns.elementAt(i);
        snmpTarget.setObjectIDList(oids);
        SnmpVarBind resultBind[][]=snmpTarget.snmpGetAllVariableBindings();
        if (resultBind == null) {
        	if(ipVersion==6&&switchBasicInfo.getIpv4address()!=null&&!switchBasicInfo.getIpv4address().equals("")){
        		snmpTarget.setTargetHost(switchBasicInfo.getIpv4address());
        		ipVersion=4;
        		resultBind=snmpTarget.snmpGetAllVariableBindings();
        	}else if(ipVersion==4&&switchBasicInfo.getIpv6address()!=null&&!switchBasicInfo.getIpv6address().equals("")){
        		snmpTarget.setTargetHost(switchBasicInfo.getIpv6address());
        		ipVersion=6;
        		resultBind=snmpTarget.snmpGetAllVariableBindings();
        	}
        }
        if (resultBind == null) {
        	//System.err.println("Request failed or timed out. \n"+snmpTarget.getErrorString());
        	return null;
        }
        int rowNum=resultBind.length+1;
        int columnNum=resultBind[0].length+indexNames.size();
        String result[][]=new String[rowNum][columnNum];//result中的第一行是每一列的名字
        //第一行的前几列是主键的名字
        for(int i=0;i<indexNames.size();i++){
        	result[0][i]=(String)indexNames.get(i);
        }
        //第一行的后几列是各个非主键列的名字
        for(int i=indexNames.size();i<columnNum;i++){
        	result[0][i]=oids[i-indexNames.size()];
        }
        //将非主键的列的各行值填好
    	for (int i=0;i<resultBind.length;i++) { // for each row
    		for (int j=0;j<resultBind[i].length;j++){ // for each column
    			SnmpVar snmpVar=resultBind[i][j].getVariable();
    			if(snmpVar instanceof SnmpString){
    				//对于mac地址，它对应的snmpVar是SnmpString类型的，需要使用toByteString，这样是十六进制的字符串，否则是乱码
    				result[i+1][j+indexNames.size()]=((SnmpString) snmpVar).toByteString();
    			}else{
    				result[i+1][j+indexNames.size()]=snmpVar.toString();
    			}	
    		}
    	}
    	SnmpOID tableFirstItemOID = mibOps.getSnmpOID(oids[0]);
    	String numberedOIDString=tableFirstItemOID.toString();
    	/*
    	MibNode tableFirstItemNode=mibOps.getMibNode(tableFirstItemOID);
    	String numberedOIDString=tableFirstItemNode.getNumberedOIDString();
    	*/
    	String combinedIndexes[]=new String[resultBind.length];
    	for(int i=0;i<resultBind.length;i++){
    		combinedIndexes[i]=resultBind[i][0].getObjectID().toString().substring(numberedOIDString.length()+1);
    	}
    	String indexValues[][]=parseIndex(tableName,combinedIndexes);
    	//将各主键的值复制到result相应的位置
    	for(int i=0;i<indexValues.length;i++){
    		for(int j=0;j<indexValues[i].length;j++){
    			result[i+1][j]=indexValues[i][j];
    		}
    	}
        return result;
	}
	
	public static void main(String[] args) {
		SwitchBasicInfoDao switchBasicInfoDao=new SwitchBasicInfoDao();
		Switchbasicinfo switchBasicInfo=switchBasicInfoDao.getSwitchBasicInfo(new Long(75));
		//for(int x=0;x<10000000;x++){
		SnmpGetTable snmpGetTable=new SnmpGetTable(switchBasicInfo);
		String result[][]=snmpGetTable.getTableData("saviObjectsFilteringTable");
		System.out.println(result==null);
		
		if(result!=null){
			for(int i=0;i<result.length;i++){
				for(int j=0;j<result[i].length;j++){
					System.out.print(result[i][j]+"           ");
				}
				System.out.print("\n");
			}
		}
		try {
			Thread.sleep(1000*20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Switchbasicinfo switchBasicInfo1=switchBasicInfoDao.getSwitchBasicInfo(new Long(40));
		//for(int x=0;x<10000000;x++){
		SnmpGetTable snmpGetTable1=new SnmpGetTable(switchBasicInfo1);
		String result1[][]=snmpGetTable1.getTableData("saviObjectsFilteringTable");
		System.out.println(result1==null);
		
		if(result1!=null){
			for(int i=0;i<result1.length;i++){
				for(int j=0;j<result1[i].length;j++){
					System.out.print(result1[i][j]+"           ");
				}
				System.out.print("\n");
			}
		}
		
		//SwitchBasicInfoDao switchBasicInfoDao2=new SwitchBasicInfoDao();
		Switchbasicinfo switchBasicInfo2=switchBasicInfoDao.getSwitchBasicInfo(new Long(75));
		//for(int x=0;x<10000000;x++){
		SnmpGetTable snmpGetTable2=new SnmpGetTable(switchBasicInfo2);
		String result2[][]=snmpGetTable2.getTableData("saviObjectsFilteringTable");
		System.out.println(result2==null);
		
		if(result2!=null){
			for(int i=0;i<result2.length;i++){
				for(int j=0;j<result2[i].length;j++){
					System.out.print(result2[i][j]+"           ");
				}
				System.out.print("\n");
			}
		}
		//}
	}
}
