$(document).ready(function() {
	$('#list_data').datagrid({
		title : '区县档案统计表',
		rownumbers : true,// 行号
		singleSelect : true,
		border : false,
		fitColumns : true,
		loadMsg : '正在加载...',
		fit : true,// 自动大小
		collapsible:false,//是否可折叠的 
		url : rfPath + '/orgarchivesstatistics/list',
		method : 'post',
		idField : 'id',
		pagination : true,// 分页控件
		nowrap:true,
		striped:true,
		collapsible:true,
		toolbar : '#toolbar',
		columns : [ [
		    {field : 'yv',title : '年度'},
			{field : 'ona',title : '填报单位'},
			{field : 'un',title : '单位负责人'},
			{field : 'cun',title : '填表人'},
			{field : 'up',title : '联系电话'},
			{field : 'ct',title : '填报日期'},
			{field : 'c1',title : '定编(人)'},
			{field : 'c2',title : '现有全部专职人员(人)'},
			{field : 'c3',title : '现有全部专职人员女性(人)'},
			{field : 'c4',title : '现有全部专职人员情况年龄50岁及以上(人)'},
			{field : 'c5',title : '现有全部专职人员情况年龄35—49岁(人)'},
			{field : 'c6',title : '现有全部专职人员情况年龄34岁及以下(人)'},
			{field : 'c7',title : '现有全部专职人员情况文化程度博士研究(人)'},
			{field : 'c8',title : '现有全部专职人员情况文化程度硕士研究生(人)'},
			{field : 'c9',title : '现有全部专职人员情况文化程度研究生班研究生(人)'},
			{field : 'c10',title : '现有全部专职人员情况文化程度双学士(人)'},
			{field : 'c11',title : '现有全部专职人员情况文化程度大学本科(人)'},
			{field : 'c12',title : '现有全部专职人员情况文化程度大专(人)'},
			{field : 'c13',title : '现有全部专职人员情况文化程度中专(人)'},
			{field : 'c14',title : '现有全部专职人员情况文化程度高中(人)'},
			{field : 'c15',title : '现有全部专职人员情况文化程度初中及以下(人)'},
			{field : 'c16',title : '现有全部专职人员情况档案专业程度博士研究生(人)'},
			{field : 'c17',title : '现有全部专职人员情况档案专业程度硕士研究生(人)'},
			{field : 'c18',title : '现有全部专职人员情况档案专业程度研究生班研究生(人)'},
			{field : 'c19',title : '现有全部专职人员情况档案专业程度大学本科(人)'},
			{field : 'c20',title : '现有全部专职人员情况档案专业程度大专(人)'},
			{field : 'c21',title : '现有全部专职人员情况档案专业程度中专(人)'},
			{field : 'c22',title : '现有全部专职人员情况档案专业程度职业高中(人)'},
			{field : 'c23',title : '现有全部专职人员情况档案干部专业技术职务研究馆员(人)'},
			{field : 'c24',title : '现有全部专职人员情况档案干部专业技术职务副研究馆员(人)'},
			{field : 'c25',title : '现有全部专职人员情况档案干部专业技术职务馆员(人)'},
			{field : 'c26',title : '现有全部专职人员情况档案干部专业技术职务助理馆员(人)'},
			{field : 'c27',title : '现有全部专职人员情况档案干部专业技术职务管理员(人)'},
			{field : 'c28',title : '现有全部专职人员情况接受在职培训教育(人)'},
			{field : 'c29',title : '馆藏档案案卷规划管理类/工程竣工类/其他类(卷)'},
			{field : 'c30',title : '馆藏档案案卷案卷排架长度(米)'},
			{field : 'c31',title : '馆藏档案案卷以件为保管单位档案(件)'},
			{field : 'c32',title : '馆藏档案案卷以件为保管单位档案排架长度(米)'},
			{field : 'c33',title : '馆藏档案案卷未整理零散文件排架长度(米)'},
			{field : 'c34',title : '馆藏档案案卷录音磁带、录像磁带、影片档案(盘)'},
			{field : 'c35',title : '馆藏档案案卷照片档案(张)'},
			{field : 'c36',title : '馆藏档案案卷实物档案(件)'},
			{field : 'c37',title : '馆藏资料(册)'},
			{field : 'c381',title : '本年进馆档案接收档案案卷规划管理类(卷)'},
			{field : 'c382',title : '本年进馆档案接收档案案卷规划工程竣工类(卷)'},
			{field : 'c383',title : '本年进馆档案接收档案案卷规划其他类(卷)'},
			{field : 'c39',title : '本年进馆档案接收档案以件为保管单位档案(件)'},
			{field : 'c40',title : '本年进馆档案接收档案录音磁带、录像磁带、影片档案(盘)'},
			{field : 'c41',title : '本年进馆档案接收档案照片档案(张)'},
			{field : 'c42',title : '本年进馆档案征集档案案卷(卷)'},
			{field : 'c43',title : '本年进馆档案征集档案以件为保管单位档案(件)'},
			{field : 'c44',title : '本年进馆档案征集档案录音磁带、录像磁带、影片档案(盘)'},
			{field : 'c45',title : '本年进馆档案征集档案照片档案(张)'},
			{field : 'c46',title : '馆藏档案的历史分期建国前档案(卷)'},
			{field : 'c47',title : '馆藏档案的历史分期建国前档案(件)'},
			{field : 'c48',title : '馆藏档案的历史分期建国前档案明清以前档案(件)'},
			{field : 'c49',title : '馆藏档案的历史分期建国前档案明清档案(卷)'},
			{field : 'c50',title : '馆藏档案的历史分期建国前档案明清档案(件)'},
			{field : 'c51',title : '馆藏档案的历史分期建国前档案民国档案(卷)'},
			{field : 'c52',title : '馆藏档案的历史分期建国前档案民国档案(件)'},
			{field : 'c53',title : '馆藏档案的历史分期建国前档案革命历史档案(卷)'},
			{field : 'c54',title : '馆藏档案的历史分期建国前档案革命历史档案(件)'},
			{field : 'c55',title : '馆藏档案的历史分期建国后档案(卷)'},
			{field : 'c56',title : '馆藏档案的历史分期建国后档案(件)'},
			{field : 'c57',title : '档案编目情况手工目录案卷目录(本)'},
			{field : 'c58',title : '档案编目情况手工目录全引目录(本)'},
			{field : 'c59',title : '档案编目情况手工目录归档文件目录(本)'},
			{field : 'c60',title : '档案编目情况手工目录专题目录簿式(本)'},
			{field : 'c61',title : '档案编目情况手工目录专题目录卡片式(张)'},
			{field : 'c62',title : '档案编目情况手工目录重要文件目录簿式(本)'},
			{field : 'c63',title : '档案编目情况手工目录重要文件目录卡片式(张)'},
			{field : 'c64',title : '档案编目情况机读目录案卷级(条/万条)'},
			{field : 'c65',title : '档案编目情况机读目录文件级(条/万条1)'},
			{field : 'c66',title : '档案编目情况机读目录文件级(条/万条2)'},
			{field : 'c67',title : '本年利用档案人次(人次)'},
			{field : 'c68',title : '本年利用档案人次其中：台港澳同胞(人次)'},
			{field : 'c69',title : '本年利用档案人次外国人(人次)'},
			{field : 'c70',title : '本年利用档案(卷次)'},
			{field : 'c71',title : '本年利用档案(件次)'},
			{field : 'c72',title : '本年利用档案所属时期建国前档案(卷次)'},
			{field : 'c73',title : '本年利用档案所属时期建国前档案(件次)'},
			{field : 'c74',title : '本年利用档案所属时期建国后档案(卷次)'},
			{field : 'c75',title : '本年利用档案所属时期建国后档案(件次)'},
			{field : 'c76',title : '本年利用档案利用者类别单位(卷次)'},
			{field : 'c77',title : '本年利用档案利用者类别单位(件次)'},
			{field : 'c78',title : '本年利用档案利用者类别个人(卷次)'},
			{field : 'c79',title : '本年利用档案利用者类别个人(件次)'},
			{field : 'c80',title : '本年利用资料(人次)'},
			{field : 'c81',title : '本年利用资料(册次)'},
			{field : 'c82',title : '本年举办档案展览(个)'},
			{field : 'c83',title : '本年举办档案展览基本陈列(个)'},
			{field : 'c84',title : '本年参观档案展览人次(人次)'},
			{field : 'c85',title : '本年编研档案资料公开出版(种)'},
			{field : 'c86',title : '本年编研档案资料公开出版(万字)'},
			{field : 'c87',title : '本年编研档案资料内部参考(种)'},
			{field : 'c88',title : '本年编研档案资料内部参考(万字)'},
			{field : 'c89',title : '档案馆总建筑面积(平方米)'},
			{field : 'c90',title : '档案馆总建筑面积档案库房建筑面积(平方米)'},
			{field : 'c91',title : '档案馆总建筑面积后库面积(平方米)'},
			{field : 'c92',title : '档案馆总建筑面积技术用房建筑面积(平方米)'},
			{field : 'c93',title : '档案馆总建筑面积对外服务用房建筑面积(平方米)'},
			{field : 'c94',title : '馆内设备电子计算机服务器(台)'},
			{field : 'c95',title : '馆内设备电子计算机微机(台)'},
			{field : 'c96',title : '馆内设备复印机(台)'},
			{field : 'c97',title : '馆内设备空调机集中式(台)'},
			{field : 'c98',title : '馆内设备空调机分散式(台)'},
			{field : 'c99',title : '馆内设备去湿机(台)'},
			{field : 'c100',title : '馆内设备消毒设备(台)'},
			{field : 'c101',title : '馆内设备数字化扫描仪(台)'},
			{field : 'c102',title : '馆藏数字档案归档电子文件电子文件(件)'},
			{field : 'c103',title : '馆藏数字档案归档电子文件电子文件(GB)'},
			{field : 'c104',title : '馆藏数字档案归档电子文件数码照片(张)'},
			{field : 'c105',title : '馆藏数字档案归档电子文件数码照片(GB)'},
			{field : 'c106',title : '馆藏数字档案归档电子文件数字录音、数字录像(小时)'},
			{field : 'c107',title : '馆藏数字档案归档电子文件数字录音、数字录像(GB)'},
			{field : 'c108',title : '馆藏数字档案馆藏档案数字化副本规划管理类已完成数量(卷)'},
			{field : 'c109',title : '馆藏数字档案馆藏档案数字化副本规划管理类已完成数量(幅)'},
			{field : 'c110',title : '馆藏数字档案馆藏档案数字化副本规划管理类已完成数量(GB)'},
			{field : 'c111',title : '馆藏数字档案馆藏档案数字化副本规划管理类未完成数量(卷)'},
			{field : 'c112',title : '馆藏数字档案馆藏档案数字化副本工程竣工类档案已完成数量(卷)'},
			{field : 'c113',title : '馆藏数字档案馆藏档案数字化副本工程竣工类档案已完成数量(幅)'},
			{field : 'c114',title : '馆藏数字档案馆藏档案数字化副本工程竣工类档案已完成数量(GB)'},
			{field : 'c115',title : '馆藏数字档案馆藏档案数字化副本工程竣工类档案未完成数量(卷)'},
			{field : 'c116',title : '馆藏数字档案馆藏档案数字化副本其它类档案已完成数量(卷)'},
			{field : 'c117',title : '馆藏数字档案馆藏档案数字化副本其它类档案已完成数量(幅)'},
			{field : 'c118',title : '馆藏数字档案馆藏档案数字化副本其它类档案已完成数量(GB)'},
			{field : 'c119',title : '馆藏数字档案馆藏档案数字化副本其它类档案未完成数量(卷)'},
			{field : 'c120',title : '馆藏数字档案数码照片(张)'},
			{field : 'c121',title : '馆藏数字档案数码照片(GB)'},
			{field : 'c122',title : '馆藏数字档案数字录音、录像(小时)'},
			{field : 'c123',title : '馆藏数字档案数字录音、录像(GB)'}
		] ]
	});
	//设置分页控件  
	var pager = $('#list_data').datagrid('getPager');
	$(pager).pagination({
		beforePageText: '第',//页数文本框前显示的汉字  
	    afterPageText: '页    共 {pages} 页',  
	    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
});

function toAdd(){
	$('#formName_add').form("clear");
	$('#dialog').dialog('open').dialog('setTitle','添加');
}

function isNull(obj) {
	if(obj == null || obj.length == 0 || obj == ""){
		return true;
	} else {
		return false;
	}
}

function commit(){
	if(isNull($("#yearValue").val())){
		parent.$.messager.alert('提示', "请输入年度！");
		return;
	}
	if(isNull($("#orgName").val())){
		parent.$.messager.alert('提示', "请输入填报单位！");
		return;
	}
	if(isNull($("#userName").val())){
		parent.$.messager.alert('提示', "请输入单位负责人！");
		return;
	}
	if(isNull($("#createUserPhone").val())){
		parent.$.messager.alert('提示', "请输入联系电话！");
		return;
	}
	for(var i=1;i<124;i++) {
		if(i==29 || i==31 || i==46 || i==47 || i==70 || i==71) {
			continue;
		}
		if(i!=38) {
			if(isNull($("#code_"+i).val())){
				parent.$.messager.alert('提示', "请输入序号列["+i+"]选项！");
				return;
			}
		} else {
			for(var j=1;j<4;j++) {
				if(isNull($("#code_"+i+"_"+j).val())){
					parent.$.messager.alert('提示', "请输入序号列["+i+"_"+j+"]选项！");
					return;
				}
			}
		}
	}
	
	parent.$.messager.confirm('Confirm','是否确定添加？',function(r){
      	if (r){
			$.ajax({
			    url: rfPath + '/orgarchivesstatistics/commit',
			    type: 'POST',
			    dataType: 'json',
			    data: {
			    	yearValue:$("#yearValue").val(),
			    	orgName:$("#orgName").val(),
			    	userName:$("#userName").val(),
			    	createUserPhone:$("#createUserPhone").val(),
					 code_1:$("#code_1").val(),
					 code_2:$("#code_2").val(),
					 code_3:$("#code_3").val(),
					 code_4:$("#code_4").val(),
					 code_5:$("#code_5").val(),
					 code_6:$("#code_6").val(),
					 code_7:$("#code_7").val(),
					 code_8:$("#code_8").val(),
					 code_9:$("#code_9").val(),
					 code_10:$("#code_10").val(),
					 code_11:$("#code_11").val(),
					 code_12:$("#code_12").val(),
					 code_13:$("#code_13").val(),
					 code_14:$("#code_14").val(),
					 code_15:$("#code_15").val(),
					 code_16:$("#code_16").val(),
					 code_17:$("#code_17").val(),
					 code_18:$("#code_18").val(),
					 code_19:$("#code_19").val(),
					 code_20:$("#code_20").val(),
					 code_21:$("#code_21").val(),
					 code_22:$("#code_22").val(),
					 code_23:$("#code_23").val(),
					 code_24:$("#code_24").val(),
					 code_25:$("#code_25").val(),
					 code_26:$("#code_26").val(),
					 code_27:$("#code_27").val(),
					 code_28:$("#code_28").val(),
					 code_30:$("#code_30").val(),
					 code_32:$("#code_32").val(),
					 code_33:$("#code_33").val(),
					 code_34:$("#code_34").val(),
					 code_35:$("#code_35").val(),
					 code_36:$("#code_36").val(),
					 code_37:$("#code_37").val(),
					 code_38_1:$("#code_38_1").val(),
					 code_38_2:$("#code_38_2").val(),
					 code_38_3:$("#code_38_3").val(),
					 code_39:$("#code_39").val(),
					 code_40:$("#code_40").val(),
					 code_41:$("#code_41").val(),
					 code_42:$("#code_42").val(),
					 code_43:$("#code_43").val(),
					 code_44:$("#code_44").val(),
					 code_45:$("#code_45").val(),
					 code_48:$("#code_48").val(),
					 code_49:$("#code_49").val(),
					 code_50:$("#code_50").val(),
					 code_51:$("#code_51").val(),
					 code_52:$("#code_52").val(),
					 code_53:$("#code_53").val(),
					 code_54:$("#code_54").val(),
					 code_55:$("#code_55").val(),
					 code_56:$("#code_56").val(),
					 code_57:$("#code_57").val(),
					 code_58:$("#code_58").val(),
					 code_59:$("#code_59").val(),
					 code_60:$("#code_60").val(),
					 code_61:$("#code_61").val(),
					 code_62:$("#code_62").val(),
					 code_63:$("#code_63").val(),
					 code_64:$("#code_64").val(),
					 code_65:$("#code_65").val(),
					 code_66:$("#code_66").val(),
					 code_67:$("#code_67").val(),
					 code_68:$("#code_68").val(),
					 code_69:$("#code_69").val(),
					 code_72:$("#code_72").val(),
					 code_73:$("#code_73").val(),
					 code_74:$("#code_74").val(),
					 code_75:$("#code_75").val(),
					 code_76:$("#code_76").val(),
					 code_77:$("#code_77").val(),
					 code_78:$("#code_78").val(),
					 code_79:$("#code_79").val(),
					 code_80:$("#code_80").val(),
					 code_81:$("#code_81").val(),
					 code_82:$("#code_82").val(),
					 code_83:$("#code_83").val(),
					 code_84:$("#code_84").val(),
					 code_85:$("#code_85").val(),
					 code_86:$("#code_86").val(),
					 code_87:$("#code_87").val(),
					 code_88:$("#code_88").val(),
					 code_89:$("#code_89").val(),
					 code_90:$("#code_90").val(),
					 code_91:$("#code_91").val(),
					 code_92:$("#code_92").val(),
					 code_93:$("#code_93").val(),
					 code_94:$("#code_94").val(),
					 code_95:$("#code_95").val(),
					 code_96:$("#code_96").val(),
					 code_97:$("#code_97").val(),
					 code_98:$("#code_98").val(),
					 code_99:$("#code_99").val(),
					 code_100:$("#code_100").val(),
					 code_101:$("#code_101").val(),
					 code_102:$("#code_102").val(),
					 code_103:$("#code_103").val(),
					 code_104:$("#code_104").val(),
					 code_105:$("#code_105").val(),
					 code_106:$("#code_106").val(),
					 code_107:$("#code_107").val(),
					 code_108:$("#code_108").val(),
					 code_109:$("#code_109").val(),
					 code_110:$("#code_110").val(),
					 code_111:$("#code_111").val(),
					 code_112:$("#code_112").val(),
					 code_113:$("#code_113").val(),
					 code_114:$("#code_114").val(),
					 code_115:$("#code_115").val(),
					 code_116:$("#code_116").val(),
					 code_117:$("#code_117").val(),
					 code_118:$("#code_118").val(),
					 code_119:$("#code_119").val(),
					 code_120:$("#code_120").val(),
					 code_121:$("#code_121").val(),
					 code_122:$("#code_122").val(),
					 code_123:$("#code_123").val()
				},
			    success: function(data){
			    	if(data.success) {
			    		parent.$.messager.alert('提示', data.msg);
			    		$('#list_data').datagrid('reload');
			    		$('#dialog').dialog('close');
			    	} else {
			    		parent.$.messager.alert('提示', data.msg);
			    	}
			    },
			    error: function(){
			    	parent.$.messager.alert('提示', "添加失败，请重试！");
			    }
			});
      	}
 	});
};
