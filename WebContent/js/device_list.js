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
function  createGrid(){  
		var xg = Ext.grid;
		var sm = new xg.CheckboxSelectionModel();
		var s = window.location.search.substring(1);
        var id = Ext.urlDecode(s);
		var routerStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'json/deviceList.do?deviceTypeId='+id.deviceTypeId
				}),
				//创建JsonReader读取port记录
				reader : new Ext.data.JsonReader({
					root : 'routerList',
					totalProperty:'totalCount',
					fields : ['id','name','chineseName','loopbackIP','loopbackIPv6','ifNum','readCommunity','snmpVersion']
				})
		});
		routerStore.setDefaultSort('name','DESC'); 
		routerStore.load();
		routerStore.load({params:{start:0,limit:28}});
		var routerPanel = new Ext.grid.GridPanel({			
			store : routerStore,
			collapsible: true,
			height:document.body.clientHeight*0.95-136,
			width:835,
        	title:'接口列表',
			columns : [
				new Ext.grid.RowNumberer(),
				{header: "名称", width: 60, sortable: true, dataIndex: 'name'}, 
				{id: "chineseName", header: "中文名称",width: 50,sortable: true,dataIndex : 'chineseName'},
				{header: 'ipv4地址', width: 100 ,sortable: true,dataIndex: 'loopbackIP'}, 
				{header: "ipv6地址", width: 150,sortable: true,dataIndex : 'loopbackIPv6'},
				{header: "端口数量", width: 150,sortable: true,dataIndex : 'ifNum'},
				{header: "readCommunity", hidden: true,width: 150,sortable: true,dataIndex : 'readCommunity'},
				{header: "snmpVersion", hidden: true,width: 150,sortable: true,dataIndex : 'snmpVersion'},
				 sm
				],
				sm:sm,
			tbar: ["->",{text:'添加',
						handler:function(){
							window.location.href = "deviceAdd.do?sstatus=2&id="+id.deviceTypeId+"&style=3";			
						}
					},"|",
//					{text:'更新设备信息',
//						handler:function(){
//							var selected = sm.getSelections();
//							var updataids ="";
//							for (var i=0;i<selected.length;i++){
//								var datas = selected[i].data;
//								if(datas.loopbackIP!=""){
//									updataids = updataids+datas.chineseName + "|" + datas.loopbackIP + "|" + datas.readCommunity + "|" + datas.snmpVersion + "\n";
//								}else{
//									updataids = updataids+datas.chineseName + "|" + datas.loopbackIPv6 + "|" + datas.readCommunity + "|" + datas.snmpVersion + "\n";
//								}
//							}
//							if(updataids!=""){
//								Ext.Ajax.request({
//					                	url:"json/routerAdd.do",
//						             timeout:"90000",
//						                  method:"post",
//					                       	params:{device:updataids, deviceTypeId:id.deviceTypeId
//					                       	,style:3
//					                       	},
//						                success:function(response, request){
//					              		var data = Ext.decode(response.responseText).mesg;
//					           		   	var string = Ext.decode(response.responseText).exist;
//							           	window.location.href="spilth.do?exist="+string+"&deviceTypeId="+id.deviceTypeId+"&status=2";
//						                 	}
//					});
//							}else{
//								
//						Ext.Msg.alert("请选择","请选择更新的设备！")	
//								
//							}
//							
//								
//							
//							
//						}
//					
//						
//						
//						
//					},
//					
//					"|",
						{
						text:'删除',
						handler:function(){
							var selected = sm.getSelections();
							var ids = "";
							for(var i=0;i<selected.length;i+=1){
								var router = selected[i].data;
								if(router.id) {
								ids=ids+";"+router.id; 
								}
							}
							if(ids=='') return;
							Ext.Msg.confirm('信息','确定要删除所选项吗?',function(btn){
								if(btn=='yes'){	
								window.location.href = "deviceDelete.do?submitDevice="+ids+"&deviceTypeId="+id.deviceTypeId+"&style=3";
								}
								});	
						}
					}
					,{text:'返回',
						handler:function(){
						window.location.href = "deviceClass.do";			
					}
					}],
			buttonAlign:'center',
			stripeRows: true,
			frame:true,
			autoExpandColumn: 'chineseName',
			bbar:new Ext.PagingToolbar({
			pageSize:28,
			store:routerStore,
			displayInfo:true,
			displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg:'没有数据'
			})
		});	 
		routerPanel.addListener('rowdblclick', rowClickFn);     
  			function rowClickFn(grid, rowIndex, e) { 
  				var s=routerPanel.getStore();
  				var router=s.getAt(rowIndex);    
			    var rid=router.get("id");  
			    window.location.href = "equip_view.do?id="+rid+"&deviceTypeId="+id.deviceTypeId+"&style=3";  
				}
				
			routerPanel.addListener('rowcontextmenu', rightClickFn);//右键菜单代码关键部分 
			var rightClick = new Ext.menu.Menu({ 
			    id:'rightClickCont', 
			    items: [ 
			        { 
			            //id: 'rMenu1', 
			            //handler: rMenu1Fn, 
			            text: '右键菜单1' 
			        }, 
			        { 
			            //id: 'rMenu2', 
			            //handler: rMenu2Fn, 
			            text: '右键菜单2' 
			        } 
			    ] 
			}); 
			//右键菜单代码关键部分 
			function rightClickFn(grid,rowindex,e){ 
			    e.preventDefault(); 
			    rightClick.showAt(e.getXY()); 
			} 		
				
        routerPanel.render('showGrid2');
   		//routerPanel.getSelectionModel().selectFirstRow();
   		
   		
   		
}