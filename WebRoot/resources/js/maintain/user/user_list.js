$(document).ready(function() {
	createTable();
	
	checkAll();
	
	$(document).on("click", ".query-list-editImg", function() {
		$("#queryForm").attr("action",rfPath + '/user/update/' + $(this).children().val());
		$("#queryForm").submit(); 
	});
	 
	$(document).on("click", ".query-list-deleteImg", function() {
		if(!confirm("您确定要删除该数据?")){
			return;
		}
		$.ajax({
		    url: rfPath + '/user/delete/' + $(this).children().val(),
		    type: 'POST',
		    dataType: 'json',
		    success: function(data){
	    		alert(data.text);
	    		queryTable();
		    },
		    error: function(){
		    	alert("用户删除失败，请重试！");
		    }
		});
	});
});

function createTable(){
	var table=$("#queryList").table({
		autoWidth:false,
		 filter:true,
		 ajaxsource:rfPath + "/user/listUsers",
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
				var sReturn=obj.aData["userId"];
					sReturn="<input type='checkbox' value='"+sReturn+"' class='checkbox'/>";
					return sReturn;
				},"bSortable": false
			},
			{ "mDataProp": "userName"},
			{ "mDataProp": "userAccount"},
			{
				"sDefaultContent":'用户状态',
				"bSortable": false,
				"fnRender":function(obj){
					var value=obj.aData["userStatus"];
					var sReturn=(value=="1"?'有效':'<font color="red">无效</font>');
					return sReturn;
				}
			},
			{ "mDataProp": "userRegistTime"},
			{ 
				"sDefaultContent":'操作',
				"sWidth":"100px",
				"bSortable": false,
			    "fnRender":function(obj){
				var value=obj.aData["userId"];
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
	params.push({"name":"parmUrl","value":rfPath + "/user/listUsers"});
	$("#queryList").table("queryData",params);
	$(".check_all").prop("checked", false);
}
function toAdd(){
	$("#queryForm").attr("action",rfPath + "/user/toAdd");
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
		    url: rfPath + '/user/batchDelete',
		    type: 'POST',
		    dataType: 'json',
		    data: {ids:arr},
		    success: function(data){
	    		alert(data.text);
	    		queryTable();
		    },
		    error: function(){
		    	alert("用户删除失败，请重试！");
		    }
		});
	}
}