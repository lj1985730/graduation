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
                    <h3 class="page-title col-sm-6" style="padding: 20px; margin: 0;">线路查询</h3>
                    <!-- BEGIN TOP NAVIGATION MENU -->
                    <div class="top-menu">
                        <ul class="nav navbar-nav pull-right">
                            <!-- BEGIN USER LOGIN DROPDOWN -->
                            <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown style -->
                            <li class="dropdown dropdown-user">
                                <a href="javascript:" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                                    <img alt="" class="img-circle" src="img/avatar3_small.jpg"/>
                                    <span class="username username-hide-on-mobile">Nick </span>
                                </a>
                            </li>
                            <!-- END USER LOGIN DROPDOWN -->
                            <!-- BEGIN USER LOGIN DROPDOWN -->
                            <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                            <li class="dropdown dropdown-extended quick-sidebar-toggler">
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
                            <li class="start active ">
                                <a href="javascript:">
                                    <i class="icon-pointer"></i>
                                    <span class="title">站点管理</span>
                                    <span class="selected"></span>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:">
                                    <i class="icon-link"></i>
                                    <span class="title">线路管理</span>
                                    <span class="arrow "></span>
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
                                <div class="portlet box blue-steel">
                                    <ls:title name="站点列表">
                                        <ls:add id="addStation" />
                                        <ls:modify id="modifyStation"/>
                                        <ls:delete id="deleteStation" />
                                        <ls:search win="searchWin" />
                                    </ls:title>
                                    <!-- 封装高级查询窗体，需指明对应table -->
                                    <ls:searchWin id="searchWin" table="stationTable">
                                        <div class="theme-option col-md-6">
                                            <span class="col-md-12">站点名称</span>
                                            <input class="form-control col-md-12" name="portCode" placeholder="港口编号" />
                                        </div>
                                        <div class="theme-option col-md-6">
                                            <span class="col-md-12">中文名称</span>
                                            <input class="form-control col-md-12" name="cname" placeholder="中文名称" />
                                        </div>
                                        <div class="theme-option col-md-6">
                                            <span class="col-md-12">英文名称</span>
                                            <input class="form-control col-md-12" name="ename" placeholder="英文名称" />
                                        </div>
                                        <div class="theme-option col-md-6">
                                            <span class="col-md-4">名称缩写</span>
                                            <input class="form-control col-md-12" name="shortName" placeholder="名称缩写" />
                                        </div>
                                        <div class="theme-option col-md-6">
                                            <span class="col-md-4">所属国</span>
                                            <!-- <select id="search_country" name="countryId" class="form-control" title="所属国"></select> -->
                                            <input class="form-control col-md-12" name="countryName" placeholder="所属国" />
                                        </div>
                                        <div class="theme-option col-md-6">
                                            <span class="col-md-4">所在省</span>
                                            <input class="form-control col-md-12" name="province" placeholder="所在省" />
                                        </div>
                                    </ls:searchWin>
                                    <div class="portlet-body">
                                        <div class="table-container">
                                            <table class="table table-striped table-bordered table-hover" id="stationTable"
                                                   data-search="true" data-show-refresh="true"
                                                   data-show-toggle="true" data-show-columns="true"
                                                   data-single-select="true" data-click-to-select="true">
                                                <thead>
                                                    <tr role="row" class="heading">
                                                        <th data-field="checkbox" data-checkbox="true"></th>
                                                        <%--<th data-field="index" data-formatter="indexFormatter">序号</th>--%>
                                                        <th data-field="name" data-sortable="true">站点名称</th>
                                                        <th data-field="code" data-sortable="true">站点编号</th>
                                                        <th data-field="location">站点地址</th>
                                                        <th data-field="remark">备注</th>
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

        <ls:modal id="editWin" title="" editable="true" onSave="$.fn.submitEditForm()"><!-- 默认保存方法为lsGridSave,自定义可声明onSave事件 -->
            <form id="editForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
                <input class="switch" id="id" name="id" type="hidden" />
                <div class="row">
                    <div class="form-group">
                        <label class="control-label col-md-2">港口编号</label>
                        <div class="col-md-10">
                            <input id="name" name="name" class="form-control validate[maxSize[100]]" placeholder="名称..." />
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="form-group">
                            <label class="control-label col-md-2"><span
                                    class="required">* </span>中文名称</label>
                            <div class="col-md-10">
                                <input id="cname" name="cname" type="text" class="form-control validate[required, maxSize[100]]" placeholder="中文名称..." />
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="form-group">
                            <label class="control-label col-md-2">英文名称</label>
                            <div class="col-md-10">
                                <input id="ename" name="ename" type="text" class="form-control validate[maxSize[200]]" placeholder="英文名称..." />
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="form-group">
                            <label class="control-label col-md-2">名称缩写</label>
                            <div class="col-md-10">
                                <input id="shortName" name="shortName" type="text" class="form-control validate[maxSize[200]]" placeholder="名称缩写..." />
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="form-group">
                            <label class="control-label col-md-2">所属省</label>
                            <div class="col-md-10">
                                <input id="province" name="province" type="text" class="form-control validate[maxSize[200]]" placeholder="所属省..." />
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </ls:modal>
    </body>
</html>
