<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>办理竣工档案登记表列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/archives/archives_completion_register_list.js"></script>
</head>
<body>
<table id="list_data" cellspacing="0" cellpadding="0"></table>

<div id="toolbar">
	<div style="margin: 0 auto;padding: 2px;">
    	<a href="javascript:toAdd();" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">办理竣工档案登记表填报</a>
    </div>
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

<div id="A_dialog" class="easyui-dialog" style="width:800px;height:500px;padding:10px" 
	data-options="closed:true,buttons:'#up-buttons',resizable:true,maximizable:true" >
	<form id="formName_A">
		<input type="hidden" id="id_jsid" />
        <table>
        	<tr>
                <td width="160px" align="right"><span style="color:red;">*</span>登记表类型：</td>
                <td>
                	<select id="type_jsid" class="easyui-combobox" style="width:320px;" editable="false" 
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
							}],
                			onSelect: function(rec){
                				if(rec.id == 1) {
　　								document.getElementById('buildWord_hidden').style.display='';
                				} else {
                					document.getElementById('buildWord_hidden').style.display='none';
                				}
                				if(rec.id == 2 || rec.id == 3) {
                					document.getElementById('measureUnit_hidden').style.display='';
                					document.getElementById('projectType_hidden').style.display='';
                				} else {
                					document.getElementById('measureUnit_hidden').style.display='none';
                					document.getElementById('projectType_hidden').style.display='none';
                				}
                				if(rec.id == 3) {
                					document.getElementById('totalInvestment_hidden').style.display='';
                				} else {
                					document.getElementById('totalInvestment_hidden').style.display='none';
                				}
		        			}">
					</select>
				</td>
            </tr>
            <tr>
                <td width="160px" align="right"><span style="color:red;">*</span>档字：</td>
                <td>
                	<input class="textbox" type="text" id="archiveWord_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr id="buildWord_hidden" style="display: none;">
                <td width="160px" align="right"><span style="color:red;">*</span>建字：</td>
                <td>
                	<input class="textbox" type="text" id="buildWord_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right"><span style="color:red;">*</span>建设单位：</td>
                <td>
                	<input class="textbox" type="text" id="buildUnit_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right"><span style="color:red;">*</span>通讯地址：</td>
                <td>
                	<input class="textbox" type="text" id="mailingAddress_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right"><span style="color:red;">*</span>邮政编码：</td>
                <td>
                	<input class="textbox" type="text" id="zipCode_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right"><span style="color:red;">*</span>登记人：</td>
                <td>
                	<input class="textbox" type="text" id="registerPerson_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right"><span style="color:red;">*</span>登记日期：</td>
                <td>
                	<input class="easyui-datebox" type="text" id="registerDate_jsid" style="width:120px" editable="false">
				</td>
            </tr>
            <tr>
                <td width="160px" align="right"><span style="color:red;">*</span>电话：</td>
                <td>
                	<input class="textbox" type="text" id="phone_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right"><span style="color:red;">*</span>工程名称：</td>
                <td>
                	<input class="textbox" type="text" id="projectName_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right"><span style="color:red;">*</span>工程地点：</td>
                <td>
                	<input class="textbox" type="text" id="projectLocation_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right"><span style="color:red;">*</span>设计单位：</td>
                <td>
                	<input class="textbox" type="text" id="designUnit_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">施工单位：</td>
                <td>
                	<input class="textbox" type="text" id="constructionUnit_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr id="measureUnit_hidden" style="display: none;">
                <td width="160px" align="right"><span style="color:red;">*</span>测量单位：</td>
                <td>
                	<input class="textbox" type="text" id="measureUnit_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr id="projectType_hidden" style="display: none;">
                <td width="160px" align="right"><span style="color:red;">*</span>工程类型：</td>
                <td>
                	<input class="textbox" type="text" id="projectType_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right"><span style="color:red;">*</span>开工：</td>
                <td>
                	<input class="easyui-datebox" type="text" id="startDate_jsid" style="width:120px" editable="false">
				</td>
            </tr>
            <tr>
                <td width="160px" align="right"><span style="color:red;">*</span>竣工：</td>
                <td>
                	<input class="easyui-datebox" type="text" id="completionDate_jsid" style="width:120px" editable="false">
				</td>
            </tr>
            <tr id="totalInvestment_hidden" style="display: none;">
                <td width="160px" align="right"><span style="color:red;">*</span>总投资(元)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="totalInvestment_jsid" style="width:320px;" precision="2" max="99999999.99" size="11" maxlength="11" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">备注：</td>
                <td>
                	<textarea cols="4" rows="3" id="remark_jsid" style="width:320px;"></textarea>
				</td>
            </tr>
            <tr>
                <td width="160px" align="right"><span style="color:red;">*</span>档案馆经办人：</td>
                <td>
                	<input class="textbox" type="text" id="archivesManagers_jsid" style="width:320px;" />
				</td>
            </tr>
       	</table>
   	</form>	
</div>
<div id="up-buttons">
	<div id="addButton">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="javascript:commit()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="javascript:$('#A_dialog').dialog('close')">取消</a>
	</div>
	<div id="editButton">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="javascript:edit()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="javascript:$('#A_dialog').dialog('close')">取消</a>
	</div>
</div>
</body>
</html>
