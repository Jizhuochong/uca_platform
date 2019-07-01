<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>档案进度更新管理列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/archives/archives_borrow.js"></script>
</head>
<body>
<table id="list_data" cellspacing="0" cellpadding="0"></table>

<div id="toolbar">
	<div style="margin: 0 auto;padding: 2px;">
    	<a href="javascript:toAdd();" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a>
        <!-- <a href="javascript:toEdit();" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a>
        <a href="javascript:toRemove();" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a> 
        <a href="javascript:toImport();" class="easyui-linkbutton" data-options="plain:true">批量导入</a>-->
    </div>
    <div style="padding: 5px;">
           借阅单编号：<input id="num_jsid" type="text" class="textbox" style="width:120px">&nbsp;
           借阅者姓名：<input id="person_jsid" type="text" class="textbox" style="width:120px">&nbsp;
    <a href="javascript:toSearch();" class="easyui-button" data-options="iconCls:'icon-search',plain:true">查询</a>&nbsp;
   		<a href="javascript:clearForm();" class="easyui-button" data-options="iconCls:'icon-remove',plain:true">重置</a>
    </div>
    <!--  <div style="padding: 5px;">
           创建日期：<input id="sd" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;-&nbsp;
           <input id="ed" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;
      	<a href="javascript:toSearch();" class="easyui-button" data-options="iconCls:'icon-search',plain:true">查询</a>&nbsp;
   		<a href="javascript:clearForm();" class="easyui-button" data-options="iconCls:'icon-remove',plain:true">重置</a>
    </div>  -->
</div>

<div id="catalog_dialog" class="easyui-dialog" style="width:500px;height:300px;padding:10px" 
	data-options="closed:true,buttons:'#up-buttons',resizable:true,maximizable:true" >
	<form id="formName_catalog">
		<input type="hidden" id="id_jsid" />
        <table>
            <tr>
                <td width="160px" align="right">借阅单编号：</td>
                <td>
                	<input class="textbox" type="text" id="num_name_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">借阅者姓名：</td>
                <td>
                	<input class="textbox" type="text" id="project_preson_jsid" style="width:320px;" />
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
