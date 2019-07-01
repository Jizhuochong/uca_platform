<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>查看打印竣工档案登记表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/archives/archives_completion_register_form.js"></script>
</head>
<body>
<div id="toolbar">
	<div style="margin: 0 auto;padding: 2px;">
    	<a href="javascript:nowprint(${register.type },${register.id});" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">下载打印登记表</a>
    </div>
</div>
<div style="padding-top: 20px;"></div>
<!--startprint1-->
<div align="center">
<table id="data_obj" cellspacing="2" cellpadding="2" border="0" width="800"><tr align="center"><td>
<div align="center"><span style="font-size:24px">北京城市建设档案馆</span></div>
<div align="center"><span style="font-size:24px">城市建设工程办理竣工档案登记表</span></div>
<div align="center" style="font-size:14px;">
(<c:choose><c:when test="${register.type == 1 }">建筑工程</c:when><c:when test="${register.type == 2 || register.type == 3 }">市政工程</c:when></c:choose>)</div>
<div align="center">
<div align="right" style="font-size:16px;">${register.archiveWord }</div>
<c:choose><c:when test="${register.type == 1 }"><div align="right" style="font-size:16px;">${register.buildWord }</div></c:when></c:choose>
<table id="data_one" cellspacing="2" cellpadding="2" border="0" width="800" style="font-size:14px">
	<tr align="left">
		<td width="10%"><strong>建设单位：</strong></td><td width="30%"><strong>${register.buildUnit }</strong></td>
		<td width="10%"><strong>邮政编码：</strong></td><td width="20%"><strong>${register.zipCode }</strong></td>
		<td width="10%"><strong>登记人：</strong></td><td width="20%"><strong>${register.registerPerson }</strong></td>
	<tr>
	<tr align="left">
		<td width="10%"><strong>通信地址：</strong></td><td width="30%"><strong>${register.mailingAddress }</strong></td>
		<td width="10%"><strong>登记日期：</strong></td><td width="20%"><strong>${register.registerDate }</strong></td>
		<td width="10%"><strong>电话：</strong></td><td width="20%"><strong>${register.phone }</strong></td>
	<tr>
</table>
<table id="data_two" cellspacing="2" cellpadding="2" border="1" width="800" style="font-size:14px">
<tr>
	<td width="10%" align="center">工程名称</td>
	<td width="40%" align="left">${register.projectName }</td>
	<td width="10%" align="center">工程地点</td>
	<td width="40%" align="left">${register.projectLocation }</td>
</tr>
<tr>
	<td width="10%" align="center">设计单位</td>
	<td width="40%" align="left">${register.designUnit }</td>
	<td width="10%" align="center">施工单位</td>
	<td width="40%" align="left">${register.constructionUnit }</td>
</tr>
<c:choose>
    <c:when test="${register.type == 2 || register.type == 3}">
    <tr>
		<td width="10%" align="center">工程类型</td>
		<td width="40%" align="left">${register.projectType }</td>
		<td width="10%" align="center">测量单位</td>
		<td width="40%" align="left">${register.measureUnit }</td>
	</tr>
    </c:when>
</c:choose>
<tr>
	<td colspan="4">
	<table id="data_three" cellspacing="2" cellpadding="2" border="1" width="800" style="font-size:14px" frame=void>
	<tr>
		<td width="20%" align="center">计划工期</td>
		<td width="10%" align="center">开工</td>
		<td width="30%" align="left">${register.startDate }</td>
		<td width="10%" align="center">竣工</td>
		<td width="30%" align="left">${register.completionDate }</td>
	</tr>
	</table>
	</td>
