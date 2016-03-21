<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean class="com.base.util.tree.JSONTree" id="JSONTree"></jsp:useBean> 
<% 
	String groupId = ""; 
	if (request.getParameter("groupId")!=null){ 
		groupId = request.getParameter("groupId").toString(); 
	} 
	JSONTree.setGroupId(groupId); 
%> 
<%=JSONTree.getChoseResource(true)%> 