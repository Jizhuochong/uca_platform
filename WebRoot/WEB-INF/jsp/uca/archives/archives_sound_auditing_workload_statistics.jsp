<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<title>声像档案审核工作量办理统计</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/uca/archives/archives_sound_auditing_workload_statistics.js" ></script>
</head>
<body>
	<div id="p" class="easyui-panel" style="width: 100%; height: 100%; padding: 10px;" data-options="fit:true,border:false">
		<div>
		    <div style="padding: 5px;">
		           起始日期：<input id="sd" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;
		           结束日期：<input id="ed" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;
		           审核状态：<select id="type_search_jsid" class="easyui-combobox" style="width:240px;" editable="false"
           				data-options="valueField:'id',textField:'text',panelHeight:'auto',panelMaxHeight:120,
                			data: [{
								id: '0',
								text: '未审核'
							},{
								id: '1',
								text: '待审核'
							},{
								id: '2',
								text: '审核不通过'
							},{
								id: '3',
								text: '审核通过'
							},{
								id: '4',
								text: '已重传'
							}]">
					</select>&nbsp;
		      	<a href="javascript:toSearch();" class="easyui-button" data-options="iconCls:'icon-search',plain:true">查询</a>&nbsp;
		   		<a href="javascript:clearForm();" class="easyui-button" data-options="iconCls:'icon-remove',plain:true">重置</a>
		    </div>
		    <div style="padding: 5px;">
		    	<c:if test="${sd != null}">${sd }</c:if> ~ <c:if test="${ed != null}">${ed }</c:if>符合条件的数据共有 ${archivesCount } 条
		    </div>
		</div>
		<table cellpadding="0" cellspacing="0" border="0" class="display" width="100%">
			<thead>
			<tr>
				<th width="20%">工程名称</th>
				<th width="20%">申请时间</th>
				<th width="20%">审核时间</th>
				<th width="20%">审批结果</th>
				<th width="20%">审批状态</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${archives }" var="archive">
			<tr>
				<td>${archive.projectName }</td>
				<td><fmt:formatDate value="${archive.uploadTime }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${archive.checkTime }" pattern="yyyy-MM-dd"/></td>
				<td>${archive.instruction }</td>
				<td>
					<c:if test="${archive.checkStatus == 0}">未审核</c:if>
					<c:if test="${archive.checkStatus == 1}">待审核</c:if>
					<c:if test="${archive.checkStatus == 2}">审核不通过</c:if>
					<c:if test="${archive.checkStatus == 3}">审核通过</c:if>
					<c:if test="${archive.checkStatus == 4}">已重传</c:if>
				</td>
			</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>