/**
 * 任务中心
 */
(function ($) {

    'use strict';       //开启严格模式

    var basePath = host + '/workflow';

    var selectedTaskId;             //选中的任务ID
    var selectedProcessInstanceId;   //选中的任务所在流程实例ID

    var $homePanel;   //主窗体
	var $todoListTable;	//待办列表
    var $historicListTable;	//已办列表
    var timeoutId;      //查询延迟

    var $businessViewPanel, $businessViewContainer; //数据查看面板以及面板内容容器
    var $disposePanel, $disposeContainer;   //处理面板以及面板内容容器
    var $retryPanel, $retryContainer;   //再次提交面板以及面板内容容器

    /**
	 * 初始化
     */
    $(function () {
        $('#showWorkflowViewBtn').workflowHistoricView({});  //流程查看

        timeoutId = 0;

        initHomePanel();
        initBusinessViewPanel();
        initDisposePanel();
        initRetryPanel();

	});

    /**
     * 面板切换
     */
    $.fn.switchPanel = function(panelName) {
        switch (panelName) {
            case 'home':
                $homePanel.addClass('in').addClass('active');
                $businessViewPanel.removeClass('in').removeClass('active');
                $disposePanel.removeClass('in').removeClass('active');
                $disposeContainer.empty();
                $retryPanel.removeClass('in').removeClass('active');
                $retryContainer.empty();
                break;
            case 'businessView':
                $businessViewPanel.addClass('in').addClass('active');
                $homePanel.removeClass('in').removeClass('active');
                $disposePanel.removeClass('in').removeClass('active');
                $disposeContainer.empty();
                $retryPanel.removeClass('in').removeClass('active');
                $retryContainer.empty();
                break;
            case 'dispose':
                $disposePanel.addClass('in').addClass('active');
                $homePanel.removeClass('in').removeClass('active');
                $businessViewPanel.removeClass('in').removeClass('active');
                $businessViewContainer.empty();
                $retryPanel.removeClass('in').removeClass('active');
                $retryContainer.empty();
                break;
            case 'retry':
                $retryPanel.addClass('in').addClass('active');
                $homePanel.removeClass('in').removeClass('active');
                $businessViewPanel.removeClass('in').removeClass('active');
                $businessViewContainer.empty();
                $disposePanel.removeClass('in').removeClass('active');
                $disposeContainer.empty();
                break;
        }
    };

    /**
     * 初始化主面板
     */
    var initHomePanel = function() {
        $homePanel = $('#homePanel');
        initTodo();
        initHistoric();
    };

    /**
     * 待办Tab初始化
     */
    var initTodo = function() {
        dateRangePicker("#todoListSearch_startDate", "#todoListSearch_endDate");    //待办日期查询组件

        $('#todoListSearch_startDate').on('change', function() {
            clearTimeout(timeoutId);
            timeoutId = setTimeout(function () {
                refreshTodoTable();
            }, 500);
        });

        $('#todoListSearch_endDate').on('change', function() {
            clearTimeout(timeoutId);
            timeoutId = setTimeout(function () {
                refreshTodoTable();
            }, 500);
        });

        /**
         * 初始化待办列表
         */
        $todoListTable = $('#todoListTable');	//待办列表
        $todoListTable.bootstrapTable({
            method : 'GET',
            url : basePath + "/user/sessionUser/todoTasks",
            pagination : true,  //是否分页
            sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
            pageNumber : 1,  //初始化加载第一页，默认第一页
            pageSize : 100, //每页的记录行数（*）
            pageList : [5, 10, 25, 50, 100], //可供选择的每页的行数（*）
            cache : false,
            uniqueId : 'id',
            queryParams: function (params) {
                return $.extend(params, {
                    limit : params.limit, //页面大小
                    offset : params.offset, //页码
                    processInstanceId : $("#todoListSearch_procInsId").val(),
                    beginDateStr : $("#todoListSearch_startDate").val(),
                    endDateStr : $("#todoListSearch_endDate").val()
                });
            }
        });

        /**
         * 待办流程编号搜索框监听
         */
        $('#todoListSearch_procInsId').off('keyup drop blur').on('keyup drop blur', function (event) {
            if ($.inArray(event.keyCode, [37, 38, 39, 40]) > -1) {
                return;
            }
            clearTimeout(timeoutId);
            timeoutId = setTimeout(function () {
                refreshTodoTable();
            }, 500);
        });
    };

    /**
     * 刷新代办table
     */
    var refreshTodoTable = function() {
        $todoListTable.bootstrapTable('removeAll');
        $todoListTable.bootstrapTable('refresh');
    };

    /**
     * 已办Tab初始化
     */
    var initHistoric = function() {
        dateRangePicker("#historicListSearch_startDate", "#historicListSearch_endDate");    //已办日期查询组件

        $('#historicListSearch_startDate').on('change', function() {
            clearTimeout(timeoutId);
            timeoutId = setTimeout(function () {
                refreshHistoricTable();
            }, 500);
        });

        $('#historicListSearch_endDate').on('change', function() {
            clearTimeout(timeoutId);
            timeoutId = setTimeout(function () {
                refreshHistoricTable();
            }, 500);
        });

        /**
         * 初始化已办列表
         */
        $historicListTable = $('#historicListTable');	//待办列表
        $historicListTable.bootstrapTable({
            method : 'GET',
            url : basePath + "/user/sessionUser/historicTasks",
            pagination : true,  //是否分页
            sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
            pageNumber : 1,  //初始化加载第一页，默认第一页
            pageSize : 10, //每页的记录行数（*）
            pageList : [5, 10, 25, 50, 100], //可供选择的每页的行数（*）
            cache : false,
            uniqueId : 'id',
            queryParams: function (params) {
                return $.extend(params, {
                    limit : params.limit, //页面大小
                    offset : params.offset, //页码
                    processInstanceId : $("#historicListSearch_procInsId").val(),
                    beginDateStr : $("#historicListSearch_startDate").val(),
                    endDateStr : $("#historicListSearch_endDate").val()
                });
            }
        });

        /**
         * 已办流程编号搜索框监听
         */
        $('#historicListSearch_procInsId').off('keyup drop blur').on('keyup drop blur', function (event) {
            if ($.inArray(event.keyCode, [37, 38, 39, 40]) > -1) {
                return;
            }
            clearTimeout(timeoutId);
            timeoutId = setTimeout(function () {
                refreshHistoricTable();
            }, 500);
        });
    };

    /**
     * 刷新已办table
     */
    var refreshHistoricTable = function() {
        $historicListTable.bootstrapTable('removeAll');
        $historicListTable.bootstrapTable('refresh');
    };

    /**
     * 点击显示业务信息
     * @param value		对应值
     * @param row		对应行
     * @param index		行号
     */
    $.fn.showBusinessViewPanelFormatter = function(value, row, index) {
        return sprintf(
            '<a style="cursor: pointer;" onclick="$.fn.showBusinessViewPanel(\'%s\',\'%s\');">%s</a>',
            row.processInstanceId, row.taskDefinitionKey, value
        );
    };

    /**
     * 待办操作格式化
     * @param value		对应值
     * @param row		对应行
     * @param index		行号
     */
    $.fn.todoOperateFormatter = function(value, row, index) {
        var content = '';
        if(!row.assigneeId) {
            content += sprintf('<a style="cursor: pointer;" onclick="$.fn.claimTask(\'%s\');">签收</a>', row.taskId);
        } else {
            content += sprintf('<a style="cursor: pointer;" onclick="$.fn.disposeTask(\'%s\',\'%s\',\'%s\',\'%s\');">办理</a>',
                row.processInstanceId, row.taskDefinitionKey, row.taskId, row.processState);
        }

        if(row.processState == ''|| row.processState == 'null'
            || (row.processState != 'PENDING' && row.processState != 'SUSPENDED' && row.processState != 'FINISHED')) {
            content += sprintf(
                '&nbsp;&nbsp;<a style="cursor: pointer;" href="javascript:void(0);" onclick="$.fn.removeProcessInstance(\'%s\', \'%s\', \'%s\', \'%s\')">删除</a>',
                row.processInstanceId, row.businessTable, row.businessColumn, row.businessId);
        }

        // content += sprintf('&nbsp;&nbsp;<a style="cursor: pointer;" onclick="$.fn.showHistoricModal(\'%s\')">跟踪</a>', row.processInstanceId);
        return content;
    };

    /**
     * 签收任务
     * @param taskId 任务ID
     */
    $.fn.claimTask = function(taskId) {
        bootbox.confirm({
            title : '<i class="glyphicon glyphicon-import"></i>&nbsp;签收确认',
            message : '确认签收此任务？',
            buttons : {
                confirm : { label : '<i class="glyphicon glyphicon-ok"></i>&nbsp;确认', className: 'btn-success' },
                cancel : { label : '<i class="glyphicon glyphicon-remove"></i>&nbsp;取消', className : 'btn-danger' }
            },
            callback : function(result) {
                if(!result) {
                    return;
                }
                $.ajax({
                    type : 'PUT',
                    url : basePath + '/task/' + taskId + '/claim',
                    dataType : 'json',
                    contentType : 'application/json',
                    success : function(data) {
                        toastr.success('签收完成！');
                        refreshTodoTable();
                    },
                    error : function(data) {
                        toastr.error('签收失败：' + data.responseText);
                        refreshTodoTable();
                    }
                });
            }
        });
    };

    /**
     * 处理任务
     * @param processInstanceId 流程实例ID
     * @param taskDefinitionKey 任务定义KEY
     * @param taskId 任务ID
     * @param processState 流程状态
     */
    $.fn.disposeTask = function(processInstanceId, taskDefinitionKey, taskId, processState) {
        if(!processInstanceId || !taskDefinitionKey) {
            toastr.error('未找到对应资源！');
            return false;
        }

        selectedTaskId = taskId;    //记录选中的任务ID
        selectedProcessInstanceId = processInstanceId;   //记录流程实例ID

        if(processState && processState == 'REJECTED') {    //驳回的数据
            showRetry(selectedProcessInstanceId, taskDefinitionKey);
        } else {
            showDispose(selectedProcessInstanceId, taskDefinitionKey);
        }
    };

    /**
     * 删除流程实例
     * @param processInstanceId 流程实例ID
     * @param businessTable 业务表
     * @param businessColumn 主键列
     * @param businessId 主键
     */
    $.fn.removeProcessInstance = function(processInstanceId, businessTable, businessColumn, businessId) {
        if(!processInstanceId || !businessTable || !businessColumn || !businessId) {
            toastr.warning("缺少参数！");
            return false;
        }

        bootbox.confirm({
            title : '<i class="glyphicon glyphicon-import"></i>&nbsp;删除确认',
            message : '确认删除此流程？',
            buttons : {
                confirm : { label : '<i class="glyphicon glyphicon-ok"></i>&nbsp;确认', className: 'btn-success' },
                cancel : { label : '<i class="glyphicon glyphicon-remove"></i>&nbsp;取消', className : 'btn-danger' }
            },
            callback : function(result) {
                if(!result) {
                    return;
                }
                $.ajax({
                    url : sprintf('%s/processInstance/%s', basePath, processInstanceId),
                    type : 'DELETE',
                    dataType : 'json',
                    contentType : 'application/json',
                    data : JSON.stringify({
                        businessTable : businessTable,
                        businessColumn : businessColumn,
                        businessId : businessId
                    }),
                    success : function() {
                        toastr.success("删除成功！");
                        refreshTodoTable();
                    },
                    error : function(data) {
                        toastr.error(data, "删除失败！");
                    }
                });
            }
        });
    };

    /**
     * 显示跟踪信息
     * @param processInstanceId 流程ID
     */
    $.fn.showHistoricModal = function(processInstanceId) {
        var $hiddenBtn = $('#showWorkflowViewBtn');
        $hiddenBtn.workflowHistoricView('setProcessInstanceId', processInstanceId);
        $hiddenBtn.trigger('click');
    };

    /**
     * 初始化业务数据查看面板
     */
    var initBusinessViewPanel = function() {
        //面板及容器
        $businessViewPanel = $('#businessViewPanel');
        $businessViewContainer = $businessViewPanel.find('.formContainer');
    };

    /**
     * 点击显示详情
     * @param processInstanceId		流程实例ID
     * @param taskDefinitionKey		流程任务定义KEY
     */
    $.fn.showBusinessViewPanel = function(processInstanceId, taskDefinitionKey) {
        if(!processInstanceId || !taskDefinitionKey) {
            toastr.error('未找到对应资源！');
            return false;
        }
        $businessViewContainer.load(
            sprintf('%s/readonlyBusinessFormView?processInstanceId=%s&taskDefinitionKey=%s',
                basePath, processInstanceId, taskDefinitionKey
            ) + " .container-fluid",
            { },
            function(html) {
                $businessViewContainer.find('button').remove();
                if($businessViewContainer.find('#buttonToolbar').length > 0) {  //删除表单页面中的按钮，采用通用按钮
                    $businessViewContainer.find('#buttonToolbar').remove();
                }
                if($businessViewContainer.find('.btn-group').length > 0) {  //删除表单页面中的按钮，采用通用按钮
                    $businessViewContainer.find('.btn-group').remove();
                }
                $(html).find("script").appendTo($businessViewContainer);
                //流程查看按钮
                $('#businessViewPanel_historicView').workflowHistoricView('setProcessInstanceId', processInstanceId);
                $.fn.switchPanel('businessView');
            }
        );
    };

    /**
     * 初始化处理窗体
     */
    var initDisposePanel = function() {
        $disposePanel = $('#disposePanel');
        $disposeContainer = $disposePanel.find('.formContainer');
        $('#disposePanel_approvalBtn').on('click', function() {
            doDispose();
        });
    };

    /**
     * 显示处理窗口
     */
    var showDispose = function(processInstanceId, taskDefinitionKey) {
        $disposeContainer = $disposePanel.find('.formContainer');
        $disposeContainer.load(
            sprintf('%s/businessFormView?processInstanceId=%s&taskDefinitionKey=%s',
                basePath, processInstanceId, taskDefinitionKey
            ) + " .container-fluid",
            { },
            function(html) {
                $disposeContainer.find('button').remove();
                if($disposeContainer.find('#buttonToolbar').length > 0) {  //删除表单页面中的按钮，采用通用按钮
                    $disposeContainer.find('#buttonToolbar').remove();
                }
                if($disposeContainer.find('.btn-group').length > 0) {  //删除表单页面中的按钮，采用通用按钮
                    $disposeContainer.find('.btn-group').remove();
                }
                $(html).find("script").appendTo($disposeContainer);
                //流程查看按钮
                $('#disposePanel_historicView').workflowHistoricView('setProcessInstanceId', processInstanceId);
                $.fn.switchPanel('dispose');
            }
        );
    };

    /**
     * 提交审批结果
     */
    var doDispose = function() {
        var result = $('[name="approvalResult"]:checked').val();    //radio选项
        if(!selectedTaskId) {
            bootbox.alert({
                title : '<i class="glyphicon glyphicon-exclamation-sign"></i>&nbsp;提示',
                message : '请选择操作的任务！'
            });
            return false;
        }
        var operate, url;
        if(result == 1) {
            operate = '同意';
            url = sprintf('%s/task/%s/pass', basePath, selectedTaskId);
        } else {
            if(!$('#comment').val()) {
                bootbox.alert({
                    title : '<i class="glyphicon glyphicon-exclamation-sign"></i>&nbsp;提示',
                    message : '驳回任务请填写意见！'
                });
                return false;
            }
            operate = '驳回';
            url = sprintf('%s/task/%s/reject', basePath, selectedTaskId);
        }

        bootbox.confirm({
            title : sprintf('<i class="glyphicon glyphicon-exclamation-sign"></i>&nbsp;确认%s', operate),
            message : sprintf('确认%s此任务？', operate),
            buttons : {
                confirm : { label : '<i class="glyphicon glyphicon-ok"></i>&nbsp;确认', className: 'btn-success' },
                cancel : { label : '<i class="glyphicon glyphicon-remove"></i>&nbsp;取消', className : 'btn-danger' }
            },
            callback : function(result) {
                if(!result) {
                    return;
                }

                $.ajax({
                    type : 'PUT',
                    url : url,
                    dataType : 'json',
                    contentType : 'application/json',
                    data : JSON.stringify({
                        processInstanceId : selectedProcessInstanceId,
                        comment : $('#comment').val()
                    }),
                    success : function(data) {
                        toastr.success('处理成功！');
                        refreshTodoTable();
                        $.fn.switchPanel('home');
                    },
                    error : function(data) {
                        toastr.error('处理失败：' + data.responseText);
                    }
                });
            }
        });
    };

    /**
     * 初始化再次提交窗体
     */
    var initRetryPanel = function() {
        $retryPanel = $('#retryPanel');
        $retryContainer = $retryPanel.find('.formContainer');
        $('#retryPanel_saveBtn').on('click', function() {
            $.fn.formSubmit(function() {
                toastr.success('保存成功！');
            });
        });
        $('#retryPanel_submitBtn').on('click', function() {
            $.fn.formSubmit($.fn.doRetry());
        });
    };

    /**
     * 显示再次提交窗口
     */
    var showRetry = function(processInstanceId, taskDefinitionKey) {
        $retryContainer.load(
            sprintf('%s/editableBusinessFormView?processInstanceId=%s&taskDefinitionKey=%s',
                basePath, processInstanceId, taskDefinitionKey
            ) + " .container-fluid",
            { },
            function(html) {
                // $retryContainer.find('button').remove();
                if($retryContainer.find('#buttonToolbar').length > 0) {  //删除表单页面中的按钮，采用通用按钮
                    $retryContainer.find('#buttonToolbar').remove();
                }
                // if($retryContainer.find('.btn-group').length > 0) {  //删除表单页面中的按钮，采用通用按钮
                //     $retryContainer.find('.btn-group').remove();
                // }
                $(html).find("script").appendTo($retryContainer);
                //流程查看按钮
                $('#retryPanel_historicView').workflowHistoricView('setProcessInstanceId', processInstanceId);
                $.fn.switchPanel('retry');
            }
        );
    };

    /**
     * 再次提交审批结果
     */
    $.fn.doRetry = function() {
        if(!selectedTaskId) {
            bootbox.alert({
                title : '<i class="glyphicon glyphicon-exclamation-sign"></i>&nbsp;提示',
                message : '请选择操作的任务！'
            });
            return false;
        }
        bootbox.confirm({
            title : '<i class="glyphicon glyphicon-exclamation-sign"></i>&nbsp;确认再次提交',
            message : '确认再次提交此任务？',
            buttons : {
                confirm : { label : '<i class="glyphicon glyphicon-ok"></i>&nbsp;确认', className: 'btn-success' },
                cancel : { label : '<i class="glyphicon glyphicon-remove"></i>&nbsp;取消', className : 'btn-danger' }
            },
            callback : function(result) {
                $.ajax({
                    type : 'PUT',
                    url : sprintf('%s/task/%s/pass', basePath, selectedTaskId),
                    dataType : 'json',
                    contentType : 'application/json',
                    data : JSON.stringify({
                        processInstanceId : selectedProcessInstanceId,
                        comment : '再次提交'
                    }),
                    success : function(data) {
                        toastr.success('处理成功！');
                        refreshTodoTable();
                        $.fn.switchPanel('home');
                    },
                    error : function(data) {
                        toastr.error('处理失败：' + data.responseText);
                        refreshTodoTable();
                    }
                });
            }
        });
    };
})(jQuery);