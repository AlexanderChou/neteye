package com.view.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.base.model.View;
import com.base.service.ViewService;
import com.base.util.BaseAction;
import com.base.util.Constants;
import com.view.dao.ViewDAO;
import com.view.dto.ViewName;

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
public class ReNameAction extends BaseAction{
	private List nameList;
	private String totalCount="0";

	public String nameLists() throws Exception{
		String viewName = this.getRequest().getParameter("viewName");
		nameList=new ArrayList();
		if(viewName!=null){
			String a[]=viewName.split(";");
			totalCount=Integer.toString(a.length-1);
			for(int i=0;i<a.length;i++){
				if(!a[i].equals("")&&a[i]!=null){
					ViewName dto=new ViewName();
					String name=a[i].substring(0,a[i].lastIndexOf("_"));
					String id=a[i].substring(a[i].lastIndexOf("_")+1,a[i].length());
					dto.setName(name);
					dto.setId(id);
					nameList.add(dto);
				}
			}
		}
		return SUCCESS;
	}
	public String nameEdit() throws Exception{
		String id = this.getRequest().getParameter("id");
		String reName = this.getRequest().getParameter("reName");
		ViewService service =new ViewService();
		if(reName!=null&&service.isExistByName(reName)&&!reName.trim().equals("")){
			PrintWriter write = this.getResponse().getWriter();
			write.print("exist");
			write.close();
		}else{
			File viewFile=new File(Constants.webRealPath+"file/temp");
			if(!viewFile.exists()){
				viewFile.mkdir();
			}else if(!viewFile.isDirectory()){
				viewFile.delete();
				viewFile.mkdir();
			}
			File fileName=new File(viewFile+"/"+id+".txt");
			if(fileName.exists()){
				fileName.delete();
				fileName.createNewFile();
			}else{
				fileName.createNewFile();
			}
			PrintWriter pw= new PrintWriter(new FileOutputStream(fileName));
			pw.write(reName.trim());
			pw.close();
			PrintWriter write = this.getResponse().getWriter();
			write.print("ok");
			write.close();
		}
		return null;	
	}
	
	public String nameModify() throws Exception{
		ViewService service =new ViewService();	
		ViewDAO dao=new ViewDAO();
		File directory=new File(Constants.webRealPath+"file/temp/");
		if(directory.exists()&&directory.isDirectory()){
			File filelist[] = directory.listFiles();
			 for (int i = 0; i < filelist.length; i++) {;
	          if (!filelist[i].isDirectory()) {
	            if(filelist[i].getAbsolutePath().contains(".txt")){
	            	String id=filelist[i].getName().substring(0,filelist[i].getName().lastIndexOf("."));
	            	BufferedReader input = new BufferedReader( new FileReader( filelist[i].getAbsolutePath() ) );
	    			String text;					
	    			while ( ( text = input.readLine() ) != null )
	    			{				
	    				if(!text.equals("")){
	    					View view=service.findById(Long.parseLong(id));
	    					if(view!=null&&text!=null&&!(text.trim()).equals("")){
	    						File viewFile=new File(Constants.webRealPath+"file/user/"+view.getUserName()+"_"+view.getUserId()+"/"+view.getName()+".xml");
	    						File renameFile=new File(Constants.webRealPath+"file/user/"+view.getUserName()+"_"+view.getUserId()+"/"+text+".xml");
	    						if(viewFile.exists()){
	    							viewFile.renameTo(renameFile);
	    							view.setName(text);
	    							service.update(view);	    							
	    						}
	    					}
	    				}
	    			}
	            }
	          }
			 }			
		}
		dao.deleteFile(Constants.webRealPath+"file/temp");
		return SUCCESS;
	}	
	public List getNameList() {
		return nameList;
	}

	public void setNameList(List nameList) {
		this.nameList = nameList;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
