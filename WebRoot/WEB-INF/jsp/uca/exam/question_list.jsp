<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>考题库列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/exam/question_list.js"></script>
</head>
<body>
<table id="list_data" cellspacing="0" cellpadding="0"></table>

<div id="toolbar">
	<div style="margin: 0 auto;padding: 2px;">
    	<a href="javascript:toAdd();" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a>
    </div>
    <div style="padding: 5px;">
           考题名称：<input id="name_search_jsid" type="text" class="textbox" style="width:120px">&nbsp;
           考题分类：<select id="classify_search_jsid" class="easyui-combobox" style="width:240px;" editable="false"
           				data-options="valueField:'id',textField:'name',panelHeight:'auto',panelMaxHeight:120,
           					url:'${ctx}/examquestionclassify/fkComboData'">
					</select>&nbsp;
           考题类型：<select id="type_search_jsid" class="easyui-combobox" style="width:240px;" editable="false"
           				data-options="valueField:'id',textField:'text',panelHeight:'auto',panelMaxHeight:120,
                			data: [{
								id: '1',
								text: '多选题'
							},{
								id: '2',
								text: '单选题'
							},{
								id: '4',
								text: '问答题'
							}]">
					    <!-- <option value="3">填空题</option>
					    <option value="4">问答题</option> -->
					</select>&nbsp;
           创建日期：<input id="sd" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;-&nbsp;
           <input id="ed" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;
      	<a href="javascript:toSearch();" class="easyui-button" data-options="iconCls:'icon-search',plain:true">查询</a>&nbsp;
   		<a href="javascript:clearForm();" class="easyui-button" data-options="iconCls:'icon-remove',plain:true">重置</a>
    </div>
</div>

<div id="obj_dialog" class="easyui-dialog" style="width:1000px;height:240px;padding:10px" 
	data-options="closed:true,buttons:'#obj-buttons',resizable:true,maximizable:true" >
	<form id="formName_obj">
		<input type="hidden" id="question_id_jsid" />
        <table>
        	<tr>
                <td width="80px" align="right">考题分类：</td>
                <td>
                	<select id="classify_jsid" class="easyui-combobox" style="width:320px;" editable="false"
                		data-options="valueField:'id',textField:'name',panelHeight:'auto',panelMaxHeight:120,
                			url:'${ctx}/examquestionclassify/fkComboData'">
					</select>
				</td>
            </tr>
        	<tr>
                <td width="80px" align="right">考题类型：</td>
                <td>
                	<select id="type_jsid" class="easyui-combobox" style="width:320px;" editable="false"
                		data-options="valueField:'id',textField:'text',panelHeight:'auto',panelMaxHeight:120,
                			data: [{
								id: '1',
								text: '多选题'
							},{
								id: '2',
								text: '单选题'
							},{
								id: '4',
								text: '问答题'
							}],
							onSelect: function(rec){
								if(rec.id==4) {
									$('#opan_tr_jsid').hide();
								} else {
									$('#opan_tr_jsid').show();
								}
					        }">
					    <!-- <option value="3">填空题</option>
					    <option value="4">问答题</option> -->
					</select>
				</td>
            </tr>
            <tr>
                <td width="80px" align="right">考题名称：</td>
                <td>
                	<input class="textbox" type="text" id="name_jsid" style="width:320px;" />
				</td>
            </tr>
            <tbody id="opan_tr_jsid" style="display:none;">
            <tr>
                <td width="80px" align="right">考题选项：</td>
                <td>
                	<input class="textbox" type="text" id="options_jsid" style="width:320px;" />&nbsp;(各选项按照先后顺序以"|"分隔，例如：选项A|选项B|选项B|选项D)
				</td>
            </tr>
            <tr>
                <td width="80px" align="right">考题答案：</td>
                <td>
                	<input class="textbox" type="text" id="answer_jsid" style="width:320px;" />&nbsp;(各答案按照选项的先后顺序以"|"分隔，答案用1表示，非答案用0表示，例如：0|0|1|0)
				</td>
            </tr>
            </tbody>
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
