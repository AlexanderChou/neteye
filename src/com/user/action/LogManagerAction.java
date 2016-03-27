package com.user.action;

import java.io.PrintWriter;
import java.util.List;

import com.base.model.Log;
import com.base.util.BaseAction;
import com.user.dao.LogDAO;
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
 * 日志管理action
 * @author 李宪亮
 *
 */
public class LogManagerAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private List<Log> logs;
	private String totalCount;
	
	/**
	 * 查看用户信息
	 * @return
	 * @throws Exception
	 */
	public String userLoginInfo() throws Exception {
		LogDAO logDAO = new LogDAO();
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		logs = logDAO.getAllLogInfoList(start, limit);
		totalCount = String.valueOf(logDAO.getLogCount());
		return SUCCESS;
	}
	
	
	/**
	 * 批量删除 用户登录信息
	 * @return
	 * @throws Exception
	 */
	public String deleteLogs() throws Exception {
		String logIds = this.getRequest().getParameter("logIds");
		LogDAO logDao = new LogDAO();
		logDao.deleteLogs(logIds);
		
		/* 告诉前台页面操作成功 */
		PrintWriter writer = this.getResponse().getWriter();
		writer.print("ok");
		writer.close();
		return null;
	}
	
	public List<Log> getLogs() {
		return logs;
	}
	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	
}
