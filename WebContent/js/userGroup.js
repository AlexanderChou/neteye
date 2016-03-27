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

var store = "";
var groupId = "";
var win = "";
Ext.onReady(function(){
	Ext.QuickTips.init();
	
	sm = new Ext.grid.CheckboxSelectionModel();

	var Record = new Ext.data.Record.create([
		{name:"id", mapping:"id"},
		{name:"name", mapping:"name"},
		{name:"subtxt", mapping:"subtxt"},
		{name:"manage"}
	]);
	
	var reader = new Ext.data.JsonReader({
		root:"userGroups",
		totalProperty:'totalCount'
		},
		Record
	    );
	
	var proxy = new Ext.data.HttpProxy({
		url:"listAllUserGroup.do"
	});

	var colm = new Ext.grid.ColumnModel([
		{header:"角色id", dataIndex:"id",width:50,sortable:true},
		{id:"userGroupName", header:"角色名称", dataIndex:"name",editor:new Ext.form.Field(),width:120,sortable:true},
		{id:"userGroupNamesubtxt", header:"角色描述", dataIndex:"subtxt",editor:new Ext.form.Field(),width:190,sortable:true},
		{header:"权限管理", renderer:assignResource,width:250,sortable:true},
		sm
	]);
	
	store = new Ext.data.Store({
		proxy:proxy,
		reader:reader
	});
	
	store.load({params:{start:0,limit:28}});
	
	var grid = new Ext.grid.EditorGridPanel({
		title:"角色信息",
		store:store,
		autoExpandColumn: 'userGroupName',
		height:document.body.clientHeight* 0.95 + 10,
		width:840,
		cm:colm,
		autoScroll:true,
		renderTo:"showDiv",
		sm:sm,
		tbar:['->', {
			id:"addRecord",
			text:"添加角色",
			handler:function(){
				/*var myRecourd = new Record({
					name:""
				});
				store.add(myRecourd);*/
				var simple = new Ext.FormPanel( {
					labelWidth : 75, // label settings here cascade unless overridden
					frame : true,
					title : '',
					bodyStyle : 'padding:5px 5px 0',
					width : 400,
					defaults : {
						width : 230
					},
					defaultType : 'textfield',

					items : [ {
						fieldLabel : '角色名称',
						name : 'userGroup.name',
						allowBlank:false,
						blankText:"不能为空！"
					},{
						fieldLabel : '角色描述',
						name : 'userGroup.subtxt',
						allowBlank:false,
						blankText:"不能为空！"
					}],

					buttons : [ {
						text : '  保存      ',
						handler : function() {
							if (simple.getForm().isValid()){
								simple.getForm().submit({
									url:"addUserGroupByForm.do",
									success:function(form, action){
										myWin.close();
								    	store.reload();
									}, 
									failure:function(form, action){
										alert("该角色名称已经存在，请重新添加！");
									}
								});
							}
						}
					}]
				});
				
				var myWin = new Ext.Window( {
					width : 400,
					height : 150,
					layout : 'fit',
					plain : true,
					frame : true,
					title : '角色信息',
					bodyStyle : 'padding:5px 5px 0',
					buttonAlign : 'center',
					items : [simple]
				});
				myWin.show();
				
			}
		},'-', {
			id:"deleteButton",
			text:"删除",
			handler:function(){
				var selected = sm.getSelections();
					var ids = "";
					var record = "";
					if (selected.length == 0) {
						alert("请选择角色！");
						return;
				}
				var isDelete = confirm("确定要删除吗？");
				if (isDelete) {
					for(var i = 0; i < selected.length; i++) {
						record = selected[i];
						var data = selected[i].data;
						store.remove(record);
						ids += data.id;
						ids += ";";
					}
					
					Ext.Ajax.request({
						url:"deleteUserGroup.do",
						params:{
							userGroupIds:ids
						},
						success:function(response, request){
							if (response.responseText == "ok") {
								alert("删除成功！");
							} else {
								alert("删除失败! 你没有操作权限");
							}
						}
					});
				}
			}
		}],
		bbar:new Ext.PagingToolbar({
			pageSize:28,
			store:store,
			displayInfo:true,
			displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg:'没有数据'
		})
	});
	grid.on("afteredit", afterEdit, grid); 
});

function assignResource(){
	return "<a href='javascript:choseDevices()'>为该角色分配资源</a>";
}

function afterEdit(obj) {
	var r = obj.record;//获取被修改的行
	var l = obj.field;//获取被修改的列
	var id = r.get("id");
    var name = r.get("name");
    var subtxt = r.get("subtxt");
    
    if (name == "") {
    	alert("角色名称不能为空！");
    	return;
    }
    if (l=="subtxt"){
    	
    			Ext.Ajax.request({
					url:"addUserGroup.do",
					params:{
							userGroupId:id,
							userGroupName:name,
							userGroupNamesubtxt:subtxt
						},
					success:function(response,request){
						//后台执行完成后重新加载 数据 
						if (response.responseText == "ok") {
							store.reload();				
						} else {
							alert("您没有权限修改或添加！");
							store.reload();
						}
					}
				});
    		
    }else{
    /* 检验名字是否已经存在 */
    Ext.Ajax.request({
    	url:"checkUserGroupNameIsHave.do?userGroupName=" + name,
    	success:function(response, request){
    		var jsonstr = eval("(" + response.responseText + ")");
    		if (jsonstr.have) {
    			alert("该角色名称已经被使用!");
    			return;
    		} else {
    			Ext.Ajax.request({
					url:"addUserGroup.do",
					params:{
							userGroupId:id,
							userGroupName:name,
							userGroupNamesubtxt:subtxt
						},
					success:function(response,request){
						//后台执行完成后重新加载 数据 
						if (response.responseText == "ok") {
							store.reload();				
						} else {
							alert("您没有权限修改或添加！");
							store.reload();
						}
					}
				});
    		}
    	}
    });
    
    }
}

