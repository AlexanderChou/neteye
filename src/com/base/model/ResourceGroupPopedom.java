package com.base.model;

/**
 * <p>Title:实体类 </p>
 * <p>Description: 资源和资源组关联类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: bjrongzhi</p>
 * @author 李宪亮
 * @version 1.0
 */
public class ResourceGroupPopedom extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private Long resourceId;
	private Long resourceGroupId;
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public Long getResourceGroupId() {
		return resourceGroupId;
	}
	public void setResourceGroupId(Long resourceGroupId) {
		this.resourceGroupId = resourceGroupId;
	}
}
