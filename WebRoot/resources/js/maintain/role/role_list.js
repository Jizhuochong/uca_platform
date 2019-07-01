$(document).ready(function() {
	createTable();
	
	checkAll();
	
	$(document).on("click", ".check_all", function(){
		if($(this).prop("checked")) {
			$.each($(".checkbox"), function(index, item){
				$(item).prop("checked", true);
			});
		}else{
			$.each($(".checkbox"), function(index, item){
				$(item).prop("checked", false);
			});
		}
	});
	
	$(document).on("click", ".query-list-editImg", function() {
		$("#queryForm").attr("action",rfPath + '/role/update/' + $(this).children().val()); 
		$("#queryForm").submit();
	});
	 
	$(document).on("click", ".query-list-deleteImg", function() {
		if(!confirm("您确定要删除该数据?")){
			return;
		}
		$.ajax({
		    url: rfPath + '/role/delete/' + $(this).children().val(),
		    type: 'POST',
		    dataType: 'json',
		    success: function(data){
	    		alert(data.text);
	    		queryTable();
		    },
		    error: function(){
		    	alert("角色删除失败，请重试！");
		    }
		});
	});
	
	$(document).on("click", ".query-list-configImg", function(){
		$("#queryForm").attr("action",rfPath + '/resource/toRoleTree/' + $(this).children().val()); 
		$("#queryForm").submit();
	});
});

function createTable(){
	var table=$("#queryList").table({
		autoWidth:false,
		 filter:true,
		 ajaxsource:rfPath + "/role/listRoles",
		 ajaxdataprop:"aaData",
		 scrolly:"100%",
		 scrollx:"100%",
		 // "bStateSave":true,
		 scrollxInner:"100%",
		 aoColumns: [
			{ "sDefaultContent":'选择',
			  "sWidth":"50px",
			  "sClass":"center",
			  "fnRender":function(obj){
				var sReturn=obj.aData["roleId"];
					sReturn="<input type='checkbox' value='"+sReturn+"' class='checkbox'/>";
					return sReturn;
				},"bSortable": false
			},
			{ "mDataProp": "roleName" },
			{ "mDataProp": "roleCode"},
			{
				"sDefaultContent":'角色状态',
				"bSortable": false,
				"fnRender":function(obj){
					var value=obj.aData["roleStatus"];
					var sReturn=(value=="1"?'有效':'<font color="red">无效</font>');
					return sReturn;
				}
			},
			{
				"sDefaultContent":'角色描述',
				"bSortable": false,
				"fnRender":function(obj){
					var value=obj.aData["roleRemark"];
					//var sReturn='<textarea rows="2" cols="30" readonly="true">'+value+'</textarea>';
					var sReturn='<div>'+value+'</div>';
					return sReturn;
				}
			},
			{ 
				"sDefaultContent":'操作',
				"sWidth":"180px",
				"bSortable": false,
			    "fnRender":function(obj){
				var value=obj.aData["roleId"];
				var sReturn="<span class='query-list-editImg spanIcon spanEditIcon' title='修改'>"+
				"<input type='hidden' value='"+value+"'\/></span>"+
				"<span class='query-list-deleteImg spanIcon spanDeletetIcon' title='删除'>"+
				"<input type='hidden' value='"+value+"'<\/><\/span>"+
				"<span class='query-list-configImg spanIcon spanConfigIcon' title='资源分配'>"+
				"<input type='hidden' value='"+value+"'<\/><\/span>";
				return sReturn;
				}
			}
			
		]
	});
	return table;
}
/**
 * 查询表格
 */
function queryTable() {
	var params=formToObjParms("queryForm",null);
	//动态修改表格获取数据的url
	params.push({"name":"parmUrl","value":rfPath + "/role/listRoles"});
	$("#queryList").table("queryData",params);
	$(".check_all").prop("checked", false);
}
function toAdd(){
	$("#queryForm").attr("action",rfPath + "/role/toAdd");
	$("#queryForm").submit();
}
function batchDel() {
	if(!checkChecked()){
		alert("请选择要删除的数据！");
		return false;
	}
	if(confirm("是否确定删除？")){
		var arr = new Array();
		$.each($(".checkbox"), function(index, item) {
			if($(item).prop("checked"))
				arr.push($(item).val());
		});
		$.ajax({
		    url: rfPath + '/role/batchDelete',
		    type: 'POST',
		    dataType: 'json',
		    data: {ids:arr},
		    success: function(data){
	    		alert(data.text);
	    		queryTable();
		    },
		    error: function(){
		    	alert("角色删除失败，请重试！");
		    }
		});
	}
}