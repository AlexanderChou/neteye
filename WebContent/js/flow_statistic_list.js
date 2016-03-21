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
		var fromTime = obj.fromTime;
		var toTime = obj.toTime;
		//alert("toTime="+toTime);
		//alert("fromTime="+fromTime);
		
		var reader = new Ext.data.JsonReader({
			root : 'flows',
			totalProperty:'totalCount',
			fields : ['linkName','loopbackIP','ifIndex','averageFlow','maxFlow','minFlow','totalFlow','startTime','endTime']
		});
		var titleStr = '流量统计';
		var eventStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'json/flowStatisticList.do?ip=' + ip + "&ifindex=" + ifindex + "&startTime=" + startTime + "&endTime=" + endTime +"&viewName=" +viewName
				}),
				//创建JsonReader读取router记录
				reader : reader
		});
		eventStore.setDefaultSort('loopbackIP','DESC'); 
		eventStore.load({params:{start:0,limit:28}});
	    var eventPanel = new Ext.grid.GridPanel({			
		store : eventStore,
		autoScroll:true,
		width:840,
	    height:document.body.clientHeight*0.95+7,
	    title:titleStr,
		columns : [
			{header: "链路名称",width: 150, sortable: true,renderer: change, dataIndex: 'linkName'}, 
			{header: "设备IP",width: 150,sortable: true,dataIndex : 'loopbackIP'},
			{header: '端口', width: 150 ,sortable: true,dataIndex: 'ifIndex'}, 
			{header: "平均流量(bps)(入/出)", width: 150,sortable: true,dataIndex : 'averageFlow'},
			{header: "峰值流量(bps)(入/出)", width: 150,sortable: true,dataIndex : 'maxFlow'},
			{header: "最小流量(bps)(入/出)", width: 150,sortable: true,dataIndex : 'minFlow'},
			{header: "总字节数(Bytes)(入/出)", width: 150,sortable: true,dataIndex : 'totalFlow'},			
			{header: "开始时间", width: 100,sortable: true,dataIndex : 'startTime'},			
			{header: "结束时间", width: 100,sortable: true,dataIndex : 'endTime'}
		],
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		buttons: [{text:'文本格式下载',
						handler:function(){
							window.open(_url+".txt",'_self','width=1,height=1,toolbar=no,menubar=no,location=no');			
						}
					},
					{
						text:'xls格式下载',
						handler:function(){
							window.open(_url+".xls",'_self','width=1,height=1,toolbar=no,menubar=no,location=no');
						}
					}],
		 buttonAlign:'center',
		 stripeRows: true,
		 frame:true,
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