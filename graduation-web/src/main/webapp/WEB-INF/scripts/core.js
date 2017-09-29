$(function(){
	
	/**
	 * 阻止退格键回到上一页面
	 */
	$(document).keydown(function(e) {
		var target = e.target ;
		var tag = e.target.tagName.toUpperCase();
		if(e.keyCode == 8) {
			if((tag == 'INPUT' && !$(target).attr("readonly")) || (tag == 'TEXTAREA' && !$(target).attr("readonly"))) {
				return !((target.type.toUpperCase() == "RADIO") || (target.type.toUpperCase() == "CHECKBOX"));
			} else {
				return false ;
			}
		}
	});

	/**
	 * 表单初始化
	 * @param method	初始化方法 load/clear
	 * @param row		表单属性值集合，method为load时使用
	 */
	$.fn.form = function(method, row) {
		try{
			$(this).validationEngine({ 
				validationEventTriggers : "keyup blur",
				success :  false
			});

			//加载表单值
			if('load' == method) {
				// 所有文本控件
				var element = $(this).find(":text,textarea,:hidden,select,:checked,:checkbox,:radio");
				$.each(element, function(key) {
					var itemName = element[key].name;
					if(itemName) {
						var value = eval("row." + itemName);
						if(typeof (value) != 'undefined') {
							element[key].value = dataEncode2Out(value);
						}
					}
	        	});
			//清空表单值
			} else if('clear' == method) {
				// 隐藏表单校验
				//$(this).hideValidate();
				$(':input', this)
					.not(':button, :submit, :reset, :radio, :checkbox, select')
					.val('');
				$(':input:checkbox', this).prop('checked', false);	//处理checkbox
				$('select', this).find('option:first').prop('selected', true);	//处理select,选中第一项
			}
		} catch(ex) {
			Metronic.alert({ message : ex.message, type : 'danger', closeInSeconds : 5 });
		}
	};

	/**
	 * 表单值转json数组
	 */
	$.fn.serializeJson = function() {
		var serializeObj = {};
		$(this.serializeArray()).each(function() {
			serializeObj[this.name] = dataEncode($.trim(this.value));
		});

		return serializeObj;
	};

	/**
	 * 执行指令，需要通过$(id)调用。
	 * 	1.如果被调用对象是个form则会将form中的所有数据转为json发送到服务器
	 *  2.如果被调用对象是个grid则会将选中行以对象的数组形式发送到服务器。
	 *  3.参数：(以下是默认值){
			method:"post",
			url:"",
			callback:function(op){},//在http请求成功时调用
			param:{},//对象形式的传参，如果使用form/grid发送，冲突的属性会用这里的进行覆盖,没冲突的就合并后发送。
			onBefore:function(smdata){},//在请求之前调用,会把当前的调用对象对应的dom引用传递过去，方便进行其他操作。
			onAfter:function(smdata){},//在请求返回之后调用，可能是失败。,会把当前的调用对象对应的dom引用传递过去，方便进行其他操作。
		    onError:function(){}
		}
	 */
	$.fn.say = function(opt, config) {

		if(opt && typeof(opt) != 'object') {
			Metronic.alert({ message : 'say 方法参数错误，需要通过对象的方式传递。', type : 'danger', closeInSeconds : 3 });
			return;
		}
		
		if(config = null || typeof(config) == 'undefined') {
			config = {};
		}

		//默认设置
		this.options = $.extend({
			
			
			contentType : "application/json",
			method : "POST",
			url : "",
            enableMask : true,	//是否启用遮罩
            disableEl : "",		//禁用的元素
			timeout : 300000,
			param : {},
			callback : function(data, self) {},
			onBefore : function(data, self) {},
			onAfter : function(data, self) {},
			onError : function(data, self) {}
		}, opt);
		

		var method = this.options.method;	//请求方式
		switch(method.toLowerCase()) {
			case "put":
				method = "put";
				break;
			case "post":
				method = "post";
				break;
			case "delete":
				method = "delete";
				break;
			default:
				method = "get";
		}

        if(this.options.enableMask) {
            // 遮罩目前尚未实现
        }

		var finalData = this.options.param;		//参数对象
		var isMapData = true;
		if(isEmptyObject(finalData)){
			isMapData = false;
		}

		/**
		 * 表单请求，需要参数特殊处理
		 */
		if($(this).is("form")) {

			if(!$(this).validationEngine('validate') && method !== 'delete') {		//校验form
				return false;
			}
			
			var jsonData = $(this).serializeJson(); //自动将form表单封装成json
			
			//再处理checkbox的值
			$(this).find("input[isArray = 'true'][type = 'checkbox']").each(function() {
				//先取出name
				var name = $(this).attr("name");
				//判断同名是否已经存在数组类型的值。
				if(!jsonData[name] || !(jsonData[name] instanceof Array)){
					jsonData[name] = $(this).val();
				}
			});
			
			finalData = $.extend(finalData, jsonData) ;	//将后传入的对象合并覆盖到form的表格对象中。冲突的以后自定义的参数为准
		}

		var thisPointer = this;

		//发送前事件触发
		if(thisPointer.options.onBefore && $.isFunction(thisPointer.options.onBefore)) {
			var onBeforeResult = thisPointer.options.onBefore.call(finalData, thisPointer);
			if(typeof(onBeforeResult) != "undefined" && !onBeforeResult) {
				return;		//如果返回false或者什么都不返回，则直接终止提交
			} else if(typeof(onBeforeResult) == "object") {
				if(isEmptyObject(onBeforeResult)){
					isMapData = false;
				}
				finalData = $.extend(finalData, onBeforeResult);	//如果返回了新的参数，则将新的参数与即将要提交的参数合并。
			}
		}
		var base64 = new Base64();  
		
		if(isMapData){
			
			this.options.contentType = "application/m-wisely";
		}else{
			
			this.options.contentType = "application/x-wisely";
		}
		//执行ajax调用
		$.ajax({
			headers : $.lhToken(true),
			type : method,
			timeout : this.options.timeout,
			url : rootUrl + this.options.url,
			dataType : "json",
			contentType : "application/json",
			//data : base64.encode(JSON.stringify(finalData)),
			data : JSON.stringify(finalData),
			error : function(){ },
			success : function(data, status, response) {
				if(data){
					dataEncodeOut(data);
				}
	        	//增加安全秘钥头
				var __token__ = response.getResponseHeader("__token__");
				if(__token__ && __token__ != null && __token__ != "") {
					resetToken(__token__);
				}
				switch(status) {
					case "timeout" :
						Metronic.alert({message : '请求超时！请稍后再次尝试。', type : 'danger', closeInSeconds : 3});
						break;
					case "error" :
						Metronic.alert({message : '请求错误！请稍后再次尝试。', type : 'danger', closeInSeconds : 3});
						break;
					case "success" :
						if (data.success) {
							if(data.message) {
								Metronic.alert({ message : data.message, type : 'success', closeInSeconds : 3 });
							}
						} else {
							if(data.message) {
								Metronic.alert({ message : data.message, type : 'danger', closeInSeconds : 3 });
							}
						}
						if(data.isWorkflow) {
							if(data.data && data.data != "-1") {
								try {
									if(data.dealType != null && data.dealType == "beforeCreateWfInst") {
										beforeCreateWfInstance(data.data);
									}
									else {
										loadWfInstance(data.data.inst_id);
									}
								} catch(e) {
									Metronic.alert({message : e, type : 'success', closeInSeconds : 2});
								}
							}
						}
						if(thisPointer.options.callback && $.isFunction(thisPointer.options.callback)) {
							thisPointer.options.callback(data, thisPointer);
						}
						break;
					default:
						Metronic.alert({message : '未知错误，请尝试刷新页面或者重新登录！', type : 'success', closeInSeconds : 2});
						return;
				}

				//发送后事件触发
				if (thisPointer.options.onAfter && $.isFunction(thisPointer.options.onAfter)) {
					thisPointer.options.onAfter(finalData, thisPointer);
				}
			}
		});
		//判断工作流拦截
		if(top.$._wfTriggerUrlMap && top.$._wfTriggerUrlMap[this.options.url + "￢" + method]) {//工作流调用
			finalData = $.extend(finalData, {proj_id : top.$._wfTriggerUrlMap[this.options.url + "￢" + method]});
			thisPointer.options.callback = function(res) {
				if(res && res.success) {
					if(res.data && res.data != "-1") {
						try {
							loadWfInstance(res.data.inst_id);	// 打开工作流div
						} catch(e) {
							Metronic.alert({ message : '未挂接工作流资源，请联系管理员！', type : 'danger', closeInSeconds : 3 });
						}
					}
				}
			};
			callUrl.call(this, "wfengine/engine/wfMain/createInstance", method);
		} else {	//正常调用
			callUrl.call(this, this.options.url, method);
		}
		function callUrl(url, method) {
			
		}
	};

	/**
	 * 扩展jquery方法
	 */
	$.extend({
		/**
		 * 从html组件中获取Token
		 * @param isObj 是否返回对象类型的token。默认是返回param字符串。
		 */
		lhToken : function(isObj) {
			var __token__ = top.$("#__token__").val();
			if(isObj) {
				var headers = {};
				headers["-token-"] = __token__;
				return headers;
			} else {
				return "?__token__=" + __token__;
			}
		},
		/**
		 * 加载下拉框数据
		 */  
		lhComboLoadData : function(strCombo, strUrl, initValue, isCache) {
			try {
				var combo = $("#" + strCombo);
				if(combo.val() != null) {
					initValue = combo.val();
				}
				combo.empty();
				var commboData = commonComboData[strUrl];
				if(isCache && commboData) {
		        	$.each(commboData, function(key) {
		        		if(initValue == commboData[key].dicvalue) {
		        			combo.append('<option value="' + commboData[key].dicvalue + '" selected>' + commboData[key].dictext + '</option>');
		        		} else {
		        			combo.append('<option value="' + commboData[key].dicvalue + '">' + commboData[key].dictext + '</option>');
		        		}
		        	});

				} else {
					$("body").say({
				        method : "GET",
				        url : strUrl,
				        callback : function(res) {
				        	$.each(res, function(key) {
				        		if(initValue == res[key].dicvalue) {
				        			combo.append('<option value="' + res[key].dicvalue + '" selected>' + res[key].dictext + '</option>');
				        		} else {
				        			combo.append('<option value="' + res[key].dicvalue + '">' + res[key].dictext + '</option>');
				        		}
				        	});
				        	if(isCache){
				        		commonComboData[strUrl] = res;
				        	}
				        }
				    });
				}
			} catch(ex) {
				Metronic.alert({ message : ex.message, type : 'danger', closeInSeconds : 3 });
			}
		}
	});

	/**
	 * 初始化My97日期控件
	 * css:true 文本框内显示查询按钮
	 * type:date 只显示日期
	 * type:dateTime 显示日期和时间
	 */
	$.fn.initDateBox = function(option) {
		try { 	
			$(this).attr('readonly', 'true');
			if(option.css == "true"){
				$(this).attr('class', 'Wdate');
			}			
			if(option.type == "date"){
				$(this).on("click", {}, function(){
					WdatePicker();
				});
			}else if(option.type == "dateTime"){
				$(this).on("focus", {}, function(){
					WdatePicker({dateFmt : 'yyyy-MM-dd HH:mm:ss'});
				});
			}
		} catch(ex) {
			Metronic.alert({ message : ex.message, type : 'danger', closeInSeconds : 3 });
		}
	};
});

