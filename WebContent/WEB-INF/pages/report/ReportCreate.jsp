<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*,com.report.dto.*,com.opensymphony.xwork2.ActionContext" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML class="IE IE_Win IE_M6 IE_D0" xmlns="http://www.w3.org/1999/xhtml" 
xmlns:web xmlns:sp>
<HEAD>
	<TITLE>iNetBoss Home</TITLE>
	<META http-equiv=Content-Type content="text/html; charset=utf-8">
	<LINK href="css/hig+start.css" type=text/css rel=StyleSheet>	
</HEAD>
<%
	List<String> allTemplates=(List<String>)session.getValue("allTemplates");
	List<String> templates=(List<String>)session.getValue("templates");
%>
<BODY>
	<DIV class=datetext></DIV>
	<DIV id=start_top_minwidth></DIV>
	<DIV id=hdr_wrapper>
		<SCRIPT type=text/javascript>
			function uxp_p(t){t.parentNode.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+t.src+"')";}
		</SCRIPT>	
		<DIV id=uxp_hdr_parent_ie6>
			<TABLE id=uxp_hdr cellSpacing=0>
				<TBODY>
					<TR>
						<TD class=uxp_hdrInner>
							<TABLE class=uxp_hdrInnerTable cellSpacing=0>
								<TBODY>
									<TR>
										<TD class=uxp_hdr_pointer id=uxp_hdr_jewelParent         onmouseleave="this.className=this.className.replace(' uxp_hdr_jewelSpanHover','')"           onmouseenter="this.className+=' uxp_hdr_jewelSpanHover'">
											<A id=uxp_hdr_jewelLink href="http://home.live.com/#">
												<SPAN style="DISPLAY: inline-block; VERTICAL-ALIGN: middle; OVERFLOW: hidden; WIDTH: 39px; POSITION: relative; HEIGHT: 37px">
													<SPAN class=uxp_hdr_jewel_ie6 style="WIDTH: 39px; POSITION: absolute; HEIGHT: 74px">
														<IMG  style="VISIBILITY: hidden" alt="More services" src="images/jewel_24_hover.png"  onload=uxp_p(this)>
													</SPAN>
												</SPAN>
												<SPAN class=uxp_hdr_windowsLiveText>iNetBoss</SPAN>
											</A>
											<BR>
											<DIV class=uxp_hdr_menuParent>
												<UL class=uxp_hdr_menu id=uxp_hdr_jewelMenu style="DISPLAY: none">
													<LI>
														<A href="#">
															<SPAN class=uxp_hdr_rightArrow>
																<SPAN style="DISPLAY: inline-block; VERTICAL-ALIGN: middle; OVERFLOW: hidden; WIDTH: 7px; POSITION: relative; TOP: -2px; HEIGHT: 7px">
																	<SPAN class=uxp_hdr_rightArrow_ie6 style="WIDTH: 16px; POSITION: absolute; HEIGHT: 900px">
																		<IMG  style="VISIBILITY: hidden" alt="" src="images/headerBG_24_~HeaderGradientImageType~.png" onload=uxp_p(this)>
																	</SPAN>
																</SPAN>
															</SPAN>报表管理
														</A>
														<UL class=uxp_hdr_submenu>
															<LI>
																<A href="configReport.do">报表配置</A>
															<LI>
																<A href="createReport.do">报表生成</A>															
															</LI>
														</UL>
														<IFRAME style="Z-INDEX: -1; FILTER: mask(); WIDTH: 1px; POSITION: absolute; TOP: -1px; HEIGHT: 1px" 
														  tabIndex=-1 src="about:blank">
														</IFRAME>
													<LI>
														  <A href="http://home.live.com/#">
															<SPAN class=uxp_hdr_rightArrow>
																<SPAN style="DISPLAY: inline-block; VERTICAL-ALIGN: middle; OVERFLOW: hidden; WIDTH: 7px; POSITION: relative; TOP: -2px; HEIGHT: 7px">
																	<SPAN class=uxp_hdr_rightArrow_ie6 style="WIDTH: 16px; POSITION: absolute; HEIGHT: 900px">
																	<IMG style="VISIBILITY: hidden" alt="" src="images/headerBG_24_~HeaderGradientImageType~.png"   onload=uxp_p(this)>
																	</SPAN>
																</SPAN>
															</SPAN>监控
														  </A>
														  <UL class=uxp_hdr_submenu>
															<LI>
																<A href="createReport.do">故障监控</A>
															<LI>
																<A href="createReport.do">故障视图</A>	
															<LI class=uxp_hdr_menuSeparator>
															<LI>
																<A href="createReport.do">流量监控</A>
															</LI>
															<LI>
																<A href="createReport.do">流量统计</A>
															</LI>
														 </UL>
														 <IFRAME 
														  style="Z-INDEX: -1; FILTER: mask(); WIDTH: 1px; POSITION: absolute; TOP: -1px; HEIGHT: 1px" 
														  tabIndex=-1 src="about:blank">
														 </IFRAME>
													</LI>
													<LI class=uxp_hdr_menuSeparator>
													<LI>
														<A href="createReport.do">帮助中心</A>
													<LI>
														<A href="createReport.do">联系我们</A>
													</LI>
												</UL>
												<IFRAME style="Z-INDEX: -1; FILTER: mask(); ; LEFT: expression(this.previousSibling.offsetLeft); ; WIDTH: expression(this.previousSibling.offsetWidth); POSITION: absolute; ; TOP: expression(this.previousSibling.offsetTop); ; HEIGHT: expression(this.previousSibling.offsetHeight)" tabIndex=-1 src="about:blank">
												</IFRAME>
											</DIV>
										</TD>
										<TD>
											<TABLE id=uxp_hdr_middleArea>
												<TBODY>
													<TR>
														<TD id=uxp_hdr_tabsParent>
															<TABLE class=uxp_hdr_pointer id=uxp_hdr_tabs cellSpacing=0>
																<TBODY>
																	<TR>
																	  <TD><A id=uxp_hdr_tabs_current href="configReport.do"><SPAN>报表配置</SPAN></A></TD>
																	  <TD><A href="createReport.do"><SPAN>报表生成</SPAN></A></TD>
																	  <TD><DIV class=h_separator></DIV></TD>
																	  <TD><A href="selfConfigReport.do"><SPAN>个性化报表设置</SPAN></A></TD>
																	  <TD><A href="selfCreateReport.do"><SPAN>个性化报表生成</SPAN></A></TD>
																	</TR>
																</TBODY>
															</TABLE>
														</TD>
														<TD id=uxp_hdr_searchParent>
															<FORM id=uxp_hdr_search action="" method=get>
																<LABEL style="DISPLAY: none" for=uxp_hdr_searchInput>Search</LABEL>
																<SPAN class=uxp_hdr_searchBorder>
																	<SPAN class=uxp_hdr_innerSearchBorder>
																		<INPUT id=uxp_hdr_searchInput name=q>
																		<SPAN class=uxp_hdr_buttonBorder>
																			<INPUT class=uxp_hdr_button id=uxp_hdr_webSearchButton onmouseover="this.className += ' uxp_hdr_searchButtonHover_ie6'" onmouseout="this.className = this.className.replace(' uxp_hdr_searchButtonHover_ie6', '')" type=submit value=Search>
																		</SPAN>
																	</SPAN>
																</SPAN>
																<INPUT type=hidden value=en-US name=mkt>
																<INPUT type=hidden value=WLHOME name=form>
															</FORM>
														</TD>
													</TR>
												</TBODY>
											</TABLE>
										</TD>
										<TD class=uxp_hdr_pointer id=uxp_hdr_meParent>
											<A id=uxp_hdr_signIn onclick=event.cancelBubble=true  href="logout.do" target=_top>退出</A>
										</TD>
									</TR>
								</TBODY>
							</TABLE>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
		</DIV>
		<SCRIPT src="js/header.js" type=text/javascript></SCRIPT>
	</DIV>	
	
	<DIV id=startmainParent>
	<DIV class=start_main id=startmain>
	<DIV class=flexipad></DIV>
	<TABLE class=main cellSpacing=0>
		<TBODY>
			 <TR>			
				<TD class=colct>
				  <DIV class=start_ct>
				  <DIV class=start_content>
					  <DIV class=module id=HotmailRow.SignOut>
						<TABLE class=moduleHeader cellSpacing=0>
							<TBODY>
								 <TR>
									 <TD class=moduletitle>
										<H2>报表生成</H2></TD>
									 <TD class=moduleicons>
										<UL></UL>
									 </TD>
								  </TR>
							 </TBODY>
						 </TABLE>
						 <s:form name="myform2" target="_blank">
						 <input type="hidden" name="type" value=""/>
						 <TABLE class=moduleBody cellSpacing=0>
							<TBODY>
								<TR>								
									<TD class=colrt>
										<h4>请选择 
										报表模板:<select name="template">
												<%
												if(allTemplates!=null && allTemplates.size()>0){
												for(int i=0;i<allTemplates.size();i++){%>
													<option name='<%=allTemplates.get(i).toString()%>'><%=allTemplates.get(i).toString()%></option>
												<%}}%>
											   </select>
										年份:<select name="year" width="10">
											<%
											int year = new Date().getYear();
											year += 1900;
											for(int i=0;i<9;i++){
											%>
												<option value=<%=year-i%>><%=year-i%></option>
											<%}%>
										</select>
										报表类型:
										<select name='morw'>
										<option value='1'><i>月</i></option>
										<option value='2'><i>周</i></option>
										<option value='3'><i>年</i></option>
										</select>
										第几:
										<input type='text' size='2' name='number'/>
										<i>月/周/年</i>&nbsp;&nbsp;&nbsp;	&nbsp;&nbsp;&nbsp;										
										<input type="button" value="确定" onclick="download()" 
											   class=btn_mouseout 
					                           onmouseover="this.className='btn_mouseover'"
					                           onmouseout="this.className='btn_mouseout'"
					                           onmousedown="this.className='btn_mousedown'"
					                           onmouseup="this.className='btn_mouseup'" 
											   style="cursor:hand">
										</h4>
									</TD>
								 </TR>
							 </TBODY>
						  </TABLE>
						  </s:form>
						</DIV>								
						<DIV class=module id=SpacesRow.SignOut>
							<TABLE class=moduleHeader cellSpacing=0>
								<TBODY>
									<TR>
										<TD class=moduletitle>
											<H2>已有模板列表</H2>
										</TD>
										<TD class=moduleicons>
												<UL></UL>
										</TD>
									</TR>
								</TBODY>
							</TABLE>
							<s:form name="myform" target="_blank">
								<input type="hidden" name="submitNodes" value=""/>
								<TABLE class=moduleBody cellSpacing=0 border="0">
									<TBODY>	
										<%for(int i=0;i<templates.size();i=i+3){%>
									   <tr bgcolor="#FFFFFF" onmouseover="this.bgColor='#DEE3E7';" onmouseout="this.bgColor='#FFFFFF';" height="21" align="center">
									   		<td align="center"><input type="radio" nodeid=<%=templates.get(i).toString()%> class="radio" name="node">			   		
									   		<%=templates.get(i).toString()%>
											<%if(templates.size()>i+1){%>
												<input type="radio" nodeid=<%=templates.get(i+1).toString()%> class="radio" name="node">			   		
									   			<%=templates.get(i+1).toString()%>
											<%}%>
											<%if(templates.size()>i+2){%>
												<input type="radio" nodeid=<%=templates.get(i+2).toString()%> class="radio" name="node">			   		
									   			<%=templates.get(i+2).toString()%>
											<%}%>
											</td>
									   </tr>	
									   <%}%>																			
										
										<TR align="center">         
										  <TD class=colrt  colspan="2">
											<input type="button" value="HTML预览" onclick="doHTML()" 
											   class=btn_mouseout 
					                           onmouseover="this.className='btn_mouseover'"
					                           onmouseout="this.className='btn_mouseout'"
					                           onmousedown="this.className='btn_mousedown'"
					                           onmouseup="this.className='btn_mouseup'" 
											   style="cursor:hand">
											&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="button" value="另存为..." onclick="saveAs()" 
												   class=btn_mouseout 
						                           onmouseover="this.className='btn_mouseover'"
						                           onmouseout="this.className='btn_mouseout'"
						                           onmousedown="this.className='btn_mousedown'"
						                           onmouseup="this.className='btn_mouseup'" 
												   style="cursor:hand">
											&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="button" value="删除..." onclick="doDelete()" 
												   class=btn_mouseout 
						                           onmouseover="this.className='btn_mouseover'"
						                           onmouseout="this.className='btn_mouseout'"
						                           onmousedown="this.className='btn_mousedown'"
						                           onmouseup="this.className='btn_mouseup'" 
												   style="cursor:hand">
										</TD>
										 </TR>
									  </TBODY>
								 </TABLE>
							</s:form>
						</DIV> 						
					</DIV>
					</DIV>
				</TD>			
			</TR>
		</TBODY>
	</TABLE>
