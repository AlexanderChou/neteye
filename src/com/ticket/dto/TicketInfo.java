package com.ticket.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.base.model.Category;
import com.base.model.Priority;
import com.base.model.Project;
import com.base.model.Ticket;
import com.base.model.UserPojo;
import com.base.service.TicketService;

public class TicketInfo {
	private Long id;
	private Long userByApproverId;
	private String  approverName="";
	private Long project;
	private String projectName="";
	private Long userByUndertakerId;
	private String underTakerName="";
	private Long category;
	private String categoryName="";
	private Long priority;
	private String priorityName="";
	private String title="";
	private String content="";
	private String desc="";
	private int status=-1;
	private String createTime="";
	private String commitTime="";
	private String commitApproverTime="";
	private String receiveApproverTime="";
	private String approverPassTime="";
	private String closeTime="";
	private String delegateTime="";
	private String receiveDelegateTime="";
	private Byte isdigest;
	private String ccIds="";
	private String ccGroupIds="";
	private Long pid;
	private String statusInfo="";
	private String operation="";
	private Long role;
	private int attachFlag=0;//是否有附件的标识
	private TicketService ticketService =new TicketService();
	public TicketInfo(Ticket t) throws Exception{
		this.id =t.getId();
		this.userByApproverId = t.getUserByApproverId();
		this.project = t.getProject();
		this.userByUndertakerId = t.getUserByUndertakerId();
		this.category = t.getCategory();
		this.priority = t.getPriority();
		this.title = t.getTitle();
		this.content = t.getContent();
		this.desc = t.getDescription();
		this.status = t.getStatus();
		

		this.createTime = formatTime(t.getCreateTime());
		this.commitTime = formatTime(t.getCommitTime());
		this.commitApproverTime = formatTime(t.getCommitApproverTime());
		this.receiveApproverTime = formatTime(t.getReceiveApproverTime());
		
		this.approverPassTime = formatTime(t.getApproverPassTime());
		this.closeTime = formatTime(t.getCloseTime());
		this.delegateTime = formatTime(t.getDelegateTime());
		this.receiveDelegateTime= formatTime(t.getReceiveDelegateTime());
		
		
		this.isdigest = t.getIsdigest();
		this.ccIds = t.getCcIds();
		this.ccGroupIds = t.getCcGroupIds();
		this.pid = t.getPid();
		setInfo();
	}
	
	public void setInfo() throws Exception{
		List temp=ticketService.QueryByHql("from Project p where p.id="+project);
        if(temp.size()!=0){
        	setProjectName(((Project)temp.get(0)).getName());
        }
        List cate=ticketService.QueryByHql("from Category p where p.id="+category);
        if(cate.size()!=0){
        	setCategoryName(((Category)cate.get(0)).getName());
        }
        
        List pri=ticketService.QueryByHql("from Priority p where p.id="+priority);
        if(pri.size()!=0){
        	setPriorityName(((Priority)pri.get(0)).getName());
        }
        
        List appr=ticketService.QueryByHql("from UserPojo p where p.id="+userByApproverId);
        if(appr.size()!=0){
        	setApproverName(((UserPojo)appr.get(0)).getName());
        }
        List under=ticketService.QueryByHql("from UserPojo p where p.id="+userByUndertakerId);
        if(under.size()!=0){
        	setUnderTakerName(((UserPojo)under.get(0)).getName());
        }
        switch(status){
        case -1:
        	setStatusInfo("来自事件平台的ticket");
        	break;
        case 0:
           setStatusInfo("事件已经提交正在等待负责人接受");
           break; 
        case 1:
        	 setStatusInfo("事件已经被负责人接受");
             break; 
        case 2:
       	 setStatusInfo("事件处理完毕等待审核");
            break;     

        case 3:
          	 setStatusInfo("事件已被委托正在等待委托人接受");
               break;    
        case 4:
         	 setStatusInfo("事件已被委托人接受");
              break;   
        case 5:
         	 setStatusInfo("事件已通过审核");
              break;      
        case 6:
        	 setStatusInfo("事件没有被负责人接受");
             break;          
        case 7:
       	 setStatusInfo("事件没有被委托人接受");
            break;  
        case 8:
       	 setStatusInfo("事件没有通过终审");
            break;    
        default:
        	 setStatusInfo("事件处于异常状态");
        	break;
        }
        
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserByApproverId() {
		return userByApproverId;
	}
	public void setUserByApproverId(Long userByApproverId) {
		this.userByApproverId = userByApproverId;
	}
	public Long getProject() {
		return project;
	}
	public void setProject(Long project) {
		this.project = project;
	}
	public Long getUserByUndertakerId() {
		return userByUndertakerId;
	}
	public void setUserByUndertakerId(Long userByUndertakerId) {
		this.userByUndertakerId = userByUndertakerId;
	}
	public Long getCategory() {
		return category;
	}
	public void setCategory(Long category) {
		this.category = category;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
    
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}

	public String getCommitApproverTime() {
		return commitApproverTime;
	}

	public void setCommitApproverTime(String commitApproverTime) {
		this.commitApproverTime = commitApproverTime;
	}

	public String getReceiveApproverTime() {
		return receiveApproverTime;
	}

	public void setReceiveApproverTime(String receiveApproverTime) {
		this.receiveApproverTime = receiveApproverTime;
	}

	public String getApproverPassTime() {
		return approverPassTime;
	}

	public void setApproverPassTime(String approverPassTime) {
		this.approverPassTime = approverPassTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getDelegateTime() {
		return delegateTime;
	}

	public void setDelegateTime(String delegateTime) {
		this.delegateTime = delegateTime;
	}

	public String getReceiveDelegateTime() {
		return receiveDelegateTime;
	}

	public void setReceiveDelegateTime(String receiveDelegateTime) {
		this.receiveDelegateTime = receiveDelegateTime;
	}

	public Byte getIsdigest() {
		return isdigest;
	}
	public void setIsdigest(Byte isdigest) {
		this.isdigest = isdigest;
	}
	public String getCcIds() {
		return ccIds;
	}
	public void setCcIds(String ccIds) {
		this.ccIds = ccIds;
	}
	public String getCcGroupIds() {
		return ccGroupIds;
	}
	public void setCcGroupIds(String ccGroupIds) {
		this.ccGroupIds = ccGroupIds;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public Long getRole() {
		return role;
	}
	public void setRole(Long role) {
		this.role = role;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getUnderTakerName() {
		return underTakerName;
	}
	public void setUnderTakerName(String underTakerName) {
		this.underTakerName = underTakerName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getPriorityName() {
		return priorityName;
	}
	public void setPriorityName(String priorityName) {
		this.priorityName = priorityName;
	}

	public String getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}

	public int getAttachFlag() {
		return attachFlag;
	}

	public void setAttachFlag(int attachFlag) {
		this.attachFlag = attachFlag;
	}
	
	public String formatTime(Date date) {
		if(date!=null){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return format.format(date);
		}
		else{
			return "";
		}
	}
}
