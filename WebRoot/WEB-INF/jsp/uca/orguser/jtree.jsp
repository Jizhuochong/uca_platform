<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
    <title>用户树</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
	<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/jquery/jquery.form.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/uca/orguser/jtree.js"></script>
</head>
<body>
<table id="tree_data" class="easyui-treegrid"></table>

<div id="toolbar">
<!-- 	<div style="margin: 0 auto;padding: 2px;"> -->
<!--         <a href="javascript:toAdd();" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加用户</a> -->
<!--    	</div> -->
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
            	<td width="160px" align="right">是否有效：</td>
                <td>
                	<select name="userStatus" class="easyui-combobox" style="width:320px;">
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
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

<div id="user_jsdw_dialog" class="easyui-dialog" style="width:600px;height:420px;padding:10px;" 
	data-options="closed:true,buttons:'#user-jsdw-buttons',resizable:true,maximizable:true">
    <form id="formName_user_jsdw" method="post">
    	<input type="hidden" name="userId" id="uidJsdw"/>
    	<table>
            <tr>
                <td width="160px" align="right">账户：</td>
                <td>
                	<input name="userAccount" id="ua_jsdw" class="easyui-validatebox" style="width:320px;" 
                		data-options="required:true,missingMessage:'请输入账户!'" readonly="readonly" onchange="javascript:checkAccount();">
				</td>
            </tr>
            <tr>
            	<td width="160px" align="right">是否激活：</td>
                <td>
                	<select name="userStatus" id="activeSt" class="easyui-combobox" style="width:320px;" readonly="readonly"> 
						<option value="1">已激活</option>
						<option value="0">未激活</option>
					</select>
				</td>
            </tr>
            <tr>
            	<td width="160px" align="right">密码：</td>
                <td>
                	<input name="userPassword" type="password" class="easyui-validatebox" style="width:320px;" 
                		data-options="required:true,missingMessage:'请输入密码!'" readonly="readonly">
				</td>
            </tr>
            <tr>
            	<td width="160px" align="right">手机：</td>
                <td>
                	<input name="phoneNum" class="easyui-validatebox" style="width:320px;" 
                		data-options="required:true,missingMessage:'请输入手机!'" readonly="readonly">
				</td>
            </tr>
            <tr>
            	<td width="160px" align="right">联系电话：</td>
                <td>
                	<input name="telephone" class="easyui-validatebox" style="width:320px;" 
                		data-options="required:false,missingMessage:'请输入联系电话!'" readonly="readonly">
				</td>
            </tr>
            <tr>
            	<td width="160px" align="right">姓名：</td>
                <td>
                	<input name="userName" class="easyui-validatebox" style="width:320px;" 
                		data-options="required:true,missingMessage:'请输入姓名!'" readonly="readonly">
				</td>
            </tr>
            <tr>
            	<td width="160px" align="right">性别：</td>
                <td>
                	<select name="sex" id="sex" class="easyui-combobox" style="width:320px;" readonly="readonly">
						<option value="1">男</option>
						<option value="0">女</option>
					</select>
				</td>
            </tr>
            <tr>
            	<td width="160px" align="right">邮箱：</td>
                <td>
                	<input name="email" class="easyui-validatebox" style="width:320px;" 
                		data-options="required:true,missingMessage:'请输入邮箱!'" readonly="readonly">
				</td>
            </tr>
            <tr>
            	<td width="160px" align="right">建设单位名称：</td>
                <td>
                	<input name="devOrg" class="easyui-validatebox" style="width:320px;" 
                		data-options="required:true,missingMessage:'请输入建设单位!'" readonly="readonly">
				</td>
            </tr>
            <tr>
            	<td width="160px" align="right">单位地址：</td>
                <td>
                	<input name="devOrgAddress" class="easyui-validatebox" style="width:320px;" 
                		data-options="required:false,missingMessage:'请输入单位地址!'" readonly="readonly">
				</td>
            </tr>
            <tr>
            	<td width="160px" align="right">备注：</td>
                <td>
                	<textarea rows="4" cols="30" name="remark" style="width:320px;" readonly="readonly"></textarea>
				</td>
            </tr>
        </table>
    </form>
</div>
<div id="user-jsdw-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="javascript:saveJsdwGrid();">激活</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="javascript:$('#user_jsdw_dialog').dialog('close');">取消</a>
</div>
</body>
</html>