</tr>
<c:choose>
	<c:when test="${register.type == 1}">
    <tr>
		<td colspan="4">
		<table id="data_fore" cellspacing="2" cellpadding="2" border="1" width="800" style="font-size:14px" frame=void>
		<tr align="center">
			<td width="15%">工程项目 (用途)</td>
			<td width="10%">结构种类</td>
			<td width="10%">层数地上/地下</td>
			<td width="20%">占地面积(平方米)</td>
			<td width="20%">建筑面积(平方米)</td>
			<td width="10%">栋楼</td>
			<td width="15%">总投资(元)</td>
		</tr>
		<c:forEach items="${detailedList }" var="detailed">
		<tr>
			<td width="15%" align="left">${detailed.projectPurpose }</td>
			<td width="10%" align="left">${detailed.categoryStructure }</td>
			<td width="10%" align="center">${detailed.floors }</td>
			<td width="20%" align="right">${detailed.coversArea }</td>
			<td width="20%" align="right">${detailed.constructionArea }</td>
			<td width="10%" align="center">${detailed.buildings }</td>
			<td width="15%" align="right"><fmt:formatNumber value="${detailed.totalInvestment }" pattern="#.00"/></td>
		</tr>
		</c:forEach>
		</table>
		</td>
	</tr>
    </c:when>
    <c:when test="${register.type == 2}">
    <tr>
		<td colspan="4">
		<table id="data_fore" cellspacing="2" cellpadding="2" border="1" width="800" style="font-size:14px" frame=void>
		<tr align="center">
			<td width="34%">工程内容</td>
			<td width="33%">数量</td>
			<td width="33%">总投资（元）</td>
		</tr>
		<c:forEach items="${detailedList }" var="detailed">
		<tr>
			<td width="34%" align="left">${detailed.projectContent }</td>
			<td width="33%" align="center">${detailed.projectQuantity }</td>
			<td width="33%" align="center"><fmt:formatNumber value="${detailed.totalInvestment }" pattern="#.00"/></td>
		</tr>
		</c:forEach>
		</table>
		</td>
	</tr>
    </c:when>
    <c:when test="${register.type == 3}">
    <tr>
		<td colspan="4">
		<table id="data_fore" cellspacing="2" cellpadding="2" border="1" width="800" style="font-size:14px" frame=void>
		<tr align="center">
			<td width="50%">管径（断面）</td>
			<td width="50%">长度（米）</td>
		</tr>
		<c:forEach items="${detailedList }" var="detailed">
		<tr>
			<td width="50%" align="left">${detailed.diameterSection }</td>
			<td width="50%" align="right">${detailed.diameterLength }</td>
		</tr>
		</c:forEach>
		</table>
		</td>
	</tr>
	<tr>
		<td width="20%" align="center">总投资（元）</td>
		<td colspan="3" align="right"><fmt:formatNumber value="${register.totalInvestment }" pattern="#.00"/></td>
	</tr>
    </c:when>
</c:choose>
<c:choose>
	<c:when test="${register.type == 1}">
    <tr>
		<td width="10%" align="center">备注</td>
		<td colspan="3" align="left">${register.remark }</td>
	</tr>
    </c:when>
    <c:when test="${register.type == 2 || register.type == 3}">
    <tr>
		<td width="20%" align="center">备注</td>
		<td colspan="3" align="left">${register.remark }</td>
	</tr>
    </c:when>
</c:choose>
</table>
<div style="padding-top: 20px;"></div>
<div align="left" style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市城建档案馆经办人：${register.archivesManagers }
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;盖章：</div>
<div style="padding-top: 30px;"></div>
<table id="show_notes" cellspacing="2" cellpadding="2" width="800" style="font-size:16px">
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr align="left">
		<td>注意事项：</td></td>
	</tr>
	<tr align="left">
		<td>（1）建设项目取得《建设工程规划许可证》后，按照《北京市城市建设档案管理办法》的要求，须到市城建档案馆办理建设工程档案登记，盖章有效。</td>
	</tr>
	<tr align="left">
		<td>（2）建设单位与施工单位签订的承包合同中，必须包括编制及移交工程档案的有关内容。</td>
	</tr>
	<tr align="left">
		<td>（3）建设单位必须配备专门人员负责检查，监督工程档案的形成、收集、整理情况，并负责与市城建档案馆联系和配合工程档案的验收移交事宜。</td>
	</tr>
	<tr align="left">
		<td>（4）此工程必须按北京市《建筑工程资料管理规程》和北京市《市政基础设施施工资料管理规程》编制竣工档案，并在竣工验收后六个月内报送市城建档案馆。</td>
	</tr>
	<tr align="left">
		<td>（5）凡竣工档案不按规定要求进行编制和移交的，将根据《中华人民共和国城乡规划法》第六十七条规定，进行处罚。</td>
	</tr>
	<tr align="center">
		<td>（第一联 市规划委存查）</td>
	</tr>
