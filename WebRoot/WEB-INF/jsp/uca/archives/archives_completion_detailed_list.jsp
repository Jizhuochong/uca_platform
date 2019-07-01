<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>办理竣工档案登记详细表列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/archives/archives_completion_detailed_list.js"></script>
<script type="text/javascript">
/* 	function numChange(){
		alert("sssss");
		var numValue = $("#coversArea_jsid").val();
		var numValue1 = document.getEelementById("coversArea_jsid").val();
		alert(numValue);
		alert(numValue1);
	}  */
	
</script>
</head>
<body>
<input type="hidden" value="${registerId}" id="registerId_jsid"/>
<input type="hidden" value="${registerType}" id="registerType_jsid"/>
<table id="list_data" cellspacing="0" cellpadding="0"></table>

<div id="toolbar">
	<div style="margin: 0 auto;padding: 2px;">
    	<a href="javascript:toAdd();" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a>
    </div>
</div>

<div id="A_dialog" class="easyui-dialog" style="width:800px;height:400px;padding:10px" 
	data-options="closed:true,buttons:'#up-buttons',resizable:true,maximizable:true" >
	<form id="formName_A">
		<input type="hidden" id="id_jsid" />
        <table>
        	<c:if test="${registerType == 1}">
            <tr>
                <td width="160px" align="right">工程项目用途：</td>
                <td>
                	<input class="textbox" type="text" id="projectPurpose_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">结构种类：</td>
                <td>
                	<input class="textbox" type="text" id="categoryStructure_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">层数地上/地下：</td>
                <td>
                	<input class="textbox" type="text" id="floors_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">占地面积（平方米）：</td>
				<td>
                	<!-- <input class="easyui-numberbox" type="text" id="coversArea_jsid" style="width:320px;" precision="2" max="99999999.99" size="11" maxlength="11"/> -->
                	<!-- <input class="textbox" type="text" id="coversArea_111s" style="width:320px;" precision="2" max="99999999.99" size="11" maxlength="11" value="0.00" onfocus="if (value =='0.00'){value =''}" onblur="if (value ==''){value='0.00'}"/> -->
                	<input class="textbox" type="text" id="coversArea_jsid" style="width:320px;" placeholder="0.00" onfocus="if (placeholder =='0.00'){placeholder =''}" onblur="if (placeholder ==''){placeholder='0.00'}" onkeyup="this.value=this.value.match(/\d+(\.\d{0,2})?/)?this.value.match(/\d+(\.\d{0,2})?/)[0]:''"/>
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">建筑面积（平方米）：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="constructionArea_jsid" style="width:320px;" precision="2" max="99999999.99" size="11" maxlength="11" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">栋楼：</td>
                <td>
                	<input class="textbox" type="text" id="buildings_jsid" style="width:320px;" />
				</td>
            </tr>
            </c:if>
            <c:if test="${registerType == 2}">
            <tr>
                <td width="160px" align="right">工程内容：</td>
                <td>
                	<input class="textbox" type="text" id="projectContent_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">数量：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="projectQuantity_jsid" style="width:320px;" />
				</td>
            </tr>
            </c:if>
            <c:if test="${registerType == 3}">
            <tr>
                <td width="160px" align="right">管径（断面）：</td>
                <td>
                	<input class="textbox" type="text" id="diameterSection_jsid" style="width:320px;" />
				</td>
            </tr>
            <tr>
                <td width="160px" align="right">长度（米）：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="diameterLength_jsid" style="width:320px;" precision="2" max="99999999.99" size="11" maxlength="11" />
				</td>
            </tr>
            </c:if>
            
<%--             <c:if test="${registerType == 1 || registerType == 2}"> --%>
            <tr>
                <td width="160px" align="right">总投资(元)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="totalInvestment_jsid" style="width:320px;" precision="2" max="99999999.99" size="11" maxlength="11" />
				</td>
            </tr>
<%--             </c:if> --%>
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
