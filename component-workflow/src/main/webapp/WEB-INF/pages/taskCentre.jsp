<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="ls" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>待办任务</title>
	<%-- 引入流程历史查询组件 --%>
	<%@ include file="/resources/script/act/act.inc" %>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/resources/js/workflow/workflow-historic-modal.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="tab-content">
			<div class="tab-pane fade in active" id="homePanel">
				<button id="showWorkflowViewBtn" style="display: none;"></button>
				<ul id="taskListTabs" class="nav nav-tabs">
					<li class="active"><a href="#todoTab" data-toggle="tab"><span class="glyphicon glyphicon-edit" style="top: 2px;"></span>&nbsp;待办任务</a></li>
					<li><a href="#historicTab" data-toggle="tab"><span class="glyphicon glyphicon-ok-sign" style="top: 2px;"></span>&nbsp;已办任务</a></li>
				</ul>
				<div class="tab-content" id="taskListTabContent">
					<div class="tab-pane fade in active" id="todoTab">
						<div class="panel panel-default" style="border-top-color: #FFF; border-top-left-radius: 0; border-top-right-radius: 0;">
							<%--<div class="panel-heading">
								<h3 class="panel-title"><span class="glyphicon glyphicon-edit"></span>&nbsp;待办任务列表</h3>
							</div>--%>
							<div class="panel-body" style="padding-top: 0;">
								<div id="todoListTableToolbar">
									<div class="row">
										<label class="control-label" style="float:left; width: 56px; margin-left: 15px; margin-bottom: 0;">流程编号</label>
										<div class="col-sm-2">
											<input class="form-control" id="todoListSearch_procInsId" placeholder="流程编号" />
										</div>
										<label class="control-label" style="float:left; width: 56px; margin-left: 15px; margin-bottom: 0;">任务日期</label>
										<div class="col-sm-2">
											<input class="form-control" id="todoListSearch_startDate" placeholder="起始日期" />
										</div>
										<label class="control-label" style="float:left; width: 14px; margin-left: -7px; margin-right: -7px; margin-bottom: 0;">至</label>
										<div class="col-sm-2">
											<input class="form-control" id="todoListSearch_endDate" placeholder="截至日期" />
										</div>
										<div class="col-sm-2"></div>
									</div>
								</div>
								<div class="table-container">
									<table id="todoListTable" class="table table-striped table-hover table-condensed"
										   data-search="false" data-show-refresh="false" data-editable="false"
										   data-single-select="true" data-click-to-select="true"
										   data-toolbar="#todoListTableToolbar">
										<thead>
											<tr role="row" class="heading">
												<th data-field="checkbox" data-checkbox="true" data-align="center" data-edit="false"></th>
												<th data-field="index" data-sortable="false" data-align="center" data-edit="false" data-formatter="indexFormatter">序号</th>
												<th data-field="processInstanceId" data-sortable="false" data-align="center" data-edit="false">流程编号</th>
												<th data-field="processState" data-sortable="false" data-align="center" data-edit="false" data-formatter="workflowState">流程状态</th>
												<%--<th data-field="processDefinitionName" data-sortable="false" data-align="center" data-edit="false">流程名称</th>--%>
												<th data-field="taskName" data-sortable="false" data-align="center" data-edit="false">当前环节</th>
												<th data-field="title" data-sortable="false" data-align="center" data-edit="false" data-formatter="$.fn.showBusinessViewPanelFormatter">主题</th>
												<th data-field="applicantName" data-sortable="false" data-align="center" data-edit="false">申请人</th>
												<th data-field="taskCreateDate" data-sortable="false" data-align="center" data-edit="false">任务开始时间</th>
												<th data-field="taskId" data-sortable="false" data-align="center" data-edit="false" data-formatter="$.fn.todoOperateFormatter">操作</th>
											</tr>
										</thead>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div id="historicTab" class="tab-pane fade">
						<div class="panel panel-default" style="border-top-color: #FFF; border-top-left-radius: 0; border-top-right-radius: 0;">
							<%--<div class="panel-heading">
								<h3 class="panel-title"><span class="glyphicon glyphicon-ok-sign"></span>&nbsp;已办任务列表</h3>
							</div>--%>
							<div class="panel-body" style="padding-top: 0;">
								<div id="historicListTableToolbar">
									<div class="row">
										<label class="control-label" style="float:left; width: 56px; margin-left: 15px; margin-bottom: 0;">流程编号</label>
										<div class="col-sm-2">
											<input class="form-control" id="historicListSearch_procInsId" placeholder="流程编号" />
										</div>
										<label class="control-label" style="float:left; width: 56px; margin-left: 15px; margin-bottom: 0;">任务日期</label>
										<div class="col-sm-2">
											<input class="form-control" id="historicListSearch_startDate" placeholder="起始日期" />
										</div>
										<label class="control-label" style="float:left; width: 14px; margin-left: -7px; margin-right: -7px; margin-bottom: 0;">至</label>
										<div class="col-sm-2">
											<input class="form-control" id="historicListSearch_endDate" placeholder="截至日期" />
										</div>
										<div class="col-sm-2"></div>
									</div>
								</div>
								<div class="table-container">
									<table id="historicListTable" class="table table-striped table-hover table-condensed"
										   data-search="false" data-show-refresh="false" data-editable="false"
										   data-show-toggle="false" data-show-columns="false"
										   data-single-select="true" data-click-to-select="true"
										   data-toolbar="#historicListTableToolbar">
										<thead>
											<tr role="row" class="heading">
												<th data-field="checkbox" data-checkbox="true" data-align="center" data-edit="false"></th>
												<th data-field="index" data-sortable="false" data-align="center" data-edit="false" data-formatter="indexFormatter">序号</th>
												<th data-field="processInstanceId" data-sortable="false" data-align="center" data-edit="false">流程编号</th>
												<%--<th data-field="processDefinitionName" data-sortable="false" data-align="center" data-edit="false">流程名称</th>--%>
												<th data-field="taskName" data-sortable="false" data-align="center" data-edit="false">当前环节</th>
												<th data-field="title" data-sortable="false" data-align="center" data-edit="false" data-formatter="$.fn.showBusinessViewPanelFormatter">主题</th>
												<th data-field="applicantName" data-sortable="false" data-align="center" data-edit="false">申请人</th>
												<th data-field="taskEndDate" data-sortable="false" data-align="center" data-edit="false">处理时间</th>
												<%--<th data-field="taskId" data-sortable="false" data-align="center" data-edit="false" data-formatter="$.fn.historicOperateFormatter">操作</th>--%>
											</tr>
										</thead>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<%-- 业务数据查看面板 --%>
			<div class="tab-pane" id="businessViewPanel">
				<h4 class="page-title col-sm-12">
					<i class="glyphicon glyphicon-comment"></i>
					<span style="font-weight:bold;">流程数据查看</span>
				</h4>
				<div class="row">
					<div class="col-md-12">
						<div class="pull-right">
							<button type="button" class="btn btn-default" onclick="$.fn.switchPanel('home')">
								<span class="glyphicon glyphicon-arrow-left"></span>&nbsp;返回
							</button>
							<yonyou:workflow-historic-view id="businessViewPanel_historicView" cssClass="btn btn-warning">流程查看</yonyou:workflow-historic-view>
						</div>
					</div>
				</div>
				<div class="row formContainer"></div>
			</div>

			<%-- 处理任务面板 --%>
			<div class="tab-pane" id="disposePanel">
				<h4 class="page-title col-sm-12">
					<i class="glyphicon glyphicon-comment"></i>
					<span style="font-weight:bold;">流程处理</span>
				</h4>
				<div class="row">
					<div class="col-md-12">
						<div class="pull-right">
							<button type="button" class="btn btn-default" onclick="$.fn.switchPanel('home')">
								<span class="glyphicon glyphicon-arrow-left"></span>&nbsp;返回
							</button>
							<button id="disposePanel_approvalBtn" type="button" class="btn btn-success">
								<span class="glyphicon glyphicon-save"></span>&nbsp;保存
							</button>
							<yonyou:workflow-historic-view id="disposePanel_historicView" cssClass="btn btn-warning">流程查看</yonyou:workflow-historic-view>
						</div>
					</div>
				</div>
				<div class="panel panel-success" style="margin-top: 15px;">
					<div class="panel-heading">
						<h3 class="panel-title"><span class="glyphicon glyphicon-edit"></span>&nbsp;审批操作</h3>
					</div>
					<div class="panel-body">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-sm-2">审批结果</label>
								<div class="col-sm-8">
									<label class="radio-inline" style="margin-right: 50px;">
										<input type="radio" name="approvalResult" checked value="1" /><i class="glyphicon glyphicon-ok"></i> 同意
									</label>
									<label class="radio-inline">
										<input type="radio" name="approvalResult" value="0" /><i class="glyphicon glyphicon-remove"></i> 驳回
									</label>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2" for="comment">审批意见</label>
								<div class="col-sm-8">
									<textarea class="form-control" id="comment" rows="3" cols="140"></textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;待审内容</h3>
					</div>
					<div class="panel-body formContainer">
					</div>
				</div>
			</div>

			<%-- 再次提交窗体 --%>
			<div class="tab-pane" id="retryPanel">
				<h4 class="page-title col-sm-12">
					<i class="glyphicon glyphicon-comment"></i>
					<span style="font-weight:bold;">再次提交流程</span>
				</h4>
				<div class="row">
					<div class="col-md-12">
						<div class="pull-right">
							<button type="button" class="btn btn-default" onclick="$.fn.switchPanel('home')">
								<span class="glyphicon glyphicon-arrow-left"></span>&nbsp;返回
							</button>
							<button type="button" class="btn btn-success" id="retryPanel_saveBtn">
								<span class="glyphicon glyphicon-save"></span>&nbsp;保存
							</button>
							<button type="button" class="btn btn-success" id="retryPanel_submitBtn">
								<span class="glyphicon glyphicon-share-alt"></span>&nbsp;保存并提交
							</button>
							<yonyou:workflow-historic-view id="retryPanel_historicView" cssClass="btn btn-warning">流程查看</yonyou:workflow-historic-view>
						</div>
					</div>
				</div>
				<div class="row formContainer"></div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/workflow/taskCentre.js"></script>
</body>

</html>
