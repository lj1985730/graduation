<!-- 自定义设备选择窗口-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ taglib prefix="ls" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 参数 -->
<%@ attribute name="id" required="false" rtexprvalue="false" type="java.lang.String" description="唯一标识" %>
<%@ attribute name="multiSelect" required="false" rtexprvalue="false" type="java.lang.Boolean" description="表格是否多选" %>

<input type="hidden" id="userId" name="userId"/> 
<input type="hidden" id="personId" name="personId" disabled/> 
<c:if test="${not empty multiSelect && multiSelect}">
<div class="col-md-10">
<textarea id="${id}" class="form-control" placeholder="人员..." disabled/></textarea> 
</div>
<div class="col-md-2">
<button type="button" class="btn blue" onclick="personWinShow()"><i class="fa fa-edit"></i></button>
</div>
</c:if>	    
<c:if test="${empty multiSelect}">
<div class="col-md-10">
<input id="${id}" class="form-control" placeholder="人员..." disabled/> 
</div>
<div class="col-md-2">
<button type="button" class="btn blue" onclick="personWinShow()"><i class="fa fa-edit"></i></button>
</div>
</c:if>	 
	
<link type="text/css" rel="stylesheet" href="css/tree/style.min.css" />
<script type="text/javascript" src="scripts/jstree.min.js"></script>
<script type="text/javascript">
var isSingleSelect = ' data-single-select="true" ';
if('${multiSelect}' == 'true') {
	isSingleSelect = '';
}
var choosePersonWin = 
	'<div id="choosePersonWin" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">' +
		'<div class="modal-dialog modal-lg">' +
		'<div class="modal-content">' +
			'<div class="modal-header bg-primary">' +
				'<h4 class="modal-title">' +
					'<i class="icon-pencil"></i>' +
					'<span id="choosePersonTitle" style="font-weight:bold;">选择用户</span>' +
					'<button type="button" class="close icon-white" data-dismiss="modal" aria-hidden="true" />' +
                '</h4>' +
			'</div>' +
			'<div class="modal-body">' +
				'<div class="row">' +
				'<div class="col-md-4">	' +				
              	'<div class="tabbable-custom">' +
					'<ul class="nav nav-tabs">' +
						'<li class="active">' +
							'<a href="#onlandTab" data-toggle="tab" onclick="refreshLand();">岸基部门</a>' +
						'</li>' +
						'<li>' +
							'<a href="#onshipTab" data-toggle="tab" onclick="refreshShip();">船端部门</a>' +
						'</li>' +
					'</ul>' +
					'<div class="tab-content">' +
						'<div id="onlandTab" class="tab-pane active">' +	
		              		    '<input id="ldept_searchText" class="form-control" placeholder="搜索部门..." onkeyup="ldept_doSearch();" />' +
							'<div id="onlandTree" class="tree-demo"></div>' +										
						'</div>' +	
						'<div id="onshipTab" class="tab-pane">' +	
		              		    '<input id="sdept_searchText" class="form-control" placeholder="搜索部门..." onkeyup="sdept_doSearch();" />' +
							'<div id="onshipTree" class="tree-demo"></div>' +											
						'</div>' +
					'</div>' +																				              		
	  		  '</div>' +						
			'</div>' +
					'<div class="col-md-8">' +				
						'<div>' +
							'<table id="userChooseTable" class="table table-striped table-hover" ' +
							' data-click-to-select="true" '+isSingleSelect+' data-search="true" data-show-refresh="true">' +
								'<thead>' +
									'<tr role="row" class="heading">' +
										'<th data-field="checkbox" data-checkbox="true" />' +
										'<th data-field="index" data-formatter="indexmatter">序号</th>' +					                
						                '<th data-field="person.userCname" data-sortable="true">用户姓名</th>' +
						                '<th data-field="loginName" data-sortable="true">登录名称</th> ' +
						                '<th data-field="person.userEname" data-sortable="true">英文名</th>' +
									'</tr>' +
								'</thead>' +
							'</table>' +
						'</div>' +
					'</div>' +
				'</div>' +
			'</div>' +
			'<div class="modal-footer bg-info">' +
			     '<button type="button" class="btn yellow" onClick="chooseUserClear();"><i class="fa fa-refresh"></i>&nbsp;重置</button>' +
			     '<button type="button" class="btn green" onClick="chooseUserSave();"><i class="fa fa-check"></i>&nbsp;确定</button>' +
				 '<button type="button" class="btn red" data-dismiss="modal"><i class="fa fa-remove"></i>&nbsp;关闭</button>' +
			'</div>' +
		'</div>' +
	'</div>' +
