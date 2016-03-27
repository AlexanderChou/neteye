package com.savi.user.action;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.savi.base.model.Administrator;
import com.savi.user.dao.AdminDao;

@SuppressWarnings("serial")
public class AdminAction extends BaseAction {
	private AdminDao adminDao = new AdminDao();

	private Administrator admin;
	private List<Administrator> adminList;
	
	private String names;
	private String name;
	private String oldPassword;
	private String password;
	private String rePassword;
	private String role;//1,2,3:普通用户，管理员，超级管理员
	private String roleName;

	private String totalCount;
	private String start;
	private String limit;

	private boolean success;
	private boolean failure;
	private String errMsg="";
	private String login_info;

	/**
	 * 用户登录
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	public String login() throws Exception {
		Administrator admin = adminDao.getAdmin(name);
		
		if (admin != null) {
			if (admin.getPassword().equals(password)) {
				this.getSession().setAttribute("username", admin.getName());
				this.getSession().setAttribute("password", admin.getPassword());
				this.getSession().setAttribute("role",admin.getRole().toString());

				login_info = "success";
			} else {
				login_info = getText("loginInfo.wrongPassword");
			}
		} else {
			login_info = getText("loginInfo.userNotExist");
		}
		return SUCCESS;
	}
	@JSON(serialize=false)
	public String login2() throws Exception {
		Administrator admin = adminDao.getAdmin(name);		
		if (admin != null) {
			if (admin.getPassword().equals(password)) {
				this.getSession().setAttribute("username", admin.getName());
				this.getSession().setAttribute("password", admin.getPassword());
				this.getSession().setAttribute("role",admin.getRole().toString());
				return SUCCESS;
			} else {
				return "failed";
			}
		} else {
			return "failed";
		}
	}
	/**
	 * 用户注销
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	public String logout() throws Exception {
		this.getSession().removeAttribute("username");
		this.getSession().removeAttribute("password");
		this.getSession().removeAttribute("role");
		this.getSession().invalidate();
		return SUCCESS;
	}
	
	
	//用户更新自己的信息
	@JSON(serialize=false)
	public String updateByMyself() throws Exception {
		success = false;
		failure = true;
		
		name = (String) this.getSession().getAttribute("username");

		if(name == null || name.equals("") || (admin=adminDao.getAdmin(name)) == null)
			errMsg = "Your session is timeout!";
		else if(oldPassword == null || oldPassword.equals(""))
			errMsg = "The old password can't be empty!";
		else if(!oldPassword.equals(admin.getPassword()))
			errMsg = "The old password is incorrect!";
		else if(password == null || password.equals(""))
			errMsg = "The password can't be empty!";
		else if(!password.equals(rePassword))
			errMsg = "The password is not matching with confirm password!";
	
		if(!errMsg.equals("")){
			return SUCCESS;
		}
		
		admin.setPassword(password);
		
		adminDao.save(admin);
		
		success = true;
		failure = false;
		
		return SUCCESS;
	}
	
	//超级管理员更新其他用户信息
	@JSON(serialize=false)
	public String updateByAdmin() throws Exception {
		success = false;
		failure = true;
		
		if(name == null || name.equals("") || (admin=adminDao.getAdmin(name)) == null)
			errMsg = "The user can't be found!";
		else if(password == null || password.equals(""))
			errMsg = "The password can't be empty!";
		else if(!password.equals(rePassword))
			errMsg = "The password is not matching with confirm password!";
		else if(role == null || (!role.equals("1") && !role.equals("2") && !role.equals("3")))
			errMsg = "The role is wrong!";
	
		if(!errMsg.equals("")){
			return SUCCESS;
		}
		
		admin.setPassword(password);
		admin.setRole(Integer.parseInt(role));
		
		adminDao.save(admin);
		
		success = true;
		failure = false;
		
		return SUCCESS;
	}
	
	//超级管理员删除用户
	@JSON(serialize=false)
	public String delete() throws Exception {
		success = false;
		failure = true;
		
		if(names == null){
			errMsg = "The request is incorrect!";
			return SUCCESS;
		}
		
		String[] nameArr = names.split("\\|");
	
		for(int i = 0; i < nameArr.length; i++){
			String name = nameArr[i];
			if(name.equals("") || (admin=adminDao.getAdmin(name)) == null){
				errMsg = "The user: " + name + " can't be found!";
				return SUCCESS;
			}
			adminDao.delete(admin);
		}
		
		success = true;
		failure = false;
		
		return SUCCESS;
	}
	
	//超级管理员添加用户
	@JSON(serialize=false)
	public String add() throws Exception {
		success = false;
		failure = true;
		
		admin = new Administrator();
		
		if(name == null || name.equals(""))
			errMsg = "The username can't be empty!";
		else if( adminDao.getAdmin(name) != null)
			errMsg = "The user has been existed!";
		else if(password == null || password.equals(""))
			errMsg = "The password can't be empty!";
		else if(!password.equals(rePassword))
			errMsg = "The password is not matching with confirm password!";
		else if(role == null || (!role.equals("1") && !role.equals("2") && !role.equals("3")))
			errMsg = "The role is wrong!";
		
		if(!errMsg.equals("")){
			return SUCCESS;
		}
		
		admin.setName(name);
		admin.setPassword(password);
		admin.setRole(Integer.parseInt(role));
		
		adminDao.save(admin);
		
		success = true;
		failure = false;
		
		return SUCCESS;
	}
	
	//超级管理员列出所有用户信息
	@JSON(serialize=false)
	public String listAdminList()throws Exception {
		adminList = adminDao.listUsers(start, limit);
		totalCount = adminDao.getUserCount().toString();
		return SUCCESS;
	}
	@JSON(serialize=false)
	public Administrator getAdmin() {
		return admin;
	}

	public void setAdmin(Administrator admin) {
		this.admin = admin;
	}

	public List<Administrator> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Administrator> adminList) {
		this.adminList = adminList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
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

	public void setLogin_info(String loginInfo) {
		login_info = loginInfo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return this.roleName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setOldPassword(String oldPassword){
		this.oldPassword = oldPassword;
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

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}
	
	

}
