/**
 * 将bootstraptable转换为可编辑表格
 */

/**
 * 转换显示元素是否为可编辑状态
 */
var tableFieldEdit_formatter = function(value,row,index,tableId){
	if($("#" + tableId + "_bootstrap_table_edit").val() == "") {
		return value;
	}
	// bootstrapTable操作对象
	var bootstrapTableOptions = $('#' + tableId).bootstrapTable('getOptions');
	// 可编辑表格信息
	var columnConfigJson = JSON.parse($("#" + tableId + "_bootstrap_table_edit").val());
	// 设置可允许编辑的列 
	var editFlag = false;
	var validate = "";
	var i=0;
	var tableColumns = bootstrapTableOptions.columns[0];
	if(columnConfigJson.editColumn) {
		for(i=0;i<columnConfigJson.editColumn.length;i++) {
			var columnJson = columnConfigJson.editColumn[i];
			if(tableColumns[this.fieldIndex].field == columnJson.columnId) {
				validate = columnJson.validate;
				editFlag = true;
				break;
			}
		}
	}
	
	// 若当前数据为新建数据，开放全部编辑权限
	if(eval("row."+columnConfigJson.createColumnId) == "") { 
		for(i=0;i<columnConfigJson.createColumn.length;i++) {
			if(tableColumns[this.fieldIndex].field == columnConfigJson.createColumn[i].columnId) {
				validate = columnConfigJson.createColumn[i].validate;
				editFlag = true;
				break;
			}
		}
	}
 	
	if(editFlag) {
		var convertHtml = '<input class="form-control validate['+validate+']" onChange="bootstrapTable_edit_field_change(this,\''+tableId+'\')" value="'+value+'" style="height:30px;text-align:center"/>';
		return convertHtml;
	} else {
		return value;
	}
}

/**
 * 表单可编辑域更新后，更新表格真实数据
 * @param obj
 */
function bootstrapTable_edit_field_change(obj, tableId) {
	var objValue = obj.value;
	
	var body_td = obj.parentNode;
	var body_tr = body_td.parentNode;
	var body_td_cell_index = -1;	// 存在列的位置
	var i = 0;
	for(i=0;i<body_tr.childNodes.length;i++) {
		if(body_tr.childNodes[i] == body_td) {
			body_td_cell_index = i;
			break;
		}
	}
	
	var body_td_row_index = -1;	// 存在行号的位置
	var body = body_tr.parentNode;
	for(i=0;i<body.childNodes.length;i++) {
		if(body.childNodes[i] == body_tr) {
			body_td_row_index = i;
			break;
		}
	}
	
	var table = body_td.parentNode.parentNode.parentNode;
//	var head_th_list = body_td.parentNode.parentNode.parentNode.childNodes;
	var head_th = null;
	for(i=0;i<table.childNodes.length;i++) {
		if(table.childNodes[i].nodeName == "THEAD") {
			head_th = table.childNodes[i].childNodes[0].childNodes[body_td_cell_index];
			break;
		}
	}
	if(head_th == null) {
		Metronic.alert({ message : "bootstrapTable转可编辑表格出错，数据回写失败。", type : 'danger', closeInSeconds : 5 });
		return;
	}
	var table_cell_data_field =  head_th.getAttribute("data-field");
	var tableData = $('#' + tableId).bootstrapTable('getData');
	 
 	// 获取表格数据方法
//	var value = eval("tableData[0]."+table_cell_data_field);
	// 表格赋值方法
	eval("tableData["+body_td_row_index+"]."+table_cell_data_field+" = \""+objValue+"\"");
	
}
	
