<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML class="IE IE_Win IE_M6 IE_D0" xmlns="http://www.w3.org/1999/xhtml" 
xmlns:web xmlns:sp>
<HEAD>
	<TITLE><s:text name="common.bottom.title"/></TITLE>
	<META http-equiv=Content-Type content="text/html; charset=utf-8">
	<LINK href="css/hig+start.css" type=text/css rel=StyleSheet>
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
</HEAD>
<BODY >
<form name="myform"  target="main">
 <TABLE id=uxp_ftr_control cellSpacing=0 cellPadding=0>
  <TBODY>
	<TR>
		<TD id=uxp_ftr_left>
		  <UL>
			<LI>
				<A id=uxp_ftr_link_trademark href="#" target=_top><s:text name="common.bottom.organization"/></A> </LI>
			<LI>
				<A id=uxp_ftr_link_privacy href="#" target=_top><s:text name="common.bottom.privacy"/></A> 
			</LI>
			<LI class=uxp_ftr_item_last>
				<A id=uxp_ftr_link_legal href="#" target=_top><s:text name="common.bottom.terms"/></A> 
			</LI>
		   </UL>
	    </TD>
		
		
		<TD id=uxp_ftr_right>
		  <TABLE id=uxp_ftr_right_nest cellSpacing=0 cellPadding=0>
			<TBODY>
				<TR>
				  <TD>
					<UL>
					  <LI>
					  	<A id=uxp_ftr_link_helpcentral  href="#"></A> 
					  </LI>
					  <LI>
					  	<A id=uxp_ftr_link_account  href="showUserInfo.do" target="_blank" ><s:text name="common.bottom.user"/>：${username}</A> 
					  </LI>
					</UL>
				   </TD>
				 </TR>
			 </TBODY>
		  </TABLE>
		</TD>
	  </TR>
	</TBODY>
 </TABLE>
 </form>
</BODY>

<script type="text/javascript" >
	/** 时间的变化 和 用户名的变化 */
	function timeInit(){
		try {
			setInterval("addTime()", 1000);
		} catch (err) {
			window.location.reload();	
		}
	}
	
	function addTime(){
		/* 设置 top frame 下 的mainframe 的大小 */
		//setBodySize();
		var date = new Date();
	    document.getElementById("uxp_ftr_link_helpcentral").innerText ="<s:text name='common.bottom.time'/>：" +  date.getYear() + "-" + eval(date.getMonth() + 1 )+ "-" + date.getDate() + "  " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
	}
	
	timeInit();
	
	function setBodySize(){
	
		/* 在这里捕获异常是在top.jsp 刷新的时候 这里不出现错误  */
		try {
			var mainFrame = null;
			var menuDiv = null;
			try {
				/* ie 和 ff 下能运行 */
				mainFrame = window.top.document.getElementById('topFrame').contentDocument.getElementById("main");
				menuDiv = window.top.document.getElementById('topFrame').contentDocument.getElementById("menuDiv");
			} catch (e) {
				/* 在搜狗浏览器下运行  */
				mainFrame = parent.frames['topFrame'].document.getElementById("main");
				menuDiv = parent.frames['topFrame'].document.getElementById("menuDiv");
			} 
			var bodyHeight = window.parent.document.body.offsetHeight;
			var frameHeight = bodyHeight - menuDiv.offsetHeight - 44;
			mainFrame.height = frameHeight;
		} catch (err){
			window.location.reload();
		}
	}
	
</script>
</HTML>
