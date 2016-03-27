package com.base.model;

/**
 * <p>Title:实体类 </p>
 * <p>Description: 用户组和资源组关联类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: bjrongzhi</p>
 * @author 李宪亮
 * @version 1.0
 */
public class UserGroupPopedom extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	/** 资源组 */
	private long resourceGroupId;
	
	/** 用户组 */
	private long userGroupId;
	
	public long getResourceGroupId() {
		return resourceGroupId;
	}
	public void setResourceGroupId(long resourceGroupId) {
		this.resourceGroupId = resourceGroupId;
	}
	public long getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(long userGroupId) {
		this.userGroupId = userGroupId;
	}
}
