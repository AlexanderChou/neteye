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
//添加链路
function addRouter(){
	var treeDiv = Ext.get("ltree");
	if(treeDiv==null){
		var addDIV = document.createElement("div");
		addDIV.id = "ltree";
		addDIV.style.width = "250px";
        addDIV.style.height = "450px";
        document.body.appendChild(addDIV);
	}
	var tree2Div = Ext.get("ltree2");
	if(tree2Div==null){
		var addDIV = document.createElement("div");
		addDIV.id = "ltree2";
		addDIV.style.width = "250px";
        addDIV.style.height = "450px";
        document.body.appendChild(addDIV);
	}
	
	var Tree = Ext.tree;
    var ltree = new Tree.TreePanel({
        el:'ltree',
        animate:true, 
        autoScroll:true,
        rootVisible: false,
        loader: new Tree.TreeLoader({
        	dataUrl:'tochosedevice.do' // 选择路由器        	
        }),
        enableDD:false,
        containerScroll: true,
        dropConfig: {appendOnly:true}
    });
    // add a tree sorter in folder mode
    new Tree.TreeSorter(ltree, {folderSort:true});
    // set the root node
    var root = new Tree.AsyncTreeNode({
        text: 'up interface', 
        draggable:false, // disable root node dragging
        id:'up'
    });
    ltree.setRootNode(root);
    ltree.render(); // render the tree
    root.expand(false, true);
    
    var ltree2 = new Tree.TreePanel({
        el:'ltree2',
        animate:true,
        autoScroll:true,
        rootVisible: false,
        loader: new Ext.tree.TreeLoader({
            dataUrl:'tochosedevice.do'            
        }),
        containerScroll: true,
        enableDD:false,
        dropConfig: {appendOnly:true}
    });
    // add a tree sorter in folder mode
    new Tree.TreeSorter(ltree2, {folderSort:true});
    // add the root node
    var root2 = new Tree.AsyncTreeNode({
        text: 'down interface', 
        draggable:false, 
        id:'down'
    });
    ltree2.setRootNode(root2);
    ltree2.render();
    root2.expand(false, true);

   var chosingDevice = new Ext.Panel({
   		id:'srcif',
   		contentEl:"ltree",
        title: '上行节点',
        region: 'west',
        split: true,
        width: 270,
        collapsible: true,
        autoScroll:true,
        margins:'3 0 3 3',
        cmargins:'3 3 3 3'       
    });   
    var chosedDevice = new Ext.Panel({
    	id:'destif',
    	contentEl:"ltree2",
        title: '下行节点',
        region: 'center',
        split: true,
        width: 270,
        collapsible: true,
        autoScroll:true,
        margins:'3 0 3 3',
        cmargins:'3 3 3 3'
    });
	var win2 = new Ext.Window({
        title: '选择节点',
        width: 568,
        height:350,
        x:270,
        y:150,
        layout: 'border',
        plain: true,
        bodyStyle:'padding:5px;color:black;',
        buttonAlign:'center',
        items:[chosingDevice, chosedDevice],
        buttons: [{
            text: '添加',
            handler  : function(){
	       		var b = ltree.getChecked();
	       		var c = ltree2.getChecked();
				var srcId = "";
				var desId = "";
				if(b.length>1){
					alert("上链节点不能超过1个！");
					return false;
				}
				if(b.length==0){
					alert("请选择上链节点！");
					return false;
				}
				if(b[0].leaf){
					srcId=(b[0].id);
				}
				
				if(c.length>1){
					alert("下链节点不能超过1个！");
					return false;
				}
				if(c.length==0){
					alert("请选择下链节点！");
					return false;
				}
				if(c[0].leaf){
					desId=(c[0].id);
				}
				if(srcId==desId){
					alert("上链和下链不能是同一个节点！");
					return false;
				}
				win2.close();		
		        addLink(srcId,desId,b[0].text,c[0].text);		               
           }
        },{
            text: '取消',
            handler  : function(){
            	win2.close();
            }
        }]
    });
    win2.show();
}

