<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>竣工档案登记表列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/archives/archives_completion_register_list_all.js"></script>
</head>
<body>
<table id="list_data" cellspacing="0" cellpadding="0"></table>

<div id="toolbar">
    <div style="padding: 5px;">
           登记表类型：<select id="type_search_jsid" class="easyui-combobox" style="width:240px;" editable="false" 
           				data-options="valueField:'id',textField:'text',panelHeight:'auto',panelMaxHeight:120,
                			data: [{
								id: '1',
								text: '建筑工程'
							},{
								id: '2',
								text: '市政公用'
							},{
								id: '3',
								text: '市政管线'
							}]">
					</select>&nbsp;
           填报日期：<input id="sd" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;-&nbsp;
           <input id="ed" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;
      	<a href="javascript:toSearch();" class="easyui-button" data-options="iconCls:'icon-search',plain:true">查询</a>&nbsp;
   		<a href="javascript:clearForm();" class="easyui-button" data-options="iconCls:'icon-remove',plain:true">重置</a>
    </div>
</div>
</body>
</html>
