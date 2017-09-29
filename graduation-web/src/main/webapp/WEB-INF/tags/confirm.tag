<!-- 自定义确认按钮-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>

<!-- 参数 -->
<%@ attribute name="onClick" required="true" rtexprvalue="true" type="java.lang.String" description="点击事件" %>

<button type="button" class="btn green" onClick="${onClick}"><i class="fa fa-check"></i>&nbsp;确定</button>