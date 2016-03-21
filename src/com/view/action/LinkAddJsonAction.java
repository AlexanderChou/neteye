package com.view.action;

import org.apache.struts2.json.annotations.JSON;

import com.base.model.IfInterface;
import com.base.model.Link;
import com.base.service.LinkService;
import com.base.service.PortService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

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
public class LinkAddJsonAction extends ActionSupport {
	//路由器id
	private String srcId;
	private String destId;
	private String srcName;
	private String destName;
	//选择的端口id
	private String srcCheckId;
	private String destCheckId;
	//视图名称
	private String success;
	private String failure;
	private String linkId;
	private String linkName;

	@JSON(serialize=false)
	public String execute() {
		try{
			//由上下端口id获得ifinterface对象
			
			PortService PortService = new PortService();
			LinkService deviceService = new LinkService();
			IfInterface srcInterface = PortService.findById(Long.valueOf(srcCheckId));
			IfInterface destInterface = PortService.findById(Long.valueOf(destCheckId));
			Double tempmaxspeed1=0.00;
			Double tempmaxspeed2=0.00;
			if(destInterface.getMaxSpeed()!=null){ tempmaxspeed1=destInterface.getMaxSpeed();}
			if(destInterface.getMaxSpeed()!=null){ tempmaxspeed1=destInterface.getMaxSpeed();}
			Double getmaxspeed =tempmaxspeed2;
			
			if(tempmaxspeed1>tempmaxspeed2){
				getmaxspeed =tempmaxspeed1;	
			}
			Link link = new Link();
			link.setUpInterface(srcInterface);
			link.setDownInterface(destInterface);
			link.setUpDevice(Long.valueOf(srcId));
			link.setDownDevice(Long.valueOf(destId));
			link.setMaxSpeed(getmaxspeed);
			linkName = srcName + "_" + srcInterface.getIfindex() + "--" + destName + "_" + destInterface.getIfindex();
			//首先要判断该链路是否重复，若重复，不允许继续添加。判断链路重复的依据是：srcCheckId或destCheckId与某一链路的上行或下行端口相等
			link.setName(linkName);
			Long isId = deviceService.isRepeatLink(link);
		
			if(isId!=null && isId>0){
				linkId = isId.toString();
			}else{
				//向数据库表link中添加一条记录
				Long id = new LinkService().save(link);
				//将生成的link的id和name返回到前台页面
				linkId = id.toString();
				success = "true";
				failure = "false";
			}
		}catch(Exception e){
			success = "false";
			failure = "true";
		}
		return Action.SUCCESS;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

	public String getSrcId() {
		return srcId;
	}


	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}


	public String getDestId() {
		return destId;
	}


	public void setDestId(String destId) {
		this.destId = destId;
	}

	public String getSrcCheckId() {
		return srcCheckId;
	}


	public void setSrcCheckId(String srcCheckId) {
		this.srcCheckId = srcCheckId;
	}

	public String getDestCheckId() {
		return destCheckId;
	}

	public void setDestCheckId(String destCheckId) {
		this.destCheckId = destCheckId;
	}

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getSrcName() {
		return srcName;
	}

	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}
}
