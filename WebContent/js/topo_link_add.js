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
function addLink(srcId,destId,srcName,destName,viewName){
		var treeDiv = Ext.get("ltree");
		if(treeDiv==null){
			var addDIV = document.createElement("div");
			addDIV.id = "ltree";
			addDIV.style.width = "300px";
	        addDIV.style.height = "450px";
	        document.body.appendChild(addDIV);
		}
		var tree2Div = Ext.get("ltree2");
		if(tree2Div==null){
			var addDIV = document.createElement("div");
			addDIV.id = "ltree2";
			addDIV.style.width = "300px";
	        addDIV.style.height = "450px";
	        document.body.appendChild(addDIV);
		}
		
		var Tree = Ext.tree;
	    var ltree = new Tree.TreePanel({
	        el:'ltree',
	        animate:true, 
	        width:295,
	        height:400,
	        autoScroll:true,
	        rootVisible: false,
	        loader: new Tree.TreeLoader({
	        	dataUrl:'topochoselink.do?deviceId='+srcId+'&viewName='+ viewName // 选择路由器端口
	        	
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
	         width:270,
	        height:400,
	        autoScroll:true,
	        rootVisible: false,
	        loader: new Ext.tree.TreeLoader({
	            dataUrl:'topochoselink.do?deviceId='+destId+'&viewName='+ viewName
	            
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
	        width: 300,
	        collapsible: true,
	        margins:'3 0 3 3',
	        cmargins:'3 3 3 3'       
	    });   
	    var chosedDevice = new Ext.Panel({
	    	id:'destif',
	    	contentEl:"ltree2",
	        title: '下行端口',
	        region: 'center',
	        split: true,
	        width: 3000,
	        collapsible: true,
	        margins:'3 0 3 3',
	        cmargins:'3 3 3 3'
	    });
		var win = new Ext.Window({
	        title: '添加链路',
	        width: 600,
	        height:500,
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
					var srcCheckId;
					var destCheckId;
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
					if(srcCheckId!="" && destCheckId!=""){
						var srcIndex = srcCheckId.lastIndexOf("_");
						var srcIfIndex = srcCheckId.substring(srcIndex+1);
						var destIndex = destCheckId.lastIndexOf("_");
						var destIfIndex = destCheckId.substring(destIndex+1);
						registerLine3(srcCheckId+"--"+destCheckId, srcCheckId+"--"+destCheckId,"s"+srcId,"s"+destId,srcIfIndex,destIfIndex);
						win.close();
					}	
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