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
//视图
function doSubmitViewOLD(){
		if(IsChecked(myform.view, "请至少选择一个视图！")){
			var myCheck = myform.view;
			if(myCheck == undefined){
				return;
			}
			var length = myCheck.length;
			myform.submitView.value = "";
			if(length == undefined){
				if(myCheck.checked){
					myform.submitView.value = myform.submitView.value + ";" + myCheck.viewid;
				}
			}
			else{
				for(var i = 0; i < length; i ++){
					if(myCheck[i].checked){
						myform.submitView.value = myform.submitView.value + ";" + myCheck[i].viewid;
					}
				}
			}
			if(confirm("您确认要删除选中的视图吗？")){
				myform.action = "viewdelete.do";
				myform.submit();
			}
		}
}
function doSubmitView(){
		if(IsChecked(document.getElementById("myform").view, "请至少选择一个视图！")){
			var myCheck = document.getElementById("myform").view;
			
	//		var myCheck = myform.view;
			var length = myCheck.length;
			if(myCheck == undefined){
				return;
			}
			var length = myCheck.length;
			var mychecked= "";
			if(length == undefined){
				if(myCheck.checked){
					mychecked = mychecked + ";" + myCheck.getAttribute("viewid");
				}
			}
			else{
				for(var i = 0; i < length; i ++){
					if(myCheck[i].checked){
						mychecked = mychecked + ";" + myCheck[i].getAttribute("viewid");
					}
				}
			}
			if(confirm("您确认要删除选中的视图吗？")){
				Ext.Ajax.request({
				            		
								url : "viewdelete.do",
								params : {
									submitView : mychecked
								},
								success : function(result, request) {
									Ext.Msg.alert("成功","删除视图成功"),
									window.location.reload()
								}
								
			})
		}
		}
}
//全选
function doSelAllViewOLD(obj){
	//    myform= document.getElementById("myform");
		var myCheck = document.getElementById("myform").view;
//		var myCheck = myform.view;
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
	function doSelAllView(obj){
	//    myform= document.getElementById("myform");
		var myCheck = document.getElementById("myform").view;
//		var myCheck = myform.view;
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
//设备类型
function doSubmitDeviceType(){
		if(IsChecked(document.getElementById("myid").deviceType, "请至少选择一个设备类型！")){
			var myCheck = document.getElementById("myid").deviceType;
			if(myCheck == undefined){
				return;
			}
			var length = myCheck.length;
			tempvalue = "";
			
			if(length == undefined){
				if(myCheck.checked){
					tempvalue = tempvalue + ";" + myCheck.getAttribute("devicetypeID");
				}
			}else{
				for(var i = 0; i < length; i ++){
					if(myCheck[i].checked){
						tempvalue = tempvalue + ";" + myCheck[i].getAttribute("devicetypeID");
					}
				}
			}
			if(confirm("您确认要删除选中的设备类型吗？(该设备类型有设备的将不予删除)")){
				Ext.Ajax.request({
				            		
								url : "deleteDeviceType.do",
								params : {
									submitDeviceType : tempvalue
								},
								success : function(result, request) {
									Ext.Msg.alert("成功","删除设备类型成功"),
									window.location.reload()
								}
								
			})
				
			}
		}
}

//全选
function doSelAllDeviceType(obj){
		var myCheck = document.getElementById("myid").deviceType;
		
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
function doEventStatus(){
	/*
		if(IsChecked(myform.view, "请至少选择一个视图！")){
			var myCheck = myform.view;
			if(myCheck == undefined){
				return;
			}
			var length=0;
			myform.submitView.value = "";
			if(length == undefined){
				if(myCheck.checked){
					myform.submitView.value = myform.submitView.value + ";" + myCheck.viewid;
				}
			}else{
				for(var i = 0; i < length; i ++){
					if(myCheck[i].checked){
						myform.submitView.value = myform.submitView.value + ";" + myCheck[i].viewid;
						length++;
					}
				}
			}
			myform.action = "viewEventStatus.do";
			myform.submit();
		}
		*/
		var checkBoxs = document.getElementsByName("view");
		if(IsChecked(document.getElementById("myform").view, "请至少选择一个视图！")) {
			var submitView = "";
			if (checkBoxs.length <= 0) {
				alert("请至少选择一个视图！");
				return;
			} else {
				for (var index = 0;index < checkBoxs.length; index++ ){
					var checkbox = checkBoxs[index];
					if(checkbox.checked != "undefined" && checkbox.checked){
						submitView += checkbox.getAttribute("viewid")+";";
					}
				}
				
				document.location.href = "viewEventStatus.do?submitView=" + submitView;
			}
		}
		
		
}
//故障监控方法
function doFaultStatus() {
	var checkBoxs = document.getElementsByName("view");
	if(IsChecked(document.getElementById("myform").view, "请至少选择一个视图！")) {
		var submitView = "";
		if (checkBoxs.length <= 0) {
			alert("请至少选择一个视图！");
			return;
		} else {
			for (var index = 0;index < checkBoxs.length; index++ ){
				var checkbox = checkBoxs[index];
				if(checkbox.checked != "undefined" && checkbox.checked){
					submitView += checkbox.getAttribute("viewid")+";";
				}
			}
			
			document.location.href = "faultEventStatus.do?submitView=" + submitView;
		}	
	}
}
function doFlowStatus() {
	var checkBoxs = document.getElementsByName("view");
	if(IsChecked(document.getElementById("myform").view, "请至少选择一个视图！")) {
		var submitView = "";
		if (checkBoxs.length <= 0) {
			alert("请至少选择一个视图！");
			return;
		} else {
			for (var index = 0;index < checkBoxs.length; index++ ){
				var checkbox = checkBoxs[index];
				if(checkbox.checked != "undefined" && checkbox.checked){
					submitView += checkbox.getAttribute("viewid")+";";
				}
			}
			
			document.location.href = "flowEventStatus.do?submitView=" + submitView;
		}	
	}
}

function renameOLD(){
		var illegal;
		if(IsChecked(myform.view, "请至少选择一个视图！")){
			var myCheck = myform.view;
			if(myCheck == undefined){
				return;
			}
			var length = myCheck.length;
			illegal=0;
			var viewName = "";
			if(length == undefined){
				if(myCheck.checked){
					viewName = viewName + ";" + myCheck.viewid;
				}
			}
			else{
				for(var i = 0; i < length; i ++){
					if(myCheck[i].checked){
						viewName = viewName + ";" + myCheck[i].viewid;
						
						illegal++;
					}
				}
			}
			Ext.onReady(function(){
				var Record = new Ext.data.Record.create([
					{name:"id", mapping:"id"},
					{name:"name", mapping:"name"},
					{name:"reName", mapping:"reName"}
				]);
				
				var reader = new Ext.data.JsonReader({
					root:"nameList",
					totalProperty:'totalCount'
					},
					Record
				    );
				
				var proxy = new Ext.data.HttpProxy({
					url:"json/viewRename.do"
				});
				var store = new Ext.data.Store({
					proxy:proxy,
					reader:reader
				});
				store.load({params:{viewName:viewName,start:0,limit:28}});
				var colm = new Ext.grid.ColumnModel([
					{header:"视图id",sortable: true, dataIndex:"id",hidden:true},
					{header:"原视图名字",sortable: true,width:200, dataIndex:"name"},
					{header:"双击后填写视图新名字", dataIndex:"reName",width:250,editor:new Ext.form.Field()}
				]);
			
				var grid = new Ext.grid.EditorGridPanel({
					store:store,
					height:450,
					width:480,		
					cm:colm,
					autoScroll:true,
					renderTo:"showDiv",					
					bbar:new Ext.PagingToolbar({
						pageSize:28,
						store:store,
						displayInfo:true,
						displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
						emptyMsg:'没有数据'
					})
				});
				grid.on("afteredit", afterEdit, grid);
				var win = new Ext.Window({
        				title:"更改视图名称列表",
                        width:515,
                        height:520,
                        autoScroll : true,   
				        closable : true,  
				        items : [grid],
				        buttons: [{
							text:'确定',
							handler:function(){
								if(illegal>0){
									alert("内容有不合法的字符或者为空，请修改后再提交！");
								}else{
									if(confirm("您确认要修改选中的视图名字吗？")){
											window.location.href="nameModify.do";
									}	
								}					
							  }
							},{
							text:'取消',
							handler:function(){
								win.close();							
							  }
							}]
	  		 	 });
	   			win.show();
			});
			
			function afterEdit(obj) {
				var r = obj.record;
				var l = obj.field;
				var id = r.get("id");
			    var reName = r.get("reName");
			    var isName = /^\S+$/;
				if (!isName.test(reName)){					
					alert("视图名称不能为空!");
					return false;
				}else{
					illegal=illegal-1;
					Ext.Ajax.request({
						url:"nameEdit.do",
						params:{
								id:id,
								reName:reName
							},
						success:function(response,request){
							if (response.responseText == "ok") {
							}else if(response.responseText == "exist") {
								alert("名字重复，修改失败！");
								store.reload();				
							}								
							else {
								alert("修改失败！");
								store.reload();
							}
						}
					});	
				}	
			}
		}
}
function rename(){
		var illegal;
		if(IsChecked(document.getElementById("myform").view, "请至少选择一个视图！")){
			var myCheck = document.getElementById("myform").view;
			if(myCheck == undefined){
				return;
			}
			var length = myCheck.length;
			illegal=0;
			var viewName = "";
			if(length == undefined){
				if(myCheck.checked){
					viewName = viewName + ";" + myCheck.getAttribute("viewid");
					alert(viewName);
				}
			}
			else{
				for(var i = 0; i < length; i ++){
					if(myCheck[i].checked){
						viewName = viewName + ";" + myCheck[i].getAttribute("viewid");
						alert(viewName);
						illegal++;
					}
				}
			}
			function afterEdit(obj) {
				var r = obj.record;
				var l = obj.field;
				var id = r.get("id");
			    var reName = r.get("reName");
			    var isName = /^\S+$/;
				if (!isName.test(reName)){					
					alert("视图名称不能为空!");
					return false;
				}else{
					illegal=illegal-1;
					Ext.Ajax.request({
						url:"nameEdit.do",
						params:{
								id:id,
								reName:reName
							},
						success:function(response,request){
							if (response.responseText == "ok") {
							}else if(response.responseText == "exist") {
								alert("名字重复，修改失败！");
								store.reload();				
							}								
							else {
								alert("修改失败！");
								store.reload();
							}
						}
					});	
				}	
			}
			Ext.onReady(function(){
				
				var Record = new Ext.data.Record.create([
					{name:"id", mapping:"id"},
					{name:"name", mapping:"name"},
					{name:"reName", mapping:"reName"}
				]);
				
				var reader = new Ext.data.JsonReader({
					root:"nameList",
					totalProperty:'totalCount'
					},
					Record
				    );
				
				var proxy = new Ext.data.HttpProxy({
					url:"json/viewRename.do"
				});
				var store = new Ext.data.Store({
					proxy:proxy,
					reader:reader
				});
				store.load({params:{viewName:viewName,start:0,limit:28}});
				var colm = new Ext.grid.ColumnModel([
					{header:"视图id",sortable: true, dataIndex:"id",hidden:true},
					{header:"原视图名字",sortable: true,width:200, dataIndex:"name"},
					{header:"双击后填写视图新名字", dataIndex:"reName",width:250,editor:new Ext.form.Field()}
				]);
			
				var grid = new Ext.grid.EditorGridPanel({
					store:store,
					height:450,
					width:480,		
					cm:colm,
					autoScroll:true,
					renderTo:"showDiv",					
					bbar:new Ext.PagingToolbar({
						pageSize:28,
						store:store,
						displayInfo:true,
						displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
						emptyMsg:'没有数据'
					})
				});
				grid.on("afteredit", afterEdit, grid);
				var win = new Ext.Window({
        				title:"更改视图名称列表",
                        width:515,
                        height:520,
                        autoScroll : true,   
				        closable : true,  
				        items : [grid],
				        buttons: [{
							text:'确定',
							handler:function(){
								if(illegal>0){
									alert("内容有不合法的字符或者为空，请修改后再提交！");
								}else{
									if(confirm("您确认要修改选中的视图名字吗？")){
											window.location.href="nameModify.do";
									}	
								}					
							  }
							},{
							text:'取消',
							handler:function(){
								win.close();							
							  }
							}]
	  		 	 });
	   			win.show();
			});
			
	
		}
}
function pageConfigOLD(){
	if(IsChecked(myform.view, "请选择一个视图！")){
			var myCheck = myform.view;
			if(myCheck == undefined){
				return;
		}
		
		
		
	var length = myCheck.length;
	var viewName = "";
	var number=0;
	if(length == undefined){
		if(myCheck.checked){
			viewName =  myCheck.viewid;
		}
	  }
	  else{
			for(var i = 0; i < length; i ++){
				if(myCheck[i].checked){
					viewName = myCheck[i].viewid;
					number+=1;
				}
			}
		}
	 if(number>1){
		 alert("只能配置一个主监控页面视图，请重新选择！");
		 return;
	 }
	 
	 
	if(confirm("您确认要设置选中的视图为主监控页面吗？")){
		window.location.href="pageConfig.do?viewName="+viewName;
	 }
	}
	
}
	
function pageConfig(){
	if(IsChecked(document.getElementById("myform").view, "请选择一个视图！")){
		var myCheck = document.getElementById("myform").view;
			if(myCheck == undefined){
				return;
			}
		
		
		
	var length = myCheck.length;
	var viewName = "";
	var number=0;
	if(length == undefined){
		if(myCheck.checked){
			viewName =  myCheck.getAttribute("viewid");
		}
	  }
	  else{
			for(var i = 0; i < length; i ++){
				if(myCheck[i].checked){
					viewName = myCheck[i].getAttribute("viewid");
					number+=1;
				}
			}
		}
	 if(number>1){
		 alert("只能配置一个主监控页面视图，请重新选择！");
		 return;
	 }
	 
	 
	if(confirm("您确认要设置选中的视图为主监控页面吗？")){
		window.location.href="pageConfig.do?viewName="+viewName;
	 }
	}
}
function doConfig(){
	if(IsChecked(myform.view, "请选择一个视图！")){
		var myCheck = myform.view;
		if(myCheck == undefined){
				return;
		}
		var length = myCheck.length;
		var viewName = "";
		var number=0;
		if(length == undefined){
			if(myCheck.checked){
				viewName =  myCheck.viewid;
			}
	  	}else{
			for(var i = 0; i < length; i ++){
				if(myCheck[i].checked){
					viewName = myCheck[i].viewid;
					number+=1;
				}
			}
		}
	 	if(number>1){
		 	alert("只能配置一个主监控页面视图，请重新选择！");
		 	return;
	 	}else{
			//var viewName = document.submitViewForm.submitView.options[document.submitViewForm.submitView.selectedIndex].innerHTML;
			//var viewId = document.submitViewForm.submitView.options[document.submitViewForm.submitView.selectedIndex].value;
			var treeDiv = Ext.get("tree");
			if(treeDiv==null){
				var addDIV = document.createElement("div");
				addDIV.id = "tree";
				addDIV.style.width = "450px";
				addDIV.style.height = "450px";
				document.body.appendChild(addDIV);
			}
			
			var Tree = Ext.tree;
			var tree = new Tree.TreePanel({
				el:'tree',
				animate:true, 
				autoScroll:true,
				loader: new Tree.TreeLoader({dataUrl:'tochoseMonitorInf.do?viewName='+viewName}),
				enableDD:true,
				containerScroll: true
			});
		 
			new Tree.TreeSorter(tree, {folderSort:true});
			var root = new Tree.AsyncTreeNode({
				text: '设备列表(端口内容显示格式为：描述||ipv4||ipv6||ifindex)', 
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
		
			var win = new Ext.Window({
				title: '选择待显示流量图形的路由器端口',
				contentEl:"tree",
				width: 480,
				autoScroll:true,
				resizable:false,
				plain: true,
				bodyStyle:'padding:5px;color:black;',
				buttonAlign:'center',
				buttons: [{
					text: '配置',
					handler  : function(){
						var checkId = new Array();
						var b = tree.getChecked();
						var c =new Array();
						for(var i=0;i<b.length;i++){
							if(b[i].leaf){
								checkId.push(b[i].id);
							}
						}
			            Ext.Ajax.request({
							url : "json/addMonitorInf.do",
							params : {
								checkedId : checkId,
								viewName : viewName
							},
							success : function(result, request) {
								var data = Ext.decode(result.responseText);
								var success =data.success;
								if(success){
								    Ext.Msg.alert("配置成功！");	
								}
							}
			            })
						win.close();
						store.reload();
				   }
				},{
					text: '取消',
					handler  : function(){
						win.close();
					}
				}]
			});
			win.show();
		}
	}	
}