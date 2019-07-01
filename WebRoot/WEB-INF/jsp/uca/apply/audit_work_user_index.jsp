<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>实际审核工作量统计表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/apply/audit_work_user_index.js"></script>
</head>
<body>
<div id="toolbar">
	<div style="margin: 0 auto;padding: 2px;">
    	<a href="javascript:nowprint(1);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">打印</a>
    </div>
    <div style="padding: 5px;">
    	办理人员姓名：<select id="user_id_select_jsid" class="easyui-combobox" style="width:120px;" editable="false" data-options="valueField:'id',textField:'name',panelHeight:'auto',panelMaxHeight:120,url:'${ctx}/auditWorkStatistics/fkComboDataByOrg/18'"></select>
           统计日期时段：<input id="sd" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;-&nbsp;
           <input id="ed" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;&nbsp;
      	<a href="javascript:toSearch();" class="easyui-button" data-options="iconCls:'icon-search',plain:true">查询</a>&nbsp;
   		<a href="javascript:clearForm();" class="easyui-button" data-options="iconCls:'icon-remove',plain:true">重置</a>
    </div>
</div>
<div style="padding-top: 20px;"></div>
<!--startprint1-->
<div align="center"><span style="font-size:20px">北京市城建档案馆</span></div>
<div style="font-size:24px;" align="center"><span id="dutysd_ed"></span>&nbsp;实际审核工作量统计表</div>
<div style="font-size:14px;" align="center">[合计]&nbsp;工程数量:<span id="totalCount"></span>&nbsp;&nbsp;&nbsp;&nbsp;相片总数量（张）:<span id="allPicNum"></span>&nbsp;&nbsp;&nbsp;&nbsp;视频总时长（分钟）:<span id="allMovMinute"></span></div>
<div align="center">
<table id="list_data" cellspacing="2" cellpadding="2" border="1" width="900" style="font-size:14px">
</table>
</div>
<!--endprint1-->
</body>
</html>
