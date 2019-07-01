<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>会议室列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/meeting/meeting_index.js"></script>
</head>
<body>
<!-- 搜索 -->
<div id="select_bar">
     <div style="margin: 0 auto;padding: 2px;">
     	<a href="javascript:getObjByParam('');" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加会议室</a>
     </div>
     <div style="padding: 5px;">
        <form id="fmsearch" method="post" novalidate>
            <span>
				<label>会议室名称:</label>
				<input name="dName" id="dName" class="easyui-validatebox">
			</span>
			<span>
				<label>可用状态:</label>
				<select class="easyui-combobox" id="dStatus" name="dStatus" style="width:180px;">
               		<option value ="">请选择---</option>
               		<option value ="1">可用</option>
               		<option value ="2">不可用</option>
               	</select>
			</span>
			<span>
			  	<input type="button" name="select" onclick="javascript:toSearch();" value="查询" id="select" >
			  	<input type="reset" name="reset" value="重置" id="reset" >
			</span>
		</form>
	</div>
</div>
<table id="list_data" cellspacing="0" cellpadding="0">
</table>

<!-- 会议室编辑-->
<div id="edit_dialog" class="easyui-dialog" maximizable="true" resizable="true" style="width:580px;height:300px;padding:0px" modal="true" buttons="#up-buttons" closed="true" >
   <form id="form_edit" method="post" style="margin:50px 10px">
        <input type="hidden" id="mrId" name="mrId" value=""/>
        <table>
			<tr>
               	<td width="90px" align="left">会议室名称：</td>
                <td>
                	<input type="text" value="" id="name" name="name"/>
					
				</td>
            </tr>
            <tr>
               	<td width="90px" align="left">会议室地址：</td>
                <td>
					<input type="text" value="" id="address" name="address" size="60"/>
				</td>
            </tr>
            <tr>
                <td width="90px" align="left">可容纳人数：</td>
                <td>
                	<input type="text" value="" id="peopleCount" name="peopleCount"/>
               	</td>
            </tr>
            <tr>
                <td width="90px" align="left">可用状态：</td>
                <td>
                	<select class="easyui-combobox" id="status" name="status" style="width:180px;">
                		<option value ="1">可用</option>
                		<option value ="2">不可用</option>
                	</select>
               	</td>
            </tr>
            <tr>
                <td colspan="2" align="center">&nbsp;</td>
            </tr>
		</table>
   </form>
</div>

<div id="up-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:commit()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#edit_dialog').dialog('close')">取消</a>
</div>
<form  method="post" id="queryForm"></form>
</body>
</html>
