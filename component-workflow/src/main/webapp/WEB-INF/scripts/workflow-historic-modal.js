/**
 * 工作流程记录查看弹窗插件
 * @author Liu Jun
 * @version 1.0
 * @date 2017-09-20 15:18:49
 */
(function($){

	'use strict';//开启严格模式
	 
	/**
	 * 调用入口:$('#btn_id').workflowHistoricView({});
     * 赋值实例ID：$('#btn_id').workflowHistoricView('setProcessInstanceId', XXX);
	 */
	$.fn.workflowHistoricView = function(option, agrs) {
		var value = null;
		this.each(function () {
			var $this = $(this);
			var data = $this.data('workflow.historic.view');
			var options = $.extend({}, $.fn.workflowHistoricView.defaults, $this.data(), typeof option === 'object' && option);

			if (typeof option === 'string') {
				if (!data) {
                	return;
				}
				value = $.fn.workflowHistoricView.methods[option](this, data.options, agrs);
				if (option === 'destroy') {
                	$this.removeData('workflow.historic.view');
				}
			}
			if (!data) {
				//为元素添加data属性
				//判断标签类型,如果是select标签则转换成input标签
				if($this[0].tagName != 'BUTTON') {
					var buttonEl = $('<button type="button" class="btn btn-md btn-default table_btn">' +
										'<span class="glyphicon glyphicon-zoom-in"></span>&nbsp;' + options.btnText +
									'</button>');
					buttonEl.insertAfter($this);
					var newEl = $this.next();
					newEl.attr('id', $this.attr('id'));
					newEl.attr('style', $this.attr('style'));
					$this.remove();
					$this = $(newEl);
				}
				$this.data('workflow.historic.view', (new WorkflowHistoricView($this, options)));
			}
        });
		 
		return typeof value === 'undefined' || value == null ? this : value;
	};
	 
	/**
	 * 定义默认配置信息
	 */
	$.fn.workflowHistoricView.defaults = {
        baseUrl : host + '/workflow/',	//业务跟路径
        btnText : '流程查看',		//按钮文本
        modalIdPrefix : '_workflow_historic_modal_',	//弹窗id前缀
        processInstanceId : '',		//流程实例主键
		disabled : false,			//禁用
		cls : '',					//自定义样式,多个样式用逗号隔开 class1,class2
		onBeforeLoad : function(param) {},	//param 请求参数
		onLoadSuccess : function(data) {},	//data加载成功后返回的数据
		onLoadError : function() {}
	};
	
	/**
	 * 控件方法属性
	 */
	$.fn.workflowHistoricView.methods = {
		/**
		 * 设置流程实例ID
		 */
		setProcessInstanceId : function(target, options, processInstanceId) {
		    var $this = $(target).data('workflow.historic.view');
		    if(typeof(processInstanceId) === 'undefined' || !processInstanceId) {
                $this.options.processInstanceId = '';
            } else {
                $this.options.processInstanceId = processInstanceId;
            }
		},
		/**
		 * 获取所有审批历史数据集
		 */
		getData : function(target, options) {
			return options.data;
		},
		enable : function(target, flag) {
            $(target).disable(!flag);
        }
	};
	
	var WorkflowHistoricView = function (el, options) {
        this.options = options;	    //配置项
        this.$el = el;	            //文本框
        this.id = el.attr('id');    //ID
        this.$contentModal = null;	//弹窗
        this.$img = null;			//图片
		this.$grid = null;			//数据网格
        this.contentModalId = null;		//弹窗id
        this.data = [];					//历史数据集合
        this.init();
    };
    
    //初始化控件
    WorkflowHistoricView.prototype.init = function() {
    	this.initModal();	//初始化窗体
    };

    /**
     * 创建下拉列表
     */
    WorkflowHistoricView.prototype.initModal = function() {
        var $this = this;
        var index = $('div[id^="' + this.options.modalIdPrefix + '"]').length + 1;
        $this.contentModalId = $this.options.modalIdPrefix + index;
        var modalHtml =
            '<div id="' + $this.contentModalId + '" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static">' +
            '	<div class="modal-lg modal-dialog">' +
            '		<div class="modal-content">' +
            '        	<div class="modal-header bg-primary">' +
            '           	<h4 class="modal-title">' +
            '               	<i class="glyphicon glyphicon-zoom-in"></i>' +
            '                	<span style="font-weight:bold;">流程查看</span>' +
            '                   <li class="glyphicon glyphicon-remove pull-right" style="transform: scale(1); top:4px;"' +
            '                       onmouseover="this.style.cssText=\'transform: scale(1.5); top:4px;\'"' +
            '                       onmouseout="this.style.cssText=\'transform: scale(1); top:4px;\'"' +
            '                       data-dismiss="modal" data-target="#' + $this.contentModalId + '"></li>' +
            '                </h4>' +
            '			</div>' +
            '			<div class="modal-body" style="height: 472px; overflow: auto;">' +
            '				<div class="sm-col-12" style="height: 220px; overflow: auto;">' +
            '                   <div class="wrapper">' +
            '                       <div id="diagramHolder_' + $this.id + '" class="diagramHolder" style="width: 100%;"></div>' +
            '                   </div>' +
            '				</div>' +
            '				<div class="sm-col-12">' +
            '					<div class="table-container" style="padding-top: 15px; height: 220px; overflow-y: auto;">' +
            '        				<table id="' + $this.contentModalId + '_table" class="table table-striped table-hover table-condensed"' +
            '               			data-search="false" data-show-refresh="false"' +
            '               			data-show-toggle="false" data-show-columns="false" data-detail-view="true"' +
            '               			data-single-select="true" data-click-to-select="true">' +
            '            				<thead>' +
            '                				<tr role="row" class="heading">' +
            '                    				<th data-field="checkbox" data-checkbox="true" data-align="center" data-edit="false"></th>' +
            '                    				<th data-field="persistentState.name" data-sortable="false" data-align="center" data-edit="false">执行环节</th>' +
            '                    				<th data-field="taskDefinitionKey" data-sortable="false" data-align="center" data-edit="false" data-visible="false">任务名称</th>' +
            '                    				<th data-field="name" data-sortable="false" data-align="center" data-edit="false">执行人</th>' +
            '                    				<th data-field="startTime" data-sortable="false" data-align="center" data-edit="false" data-formatter="$.fn.workflowHistoricView.fmtTime">开始时间</th>' +
            '                    				<th data-field="claimTime" data-sortable="false" data-align="center" data-edit="false" data-formatter="$.fn.workflowHistoricView.fmtTime">签收时间</th>' +
            '                    				<th data-field="endTime" data-sortable="false" data-align="center" data-edit="false" data-formatter="$.fn.workflowHistoricView.fmtTime">完成时间</th>' +
            // '                    				<th data-field="id" data-sortable="false" data-align="center" data-edit="false" data-formatter="$.fn.workflowHistoricView.showCommentFmt">操作</th>' +
            '                				</tr>' +
            '            				</thead>' +
            '        				</table>' +
            '    				</div>' +
            '				</div>' +
            '			</div>' +
            '		</div>' +
            '	</div>' +
            '</div>';
        $(modalHtml).appendTo($(document.body));
        $this.$contentModal = $('#' + $this.contentModalId);
        $this.$img = $('#' + $this.contentModalId + '_img');
        $this.$table = $('#' + $this.contentModalId + '_table');
        $('#' + $this.contentModalId + '_closeBtn').on('click', function() {
            $this.hideModal();
        });
        $this.$el.on('click', function() {
            $this.showModal();
        });
        $this.initTable();
    };

    /**
     * 初始化表格
     */
    WorkflowHistoricView.prototype.initTable = function() {
        var $this = this;
        $this.$table.bootstrapTable({
            method : 'GET',
            url : '',
            pagination : false,				    // 是否分页
            cache : false,
            uniqueId : 'id',
            onExpandRow : function(index, row, $detail) {
                var taskId = row.id;
                $detail.html(
                    '<div class="table-container">' +
                    '   <table id="' + $this.contentModalId + '_dtlTable" class="table table-striped table-hover table-condensed">' +
                    '       <thead>' +
                    '           <tr role="row" class="heading">' +
                    '               <th data-field="message" data-sortable="false" data-align="center" data-edit="false">批注人</th>' +
                    '               <th data-field="fullMessage" data-sortable="false" data-align="center" data-edit="false">意见</th>' +
                    '               <th data-field="time" data-sortable="false" data-align="center" data-edit="false" data-formatter="$.fn.workflowHistoricView.fmtTime">批注时间</th>' +
                    '           </tr>' +
                    '       </thead>' +
                    '   </table>' +
                    '</div>'
                );
                $('#' + $this.contentModalId + '_dtlTable').bootstrapTable({
                    method : 'GET',
                    url : host + '/workflow/tasks/' + taskId + '/comments',
                    pagination : false,
                    cache : false,
                    uniqueId : 'id'
                });
            },
            onClickRow : function (row) {
                var taskKey = row.taskDefinitionKey;
                // ProcessDiagramGenerator.processDiagramCanvas.g.getById(taskKey + "_border").attr("opacity", 0.3);
            }
        });

        $this.$table.find('thead tr th').css({"background-color": "#337ab7", "color": "white"});

    };

    /**
     * 显示弹窗
     */
    WorkflowHistoricView.prototype.showModal = function() {

        if(!this.options.processInstanceId) {
            toastr.error('未找到流程数据！');
            return false;
        }

        this.loadDiagram();
        this.loadTable();
        // $(body).addClass('modal-open');
        // $(body).append("<div id='" + this.contentModalId + "_backdropId' class='modal-backdrop fade in'></div>");
        this.$contentModal.modal('show');
    };

    /**
     * 加载制流程图
     */
    WorkflowHistoricView.prototype.loadDiagram = function() {
        var $this = this;

        $.ajax({
            type : "GET",
            url : $this.options.baseUrl + 'processInstances/' + $this.options.processInstanceId + '/processDefinitionId',
            dataType : "text",
            async : false,
            success: function (data) {
                if (data) {
                    $this.drawDiagram(data);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error('加载流程图像失败！');
            }
        });
    };

    /**
	 * 绘制流程图
     * @param processDefinitionId 流程定义ID
     */
    WorkflowHistoricView.prototype.drawDiagram = function(processDefinitionId) {
    	var $this = this;

        var processInstanceId = $this.options.processInstanceId;

        ProcessDiagramGenerator.options = {
            diagramBreadCrumbsId : "diagramBreadCrumbs_" + $this.id,
            diagramHolderId : "diagramHolder_" + $this.id,
            diagramInfoId : "diagramInfo_" + $this.id,
            on : {
                click : function (canvas, element, contextObject) {
                    var mouseEvent = this;

                    //点击节点选中对应grid行
                    $this.$table.bootstrapTable('uncheckAll');
                    $this.$table.bootstrapTable('checkBy', { field : 'taskDefinitionKey', values : [contextObject.id]});

                    if (contextObject.getProperty("type") == "callActivity") {
                        var processDefinitonKey = contextObject.getProperty("processDefinitonKey");
                        var processDefinitons = contextObject.getProperty("processDefinitons");
                        var processDefiniton = processDefinitons[0];
                        console.log("Load callActivity '" + processDefiniton.processDefinitionKey + "', contextObject: ", contextObject);

                        // Load processDefinition
                        ProcessDiagramGenerator.drawDiagram(processDefiniton.processDefinitionId);
                    }
                },
                rightClick : function (canvas, element, contextObject) {
                    var mouseEvent = this;
                    console.log("[RIGHTCLICK] mouseEvent: %o, canvas: %o, clicked element: %o, contextObject: %o", mouseEvent, canvas, element, contextObject);
                },
                over : function (canvas, element, contextObject) {
                    var mouseEvent = this;
                    ProcessDiagramGenerator.showActivityInfo(contextObject);
                },
                out : function (canvas, element, contextObject) {
                    var mouseEvent = this;
                    ProcessDiagramGenerator.hideInfo();
                }
            },
            finish : function(canvas, element, contextObject) {
                var $diagram = $('#diagramHolder_' + $this.id).find('div[class="diagram"]');
                $diagram.find('svg')[0].setAttribute('width', $diagram.width());
                $diagram.find('svg')[0].setAttribute('height', $diagram.height());
                $diagram.find('svg').find('text').find('tspan').attr('dy', 10);
                $diagram.css("margin", "auto");
                ProcessDiagramGenerator.drawHighLights(processInstanceId);
            }
        };

        var baseUrl = window.document.location.protocol + "//" + window.document.location.host + "/";
        var shortenedUrl = window.document.location.href.replace(baseUrl, "");
        baseUrl = baseUrl + shortenedUrl.substring(0, shortenedUrl.indexOf("/"));

        ActivitiRest.options = {
            processInstanceHighLightsUrl: baseUrl + "/process-instance/{processInstanceId}/highlights",
            processDefinitionUrl: baseUrl + "/process-definition/{processDefinitionId}/diagram-layout",
            processDefinitionByKeyUrl: baseUrl + "/process-definition/{processDefinitionKey}/diagram-layout"
        };
        if (processDefinitionId) {
            ProcessDiagramGenerator.drawDiagram(processDefinitionId);
        } else {
            toastr.error('缺少流程定义！');
        }
    };

    /**
     * 向服务器请求网格数据
     */
    WorkflowHistoricView.prototype.loadTable = function() {
        var $this = this;
        $this.$table.bootstrapTable('refresh', {
            url : $this.options.baseUrl + 'processInstances/' + $this.options.processInstanceId + '/historicTasks',
            silent : true
        });
    };

    /**
     * 隐藏弹窗
     */
    WorkflowHistoricView.prototype.hideModal = function() {
		if(this.$contentModal.css('display') != 'none') {
            // $(body).removeClass('modal-open');
            // $(body).find("#" + this.contentModalId + "_backdropId").remove();
            this.$contentModal.modal('hide');
            // $("#diagramHolder_" + this.id).empty();
		}
	};

    /**
     * 格式化时间
     */
    $.fn.workflowHistoricView.fmtTime = function(value) {
        if(!value) {
            return "";
        }
        var date = new Date(value);
        return date.getFullYear() +
            '-' + fillZero(date.getMonth() + 1) +
            '-' + fillZero(date.getDay()) +
            ' ' + fillZero(date.getHours()) +
            ':' + fillZero(date.getMinutes()) +
            ':' + fillZero(date.getSeconds());
    };

    /**
     * 格式化时间填充0
     */
    var fillZero = function(value) {
        if(!value && value != 0 && value != '0') {
            return '';
        }
        if(Number(value) < 10) {
            return '0' + value;
        }
        return value;
    };

    /**
     * 显示审批意见
     */
    $.fn.workflowHistoricView.showCommentFmt = function(value, row, index) {
        return '<a style="cursor: pointer;" onclick="$.fn.workflowHistoricView.showComment(this, ' + index + ');">查看意见</a>';
    };

    /**
     * 显示审批意见
     */
    $.fn.workflowHistoricView.showComment = function(element, index) {
        var $a = $(element);
        var $table = $a.closest('.table');
        $table.bootstrapTable('expandRow', index);
    };

})(jQuery);

