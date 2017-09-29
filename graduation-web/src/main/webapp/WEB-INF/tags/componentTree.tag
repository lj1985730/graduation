<!-- 自定义设备分类树-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ taglib prefix="ls" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 参数 -->
<%@ attribute name="id" required="false" rtexprvalue="false" type="java.lang.String" description="唯一标识" %>
<%@ attribute name="onChange" required="false" rtexprvalue="false" type="java.lang.String" description="返回选中节点对象方法参数为(e, data)" %>
<%@ attribute name="inBox" required="false" rtexprvalue="false" type="java.lang.Boolean" description="外层是否封装框体" %>

<c:if test="${inBox != null && inBox}">
<div class="portlet box green">
	<div class="portlet-title">
		<div class="caption">
			<i class="fa fa-th-list"></i>&nbsp;设备分类
		</div>
		<div class="actions">
			<ls:fullscreen />
		</div>
	</div>
	<div class="portlet-body">
		<div>
</c:if>
			<input type="hidden" id="tag_compCategory_searchText" class="form-control" placeholder="搜索..." onkeyup="tag_compCategory_doSearch();" />
			<div id="${id}" style="overflow-x: auto;"></div>
<c:if test="${inBox != null && inBox}">
  		</div>
	</div>
</div>
</c:if>
<link type="text/css" rel="stylesheet" href="css/tree/style.min.css" />
<script type="text/javascript" src="scripts/jstree.min.js"></script>
<script type="text/javascript">

	/**
	 * 初始加载
	 */
	$(function() {
		tag_compCategory_load();
	});

	/**
	 * 查询树
	 */
	function tag_compCategory_doSearch() {		
		$('#${id}').jstree(true).search($('#tag_compCategory_searchText').val());
	}
	/**
	 * 初始加载树
	 */
	function tag_compCategory_load() {
		$("#${id}").jstree({
			plugins : [ "state","sort", "search", 'types', 'wholerow'],
			core : {
				data : {
					type : 'POST',
					url : rootUrl + '/ship/component/shipComponent/treelist',	// 请求地址
//					data : {__token__ : $("#__token__").val()},
					data : function (node) {
						return { __token__ : $("#__token__").val(),
							  id: node.id,
							  shipId : $('#selShip').val()
							};
					},
					success : function(data) {},
					error : function() {}
				},
				themes : { 
                	dots: false,
                	icons: !0,
                	stripes: true,
					//variant : "small",
					responsive : true
				}
			},
			types : {			
			    "component" : {
				      icon: 'images/icon/orange_settings.png'
				    },
			    "voidComponent" : {
				      icon: 'images/icon/grey_settings.png'
				    },				    
			    "category" : {
				      icon: 'images/icon/folder.png'
				    }
			},
			sort : function(a, b) {
				a = this.get_node(a);
				b = this.get_node(b);
				return (a.original.sort > b.original.sort ? 1 : -1);
			}
		}).on("changed.jstree", eval('${onChange}')).on('load_node.jstree', function(e, obj) {
			if(obj.node && obj.node.children) {	//默认打开第一层
				//obj.instance.open_node(obj.node.children[0]);
			}
		}); 
	}
</script>