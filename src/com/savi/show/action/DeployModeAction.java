package com.savi.show.action;

import org.apache.struts2.json.annotations.JSON;

import com.savi.base.util.Constants;

@SuppressWarnings("serial")
public class DeployModeAction extends BaseAction{
	private int deployMode;
	@JSON(serialize = false)
	public String deployMode(){
		while(true){
			if(Constants.deployMode!=-1)break;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		deployMode=Constants.deployMode;
		return SUCCESS;
	}
	public int getDeployMode() {
		return deployMode;
	}
	public void setDeployMode(int deployMode) {
		this.deployMode = deployMode;
	}
}
