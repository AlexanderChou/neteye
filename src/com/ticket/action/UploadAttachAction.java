package com.ticket.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.struts2.ServletActionContext;

import com.base.model.Attachment;
import com.base.service.TicketService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 接受在处理ticket时上传的文件
 * @author sunlujing
 *
 */
public class UploadAttachAction extends ActionSupport{
	   private String ticketId="0";
	   private File upload;
	   private String uploadFileName;
	   private String uploadContentType;
	   private String savePath;
	   private String allowedTypes;
	   private String jsonMsg;
	   private long  attachId=-1;

       public String execute()throws Exception{
    	//判断文件的类型是否符合要求
//    	String filterResult = filterType(getAllowedTypes().split(","));
//   		if(filterResult !=null){
//   		
//   			return filterResult;
//   		}    
   		//判断文件的名字是否唯一
   		TicketService ticketService=new TicketService();
   		//boolean isRepeat=ticketService.isNameUnique(uploadFileName);
   		    File dir=new File(getSavePath());
   		    if(dir.exists() == false) {
   			dir.mkdirs();
   		    }
   			File dst =new File(getSavePath()+"/"+getUploadFileName());  
   			FileOutputStream fos = new FileOutputStream(dst);
	    	FileInputStream fis = new FileInputStream(getUpload());
	    	byte[] buffer = new byte[16*1024*1024];
	    	int len = 0;
	    	while((len = fis.read(buffer))>0){
	    		fos.write(buffer,0,len);
	    	}	
	    	if(null!=fos)
	    		fos.close();
	    	if(null!=fis)
	    		fis.close();
	    	
	    	Attachment attachment =new Attachment();
	   		attachment.setFileName(uploadFileName);
	   		attachment.setFilePath(getSavePath()+"/"+getUploadFileName());
	   		attachment.setTicket(Long.parseLong(ticketId));
	   		ticketService.create(attachment);
	   	    attachId=((Attachment)ticketService.QueryByHql("from Attachment where fileName='"+uploadFileName+"'").get(0)).getId();
	   		
   		
   
   		ServletActionContext.getRequest().getSession().setAttribute("attachId",attachId);
     	 return SUCCESS;
   		
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
	public String getAllowedTypes() {
		return allowedTypes;
	}
	public void setAllowedTypes(String allowTypes) {
		this.allowedTypes = allowTypes;
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
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
       
	 public void setJsonMsg(String jsonMsg) {   
	        this.jsonMsg=jsonMsg;   
	    }   
	 public String getJsonMsg() {   
	        return jsonMsg;   
	    }   
}
