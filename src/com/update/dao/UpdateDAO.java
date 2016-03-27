package com.update.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
public class UpdateDAO {
	public static String  getUpdateStatus(){
		String status = "";
		try{
			Process p1 = Runtime.getRuntime().exec("service neteye status");
			InputStream isn = p1.getInputStream();
    		BufferedReader br  = new BufferedReader(new InputStreamReader(isn));
    		String message = br.readLine();
    		if(message.indexOf("Nightly neteye update is enabled")!=-1)
    			status = "start";
    		else if(message.indexOf("Nightly neteye update is disabled")!=-1)
    			status = "stop";
    		else status = "unknown";
		}catch(Exception e){}
		return status;
	}
	/**
	 * 根据用户的选择，执行自动升级功能的开启或停止操作
	 * @param flag 1:启动自动升级功能 2:关闭升级功能
	 * @return
	 */
	public static String  runUpdateStatus(String flag){
		String status = "";
		try{
			Process p1 = null;
			if(flag.equals("1")){
				p1 = Runtime.getRuntime().exec("service neteye start");
			}else{
				p1 = Runtime.getRuntime().exec("service neteye stop");
			}
			InputStream isn = p1.getInputStream();
    		BufferedReader br  = new BufferedReader(new InputStreamReader(isn));
    		String message = br.readLine();
    		if(message.indexOf("Enabling nightly neteye update:")!=-1)
    			status = "start";
    		else if(message.indexOf("Disabling nightly neteye update:")!=-1)
    			status = "stop";
    		else status = "unknown";
		}catch(Exception e){}
		return status;
	}
	public static String  getVersionStatus(){
		String version = "";
		//首先判断文件是否存在，若存在，读取其内容
		String fileStr = "/etc/neteye-release";
		File file = new File(fileStr);
		try{
			FileReader reader = new FileReader(fileStr);
			if(file.exists()){
				BufferedReader br = new BufferedReader(reader);
				String line = null;
				try {
					while ((line = br.readLine()) != null) {
						version = line;
					}
				}catch (IOException e) {
					e.printStackTrace();
					br.close();
					reader.close();
				} finally {
					br.close();
					reader.close();
				}
			}
		}catch(Exception e){}
		return version;
	}
}
