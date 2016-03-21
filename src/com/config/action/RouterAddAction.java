package com.config.action;

import java.io.File;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.base.model.Device;
import com.base.model.DeviceType;
import com.base.model.IfInterface;
import com.base.service.DeviceService;
import com.base.service.DeviceTypeService;
import com.base.service.InitService;
import com.base.service.PortService;
import com.base.util.BaseAction;
import com.base.util.Constants;
import com.config.dao.DeviceDAO;

/*
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
*/
public class RouterAddAction extends BaseAction {
	private String device;
	private long deviceTypeId;
	private long style=0;
	private String error;
	private DeviceService service =new DeviceService();
	private InitService initService=new InitService();
	private DeviceTypeService serv=new DeviceTypeService();
	private Map<String,List<Device>> errorMap = new HashMap<String,List<Device>>();
	private List<Device>  repeatNodes = new ArrayList<Device>();
	private List<Device>  errorNodes = new ArrayList<Device>();
	private DeviceDAO deviceDAO = new DeviceDAO();
	private List<Device>[] errors = new ArrayList[3];
	private List<Device> existList=new ArrayList<Device>();
	private String mesg;
	private String exist="";

	@JSON(serialize=false)
	public String execute()  throws Exception{
		int nullNumber=0;
		int existNumber=0;
		int illegimageNumber=0;
		int count=0;
		int sucessNumber=0;
		String IP = "";
		String existStr="";
		List exisList=new ArrayList();
		List illegimageList=new ArrayList();
		List content=new ArrayList();
		DeviceDAO dao=new DeviceDAO();
		for(int i=0;i<3;i++){
			errors[i] = new ArrayList<Device>();
		}
		if(deviceTypeId<=2){
			if(device!=null && device.trim().length()>0){
				String a[]=(device.trim()).split("\n");
					if(a!=null){
						count=a.length;						
						for(int i=0;i<a.length;i++){
							String deviceString=Long.toString(deviceTypeId)+"//";			
							if(!a[i].trim().equals("")){
								String b[]=(a[i]+" ").split("\\|");
								if(b.length>0){										
									if(!b[1].trim().equals("")){
										IP = b[1].trim();
										deviceString+=IP+"//";
									}else{
										deviceString+="//";
									}
									if(service.isExistByIP(IP)){
										existNumber++;										
										existStr+=a[i]+";";										
										continue;
									}
									if(IP.equals("")){
										nullNumber++;
										continue;
									}									
									if(!IP.equals("")){
										try {
											InetAddress test = InetAddress.getByName(IP);
										} catch (UnknownHostException e) {
											illegimageNumber++;
											illegimageList.add(IP);
											continue;
										}
									}	
									if(!b[2].trim().equals("")){
									String tempb2 = b[2].trim();
										deviceString+=tempb2+"//";			
									}else{
										deviceString+="//";
									}
									if(!b[0].trim().equals("")){
										String tempb0 = b[0].trim();
										deviceString+=tempb0;
									}
								}
								
								sucessNumber++;
								content.add(deviceString);
							}
						}
					}
				}
			mesg="本次提交添加设备共"+count+"条。\n"+"其中成功添加"+sucessNumber+"条。\n";
			if(nullNumber>0){
				mesg=mesg+"IP地址为空的有"+nullNumber+"条。\n";
			}
			if(illegimageNumber>0){
				mesg=mesg+"IP地址不合法的有"+illegimageNumber+"条。\n";
				String temp="";
				for(int j=0;j<illegimageNumber;j++){
					temp=temp+illegimageList.get(j)+"\n";
				}
				mesg=mesg+temp;
			}
			if(content!=null&&content.size()>0){
				dao.topoSaveInitText(content, "temp", true);
				String textDataFilePath = Constants.webRealPath + "file/topo/data/temp.txt";
				File txtFile = new File(textDataFilePath);
				if (txtFile.exists()) {
					String cmd = "initial --file " + textDataFilePath +" 2>1 >/dev/null &";
					try{ 
						Process ps=java.lang.Runtime.getRuntime().exec(cmd);  
						ps.getErrorStream();
						ps.waitFor();
				        //txtFile.delete();
					}catch(java.io.IOException e){
						e.printStackTrace();             
					}
				}
			}
			if(existNumber>0){
				exist=this.toBinary(existStr);
				mesg= "exist";
			}
			return SUCCESS;
  }else if(deviceTypeId==3){
	  return SUCCESS;
  }else{
	  if(device!=null && device.trim().length()>0){
			String a[]=(device.trim()).split("\n");
					if(a!=null){
						count=a.length;
						for(int i=0;i<a.length;i++){
							String ipv6 = null;
							String description = null;
							String name = null;
							String chineseName = null;
							InetAddress address = null;
							InetAddress addressv6 = null;
							if(!a[i].trim().equals("")){
								Device device=new Device();
								String b[]=(a[i]+" ").split("\\|");
									if(b.length>0){
									if(!b[0].trim().equals("")){
										chineseName = b[0].trim();
										device.setChineseName(chineseName);
									}
									//获得ipv4信息
									if(!b[1].trim().equals("")){
										IP = b[1].trim();
										if(service.isExistByIP(IP)){
											existNumber++;
											exisList.add(IP);
											continue;
										}else{
											try {
												address = InetAddress.getByName(IP);
												if(address instanceof Inet4Address){
													device.setLoopbackIP(IP);
												}
											} catch (UnknownHostException e) {
												existNumber++;
												exisList.add(IP);
												continue;
											}
										}
									}else{
										nullNumber++;
										continue;
									}
									if(!b[2].trim().equals("")){
										name = b[2].trim();
										device.setName(name);
									}
									//获得ipv6信息
									if(!b[3].trim().equals("")){
										ipv6 = b[3].trim();
										if(service.isExistByIP(ipv6)){
											existNumber++;
											exisList.add(ipv6);
											continue;
										}else
											try {
												addressv6 = InetAddress.getByName(ipv6);
												if(addressv6 instanceof Inet6Address){
													device.setLoopbackIPv6(ipv6);
												}
											} catch (UnknownHostException e) {
												existNumber++;
												exisList.add(IP);
												continue;
											}
										}
									}else{
										nullNumber++;
										continue;
									}
									
									if(!b[4].trim().equals("")){
										device.setReadCommunity(b[4].trim());
									}
									if(!b[5].trim().equals("")){
										device.setSnmpVersion(b[5].trim());
									}
									if(!b[6].trim().equals("")){
										description = b[6].trim();
										device.setDescription(description);
									}
									if(initService.hasRepeat(device, IP,false) == null){
										//获得设备类型
										DeviceType deviceType = new DeviceTypeService().findById(deviceTypeId);
										device.setDeviceType(deviceType);
										service.save(device);
										//同时需要向interface表中增加一条记录，以便能够同其它设备间创建链路
										IfInterface ifinterface=new IfInterface();
										
										if(description!=null && !description.equals("")){
											ifinterface.setDescription(description);
										}
										if(chineseName!=null && !chineseName.equals("")){
											ifinterface.setChineseName(chineseName);
										}
										if(name!=null && !name.equals("")){
											ifinterface.setName(name);
										}
										ifinterface.setIfindex("1");
										if(IP!=null && !IP.equals("")){
											ifinterface.setIpv4(IP);						
										}										
										if(ipv6!=null && !ipv6.equals("")){
												ifinterface.setIpv6(ipv6);							
										}
										ifinterface.setMaxSpeed(Double.valueOf("0"));
										ifinterface.setSpeed(Double.valueOf("0"));
										ifinterface.setTrafficFlag(1);
										ifinterface.setDevice(device);
										new PortService().save(ifinterface);
										sucessNumber++;
									}else{
										existNumber++;//数据库已存在该设备，请核对后再添加!
									}
								}
							}
						}
					}
				}
		mesg="本次提交添加设备共"+count+"条。\n"+"其中成功添加"+sucessNumber+"条。\n";
		if(nullNumber>0){
			mesg=mesg+"IP地址为空的有"+nullNumber+"条。\n";
		}
		if(existNumber>0){
			mesg=mesg+"IP地址重复或不合法的有"+existNumber+"条。\n";
			String temp="";
			for(int j=0;j<existNumber;j++){
				temp=temp+existList.get(j)+"\n";
			}
			mesg=mesg+temp;
		}
		return SUCCESS;
  }
	
