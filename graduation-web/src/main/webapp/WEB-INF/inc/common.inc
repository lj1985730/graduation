<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<base href="<%=basePath%>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">

<link type="text/css" rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css"/>
<link type="text/css" rel="stylesheet" href="plugins/simple-line-icons/simple-line-icons.min.css"/>
<link type="text/css" rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="plugins/uniform/css/uniform.default.css" />
<link type="text/css" rel="stylesheet" href="plugins/bootstrap-switch/bootstrap-switch.min.css" />
<link type="text/css" rel="stylesheet" href="plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
<link type="text/css" rel="stylesheet" href="css/global/components.css" id="style_components" />
<link type="text/css" rel="stylesheet" href="css/global/plugins.css" />
<link type="text/css" rel="stylesheet" href="css/layout.css" />
<link type="text/css" rel="stylesheet" href="css/themes/grey.css" />
<link type="text/css" rel="stylesheet" href="css/custom.css" />


<script type="text/javascript" src="plugins/jquery.min.js"></script>
<script type="text/javascript" src="plugins/jquery-migrate.min.js"></script>
<!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script type="text/javascript" src="plugins/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="plugins/uniform/jquery.uniform.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-dropdown/bootstrap-hover-dropdown.min.js"></script>
<script type="text/javascript" src="plugins/bootstrap-switch/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="plugins/datatables/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="scripts/global/metronic.js"></script>
<script type="text/javascript" src="scripts/layout.js"></script>
<script type="text/javascript" src="scripts/quick-sidebar.js"></script>
