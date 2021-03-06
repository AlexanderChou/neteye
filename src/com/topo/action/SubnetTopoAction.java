package com.topo.action;

import org.apache.commons.lang.StringUtils;

import com.base.util.Constants;
import com.opensymphony.xwork2.ActionContext;
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
/**
 * <p>Title: 拓扑子网发现</p>
 * <p>Description: 进行拓扑子网发现</p>
 * @version 1.0
 * @author 杨自强  update:郭玺
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class SubnetTopoAction extends ActionSupport {
	private String topoName;
	private String seed;
	private String mask;
	private String[] community;
	private String version;
	private String stp;

	public String execute() throws Exception {
		String path = Constants.webRealPath + "file/topo";
		String cmdPrameter = path+"/topoHis";
		String userName = (String)ActionContext.getContext().getSession().get("userName");
		String cmd = new String();
		String[] seeds = seed.split(",");
		String[] masks = mask.split(",");
		
		StringBuffer ipSB = new StringBuffer();
		for (int i = 0; i < seeds.length; i++) {
			String im = seeds[i].trim() + "/" + masks[i].trim();
			ipSB.append(" --ip ");
			ipSB.append(im);
		}
		
		if(stp.equals("1")){
			cmd = "topo --name " + topoName +" --output "+ cmdPrameter + ipSB.toString() + " --hop " + "1";
		}else{
			cmd = "topo --name " + topoName +" --output "+ cmdPrameter + ipSB.toString() + " --hop " + "1" + " --stp ";
		}
		
	    for(int i=0;i<community.length;i++){
	    	cmd+= " --community " + community[i];
	    }

		try {
			Process ps = java.lang.Runtime.getRuntime().exec(cmd +" --user " + userName +" 2>1 >/dev/null &");
			ps.getErrorStream();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getTopoName() {
		return topoName;
	}

	public void setTopoName(String topoName) {
		this.topoName = topoName;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	public String[] getCommunity() {
		return community;
	}

	public void setCommunity(String[] community) {
		this.community = community;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStp() {
		return stp;
	}

	public void setStp(String stp) {
		this.stp = stp;
	}
}
