package com.ticket.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.base.model.Attachment;
import com.base.model.Category;
import com.base.model.Priority;
import com.base.model.Project;
import com.base.model.UserGroup;
import com.base.model.UserPojo;
import com.base.model.UserPopedom;
import com.base.service.TicketService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.ticket.dto.GroupUser;

public class TicketShowAction extends ActionSupport{
     public List<Project> projectList;
     public List<Priority> priorityList;
     public List<Category> categoryList;
     public List<UserPojo> auditorList=new ArrayList();
     public List<Attachment> attachList=new ArrayList();
     public List<UserPojo> underTakerList=new ArrayList();
     public List<UserPojo> copyUserList=new ArrayList();
     public List<GroupUser> groupUserList =new ArrayList();
     public TicketService ticketService =new TicketService();
     public String ticketId="";
     @JSON(serialize=false)
     public String getProjectListAction() throws Exception{
    	
    	 projectList=ticketService.QueryByHql("from Project");
    	 return Action.SUCCESS;
     }
     @JSON(serialize=false)
     public String getPriorityListAction() throws Exception{
     	
    	 priorityList=ticketService.QueryByHql("from Priority");
    	 return Action.SUCCESS;
     }
     @JSON(serialize=false)
     public String getCategoryListAction() throws Exception{
     	 
    	 categoryList=ticketService.QueryByHql("from Category");
    	 return Action.SUCCESS;
     }
     
     @JSON(serialize=false)
     public String getAttachListAction()throws Exception{
    	 attachList=ticketService.QueryByHql("from Attachment where ticket="+ticketId);
    	 return Action.SUCCESS; 
     }
     @JSON(serialize=false)
     public String getUnderTakerListAction()throws Exception{
    	 String underTakerId =ServletActionContext.getRequest().getSession().getAttribute("userId").toString();
    	 List temp=ticketService.QueryByHql("from UserPopedom ud where ud.groupId=2");
    	 for(int i=0;i<temp.size();i++){
    		 long id=((UserPopedom)temp.get(i)).getUserId();
             UserPojo user=(UserPojo) ticketService.read(UserPojo.class, id);
             if(user.getId() != Long.parseLong(underTakerId)){
                underTakerList.add(user);
             }
    	 }
    	 
    	 return Action.SUCCESS;
     }
     @JSON(serialize=false)
     public String getCopyUserListAction()throws Exception{
    	 List temp=ticketService.QueryByHql("from UserPopedom");
    	 List set = new ArrayList();
    	 for(int i=0;i<temp.size();i++){
    		 long id=((UserPopedom)temp.get(i)).getUserId();
    		 if(queryId(id,set)){
    		 UserPojo user=(UserPojo) ticketService.read(UserPojo.class, id);
    		 copyUserList.add(user);
    		 set.add(id);
    		 }
    		 
    	 }
    	 
    	 return Action.SUCCESS;
     }
     private static boolean queryId(long id,List pidSet){
	     for(int i=0;i<pidSet.size();i++){
	    	 long temp=(Long)pidSet.get(i);
	    	 if(id==temp)
	    		 return false;
	     }
    	 return true;
  }
     @JSON(serialize=false)
     public String getAuditorListAction()throws Exception{
    	 List temp=ticketService.QueryByHql("from UserPopedom ud where ud.groupId=3");
    	 for(int i=0;i<temp.size();i++){
    		 long id=((UserPopedom)temp.get(i)).getUserId();
             UserPojo user=(UserPojo) ticketService.read(UserPojo.class, id);
             auditorList.add(user);
    	 }
    	 
    	 return Action.SUCCESS;
     }
     /**
      * 获得组列表和每个组的用户 
      * @return
      * @throws Exception
      */
     @JSON(serialize=false)
     public String getGroupListAction()throws Exception{
    
    	 List<UserGroup> userList =ticketService.QueryByHql("from UserPojo");
    	 Iterator it =userList.iterator();
    	 while(it.hasNext()){
    		 UserPojo user=(UserPojo)it.next();
    		 List temp =ticketService.QueryByHql("from UserPopedom up,UserGroup group where up.userId="+user.getId()+" and group.id=up.groupId");
    		
    		 for(int i=0;i<temp.size();i++){
    			 GroupUser dto=new GroupUser();
        		 dto.setTotalgroup(userList.size());
        		 dto.setUserName(user.getName());
    			 Object[]obj=(Object[])temp.get(i);

    			 for(int j=0;j<obj.length;j++){
    		
    				if(obj[j]instanceof UserGroup){
    				    dto.setGroupName(((UserGroup)obj[j]).getName());
    				    dto.setCurrentGroupId(((UserGroup)obj[j]).getId());
    				    groupUserList.add(dto);
    				}
    			}
    			
    		 }
    		
    		 
    	 }

    	 return Action.SUCCESS;
     } 
	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}
	public List<Project> getProjectList() {
		return projectList;
	}

	public List<Priority> getPriorityList() {
		return priorityList;
	}

	public void setPriorityList(List<Priority> priorityList) {
		this.priorityList = priorityList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<UserPojo> getUnderTakerList() {
		return underTakerList;
	}

	public void setUnderTakerList(List<UserPojo> underTakerList) {
		this.underTakerList = underTakerList;
	}

	public List<GroupUser> getGroupUserList() {
		return groupUserList;
	}

	public void setGroupUserList(List<GroupUser> groupUserList) {
		this.groupUserList = groupUserList;
	}
	public List<UserPojo> getAuditorList() {
		return auditorList;
	}
	public void setAuditorList(List<UserPojo> auditorList) {
		this.auditorList = auditorList;
	}
	public List<UserPojo> getCopyUserList() {
		return copyUserList;
	}
	public void setCopyUserList(List<UserPojo> copyUserList) {
		this.copyUserList = copyUserList;
	}
	public List<Attachment> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<Attachment> attachList) {
		this.attachList = attachList;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
     
}
