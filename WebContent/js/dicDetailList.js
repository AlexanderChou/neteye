Telewave.oModelConfig.Id='dicContenId';
Telewave.oModelConfig.Url.Query = 'queryDicContentList.do?dicContent.dicTypeId='+dicTypeId;//列表
Telewave.oModelConfig.Url.DeleteM = 'deleteDicContent.do';
Telewave.oModelConfig.Url.DeleteMDone = 'deleteDicContent.do';
Telewave.oModelConfig.Url.Add= 'addDicContent.do';
Telewave.oModelConfig.Url.Update='updateDicContent.do';

Telewave.oModelConfig.Title={List:'基础数据详情列表',Add:'新增基础数据详情'};
Telewave.oModelConfig.Tip={Refresh:'刷新',Add:'新增',Update:'修改',Delete:'删除'};

Telewave.oModelConfig.JsonReaderConfig=[
 	{name : 'dicContenId',type : 'string'},
 	{name : 'dicTypeId',type : 'string'},
 	{name : 'dicContentDesc',type : 'string'}, 
 	{name : 'dicContentOrder',type : 'string'},
 	{name : 'dicContentName',type : 'string'}
];

Telewave.oModelConfig.ColumnConfig=
	[
	new Ext.grid.RowNumberer(),
	Telewave.DataModel.SM, 
		{header : '数据字典类型',dataIndex : 'dicTypeId',align : 'left',width : 100,sortable : true},
		{header : '字典内容名称',dataIndex : 'dicContentName',align : 'center',width : 100,sortable : true},
		{header : '优先级',align : 'center',dataIndex : 'dicContentOrder',width : 100,sortable : true},
		{header : '描述',align : 'center',dataIndex : 'dicContentDesc',width : 100,sortable : true,id : 'autoExpandColumn'}
	];
/**
 * 渲染修改操作
 * @param value
 * @param p
 * @param record
 */
function renderUpdate(value, p, record){
	return '<a onclick="oHandlerUpdate.Show()"><image src="'+path+'images/update.gif"></a>';
}

dicTypeStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'queryAllDicType.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'Data',
							fields : [{name:"dicTypeId", mapping:"dicTypeId"},
							{name:"dicTypeName", mapping:"dicTypeName"}
							]
						})
			});
dicTypeStore.load({
	callback:function(r,options,success){
		if(!success){
			Ext.Msg.alert('服务端出错','获取服务端数据出错');
		}
	}
});

var comDicTypeAdd= new Ext.form.ComboBox({                
   store: dicTypeStore,                                  
   valueField:'dicTypeId',
   displayField:'dicTypeName',
   hiddenName:'dicContent.dicTypeId',                    
   mode: 'remote',                    
   forceSelection: true,                    
   blankText: '请选择字典类型',                    
   emptyText: '请选择字典类型',                                      
   editable: false,                    
   triggerAction: 'all',
   value:getDicTypeValue(),
   allowBlank: true,                    
   fieldLabel: '字典类型',                    
   name: 'prodefId',                    
   width:150            
});

function getDicTypeValue(){
	if(dicTypeId!="null"){
		return dicTypeId;
	}else{
		return '';
	}
}


var comDicTypeUpdate= new Ext.form.ComboBox({                
	   store: dicTypeStore,                                  
	   valueField:'dicTypeId',
	   displayField:'dicTypeName',
	   hiddenName:'dicContent.dicTypeId',                    
	   mode: 'remote',                    
	   forceSelection: true,                    
	   blankText: '请选择字典类型',                    
	   emptyText: '请选择字典类型',                                      
	   editable: false,                    
	   triggerAction: 'all',                    
	   allowBlank: true,                    
	   fieldLabel: '字典类型',                    
	   name: 'prodefId',                    
	   width:150            
	});

var comDicTypeSearch= new Ext.form.ComboBox({                
	   store: dicTypeStore,                                  
	   valueField:'dicTypeId',
	   displayField:'dicTypeName',
	   hiddenName:'dicContent.dicTypeId',                    
	   mode: 'remote',                    
	   forceSelection: true,                    
	   blankText: '请选择字典类型',                    
	   emptyText: '请选择字典类型',                                      
	   editable: false,                    
	   triggerAction: 'all',                    
	   allowBlank: true,                    
	   fieldLabel: '字典类型',                    
	   name: 'prodefId',                    
	   width:150            
	});


