<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
		<link rel="stylesheet" href="${ctx}/resources/css/ztree/zTreeStyle.css" type="text/css">
		<link rel="stylesheet" href="${ctx}/resources/css/ztree/ztreeCap.css" type="text/css">
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.all-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.core-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.exedit-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/ztree/jquery.ztree.exhide-3.5.min.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/jquery-1.9.1/jquery.json-2.4.min.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/themes/default/easyui.css">
    	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/themes/icon.css">
    	<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/jquery-easyui-1.3.5/demo.css">
		<script type="text/javascript" src="${ctx}/resources/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
		<style scoped="scoped">
        .textbox{
            height:20px;
            margin:0;
            padding:0 2px;
            box-sizing:content-box;
        }
    	</style>
	</head>
	<script type="text/javascript">
		function validate() {
		    if ($("#resourceName").val()=="")
		    {
		        alert("请输入资源名称！");
		        return false;
		    }
		    if ($("#resourceParentId").val()=="")
		    {
		        alert("请选择父级资源！");
		        return false;
		    }
		    if ($('input:radio[name="resourceType"]:checked').val()==null)
		    {
		    	alert("请选择资源类型！");
		        return false;
		    }
		    if ($('input:radio[name="resourceType"]:checked').val()=="1")
		    {
		    	if ($("#resourceUrl").val()=="")
			    {
			        alert("资源类型为URL，请输入URL资源！");
			        return false;
			    }
		    }
		     return true;
		}
	</script>
	<script type="text/javascript">
		$(function() {
			$("input[name=submitSave]").click(function(){
				if(confirm("是否确定提交保存？")){
					checkroleids();
					var obj = {}, flag = true, id = $("#resourceId").val(), pid = $("#resourceParentId").val(), name = $("#resourceName").val();
					var saveFlag = true, beforePid = $("#beforeParentId").val();
					//表单验证
					saveFlag = validate();
					obj={resourceId:id,
							resourceSort:$("#resourceSort").val(),
							resourceParentId:pid,
							resourceName:name,
							resourceUrl:$("#resourceUrl").val(),
							resourceUrlDirection:$("#resourceUrlDirection").val(),
							resourceType:$('input:radio[name="resourceType"]:checked').val(),
							resourceStatus:$('input:radio[name="resourceStatus"]:checked').val(),
							resourceShowflag:$('input:radio[name="resourceShowflag"]:checked').val(),
							roleIds:$("#roleIds").val()};
					if(!saveFlag){
						return false;
					}
					$.ajax({
					    url: '${ctx}/resource/save',
					    type: 'POST',
					    contentType: "application/json;charset=utf-8",
					    dataType: 'json',
					    async :false,
					    data: $.toJSON(obj),
					    success: function(data){
					    	if(data.success) {
					    		flag = true;
					    		alert(data.msg);
					    	} else {
					    		flag = false;
					    		alert(data.msg);
					    	}
					    },
					    error: function(){
					    	alert("提交保存失败，请重试！");
					    }
					});
					if(flag) {
						parent.updateNode(id, pid, name, beforePid);
					}
				}
			});
		});
	</script>
	<body>
		<div class="easyui-panel" title="修改资源" data-options="border:false,fit:true,closable:true,collapsible:true" style="padding: 20px;">
        <form name="formName">
        	<input type="hidden" name="resourceId" id="resourceId" value="${resource.resourceId }"/>
			<input type="hidden" name="resourceSort" id="resourceSort" value="${resource.resourceSort }"/>
			<input type="hidden" name="beforeParentId" id="beforeParentId" value="${resource.resourceParentId }"/>
			<input type="hidden" name="resourceParentId" id="resourceParentId" value="${resource.resourceParentId }"/>
			<input type="hidden" name="roleIds" id="roleIds" value=""/>
            <table cellpadding="9">
                <tr>
                    <td>资源名称：</td>
                    <td><input class="textbox" type="text" name="resourceName" id="resourceName" value="${resource.resourceName }" size="60"></input></td>
                </tr>
                <tr>
                    <td>父级资源：</td>
                    <td><input class="textbox" type="text" id="resourceParentName" value="${resource.resourceParentName }" size="60" readonly></input>&nbsp;&nbsp;<a id="menuBtn" href="javascript:void(0);" onclick="showMenu();">选择</a></td>
                </tr>
                <tr>
                    <td>资源链接：</td>
                    <td><input class="textbox" type="text" name="resourceUrl" id="resourceUrl" value="${resource.resourceUrl }" size="60"></input></td>
                </tr>
                <tr>
                    <td>资源描述：</td>
                    <td><textarea name="resourceUrlDirection" id="resourceUrlDirection" rows="5" cols="56">${resource.resourceUrlDirection }</textarea></td>
                </tr>
                <tr>
                    <td>资源类型：</td>
                    <td>
						<input type="radio" name="resourceType" id="resourceType" value="1" <c:if test="${resource.resourceType eq 1}">checked</c:if>/>URL&nbsp;&nbsp;
						<input type="radio" name="resourceType" id="resourceType" value="0" <c:if test="${resource.resourceType eq 0}">checked</c:if>/>NO URL
                	</td>
                </tr>
                <tr>
                    <td>资源状态：</td>
                    <td>
                    	<input type="radio" name="resourceStatus" id="resourceStatus" value="1" <c:if test="${resource.resourceStatus eq 1}">checked</c:if>/>有效&nbsp;&nbsp;
						<input type="radio" name="resourceStatus" id="resourceStatus" value="0" <c:if test="${resource.resourceStatus eq 0}">checked</c:if>/>无效
                    </td>
                </tr>
                <tr>
                    <td>资源显示：</td>
                    <td>
                    	<input type="radio" name="resourceShowflag" id="resourceShowflag" value="1" <c:if test="${resource.resourceShowflag eq 1}">checked</c:if>/>显示&nbsp;&nbsp;
						<input type="radio" name="resourceShowflag" id="resourceShowflag" value="0" <c:if test="${resource.resourceShowflag eq 0}">checked</c:if>/>不显示
                    </td>
                </tr>
                <tr>
                    <td>角色分配：</td>
                    <td>
                    	<c:forEach items="${roleList }" var="role" varStatus="urstatus">
							<input type="checkbox" name="roleId" value="${role.roleId }" <c:forEach items="${resource.umRoles }" var="uRole"><c:if test="${uRole.roleId eq role.roleId }">checked</c:if></c:forEach>/><c:out value="${role.roleName }"></c:out>
						</c:forEach>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                    	<input type="button" class="bn3" value="保存" name="submitSave">&nbsp;&nbsp;
						<input type="reset" class="bn3" value="重置">
                    </td>
                </tr>
            </table>
        </form>
        </div>
		<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
			<ul id="resTree" class="ztree" style="margin-top:0;"></ul>
		</div>
		<script type="text/javascript">
		function checkroleids() {
			var roleId = document.getElementsByName("roleId");
			var objarray = roleId.length;
			var checks = "";
			for (var i = 0; i < objarray; i++) {
				if (roleId[i].checked == true) {
					checks += roleId[i].value + ",";
				}
			}
			$("#roleIds").val(checks);
		}
		</script>
		<SCRIPT type="text/javascript">
		<!--
		var setting = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			async: {
				enable: true,
				url:"${ctx}/resource/showAddResourceTree",
				autoParam:["id"],
				dataFilter: filter
			},
			callback: {
				onClick: onClick
			}
		};
		
		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}

		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("resTree"),
			nodes = zTree.getSelectedNodes(),
			nameV = "";
			pidV = "";
			nodes.sort(function compare(a,b){return a.id-b.id;});
			for (var i=0, l=nodes.length; i<l; i++) {
				nameV += nodes[i].name + ",";
				pidV += nodes[i].id + ",";
			}
			if (nameV.length > 0 ) nameV = nameV.substring(0, nameV.length-1);
			if (pidV.length > 0 ) pidV = pidV.substring(0, pidV.length-1);
			
			var idV = $("#resourceId").val();
			if(idV == pidV) {
				alert("父级资源不能等同于当前资源！");
				return;
			}
			
			var nameVObj = $("#resourceParentName");
			var pidObj = $("#resourceParentId");
			//nameVObj.attr("value", nameV);
			//pidObj.attr("value", pidV);
			nameVObj.val(nameV);
			pidObj.val(pidV);
		}
		
		function showMenu() {
			var cityObj = $("#resourceParentName");
			var cityOffset = $("#resourceParentName").offset();
			$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown);
		}
		
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#resTree"), setting);
		});
		//-->
	</SCRIPT>
	</body>
</html>
