<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>通知通告列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/notice/show_notice_index.js"></script>
</head>
<body>
<input type="hidden" value="${type}"  name="dType" id="dType"/>
<!-- 搜索 -->
<div id="select_bar">
	 <c:if test="${type == 1}">
     <div style="margin: 0 auto;padding: 2px;">
     	<a href="javascript:getObjByParam('');" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加通知通告</a>
        <%--     <input type="button" onclick="javascript:getObjByParam('',${type});" value="添加通知通告"/> --%>
     </div>
     </c:if>
     <div style="padding: 5px;">
        <form id="fmsearch" method="post" novalidate>
            <span>
				<label>标题:</label>
				<input name="dTitle" id="dTitle" class="easyui-validatebox">
			</span>
			<span>
				<label>内容:</label>
				<input name="dContent" id="dContent" class="easyui-validatebox" >
			</span>
			<span>
			  	<input type="button" name="select" onclick="javascript:toSearch();" value="查询" id="select" >
			  	<input type="reset" name="reset" value="重置" id="reset" >
			</span>
		</form>
	</div>
</div>
<table id="list_data" cellspacing="0" cellpadding="0">
</table>

<!-- 通知通告编辑-->
<div id="edit_dialog" class="easyui-dialog" maximizable="true" resizable="true" style="width:580px;height:320px;padding:0px" modal="true" buttons="#up-buttons" closed="true" >
   <form id="form_edit" method="post" style="margin:20px 10px">
        <input type="hidden" id="noticeId" name="noticeId" value=""/>
        <input type="hidden" id="releaseObj" name="releaseObj" value="1"/>
        <table>
			<tr>
               	<td width="90px" align="left">标题：</td>
                <td>
                	<input type="text" style="width:400px;" value="" id="title" name="title" readonly="true"/>
				</td>
            </tr>
            <tr>
                <td colspan="2" align="center">&nbsp;</td></tr>
            <tr>
               	<td width="90px" align="left">内容：</td>
                <td>
					<textarea cols="4" rows="3" id="content" name="content" style="width:400px;height: 120px" readonly="true"></textarea>
				</td>
            </tr>
            <tr>
                <td colspan="2" align="center">&nbsp;</td></tr>
            <tr>
                <td width="90px" align="left">文件：</td>
                <td>
                	<a href="" id="fileDown" name="fileDown" target="_blank"></a><img src="${ctx}/resources/images/loading.gif" id="loading" style="display:none;"/>
               	</td>
            </tr>
            <!-- <tr>
                <td width="90px" align="left">发布状态：</td>
                <td>
                	<select class="easyui-combobox" id="releaseStatus" name="releaseStatus" style="width:180px;">
                		<option value ="2">未发布</option>
                		<option value ="1">已发布</option>
                	</select>
               	</td>
            </tr>
            <tr>
               	<td width="90px" align="left">联系人：</td>
                <td>
                	<input type="text" value="" id="contactUser" name="contactUser" readonly="true"/>
					
				</td>
            </tr>
            <tr>
               	<td width="90px" align="left">电话：</td>
                <td>
                	<input type="text" class="easyui-validatebox" data-options="validType:'phoneOrMobileRex'" value="" id="tel" name="tel" readonly="true"/>
					
				</td>
            </tr>
            <tr>
               	<td width="90px" align="left">传真：</td>
                <td>
                	<input type="text" class="easyui-validatebox" data-options="validType:'phoneRex'" value="" id="fax" name="fax" readonly="true"/>
					
				</td>
            </tr>
            <tr>
               	<td width="90px" align="left">邮箱：</td>
                <td>
                	<input type="text" class="easyui-validatebox" validType="email" invalidMessage="请输入正确的邮箱!" value="" id="email" name="email" readonly="true"/>
					
				</td>
            </tr> -->
            <tr>
                <td colspan="2" align="center">&nbsp;</td>
            </tr>
		</table>
   </form>
</div>

<div id="up-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#edit_dialog').dialog('close')">关闭</a>
</div>
<form  method="post" id="queryForm"></form>
</body>
</html>
