var baseUrl = "/authentication/menu";
$(function() {
	loadTree();
	//覆盖tableOptions中的queryParams方法
	$('#buttonTable').bootstrapTable(
		$.extend({ url : rootUrl + baseUrl + '/button' },
		$.extend({}, tableOptions, { queryParams : btnParams }))
	);
});

/**
 * 按钮查询条件
 * @param params 基本查询条件，包含search、sort、order、limit、offset
 */
function buttonParams(params) {
	var localParams = {
		menuId : $('#menuTree').jstree(true).get_selected()[0]
	}
	return $.extend(localParams, params);
}
/**
 * 查询树
 */
function menu_doSearch() {
	$('#menuTree').jstree(true).search($('#menu_searchText').val());
}

/**
 * 菜单树初始加载
 */
function loadTree() {
	$("#menuTree").jstree({
		plugins : [ 'sort', "search", 'types', 'wholerow' ],
		core : {
			data : {
				type : 'post',
				url : rootUrl + baseUrl + '/tree',	// 请求地址
				data : {
					enabled : null
				},
				success : function(data) {}
			},
			themes : {
				dots : false,
				stripes : true,
				responsive : true
			}
		},
		types : {
			"default" : {
				icon : 'images/icon/page.png'
			}
		},
		sort : function(a, b) {
			a = this.get_node(a);
			b = this.get_node(b);
			return (a.original.sort > b.original.sort ? 1 : -1);
		},
	}).on("changed.jstree", function(e, data) {
		if (data.selected.length) {
			var id = data.instance.get_node(data.selected[0]).id;
			$("#details").call({
				method : "GET",
				url : baseUrl + '/' + id,
				callback : function(data) {
					if(data != 'null') {
						$("#keyboard").html(JSON.stringify(e.keyboard).replace(/\"/g, ""));
				 		$("#utime").html(JSON.stringify(e.uTime).replace(/\"/g, ""));
						$("#parent").html(JSON.stringify(e.parent).replace(/\"/g, ""));
						$("#text").html(JSON.stringify(e.text).replace(/\"/g, ""));
				 		$("#menuUrl").html(JSON.stringify(e.menuUrl).replace(/\"/g, ""));
						$("#menuLevel").html(JSON.stringify(e.menuLevel).replace(/\"/g, ""));
						$("#sortid").html(JSON.stringify(e.sort).replace(/\"/g, ""));
						$("#menuIcon").html(JSON.stringify(e.menuIcon).replace(/\"/g, ""));
						$("#menuBigIcon").html(JSON.stringify(e.menuBigIcon).replace(/\"/g, ""));
						$("#business").html(JSON.stringify(e.business).replace(/\"/g, ""));
						$("#menuHelp").html(JSON.stringify(e.menuHelp).replace(/\"/g, ""));
						$("#memo").html(JSON.stringify(e.memo).replace(/\"/g, ""));
						if(e.isUsed == '1') {
							$("#isUsed").html("使用");
							$("#stopused").html("<i class=\"fa fa-refresh\"></i>&nbsp;菜单禁用");
						} else if (e.isUsed == '0') {
							$("#isUsed").html("禁用");
							$("#stopused").html("<i class=\"fa fa-refresh\"></i>&nbsp;菜单使用");
						} else {
							$("#isUsed").html("");
						}
						if(e.menuType == '0') {
							$("#menuType").html("全部");
						} else if (e.menuType == '1') {
							$("#menuType").html("岸基");
						} else if (e.menuType == '2') {
							$("#menuType").html("船端");
						}else {
							$("#menuType").html("");
						}	 
						if(e.isPublic == 0) {
							$("#isPublic").html("全部");
						} else if (e.isPublic == 1) {
							$("#isPublic").html("前台");
						} else if (e.isPublic == 2) {
							$("#isPublic").html("后台");
						}else {
							$("#isPublic").html("");
						}	
						if(e.isAssignable == 0) {
							$("#isAssignable").html("否");
						} else if (e.isAssignable == 1) {
							$("#isAssignable").html("是");
						} else {
							$("#isAssignable").html("");
						}						
						$("#details").html($("#details").html().replace(/null/g, ""));
					}
				}
			});
			$('#buttonTable').bootstrapTable('refresh');
		}
	});
}

/**
 * 弹出编辑窗口
 * @param saveOrUpdate	对应操作 0 增加同级；1 增加下级；2 修改；
 */
var sOrU;
function showEditModal(saveOrUpdate) {
	try {
		sOrU = saveOrUpdate;
		$("#menuForm").form('clear');
		var menuTree = $('#tree').jstree(true);
		var menuId = menuTree.get_selected()[0];
		if (sOrU == 2) {	//修改
			if (menuId) {
				$("#id_2").val(menuId);
				$("#parent_2").val($("#parent").html());
				$("#text_2").val($("#text").html());
				$("#menuUrl_2").val($("#menuUrl").html());
				$("#menuLevel_2").val($("#menuLevel").html());
				$("#sortid_2").val($("#sortid").html());
				$("#menuIcon_2").val($("#menuIcon").html());
				$("#menuBigIcon_2").val($("#menuBigIcon").html());
				$("#business_2").val($("#business").html());
				$("#menuHelp_2").val($("#menuHelp").html());
				$("#memo_2").val($("#memo").html());
				$("#keyboard_2").val($("#keyboard").html());
				if($("#isUsed").html()=='使用'){
                      $("#isUsed_2").val("1");
           	      }else if($("#isUsed").html()=='禁用'){           		
                     $("#isUsed_2").val("0");
           	      }else{           		
                     $("#isUsed_2").val("");
           	       }
				if($("#menuType").html()=='全部'){
                    $("#menuType_2").val("0");
         	      }else if($("#menuType").html()=='岸基'){           		
                   $("#menuType_2").val("1");
         	      }else if($("#menuType").html()=='船端'){           		
                   $("#menuType_2").val("2");
         	      }else{           		
                   $("#menuType_2").val("");
         	       }					
				if($("#isPublic").html()=='全部'){
                    $("#isPublic_2").val("0");
         	      }else if($("#isPublic").html()=='前台'){           		
                   $("#isPublic_2").val("1");
         	      }else if($("#isPublic").html()=='后台'){           		
                   $("#isPublic_2").val("2");
         	      }else{           		
                   $("#isPublic_2").val("");
         	       }
				if($("#isAssignable").html()=='否'){
                    $("#isAssignable_2").val("0");
         	      }else if($("#isAssignable").html()=='是'){           		
                   $("#isAssignable_2").val("1");
         	      }else{           		
                   $("#isAssignable_2").val("");
         	       }				
				$("#menuEditWinTitle").html("修改菜单信息");
				$("#menuEditWin").modal("show");
			} else {
				Metronic.alert({
					message : "请选中一行进行操作!",
					type : 'warning',
					closeInSeconds : 5
				});
			}
		} else if (sOrU == 0) {
			if (menuId) {
//				$("#id_2").val(Math.uuid());
				//默认添加的菜单是前台可分配菜单
				$("#isPublic_2").val(1);
				$("#isAssignable_2").val(1);
				$("#isUsed_2").val("1");
				$("#parent_2").val($("#parent").html());
				$("#menuLevel_2").val($("#menuLevel").html());
				$("#menuEditWinTitle").html("添加同级菜单信息");
				$("#menuEditWin").modal("show");
			} else {
				Metronic.alert({
					message : "请选中一行进行操作!",
					type : 'warning',
					closeInSeconds : 5
				});
			}
		} else if (sOrU == 1) {
			if (menuId) {
//				$("#id_2").val(Math.uuid());
				//默认添加的菜单是前台可分配菜单
				$("#isPublic_2").val(1);
				$("#isAssignable_2").val(1);				
				$("#isUsed_2").val("1");
				$("#parent_2").val(menuId);
				$("#menuLevel_2").val(Number($("#menuLevel").html())+1);
				$("#menuEditWinTitle").html("添加下级菜单信息");
				$("#menuEditWin").modal("show");
			} else {
				Metronic.alert({
					message : "请选中一行进行操作!",
					type : 'warning',
					closeInSeconds : 5
				});
			}
		}
	} catch (e) {
		Metronic.alert({
			message : e.message,
			type : 'danger',
			closeInSeconds : 5
		});
	}
}

function lsGridSave() {
	if (sOrU == 2) {
		bootbox.setLocale("zh_CN");
		bootbox.confirm("确定要修改吗!", function(callback) {
			if (callback) {
				doUpdate();
			}
		});
	} else {
		doCreate();
	}
}

/**
 * 执行添加的操作
 */
function doCreate() {
	$("#menuForm").say({
		method : "post",
		url : baseUrl,
		callback : function(res) {
			if (res.success) {
//				var select_node_id = $("#id_2").val();
				$("#menuEditWin").modal("hide");
				$("#tree").jstree("deselect_node", $('#tree').jstree(true).get_selected()[0]);
				$("#tree").jstree("refresh");
//				// setInterval(alert(1), 5000 );
//				bootbox.setLocale("zh_CN");
//				bootbox.confirm("添加成功!", function(callback) {
//					if (callback) {
//						$("#tree").jstree("select_node", select_node_id);
//					} else {
//						$("#tree").jstree("select_node", select_node_id);
//					}
//				});
			}
		}
	});
}

/**
 * 执行修改的操作
 */
function doUpdate() {
	$("#menuForm").say({
		method : "PUT",
		url : baseUrl,
		callback : function(res) {
			if (res.success) {
				$("#menuEditWin").modal("hide");
				$("#tree").jstree("refresh");
			}
		}
	});
}

/**
 * 执行删除动作的操作
 */
function lsGridDelete() {
	try {
		var selectid = $('#tree').jstree(true).get_selected()[0];
		if (selectid) {
			$("#details").say({
				method : "GET",
				url : baseUrl + '/candelete/' + selectid,
				callback : function(res) {
					if (res == true) {
						bootbox.setLocale("zh_CN");
						bootbox.confirm("确定要删除吗!", function(callback) {
							if (callback) {
								$("#details").say({
									method : "DELETE",
									url : baseUrl + '/' + selectid,
									callback : function(res) {
										if (res.success) {
											$("#tree").jstree("refresh");
								    		$("#keyboard").html("");   
											$("#utime").html("");  										 
						    			 $("#parent").html("");
										 $("#text").html("");
										 $("#menuUrl").html("");
										 $("#menuLevel").html("");
										 $("#menuIcon").html("");
										 $("#menuBigIcon").html("");
										 $("#business").html("");
										 $("#menuHelp").html("");
										 $("#sortid").html("");
										 $("#memo").html("");
										 $("#isUsed").html("");
										 $("#menuType").html("");
										 $("#isPublic").html("");
										 $("#isAssignable").html("");
										}
									}
								});
							}
						});
					} else {
						Metronic.alert({
							message : "非叶节点的数据不能删除!",
							type : 'warning',
							closeInSeconds : 5
						})
					}
				}
			});
		} else {
			Metronic.alert({
				message : "请选择要删除的数据!",
				type : 'warning',
				closeInSeconds : 5
			});
		}
	} catch (e) {
		Metronic.alert({
			message : e.message,
			type : 'danger',
			closeInSeconds : 5
		});
	}
}
/**
 * 菜单 使用\禁用
 */
function isUse() {
	try {
		var isUse;
		if ($("#isUsed").html() == '使用') {
			isUse = 1;
		} else if ($("#isUsed").html() == '禁用') {
			isUse = 0;
		}
		var selectid = $('#tree').jstree(true).get_selected()[0];
		if (selectid) {
			$("#details").say({
				method : "PUT",
				url : baseUrl + '/isUsed/' + selectid + '/' + isUse,
				callback : function(res) {
					if (res.success) {
						$("#tree").jstree("refresh");
						if(isUsed == 1) {
							$("#stopused").html("<i class=\"fa fa-refresh\"></i>&nbsp;菜单使用");
						} else {
							$("#stopused").html("<i class=\"fa fa-refresh\"></i>&nbsp;菜单禁用");
						}
					}
				}
			});
		} else {
			Metronic.alert({
				message : "请选中一行进行操作!",
				type : 'warning',
				closeInSeconds : 5
			});
		}
	} catch (e) {
		Metronic.alert({
			message : e.message,
			type : 'danger',
			closeInSeconds : 5
		});
	}
}
// -----------------------功能按钮的增删改-----------------------------------

/**
 * 显示编辑窗口
 * @param saveOrUpdate	0 新增；1 修改
 */
var btnsOrU;
function btnShow(btnsaveOrUpdate) {
	btnsOrU = btnsaveOrUpdate;
	try {
		$("#btnForm").form('clear');
		$("#menuid").val($('#tree').jstree(true).get_selected()[0]);
		if(btnsOrU == 1) {
			var row = $("#btnTable").bootstrapTable('getSelections')[0];
			if (row) {
				$("#btnForm").form('load', row);
				$("#btnEditWinTitle").html("修改按钮信息");
				$("#btnEditWin").modal("show");
			} else {
				Metronic.alert({ message : "请选中一行进行操作!", type : 'warning', closeInSeconds : 5 });
			}
		} else if(btnsOrU == 0) {
			var menuid = $("#menuid").val();
			if(menuid){
				$("#btnEditWinTitle").html("添加按钮信息");
				$("#btnEditWin").modal("show");				
			}else{
				Metronic.alert({ message : "请选中一个菜单!", type : 'warning', closeInSeconds : 5 });
			}

		}
	} catch(e) {
		Metronic.alert({message:e.message,type:'danger',closeInSeconds:5});
	}
}
 
/**
 * 保存触发
 */
function btnSave() {
	bootbox.setLocale("zh_CN");
	bootbox.confirm("确定要保存吗!", function(callback) {
		if (callback) {
			execute(btnsOrU);
		}
	});
}
 
/**
 * 执行保存动作的操作
 */
function execute(btnsOrU) {
	var method = (btnsOrU == 0 ? "POST" : "PUT");
	$("#btnForm").say({
		method : method,
		url : btnUrl,
		callback : function(res) {
			if (res.success) {
				$("#btnEditWin").modal("hide");
				$('#btnTable').bootstrapTable('refresh');
			}
		}
	});
}
 
/**
 * 执行删除动作的操作
 */
function btnDelete() {
	try {
		var row = $("#btnTable").bootstrapTable('getSelections')[0];
		if (row) {
			bootbox.setLocale("zh_CN");
			bootbox.confirm("确定要删除吗!", function(callback) {
				if (callback) {
					$("#btnTable").say({
						method : "DELETE",
						url : btnUrl + '/' + row.id,
						callback : function(res) {
							if (res.success) {
								$('#btnTable').bootstrapTable('refresh');// 重新加载
							}
						}
					});
				}
			});
		} else {
			Metronic.alert({ message : "请选择要删除的数据!", type : 'warning', closeInSeconds : 5 });
		}
	} catch (e) {
		Metronic.alert({ message : e.message, type : 'danger', closeInSeconds : 5 });
	}
}
