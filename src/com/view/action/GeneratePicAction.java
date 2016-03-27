package com.view.action;

import com.opensymphony.xwork2.ActionSupport;
import com.view.util.PicUtil;

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
public class GeneratePicAction extends  ActionSupport{
	private String ipv4;
	private String ipv6;
	private String srcIP;
	private String srcIPv6;
	private String destIP;
	private String destIPv6;
	private String srcIfIndex;
	private String destIfIndex;
	private String srcInfIPv6;
	private String destInfIPv6;
	private String srcInfIP;
	private String destInfIP;
	
	public String execute() throws Exception{
		
		if(ipv4!=null && !("").equals(ipv4) && ipv6!=null && !("").equals(ipv6)){
			PicUtil.generatePic(ipv4, ipv6);
			PicUtil.createRttAndLoss(ipv4,ipv6);
		}
		
		if(srcInfIP!=null && !("").equals(srcInfIP) || srcInfIPv6!=null && !("").equals(srcInfIPv6)){
			PicUtil.createRttAndLoss(srcInfIP,srcInfIPv6);
		}
		if(srcIP!=null && !("").equals(srcIP) || srcIPv6!=null && !("").equals(srcIPv6)){
			PicUtil.createRttAndLoss(srcIP,srcIPv6);
		}
		if(destIP!=null && !("").equals(destIP) || destIPv6!=null && !("").equals(destIPv6)){
			PicUtil.createRttAndLoss(destIP,destIPv6);
		}
		if(destInfIP!=null && !("").equals(destInfIP) || destInfIPv6!=null && !("").equals(destInfIPv6)){
			PicUtil.createRttAndLoss(destInfIP,destInfIPv6);
		}
		if(srcIfIndex!=null && !("").equals(srcIfIndex)){
			if(srcIP!=null && !("").equals(srcIP)){
				PicUtil.generateImAndOut(srcIP,srcIfIndex);
			}
			if(srcIPv6!=null && !("").equals(srcIPv6)){
				PicUtil.generateImAndOut(srcIPv6,srcIfIndex);
			}
			if(srcInfIP!=null && !("").equals(srcInfIP)){
				PicUtil.generateImAndOut(srcInfIP,srcIfIndex);
			}
			if(srcInfIPv6!=null && !("").equals(srcInfIPv6)){
				PicUtil.generateImAndOut(srcInfIPv6,srcIfIndex);
			}
			
		}
		if(destIfIndex!=null && !("").equals(destIfIndex)){
			if(destIP!=null && !("").equals(destIP)){
				PicUtil.generateImAndOut(destIP,destIfIndex);
			}
			if(destIPv6!=null && !("").equals(destIPv6)){
				PicUtil.generateImAndOut(destIPv6,destIfIndex);
			}
			if(destInfIP!=null && !("").equals(destInfIP)){
				PicUtil.generateImAndOut(destInfIP,destIfIndex);
			}
			if(destInfIPv6!=null && !("").equals(destInfIPv6)){
				PicUtil.generateImAndOut(destInfIPv6,destIfIndex);
			}
			
		}
//		if(srcIP!=null && !("").equals(srcIP) && srcIfIndex!=null && !("").equals(srcIfIndex)){
//			PicUtil.generateImAndOut(srcIP,srcIfIndex);
//			PicUtil.createRttAndLoss(srcInfIP,srcInfIPv6);
//		};
//		if(destIP!=null && !("").equals(destIP)&& destIfIndex!=null && !("").equals(destIfIndex)){
//			PicUtil.generateImAndOut(destIP,destIfIndex);
//		//	PicUtil.generateImAndOut(destInfIP,destIfIndex);
//			PicUtil.createRttAndLoss(destInfIP,destInfIPv6);
//		}
//		else
//		{
//			PicUtil.generatePic(ipv4, ipv6);
//			PicUtil.createRttAndLoss(ipv4,ipv6);
//		}
		return SUCCESS;
	}
	
	public String getSrcIP() {
		return srcIP;
	}

	public void setSrcIP(String srcIP) {
		this.srcIP = srcIP;
	}

	public String getDestIP() {
		return destIP;
	}

	public void setDestIP(String destIP) {
		this.destIP = destIP;
	}

	public String getSrcIfIndex() {
		return srcIfIndex;
	}

	public void setSrcIfIndex(String srcIfIndex) {
		this.srcIfIndex = srcIfIndex;
	}

	public String getDestIfIndex() {
		return destIfIndex;
	}

	public void setDestIfIndex(String destIfIndex) {
		this.destIfIndex = destIfIndex;
	}


	public String getIpv4() {
		return ipv4;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}

	public String getIpv6() {
		return ipv6;
	}

	public void setIpv6(String ipv6) {
		this.ipv6 = ipv6;
	}

	public String getSrcInfIPv6() {
		return srcInfIPv6;
	}

	public void setSrcInfIPv6(String srcInfIPv6) {
		this.srcInfIPv6 = srcInfIPv6;
	}

	public String getDestInfIPv6() {
		return destInfIPv6;
	}

	public void setDestInfIPv6(String destInfIPv6) {
		this.destInfIPv6 = destInfIPv6;
	}

	public String getSrcInfIP() {
		return srcInfIP;
	}

	public void setSrcInfIP(String srcInfIP) {
		this.srcInfIP = srcInfIP;
	}

	public String getDestInfIP() {
		return destInfIP;
	}

	public void setDestInfIP(String destInfIP) {
		this.destInfIP = destInfIP;
	}

	public String getDestIPv6() {
		return destIPv6;
	}

	public void setDestIPv6(String destIPv6) {
		this.destIPv6 = destIPv6;
	}

	public String getSrcIPv6() {
		return srcIPv6;
	}

	public void setSrcIPv6(String srcIPv6) {
		this.srcIPv6 = srcIPv6;
	}
}
