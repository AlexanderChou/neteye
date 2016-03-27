package com.ticket.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.base.util.Role;
import com.base.util.TicketState;
import com.opensymphony.xwork2.ActionSupport;
import com.ticket.dto.RoleResource;
import com.ticket.dto.TicketInfo;
import com.ticket.jbpmUtil.JbpmUtil;
/**
 * 用户查看与自己相关的ticket
 * 分配给自己的ticket
 * 
 * @author sunlujing
 *
 */
public class UnderTakerViewMyEventAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
    private TaskInstance taskin=null;
    private List taskInstanceList = new ArrayList();   
    private List underTakenTask_undone =new ArrayList();
    private List underTakenTask_accept =new ArrayList();
    private List underTakenTask_dealDone =new ArrayList();
    private List taskBeDelegated = new ArrayList();
    private List taskNeedAudit = new ArrayList();
    private List myTaskAuditSuccess = new ArrayList();
    private List myTaskAuditFailure = new ArrayList();
    private List myDelegationDisAccept = new ArrayList();
    private List myDelegationAccept = new ArrayList();
    private List myPubDisAccept = new ArrayList();
    private List myTicketPub =new ArrayList();
    private List<TicketInfo> myTicketList =new ArrayList();
    private TicketService ticketService=new TicketService();
    private List ticketFromEvent =new ArrayList();
    private List idSet = new ArrayList();
    long role[];
	public  String execute() throws Exception{
		String underTakerId =ServletActionContext.getRequest().getSession().getAttribute("userId").toString();
		
		TicketService ticketService =new TicketService();
        role=ticketService.getGroupIdByUserId(Long.parseLong(underTakerId));
        
        

    	//根据userId获得分配给自己的用户列表
    	//获得的分配给自己的任务列表放在List:underTakenTask中
       
    	underTakenTask_undone=JbpmUtil.getTask_Undo(underTakerId,"decideAcception");
    
		//获得别人委托给我的任务
		//保存在taskBeDelegated
    	taskBeDelegated=JbpmUtil.getTask_Undo(underTakerId,"decideDelegationAcception");
    	
    	//获得委托给别人当不被接受的任务
    	myDelegationDisAccept=JbpmUtil.getTask_Undo(underTakerId,"decideDelegation",TicketState.DELEGATING_DISACCEPT);
		
		//获得要我进行需要审批的任务
		//保存在taskNeedAudit中
    	taskNeedAudit=JbpmUtil.getTask_Undo(underTakerId,"finalCheck");
		
		//获得当前用户已经接受的正在处理的任务
		//已接受的任务列表放在List:underTakenTaskaccept中
    	underTakenTask_accept=JbpmUtil.getTaskHasDone(underTakerId, "decideAcception", TicketState.ACCEPT_PUB_AND_WAIT_DEALING);
    	

		//获得那些已经处理过的任务正等待审核的任务
		//处理的任务放在underTakenTask_dealDone中
    	underTakenTask_dealDone=JbpmUtil.getTaskHasDone(underTakerId, "mywork", TicketState.DEAL_DONE_WAIT_AUDITING);

		//获得我处理的任务中审核通过的tikcet列表
		//myTaskAuditSuccess
    	myTaskAuditSuccess=JbpmUtil.getTaskHasDone(underTakerId, "finalCheck", TicketState.TICKET_DONE);
    	
		
		//获得我处理的任务中审核未通过的ticket列表
		//myTaskAuditFailure
    	myTaskAuditFailure=JbpmUtil.getTaskHasDone(underTakerId, "finalCheck", TicketState.TICKET_UNPASS);
	
		
		//获得我委托的任务中被接受的列表
		//myDelegationAccept
    	myDelegationAccept=JbpmUtil.getTaskHasDone(underTakerId, "decideDelegationAcception", TicketState.DELEGATING_ACCEPT);

		
	    //获得我委托的任务中没被接受的ticket列表
		//myDelegationDisAccept
    	//myDelegationDisAccept=JbpmUtil.getTaskHasDone(underTakerId, "decideDelegationAcception", TicketState.DELEGATING_DISACCEPT);
    	
	
			//从事件平台过来需要我处理的ticket
			ticketFromEvent=JbpmUtil.getTask_Undo(underTakerId,"ticketFromEventTask");
		
		//我提交的任务不被接受的ticket列表
		//myPubDisAccept
			
    	   myPubDisAccept=JbpmUtil.getTask_Undo(underTakerId,"createEventTask");
    	//我提交的ticket还未做任务处理的列表
    	   myTicketPub=JbpmUtil.getTaskHasDone(underTakerId, "createEventTask", TicketState.PUBLISH);
	
		addTask(myTicketList,ticketFromEvent);

    	addTask(myTicketList,underTakenTask_undone);
    	addTask(myTicketList,myTicketPub);
    	addTask(myTicketList,taskBeDelegated);
    	addTask(myTicketList,taskNeedAudit);
    	addTask(myTicketList,underTakenTask_accept);
    	addTask(myTicketList,underTakenTask_dealDone);
    	addTask(myTicketList,myTaskAuditSuccess);
    	addTask(myTicketList,myTaskAuditFailure);
    	addTask(myTicketList,myDelegationAccept);
    	addTask(myTicketList,myDelegationDisAccept);
    	addTask(myTicketList,myPubDisAccept);
    	removerDuplicateTicket(myTicketList);
    	   return SUCCESS;
	}

	public List<TicketInfo> getMyTicketList() {
		return myTicketList;
	}

	public void setMyTicketList(List<TicketInfo> myTicketList) {
		this.myTicketList = myTicketList;
	}
	
	private void addTask(List<TicketInfo> result,List task) throws Exception{
		for(int i=0;i<task.size();i++){
			Ticket ticket= (Ticket)task.get(i);
			TicketInfo ticketInfo =new TicketInfo(ticket);
			for(int j = 0; j<role.length; j++){
			
			ticketInfo.setOperation(RoleResource.getOperation(role[j], ticket.getStatus()));
			if(!ticketInfo.getOperation().equals("")){
				break;
			}
			}

			
			result.add(ticketInfo);
		}
	}
	
	private void removerDuplicateTicket(List<TicketInfo> ticketList){
		int size = ticketList.size();
		for(int i=0;i<size;i++){
			TicketInfo ticketInfo =ticketList.get(i);
			long id = ticketInfo.getId();
			if(!queryId(id,idSet)){
				ticketList.remove(ticketInfo);
			}
			else{
				idSet.add(id);
			}
		}
		
	}
	
	 private static boolean queryId(long id,List pidSet){
	     for(int i=0;i<pidSet.size();i++){
	    	 long temp=(Long)pidSet.get(i);
	    	 if(id==temp)
	    		 return false;
	     }
    	 return true;
  }
	
}
