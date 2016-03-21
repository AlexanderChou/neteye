package com.topo.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.base.model.DeviceIcon;
import com.base.model.View;
import com.base.service.DeviceTypeService;
import com.base.service.InitService;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.config.dao.DeviceDAO;
import com.config.dto.Info;
import com.opensymphony.xwork2.ActionSupport;
import com.topo.dao.FileDAO;
import com.topo.dao.TopoHisDAO;
import com.topo.dto.Device;
import com.topo.dto.Router;

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
/**
 * <p>Title: 系统初始化</p>
 * <p>Description: 对拓扑发现结果进行编辑后保存，系统进行初始化，同时生成视图文件</p>
 * @version 1.0
 * @author 郭玺 update:李宪亮 赵一
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */

public class TopoSaveAction extends ActionSupport {
	private String topoViewName;
	private String viewId;
	private String[] devices;
	private String[] initLinks;
	private Info info;
	private int flag = 0;
	private String homePage;
	private String description;
	private List<Device> deviceList;
	private String[] deviceArr;
	private String disName;
	private Logger logger = Logger.getLogger(TopoSaveAction.class.getName());
	private ViewService service = new ViewService();
	
	
	/**
	 * 这个action方法为过滤 devices 中的设备
	 * @return
	 * @throws Exception 
	 */
	public String resultFilter() throws Exception{
		/** 判断名字是否重复 */
		View view = service.isNameUnique(topoViewName);
		if(view==null && devices.length > 0 && StringUtils.isNotEmpty(devices[0])){
			/* 拼接lookbackIp 的sql 字符串 */
			StringBuffer loobackIp = new StringBuffer();
			for (String deviceStr : devices) {
				if (StringUtils.isNotEmpty(deviceStr)) {
					String lookbackIp = deviceStr.split("\\|\\|")[1];
					loobackIp.append("'" + lookbackIp + "'");
					loobackIp.append(",");
				}
			}
			
			
			/* 抽查出数据库中具有相同loobackIp的设备  */
			TopoHisDAO topoHisDAO = new TopoHisDAO();
			List<com.base.model.Device> depulicatedeviceList = topoHisDAO.getDuplicationDeviceByLookBackIPs(loobackIp.toString());
			
			/* 将 lookbackIp 不重复的设备 信息保存到  txt 文件中   并返回前台所需要的现实信息  */
			deviceList = topoHisDAO.saveDeviceAsText(devices, loobackIp.toString(), depulicatedeviceList, topoViewName, disName);
			
			/* 将链路信息传递到页面 下一步使用  只传递使用    注：没有更换jar包前的方法是fromArray,更改为fromObject后不知是否可以，未进行测试*/
			//ServletActionContext.getRequest().setAttribute("initLinks", JSONArray.fromArray(initLinks).toString());
			//ServletActionContext.getRequest().setAttribute("devices", JSONArray.fromArray(devices).toString());
			ServletActionContext.getRequest().setAttribute("initLinks", JSONArray.fromObject(initLinks).toString());
			ServletActionContext.getRequest().setAttribute("devices", JSONArray.fromObject(devices).toString());
		} else {
			return ERROR;
		}
		return SUCCESS;
		
	}
	
	/**
	 * 这个方法为 在对结果集  devices 进行处理后 对拓扑进行保存
	 * @return
	 * @throws Exception
	 */
	public String topoSave() throws Exception {
		List<Router> routers = new ArrayList<Router>();
		info = new Info();
		
		/* 对重复设备的处理  */
		TopoHisDAO topoHisDAO = new TopoHisDAO();
		FileDAO fileDAO = new FileDAO();
		Map<Long, String> updateDevices =  new HashMap<Long, String>();
		//Map<String, Long> ignoreDevices =  new HashMap<String, Long>();
		Map<String, Object> ignoreDevices =  new HashMap<String, Object>();
		if (deviceArr.length > 0 && StringUtils.isNotEmpty(deviceArr[0])) {
			List<Object> resultList = topoHisDAO.updateDuplicationOfDevice(deviceArr, topoViewName);
			
			Object[] deleteDevices = (Object[])resultList.get(0);
			updateDevices = (HashMap<Long, String>)resultList.get(1);
			//ignoreDevices = (HashMap<String, Long>)resultList.get(2);
			ignoreDevices = (HashMap<String, Object>)resultList.get(2);
			//用户编辑结束，点击“保存”，开始进行系统初始化
			
			/** 从前台得到参数 devices 和links 将devices 存为txt文件  */
			int index = 0;
			if (deleteDevices != null) {
				DeviceTypeService typeService=new DeviceTypeService();
				String[] content = new String[deleteDevices.length];
				for (Object device : deleteDevices) {
					String[] attrs = device.toString().split(";");
					String deviceTypeId=Long.toString( typeService.getId(attrs[5].trim()));
					content[index] =deviceTypeId+"//"+ attrs[2].trim() + "//" + attrs[4].trim() + "//" + attrs[1].trim();
					index++;
				}
				fileDAO.topoSaveInitText(content, topoViewName, true);
			}
		}
		
		/** 调用perl命令 对device进行snmp验证 */
		String textDataFilePath = Constants.webRealPath + "file/topo/data/" + topoViewName + ".txt";
		File txtFile = new File(textDataFilePath);
		if (txtFile.exists()) {
			String cmd = "initial --file " + textDataFilePath +" 2>1 >/dev/null &";
			try{ 
				Process ps=java.lang.Runtime.getRuntime().exec(cmd);  
				ps.getErrorStream();
				ps.waitFor();
			}catch(java.io.IOException e){
				e.printStackTrace();             
			}
		}
		
		/** 检查文件是否存在 存在的话读取文件 生成视图文件*/
		String targetFilePath = Constants.webRealPath + "/file/topo/data/" + topoViewName + ".xml";
		
		File file = new File(targetFilePath);
		//更改该对象类型
		//Map<String, Long> map = new HashMap<String, Long>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (file.exists()) {
			map = fileDAO.getRoueterIdFromDataFile(topoViewName);
		}
		
