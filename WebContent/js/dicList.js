Telewave.oModelConfig.Id='dicTypeId';
Telewave.oModelConfig.Url.Query = 'queryDicTypeList.do';//列表
Telewave.oModelConfig.Url.DeleteM = 'deleteDictype.do';
Telewave.oModelConfig.Url.Add= 'addDicType.do';
Telewave.oModelConfig.Url.Update='updateDicType.do';

Telewave.oModelConfig.Title={List:'数据字典列表',Add:'新增数据字典',Update:'修改数据字典'};
Telewave.oModelConfig.Tip={Refresh:'刷新',Add:'新增',Update:'修改',Delete:'删除'};


Telewave.oModelConfig.JsonReaderConfig=[
 	{name : 'dicTypeId',type : 'string'},
 	{name : 'dicTypeName',type : 'string'},
 	{name : 'dicTypeDesc',type : 'string'}
];

Telewave.oModelConfig.ColumnConfig=
	[
	new Ext.grid.RowNumberer(),
	Telewave.DataModel.SM,
		{header : '字典类型',dataIndex : 'dicTypeId',align : 'left',width : 100,sortable : true},
		{header : '字典类型名称',dataIndex : 'dicTypeName',align : 'center',width : 100,sortable : true},
		{header : '描述',dataIndex : 'dicTypeDesc',align : 'left',width : 75,sortable : true,id : 'autoExpandColumn'},
		{header : "操作",dataIndex : 'dicTypeId',align : 'center',width : 150,sortable : true,renderer : renderHandler}
	];


/**
 * 渲染操作
 * @param value
 * @param p
 * @param record
 * @returns {String}
 */
function renderHandler(value, p, record){
	return '<img src="'+path+'images/dataDetails.gif" alt="详情" style="cursor:pointer;" onclick="oHandlerDetail.Show(\''+value+'\')"/>';
}

var oHandlerDetail={
	Show:function(dicTypeId){
		window.location.href="dicdetailList.do?dicContent.dicTypeId="+dicTypeId;
	}
};

/**
 * 渲染修改操作
 * @param value
 * @param p
 * @param record
 */
function renderUpdate(value, p, record){
	return '<a onclick="oHandlerUpdate.Show()"><image src="'+path+'images/update.gif"></a>';
}

var oModelConfig = {
		ModelName:'dicType',
		//表单域组-添加
		FormAdd:[
		    { layout:'column', border:false, items:[     
                { columnWidth:.5, layout: 'form', border:false, items: [
                	{ xtype:'textfield', width:150,fieldLabel: '字典类型', name: 'dicType.dicTypeId' }
                ]},
                { columnWidth:.5, layout: 'form', border:false, items: [
    				{ xtype:'textfield', width:150,fieldLabel: '字典类型名称', name: 'dicType.dicTypeName' }
                ]}
            ]},    
		    { layout:'column', border:false, items:[
		        { columnWidth:.99, layout: 'form', border:false, items: [
		        	{ xtype:'textarea', width:388,fieldLabel: '描述', name: 'dicType.dicTypeDesc' }
		        ]}
		    ]}
		],
		FormUpdate:[
			
		    { layout:'column', border:false, items:[
		    	{ columnWidth:.5, layout: 'form', border:false, items: [
                	{ xtype:'hidden', width:150,fieldLabel: '字典类型', name: 'dicType.dicTypeId' }
                ]},
                { columnWidth:.5, layout: 'form', border:false, items: [
    				{ xtype:'textfield', width:150,fieldLabel: '字典类型名称', name: 'dicType.dicTypeName' }
                ]}
            ]},    
		    { layout:'column', border:false, items:[
		        { columnWidth:.99, layout: 'form', border:false, items: [
		        	{ xtype:'textarea', width:388,fieldLabel: '描述', name: 'dicType.dicTypeDesc' }
		        ]}
		    ]}
		],
		FormQuery:[
			{ layout:'column', border:false, items:[
                { columnWidth:.33, layout: 'form', border:false, items: [
                	{ xtype:'textfield', width:150,fieldLabel: '字典类型', name: 'dicType.dicTypeId' }
                ]},
                { columnWidth:.33, layout: 'form', border:false, items: [
    				{ xtype:'textfield', width:150,fieldLabel: '字典类型名称', name: 'dicType.dicTypeName' }
                ]},
                { columnWidth:.1, layout: 'form', border:false, items: [
                	{ xtype:'button', text: '查询' ,handler:Telewave.Data.Refresh} 
                ]},
	            { columnWidth:.1, layout: 'form', border:false, items: [
	            	{ xtype:'button', text: '重置',handler:function(){
	            		frm.getForm().reset();
	            	}} 
	            ]}
            ]}    
		]
	}







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
		frmAdd.getForm().submit({
			success : function(form, action) {
				 if(action.result.success){
					 winAdd.hide();
					 Telewave.Data.Refresh();
				 }
			},failure : function(form, action) {		
				Ext.Msg.alert(action.result.tip,action.result.msg);
			}
		});	
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
		                width: 600,
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
  title:'数据字典查询',
  bodyStyle:'padding:20px 10px 0px 20px',
	items:oModelConfig.FormQuery
});

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