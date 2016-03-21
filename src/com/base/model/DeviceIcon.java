package com.base.model;

/**
 * 
 * 作者：郭玺
 * 创建于 2010-6-27  下午04:02:24
 * 类描述
 */
public class DeviceIcon extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/* 设备厂家 */
	private String manufacture;
	/* 设备型号 */
	private String model;
	/* 描述 */
	private String description;
	/* 图标文件 */
	private String iconFile;
	/* 设备类型 */
	private DeviceType deviceType;
	public String getManufacture() {
		return manufacture;
	}
	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIconFile() {
		return iconFile;
	}
	public void setIconFile(String iconFile) {
		this.iconFile = iconFile;
	}
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
}
