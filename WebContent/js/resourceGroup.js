var store = "";
var resourceGroupWin = "";
function assignResource(groupId){
	//return "<a href='javascript:addResource("+groupId+")'>为该资源组分配资源</a>";
	return "<img onclick='addResource("+groupId+")' src='images/assign.gif' >";
}
Ext.onReady(function(){
	Ext.QuickTips.init();
	
	var sm = new Ext.grid.CheckboxSelectionModel();

	var Record = new Ext.data.Record.create([
		{name:"id", mapping:"id"},
		{name:"name", mapping:"name"},
		{name:"description", mapping:"description"}
	]);
	
	var reader = new Ext.data.JsonReader({
		root:"resourceGroups",
		totalProperty:'totalCount'
		},
		Record
	    );
	
	var proxy = new Ext.data.HttpProxy({
		url:"allResourceGroup.do"
	});

	var colm = new Ext.grid.ColumnModel([
		{id:"department", header:"资源组名称", dataIndex:"name",editor:new Ext.form.Field(),sortable:true},
		{header:"资源组描述", dataIndex:"description",editor:new Ext.form.Field(),sortable:true},
		{header:"权限管理", dataIndex:"id",renderer : assignResource,sortable:true},
		sm
	]);
	
	store = new Ext.data.Store({
		proxy:proxy,
		reader:reader
	});
	
	store.load({params:{start:0,limit:28}});
	var grid = new Ext.grid.EditorGridPanel({
		title:"资源信息",
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
			text:"增加资源组",
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
						fieldLabel : '资源组名称',
						name : 'resourceGroup.name',
						allowBlank:false,
						blankText:"不能为空！"
					},{
						fieldLabel : '资源组描述',
						name : 'resourceGroup.description',
						allowBlank:false,
						blankText:"不能为空！"
					}],

					buttons : [ {
						text : '  保存       ',
						handler : function() {
							if (simple.getForm().isValid()) {
								simple.getForm().submit({
									url:"addResourceGroup.do",
									success:function(form, action){
										myWin.close();
								    	store.reload();
									}, 
									failure:function(form, action){
										alert("资源组名称已经存在，请重新添加！");
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
					title : '资源组信息',
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
				var ids = new Array();
				var record = "";
				if (selected.length == 0) {
					alert("请选择资源组！");
					return;
				}
				var isDelete = confirm("确定要删除吗？");
				if (isDelete) {
					for(var i = 0; i < selected.length; i++) {
						record = selected[i];
						var data = selected[i].data;
						store.remove(record);
						ids.push(data.id);
					}
					
					Ext.Ajax.request({
						url:"deleteResourceGroup.do",
						params:{
							ids:ids
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
    var description = r.get("description");
    if (!isNaN(name)) {
    	alert("资源组名字不能全是数字！");
    	return;
    } 
    if (l=="description"){
    	
    			Ext.Ajax.request({
					url:"addresourceGroup.do",
					params:{
							resourceGroupId:id,
							resourceGroupName:name,
							description:description
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
    	url:"checkesourceGroupName.do?resourceGroupName=" + name,
    	success:function(response, request){
    		var jsonstr = eval("(" + response.responseText + ")");
    		if(jsonstr.have) {
    			alert("对不起，该资源组名已经使用！");
    			return ;
    		} else {
    			Ext.Ajax.request({
					url:"addresourceGroup.do",
					params:{
							resourceGroupId:id,
							resourceGroupName:name,
							description:description
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

function addResource(value){
	if(userName=='admin'){
		if (resourceGroupWin != "") {
			resourceGroupWin.close();
		}
		var treeDiv = Ext.get("tree");
		if(treeDiv==null){
			var addDIV = document.createElement("div");
			addDIV.id = "tree";
			addDIV.style.width = "400px";
	        addDIV.style.height = "650px";
	        document.body.appendChild(addDIV);
		}
	
		var Tree = Ext.tree;
	    var tree = new Tree.TreePanel({
	        el:'tree',
	        animate:true, 
	        autoScroll:true,
	        loader: new Tree.TreeLoader({dataUrl:'toUserChose.do?groupId='+value}),
	        enableDD:true,
	        containerScroll: true
	    });
	    
	    new Tree.TreeSorter(tree, {folderSort:true});
	    
	    var root = new Tree.AsyncTreeNode({
	        text: '资源列表', 
	        draggable:false, // disable root node dragging
	        checked:false,
	        id:'source'
	    });
	    tree.setRootNode(root);
	    tree.render(); // render the tree
	    root.expand(true, true);	    
	    tree.on('checkchange', function(node, checked) {        
			mytoggleChecked(node);
		}, tree);	    
	    function mytoggleChecked(node){
	    	if(node.hasChildNodes){
	    		node.eachChild(function(child){
	    			child.getUI().toggleCheck(node.attributes.checked);
	    			child.attributes.checked = node.attributes.checked;
	    			child.on("checkchange",function(sub){
	    				mytoggleChecked(sub);
	    			});
	    			mytoggleChecked(child);
	    		});
	    	}
	    };
	  	resourceGroupWin = new Ext.Window({
			id:'resourceAssignPanel',
	        title: '分配资源',
	        contentEl:"tree",
	        width: 430,
	    //    height: 450,
	        autoScroll:true,
	        resizable:true,
	        x:270,
	        y:0,
	        plain: true,
	        bodyStyle:'padding:5px;color:black;',
	        buttonAlign:'center',
	        buttons: [{
	            text: '保存',
	            handler  : function(){
	            	var checkId = new Array();
				    var b = tree.getChecked();
				    for(var i=0;i<b.length;i++){
						checkId.push(b[i].id);
					}
					Ext.Ajax.request({		       		
						url : 'assignRPopedom.do',					
						disableCaching : true,
						params : {
							GroupId : value,
							resourceIds:checkId.join(";")
						},
						method : 'post',
						success : function(result, request) {
							Ext.Msg.alert('提示', '资源分配成功！');
							resourceGroupWin.close();
						},
						failure : function(result, request) {
							Ext.Msg.alert("分配资源发生错误,内容为：","<font color='black'>"+result.responseText+"</font>");
						}
					});
	           }
	        },{
	            text: '取消',
	            handler  : function(){
	            	resourceGroupWin.close();
	            }
	        }]
	    });
	    resourceGroupWin.show();
	}else{
		Ext.onReady(function() { 
			Ext.Msg.alert('提示', '资源分配只能由管理员才能执行操作！'); 
			}); 
	}
}

