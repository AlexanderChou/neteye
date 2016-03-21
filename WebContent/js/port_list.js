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
       var s = window.location.search.substring(1);
        var pairs = s.split("&");
       var id = Ext.urlDecode(pairs[0]);
        var id2= Ext.urlDecode(pairs[1]);
        
Ext.onReady(function(){  
		var xg = Ext.grid;
		var sm = new xg.CheckboxSelectionModel();
		var portStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'json/portList.do?deviceId='+id.deviceId+'&style='+id2.style
				}),
				//创建JsonReader读取port记录
				reader : new Ext.data.JsonReader({
					root : 'portList',
					totalProperty:'totalCount',
					fields : ['id','deviceId','name','chineseName','ipv4','ipv6','ifindex','netmask','maxSpeed','description','interfaceType','lowerThreshold','upperThreshold','trafficFlag']
				})
		});
		portStore.setDefaultSort('trafficFlag','DESC');
		portStore.load({params:{start:0,limit:28}});
		var portPanel = new Ext.grid.GridPanel({			
			store : portStore,
//			collapsible: true,
			autoScroll:true,
			height:document.body.clientHeight*0.95 + 5,
			width:840,
        	title:'接口列表',
			columns : [
				new Ext.grid.RowNumberer(),
				sm,
				{header: "监控状态", width: 80, sortable: true, dataIndex: 'trafficFlag',renderer : colorstutas}, 
				{header: "描述", width: 60, sortable: true, dataIndex: 'description'}, 
				{header: "接口索引", width: 30, sortable: true, dataIndex: 'ifindex'}, 
				{header: "名称", width: 60, sortable: true, dataIndex: 'name'},				
				{id: "chineseName", header: "中文名称",width: 50,sortable: true,dataIndex : 'chineseName'},
				{header: 'ipv4地址', width: 100 ,sortable: true,dataIndex: 'ipv4'}, 
				{header: "子网掩码数", width: 60, sortable: true,hidden: true, dataIndex: 'netmask'},
				{header: "ipv6地址", width: 150,sortable: true,dataIndex : 'ipv6'},
				{header: "最大速率", width: 60, sortable: true, dataIndex: 'maxSpeed'},
				{header: "最低阈值", width: 60, sortable: true, hidden: true,dataIndex: 'lowerThreshold'},
				{header: "最高阈值", width: 60, sortable: true, hidden: true,dataIndex: 'upperThreshold'},
				{header: "接口类型", width: 60, sortable: true, dataIndex: 'interfaceType'},
				{header: "监控", width: 60, sortable: true, dataIndex: 'id',renderer : dotraffic},
				{header: "操作", width: 60, sortable: true, dataIndex: 'id',renderer : delid}
				],
				stripeRows: true,
				frame:true,
				sm:sm,
			tbar: ['->',{
						text:'批量删除',
						handler:function(){
							var selected = sm.getSelections();
							var ids = "";
							for(var i=0;i<selected.length;i+=1){
								var port = selected[i].data;
								if(port.id) {
								ids=ids+";"+port.id; 
								}
							}
							if(ids=='') return;
							Ext.Msg.confirm('信息','确定要删除所选项吗?',function(btn){
								if(btn=='yes'){	
								window.location.href = "port_delete.do?submitPort="+ids+"&deviceId="+id.deviceId+"&style="+id2.style;
								}
								});	
						}
					},'|',
					{
						text:'批量更改流量监控状态',
						handler:function(){
							var selected = sm.getSelections();
							var ids = "";
							for(var i=0;i<selected.length;i+=1){
								var port = selected[i].data;
								if(port.id) {
								ids=ids+";"+port.id; 
								}
							}
							if(ids==''){
							 alert("请选择需要更改监控状态的接口！");
							 return;
							 }
							Ext.Msg.confirm('信息','确定要更改所选的接口监控状态吗?',function(btn){
								if(btn=='yes'){							
								Ext.Ajax.request({								   
									url:"json/portflow.do",
									timeout:"90000",
									method:"post",
									params:{submitPort:ids},
									success:function(response, request){
										var data = Ext.decode(response.responseText);
										Ext.Msg.buttonText.yes='确定';
										Ext.Msg.buttonText.no="取消";
										var message=Ext.Msg.show({
											title:'处理结果 ',								
											buttons:Ext.MessageBox.YESNO,
											msg:Ext.decode(response.responseText).mesg,
											fn:function(btn){
												if(btn=='yes'){
													window.location.href="toport.do?deviceId="+id.deviceId+"&style="+id2.style;
												}
												if(btn=='no'){
													message.close();
												}
											}
									  });
									}
								  });
								}
							});	
						}
					},'|',
					{text: '添加接口',
			            handler:function(){
			            	window.location.href="portforward.do?id=" + device_id + "&style=" + deviceType;
			            }
			         },'|',
					{text:'返回',
						handler:function(){
						window.location.href = "equip_view.do?id="+id.deviceId+"&style="+id2.style;			
					}
					}],
			buttonAlign:'center',			
			autoExpandColumn: 'chineseName',
			bbar:new Ext.PagingToolbar({
			pageSize:28,
			store:portStore,
			displayInfo:true,
			displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg:'没有数据'
			})
		});	 
		portPanel.addListener('rowdblclick', rowClickFn);     
  			function rowClickFn(grid, rowIndex, e) { 
  				var s=portPanel.getStore();
  				var port=s.getAt(rowIndex);    
			    var id=port.get("id");  
			    window.location.href = "port_view.do?id="+id+"&style="+id2.style;  
				}
