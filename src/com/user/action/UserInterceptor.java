package com.user.action;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.base.model.Resource;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.user.dao.ResourceDAO;

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
 * 拦截器 控制用户的权限
 * @author 李宪亮
 *
 */
public class UserInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception {
		
		String url = ServletActionContext.getRequest().getRequestURI();
		if(url.indexOf("generator.do")>0||url.indexOf("validateAuthCode.do")>0||url.indexOf("login.do")>0){
			return invocation.invoke();
		}
		url =url.substring(url.lastIndexOf("/")+1);
		
		String userId = String.valueOf((Long)ServletActionContext.getRequest().getSession().getAttribute("userId"));
		String userName="";
		if(null!=ServletActionContext.getRequest().getSession().getAttribute("userName")){
			userName=(ServletActionContext.getRequest().getSession().getAttribute("userName")).toString();
		}
		if (StringUtils.isEmpty(userId) || userId.equals("null")) {
			return "reLogin";
		}		
		
		if(!userName.equals("admin")){
			//查询该url是否在resource表中,即该资源是否需要做权限控制
			ResourceDAO dao = new ResourceDAO();
			Resource resource = dao.getResourceByURL(url);
			if(resource!=null){
				//查询该用户是否拥有该资源
				Long isHave = dao.urlIsInUser(Long.valueOf(userId), resource.getId());
				if(isHave.intValue()==0){
					return "info";
				}
			}
			return invocation.invoke();
		}else{
			return invocation.invoke();
		}
	}

}
