<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>住宅类项目级档案目录列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/catalog/catalog_list.js"></script>
</head>
<body>
<table id="list_data" cellspacing="0" cellpadding="0"></table>

<div id="toolbar">
	<div style="margin: 0 auto;padding: 2px;">
    	<a href="javascript:toAdd();" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a>
        <!-- <a href="javascript:toEdit();" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a>
        <a href="javascript:toRemove();" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a> -->
        <a href="javascript:toImport();" class="easyui-linkbutton" data-options="plain:true">批量导入</a>
    </div>
    <div style="padding: 5px;">
           工程名称：<input id="projectName_jsid" type="text" class="textbox" style="width:120px">&nbsp;
           建设单位：<input id="devOrg_jsid" type="text" class="textbox" style="width:120px">&nbsp;
           工程地点：<input id="projectAddress_jsid" type="text" class="textbox" style="width:120px">&nbsp;
           规划许可证号：<input id="archivesNum_jsid" type="text" class="textbox" style="width:120px">
    </div>
    <div style="padding: 5px;">
           创建日期：<input id="sd" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;-&nbsp;
           <input id="ed" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;
      	<a href="javascript:toSearch();" class="easyui-button" data-options="iconCls:'icon-search',plain:true">查询</a>&nbsp;
   		<a href="javascript:clearForm();" class="easyui-button" data-options="iconCls:'icon-remove',plain:true">重置</a>
    </div>
</div>

<div id="catalog_dialog" class="easyui-dialog" style="width:500px;height:300px;padding:10px" 
	data-options="closed:true,buttons:'#up-buttons',resizable:true,maximizable:true" >
	<form id="formName_catalog">
		<input type="hidden" id="catalog_id_jsid" />
        <table>
            <tr>
                <td width="160px" align="right">工程名称：</td>
                <td>
                	<input class="textbox" type="text" id="project_name_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">建设单位：</td>
                <td>
                	<input class="textbox" type="text" id="project_address_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">工程地点：</td>
                <td>
                	<input class="textbox" type="text" id="dev_org_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">规划许可证号：</td>
                <td>
                	<input class="textbox" type="text" id="archives_num_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">设计单位：</td>
                <td>
                	<input class="textbox" type="text" id="plan_per_num_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">施工单位：</td>
                <td>
                	<input class="textbox" type="text" id="construction_unit_jsid" style="width:320px;" />
				</td>
            </tr>
       	</table>
   	</form>	
</div>
<div id="up-buttons">
	<div id="addButton">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="javascript:commit()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="javascript:$('#catalog_dialog').dialog('close')">取消</a>
	</div>
	<div id="editButton">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="javascript:edit()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="javascript:$('#catalog_dialog').dialog('close')">取消</a>
	</div>
</div>

<div id="import_dialog" class="easyui-dialog" style="width:500px;height:200px;padding:10px" 
	data-options="closed:true,buttons:'#import-buttons',resizable:true,maximizable:true" >
	<table>
	    <tr>
	        <td width="160px" align="right">选择批量导入数据文件：</td>
	        <td>
	        	<form id="materialImport" action="${ctx}/catalog/importExcel" enctype="multipart/form-data">
	            	<input type="file" name="fileItem" id="fileItem" multiple="multiple" /><!-- accept="application/msexcel" -->
				</form>
			</td>
	    </tr>
    </table>
</div>
<div id="import-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" onclick="javascript:importExcel();">开始导入</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" onclick="javascript:$('#import_dialog').dialog('close');">取消</a>
</div>
</body>
</html>
