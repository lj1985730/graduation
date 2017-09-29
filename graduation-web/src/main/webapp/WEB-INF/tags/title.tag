<!-- 自定义页面主标题-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless" %>
<%@ taglib prefix="ls" tagdir="/WEB-INF/tags" %>

<!-- 参数 -->
<%@ attribute name="name" required="false" rtexprvalue="false" type="java.lang.String" description="页面标题" %>

<div class="portlet-title">
	<div class="caption">
		<i class="fa fa-th-list"></i>&nbsp;${name}
	</div>
	<div class="actions">

		<!-- 标签体（各种功能按钮） -->
		<jsp:doBody />
		<ls:fullscreen />
		
	</div>
</div>