$(document).ready(function() {
	createTable();

	checkAll();
	
	$(document).on("click", ".query-list-editImg", function() {
		$("#queryForm").attr("action",rfPath + '/permission/update/' + $(this).children().val());
		$("#queryForm").submit(); 
	});
	 
	$(document).on("click", ".query-list-deleteImg", function() {
		if(!confirm("您确定要删除该数据?")){
			return;
		}
		
		$.ajax({
		    url: rfPath + '/permission/delete/' + $(this).children().val(),
		    type: 'POST',
		    dataType: 'json',
		    success: function(data){
	    		alert(data.text);
	    		queryTable();
		    },
		    error: function(){
		    	alert("权限删除失败，请重试！");
		    }
		});
	});
});

function createTable(){
	var table=$("#queryList").table({
		autoWidth:false,
		 filter:true,
		 ajaxsource:rfPath + "/permission/listPermissions",
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
				var sReturn=obj.aData["permissionId"];
					sReturn="<input type='checkbox' value='"+sReturn+"' class='checkbox'/>";
					return sReturn;
				},"bSortable": false
			},
			{ "mDataProp": "permissionName" },
			{
				"sDefaultContent":'权限描述',
				"bSortable": false,
				"fnRender":function(obj){
					var value=obj.aData["permissionRemark"];
					var sReturn='<textarea rows="2" cols="30" readonly="true">'+value+'</textarea>';
					return sReturn;
				}
			},
			{
				"sDefaultContent":'资源列表',
				"bSortable": false,
				"fnRender":function(obj){
					var sReturn='';
					$.each(obj.aData["umResources"], function(index, val){
						sReturn+=val["resourceName"]+'&nbsp';
					});
					return sReturn;
				}
			},
			{ 
				"sDefaultContent":'操作',
				"sWidth":"100px",
				"bSortable": false,
			    "fnRender":function(obj){
				var value=obj.aData["permissionId"];
				var sReturn="<span class='query-list-editImg spanIcon spanEditIcon' title='修改'>"+
				"<input type='hidden' value='"+value+"'\/></span>"+
				"<span class='query-list-deleteImg spanIcon spanDeletetIcon' title='删除'>"+"<input type='hidden' value='"+value+"'<\/><\/span>";
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
	params.push({"name":"parmUrl","value":rfPath + "/permission/listPermissions"});
	$("#queryList").table("queryData",params);
	$(".check_all").prop("checked", false);
}
function toAdd(){
	$("#queryForm").attr("action",rfPath + "/permission/toAdd");
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
		    url: rfPath + '/permission/batchDelete',
		    type: 'POST',
		    dataType: 'json',
		    data: {ids:arr},
		    success: function(data){
	    		alert(data.text);
	    		queryTable();
		    },
		    error: function(){
		    	alert("权限删除失败，请重试！");
		    }
		});
	}
}