var oModelConfig = {
		ModelName:'dicContent',
		//表单域组-添加
		FormAdd:[
		    { layout:'column', border:false, items:[     
                { columnWidth:.5, layout: 'form', border:false, items: [
                    comDicTypeAdd
                ]},
                { columnWidth:.5, layout: 'form', border:false, items: [
    				{ xtype:'textfield', width:150,fieldLabel: '字典名称', name: 'dicContent.dicContentName' }
                ]}
            ]},
            { layout:'column', border:false, items:[     
                { columnWidth:.5, layout: 'form', border:false, items: [
                	{ xtype:'numberfield', width:150,fieldLabel: '优先级', name: 'dicContent.dicContentOrder',minValue:1,minText:'数值不能少于1',maxValue:100,maxText:'数值不能大于100',vtypeText:'请输入有效的数字' }
                ]}
             ]},  
		    { layout:'column', border:false, items:[
		        { columnWidth:.99, layout: 'form', border:false, items: [
		        	{ xtype:'textarea', width:388,fieldLabel: '字典内容描述', name: 'dicContent.dicContentDesc' }
		        ]}
		    ]}
		],
		FormUpdate:[
		        { xtype:'hidden',name: 'dicContent.dicContenId'},    
	            { layout:'column', border:false, items:[     
		            { columnWidth:.5, layout: 'form', border:false, items: [
                        comDicTypeUpdate
		            ]},
		            { columnWidth:.5, layout: 'form', border:false, items: [
						{ xtype:'textfield', width:150,fieldLabel: '字典名称', name: 'dicContent.dicContentName' }
		            ]}
	            ]},
	            { layout:'column', border:false, items:[     
	                { columnWidth:.5, layout: 'form', border:false, items: [
	                	{ xtype:'textfield', width:150,fieldLabel: '优先级', name: 'dicContent.dicContentOrder' }
	                ]}
	             ]},  
			    { layout:'column', border:false, items:[
			        { columnWidth:.99, layout: 'form', border:false, items: [
			        	{ xtype:'textarea', width:388,fieldLabel: '字典内容描述', name: 'dicContent.dicContentDesc' }
			        ]}
			    ]}
		],
		FormQuery:[
			{ layout:'column', border:false, items:[
                { columnWidth:.33, layout: 'form', border:false, items: [
                    comDicTypeSearch
                ]},
                { columnWidth:.33, layout: 'form', border:false, items: [
    				{ xtype:'textfield', width:150,fieldLabel: '字典类型名称', name: 'dicContent.dicContentName' }
                ]},
                { columnWidth:.1, layout: 'form', border:false, items: [
                	{ xtype:'button', text: '查询' ,handler:Telewave.Data.Refresh} 
                ]},
	            { columnWidth:.1, layout: 'form', border:false, items: [
	            	{ xtype:'button', text: '重置',handler:function(){
	            		frm.getForm().reset();
	            	}} 
	            ]}
//                ,
//	            { columnWidth:.1, layout: 'form', border:false, items: [
//	            	{ xtype:'button', text: '返回数据字典',handler:function(){
//	            		window.location.href="dicList.do";
//	            	}} 
//	            ]}
            ]}    
		]
	}


function renderHandler(value, p, record){
	var runId = record.get("runId");
	return '<a onclick="openImage('+runId+',\'runId\')"><image src="images/ProcessGraph.gif"/></a>';
}


//表单-添加
var frmAdd = new Ext.form.FormPanel({
    formId :'formAdd',
    url:Telewave.oModelConfig.Url.Add,
    labelWidth: 80,
	labelAlign:'right',
	height:120,
    frame:true,
    header:false,
    bodyStyle:'padding:5px 5px 0',
    items: oModelConfig.FormAdd
});

Telewave.oModelConfig.FormSearchId="formSearch";
var frm=new Ext.form.FormPanel({
	formId :'formSearch',
    region: 'north',
    labelWidth: 80,
	labelAlign:'right',
	collapsible: true,
	height:80,
    frame:false,
    border:false,
    header:true,
    title:'基础数据详情查询',
    bodyStyle:'padding:20px 10px 0px 20px',
	items:oModelConfig.FormQuery
});

//表单-添加
var frmUpdate = new Ext.form.FormPanel({
  formId :'formUpdate',
  url:Telewave.oModelConfig.Url.Update,
  labelWidth: 80,
  labelAlign:'right',
  height:120,
  frame:true,
  header:false,
  bodyStyle:'padding:5px 5px 0',
  items: oModelConfig.FormUpdate
});



var grid=null;
var winAdd=null;
var oHandlerAdd = {
	Show : function() {
		// 新增之前清空表单
		// frmAdd.form.reset();
		if (!winAdd) {
			winAdd = new Ext.Window({
				title: Telewave.oModelConfig.Title.Add,
                width: 535,
                height:230,
                layout: 'fit',
                closeAction:'hide',
                modal:true,
                minimizable:false,
                maximizable:false,
                resizable:false,
                plain:true,
                border:false,
                //bodyStyle:'padding:5px;',
                items: frmAdd,
                iconCls:'CssIconForm',
                buttons: aButtonAdd
			});
		}
		winAdd.show();
		frmAdd.getForm().reset();
	},
	Save : function() {
		var flag=frmAdd.form.isValid();
		if(flag){
			frmAdd.getForm().submit({
				success : function(form, action) {
					winAdd.hide();
					Telewave.Data.Refresh();
				},failure : function(form, action) {		
					
				}
			});	
		}
	},
	Reset:function(){
		frmAdd.getForm().reset();
	},
	Close : function() {
		winAdd.hide();
	}
}

