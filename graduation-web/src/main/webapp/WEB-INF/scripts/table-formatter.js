/**
 * 表单初始化
 * @param method	初始化方法 load/clear
 * @param row		表单属性值集合，method为load时使用
 */
$.fn.form = function(method, row) {
    try{
        //加载表单值
        if('load' === method) {
            // 所有文本控件
            var elements = $(this).find(":text, textarea, :hidden, select, :checked, :checkbox, :radio");
            $.each(elements, function(key) {
                var itemName = elements[key].name;
                if(itemName) {
                    var value = row[itemName];
                    if(typeof (value) !== 'undefined') {
                        if(elements[key].type == 'radio') {
                            if(elements[key].value === value.toString()) {
                                $(elements[key]).attr('checked', true);
                                $(elements[key]).parent().addClass('checked');
                            } else {
                                $(elements[key]).attr('checked', false);
                                $(elements[key]).parent().removeClass('checked');
                            }
                        } else {
                            elements[key].value = value;
                        }
                    }
                }
            });
            //清空表单值
        } else if('clear' === method) {
            $(':input', this).not(':button, :submit, :reset, :radio, :checkbox, select').val('');
            $(':input:checkbox', this).prop('checked', false);	//处理checkbox
            $('select', this).find('option:first').prop('selected', true);	//处理select,选中第一项
        }
    } catch(ex) {
        Metronic.alert({ message : ex.message, type : 'danger', closeInSeconds : 5 });
    }
};

// function dataEncode2Out(data) {
//     var rel = data;
//     var source = "";
//     if(typeof(rel) === "object"){
//         source = htmlEncode2Out(JSON.stringify(rel));
//         source = JSON.parse(source);
//         rel = source;
//     }else if(typeof(rel) === "string"){
//         source = htmlEncode2Out(rel);
//         rel = source;
//     }
//     return data;
// }

/**
 * 是否为空
 * @param value 值
 */
$.fn.isNull = function(value) {
    return value === null || value === '' || typeof(value) === "undefined";
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

function dataEncode(data) {
    var result;
    if(typeof(data) === "object") {
        result = htmlEncode(JSON.stringify(data));
        result = JSON.parse(result);
    } else if(typeof(data) === "string") {
        result = htmlEncode(data);
    }
    return data;
}

function htmlEncode(str) {
    if (str.length === 0) {
        return "null";
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

function dataEncodeOut(data) {
    var rel = data;
    var source = "";
    if(typeof(rel) === "object"){
        source = htmlEncodeOut(JSON.stringify(rel));
        source = JSON.parse(source);
        rel = source;
    } else if(typeof(rel) === "string") {
        source = htmlEncodeOut(rel);
        rel = source;
    }
    return data;
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

// function dataEncode(data) {
//     var rel = data;
//     var source = "";
//     if(typeof(rel) === "object") {
//         source = htmlEncode(JSON.stringify(rel));
//         source = JSON.parse(source);
//     } else if(typeof(rel) === "string") {
//         source = htmlEncode(rel);
//     }
//     return data;
// }

// function htmlEncode(str) {
//     if (str.length === 0) {
//         return "";
//     }
//     //s = str.replace(/ /g, "&nbsp;");
//     //s = str.replace(/&/g, "&amp;");
//     var s = str.replace(/</g, "%26lt%3B");
//     s = s.replace(/%3C/g,"%26lt%3B");
//     s = s.replace(/%3c/g,"%26lt%3B");
//     s = s.replace(/>/g, "%26gt%3B");
//     s = s.replace(/%3E/g, "%26gt%3B");
//     s = s.replace(/%3e/g, "%26gt%3B");
//     //s = s.replace(/\'/g, "&#39;");
//     //s = s.replace(/\"/g, "&quot;");
//     //s = s.replace(/\n/g, "<br>");
//     return s;
// }

var generalTableOption = {
    pagination : true,
    locale : 'zh_CN',
    pageSize : 10,
    dataType : "json",
    striped : true,
    queryParamsType : "limit",
    sidePagination : "server",	// 设置为服务器端分页
    responseHandler : responseHandler
    // queryParams : queryParams
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

/**
 * 传递的参数
 */
// function queryParams(params) {
//     return {
//         limit : params.limit,
//         offset : params.offset,
//         sort : params.sort,
//         order : params.order
//     };
// }

/**
 * 格式化显示鼠标提示
 * @param value 值
 * @returns {String}
 */
var overSpanFormatter = function(value) {
    if($.fn.isNull(value)) {
        return '';
    } else {
        return '<span title="' + value + '">' + value + '</span>';
    }
};

/**
 * 通用-显示序号
 * @param value 显示值
 */
var indexFormatter = function (value, row, index) {
    if(value) {
        value = index + 1;
    } else {
        value ='等待查询..';
    }
    return overSpanFormatter(value);
};

var toHomePage = function() {
    window.location.href = rootDir + 'home';
};

var toRoutePage = function() {
    window.location.href = rootDir + 'bus/route/home';
};

var toRouteReadonlyPage = function() {
    window.location.href = rootDir + 'bus/route/readonly';
};

var toStationPage = function() {
    window.location.href = rootDir + 'bus/station/home';
};

var toStationDistancePage = function() {
    window.location.href = rootDir + 'bus/stationDistance/home';
};

var logout = function() {
    bootbox.confirm("确定要退出系统吗？", function(callback) {
        if (callback) {
            window.location.href = rootDir + 'logout';
        }
    });
};

/**
 * 加载下拉框数据
 * @param businessKey 业务KEY
 * @param hasAll 类型 true 首选项为'全部'；false 首选项为'请选择'；
 * @param initValue 初始值
 * @param isCache 是否缓存列表
 */
var commonComboData = {};
$.fn.comboData = function(businessKey, hasAll, initValue, isCache) {
    try {
        var combo = $(this);
        if(combo.val() !== null) {
            initValue = combo.val();
        }
        combo.empty();
        if(hasAll) {
            combo.append('<option value="">全部</option>');
        } else {
            combo.append('<option value="">请选择</option>');
        }
        var comboData = commonComboData[businessKey];
        if(isCache && comboData) {
            $.each(comboData, function(index) {
                if(initValue === comboData[index].id) {
                    combo.append('<option value="' + comboData[index].id + '" selected>' + comboData[index].value + '</option>');
                } else {
                    combo.append('<option value="' + comboData[index].id + '">' + comboData[index].value + '</option>');
                }
            });
        } else {
            $.ajax({
                method : "GET",
                url : rootDir + 'system/base/sysComboData/' + businessKey,
                dataType : "json",
                contentType : "application/json",
                // data : JSON.stringify($editForm.serializeJson()),
                success : function(result, status) {
                    switch(status) {
                        case "timeout" :
                            SysMessage.alertError('请求超时！请稍后再次尝试。');
                            break;
                        case "error" :
                            SysMessage.alertError('请求错误！请稍后再次尝试。');
                            break;
                        case "success" :
                            var data = result.data;
                            $.each(data, function(index) {
                                if(initValue === data[index].id) {
                                    combo.append('<option value="' + data[index].id + '" selected>' + data[index].value + '</option>');
                                } else {
                                    combo.append('<option value="' + data[index].id + '">' + data[index].value + '</option>');
                                }
                            });
                            if(isCache) {
                                commonComboData[businessKey] = data;
                            }
                            break;
                        default:
                            SysMessage.alertError('未知错误，请尝试刷新页面或者重新登录！');
                            return;
                    }
                }
            });
        }
        combo.select2({theme: "bootstrap"});
    } catch(ex) {
        SysMessage.alertError(ex.message);
    }
};