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
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" href="css/portInfo.css"  rel="StyleSheet">
<title>接口管理</title>
	<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
	<script type="text/javascript" src="js/ext-base.js"></script>
	<script type="text/javascript" src="js/ext-all.js"></script>
	
	<script type="text/javascript">
			Ext.onReady(function(){
				Ext.QuickTips.init();
			    Ext.form.Field.prototype.msgTarget = 'side';
				var iptitle = '${device.loopbackIP}'+'/'+'${device.loopbackIPv6}';
				if('${upperThreshold}'==''){
					upperThresholds=100;
				}else{
					upperThresholds='${upperThreshold}';
				}
				if('${lowerThreshold}'==''){
					lowerThresholds=0;
				}else{
					lowerThresholds='${lowerThreshold}';
				}
					
				var simple = new Ext.FormPanel({
			        labelWidth: 75, // label settings here cascade unless overridden
			        url:'portUpdate.do',
			        frame:true,
//			        title: '${device.loopbackIP}下的接口',
			        title: iptitle+'下的接口',
			        bodyStyle:'padding:5px 5px 0',
			        width: 450,
			        defaults: {width: 308},
			        defaultType: 'textfield',
					onSubmit: Ext.emptyFn,
			        submit:function() {
			            this.getForm().getEl().dom.action = this.url;
			            this.getForm().getEl().dom.submit();
			        },
			        items: [{
			                fieldLabel: '英文名称',
			                value:"${name}",
			                name: 'name'
			            },{
			                fieldLabel: '中文名称',
			                value:"${chineseName}",
			                name: 'chineseName'
			            },{
			                fieldLabel: '接口描述',
			                value:"${description}",
			                name: 'description'
			            },{
			                fieldLabel: 'IFINDEX',
			                value:"${ifindex}",
			                name: 'ifindex',
			                regex: /^[0-9]+$/,   
               				regexText:'只能输入整数格式'			                
			            },{
			                fieldLabel: 'IPV4地址',
			                value:"${ipv4}",
			                name: 'ipv4',
			                regex: /^([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])$/,   
               				regexText:'IPV4地址不正确'			                
			            },{
			                fieldLabel: 'IPV4掩码',
			                value:"${netmask}",
			                name: 'netmask',
			                regex: /^([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]|[0-1]{0,1}[0-9][0-9]|2[0-4][0-9]|25[0-5])$/,  
               				regexText:'IPV4掩码格式不正确'
			            },{
			                fieldLabel: 'IPV6地址',
			                value:"${ipv6}",
			                name: 'ipv6',
			                regex: /^[0-9,a-f,A-F,:]+$/,   
               				regexText:'IPV6地址不正确'
			            },{
			                fieldLabel: 'IPV6前缀',
			                value:"${prefix}",
			                name: 'prefix',
			                regex: /^[0-9]+$/,   
               				regexText:'只能输入整数格式'
			            },{
						    id:"lowerThreshold",
			            	xtype : 'combo',
			                fieldLabel: '阈值下限(%)',
			                value:lowerThresholds+"%",
			                emptyText:"${lowerThreshold}"+"%",
//			                value:"${lowerThreshold}",
			                name: 'lowerThreshold',
			                regex: /^[0-9]+[%]*$/, 
//    		                hiddenName : 'lowerThreshold',
			                triggerAction : 'all',
	//				     	displayField : 'tdvalue',
	//					    valueField : 'tdid',
						    mode : 'local',
//			                 store : new Ext.data.SimpleStore({  
//						    	 fields : ['tdid', 'tdvalue'],
//						    	 data:   [ [0, '0'],[10, '10'], [20, '20'], [30, '30'], [40, '40'],
//								[50, '50'], [60, '60'], [70, '70'], [80, '80'],
	//							[90, '90']]
//						    })
	                         store:['0%','10%','20%','30%','40%','50%','60%','70%','80%','90%']
	                         
//			                regex: /^[0-9]+$/,   
//              				regexText:'只能输入整数格式'
			                
			            },{
			   	            id:"upperThreshold",
			            	xtype : 'combo',
			                fieldLabel: '阈值上限(%)',
			                emptyText:"${upperThreshold}"+"%",
			                value: upperThresholds+"%",
			                name: 'upperThreshold',
//     		                hiddenName : 'upperThreshold',
			                triggerAction : 'all',
			                regex: /^[0-9]+[%]*$/, 
//					     	displayField : 'tuvalue',
//						    valueField : 'tuid',
						    mode : 'local',
						     store:['10%','20%','30%','40%','50%','60%','70%','80%','90%','100%']
//			                store : new Ext.data.SimpleStore({  
//						    	 fields : ['tuid', 'tuvalue'],
//						    	 data:   [ [10, '10'], [20, '20'], [30, '30'], [40, '40'],
//								[50, '50'], [60, '60'], [70, '70'], [80, '80'],
//								[90, '90'], [100, '100']]
//						    })
//			                regex: /^[0-9]+$/,   
//             				regexText:'只能输入整数格式'
			                
			            }

			            ,{
			                fieldLabel: '速率',
			                value:"${maxSpeed}",
			                name: 'maxSpeed',
			                regex: /^[0-9]+[.]?[0-9]*$/,   
               				regexText:'只能输入数字格式'			                
			            },{
			                fieldLabel: '接口类型',
			                value:"${interfaceType}",
			                name: 'interfaceType',
			                regex: /^[0-9]+$/,   
               				regexText:'只能输入整数格式'			                
			            },{
			                fieldLabel: '流量监控',
			            	layout : 'column',
			                xtype: 'panel',
			                isFormField:true,
			                defaultType: 'checkbox', // each item will be a checkbox
			                items: [{
			                	columnWidth : .2,
			                    name: 'flowflag',
                                inputValue : 'flow'
			                }]
			        	},
			            new Ext.form.Hidden({
			            	name:"deviceId",
			            	value:"${deviceId}"
			            }),
			            new Ext.form.Hidden({
			            	name:"style",
			            	value:"${style}"
			            }),
			            new Ext.form.Hidden({
			            	name:"id",
			            	value:"${id}"
			            }),
			            new Ext.form.Hidden({
			            	id:"deviceTypeId",
			            	name:"deviceTypeId",
			            	value:"${device.deviceType.id}"
			            })
			        ],
			
			        buttons: [{
			            text: '  保存  ',
			            	handler:function(){
			            		
			            	var lowvalue = parseInt((Ext.get('lowerThreshold').dom.value).replace("%",""));
			            	var uppervalue= parseInt((Ext.get('upperThreshold').dom.value).replace("%",""));
			            	Ext.get("lowerThreshold").dom.value = lowvalue;
			            	Ext.get('upperThreshold').dom.value = uppervalue;
			            	if(simple.getForm().isValid()){
			            	if(lowvalue>=uppervalue){
			            		Ext.Msg.alert("阈值不正确","阈值下限大于上限，请检查数值！")	}
			            	else if(lowvalue<0||uppervalue<0){
			            		Ext.Msg.alert("阈值不正确","阈值上下限不能为负数，数值范围1-100！")}
			            	else if(lowvalue>100||uppervalue>100){
			            		Ext.Msg.alert("阈值不正确","阈值上下限过高，数值范围1-100！")}
			            	else {
			            		simple.submit();
			            		}
			            			}
			            	}
			            	},
			            		
			            		
			            {text: '  返回  ',
			            	handler:function(id,style){
			            		var id = '${device.deviceType.id}';
				            	if('${style}' == 2){
				            		window.location.href="devicelistaction.do?deviceTypeId="+id+"&status=1";
					            }
				            	else{
				            		window.location.href="toport.do?deviceId=${deviceId}&style=${style}";
					            }
			            	}
					        
			            }]
		    	});
				if(${flowFlag}){
				    		var chk=simple.items.get(12).items.get(0);
				    		chk.setValue(true);
				    	}
		    simple.render("formDiv5");

			});
		</script>	
</head>
<body>
<div id="formDiv5" style="margin-top:5%;margin-left:25%;"></div>
</body>
</html>