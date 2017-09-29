/**
 * @author Mr.Tu
 * http://www.loveweb8.com/
 *
 * Version 1.0
 * Copyright (c) 2014 我爱Web吧
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/mit-license.php
 *
 * Date: 2014-2-10 15:30
 */
(function($){
	$.formatDate = function(pattern, date) {
		/* 未传入时间,设置为当前时间 */
		if(date == undefined)
			date = new Date();
		/* 传入时间为字符串 */
		if($.type(date) === "string"){
			if(date == "") date = new Date();
			else date = new Date(date.replace(/-/g, "/"));
		}
			
		var result = [];
		while (pattern.length>0) {
			options.RegExpObject.lastIndex = 0;
			var matched = options.RegExpObject.exec(pattern);
			if (matched) {
				result.push(patternValue[matched[0]](date));
				pattern = pattern.slice(matched[0].length);
			}else {
				result.push(pattern.charAt(0));
				pattern = pattern.slice(1);
			}
		}
		return result.join('');
	};
	/* 配置 */
	var options = {
		RegExpObject: /^(y+|M+|d+|H+|h+|m+|s+|E+|S|a)/,
		months: ['January','February','March','April','May','June','July',
				'August','September','October','November','December'],
		weeks: ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday',
				'Saturday']
	}
	/* 补全 */
	var toFixedWidth = function(value, length) {
		var result = "00" + value.toString();
		return result.substr(result.length - length);
	};
	/* 匹配值处理 */
	var patternValue = {
		y: function(date) {
			return date.getFullYear().toString().length > 1 ? toFixedWidth(date.getFullYear(), 2) : toFixedWidth(date.getFullYear(), 1);
		},
		yy: function(date) {
			return toFixedWidth(date.getFullYear(), 2);
		},
		yyy: function(date) {
			return toFixedWidth(date.getFullYear(), 3);
		},
		yyyy: function(date) {
			return date.getFullYear().toString();
		},
		MMMM: function(date) {
			return options.months[date.getMonth()];
		},
		MMM: function(date) {
			return options.months[date.getMonth()].substr(0, 3);
		},
		MM: function(date) {
			return toFixedWidth(date.getMonth()+1, 2);
		},
		M: function(date) {
			return date.getMonth()+1;
		},
		dd: function(date) {
			return toFixedWidth(date.getDate(), 2);
		},
		d: function(date) {
			return date.getDate();
		},
		EE: function(date) {
			return options.weeks[date.getDay()];
		},
		E: function(date) {
			return options.weeks[date.getDay()].substr(0, 3);
		},
		HH: function(date) {
			return toFixedWidth(date.getHours(),2);
		},
		H: function(date) {
			return date.getHours();
		},
		hh: function(date) {
			return toFixedWidth(date.getHours() > 12 ? date.getHours() - 12 : date.getHours(), 2);
		},
		h: function(date) {
			return date.getHours()%12;
		},
		mm: function(date) {
			return toFixedWidth(date.getMinutes(), 2);
		},
		m: function(date) {
			return date.getMinutes();
		},
		ss: function(date) {
			return toFixedWidth(date.getSeconds(), 2);
		},
		s: function(date) {
			return date.getSeconds();
		},
		S: function(date) {
			return toFixedWidth(date.getMilliseconds(), 3);
		},
		a: function(date) {
			return date.getHours() < 12 ? 'AM' : 'PM';
		}
	};

})(jQuery);