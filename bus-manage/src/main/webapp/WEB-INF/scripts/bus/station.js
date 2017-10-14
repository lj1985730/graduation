!function ($) {

    'use strict';

    var baseUrl = 'bus/station';
    var $table;
    var $editWin;
    var $editForm;

    /**
     * 初始化
     */
    $(function() {

        $table = $('#stationTable');

        $table.bootstrapTable(
            $.extend({
                url : rootDir + 'bus/stations',
                formatSearch : function () {
                    return '搜索站点名称';
                }
            }, generalTableOption)
        );

        /**
         * 按钮初始化
         */
        $('#addStation').on('click', function() { showEditWin(0); });
        $('#modifyStation').on('click', function() { showEditWin(1); });
        $('#deleteStation').on('click', function() { remove(); });

        $editWin = $("#editWin");
        $editForm = $("#editForm");
    });

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
                        } else {
                            if(data.message) {
                                SysMessage.alertInfo(data.message);
                            }
                        }
                        $editWin.modal("hide");
                        $table.bootstrapTable('refresh');
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

}(jQuery);