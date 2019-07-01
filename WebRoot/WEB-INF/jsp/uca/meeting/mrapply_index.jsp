<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>会议室预约列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/meeting/mrapply_index.js"></script>
<link rel="stylesheet" href="${ctx}/resources/css/ztree/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${ctx}/resources/css/ztree/ztreeCap.css" type="text/css">
<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.exhide-3.5.min.js"></script>
<style type="text/css">
/* .sel{border:solid #FF0000; border-width:2px 2px 2px 2px;} */
.sel{background:#FFACAC}
.dissel{background:#F8F66A}
.table{border-collapse: separate;border-spacing:20px;}
.table td{padding:10px 10px;width:100px;height:80px}
.selwin{height:50px;line-height:50px;cursor:pointer;}
.showwin{cursor:pointer;float:right;}
</style>
</head>
<body>
<!-- 搜索 -->
<div id="select_bar" style="background:#F4F4F4">
     <div style="padding: 5px;">
        <form id="fmsearch" method="post" novalidate>
            <span>
				<label>会议室:</label>
				<select id="dMrId" name="dMrId" style="width:180px;" editable="false">
               		<option value ="" selected>请选择---</option>
               		<c:forEach items="${mrLi}" var="mr" varStatus="mrstatus">
				    	<option value ="${mr.mrId}|${mr.address}|${mr.peopleCount}">${mr.name}</option>
				  	</c:forEach>
               	</select>
			</span>
			<span>
				<label>开会日期：</label>
				<input id="qDate" type="text" class="easyui-datebox" style="width:120px" editable="false">
			</span>
			<span>
			  	<input type="button" name="select" onclick="javascript:toSearch();" value="查询" id="select" >&nbsp;&nbsp;&nbsp;&nbsp;
			  	<input type="button" name="mrapply" onclick="javascript:getObjByParam(1);" value="预约" id="mrapply" >&nbsp;&nbsp;&nbsp;&nbsp;
			  	<input type="button" name="nomrapply" onclick="javascript:getObjByParam(2);" value="取消预约" id="nomrapply" >&nbsp;&nbsp;&nbsp;&nbsp;
			  	<input type="reset" name="reset" value="重置" id="reset" >
			</span>
		</form>
	</div>
</div>
<div style="padding: 5px;">
<!-- #B8D8F9 -->
	<span style="padding:5px;width:10px;height:10px;background:#6FE55E">可预约</span>&nbsp;|&nbsp;<span style="padding:5px;width:10px;height:10px;background:#FFACAC">选中</span>&nbsp;|&nbsp;<span style="padding:5px;width:10px;height:10px;background:#E0E0E0">不能预约</span>&nbsp;|&nbsp;<span style="padding:5px;width:10px;height:10px;background:#FF0000">已预约</span>
</div>
<div id="show_bar">
	<div style="padding: 5px;">
		<span id="dAddress"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span id="dPeopleCount"></span>
	</div>
</div>
<table id="list_data" class="table">
</table>

<!-- 预约信息填报-->
<div id="edit_dialog" class="easyui-dialog" maximizable="true" resizable="true" style="width:580px;height:350px;padding:0px" modal="true" buttons="#up-buttons" closed="true" >
   <form id="form_edit" method="post" style="margin:20px 10px">
        <input type="hidden" id="mrId" name="mrId" value=""/>
        <input type="hidden" id="flag" name="flag" value=""/>
        <input type="hidden" id="vleStr" name="vleStr" value=""/>
        <input type="hidden" id="applyDate" name="applyDate" value=""/>
        <input type="hidden" id="devices" name="devices"/>
        <table>
			<tr>
               	<td width="90px" align="left">会议名称：</td>
                <td>
                	<input type="text" value="" id="conferenceName" name="conferenceName"/>
				</td>
            </tr>
            <tr>
               	<td width="90px" align="left">参会人员：</td>
                <td>
                	<input type="hidden" id="participants" name="participants"/>
					<textarea cols="4" rows="3" id="showParticipants" name="showParticipants" style="width:300px;" readonly="readonly"></textarea>
					<a id="menuBtn" href="javascript:void(0);" onclick="showMenu();">选择</a>
				</td>
            </tr>
            <tr>
            	<td width="90px" align="left">会议设备：</td>
                <td>
					<input type="checkbox" name="deviceIds" value="1" onclick="javascript:selectRoles();" />投影仪&nbsp;
					<input type="checkbox" name="deviceIds" value="2" onclick="javascript:selectRoles();" />电视&nbsp;
					<input type="checkbox" name="deviceIds" value="3" onclick="javascript:selectRoles();" />麦克&nbsp;
					<input type="checkbox" name="deviceIds" value="4" onclick="javascript:selectRoles();" />电脑&nbsp;
					<input type="checkbox" name="deviceIds" value="5" onclick="javascript:selectRoles();" />会标&nbsp;
					<input type="checkbox" name="deviceIds" value="6" onclick="javascript:selectRoles();" />桌签&nbsp;
				</td>
            </tr>
            <tr>
               	<td width="90px" align="left">会议备注：</td>
                <td>
					<textarea cols="4" rows="3" id="remark" name="remark" style="width:300px;"></textarea>
				</td>
            </tr>
            <tr>
                <td colspan="2" align="center">&nbsp;</td>
            </tr>
		</table>
   </form>
</div>
<div id="up-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:commit()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#edit_dialog').dialog('close')">取消</a>
</div>

<!-- 参会人员选择-->
<div id="menuContent" class="menuContent" style="display:none; position: absolute;z-index:9999">
	<ul id="treeDemo" class="ztree" style="margin-top:0;"></ul>
</div>

<!-- 预约信息查看-->
<div id="show_dialog" class="easyui-dialog" maximizable="true" resizable="true" style="width:580px;height:350px;padding:0px" modal="true" closed="true" >
   <form id="form_show" method="post" style="margin:20px 10px">
        <table>
			<tr>
               	<td width="90px" align="left">会议名称：</td>
                <td>
                	<input type="text" value="" id="cfName" name="cfName" style="width:300px;" readonly="readonly"/>
				</td>
            </tr>
            <tr>
               	<td width="90px" align="left">会议室：</td>
                <td>
                	<input type="text" value="" id="mName" name="mName" style="width:300px;" readonly="readonly"/>
				</td>
            </tr>
            <tr>
               	<td width="90px" align="left">参会人员：</td>
                <td>
					<textarea cols="4" rows="3" id="showPs" name="showPs" style="width:300px;" readonly="readonly"></textarea>
				</td>
            </tr>
            <tr>
               	<td width="90px" align="left">会议设备：</td>
                <td>
					<input type="text" value="" id="devicesName" name="devicesName" style="width:300px;" readonly="readonly"/>
				</td>
            </tr>
            <tr>
               	<td width="90px" align="left">会议备注：</td>
                <td>
					<textarea cols="4" rows="3" id="remk" name="remk" style="width:300px;"  readonly="readonly"></textarea>
				</td>
            </tr>
            <tr>
               	<td width="90px" align="left">申请人姓名：</td>
                <td>
                	<input type="text" value="" id="applyName" name="applyName" readonly="readonly"/>
				</td>
            </tr>
            <tr>
               	<td width="90px" align="left">申请人部门：</td>
                <td>
                	<input type="text" value="" id="applyOrg" name="applyOrg" readonly="readonly"/>
				</td>
            </tr>
            <tr>
               	<td width="90px" align="left">预约时间：</td>
                <td>
                	<input type="text" value="" id="applyDt" name="applyDt" readonly="readonly"/>
				</td>
            </tr>
            <tr>
                <td colspan="2" align="center">&nbsp;</td>
            </tr>
		</table>
   </form>
</div>
<form method="post" id="queryForm"></form>
</body>
</html>