</table>
</div>
</td></tr></table></div>
<div style="padding-top: 200px;"></div>
<div align="center">
<table id="data_obj" cellspacing="2" cellpadding="2" border="0" width="800"><tr align="center"><td>
<div align="center"><span style="font-size:24px">北京城市建设档案馆</span></div>
<div align="center"><span style="font-size:24px">城市建设工程办理竣工档案登记表</span></div>
<div align="center" style="font-size:14px;">
(<c:choose><c:when test="${register.type == 1 }">建筑工程</c:when><c:when test="${register.type == 2 || register.type == 3 }">市政工程</c:when></c:choose>)</div>
<div align="center">
<div align="right" style="font-size:16px;">${register.archiveWord }</div>
<c:choose><c:when test="${register.type == 1 }"><div align="right" style="font-size:16px;">${register.buildWord }</div></c:when></c:choose>
<table id="data_one" cellspacing="2" cellpadding="2" border="0" width="800" style="font-size:14px">
	<tr align="left">
		<td width="10%"><strong>建设单位：</strong></td><td width="30%"><strong>${register.buildUnit }</strong></td>
		<td width="10%"><strong>邮政编码：</strong></td><td width="20%"><strong>${register.zipCode }</strong></td>
		<td width="10%"><strong>登记人：</strong></td><td width="20%"><strong>${register.registerPerson }</strong></td>
	<tr>
	<tr align="left">
		<td width="10%"><strong>通信地址：</strong></td><td width="30%"><strong>${register.mailingAddress }</strong></td>
		<td width="10%"><strong>登记日期：</strong></td><td width="20%"><strong>${register.registerDate }</strong></td>
		<td width="10%"><strong>电话：</strong></td><td width="20%"><strong>${register.phone }</strong></td>
	<tr>
</table>
<table id="data_two" cellspacing="2" cellpadding="2" border="1" width="800" style="font-size:14px">
<tr>
	<td width="10%" align="center">工程名称</td>
	<td width="40%" align="left">${register.projectName }</td>
	<td width="10%" align="center">工程地点</td>
	<td width="40%" align="left">${register.projectLocation }</td>
</tr>
<tr>
	<td width="10%" align="center">设计单位</td>
	<td width="40%" align="left">${register.designUnit }</td>
	<td width="10%" align="center">施工单位</td>
	<td width="40%" align="left">${register.constructionUnit }</td>
</tr>
<c:choose>
    <c:when test="${register.type == 2 || register.type == 3}">
    <tr>
		<td width="10%" align="center">工程类型</td>
		<td width="40%" align="left">${register.projectType }</td>
		<td width="10%" align="center">测量单位</td>
		<td width="40%" align="left">${register.measureUnit }</td>
	</tr>
    </c:when>
</c:choose>
<tr>
	<td colspan="4">
	<table id="data_three" cellspacing="2" cellpadding="2" border="1" width="800" style="font-size:14px" frame=void>
	<tr>
		<td width="20%" align="center">计划工期</td>
		<td width="10%" align="center">开工</td>
		<td width="30%" align="left">${register.startDate }</td>
		<td width="10%" align="center">竣工</td>
		<td width="30%" align="left">${register.completionDate }</td>
	</tr>
	</table>
	</td>
