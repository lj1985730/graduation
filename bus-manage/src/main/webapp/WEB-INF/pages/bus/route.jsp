<%--
  Created by IntelliJ IDEA.
  User: LiuJun
  Date: 2017/10/14
  Time: 19:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="ls" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/inc/common.inc" %>
    <script type="text/javascript" src="scripts/bus/route.js"></script>
</head>
<body class="page-boxed page-header-fixed page-footer-fixed page-sidebar-closed-hide-logo page-container-bg-solid">
<!-- BEGIN HEADER -->
<div class="page-header navbar navbar-fixed-top">
    <!-- BEGIN HEADER INNER -->
    <div class="page-header-inner container">
        <!-- BEGIN LOGO -->
        <div class="page-logo">
            <a href="javascript:">
                <img src="img/logo-default.png" alt="logo" class="logo-default"/>
            </a>
            <div class="menu-toggler sidebar-toggler">
                <!-- DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
            </div>
        </div>
        <!-- END LOGO -->
        <!-- BEGIN RESPONSIVE MENU TOGGLER -->
        <a href="javascript:" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse"></a>
        <!-- END RESPONSIVE MENU TOGGLER -->
        <!-- BEGIN PAGE TOP -->
        <div class="page-top">
            <h3 class="page-title col-sm-6" style="padding: 20px; margin: 0;">线路管理</h3>
            <!-- BEGIN TOP NAVIGATION MENU -->
            <div class="top-menu">
                <ul class="nav navbar-nav pull-right">
                    <!-- BEGIN USER LOGIN DROPDOWN -->
                    <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown style -->
                    <li class="dropdown dropdown-user">
                        <a href="javascript:" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                            <img alt="" class="img-circle" src="img/avatar3_small.jpg"/>
                            <span class="username username-hide-on-mobile">${sessionScope.get('userName')}</span>
                        </a>
                    </li>
                    <!-- END USER LOGIN DROPDOWN -->
                    <!-- BEGIN USER LOGIN DROPDOWN -->
                    <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                    <li class="dropdown dropdown-extended quick-sidebar-toggler" onclick="logout();">
                        <span class="sr-only">Toggle Quick Sidebar</span>
                        <i class="icon-logout"></i>
                    </li>
                    <!-- END USER LOGIN DROPDOWN -->
                </ul>
            </div>
            <!-- END TOP NAVIGATION MENU -->
        </div>
        <!-- END PAGE TOP -->
    </div>
</div>
<!-- END HEADER -->
<div class="clearfix"></div>
<div class="container">
    <div class="page-container">
        <!-- BEGIN SIDEBAR -->
        <div class="page-sidebar-wrapper">
            <div class="page-sidebar navbar-collapse collapse">
                <ul class="page-sidebar-menu page-sidebar-menu-hover-submenu " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
                    <li class="start">
                        <a href="javascript:toStationPage();">
                            <i class="icon-pointer"></i>
                            <span class="title">站点管理</span>
                            <span class="arrow"></span>
                        </a>
                    </li>
                    <li class="active">
                        <a href="javascript:toRoutePage();">
                            <i class="icon-graph"></i>
                            <span class="title">线路管理</span>
                            <span class="selected"></span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:toStationDistancePage();">
                            <i class="icon-cursor-move"></i>
                            <span class="title">站点距离管理</span>
                            <span class="arrow"></span>
                        </a>
                    </li>
                </ul>
                <!-- END SIDEBAR MENU -->
            </div>
        </div>
        <!-- END SIDEBAR -->
        <!-- BEGIN CONTENT -->
        <div class="page-content-wrapper">
            <div class="page-content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="portlet light">
                            <ls:title name="线路列表">
                                <ls:add id="addRoute" />
                                <ls:modify id="modifyRoute"/>
                                <ls:delete id="deleteRoute" />
                            </ls:title>
                            <div class="portlet-body">
                                <div class="table-container">
                                    <table class="table table-striped table-bordered table-hover" id="routeTable"
                                           data-search="true" data-show-refresh="true"
                                           data-show-toggle="true" data-show-columns="true"
                                           data-single-select="true" data-click-to-select="true">
                                        <thead>
                                        <tr role="row" class="heading">
                                            <th data-field="checkbox" data-checkbox="true"></th>
                                            <th data-field="id" data-formatter="indexFormatter">序号</th>
                                            <th data-field="name" data-sortable="true">名称</th>
                                            <th data-field="category.name" data-sortable="true">类型</th>
                                            <th data-field="pullInterval" data-sortable="true">发车间隔</th>
                                            <th data-field="startStation.name" data-sortable="true">始发站</th>
                                            <th data-field="endStation.name" data-sortable="true">终点站</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="portlet light">
                            <ls:title name="线路站点列表">
                                <ls:add id="linkStation" />
                                <ls:delete id="dislinkStation" />
                            </ls:title>
                            <div class="portlet-body">
                                <div class="table-container">
                                    <table class="table table-striped table-bordered table-hover" id="linkTable"
                                           data-search="true" data-show-refresh="true"
                                           data-show-toggle="true" data-show-columns="true"
                                           data-single-select="true" data-click-to-select="true">
                                        <thead>
                                            <tr role="row" class="heading">
                                                <th data-field="checkbox" data-checkbox="true"></th>
                                                <th data-field="sort" data-sortable="false">序号</th>
                                                <th data-field="station.name" data-sortable="false">站点名称</th>
                                                <th data-field="station.location" data-sortable="false">站点地址</th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<ls:modal id="editWin" modalClass="modal-lg" title="" editable="true" onSave="$.fn.submitEditForm()"><!-- 默认保存方法为lsGridSave,自定义可声明onSave事件 -->
