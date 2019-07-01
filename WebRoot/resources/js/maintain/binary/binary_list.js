$(document).ready(function() {
	createTable();
	
	$(document).on("click", ".query-list-downloadImg", function() {
		$("#queryForm").attr("action",rfPath + '/filedownload/download/' + $(this).children().val());
		$("#queryForm").submit();
	});
	
	$(document).on("click", ".query-list-deleteImg", function() {
		if(!confirm("您确定要删除该数据?")){
			return;
		}
		
		$("#queryForm").attr("action",rfPath + '/binary/delete/' + $(this).children().val());
		$("#queryForm").submit();
	});
});

function createTable(){
	var table=$("#queryList").table({
		autoWidth:false,
		 filter:true,
		 ajaxsource:rfPath + "/binary/listBinarys",
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
				var sReturn=obj.aData["binaryId"];
					sReturn="<input type='checkbox' value='"+sReturn+"' />";
					return sReturn;
				},"bSortable": false
			},
			{ "mDataProp": "name" },
			{
				"sDefaultContent":'描述',
				"bSortable": false,
				"fnRender":function(obj){
					var value=obj.aData["description"];
					var sReturn='<textarea rows="2" cols="30" readonly="true">'+value+'</textarea>';
					return sReturn;
				}
			},
			{ "mDataProp": "extension" },
			{ "mDataProp": "createTime" },
			{ 
				"sDefaultContent":'操作',
				"sWidth":"100px",
				"bSortable": false,
			    "fnRender":function(obj){
				var value=obj.aData["binaryId"];
				var sReturn="<span class='query-list-downloadImg spanIcon spanDownloadIcon' title='下载'>"+
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
	params.push({"name":"parmUrl","value":rfPath + "/binary/listBinarys"});
	$("#queryList").table("queryData",params);
}
function toAdd(){
	$("#queryForm").attr("action",rfPath + "/binary/toAdd");
	$("#queryForm").submit();
}