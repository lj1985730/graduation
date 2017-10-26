<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<base href="<%=basePath%>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta http-equiv="Content-type" content="text/html; charset=utf-8">

<link type="text/css" rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css" />
<link type="text/css" rel="stylesheet" href="plugins/simple-line-icons/simple-line-icons.min.css" />
<link type="text/css" rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="plugins/uniform/css/uniform.default.css" />
<link type="text/css" rel="stylesheet" href="plugins/bootstrap-switch/bootstrap-switch.min.css" />
<link type="text/css" rel="stylesheet" href="plugins/bootstrap-table/bootstrap-table.css" />
<link type="text/css" rel="stylesheet" href="plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.css" />
<%-- <link type="text/css" rel="stylesheet" href="plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" /> --%>
<link type="text/css" rel="stylesheet" href="css/global/components.css" id="style_components" />
<link type="text/css" rel="stylesheet" href="css/global/plugins.css" />
<link type="text/css" rel="stylesheet" href="css/layout.css" />
<link type="text/css" rel="stylesheet" href="css/themes/grey.css" />
<link type="text/css" rel="stylesheet" href="css/custom.css" />
<link type="text/css" rel="stylesheet" href="plugins/toastr/toastr.css" />
<link type="text/css" rel="stylesheet" href="plugins/select2/css/select2.css" />
<link type="text/css" rel="stylesheet" href="plugins/select2/css/select2-bootstrap.css" />

<script type="text/javascript" src="plugins/jquery.min.js"></script>
<script type="text/javascript" src="plugins/jquery-migrate.min.js"></script>
<%-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip --%>
<script type="text/javascript" src="plugins/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="plugins/uniform/jquery.uniform.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="plugins/bootbox/bootbox.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-dropdown/bootstrap-hover-dropdown.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-switch/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="plugins/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="scripts/global/metronic.js"></script>
<script type="text/javascript" src="scripts/layout.js"></script>
<script type="text/javascript" src="scripts/quick-sidebar.js"></script>
<script type="text/javascript" src="scripts/table-formatter.js"></script>
<script type="text/javascript" src="plugins/toastr/toastr.js"></script>
<script type="text/javascript" src="scripts/global/sys-message.js"></script>
<script type="text/javascript" src="plugins/select2/js/select2.js"></script>
<script type="text/javascript" src="plugins/select2/js/i18n/zh-CN.js"></script>

<style>
    .select2-container {
        width: 100% !important;
        padding: 0;
    }
    .select2-container--open {
        z-index: 9999999;
    }
    <%--
    .select2-container--default .select2-selection--single {
        height: 34px;
    }
    .select2-container--default .select2-selection--single .select2-selection__rendered {
        padding-top: 6px;
        padding-bottom: 6px;
        height: 32px;
        line-height: 20px;
    }
    .select2-container--default .select2-selection--single .select2-selection__arrow {
        height: 32px;
    }
    --%>
</style>

<script>
    var rootDir = '<%=basePath%>';
    $(document).ready(function() {
        Metronic.init(); // init metronic core components
        Layout.init(); // init current layout
    });
    $.fn.modal.Constructor.prototype.enforceFocus =function(){};    //select2 modal bug fix
</script>
