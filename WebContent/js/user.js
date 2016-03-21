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
var userId = "";
var win = "1";
Ext.onReady( function() {
	Ext.QuickTips.init();

	sm = new Ext.grid.CheckboxSelectionModel();

	var Record = new Ext.data.Record.create( [ {
		name : "id",
		mapping : "id"
	}, {
		name : "name",
		mapping : "name"
	}, {
		name : "password",
		mapping : "password"
	}, {
		name : "realName",
		mapping : "realName"
	}, {
		name : "email",
		mapping : "email"
	}, {
		name : "telephone",
		mapping : "telephone"
	}, {
		name : "department",
		mapping : "department.name"
	}, {
		name : "manager"
	} ]);

	var reader = new Ext.data.JsonReader( {
		root : "users",
		totalProperty : 'totalCount'
	}, Record);

	var proxy = new Ext.data.HttpProxy( {
		url : "listUsers.do"
	});

	var Department = new Ext.data.Record.create( [ {
		name : "id",
		mapping : "id"
	}, {
		name : "name",
		mapping : "name"
	} ]);

	var dd = new Ext.data.JsonReader( {
		root : "departments"
	}, Department);

	var pp = new Ext.data.HttpProxy( {
		url : "listAllDepartment.do"
	});

	var dStore = new Ext.data.Store( {
		proxy : pp,
		reader : dd
	});

	dStore.load();

	var combo = new Ext.form.ComboBox( {
		store : dStore,
		displayField : 'name',
		valueField : 'id',
		mode : 'local',
		typeAhead : true, //自动将第一个搜索到的选项补全输入
		triggerAction : 'all',
		emptyText : '全部',
		selectOnFocus : true,
		forceSelection : true
	})

	var colm = new Ext.grid.ColumnModel( [ {
		header : "用户id",
		dataIndex : "id",
		sortable : true
	}, {
		id:"name",
		header : "用户名称",
		dataIndex : "name",
		editor : new Ext.form.Field(),
		sortable : true
	}, {
		header : "用户密码",
		dataIndex : "password",
		renderer : returnValue,
		editor : new Ext.form.Field( {
			inputType : 'password'
		}),
		sortable : true
	}, {
		header : "真实姓名",
		dataIndex : "realName",
		editor : new Ext.form.Field(),
		sortable : true
	}, {
		header : "邮箱",
		dataIndex : "email",
		editor : new Ext.form.Field(),
		sortable : true
	}, {
		header : "联系电话",
		dataIndex : "telephone",
		editor : new Ext.form.Field(),
		sortable : true
	}, {
		header : "部门",
		dataIndex : "department",
		editor : combo,
		sortable : true
	}, {
		header : "权限管理",
		renderer : uerGroupManager
	},sm]);

	store = new Ext.data.Store( {
		proxy : proxy,
		reader : reader
	});

	store.load( {
		params : {
			start : 0,
			limit : 28
		}
	});
	
	var grid = new Ext.grid.EditorGridPanel( {
		title:"用户信息",
		store : store,
		height : document.body.clientHeight*.95+10,
		autoExpandColumn: 'name',
		width : 840,
		cm : colm,
		autoScroll : true,
		renderTo : "showDiv",
		sm : sm,
		tbar : [ '->', {
			id : "addRecord",
			text : "增加用户",
			handler : function() {
			
				var combo2 = new Ext.form.ComboBox( {
					fieldLabel : '部门',
					store : dStore,
					displayField : 'name',
					valueField : 'id',
					mode : 'local',
					value : "",
					typeAhead : true, //自动将第一个搜索到的选项补全输入
					triggerAction : 'all',
					emptyText : '请选择部门',
					selectOnFocus : true,
					forceSelection : true,
					allowBlank:false,
					blankText:"不能为空！",
					listeners : {
						select : function(combo2, record, index) {
							combo2.value = record.get("id");
						}
					}
				});
			
				var simple = new Ext.FormPanel( {
					labelWidth : 75, // label settings here cascade unless overridden
					frame : true,
					title : '',
					bodyStyle : 'padding:5px 5px 0',
					width : 600,
					defaults : {
						width : 500
					},
					defaultType : 'textfield',

					items : [ {
						fieldLabel : '用户名称',
						name : 'user.name',
						allowBlank:false,
						blankText:"不能为空！"
					}, {
						fieldLabel : '用户密码',
						inputType : 'password',
						allowBlank:false,
						minLength:6,
						minLengthText:"密码位数不能少于 6 位！",
						maxLength:24,
						maxLengthText:"密码位数不能多于 24 位！",
						blankText:"不能为空！",
						name : 'user.password'
					}, {
						fieldLabel : '真实姓名',
						allowBlank:false,
						blankText:"不能为空！",
						name : 'user.realName'
					}, {
						fieldLabel : '邮箱',
						value:'该邮箱主要用于接收告警邮件，请输入合法邮件地址！',
						allowBlank:true,
						//blankText:"不能为空！",
						vtype:"email",
						//vtypeText:"邮箱格式无效！",
						name : 'user.email'
					}, {
						fieldLabel : '固话/手机',
						//regex:/^\d{3}-\d{8}$|^\d{4}-\d{7}$|^0?\d{11}$/,
						//regexText:"电话号码输入格式不对，请认真输入，座机格式为：区号-号码。",
						value:"手机号码主要用于接收告警短信，请输入合法的手机号；若输入座机，格式为：区号-号码",
						//allowBlank:true,
						//blankText:"不能为空！",
						name : 'user.telephone'

					}, combo2],

					buttons : [ {
						text : '  保存      ',
						handler : function() {
							if (simple.getForm().isValid()){
								simple.getForm().submit({
									url:"addUserByForm.do?departmentId=" + combo2.getValue(),
									success:function(form, action){
										myWin.close();
								    	store.reload();
									}, 
									failure:function(form, action){
										myWin.close();
								    	store.reload();
									}
								});
							}
						}
					}]
				});
				
				var myWin = new Ext.Window( {
					width : 620,
					height : 255,
					layout : 'fit',
					plain : true,
					frame : true,
					title : '用户信息',
					bodyStyle : 'padding:5px 5px 0',
					buttonAlign : 'center',
					items : [simple]
				});
				myWin.show();
			}
		},'->','-', {
			id : "deleteButton",
			text : "删除",
			handler : function() {
				var ids = "";
				var record = "";
				var selected = sm.getSelections();
				if (selected.length == 0) {
					alert("请选择用户！");
					return;
				}
				var isDelete = confirm("确定要删除吗？");
				if (isDelete) {
					for ( var i = 0; i < selected.length; i++) {
						record = selected[i];
						var data = selected[i].data;
						ids += data.id;
						ids += ";";
					}
	
					Ext.Ajax.request( {
						url : "deleteUser.do",
						params : {
							userIds : ids
						},
						success : function(response, request) {
							if (response.responseText == "ok") {
								for ( var i = 0; i < selected.length; i++) {
									record = selected[i];
									store.remove(record);
								}
								alert("删除成功！");
							} else {
								alert("删除失败! 你没有操作权限");
							}
						}
					});
				}
			}
		} ],
		bbar : new Ext.PagingToolbar( {
			pageSize : 28,
			store : store,
			displayInfo : true,
			displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg : '没有数据'
		})
	});

	grid.on("afteredit", afterEdit, grid);
});

