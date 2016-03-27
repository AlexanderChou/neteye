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
//大图标
function doSubmitDevice(id){
		if(IsChecked(document.getElementById("myid").device, "请至少选择一个设备！")){
			var myCheck = document.getElementById("myid").device;
			if(myCheck == undefined){
				return;
			}
			var length = myCheck.length;
			tempvalue = "";
			if(length == undefined){
				if(myCheck.checked){
					tempvalue = tempvalue + ";" + myCheck.getAttribute("deviceId");
				}
			}
			else{
				for(var i = 0; i < length; i ++){
					if(myCheck[i].checked){
						tempvalue = tempvalue + ";" + myCheck[i].getAttribute("deviceId");
					}
				}
			}
			if(confirm("您确认要删除选中的设备吗？")){
				
				Ext.Ajax.request({
				            		
								url : "deviceDelete.do",
								params : {
									submitDevice : tempvalue,
									style:id,
									deviceTypeId:id
								},
								success : function(result, request) {
									window.location.reload()
									Ext.Msg.alert("成功","删除设备成功")
								}
								
			})
			}
		}
}
function doFault(){
		if(IsChecked2(document.getElementById("myid").device, "无选中任何设备！")){
			var myCheck = document.getElementById("myid").device;
			if(myCheck == undefined){
				return;
			}
			var length = myCheck.length;
			tempvalue = "";
			if(length == undefined){
				if(myCheck.checked){
					tempvalue = tempvalue + ";" + myCheck[i].getAttribute("deviceId");
				}
			}
			else{
				for(var i = 0; i < length; i ++){
					if(myCheck[i].checked){
						tempvalue = tempvalue + ";" + myCheck[i].getAttribute("deviceId");
					}
				}
			}
			if(tempvalue.length==0){
					if(confirm("您确认不对任何设备进行监控吗？")){
						Ext.Ajax.request({
				            		
								url : "deviceconf.do",
								params : {
									submitDevice : tempvalue,
									style:id
								},
								success : function(result, request) {
									Ext.Msg.alert("成功","取消设备监控成功"),
									window.location.reload()
								}
								
			})
				}
			}else{
				if(confirm("您确认要监控选中的设备吗？")){
					Ext.Ajax.request({
				            		
								url : "deviceconf.do",
								params : {
									submitDevice : tempvalue,
									style:id
								},
								success : function(result, request) {
									Ext.Msg.alert("成功","监控选中的设备成功"),
									
									window.location.reload()
								}
								
			})
				}
			}
		}
}
//全选
function doSelAllDevice(obj){
		var myCheck = document.getElementById("myid").device;
		if(myCheck == undefined){
			return;
		}
		var length = myCheck.length;
		if(length == undefined){
			myCheck.checked = obj.checked;
		}else{
			for(var i = 0; i < length; i ++){
				myCheck[i].checked = obj.checked;
			}
		}
	}
//校验是否有元素被选中
function IsChecked(field, msg){
		if(field == undefined){
			return;
		}
		if(field.length == undefined){
			if(!field.checked){
				alert(msg);
				return false;
			}else{
				return true;
			}
		}else{
			l = field.length;
			flag = 0;
			for(i = 0; i < l; i ++){
				if(field[i].checked == true){
					flag ++;
				}
			}
			if(flag == 0){
				alert(msg);
				return false;
			}else{
				return true;
			}
		}
}

function IsChecked2(field, msg){
		if(field == undefined){
			return;
		}
		if(field.length == undefined){
			if(!field.checked){
				alert(msg);
				return false;
			}else{
				return true;
			}
		}else{
			l = field.length;
			flag = 0;
			for(i = 0; i < l; i ++){
				if(field[i].checked == true){
					flag ++;
				}
			}
			if(flag == 0){
				alert(msg);
				return true;
			}else{
				return true;
			}
		}
}

//添加
function addDevice(id,style){
	window.location.href = "deviceAdd.do?sstatus=0&id="+id+"&style="+style;
}

function goback(){
	window.location.href = "deviceClass.do";
}

