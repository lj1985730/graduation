<!-- 自定义物料选择弹窗 -->
<!-- 使用方式 -->
<%--
    1.页面头部引入自定义标签声明：
    <%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
    2.在需要位置放置自定义按钮，id必须设置：
    <yonyou:workflow-historic-view id="workflowView" cssClass="btn btn-default table_btn">流程查看</yonyou:workflow-historic-view>
    3.使用脚本赋值流程实例ID，注意jQuery选择器的ID为按钮id：
    $('#workflowView').workflowHistoricView('setProcessInstanceId', XXX);
    4.点击按钮即可弹窗。
--%>
<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless" %>

<!-- 参数 -->
<%@ attribute name="id" required="true" rtexprvalue="false" type="java.lang.String" description="唯一标识" %>
<%@ attribute name="cssClass" required="false" rtexprvalue="false" type="java.lang.String" description="样式" %>

<%@ include file="/resources/script/act/act.inc" %>

<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/workflow/workflow-historic-modal.js"></script>

<button id="${id}" type="button" class="${cssClass}">
    <span class="glyphicon glyphicon-search"></span>&nbsp;<jsp:doBody />
</button>

<script language='javascript'>
    var DiagramGenerator = {};
    var progressBar;
    $(document).ready(function () {
        $('#${id}').workflowHistoricView({});
    });
</script>