function afterEdit(obj) {
	var r = obj.record;// 获取被修改的行
	var l = obj.field;// 获取被修改的列
	var userId = r.get("id");
	var value = r.get(l);

	if (l == "name" && value == "") {
		alert("姓名不能为空！");
		return;
	}

	if (l == "password" && value  == "") {
		alert("请填写密码，密码不能为空！");
		return;
	} else if (l == "password" && value .length < 6 || value.length > 20) {
		alert("密码长度在 6位 -- 20位 之间，请认真填写！");
		return;
	}

	if (l == "email" && value  != "") {
		var emailReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		if (!emailReg.test(value)) {
			alert("邮箱格式不对请认真填写!");
			return;
		}
	}

	if (l == "telephone" && value  != "") {
		var tpReg = /^\d{3}-\d{8}$|^\d{4}-\d{7}$|^0?\d{11}$/;
		if (!tpReg.test(value)) {
			alert("电话号码输入格式不对，请认真输入，座机格式为：区号-号码。");
			return;
		}
	}

	Ext.Ajax.request( {
		url : "modifyUser.do",
		params : {
			userId : userId,
			paramName:l,
			paramValue:value
		},
		success : function(response, request) {
			//后台执行完成后重新加载 数据 
		if (response.responseText == "same") {
			alert("该用户名已经存在！");
			store.reload();
		} else if (response.responseText == "ok") {
			store.reload();
		} else {
			alert("您没有权限修改或添加！");
			store.reload();
		}
	}
	});
}

