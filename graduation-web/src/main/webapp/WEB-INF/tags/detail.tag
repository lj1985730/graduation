<!-- 自定义新增按钮-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>

<!-- 参数 -->
<%@ attribute name="onClick" required="true" rtexprvalue="true" type="java.lang.String" description="点击事件" %>

<button type="button" class="btn blue" onClick="${onClick}"><i class="icon-info icon-white"></i>&nbsp;详细信息</button>