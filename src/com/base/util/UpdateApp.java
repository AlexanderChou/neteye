package com.base.util;

import java.io.File;
import java.text.MessageFormat;
import java.util.Date;

import com.base.service.DataManageService;
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
 * <p>Title: 为系统升级提供数据备份与恢复（由shell命令直接调用）</p>
 * <p>Description: 包括对数据库、配置文件及rrd文件的备份和恢复</p>
 * @version 1.0
 * @author 郭玺
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2010</p>
 */
public class UpdateApp {
	private static final String TASK_RESTORE = "restore";
	private static final String TASK_BACKUP = "backup";
	private static DataManageService dataManageService = new DataManageService();
	public static void main(String[] args){
		if(args.length == 0){
			usage();
			return;
		}
		String taskName = args[0];
		String fileName = null;
			try{
				if (TASK_RESTORE.equals(taskName)){//恢复
					fileName = args[1];
					if(fileName!=null){
					    if(fileName.equals("${args}")){
							fileName = MessageFormat.format("{0,date,yyyy-MM-dd}" ,new Date(new Long(System.currentTimeMillis())));
					    }
					    dataManageService.restoreDatabase("1",fileName);
					}else{
						usage();
						return;
					}
				}else if(TASK_BACKUP.equals(taskName)){//备份
				    long day = 24 * 60 * 60 * 1000;
					Date date = new Date(new Long(System.currentTimeMillis())-7*day);
					String  preDate = MessageFormat.format("{0,date,yyyy-MM-dd}" ,date);
					String rootPath = Constants.BACKUP_PATH + Constants.BACKUP_FOLDER + File.separator + "fix" + File.separator;
					String backupPath =  rootPath + preDate + File.separator ;
					File backupFolder = new File(backupPath);
			        if (backupFolder.exists() && backupFolder.isDirectory()) {
			        	//删除该文件夹及其包含的数据
			        	FileUtil.deleteFolder(backupFolder);
			        }
					fileName = MessageFormat.format("{0,date,yyyy-MM-dd}" ,new Date(new Long(System.currentTimeMillis())));
					boolean flag = dataManageService.backupDataBase("1",fileName);
					if(flag){
						FileUtil.createFile(rootPath+"success.txt","");
					}
				}else{
					usage();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
	}
	/**
	 * shell命令调用的格式和参数
	 */
	private static void usage(){
		System.out.println( "UpdateApp usage:" );
		System.out.println( "\tjava UpdateApp task file" );
		System.out.println( "\tTasks : build | restore" );
		System.out.println( "\tParas : -Dargs=YYYY-MM-dd" );
	}

}
