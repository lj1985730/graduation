<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ls" tagdir="/WEB-INF/tags" %>

<html>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		String __token__ = (String)request.getAttribute("__token__");
		String lsMenuId = (String)request.getAttribute("lsMenuId");
	%>
	<head>

        <script type="text/javascript" src="scripts/jquery.cookie.js"></script>
		<script type="text/javascript" src="templates/js/top.js"></script>
		<script type="text/javascript" src="templates/js/websocket/top_wfInstance.js"></script>
		<style>
		.themecolor{
			width: 35px;
		    height: 35px;
		    margin: 0px 4px;
		    cursor: pointer;
		    list-style: none;
		    float: left;
		    border: solid 1px #707070;
		   }
		</style>
	   
	</head>
		<input id="__token__" type="hidden" value="<%=__token__ %>">
		<input id="basePath" type="hidden" value="<%=basePath%>">
		<input id="lsMenuId" type="hidden" value="<%=lsMenuId%>">
		<div id="pageHeader" class="page-header navbar navbar-fixed-top" >
			<div class="page-header-inner">
				<div class="page-logo">
					<a href="javascript:openUrl('home');">
						<img src="images/logo.png" alt="logo" class="logo-default" id="backLogo"/>
					</a>
					<!-- <div class="menu-toggler sidebar-toggler hide"></div> -->
				</div>
				<div id="topSystemInfo" style="float:left; line-height: 46px; font-size: 17px; color: #C6CFDA;"></div>
				
				<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse"></a>
				<div class="top-menu">
					<ul class="nav navbar-nav pull-right" id="headalert">
						<!-- 系统切换 -->
						<li class="dropdown dropdown-user">
							<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
								<span id="currentSystem" class="username"></span>
								<i class="fa fa-angle-down"></i>
							</a>
							<ul id="systemDropdown" class="dropdown-menu dropdown-menu-default">
								<li class="divider" style="margin: 4px 0;"></li>
							</ul>
						</li>
						<li class="dropdown dropdown-extended dropdown-tasks" id="header_task_bar">
							<a href="javascript:;" class="dropdown-toggle"  data-hover="dropdown" data-close-others="true">
								<i class="icon-bell"></i>
								<span class="badge badge-default" id="workflow_top_count"> 0 </span>
							</a>
							<ul class="dropdown-menu">
								<li class="external">
									<h3><span class="bold" id=""></span>待办流程</h3>
									<a href="system/wfengine/loadUserInstance/index?__token__=<%=__token__ %>">查看全部</a>
								</li>
								<li>
									<ul class="dropdown-menu-list scroller" style="height: 250px;" data-handle-color="#637283" id="workflow_top_ul"></ul>
								</li>
						    </ul>
						</li>
						<li class="dropdown dropdown-extended dropdown-tasks" id="birthday_reminder_bar">
							<a href="javascript:;" class="dropdown-toggle"  data-hover="dropdown" data-close-others="true">
								<i class="icon-users"></i>
								<span class="badge badge-default" id="birthday"> 0 </span>
							</a>
							<ul class="dropdown-menu">
								<li class="external">
									<h3><span class="bold" id="birthdayMan">0 </span>生日提醒</h3>
									<!-- <a href="javascript:openUrl('crew/manage/sysPersonCrew/index','5D72A8FD-0A7D-405F-A2AA-C33FF96B8DF7');">查看全部</a> -->
								</li>
								<li>
									<ul class="dropdown-menu-list scroller" style="height: 150px; padding-left: 5px; padding-top: 5px;" data-handle-color="#637283" id="birthdayManLi"></ul>
								</li>
						    </ul>
						</li>
						<li class="dropdown dropdown-language">
							<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
								<img id="topImg" src="images/cn.png">
								<span id="topSpan" class="langname">
								中文 </span>
								<i class="fa fa-angle-down"></i>
							</a>
							<ul class="dropdown-menu dropdown-menu-default">
								<li>
									<a href="javascript:language(0);">
									<img alt="中文" src="images/cn.png"> 中文 </a>
								</li>
								<li>
									<a href="javascript:language(1);">
									<img alt="English" src="images/us.png"> English </a>
								</li>
								<!-- <li>
									<a href="javascript:;">
									<img alt="Russian" src="images/ru.png"> Russian </a>
								</li>
								<li>
									<a href="javascript:;">
									<img alt="French" src="images/fr.png"> French </a>
								</li> -->
							</ul>
					     </li>
					    
					     <li class="dropdown dropdown-extended dropdown-notification" id="header_notification_bar">
							<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
							<i class="icon-calendar"></i>
							</a>
							<ul class="dropdown-menu">
								<li id="styleDemo_darkblue" class="themecolor" style="background-color: #2b3643;" onclick="toggleStyle('darkblue')"></li>
								<li id="styleDemo_default" class="themecolor" style="background-color: #333438;" onclick="toggleStyle('default')"></li>
								<li id="styleDemo_blue" class="themecolor" style="background-color: #2D5F8B;" onclick="toggleStyle('blue')"></li>
								<li id="styleDemo_grey" class="themecolor" style="background-color: #697380;" onclick="toggleStyle('grey')"></li>
								<li id="styleDemo_light" class="themecolor" style="background-color: #F9FAFD;" onclick="toggleStyle('light')"></li>
								<li id="styleDemo_light2" class="themecolor" style="background-color: #F1F1F1;" onclick="toggleStyle('light2')"></li>
						    </ul>
						</li>
						<li class="dropdown dropdown-user">
							<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
								<img alt="" id="profileInfo_photo_id" class="img-circle" src=""  style="display: none;" />
								<span id="topPersonName" class="username username-hide-on-mobile"></span>
								<i class="fa fa-angle-down"></i>
							</a>
							<ul class="dropdown-menu dropdown-menu-default">
								<li>  
									<a href="<%=basePath%>system/authorization/sysPerson/personInfo/index">
									<i class="icon-user"></i> 我的信息 </a>
								</li>
								
								<!-- <li>
									<a href="inbox.html">
									<i class="icon-envelope-open"></i> 我的邮件 <span class="badge badge-danger">
									3 </span>
									</a>
								</li>
								<li>
									<a href="page_todo.html">
									<i class="icon-call-in"></i> 我的任务 <span class="badge badge-success">
									7 </span>
									</a>
								</li> -->
								<li class="divider"></li>
								<li>
									<a href="javascript:changePasswd();">
									<i class="icon-key"></i> 修改密码 </a>
								</li>
								<li>
									<a href="javascript:logout();">
									<i class="icon-power"></i> 退出系统 </a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<!--------------------------修改密码的弹出层---------------------------->
		<ls:modal id="changePassWin" title="修改密码" editble="true" onSave="lsEditPassword()"><!-- 默认保存方法为lsGridSave,自定义可声明onSave事件 -->
			<form id="changePassForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
				<div class="modal-body">
					<div class="row">
                    	<div class="col-md-12">
                        	<div class="form-group">
								<label class="control-label col-md-2">原密码</label>
								<div class="col-md-10">
									<input id="oldPassword" name="passwdOld" type="text" class="form-control validate[required, minSize[6], maxSize[15]]" placeholder="原密码..." />
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-md-2">新密码</label>
								<div class="col-md-10">
									<input id="newPassword" name="passwdNew" type="text" class="form-control validate[required, minSize[6], maxSize[15]]" placeholder="新密码..." />
								</div>
                            </div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-md-2">确认密码</label>
								<div class="col-md-10">
									<input id="againPassword" name="passwdAgain" type="text" class="form-control validate[required, minSize[6], maxSize[15], equals[newPassword]]" placeholder="确认新密码..." />
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</ls:modal>
		<%-- <!-- -----------------------流程发起 start--------------------------------------- -->
		
		<div id="_wf_loadInst_dlg" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog modal-xl">
		<div class="modal-content">
			<div class="modal-header bg-primary">
				<h4 class="modal-title">
					<i class="icon-pencil"></i>
					<span id="wfNodeRouteEditWinTitle" style="font-weight:bold;">流程处理</span>
					<button type="button" class="close icon-white" data-dismiss="modal" aria-hidden="true" />
                </h4>
			</div>
			<div class="modal-body">
		<form id="_wf_loadInst_dataForm" method="post" >
		  <div class="portlet box green">
			<div class="portlet-title">
			
				</div>
				<div class="portlet-body">
					<ul id="instTab" class="nav nav-tabs">
						<li class="active">
							<a href="#tab_2_1" data-toggle="tab">
							审批信息 </a>
						</li>
						<li>
							<a href="#tab_2_2" data-toggle="tab">
							附件列表 </a>
						</li>
						<li>
							<a href="#tab_2_3" data-toggle="tab">
							审批历史 </a>
						</li>
						<li>
							<a href="#tab_2_4" data-toggle="tab">
							流程信息 </a>
						</li>
						</ul>
						<div class="tab-content" >
						   <div class="nav tab-pane fade active in" id="tab_2_1">
						   		<!-- <div class="row col-md-12"> 
							   		<div class="col-md-12">
										<div class="form-group">
											<label class="control-label col-md-2"><span
												class="required">* </span>流程主旨</label>
											<div class="col-md-10">
												<input id="edit_pSubject" name="pSubject" class="form-control validate[required,maxSize[250]]" placeholder="岗位名称...">
											</div> 
										</div>
									</div>
								</div>
								<div class="row col-md-12" style="left: 0px; height: 1px; margin-top: 3px; margin-bottom: 3px; margin-left: 0px; background-color: green;"></div> -->
					   			<div id="_wf_loadInst_form_plugin" class=" form-horizontal form-bordered form-row-stripped"></div>
					   			<div id="_wf_loadInst_hiddens_plugin"></div> 
					   			<div id="_wf_loadInst_form_js_plugin"></div>
					   			<div id="_wf_loadInst_approve_plugin" style="margin-top:15px;"></div>    
						    </div> 
						     <div class="nav tab-pane fade" id="tab_2_2"> 
		    		             <div id="_wf_loadInst_attachFile_buttons_plugin1" style="float:right;">
		    		             	<a class="btn green" onclick="_wf_loadInst_hxWfAddAttachFile()" href="javascript:void(0)"><i class="fa fa-upload"> </i> <span class="hidden-480">上传</span></a>
		    		             	<a class="btn blue" onclick="_wf_loadInst_hxWfDownloadAttachFile()" href="javascript:void(0)"><i class="fa fa-download"> </i> <span class="hidden-480">下载</span></a>
		    		             	<!-- <a class="btn  red" onclick="_wf_loadInst_hxWfRemoveAttachFile()" href="javascript:void(0)"><i class="fa fa-remove"> </i> <span class="hidden-480">删除</span></a> -->
		    		             </div> 
		    		             <div class="table-container">
									<table id="_wf_loadInst_dg_attachFile" class="table table-striped table-hover"
										data-single-select="true" data-click-to-select="true">
										<thead>
											<tr role="row" class="heading">
												<th data-field="id" data-visible="false">ID</th>
												<th data-field="checkbox" data-checkbox="true" />
												<th data-field="index" data-formatter="indexmatter">序号</th>
												<th data-field="fileName" >名称</th>
												<th data-field="filesize" >大小（KB）</th> 
												<th data-field="sysUser.person.userCname" >上传人</th>   
												<th data-field="operatedate" >上传时间</th> 
											</tr>
										</thead>
									</table>
								  </div>
						    </div>
						     <div class="tab-pane fade" id="tab_2_3">
						     
						        <div class="table-container">
								<table id="_wf_loadInst_dg_history" class="table table-striped table-hover"
									
									data-single-select="true" data-click-to-select="true">
									<thead>
										<tr role="row" class="heading">
											<th data-field="index" data-formatter="indexmatter">序号</th>
											<th data-field="nodeName" >节点名称</th>
											<th data-field="sysUser.person.userCname" data-formatter="consignShowFormat" >处理人</th>
											<th data-field="approveStatus" data-formatter="procmatter">处理意见</th>
											<th data-field="approveOpinion" >处理信息</th>
											<th data-field="uTime" >处理时间</th> 
										</tr>
									</thead>
								</table>
							    </div>
							    <input type="hidden" id="uploadAttachFiles" name="uploadAttachFiles" />
							    <input type="hidden" id="deleteAttachFiles" name="deleteAttachFiles" />
						    </div>
						    <div class="nav tab-pane fade" id="tab_2_4" >
						    	<div class="table-container" >
									<div style="float:left; width:40px; border:1px solid #ccc; padding:1px">
						          		<div style="height:0;border:8px solid #FFF;overflow:hidden"></div>
						        	</div>
									<div style="float:left; margin-right:20px;">&nbsp;未审批</div>
									&nbsp;&nbsp;&nbsp;
									<div style="float:left; width:40px; border:1px solid #ccc; padding:1px">
						          		<div style="height:0;border:8px solid #FF0;overflow:hidden"></div>
						        	</div>
									<div style="float:left; margin-right:20px;">&nbsp;待审批</div>
									&nbsp;&nbsp;&nbsp;
									<div style="float:left; width:40px; border:1px solid #ccc; padding:1px">
						          		<div style="height:0;border:8px solid #0FF;overflow:hidden"></div>
						        	</div>
									<div style="float:left; margin-right:20px;">&nbsp;已审批</div>
									&nbsp;&nbsp;&nbsp;
									<div style="float:left; width:40px; border:1px solid #ccc; padding:1px">
						          		<div style="height:0;border:8px solid #F00;overflow:hidden"></div>
						        	</div>
									<div style="float:left; margin-right:20px;">&nbsp;异常</div>
						    	</div>
		    		           <div style="width:100%;">
					               <iframe id="ifChart" name="ifChart" onload="" width="100%" height="250px" scrolling="no" style="overflow:hidden;" frameborder="0" src=""></iframe>
				               </div>
						    </div> 
						  </div>
						</div>
				      </div>
				    </form>
		         </div>
				       <div class="modal-footer bg-info">
				          <div id="_wf_loadInst_approve_buttons_plugin" style="float:right;"></div>
					   </div>
			        </div>
			    </div>
			</div>
		   <!-- -----------------------流程发起 end--------------------------------------- -->
		    <!-- -----------------------流程发起 用户选择  start--------------------------------------- -->
		 
		     <div id="_wf_PersonPlugin_userchoose_dlg" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			    <div class="modal-dialog ">
				<div class="modal-content">
				<div class="modal-header bg-primary">
					<h4 class="modal-title">
						<i class="icon-pencil"></i>
						<span id="wfNodeRouteEditWinTitle" style="font-weight:bold;">处理人选择</span>
						<button type="button" class="close icon-white" data-dismiss="modal" aria-hidden="true" />
	                </h4>
				</div>
			<div class="modal-body">
		     
				<table style="width:95%;height:50px;">
			   		<tr>
			     		<td>
			     			<form id="_wf_loadInst_user_choose_form" name="_wf_loadInst_user_choose_form">
			       			  <!-- <select id="_wf_loadInst_user_code" 
			       				name="_wf_loadInst_user_code" style="width:150px;">
				  			  </select> -->
				  			  <div class="col-md-12"> 
								<div class="form-group">  
									<label class="control-label col-md-3"><span class="required"></span>审批人</label>
									<div class="col-md-9">
				  			  			<select id="_wf_loadInst_user_code" name="_wf_loadInst_user_code" class="form-control" placeholder="" >
                                    	</select>
                                    </div>
                                </div>
                              </div>
				  			</form>
						</td>
			    	</tr>
				</table>
				
				</div>
				       <div class="modal-footer bg-info">
				           <a href="javascript:void(0)" class="btn  green" 
				    	     onclick="hxWfPersonPlugin_saveUserChoose()"><i class="icon-wrench icon-white"> </i> 
				    	     <span class="hidden-480">确定</span></a> 
				           <a href="javascript:void(0)" class="btn  blue" 
					  	     onclick="javascript:$('#_wf_PersonPlugin_userchoose_dlg').modal('hide')">
					  	     <i class="icon-trash icon-white"> </i> <span class="hidden-480">
									关闭 </span></a>
					       </div>
			        </div>
			    </div>
			</div>
			    <!-- layout end-->
		    
		<div id="_wf_loadInst_javascripts"></div>
        <div id="_wf_loadInst_print_javascripts"></div>
		<form id="_wf_loadInst_loadChartForm" action="" target="ifChart" method="post" >
			<input type="hidden" id="_wf_loadInst_loadChartForm_token" name="__token__" value="">
        </form>
         <!-- -----------------------流程发起 用户选择  end--------------------------------------- -->
         
         <!-- uploadFile -->
		<ls:fileUpload id="importWorkflowFileWin" 
			url="/system/wfengine/wfInstattach/uploadFile" name="uploadFile"
			allowExt="[]" />  --%>
</html>

