package com.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.struts2.ServletActionContext;

import com.base.model.Image;
import com.base.service.ImageService;
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
public class UploadAction  extends ActionSupport{ 
    private File upload;
    private String uploadFileName;
    private String uploadContentType;
    private String savePath;
    private String allowTypes;
    private String imageType;
    private String messsage="";
	@Override
    public String execute() throws Exception {
		String filterResult = filterType(getAllowTypes().split(","));
		
		if(filterResult !=null){
			ActionContext.getContext().put("typeError","您要上传的文件不正确！");
			return filterResult;
		}
		ImageService imageService = new ImageService();
		//判断是否重复（名字和类型一样）
		boolean isRepeat = imageService.isNameUnique(uploadFileName, imageType);
		if(isRepeat){
	    	FileOutputStream fos = new FileOutputStream(getSavePath()+"/"+getUploadFileName());
	    	FileInputStream fis = new FileInputStream(getUpload());
	    	byte[] buffer = new byte[1024];
	    	int len = 0;
	    	while((len = fis.read(buffer))>0){
	    		fos.write(buffer,0,len);
	    	}			
	    	//向数据库添加一条记录
	    	Image image = new Image();
	    	image.setImageName(uploadFileName);
	    	image.setImageType(imageType);
	    	imageService.addImage(image);
		}
		//前台页面怎样获得操作信息（以后再补充完善）
    	return SUCCESS;
    }
    
	public String filterType(String[] types){
		String fileType = getUploadContentType();
		for(String type:types){
			if(type.equals(fileType)){
				return null;
			}
		}
		return INPUT;
	}
	
	public String getAllowTypes() {
		return allowTypes;
	}
	
	public void setAllowTypes(String allowTypes) {
		this.allowTypes = allowTypes;
	}
	

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getSavePath() {
		return ServletActionContext.getRequest().getRealPath(savePath);
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
}
