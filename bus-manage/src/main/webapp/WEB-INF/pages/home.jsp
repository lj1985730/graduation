<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/WEB-INF/inc/common.inc" %>
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
                                    <i class="icon-link"></i>
                                    <span class="title">线路查询</span>
                                    <span class="selected"></span>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:">
                                    <i class="icon-pointer"></i>
                                    <span class="title">站点查询</span>
                                    <span class="arrow "></span>
                                </a>
                            </li>
                            <li class="last">
                                <a href="javascript:">
                                    <i class="icon-paper-plane"></i>
                                    <span class="title">公交换乘查询</span>
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
                        <!-- BEGIN PAGE HEADER-->
                        <h3 class="page-title">线路查询</h3>
                        <div class="page-bar">
                            <div class="page-toolbar">
                                <div class="chat-form">
                                    <div class="input-cont">
                                        <input class="form-control" type="text" placeholder="搜索公交线路"/>
                                    </div>
                                    <div class="btn-cont">
									    <span class="arrow"></span>
                                        <a href="" class="btn blue icn-only">
                                            <i class="fa fa-check icon-white"></i>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- END PAGE HEADER-->
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN EXAMPLE TABLE PORTLET-->
                                <div class="portlet box grey-cascade">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="fa fa-link"></i>站点列表
                                        </div>
                                    </div>
                                    <div class="portlet-body">
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
                                <!-- END EXAMPLE TABLE PORTLET-->
                            </div>
                        </div>
                        <%--<!-- END PAGE HEADER-->
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN EXAMPLE TABLE PORTLET-->
                                <div class="portlet box grey-cascade">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="fa fa-link"></i>线路查询
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <table class="table table-striped table-bordered table-hover" id="stationTable"
                                               data-search="true" data-show-refresh="true"
                                               data-show-toggle="true" data-show-columns="true"
                                               data-single-select="true" data-click-to-select="true" data-detail-view="true">
                                            <thead>
                                            <tr role="row" class="heading">
                                                <th data-field="checkbox" data-checkbox="true"></th>
                                                <th data-field="index" data-formatter="indexFormatter">序号</th>
                                                <th data-field="name" data-sortable="true">站点名称</th>
                                                <th data-field="code" data-sortable="true">站点编号</th>
                                                <th data-field="location">站点地址</th>
                                                <th data-field="remark">备注</th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>
                                </div>
                                <!-- END EXAMPLE TABLE PORTLET-->
                            </div>
                        </div>--%>
                    </div>
                </div>
                <!-- END CONTENT -->
            </div>
            <!-- END CONTAINER -->
            <!-- BEGIN FOOTER -->
            <div class="page-footer">
                <div class="page-footer-inner">
                    2017 &copy; Metronic by keenthemes.
                </div>
                <div class="scroll-to-top">
                    <i class="icon-arrow-up"></i>
                </div>
            </div>
            <!-- END FOOTER -->
        </div>
        <script src="scripts/bus/station.js"></script>
    </body>
</html>
