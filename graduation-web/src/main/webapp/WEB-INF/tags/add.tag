<!-- 自定义新增按钮-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>

<%@ attribute name="onClick" required="true" rtexprvalue="true" type="java.lang.String" description="点击事件" %>
<%@ attribute name="id" required="false" rtexprvalue="true" type="java.lang.String" description="绑定id属性" %>

<button type="button" class="btn green" onClick="${onClick}" id="${id}"><i class="fa fa-plus"></i>&nbsp;新增</button>