//添加用户组管理链接
function uerGroupManager() {
	return "<a href ='javascript:choseDevices()'>为用户分配角色</a>";
}

//添加用户组
function choseDevices() {

	if (win != "1") {
		win.close();
	}

	var treeDiv = Ext.get("tree");

	if (treeDiv == null) {
		var addDIV = document.createElement("div");
		addDIV.id = "tree";
		addDIV.style.width = "300px";
		addDIV.style.height = "450px";
		document.body.appendChild(addDIV);
	} else {
		treeDiv = treeDiv.dom;
		treeDiv.style.width = "300px";
		treeDiv.style.height = "450px";
	}
	var tree2Div = Ext.get("tree2");
	
	if (tree2Div == null) {
		var addDIV = document.createElement("div");
		addDIV.id = "tree2";
		addDIV.style.width = "300px";
		addDIV.style.height = "450px";
		document.body.appendChild(addDIV);
	} else {
		tree2Div = tree2Div.dom;
		tree2Div.style.width = "300px";
		tree2Div.style.height = "450px";
	}

	var selected = sm.getSelections();
	var record = "";
	for ( var i = 0; i < selected.length; i++) {
		record = selected[i];
		var data = selected[i].data;
		userId = data.id;
	}

	var Tree = Ext.tree;
	var tree = new Tree.TreePanel( {
		el : 'tree',
		animate : true,
		autoScroll : true,
		layout:"fit",
		style:"text-align:left",
		width:197,
		height:200,
		loader : new Tree.TreeLoader( {
			dataUrl : 'userGroupTreeNS.do?userId=' + userId
		}),
		enableDD : true,
		containerScroll : true
	});
	
	tree.on("nodedrop", function(e) {
		e.point = "above";
	});
	
	// add a tree sorter in folder mode
	new Tree.TreeSorter(tree, {
		folderSort : true
	});
	// set the root node
	var root = new Tree.AsyncTreeNode( {
		text : '待选角色',
		draggable : false, // disable root node dragging
		id : 'source'
	});

	tree.setRootNode(root);
	tree.render(); // render the tree
	root.expand(true, true);

	var tree2 = new Tree.TreePanel( {
		el : 'tree2',
		animate : true,
		layout:"fit",
		width:190,
		style:"text-align:left",
		height:200,
		autoScroll : true,
		loader : new Tree.TreeLoader( {
			dataUrl : 'userGroupTreeSelect.do?userId=' + userId
		}),
		containerScroll : true,
		enableDD : true
	});
	
	tree2.on("nodedrop", function(e) {
		e.point = "above";
	});
	
	// add a tree sorter in folder mode

	new Tree.TreeSorter(tree2, {
		folderSort : true,
		layout:"fit"
	});
	// add the root node
	var root2 = new Tree.AsyncTreeNode( {
		text : '已选角色',
		draggable : false,
		id : 'chosed'
	});
	tree2.setRootNode(root2);

	tree2.render();
	root2.expand(true, true);
	var chosingDevice = new Ext.Panel( {
		id : 'chosing',
		contentEl : "tree",
		title : '所有待选角色',
		region : 'west',
		split : true,
		width : 200,
		collapsible : true,
		margins : '3 0 3 3',
		cmargins : '3 3 3 3'
	});
	var chosedDevice = new Ext.Panel( {
		id : 'chosed',
		contentEl : "tree2",
		title : '已选角色',
		region : 'center',
		split : true,
		width : 220,
		collapsible : true,
		margins : '3 0 3 3',
		cmargins : '3 3 3 3'
	});
	win = new Ext.Window( {
		title : '为该用户选择角色',
		width : 420,
		height : 300,
		layout : 'border',
		resizable :false,
		plain : true,
		bodyStyle : 'padding:5px;color:black;',
		buttonAlign : 'center',
		items : [ chosingDevice, chosedDevice ],
		buttons : [ {
			text : '保存',
			handler : function() {
				var checkid = new Array();
				if (root2.hasChildNodes) {
					root2.eachChild( function(child) {
						if (child.isLeaf()) {
							checkid.push(child.id);
						}
					});
				}
				Ext.Ajax.request({
					url : 'addUserPopedom.do',
					disableCaching : true,
					params : {
						userId : userId,
						groupIds : checkid.join(";")
					},
					method : 'post'
				});
				win.close();
			}
		} ]
	});
	win.show();
}


/*  */
function returnValue() {
	return "******";
}
