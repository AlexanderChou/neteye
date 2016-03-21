package com.data.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.base.util.BaseAction;
import com.base.util.Constants;

public class GetDirFiles extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<BackupFiles> result;
	String paths = Constants.webRealPath+"file/backup/savi/";
	private boolean success;
	private boolean failure;

	public String getdirfilesall() {
		File path = new File(paths);
		if(!path.exists()){
			path.mkdirs();
		}
		File[] listStrings;
		listStrings=path.listFiles();
		List<BackupFiles> temp= new ArrayList<BackupFiles>();
		for(File file: listStrings){
			if(file.isFile()){
			BackupFiles files = new BackupFiles();	
			files.setFilelength(file.length());
			files.setFilename(file.getName());
			files.setFilepath(file.getPath());
			temp.add(files);	
			}
		}
		result = temp;
		setSuccess(true);
		return SUCCESS;
	}
	
	
	public String reName(){
		String filepath = this.getRequest().getParameter("value");
		String newname = this.getRequest().getParameter("name");
		
		File path = new File(filepath);
		File renamefile = new File(paths+newname);
		path.renameTo(renamefile);
		setSuccess(true);
		return SUCCESS;
		
	}
	public String delName(){
		String filepath = this.getRequest().getParameter("value");
		File path = new File(filepath);
		path.delete();
		setSuccess(true);
		return SUCCESS;
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GetDirFiles a = new GetDirFiles();
		String b=a.getdirfilesall();
		String d=a.getdirfilesall();
		// TODO Auto-generated method stub

	}




	public List<BackupFiles> getResult() {
		return result;
	}




	public void setResult(List<BackupFiles> result) {
		this.result = result;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public boolean isFailure() {
		return failure;
	}


	public void setFailure(boolean failure) {
		this.failure = failure;
	}

}