//添加链路
function addLink(srcId,destId,srcName,destName){
	var treeDiv = Ext.get("ltree");
	if(treeDiv==null){
		var addDIV = document.createElement("div");
		addDIV.id = "ltree";
		addDIV.style.width = "250px";
        addDIV.style.height = "450px";
        document.body.appendChild(addDIV);
	}
	var tree2Div = Ext.get("ltree2");
	if(tree2Div==null){
		var addDIV = document.createElement("div");
		addDIV.id = "ltree2";
		addDIV.style.width = "250px";
        addDIV.style.height = "450px";
        document.body.appendChild(addDIV);
	}
	
	var Tree = Ext.tree;
    var ltree = new Tree.TreePanel({
        el:'ltree',
        animate:true, 
        autoScroll:true,
        rootVisible: false,
        loader: new Tree.TreeLoader({
        	dataUrl:'tochoselink.do?deviceId='+srcId  // 选择路由器端口        	
        }),
        enableDD:false,
        containerScroll: true,
        dropConfig: {appendOnly:true}
    });
    // add a tree sorter in folder mode
    new Tree.TreeSorter(ltree, {folderSort:true});
    // set the root node
    var root = new Tree.AsyncTreeNode({
        text: 'up interface', 
        draggable:false, // disable root node dragging
        id:'up'
    });
    ltree.setRootNode(root);
    ltree.render(); // render the tree
    root.expand(true, true);
    
    var ltree2 = new Tree.TreePanel({
        el:'ltree2',
        animate:true,
        autoScroll:true,
        rootVisible: false,
        loader: new Ext.tree.TreeLoader({
            dataUrl:'tochoselink.do?deviceId='+destId            
        }),
        containerScroll: true,
        enableDD:false,
        dropConfig: {appendOnly:true}
    });
    // add a tree sorter in folder mode
    new Tree.TreeSorter(ltree2, {folderSort:true});
    // add the root node
    var root2 = new Tree.AsyncTreeNode({
        text: 'down interface', 
        draggable:false, 
        id:'down'
    });
    ltree2.setRootNode(root2);
    ltree2.render();
    root2.expand(true, true);
    
   var chosingDevice = new Ext.Panel({
   		id:'srcif',
   		contentEl:"ltree",
        title: '上行端口',
        region: 'west',
        split: true,
        width: 270,
        collapsible: true,
        autoScroll:true,
        margins:'3 0 3 3',
        cmargins:'3 3 3 3'       
    });   
    var chosedDevice = new Ext.Panel({
    	id:'destif',
    	contentEl:"ltree2",
        title: '下行端口',
        region: 'center',
        split: true,
        width: 270,
        collapsible: true,
        autoScroll:true,
        margins:'3 0 3 3',
        cmargins:'3 3 3 3'
    });
	var win = new Ext.Window({
        title: '添加链路',
        width: 568,
        height:350,
        x:270,
        y:150,
        layout: 'border',
        plain: true,
        bodyStyle:'padding:5px;color:black;',
        buttonAlign:'center',
        items:[chosingDevice, chosedDevice],
        buttons: [{
            text: '添加',
            handler  : function(){
	       		var b = ltree.getChecked();
	       		var c = ltree2.getChecked();
				var srcCheckId = "";
				var destCheckId = "";
				if(b.length>1){
					alert("上链端口不能超过1个！");
					return false;
				}
				if(b.length==0){
					alert("请选择上链端口！");
					return false;
				}
				if(b[0].leaf){
					srcCheckId=(b[0].id);
				}
				
				if(c.length>1){
					alert("下链端口不能超过1个！");
					return false;
				}
				if(c.length==0){
					alert("请选择下链端口！");
					return false;
				}
				if(c[0].leaf){
					destCheckId=(c[0].id);
				}
				if(srcCheckId.length==0){
					alert("没有上链端口被选中！");
					return false;
				}
				if(destCheckId.length==0){
					alert("没有下链端口被选中！");
					return false;
				}
		       	Ext.Ajax.request({
					url : 'json/addLink.do',
					disableCaching : true,
					params : {
						srcCheckId : srcCheckId,
						destCheckId: destCheckId,
						srcId:       srcId,
						destId:      destId,
						srcName:     srcName,
						destName:    destName
					},
					method : 'GET',
					success : function(result, request) {
			 			var flag = Ext.decode(result.responseText);
						if(flag.data==0){
							Ext.Msg.confirm('信息',"链路添加成功",function(btn){
							if(btn=='yes'){	
							win.close();
							linkStore.load({params:{start:0,limit:15}});
							}
							});
						}else if(flag.data==1){
							Ext.Msg.confirm('信息',"<font color='red'>添加链路已存在！</font>",function(btn){
							if(btn=='yes'){	
							win.close();
							}
							});
						}else if(flag.data==2){
							Ext.Msg.confirm('信息',"<font color='red'>上链端口被占用！</font>",function(btn){
							if(btn=='yes'){	
							win.close();
							}
							});
						}else if(flag.data==3){
							Ext.Msg.confirm('信息',"<font color='red'>下链端口被占用！</font>",function(btn){
							if(btn=='yes'){	
							win.close();
							}
							});
						}else if(flag.data==4){
							Ext.Msg.confirm('信息',"<font color='red'>数据库操作失败！</font>",function(btn){
							if(btn=='yes'){	
							win.close();
							}
							});
						}
					},
					failure : function(result, request) {
						var flag = Ext.decode(result.responseText);
						Ext.Msg.confirm('信息',"<font color='black'>"+flag.data+"</font>",function(btn){
								if(btn=='yes'){	
									win.close();
								}
								});
					}
				});
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