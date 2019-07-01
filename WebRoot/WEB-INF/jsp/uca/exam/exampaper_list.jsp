<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>试卷库列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/exam/exampaper_list.js"></script>
</head>
<body>
<table id="list_data" cellspacing="0" cellpadding="0"></table>

<div id="toolbar">
	<div style="margin: 0 auto;padding: 2px;">
    	<a href="javascript:toAdd();" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a>
    </div>
    <div style="padding: 5px;">
           试卷名称：<input id="epName_search_jsid" type="text" class="textbox" style="width:120px">&nbsp;
           创建日期：<input id="sd" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;-&nbsp;
           <input id="ed" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;
      	<a href="javascript:toSearch();" class="easyui-button" data-options="iconCls:'icon-search',plain:true">查询</a>&nbsp;
   		<a href="javascript:clearForm();" class="easyui-button" data-options="iconCls:'icon-remove',plain:true">重置</a>
    </div>
</div>

<div id="obj_dialog" class="easyui-dialog" style="width:600px;height:240px;padding:10px" 
	data-options="closed:true,buttons:'#obj-buttons',resizable:true,maximizable:true" >
	<form id="formName_obj">
		<input type="hidden" id="ep_id_jsid" />
        <table>
            <tr>
                <td width="120px" align="right">试卷名称：</td>
                <td>
                	<input class="textbox" id="epName_jsid" type="text" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="120px" align="right">考试时限：</td>
                <td>
                	<input class="easyui-numberbox" id="examTime_jsid" type="text" style="width:320px;" data-options="required:true,min:30,max:120" missingMessage="时限范围：30-120，单位：分钟" />
				</td>
            </tr>
            <tr>
                <td width="120px" align="right">规定分数：</td>
                <td>
                	<input class="easyui-numberbox" id="passScore_jsid" type="text" style="width:320px;" data-options="required:true,min:1,max:100" missingMessage="规定分数范围：1-100" />
				</td>
            </tr>
            <tr>
                <td width="120px" align="right">学时：</td>
                <td>
                	<input class="easyui-numberbox" id="period_jsid" type="text" style="width:320px;" data-options="required:true,min:1,max:100" missingMessage="学时范围：1-100" />
				</td>
            </tr>
       	</table>
   	</form>	
</div>
<div id="obj-buttons">
	<div id="addButton">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="javascript:commit()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="javascript:$('#obj_dialog').dialog('close')">取消</a>
	</div>
	<div id="editButton">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="javascript:edit()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="javascript:$('#obj_dialog').dialog('close')">取消</a>
	</div>
</div>
</body>
</html>
