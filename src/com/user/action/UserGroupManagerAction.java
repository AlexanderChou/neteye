package com.user.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.base.model.ResourceGroup;
import com.base.model.UserGroup;
import com.base.model.UserGroupPopedom;
import com.base.util.BaseAction;
import com.user.dao.ResourceGroupDAO;
import com.user.dao.UserGroupDAO;
import com.user.dao.UserGroupPopedomDAO;

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
 * 用户组管理action
 * @author 李宪亮
 *
 */
public class UserGroupManagerAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private UserGroupDAO userGroupDAO = new UserGroupDAO();
	private UserGroupPopedomDAO userGroupPopedomDAO = new UserGroupPopedomDAO();
	private UserGroup userGroup;
	private ResourceGroup resourceGroup;
	private List<UserGroup> userGroups;
	private List<ResourceGroup> resourceGroups;
	private String totalCount;
	private boolean isHave;
	private String userGroupName;
	private boolean success;
	private boolean failure;
	
	/**
	 * 对添加页面的初始化
	 * @return
	 * @throws Exception
	 */
	public String initUserGroup() throws Exception {
		String userGroupId = this.getRequest().getParameter("userGroupId");
		if (StringUtils.isNotEmpty(userGroupId)) {
			userGroup = userGroupDAO.getUserGroupById(Long.parseLong(userGroupId));
		}
		return SUCCESS;
	}
	
	/**
	 * 以 form 的形式添加用户组
	 * @return
	 * @throws Exception
	 */
	public String addUserGroupByForm() throws Exception {
		UserGroupDAO userGroupDao = new UserGroupDAO();
		boolean isExist = userGroupDao.checkUserGroupNameIsHave(userGroup.getName());
		
		if (isExist) {
			failure = true;
			return SUCCESS;
		}
		
		success = true;
		userGroupDao.save(userGroup);
		return super.execute();
	}	
	
	/**
	 * 添加（修改）用户组
	 * @return
	 * @throws Exception
	 */
	public String addUserGroup() throws Exception {
		String userGroupId = this.getRequest().getParameter("userGroupId").trim();
		String userGroupName = this.getRequest().getParameter("userGroupName").trim();
		String userGroupNamesubtxt = this.getRequest().getParameter("userGroupNamesubtxt").trim();
		if (StringUtils.isNotEmpty(userGroupName)) {
			UserGroup ug = new UserGroup();
			ug.setName(userGroupName);
			ug.setSubtxt(userGroupNamesubtxt);
			if (StringUtils.isNotEmpty(userGroupId)) {
				ug.setId(Long.parseLong(userGroupId));
			}
			userGroupDAO.save(ug);
			PrintWriter writer = this.getResponse().getWriter();
			writer.print("ok");
			writer.close();
		}
		return null;
	}
	
	/**
	 * 用户组列表 个人用
	 * @return
	 * @throws Exception
	 */
	public String listUserGroup() throws Exception {
		String userId = this.getRequest().getParameter("userId");
		if (StringUtils.isNotEmpty(userId)) {
			List<Object[]> list = userGroupDAO.getAllUserGroups(userId);
			for (Object[] objects : list) {
				UserGroup userGroup = new UserGroup();
				userGroup.setId(Long.parseLong(objects[0].toString()));
				userGroup.setName(objects[1].toString());
				userGroups.add(userGroup);
			}
		} else {
			userGroups = userGroupDAO.getAllUserGroups();
		}
		return SUCCESS;
	}
	
	/**
	 * 列出所有的用户组
	 * @return
	 * @throws Exception
	 */
	public String listAllUserGroup() throws Exception {
		totalCount = String.valueOf(userGroupDAO.getUserGroupsCount());
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		userGroups = userGroupDAO.getAllUserGroups(start, limit);
		return SUCCESS;
	}
	
	/**
	 * 删除用户组
	 * @return
	 * @throws Exception
	 */
	public String deleteUserGroup() throws Exception {
		String[] userGroupIds = this.getRequest().getParameter("userGroupIds").trim().split(";");
		for (String userGroupId : userGroupIds) {
			UserGroup ug = new UserGroup();
			ug.setId(Long.parseLong(userGroupId));
			userGroupDAO.delete(ug);
			PrintWriter writer = this.getResponse().getWriter();
			writer.print("ok");
			writer.close();
		}
		return null;
	}
	
	
	/**
	 * 列出所有的待选资源
	 * @return
	 * @throws Exception
	 */
	public String listAllResourceGroup() throws Exception {
		String userGroupId = this.getRequest().getParameter("userGroupId");
		ResourceGroupDAO resourceGroupDao = new ResourceGroupDAO();
		resourceGroups = resourceGroupDao.getResourceGroups(userGroupId);
		return SUCCESS;
	}

	/**
	 * 保存资源组
	 * @return
	 * @throws Exception
	 */
	public String assignPopedom() throws Exception {
		String userGroupId = this.getRequest().getParameter("userGroupId");
		String[] resourceGroupIds = this.getRequest().getParameter("resourceGroupIds").split(";");
		
		//先把权限组里userGroupId的记录删掉
		userGroupPopedomDAO.deleteBatchByUserGroup(Long.parseLong(userGroupId));
		for (String resourceGroupId : resourceGroupIds) {
			if(!"".endsWith(resourceGroupId)){
				UserGroupPopedom popedom = new UserGroupPopedom();
				popedom.setResourceGroupId(Long.parseLong(resourceGroupId));
				popedom.setUserGroupId(Long.parseLong(userGroupId));
				userGroupPopedomDAO.save(popedom);
			}
		}
		
		/** 告诉浏览器操作成功 */
		PrintWriter writer = this.getResponse().getWriter();
		writer.print("ok");
		writer.close();
		
		return null;
	}
	
	
	/**
	 * 由用户组列出所有的资源
	 * @return
	 * @throws Exception
	 */
	public String listResourceByUserGroup() throws Exception {
		String userGroupId = this.getRequest().getParameter("userGroupId");
		List<Object[]> rgs = userGroupPopedomDAO.getResourceGroupByugId(userGroupId);
		resourceGroups = new ArrayList<ResourceGroup>();
		for (Object[] objects : rgs) {
			ResourceGroup group = new ResourceGroup();
			group.setId(Long.parseLong(objects[0].toString()));
			group.setName(objects[1].toString());
			resourceGroups.add(group);
		}
		return SUCCESS;
	}
	
	
	/**
	 * 检查用户组的名字是已经存在
	 * @return
	 * @throws Exception
	 */
	public String checkUserGroupNameIsHave() throws Exception {
		isHave = userGroupDAO.checkUserGroupNameIsHave(userGroupName);
		return SUCCESS;
	}
	
	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public ResourceGroup getResourceGroup() {
		return resourceGroup;
	}

	public void setResourceGroup(ResourceGroup resourceGroup) {
		this.resourceGroup = resourceGroup;
	}

	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public List<ResourceGroup> getResourceGroups() {
		return resourceGroups;
	}

	public void setResourceGroups(List<ResourceGroup> resourceGroups) {
		this.resourceGroups = resourceGroups;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}


	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public boolean isHave() {
		return isHave;
	}

	public void setHave(boolean isHave) {
		this.isHave = isHave;
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
