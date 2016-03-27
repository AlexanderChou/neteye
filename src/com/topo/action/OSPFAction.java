package com.topo.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.base.util.Constants;
import com.opensymphony.xwork2.ActionSupport;
import com.topo.dao.FileDAO;
import com.topo.dto.OSPFConfig;

public class OSPFAction extends ActionSupport{
	private String info;
	private boolean success;
	private List<OSPFConfig> configList;
	private String congfigValue;
	public String hasOSPF() throws Exception {
		String cmd = new String();
		//调用后台程序，判断OSPF是否可用
		cmd = "setup_ospf";
		Process ps=java.lang.Runtime.getRuntime().exec(cmd + " 2>1 >/dev/null &");  
		ps.getErrorStream();
		//根据后台程序的输出，检查file/topo/目录下ospf.flag该文件是否存在，存在：OSPF可用，不存在：OSPF不可用
		File file = new File(Constants.webRealPath + "file/topo/success.flag");
		if(file.exists()){
			info = "enableOSPF";
		}else{
			//调用后台程序，以便获得各个设备网卡及IP地址信息
				cmd = "read_network";
				ps=java.lang.Runtime.getRuntime().exec(cmd + " 2>1 >/dev/null &");  
				ps.getErrorStream();
			//根据后台输出，检查file/topo/目录下interfaces.xml文件是否存在，存在：读取该文件值，传至前台显示，不存在：提示用户OSPF配置不正确
				File configFile = new File(Constants.webRealPath + "file/topo/interfaces.xml");
				if(configFile.exists()){					
					info = "successConfigOSPF";
				}else{
					info = "errorConfigOSPF";
				}
		}
		return SUCCESS;
	}
	public String configOSPF() throws Exception {
		String[] contents = congfigValue.split(" ");
		if(contents.length==2){
			String cmd = new String();
			//调用后台程序，判断OSPF是否可用
			cmd = "setup_ospf --ifname "+ contents[1]+" --ip "+contents[0];
			Process ps=java.lang.Runtime.getRuntime().exec(cmd + " 2>1 >/dev/null &");  
			ps.getErrorStream();
			//根据后台程序的输出，检查file/topo/目录下ospf.flag该文件是否存在，存在：OSPF可用，不存在：OSPF不可用
			File file = new File(Constants.webRealPath + "file/topo/success.flag");
			if(file.exists()){
				success = true;
			}else{
				success = false;
			}
		}else{
			success = false;
		}
		return SUCCESS;
	}
	public String getConfig()throws Exception {
		Document document = FileDAO.readXML(Constants.webRealPath + "file/topo/interfaces.xml");
		if(document!=null){
			configList = new ArrayList<OSPFConfig>();
			//读取该文件，将每行数据传给一个dto
			Element root = document.getRootElement();
			List<Element> datas = root.element("datas").elements();
			// 循环device
			for (Element data : datas) {
				OSPFConfig ospf = new OSPFConfig();
				String IP = data.elementText("IP");
				String ifName = data.elementText("ifName");
				ospf.setContent(IP+" "+ifName);
				configList.add(ospf);
			}
		}
		return SUCCESS;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<OSPFConfig> getConfigList() {
		return configList;
	}
	public void setConfigList(List<OSPFConfig> configList) {
		this.configList = configList;
	}
	public String getCongfigValue() {
		return congfigValue;
	}
	public void setCongfigValue(String congfigValue) {
		this.congfigValue = congfigValue;
	}
}
