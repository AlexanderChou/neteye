package com.ticket.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.base.model.Attachment;
import com.base.service.TicketService;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 处理ticket时候下载附件
 * @author SUNLUJING	
 *
 */
public class DownloadAttachMentAction extends ActionSupport{
	
	private String id;//从页面传过来的ticket id
	 private String filename;//文件名字
	 private TicketService ticketService=new TicketService();
	
	 public InputStream getDownloadFile()throws Exception{
		 
		   Attachment attachment=ticketService.getAttachmentById(Long.parseLong(id));
           File file = new File(attachment.getFilePath());

		 
	      filename=attachment.getFileName();
	       InputStream fis=null;
	        fis = new FileInputStream(file);
		    return fis;
		 
	    }
	    
	    @Override
	    public String execute() throws Exception {
	        // TODO Auto-generated method stub
	    	
	    	
		   
			    return SUCCESS;
	    }

        public String getFileName(){
        
        	 try {   
        		 filename = new String(filename.getBytes(), "ISO8859-1");   
             } catch (UnsupportedEncodingException e) {   
                 e.printStackTrace();   
             }  
        	return filename;
        }

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
        public void setFileName(String filename){
        	this.filename=filename;
        }
        
}