	public void saveNode(Device device,String IP) throws Exception{
		if(deviceDAO.snmpNode(device)!=-1){
			//首先判断数据库中是否有该设备(根据name和IP地址作相应的判断)
			if(initService.hasRepeat(device, IP,true) == null){
				if(deviceDAO.snmpNodeInterfaces(device)!=-1){
					Device newDevice = deviceDAO.addNode(device);
					if(newDevice==null){
						errors[0].add(device);
						errorMap.put("1", errors[0]);//添加设备时出现例外！
					}
				}else{
					service.save(device);
					errors[1].add(device);
					errorMap.put("2", errors[1]);//通过SNMP获得该设备端口时出现例外，但该设备已添加至数据库！
				}
			}else{
				repeatNodes.add(device);//数据库已存在该设备，请核对后再添加!
			}
		}else{
			//首先判断数据库中是否有该设备(根据chineseName和IP作相应判断)
			if(initService.hasRepeat(device, IP,false) == null){
				service.save(device);
				errors[2].add(device);
				errorMap.put("3", errors[2]);//该设备无法通过SNMP获得相应信息，但已添加至数据库！
			}else{
				repeatNodes.add(device);//数据库已存在该设备，请核对后再添加!
			}
		}
	}
	public String toBinary(String str){
	        char[] strChar = str.toCharArray();
	        String result = "";
	        for(int i = 0; i < strChar.length; i++){
	            
	            result += Integer.toBinaryString(strChar[i]) + " ";
	        }
	        return result;
	        
	    }
	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Map<String, List<Device>> getErrorMap() {
		return errorMap;
	}

