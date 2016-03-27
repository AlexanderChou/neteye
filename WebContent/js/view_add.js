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
//添加视图

function addView() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
    var MsgForm = new Ext.form.FormPanel({
		baseCls: 'x-plain',
        labelAlign: 'left',
    	buttonAlign: 'right',
    	frame: true,
    	labelWidth: 55,
        items: [{
        	xtype:'fieldset',
        	title:'视图信息',
        	//collapsible:true,
        	width:290,
        	//autoHeight:true,//使自适应展开排版
        	height:140,
        	defaults:{width:200,height:30},
        	defaultType:'textfield',
        	items:[{
	        		fieldLabel:'名称',
	        		id:'nameId',
	        		name:'name',
	        		allowBlank: false,
	        		blankText:'视图名称不能为空!',
	        		//invalidText:'视图名称不能为空!',
                    listeners: {
                       blur  : function(combo) {
                       
                       		/* 检查视图的名字是否含有中文
                       		var reg = /[\u2e80-\u9fff]/;
                       		if (reg.test(Ext.get('nameId').getValue())) {
                       			alert("视图名称不能含有中文字符！");
                       			return;
                       		} */
                       		
		        			Ext.Ajax.request({
		        				url : 'json/checkName.do',
		        				method : 'post',		
		        				params : {name:Ext.get('nameId').getValue()},
		        				success : function(result, request) {
		        		    		var isHaveRecord = Ext.decode(result.responseText).isHaveRecord;
		        		    		if(isHaveRecord == "yes"){
		        		    			Ext.Msg.alert("警告！","<font color=red>拓扑保存视图名称重复</font>");
		        		    		}
		        				}
		        			});
		        		}
                  	},
	        		anchor:"90%"//330px-labelWidth剩下的宽度的95%，留下5%作为后面提到的验证错误提示
        		},{
	        		fieldLabel:'描述',
	        		anchor:"90%",
	        		name:'description'
        		},{
                    xtype:"combo",
                    id:"isHomePage",
                    name: 'homePage',
                    store:["yes","no"],
                    fieldLabel:"级别",
                    emptyText:'选择是否首页显示',
                    blankText:'请选择是否首页显示',
                    triggerAction:'all',
                    hiddenName:'homePage',
                    allowBlank : false,
                    editable : false,
                    forceSelection : true,
                    listeners: {
                        select : function(combo) {
		        			Ext.Ajax.request({
		        				url : 'json/checkHomePage.do',
		        				method : 'post',		
		        				params : {homePageValue:Ext.get('isHomePage').getValue()},
		        				success : function(result, request) {
		        		    		var isHaveRecord = Ext.decode(result.responseText).isHaveRecord;
		        		    		if(isHaveRecord == "yes"){
		        		    			Ext.Msg.alert("警告！","<font color=red>首页已设定，继续将覆盖原首页</font>");
		        		    		}
		        				}
		        			});
		        		}
                  	},
                    anchor:"90%"//330px-labelWidth剩下的宽度的95%，留下5%作为后面提到的验证错误提示
	        	}]
        }]
    });
    var homePageItem = Ext.getCmp('isHomePage');
    var name = Ext.getCmp('nameId');
    
    var win = new Ext.Window({
        title: '创建视图',
        modal:true,
        width: 320,
        height:230,
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
            /* 判断视图名称是否含有中文字符 
            var reg = /[\u2e80-\u9fff]/;
       		if (reg.test(Ext.get('nameId').getValue())) {
       			alert("视图名称不能含有中文字符！");
       			return;
       		}*/
            
            if(MsgForm.form.isValid()){
            		MsgForm.form.doAction('submit',{
            			url: 'json/viewAdd.do',
     					method: 'post',
     					waitTitle:"提示",
     					waitMsg:"<font color='black'>正在发送，请稍后......</font>",
     					success:function(form,action){
     						win.close();
      						choseDevices(action.result.data);
     					},
     					failure:function(form,action){
     						Ext.Msg.alert("操作失败!","<font color='black'>"+action.result.data+"</font>");
     					}
        			}); 
        		}
            }
        },{
            text: '关闭',
            handler  : function(){
            	win.close();
            	//应该加一个页面刷新...
            }
        }]
    });
    win.show();
}
function choseDevices(viewName) {
	var treeDiv = Ext.get("tree");
	if(treeDiv==null){
		var addDIV = document.createElement("div");
		addDIV.id = "tree";
		addDIV.style.width = "300px";
        addDIV.style.height = "450px";
        addDIV.style.textAlign = "left";
        document.body.appendChild(addDIV);
	}
	var tree2Div = Ext.get("tree2");
	if(tree2Div==null){
		var addDIV = document.createElement("div");
		addDIV.id = "tree2";
		addDIV.style.width = "300px";
        addDIV.style.height = "450px";
        addDIV.style.textAlign = "left";
        document.body.appendChild(addDIV);
	}
    var Tree = Ext.tree;
    var tree = new Tree.TreePanel({
        el:'tree',
        animate:true,
        autoScroll:true,
        width:390,
        height:217,
        loader: new Tree.TreeLoader({dataUrl:'totree.do'}),
        enableDD:true,
        containerScroll: true
    });
    
    tree.on("nodedrop", function(e){
    	e.point = "above";
    });
    
    // add a tree sorter in folder mode
    new Tree.TreeSorter(tree, {folderSort:true});
    // set the root node
    var root = new Tree.AsyncTreeNode({
        text: 'selecting device', 
        draggable:false, // disable root node dragging
        id:'source'
    });
    tree.setRootNode(root);
    tree.render(); // render the tree
    root.expand(true, true);
    
    var tree2 = new Tree.TreePanel({
        el:'tree2',
        animate:true,
        autoScroll:true,
        width:390,
        height:217,
        //rootVisible: false,
        loader: new Ext.tree.TreeLoader({
            dataUrl:'tonulltree.do',
            baseParams: {lib:'yui'} // custom http params
        }),
        containerScroll: true,
        enableDD:true
    });
    
    tree2.on("nodedrop", function(e){
    	e.point = "above";
    });
    
    // add a tree sorter in folder mode
    new Tree.TreeSorter(tree2, {folderSort:true});
    // add the root node
    var root2 = new Tree.AsyncTreeNode({
        text: 'selected device', 
        draggable:false, 
        id:'chosed'
    });
    tree2.setRootNode(root2);
    tree2.render();
    root2.expand(true, true);
    
   var chosingDevice = new Ext.Panel({
   		id:'chosing',
   		contentEl:"tree",
        title: '可选设备',
        region: 'west',
        split: true,
        autoScroll:false,
        width: 400,
        collapsible: true,
        margins:'3 0 3 3',
        cmargins:'3 3 3 3'       
    });   
    var chosedDevice = new Ext.Panel({
    	id:'chosed',
    	contentEl:"tree2",
        title: '已选设备',
        region: 'center',
        split: true,
        autoScroll:false,
        width: 400,
        collapsible: true,
        margins:'3 0 3 3',
        cmargins:'3 3 3 3'
    });
    var checkid = new Array();
    var checkText = new Array();
	function mytoggleChecked(node){
		checkid = new Array();
    	checkText = new Array();
    	if(node.hasChildNodes){
    		node.eachChild(function(child){
				if(child.isLeaf()){
					checkid.push(child.id);
					checkText.push(child.text);
				}else{
    				mytoggleChecked(child);
				}
    		});
    	}
    }
	var win = new Ext.Window({
        title: '为该视图选择设备',
        width: 800,
        height:320,
        closable :true,
        resizable:false,
        x:270,
        y:150,
        layout: 'border',
        plain: true,
        bodyStyle:'padding:5px;color:black;',
        buttonAlign:'center',
        items:[chosingDevice, chosedDevice],
        buttons: [{
            text: '保存',
            handler  : function(){
	       		mytoggleChecked(root2);
		       	Ext.Ajax.request({
					url : 'json/deviceChose.do',
					disableCaching : true,
					params : {
						viewName : viewName,
						checkId : checkid,
						checkText:checkText
					},
					method : 'post',				
					success : function(result, request) {
						//document.location.reload(); 
						//修改声明（赵一）：为给下拉菜单中的添加视图增加动作，因此将此处改为重定向
						document.location.href='manage.do';
						//直接跳转至视图管理页面
					},
					failure : function(result, request) {
						Ext.Msg.alert("为视图选择设备时有错误发生，错误内容为：","<font color='black'>"+result.responseText+"</font>");
					}
				});
           }
        }]
    });
    win.show();
}


