<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ls" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="lsj" uri="/lsTags" %>
<html>
	<head>
		<style type="text/css">
			#commonMenu .dashboard-stat .moduleName {
				font-size: 16px;
				white-space: nowrap;
				padding-top: 10px;
				padding-bottom: 10px;
			    line-height: 14px;
				display: block;
				width: 100%;
			    color: #FFFFFF;
				text-align: center;
			}
			#commonMenu .dashboard-stat .fatherName {
			    font-size: 14px;
				white-space: nowrap;
				padding: 10px 0 0 25px;
				text-align: right;
			    font-weight: 300;
				float: left;
		        color: #FFFFFF;
			}
		</style>
        <link type="text/css" rel="stylesheet" href="css/tree/style.min.css" />
		<script type="text/javascript" src="scripts/jstree.min.js"></script>
		<script type="text/javascript" src="templates/js/index.js"></script>
		<script type="text/javascript" src="plugins/Highcharts-4.2.5/js/highcharts.js"></script>
		<script type="text/javascript" src="plugins/Highcharts-4.2.5/js/highcharts-3d.js"></script>
		
		<script type="text/javascript" src="plugins/Highcharts-4.2.5/js/themes/grid-light.js"></script>

		<script type="text/javascript" src="templates/js/websocket/index_monthAnalysis.js"></script>
		
		<script type="text/javascript" src="templates/js/websocket/index_demoAnalysis.js"></script>
		
		<script type="text/javascript" src="templates/js/websocket/index_safetyAlarm.js"></script>

		<script type="text/javascript" src="templates/js/websocket/index_wfInstance.js"></script>
		
		<script type="text/javascript" src="templates/js/websocket/index_certificateAlarm.js"></script>
		
		<script type="text/javascript" src="templates/js/websocket/index_birthdayRemind.js"></script>

		<script type="text/javascript" src="templates/js/websocket/index_reportList.js"></script>

		<jsp:include page="/WEB-INF/templates/jsp/wfengine/engine/wfEngineUtil.jsp"></jsp:include>
	</head>
	<body>
		<div class="row">
			<div class="portlet light" style="margin-top: 0; margin-left: 15px; margin-right: 15px; padding-top: 0; padding-bottom: 0;">
				<div class="portlet-title" style="margin-bottom: 0;">
					<div class="caption">
						<i class="icon-paper-clip font-green-sharp"></i>
						<span class="caption-subject font-green-sharp bold uppercase">快速通道</span>
					</div>
					<div class="actions">
						<ls:add onClick="openQuickMenuWin();" />
					</div>
				</div>
				<div class="portlet-body">
					<div class="row">
						<!-- 快速通道 -->
						<div id="commonMenu"></div>
					</div>
				</div>
			</div>
			<ls:modal id="quickMenuWin" title="选择快速访问菜单（最多5个）" editble="true" onSave="saveQuickMenu();">
				 <ul id="quickMenuTree"></ul>
			</ls:modal>
		</div>
		<div class="clearfix"></div>
		<div class="row">
			<!-- <div id="welcomeAlarm" class="col-sm-12 col-md-6 col-lg-6 col-xl-6 col-xxl-6 col-xxxl-6 col-xxxxl-6">

			</div> -->
			<div class="col-sm-12 col-md-6 col-lg-6 col-xl-6 col-xxl-4">
				<div class="portlet light">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-list"></i>
							<span class="caption-subject font-green-sharp bold uppercase">待办提醒</span>
							<a class="caption-helper" href="wfengine/instance/wfMyTodoInstance/index">详细信息...</a>
						</div>
						<div class="actions"></div>
					</div>
					<div class="portlet-body">
						<div id="reminder" style="height: 250px;">
							
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-12 col-md-6 col-lg-6 col-xl-6 col-xxl-4">
				<div class="portlet light">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-bar-chart-o"></i>
							<span class="caption-subject font-green-sharp bold uppercase">维护保养</span>
							<a class="caption-helper" href="ship/maintenance/shipMaintenanceSchemeMonth/index">完工情况...</a>
						</div>
						<div class="actions"></div>
					</div>
					<div class="portlet-body">
						<div class="col-md-12" style="position: relative; height:30px; margin-bottom: 5px;">
 						    <div class=" col-md-6 col-sm-6 col-xs-6" style="line-height:30px; ">
							     <span class="col-md-4 col-sm-4 col-xs-4" style="text-align: right; padding-right: 0px; padding-left: 0px;">船舶</span>
								<div class="col-md-8 col-sm-8 col-xs-8">
									<lsj:shipSelect id="shipOnSearch" name="shipId" topOption=""/>
								</div>
							</div>
							<div class=" col-md-6 col-sm-6 col-xs-6" style="line-height:30px; ">
								<span class="col-md-4 col-sm-4 col-xs-4" style="text-align: right; padding-right: 0px; padding-left: 0px;">月份</span>
								<div class="col-md-8 col-sm-8 col-xs-8">
									<select id="MonthOnSearch" name="month" class="form-control">
										<option value="01">一月</option>
										<option value="02">二月</option>
										<option value="03">三月</option>
										<option value="04">四月</option>
										<option value="05">五月</option>
										<option value="06">六月</option>
										<option value="07">七月</option>
										<option value="08">八月</option>
										<option value="09">九月</option>
										<option value="10">十月</option>
										<option value="11">十一月</option>
										<option value="12">十二月</option>
									</select>
								</div>
							</div>
						</div>
						<div id="pieContainer" style="height: 250px;"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-12 col-md-6 col-lg-6 col-xl-6 col-xxl-4">
				<div class="portlet light">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-bar-chart-o"></i>
							<span class="caption-subject font-green-sharp bold uppercase">船舶修理</span>
							<a class="caption-helper" href="ship/repair/shipRepairScheduling/index">年度统计...</a>
						</div>
						<div class="actions"></div>
					</div>
					<div class="portlet-body">
						<div id="fuelMonth" style="height: 250px;"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-12 col-md-6 col-lg-6 col-xl-6 col-xxl-4">
				<div class="portlet light">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-line-chart-o"></i>
							<span class="caption-subject font-green-sharp bold uppercase">安全隐患</span>
							<a class="caption-helper" href="ship/safety/shipSafetyRectify/index">整改报警...</a>
						</div>
						<div class="actions"></div>
					</div>
					<div class="portlet-body">
						<div id="safetyRectifyAlarm" style="height: 250px;">
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-12 col-md-6 col-lg-6 col-xl-6 col-xxl-4">
				<div class="portlet light">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-list"></i>
							<span class="caption-subject font-green-sharp bold uppercase">船舶证书</span>
							<a class="caption-helper" href="ship/certificate/shipCertInspection/index">详细信息...</a>
						</div>
						<div class="actions"></div>
					</div>
					<div class="portlet-body">
						<div id="certificateAlarm" style="height: 250px;">
							
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-12 col-md-6 col-lg-6 col-xl-6 col-xxl-4">
				<div class="portlet light">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-list"></i>
							<span class="caption-subject font-green-sharp bold uppercase">报表审批提醒</span>
							<a class="caption-helper" href="ship/report/ecsb/shipReportList/index">详细信息...</a>
						</div>
						<div class="actions"></div>
					</div>
					<div class="portlet-body">
						<div id="reportList" style="height: 250px;">
							
						</div>
					</div>
				</div>
			</div>
			<!-- <div class="col-sm-12 col-md-6 col-lg-6 col-xl-6 col-xxl-4">
				<div class="portlet light">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-list"></i>
							<span class="caption-subject font-green-sharp bold uppercase">生日提醒</span>
							<a class="caption-helper" href="system/authorization/sysPerson/personBirthday/index">详细信息...</a>
						</div>
						<div class="actions"></div>
					</div>
					<div class="portlet-body">
						<div id="birthdayRemider" style="height: 250px;">
							
						</div>
					</div>
				</div>
			</div> -->
			
		</div>
	</body>
</html>