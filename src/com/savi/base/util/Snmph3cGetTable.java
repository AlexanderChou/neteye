package com.savi.base.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import com.adventnet.snmp.beans.SnmpTarget;
import com.adventnet.snmp.mibs.MibException;
import com.adventnet.snmp.mibs.MibNode;
import com.adventnet.snmp.mibs.MibOperations;
import com.adventnet.snmp.snmp2.SnmpAPI;
import com.adventnet.snmp.snmp2.SnmpCounter64;
import com.adventnet.snmp.snmp2.SnmpIpAddress;
import com.adventnet.snmp.snmp2.SnmpOID;
import com.adventnet.snmp.snmp2.SnmpString;
import com.adventnet.snmp.snmp2.SnmpVar;
import com.adventnet.snmp.snmp2.SnmpVarBind;
import com.savi.base.model.Deviceinfo;
import com.savi.base.model.Switchbasicinfo;
import com.savi.collection.dao.SwitchBasicInfoDao;
import com.savi.show.dao.DeviceDao;

public class Snmph3cGetTable {
	private Deviceinfo deviceinfo;
	private SnmpTarget snmpTarget;
	private int ipVersion;
	public int getIpVersion() {
		return ipVersion;
	}
	public void setIpVersion(int ipVersion) {
		this.ipVersion = ipVersion;
	}
	public Snmph3cGetTable(Deviceinfo deviceinfo){
		this.deviceinfo=deviceinfo;
   		synchronized(Constants.lock){
   			initializeSnmpTarget();
   		}
	}
	
