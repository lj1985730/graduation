<!-- 自定义页头导航条 -->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 参数 -->
<%@ attribute name="father" required="false" rtexprvalue="true" type="java.lang.String" description="父级菜单" %>
<%@ attribute name="model" required="true" rtexprvalue="true" type="java.lang.String" description="模块名称" %>

<div class="page-bar">
	<ul class="page-breadcrumb">
		<li>
			<i class="fa fa-home"></i>&nbsp;<a href="javascript:openUrl('home');">管理首页</a>
			<i class="fa fa-angle-right"></i>
		</li>
		<c:if test="${not empty father}">
		<li>
			<a href="javascript:;">${father}</a>
			<i class="fa fa-angle-right"></i>
		</li>
		</c:if>
		<li><a href="javascript:;">${model}</a></li>
	</ul>
</div>