package com.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class Constants {
    public static String realPath;
    public static String webRealPath; 
    public static final String BACKUP_FOLDER = "backup";
    public static final String BACKUP_PATH = "/opt/";
    public static final String WEB_PATH = "/opt/NetEye/";
    //邮件发送服务器
    public static String SMTPSERVICE = "sea.net.edu.cn";
    //发送用户名称
    public static String USEROFEMAIL = "guoxi";
    //发送用户密码
    public static String PWDOFEMAIL = "70501782";
    //发送邮件地址
    public static String EMAILOFSEND = "guoxi@cernet.edu.cn";
    public static List<String> receives = new ArrayList<String>();
    //发送人手机号
    public static String sendMboile;
    public static String sendPasswd;
    public static List<String> receiveMobiles = new ArrayList<String>();
    //以下两项可以通过StartUpServlet进行配置文件的读取，对相应项赋值
    public static String IsIPv6 = "0";//是否支持IPv6 0：支持  1:不支持
    public static int permitNum = 0;//允许添加设备的数目，评测版的限定数目为5，完全版时值为0
    //NetFlow服务部署的IP地址和端口
    public static String NETFLOW_IP;
    public static String NETFLOW_PORT;
    //用户流量分析服务部署的IP地址和端口
    public static String ANALYSIS_IP;
    public static String ANALYSIS_PORT;
    //视图图标默认的大小
    public static String ICON_WIDTH;
    public static String ICON_HEIGHT;
    
    public static Map serviceMap=new HashMap(){{
	    	put("DNS","53");
	    	put("Email","25");
	    	put("FTP","20|21");
	    	put("HTTP","80|8080");
	    }    	
    };//服务类型对应的端口号          
}
