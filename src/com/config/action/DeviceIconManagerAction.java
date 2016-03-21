package com.config.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.base.model.DeviceIcon;
import com.base.model.DeviceType;
import com.base.util.Constants;
import com.config.dao.DeviceDAO;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * 作者： 郭玺
 * 创建于 2010-6-27  下午04:46:31
 * 类描述
 */
public class DeviceIconManagerAction extends ActionSupport {


	private static final long serialVersionUID = 1L;
	private List<DeviceIcon> iconList;
	private String start;
	private String limit;
	private String totalCount;
	private DeviceIcon deviceIcon;
    private File upload;
    private String uploadFileName;
	private boolean success;
	private List<DeviceType> deviceTypeList;
    private String allowTypes;
    private String uploadContentType;
    private String savePath;
	/**
	 * 得到所有的图标
	 * @return
	 * @throws Exception
	 */
	public String listAllDeviceIcons() throws Exception {
		DeviceDAO deviceDAO = new DeviceDAO();
		iconList = deviceDAO.getAllDeviceIcons(start, limit);
		success = true;
		return SUCCESS;
	}
	
	
	/**
	 * 删除图标
	 * @return
	 * @throws Exception
	 */
	public String deleteDeviceIcon() throws Exception {
		String deviceIconId = ServletActionContext.getRequest().getParameter("id");
		DeviceDAO deviceDAO = new DeviceDAO();
		if (StringUtils.isNotEmpty(deviceIconId)) {
			deviceDAO.deleteDeviceIcon(deviceIconId);
			success = true;
		}
		return SUCCESS;
	}
	
	/**
	 * 添加图标
	 * @return
	 * @throws Exception
	 */
	public String addDeviceIcon() throws Exception {
		DeviceDAO deviceDAO = new DeviceDAO();
		String department = ServletActionContext.getRequest().getParameter("deviceiconname");
		DeviceType deviceType =deviceDAO.getDeviceIconByName(department);
		
		/* 对图标名字的处理   格式如： 设备类型_设备厂家_设备型号.相应扩名   */
		String extension = getUploadFileName().split("\\.")[1];
		String iconFile = department + "_" + deviceIcon.getManufacture() + "_" + deviceIcon.getModel() + "." + extension;
		deviceIcon.setIconFile(iconFile);
		deviceIcon.setDeviceType(deviceType);
		FileOutputStream fos = new FileOutputStream(getSavePath()+"/"+ iconFile);
		FileInputStream fis = new FileInputStream(getUpload());
		try {
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = fis.read(buffer))>0){
				fos.write(buffer,0,len);
			}
			boolean isAdd = deviceDAO.addDeviceIcon(deviceIcon);
			if (isAdd) {
				System.out.println("last success");
				success = true;
			}
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		} finally {
			fos.close();
			fis.close();
		}
		System.out.println("submit");
		return SUCCESS;
	}
	

	/**
	 * 更改图标
	 * @return
	 * @throws Exception
	 */
	public String alterDeviceIcon() throws Exception {
		DeviceDAO deviceDAO = new DeviceDAO();
		String deviceIconId = ServletActionContext.getRequest().getParameter("deviceIconId");
		
		DeviceIcon deviceIcon = deviceDAO.getDeviceIconById(deviceIconId);
		String fileName = deviceIcon.getIconFile();
		//删除原有的
		File source = new File(getSavePath() +"/"+ fileName);
		source.delete();
		/* 对图标名字的处理   格式如： 设备类型_设备厂家_设备型号.相应扩名   */
		FileOutputStream fos = new FileOutputStream(getSavePath() +"/"+ fileName);
		FileInputStream fis = new FileInputStream(getUpload());
		
		try {
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = fis.read(buffer))>0){
				fos.write(buffer,0,len);
			}
			//对记录进行更新
			deviceDAO.alterDeviceIcon(deviceIconId, fileName);
			success = true;
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		} finally {
			fos.close();
			fis.close();
		}
		return SUCCESS;
	}
	
	/**
	 * 得到所有的设备类型
	 * @return
	 * @throws Exception
	 */
	public String listAllDeviceType() throws Exception {
		DeviceDAO deviceDAO = new DeviceDAO();
		deviceTypeList = deviceDAO.listAllDeviceType();
		return SUCCESS;
	}
	
	public List<DeviceIcon> getIconList() {
		return iconList;
	}

	public void setIconList(List<DeviceIcon> iconList) {
		this.iconList = iconList;
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

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}


	public DeviceIcon getDeviceIcon() {
		return deviceIcon;
	}


	public void setDeviceIcon(DeviceIcon deviceIcon) {
		this.deviceIcon = deviceIcon;
	}


	public File getUpload() {
		return upload;
	}


	public void setUpload(File upload) {
		this.upload = upload;
	}


	public boolean getSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public List<DeviceType> getDeviceTypeList() {
		return deviceTypeList;
	}

	public void setDeviceTypeList(List<DeviceType> deviceTypeList) {
		this.deviceTypeList = deviceTypeList;
	}
	
	public String getAllowTypes() {
		return allowTypes;
	}
	
	public void setAllowTypes(String allowTypes) {
		this.allowTypes = allowTypes;
	}
	
	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	
	public String getSavePath() {
		return ServletActionContext.getRequest().getRealPath(savePath);
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}


	public String getUploadFileName() {
		return uploadFileName;
	}


	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

}