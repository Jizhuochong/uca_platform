<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>声像档案待审核列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/archives/archives_sound_auditing_list.js"></script>
</head>
<body>
<table id="list_data" cellspacing="0" cellpadding="0"></table>

<div id="auditing_dialog" class="easyui-dialog" style="width:600px;height:200px;padding:10px" 
	data-options="closed:true,buttons:'#auditing-buttons',resizable:true,maximizable:true" >
    <form id="formName_auditing" enctype="multipart/form-data">
		<input type="hidden" id="archives_id_jsid" />
        <table>
            <tr>
                <td width="220px" align="right">审核不通过原因：</td>
                <td>
                	<textarea cols="4" rows="3" id="auditing_unpass_txt_jsid" style="width:300px;"></textarea>
				</td>
            </tr>
            <tr>
                <td width="220px" align="right">上传经办人协助修改文件：</td>
                <td>
                	<a href="" id="fileDown" name="fileDown" target="_blank"></a>
                	<img src="${ctx}/resources/images/loading.gif" id="loading" style="display:none;"/>
                	<input type="file" class="span315" id="file" name="file"/>
                	<input type="button" value="上传" onclick="ajaxFileUpload()"/>
					<input type="hidden" value="" id="fileUrl" name="fileUrl">
					<input type="hidden" value="" id="sourceFileName" name="sourceFileName">
				</td>
            </tr>
       	</table>
   	</form>
</div>
<div id="auditing-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" onclick="javascript:auditingUnPass();">审核不通过</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" onclick="javascript:$('#auditing_dialog').dialog('close');">取消</a>
</div>
</body>
</html>