</DIV>
<DIV class=footerpad></DIV></DIV>
<TABLE id=uxp_ftr_control cellSpacing=0 cellPadding=0>
  <TBODY>
	<TR>
		<TD id=uxp_ftr_left>
		  <UL>
			<LI>
				<A id=uxp_ftr_link_trademark href="http://g.live.com/9uxp9zh-cn/ftr1" target=_top>2008 清华大学网络中心</A> </LI>
			<LI>
				<A id=uxp_ftr_link_privacy href="http://go.microsoft.com/fwlink/?LinkId=74170" target=_top>隐私声明</A> 
			</LI>
			<LI class=uxp_ftr_item_last>
				<A id=uxp_ftr_link_legal href="http://g.msn.com/0TO_/zhcn" target=_top>使用条款</A> 
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
					  	<A id=uxp_ftr_link_helpcentral href="http://g.live.com/9uxp9zh-cn/ftr2" target=_top>帮助中心</A> 
					  </LI>
					  <LI>
					  	<A id=uxp_ftr_link_account href="http://g.live.com/9uxp9zh-cn/ftr3" target=_top>帐户</A> 
					  </LI>
					  <LI class=uxp_ftr_item_last>
					  	<A id=uxp_ftr_link_feedback href="http://g.live.com/9uxp9zh-cn/ftr4?productkey=wlhomepage" target=_top>反馈</A> 
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
</BODY>
<SCRIPT LANGUAGE="JavaScript">
<!--
document.myform2.number.focus();
function download()
{
	var temp=myform2.morw.value;
	if(temp == 1){
		myform2.type.value="month";
		if(myform2.number.value>12||myform2.number.value<=0){
		alert("输入的数值不正确,请检查填好后再提交!!");
		return false;
		}
	}
	if(temp == 2){
		myform2.type.value="week";
		if(myform2.number.value>53||myform2.number.value<=0){
			alert("输入的数值不正确,请检查填好后再提交!!");
			return false;
		}
	}
	if(temp==3){
		myform2.type.value="year";
	}
	myform2.action="outputReport.do";
	myform2.submit();
}
function doHTML(){
	if(IsChecked(myform.node, "请至少选择一个模板!"))
	{
		var myCheck = myform.node;
		if(myCheck == undefined)
		{
			return;
		}
		var length = myCheck.length;
		myform.submitNodes.value = "";
		if(length == undefined)
		{
			if(myCheck.checked)
			{
				myform.submitNodes.value = myform.submitNodes.value + ";" + myCheck.nodeid;
			}
		}
		else
		{
			for(var i = 0; i < length; i ++)
			{
				if(myCheck[i].checked)
				{
					myform.submitNodes.value = myform.submitNodes.value + ";" + myCheck[i].nodeid;
				}
			}
		}
		myform.action="htmlReport.do";
		myform.submit();
	}		
}
function doPDF(){
	if(IsChecked(myform.node, "请至少选择一个模板!"))
	{
		var myCheck = myform.node;
		if(myCheck == undefined)
		{
			return;
		}
		var length = myCheck.length;
		myform.submitNodes.value = "";
		if(length == undefined)
		{
			if(myCheck.checked)
			{
				myform.submitNodes.value = myform.submitNodes.value + ";" + myCheck.nodeid;
			}
		}
		else
		{
			for(var i = 0; i < length; i ++)
			{
				if(myCheck[i].checked)
				{
					myform.submitNodes.value = myform.submitNodes.value + ";" + myCheck[i].nodeid;
				}
			}
		}
		myform.action="PDFReportServlet.do";
		myform.submit();
	}		
}
function saveAs()
{
	if(IsChecked(myform.node, "请至少选择一个模板!"))
	{
		var myCheck = myform.node;
		if(myCheck == undefined)
		{
			return;
		}
		var length = myCheck.length;
		myform.submitNodes.value = "";
		if(length == undefined)
		{
			if(myCheck.checked)
			{
				myform.submitNodes.value = myCheck.nodeid;
			}
		}
		else
		{
			for(var i = 0; i < length; i ++)
			{
				if(myCheck[i].checked)
				{
					myform.submitNodes.value = myCheck[i].nodeid;
				}
			}
		}
		myform.action = myform.submitNodes.value+"Action.do";
		//myform.action = "downloadReport.do";	
		myform.submit();		
	}		
}
function doDelete()
{
	if(IsChecked(myform.node, "请至少选择一个模板!"))
	{
		var myCheck = myform.node;
		if(myCheck == undefined)
		{
			return;
		}
		var length = myCheck.length;
		myform.submitNodes.value = "";
		if(length == undefined)
		{
			if(myCheck.checked)
			{
				myform.submitNodes.value = myCheck.nodeid;
			}
		}
		else
		{
			for(var i = 0; i < length; i ++)
			{
				if(myCheck[i].checked)
				{
					myform.submitNodes.value = myCheck[i].nodeid;
				}
			}
		}
		myform.action = "deleteReport.do";	
		myform.submit();		
	}		
}
//检测是否有radio被选中
function IsChecked(field, msg)
{
	if(field == undefined)
	{
		return;
	}
	if(field.length == undefined)
	{
		if(!field.checked)
		{
			alert(msg);
			return false;
		}
		else
		{
			return true;
		}
	}
	else
	{
		l = field.length;
		flag = 0;
		for(i = 0; i < l; i ++)
		{
			if(field[i].checked == true)
			{
				flag ++;
			}
		}
		if(flag == 0)
		{
			alert(msg);
			return false;
		}
		else
		{
			return true;
		}
	}
}
//-->
</SCRIPT>
</HTML>
