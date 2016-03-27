package com.view.action;

import com.base.model.View;
import com.base.util.Constants;
import com.base.util.JDOMXML;
import com.opensymphony.xwork2.ActionSupport;
import com.view.dao.ViewDAO;

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
public class ViewSaveAction extends ActionSupport {
	private Long viewId;
	private String locationXML;
	
	private String viewInfoXML;
	private boolean success;

	public String execute() throws Exception {
		try {
			//只有未重复的视图才可以允许添加(需补充完整)
			ViewDAO viewDAO = new ViewDAO();
	//		View view = viewDAO.getViewByViewName(viewName);
			View view = viewDAO.getViewByViewId(viewId);
			String viewName = view.getName();
			String path = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
			JDOMXML.writeXML(path+viewName+".xml",viewInfoXML);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public void setLocationXML(String locationXML) {
		this.locationXML = locationXML;
	}
	public String getViewInfoXML() {
		return viewInfoXML;
	}
	public void setViewInfoXML(String viewInfoXML) {
		this.viewInfoXML = viewInfoXML;
	}
	public String getLocationXML() {
		return locationXML;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Long getViewId() {
		return viewId;
	}
	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}
	
}