/**
 * 加载主菜单
 */
function loadAccordionMenu() {
	var requestUrl = rootUrl + "/index/accordionMenu";
	
	$.ajax({ 
		headers : $.lhToken(true),	//token加入进head信息
        type : "GET",
        url : requestUrl,
        dataType : "json",
        contentType:"application/json",
        data : {
		},
        error : function() { },
        success : function(data) {
        	//先清空菜单
			var $menuBar = $("#page-sidebar-menu");
			$menuBar.find(".ls_menu_li").remove();
        	//拼接1级菜单
        	for(var i = 0; i < data.data.length; i ++) {
        		 var html = '';
                 html += "<li id=" + data.data[i].id + " class=\"ls_menu_li\">";
                 if('NULL'== data.data[i].href || ''== data.data[i].href) {
                	 html+="<a href=\"javascript:void(0);\">";
                 } else {
                	 html+="<a href=\"javascript:void(0);\" onClick=\"openUrl(\'" + data.data[i].href.replace(/\//g,"\/") + "\', \'" + data.data[i].id + "\')\" id=\"" + data.data[i].id.replace(/\-/g,"") + "\">";
                 }
                 html += "<i class=\"" + data.data[i].iconCls + "\"></i>";
                 html += "<span class=\"title\">" + data.data[i].text + "</span>";
                 html += "<span class=\"arrow\"></span>";
                 html += "</a>";
                 html += "</li>";
				$menuBar.append(html);
                 if(data.data[i].children.length > 0) {
                	 var html2 = '';
                	 html2 += "<ul class=\"sub-menu\">";
                	 //拼接2级菜单
                     for(var j = 0; j < data.data[i].children.length; j ++) {
                    	 html2 += "<li>";
                    	 html2 += "<a href=\"javascript:void(0);\" onClick=\"openUrl(\'" + data.data[i].children[j].href.replace(/\//g,"\/") + "\', \'" + data.data[i].children[j].id + "\')\" id=\"" + data.data[i].children[j].id.replace(/\-/g,"") + "\">";
                    	 html2 += "<i class=\"" + data.data[i].children[j].iconCls + "\"></i>";
                    	 html2 += " " + data.data[i].children[j].text + "</a>";
                    	 html2 += "</li>";
                     }
                     html2 += "<ul>";
                	 $("#" + data.data[i].id + "").append(html2);
                 }
        	}
        	if($('#lsMenuId').val() != null && $('#lsMenuId').val() != 'null') {
        		Layout.setSidebarMenuActiveLink("set", $("#" + $('#lsMenuId').val().replace(/\-/g,"")));
        	}
        }
	});
}

/**
 * 点击菜单，打开对应页面
 * @param url	菜单页面路径
 * @param menuId	菜单ID
 */
function openUrl(url, menuId) {
	var base64 = new Base64();  
	var realUrl = $("#basePath").val() + url;
	/*if(typeof(menuId) == 'undefined') {
		top.location.href = realUrl + '?__token__=' + base64.encode(top.$('#__token__').val())
	}else{
		top.location.href = realUrl + '?__token__=' + base64.encode(top.$('#__token__').val()) + '&lsMenuId=' + base64.encode(menuId);		
	}*/
	
	
	if(typeof(menuId) == 'undefined') {
		top.location.href = realUrl + '?__token__=' + top.$('#__token__').val();
	}else{
		top.location.href = realUrl + '?__token__=' + top.$('#__token__').val() + '&lsMenuId=' + menuId;		
	}
}

/**
 * 格式化流程待办
 * @param row
 */
function _wf_loadUserInst_formatInsId_top(row) {
	if(row && row!=""){
		if(row.is_time_limit == 0){
			return '<li><a href="javascript:void(0)" title="'+row.proj_name+' '+row.summary+'" onclick="loadWfInstance(\''+row.inst_id+'\',\''+row.isconsign+'\')"><span><font color="red">'+row.proj_name+' '+row.summary+' [超时]</font></span></a></li>';
		}else{
			return '<li><a href="javascript:void(0)" title="'+row.proj_name+' '+row.summary+'" onclick="loadWfInstance(\''+row.inst_id+'\',\''+row.isconsign+'\')"><span>'+row.proj_name+' '+row.summary+'</span></a></li>';
		}
	}
	return '';
}

function htmlEncode (str) {
    if (str.length == 0) {
		return "";
	}
    //s = str.replace(/ /g, "&nbsp;");
    //s = str.replace(/&/g, "&amp;");
    var s = str.replace(/</g, "%26lt%3B");
    s = s.replace(/%3C/g,"%26lt%3B");
    s = s.replace(/%3c/g,"%26lt%3B");
    s = s.replace(/>/g, "%26gt%3B");
    s = s.replace(/%3E/g, "%26gt%3B");
    s = s.replace(/%3e/g, "%26gt%3B");
    //s = s.replace(/\'/g, "&#39;");
    //s = s.replace(/\"/g, "&quot;");
    //s = s.replace(/\n/g, "<br>");
    return s;
}

function htmlEncodeOut (str) {
    if (str.length == 0) {
		return "";
	}
    //s = str.replace(/ /g, "&nbsp;");
    //s = str.replace(/&/g, "&amp;");
    var s = str.replace(/</g, "&lt;");
    s = s.replace(/%3C/g,"&lt;");
    s = s.replace(/%3c/g,"&lt;");
    s = s.replace(/>/g, "&gt;");
    s = s.replace(/%3E/g, "&gt;");
    s = s.replace(/%3e/g, "&gt;");
    s = s.replace(/%26lt%3B/g, "&lt;");
    s = s.replace(/%26lt%3b/g, "&lt;");
    s = s.replace(/%26gt%3B/g, "&gt;");
    s = s.replace(/%26gt%3b/g, "&gt;");
    //s = s.replace(/\'/g, "&#39;");
    //s = s.replace(/\"/g, "&quot;");
    //s = s.replace(/\n/g, "<br>");
    return s;
}

function htmlEncode2Out (str){
    var s = "";
    if (str.length == 0) return "";
    s = str.replace(/&lt;/g,"<");
    s = s.replace(/&gt;/g,">");
    return str;
}

function dataEncode(data) {
    var rel = data;
    var source = "";
    if(typeof(rel) == "object") {
        source = htmlEncode(JSON.stringify(rel));
        source = JSON.parse(source);
        rel = source;
	} else if(typeof(rel) == "string") {
        source = htmlEncode(rel);
        rel = source;
    }
    return data;
}

function dataEncodeOut(data) {
    var rel = data;
    var source = "";
    if(typeof(rel) == "object"){
        source = htmlEncodeOut(JSON.stringify(rel));
        source = JSON.parse(source);
        rel = source;
    } else if(typeof(rel) == "string"){
        source = htmlEncodeOut(rel);
        rel = source;
    }
    return data;
}

function dataEncode2Out(data) {
    var rel = data;
    var source = "";
    if(typeof(rel) == "object"){
        source = htmlEncode2Out(JSON.stringify(rel));
        source = JSON.parse(source);
        rel = source;
    }else if(typeof(rel) == "string"){
        source = htmlEncode2Out(rel);
        rel = source;
    }
    return data;
}

/** 格式化显示脚本 */
/**
 * 格式化金钱  例如： ￥123,456.1
 */
function f_rmb_1(val, rowData, index) {
	return '￥' + lhFormatMoney(val, 1);	
}

/**
 * 格式化金钱  例如： ￥123,456.12
 */
function f_rmb_2(val, rowData, index) {
	return '￥' + lhFormatMoney(val,2);	
}

/**
 * 格式化金钱  例如： ￥123,456.123
 */
function f_rmb_3(val, rowData, index) {
	return '￥' + lhFormatMoney(val, 3);	
}

/**
 * 格式化金钱（1位小数）  例如： 123,456.1
 */
function f_comma_1(val, rowData, index) {
	return lhFormatMoney(val, 1);	
}

/**
 * 格式化金钱（2位小数） 例如： 123,456.12
 */
function f_comma_2(val, rowData, index) {
	return lhFormatMoney(val, 2);	
}

/**
 * 格式化金钱（3位小数）例如： 123,456.123
 */
function f_comma_3(val, rowData, index) {
	return lhFormatMoney(val, 3);	
}

/**
 * 格式化金钱
 */
function lhFormatMoney(s, n) {
	n = n > 0 && n <= 20 ? n : 2;
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
	t = "";
	for (i = 0; i < l.length; i++) {
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	}
	return t.split("").reverse().join("") + "." + r;
}

/**
 * 格式化日期 例如： 2014/03/19
 */
function f_Date_YMD(val, rowData, index) {
	return $.formatDate("yyyy/MM/dd", new Date(hxGetDate(val)));
}

/**
 * 格式化日期 例如： 2014/03/19 12:11:13
 */
function f_Date_YMD_HMS(val, rowData, index){
	return $.formatDate("yyyy/MM/dd/ hh:mm:ss", new Date(hxGetDate(val)));
}

/**
 * 格式化日期 例如： 2014年03月19日
 */
function f_Date_YMD_CH(val, rowData, index){
	return $.formatDate("yyyy年MM月dd日", new Date(hxGetDate(val)));
}

/**
 * 格式化日期 例如： 格式化日期 例如： 2014年03月19日 12时23分22秒
 */
function f_Date_YMD_HMS_CH(val, rowData, index){
	return $.formatDate("yyyy年MM月dd日 hh时mm分ss秒", new Date(hxGetDate(val)));
}

/**
 * 格式化显示鼠标提示
 * @param val
 * @param rowData
 * @param index
 * @returns {String}
 */
function f_add_tip(val,rowData,index) {
	if(is_null(val)) {
		return '';
	} else {
		return '<span title="' + val + '">' + val + '</span>';
	}
}

/**
 * 是否为空
 * @param val
 */
function is_null(val) {
	return val == null || val == '' || typeof(val) == "undefined";
}

/**
 * 还原金钱
 */
function lhRemoveMoney(s) {
	return parseFloat(s.replace(/[^\d\.-]/g, ""));
}

/**
 * 格式化日期
 */
Date.prototype.lhFormat = function(format) { 
	var ret = "";
	try {
		ret = $.formatDate(format, this);
	} catch (e) {
		ret = "";
	}
	return ret;
}

/**
 * 获取时间
 */
function lhGetDate(val){
	val = val.replace(/-/g, "/")
	return new Date(val);
}

function isEmptyObject(obj) { 
    for (var name in obj) { 
        return false; 
    } 
    return true; 
}

/**
 * 处理消息条数
 */
function lhDealNum(typeID) {
	var count = $("#" + typeID).html();
	if(count > 0){
		count --;
	}
	if(count <= 0){
		count = '0';
	}
	$("#" + typeID).html(count);
}

/** 格式化显示脚本(自带鼠标提示) */
//格式化金钱  例如： ￥123,456.1
function tip_f_rmb_1(val, rowData, index) {
	val = '￥'+ lhFormatMoney(val,1);	
	return f_add_tip(val, rowData, index);
}

function tip_f_rmb_2(val, rowData, index) {
    if (undefined == val) {
        return '';
    }
    if (Number(val) < Number(0)) {
        val = val + "";
        val = val.substring(1, val.length);
        val = '￥-' + lhFormatMoney(val, 2);
    } else {
        val = '￥' + lhFormatMoney(val, 2);
    }
    return f_add_tip(val, rowData, index);
}

/**
 * 格式化金钱  例如： ￥123,456.123
 */
function tip_f_rmb_3(val, rowData, index) {
	val =  '￥' + lhFormatMoney(val, 3);	
	return f_add_tip(val, rowData, index);
}

/**
 * 格式化金钱十位小数 例如： ￥123,456.1200000000 zc
 */
function tip_f_rmb_10(val, rowData, index) {
	if (undefined == val) {
		return '';
	}
	if (Number(val) < Number(0)) {
		val = val + "";
		val = val.substring(1, val.length);
		val = '￥-' + lhFormatMoney(val, 10);
	} else {
		val = '￥' + lhFormatMoney(val, 10);
	}
	return f_add_tip(val, rowData, index);
}

/**
 * 格式化金钱（1位小数）  例如： 123,456.1
 * @param val
 * @param rowData
 * @param index
 * @returns {String}
 */
function tip_f_comma_1(val, rowData, index) {
	val = lhFormatMoney(val, 1);	
	return f_add_tip(val, rowData, index);
}

/**
 * 格式化金钱（2位小数） 例如： 123,456.12
 */
function tip_f_comma_2(val, rowData, index) {
	val = lhFormatMoney(val, 2);	
	return f_add_tip(val, rowData, index);
}

/**
 * 格式化金钱（3位小数）例如： 123,456.123
 */
function tip_f_comma_3(val, rowData, index) {
	val = lhFormatMoney(val, 3);	
	return f_add_tip(val, rowData, index);	
}

/**
 * 格式化日期 例如： 2014-03-19 12:11:13
 */
function tip_f_Date_YMD_HMS2(val, rowData, index) {
	if (val != undefined) {
		val = $.formatDate("yyyy-MM-dd HH:mm:ss", new Date(val));
	} else {
		val = "";
	}
	return f_add_tip(val, rowData, index);
}

/**
 * 格式化日期 例如： 2014/03/19
 * @param val
 * @param rowData
 * @param index
 * @returns {String}
 */
function tip_f_Date_YMD(val, rowData, index) {
	val = $.formatDate("yyyy/MM/dd", new Date(val));
	return f_add_tip(val, rowData, index);
}

/**
 * 格式化日期 例如： 2014/03/19 12:11:13
 */
function tip_f_Date_YMD_HMS(val, rowData, index) {
	val = $.formatDate("yyyy/MM/dd/ hh:mm:ss", new Date(val));
	return f_add_tip(val, rowData, index);
}

/**
 * 格式化日期 例如： 2014年03月19日
 */
function tip_f_Date_YMD_CH(val, rowData, index) {
	val = $.formatDate("yyyy年MM月dd日", new Date(val));
	return f_add_tip(val, rowData, index);
}

/**
 * 格式化日期 例如： 2014年03月19日 12时23分22秒
 */
function tip_f_Date_YMD_HMS_CH(val, rowData, index) {
	val = $.formatDate("yyyy年MM月dd日 hh时mm分ss秒", new Date(val));
	return f_add_tip(val, rowData, index);
}

/*
 * 2015-01-29 add
 */
// 格式化精度(面积位数) 2位 ssf
function fit_digits_square(value, row, index) {
	if (undefined == value) {
		return '';
	}
	if (value != 0) {
		value = Number(value).toFixed(2);
	} else {
		value = '0';
	}
	return f_add_tip(value, row, index);
}

// 格式化精度(用量位数) 3位 ssf
function fit_digits_dosage(value, row, index) {
	if (undefined == value) {
		return '';
	}
	if (value != 0) {
		value = Number(value).toFixed(3);
	} else {
		value = '0';
	}
	return f_add_tip(value, row, index);
}

/**
 * 格式化精度(户数、天数等) 0位 ssf
 */
function fit_digits_integer(value, row, index) {
    if (undefined == value) {
        return '';
    }
    if (value != 0) {
        value = Number(value).toFixed(0);
    } else {
        value = '0';
    }
    return f_add_tip(value, row, index);
}

/**
 * 格式化精度(比率位数) 2位 ssf
 */
function fit_digits_rate(value, row, index) {
    if (undefined == value) {
        return '';
    }
    if (value != 0) {
        value = Number(value).toFixed(2);
    } else {
        value = '0';
    }
    return f_add_tip(value, row, index);
}

/**
 * 百分数两位小数 zc
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function format_percent(value, row, index) {
	if (Number(value) === 0 || value == null) {
		value = '0';
	} else {
		value = Number(value).toFixed(2);
		value = value + '%';
	}
	return f_add_tip(value, row, index);
}

/**
 * 格式化是否
 */
function isnotmatter(value, row, index) {
	if(value != "" && value != null){
	    if (Number(value) === 0) {
	        value = '不是';
	    } else if(Number(value) === 1){
	    	value = '是';
	    }		
	}
    return f_add_tip(value, row, index);
}

/**
 * 格式化是否启用
 */
function agreematter(value, row, index) {
	if(value!=""&&value!=null){
	    if (Number(value) === 0) {
	        value = '禁用';
	    } else if(Number(value) === 1){
	    	value = '启用';
	    }
	}
    return f_add_tip(value, row, index);
}

/**
 * 格式化"是"/"否"
 */
function ismatter(value, row, index) {
    if (Number(value) === 0) {
        value = '否';
    } else if(Number(value) === 1){
    	value = '是';
    }
    return f_add_tip(value, row, index);
}

/**
 * 格式化 性别
 */
function sexmatter(value, row, index) {
    if (Number(value) === 1) {
        value = '男';
    } else if(Number(value) === 2){
    	value = '女';
    } else if(Number(value) === 3){
    	value = '保密';
    }
    return f_add_tip(value, row, index);
}

/**
 * 格式化用户类型
 */
function typematter(value, row, index) {
    if (Number(value) === 1) {
        value = '普通用户';
    } else if(Number(value) === 2){
    	value = '管理员';
    }
    return f_add_tip(value, row, index);
}

/**
 * 行序号
 */
function indexmatter(value, row, index) {	
	if(undefined == value){
		value = index + 1;		
	} else {		
		value ='等待查询..';
	}
    return f_add_tip(value, row, index);
}

/**
 * 格式化文件大小
 * @param value	原值
 */
function fileSizeFmt(value) {
	if(null == value || value == '') {
	    return "0Bytes";
	}
	var unitArr = ["Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"];
    var srcSize = parseFloat(value);
    var size = (srcSize / Math.pow(1024, (i = Math.floor(Math.log(srcSize) / Math.log(1024))))).toFixed(2);
    size = size.toString().replace('.00', '');	//去掉小数点后的00
    return size + unitArr[i];
}

/**
 * 全屏方法
 */
function fullScreen() {
	top.$("#indexLayout").layout('collapse', 'north');
	top.$('#indexLayout').layout('collapse', 'west');
}

/**
 * 浮点数加法运算
 */
function lhFloatAdd(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1 * m + arg2 * m) / m;
}

/**
 * 浮点数减法运算
 */
function lhFloatSub(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	// 动态控制精度长度
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

/**
 * 浮点数乘法运算
 */
function lhFloatMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length;
	} catch (e) {
	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", ""))
			/ Math.pow(10, m);
}

/**
 * 浮点数除法运算
 */
function lhFloatDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
}

