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
Ext.override(Ext.Container, {
	render : function(){
		Ext.Container.superclass.render.apply(this, arguments);
		if(this.layout){
			if(typeof this.layout == 'string'){
				this.layout = new Ext.Container.LAYOUTS[this.layout.toLowerCase()](this.layoutConfig);
			}
			this.setLayout(this.layout);
			if(this.activeItem !== undefined){
				var item = this.activeItem;
				delete this.activeItem;
				this.layout.setActiveItem(item);
				//return;
			}
		}
		if(!this.ownerCt){
			this.doLayout();
		}
		if(this.monitorResize === true){
			Ext.EventManager.onWindowResize(this.doLayout, this, [false]);
		}
	}
});
Ext.override(Ext.layout.Accordion, {
	setActiveItem: function(c) {
		c = this.container.getComponent(c);
		if(this.activeItem != c){
			if(c.rendered && c.collapsed){
				c.expand();
			}else{
				this.activeItem = c;
			}
		}
	},
	renderItem : function(c){
		if(this.animate === false){
			c.animCollapse = false;
		}
		c.collapsible = true;
		if(this.autoWidth){
			c.autoWidth = true;
		}
		if(this.titleCollapse){
			c.titleCollapse = true;
		}
		if(this.hideCollapseTool){
			c.hideCollapseTool = true;
		}
		if(this.collapseFirst !== undefined){
			c.collapseFirst = this.collapseFirst;
		}
		if(!this.activeItem && !c.collapsed){
			this.activeItem = c;
		}else if(this.activeItem){
			c.collapsed = this.activeItem != c;
		}
		Ext.layout.Accordion.superclass.renderItem.apply(this, arguments);
		c.header.addClass('x-accordion-hd');
		c.on('beforeexpand', this.beforeExpand, this);
	}
});