//添加用户组
function choseDevices() {
	if (win != "") {
		win.close();
	}

	var treeDiv = Ext.get("tree");
	if(treeDiv==null){
		var addDIV = document.createElement("div");
		addDIV.id = "tree";
		addDIV.style.width = "300px";
        addDIV.style.height = "450px";
        document.body.appendChild(addDIV);
	}
	var tree2Div = Ext.get("tree2");
	if(tree2Div==null){
		var addDIV = document.createElement("div");
		addDIV.id = "tree2";
		addDIV.style.width = "300px";
        addDIV.style.height = "450px";
        document.body.appendChild(addDIV);
	}
    var Tree = Ext.tree;
    var tree = new Tree.TreePanel({
        el:'tree',
        animate:true, 
        autoScroll:true,
        style:"text-align:left",
		width:197,
		height:200,
        enableDD:true,
        containerScroll: true
    });
    
    tree.on("nodedrop", function(e) {
		e.point = "above";
	});
    
    // add a tree sorter in folder mode
    new Tree.TreeSorter(tree, {folderSort:true});
    // set the root node
    var root = new Tree.TreeNode({
        text: '待选资源', 
        draggable:false, // disable root node dragging
        id:'source'
    });
    
    var selected = sm.getSelections();
	var record = "";
	for(var i = 0; i < selected.length; i++) {
		record = selected[i];
		var data = selected[i].data;
		groupId =  data.id;
	}
    
    Ext.Ajax.request({
    	url:"listAllResourceGroup.do?userGroupId=" + groupId,
    	success:function(response, request){
    		var json = eval("(" + response.responseText + ")");
    		var arr = json["resourceGroups"];
    		for (var i = 0; i < arr.length; i++ ) {
    			var element = new Ext.tree.TreeNode({
    				id:arr[i].id,
			    	text:arr[i].name,
			    	leaf:true
    			});
    			root.appendChild(element);
    		}
    	}
    });
    tree.setRootNode(root);
    tree.render(); // render the tree
    root.expand(true, true);
    
    var tree2 = new Tree.TreePanel({
        el:'tree2',
        animate:true,
        autoScroll:true,
        style:"text-align:left",
		width:197,
		height:200,
        containerScroll: true,
        enableDD:true
    });
    
    tree2.on("nodedrop", function(e) {
		e.point = "above";
	});
    
    // add a tree sorter in folder mode
    new Tree.TreeSorter(tree2, {folderSort:true});
    // add the root node
    var root2 = new Tree.TreeNode({
        text: '已选资源', 
        draggable:false, 
        id:'chosed'
    });
    tree2.setRootNode(root2);
    
    Ext.Ajax.request({
    	url:"listResourceByUserGroup.do?userGroupId=" + groupId,
    	success:function(response, request){
    		var json = eval("(" + response.responseText + ")");
    		var arr = json["resourceGroups"];
    		for (var i = 0; i < arr.length; i++ ) {
    			var element = new Ext.tree.TreeNode({
    				id:arr[i].id,
			    	text:arr[i].name,
			    	leaf:true
    			});
    			root2.appendChild(element);
    		}
    	}
    });
    
    tree2.render();
    root2.expand(true, true);
   var chosingDevice = new Ext.Panel({
   		id:'chosing',
   		contentEl:"tree",
        title: '待选资源',
        region: 'west',
        split: true,
        width: 200,
        collapsible: true,
        margins:'3 0 3 3',
        cmargins:'3 3 3 3'       
    });   
    var chosedDevice = new Ext.Panel({
    	id:'chosed',
    	contentEl:"tree2",
        title: '已选资源',
        region: 'center',
        split: true,
        width: 220,
        collapsible: true,
        margins:'3 0 3 3',
        cmargins:'3 3 3 3'
    });
	win = new Ext.Window({
        title: '为角色分配资源',
        width: 420,
        height:300,
        resizable :false,
        layout: 'border',
        plain: true,
        bodyStyle:'padding:5px;color:black;',
        buttonAlign:'center',
        items:[chosingDevice, chosedDevice],
        buttons: [{
            text: '保存',
            handler  : function(){
	       		var checkid = new Array();
	       		if(root2.hasChildNodes){
    				root2.eachChild(function(child){
						if(child.isLeaf()){
							checkid.push(child.id);
						}
    				});
    			}
		       	Ext.Ajax.request({
					url : 'assignPopedom.do',
					disableCaching : true,
					params : {
						userGroupId : groupId,
						resourceGroupIds:checkid.join(";")
					},
					method : 'post'
				});
				win.close();
           }
        }]
    });
    win.show();
}