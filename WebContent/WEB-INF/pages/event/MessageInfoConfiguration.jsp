<%--
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN "   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>短信配置</title>
    <link rel="stylesheet" type="text/css" href="css/ext-all.css"/>
    <script type="text/javascript" src="js/ext-base.js"></script>
    <script type="text/javascript" src="js/ext-all.js"></script>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript">
	
	$(function(){
		$("#addAddress").click(function(){
			addDesAddress();
		});
	})

    </script>

</head>
<body>
<div id="outer">
	<div id="bodyDiv">
		<div id="menu">
			<s:include value="right_menu.jsp"></s:include>
		</div>
	
		<div id="infoDiv">
		
			<div id="menuDiv1" class="menuDiv" style="width:840px;background-color:#F2F4FF;">
				<div id="top" style="width:840px;">
					<h2>短信配置</h2>
				</div>
			
				<div id="leftMenu" style="width:840px;background-color:#F2F4FF;overflow:auto;height:100%;">
		
					<form name="email" method="POST" action="messageConf.do" onsubmit="return ChcekForm();">
						<table width="100%" class="ListTable" id="addPara" >
											<tr height="23" align="left" class="title">
												<td colspan="4">&nbsp;</td>
											</tr>
											<tr height="23" align="left" class="title">
												<td width="5%">&nbsp;</td>
												<td width="15%">发送人号码：</td>
												<td colspan="2">
													<input type="password" disabled="true" name="srcNumber" onblur="checkSrcNumber()" style="width:202px;" value='${srcNumber }'  maxlength="50" id="srcNumber"/>
													<span id='srcNumberInfo'></span>
												</td >									
											</tr>
											
											<tr height="23" align="left" class="title ">
												<td width="5%">&nbsp;</td>
												<td width="15%">发送人密码：</td>
												<td colspan="2">
													<input type="password" name="passwd" disabled="true" onblur="checkPasswd()" style="width:202px;" value='${passwd }' maxlength="50" value="" id="passwd"/>
												    <span id='passwdInfo'></span> 
												</td>									
											</tr>
											
								
                                            <%
                                                  String []recs=(String[])request.getAttribute("recNumber");
											      int length=recs.length;
                                            %>
                                            <%
                                             if(length >1){
                                            %>
							
											<%
											      
											      for(int i=0;i<length-1;i++){
											
											%>
											<tr height="23" align="left" class="title recMessage" id="del<%=i+1%>">
												<td width="5%">&nbsp;</td>								
							  					<td width="15%">接收者号码：</td>
							  					<td colspan="2"> 
												<input name="recNumber" onblur ="checkDesAddress(this.id)" type="text" maxlength="50" style="width:202px;" value ="<%=recs[i]%>" id="recAdd<%=i+1%>"/>
							  					<span id='desInforecAdd<%=i+1%>'></span>
							  					<img align='absmiddle' src="images/delete.gif" alt="删除" onclick ="deleteAddess(this.id)" id="<%=i+1%>" style="cursor:hand">
												</td> 
											</tr>		         
											<%
											}
											%>
											<tr height="23" align="left" class="title recMessage">
												<td width="5%">&nbsp;</td>								
							  					<td width="15%">接收者号码：</td>
							  					<td colspan="2"> 
												<input name="recNumber" onblur ="checkDesAddress(this.id)" type="text" maxlength="50" style="width:202px;" value ='<%=recs[length -1]%>' id="recAdd<%=length%>"/>
							  					<span id='desInforecAdd<%=length%>'></span>
												<img align='absmiddle' src="images/default/dd/drop-add.gif" alt="添加" id="addAddress" style="cursor:hand">
												</td> 
											</tr>		
											<%
											}
											else{
											%>
											<tr height="23" align="left" class="title recMessage">
												<td width="5%">&nbsp;</td>								
							  					<td width="15%">接收者号码：</td>
							  					<td colspan="2"> 
												<input name="recNumber" onblur ="checkDesAddress(this.id)" type="text" maxlength="50" style="width:202px;" value ='<%=recs[length -1]%>' id="recAdd1"/>
							  					<span id='desInforecAdd1'></span>
												<img align='absmiddle' src="images/default/dd/drop-add.gif" alt="添加" id="addAddress" style="cursor:hand">
												</td> 
											</tr>		
												
											<%
											}
											%>			
											<tr height="23" align="left" class="title">
												<td width="5%">&nbsp;</td>								
							  					<td width="15%"></td>
							  					<td width="15%" align="center"> 
												<input class="button" type="submit" value="保存" >
							  					</td>				  					
							  					<td align="left">
												</td>
											</tr>								
										</table>							
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
<SCRIPT LANGUAGE="JavaScript">
    var srcNumberflag =1;
    var passwdflag =1;
    var desAddflag =1;
	function addDesAddress(){
		var recMessage = $(".recMessage");
		
		if (recMessage.length > 0) {
			var old_rec = $(recMessage[recMessage.length-1]);
			var new_rec = old_rec.clone().attr("id", "rec" + recMessage.length);
			var img = new_rec.children("td").children("img");
			img.attr("src", "images/delete.gif").attr("alt", "删除").attr("id", "removeImg");
			img.unbind();
			img.click(function(){
				$("#" + "rec" + recMessage.length).remove();
			
			});
			old_rec.after(new_rec);
		}
	}
	//check  发送人号码
	function checkSrcNumber(){
	   var srcNumber=document.getElementById("srcNumber").value;
	   if(srcNumber ==""){
	        srcNumberflag=0;
	        document.getElementById("srcNumber").focus();  
	        srcNumberInfo.style.color='red';
            srcNumberInfo.innerHTML='<span class="red">[号码名不能为空]</span>';
	   } 
	   else{
	         srcNumberflag=1;
	         srcNumberInfo.innerHTML='<span class="red"></span>';
	   }   
	
	}
	
    //checkPassWd
    function checkPasswd(){
       var passwd = document.getElementById("passwd").value;
       if(passwd==""){
          passwdflag=0;
          document.getElementById("passwd").focus();  
          passwdInfo.style.color='red';
          passwdInfo.innerHTML = '<span class="red">[密码不能为空]</span>';
       }
       else{
          passwdflag=1;
          passwdInfo.innerHTML = '<span class="red"></span>';
       }
    }    
  
    //checkDesMessgeAddress
   function checkDesAddress(id){
        //验证只有一个地址
        var recAddress = document.getElementById(id).value;
        if(recAddress==""){
          desAddflag=0;
          document.getElementById("desInfo"+id).focus();  
          document.getElementById("desInfo"+id).style.color='red';
          document.getElementById("desInfo"+id).innerHTML = '<span class="red">[号码不能为空]</span>';
       }
       else{
           desAddflag=1;
           document.getElementById("desInfo"+id).innerHTML = '<span class="red"></span>'; 
       }//end else
    
    //验证如果有多个地址
    var inputId=document.all(id);
    if(!inputId.value){
     var inforId=document.all("desInfo"+id);
     for(i=0;i<inputId.length;i++)
     {
       var recAddress = inputId[i].value;
       if(recAddress==""){
          desAddflag=0;
          inputId[i].focus();  
          inforId[i].style.color='red';
          inforId[i].innerHTML = '<span class="red">[号码不能为空]</span>';
       }
       else{
           desAddflag=1;
           inforId[i].innerHTML = '<span class="red"></span>'; 
       }//end else
      }//end for
    }
        
        var recAddress = document.getElementById(id).value;
        if(recAddress==""){
          desAddflag=0;
          document.getElementById("desInfo"+id).focus();  
          document.getElementById("desInfo"+id).style.color='red';
          document.getElementById("desInfo"+id).innerHTML = '<span class="red">[号码不能为空]</span>';
       }
       else{
           desAddflag=1;
           document.getElementById("desInfo"+id).innerHTML = '<span class="red"></span>'; 
       }//end else
 
  }
    //删除表单元素
    function deleteAddess(id){
            $("#del"+id).remove();
         	
    }
    //提交时进行验证
    function ChcekForm(){
        if(srcNumberflag && passwdflag && desAddflag){
           return true;
        }
        else{
        Ext.Msg.alert("提示信息","请填写正确的信息");
          return false;
        }
    
    }
	var menuDiv = document.getElementById("menuDiv1");
	var height = document.body.clientHeight * 0.95;
	menuDiv.style.height = height;
	</SCRIPT>
</html>