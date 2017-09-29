<!-- 自定义删除按钮-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 触发JS方法，不写默认为lsGridDelete -->
<%@ attribute name="onClick" required="false" rtexprvalue="true" type="java.lang.String" description="点击事件" %>
<%@ attribute name="id" required="false" rtexprvalue="true" type="java.lang.String" description="绑定id属性" %>

<c:if test="${not empty onClick}">
	<button type="button" class="btn red" onClick="${onClick}" id="${id}"><i class="fa fa-remove"></i>&nbsp;删除</button>
</c:if>
<c:if test="${empty onClick}">
	<button type="button" class="btn red" onClick="lsGridDelete();" id="${id}"><i class="fa fa-remove"></i>&nbsp;删除</button>
</c:if>