</tr>
<c:choose>
	<c:when test="${register.type == 1}">
    <tr>
		<td colspan="4">
		<table id="data_fore" cellspacing="2" cellpadding="2" border="1" width="800" style="font-size:14px" frame=void>
		<tr align="center">
			<td width="15%">工程项目 (用途)</td>
			<td width="10%">结构种类</td>
			<td width="10%">层数地上/地下</td>
			<td width="20%">占地面积(平方米)</td>
			<td width="20%">建筑面积(平方米)</td>
			<td width="10%">栋楼</td>
			<td width="15%">总投资(元)</td>
		</tr>
		<c:forEach items="${detailedList }" var="detailed">
		<tr>
			<td width="15%" align="left">${detailed.projectPurpose }</td>
			<td width="10%" align="left">${detailed.categoryStructure }</td>
			<td width="10%" align="center">${detailed.floors }</td>
			<td width="20%" align="right">${detailed.coversArea }</td>
			<td width="20%" align="right">${detailed.constructionArea }</td>
			<td width="10%" align="center">${detailed.buildings }</td>
			<td width="15%" align="right"><fmt:formatNumber value="${detailed.totalInvestment }" pattern="#.00"/></td>
		</tr>
		</c:forEach>
		</table>
		</td>
	</tr>
    </c:when>
    <c:when test="${register.type == 2}">
    <tr>
		<td colspan="4">
		<table id="data_fore" cellspacing="2" cellpadding="2" border="1" width="800" style="font-size:14px" frame=void>
		<tr align="center">
			<td width="34%">工程内容</td>
			<td width="33%">数量</td>
			<td width="33%">总投资（元）</td>
		</tr>
		<c:forEach items="${detailedList }" var="detailed">
		<tr>
			<td width="34%" align="left">${detailed.projectContent }</td>
			<td width="33%" align="center">${detailed.projectQuantity }</td>
			<td width="33%" align="center"><fmt:formatNumber value="${detailed.totalInvestment }" pattern="#.00"/></td>
		</tr>
		</c:forEach>
		</table>
		</td>
	</tr>
    </c:when>
    <c:when test="${register.type == 3}">
    <tr>
		<td colspan="4">
		<table id="data_fore" cellspacing="2" cellpadding="2" border="1" width="800" style="font-size:14px" frame=void>
		<tr align="center">
			<td width="50%">管径（断面）</td>
			<td width="50%">长度（米）</td>
		</tr>
		<c:forEach items="${detailedList }" var="detailed">
		<tr>
			<td width="50%" align="left">${detailed.diameterSection }</td>
			<td width="50%" align="right">${detailed.diameterLength }</td>
		</tr>
		</c:forEach>
		</table>
		</td>
	</tr>
	<tr>
		<td width="20%" align="center">总投资（元）</td>
		<td colspan="3" align="right"><fmt:formatNumber value="${register.totalInvestment }" pattern="#.00"/></td>
	</tr>
    </c:when>
</c:choose>
<c:choose>
	<c:when test="${register.type == 1}">
    <tr>
		<td width="10%" align="center">备注</td>
		<td colspan="3" align="left">${register.remark }</td>
	</tr>
    </c:when>
    <c:when test="${register.type == 2 || register.type == 3}">
    <tr>
		<td width="20%" align="center">备注</td>
		<td colspan="3" align="left">${register.remark }</td>
	</tr>
    </c:when>
</c:choose>
</table>
<div style="padding-top: 20px;"></div>
<div align="left" style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市城建档案馆经办人：${register.archivesManagers }
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;盖章：</div>
<div style="padding-top: 30px;"></div>
<table id="show_notes" cellspacing="2" cellpadding="2" width="800" style="font-size:16px">
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr align="left">
		<td>注意事项：</td></td>
	</tr>
	<tr align="left">
		<td>（1）建设项目取得《建设工程规划许可证》后，按照《北京市城市建设档案管理办法》的要求，须到市城建档案馆办理建设工程档案登记，盖章有效。</td>
	</tr>
	<tr align="left">
		<td>（2）建设单位与施工单位签订的承包合同中，必须包括编制及移交工程档案的有关内容。</td>
	</tr>
	<tr align="left">
		<td>（3）建设单位必须配备专门人员负责检查，监督工程档案的形成、收集、整理情况，并负责与市城建档案馆联系和配合工程档案的验收移交事宜。</td>
	</tr>
	<tr align="left">
		<td>（4）此工程必须按北京市《建筑工程资料管理规程》和北京市《市政基础设施施工资料管理规程》编制竣工档案，并在竣工验收后六个月内报送市城建档案馆。</td>
	</tr>
	<tr align="left">
		<td>（5）凡竣工档案不按规定要求进行编制和移交的，将根据《中华人民共和国城乡规划法》第六十七条规定，进行处罚。</td>
	</tr>
	<tr align="center">
		<td>（第二联 建设单位存查）</td>
	</tr>
</table>
</div>
</td></tr></table></div>
<!--endprint1-->
<div style="padding-top: 20px;"></div>
</body>
</html>
