<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>答题库列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/exam/answer_list.js"></script>
</head>
<body>
<table id="list_data" cellspacing="0" cellpadding="0"></table>

<div id="toolbar">
	<div style="margin: 0 auto;padding: 5px;">
           试卷名称：<input id="epName_search_jsid" type="text" class="textbox" style="width:120px">&nbsp;
           答题人：<input id="userName_search_jsid" type="text" class="textbox" style="width:120px">&nbsp;
      	<a href="javascript:toSearch();" class="easyui-button" data-options="iconCls:'icon-search',plain:true">查询</a>&nbsp;
   		<a href="javascript:clearForm();" class="easyui-button" data-options="iconCls:'icon-remove',plain:true">重置</a>
    </div>
</div>

<div id="obj_dialog" class="easyui-dialog" style="width:800px;height:240px;padding:10px" 
	data-options="closed:true,buttons:'#obj-buttons',resizable:true,maximizable:true" >
	<form id="formName_obj">
		<input type="hidden" id="answerId_jsid" />
		<input type="hidden" id="eqType_jsid" />
        <table>
        	<tr>
                <td width="120px" align="right">答题人：</td>
                <td align="left">
                	<span id="answerUser_jsid"></span>
				</td>
            </tr>
        	<tr>
                <td width="120px" align="right">试卷名称：</td>
                <td align="left">
                	<span id="epName_jsid"></span>
				</td>
            </tr>
            <tr>
                <td width="120px" align="right">试题名称：</td>
                <td align="left">
                	<span id="eqName_jsid"></span>
				</td>
            </tr>
            <tr>
				<td width="120px" align="right">试题分数：</td>
                <td align="left">
                	<span id="eqScore_jsid"></span>
				</td>
            </tr>
            <tbody id="opan_tr_jsid" style="display:none;">
            <tr>
                <td width="120px" align="right">试题选项：</td>
                <td align="left">
                	<span id="eqOptions_jsid"></span>
				</td>
            </tr>
            <tr>
                <td width="120px" align="right">试题答案：</td>
                <td align="left">
                	<span id="eqAnswer_jsid"></span>
				</td>
            </tr>
            </tbody>
        	<tr>
				<td width="120px" align="right">答题人提交答案：</td>
                <td align="left">
                	<span id="answers_jsid"></span>
				</td>
            </tr>
            <tbody id="score_tr_jsid" style="display:none;">
            <tr>
                <td width="120px" align="right">打分分数：</td>
                <td align="left">
                	<input class="easyui-numberbox" id="score_jsid" type="text" style="width:320px;" />
				</td>
            </tr>
            </tbody>
       	</table>
   	</form>	
</div>
<div id="obj-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="javascript:commitScore();">打分</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="javascript:$('#obj_dialog').dialog('close');">取消</a>
</div>
</body>
</html>