	private void initializeSnmpTarget(){
    	snmpTarget = new SnmpTarget();
    	String SNMPVersion=deviceinfo.getSnmpVersion();
    	if(deviceinfo.getIpv4address()==null||deviceinfo.getIpv4address().equals("")){
    		snmpTarget.setTargetHost(deviceinfo.getIpv6address());
    		ipVersion=6;
    	}else{
    		snmpTarget.setTargetHost(deviceinfo.getIpv4address());
    		ipVersion=4;
    	}   	 
    	snmpTarget.setTargetPort(161);
    	snmpTarget.setRetries(2);
    	snmpTarget.setTimeout(2);
    	snmpTarget.setMaxNumRows(3000);
    	if(SNMPVersion.equals("1")){
    		snmpTarget.setSnmpVersion( SnmpTarget.VERSION1 ) ;
    		snmpTarget.setCommunity(deviceinfo.getReadCommunity()); 
    	}else if(SNMPVersion.equals("2c")){
    		snmpTarget.setSnmpVersion( SnmpTarget.VERSION2C ) ;
    		snmpTarget.setCommunity(deviceinfo.getReadCommunity());
    		
    	}else{
    		snmpTarget.setSnmpVersion( SnmpTarget.VERSION3 ) ;
    		snmpTarget.setSecurityLevel(SnmpTarget.AUTH_PRIV);
    		snmpTarget.setContextName(deviceinfo.getWriteCommunity());
    		snmpTarget.setPrincipal(deviceinfo.getReadCommunity());
    		snmpTarget.setAuthProtocol(SnmpTarget.MD5_AUTH);
    		snmpTarget.setAuthPassword(deviceinfo.getAuthKey());
    		snmpTarget.setPrivProtocol(SnmpTarget.CBC_DES);
    		snmpTarget.setPrivPassword(deviceinfo.getPrivateKey());
    		snmpTarget.create_v3_tables();
    	}
        try {
        	//Constants.webRealPath="D:/MyProject/MyProject/WebContent/";
    		while(true){
    			if(Constants.webRealPath!=null)break;
    			try {
    				Thread.sleep(1000);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
        
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
		}else if(tableName.equals("hh3cDot11APObjectTable")){
			String parseResult[][]=new String[combinedIndexes.length][1];
			for(int i=0;i<combinedIndexes.length;i++){
				String recordTemp[]=combinedIndexes[i].split("\\.");
				if(recordTemp[0].equals("20")){
					String str = "";
					for(int j = 1;j < recordTemp.length;j++){
						String c = String.valueOf((char)Integer.parseInt(recordTemp[j]));
						str += c;
					}
					parseResult[i][0] = str;
				}
				
				//parseResult[i][0]=combinedIndexes[i];
				
				//parseResult[i][1]=recordTemp[1];
				//parseResult[i][2]=recordTemp[2];
				//parseResult[i][3]=recordTemp[3];
			}
			return parseResult;
		}else if(tableName.equals("hh3cDot11StationAPRelationTable")){
			String parseResult[][]=new String[combinedIndexes.length][1];
			for(int i=0;i<combinedIndexes.length;i++){
				parseResult[i][0]=combinedIndexes[i];
			}
			return parseResult;
		}else if(tableName.equals("hh3cDot11StationAssociateTable")){
			String parseResult[][]=new String[combinedIndexes.length][1];
			for(int i=0;i<combinedIndexes.length;i++){
				
				parseResult[i][0]=combinedIndexes[i];//不知道ASCII怎么转成字符，转了一下为乱码
				//String recordTemp[]=combinedIndexes[i].split("\\.");
				//StringBuffer sbu = new StringBuffer();  
				//for(int j = 0;j < recordTemp.length;j++){
				//	sbu.append(String.valueOf(Integer.parseInt(recordTemp[j])));  
				//}
				//parseResult[i][0] = sbu.toString();
				
			}
			return parseResult;
		}else if(tableName.equals("saviWlanObjectsFilteringTable")){
			String parseResult[][]=new String[combinedIndexes.length][3];
			for(int i=0;i<combinedIndexes.length;i++){
				String recordTemp[]=combinedIndexes[i].split("\\.");
				parseResult[i][0]=recordTemp[0];
				parseResult[i][1]=recordTemp[1];
				//parseResult[i][2]=recordTemp[2];
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
					parseResult[i][2]=covertToIPv6Format(ipv6StringTemp);
				}else{
					for(int j=4;j<recordTemp.length;j++){
						if(j==recordTemp.length-1){
							parseResult[i][2]+=recordTemp[j];
						}else{
							parseResult[i][2]+=recordTemp[j]+".";
						}
					}
				}
			}
			return parseResult;
		}else if(tableName.equals("hh3cDot11ServicePolicyTable")){
			String parseResult[][]=new String[combinedIndexes.length][1];
			for(int i=0;i<combinedIndexes.length;i++){
				parseResult[i][0]=combinedIndexes[i];
			}
			return parseResult;
		}else if(tableName.equals("saviWlanObjectsCountTable")){
			String parseResult[][]=new String[combinedIndexes.length][2];
			for(int i=0;i<combinedIndexes.length;i++){
				String recordTemp[]=combinedIndexes[i].split("\\.");
				parseResult[i][0]=recordTemp[0];
				parseResult[i][1]=String.valueOf((char)Integer.parseInt(recordTemp[2]));
			}
			return parseResult;
		}
		return null;
	}
	
	public static String asciiToString(String value)  {  
	    StringBuffer sbu = new StringBuffer();  
	    String[] chars = value.split(",");  
	    for (int i = 0; i < chars.length; i++) {  
	        sbu.append((char) Integer.parseInt(chars[i]));  
	    }  
	    return sbu.toString();  
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
		}else if(tableName.equals("hh3cDot11APObjectTable")){
			indexNames.add("hh3cDot11APObjID");
			//indexNames.add("hh3cDot11CurrAPIPAddress");
			//indexNames.add("hh3cDot11CurrAPMacAddress");
			//indexNames.add("hh3cDot11CurrAPName");
			
			return indexNames;
		}else if(tableName.equals("hh3cDot11StationAPRelationTable")){
			indexNames.add("hh3cDot11StationMAC");
			return indexNames;
		}else if(tableName.equals("hh3cDot11StationAssociateTable")){
			indexNames.add("hh3cDot11StationMAC");
			return indexNames;
		}else if(tableName.equals("saviWlanObjectsFilteringTable")){
			indexNames.add("saviWlanObjectsFilteringIpAddressType");
			indexNames.add("saviWlanObjectsFilteringServiceName");
			indexNames.add("saviWlanObjectsFilteringIpAddress");
			return indexNames;
		}else if(tableName.equals("hh3cDot11ServicePolicyTable")){
			indexNames.add("hh3cDot11ServicePolicyID");
			return indexNames;
		}else if(tableName.equals("saviWlanObjectsCountTable")){
			indexNames.add("saviWlanObjectsCountIPVersion");
			indexNames.add("saviWlanObjectsCountServiceString");
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
        Vector indexNames=getIndexNames(tableName);
        if ( (columns == null) || (columns.size() == 0) ) {
        	System.err.println("Not a table.No columns:");  
        	return null;
        } 
        
        while (columns.size() > 0) {
        	SnmpOID firstOID = mibOps.getSnmpOID((String)columns.elementAt(0));
        	MibNode firstNode = mibOps.getMibNode(firstOID);
        	if((firstNode.getAccess()!= SnmpAPI.RONLY)&&(firstNode.getAccess()!= SnmpAPI.RWRITE)&&(firstNode.getAccess()!= SnmpAPI.RCREATE) ) { 
        		columns.removeElementAt(0);
        	} else break;
        }
        String oids[] = new String[columns.size()];
        String oids1[] = new String[columns.size()];
        for (int i=0;i<oids.length;i++){
        	oids[i] = (String)columns.elementAt(i);
        	SnmpOID oid4 = mibOps.getSnmpOID(oids[i]);
        	oids1[i] = oid4.toString();
        }
        snmpTarget.setObjectIDList(oids1);
        
       
        
        SnmpVarBind resultBind[][]=snmpTarget.snmpGetAllVariableBindings();
        if (resultBind == null) {
        	if(ipVersion==6&&deviceinfo.getIpv4address()!=null&&!deviceinfo.getIpv4address().equals("")){
        		snmpTarget.setTargetHost(deviceinfo.getIpv4address());
        		ipVersion=4;
        		resultBind=snmpTarget.snmpGetAllVariableBindings();
        	}else if(ipVersion==4&&deviceinfo.getIpv6address()!=null&&!deviceinfo.getIpv6address().equals("")){
        		snmpTarget.setTargetHost(deviceinfo.getIpv6address());
        		ipVersion=6;
        		resultBind=snmpTarget.snmpGetAllVariableBindings();
        	}
        }
        if (resultBind == null) {
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
    			//System.out.println(snmpVar.toString());
    			if(snmpVar instanceof SnmpString){
    				//对于mac地址，它对应的snmpVar是SnmpString类型的，需要使用toByteString，这样是十六进制的字符串，否则是乱码
    				if(tableName.equals("hh3cDot11APObjectTable")){
    					if(j == 0 || j == 6){//ipADDRESS,APNAME
    						result[i+1][j+indexNames.size()]=snmpVar.toString();
    						//System.out.println(snmpVar.toString());
    					}else{
    						result[i+1][j+indexNames.size()]=((SnmpString) snmpVar).toByteString();
    						//System.out.println(((SnmpString) snmpVar).toByteString());
    					}
    				}else if(tableName.equals("hh3cDot11StationAPRelationTable")){
    					if(j == 0){
    						result[i+1][j+indexNames.size()]=snmpVar.toString();
    					}else{
    						result[i+1][j+indexNames.size()]=((SnmpString) snmpVar).toByteString();
    					}
    					
    				}else if(tableName.equals("hh3cDot11StationAssociateTable")){
    					if(j == 0 || j == 1 || j == 10){//IPADDRESS,USERNAME,SSIDNAME,MACADDRESS为22
    						result[i+1][j+indexNames.size()]=snmpVar.toString();
    					}else{
    						result[i+1][j+indexNames.size()]=((SnmpString) snmpVar).toByteString();
    					}
    				}else if(tableName.equals("saviWlanObjectsFilteringTable")){
    					result[i+1][j+indexNames.size()]=((SnmpString) snmpVar).toByteString();
    				}else if(tableName.equals("hh3cDot11ServicePolicyTable")){
    					if(j == 0){
    						result[i+1][j+indexNames.size()]=snmpVar.toString();
    					}else{
    						result[i+1][j+indexNames.size()]=((SnmpString) snmpVar).toByteString();
    					}
    				}else if(tableName.equals("saviWlanObjectsCountTable")){//未知
    					result[i+1][j+indexNames.size()]=((SnmpString) snmpVar).toByteString();
    				}else{
    					result[i+1][j+indexNames.size()]=((SnmpString) snmpVar).toByteString();
    					//System.out.println(((SnmpString) snmpVar).toByteString());
    				}
    				
    				//System.out.println(((SnmpString) snmpVar).toByteString());
    			}else{
    				if(tableName.equals("saviWlanObjectsCountTable")){
    					result[i+1][j+indexNames.size()]=((SnmpCounter64) snmpVar).getNumericValueAsString();
    				}else{
    					result[i+1][j+indexNames.size()]=snmpVar.toString();
    				}
    				
    				//System.out.println(snmpVar.toString());
    			}	
    		}
    	}
    	SnmpOID tableFirstItemOID = mibOps.getSnmpOID(oids[0]);
    	String numberedOIDString=tableFirstItemOID.toString();
    	
    	String combinedIndexes[]=new String[resultBind.length];
    	for(int i=0;i<resultBind.length;i++){
    		//Name/OID: hh3cDot11CurrAPIPAddress.20.50.49.48.50.51.53.65.49.71.81.67.49.52.65.48.48.49.53.48.48
    		//取Name/OID的第“标题列名长度+1”之后的数据，也就是20.50.49.48.50.51.53.65.49.71.81.67.49.52.65.48.48.49.53.48.48
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
		DeviceDao deviceDao=new DeviceDao();
		Deviceinfo deviceinfo=deviceDao.getDeviceinfo(new Long(1));
		Snmph3cGetTable snmpGetTable=new Snmph3cGetTable(deviceinfo);
		//String result1[][]=snmpGetTable.getTableData("hh3cDot11APObjectTable");
		String result1[][] = snmpGetTable.getTableData("hh3cDot11StationAPRelationTable");
		//String result1[][] = snmpGetTable.getTableData("hh3cDot11StationAssociateTable");
		//String result1[][] = snmpGetTable.getTableData("hh3cDot11ServicePolicyTable");
		//String result1[][] = snmpGetTable.getTableData("saviWlanObjectsFilteringTable");
		//String result1[][]=snmpGetTable.getTableData("saviWlanObjectsCountTable");
		System.out.println(result1==null);
		
		if(result1!=null){
			for(int i=0;i<result1.length;i++){
				//System.out.print(result1[i][1]+"           ");
				
				for(int j=0;j<result1[i].length;j++){
					
					System.out.print(result1[i][j]+"           ");
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
	}
}