var winUpdate=null;
var oHandlerUpdate = {
		Show : function() {
			// 新增之前清空表单
			var aSelections = Telewave.DataModel.SM.getSelections();
			if(aSelections.length==0){
				Ext.onReady(function() { 
					Ext.Msg.alert('提示', '请选择要修改的记录'); 
					}); 
				return;
			}else if(aSelections.length>1){
				Ext.onReady(function() { 
					Ext.Msg.alert('提示', '一次只能修改一条记录'); 
					}); 
				return;
			}else{
				rSelected = aSelections[0];
				if (!winUpdate) {
					winUpdate = new Ext.Window({
						title: Telewave.oModelConfig.Title.Update,
		                width: 535,
		                height:230,
		                layout: 'fit',
		                closeAction:'hide',
		                modal:true,
		                minimizable:false,
		                maximizable:false,
		                resizable:false,
		                plain:true,
		                border:false,
		                //bodyStyle:'padding:5px;',
		                items: frmUpdate,
		                iconCls:'CssIconForm',
		                buttons: aButtonUpdate
					});
				}
				winUpdate.show();
				 var oModel = {};
	             for(var sFieldName in rSelected.data){
		                oModel[oModelConfig.ModelName + '.'+sFieldName] = rSelected.data[sFieldName];
	             }
	             frmUpdate.getForm().setValues(oModel);
			}
			
		},
		Save : function() {
			var flag=frmUpdate.form.isValid();
			if(flag){
				frmUpdate.getForm().submit({
					success : function(form, action) {
						 if(action.result.success){
							 winUpdate.hide();
							 Telewave.Data.Refresh();
						 }
					},failure : function(form, action) {		
						Ext.Msg.alert(action.result.tip,action.result.msg);
					}
				});	
			}
		},
		Reset:function(){
			// 新增之前清空表单
			var rSelected = Telewave.DataModel.SM.getSelected();
			 var oModel = {};
             for(var sFieldName in rSelected.data){
	                oModel[oModelConfig.ModelName + '.'+sFieldName] = rSelected.data[sFieldName];
             }
             frmUpdate.getForm().setValues(oModel);
		},
		Close : function() {
			winUpdate.hide();
		}
	}

// 按钮栏--新增
var aButtonAdd = [{
			text : '保存',
			handler : oHandlerAdd.Save,id:'addbutton'
		}, {
			text : '重置',
			handler : oHandlerAdd.Reset
		}, {
			text : '关闭',
			handler : oHandlerAdd.Close
		}];

//按钮栏--新增
var aButtonUpdate = [{
			text : '保存',
			handler : oHandlerUpdate.Save		
		},{
			text : '重置',
			handler : oHandlerUpdate.Reset
		}, {
			text : '关闭',
			handler : oHandlerUpdate.Close
		}];


var aToolBar = [];
/**
 * 鉴权
 */
function checkPermission(){
	var flag=true;
	if(flag){
		aToolBar.push({text:'新增', tooltip:Telewave.oModelConfig.Tip.Add, iconCls:'CssBtnAdd', handler:oHandlerAdd.Show});
		aToolBar.push('-');
	}if(flag){
		aToolBar.push({text:'修改', tooltip:Telewave.oModelConfig.Tip.Update, iconCls:'CssBtnUpdate',handler:oHandlerUpdate.Show });
		aToolBar.push('-');
	}if(flag){
		aToolBar.push({text:'删除', tooltip:Telewave.oModelConfig.Tip.Delete,iconCls:'CssBtnDelete', handler:Telewave.Data.DeleteM});
		aToolBar.push('-');
	}
	//aToolBar.push('-');
	aToolBar.push({text:'刷新', tooltip:Telewave.oModelConfig.Tip.Refresh ,iconCls:'CssBtnFresh', handler:Telewave.Data.Refresh });
	return aToolBar;
}

Ext.onReady(function(){
	Telewave.Init();
	var hidenids=new Array();
	var toolbar=checkPermission();
	var pagebar=Telewave.oModelConfig.PageBar(15);
	var grid=Telewave.Tool.Grid(toolbar,pagebar,'autoExpandColumn');
	
    var viewport = new Ext.Viewport({
        layout:'border',
        items: [frm,grid]
    });
   Telewave.Data.Refresh();
});