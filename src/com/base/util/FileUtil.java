package com.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

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
public class FileUtil {
	static Logger logger = Logger.getLogger(FileUtil.class.getName());
	/**
	 * 生成文件
	 * @param file 包含路径和名称
	 * @param content 文件内容
	 */
	public static void createFile(String file, String content){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(file));
			writer.print(content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	public static boolean createFolder(String path){
		boolean isSuccess = true;
		File dirFile  =   new  File(path);
		if ( ! (dirFile.exists())  &&   ! (dirFile.isDirectory()))  {
				if(!dirFile.mkdirs()){
					isSuccess = false;
					logger.fatal("创建目录时出现错误!");
				}
		}else{
			//删除目录，然后重建
			boolean deleteFlag = FileUtil.deleteFolder(dirFile);
			if(deleteFlag){
				if(!dirFile.mkdirs()){
					isSuccess = false;
					logger.fatal("删除目录成功，但重新创建目录时出现错误!");
				}
			}else{
				isSuccess = false;
				logger.fatal("删除目录时出现错误!");
			}
		}
		return isSuccess;
	}
	public static boolean deleteFolder(File folder)     {  
        boolean  result = false ;
        try  {
             String   childs[]    =    folder.list();  
              if(childs ==  null || childs.length<=0 ){  
	               if (folder.delete())  {
	                   result  =   true ;
	               } 
              }else{
                  for( int i=0 ; i<childs.length; i++ ){  
                         String   childName    =    childs[i];  
                         String   childPath    =   
                             folder.getPath()    +    File.separator    +    childName;  
                         File   filePath    =     new    File(childPath);  
                         if(filePath.exists()    &&    filePath.isFile()){ 
	                           if (filePath.delete())  {
	                               result  =   true ;
	                           }else  {
	                               result  =   false ;
	                                break ;
	                           } 
                         }else  if(filePath.exists() && filePath.isDirectory()){  
                               if (deleteFolder(filePath))  {
                                   result  =   true ;
                               }else{
                                   result  =   false ;
                                    break ;
                               } 
                         }   
                 } 
               } 
             folder.delete();  
         } catch (Exception e)  {
             e.printStackTrace();
             result  =   false ;
         } 
        return  result;
    }
	/**
	 * 将源目录下的文件及文件夹拷贝到目标文件夹
	 * @param destPath 目标文件夹
	 * @param srcPath 源文件夹
	 * @throws IOException
	 */
	public static void copyDirectiory(String destPath,String srcPath) throws IOException{
		(new File(destPath)).mkdirs();
		File[] file=(new File(srcPath)).listFiles();
		if(file!=null){
			for(int i=0;i<file.length;i++){
				if(file[i].isFile()){
					FileInputStream input=new FileInputStream(file[i]);
					FileOutputStream output=new FileOutputStream(destPath+"/"+file[i].getName());
					byte[] b=new byte[1024*5];
					int len;
					while((len=input.read(b))!=-1){
						output.write(b,0,len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if(file[i].isDirectory()){
					copyDirectiory(destPath+file[i].getName()+"/",srcPath+file[i].getName()+"/");
				}
			}
		}
	}
	public static void copyFileAndReName(String destPath,String srcPath,String srcFile,String destFile) throws IOException{
		(new File(destPath)).mkdirs();
		File file = new File(srcPath+srcFile);
		if(file.exists()){
			FileInputStream input=new FileInputStream(file);
			FileOutputStream output=new FileOutputStream(destPath+"/"+destFile);
			byte[] b=new byte[1024*5];
			int len;
			while((len=input.read(b))!=-1){
				output.write(b,0,len);
			}
			output.flush();
			output.close();
			input.close();
		}
	}
	public File[] getFiles(String dirPath, String fileName,String prefix,String postfix){
		File myDir = new File(dirPath);
	    // Define a filter for java source files beginning with fileName[*
	    FilenameFilter select = new FileListFilter(fileName + prefix, postfix);
	    File[] contents = myDir.listFiles(select);
	    return contents;
	}
	class FileListFilter implements FilenameFilter {
		  private String name; 
		  private String extension; 

		  public FileListFilter(String name, String extension) {
		    this.name = name;
		    this.extension = extension;
		  }

		  public boolean accept(File directory, String filename) {
		    boolean fileOK = true;

		    if (name != null) {
		      fileOK &= filename.startsWith(name);
		    }

		    if (extension != null) {
		      fileOK &= filename.endsWith('.' + extension);
		    }
		    return fileOK;
		  }
	}
}
