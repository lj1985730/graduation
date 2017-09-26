<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ls" tagdir="/WEB-INF/tags" %>
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="resources/css/tree/style.min.css" />
		<script type="text/javascript" src="resources/javascripts/jstree.min.js"></script>
		<script type="text/javascript" src="resources/pages/js/authority/menu.js"></script>
	</head>
	<body>
		<ls:nav father="系统管理" model="菜单管理" />
		<div class="row">
			<div class="col-md-12">
				<div class="portlet box blue-steel">
					<ls:title name="系统菜单">
						<button type="button" class="btn yellow" id="stopused" onClick="isUse();"><i class="fa fa-refresh"></i>&nbsp;菜单禁用</button>					
						<button type="button" class="btn green" onClick="winShow(0);"><i class="fa fa-plus"></i>&nbsp;增加同级菜单</button>
						<button type="button" class="btn green" onClick="winShow(1);"><i class="fa fa-plus"></i>&nbsp;增加下级菜单</button>
						<ls:modify onClick="winShow(2);" />
						<ls:delete /><!-- 默认删除方法为lsGridDelete,自定义可声明onClick事件 -->
					</ls:title>
					<div class="portlet-body">
                 		<div class="row">
		              		<div class="col-md-3">
		              		    <input id="menu_searchText" class="form-control" placeholder="搜索菜单..." onkeyup="menu_doSearch();" />
								<div id="menuTree" class="tree-demo"></div>
					  		</div>
						  	<div class="col-md-9">
								<div class="tabbable-custom">
									<ul class="nav nav-tabs">
										<li class="active">
											<a href="#menuDtlTab" data-toggle="tab">详细信息</a>
										</li>
										<li>
											<a href="#menuBtnTab" data-toggle="tab">菜单按钮</a>
										</li>
									</ul>
									<div class="tab-content">
										<!-- 详细信息 -->
										<div id="menuDtlTab" class="tab-pane active">
											<div class="table-container">
												<table align="center" class="table table-bordered table-striped" id="details">
													<tbody>
														<tr>
															<td class="col-md-3" align="right">菜单名称：</td>
													    	<td class="col-md-3" align="left" id="text"></td>
															<td class="col-md-3" align="right">菜单地址：</td>
															<td class="col-md-3" align="left" id="menuUrl"></td>
														</tr>
														<tr>
													    	<td align="right">菜单级别：</td>
															<td align="left" id="menuLevel"></td>
													    	<td align="right">是否使用：</td>
													    	<td align="left" id="isUsed"></td>
													  	</tr>
														<tr>
															<td align="right">排序号：</td>
															<td align="left" id="sortid"></td>
															<td align="right">图标：</td>
															<td align="left" id="menuIcon"></td>
														</tr>
														<tr>
															<td align="right">大图标：</td>
															<td align="left" id="menuBigIcon">
															<td align="right">业务树：</td>
															<td align="left" id="business"></td>
														</tr>
														<tr>
															<td align="right">快捷键：</td>
															<td align="left" id="keyboard"></td>
															<td align="right">操作时间：</td>
															<td align="left" id="utime"></td> 
														</tr>
														<tr>
															<td align="right">帮助：</td>
															<td align="left" id="menuHelp"></td>   
															<td align="right">菜单类型：</td>
															<td align="left" id="menuType"></td> 															  
														</tr>
														<tr>
															<td align="right">是否前台：</td>
															<td align="left" id="isPublic"></td>   
															<td align="right">是否可分配：</td>
															<td align="left" id="isAssignable"></td> 															  
														</tr>														
														<tr>
													    	<td align="right">备注：</td>
													    	<td align="left" id="memo" colspan="3"></td>
													    	<td style="display:none;">父ID：</td>
													    	<td id="parent" style="display:none;"></td>    
														</tr>
													</tbody>
												</table>
											</div>
										</div>
										<!-- 菜单按钮 -->
										<div id="menuBtnTab" class="tab-pane">							
											<div class="table-container">								
												<table id="btnTable" class="table table-striped table-hover" 
													data-single-select="true" data-click-to-select="true" data-search="true"
													data-create="btnShow(0);" data-modify="btnShow(1);" data-remove="btnDelete();">
													<thead>
														<tr role="row" class="heading">
															<th data-field="checkbox" data-checkbox="true" />
											                <th data-field="index" data-formatter="indexmatter">序号</th>
											                <th data-field="btnname" data-sortable="true" >按钮名称</th>
											                <th data-field="btnType" data-sortable="true" data-formatter="lsFmt.pub.landOrShip">按钮类型 </th>
											                <th data-field="btnUnionId" data-sortable="true">按钮属性值</th>
											                <th data-field="btnicon" data-sortable="true">按钮样式</th>
											                <th data-field="isused" data-sortable="true" data-formatter="lsFmt.pub.isUsed">是否使用</th>
											                <th data-field="memo" data-visible="false">备注</th> 								
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
		</div>		
		
		<!-- ------------------------添加/修改菜单的弹出层-------------------------- -->
		<ls:modal id="menuEditWin" title="修改菜单" editble="true" tagClass="modal-lg"><!-- 默认保存方法为lsGridSave,自定义可声明onSave事件 -->
			<form id="menuForm" class="form-horizontal form-bordered form-row-strippe" data-toggle="validator">
				<input class="switch" id="id_2" name="id" type="hidden" />
				<input class="switch" id="parent_2" name="parent" type="hidden" />
				<input id="isUsed_2" name="isUsed" type="hidden" />
				<input id="isPublic_2" name="isPublic" type="hidden" />
				<input id="isAssignable_2" name="isAssignable" type="hidden" />
				<div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label col-md-4"><span
								class="required">* </span>菜单名称</label>
                                <div class="col-md-8">                               
                                     <input id="text_2" name="text" type="text" class="form-control validate[required,maxSize[50]]" placeholder="菜单名称..." />
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label col-md-4">菜单地址</label>
                                <div class="col-md-8">
                                     <input id="menuUrl_2" name="menuUrl" type="text" class="form-control validate[maxSize[200]]" placeholder="菜单地址..." />
                                </div>
                            </div>
                        </div>
                         <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label col-md-4"><span
								class="required">* </span>菜单级别</label>
                                <div class="col-md-8">
                                     <input id="menuLevel_2" name="menuLevel" type="text" class="form-control" readonly />
                                </div>
                            </div>
                        </div>

                         <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label col-md-4"><span
								class="required">* </span>菜单类型</label>
                                <div class="col-md-8">
                                  <select id="menuType_2" name="menuType"  class="form-control select2 validate[required]" placeholder="菜单类型..." >                                     
								      <option value="0">全部</option>
								      <option value="1">岸基</option>
								      <option value="2">船端</option>
								   </select>
                                </div>                                
                            </div>
                        </div> 
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label col-md-4"><span
								class="required">* </span>排序号</label>
                                <div class="col-md-8">
                                     <input id="sortid_2" name="sort" type="text" class="form-control validate[required,custom[integer],maxSize[11]]" placeholder="排序号..." />
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label col-md-4">图标</label>
                                <div class="col-md-8">
                                     <input id="menuIcon_2" name="menuIcon" type="text" class="form-control validate[maxSize[50]]" placeholder="图标..." />
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label col-md-4">大图标</label>
                                <div class="col-md-8">
                                     <input id="menuBigIcon_2" name="menuBigIcon" type="text" class="form-control validate[maxSize[50]]" placeholder="大图标..." />
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label col-md-4">业务树</label>
                                <div class="col-md-8">
                                     <input id="business_2" name="business" type="text" class="form-control validate[maxSize[200]]" placeholder="业务树..." />
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label col-md-4">帮助</label>
                                <div class="col-md-8">
                                      <input id="menuHelp_2" name="menuHelp" type="text" class="form-control validate[maxSize[1000]]" placeholder="帮助..." />
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="control-label col-md-4">快捷键</label>
                                <div class="col-md-8">
                                      <input id="keyboard_2" name="keyboard" type="text" class="form-control validate[maxSize[100]]" placeholder="快捷键..." />
                                </div>
                            </div>
                        </div>                                        
                        <div class="col-md-12">
                            <div class="form-group">
                                <label class="control-label col-md-2">备注</label>
                                <div class="col-md-10">
                                    <textarea id="memo_2" name="memo" class="form-control" placeholder="备注..."></textarea>
                                </div>
                            </div>
                        </div>       						
				</div>
			</form>	
		</ls:modal>
		
				<!-- ------------------------添加/修改按钮的弹出层-------------------------- -->
		<ls:modal id="btnEditWin" title="修改按钮" editble="true" onSave="btnSave();"><!-- 默认保存方法为lsGridSave,自定义可声明onSave事件 -->
			<form id="btnForm" class="form-horizontal form-bordered form-row-strippe" data-toggle="validator">
				<input class="switch" name="id" type="hidden" />
				<input class="switch" id="menuid" name="menuid" type="hidden" />
				<div class="row">
                          <div class="col-md-12">
                            <div class="form-group">
                                <label class="control-label col-md-3"><span
								class="required">* </span>按钮名称</label>
                                <div class="col-md-9">
                                     <input name="btnname" type="text" class="form-control validate[required,maxSize[50]]" placeholder="按钮名称..." />
                                     
                                </div>
                            </div>
                        </div>
                           <div class="col-md-12">
                            <div class="form-group">
                                <label class="control-label col-md-3"><span
								class="required">* </span>按钮类型</label>
                                <div class="col-md-9">
                                    <select name="btnType" type="text" class="form-control select2" placeholder="按钮类型..." >
								      <option value="0">全部</option>
								      <option value="1">岸基</option>
								      <option value="2">船端</option>							      
                                    </select>
                                    
                                </div>
                            </div>
                        </div>  
                            <div class="col-md-12">
                            <div class="form-group">
                                <label class="control-label col-md-3"><span
								class="required">* </span>按钮属性值</label>
                                <div class="col-md-9">
                                     <input name="btnUnionId" type="text" class="form-control validate[required,maxSize[50],custom[onlyHtmlArr]]" placeholder="按钮属性值..." />
                                     
                                </div>
                            </div>
                        </div>                                               
                           <div class="col-md-12">
                            <div class="form-group">
                                <label class="control-label col-md-3">按钮样式</label>
                                <div class="col-md-9">
                                     <input name="btnicon" type="text" class="form-control validate[maxSize[50]]" placeholder="按钮样式..." />
                                     
                                </div>
                            </div>
                        </div>  
                          <div class="col-md-12">
                            <div class="form-group">
                                <label class="control-label col-md-3"><span
								class="required">* </span>是否使用</label>
                                <div class="col-md-9">
                                    <select name="isused" type="text" class="form-control select2" placeholder="是否使用..." >
                                      <option value="1">使用</option>
                                      <option value="0">禁用</option>								      
                                    </select>
                                    
                                </div>
                            </div>
                        </div>  
                            <div class="col-md-12">
                            <div class="form-group">
                                <label class="control-label col-md-3">备注</label>
                                <div class="col-md-9">
                                    <textarea name="memo" class="form-control" placeholder="备注..."></textarea>
                                    
                                </div>
                            </div>
                        </div>      						
				</div>
			</form>
		</ls:modal>
		
	</body>
</html>