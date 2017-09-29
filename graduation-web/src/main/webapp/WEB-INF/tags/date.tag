<!-- 自定义时间选择控件-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>

<%@ attribute name="id" required="true" rtexprvalue="true" type="java.lang.String" description="组件id" %>
<%@ attribute name="name" required="true" rtexprvalue="true" type="java.lang.String" description="表单name" %>
<%@ attribute name="placeholder" required="false" rtexprvalue="true" type="java.lang.String" description="占位内容" %>
<%@ attribute name="tagClass" required="false" rtexprvalue="false" type="java.lang.String" description="样式" %>
<%@ attribute name="format" required="false" rtexprvalue="false" type="java.lang.String" description="格式" %>
<%@ attribute name="view" required="false" rtexprvalue="false" type="java.lang.String" description="初始窗口:2day 3month" %>
<%@ attribute name="value" required="false" rtexprvalue="true" type="java.lang.String" description="值" %>
<%@ attribute name="start" required="false" rtexprvalue="false" type="java.lang.String" description="最小日期" %>
<%@ attribute name="end" required="false" rtexprvalue="false" type="java.lang.String" description="最大日期" %>

<div class="input-group date">
	<input class="form-control ${tagClass}" id="${id}" name="${name}" placeholder="${placeholder}" value="${value}" />
	<span class="input-group-addon"><i class="icon-calendar"></i></span>
</div>

<script type="text/javascript">
	if(!datapicker_option) {
		var datapicker_option = [];
	}
	
	datapicker_option_${id} = {
		language : 'zh-CN',
		format : 'yyyy-mm-dd',
		startView : 2,
	    minView : 2,
	    autoclose : true,
	    todayBtn : true,
	    todayHighlight: true
	}
	
	if('${format}') {
		$.extend(datapicker_option_${id}, {format : '${format}'});
		if('${format}' == 'yyyy-mm-dd hh:ii') {
			$.extend(datapicker_option_${id}, {startView : 2, minView : 0});
		} else if('${format}' == 'yyyy-mm-dd') {
			$.extend(datapicker_option_${id}, {startView : 2, minView : 2});
		} else if('${format}' == 'yyyy-mm') {
			$.extend(datapicker_option_${id}, {startView : 3, minView : 3});
		} else if('${format}' == 'yyyy') {
			$.extend(datapicker_option_${id}, {startView : 4, minView : 4});
		}
	} else if('${view}') {
		$.extend(datapicker_option_${id}, {startView : '${view}', minView : '${view}'});
	}
	if('${start}') {
		if('${start}' == 'current') {
			$.extend(datapicker_option_${id}, {startDate : new Date()});
		} else {
			$.extend(datapicker_option_${id}, {startDate : '${start}'});
		}
	}
	if('${end}') {
		if('${end}' == 'current') {
			$.extend(datapicker_option_${id}, {endDate : new Date()});
		} else {
			$.extend(datapicker_option_${id}, {endDate : '${end}'});
		}
	}

	$('#${id}').datetimepicker(datapicker_option_${id});
</script>