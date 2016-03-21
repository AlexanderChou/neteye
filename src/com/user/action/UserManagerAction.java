package com.user.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.base.model.Department;
import com.base.model.UserGroup;
import com.base.model.UserPojo;
import com.base.model.UserPopedom;
import com.base.service.ViewService;
import com.base.util.BaseAction;
import com.base.util.Constants;
import com.config.dao.DeviceDAO;
import com.user.dao.DepartmentDAO;
import com.user.dao.FileForUserDAO;
import com.user.dao.LogDAO;
import com.user.dao.PopedomDAO;
import com.user.dao.UserDAO;
import com.user.dao.UserPopedomDAO;

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
 * 用户管理
 * @author 李宪亮
 *
 */
public class UserManagerAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private UserPojo user;
	private UserDAO userDao = new UserDAO();
	private UserGroup userGroup = new UserGroup();
	private UserPopedomDAO userPopedomDao = new UserPopedomDAO();
	private FileForUserDAO fileForUserDao = new FileForUserDAO();
	private DeviceDAO deviceDAO = new DeviceDAO();
	private List<UserPojo> users;
	private LogDAO logDao = new LogDAO();
	private DepartmentDAO departmentDao = new DepartmentDAO();
	private PopedomDAO popedomDao = new PopedomDAO();
	private List<Object[]> userGroups; 
	private String totalCount;
	private boolean success;
	private boolean failure;
	private String errMsg;
	private String login_info;
	private String saviIP = "localhost";
	
	/**
	 * 用户身份切换
	 * @return
	 * @throws Exception
	 */
	public String changeUser() throws Exception {
		UserPojo u = userDao.getUserByName(user.getName());
		
		if (u != null) {
			if (u.getPassword().equals(user.getPassword())) {
				
				Long logId = (Long)this.getSession().getAttribute("logId");
				if (logId == null) {
					logId = Long.parseLong(this.getRequest().getParameter("logId"));
				}
				logDao.updateLogByLogId(logId);
				this.getSession().removeAttribute("userId");
				this.getSession().removeAttribute("userName");
				this.getSession().removeAttribute("logId");
				this.getSession().removeAttribute("userPwd");
				
				this.getSession().setAttribute("userId", u.getId());
				this.getSession().setAttribute("userName", u.getName());
				this.getSession().setAttribute("userPwd", u.getPassword());
				if(this.isMerger()){
					
				}
				String requestIP = this.getRequest().getRemoteHost();
				logDao.addLogInfo(u.getName(), requestIP);
				success = true;
				return SUCCESS;
			} else {
				errMsg = "输入密码不对，请重新输入！";
				failure = true;
				return SUCCESS;
			}
		} else {
			errMsg = "该用户不存在，请校对输入用户名！";
			failure = true;
			return SUCCESS;
		}
		
	}
	
	/**
	 * 用户登录
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		/*
		if(this.isMerger()){
			SaviDAO saviDao=new SaviDAO();
			List<UserPojo> userList=userDao.getUserList();
			for(UserPojo us:userList){
//				System.out.println("id="+us.getId());
				Administrator admin=saviDao.getAdminById(us.getId());
				if(admin==null){
					Administrator saviUser=new Administrator();
					//saviUser.setId(us.getId());
					saviUser.setName(us.getName());
					saviUser.setPassword(us.getPassword());
					saviUser.setRole(3);
					saviDao.save(saviUser);
				}else{
					admin.setName(us.getName());
					admin.setPassword(us.getPassword());
					saviDao.save(admin);
				}
			}
		}*/
		String userName = this.getRequest().getParameter("name");
		String password = this.getRequest().getParameter("password");
		user = new UserPojo();
		user.setName(userName);
		user.setPassword(password);
		
		
		UserPojo u = userDao.getUserByName(user.getName());
		
		if (u != null) {
			if (u.getPassword().equals(user.getPassword())) {
				
				//com.savi.user.dao.AdminDao adminDao = new AdminDao();
				//com.savi.base.model.Administrator admin = adminDao.getAdmin(user.getName());
				this.getSession().setAttribute("userId", u.getId());
				this.getSession().setAttribute("userName", u.getName());
				this.getSession().setAttribute("userPwd", u.getPassword());
				this.getSession().setAttribute("saviIP", this.saviIP);
				//this.getSession().setAttribute("role",admin.getRole().toString());
				
				String requestIP = this.getRequest().getRemoteHost();
				logDao.addLogInfo(u.getName(), requestIP);
				
				/** 在这里增加一个判断 判断该人的个人文件夹是否存在，不存在则创建 */
				String filePath = Constants.webRealPath + "/file/user/" + u.getName() + "_" + u.getId()  + "/";
				File file = new File(filePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				login_info = "success";
			} else {
				login_info = "用户名和密码不匹配，请核实后再登陆！";
			}
		} else {
			login_info = "对不起该用户不存在！";
		}
		return SUCCESS;
	}
	//给毕军的接口相应的处理action
	public String superLogin() throws Exception {
		String userName = this.getRequest().getParameter("name");
		String password = this.getRequest().getParameter("password");
		
		user = new UserPojo();
		user.setName(userName);
		user.setPassword(password);	
		UserPojo u = userDao.getUserByName(user.getName());
		if (u != null) {
			if (u.getPassword().equals(user.getPassword())) {				
				//com.savi.user.dao.AdminDao adminDao = new AdminDao();
				//com.savi.base.model.Administrator admin = adminDao.getAdmin(user.getName());
				this.getSession().setAttribute("userId", u.getId());
				this.getSession().setAttribute("userName", u.getName());
				this.getSession().setAttribute("userPwd", u.getPassword());
				this.getSession().setAttribute("saviIP", this.saviIP);
				//this.getSession().setAttribute("role",admin.getRole().toString());				
				String requestIP = this.getRequest().getRemoteHost();
				logDao.addLogInfo(u.getName(), requestIP);			
				/** 在这里增加一个判断 判断该人的个人文件夹是否存在，不存在则创建 */
				String filePath = Constants.webRealPath + "/file/user/" + u.getName() + "_" + u.getId()  + "/";
				File file = new File(filePath);
				if (!file.exists()) {
					file.mkdirs();
				}	
			} else {
				return "failed";
			}
		} else {
			return "failed";
		}
		return SUCCESS;
	}
	/**
	 * 用户注销
	 * @return 
	 * @throws Exception
	 */
	public String logout() throws Exception {
		String type = this.getRequest().getParameter("type");
		if ("changeUser".equals(type)) {
			return "changeUser";
		} else {
			Long logId = (Long)this.getSession().getAttribute("logId");
			if (logId == null) {
				logId = Long.parseLong(this.getRequest().getParameter("logId"));
			}
			logDao.updateLogByLogId(logId);
			this.getSession().removeAttribute("userId");
			this.getSession().removeAttribute("userName");
			this.getSession().removeAttribute("userPwd");
			this.getSession().removeAttribute("logId");
			return "welcome";
		}
	}
		
	/**
	 * 用户初始化
	 * @return
	 * @throws Exception
	 */
	public String initUser() throws Exception {
		String userId = String.valueOf(this.getSession().getAttribute("userId"));
		if (StringUtils.isNotEmpty(userId)) {
			user = userDao.getUserById(Long.parseLong(userId));
		}
		
		List<Department> departments = departmentDao.getAllDepartment();
		this.getRequest().setAttribute("departments", departments);
		return SUCCESS;
	}
	
	/**
	 * 编辑个人信息
	 * @return
	 * @throws Exception
	 */
	public String editUserInfo() throws Exception {
		userDao.save(user);
		/*/如果包含savimanager网管，修改savimanager网管数据库
		if(this.isMerger()){
			SaviDAO saviDao=new SaviDAO();
			Administrator admin=saviDao.getAdminById(user.getId());
			if(admin==null){
				Administrator saviUser=new Administrator();
				saviUser.setId(user.getId());
				saviUser.setName(user.getName());
				saviUser.setPassword(user.getPassword());
				saviUser.setRole(2);
				saviDao.save(saviUser);
			}else{
				admin.setName(user.getName());
				admin.setPassword(user.getPassword());
				saviDao.save(admin);
			}
		}*/
		return null;
	}
	
	
	/**
	 * 添加用户  通过form表单提交
	 * @return
	 * @throws Exception
	 */
	public String addUserByForm() throws Exception {
		UserDAO userDao = new UserDAO();
		
		boolean userNameIsHave = userDao.checkUserNameIsExist(user.getName());
		if (userNameIsHave) {
			failure = true;
			return SUCCESS;
		}
		
		String departmentId = this.getRequest().getParameter("departmentId");
		Department department = new Department();
		department.setId(Long.parseLong(departmentId));
		user.setDepartment(department);
		userDao.save(user);
		/*/如果包含savimanager网管，修改savimanager网管数据库
		if(this.isMerger()){
			SaviDAO saviDao=new SaviDAO();
			Long userId=userDao.getUserByName(user.getName()).getId();
			Administrator admin=saviDao.getAdminById(userId);
			if(admin==null){
				Administrator saviUser=new Administrator();
				saviUser.setId(userId);
				saviUser.setName(user.getName());
				saviUser.setPassword(user.getPassword());
				saviUser.setRole(2);
				saviDao.save(saviUser);
			}else{
				admin.setName(user.getName());
				admin.setPassword(user.getPassword());
				saviDao.save(admin);
			}
			
		}*/
		success = true;
		return SUCCESS;
	}
	
	/**
	 * 添加用户
	 * @return
	 * @throws Exception
	 */
	public String modifyUser() throws Exception {
		String userId = this.getRequest().getParameter("userId");
		String paramName = this.getRequest().getParameter("paramName");
		String paramValue = this.getRequest().getParameter("paramValue");
		
		UserPojo user = this.userDao.getUserById(Long.parseLong(userId));
		
		if ("name".equals(paramName)) {
			boolean userNameIsHave = userDao.checkUserNameIsExist(paramValue);
			if (userNameIsHave) {
				/* 告诉页面该用户名已经存在 */
				PrintWriter w = this.getResponse().getWriter();
				w.print("same");
				w.close();
				return null;
			} else {
				user.setName(paramValue);
			}
		} 
		
		if ("realName".equals(paramName)) {
			user.setRealName(paramValue);
		}
		
		if ("password".equals(paramName)) {
			user.setPassword(paramValue);
		}
		
		if ("email".equals(paramName)) {
			user.setEmail(paramValue);
		}
		
		if ("telephone".equals(paramName)) {
			user.setTelephone(paramValue);
		}
		
		if ("department".equals(paramName)) {
			
			if (StringUtils.isNotEmpty(paramValue)) {
				long id = 0;
				try {
					id = Long.parseLong(paramValue);
				} catch (NumberFormatException e) {
					/** 前台传过来的有可能departmentId 有可能是字符串名字 */
					id = departmentDao.getDepartmentByName(paramValue).getId();
				}
				
				Department department = new Department();
				department.setId(id);
				user.setDepartment(department);
			} else {
				/** 添加一个默认组 */
				Department department = departmentDao.getDepartmentById(1l);
				/**  如果没有id为默认部门添加一个  */
				if (department == null) {
					department = new Department();
					department.setId(1l);
					department.setName("默认部门");
					departmentDao.save(department);
				}
				user.setDepartment(department);
			}
		}
		
		userDao.save(user);
		/*/如果包含savimanager网管，修改savimanager网管数据库
		if(this.isMerger()){
			SaviDAO saviDao=new SaviDAO();
			Administrator admin=saviDao.getAdminById(user.getId());
			if(admin==null){
				Administrator saviUser=new Administrator();
				saviUser.setId(user.getId());
				saviUser.setName(user.getName());
				saviUser.setPassword(user.getPassword());
				saviUser.setRole(2);
				saviDao.save(saviUser);
			}else{
				admin.setName(user.getName());
				admin.setPassword(user.getPassword());
				saviDao.save(admin);
			}
			
		}*/
		String userXMLSavePath = Constants.webRealPath + "/file/user/" + user.getName() + "_" + user.getId()  + "/";
		File file = new File(userXMLSavePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		userXMLSavePath = userXMLSavePath + user.getName()+ ".xml" ;
		fileForUserDao.initUserInfoToXml(user, userXMLSavePath);
		
		/** 告诉前台可以操作成功  */
		PrintWriter write = this.getResponse().getWriter();
		write.print("ok");
		write.close();
		return null;
	}
	
	/**
	 * 用户列表
	 * @return
	 * @throws Exception
	 */
	public String listUsers() throws Exception {
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		users = userDao.getAllUsers(start, limit);
		totalCount = String.valueOf(userDao.getUsersCount());
		return SUCCESS;
	}
	
    /**
     * 通过用户得到所在的用户组
     * @return
     * @throws Exception
     */
	public String listUserGroupByUserId() throws Exception {
		String userId = this.getRequest().getParameter("userId");
		userGroups = popedomDao.getUserGroupsByUserId(userId);
		return SUCCESS;
	}
	
	
	/**
	 * 删除用户
	 * @return
	 * @throws Exception
	 */
	public String deleteUser() throws Exception {
		String[] userIds = this.getRequest().getParameter("userIds").trim().split(";");
		for (String userId : userIds) {
			UserPojo u = new UserPojo();
			u.setId(Long.parseLong(userId));
			//在数据库中删除用户
			userDao.delete(u);
			/*/如果包含savimanager网管，修改savimanager网管数据库
			if(this.isMerger()){
				SaviDAO saviDao=new SaviDAO();
				Administrator saviUser=saviDao.getAdminById(Long.parseLong(userId));
				if(saviUser!=null){
					saviDao.delete(saviUser);
				}
			}*/			
			//删除对应的文件信息
			fileForUserDao.deleteUserInfo(userId);
			//在视图链路中删除该用户的视图信息
			ViewService viewService = new ViewService();
			viewService.deleteViewByUserId(userId);
		}
		PrintWriter writer = this.getResponse().getWriter();
		writer.print("ok");
		writer.close();
		return null;
	}
	
	/**
	 * 添加用户权限
	 * @return
	 * @throws Exception
	 */
	public String addUserPopedom() throws Exception {
		String userId = this.getRequest().getParameter("userId");
		String[] ids = this.getRequest().getParameter("groupIds").split(";");
		//把该用户用户权限全部去掉
		//userPopedomDao.deleteBatch(Long.parseLong(userId));
		//将新选择的用户组加入user_poperdom表
		boolean isUpdate = userDao.updates(ids, Long.valueOf(userId));
		//更新xml文件
		if (isUpdate) {
			UserPojo u = userDao.getUserById(Long.parseLong(userId));
			String userXMLSavePath = Constants.webRealPath + "/file/user/" + u.getName() + "_" + u.getId() + "/" + u.getName() + ".xml";
			File file = new File(Constants.webRealPath + "/file/user/" + u.getName() + "_" + u.getId() + "/");
			if (!file.exists()) {
				file.mkdirs();
			}
			fileForUserDao.initUserInfoToXml(u, userXMLSavePath);
		}
		return null;
	}
	
	/**
	 * 用户登录后看是否有必要进行拓扑发现
	 * @return
	 * @throws Exception
	 */
	public String isNew() throws Exception {
		String isNew = "";
		boolean isHaveData = deviceDAO.deviceTableIsHaveData();
		if (isHaveData) {
			isNew = "yes";
		} else {
			isNew = "no";
		}
		PrintWriter writer = ServletActionContext.getResponse().getWriter();
		writer.print(isNew);
		writer.close();
		return SUCCESS;
	}
	
	/**
	 * 某用户用户组列表
	 * @return
	 * @throws Exception
	 */
	public String user_groupList() throws Exception {
		String userId = this.getRequest().getParameter("userId");
		List<UserPopedom> ups = userPopedomDao.getUserPopedomByUserId(Long.parseLong(userId));
		this.getRequest().setAttribute("userPopedoms", ups);
		return SUCCESS;
	}
	
	public UserPojo getUser() {
		return user;
	}
	
	public void setUser(UserPojo user) {
		this.user = user;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public List<UserPojo> getUsers() {
		return users;
	}

	public void setUsers(List<UserPojo> users) {
		this.users = users;
	}


	public List<Object[]> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<Object[]> userGroups) {
		this.userGroups = userGroups;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
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

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getLogin_info() {
		return login_info;
	}

	public void setLogin_info(String login_info) {
		this.login_info = login_info;
	}
	public boolean isMerger() throws Exception{
//		boolean flag=false;
//		String filePath=Constants.webRealPath + "/file/user/user.conf";
//		File file=new File(filePath);
//		if(file.exists()){
//			Properties p = new Properties();
//			//p.load(new BufferedReader(new FileReader(filePath)));
//			String mergerValue = p.getProperty("merger");
//			if(mergerValue.equals("true")){
//				flag = true;
//			}
//			saviIP = p.getProperty("saviIP");
//		}
		
		return true;
	}
	public static void main(String[] args) throws Exception{
		Properties p = new Properties();
		//p.load(new BufferedReader(new FileReader("d:/user.conf")));
		String mergerValue = p.getProperty("merger");
		String saviIP = p.getProperty("saviIP");
	}
}
