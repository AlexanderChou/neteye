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


/**         这里的方法是在 shouWindow 加载完在提交时执行的js代码             */

var currentDate = new Date();

function doSubmit(){

	Ext.Ajax.request({
		url : 'json/anyTimePic.do',
		disableCaching : true,
		params : {
			time : Ext.get('time').getValue(false),
			length : Ext.get('length').getValue(false),
			url:url_param,
			type:type_param,
			style:style_param
		},
		method : 'post',				
		success : function(result, request) {
			var ipinfo=document.getElementById("anypic");
			var picTime =Ext.decode(result.responseText).picTime;
			var flag=type_param;
//			if(url_param.indexOf(":")>0){
//				url_param=url_param.replace(/:/g,"_");
//			}
			if(flag=="snmp"){
				ipinfo.innerHTML='<img src="file/service/dat/tmp/'+ url_param+"_"+style_param+"_tmp" + '.gif?' + currentDate + '"/>';
			}else{
				ipinfo.innerHTML='<img src="file/service/dat/tmp/'+ url_param+"_tmp" + '.gif?' + currentDate + '"/>';
			}
		},
		failure : function(result, request) {
			Ext.Msg.alert("生成任一时段图时发生错误!");
		}
	});
}




function showWindow(html) {
	var responseDiv = document.getElementById("responseDiv");
	responseDiv.innerHTML = html;
	
	var win = new Ext.Window({
		title:"固定或任意时段图",
		width:1020,
		height:400,
		autoScroll:true,
		resizable :false,
		modal:true,
		items:[createPanel()]
	});
	
	win.setWidth(820);
	win.show();
}

function createPanel(){
	
	var tabs = new Ext.TabPanel({
        activeTab: 0,
        renderTo:"tabDiv",
        width:800,
        height:400,
        defaults:{autoScroll: true},
        items:[{
                title: '固定时间段图',
                contentEl:"div1"
            },getFormPanel()]
	    });
	return tabs;
}

function getFormPanel(){

	var formPanel = new Ext.form.FormPanel({
      width:850,
      frame:true,//圆角和浅蓝色背景
      region:'north',
      height:100,
      
      items:[ {
            fieldLabel:"开始时间",//文本框标题
            xtype:"datefield",//表单文本框
            format: 'Y-m-d',
            id:"time",
            width:200
          }, {
            fieldLabel:"指定天数",
            xtype:"textfield",
            id:"length",
            width:200
          }
      ],
      buttons:[{text:"确定",handler:function(){
      		doSubmit();
      }}]
    });
	
	var imgPanel = new Ext.Panel({
		region:'center',
		width:850,
		height:290,
		frame:true,
		contentEl:"imgPanel"
	
	});
	
	var panel = new Ext.Panel({ 
       layout:'border',
       title:"任意时间段图",
       width:850,
       height:400,
       items:[formPanel, imgPanel]
    });

	
    
 	return panel;
}