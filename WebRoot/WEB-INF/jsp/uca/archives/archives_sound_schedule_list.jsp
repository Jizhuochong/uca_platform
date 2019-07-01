<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>声像档案状态更新进度列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/archives/archives_sound_schedule_list.js"></script>
</head>
<body>
<table id="list_data" cellspacing="0" cellpadding="0"></table>

<div id="edit_dialog" class="easyui-dialog" style="width:500px;height:200px;padding:10px" 
	data-options="closed:true,buttons:'#schedule-buttons',resizable:true,maximizable:true" >
    <form id="formName_schedule">
		<input type="hidden" id="archives_id_jsid" />
        <table>
            <tr>
                <td width="120px" align="right">调卷单编号：</td>
                <td>
                	<input class="textbox" type="text" id="order_num_jsid" style="width:320px;" />
				</td>
            </tr>
       	</table>
   	</form>
</div>
<div id="schedule-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" onclick="javascript:editOrderNum();">修改</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" onclick="javascript:$('#edit_dialog').dialog('close');">取消</a>
</div>
</body>
</html>