		/* 添加更新的设备Id */
		
		Set<Long> set = updateDevices.keySet();
		for (Long key : set) {
			map.put(updateDevices.get(key), key);
		}

		/* 添加或略操作的 设备 */
		if (!ignoreDevices.isEmpty()) {
			map.putAll(ignoreDevices);
		}
		
		if(file.exists() || !map.isEmpty()){
			//如果数据库中已有loopbackIP地址的记录，此时    map  中就不会保存该设备
			boolean iconFlag = false;
			try {				
				/** perl 程序执行完后 得到 设备在数据库中的id 在下面的程序中给设备进行赋值 */
				for(int i=0;i<devices.length;i++){
					String[] r = devices[i].toString().split("\\|\\|");
					if (map.containsKey(r[0])) {
						Router router = new Router();
						router.setName(r[0]);
						//router.setId(map.get(r[0]));
						//此处判断设备的图标类型
						String[] idOrDescr = ((String)map.get(r[0])).split("\\|\\|");
						if(idOrDescr.length>1){
							String sysDescr = idOrDescr[1];
							long initType = Long.parseLong(r[4]);
							//从数据库中取出所有图标类型，然后与该设备的描述进行匹配
							 List<DeviceIcon> icons = new DeviceDAO().getAllDeviceIcons("model");
							 for(DeviceIcon icon:icons){
								 String manufacture = icon.getManufacture();
								 sysDescr = sysDescr.toLowerCase();
								 manufacture = manufacture.toLowerCase();
								 if(sysDescr.indexOf(manufacture)!=-1){
									 if(icon.getDeviceType().getId()==initType){
										 router.setIconId(icon.getId());//新添加的图标id
										 router.setImgPath(icon.getIconFile());
										 iconFlag = true;
										 break;
									 }
								 }
							 }
							
						}
						router.setId(Long.parseLong(idOrDescr[0]));
						router.setIpv4(r[1]);
						router.setCommunity(r[2]);
						router.setVersion(r[3]);
						router.setDeviceType(Integer.parseInt(r[4]));
						router.setRX(Integer.parseInt(r[5]));
						router.setRY(Integer.parseInt(r[6]));
						if(!iconFlag){
							router.setImgPath(r[7]);
							router.setIconId(Long.parseLong(r[4]));
						}
						routers.add(router);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				flag = 1;
			}
			
			info = new InitService().addNodesByTopo(routers,initLinks,topoViewName,description, homePage);
			if(info.getViewId()!=null && !info.getViewId().equals("")){
				viewId = info.getViewId();
			}
		}
		return SUCCESS;
	}
	public String[] getDevices() {
		return devices;
	}
	
	public void setDevices(String[] devices) {
		if (devices != null && devices.length == 1) {
			devices = devices[0].split(",");
		}
		this.devices = devices;
	}
	
	public Info getInfo() {
		return info;
	}
	
	public void setInfo(Info info) {
		this.info = info;
	}	
	
	public String getHomePage() {
		return homePage;
	}
	
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String[] getInitLinks() {
		return initLinks;
	}
	
	public void setInitLinks(String[] initLinks) {
		if (initLinks != null && initLinks.length == 1) {
			initLinks = initLinks[0].split(",");
		}
		this.initLinks = initLinks;
	}

	public List<Device> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}

	public String getTopoViewName() {
		return topoViewName;
	}

	public void setTopoViewName(String topoViewName) {
		this.topoViewName = topoViewName;
	}

	public String[] getDeviceArr() {
		return deviceArr;
	}

	public void setDeviceArr(String[] deviceArr) {
		this.deviceArr = deviceArr;
	}

	public String getDisName() {
		return disName;
	}

	public void setDisName(String disName) {
		this.disName = disName;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
}