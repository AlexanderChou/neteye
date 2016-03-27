package com.view.action;

import java.io.File;

import org.apache.struts2.ServletActionContext;
import org.jdom.input.SAXBuilder;

import com.base.model.View;
import com.base.service.ViewService;
import com.base.util.Constants;
import com.opensymphony.xwork2.ActionSupport;
import com.view.util.JdomXML;

public class ViewJoinAction extends  ActionSupport{ 
	private String joinedView;
	private String sourceView;
	private String deviceId;
	private String deviceName;
	private String nullview;
	private boolean success;
	private String flag;//0:取消关联 1：关联成功
	public String execute() throws Exception{
		long currentUserId = (Long)ServletActionContext.getRequest().getSession().getAttribute("userId");
		String currentUserName = (String)ServletActionContext.getRequest().getSession().getAttribute("userName");
		//通过viewId找到相应的viewName
		View view = new ViewService().findById(Long.valueOf(sourceView));
		String file = Constants.webRealPath + "file/user/" + currentUserName + "_" + currentUserId + "/" +view.getName()+".xml";
		//根据提交的关联视图id，修改xml视图文件，添加关联子视图菜单项
		try {
			SAXBuilder as=new SAXBuilder();
		   org.jdom.Document doc=as.build(new File(file));
		   if(!("-1").equals(joinedView)){
//		   if(nullview!=null && !nullview.equals("NULL")){
			   new JdomXML().setvalue(doc, deviceId, joinedView,file);
			   flag = "1";
		   }else{
			   new JdomXML().delvalue(doc, deviceId, joinedView,file);
			   flag = "0";
		   }
		   success = true;
        }catch(Exception e){
        	success = false;
        	e.printStackTrace();
        }
		return SUCCESS;
	}
	public static void main(String[] args) throws Exception{
		//根据设备id和name，找到对应的视图文件的xml片断，然后新增添加子视图片断
		String idValue = "1001";
		String file = "bbbb";
		SAXBuilder as=new SAXBuilder();
	   try {
		   //这种方法运行一次就会往文件中写一次，最好是写时能判断，若已存在，覆盖原来的文件(待修改)
		   org.jdom.Document doc=as.build(new File("e:/ZJ10.xml"));
		    new JdomXML().setvalue(doc, idValue, "12345566","e:/ZJ10.xml");
        }catch(Exception e){e.printStackTrace();}
	} 

	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getJoinedView() {
		return joinedView;
	}
	public void setJoinedView(String joinedView) {
		this.joinedView = joinedView;
	}
	public String getSourceView() {
		return sourceView;
	}
	public void setSourceView(String sourceView) {
		this.sourceView = sourceView;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public void setNullview(String nullview) {
		this.nullview = nullview;
	}
	public String getNullview() {
		return nullview;
	}
}
