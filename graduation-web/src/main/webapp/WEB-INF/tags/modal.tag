<!-- 自定义模态弹窗-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless" %>
<%@ taglib prefix="ls" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 参数 -->
<%@ attribute name="id" required="true" rtexprvalue="false" type="java.lang.String" description="唯一标识" %>
<%@ attribute name="title" required="true" rtexprvalue="false" type="java.lang.String" description="窗口标题" %>
<%@ attribute name="editble" required="false" rtexprvalue="false" type="java.lang.Boolean" description="是否可保存" %>
<%@ attribute name="onSave" required="false" rtexprvalue="true" type="java.lang.String" description="保存触发" %>
<%@ attribute name="modalClass" required="false" rtexprvalue="false" type="java.lang.String" description="模态窗大小" %>
<!-- modal-lg(大) -->
<div id="${id}" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
	<div class="${modalClass} modal-dialog">
		<div class="modal-content">
			<div class="modal-header bg-primary">
				<h4 class="modal-title">
					<i class="icon-pencil"></i>
					<span id="${id}Title" style="font-weight:bold;">${title}</span>
					<!-- <button type="button" class="close icon-white" data-dismiss="modal" aria-hidden="true" /> -->
					<li class="fa fa-remove" style="float: right; color: rgb(191, 202, 209); transform: scale(1);" 
					onmouseover="this.style.cssText='float: right; color: rgb(191, 202, 209); transform: scale(2);'" 
					onmouseout="this.style.cssText='float: right; color: rgb(191, 202, 209); transform: scale(1);'" data-dismiss="modal"></li>	
                </h4>
			</div>
			<div class="modal-body">
				<!-- 标签体 -->
				<jsp:doBody />
			</div>

			<div class="modal-footer bg-info">
				<c:if test="${not empty editble && editble}">
					<c:if test="${not empty onSave}">
						<ls:save onClick="${onSave}" />
					</c:if>
					<c:if test="${empty onSave}">
						<ls:save />
					</c:if>
				</c:if>
				<button type="button" class="btn red" data-dismiss="modal"><i class="fa fa-remove"></i>&nbsp;关闭</button>
			</div>
        </div>
    </div>
</div>