//		 portStore.on('load',function(store,records,options){   
//		    var arr=[];   
//		    for(var i=0;i<records.length;i++){   
//		      var record = records[i];   
//		      var isMatch = record.get('trafficFlag');   
//		      if(isMatch==1){   
//		        arr.push(record);   
//		      }   
//		    }   
//		    sm.selectRecords(arr);   
//		  },this,{delay:100})   
        portPanel.render('showGrid');
   		//portPanel.getSelectionModel().selectFirstRow();
});



function colorstutas(value){
    	if (value=='0'){
   // 	return "<img src='/images/black.gif' />";
   	return "<font color='gray'>流量未监控</font>";

    } else if(value=='1'){
     //   return "<img src='/images/green.gif' />";
    	 	return "<font color='#33CC00'>流量已监控</font>";
    }else{
	//	return "<img src='/images/red.gif' />";
       return "<font color='#FF0000'>监控状态异常</font>";
    }	
	}
function delid(val){
return '<a href="javascript:deDel(\''+val+'\')">删除</a>';		
}
function dotraffic(val){
	
return '<a href="javascript:changetraffic(\''+val+'\')">监控变更</a>';		
}
function changetraffic(value){
		Ext.Msg.confirm('信息','确定要更改所选的接口监控状态吗?',function(btn){
								if(btn=='yes'){							
								Ext.Ajax.request({								   
									url:"json/portflow.do",
									timeout:"90000",
									method:"post",
									params:{submitPort:value},
									success:function(response, request){
										var data = Ext.decode(response.responseText);
										Ext.Msg.buttonText.yes='确定';
										Ext.Msg.buttonText.no="取消";
										var message=Ext.Msg.show({
											title:'处理结果 ',								
											buttons:Ext.MessageBox.YESNO,
											msg:Ext.decode(response.responseText).mesg,
											fn:function(btn){
												if(btn=='yes'){
													window.location.href="toport.do?deviceId="+id.deviceId+"&style="+id2.style;
												}
												if(btn=='no'){
													message.close();
												}
											}
									  });
									}
								  });
								}
							});	
	
};
function deDel(value){
		Ext.Msg.confirm('信息','确定要删除所选项吗?',function(btn){
								if(btn=='yes'){	
								window.location.href = "port_delete.do?submitPort="+value+"&deviceId="+id.deviceId+"&style="+id2.style;
								}
								});	
	
};