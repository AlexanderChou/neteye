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
	<title></title>
	<link rel='StyleSheet' href="css/topoInit.css" type="text/css" />
    <link type="text/css" href="css/ext-all.css"  rel="stylesheet"/>   
    <script type="text/javascript" src="js/ext-base.js"></script>
    <script type="text/javascript" src="js/ext-all.js"></script>
	<script type="text/javascript" >
	
		var yearData = [['2001', '2001'], ['2002', '2002'], ['2003', '2003'], ['2004', '2004'], ['2005', '2005'], ['2005', '2005'], ['2006', '2006'], ['2007', '2007'], ['2008', '2008'], ['2009', '2009'], ['2010', '2010']];
  		
  		var TReader = new Ext.data.Record.create([
  			{name:'value', mapping:0},
  			{name:'label', mapping:1}
  		]);
  		
  		var myReader = new Ext.data.ArrayReader({
  			id:0
  		}, TReader);
		
		var yearStore = new Ext.data.Store({
  			reader:myReader,
  			data:yearData
  		});
  		
  		var monthData = [['1', '1'], ['2', '2'], ['3', '3'], ['4', '4'], ['5', '5'], ['6', '6'], ['7', '7'], ['8', '8'], ['9', '9'], ['10', '10'], ['11', '11'], ['12', '12']];
			
		var monthStore = new Ext.data.Store({
			reader:myReader,
			data:monthData
		});
  		
		function mainBody(flag) {
			/* 对组建的清空操作 */
	  		var container = Ext.getCmp("container");
			var item = null;
			while (item = container.items.first()) {
				var label = item.el.up(".x-form-item");
				label.remove();
				container.remove(item);
			}
			
			/* 对组建的添加 */
	  		switch (flag) {
	  			case "year" :
		  			var yearComb = new Ext.form.ComboBox({
			  			store:yearStore,
			  			displayField:"label",
			  			valueField:'value',
			  			mode:"local",
			  			fieldLabel:"年份",
			  			name:"year",
			  			id:"year",
			  			triggerAction:"all",
                        value:new Date().getYear()
			  		});
        			container.add(yearComb);
        			container.doLayout();
        			break;
		  		case "month" :
		  			var yearComb = new Ext.form.ComboBox({
			  			store:yearStore,
			  			displayField:"label",
			  			valueField:'value',
			  			mode:"local",
			  			fieldLabel:"年份",
			  			name:"year",
			  			id:"year",
			  			triggerAction:"all",
                        value:new Date().getYear()
			  		});
					var monthComb = new Ext.form.ComboBox({
						store:monthStore,
						displayField:"label",
						valueField:"value",
						mode:"local",
						fieldLabel:"月份",
						name:"month",
						readOnly:true,
		        		value:new Date(),
			  			id:"month",
			  			triggerAction:"all",
                        value:new Date().getMonth() + 1
					});
					container.add(yearComb);
					container.add(monthComb);
        			container.doLayout();
        			break;
        		case "day": 
					/* 开始时间 */
					var startDate = new Ext.form.DateField({
						fieldLabel: '开始时间',
						id:'fromDate',
						name:'fromDate',
						format: 'Y-m-d',
		        		endDateField: 'toDate',
		        		value:new Date(),
		        		readOnly:true,
		        		width:200,
			  			id:"startDate"
					});
					
					/* 结束时间 */
					var endDate = new Ext.form.DateField({
						fieldLabel: '结束时间',
						id:'toDate',
						name:'toDate',
						format: 'Y-m-d', 
						readOnly:true,
		        		value:new Date(),
						startDateField: 'fromDate',
		        		width:200,
			  			id:"endDate" 
					});
					container.add(startDate);
					container.add(endDate);
        			container.doLayout();
	  		}
		}

        Ext.onReady(function(){
        	
        	Ext.QuickTips.init();
			Ext.form.Field.prototype.msgTarget = 'side';

			var	nameStore = new Ext.data.Store({
			    proxy: new Ext.data.HttpProxy({ 
				url:"json/viewName.do"
				}),
				reader:new Ext.data.JsonReader({
				root:"viewList"
				},
				[{name: 'id', mapping: 'id'},{name: 'name', mapping: 'name'}]
				)
			});
			nameStore.load();
		    var MsgForm = new Ext.form.FormPanel({
		    	baseCls: 'x-plain',
		        labelAlign: 'left',
		    	buttonAlign: 'right',
		    	frame: true,
		    	labelWidth: 55,
		    	height:355,
		        onSubmit : Ext.emptyFn, 
		    	submit : function() {
		    		   
			    	this.getEl().dom.action ='flowQueryList.do';
			    	this.getEl().dom.method = 'post';      
			    	this.getEl().dom.submit();      
		    	},
		        items: [{
		            	layout : 'column',
		                xtype: 'fieldset',
		                title: '选择查询方式',
		                autoHeight: true,
		                defaultType: 'radio', // each item will be a radio
		                items: [{
		                	columnWidth : .25,
		                    boxLabel: '年',
		                    name: 'serviceType',
		                    checked:true,
                            inputValue : 'year',
                            anchor : '95%',
                            listeners:{
					        	render:function(){

					        		Ext.fly(this.el).on("click", function() {
					        			mainBody("year");
					        		});
					        		
					        	}
					        }
		                }, {
		                	columnWidth : .25,
		                    labelSeparator: '',
		                    boxLabel: '月',
		                    name: 'serviceType',
                            inputValue : 'month',
                            anchor : '95%',
                            listeners:{
	                            render:function(d)  {
	                            	Ext.fly(this.el).on("click",function(){
	                            		mainBody("month");
	                            	});
	                            }
                            }
		                }, {
		                	columnWidth : .25,
		                    labelSeparator: '',
		                    boxLabel: '日',
		                    name: 'serviceType',
                            inputValue : 'day',
                            anchor : '95%',
                            listeners:{
                            	render:function() {
                            		Ext.fly(this.el).on("click", function() {
                            			mainBody("day");
                            		});
                            	}
                            }
		                }]
			        },{
		        	xtype:'fieldset',
		        	title:'选择流量发生时间',
		        	collapsible:true,
		        	id:'container',
		        	width:290,
		        	autoHeight:true,
		        	defaultType:'textfield',
		        	items:[
		        	      	new Ext.form.ComboBox({
					  			store:yearStore,
					  			displayField:"label",
					  			valueField:'value',
					  			mode:"local",
					  			fieldLabel:"年份",
					  			name:"year",
					  			id:"year",
					  			triggerAction:"all",
		                        value:new Date().getYear()
			  				})
						]
		       		 },
			        {
		                xtype: 'fieldset',
		                title: '基于视图查询',
		                checkboxToggle:true,
           				checkboxName:'view',
           				id:"view",
		                autoHeight: true,
		                defaultType: 'textfield', 	// each item will be a textfield
		                collapsed: true,
		                items: [
		                new Ext.form.ComboBox({	                		
		                		allowBlank:false,   
		                		blankText:"不能为空，请填写",
    							mode: 'local',					  			
					  			readOnly:true,
					  			triggerAction:'all', 
					  			emptyText:'请选择...',
					  			fieldLabel:"视图名称",
					  			name:"viewName",
					  			id:"viewName",
					  			store:nameStore,
					  			valueField: 'id',   
    							displayField: 'name'  
			  			})]
			        },{
		                xtype: 'fieldset',
		                title: '基于地址查询',
		                checkboxToggle:true,
           				checkboxName:'address',
           				id:"address",
		                autoHeight: true,
		                defaultType: 'textfield', 	// each item will be a textfield
		                collapsed: true,
		                items: [{
	                		fieldLabel:"设备IP",
	                		name:"ip",
	                		alowBlank:false,
	                		width:150,
			  				id:"ip"
		                }, {
	                		width:150,
		                	fieldLabel:"设备端口",
	                		name:"ifindex",
	                		alowBlank:false,
			  				id:"ifIndex"
		                }]
			        }]
		    });
		    var fromDate = Ext.getCmp('fromDate');
		    var toDate = Ext.getCmp('toDate');
        	
        	var bPanel = new Ext.Panel({
        		buttonAlign:"center",
        		bodyStyle:"background-color:#D4E2F4",
        		border:false,
        		buttons: [{
            		text: '确定',
		            handler: function() {
	            		var ip = Ext.getCmp("ip").getValue()
	            		var ifIndex = Ext.getCmp("ifIndex").getValue();
	            		var flag1=Ext.getCmp("view").collapsed;
	            		var flag2=Ext.getCmp("address").collapsed;
	            		var viewName=Ext.getCmp("viewName").getValue();
	            		if(flag1&&flag2){
	            				alert("请选择查询方式！");
		            			return;
	            		}
	            		if(!flag1&&!flag2){
	            				alert("只能选择一种查询方式！");
		            			return;
	            		}
	            		if(!flag1){	            			
		            		if(viewName==""){	            			
		            			alert("视图名称不能为空！");
		            			return;
		            		}
	            		}
	            		if(!flag2){
		            		if(ip == "") {
		            			alert("IP 不能为空！");
		            			return;
		            		} else {
		            			var reg = /^[\d\w.:\\/]+$/;
		            			if (!reg.test(ip)) {
		            				alert("请输入正确的ip");
		            				return;
		            			}
		            		}
		            		if (ifIndex == "") {
		            		} else {
		            			var reg = /^\d+$/;
		            			if (!reg.test(ifIndex)) {
		            				alert("请输入正确的端口号");
		            				return;
		            			}
		            		}
	            		}
	            		MsgForm.form.submit();
		        	}
		        },{
		            text: '关闭',
		            handler  : function(){
		            	window.history.back();
		            }
		        }]
        	});
        
        	var panel = new Ext.Panel({
        		title:"流量统计",
        		width:840,
        		autoScroll : true,
             	height : document.body.clientHeight * 0.95 + 5,
        		plain: true,
        		items:[MsgForm, bPanel],
        		bodyStyle:"background-color:#D4E2F4;padding-top:5px;"
        	});
        	
        	panel.render("container");
        });
		
	</script>
	
</head>
<body>
	<div id="outer">
		<div id="bodyDiv">
			<div id="menu">
				<s:include value="right_menu.jsp"></s:include>
			</div>
		
			<div id="infoDiv">
				<div id='container'></div>
			</div>
		</div>
	</div>
	
</body>
</html>