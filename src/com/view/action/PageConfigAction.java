package com.view.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.base.model.View;
import com.base.service.ViewService;
import com.base.util.BaseAction;

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
public class PageConfigAction extends BaseAction{
	private String viewName;
	private String msg;
	
	/**
	 * 本方法201201026号进行修改：将原来与用户关联去掉，因为目前整个系统用户权限是根据URL进行确定，只要用户拥有权限，那么就应该允许其进行操作
	 */
	public String execute()  throws Exception{
		if(viewName==null&&viewName.trim().length()==0){
			return ERROR;
		}else{
			String a[]=viewName.split("_");
			ViewService service=new ViewService();
			View view = null;
			/* 判断当前用户是否拥有该视图 */
			//view=service.findByIdAndUserId(Long.parseLong(a[a.length-1]));
			view=service.findById(Long.parseLong(a[a.length-1]));
			if (view == null) {
				msg = "您不能设置该视图！";
				return SUCCESS;
			}
			/*
			long currentUserId = (Long)ServletActionContext.getRequest().getSession().getAttribute("userId");
			List list=service.findPageById(currentUserId);
			if(!list.isEmpty()){
				for(int i=0;i<list.size();i++){
					View v=(View)list.get(i);
					v.setHomePage(false);
					ViewService.update(v);
				}
			}*/
			view.setHomePage(true);
			ViewService.update(view);
			return SUCCESS;
		}		
	}
	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
