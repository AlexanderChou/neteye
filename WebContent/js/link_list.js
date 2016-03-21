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

Ext.onReady(function(){  
		var xg = Ext.grid;
		var sm = new xg.CheckboxSelectionModel();
		linkStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'json/linkList.do'
				}),
				//创建JsonReader读取router记录
				reader : new Ext.data.JsonReader({
					root : 'linkList',
					totalProperty:'totalCount',
					fields : ['id','name','description','upInterface.ipv4','downInterface.ipv4','upInterface.ipv6','downInterface.ipv6','upInterface.ifindex','downInterface.ifindex','upDevice','downDevice']
				})
		});
		linkStore.setDefaultSort('name','DESC'); 
		linkStore.load({params:{start:0,limit:28}});
		var linkPanel = new Ext.grid.GridPanel({			
			store : linkStore,
			height:document.body.clientHeight * 0.95 + 5,
        	width:840,
			autoScroll:true,
        	title:'链路列表',
			columns : [
				new Ext.grid.RowNumberer(),
				{id:"name",header: "名称", width: 10, sortable: true, dataIndex: 'name'}, 
				{header: "上链地址",width: 90,sortable: true,dataIndex : 'upInterface.ipv4'},
				{header: '下链地址', width: 90 ,sortable: true,dataIndex: 'downInterface.ipv4'}, 
				{header: "上链v6地址", width: 90,sortable: true,dataIndex : 'upInterface.ipv6'},
				{header: "下链v6地址", width: 90,sortable: true,dataIndex : 'downInterface.ipv6'},
				{header: "上链端口索引", width: 90,sortable: true,dataIndex : 'upInterface.ifindex'},
				{header: "下链端口索引", width: 90,sortable: true,dataIndex : 'downInterface.ifindex'},
				{header: "链路描述", width: 90,sortable: true,dataIndex : 'description'},
				 sm
				],
				sm:sm,
			tbar: ['->',
					{text:'添加',
						handler:function(){
						addRouter();			
					}
					},"|",
					{
						text:'删除',
						handler:function(){
							var selected = sm.getSelections();
							var ids = "";
							for(var i=0;i<selected.length;i+=1){
								var link = selected[i].data;
								if(link.id) {
								ids=ids+";"+link.id; 
								}
							}
							if(ids==''){
							 alert("没有链路被选中！");
							 return;
							 }
							else{
							window.location.href = "link_delete.do?submitId="+ids; 
							}	
						}
					}],
			stripeRows: true,
			frame:true,
			autoExpandColumn: 'name',
			bbar:new Ext.PagingToolbar({
			pageSize:28,
			store:linkStore,
			displayInfo:true,
			displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg:'没有数据'
		})
		});	 
		linkPanel.addListener('rowdblclick', rowClickFn);     
  			function rowClickFn(grid, rowIndex, e) { 
  				var s=linkPanel.getStore();
  				var link=s.getAt(rowIndex);    
			    var rid=link.get("id");  
			    window.location.href = "link_view.do?id="+rid;  
				}

        linkPanel.render('showGrid');
   		//linkPanel.getSelectionModel().selectFirstRow();
});