'</div>';

	/**
	 * 初始加载
	 */
	$(function() {
		
		$(choosePersonWin).appendTo('body');
		//部门树
		loadOnlandTree();
		loadOnshipTree();
		//用户表
		$('#userChooseTable').bootstrapTable(
				$.extend({ url : rootUrl + "/system/authorization/sysUser/winList" },
				$.extend({}, tableOptions, { queryParams : userChooseParams }))
		);
		//getCurrentTree().jstree("select_node", "0");
	});
	var landOrShip = "1";
	/**
	 * 切换树
	 */
	function getCurrentTree() {		
		if($("#onlandTab").hasClass('active')){
			return $("#onlandTree");
			
		} else {
			return $("#onshipTree");
			
		}
	}
	
	/**
	 * 查询树
	 */
	function ldept_doSearch() {
		$('#onlandTree').jstree(true).search($('#ldept_searchText').val());
	}
	var isLand = "1";//treelist初始化岸端
	var isUsed = "1";//treelist不做是否使用过滤
	function loadOnlandTree() {
		$("#onlandTree").jstree({
			plugins : [ "sort", "search", 'types', 'wholerow' ],
			core : {
				data : {
					type : 'post',
					url : rootUrl + '/system/authorization/sysDepartment/treelist/' + isUsed + '/' + isLand,	// 请求地址
					data : $.lhToken(true),
					success : function(data) {},
					error : function() {}
				},
				themes : {
					dots : false,
					stripes : true,
					responsive : true
				}
			},
			types : {			
			    "house" : {
				      icon: 'images/icon/house.png'
				    },
			    "group" : {
				      icon: 'images/icon/group.png'
				    },
			    "user" : {
				      icon: 'images/icon/user.png'
				    }
			},
			sort : function(a, b) {
				a = this.get_node(a);
				b = this.get_node(b);
				return (a.original.sort > b.original.sort ? 1 : -1);
			},
		}).on("changed.jstree", eval('selectDept')).on('load_node.jstree', function(e, obj) {
			if(obj.node && obj.node.children) {	//默认打开第一层
				obj.instance.open_node(obj.node.children[0]);			
			}
		});    
	}
	/**
	 * 查询树
	 */
	function sdept_doSearch() {
		$('#onshipTree').jstree(true).search($('#sdept_searchText').val());
	}
	var isShip = "2";//treelist初始化船端
	function loadOnshipTree() {
		$("#onshipTree").jstree({
			plugins : [ "sort", "search", 'types', 'wholerow' ],
			core : {
				data : {
					type : 'post',
					url : rootUrl + '/system/authorization/sysDepartment/treelist/' + isUsed + '/' + isShip,	// 请求地址
					data : $.lhToken(true),
					success : function(data) {},
					error : function() {}
				},
				themes : {
					dots : false,
					stripes : true,
					responsive : true
				}
			},
			types : {			
			    "house" : {
				      icon: 'images/icon/house.png'
				    },
			    "group" : {
				      icon: 'images/icon/group.png'
				    },
			    "user" : {
				      icon: 'images/icon/user.png'
				    }
			},
			sort : function(a, b) {
				a = this.get_node(a);
				b = this.get_node(b);
				return (a.original.sort > b.original.sort ? 1 : -1);
			},
		}).on("changed.jstree", eval('selectDept')).on('load_node.jstree', function(e, obj) {
			if(obj.node && obj.node.children) {	//默认打开第一层
				obj.instance.open_node(obj.node.children[0]);			
			}
		});    
	}
	/**
	 * 查询条件
	 * @param params 基本查询条件，包含search、sort、order、limit、offset
	 */	
	function userChooseParams(params) {
		if(!getCurrentTree().jstree(true).get_selected()[0]) {
			return null;
		}
		var localParams = {
			__token__ : $('#__token__').val(),
			deptId	:  getCurrentTree().jstree(true).get_selected()[0],
			landOrShip:landOrShip
		}
		return $.extend(localParams, params);
	}

	/**
	 * 查询用户
	 */
	function searchUser() {
		$('#userChooseTable').bootstrapTable('refresh');
	}

	/**
	 * 选中tree触发
	 * @param e
	 * @param data
	 */
	function selectDept(e, data) {
		if (data.selected.length > 0) {
			searchUser();
		}
	}
	/**
	* tab页点击触发刷新对应树
	*/
	function refreshLand(){
		landOrShip = "1";
		$("#onlandTree").jstree("refresh");
		if(!$("#onlandTree").jstree(true).get_selected()[0]){
			$("#userChooseTable").bootstrapTable('removeAll');
		}
	}
	function refreshShip(){
		landOrShip = "2";
		$("#onshipTree").jstree("refresh");
		if(!$("#onshipTree").jstree(true).get_selected()[0]){
			$("#userChooseTable").bootstrapTable('removeAll');
		}
	}
//----------------------------------------弹出用户选择-----------------------------------------------------	
	/**
	 * 弹出窗口
	 */
	function personWinShow(){
		$('#userChooseTable').bootstrapTable('refresh');	
		$("#choosePersonWin").modal("show");	
	}
	/**
	 * 保存
	 */
	function chooseUserSave(){
		var userRow = $("#userChooseTable").bootstrapTable('getSelections')[0];
		if(userRow){
			var userRows = $("#userChooseTable").bootstrapTable('getSelections');
			 	var userId = [];
		    var personId = [];
		    var userCname = [];
		    for(var i=0;i<userRows.length;i++){
		    	userId[i] = userRows[i].userId;
		    	personId[i] = userRows[i].personId;
		    	userCname[i] = userRows[i].person.userCname;
	 	    }			
		   $("#userId").val(userId);
			$("#personId").val(personId);
			$("#${id}").val(userCname);		
		}else{
			$("#userId").val("");
			$("#personId").val("");
			$("#${id}").val("");			
		}
		$("#choosePersonWin").modal("hide");	
	}
	/**
	 * 清空表格选中
	 */
	function chooseUserClear(){		
		getCurrentTree().jstree("deselect_node", getCurrentTree().jstree(true).get_selected()[0]);
		//getCurrentTree().jstree("select_node", "0");
		getCurrentTree().jstree("close_all");		
		getCurrentTree().jstree("refresh");
		$("#userChooseTable").bootstrapTable('removeAll');
	}
</script>