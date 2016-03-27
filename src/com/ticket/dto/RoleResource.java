package com.ticket.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.base.util.Role;
import com.base.util.TicketState;

public class RoleResource {
	private static String operation="";
	/**
	 * 当前用户所对应的角色
	 */
	private long roleId;
	/**
	 * 每一个角色所对应的ticket操作状态集合
	 */
	private static Map<Long,Map<String,Vector<String>>> roleStatusMap;

	public static Map<Long, Map<String,Vector<String>>> getRoleStatusMap(long roleId,int status) {
		roleStatusMap = new HashMap<Long,Map<String,Vector<String>>>();
		Map<String,Vector<String>> statusButtonMap = new HashMap<String,Vector<String>>();
		Vector<String> vec = new Vector<String>();
		if(roleId==Role.UNDERTAKER){
			if(status==TicketState.PUBLISH){
				vec.add("accept");//接受
				vec.add("noAccept");//不接受
				operation="accept@noAccept";
				statusButtonMap.put(String.valueOf(status), vec);
			}else if(status==TicketState.ACCEPT_PUB_AND_WAIT_DEALING){
				vec.add("delegatorDeal");//处理事件 
				operation="submit@usingDelegate";
				statusButtonMap.put(String.valueOf(status), vec);
			}else if(status==TicketState.DEAL_DONE_WAIT_AUDITING){
				vec.add("view");
				operation="";
				statusButtonMap.put(String.valueOf(status), vec);
			}else if(status==TicketState.BEING_DELEGATE){
				vec.add("acceptDelegate");//接受委托
				vec.add("noAcceptDelegate");//不接受委托
				operation="acceptDelegate@noAcceptDelegate";
				statusButtonMap.put(String.valueOf(status), vec);
			}else if(status==TicketState.DELEGATING_ACCEPT){
				vec.add("view");
				operation="delegatorDeal";
				statusButtonMap.put(String.valueOf(status), vec);
			}else if(status==TicketState.TICKET_DONE){
				vec.add("reset");//重置为新的ticket
				operation="";
				statusButtonMap.put(String.valueOf(status), vec);
			}else if(status==TicketState.PUB_DISACCEPT){
				vec.add("view");
				operation="";
				statusButtonMap.put(String.valueOf(status), vec);
			}else if(status==TicketState.DELEGATING_DISACCEPT){
				vec.add("reDelegate");//重新委托
				vec.add("unDelegate");//撤消委托
				operation="reDelegate";
				statusButtonMap.put(String.valueOf(status), vec);
			}else if(status==TicketState.TICKET_UNPASS){
				vec.add("reDoing");//重新处理
				operation="reDoing";
				statusButtonMap.put(String.valueOf(status), vec);
			}
			else{
				operation="";
			}
			roleStatusMap.put(Long.valueOf(roleId),statusButtonMap);
		}else if(roleId==Role.ADMIN){
			if(status==TicketState.UNPUBLISH){
				vec.add("editor");
				vec.add("delete");
				vec.add("submit");
				operation="editor@delete@admin_submit";
				statusButtonMap.put(String.valueOf(status), vec);
			}else if(status==TicketState.PUBLISH || status==TicketState.ACCEPT_PUB_AND_WAIT_DEALING || status==TicketState.DEAL_DONE_WAIT_AUDITING
					|| status==TicketState.BEING_DELEGATE || status==TicketState.DELEGATING_ACCEPT  
					|| status==TicketState.DELEGATING_DISACCEPT || status==TicketState.TICKET_UNPASS){
				vec.add("view");
				operation="";
				statusButtonMap.put(String.valueOf(status), vec);
			}
			else if(status==TicketState.PUB_DISACCEPT){
				operation="editor";
			}
			
			else if(status==TicketState.TICKET_DONE){
				vec.add("reset");//重置为新的ticket
				statusButtonMap.put(String.valueOf(status), vec);
				operation="reset@delete";
				
			}
			else{
				operation="";
			}
			roleStatusMap.put(Long.valueOf(roleId),statusButtonMap);
		}else if(roleId==Role.APPROVER){
			if(status==TicketState.DEAL_DONE_WAIT_AUDITING){
				vec.add("pass");//审核通过
				vec.add("noPass");//审核不通过
				operation="pass@noPass";
				statusButtonMap.put(String.valueOf(status), vec);
			}else if(status==TicketState.TICKET_DONE){
				vec.add("view");
				operation="";
				statusButtonMap.put(String.valueOf(status), vec);
			}
			else{
				operation="";
			}
			roleStatusMap.put(Long.valueOf(roleId),statusButtonMap);
		}
		return roleStatusMap;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	public static String getOperation(long roleId,int status){
		getRoleStatusMap(roleId,status);
		return operation;
	}
}