<form id="editForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
    <input class="switch" id="id" name="id" type="hidden" />
    <div class="form-group">
        <label class="control-label col-md-2" for="name"><span class="required">* </span>线路名称</label>
        <div class="col-md-10">
            <input id="name" name="name" class="form-control" placeholder="线路名称..." />
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2" for="category"><span class="required">* </span>线路分类</label>
        <div class="col-md-10">
            <select id="category" name="categoryId" class="form-control"></select>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2" for="pullInterval">发车间隔</label>
        <div class="col-md-10">
            <input id="pullInterval" name="pullInterval" class="form-control" placeholder="发车间隔..." />
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2" for="ticketPrice">票价(元)</label>
        <div class="col-md-10">
            <input id="ticketPrice" name="ticketPrice" class="form-control" placeholder="票价(元)..." />
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2" for="startStation"><span class="required">* </span>始发站</label>
        <div class="col-md-10">
            <select id="startStation" name="startStationId" class="form-control"></select>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2" for="startStationFirstTime">始发站首车时间</label>
        <div class="col-md-10">
            <input id="startStationFirstTime" name="startStationFirstTimeStr" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2" for="startStationLastTime">始发站末车时间</label>
        <div class="col-md-10">
            <input id="startStationLastTime" name="startStationLastTimeStr" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2" for="endStation"><span class="required">* </span>终点站</label>
        <div class="col-md-10">
            <select id="endStation" name="endStationId" class="form-control"></select>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2" for="endStationFirstTime">终点站首车时间</label>
        <div class="col-md-10">
            <input id="endStationFirstTime" name="endStationFirstTimeStr" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2" for="endStationLastTime">终点站末车时间</label>
        <div class="col-md-10">
            <input id="endStationLastTime" name="endStationLastTimeStr" class="form-control" />
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">备注</label>
        <div class="col-md-10">
            <textarea id="remark" name="remark" class="form-control" rows="3" placeholder="备注..."></textarea>
        </div>
    </div>
</form>
</ls:modal>

<ls:modal id="linkWin" modalClass="modal-lg" title="线路绑定站点" editable="true" onSave="$.fn.submitLinkForm()"><!-- 默认保存方法为lsGridSave,自定义可声明onSave事件 -->
<form id="linkForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
    <div class="form-group">
        <label class="control-label col-md-2" for="name">线路名称</label>
        <div class="col-md-10">
            <p id="link_route"></p>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2" for="link_station"><span class="required">* </span>站点</label>
        <div class="col-md-10">
            <select id="link_station" class="form-control"></select>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">序号</label>
        <div class="col-md-10">
            <input id="link_sort" type="number" name="sort" class="form-control" placeholder="序号" />
        </div>
    </div>
</form>
</ls:modal>
</body>
</html>
