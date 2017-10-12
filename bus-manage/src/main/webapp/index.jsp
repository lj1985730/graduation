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
                                        <table class="table table-striped table-bordered table-hover" id="lineTable">
                                            <thead>
                                            <tr>
                                                <th class="table-checkbox">
                                                    <input type="checkbox" class="group-checkable" data-set="#lineTable .checkboxes"/>
                                                </th>
                                                <th>站点名称</th>
                                                <th>站点地址</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    shuxer
                                                </td>
                                                <td>
                                                    <a href="mailto:shuxer@gmail.com"> shuxer@gmail.com </a>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    looper
                                                </td>
                                                <td>
                                                    <a href="mailto:looper90@gmail.com">
                                                        looper90@gmail.com </a>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    userwow
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@yahoo.com">
                                                        userwow@yahoo.com </a>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    user1wow
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        userwow@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    12.12.2012
                                                </td>
                                                <td>
										<span class="label label-sm label-default">
										Blocked </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    restest
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        test@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    12.12.2012
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    foopl
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        good@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    19.11.2010
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    weep
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        good@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    19.11.2010
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    coop
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        good@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    19.11.2010
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    pppol
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        good@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    19.11.2010
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    test
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        good@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    19.11.2010
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    userwow
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        userwow@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    12.12.2012
                                                </td>
                                                <td>
										<span class="label label-sm label-default">
										Blocked </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    test
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        test@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    12.12.2012
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    goop
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        good@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    12.11.2010
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    weep
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        good@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    15.11.2011
                                                </td>
                                                <td>
										<span class="label label-sm label-default">
										Blocked </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    toopl
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        good@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    16.11.2010
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    userwow
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        userwow@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    9.12.2012
                                                </td>
                                                <td>
										<span class="label label-sm label-default">
										Blocked </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    tes21t
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        test@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    14.12.2012
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    fop
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        good@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    13.11.2010
                                                </td>
                                                <td>
										<span class="label label-sm label-warning">
										Suspended </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    kop
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        good@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    17.11.2010
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    vopl
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        good@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    19.11.2010
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    userwow
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        userwow@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    12.12.2012
                                                </td>
                                                <td>
										<span class="label label-sm label-default">
										Blocked </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    wap
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        test@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    12.12.2012
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    test
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        good@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    19.12.2010
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    toop
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        good@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    17.12.2010
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            <tr class="odd gradeX">
                                                <td>
                                                    <input type="checkbox" class="checkboxes" value="1"/>
                                                </td>
                                                <td>
                                                    weep
                                                </td>
                                                <td>
                                                    <a href="mailto:userwow@gmail.com">
                                                        good@gmail.com </a>
                                                </td>
                                                <td>
                                                    20
                                                </td>
                                                <td class="center">
                                                    15.11.2011
                                                </td>
                                                <td>
										<span class="label label-sm label-success">
										Approved </span>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <!-- END EXAMPLE TABLE PORTLET-->
                            </div>
                        </div>
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
        <script>
            $(document).ready(function() {
                Metronic.init(); // init metronic core components
                Layout.init(); // init current layout
                initTable1();
            });

            var initTable1 = function () {

                var table = $('#lineTable');

                // begin first table
                table.dataTable({

                    // Internationalisation. For more info refer to http://datatables.net/manual/i18n
                    "language": {
                        "aria": {
                            "sortAscending": ": activate to sort column ascending",
                            "sortDescending": ": activate to sort column descending"
                        },
                        "emptyTable": "No data available in table",
                        "info": "Showing _START_ to _END_ of _TOTAL_ records",
                        "infoEmpty": "No records found",
                        "infoFiltered": "(filtered1 from _MAX_ total records)",
                        "lengthMenu": "Show _MENU_ records",
//                        "search": "Search:",
                        "zeroRecords": "No matching records found",
                        "paginate": {
                            "previous":"Prev",
                            "next": "Next",
                            "last": "Last",
                            "first": "First"
                        }
                    },

                    // Or you can use remote translation file
                    //"language": {
                    //   url: '//cdn.datatables.net/plug-ins/3cfcc339e89/i18n/Portuguese.json'
                    //},

                    "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

                    "columns": [{
                        "orderable": false
                    }, {
                        "orderable": true
                    }, {
                        "orderable": false
                    }, {
                        "orderable": false
                    }, {
                        "orderable": true
                    }, {
                        "orderable": false
                    }],
                    "lengthMenu": [
                        [5, 10, 15, 20, -1],
                        [5, 10, 15, 20, "All"] // change per page values here
                    ],
                    // set the initial value
                    "pageLength": 10,
                    "pagingType": "bootstrap_full_number",
                    "columnDefs": [{  // set default column settings
                        'orderable': false,
                        'targets': [0]
                    }, {
                        "searchable": false,
                        "targets": [0]
                    }],
                    "order": [
                        [1, "asc"]
                    ] // set first column as a default sort by asc
                });

                var tableWrapper = $('#lineTable_wrapper');

                table.find('.group-checkable').change(function () {
                    var set = $(this).attr("data-set");
                    var checked = $(this).is(":checked");
                    $(set).each(function () {
                        if (checked) {
                            $(this).attr("checked", true);
                            $(this).parents('tr').addClass("active");
                        } else {
                            $(this).attr("checked", false);
                            $(this).parents('tr').removeClass("active");
                        }
                    });
                    $.uniform.update(set);
                });

                table.on('change', 'tbody tr .checkboxes', function () {
                    $(this).parents('tr').toggleClass("active");
                });

                tableWrapper.find('.dataTables_length select').addClass("form-control input-xsmall input-inline"); // modify table per page dropdown
            }
        </script>
    </body>
</html>