/**
 * 保留位数
 */
function lhTo2bits(flt, pos) {
	var rd = 1;
	for (var i = 1; i <= parseInt(pos); i++) {
		rd = rd * 10;
	}
	if (parseFloat(flt) == flt) {
		return Math.round(flt * rd) / rd;
	} else {
		return 0;
	}
}

 // -----------------------bootstrapTable初始化 start-----------------------------------
var tableOptions = {
	pagination : true,
	locale : $.cookie('Language')==undefined?'zh_CN':$.cookie('Language'),
	pageSize : 10,
	dataType : "json",
	striped : true,
	queryParamsType : "limit",
	sidePagination : "server",	// 设置为服务器端分页
	responseHandler : responseHandler,
	queryParams : queryParams
};

function responseHandler(res) {
	if (res) {
		if(res.success && res.data) {
			return {
				"rows" : res.data.rows,
				"total" : res.data.total
			};
		} else {
			return {
				"rows" : res.rows,
				"total" : res.total
			};
		}
	} else {
		return {
			"rows" : [],
			"total" : 0
		};
	}
}

//传递的参数
function queryParams(params) {
	return {
		limit : params.limit,
		start : params.offset,
		sort : params.sort,
		order : params.order,
		__token__ : $("#__token__").val()
	};
}
// -----------------------bootstrapTable初始化 end-----------------------------------

