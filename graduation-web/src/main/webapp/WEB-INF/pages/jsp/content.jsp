<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
	<head>
		<title>航运信息管理平台</title>
		<script type="text/javascript">
			var pathArray = top.window.location.href.split('/');
			var protocol = pathArray[0];
			var host = pathArray[2];
			var context = pathArray[3];
			var rootUrl = "/" + context;
			var commonComboData = [];
		</script>
		<base href="<%=basePath%>" />
		<meta charset="utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		<meta name="description" content="" />
		<meta name="author" content="landsea" />
		<link rel="shortcut icon" href="images/favicon.ico" />
		
		<link type="text/css" rel="stylesheet" href="css/font-awesome.min.css" />
		<link type="text/css" rel="stylesheet" href="css/simple-line-icons.min.css" />
		<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
		<link type="text/css" rel="stylesheet" href="css/layout.css" />
		<link type="text/css" rel="stylesheet" href="css/plugins.css" />
		<link type="text/css" rel="stylesheet" href="css/themes/darkblue.css" id="style_color" />
		<link type="text/css" rel="stylesheet" href="plugins/metronic/components.css" id="style_components" />
		<link type="text/css" rel="stylesheet" href="plugins/bootstrap-table/bootstrap-table.css" />
		<link type="text/css" rel="stylesheet" href="plugins/bootstrap-table/bootstrap-table-group-by.css" />
 		<!-- <link type="text/css" rel="stylesheet" href="plugins/bootstrap-select/bootstrap-select.css" /> -->
		<link type="text/css" rel="stylesheet" href="plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.css"/>
		<link type="text/css" rel="stylesheet" href="plugins/jquery.validationEngine/jquery.validationEngine.css" />
		
		<script type="text/javascript" src="scripts/jquery-1.11.0.min.js"></script>
		<script type="text/javascript" src="plugins/jquery.validationEngine/jquery.validationEngine.js"></script>
		<script type="text/javascript" src="plugins/jquery.validationEngine/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="scripts/bootstrap/bootstrap.min.js"></script>
		<script type="text/javascript" src="plugins/metronic/metronic.js"></script>
		<script type="text/javascript" src="scripts/layout.js"></script>
		<script type="text/javascript" src="plugins/bootstrap-table/bootstrap-table.js"></script>
		<script type="text/javascript" src="plugins/bootstrap-table/bootstrap-table-zh-CN.js"></script>			
        <script type="text/javascript" src="plugins/bootstrap-table/bootstrap-table-group-by.js"></script>
<!-- 		<script type="text/javascript" src="plugins/bootstrap-select/bootstrap-select.js"></script> -->
<!-- 		<script type="text/javascript" src="plugins/bootstrap-select/i18n/defaults-zh_CN.js"></script> -->
		<script type="text/javascript" src="plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
		<script type="text/javascript" src="plugins/bootstrap-dropdown/bootstrap-hover-dropdown.min.js"></script>
		<script type="text/javascript" src="plugins/jquery-slimscroll/jquery.slimscroll.min.js"></script>
		<script type="text/javascript" src="plugins/jquery-formatDate/jquery.formatDate.js"></script>
		<script type="text/javascript" src="plugins/jquery-fileDownload/jquery.fileDownload.js"></script>
		<script type="text/javascript" src="plugins/jquery-i18n/jquery.i18n.properties-min-1.0.9.js"></script>
		<script type="text/javascript" src="plugins/bootstrap-paginator/bootstrap-paginator.js"></script>
		<!-- <script type="text/javascript" src="plugins/flot/jquery.flot.min.js"></script>
		<script type="text/javascript" src="plugins/flot/jquery.flot.resize.min.js"></script>
		<script type="text/javascript" src="plugins/flot/jquery.flot.categories.min.js"></script> -->

		<!-- 统一封装消息提示 -->
		<script type="text/javascript" src="scripts/sys-message.js"></script>

		<script type="text/javascript" src="plugins/bootbox.min.js"></script>
		<script type="text/javascript" src="scripts/jquery.cookie.js"></script>
<!-- 		<link rel="stylesheet" href="plugins/showLoading/showLoading.css" />
		<script type="text/javascript" src="plugins/showLoading/jquery.showLoading.min.js"></script>   -->
		<script type="text/javascript" src="scripts/core.js"></script>
		<script type="text/javascript" src="scripts/math.uuid.js"></script>
		<script type="text/javascript" src="scripts/grid-format.js"></script>
		
		<!-- 首页提醒业务-工作流 -->
		<script type="text/javascript" src="scripts/websocket.js"></script>

		<script type="text/javascript">
			/*** 处理通用信息 ***/
			var SYS_INFO = {};
			<%--SYS_INFO.VERSION = '<%=request.getAttribute("const_system_version")%>';			//系统版本号--%>
			<%--SYS_INFO.COM_NAME = '<%=request.getAttribute("const_com_name")%>';      		//企业名称--%>
			<%--SYS_INFO.SHIP_NAME = '<%=request.getAttribute("const_ship_name")%>';			//船舶名称--%>
			<%--SYS_INFO.PERSON_NAME = '<%=request.getAttribute("const_person_name")%>';		//人员名称--%>
			<%--SYS_INFO.LAND_OR_SHIP = '<%=request.getAttribute("const_land_or_ship")%>';		//船端或岸端  "1" 岸端；"2" 船端；--%>
			SYS_INFO.VERSION = $.cookie('const_system_version');			//系统版本号
			SYS_INFO.COM_NAME = $.cookie('const_com_name');     			//企业名称
			SYS_INFO.SHIP_NAME = $.cookie('const_ship_name');     			//船舶名称
			SYS_INFO.SHIP_CODE = $.cookie('const_ship_code');     			//船舶名称
			SYS_INFO.PERSON_NAME = $.cookie('const_person_name');			//人员名称
			SYS_INFO.LAND_OR_SHIP = $.cookie('const_land_or_ship');   		//船端或岸端  "1" 岸端；"2" 船端；
			SYS_INFO.BACK_LOGO = $.cookie('const_back_logo');   		//管理系统logo;
			SYS_INFO.BACK_SITE_NAME = decodeURI($.cookie('const_back_site_name')); //管理系统名称
		</script>
		<!-- 装饰头文件 -->
		<sitemesh:write property="head" />
	</head>
	<body class="page-header-fixed page-quick-sidebar-over-content page-sidebar-fixed page-footer-fixed page-container-bg-solid">
		<!-- 页头 -->
		<jsp:include page="top.jsp" />

		<div class="clearfix"></div>
		<div class="page-container">

			<!-- 左侧菜单 -->
    		<jsp:include page="left.jsp" />

    		<!-- 主显示内容 -->
			<div class="page-content-wrapper">
				<div class="page-content">
					<sitemesh:write property="body" />
				</div>
			</div>

		</div>

		<!-- 页脚 -->
		<jsp:include page="bottom.jsp" />
		
	</body>
</html>