<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML class="IE IE_Win IE_M6 IE_D0" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:web xmlns:sp>
<HEAD>
<TITLE><s:text name="common.top.title"/></TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<LINK href="css/hig+start.css" type=text/css rel=StyleSheet>

<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<SCRIPT type="text/javascript" src="js/jquery.min.js"></SCRIPT>
<script type="text/javascript" src="js/ext-base.js"></script>
<script type="text/javascript" src="js/ext-all.js"></script>
<script type="text/javascript">
	
	Ext.onReady( function() {
		//获取部署方式
		Ext.Ajax.request({
			url : 'show/getDeployMode.do',
			disableCaching : true,
			method : 'GET',
			success : function(result, request) {
				var deployMode=Ext.decode(result.responseText).deployMode;
				var userMenu=document.getElementById("jiangning");
				var logoutMenu=document.getElementById("uxp_hdr_meParent");
				if(deployMode==1){
					userMenu.style.visibility ="visible";
					logoutMenu.style.visibility ="visible";
				}else{
					userMenu.style.visibility ="hidden";
					logoutMenu.style.visibility ="hidden";
				}
			}
		});
	});
	
	$(function(){
		$(document).keydown(function(e) {
			e = window.event || e;
			var keyCode = e.keyCode || e.which || e.charCode;
						
   			if (keyCode == 117) {
   				parent.mainFrame.location.reload();
			}
		}); 
	});
	
	function doSearch(){
		document.uxp_hdr_search.submit(); 
		return false;
	}
</script>
<style type="text/css">
	
	#sub_manu {
		position:absolute;margin-left:5px;width:120px;BORDER-RIGHT: #a5a8a8 1px solid;height:430px;
		BORDER-RIGHT: #42555c 2px solid;  BORDER-TOP: #a5a8a8 1px solid; BORDER-LEFT: #a5a8a8 1px solid;BORDER-BOTTOM: #42555c 2px solid; 
	}
	

	#sub_manu li {
		width:120px;
	}
	
	.myclass {
		BORDER-RIGHT: #b9eaff 1px solid; PADDING-RIGHT: 8px; BORDER-TOP: #b9eaff 1px solid; PADDING-LEFT: 8px; BACKGROUND: #d6effc; PADDING-BOTTOM: 4px; BORDER-LEFT: #b9eaff 1px solid; PADDING-TOP: 4px; BORDER-BOTTOM: #b9eaff 1px solid
	}
	
	#uxp_hdr_jewelMenu {
		height:100%; 
	}
	
	.parent_manu {
		padding-left:20px;
	}
	
	* html #arrowPosition {
		color:blue;DISPLAY: inline-block; VERTICAL-ALIGN: middle; OVERFLOW: hidden; WIDTH: 7px; POSITION: relative; TOP: -4px; HEIGHT: 7px
	}
	
	#arrowPosition {
		color:red;DISPLAY: inline-block; VERTICAL-ALIGN: middle; OVERFLOW: hidden; WIDTH: 7px; POSITION: relative; TOP: 13px; HEIGHT: 7px
	}
	*+html #arrowPosition {
		color:red;DISPLAY: inline-block; VERTICAL-ALIGN: middle; OVERFLOW: hidden; WIDTH: 7px; POSITION: relative; TOP: -4px; HEIGHT: 7px
	}
	
</style>

</HEAD>
<BODY>
<div id="menuDiv">
	<DIV class=datetext></DIV>
	<DIV id=start_top_minwidth></DIV>
	<DIV id=hdr_wrapper> <SCRIPT type=text/javascript>
				function uxp_p(t){t.parentNode.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+t.src+"')";}
			</SCRIPT>
	</DIV>
	<DIV id=uxp_hdr_parent_ie6>
	<TABLE id=uxp_hdr cellSpacing=0>
	
		<TR>
			<TD class=uxp_hdrInner>
			<TABLE width="1154" cellSpacing=0 class=uxp_hdrInnerTable>
	
				<TR>
					<TD width="224" class=uxp_hdr_pointer id=uxp_hdr_jewelParent>
					<A id=uxp_hdr_jewelLink href="showSubnets.do" target="mainFrame"> <SPAN
						style="DISPLAY: inline-block; VERTICAL-ALIGN: middle; OVERFLOW: hidden; WIDTH: 39px; POSITION: relative; HEIGHT: 37px">
					<SPAN class=uxp_hdr_jewel_ie6
						style="WIDTH: 39px; POSITION: absolute; HEIGHT: 74px">
				    <IMG
						style="VISIBILITY: hidden" alt="More services"
						src="images/jewel_24_hover.png" onload=uxp_p(this)> </SPAN> </SPAN> <SPAN id="uxp_hdr_windowsLiveText"
						class=uxp_hdr_windowsLiveText><s:text name="common.top.windowsLiveText"/></SPAN> </A> <BR>
					<DIV class=uxp_hdr_menuParent id=uxp_hdr_menuParent>
					<UL class=uxp_hdr_menu id=uxp_hdr_jewelMenu style="DISPLAY: none">
					</UL>
					</DIV>
				  </TD>
				  <TD width="441"></TD>
				  <TD width="481" >
					  <TABLE id=uxp_hdr_middleArea>
						<TR>
							<TD width="515" id=uxp_hdr_tabsParent>
							<TABLE class=uxp_hdr_pointer id=uxp_hdr_tabs cellSpacing=0>
								<TR>
									<TD id="jiangning"><A href="showUserList.do"  target="mainFrame"><SPAN><s:text name="common.top.User"/></SPAN></A></TD>
									<TD><A href="configSubent.do" target="mainFrame"><SPAN><s:text name="common.top.Management"/></SPAN></A></TD>
									<TD><A href="showSubnets.do" target="mainFrame"><SPAN><s:text name="common.top.Showing"/></SPAN></A></TD>
									<TD><A href="showStatistic.do" target="mainFrame"><SPAN><s:text name="common.top.Statistic"/></SPAN></A></TD>
									<TD style= "visibility:hidden"><A href="#" target="mainFrame"><SPAN><s:text name="common.top.Verification"/></SPAN></A></TD>
								</TR>
						  </TABLE>	
						  </TD>
						  	<TD id=uxp_hdr_searchParent>
							<FORM id=uxp_hdr_search name=uxp_hdr_search action="showSearchResults.do" target="mainFrame" method=get>
							<LABEL style="DISPLAY: none" for=uxp_hdr_searchInput>Search</LABEL> 
								<INPUT id=uxp_hdr_searchInput name=keywords> 
								<a href="javascript:void(0)" onclick="doSearch();">
								<img src="images/search.gif">
								<img src="images/search_big.gif">
								</a>
							</FORM>
							</TD>
						</TR>
					</TABLE>
				  </TD>
				  <TD class=uxp_hdr_pointer id=uxp_hdr_meParent>
						<A href="logout.do" target="_parent"><SPAN><s:text name="common.top.Logout"/></SPAN></A>
				  </TD>
				</TR>
	
			</TABLE>
			</TD>
		</TR>
	
	</TABLE>
	</DIV>
</div>
</BODY>
</HTML>


