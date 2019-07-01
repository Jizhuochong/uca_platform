$(document).ready(function() {
	createTable();
	
	checkAll();
	
	$(document).on("click", ".query-list-editImg", function() {
		$("#queryForm").attr("action",rfPath + '/resource/update/' + $(this).children().val());
		$("#queryForm").submit(); 
	});
	 
	$(document).on("click", ".query-list-deleteImg", function() {
		if(!confirm("您确定要删除该数据?")){
			return;
		}
		
		$.ajax({
		    url: rfPath + '/resource/delete/' + $(this).children().val(),
		    type: 'POST',
		    dataType: 'json',
		    success: function(data){
	    		alert(data.text);
	    		queryTable();
		    },
		    error: function(){
		    	alert("资源删除失败，请重试！");
		    }
		});
	});
});

function createTable(){
	var table=$("#queryList").table({
		autoWidth:false,
		 filter:true,
		 ajaxsource:rfPath + "/resource/listResources",
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
				var sReturn=obj.aData["resourceId"];
					sReturn="<input type='checkbox' value='"+sReturn+"' class='checkbox'/>";
					return sReturn;
				},"bSortable": false
			},
			{ "mDataProp": "resourceName" },
			{
				"sDefaultContent":'资源类型',
				"bSortable": false,
				"fnRender":function(obj){
					var value=obj.aData["resourceStatus"];
					var sReturn=(value=="1"?'URL':'<font color="red">NO URL</font>');
					return sReturn;
				}
			},
			/*{
				"sDefaultContent":'资源等级',
				"bSortable": false,
				"fnRender":function(obj){
					var value=obj.aData["resourceType"];
					var sReturn = '';
					switch(value){
					case '1':
						sReturn = '一级';
						break;
					case '2':
						sReturn = '二级';
						break;
					case '3':
						sReturn = '三级';
						break;
					case '4':
						sReturn = '四级';
						break;
					case '5':
						sReturn = '五级';
						break;
					default:
						break;
					}
					return sReturn;
				}
			},*/
			{ "mDataProp": "resourceUrl" },
			/*{ "mDataProp": "resourceUrlDirection" },*/
			{ "mDataProp": "resourceSort" },
			{
				"sDefaultContent":'资源状态',
				"bSortable": false,
				"fnRender":function(obj){
					var value=obj.aData["resourceStatus"];
					var sReturn=(value=="1"?'有效':'<font color="red">无效</font>');
					return sReturn;
				}
			},
			{
				"sDefaultContent":'资源显示',
				"bSortable": false,
				"fnRender":function(obj){
					var value=obj.aData["resourceShowflag"];
					var sReturn=(value=="1"?'显示':'<font color="red">不显示</font>');
					return sReturn;
				}
			},
			{
				"sDefaultContent":'角色列表',
				"bSortable": false,
				"fnRender":function(obj){
					var sReturn='';
					$.each(obj.aData["umRoles"], function(index, val){
						sReturn+=val["roleName"]+'&nbsp';
					});
					return sReturn;
				}
			},
			/*{
				"sDefaultContent":'权限列表',
				"bSortable": false,
				"fnRender":function(obj){
					var sReturn='';
					$.each(obj.aData["umPermissions"], function(index, val){
						sReturn+=val["permissionName"]+'&nbsp';
					});
					return sReturn;
				}
			},*/
			{ 
				"sDefaultContent":'操作',
				"sWidth":"100px",
				"bSortable": false,
			    "fnRender":function(obj){
				var value=obj.aData["resourceId"];
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
	params.push({"name":"parmUrl","value":rfPath + "/resource/listResources"});
	$("#queryList").table("queryData",params);
	$(".check_all").prop("checked", false);
}
function toAdd(){
	$("#queryForm").attr("action",rfPath + "/resource/toAdd");
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
		    url: rfPath + '/resource/batchDelete',
		    type: 'POST',
		    dataType: 'json',
		    data: {ids:arr},
		    success: function(data){
	    		alert(data.text);
	    		queryTable();
		    },
		    error: function(){
		    	alert("资源删除失败，请重试！");
		    }
		});
	}
}