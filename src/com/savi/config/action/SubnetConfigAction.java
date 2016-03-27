package com.savi.config.action;

import org.apache.struts2.json.annotations.JSON;

import com.savi.base.model.Subnet;
import com.savi.show.dao.SubnetDao;

@SuppressWarnings("serial")
public class SubnetConfigAction extends BaseAction {
	private SubnetDao subnetDao = new SubnetDao();
	private String ids;
	private Subnet subnet;
	private boolean success;
	private boolean failure;
	private String errMsg = "";

	// 删除子网
	@JSON(serialize = false)
	public String delete() throws Exception {
		success = false;
		failure = true;
		if (ids == null) {
			errMsg = getText("SubnetConfigAction.requestIncorrect");
			return SUCCESS;
		}

		String[] idArr = ids.split("\\|");

		for (int i = 0; i < idArr.length; i++) {
			String id = idArr[i];
			/*
			 * 此处设置了subnet为一个游离态对象，此时session虽然已经关闭，但是如果调用它的方法getSwitchbasicinfos()时
			 * 如果hibernate设置成了延迟加载，那么hibernate会抛出延迟加载的异常。从这里可以判断，虽然该对象已经没有和
			 * session关联（因为session已经清空），但是hibernate仍然是通过某种方式管理着它的。所以在这里不让人调用该
			 * subnet中的getSwitchbasicinfos()方法，就必须设置@JSON(serialize = false)，让json生成的时候不去生成subnet
			 */
			if (id.equals("") || (subnet = subnetDao.getSubnet(id)) == null) {
				errMsg = getText("SubnetConfigAction.subnetNotFound");
				return SUCCESS;
			}

			if (subnetDao.hasActiveSwitchbasicinfos(id)) {
				errMsg = getText("SubnetConfigAction.subnet") + subnet.getName()
						+ " " + getText("SubnetConfigAction.hasSwitches");
				return SUCCESS;
			}
			subnetDao.delete(subnet);
		}
		success = true;
		failure = false;
		return SUCCESS;
	}
	
	//添加或更新子网
	@JSON(serialize = false)
	public String save() throws Exception {
		success = false;
		failure = true;
		
		if (subnet == null) {
			errMsg = getText("SubnetConfigAction.requestIncorrect");
			return SUCCESS;
		}
		
		boolean isNew = true;
		if(subnet.getId() != null) isNew = false;
		
		if(!isNew && subnetDao.getSubnet(subnet.getId().toString()) == null){
			errMsg = getText("SubnetConfigAction.subnetNotExist");
			return SUCCESS;
		}

		subnet.setIsDelete(0);
		subnetDao.save(subnet);

		success = true;
		failure = false;

		return SUCCESS;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	@JSON(serialize = false)
	public Subnet getSubnet() {
		return subnet;
	}

	public void setSubnet(Subnet subnet) {
		this.subnet = subnet;
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

}
