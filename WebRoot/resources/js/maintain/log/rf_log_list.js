$(document).ready(function() {
	createTable();
	
	$(document).on("click", ".query-list-showImg", function() {
		$("#queryForm").attr("action",rfPath + '/rflog/show/' + $(this).children().val());
		$("#queryForm").submit(); 
	});
});

function createTable(){
	var table=$("#queryList").table({
		autoWidth:false,
		 filter:true,
		 ajaxsource:rfPath + "/rflog/listLogs",
		 ajaxdataprop:"aaData",
		 scrolly:"100%",
		 scrollx:"100%",
		 // "bStateSave":true,
		 scrollxInner:"100%",
		 aoColumns: [
			/*{ "sDefaultContent":'选择',
			  "sWidth":"50px",
			  "sClass":"center",
			  "fnRender":function(obj){
				var sReturn=obj.aData["id"];
					sReturn="<input type='checkbox' value='"+sReturn+"' />";
					return sReturn;
				},"bSortable": false
			},*/
			{ "mDataProp": "actionUserName" },
			{ "mDataProp": "actionUserIp" },
			{ "mDataProp": "actionTime" },
			{ "mDataProp": "actionResult" },
			{
				"sDefaultContent":'用户行为',
				"bSortable": false,
				"fnRender":function(obj){
					var value=obj.aData["actionLogDescription"];
					var sReturn='<textarea rows="2" cols="30" readonly="true">'+value+'</textarea>';
					return sReturn;
				}
			},
			{ "mDataProp": "actionLogSpentMilliseconds" },
			{ 
				"sDefaultContent":'操作',
				"sWidth":"100px",
				"bSortable": false,
			    "fnRender":function(obj){
				var value=obj.aData["id"];
				var sReturn="<span class='query-list-showImg spanIcon spanShowIcon' title='查看'>"+
				"<input type='hidden' value='"+value+"'\/></span>";
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
	params.push({"name":"parmUrl","value":rfPath + "/rflog/listLogs"});
	$("#queryList").table("queryData",params);
}