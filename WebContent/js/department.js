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
Ext.onReady(function(){
	Ext.QuickTips.init();
	
	var sm = new Ext.grid.CheckboxSelectionModel();

	var Record = new Ext.data.Record.create([
		{name:"id", mapping:"id"},
		{name:"name", mapping:"name"},
		{name:"subtxt", mapping:"subtxt"}
	]);
	
	var reader = new Ext.data.JsonReader({
		root:"departments",
		totalProperty:'totalCount'
		},
		Record
	    );
	
	var proxy = new Ext.data.HttpProxy({
		url:"listDepartment.do"
	});

	var colm = new Ext.grid.ColumnModel([
		{header:"部门id", dataIndex:"id",sortable:true},
		{id:"department", header:"部门名称", dataIndex:"name",editor:new Ext.form.Field(),sortable:true},
		{id:"departmenttxt", header:"部门简介", dataIndex:"subtxt",editor:new Ext.form.Field(),sortable:true},
		sm
	]);
	
	store = new Ext.data.Store({
		proxy:proxy,
		reader:reader
	});
	
	store.load({params:{start:0,limit:28}});
	var grid = new Ext.grid.EditorGridPanel({
		title:"部门信息",
		store:store,
		height:document.body.clientHeight* 0.95 + 10,
		width:840,
		cm:colm,
		autoExpandColumn: 'department',
		autoScroll:true,
		renderTo:"showDiv",
		sm:sm,
		tbar:["->", {
			id:"addRecord",
			text:"增加部门",
			handler:function(){
				/*var myRecourd = new Record({
					name:""
				});
				store.add(myRecourd);*/
				var simple = new Ext.FormPanel( {
					labelWidth : 75, // label settings here cascade unless overridden
					frame : true,
					bodyStyle : 'padding:5px 5px 0',
					width : 400,
					defaults : {
						width : 230
					},
					defaultType : 'textfield',
					items : [ {
						fieldLabel : '部门名称',
						name : 'department.name',
						allowBlank:false,
						blankText:"不能为空！"
					},{
						fieldLabel : '部门简介',
						name : 'department.subtxt',
						allowBlank:false,
						blankText:"不能为空！"
					}],

					buttons : [ {
						text : '  保存       ',
						handler : function() {
							if (simple.getForm().isValid()) {
								simple.getForm().submit({
									url:"addDepartmentbyForm.do",
									success:function(form, action){
										myWin.close();
								    	store.reload();
									}, 
									failure:function(form, action){
										alert("该部门名已经存在，请重新添加！");
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
					title : '部门信息',
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
					alert("请选择部门！");
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
						url:"deleteDepartment.do",
						params:{
							departmentIds:ids
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
			pageSize:13,
			store:store,
			displayInfo:true,
			displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg:'没有数据'
		})
	});
	grid.on("afteredit", afterEdit, grid); 
});

function afterEdit(obj) {
	var r = obj.record;	//获取被修改的行
	var l = obj.field;	//获取被修改的列
	var id = r.get("id");
    var name = r.get("name");
    var subtxt = r.get("subtxt");
    if (!isNaN(name)) {
    	alert("部门名字不能全是数字！");
    	return;
    } 
    if (l=="subtxt"){
    	
    			Ext.Ajax.request({
					url:"addDepartment.do",
					params:{
							departmentId:id,
							departmentName:name,
							departmentSubtxt:subtxt
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
    /* 校验部门名字是否相同 */
    Ext.Ajax.request({
    	url:"checkDepartmentName.do?departmentName=" + name,
    	success:function(response, request){
    		var jsonstr = eval("(" + response.responseText + ")");
    		if(jsonstr.have) {
    			alert("对不起，该部门名已经使用！");
    			return ;
    		} else {
    			Ext.Ajax.request({
					url:"addDepartment.do",
					params:{
							departmentId:id,
							departmentName:name,
							departmentSubtxt:subtxt
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

