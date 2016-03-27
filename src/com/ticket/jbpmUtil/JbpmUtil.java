package com.ticket.jbpmUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.base.model.Ticket;
import com.base.service.TicketService;
import com.base.util.TicketState;

public class JbpmUtil {
	static JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
   
	
	/**
	 * 根据actorId和任务名查看待处理的任务
	 * @param actorId
	 * @param taskName
	 * @return
	 * @throws Exception 
	 */
     public static List getTask_Undo(String actorId,String taskName) throws Exception{
    	 List <Ticket>result=new ArrayList();
    	 TicketService ticketService = new TicketService();
    	 JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    	 TaskInstance taskin=null;
    	 List task=jbpmContext.getTaskMgmtSession().findTaskInstances(actorId);
    		for(int i=0;i<task.size();i++){
    			taskin=(TaskInstance)task.get(i);
    			if((taskName).equals(taskin.getName())){
    				long pid = taskin.getToken().getId();
    				Ticket ticket=ticketService.getTicketByPid(pid);
    				if(ticket!=null)
    				  result.add(ticket);
    			}
    		}
    		jbpmContext.close();
    	 return result;
     }
     
     public static List getTask_Undo(String actorId) throws Exception{
    	 List <Ticket>result=new ArrayList();
    	 TicketService ticketService = new TicketService();
    	 JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    	 TaskInstance taskin=null;
    	 List task=jbpmContext.getTaskMgmtSession().findTaskInstances(actorId);
    	 for(int i=0;i<task.size();i++){
 			    taskin=(TaskInstance)task.get(i);
 				long pid = taskin.getToken().getId();
 				Ticket ticket=ticketService.getTicketByPid(pid);
 				if(ticket!=null)
 				  result.add(ticket);
 		}
         jbpmContext.close();
    	 return result;
     }
     
     public static List getTask_Undo(String actorId,String taskName,int taskState) throws Exception{
    	 List <Ticket>result=new ArrayList();
    	 TicketService ticketService = new TicketService();
    	 JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    	 TaskInstance taskin=null;
    	 List task=jbpmContext.getTaskMgmtSession().findTaskInstances(actorId);
    		for(int i=0;i<task.size();i++){
    			taskin=(TaskInstance)task.get(i);
    			if( (taskName).equals(taskin.getName())){
    				long pid = taskin.getToken().getId();
    				Ticket ticket=ticketService.getTicketByPid(pid);
    				if(ticket!=null && ticket.getStatus()==taskState)
    				  result.add(ticket);
    			}
    		}
    		jbpmContext.close();
    	 return result;
     }
     
     /**
      * 获得关于actor在特定任务和任务状态下的任务列表
      * @param actorId
      * @param taskName
      * @param taskState
      * @return
      * @throws Exception
      */
     public static List getTaskHasDone(String actorId,String taskName,int taskState) throws Exception{
    	 List result=new ArrayList();
    	 TaskInstance taskin=null;
    	 TicketService ticketService = new TicketService();
    	 List taskInstanceList = new ArrayList();
    	 List pidSet=new ArrayList();
    	 JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
    	 Session session = jbpmContext.getSession();   
		 Query query = session.createQuery("select ti from "+   
		 "org.jbpm.taskmgmt.exe.TaskInstance as ti "+   
		      "where ti.actorId = "+actorId+   
		        "and ti.end is not null ");   
		 taskInstanceList = query.list();    
		 
		 for(int i=0;i<taskInstanceList.size();i++){
			taskin=(TaskInstance)taskInstanceList.get(i);
			long pid = taskin.getToken().getId();
		    
			if(queryPid(pid,pidSet)){
				Ticket t=ticketService.getTicketByPid(pid);
			    
				if((t!=null) && (t.getStatus()==taskState)){
				    result.add(t);
				}
				pidSet.add(pid);
			}
		}
		 jbpmContext.close();
    	return result; 
     }
   /**
    * 结束当前实例的某一个任务  
    * @param processInstance
    * @param taskName
    * @param transitionName
    */
  public static void endCurrentTaskInstance( ProcessInstance processInstance,String taskName,String transitionName){
	     Collection ls=processInstance.getTaskMgmtInstance().getTaskInstances();
	     Iterator it =ls.iterator();
	        while(it.hasNext()){
	   	        TaskInstance wr2 = (TaskInstance)it.next() ;
	   	        if(wr2.getName().equals(taskName)){
	   	           if(wr2.isOpen() && wr2.isSignalling()){
	   	                 
	   	        	    wr2.end(transitionName);
	   	               return;
	   	           }
	   	              
	   	       }
	        }
  }   
     
  /**
   * 为了避免重复查询
   * 每次都查询pid是否重复
   * @param pid
   * @return
   */
 private static boolean queryPid(long pid,List pidSet){
	     for(int i=0;i<pidSet.size();i++){
	    	 long temp=(Long)pidSet.get(i);
	    	 if(pid==temp)
	    		 return false;
	     }
    	 return true;
  }
 
}
