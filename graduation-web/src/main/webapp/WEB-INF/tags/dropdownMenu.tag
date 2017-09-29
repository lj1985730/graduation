<!-- 自定义菜单下拉按钮-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless" %>
<%@ taglib prefix="ls" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 参数 -->
<%@ attribute name="btnName" required="false" rtexprvalue="false" type="java.lang.String" description="按钮名称" %>
<%@ attribute name="id" required="false" rtexprvalue="true" type="java.lang.String" description="绑定id属性" %>

<div id="${id}" class="btn-group">
	<a class="btn purple" href="javascript:;" data-toggle="dropdown">
		<c:if test="${not empty btnName}">
			<i class="fa fa-ellipsis-v"></i>&nbsp;${btnName}&nbsp;<i class="fa fa-angle-down"></i>
		</c:if>
		<c:if test="${empty btnName}">
			<i class="fa fa-ellipsis-v"></i>&nbsp; 更多功能&nbsp;<i class="fa fa-angle-down"></i>
		</c:if>
	</a>
	<ul class="dropdown-menu pull-right">

		<!-- 标签体（各种<li>功能按钮</li>模式）诸如：<li><a href="javascript:importTemp();"><i class="fa fa-cloud-upload"> </i>&nbsp;excel导入</a></li> -->
		<jsp:doBody />
	</ul>
</div>
				

				