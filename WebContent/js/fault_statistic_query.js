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
function query(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
    var MsgForm = new Ext.form.FormPanel({
		baseCls: 'x-plain',
        labelAlign: 'left',
    	buttonAlign: 'right',
    	frame: true,
    	labelWidth: 55,
        onSubmit : Ext.emptyFn, 
    	submit : function() {      
    	this.getEl().dom.action ='FaultStatistic.do?fromTime='
    								+Ext.util.Format.date(fromDate.getValue(),'Y-m-d')
    								+'&toTime='+Ext.util.Format.date(toDate.getValue(),'Y-m-d');
    	this.getEl().dom.method = 'post';      
    	this.getEl().dom.submit();      
    	},
        items: [{
        	xtype:'fieldset',
        	title:'选择故障发生时间',
        	collapsible:true,
        	width:290,
        	autoHeight:true,
//        	height:140,
        	defaults:{width:200,height:30},
        	defaultType:'textfield',
        	items:[
        	       new Ext.form.DateField({
						fieldLabel: '开始时间',
						id:'fromDate',
						name:'fromDate',
						vtype: 'daterange',
						format: 'Y-m-d',
		        		endDateField: 'toDate' 
					}),
					new Ext.form.DateField({
						fieldLabel: '结束时间',
						id:'toDate',
						name:'toDate',
						vtype: 'daterange',
						format: 'Y-m-d', 
						startDateField: 'fromDate'
					})]
        }]
    });
    var fromDate = Ext.getCmp('fromDate');
    var toDate = Ext.getCmp('toDate');
    var win = new Ext.Window({
        title: '故障统计',
        modal:true,
        width: 320,
        height:230,
        x:330,
        y:150,
        layout: 'fit',
        plain: true,
        bodyStyle:'padding:5px;color:black;',
        buttonAlign:'center',
        items: MsgForm,
        buttons: [{
            text: '确定',
            handler: function() {
        	MsgForm.form.submit();
        	}
        },{
            text: '关闭',
            handler  : function(){
            	win.close();
            }
        }]
    });
    win.show();

}