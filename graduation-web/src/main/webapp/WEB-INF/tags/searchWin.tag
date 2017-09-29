<!-- 自定义模态弹窗-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless" %>
<%@ taglib prefix="ls" tagdir="/WEB-INF/tags" %>

<!-- 参数 -->
<%@ attribute name="id" required="true" rtexprvalue="false" type="java.lang.String" description="唯一标识" %>
<%@ attribute name="table" required="true" rtexprvalue="false" type="java.lang.String" description="搜索表格id" %>

<div class="theme-panel hidden-xs hidden-sm" >
	<div id="${id}" class="theme-options">
		<form id="${id}Form" class="form-inline" role="form">
			<!-- 标签体 -->
			<jsp:doBody />
			<!-- 按钮 -->
			<div class="theme-option col-md-12" style="padding: 15px;">
				<ls:confirm onClick="$('#${table}').bootstrapTable('refresh');$('#${id}').toggle();" />
				<button type="button" class="btn red" onClick="$('#${id}').toggle();"><i class="fa fa-remove"></i>&nbsp;关闭</button>
				<button type="button" class="btn yellow" onClick="$('#${id}Form').form('clear');"><i class="fa fa-refresh"></i>&nbsp;清空</button>
			</div>
		</form>
	</div>
</div>