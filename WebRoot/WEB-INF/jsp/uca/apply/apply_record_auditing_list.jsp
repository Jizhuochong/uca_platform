<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>声像档案预约审核列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/apply/apply_record_auditing_list.js"></script>
</head>
<body>
<table id="list_data" cellspacing="0" cellpadding="0"></table>

<div id="pass_dialog" class="easyui-dialog" style="width:500px;height:200px;padding:10px" 
	data-options="closed:true,buttons:'#pass-buttons',resizable:true,maximizable:true" >
    <form id="formName_pass">
		<input type="hidden" id="pass_aq_id_jsid" />
        <table>
            <tr>
                <td width="120px" align="right">来馆查询时间：</td>
                <td>
                	<textarea cols="4" rows="3" id="auditing_pass_txt_jsid" style="width:300px;"></textarea>
				</td>
            </tr>
       	</table>
   	</form>
</div>
<div id="pass-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" onclick="javascript:auditingPass();">审核通过</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" onclick="javascript:$('#pass_dialog').dialog('close');">取消</a>
</div>

<div id="unpass_dialog" class="easyui-dialog" style="width:500px;height:200px;padding:10px" 
	data-options="closed:true,buttons:'#unpass-buttons',resizable:true,maximizable:true" >
    <form id="formName_unpass">
		<input type="hidden" id="unpass_aq_id_jsid" />
        <table>
            <tr>
                <td width="120px" align="right">审核不通过原因：</td>
                <td>
                	<textarea cols="4" rows="3" id="auditing_unpass_txt_jsid" style="width:300px;"></textarea>
				</td>
            </tr>
       	</table>
   	</form>
</div>
<div id="unpass-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" onclick="javascript:auditingUnPass();">审核不通过</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" onclick="javascript:$('#unpass_dialog').dialog('close');">取消</a>
</div>
</body>
</html>
