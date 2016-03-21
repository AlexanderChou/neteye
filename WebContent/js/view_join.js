function joinToView(deiceId,deviceName,viewId,currentview){
var viewStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'json/viewList.do?viewId='+viewId+'&deiceId='+deiceId
			}),
		//创建JsonReader读取router记录
		reader : new Ext.data.JsonReader({
			root : 'viewList',
			fields : ['id','name']
		})
});
viewStore.load();

var MsgForm = new Ext.form.FormPanel({
	baseCls: 'x-plain',
    labelAlign: 'left',
   	buttonAlign: 'center',
   	frame: true,
   	labelWidth: 65,
   	defaultType: 'textfield',
   	defaults: {
   		allowBlank: false
   	},
   items: [{
   	xtype:'fieldset',
   	title:'视图信息',
   	collapsible:true,
   	width:320,
   	autoHeight:true,//使自适应展开排版
   	defaults:{width:200,height:30},
   	defaultType:'textfield',
   	items:[{
            xtype:"combo",
            id:'VIEW_ITEM_ID',                    
            store: viewStore,
         	valueField: 'id',   
            displayField:'name',
            mode: 'remote',
            fieldLabel:"视图名称",
            emptyText:'请选择要关联的子视图',
            triggerAction:'all',
            hiddenName:'joinedView',
            allowBlank:false,
            editable : false,
            forceSelection : true
    }],
    buttons: [{   
        text: '确定',   
        handler: function() {
        	var nullview =Ext.get('VIEW_ITEM_ID').dom.value;
         if(MsgForm.form.isValid()){
          		MsgForm.form.doAction('submit',{
          			url: 'json/viewJoin.do',
   					method: 'post',
   					params : {
   						nullview:nullview,
          				sourceView:viewId,
          				deviceId:deiceId,
          				deviceName:deviceName
          			},
   					waitTitle:"提示",
   					waitMsg:"<font color='black'>正在发送，请稍后......</font>",
   					success:function(MsgForm,action){
   						var subviewclick =Ext.get('joinedView').dom.value;//此处应该无需向后台提交对xml进行修改，而是修改页面的信息，然后再通过保存进行全盘的保存。
   						var flag = action.result.flag;
   						if(flag=="1"){
   						currentObj.subview = subviewclick;//
   							Ext.Msg.alert('系统消息', '视图关联操作完成！'); 
   						}else{
   						currentObj.subview ="";//
   							Ext.Msg.alert('系统消息', '取消关联操作完成！'); 
   						}
   						win.close();
   					},
   					failure:function(MsgForm,action){
   						Ext.Msg.alert('错误', '视图关联操作失败');  
   					}
      			}); 
      		}
      }   
    },{
        text: '关闭',
        handler  : function(){
        	win.close();
        }
       }]
    }]
});

viewStore.on('load',function(){
  if(currentview!=null&&currentview!=("undefined")&&currentview!=("")){
Ext.getCmp("VIEW_ITEM_ID").setValue(currentview);}

});

         
         
         
var win = new Ext.Window({
       title: '视图关联',
       width: 350,
       height:180,
       x:330,
       y:150,
       layout: 'fit',
       plain: true,
       bodyStyle:'padding:5px;color:black;',
       buttonAlign:'center',
       items: MsgForm
   });
   win.show();
}