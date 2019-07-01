<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
    <title>用户列表</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
	<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/uca/umuser/umuser_list.js"></script>
</head>
<body>
<table id="list_data" cellspacing="0" cellpadding="0"></table>

<div id="toolbar">
	<div style="margin: 0 auto;padding: 2px;">
        <a href="javascript:toAdd();" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a>
        <a href="javascript:toEdit();" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a>
        <a href="javascript:toRemove();" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
   	</div>
   	<div style="padding: 5px;">
       	账户：<input id="uaccount" type="text" class="textbox" style="width:120px">&nbsp;
       	姓名：<input id="uname" type="text" class="textbox" style="width:120px">&nbsp;
       	机构(用户单位)：<select id="org_id_select_jsid" class="easyui-combobox" style="width:120px;" editable="false"
       				data-options="valueField:'id',textField:'name',panelHeight:'auto',panelMaxHeight:120,
       					url:'${ctx}/org/fkComboData'">
	</select>&nbsp;
       	创建日期：<input id="sd" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;-&nbsp;
    		<input id="ed" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;
    	<a href="javascript:toSearch();" class="easyui-button" data-options="iconCls:'icon-search',plain:true">查询</a>&nbsp;
		<a href="javascript:clearForm();" class="easyui-button" data-options="iconCls:'icon-remove',plain:true">重置</a>
    </div>
</div>

<div id="user_dialog" class="easyui-dialog" style="width:600px;height:300px;padding:10px;" 
	data-options="closed:true,buttons:'#user-buttons',resizable:true,maximizable:true">
    <form id="formName_user" method="post">
    	<input type="hidden" name="userId" id="uidflag"/>
    	<input type="hidden" name="roles" id="ridflag"/>
    	<table>
            <tr>
                <td width="160px" align="right">账户：</td>
                <td>
                	<input name="userAccount" id="ua" class="easyui-validatebox" style="width:320px;" 
                		data-options="required:true,missingMessage:'请输入账户!'" onchange="javascript:checkAccount();">
				</td>
            </tr>
            <tr>
            	<td width="160px" align="right">密码：</td>
                <td>
                	<input name="userPassword" type="password" class="easyui-validatebox" style="width:320px;" 
                		data-options="required:true,missingMessage:'请输入密码!'">
				</td>
            </tr>
            <tr>
            	<td width="160px" align="right">姓名：</td>
                <td>
                	<input name="userName" class="easyui-validatebox" style="width:320px;" 
                		data-options="required:true,missingMessage:'请输入姓名!'">
				</td>
            </tr>
            <tr>
            	<td width="160px" align="right">角色：</td>
                <td>
                	<c:forEach items="${roleList }" var="role" varStatus="status">
						<input type="checkbox" name="roleIds" value="${role.roleId }" onclick="javascript:selectRoles();" />${role.roleName }
					</c:forEach>
				</td>
            </tr>
            <tr>
            	<td width="160px" align="right">所属机构：</td>
                <td>
                	<select id="org_id_jsid" name="orgId" class="easyui-combobox" style="width:320px;" 
              			data-options="valueField:'id',textField:'name',panelHeight:'auto',panelMaxHeight:120,
              				url:'${ctx}/org/fkComboData',required:true,editable:false,multiple:false">
					</select>
				</td>
            </tr>
            <!-- <tr>
            	<td width="160px" align="right">描述：</td>
                <td>
                	<textarea rows="4" cols="30" name="description" style="width:320px;"></textarea>
				</td>
            </tr> -->
        </table>
    </form>
</div>
<div id="user-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="javascript:saveGrid();">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#user_dialog').dialog('close');">取消</a>
</div>
</body>
</html>