//添加设备分类
function addDeviceType() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
    var MsgForm = new Ext.form.FormPanel({
		baseCls: 'x-plain',
        labelAlign: 'left',
    	buttonAlign: 'right',
    	frame: true,
    	labelWidth: 55,
    	defaultType: 'textfield',
    	defaults: {
    		allowBlank: false
    	},
        items: [{
        	xtype:'fieldset',
        	title:'分类信息',
        	collapsible:true,
        	width:300,
        	height:120,
        	defaults:{width:200,height:30},
        	defaultType:'textfield',
        	items:[{
        		fieldLabel:'名称',
        		id:'classNameId',
        		name:'name',
        		allowBlank: false,
	        	blankText:'设备类型名称不能为空!',
                listeners: {
                       blur  : function() {
		        			Ext.Ajax.request({
		        				url : 'json/checkDeviceName.do',
		        				method : 'post',		
		        				params : {name:Ext.get('classNameId').getValue()},
		        				success : function(result, request) {
		        		    		var isHaveRecord = Ext.decode(result.responseText).isHaveRecord;
		        		    		if(isHaveRecord == "yes"){
		        		    			Ext.Msg.alert("警告！","<font color=red>设备类型名称重复</font>");
		        		    		}
		        				}
		        			});
		        		}
                  	}
        		},{
        		fieldLabel:'描述',
        		name:'description'
        	}]
        }]
    });
    
    //MsgForm.render(document.body);
    var win = new Ext.Window({
        title: '添加分类',
        width: 350,
        height:200,
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
            if(MsgForm.form.isValid()){
            		MsgForm.form.doAction('submit',{            		
            			url: 'json/addDeviceType.do',
     					method: 'post',
     					waitTitle: "提示",
     					waitMsg: "<font color='black'>正在发送,请稍候...</font>",
     					success:function(form,action)
     					{
      						Ext.Msg.alert("操作成功!","<font color='black'>"+action.result.data+"</font>");
      						win.close();
      						window.location.href = "deviceClass.do";
     					},
     					failure:function(form,action){
     						Ext.Msg.alert("操作失败!","<font color='black'>"+action.result.data+"</font>");
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
