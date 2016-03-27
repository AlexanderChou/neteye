package com.user.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.base.model.Resource;
import com.base.model.ResourceGroup;
import com.base.model.ResourceGroupPopedom;
import com.base.model.UserGroup;
import com.base.model.UserGroupPopedom;
import com.base.model.UserPojo;
import com.base.model.UserPopedom;
import com.base.util.BaseAction;
import com.base.util.Constants;
import com.user.dao.ResourceDAO;
import com.user.dao.ResourceGroupDAO;
import com.user.dao.ResourceGroupPopedomDAO;
import com.user.dao.UserDAO;
import com.user.dao.UserFileInfoDAO;
import com.user.dao.UserGroupPopedomDAO;
import com.user.dao.UserPopedomDAO;

/**
 * <p>Title:业务逻辑类 </p>
 * <p>Description: 资源组管理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: bjrongzhi</p>
 * @author 李宪亮
 * @version 1.0
 */
public class ResourceGroupManagerAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private ResourceGroupDAO resourceGroupDAO=new ResourceGroupDAO();
	private ResourceGroupPopedomDAO rpopedomDAO = new ResourceGroupPopedomDAO();
	private UserGroup userGroup;
	private ResourceGroup resourceGroup;
	private List<ResourceGroup> resourceGroups;
	private List<Resource> resource;
	private String totalCount;
	private boolean isHave;
	private String resourceGroupName;
	private boolean success;
	private boolean failure;
	private UserGroupPopedomDAO userGroupPopedomDAO=new UserGroupPopedomDAO();
	private UserPopedomDAO userPopedomDAO=new UserPopedomDAO();
	private UserDAO userDao=new UserDAO();
	private UserFileInfoDAO fileForUserDAO=new UserFileInfoDAO();
	private String[] ids;
	
	/**
	 * 以 form 的形式添加用户组
	 * @return
	 * @throws Exception
	 */
	public String addResourceGroupByForm() throws Exception {
		boolean isExist = resourceGroupDAO.checkResourceGroupNameIsHave(resourceGroup.getName());			
		if (isExist) {
			failure = true;
			success =false;
			//String content=JsonUtil.handlerJsonData(false, "已存在相同的资源组名称!");
			//JsonUtil.printMsgToClient(content);
			return null;
		}else{		
			resourceGroupDAO.save(resourceGroup);
			success = true;
			return super.execute();	
		}
	}	
	
	/**
	 * 添加（修改）用户组
	 * @return
	 * @throws Exception
	 */
	public String editResourceGroup() throws Exception {
		String resourceGroupId = this.getRequest().getParameter("resourceGroupId").trim();
		String resourceGroupName = this.getRequest().getParameter("resourceGroupName").trim();
		String resourceGroupNamesubtxt = this.getRequest().getParameter("description").trim();
		if (StringUtils.isNotEmpty(resourceGroupName)) {
			ResourceGroup rg = new ResourceGroup();
			rg.setName(resourceGroupName);
			rg.setDescription(resourceGroupNamesubtxt);
			if (StringUtils.isNotEmpty(resourceGroupId)) {
				rg.setId(Long.parseLong(resourceGroupId));
			}
			resourceGroupDAO.save(rg);
			PrintWriter writer = this.getResponse().getWriter();
			writer.print("ok");
			writer.close();
		}
		return null;
	}
	
	
	/**
	 * 删除资源组
	 * @return
	 * @throws Exception
	 */
	public String deleteResourceGroup() throws Exception {
		//String resourceGroupIdStr = this.getRequest().getParameter("resourceGroupIds");
		//if(resourceGroupIdStr!=null){
		//String[] resourceGroupIds = resourceGroupIdStr.trim().split(";");
		String[] resourceGroupIds=ids;
		for (String resourceGroupId : resourceGroupIds) {
			ResourceGroup rg = resourceGroupDAO.getResourceGroupById(Long.parseLong(resourceGroupId));
						
			//删除资源组所拥有的资源
			List<ResourceGroupPopedom> rgPopedoms= rpopedomDAO.getResourceByGroupId(Long.parseLong(resourceGroupId));
			for(ResourceGroupPopedom rgPopedom:rgPopedoms){
				rpopedomDAO.delete(rgPopedom);
			}
			//删除资源组的用户
			List<UserGroupPopedom> ugPopedoms=userGroupPopedomDAO.getPopedomByResourceGroupId(Long.parseLong(resourceGroupId));
			for(UserGroupPopedom ugPopedom:ugPopedoms){
				Long userGroupId=ugPopedom.getUserGroupId();
				userGroupPopedomDAO.delete(ugPopedom);
				//更新本用户组的用户所拥有的资源
				List<UserPopedom> userPopedoms=userPopedomDAO.getUserPopedomByGroupId(userGroupId,"0","15");
				for(UserPopedom uPopedom:userPopedoms){
					Long userId=uPopedom.getUserId();			
					UserPojo user= userDao.getUserById(userId);	
					String SavePath = Constants.webRealPath + "file/user/" + user.getName() + "_" + user.getId()  + "/";			
					File path=new File(Constants.webRealPath + "file/user/" + user.getName() + "_" + user.getId());			
					if(!path.exists()){
						path.mkdirs();
					}
					String userXMLSavePath = SavePath + user.getName()+ ".xml" ;
					File file = new File(userXMLSavePath);
					if(file.exists()){
						file.delete();
					}
					fileForUserDAO.initUserInfoToXml(user, userXMLSavePath);
				}				
			}
			//删除资源组
			resourceGroupDAO.delete(rg);
			PrintWriter writer = this.getResponse().getWriter();
			writer.print("ok");
			writer.close();
		}
		//String msg=	"{success:true,tip:'成功提示',msg:'删除成功！'}";	   
		//JsonUtil.printMsgToClient(msg);
		return null;
	}
	
	/**
	 * 为资源组分配资源
	 * @return
	 * @throws Exception
	 */
	public String assignRPopedom() throws Exception {
		String GroupId = this.getRequest().getParameter("GroupId");
		if(GroupId!=null){
			//每次配置后删除以前所有的资源，可以实现资源任意添加或删除
			List<ResourceGroupPopedom> rgs=rpopedomDAO.getResourceByGroupId(Long.parseLong(GroupId));
			for(ResourceGroupPopedom rg:rgs){
				rpopedomDAO.delete(rg);
			}			
			String[] resourceIds = this.getRequest().getParameter("resourceIds").split(";");
			for (String resourceId : resourceIds) {
				if(!"".endsWith(resourceId)&&!resourceId.equals("source")){
					ResourceGroupPopedom popedom = new ResourceGroupPopedom();
					popedom.setResourceGroupId(Long.parseLong(GroupId));
					popedom.setResourceId(Long.parseLong(resourceId));
					if(rpopedomDAO.ifExist(Long.parseLong(resourceId),Long.parseLong(GroupId))){
					rpopedomDAO.save(popedom);
					}
				}
			}
			/*//为保证所有的用户所分配的资源跟数据库保持一致，需要对xml文件进行更新
			//由资源组ID查找用户组ID
			List<UserGroupPopedom> ugPopedoms=userGroupPopedomDAO.getPopedomByResourceGroupId(Long.parseLong(GroupId));
			for(UserGroupPopedom ugPopedom:ugPopedoms){
				Long userGroupId=ugPopedom.getUserGroupId();
				
				List<UserPopedom> ups=userPopedomDAO.getUserPopedomByGroupId(userGroupId,"0","15") ;
				
				for(UserPopedom userPopedom:ups){
					Long userId=userPopedom.getUserId();			
					UserPojo user= userDao.getUserById(userId);	
					String SavePath = Constants.webRealPath + "file/user/" + user.getName() + "_" + user.getId()  + "/";			
					File path=new File(Constants.webRealPath + "file/user/" + user.getName() + "_" + user.getId());			
					if(!path.exists()){
						path.mkdirs();
					}
					String userXMLSavePath = SavePath + user.getName()+ ".xml" ;
					File file = new File(userXMLSavePath);
					if(file.exists()){						
						file.delete();
					}
					fileForUserDAO.initUserInfoToXml(user, userXMLSavePath);
				}
			}*/
			/** 告诉浏览器操作成功 */
			PrintWriter writer = this.getResponse().getWriter();
			writer.print("ok");
			writer.close();
		}
		return null;
	}
	
	/**
	 * 列出所有资源组
	 * @return
	 * @throws Exception
	 */
	public String allResourceGroup() throws Exception {
		ResourceGroupDAO resourceGroupDAO = new ResourceGroupDAO();
		totalCount = String.valueOf(resourceGroupDAO.getResourceGroupsCount());
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		if(start==null) start = "0";
		if(limit==null) limit = "28";
		resourceGroups = resourceGroupDAO.getListResourceGroup(Integer.valueOf(start), Integer.valueOf(limit));
		success = true;
		return SUCCESS;
		/*
		Page page=super.getPage();
		resourceGroups = resourceGroupDAO.getListResourceGroup(page.getRecordIndex(),page.getPageSize());
		page.setTotalRecords(resourceGroupDAO.getResourceGroupsCount());
		StringBuffer buf = new StringBuffer("[");
		String resultStr = "";
		for(ResourceGroup rg:resourceGroups){
			buf.append("{id:'" + rg.getId() + "'");
			buf.append(",name:'" + rg.getName() + "'},");
		}		
		if (buf.length() > 1) {
			resultStr = buf.toString().substring(0, buf.length() - 1);
		} else {
			resultStr += "[";
		}
		resultStr = resultStr + "]";
		String str=JsonUtil.pageToJosnData(page, resultStr);
		try {
			JsonUtil.printMsgToClient(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;*/
	}

	/**
	 * 列出所有的待选资源
	 * @return
	 * @throws Exception
	 */
	public String listAllResource() throws Exception {
		String id = this.getRequest().getParameter("resourceGroupId");
		resource=rpopedomDAO.getNoSsignResources(id);
		return SUCCESS;
	}
	
	
	/**
	 * 由用户组列出所有的资源
	 * @return
	 * @throws Exception
	 */
	public String listResourceById() throws Exception {
		String id = this.getRequest().getParameter("resourceGroupId");
		resource = rpopedomDAO.getResourceById(id);
		return SUCCESS;
	}
	/**
	 * 根据id获取资源组信息
	 * @return
	 * @throws Exception
	 */

	public String getResourceGroupInfo() throws Exception {
		String resourceGroupId = this.getRequest().getParameter("resourceGroupId");
		if (StringUtils.isNotEmpty(resourceGroupId)) {
			resourceGroup = resourceGroupDAO.getResourceGroupById(Long.parseLong(resourceGroupId));
		}
		success = true;
		return SUCCESS;
	}
	
	/**
	 * 列出某一资源组所拥有的资源
	 * @return
	 * @throws Exception
	 */
	public String listResourceByResourceGroup() throws Exception {
		String resourceGroupId = this.getRequest().getParameter("groupId");
		//列出该资源组所拥有的资源
		resource = rpopedomDAO.getResourceById(resourceGroupId);
		//列出系统中的所有资源
		List<Resource> resources = new ResourceDAO().getAllResources();
		
		return SUCCESS;
	}
	

	
	public String checkResourceGroupNameIsHave() throws Exception {
		isHave = resourceGroupDAO.checkResourceGroupNameIsHave(resourceGroupName);
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


	public String getResourceGroupName() {
		return resourceGroupName;
	}

	public void setResourceGroupName(String resourceGroupName) {
		this.resourceGroupName = resourceGroupName;
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

	public List<Resource> getResource() {
		return resource;
	}

	public void setResource(List<Resource> resource) {
		this.resource = resource;
	}
	
	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}
}
