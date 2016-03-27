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
Ext.apply(Ext.form.VTypes, {
  daterange: function(val, field) {
    var date = field.parseDate(val);
    
    // We need to force the picker to update values to recaluate the disabled dates display
    var dispUpd = function(picker) {
      var ad = picker.activeDate;
      picker.activeDate = null;
      picker.update(ad);
    };
    
    if (field.startDateField) {
      var sd = Ext.getCmp(field.startDateField);
      sd.maxValue = date;
      if (sd.menu && sd.menu.picker) {
        sd.menu.picker.maxDate = date;
        dispUpd(sd.menu.picker);
      }
    } else if (field.endDateField) {
      var ed = Ext.getCmp(field.endDateField);
      ed.minValue = date;
      if (ed.menu && ed.menu.picker) {
        ed.menu.picker.minDate = date;
        dispUpd(ed.menu.picker);
      }
    }
    /* Always return true since we're only using this vtype
     * to set the min/max allowed values (these are tested
     * for after the vtype test)
     */
    return true;
  }
});
Ext.onReady(function(){  
		var s = window.location.search.substring(1);
		var obj = Ext.urlDecode(s);
		var objName = obj.objName;
		var objId = obj.objId;
		
		var reader = new Ext.data.JsonReader({
			root : 'events',
			totalProperty:'totalCount',
			fields : ['id','occurNum','occurTime','receiveTime','moduleId','moduleType','typeValue','priority','objType','objName','objIPv4','objIPv6','title','content']
		});
		var titleStr = '事件列表(设备：'+objName+')';
		var eventStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'json/objEvenList.do?objId='+objId
				}),
				//创建JsonReader读取router记录
				reader : reader
		});
		eventStore.setDefaultSort('occurTime','DESC'); 
		eventStore.load({params:{start:0,limit:28}});
	    var eventPanel = new Ext.grid.GridPanel({			
		store : eventStore,		
		autoScroll:true,
	    height:document.body.clientHeight*0.95+5,
	    width:840,
	    title:titleStr,
		columns : [
			{header: "事件id",hidden:true,sortable: true,dataIndex: 'id'},
			{header: "发生次数",width: 70, sortable: true,renderer: change, dataIndex: 'occurNum'}, 
			{header: "发生时间",width: 130,sortable: true,dataIndex : 'occurTime'},
			{header: '收到时间', width: 130 ,sortable: true,dataIndex: 'receiveTime'}, 
			{header: "模块名称", width: 100,sortable: true,dataIndex : 'moduleId'},
			{header: "事件类型", width: 70,sortable: true,dataIndex : 'moduleType'},
			{header: "事件值", width: 70,sortable: true,dataIndex : 'typeValue'},
			{header: "优先级", width: 50,sortable: true,dataIndex : 'priority'},			
			{header: "IPv4", width: 100,sortable: true,dataIndex : 'objIPv4'},
			{header: "IPv6", width: 130,sortable: true,dataIndex : 'objIPv6'},
			{header: "事件主题", width: 80,sortable: true,dataIndex : 'title'},
			{header: "事件内容", width: 80,sortable: true,dataIndex : 'content'}
		],
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		tbar:[	new Ext.Toolbar.TextItem('显示条目:'),
	      	{	pressed:true,
				text:'最近100条',
				handler:function(){
				  	  var ss = new Ext.data.Store({
		                   proxy:new Ext.data.HttpProxy({url:'json/objEvenList.do?objId=' + objId + "&howMuch=" + "0"}),
		                   reader:reader
				  	  });
				  	  ss.load({callback:function(){
		                    eventStore.removeAll();
		                    eventStore.add(ss.getRange());
				  	  }});
				}
			},
			{	pressed:true,
	      		text:'今日',
	      		handler:function(){
				  	  var ss = new Ext.data.Store({
		                   proxy:new Ext.data.HttpProxy({url:'json/objEvenList.do?objId=' + objId + "&howMuch=" + "1"}),
		                   reader:reader
				  	  });
				  	  ss.load({callback:function(){
		                    eventStore.removeAll();
		                    eventStore.add(ss.getRange());
				  	  }});
				}
	      	},'从',					
			new Ext.form.DateField({
				fieldLabel: '开始时间',
				id:'fromDate',
				name:'fromDate',
				vtype: 'daterange',
				format: 'Y-m-d',
        		endDateField: 'toDate' 
			}),'到',
			new Ext.form.DateField({
				fieldLabel: '结束时间',
				id:'toDate',
				name:'toDate',
				vtype: 'daterange',
				format: 'Y-m-d', 
				startDateField: 'fromDate'
			}),
			{	pressed:true,
	      		text:'按时段查询',
				handler:function(){
				  	  var ss = new Ext.data.Store({
		                            proxy:new Ext.data.HttpProxy({url:'json/objEvenList.do?howMuch=' + '2&fromTime='+Ext.get("fromDate").getValue()+'&toTime='+Ext.get("toDate").getValue()}),
		                            reader:reader
		                     });
                     ss.load({
                     params: {
						objId : objId
					 },
                     callback:function(){
                             eventStore.removeAll();
                             eventStore.add(ss.getRange());
                     }});
				}
			}
	     ],
	     bbar:new Ext.PagingToolbar({
			pageSize:28,
			store:eventStore,
			displayInfo:true,
			displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			emptyMsg:'没有数据'
		})
	});	 

	eventPanel.getSelectionModel().on('rowselect',function(sm,rowIdx,/*Ext.data.Record*/r) {
		 window.location.href = "objEventSeq.do?eventId="+r.get("id")+"&objName="+r.get("objName");  
	});
	function change(val){
           return '<span style="color:blue;">' + val + '</span>';
   	}
    eventPanel.render('showGrid');
});