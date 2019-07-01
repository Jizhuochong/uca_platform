function commit(prm){
	if(prm == 1 && $("#handingPersonId").val() == ""){
		alert("请选择经办人！");
		return;
	}
	$("#checkStatus").val(prm);
	
	$.ajax({
	    url: rfPath + '/archives/save',
	    type: 'POST',
	    contentType: "application/json; charset=utf-8",
	    dataType: 'json',
	    data: $.toJSON(obj),
	    success: function(data){
	    	//如果是送审无条件清空表单
	    	if($("#checkStatus").val() == "1"){
	    		alert(data.text);
	    		$("#form_edit").form("clear");
	    	}else{
	    		if(confirm(data.text+"\n点击确定继续上传")){
		    		//清空表单
	    			$("#form_edit").form("clear");
		    	}
	    	}
	    },
	    error: function(){
	    	alert("提交保存失败，请重试！");
	    }
	});
}

/* function fileChange(obj){
	var objUrl = getObjectURL(obj.files[0]) ;
	console.log("objUrl = "+objUrl) ;
	if (objUrl) {
		$("#imgUrl").attr("src", objUrl) ;
	}
};

//建立一個可存取到該file的url
function getObjectURL(file) {
	var url = null ; 
	if (window.createObjectURL!=undefined) { // basic
		url = window.createObjectURL(file) ;
	} else if (window.URL!=undefined) { // mozilla(firefox)
		url = window.URL.createObjectURL(file) ;
	} else if (window.webkitURL!=undefined) { // webkit or chrome
		url = window.webkitURL.createObjectURL(file) ;
	}
	return url ;
} */

$(function(){
	$('#handingPersonId').combobox('reload', rfPath + '/ucasecurity/getHandingPersonList');
});

function ajaxFileUpload(){
    $("#loading").show();
    $.ajaxFileUpload
    (
        {
           url:rfPath + '/upload_file/upload',
           secureuri:false,
           fileElementId:'fileItem',
           dataType: 'json',
           success: function (data, status){
			   $("#fileUrl").val(data.fileUrl);
			   $("#fileDown").attr("href",data.fileUrl);
			   $("#sourceFileName").val(data.fileName);
			   $("#loading").hide();
            },
            error: function (data, status, e){
            	$("#loading").hide();
                alert(e);
            }
        }
    )
    return false;
}