/**
 * 更新token值
 * @param tokenVal	新token值
 */
function resetToken(tokenVal) {
	top.$("#__token__").val(tokenVal);
}

/**
 * 根据字段自动合并单元格
 * @param tableID 表ID
 * @param colList field字段用","连接
 * @author dong 
 */
function mergeCellsByField(tableID, colList) {
    var colArray = colList.split(",");
    var $table = $("#" + tableID);
    var rowCount = $table.bootstrapTable('getData').length;
    var tmpA;
    var tmpB;
    var perTxt = "";
    var curTxt = "";
    var row;
    for (var j = colArray.length - 1; j >= 0; j--) {
        perTxt = "";
        tmpA = 1;
        tmpB = 0;
        for (var i = 0; i <= rowCount; i++) {
            if (i == rowCount) {
                curTxt = "";
            } else {
				row = $table.bootstrapTable('getData')[i];
				if(colArray[j].indexOf('.') != -1) {
					var mainObj = row[colArray[j].split('.')[0]];
					curTxt = mainObj[colArray[j].split('.')[1]];
				} else {
					curTxt = row[colArray[j]];
				}
            }
            if (perTxt == curTxt) {
                tmpA += 1;
            } else {
                tmpB += tmpA;
            	//合并字段
				$table.bootstrapTable("mergeCells", {
                    index : i - tmpA,
                    field : colArray[j],
                    rowspan : tmpA,
                    colspan : 1
                });
                tmpA = 1;
            }
            perTxt = curTxt;
        }
    }
}

