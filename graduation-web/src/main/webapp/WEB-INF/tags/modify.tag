<!-- 自定义修改-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>

<!-- 参数 -->
<%@ attribute name="onClick" required="true" rtexprvalue="true" type="java.lang.String" description="点击事件" %>
<%@ attribute name="id" required="false" rtexprvalue="true" type="java.lang.String" description="绑定id属性" %>

<button type="button" class="btn blue" id="${id}" onClick="${onClick}"><i class="fa fa-edit"></i>&nbsp;修改</button> 