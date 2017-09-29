/*
 * 复制到剪切板插件。
 * 需要zeroClipboard支持，下面的全局变量根据需要定义：
 * gClipboardJsPath		ZeroClipboard.min.js的位置
 * gClipboardFlashPath	ZeroClipboard.swf的位置
 */


if(!Ext.isIE){
	if (typeof(gClipboardJsPath) != 'undefined'){
		Ext.Loader.load([gClipboardJsPath]); 
	} else {
		Ext.Loader.load(["templates/jsp/public/js/extjs/ZeroClipboard"]); 
	}
};

/**
 * 跨浏览器的复制至剪切板插件(基于ZeroClipboard 2)
 */
Ext.define('ClipBoard',{
	extend : Object,
	constructor : function(config) {
        Ext.apply(this, config || {forceHandCursor: true});
    },

    /**
     * 初始化，最为plugin创建时调用
     */
    init : function(component){
    	var me = this;
		me.component = component;
		
		if(Ext.isIE){
			component.on('click', me.onIECopy, me);
		} else {
			// 使用falsh支持拷贝
			if (typeof(gClipboardFlashPath) != 'undefined'){
				ZeroClipboard.config({swfPath: gClipboardFlashPath});
			} else {
				ZeroClipboard.config({swfPath: 'templates/jsp/public/js/extjs/ZeroClipboard.swf'});
			}

			component.on('afterrender', me.initZeroClipboard, me, {single: true});
	    	
			//此处无效
			//component.on('focus', me.focusZeroClipboard, me);
		}
    },
    
    destroy: function() {
		var me = this;
		
		me.component.clearListeners();
		delete me.component;
		
		if (me.clip){
			me.clip.unclip();
			delete me.clip;
		}
	},

	/**
	 * 初始化ZeroClipboard 
	 */
	initZeroClipboard: function() {
		var me = this;
		me.clip = new ZeroClipboard(document.getElementById(me.component.getEl().id));
		me.clip.component = me;
		me.clip.on('copy', me.onFlashCopy);
		me.clip.on('aftercopy', function (event) {
			event.client.unclip();
		});
	},
	
	/**
	 * 聚焦ZeroClipboard
	 */
/*	focusZeroClipboard: function() {
		var me = this;
		if(me.clip) {
			me.clip.focus();
		}
	},*/

	/**
	 * 使用flash拷贝
	 * @param clip
	 */
	onFlashCopy: function(event) {
		var clipboard = event.client.component;
		
		// 读取复制的值
		var val = '';
		if(clipboard.getCopyValue) {
			val = clipboard.getCopyValue(clipboard.component);
		} else if(clipboard.copyValue) {
			val = clipboard.copyValue;
		}
		
		event.client.setText(val);
	},
	
	/**
	 * 使用IE拷贝
	 * @param txt
	 */
	onIECopy : function(event, element, args) {
		var clipboard = this;
		
		// 读取复制的值
		var val = '';
		if(clipboard.getCopyValue) {
			val = clipboard.getCopyValue(clipboard.component);
		} else if(clipboard.copyValue) {
			val = clipboard.copyValue;
		}
		
	   window.clipboardData.clearData();
	   window.clipboardData.setData("Text", val);
	}
});