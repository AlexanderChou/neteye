/****************************************
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
******************************************/
if (window.ActiveXObject && !window.XMLHttpRequest) {
      window.XMLHttpRequest=function() {
      	return new ActiveXObject((navigator.userAgent.toLowerCase().indexOf('msie 5') != -1)?'Microsoft.XMLHTTP':'Msxml2.XMLHTTP');
      	}
}

function checkName(){
var topoName=document.getElementById("name").value;
var nameinfo=document.getElementById("nameinfo");
   var req=new XMLHttpRequest();
     if (req){
         req.onreadystatechange=function() {
          if(req.readystate<4){
          		nameinfo.style.color='green';
               nameinfo.innerHTML='&nbsp;&nbsp;sending...';
          }
          if (req.readyState==4 && req.status==200) {
          		
          	  var temp = eval('('+req.responseText+')'); 
             if(temp.info=="0"){				  
			   //判断是否包含非法字符
           	if(!isName.test(topoName)) {
				 nameinfo.style.color='red';
				 nameinfo.innerHTML='<span class="red">[该名称包含非法字符，请重新输入]</span>';   
			}else{
               nameinfo.style.color='green';
               nameinfo.innerHTML='<span class="green">[该名称可正常使用]</span>';              
			}
             }else if(temp.info=="2") {
                  nameinfo.style.color='red';
              	   nameinfo.innerHTML='<span class="red">[该名称重复，请选择其它的名称]</span>';
             }else if(temp.info=="3") {
                  nameinfo.style.color='red';
                  nameinfo.innerHTML='<span class="red">[拓扑名称不能为空]</span>';
             }			  
          }//if(req.readyState==4 && req.status==200) 
        }
        req.open('post',"json/topoCheck.do");
        req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        req.send("checkValue="+topoName+"&temp="+new Date().getTime());
     }
 	}
   function checkIP(){
	var topoIP=document.getElementById("addr1").value;
	var ipinfo=document.getElementById("ipinfo");
    if(topoIP!=""){
		if(!isIPaddress.test(topoIP)){
			ipinfo.style.color='red';
            ipinfo.innerHTML='<span class="red">[该IP地址无效，请重新输入！]</span>'; 
		}else{
			ipinfo.style.color='green';
            ipinfo.innerHTML='<span class="green">[该IP地址可正常使用]</span>';  
		}
	}else{
		ipinfo.style.color='red';
           ipinfo.innerHTML='<span class="red">[IP地址不能为空]</span>';
	}
}
   function checkMask(){
	   var topoMask=document.getElementById("maskId1").value;
	   var maskinfo=document.getElementById("maskinfo");
	   if(topoMask!=""){
		   if(!isMask.test(topoMask)){
			   maskinfo.style.color='red';
			   maskinfo.innerHTML='<span class="red">[该子网掩码无效，请重新输入！]</span>'; 
		   }else{
			   maskinfo.style.color='green';
			   maskinfo.innerHTML='<span class="green">[该子网掩码可正常使用]</span>';  
		   }
	   }else{
		   maskinfo.style.color='red';
		   maskinfo.innerHTML='<span class="red">[子网掩码不能为空]</span>';
	   }
   }