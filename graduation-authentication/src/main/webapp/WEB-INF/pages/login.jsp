<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!--[if lt IE 8]>

<!--<![endif]-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
	<head>
		<base href="<%=basePath%>" />
		<title>BUS</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<link type="text/css" rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css" />
		<link type="text/css" rel="stylesheet" href="plugins/simple-line-icons/simple-line-icons.min.css" />
		<link type="text/css" rel="stylesheet" href="plugins/bootstrap/css/bootstrap.min.css" />
		<link type="text/css" rel="stylesheet" href="plugins/uniform/css/uniform.default.css" />
		<link type="text/css" rel="stylesheet" href="css/login/login-soft.css" />
		<link type="text/css" rel="stylesheet" href="css/global/components.css" id="style_components" />
		<link type="text/css" rel="stylesheet" href="css/layout.css" />
	</head>
	<body class="login" onkeydown="enterDown(event)">
		<div class="logo">
			<div class="form-title">
				<span style="color: white; font-size: 55px;">公交管理系统</span>
			</div>
		</div>
		<div class="menu-toggler sidebar-toggler"></div>
		<!-- BEGIN LOGIN -->
		<div class="content">
		<!-- BEGIN LOGIN FORM -->
			<h3 class="form-title">账户登录</h3>
			<div class="alert alert-danger display-hide">
				<button class="close" data-close="alert"></button>
				<span>
				键入用户名和密码。 </span>
			</div>
			<label id="msg" class="msg"></label>
			<div class="form-group">
				<div class="input-icon">
					<i class="fa fa-user"></i>
					<input type="text" id="name" name="name" value="${user.loginName}" class="form-control placeholder-no-fix" autocomplete="off" placeholder="用户名" /><!-- ${user.loginName} -->
				</div>
			</div>
			<div class="form-group">
				<div class="input-icon">
					<i class="fa fa-lock"></i>
					<input type="password" id="pass" name="pass" value="" class="form-control placeholder-no-fix" autocomplete="off" placeholder="密码" /><!-- ${user.loginPassword} -->
				</div>
			</div>
			<div class="form-actions">
				<label class="checkbox" title="保存时限7天，公共网络慎用">
					<input type="checkbox" id="rememberMe"  name="rememberMe" /> 记住账号 
				</label>
				<button class="btn blue pull-right" onclick="doLogin();">
				登录 <i class="m-icon-swapright m-icon-white"></i>
				</button>
			</div>
		</div>
		<!-- BEGIN COPYRIGHT -->
		<div class="copyright">
			 2017 &copy; EDU
		</div>
		<script type="text/javascript" src="plugins/jquery.min.js"></script>
		<script type="text/javascript" src="plugins/jquery-migrate.min.js"></script>
		<script type="text/javascript" src="plugins/uniform/jquery.uniform.min.js"></script>
		<script type="text/javascript" src="plugins/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="scripts/login/jquery.cokie.min.js"></script>
		<script type="text/javascript" src="scripts/login/jquery.backstretch.min.js"></script>
		<script type="text/javascript" src="scripts/login/login-soft.js"></script>
		<script type="text/javascript" src="scripts/global/metronic.js"></script>
		<script type="text/javascript">
            var pathArray = top.window.location.href.split('/');
            var protocol = pathArray[0];
            var host = pathArray[2];
            var context = pathArray[3];
            /**
             * 登录
             */
            function doLogin() {
                var msg = $("#msg");
                var name = $("#name").val();
                var pass = $("#pass").val();
                if(typeof(pass) === "undefined" || $.trim(pass) === "") {
                    msg.html("密码不能为空！");
                    return false;
                }
                var rememberMe = document.getElementById("rememberMe").checked;
                msg.html("登录中...");
                $.ajax({
                    type : "POST",
                    timeout : 20000,
                    url : "login",
                    dataType : "json",
                    contentType : "application/json",
                    data : JSON.stringify({
                        name : name,
                        pass : pass,
                        rememberMe : rememberMe
                    }),
                    success : function(data) {
                        if(!data) {
                            msg.html("系统没有响应，请联系管理员。");
                            return false;
                        }
                        try {
                            if(data.message && data.message.length > 20) {
                                msg.html(data.message);
                            } else {
                                msg.html(data.message);
                                msg.fadeIn("normal");
                            }
                            if(data.success) {
                                if(top === window) {
                                    top.location.href = protocol + '//' + host + "/" + context + "/bus/station/home";
                                }
                            }
                        } catch(e) {
                        }
                    },
                    error:function(jqXHR, status, errorThrown){
                        msg.html("出错了！异常：" + status + " " + errorThrown);
                    }
                });
            }

            /**
             * 回车登录
             */
            function enterDown(e) {
                var _key;
                if(window.event) { //兼容IE浏览器
                    _key = e.keyCode;
                } else if(e.which) {	//兼容非IE浏览器
                    _key = e.which;
                }
                if (_key === 0xD) {	//按下回车键时触发
                    doLogin();
                }
            }
		</script>
		<script>
			$(function() {
				Metronic.init();
				// 背景图片定时切换
				$.backstretch([
					"img/login/login_bg4.jpg"
				], {
					fade: 1000,
					duration: 15000
				});
			});
		</script>
	</body>
</html>