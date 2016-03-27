package com.config.action;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;

import com.base.model.Device;
import com.base.model.DeviceType;
import com.base.model.IfInterface;
import com.base.service.DeviceService;
import com.base.service.DeviceTypeService;
import com.base.service.PortService;
import com.base.util.BaseAction;
import com.config.dao.ServerDAO;


public class ServerAddAction extends BaseAction{
	private String http;
	private String dns;
	private String pop3;
	private String smtp;
	private String ftp;
	private String snmp;
	private String IP;
	private String IPv6;
	private String  snmpIp;
	private String community;
	private String ftpServer;
	private int ftpPort;
	private String ftpUsername;
	private String ftpPasswd;
	private String ftpUrl;
	private String httpUrl;
	private int httpPort;
	private String dnsServer;
	private int dnsPort;
	private String smtpServer;
	private String smtpSource;
	private String smtpDestination;
	private int smtpPort;
	private String pop3Server;
	private int pop3Port;
	private String pop3Username;
	private String pop3Passwd;
	private String name;
	private String chineseName;
	private String description;
	private String mesg;
	private boolean success;

	public String execute() throws Exception{
		DeviceService service=new DeviceService();
		Device device=new Device();
		InetAddress address = null;
		InetAddress addressv6 = null;
		String lastIP = null;
		if(IP!=null&&!service.isExistByIP(IP)){
			try {
				address = InetAddress.getByName(IP);
				if(address instanceof Inet4Address){
					device.setLoopbackIP(IP);
					lastIP = IP;
				}else{
					mesg="IPv4地址不正确，请重新填写！";
					success=false;
					return SUCCESS ;
				}
			} catch (UnknownHostException e) {
				mesg="IPv4地址不正确，请重新填写！";
				success=false;
				return SUCCESS ;
			}
		}else{
			mesg="IPv4地址重复，请重新填写！";
			success=false;
			return SUCCESS;
		}
		
		if(IPv6!=null&&!service.isExistByIP(IPv6)){
			try {
				addressv6 = InetAddress.getByName(IPv6);
				if(addressv6 instanceof Inet6Address){
					device.setLoopbackIPv6(IPv6);
					lastIP = IPv6;
				}else{
					mesg="IPv6地址不正确，请重新填写！";
					success=false;
					return SUCCESS ;
				}
			} catch (UnknownHostException e) {
				mesg="IPv6地址不正确，请重新填写！";
				success=false;
				return SUCCESS ;
			}
		}else{
			mesg="IPv6地址重复，请重新填写！";
			success=false;
			return SUCCESS;
		}
		
		device.setChineseName(chineseName);
		device.setName(name);
		device.setDescription(description);
		String label="";
		String server="";
		String tmp="";
		boolean flag = false;
		Hashtable table=new Hashtable();
		table.put("IP", lastIP);
		if(snmp!=null&&snmp.equals("on")){
			table.put("snmp", snmpIp+";"+community);
			server+="snmp;";
			label+=IP+";";
			flag = true;
		}
		if(ftp!=null&&ftp.equals("on")){
			if(ftpServer==null){
				ftpServer=IP;
			}
			tmp=ftpServer+";"+ftpPort+";"+ftpUsername+";"+ftpPasswd+";"+ftpUrl;
			table.put("ftp", tmp);
			label+=IP+"_"+ftpPort+";";
			server+="ftp;";
			flag = true;
		}
		if(http!=null&&http.equals("on")){
			if(httpUrl==null){
				httpUrl=IP;
			}
			tmp=httpUrl+";"+httpPort;
			table.put("http", tmp);
			label+=IP+"_"+httpPort+";";
			server+="http;";
			flag = true;
		}
		if(dns!=null&&dns.equals("on")){
			if(dnsServer==null){
				dnsServer=IP;
			}
			tmp=dnsServer+";"+dnsPort;
			table.put("dns", tmp);
			label+=IP+"_"+dnsPort+";";
			server+="dns;";
			flag = true;
		}
		if(smtp!=null&&smtp.equals("on")){
			if(smtpServer==null){
				smtpServer=IP;
			}
			tmp=smtpServer+";"+smtpSource+";"+smtpPort+";"+smtpDestination;
			table.put("smtp", tmp);
			label+=IP+"_"+smtpPort+";";
			server+="smtp;";
			flag = true;
		}
		if(pop3!=null&&pop3.equals("on")){
			if(pop3Server==null){
				pop3Server=IP;
			}
			tmp=pop3Server+";"+pop3Port+";"+pop3Username+";"+pop3Passwd;
			table.put("pop3", tmp);
			label+=IP+"_"+pop3Port;
			server+="pop3;";
			flag = true;
		}
		device.setLabel(label);
		device.setService(server);
		DeviceType deviceType=new DeviceTypeService().findById(3);
		device.setDeviceType(deviceType);
		service.save(device);
		ServerDAO dao= new ServerDAO();
		if(flag){
			dao.InsertXML(table);
		}
		//同时需要向interface表中增加一条记录，以便能够同其它设备间创建链路
		IfInterface ifinterface=new IfInterface();
		
		if(description!=null && !description.equals("")){
			ifinterface.setDescription(description);
		}
		if(chineseName!=null && !chineseName.equals("")){
			ifinterface.setChineseName(chineseName);
		}	
		ifinterface.setIfindex("1");
		if(IP!=null && !IP.equals("")){
			ifinterface.setIpv4(IP);						
		}										
		if(IPv6!=null && !IPv6.equals("")){
				ifinterface.setIpv6(IPv6);							
		}
		ifinterface.setMaxSpeed(Double.valueOf("0"));
		ifinterface.setSpeed(Double.valueOf("0"));
		ifinterface.setTrafficFlag(1);
		ifinterface.setDevice(device);
		new PortService().save(ifinterface);
		mesg="添加成功！";
		success=true;
		return SUCCESS;
	}
  
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHttp() {
		return http;
	}
	public void setHttp(String http) {
		this.http = http;
	}
	public String getDns() {
		return dns;
	}
	public void setDns(String dns) {
		this.dns = dns;
	}
	public String getPop3() {
		return pop3;
	}
	public void setPop3(String pop3) {
		this.pop3 = pop3;
	}
	public String getSmtp() {
		return smtp;
	}
	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}
	public String getFtp() {
		return ftp;
	}
	public void setFtp(String ftp) {
		this.ftp = ftp;
	}
	public String getSnmp() {
		return snmp;
	}
	public void setSnmp(String snmp) {
		this.snmp = snmp;
	}
	public String getIP() {
		return IP;
	}

	public void setIP(String ip) {
		IP = ip;
	}

	public String getSnmpIp() {
		return snmpIp;
	}

	public void setSnmpIp(String snmpIp) {
		this.snmpIp = snmpIp;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getFtpServer() {
		return ftpServer;
	}

	public void setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpUsername() {
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}

	public String getFtpPasswd() {
		return ftpPasswd;
	}

	public void setFtpPasswd(String ftpPasswd) {
		this.ftpPasswd = ftpPasswd;
	}

	public String getFtpUrl() {
		return ftpUrl;
	}

	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public int getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}

	public String getDnsServer() {
		return dnsServer;
	}

	public void setDnsServer(String dnsServer) {
		this.dnsServer = dnsServer;
	}

	public int getDnsPort() {
		return dnsPort;
	}

	public void setDnsPort(int dnsPort) {
		this.dnsPort = dnsPort;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public String getSmtpSource() {
		return smtpSource;
	}

	public void setSmtpSource(String smtpSource) {
		this.smtpSource = smtpSource;
	}

	public String getSmtpDestination() {
		return smtpDestination;
	}

	public void setSmtpDestination(String smtpDestination) {
		this.smtpDestination = smtpDestination;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getPop3Server() {
		return pop3Server;
	}

	public void setPop3Server(String pop3Server) {
		this.pop3Server = pop3Server;
	}

	public int getPop3Port() {
		return pop3Port;
	}

	public void setPop3Port(int pop3Port) {
		this.pop3Port = pop3Port;
	}

	public String getPop3Username() {
		return pop3Username;
	}

	public void setPop3Username(String pop3Username) {
		this.pop3Username = pop3Username;
	}

	public String getPop3Passwd() {
		return pop3Passwd;
	}

	public void setPop3Passwd(String pop3Passwd) {
		this.pop3Passwd = pop3Passwd;
	}
	public String getMesg() {
		return mesg;
	}

	public void setMesg(String mesg) {
		this.mesg = mesg;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getIPv6() {
		return IPv6;
	}

	public void setIPv6(String pv6) {
		IPv6 = pv6;
	}
}