	public void setErrorMap(Map<String, List<Device>> errorMap) {
		this.errorMap = errorMap;
	}

	public List<Device> getRepeatNodes() {
		return repeatNodes;
	}

	public void setRepeatNodes(List<Device> repeatNodes) {
		this.repeatNodes = repeatNodes;
	}

	public List<Device> getErrorNodes() {
		return errorNodes;
	}

	public void setErrorNodes(List<Device> errorNodes) {
		this.errorNodes = errorNodes;
	}

	public long getStyle() {
		return style;
	}

	public void setStyle(long style) {
		this.style = style;
	}

	public List<Device> getExistList() {
		return existList;
	}

	public void setExistList(List<Device> existList) {
		this.existList = existList;
	}

	public String getMesg() {
		return mesg;
	}

	public void setMesg(String mesg) {
		this.mesg = mesg;
	}
	
	public String getExist() {
		return exist;
	}

	public void setExist(String exist) {
		this.exist = exist;
	}
	  public String toStr(String binStr){
	        String[] tempStr = StrToStrArray(binStr);
	        char[] tempChar = new char[tempStr.length];
	        for(int i = 0; i < tempStr.length; i++){
	            tempChar[i] = toChar(tempStr[i]);
	        }
	        return String.valueOf(tempChar);
	    }
	  private String[] StrToStrArray(String str){
	        return str.split(" ");
	    }
	   private char toChar(String binStr){
	        int[] temp = binStrToIntArray(binStr);
	        int sum = 0;
	        
	        for(int i = 0; i < temp.length; i++){

	            sum += temp[temp.length - 1 - i] << i;

	        }
	    
	        return (char)sum;
	        
	    }
	   private int[] binStrToIntArray(String binStr){
	        
	        char[] temp = binStr.toCharArray();
	        int[] result = new int[temp.length];
	    
	        for(int i = 0; i < temp.length; i++){
	            result[i] = temp[i] - 48;
	        }
	        return result;
	        
	    }
}