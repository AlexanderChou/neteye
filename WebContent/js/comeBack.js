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

	sm = new Ext.grid.CheckboxSelectionModel();

	var Record = new Ext.data.Record.create([
		{name:"name", mapping:"name"},
		{name:"time", mapping:"time"},
		{name:"userName", mapping:"userName"},
		{name:"recover"}
	]);
	
	var reader = new Ext.data.JsonReader({
		root:"dataList",
		totalProperty:'totalCount'
		},
		Record
	    );
	
	var proxy = new Ext.data.HttpProxy({
		url:"json/resume.do"
	});
	
	var colm = new Ext.grid.ColumnModel([
		{id:'name', header:"数据备份名称",sortable: true,width:200,align:'center',dataIndex:"name",editor:new Ext.form.Field()},
		{header:"数据备份时间",sortable: true,width:200,align:'center',dataIndex:"time"},
		{header:"数据备份人员",sortable: true,width:200,align:'center',dataIndex:"userName"},
		{header:"数据恢复",dataIndex:"name",width:176,align:'center',renderer:showUrl},
		sm
	]);
	
	store = new Ext.data.Store({
		proxy:proxy,
		reader:reader
	});
	store.load({params:{start:0,limit:28}});
	var grid = new Ext.grid.EditorGridPanel({
		title:"数据备份列表",
		store:store,
		height: document.body.clientHeight*0.95+5,
		width: 840,
		cm:colm,
		frame:true,
		autoScroll:true,
		renderTo:"showDiv",
		buttonAlign:"center",
		autoExpandColumn:"name",
		sm:sm,	
		buttons: [{
			text:'删除',
			handler:function(){
				var selected = sm.getSelections();
				var names = "";
				for(var i=0;i<selected.length;i+=1){
					var backup = selected[i].data;
					if(backup.name) {
					names=names+";"+backup.name;					
					}
				}
				if(names=='') {
				    alert("请选中要删除的备份名字！");
					return;
				}
				Ext.Msg.confirm('信息','确定要删除所选项吗?',function(btn){
					if(btn=='yes'){	
						Ext.Ajax.request({
							url:"json/delBackUp.do",
							timeout:"90000",
							method:"post",
							params:{names:names},
							success:function(response, request){
								var data = Ext.decode(response.responseText);
								var message=Ext.Msg.show({
									title:'备份结果 ',	
									buttons:Ext.MessageBox.YESNO,							
									msg:Ext.decode(response.responseText).mesg,
									fn:function(btn){
										if(btn=='yes'){
											window.location.href="comeBack.do";
										}
										else{
											message.close();
										}
									}
							 });
							}
						  });
					   }
					});	
				}
			},{text:'上传',
				handler:function(){
				window.location.href = "";			
			}
			},{text:'下载',
				handler:function(){
				window.location.href = "";			
			}
			}],
		bbar:new Ext.PagingToolbar({
			pageSize:28,
			store:store,
			displayInfo:true,
			displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg:'没有数据'
		})
	});
	var panel = new Ext.Panel({
		layout:"fit",
		renderTo:"showDiv",
        items : [grid]
	})
});

function showUrl(value)
{
return '<a href="javascript:recover(\''+value+'\')">数据恢复</a>';
}
function recover(value){

Ext.Msg.confirm('信息','确定要恢复吗，确定后现有的数据将被覆盖?',function(btn){
	if(btn=='yes'){	
		Ext.Ajax.request({
			url:"json/recover.do",
			timeout:"90000",
			method:"post",
			params:{backUpName:value},
			success:function(response, request){
				var data = Ext.decode(response.responseText);
				var message=Ext.Msg.show({
					title:'数据恢复结果 ',	
					buttons:Ext.MessageBox.YESNO,							
					msg:Ext.decode(response.responseText).mesg,
					fn:function(btn){
						if(btn=='yes'){
							window.location.href="comeBack.do";
						}
						else{
							message.close();
						}
					}
			 });
			}
		  });
	   }
	});	
}