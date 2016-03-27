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
function changeBackground(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

	var imageStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'json/imageList.do?imageType=background'
				}),
				//创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
					root : 'imageList',
					fields : ['id','imageName']
				})
		});
	imageStore.load();
	
	var MsgForm = new Ext.form.FormPanel({
		baseCls: 'x-plain',
        labelAlign: 'left',
    	buttonAlign: 'center',
    	frame: true,
    	labelWidth: 65,
    	defaultType: 'textfield',
    	fileUpload: true,  
    	defaults: {
    		allowBlank: false
    	},
        items: [{
        	xtype:'fieldset',
        	title:'属性信息',
        	collapsible:true,
        	width:320,
        	autoHeight:true,//使自适应展开排版
        	defaults:{width:200,height:30},
        	defaultType:'textfield',
        	items:[{
                    xtype:"combo",
                    name: 'backgroundImg',
                    store:imageStore,
                    displayField:'imageName',
                    mode: 'remote',
                    fieldLabel:"图片浏览",
                    emptyText:'选择背景图片',
                    triggerAction:'all',
                    hiddenName:'backgroundImg',
                    editable : false,
                    listeners: {
                        select : function() {
        			 	document.body.style.backgroundImage='url(images/'+this.value+')';
                     }
                  },

                    forceSelection : true
        		},{
        		id:'fileName',
        		xtype: 'textfield',   
		        fieldLabel: '图片上传',
		        allowBlank:false,
		        name: 'upload',   
		        inputType: 'file'//文件类型   
        	}],
        	buttons: [{   
		        text: '上传',   
		        handler: function() {
		         if(MsgForm.form.isValid()){
            		MsgForm.form.doAction('submit',{
            			url: 'upload.do',
     					method: 'post',
     					params : {
            				imageType:'background'
            			},
     					waitTitle:"提示",
     					waitMsg:"<font color='black'>正在发送，请稍后......</font>",
     					success:function(MsgForm,action){
     						document.body.style.backgroundImage='url(images/' + Ext.get('fileName').getValue(false)
     						    .substring(Ext.get('fileName').getValue(false).lastIndexOf("\\")+1) + ')';
     						//imageStore.load();
     						win.close();
     					
     					},
     					failure:function(MsgForm,action){
     						Ext.Msg.alert('错误', '文件上传失败');  
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
		    }]
    });
	var win = new Ext.Window({
        title: '更改属性',
        width: 350,
        height:180,
        x:330,
        y:150,
        layout: 'fit',
        plain: true,
        bodyStyle:'padding:5px;color:black;',
        buttonAlign:'center',
        items: MsgForm
    });
    win.show();
}
function changeDevice(currentObj,xSize,ySize){
	var imageStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : 'json/imageList.do?imageType=device'
			}),
			//创建JsonReader读取router记录
			reader : new Ext.data.JsonReader({
				root : 'imageOfIconList',
				fields : ['id','iconFile']
			})
	});
	imageStore.load();

	var MsgForm = new Ext.form.FormPanel({
		baseCls: 'x-plain',
        labelAlign: 'left',
    	buttonAlign: 'center',
    	frame: true,
    	labelWidth: 65,
    	defaultType: 'textfield',
    	fileUpload: true,  
    	defaults: {
    		allowBlank: false
    	},
        items: [{
        	xtype:'fieldset',
        	title:'属性信息',
        	collapsible:true,
        	width:320,
        	autoHeight:true,//使自适应展开排版
        	defaults:{width:200,height:30},
        	defaultType:'textfield',
        	items:[{
        			fieldLabel:'图标宽度',
	        		id:'deviceIconWidth',
	        		name:'deviceIconWidth',
	        		allowBlank: false,
	        		blankText:'图标宽度不能为空!',
	        		value:xSize
	        		},{
	        		fieldLabel:'图标高度',
	        		id:'deviceIconHeight',
	        		name:'deviceIconHeight',
	        		allowBlank: false,
	        		blankText:'图标高度不能为空!',
	        		value:ySize
	        		},{
                    xtype:"combo",
                    name: 'backgroundImg',
                    store:imageStore,
                    //store:["china.gif","world.gif","router.gif"],
                    displayField:'iconFile',
                    mode: 'remote',
                    //mode:'local',
                    fieldLabel:"图片浏览",
                    emptyText:'选择设备背景图片',
                    triggerAction:'all',
                    hiddenName:'backgroundImg',
                    editable : false,
                    listeners: {
                        select : function(combo) {
                        currentObj.swapImage("images/" + this.value);
                        currentObj.isChangeIcon = '1';
                     }
                  },

                    forceSelection : true
        		}],
        	buttons: [{
	            text: '关闭',
	            handler  : function(){
	                var iconWidth = Ext.get("deviceIconWidth").getValue(false);
	                var iconHeight = Ext.get("deviceIconHeight").getValue(false);
	                currentObj.resizeTo(iconWidth+"px",iconHeight+"px");
	            	win.close();
	            }
	        }]
		    }]
    });
	var win = new Ext.Window({
        title: '更改设备属性',
        width: 350,
        height:220,
        x:330,
        y:150,
        layout: 'fit',
        plain: true,
        bodyStyle:'padding:5px;color:black;',
        buttonAlign:'center',
        items: MsgForm
    });
    win.show();
}
function changeLink(id,srcId,destId,srcName,destName,srcInterfaceId,destInterfaceId){
	var changeid = id;
	var treeDiv = Ext.get("treecontainer1");
	if(treeDiv==null){
		var addDIV = document.createElement("div");
		addDIV.id = "treecontainer1";
        document.body.appendChild(addDIV);
	}
	var tree2Div = Ext.get("treecontainer2");
	if(tree2Div==null){
		var addDIV = document.createElement("div");
		addDIV.id = "treecontainer2";
        document.body.appendChild(addDIV);
	}
	
	var Tree = Ext.tree;
    var treecontainer1 = new Tree.TreePanel({
        el:'treecontainer1',
        animate:true, 
        autoScroll:true,
        width: 240,
        height:220,
        rootVisible: false,
        loader: new Tree.TreeLoader({
        	dataUrl:'tochosedlink.do?deviceId='+srcId.replace("s", "")+"&interfaceId="+srcInterfaceId
        	
        }),
        enableDD:false,
        containerScroll: true,
        dropConfig: {appendOnly:true}
    });
    // add a tree sorter in folder mode
    new Tree.TreeSorter(treecontainer1, {folderSort:true});
    // set the root node
    var root = new Tree.AsyncTreeNode({
        text: 'up interface', 
        draggable:false, // disable root node dragging
        id:'up'
    });
    treecontainer1.setRootNode(root);
    treecontainer1.render(); // render the tree
    root.expand(true, true);
    var treecontainer2 = new Tree.TreePanel({
        el:'treecontainer2',
        animate:true,
        autoScroll:true,
        width: 240,
        height:220,
        rootVisible: false,
        loader: new Ext.tree.TreeLoader({
            dataUrl:'tochosedlink.do?deviceId='+destId.replace("s", "")+"&interfaceId="+destInterfaceId
            
        }),
        containerScroll: true,
        enableDD:false,
        dropConfig: {appendOnly:true}
    });
    // add a tree sorter in folder mode
    new Tree.TreeSorter(treecontainer2, {folderSort:true});
    // add the root node
    var root2 = new Tree.AsyncTreeNode({
        text: 'down interface', 
        draggable:false, 
        id:'down'
    });
    treecontainer2.setRootNode(root2);
    treecontainer2.render();
    root2.expand(true, true);
    
   var chosingDevice = new Ext.Panel({
   		id:'srcif',
   		contentEl:"treecontainer1",
        title: '上行端口',
        region: 'west',
        split: true,
        width: 240,
        height:220,
        collapsible: true,
        margins:'3 0 3 3',
        cmargins:'3 3 3 3'       
    });   
    var chosedDevice = new Ext.Panel({
    	id:'destif',
    	contentEl:"treecontainer2",
        title: '下行端口',
        region: 'center',
        split: true,
        width: 240,
        height:200,
        collapsible: true,
        margins:'3 0 3 3',
        cmargins:'3 3 3 3'
    });
	var win = new Ext.Window({
        title: '更改端口',
        width: 510,
        height:320,
        layout: 'border',
        plain: true,
        resizable:false,
        bodyStyle:'padding:5px;color:black;',
        buttonAlign:'center',
        items:[chosingDevice, chosedDevice],
        buttons: [{
            text: '更改',
            handler  : function(){
	       		var b = treecontainer1.getChecked();
	       		var c = treecontainer2.getChecked();
				var srcCheckId = new Array();
				var destCheckId = new Array();
				for(var i=0;i<b.length;i++){
					if(b[i].leaf){
						srcCheckId.push(b[i].id);
					}
				}
				for(var i=0;i<c.length;i++){
					if(c[i].leaf){
						destCheckId.push(c[i].id);
					}
				}
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
					url : 'json/linkAdd.do',
					disableCaching : true,
					params : {
						srcCheckId : srcCheckId,
						destCheckId: destCheckId,
						srcId: srcId.replace("s", ""),
						destId: destId.replace("s", ""),
						srcName: srcName,
						destName: destName
					},
					method : 'post',
					success : function(result, request) {
					    Ext.MessageBox.alert('Status', '更改链路属性成功！');
					    var data = Ext.decode(result.responseText);
						var linkId = data.linkId;
						var linkName = data.linkName;
						var changelink = new Link({
			                id:changeid,
			                 name:linkName,
			                 srcId:srcId,
			                 destId:destId,
			                 srcIfId:srcCheckId,
			                 destIfId:destCheckId,
			                  linkId:"s"+linkId,
			                  lineNum:1
		});
					   for(var j = 0; j < allLinks.length; j++){
				          	if( changeid == allLinks[j][0]){
				          	allLinks.splice(j,1,[changeid,changelink]); 
				          	break;
				          	}
			              	}
						win.close();
					},
					failure : function(result, request) {
						Ext.Msg.alert("<font color='black'>更改链路属性时发生错误!</font>");
					}
				});
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