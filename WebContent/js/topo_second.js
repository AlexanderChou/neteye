function getTopoStartPage(values,flag,IP) {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	var dd = {
		layout:"column",   
		frame:true,
		items:[{
			columnWidth:.8,//第二列   
            layout:"form",  
            items:[{   
              	fieldLabel:'community',
				width:220, 
        		anchor:"90%",
				inputType:'password',
				xtype:"textfield",
        		allowBlank: false,
        		name:'community'
                }]   

		},{
			columnWidth:.2,
			layout:"form",
			items:[{
               	tooltip:"删除  community 输入框", 
              	tooltipType:"qtip",
             	xtype:"button",  
              	handleMouseEvents:true, 
              	icon:"images/delete.png",
              	width:80,
				listeners:{
					"click":function(){
						var content = Ext.getCmp("content");
						content.remove(this.ownerCt.ownerCt);
						content.doLayout();	
					}
				}
	        }]
		}]
	};
	
	
    var MsgForm = new Ext.form.FormPanel({
		baseCls: 'x-plain',
     	layout:"form",   
        labelWidth: 80,   
        labelAlign:"left", 
        border:false,  
		autoHeight:true, 
        frame:true,
        items: [{
				id:"content",
				autoHeight:true,
				items:[{
					layout:"column",   
					frame:true,
					items:[{
						columnWidth:.8,
						layout:"form",
						items:[{
				    		fieldLabel:'拓扑发现名称',
							xtype:"textfield",
				    		id:'nameId',
				    		name:'secondTopoName',
				    		allowBlank: 0,
				    		blankText:'拓扑发现名称不能为空!',
				    		//invalidText:'视图名称不能为空!',
				            listeners: {
				               blur  : function(combo) {
				               
				               		/* 检查视图的名字是否含有中文 */
				               		var reg = /[\u2e80-\u9fff]/;
				               		if (reg.test(Ext.get('nameId').getValue())) {
				               			alert("拓扑发现名称不能含有中文字符！");
				               			return;
				               		}
				        			Ext.Ajax.request({
				        				url : 'json/topoCheck.do',
				        				method : 'post',		
				        				params : {checkValue:Ext.get('nameId').getValue()},
				        				success : function(result, request) {
				        		    		var info = Ext.decode(result.responseText).info;
				        		    		if(info == "2"){
				        		    			Ext.Msg.alert("警告！","<font color=red>拓扑发现名称重复</font>");
				        		    		}
				        				}
				        			});
				        		}
				          	},
				    		anchor:"90%"//330px-labelWidth剩下的宽度的95%，留下5%作为后面提到的验证错误提示
						},{   
			              	fieldLabel:'community',
							width:220, 
			        		anchor:"90%",
			        		inputType:'password',
							xtype:"textfield",
			        		allowBlank: false,
			        		name:'community'
			                }]
					},{
						columnWidth:.2,
						layout:"form",
						items:[{
							text:"",
							xtype:"panel",
							height:25
						},new Ext.Button({
			               	tooltip:"添加 community 输入框", 
			              	tooltipType:"qtip",
			             	type:"button",  
			              	handleMouseEvents:true, 
			              	icon:"images/add.png",
			              	width:80,
							listeners:{
								"click": function(){
									var content = Ext.getCmp("content");
									content.add(dd);
									content.doLayout();
								}
							}
				        })]
					}]
				}
				]
		}]
    });
    
    var win = new Ext.Window({
        title: '启动拓扑发现',
        modal:true,
        width: 420,
		frame:true,
		autoHeight:true,
        x:330,
        y:150,
        layout: 'fit',
        plain: true,
        bodyStyle:'padding:5px;color:black;',
        buttonAlign:'center',
        items: MsgForm,
        buttons: [{
            text: '确定',
            handler  : function(){
            /* 判断拓扑发现名称是否含有中文字符 */
            var reg = /[\u2e80-\u9fff]/;
       		if (reg.test(Ext.get('nameId').getValue())) {
       			alert("拓扑发现名称不能含有中文字符！");
       			return;
       		}
            if(MsgForm.form.isValid()){
            		MsgForm.form.doAction('submit',{
            			url: 'json/secondTopoStart.do',
     					method: 'post',
     					params : {
     						//secondTopoName:Ext.get('nameId').getValue(),  这两个参数可以不要 只要form表单里name里有就成
     					    //community:Ext.get('community').getValue(),
     					    values:values,
     					    flag:flag,
     					    IP:IP
     					},
     					waitTitle:"提示",
     					waitMsg:"<font color='black'>正在发送，请稍后......</font>",
     					success:function(form,action){
     						Ext.MessageBox.confirm("确认", "当前视图尚未保存，您是否要跳转到拓扑发现页面？", function(btn){
            					if(btn=="yes"){
            						//跳转至拓扑发现页面
            						alert("sec name="+Ext.get('nameId').getValue());
            						window.location.href = "topoDisplay.do?name=" + Ext.get('nameId').getValue();
            					}else{
            						win.close();
            					}
     						});
     					},
     					failure:function(form,action){
     						Ext.Msg.alert("操作失败!","<font color='black'>请检查参数是否正确</font>");
     					}
        			}); 
        		}
            }
        },{
            text: '关闭',
            handler  : function(){
            	win.close();
            }
        }]
    });
    win.show();
}