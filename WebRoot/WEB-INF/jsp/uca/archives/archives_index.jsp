<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>工程档案列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/archives/archives_index.js"></script>
</head>
<body>
<input type="hidden" value="${type}"  name="dType" id="dType"/>
<!-- 搜索 -->
<div id="select_bar" style="height:25px;padding-top:5px">
   	<div style="margin: 0 auto;padding: 2px;">
   		<a href="javascript:getObjByParam('',${type});" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">
   			<c:if test="${type == 1}">上传档案</c:if>
   			<c:if test="${type == 2}">上传数据文件</c:if>
   		</a>
          <%-- <input type="button" onclick="javascript:getObjByParam('',${type});" value="上传档案"/> --%>
    </div>
    <!-- <form id="fmsearch" method="post" novalidate>
          <span>
		<label>标题:</label>
		<input name="bname" id="bname" class="easyui-validatebox">
		</span>
		<span>
			<label>内容:</label>
			<input name="bcontent" id="bcontent" class="easyui-validatebox" >
		</span>
		<span>
		  	<input type="button" name="select" onclick="javascript:toSearch();" value="查询" id="select" >
		  	<input type="reset" name="reset" value="重置" id="reset" >
		</span>
	</form> -->
</div>
<table id="list_data" cellspacing="0" cellpadding="0">
</table>

<!-- 档案编辑-->
<div id="edit_dialog" class="easyui-dialog" maximizable="true" resizable="true" style="width:580px;height:300px;padding:0px" modal="true" buttons="#up-buttons" closed="true" >
   <form id="form_edit" method="post" style="margin:50px 10px">
        <input type="hidden" id="archivesId" name="archivesId" value=""/>
 		<input type="hidden" id="checkStatus" name="checkStatus" value=""/>
 		<input type="hidden" id="type" name="type" value=""/>
        <table>
			<tr>
               	<td width="90px" align="left">电子档案：</td>
                <td <%-- <c:if test="${type == 1}"> --%>colspan="3"<%-- </c:if> --%>>
					<a href="" id="fileDown" name="fileDown" target="_blank"></a><img src="${ctx}/resources/images/loading.gif" id="loading" style="display:none;"/>
					<c:if test="${type == 1}"><input type="file" class="span315" id="file" name="file" accept="text/xml,application/xml"/><input type="button" value="上传" onclick="ajaxFileUpload()"/></c:if>
					<c:if test="${type == 2}"><input type="file" class="span315" id="file" name="file"/><input type="button" value="上传" onclick="ajaxFileUpload()"/></c:if>
					<input type="hidden" value="" id="fileUrl" name="fileUrl">
					<input type="hidden" value="" id="sourceFileName" name="sourceFileName">
				</td>
            </tr>
            <%-- <c:if test="${type == 1}"> --%>
            <tr>
               	<td width="90px" align="left">工程名称：</td>
                <td colspan="3">
					<input type="text" value="" id="projectName" name="projectName">
				</td>
            </tr>
            <%-- </c:if> --%>
            <tr>
            	<%-- <c:if test="${type == 1}"> --%>
            	<td width="90px" align="left">机构：</td>
                <td>
                	<select class="easyui-combobox" id="orgId" name="orgId" style="width:180px;">
                		<option value="-2" selected="selected">请选择部门---</option>
			          	<c:forEach items="${orgList}" var="org" varStatus="urstatus">
					    	<option value ="${org.orgId}">${ org.orgName }</option>
					  	</c:forEach>
                	</select>
               	</td>
               	<%-- </c:if> --%>
                <td width="90px" align="left">经办人：</td>
                <td>
                	<input class="easyui-combobox" id="handingPersonId" name="handingPersonId" data-options="valueField:'userId',textField:'userName'" style="width:180px;">
               	</td>
            </tr>
            <tr>
                <td colspan="2" align="center">&nbsp;</td>
            </tr>
		</table>
   </form>
</div>

<div id="up-buttons">
	<%-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:commit(0)">保存</a> --%>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:commit(1)">提交审核</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#edit_dialog').dialog('close')">取消</a>
</div>
<form  method="post" id="queryForm"></form>
</body>
</html>
