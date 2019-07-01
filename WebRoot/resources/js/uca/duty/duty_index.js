$(document).ready(function() {
	$('#list_data').datagrid({
	    border: false,  
	    fitColumns:true,
	    singleSelect:true,
	    collapsible:false,//是否可折叠的  
	    fit: true,//自动大小  
	    url:rfPath+'/duty/list',
	    idField:'id',  
	    pagination:true,//分页控件  
	    rownumbers:true,//行号     
	    toolbar:'#toolbar',
		columns : [ [
			{field : 'dutyDate',title : '值班日期',formatter : function(value, row, index) {
				return row.dutyDate.substring(0,11);
			}},
			{field : 'dayShiftUser',title : '白班值班人'},
			{field : 'nightShiftUser',title : '夜班值班人'},
			{field : 'leader',title:'带班领导'},
			{field : 'createTime',title : '创建日期'},
			{field : 'operation',title : '操作',formatter : function(value, row, index) {
				return "<a href='javascript:toEdit("+ row.dutyId + ");' class='easyui-linkbutton'>修改</a>" +
						"&nbsp;/&nbsp;<a href='javascript:toRemove("+ row.dutyId+ ");' class='easyui-linkbutton'>删除</a>";
			}}
		] ]
	});
	//设置分页控件  
	var pager = $('#list_data').datagrid('getPager');
	$(pager).pagination({
		beforePageText: '第',//页数文本框前显示的汉字  
	    afterPageText: '页    共 {pages} 页',  
	    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	$("#period").hide();
	$("#sd").datebox({
		formatter : formatDate
	});

	$("#ed").datebox({
		formatter : formatDate
	});
	
	$("#dutyDate").datebox({
		formatter : formatDate
	});
	
	$("#lunarCalendar").datebox({
		formatter : formatDate
	});
	
	function formatDate(date) {
		var month = (date.getMonth() + 1);
		var dDate = date.getDate();
		if(month < 10){
			month = "0" + month;
		}
		if(dDate < 10){
			dDate = "0" + dDate;
		}
		return date.getFullYear() + "-" + month
				+ "-" + dDate;
	}
});

function toSearch() {
	$('#list_data').datagrid('load', {
		bgTime : $('#sd').datebox('getValue'),
	 	endTime : $('#ed').datebox('getValue')
	});
}

function clearForm() {
	$('#sd').datebox('clear');
	$('#ed').datebox('clear');
}

function toAdd(){
	$('#form_edit').form("clear");
	$("#addButton").show();
	$('#edit_dialog').dialog('open').dialog('setTitle','添加');
}

var tel = /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^[0−9]3,4 [0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)|(13\d{9}$)|(15[0135-9]\d{8}$)|(18[267]\d{8}$)/;

function commit(){
	var dutyId = $("#dutyId").val(), dutyDate = $("#dutyDate").datebox('getValue'), 
	dayShiftUser = $("#dayShiftUser").val(), nightShiftUser = $("#nightShiftUser").val(), 
	leader = $("#leader").val();

	if(dutyDate == null || dutyDate.length == 0 || dutyDate == ""){
		parent.$.messager.alert('提示', "请选择值班日期！");
		return;
	}
	
	/*if(dayShiftUser == null || dayShiftUser.length == 0 || dayShiftUser == ""){
		parent.$.messager.alert('提示', "请输入白班值班人！");
		return;
	}
	
	if(nightShiftUser == null || nightShiftUser.length == 0 || nightShiftUser == ""){
		parent.$.messager.alert('提示', "请输入夜班值班人！");
		return;
	}*/
	
	/*if(leader == null || leader.length == 0 || leader == ""){
		parent.$.messager.alert('提示', "请输入带班领导！");
		return;
	}*/
	
	var options = {
		type : 'post',
		dataType : 'json',
		url:rfPath+'/duty/save',
		success : function(data) {
			if(data.success){
	    		$('#edit_dialog').dialog('close');
	    		parent.$.messager.alert('提示', data.msg);
	    	}else{
	    		parent.$.messager.alert('提示', data.msg);
	    	}
//			parent.updateTab();
			$('#list_data').datagrid('reload');
		}
	};
	parent.$.messager.confirm('Confirm','是否确定保存？',function(r){
      	if (r){
      		$("#form_edit").ajaxSubmit(options);
      	}
 	});
	
	/*parent.$.messager.confirm('Confirm','是否确定添加？',function(r){
  	if (r){
		$.ajax({
		    url: rfPath + '/duty/save',
		    type: 'POST',
		    dataType: 'json',
		    data: {
		    	dutyId : dutyId,
		    	dutyTime : dutyTime,
		    	dutyUser : dutyUser,
		    	contactPhone : contactPhone,
		    	remarks : remarks
		    },
		    success: function(data){
		    	if(data.success) {
		    		parent.$.messager.alert('提示', data.msg);
		    		$('#list_data').datagrid('reload');
		    		$('#edit_dialog').dialog('close');
		    	} else {
		    		parent.$.messager.alert('提示', data.msg);
		    	}
		    },
		    error: function(){
		    	parent.$.messager.alert('提示', "添加失败，请重试！");
		    }
		});
  	}
	});*/
};

function toEdit(catalogId){
	$.ajax({
	    url: rfPath + '/duty/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: {
	    	id : catalogId
	    },
	    success: function(data){
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#dutyId').val(obj.dutyId == null || obj.dutyId == 0?"":obj.dutyId);
	    		$('#dayShiftUser').val(obj.dayShiftUser != null?obj.dayShiftUser:"");
	    		$('#nightShiftUser').val(obj.nightShiftUser != null?obj.nightShiftUser:"");
	    		$("#dutyDate").datebox('setValue',obj.dutyDate != null?obj.dutyDate:"");
	    		$("#lunarCalendar").datebox('setValue',obj.lunarCalendar != null?obj.lunarCalendar:"");
	    		$('#leader').val(obj.leader != null?obj.leader:"");
	    		$("#addButton").show();
	    		$('#edit_dialog').dialog('open').dialog('setTitle','编辑');
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
}

function toRemove(dId){
	parent.$.messager.confirm('Confirm','是否确定删除？',function(r){
      	if (r){
      		$.ajax({
      		    url: rfPath + '/duty/delete',
      		    type: 'POST',
      		    dataType: 'json',
      		    data: {
      		    	dId : dId
      		    },
      		    success: function(data){
      		    	if(data.success) {
      		    		parent.$.messager.alert('提示', data.msg);
      		    		$('#list_data').datagrid('reload');
      		    	} else {
      		    		parent.$.messager.alert('提示', data.msg);
      		    	}
      		    },
      		    error: function(){
      		    	parent.$.messager.alert('提示', "删除失败，请重试！");
      		    }
      		});
      	}
 	});
};

function toImport(){
	$('#materialImport').form("clear");
	$('#import_dialog').dialog('open').dialog('setTitle','批量导入');
}

function importExcel() {
	var fileItemV = $("#fileItem").val();
	if (fileItemV == "") {
		parent.$.messager.alert('提示', "选择批量导入数据文件！");
		return false;
	}
	var i = fileItemV.indexOf(".xlsx");
    if (i == -1) {
        i = fileItemV.indexOf(".xls");
    }
	if (i == -1) {
		parent.$.messager.alert('提示', "批量导入数据文件只能是xls或xlsx格式！");
		return false;
	}
	var options = {
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.success){
				parent.$.messager.alert('提示', data.msg);
	    		$('#list_data').datagrid('reload');
	    		$('#import_dialog').dialog('close');
			} else {
				parent.$.messager.alert('提示', "批量导入失败："+data.msg+"！");
			}
		}
	};
	$("#materialImport").ajaxSubmit(options);
}