$(document).ready(function(){
	$('#dMrId').combobox({
		onSelect: function(record){
			showMrInfo($('#dMrId').combobox('getValue'));
		}
	});
	
	$("#qDate").datebox({
		formatter : formatDate
	});

	function formatDate(date) {
		return date.getFullYear() + "-" + (date.getMonth() + 1)
				+ "-" + date.getDate();
	}
	
	$(document).on("click", ".selwin", function(){
		selWin($(this).parent());
	});
	
	$(document).on("click", ".showwin", function(){
		showWin($(this).parent());
	});
}); 

function showMrInfo(inf){
	if(inf != ""){
		var arrInf = inf.split("|");
		$('#mrId').val(arrInf[0]);
		$('#dAddress').text("会议室地址："+arrInf[1]);
		$('#dPeopleCount').text("可容纳人数："+arrInf[2]);
	}else{
		$('#dAddress').text("");
		$('#dPeopleCount').text("");
	}
}

//搜索
function toSearch(){
	var dataObj = null;
	var dMrId = $("#dMrId").combobox('getValue');
	if(dMrId == ""){
		parent.$.messager.alert('提示', "请选择会议室！");
		return false;
	}
	
	var qDate = $('#qDate').datebox('getValue');
	
	if(qDate != ''){
		dataObj = {dMrId : dMrId.split("|")[0], qDate : qDate};
	}else{
		dataObj = {dMrId : dMrId.split("|")[0]};
	}
	
	$("#list_data").html("");
	$.ajax({
	    url: rfPath + '/mrapply/list',
	    type: 'POST',
	    dataType: 'json',
	    data: dataObj,
	    success: function(data){
	    	if(data.success) {
	    		var voList = data.voList;
	    		var trObj = null;
	    		for(var i = 0;i < voList.length;i++){
	    			if(i % 5 == 0){
	    				trObj = '<tr height="70" align="center">';
	    				trObj += buildTd(voList[i]);
	    			}else if((i + 1) % 5 == 0){
	    				trObj += buildTd(voList[i]);
	    				trObj += '</tr>';
	    				//添加行
	    				$(trObj).appendTo($("#list_data"));
	    			}else if(i == voList.length - 1){
	    				trObj += buildTd(voList[i]);
	    				trObj += '</tr>';
	    				//添加行
	    				$(trObj).appendTo($("#list_data"));
	    			}else{
	    				trObj += buildTd(voList[i]);
	    			}
	    		}
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
}

function buildTd(voObj){
	var tdObj = '<td vle="'+voObj.winTime+'" apid="'+voObj.applyId+'" class="" '+(2 == voObj.applyStatus?'bgcolor="#6FE55E"':(1 == voObj.beloneStatus?'bgcolor="#FF0000"':'bgcolor="#E0E0E0"'))+'><div class="selwin">'+voObj.winTimeShow+'</div><div class="showwin">查看</div></td>';
	return tdObj;
}
//onclick="selWin(this)" ondbclick="showWin(this)"

function selWin(tdObj){
	var flag = tdObj.attr("bgcolor");
	if(flag.toUpperCase() == '#E0E0E0'){
		return false;
	}
	if(flag.toUpperCase() == '#FF0000'){
		var apid = tdObj.attr("apid");
		if(apid != 0){
			if(tdObj.attr("class") == ""){
				$("#list_data").find("td").each(function(){
					if($(this).attr("apid") == apid){
						$(this).addClass("sel");
					}
				});
			}else{
				$("#list_data").find("td").each(function(){
					if($(this).attr("apid") == apid){
						$(this).removeClass("sel");
					}
				});
			}
		}
	}else if(flag.toUpperCase() == '#6FE55E'){
		if(tdObj.attr("class") == ""){
			tdObj.addClass("sel");
		}else{
			tdObj.removeClass("sel");
		}
	}
}

function showWin(tdObj){
	var flag = tdObj.attr("bgcolor");
	if(flag.toUpperCase() == '#6FE55E'){
		parent.$.messager.alert('提示', "该时段没有预约。");
		return false;
	}
	var apid = tdObj.attr("apid");
	var dataObj = {applyId : apid};
	$.ajax({
	    url: rfPath + '/mrapply/getObjById',
	    type: 'POST',
	    dataType: 'json',
	    data: dataObj,
	    success: function(data){
//	    	$('#form_show').form("clear");
	    	if(data.success) {
	    		var obj = data.objPo;
	    		$('#cfName').val(obj.conferenceName != null?obj.conferenceName:"");
	    		$("#mName").val(obj.meetingName != null?obj.meetingName:"");
	    		var sps = "";
	    		if(obj.ucaMrApplyUser.length > 0){
	    			sps = obj.ucaMrApplyUser[0].userName;
	    			for(var i = 1;i < obj.ucaMrApplyUser.length;i++){
	    				sps += "," + obj.ucaMrApplyUser[i].userName;
	    			}
	    		}
	    		$('#showPs').val(sps);
	    		
	    		var devicesName = "";
	    		var devices = obj.devices;
	    		if(devices != null && devices != "" && devices.length > 0) {
	    			var deviceArray = new Array();
		    		deviceArray=devices.split(",");
		    		for (i=0;i<deviceArray.length;i++){
		    			if(deviceArray[i] == 1) {
		    				devicesName += "投影仪,";
		    			}
		    			if(deviceArray[i] == 2) {
		    				devicesName += "电视,";
		    			}
		    			if(deviceArray[i] == 3) {
		    				devicesName += "麦克,";
		    			}
		    			if(deviceArray[i] == 4) {
		    				devicesName += "电脑,";
		    			}
		    			if(deviceArray[i] == 5) {
		    				devicesName += "会标,";
		    			}
		    			if(deviceArray[i] == 6) {
		    				devicesName += "桌签,";
		    			}
		    	    }
		    		devicesName = devicesName.substring(0,devicesName.length-1);
	    		}
	    		$('#devicesName').val(devicesName);
	    		
	    		$('#remk').val(obj.remark != null?obj.remark:"");
	    		$('#applyName').val(obj.applyName != null?obj.applyName:"");
	    		$('#applyOrg').val(obj.applyOrg != null?obj.applyOrg:"");
	    		$('#applyDt').val(obj.applyDate != null?obj.applyDate:"");
	    		$('#show_dialog').dialog('open').dialog('setTitle','预约会议查看');
	    	} else {
	    		parent.$.messager.alert('提示', data.msg);
	    	}
	    }
	});
}

function getObjByParam(flg){
	var objId = $('#mrId').val();
	if(objId == null || objId == ''){
		parent.$.messager.alert('提示', "请选择会议室！");
		return false;
	}
	var vleStr = "";
	var cacheInd = 0;
	var cacheVle = 0;
	var repeatFlag = false;
	$(".sel").each(function(){
		if(flg == 1 && $(this).attr("bgcolor") == "#FF0000"){
			repeatFlag = true;
			return false;
		}else if(flg == 2 && $(this).attr("bgcolor") == "#6FE55E"){
			repeatFlag = true;
			return false;
		}
		cacheVle = $(this).attr("vle");
		if(cacheInd == 0 && vleStr.length == 0){
			vleStr += cacheVle;
		}else if(cacheVle - cacheInd > 1){
			vleStr += "|" + cacheVle;
		}else if(cacheVle - cacheInd == 1){
			vleStr += "," + cacheVle;
		}
		cacheInd = cacheVle;
	});
	if(flg == 1 && repeatFlag){
		parent.$.messager.alert('提示', "请勿重复预约！");
		return false;
	}
	if(flg == 2 && repeatFlag){
		parent.$.messager.alert('提示', "您选择的时间段还未预约！");
		return false;
	}
	if(vleStr == ""){
		parent.$.messager.alert('提示', "请选择会议时间段！");
		return false;
	}
	$('#vleStr').val(vleStr);
	$('#flag').val(flg);
	var apDate = $('#qDate').datebox('getValue');
	if(apDate != null && apDate !=""){
		$('#applyDate').val(apDate);
	}
	if(flg == 1){
		$('#edit_dialog').dialog('open').dialog('setTitle','预约信息填报');
	}else{
		var options = {
			type : 'post',
			dataType : 'json',
			url:rfPath+'/mrapply/save',
			success : function(data) {
				if(data.success){
		    		$('#edit_dialog').dialog('close');
		    		parent.$.messager.alert('提示', data.msg);
		    	}else{
		    		parent.$.messager.alert('提示', data.msg);
		    	}
//				parent.updateTab();
				toSearch();
			}
		};
		parent.$.messager.confirm('Confirm','是否确定保存？',function(r){
	      	if (r){
	      		$("#form_edit").ajaxSubmit(options);
	      	}
	 	});
	}
}

function selectDevices() {
	var devices = document.getElementsByName("deviceIds");
	var objarray = devices.length;
	var checks = "";
	for (var i = 0; i < objarray; i++) {
		if (devices[i].checked == true) {
			checks += devices[i].value + ",";
		}
	}
	$("#devices").val(checks);
}

function commit(){
	selectDevices();
	if($('#conferenceName').val() == ""){
		parent.$.messager.alert('提示', "请输入会议名称！");
		return false;
	}
	if($('#participants').val() == ""){
		parent.$.messager.alert('提示', "请选择参会人员！");
		return false;
	}
//	if($('#devices').val() == ""){
//		parent.$.messager.alert('提示', "请选择会议设备！");
//		return false;
//	}
	
	var options = {
		type : 'post',
		dataType : 'json',
		url:rfPath+'/mrapply/save',
		success : function(data) {
			if(data.success){
	    		$('#edit_dialog').dialog('close');
	    		parent.$.messager.alert('提示', data.msg);
	    	}else{
	    		parent.$.messager.alert('提示', data.msg);
	    	}
//			parent.updateTab();
			toSearch();
		}
	};
	parent.$.messager.confirm('Confirm','是否确定保存？',function(r){
      	if (r){
      		$("#form_edit").ajaxSubmit(options);
      	}
 	});
}

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
		url:rfPath+"/org/orgAndUserTree",
		autoParam:["id"],
		otherParam:{"type":3,"userIds":$('#participants').val()},
		dataFilter: filter
	},
	check: {
        enable: true,
        chkStyle: "checkbox"//多选
    },
	callback: {
		onCheck: checkTreeVal
	}
};

function checkTreeVal(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//	nodes = zTree.getSelectedNodes(),
	nodes = zTree.getCheckedNodes(true);
	nameV = "";
	pidV = "";
	nodes.sort(function compare(a,b){return a.id-b.id;});
	for (var i=0, l=nodes.length; i<l; i++) {
		if(nodes[i].tp == 9){
			nameV += nodes[i].name + ",";
			pidV += nodes[i].id + ",";
		}
	}
	if (nameV.length > 0 ) nameV = nameV.substring(0, nameV.length-1);
	if (pidV.length > 0 ) pidV = pidV.substring(0, pidV.length-1);
	
	$("#showParticipants").val(nameV);
	$("#participants").val(pidV);
}

function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

function showMenu() {
	var cityObj = $("#showParticipants");
	var cityOffset = $("#showParticipants").offset();
	$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
	$.fn.zTree.init($("#treeDemo"), setting);
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

/*$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting);
});*/