package com.ticket.dto;

import java.util.List;

import com.base.model.UserPojo;

public class GroupUser {
	private long totalgroup;
	private long currentGroupId;
	private String groupName;
	private String currentUserId;
	private String UserName;
	public long getTotalgroup() {
		return totalgroup;
	}
	public void setTotalgroup(long totalgroup) {
		this.totalgroup = totalgroup;
	}
	public long getCurrentGroupId() {
		return currentGroupId;
	}
	public void setCurrentGroupId(long currentGroupId) {
		this.currentGroupId = currentGroupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCurrentUserId() {
		return currentUserId;
	}
	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}
	
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}

	
}