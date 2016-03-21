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
/*
 * Ext JS Library 2.1
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
function  createPortGrid(){

    Ext.QuickTips.init();
    
    var s = window.location.search.substring(1);
    var parameter = Ext.urlDecode(s);
    var xg = Ext.grid;
	var portStore =  new Ext.data.GroupingStore({
        	proxy : new Ext.data.HttpProxy({
        		url : 'faultjson/smallIcon.do?deviceTypeId='+parameter.deviceTypeId
        	}),
            reader: new Ext.data.JsonReader({
					root : 'portList',
					totalProperty:'totalCount',
					fields : ['id','name','ipv4','ipv6','ifindex','maxSpeed','deviceName','deviceId']
				}),
            sortInfo:{field: 'name', direction: "ASC"},
            groupField:'deviceName'
        });
        portStore.load();
    // shared reader
    var gridView = new Ext.grid.GroupingView({
            forceFit:true,
            startCollapsed:true,
            groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Ports" : "Port"]})'
        });
    portStore.load({params:{start:0,limit:28}});
    var grid = new xg.GridPanel({
        store:portStore,
		height:document.body.clientHeight*0.95-136,
		width:835,
        columns: [
            {id:'name',header: "名称", width: 80, sortable: true, dataIndex: 'name'},
            {header: "接口IP地址", width: 110, sortable: true,  dataIndex: 'ipv4'},
            {header: "接口IPV6地址", width: 110, sortable: true, dataIndex: 'ipv6'},
            {header: "接口索引", width: 110, sortable: true, dataIndex: 'ifindex'},
            {header: "接口速率", width: 120, sortable: true, dataIndex: 'maxSpeed'},
            {header: "设备名称", width: 120, sortable: true, dataIndex: 'deviceName'}
        ],
        view:gridView,
		tbar: [		"->",
					{text:'添加',
						handler:function(){
						window.location.href = "deviceAdd.do?sstatus=1&id="+parameter.deviceTypeId+"&style=2";			
						}
					},"|"
					,{text:'返回',
						handler:function(){
							history.back();		
						}
					}],
		buttonAlign:'center',
        frame:true,
        title: '设备列表',
        bbar:new Ext.PagingToolbar({
			pageSize:28,
			store:portStore,
			displayInfo:true,
			displayMsg:'每页28条设备记录，显示从第 {0}设备开始，一共 {2} 条设备',
			emptyMsg:'没有数据'
		})
    });
    	grid.addListener('rowdblclick', rowClickFn);     
  			function rowClickFn(grid, rowIndex, e) { 
  				var s=grid.getStore();
  				var port=s.getAt(rowIndex);    
			    var rid=port.get("id");  
			   window.location.href = "port_view.do?id="+rid+"&style=2";
				}
    grid.render('showGrid');
    return grid;
}

// add in some dummy descriptions