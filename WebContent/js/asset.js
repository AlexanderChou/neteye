/*
 * Ext JS Library 2.3.0
 * Copyright(c) 2006-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.onReady(function(){

    Ext.QuickTips.init();

    var xg = Ext.grid;
    //表格数据
		var sumdatareader = new Ext.data.JsonReader({
				root : "assetSums",
			    // idProperty:'taskId',
				fields : ['totalname','totalmoney','totalnumber','groupname']
			});
	var sumdataproxy = new Ext.data.HttpProxy({
				url : "json/assetSumList.do"
			});

	
	sumdataStore = new Ext.data.GroupingStore({
				proxy : sumdataproxy,
				reader : sumdatareader,
				 sortInfo:{field: 'totalmoney', direction: "ASC"},
             groupField:'groupname'
			});
	sumdataStore.setDefaultSort('totalname', 'ASC');
	sumdataStore.load();
    

    Ext.grid.GroupSummary.Calculations['totalCost'] = function(v, record, field){
        return v + (record.data.estimate * record.data.rate);
    }

    var summary = new Ext.grid.GroupSummary(); 

    var grid = new xg.EditorGridPanel({
       // ds: 
       store:sumdataStore,
        columns: [
            {
                id: 'totalname',
                header: "统计项",
                width: 80,
                sortable: true,
                dataIndex: 'totalname',
                summaryType: 'count',
                hideable: false,
                summaryRenderer: function(v, params, data){
                    return ((v === 0 || v > 1) ? '(' + v +' 项)' : '(1 项)');
                },
                editor: new Ext.form.TextField({
                   allowBlank: false
                })
            },{
                header: "groupname",
                width: 20,
                sortable: true,
                dataIndex: 'groupname'
            },{
                header: "设备数目",
                width: 20,
                sortable: true,
                dataIndex: 'totalnumber',
                summaryType:'sum',
                renderer : function(v){
                    return v +' 台';
                },
                editor: new Ext.form.NumberField({
                   allowBlank: false,
                   allowNegative: false,
                   style: 'text-align:left'
                })
            },{
                id: 'totalmoney',
                header: "总金额",
                width: 20,
                sortable: false,
                groupable: false,
                renderer: function(v){
                    return v+ '元';
                },
                dataIndex: 'totalmoney',
                summaryType:'sum'
             //   summaryRenderer: Ext.util.Format.usMoney
            }
        ],

        view: new Ext.grid.GroupingView({
            forceFit:true,
            showGroupName: false,
            enableNoGroups:false, // REQUIRED!
            hideGroupedColumn: true
        }),

        plugins: summary,

        frame:true,
       width : 840,
		height : document.body.clientHeight * 0.95 + 5,
        clicksToEdit: 1,
        collapsible: true,
        animCollapse: false,
        trackMouseOver: false,
        //enableColumnMove: false,
        title: '资产管理总计',
        iconCls: 'icon-grid'
       
    });
    
    grid.render('showGrid');
});

