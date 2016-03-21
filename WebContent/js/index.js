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

Ext.onReady(function(){
	Ext.Ajax.request({
		url:"json/isNew.do",
		success:function(res, req){
			if (res.responseText == "no") {//表中有数据返回yes，表中没数据返回no
				Ext.Msg.confirm('信息','您还没有拓扑历史，是否进行？',function(btn){
					if(btn=='yes'){	
						var dd = document.getElementById("main");
						dd.src = "totalinit.do";
					} else {
						
					}
				});	
			}
		}
	});
});


function doTopo(){
    Ext.QuickTips.init();

    Ext.form.Field.prototype.msgTarget = 'side';

    var MsgForm = new Ext.form.FormPanel({
        //labelWidth: 75, // label settings here cascade unless overridden
        frame:true,
        bodyStyle:'padding:5px 5px 0',
        width: 370,

        items: [{
            xtype:'fieldset',
            title: 'Essential Parameters',
            collapsible: true,
            autoHeight:true,
            defaults: {width: 230},
            defaultType: 'textfield',            
            items :[{
            		xtype:'datefield',
			        fieldLabel: 'Start Date',
			        name: 'startdt',
			        id: 'startdt'			        
			      },{
                    fieldLabel: 'Topo Name',
                    name: 'topoName',
                    allowBlank:false
                },{
                    fieldLabel: 'Start IP',
                    name: 'IP',
                    allowBlank:false
                },{
                    fieldLabel: 'Community',
                    name: 'community',
                    inputType :"password",
                    allowBlank:false
                },{
                	fieldLabel:"+",
                	inputType :"button",
                	value :'dddd',
                	handler:function(){
                		alert(123);
                	}
                },{
                    xtype:"combo",
                    name: 'version',
                    store:["1","2c","3"],
                    fieldLabel:"SNMPVersion",
                    triggerAction:'all',
                    hiddenName:'SNMPVersion',
                    allowBlank:false,
                    editable : false,
                    forceSelection : true,
                    emptyText:'Please Chose SNMP Version'
            }]
            
        },{
            xtype:'fieldset',
            checkboxToggle:true,
            title: 'Option Parameters',
            autoHeight:true,
            defaults: {width: 230},
            defaultType: 'combo',
            collapsed: true,
            items :[{
                    name: 'maxthread',
                    store:["10","20","30","40","50","64"],
                    fieldLabel:"MaxThread",
                    triggerAction:'all',
                    hiddenName:'maxthread',
                    allowBlank:true,
                    editable : false,
                    forceSelection : true,
                    emptyText:'Please Chose MaxThread'
                },{
                    name: 'retries',
                    store:["0次","1次","2次"],
                    fieldLabel:"SNMPRetries",
                    triggerAction:'all',
                    hiddenName:'retries',
                    allowBlank:true,
                    editable : false,
                    forceSelection : true,
                    emptyText:'Please Chose SNMPRetries'
                },{
                    name: 'timeout',
                    store:["1秒","2秒","4秒"],
                    fieldLabel:"SNMPTimeout",
                    triggerAction:'all',
                    hiddenName:'timeout',
                    allowBlank:true,
                    editable : false,
                    forceSelection : true,
                    emptyText:'Please Chose Timeout'
               }]
        }]
    });
    
    
    
    var win = new Ext.Window({
        title: 'Topo Guide',
        width: 420,
        height:400,
        x:330,
        y:150,
        layout: 'fit',
        plain: true,
        bodyStyle:'padding:5px;color:black;',
        buttonAlign:'center',
        items:[MsgForm],
        buttons: [{
            text: 'save',
            handler  : function(){
            if(MsgForm.form.isValid()){
            		MsgForm.form.doAction('submit',{
            			url: 'json/topoFind.do',
     					method: 'post',
     					waitTitle:"提示",
     					waitMsg:"<font color='black'>正在发送，请稍后......</font>",
     					success:function(form,action){
     						win.close();  
     						window.location.href = "find.do";    						
     					},
     					failure:function(form,action){
     						Ext.Msg.alert("操作失败!","<font color='black'>"+action.result.data+"</font>");
     					}
        			}); 
        		}
            }
        },{
            text: 'close',
            handler  : function(){
            	win.close();
            }
        }]
    });
    win.show();
}
