<!-- 自定义保存按钮-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="onClick" required="false" rtexprvalue="true" type="java.lang.String" description="点击事件" %>

<c:if test="${not empty onClick}">
	<button type="button" class="btn green" onClick="${onClick}"><i class="fa fa-check"></i>&nbsp;保存</button>
</c:if>
<c:if test="${empty onClick}">
	<button type="button" class="btn green" onClick="lsGridSave();"><i class="fa fa-check"></i>&nbsp;保存</button>
</c:if>

