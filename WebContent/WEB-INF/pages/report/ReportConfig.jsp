<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML class="IE IE_Win IE_M6 IE_D0" xmlns="http://www.w3.org/1999/xhtml" 
xmlns:web xmlns:sp>
<HEAD>
	<TITLE>iNetBoss Home</TITLE>
	<META http-equiv=Content-Type content="text/html; charset=utf-8">
	<LINK href="css/hig+start.css" type=text/css rel=StyleSheet>
</HEAD>
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
																<A href="configReport.do">故障监控</A>
															<LI>
																<A href="configReport.do">故障视图</A>	
															<LI class=uxp_hdr_menuSeparator>
															<LI>
																<A href="configReport.do">流量监控</A>
															</LI>
															<LI>
																<A href="configReport.do">流量统计</A>
															</LI>
														 </UL>
														 <IFRAME 
														  style="Z-INDEX: -1; FILTER: mask(); WIDTH: 1px; POSITION: absolute; TOP: -1px; HEIGHT: 1px" 
														  tabIndex=-1 src="about:blank">
														 </IFRAME>
													</LI>
													<LI class=uxp_hdr_menuSeparator>
													<LI>
														<A href="configReport.do">帮助中心</A>
													<LI>
														<A href="configReport.do">联系我们</A>
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
	<s:form name="myForm" method="post">
	<TABLE class=main cellSpacing=0 border="0">
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
										<H2>点评</H2></TD>
									 <TD class=moduleicons>
										<UL></UL>
									 </TD>
								  </TR>
							 </TBODY>
						 </TABLE>
						 <TABLE class=moduleBody cellSpacing=0>
							<TBODY>
								<TR>								
									<TD class=colrt>
										<s:textarea  name="comment" value='${comment}'  rows="5" cssStyle="width:100%;"/>								
									</TD>
								 </TR>
							 </TBODY>
						  </TABLE>
						</DIV>
						<DIV class=module id=HotmailRow.SignOut>
						<TABLE class=moduleHeader cellSpacing=0>
							<TBODY>
								 <TR>
									 <TD class=moduletitle>
										<H2>重要事件</H2></TD>
									 <TD class=moduleicons>
										<UL></UL>
									 </TD>
								  </TR>
							 </TBODY>
						 </TABLE>
						 <TABLE class=moduleBody cellSpacing=0>
							<TBODY>
								<TR>								
									<TD class=colrt>
										<s:textarea  name="events" value='${events}'  rows="10" cssStyle="width:100%;"/>								
									</TD>
								 </TR>
							 </TBODY>
						  </TABLE>
						</DIV>										
						<DIV class=module id=SpacesRow.SignOut>
							<TABLE class=moduleHeader cellSpacing=0>
								<TBODY>
									<TR>
										<TD class=moduletitle>
											<H2>运行统计</H2>
										</TD>
										<TD class=moduleicons>
												<UL></UL>
										</TD>
									</TR>
								</TBODY>
							</TABLE>							
							<TABLE class=moduleBody cellSpacing=0>
									<TBODY>
										<TR>         
										  <TD class=colrt>
										  	请输入故障统计服务器的IP:
										  	<input name="faultIP" type="text" value="${faultIP}" maxlength="50"/>&nbsp;&nbsp;
										  </TD>
										  <TD class=colrt>
										  	请输入流量采集服务器的IP:
										  	<input name="flowIP" type="text" value="${flowIP}" maxlength="50"/>&nbsp;&nbsp;
										  </TD>										  
										</TR>
										<TR>         
										  	<TD class=colrt>							
										  	请输入BGP部署服务器的IP:
										  	<input name="webIP" type="text" value="${webIP}" maxlength="50"/>
										  	</TD>																	
											<TD class=colrt>
											请输入路由监控服务器的IP:
										  	<input name="dataIP" type="text" value="${dataIP}" maxlength="50"/>&nbsp;&nbsp;
										  </TD>
										</TR>																																					
										<TR>         
										  <TD class=colrt>
										  	<input name="faultTitle" type="text" style="color:blue" value="${faultTitle}" size="24" maxlength="50"/>
										  </TD>
										  <TD class=colrt>
										  	<input name="usabilityTitle" type="text" style="color:blue" value="${usabilityTitle}" size="24" maxlength="50"/>
										  </TD>
										</TR>																			
										<TR>         
										  <TD class=colrt>
										  	<s:textarea  name="fault" value='${fault}'  rows="10" cssStyle="width:100%;"/>
										  </TD>										        
										  <TD class=colrt>
										  	<s:textarea  name="usability" value='${usability}'  rows="10" cssStyle="width:100%;"/>
										  </TD>
										</TR>
										<TR>         
										  <TD class=colrt>
										  	<input name="faultOfLineTitle" type="text" style="color:blue" value="${faultOfLineTitle}" size="24" maxlength="50"/>
										  </TD>
										  <TD class=colrt>
										  	<input name="usabilityOfCNGITitle" type="text" style="color:blue" value="${usabilityOfCNGITitle}" size="24" maxlength="50"/>
										  </TD>
										</TR>																			
										<TR>         
										  <TD class=colrt>
										  	<s:textarea  name="faultOfLine" value='${faultOfLine}'  rows="10" cssStyle="width:100%;"/>
										  </TD>										         
										  <TD class=colrt>
										  	<s:textarea  name="usabilityOfCNGI" value='${usabilityOfCNGI}'  rows="10" cssStyle="width:100%;"/>
										  </TD>
										</TR>
										<TR>         
										  <TD class=colrt>
										  	<input name="sumOfLineTitle" type="text" style="color:blue" value="${sumOfLineTitle}" size="24" maxlength="50"/>
										  </TD>
										  <TD class=colrt>
										  	<input name="sumOfDeviceTitle" type="text" style="color:blue" value="${sumOfDeviceTitle}" size="24" maxlength="50"/>
										  </TD>
										</TR>
										<TR>								
											<TD class=colrt>
												<s:textarea  name="sumOfLine" value='${sumOfLine}'  rows="10" cssStyle="width:100%;"/>								
											</TD>																	
											<TD class=colrt>
												<s:textarea  name="sumOfDevice" value='${sumOfDevice}'  rows="10" cssStyle="width:100%;"/>								
											</TD>
										</TR>				
										<TR>         
											  <TD class=colrt>
											  	<input name="flowOfInputTitle" type="text" style="color:blue" value="${flowOfInputTitle}" size="24" maxlength="50"/>
											  </TD>
											  <TD class=colrt>
											  	<input name="flowOfOutputTitle" type="text" style="color:blue" value="${flowOfOutputTitle}" size="24" maxlength="50"/>
											  </TD>
										</TR>
										<TR>								
											<TD class=colrt>
												<s:textarea  name="flowOfInput" value='${flowOfInput}'  rows="10" cssStyle="width:100%;"/>								
											</TD>									 								
											<TD class=colrt>
												<s:textarea  name="flowOfOutput" value='${flowOfOutput}'  rows="10" cssStyle="width:100%;"/>								
											</TD>
									   </TR>	 
								</TBODY>
							</TABLE>
						</DIV>						
						<DIV class=module id=HotmailRow.SignOut>
							<TABLE class=moduleHeader cellSpacing=0>
								<TBODY>
									 <TR>
										 <TD class=moduletitle>
											<H2>流量曲线</H2></TD>
										 <TD class=moduleicons>
											<UL></UL>
										 </TD>
									  </TR>
								 </TBODY>
							 </TABLE>
							 <TABLE class=moduleBody cellSpacing=0>
								<TBODY>
									<TR>         
									  <TD class=colrt>
									  	<input name="flowOfBorderTitle" type="text" style="color:blue" value="${flowOfBorderTitle}" size="24" maxlength="50"/>
									  </TD>
									  <TD class=colrt>
									  	<input name="flowOfInternetTitle" type="text" style="color:blue" value="${flowOfInternetTitle}" size="24" maxlength="50"/>	
									  </TD>
									</TR>
									<TR>								
										<TD class=colrt>
											<s:textarea  name="flowOfBorder" value='${flowOfBorder}'  rows="10" cssStyle="width:100%;"/>								
										</TD>
										<TD class=colrt>
											<s:textarea  name="flowOfInternet" value='${flowOfInternet}'  rows="10" cssStyle="width:100%;"/>								
										</TD>
									 </TR>
									 <TR>         
									  <TD class=colrt colspan="2">
									  	<input name="flowOfIntranetTitle" type="text" style="color:blue" value="${flowOfIntranetTitle}" size="24" maxlength="50"/>
									  </TD>
									</TR>
									<TR>								
										<TD class=colrt colspan="2">
											<s:textarea  name="flowOfIntranet" value='${flowOfIntranet}'  rows="10" cssStyle="width:100%;"/>								
										</TD>										
									 </TR>
									 <TR>         
									  <TD class=colrt>
									  	<input name="flowOfBJTitle" type="text" style="color:blue" value="${flowOfBJTitle}" size="24" maxlength="50"/>
									  </TD>
									  <TD class=colrt>
									  	<input name="flowOfSHTitle" type="text" style="color:blue" value="${flowOfSHTitle}" size="24" maxlength="50"/>
									  </TD>
									</TR>
									<TR>								
										<TD class=colrt>
											<s:textarea  name="flowOfBJ" value='${flowOfBJ}'  rows="10" cssStyle="width:100%;"/>								
										</TD>
										<TD class=colrt>
											<s:textarea  name="flowOfSH" value='${flowOfSH}'  rows="10" cssStyle="width:100%;"/>								
										</TD>
									 </TR>
									 <TR align="center">         
									  <TD class=colrt  colspan="2">
										请输入模板名称(建议输入英文名称)：
										<input name="template" type="text" value="${template}" maxlength="50"/>&nbsp;&nbsp;
										<input type="button" value="确定" 
										   onclick="doAdd()"
										   class=btn_mouseout 
			                          	   onmouseover="this.className='btn_mouseover'"
			                               onmouseout="this.className='btn_mouseout'"
			                               onmousedown="this.className='btn_mousedown'"
			                               onmouseup="this.className='btn_mouseup'" 
			                               style="cursor:hand"/>	
									  </TD>
									 </TR>
								 </TBODY>
							 </TABLE>
						</DIV>							
					</DIV>
					</DIV>
				</TD>			
			</TR>
		</TBODY>
	</TABLE>
  </s:form>
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
		function doAdd(){		
		myForm.action = "addReport.do";
		myForm.submit();
	}
//-->
</SCRIPT>
</HTML>
