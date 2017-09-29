<!-- 自定义时间选择控件-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>

<%@ attribute name="id" required="true" rtexprvalue="false" type="java.lang.String" description="ID" %>
<%@ attribute name="startName" required="true" rtexprvalue="true" type="java.lang.String" description="表单开始name" %>
<%@ attribute name="endName" required="true" rtexprvalue="true" type="java.lang.String" description="表单结束name" %>
<%@ attribute name="placeholder" required="false" rtexprvalue="true" type="java.lang.String" description="占位内容" %>
<%@ attribute name="tagClass" required="false" rtexprvalue="false" type="java.lang.String" description="样式" %>
<%@ attribute name="format" required="false" rtexprvalue="false" type="java.lang.String" description="格式" %>
<%@ attribute name="view" required="false" rtexprvalue="false" type="java.lang.String" description="初始窗口:2day 3month" %>
<%@ attribute name="startValue" required="false" rtexprvalue="true" type="java.lang.String" description="开始值" %>
<%@ attribute name="endValue" required="false" rtexprvalue="true" type="java.lang.String" description="结束值" %>
<%@ attribute name="start" required="false" rtexprvalue="false" type="java.lang.String" description="最小日期" %>
<%@ attribute name="end" required="false" rtexprvalue="false" type="java.lang.String" description="最大日期" %>
<%@ attribute name="onChange" required="false" rtexprvalue="false" type="java.lang.String" description="修改触发" %>

<div class="input-group input-daterange col-md-12">
	<input class="form-control col-md-4 ${tagClass}" id="datapicker_range_start_${id}" name="${startName}" placeholder="${placeholder}" value="${startValue}" style="min-width: 110px;" />
    <div class="input-group-addon" style="width: 39px;">to</div>
	<input class="form-control col-md-4 ${tagClass}" id="datapicker_range_end_${id}" name="${endName}" placeholder="${placeholder}" value="${endValue}" style="min-width: 110px;" />
</div>

<script type="text/javascript">
	if(!datapicker_option_range) {
		var datapicker_option_range = [];
	}
	
	datapicker_option_range['${id}'] = {
		language : 'zh-CN',
		format : 'yyyy-mm-dd',
		startView : 2,
	    minView : 2,
	    autoclose : true,
	    todayBtn : true,
	    forceParse : true,
	    todayHighlight: true
	}
	
	if('${format}') {
		$.extend(datapicker_option_range['${id}'], {format : '${format}'});
	}
	if('${view}') {
		$.extend(datapicker_option_range['${id}'], {startView : '${view}', minView : '${view}'});
	}
	if('${start}') {
		if('${start}' == 'current') {
			$.extend(datapicker_option_range['${id}'], {startDate : new Date()});
		} else {
			$.extend(datapicker_option_range['${id}'], {startDate : '${start}'});
		}
	}
	if('${end}') {
		if('${end}' == 'current') {
			$.extend(datapicker_option_range['${id}'], {endDate : new Date()});
		} else {
			$.extend(datapicker_option_range['${id}'], {endDate : '${end}'});
		}
	}
	
	$('#datapicker_range_start_${id}').datetimepicker(datapicker_option_range['${id}']).on('changeDate', function() {	//设置时间约束-结束时间为最大时间

		$('#datapicker_range_end_${id}').datetimepicker('setStartDate', this.value);
		/**
		 * 增加onchange事件
		 */
		if('${onChange}') {
			eval('${onChange}');
		}
	});
	$('#datapicker_range_end_${id}').datetimepicker(datapicker_option_range['${id}']).on('changeDate', function() {	//设置时间约束-起始时间为最小时间

		$('#datapicker_range_start_${id}').datetimepicker('setEndDate', this.value);
		/**
		 * 增加onchange事件
		 */
		if('${onChange}') {
			eval('${onChange}');
		}
	});
</script>