/**
 * 获取对象属性值,自递归处理多层关系
 * @param obj	对象
 * @param param	属性名，关系属性用'.'分隔
 */
function getObjValue(obj, param) {
	var parent, parentParam;
	if(param) {
		if(typeof(obj[param]) != 'undefined') {
			if(obj[param] == null) {
				return '';
			} else {
				return obj[param];
			}
		} else if(param.indexOf('.') > 0) {
			parentParam = param.substring(0, param.indexOf('.'));
			parent = obj[parentParam];
			if(parent != 'undefined' && parent != null) {
				return getObjValue(parent, param.substr(param.indexOf('.') + 1));
			} else {
				return '';
			}
		}
	} else {
		return '';
	}
}

/**
 * 获取bootstrap-table全部选中的Id
 * @param tableId table tag's id
 * @param path id的引用路径
 * 	例：	正常获取Id为row.id,path为id；
 * 		正常获取Idrow.maintenance.maintenanceId,path为maintenance.maintenanceId
 */
function getSelectedIds(tableId, path) {
	try {
		var rows = $('#' + tableId).bootstrapTable('getSelections');	//获取选中记录集合
		if(!rows) {
			Metronic.alert({ message : "未选中任何数据！", type : 'danger', closeInSeconds : 5 });
			return null;
		}
		var row;	//数据记录
		var ids = '';	//id串
		for (var i = 0; i < rows.length; i++) {
			row = rows[i];
			ids += eval("row." + path) + ',';
		}
		return ids.substring(0, ids.length - 1);
	} catch(e) {
		Metronic.alert({ message : "未选中任何数据！", type : 'danger', closeInSeconds : 5 });
		throw e;
	}
}

