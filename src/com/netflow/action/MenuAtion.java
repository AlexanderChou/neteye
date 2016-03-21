package com.netflow.action;

import com.base.util.BaseAction;
import com.base.util.Constants;

/**
 * 作者 李宪亮
 * 创建时间 ：Apr 9, 2011   4:28:34 PM
 */
public class MenuAtion extends BaseAction {
	private String IP;
	private String port;
	private String itemName;
	
	/**
	 * 网络整体流量实时的
	 * @return
	 * @throws Exception
	 */
	public String netUnityFlow() throws Exception {
		this.IP = Constants.ANALYSIS_IP;
		this.port = ":"+Constants.ANALYSIS_PORT;
		return SUCCESS;
	}
	
	/**
	 * 网络整体流量历史的
	 * @return
	 * @throws Exception
	 */
	public String netUnityFlowHis() throws Exception {
		//TODO 添加逻辑代码
		return SUCCESS;
	}
	
	
	/**
	 * TOPN用户流量历史
	 * @return
	 * @throws Exception
	 */
	public String topNHis() throws Exception {
		// TODO 添加逻辑代码
		return SUCCESS;
	}
	
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}

