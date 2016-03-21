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
/*
 * @autor:JiangNing
 * @data:2009-7-11
 */
package com.view.dto;

import org.apache.struts2.json.annotations.JSON;

public class Device {
	private int id;
	private String name;
	private String chineseName;
	private String ipv4;
	private String ipv6;
	private int rX;
	private int rY;
	private String picture;
	private String subView;
	private String deviceType;
	/**
	 * 以下两个变量可以用来控制批量修改设备的图标，但现在暂时没用，可用于以后扩展
	 */
	private String productor;
	private String model;
	private String serial;
	private String location;
	private String label;
	private String description;
	private Integer ifNum;
	private String trafficIp;
	private String faultFlag;
	private String imgHeight;
	private String imgWidth;
	/*以下两个标记是用来控制轮循以及故障告警时，图标文件名的匹配 正常：green_manuIcon_modelIcon.gif
	 * 轮循：purple_manuIcon_modelIcon.gif  告警:red_manuIcon_modelIcon.gif
	 */
	private String manuIcon;
	private String modelIcon;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
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
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	@JSON(name="rX")
	public int getRX() {
		return rX;
	}
	public void setRX(int rx) {
		rX = rx;
	}
	@JSON(name="rY")
	public int getRY() {
		return rY;
	}
	public void setRY(int ry) {
		rY = ry;
	}
	public String getProductor() {
		return productor;
	}
	public void setProductor(String productor) {
		this.productor = productor;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getIfNum() {
		return ifNum;
	}
	public void setIfNum(Integer ifNum) {
		this.ifNum = ifNum;
	}
	public String getTrafficIp() {
		return trafficIp;
	}
	public void setTrafficIp(String trafficIp) {
		this.trafficIp = trafficIp;
	}
	public String getFaultFlag() {
		return faultFlag;
	}
	public void setFaultFlag(String faultFlag) {
		this.faultFlag = faultFlag;
	}
	public String getSubView() {
		return subView;
	}
	public void setSubView(String subView) {
		this.subView = subView;
	}
	public String getImgHeight() {
		return imgHeight;
	}
	public void setImgHeight(String imgHeight) {
		this.imgHeight = imgHeight;
	}
	public String getImgWidth() {
		return imgWidth;
	}
	public void setImgWidth(String imgWidth) {
		this.imgWidth = imgWidth;
	}
	public String getManuIcon() {
		return manuIcon;
	}
	public void setManuIcon(String manuIcon) {
		this.manuIcon = manuIcon;
	}
	public String getModelIcon() {
		return modelIcon;
	}
	public void setModelIcon(String modelIcon) {
		this.modelIcon = modelIcon;
	}
}