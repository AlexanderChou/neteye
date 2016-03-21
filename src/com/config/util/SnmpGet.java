package com.config.util;

import java.io.BufferedReader;
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
public class SnmpGet
{
	private String result = "";
	
	/**
	 * getInt and getString should not assume that result starts with
	 * "INTEGER: " or "STRING: "
	 * since timeout error may occur and result becomes empty;
	 */
	
	public long getInt(String host, String community, String oid, String version)
	{
		int ret = this.run(host, community, oid,version);
		if(ret == 0){
			int first = this.result.indexOf(" = ");
			if(first!=-1){
				String temp = this.result.substring(first+3);
				int two = temp.indexOf(": ");
				if(two!=-1){
					return Long.parseLong(temp.substring(two+2).trim().replace("\"",""));
				}else{
					return 0;
				}
			}else{
				return 0;
			}
		}else
			return 0;		
	}
	
	public String getString(String host, String community, String oid,String version)
	{
		int ret = this.run(host, community, oid, version);
		if(ret == 0){
			int first = this.result.indexOf(" = ");
			if(first!=-1){
				String temp = this.result.substring(first+3);
				int two = temp.indexOf(": ");
				if(two == -1)
					return null;
				else
					return temp.substring(two + 2).replace("\"","");
			}else{
				return null;
			}
		}else
			return null;
	}
	
    private int run(String host, String community, String oid,String version) 
    {
		try{
			// String osName = System.getProperty("os.name" );
			String[] cmd = new String[3];
			if(host.indexOf(":")!=-1){
				host = "udp6:"+host;
			}
			cmd[0] = "snmpwalk -v "+ version +" -c " + community + " " + host + " " + oid;

			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmd[0]);					
			
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(proc.getInputStream()));
			String output = bufferedReader.readLine();	
			if(output!=null){
				if(output.indexOf("No Response from") > 0) return  -1;
				this.result = output;

				int exitVal = proc.waitFor();			
				return exitVal;
			}else{
				return -1;
			}
		}catch (Throwable t){
			t.printStackTrace();
			return -1;
		}
	} 
}

