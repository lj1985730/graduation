<!-- 自定义查询按钮-->
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>

<!-- 查询窗口id -->
<%@ attribute name="win" required="true" rtexprvalue="true" type="java.lang.String" description="查询窗主键" %>
<%@ attribute name="id" required="false" rtexprvalue="true" type="java.lang.String" description="绑定id属性" %>
 
<button type="button" class="btn yellow" onClick="$('#${win}').toggle();" id="${id}"><i class="fa fa-search"></i>&nbsp;高级查询</button>
