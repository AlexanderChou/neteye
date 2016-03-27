var ds=null;
var proxy=null;
var jr=null;
var cm=null;
var oPageBar=null;
var Telewave=new Object();
Ext.QuickTips.init();
/*配置模型*/
Telewave.oModelConfig = {
	ModelName : '',
	Id : '',
	Url : {
		Query : '',
		Add:'',
        Update:'',
		DeleteS : '',
		DeleteM : ''
	},
	Title : {
		List : '列表',
		Add : '添加',
		Update : '修改'
	},
	Tip : {
		Reflesh : '刷新',
		Add : '添加',
		Update : '修改',
		Delete : '删除'
	},
	JsonReaderConfig : [],
	ColumnConfig : [],
	FormSearchId :'',
	PageBar:function(pssize__){//这里定义每页显示的记录数
		if(!Ext.isEmpty(pssize__)){
			oPageBar = Telewave.Tool.PageBar(pssize__);
		}else{
			return null;
		}
		return oPageBar;
	},
	ToolBar:function(addFun,updateFun,hiddenBtns){
		if(Ext.type(addFun)=='function'&&Ext.type(updateFun)=='function'){
			return Telewave.Tool.ToolBar(addFun,updateFun,hiddenBtns);
		}else{
			return null;
		}
	}
};
/*工具类，用来获取数据字典数据*/
Telewave.DictList=function(url_,id_,name_){
	var jr_=new Ext.data.JsonReader({ successProperty : 'State', root: 'Data',id: id_ },[{name: 'id', mapping:id_},{name: 'name', mapping:name_}]);
	var proxy_=new Ext.data.HttpProxy({ url:url_,method:'post' });
	var store__=new Ext.data.Store({ proxy: proxy_, reader: jr_});store__.load();
	return store__;
};
/*遍历ds对象，返回与eid相同的name指（这一般和上面的Telewave.DictList同时用，其中ddataStore为Telewave.DictList返回的Store对象）*/
Telewave.DeCode=function(dataStore,eid){
	if(null==dataStore||typeof(dataStore)=='undefined'||dataStore==''){return "";}
	var ename="";
	dataStore.each(function(record){
		if(typeof(eid)=='undefined'||eid==''){return "";}else{
			if(record.get('id')==eid){
				ename = record.get('name');
			}
		}
	});
	return ename;
};
/*程序入口，所有对象进行初始化*/
Telewave.Init=function(){
	cm=Telewave.DataModel.CM();
	proxy=Telewave.DataModel.Proxy();
	jr=Telewave.DataModel.JR();
	ds=Telewave.DataModel.DataStore();
};
/*数据模型*/
Telewave.DataModel={
	JR:function(){
		var jr = new Ext.data.JsonReader({
		    root: 'Data',
		    totalProperty: 'totalRecords'/*,
		    id: Telewave.oModelConfig.Id*/
		}, Telewave.oModelConfig.JsonReaderConfig);
		return jr;
	},
	Proxy:function(){
		var proxy = new Ext.data.HttpProxy({
			url: Telewave.oModelConfig.Url.Query
		});
		return proxy;
	},DataStore:function(){
		if(ds!=null){
			return ds;
		}
		var ds=new Ext.data.Store({
		    proxy: proxy,
		    reader: jr
		});
		return ds;
	},CM:function(){
		return new Ext.grid.ColumnModel(Telewave.oModelConfig.ColumnConfig);
	},SM:new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn})
};
/*数据对象*/
Telewave.Data={
	/*刷新当前grid对象中的数据*/
	
	
    Refresh:function(){   
    	ds.proxy.conn.form = Telewave.oModelConfig.FormSearchId;
        ds.load({
            callback:function(r,options,success){
            
                if(success){
                }else{
                    Ext.Msg.alert('提示','登录超时，请重新登录后再试');
                }
            }
        });
    },
    /*删除（多删）*/
    DeleteM:function(){
        var aSelections = Telewave.DataModel.SM.getSelections();
		var ids__ = '';
        for(var i = 0; i < aSelections.length; i++){
            ids__ += 'ids='+aSelections[i].get(Telewave.oModelConfig.Id)+'&';
        }
        
        if(aSelections.length > 0){
            Ext.Msg.confirm('删除提示','确定要删除选中的' + aSelections.length + '项?\n删除后该数据的所有详细数据也被删除,且无法恢复!',function(btn){
                if (btn == 'yes'){
  					Ext.Ajax.request({
			   			url: Telewave.oModelConfig.Url.DeleteM,
			   			success: function(resp, action) {
			   			
			   				var respText = Ext.util.JSON.decode(resp.responseText);
			   				if(respText.flag=='true'){
			   				   //直接删除信息
			   					Ext.Ajax.request({
			   			        url: Telewave.oModelConfig.Url.DeleteMDone,
			   			        params: ids__,
			   			        success: function(resp, action) {
			   			           Telewave.Data.Refresh();
			   			        },
			   			        failure: function(resp, action){
			   			           Ext.Msg.alert("提示","删除出错");
			   			        }   
			   					});
			   				}
			   				else if(respText.flag=='false'){
			   				     Ext.Msg.confirm('删除提示',respText.msg,function(btn){
                                 if (btn == 'yes'){
                                      Ext.Ajax.request({
			   			              url: Telewave.oModelConfig.Url.DeleteMDone,
			   			               params: ids__,
			   			              success: function(resp, action) {
			   			                   Telewave.Data.Refresh();
			   			              },
			   			              failure: function(resp, action){
			   			                   Ext.Msg.alert("提示","删除出错");
			   			              }   
                                  })
                                 }
			   				   });
			   					
			   				}
			   				else{
			   				Telewave.Data.Refresh();
			   				}
			   				
							
						},
			   			failure: function(resp, action) {var respText = Ext.util.JSON.decode(resp.responseText);Ext.Msg.alert(respText.tip, respText.msg);},
			   			params: ids__
					});
                }
            
            });
        }else{
            Ext.Msg.alert('删除提示','请先选中要删除的记录');
        }
    },
    /*显示页面，URL为显示的地址，w为宽度，h为高度，isView表示是否不为查看页面（取值true|false），若不指定w,h则默认为最大化显示，title如果不指定，默认为空（适合Iframe）*/
    Show:function(url,title,isView,w,h){
    	if(!Ext.type(url)){
    		Ext.Msg.alert('提示信息','配置显示的URL不能为空');
    		return;
    	}
    	if(!Ext.type(title)){
    		title='';
    	}
    	var iframeId=Ext.id();
    	var btn=null;
    	//当isView存在，且为true的时候，显示保存和重置按钮
    	if(Ext.type(isView)&&isView){
    		btn=[
				    { text: '保存', handler:function(){
				    	var childiframe= document.getElementById(iframeId).contentWindow;
				    	if(!Ext.type(childiframe)){
				    		Ext.Msg.alert("提示信息","系统找不到Iframe框架，请检查配置是否正确");
				    		return;
				    	}
						var oForm =childiframe.document.forms[0];
						if(!Ext.type(oForm)){
				    		Ext.Msg.alert("提示信息","系统找不到表单，请检查配置是否正确");
				    		return;
				    	}
						var formSubmit = new Ext.form.BasicForm(oForm, {});
						//调用IFrame页面验证函数
						var suc=childiframe.vForm();
						//判断是否通过客户端验证
						if(suc){
							//提交前出现遮罩层
							winShow.el.mask('正在提交表单数据，请稍候...');
							formSubmit.submit({
								//waitMsg:"正在提交表单数据，请稍候...",
								success : function(form, action) {
									winShow.el.unmask();
									winShow.close();
									Telewave.Data.Refresh();
									Ext.ux.AutoHideMsg.msg(action.result.tip, action.result.msg);
								},
								failure : function(form, action) {
									winShow.el.unmask();
									Ext.Msg.alert(action.result.tip, action.result.msg);
								}
							});
						}else{
							winShow.el.unmask();
						}				    
				    }},
				    { text: '重置', handler:function(){
				    	var childiframe= document.getElementById(iframeId).contentWindow;
				    	if(!Ext.type(childiframe)){
				    		Ext.Msg.alert("提示信息","系统找不到Iframe框架，请检查配置是否正确");
				    		return;
				    	}
						var oForm =childiframe.document.forms[0];
						if(!Ext.type(oForm)){
				    		Ext.Msg.alert("提示信息","系统找不到表单，请检查配置是否正确");
				    		return;
				    	}
				    	oForm.reset();
				    }},
				    { text: '关闭', handler:function(){winShow.close();}}
				];
    	}else{
    		btn=[{text:'关闭', handler:function(){winShow.close();}}];
    	}
    	var winShow=null;
    	//这里不再隐藏，而是每次关闭并销毁掉
		//if(!winShow){
	        winShow = new Ext.Window({
				title: title,
				layout: 'fit',
				autoScroll:true,
				resizable  : false,
				plain : true,
				border : false,
				modal :true,
				html : '<iframe id="'+iframeId+'"  width="100%" height="100%" style="border:none;" src="" />',
				iconCls:'CssIconForm',
				buttons: btn
		 	});
		//}
		winShow.show();
		if(Ext.type(w)&&Ext.type(h)){
			winShow.setWidth(w);
			winShow.setHeight(h);
		}else{
			winShow.maximize();
		}
		winShow.center();
	    var oFrm = document.getElementById(iframeId);
		oFrm.src=url;
		Telewave.Tool.Mask(oFrm,winShow.id);
    }
};
/*列渲染*/
Telewave.Column={
	Icon:function(value){
		return "<img id='UpdateImage' src='"+ctxpath+"/Images/Update_Min.gif' alt='修改' style='cursor:pointer;' onclick='Telewave.Column.Renderer(\""+value+"\");' />&nbsp;<img id='DeleteImage'  src='"+ctxpath+"/Images/Delete_Min.gif' alt='删除' style='cursor:pointer;' onclick='Telewave.Data.DeleteM();' />";
	},
	Renderer: function(sId){
		Ext.Msg.alert("提示信息","函数Telewave.Column.Renderer未被覆盖！\n请覆盖Telewave.Column.Renderer函数");
    }
}
/*工具类*/
Telewave.Tool={
	/*取得分页栏对象*/
	PageBar:function(pagesize___){
		//分页栏
		var oPageBar =  new Ext.PagingToolbar({
			pageSize:pagesize___,
		    store: ds,
		    displayInfo: true,
		    paramNames:{start: 'recordIndex', limit: 'pageSize'},
		    items:[]
		});
		oPageBar.on('beforechange',function(me, changeEvent){
		    proxy.conn.form = Telewave.oModelConfig.FormSearchId;
		    proxy.conn.params = {recordIndex:changeEvent.recordIndex,pageSize:changeEvent.pageSize};
		});
		return oPageBar;
	},
	/*取得工具栏对象*/
	ToolBar:function(addFun,updateFun,hiddenBtns){
		//工具栏
		var aToolBar = [
			{text:'新增', tooltip:Telewave.oModelConfig.Tip.Add, iconCls:'CssBtnAdd', handler:addFun},'-',
		   /* {text:'修改', tooltip:Telewave.oModelConfig.Tip.Update,iconCls:'CssBtnModify', handler:updateFun}, '-',*/
		    {text:'删除', tooltip:Telewave.oModelConfig.Tip.Delete, iconCls:'CssBtnDelete', handler:Telewave.Data.DeleteM},'-',
		    {text:'刷新', tooltip:Telewave.oModelConfig.Tip.Refresh, iconCls:'CssBtnFresh', handler:Telewave.Data.Refresh },'-'
		];
		/*循环需要隐藏的按钮，把为true的隐藏掉*/
		for(var i=0;i<hiddenBtns.length;i++){
			if(hiddenBtns[i]){
				aToolBar[i*2+1]='';
				aToolBar[i*2].hidden=false;
			}else{
				aToolBar[i*2].hidden=true;
				aToolBar[i*2+1].hidden=true;
			}
		}
		return aToolBar;
	},
	/*取得Grid对象*/
	Grid:function(aToolBar,pssize,autoExpandColumn,plugs){
		//列表
		var expander__ =null;
		if(Ext.type(plugs)){
		  expander__=expander;
		}
		var oGrid = new Ext.grid.GridPanel({
		    id:'grdCustomView',
		    header:true,
		    title:Telewave.oModelConfig.Title.List,
		    autoExpandColumn: autoExpandColumn,
		    plugins:expander__,
		    ds: ds,
		    cm: cm,
		    sm: Telewave.DataModel.SM,
		    loadMask:true,
		    viewConfig: { forceFit:false },//false:调整列宽时，可出现滚动条，否则不出现
		    stripeRows:true,
		    border:false,
		    region: 'center',
		    layout:'fit',
		    tbar:aToolBar,
		    bbar:oPageBar
		});
		return oGrid;
	},
	/*取随机数*/
	Random:function(){
		return Math.random();
	},
	/*防止Ext Iframe 加载出现白板*/
	Mask:function(iframeObj,winId){
		if(typeof(iframeObj)=='object'){
			iframeObj.onload = iframeObj.onreadystatechange = function() {
			     if (this.readyState && this.readyState != 'complete'){
			     	if(Ext.getCmp(winId)=='undefined'){
			     		Ext.getBody().mask("正在加载，请稍后…");
			     	}else{
			     		Ext.getCmp(winId).getEl().mask("正在加载，请稍后…");
			     	}
			     }else {
			        if(Ext.getCmp(winId)=='undefined'){
			     		Ext.getBody().remove();
			     	}else{
			     		Ext.getCmp(winId).getEl().mask().remove();
			     	}
			     }
			};
		}else{
			Ext.Msg.alert('程序配置错误','无法获取IFrame对象。');
		}
	},
	/*返回URL中的参数，以JSON对象形式返回，如果loaction为空，则返回当前URL中的参数*/
	ArgJSON:function(location){
		if(!Ext.type(location)){
			location=unescape(window.location.href);
		}
		var jsonstr="{";
		if(location.indexOf("?")>0){
	        var allargs = location.split("?")[1];
	        var args = allargs.split("&");
	        for(var i=0; i<args.length; i++) {
	            var arg = args[i].split("=");
	            jsonstr+="'"+arg[0]+"':'"+arg[1]+"'"+",";
	        }
	        if(jsonstr.indexOf(",")>=0){
				jsonstr=jsonstr.substring(jsonstr.length-1,0);
			}
	    }
	    jsonstr+="}";
	    return eval("("+jsonstr+")");
	}
	
};