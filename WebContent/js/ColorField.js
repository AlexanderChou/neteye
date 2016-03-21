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
/**
 * @class Ext.form.ColorField
 * @extends Ext.form.TriggerField
 * Provides a very simple color form field with a ColorMenu dropdown.
 * Values are stored as a six-character hex value without the '#'.
 * I.e. 'ffffff'
 * @constructor
 * Create a new ColorField
 * <br />Example:
 * <pre><code>
var cf = new Ext.form.ColorField({
	fieldLabel: 'Color',
	hiddenName:'pref_sales',
	showHexValue:true
});
</code></pre>
 * @param {Object} config
 */
Ext.form.ColorField = function(config){
    Ext.form.ColorField.superclass.constructor.call(this, config);
	this.on('render', this.handleRender);
};

Ext.extend(Ext.form.ColorField, Ext.form.TriggerField,  {
    /**
     * @cfg {Boolean} showHexValue
     * True to display the HTML Hexidecimal Color Value in the field
     * so it is manually editable.
     */
    showHexValue : false,
	
	/**
     * @cfg {String} triggerClass
     * An additional CSS class used to style the trigger button.  The trigger will always get the
     * class 'x-form-trigger' and triggerClass will be <b>appended</b> if specified (defaults to 'x-form-color-trigger'
     * which displays a calendar icon).
     */
    triggerClass : 'x-form-color-trigger',
	
    /**
     * @cfg {String/Object} autoCreate
     * A DomHelper element spec, or true for a default element spec (defaults to
     * {tag: "input", type: "text", size: "10", autocomplete: "off"})
     */
    // private
    defaultAutoCreate : {tag: "input", type: "text", size: "10",
						 autocomplete: "off", maxlength:"6"},
	
	/**
	 * @cfg {String} lengthText
	 * A string to be displayed when the length of the input field is
	 * not 3 or 6, i.e. 'fff' or 'ffccff'.
	 */
	lengthText: "Color hex values must be either 3 or 6 characters.",
	
	//text to use if blank and allowBlank is false
	blankText: "Must have a hexidecimal value in the format ABCDEF.",
	
	/**
	 * @cfg {String} color
	 * A string hex value to be used as the default color.  Defaults
	 * to 'FFFFFF' (white).
	 */
	defaultColor: 'FFFFFF',
	
	maskRe: /[a-f0-9]/i,
	// These regexes limit input and validation to hex values
	regex: /[a-f0-9]/i,

	//private
	curColor: 'ffffff',
	
    // private
    validateValue : function(value){
		if(!this.showHexValue) {
			return true;
		}
		if(value.length<1) {
			this.el.setStyle({
				'background-color':'#' + this.defaultColor
			});
			if(!this.allowBlank) {
				this.markInvalid(String.format(this.blankText, value));
				return false
			}
			return true;
		}
		if(value.length!=3 && value.length!=6 ) {
			this.markInvalid(String.format(this.lengthText, value));
			return false;
		}
		this.setColor(value);
        return true;
    },

    // private
    validateBlur : function(){
        return !this.menu || !this.menu.isVisible();
    },
	
	// Manually apply the invalid line image since the background
	// was previously cleared so the color would show through.
	markInvalid : function( msg ) {
		Ext.form.ColorField.superclass.markInvalid.call(this, msg);
		this.el.setStyle({
			'background-image': 'url(../lib/resources/images/default/grid/invalid_line.gif)'
		});
	},

    /**
     * Returns the current color value of the color field
     * @return {String} value The hexidecimal color value
     */
    getValue : function(){
		return this.curValue || this.defaultValue || "FFFFFF";
    },

    /**
     * Sets the value of the color field.  Format as hex value 'FFFFFF'
     * without the '#'.
     * @param {String} hex The color value
     */
    setValue : function(hex){
		Ext.form.ColorField.superclass.setValue.call(this, hex);
		this.setColor(hex);
    },
	
	/**
	 * Sets the current color and changes the background.
	 * Does *not* change the value of the field.
	 * @param {String} hex The color value.
	 */
	setColor : function(hex) {
		this.curColor = hex;
		
		this.el.setStyle( {
			'background-color': '#' + hex,
			'background-image': 'none'
		});
		if(!this.showHexValue) {
			this.el.setStyle({
				'text-indent': '-100px'
			});
			if(Ext.isIE) {
				this.el.setStyle({
					'margin-left': '100px'
				});
			}
		}
	},
	
	handleRender: function() {
		this.setDefaultColor();
	},
	
	setDefaultColor : function() {
		this.setValue(this.defaultColor);
	},

    // private
    menuListeners : {
        select: function(m, d){
			// 自己加的一句代码，用来改div文本颜色的，get()括号里的内容为div的id
			Ext.get("drawTitle").setStyle("color",d);
			//Ext.get("s3").setStyle("color",d);
            this.setValue(d);
        },
        show : function(){ // retain focus styling
            this.onFocus();
        },
        hide : function(){
            this.focus();
            var ml = this.menuListeners;
            this.menu.un("select", ml.select,  this);
            this.menu.un("show", ml.show,  this);
            this.menu.un("hide", ml.hide,  this);
        }
    },
	
	//private
	handleSelect : function(palette, selColor) {
		this.setValue(selColor);
	},

    // private
    // Implements the default empty TriggerField.onTriggerClick function to display the ColorPicker
    onTriggerClick : function(){
        if(this.disabled){
            return;
        }
        if(this.menu == null){
            this.menu = new Ext.menu.ColorMenu();
			this.menu.palette.on('select', this.handleSelect, this );
        }
        this.menu.on(Ext.apply({}, this.menuListeners, {
            scope:this
        }));
        this.menu.show(this.el, "tl-bl?");
    }
});