/**
 * 导出EXCEL共通方法
 * @param dg
 * @param isExpAll
 */
function expGridExcel(dg, isExpAll){

	var $dg = $("#" + dg);

	var options = $dg.bootstrapTable('getOptions');
 
	var rows = options.totalRows;
	
	// 定义请求参数
	var expExcelPara = {};
	
	//获取显示名称
	var arrCols = [];

	$dg.find('thead').find('tr').find('th').each(function() {
		if($(this).attr("data-checkbox") == true || $(this).attr("data-visible") == false || $(this).attr("data-field") == "index" || $(this).attr("data-field") == "checkbox") {
			
		} else {
			var cal = {};
			var field = $(this).children();
			cal.title = field.text();
			cal.field = $(this).attr("data-field");
			arrCols.push(cal);
		}
	});

	 // 设置列属性
    expExcelPara.colums = arrCols;
    expExcelPara.token = $("#__token__").val();
    
    //判断是否导出全部
	if(isExpAll) {
		expExcelPara.page = 1;
        if(rows > 0) {
		    expExcelPara.rows = rows;
        } else {
            expExcelPara.rows = 0;
        }
		expExcelPara.exptype = "allpage";
	} else {
		expExcelPara.page = options.pageNumber;
		expExcelPara.rows = options.pageSize;
		expExcelPara.exptype = "singlepage";
	}
    
    // 查询条件
	
    expExcelPara.params = $.param(options.queryParams.apply());
	
    // 格式化方法
	if($dg.attr("customFormatter")) {
		expExcelPara.customFormatter = $dg.attr("customFormatter");
	} else {
		expExcelPara.customFormatter = "";
	}
	
    expExcelPara.filename = $dg.attr("name");// 设置导出excel名称

    expExcelPara.url = options.url;	// 设置查询地址
    
    if(isExpAll) {
        var msg1 = '您正在导出' + expExcelPara.rows + '条记录,';
        var msg2 = expExcelPara.rows > 5000 ? "将会花费较多时间," : "";
        
        bootbox.setLocale($.cookie('Language') == undefined ? 'zh_CN' : $.cookie('Language'));
		bootbox.confirm(msg1 + msg2 + '是否继续？', function(callback) {
			if (callback) {
				$("#dicTable").say({
	            	method : "post",
	            	url : "/system/util/exportfile/expexcel",
					param : expExcelPara,
					callback : function(data) {
						if(data && data.filename) {
							var filename = data.filename;
							$.ajax({
								type : "get",
								timeout : 3000,
								url : rootUrl + "/system/file/sysFile/downloadFileCheck/" + filename + "?__token__=" + $("#__token__").val(),
								dataType : "json",
								contentType : "application/json",
								success : function(data) {
									if(data.success) {
										window.location.href = rootUrl + "/system/file/sysFile/downloadFile/" + filename + "?target='blank'&__token__=" + $("#__token__").val();
									}
									else {
										Metronic.alert({ message : '文件下载出错！', type : 'danger', closeInSeconds : 3 });
									}
								}
							});
						} else {
							Metronic.alert({ message : 'excel导出失败！', type : 'danger', closeInSeconds : 3 });
						}
					}
				});
			}
		});
    }
}


function Base64() {
	 
    // private property
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
 
    // public method for encoding
    this.encode = function (input) {
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;
        input = _utf8_encode(input);
        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output +
            _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
            _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
        }
        return output;
    }
 
    // public method for decoding
    this.decode = function (input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        while (i < input.length) {
            enc1 = _keyStr.indexOf(input.charAt(i++));
            enc2 = _keyStr.indexOf(input.charAt(i++));
            enc3 = _keyStr.indexOf(input.charAt(i++));
            enc4 = _keyStr.indexOf(input.charAt(i++));
            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;
            output = output + String.fromCharCode(chr1);
            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }
        }
        output = _utf8_decode(output);
        return output;
    }
 
    // private method for UTF-8 encoding
    _utf8_encode = function (string) {
        string = string.replace(/\r\n/g,"\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }
 
        }
        return utftext;
    }
 
    // private method for UTF-8 decoding
    _utf8_decode = function (utftext) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;
        while ( i < utftext.length ) {
            c = utftext.charCodeAt(i);
            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i+1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utftext.charCodeAt(i+1);
                c3 = utftext.charCodeAt(i+2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    }
}