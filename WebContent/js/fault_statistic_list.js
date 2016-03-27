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
			root : 'faults',
			fields : [
					  {name:"deviceName", mapping:0},
					  {name:"deviceIP", mapping:1},
					  {name:"faultHappenCount", mapping:2},
					  {name:"faultTotalTime", mapping:3},
					  {name:"maxTime", mapping:4},
					  {name:"averageTime", mapping:5},
					  {name:"faultOccurrence", mapping:6}
						]
		});
		var titleStr = '故障统计';
		var eventStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'json/faultStatisticList.do?startTime=' + startTime + "&endTime=" + endTime +"&ip=" +ip +"&viewName="+viewName
				}),
				//创建JsonReader读取router记录
				reader : reader
		});
		eventStore.setDefaultSort('deviceIP','DESC'); 
		eventStore.load({params:{start:0,limit:28}});
	    var eventPanel = new Ext.grid.GridPanel({		
		store : eventStore,
		autoScroll:true,
		width:840,
	    height:document.body.clientHeight*0.95+7,
	    title:titleStr,
		columns : [
			//{header: "设备名称",sortable: true,dataIndex: 'deviceName'},
			{header: "设备IP",width: 110,sortable: true,dataIndex : 'deviceIP', id:"IP",renderer:showip},
			{header: '故障发生次数', width: 110 ,sortable: true,dataIndex: 'faultHappenCount'}, 
			{header: "发生故障的总的时间", width: 150,sortable: true,dataIndex : 'faultTotalTime'},
			{header: "故障占用时间的最大值", width: 150,sortable: true,dataIndex : 'maxTime'},
			{header: "一个故障的平均占用时间", width: 150,sortable: true,dataIndex : 'averageTime'},
			{header: "故障发生率", width: 150,sortable: true,dataIndex : 'faultOccurrence',renderer:showhan}
		],
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
	    autoExpandColumn: 'IP',
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
		 frame:true
//		 ,
//	     bbar:new Ext.PagingToolbar({
//			pageSize:28,
//			store:eventStore,
//			displayInfo:true,
//			displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',
//			emptyMsg:'没有数据'
//		})
	});	 
	function change(val){
           return '<span style="color:blue;">' + val + '</span>';
   	}
   	function showhan(val){
           return  val + '%';
   	}
   	function showip(value)
{
	if(value.indexOf(":")>0){
		return "<font color=#0000FF >"+value+"</font>";
	}else {
		return value;
	}
}
       eventPanel.render('showGrid');
});