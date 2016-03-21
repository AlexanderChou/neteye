package com.base.util;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlrpc.XmlRpcServer;

import com.event.server.EventServer;
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
 * <p>Title: ����webservice����vwewewwwekweebwe web service服务发布</p>
 * <p>Description: ����webservice����整个系统共用的服务发布类</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: tsinghua </p>
 * @author guoxi
 * @version 1.0
 */
public class PublishServlet extends HttpServlet{
	public static XmlRpcServer xmlrpc = new XmlRpcServer();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 byte[] result = xmlrpc.execute(request.getInputStream(), null, null); 
		 response.setContentType("text/xml"); 
		 response.setContentLength(result.length); 
		 OutputStream output = response.getOutputStream(); 
		 output.write(result); 
		 output.flush();
		 }
	public void init (ServletConfig config) throws ServletException {	
		xmlrpc = new XmlRpcServer();
		xmlrpc.addHandler("remoteEventServer", new EventServer());
	}
}