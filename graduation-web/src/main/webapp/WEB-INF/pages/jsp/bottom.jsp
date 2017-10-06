<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false"%>
<html>
	
	<jsp:include page="aboutUs.jsp"></jsp:include>
	<link rel="stylesheet" href="plugins/layui/css/layui.css">
	<script src="plugins/layui/layui.js"></script>
	<div class="page-footer">
		<div class="page-footer-inner">
			<a href="system/manage/apiTest" target="_blank">apiTest</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="portal/manage/portalApiTest" target="_blank">门户网站apiTest</a>&nbsp;&nbsp;&nbsp;&nbsp;
			关于我们：<a href="http://www.maritech.cn" target="_blank">大连陆海科技股份有限公司</a>&nbsp;&nbsp;&nbsp;&nbsp; 
			<a href="#" onclick="javascript:showAboutUs();return false;" target="_blank">售后服务</a>
		</div>
		<div class="scroll-to-top">
			<i class="icon-arrow-up"></i>
		</div>
	</div>
	<script type="text/javascript" src="templates/js/chat.js"></script>
</html>
