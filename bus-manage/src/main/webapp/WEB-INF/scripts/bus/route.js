!function ($) {

    'use strict';

    var baseUrl = 'bus/route';
    var $table;
    var $editWin;
    var $editForm;

    var $linkTable;
    var $linkWin;
    var $linkForm;

    var selectedRoute;

    /**
     * 初始化
     */
    $(function() {

        initRoute();
        initLink();
    });

    var initRoute = function() {

        $table = $('#routeTable');

        $table.bootstrapTable(
            $.extend({
                url : rootDir + 'bus/routes',
                formatSearch : function () {
                    return '搜索线路名称';
                },
                onCheck : function(row) {	//选中规划表某一行时触发指标表刷新事件
                    selectedRoute = row;
                    refreshStation();
                },
                onExpandRow: function (index, row, $detail) {
                    $detail.html('<form class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">\n' +
                        '    <div class="form-group">\n' +
                        '        <label class="control-label col-md-2">线路名称</label>\n' +
                        '        <p class="form-control-static col-md-4">' + row.name + '</p>\n' +
                        '        <label class="control-label col-md-2">线路分类</label>\n' +
                        '        <p class="form-control-static col-md-4">' + (row.category ? row.category.name : '') + '</p>\n' +
                        '    </div>\n' +
                        '    <div class="form-group">\n' +
                        '        <label class="control-label col-md-2">发车间隔</label>\n' +
                        '        <p class="form-control-static col-md-4">' + row.pullInterval + '</p>\n' +
                        '        <label class="control-label col-md-2">票价(元)</label>\n' +
                        '        <p class="form-control-static col-md-4">' + row.ticketPrice + '</p>\n' +
                        '    </div>\n' +
                        '    <div class="form-group">\n' +
                        '        <label class="control-label col-md-2">始发站</label>\n' +
                        '        <p class="form-control-static col-md-2">' + (row.startStation ? row.startStation.name : '') + '</p>\n' +
                        '        <label class="control-label col-md-2">始发站首车时间</label>\n' +
                        '        <p class="form-control-static col-md-2">' + row.startStationFirstTimeStr + '</p>\n' +
                        '        <label class="control-label col-md-2">始发站末车时间</label>\n' +
                        '        <p class="form-control-static col-md-2">' + row.startStationLastTimeStr + '</p>\n' +
                        '    </div>\n' +
                        '    <div class="form-group">\n' +
                        '        <label class="control-label col-md-2">终点站</label>\n' +
                        '        <p class="form-control-static col-md-2">' + (row.endStation ? row.endStation.name : '') + '</p>\n' +
                        '        <label class="control-label col-md-2">终点站首车时间</label>\n' +
                        '        <p class="form-control-static col-md-2">' + row.endStationFirstTimeStr + '</p>\n' +
                        '        <label class="control-label col-md-2">终点站末车时间</label>\n' +
                        '        <p class="form-control-static col-md-2">' + row.endStationLastTimeStr + '</p>\n' +
                        '    </div>\n' +
                        '    <div class="form-group">\n' +
                        '        <label class="control-label col-md-2">备注</label>\n' +
                    '            <p class="form-control-static col-md-10">' + row.remark + '</p>\n' +
                        '    </div>\n' +
                        '</form>');	//动态写入详细展示内容
                },
                onUncheck : function() {
                    selectedRoute = null;
                    refreshStation();
                }
            }, generalTableOption)
        );

        /**
         * 按钮初始化
         */
        $('#addRoute').on('click', function() { showEditWin(0); });
        $('#modifyRoute').on('click', function() { showEditWin(1); });
        $('#deleteRoute').on('click', function() { remove(); });

        $editWin = $("#editWin");
        $editForm = $("#editForm");

        $('#startStationFirstTime').datetimepicker({
            autoclose : true,
            startView : 1,
            maxView : 1,
            minView : 0,
            language : 'zh-CN',
            format : 'hh:ii',
            minuteStep : 10
        });

        $('#startStationLastTime').datetimepicker({
            autoclose : true,
            startView : 1,
            maxView : 1,
            minView : 0,
            language : 'zh-CN',
            format : 'hh:ii',
            minuteStep : 10
        });

        $('#endStationFirstTime').datetimepicker({
            autoclose : true,
            startView : 1,
            maxView : 1,
            minView : 0,
            language : 'zh-CN',
            format : 'hh:ii',
            minuteStep : 10
        });

        $('#endStationLastTime').datetimepicker({
            autoclose : true,
            startView : 1,
            maxView : 1,
            minView : 0,
            language : 'zh-CN',
            format : 'hh:ii',
            minuteStep : 10
        });

        $('#startStation').comboData('STATION', false, null, true);
        $('#endStation').comboData('STATION', false, null, true);
        $('#category').comboData('BUS_CATEGORY', false, null, true);
    };

    var refreshStation = function() {
        $linkTable.bootstrapTable('removeAll');
        if(selectedRoute) {
            $linkTable.bootstrapTable('refresh',
                { url : rootDir + 'bus/route/' + selectedRoute.id + '/stations', silent : true }
            );
        }
    };

    /**
     * 初始化绑定内容
     */
    var initLink = function() {

        $linkTable = $('#linkTable');

        $linkTable.bootstrapTable(
            $.extend({
                url : rootDir + 'bus/route/null/stations',
                formatSearch : function () {
                    return '搜索站点名称';
                }
            }, generalTableOption)
        );

        /**
         * 按钮初始化
         */
        $('#linkStation').on('click', function() { showLinkWin(); });
        $('#dislinkStation').on('click', function() { removeLink(); });

        $linkWin = $("#linkWin");
        $linkForm = $("#linkForm");

        $('#link_station').comboData('STATION', false, null, true);
    };

    /**
     * 查询条件
     * @param params 基本查询条件，包含search、sort、order、limit、offset
     */
    function tableParams(params) {
        var localParams = {
        };
        return $.extend(localParams, null, params);
    }

    /**
     * 显示编辑窗口
     * @param saveOrUpdate	0 新增；1 修改
     */
    var sOrU;
    var showEditWin = function(saveOrUpdate) {
        sOrU = saveOrUpdate;
        try {
            $editForm.form('clear');
            if(sOrU === 1) {
                var row = $table.bootstrapTable('getSelections')[0];
                if (!row) {
                    SysMessage.alertNoSelection();
                    return false;
                }
                $editForm.form('load', row);
                $("#editWinTitle").html("修改站点信息");
                $editWin.modal("show");
            } else if(sOrU === 0) {
                $("#editWinTitle").html("添加站点信息");
                $editWin.modal("show");
            }
        } catch(e) {
            SysMessage.alertError(e.message);
        }
    };

    /**
     * 提交表单
     */
    $.fn.submitEditForm = function() {

        if(!$('#name').val()) {
            SysMessage.alertWarning("请填写名称！");
            return false;
        }

        if(!$('#category').val()) {
            SysMessage.alertWarning("请选择分类！");
            return false;
        }

        if(!$('#startStation').val()) {
            SysMessage.alertWarning("请选择始发站！");
            return false;
        }

        if(!$('#endStation').val()) {
            SysMessage.alertWarning("请选择终点站！");
            return false;
        }

        var method = (sOrU === 0 ? "POST" : "PUT");
        $.ajax({
            method : method,
            url : rootDir + baseUrl,
            dataType : "json",
            contentType : "application/json",
            data : JSON.stringify($editForm.serializeJson()),
            success : function(data, status) {
                switch(status) {
                    case "timeout" :
                        SysMessage.alertError('请求超时！请稍后再次尝试。');
                        break;
                    case "error" :
                        SysMessage.alertError('请求错误！请稍后再次尝试。');
                        break;
                    case "success" :
                        if (data.success) {
                            if(data.message) {
                                SysMessage.alertSuccess(data.message);
                            }
                            $editWin.modal("hide");
                            $table.bootstrapTable('refresh');
                        } else {
                            if(data.message) {
                                SysMessage.alertInfo(data.message);
                            }
                        }
                        break;
                    default:
                        SysMessage.alertError('未知错误，请尝试刷新页面或者重新登录！');
                        return;
                }
            }
        });
    };

    /**
     * 执行删除动作的操作
     */
    function remove() {
        try {
            var row = $table.bootstrapTable('getSelections')[0];
            if (!row) {
                SysMessage.alertNoSelection();
                return false;
            }
            bootbox.confirm("确定要删除吗!", function(callback) {
                if (callback) {
                    $.ajax({
                        method : "DELETE",
                        url : rootDir + baseUrl + '/' + row.id,
                        dataType : "json",
                        contentType : "application/json",
                        data : $editForm.serializeJson(),
                        success : function(data, status) {
                            switch(status) {
                                case "timeout" :
                                    SysMessage.alertError('请求超时！请稍后再次尝试。');
                                    break;
                                case "error" :
                                    SysMessage.alertError('请求错误！请稍后再次尝试。');
                                    break;
                                case "success" :
                                    if (data.success) {
                                        if(data.message) {
                                            SysMessage.alertSuccess(data.message);
                                        }
                                    } else {
                                        if(data.message) {
                                            SysMessage.alertInfo(data.message);
                                        }
                                    }
                                    $table.bootstrapTable('refresh');
                                    break;
                                default:
                                    SysMessage.alertError('未知错误，请尝试刷新页面或者重新登录！');
                                    return;
                            }
                        }
                    });
                }
            });
        } catch (e) {
            SysMessage.alertError(e.message);
        }
    }

    /**
     * 显示绑定窗口
     */
    var showLinkWin = function() {
        var selectedRoute = $table.bootstrapTable('getSelections')[0];
        if(!selectedRoute || selectedRoute.length === 0) {
            SysMessage.alertWarning("请选择线路！");
            return false;
        }

        try {
            $linkForm.form('clear');
            $('#link_route').text(selectedRoute.name);
            $linkWin.modal("show");
        } catch(e) {
            SysMessage.alertError(e.message);
        }
    };

    /**
     * 提交表单
     */
    $.fn.submitLinkForm = function() {

        var selectedRoute = $table.bootstrapTable('getSelections')[0];
        if(!selectedRoute || selectedRoute.length === 0) {
            SysMessage.alertWarning("请选择线路！");
            return false;
        }

        if(!$('#link_station').val()) {
            SysMessage.alertWarning("请选择要绑定的站点！");
            return false;
        }

        $.ajax({
            method : "POST",
            url : rootDir + baseUrl + '/' + selectedRoute.id + '/station/' + $('#link_station').val() + '/' + $('#link_sort').val(),
            dataType : "json",
            contentType : "application/json",
            // data : JSON.stringify($editForm.serializeJson()),
            success : function(data, status) {
                switch(status) {
                    case "timeout" :
                        SysMessage.alertError('请求超时！请稍后再次尝试。');
                        break;
                    case "error" :
                        SysMessage.alertError('请求错误！请稍后再次尝试。');
                        break;
                    case "success" :
                        if (data.success) {
                            if(data.message) {
                                SysMessage.alertSuccess(data.message);
                            }
                            $linkWin.modal("hide");
                            $linkTable.bootstrapTable('refresh');
                        } else {
                            if(data.message) {
                                SysMessage.alertInfo(data.message);
                            }
                        }
                        break;
                    default:
                        SysMessage.alertError('未知错误，请尝试刷新页面或者重新登录！');
                        return;
                }
            }
        });
    };

    /**
     * 执行删除动作的操作
     */
    function removeLink() {
        try {
            var row = $linkTable.bootstrapTable('getSelections')[0];
            if (!row) {
                SysMessage.alertNoSelection();
                return false;
            }
            bootbox.confirm("确定要删除吗!", function(callback) {
                if (callback) {
                    $.ajax({
                        method : "DELETE",
                        url : rootDir + baseUrl + '/station/' + row.id,
                        dataType : "json",
                        contentType : "application/json",
                        data : $editForm.serializeJson(),
                        success : function(data, status) {
                            switch(status) {
                                case "timeout" :
                                    SysMessage.alertError('请求超时！请稍后再次尝试。');
                                    break;
                                case "error" :
                                    SysMessage.alertError('请求错误！请稍后再次尝试。');
                                    break;
                                case "success" :
                                    if (data.success) {
                                        if(data.message) {
                                            SysMessage.alertSuccess(data.message);
                                        }
                                    } else {
                                        if(data.message) {
                                            SysMessage.alertInfo(data.message);
                                        }
                                    }
                                    $linkTable.bootstrapTable('refresh');
                                    break;
                                default:
                                    SysMessage.alertError('未知错误，请尝试刷新页面或者重新登录！');
                                    return;
                            }
                        }
                    });
                }
            });
        } catch (e) {
            SysMessage.alertError(e.message);
        }
    }

}(jQuery);