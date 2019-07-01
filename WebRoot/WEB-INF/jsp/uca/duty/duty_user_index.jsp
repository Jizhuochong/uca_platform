<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>值班表查看</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/duty/duty_user_index.js"></script>
</head>
<body>
<div id="toolbar">
	<div style="margin: 0 auto;padding: 2px;">
    	<a href="javascript:nowprint(1);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">打印</a>
    </div>
    <div style="padding: 5px;">
           值班日期：<input id="qDate" type="text" class="easyui-datebox" style="width:120px" editable="false">
      	<a href="javascript:toSearch();" class="easyui-button" data-options="iconCls:'icon-search',plain:true">查询</a>&nbsp;
   		<a href="javascript:clearForm();" class="easyui-button" data-options="iconCls:'icon-remove',plain:true">重置</a>
    </div>
</div>
<!--startprint1-->
<div align="center"><span style="font-size:20px">北京市城建档案馆</span></div>
<div style="font-size:25px;" align="center"><span id="dutyYear"></span>年<span id="dutyMonth"></span>月值班表</div>
<div align="center">
<table id="list_data" cellspacing="2" cellpadding="2" border="1" width="800" style="font-size:18px">
</table>
<table id="show_notes" cellspacing="2" cellpadding="2" width="800" style="font-size:16px">
	<tr>
		<td colspan="3">&nbsp;</td>
	</tr>
	<tr align="left">
		<td>注&nbsp;</td><td colspan="2">1、值班人员要认真履行职责，按时交接班，不准擅离职守。</td>
	</tr>
	<tr align="left">
		<td>&nbsp;&nbsp;</td><td colspan="2">2、值班人员要加强巡视，发现问题要及时向带班领导汇报。</td>
	</tr>
	<tr align="left">
		<td>&nbsp;&nbsp;</td><td colspan="2">3、加强信息传递，做好上传下达工作。</td>
	</tr>
	<tr align="left">
		<td>&nbsp;&nbsp;</td><td colspan="2">4、值班人员换班须及时通知保卫科。</td>
	</tr>
	<tr align="left">
		<td>&nbsp;&nbsp;</td><td>5、委值班室电话：68020689</td><td>委保卫处电话：68021381</td>
	</tr>
	<tr align="left">
		<td width="5%">&nbsp;&nbsp;</td><td width="35%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;馆值班室电话：68038621</td><td width="60%">委保卫处电话：68022725</td>
	</tr>
</table>
</div>
<!--endprint1-->
</body>
</html>
