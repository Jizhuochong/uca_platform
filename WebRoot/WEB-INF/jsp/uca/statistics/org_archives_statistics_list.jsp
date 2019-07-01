<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>区县档案统计表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/statistics/org_archives_statistics_list.js"></script>
</head>
<body>
<table id="list_data" cellspacing="0" cellpadding="0"></table>

<div id="toolbar">
	<div style="margin: 0 auto;padding: 2px;">
    	<a href="javascript:toAdd();" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">增加区县档案统计</a>
    </div>
</div>

<div id="dialog" class="easyui-dialog" style="width:800px;height:400px;padding:10px" 
	data-options="closed:true,draggable:false,modal:true" >
	<form id="formName_add">
        <table>
            <tr>
                <td width="520px" align="right">年度：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="yearValue" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">填报单位：</td>
                <td>
                	<input class="textbox" type="text" id="orgName" style="width:260px;" />
				</td>
            </tr>
            <%-- <tr>
                <td width="80px" align="right">填报单位：</td>
                <td>
                	<select id="org_id_select_jsid" class="easyui-combobox" style="width:320px;" editable="false"
                		data-options="valueField:'id',textField:'name',panelHeight:'auto',panelMaxHeight:120,
                			url:'${ctx}/org/fkComboData'">
					</select>
				</td>
            </tr> --%>
            <tr>
                <td width="520px" align="right">单位负责人：</td>
                <td>
                	<input class="textbox" type="text" id="userName" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">联系电话：</td>
                <td>
                	<input class="textbox" type="text" id="createUserPhone" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">1、定编(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_1" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">2、现有全部专职人员(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_2" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">3、现有全部专职人员女性(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_3" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">4、现有全部专职人员情况年龄50岁及以上(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_4" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">5、现有全部专职人员情况年龄35—49岁(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_5" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">6、现有全部专职人员情况年龄34岁及以下(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_6" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">7、现有全部专职人员情况文化程度博士研究(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_7" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">8、现有全部专职人员情况文化程度硕士研究生(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_8" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">9、现有全部专职人员情况文化程度研究生班研究生(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_9" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">10、现有全部专职人员情况文化程度双学士(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_10" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">11、现有全部专职人员情况文化程度大学本科(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_11" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">12、现有全部专职人员情况文化程度大专(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_12" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">13、现有全部专职人员情况文化程度中专(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_13" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">14、现有全部专职人员情况文化程度高中(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_14" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">15、现有全部专职人员情况文化程度初中及以下(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_15" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">16、现有全部专职人员情况档案专业程度博士研究生(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_16" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">17、现有全部专职人员情况档案专业程度硕士研究生(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_17" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">18、现有全部专职人员情况档案专业程度研究生班研究生(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_18" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">19、现有全部专职人员情况档案专业程度大学本科(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_19" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">20、现有全部专职人员情况档案专业程度大专(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_20" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">21、现有全部专职人员情况档案专业程度中专(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_21" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">22、现有全部专职人员情况档案专业程度职业高中(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_22" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">23、现有全部专职人员情况档案干部专业技术职务研究馆员(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_23" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">24、现有全部专职人员情况档案干部专业技术职务副研究馆员(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_24" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">25、现有全部专职人员情况档案干部专业技术职务馆员(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_25" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">26、现有全部专职人员情况档案干部专业技术职务助理馆员(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_26" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">27、现有全部专职人员情况档案干部专业技术职务管理员(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_27" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">28、现有全部专职人员情况接受在职培训教育(人)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_28" style="width:260px;" />
				</td>
            </tr>
            <!-- <tr>
                <td width="520px" align="right">馆藏档案案卷规划管理类/工程竣工类/其他类(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_29" style="width:260px;" />
				</td>
            </tr> -->
            <tr>
                <td width="520px" align="right">30、馆藏档案案卷案卷排架长度(米)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_30" style="width:260px;" />
				</td>
            </tr>
            <!-- <tr>
                <td width="520px" align="right">馆藏档案案卷以件为保管单位档案(件)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_31" style="width:260px;" />
				</td>
            </tr> -->
            <tr>
                <td width="520px" align="right">32、馆藏档案案卷以件为保管单位档案排架长度(米)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_32" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">33、馆藏档案案卷未整理零散文件排架长度(米)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_33" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">34、馆藏档案案卷录音磁带、录像磁带、影片档案(盘)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_34" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">35、馆藏档案案卷照片档案(张)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_35" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">36、馆藏档案案卷实物档案(件)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_36" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">37、馆藏资料(册)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_37" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">38_1、本年进馆档案接收档案案卷规划管理类(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_38_1" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">38_2、本年进馆档案接收档案案卷规划工程竣工类(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_38_2" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">38_3、本年进馆档案接收档案案卷规划其他类(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_38_3" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">39、本年进馆档案接收档案以件为保管单位档案(件)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_39" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">40、本年进馆档案接收档案录音磁带、录像磁带、影片档案(盘)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_40" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">41、本年进馆档案接收档案照片档案(张)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_41" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">42、本年进馆档案征集档案案卷(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_42" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">43、本年进馆档案征集档案以件为保管单位档案(件)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_43" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">44、本年进馆档案征集档案录音磁带、录像磁带、影片档案(盘)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_44" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">45、本年进馆档案征集档案照片档案(张)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_45" style="width:260px;" />
				</td>
            </tr>
            <!-- <tr>
                <td width="520px" align="right">馆藏档案的历史分期建国前档案(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_46" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">馆藏档案的历史分期建国前档案(件)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_47" style="width:260px;" />
				</td>
            </tr> -->
            <tr>
                <td width="520px" align="right">48、馆藏档案的历史分期建国前档案明清以前档案(件)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_48" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">49、馆藏档案的历史分期建国前档案明清档案(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_49" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">50、馆藏档案的历史分期建国前档案明清档案(件)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_50" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">51、馆藏档案的历史分期建国前档案民国档案(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_51" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">52、馆藏档案的历史分期建国前档案民国档案(件)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_52" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">53、馆藏档案的历史分期建国前档案革命历史档案(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_53" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">54、馆藏档案的历史分期建国前档案革命历史档案(件)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_54" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">55、现馆藏档案的历史分期建国后档案(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_55" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">56、馆藏档案的历史分期建国后档案(件)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_56" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">57、档案编目情况手工目录案卷目录(本)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_57" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">58、档案编目情况手工目录全引目录(本)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_58" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">59、档案编目情况手工目录归档文件目录(本)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_59" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">60、档案编目情况手工目录专题目录簿式(本)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_60" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">61、档案编目情况手工目录专题目录卡片式(张)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_61" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">62、档案编目情况手工目录重要文件目录簿式(本)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_62" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">63、档案编目情况手工目录重要文件目录卡片式(张)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_63" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">64、档案编目情况机读目录案卷级(条/万条)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_64" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">65、档案编目情况机读目录文件级(条/万条)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_65" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">66、档案编目情况机读目录文件级(条/万条)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_66" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">67、本年利用档案人次(人次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_67" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">68、本年利用档案人次其中：台港澳同胞(人次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_68" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">69、本年利用档案人次外国人(人次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_69" style="width:260px;" />
				</td>
            </tr>
            <!-- <tr>
                <td width="520px" align="right">本年利用档案(卷次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_70" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">本年利用档案(件次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_71" style="width:260px;" />
				</td>
            </tr> -->
            <tr>
                <td width="520px" align="right">72、本年利用档案所属时期建国前档案(卷次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_72" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">73、本年利用档案所属时期建国前档案(件次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_73" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">74、本年利用档案所属时期建国后档案(卷次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_74" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">75、本年利用档案所属时期建国后档案(件次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_75" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">76、本年利用档案利用者类别单位(卷次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_76" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">77、本年利用档案利用者类别单位(件次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_77" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">78、本年利用档案利用者类别个人(卷次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_78" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">79、本年利用档案利用者类别个人(件次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_79" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">80、本年利用资料(人次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_80" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">81、本年利用资料(册次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_81" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">82、本年举办档案展览(个)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_82" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">83、本年举办档案展览基本陈列(个)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_83" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">84、本年参观档案展览人次(人次)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_84" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">85、本年编研档案资料公开出版(种)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_85" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">86、本年编研档案资料公开出版(万字)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_86" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">87、本年编研档案资料内部参考(种)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_87" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">88、本年编研档案资料内部参考(万字)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_88" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">89、档案馆总建筑面积(平方米)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_89" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">90、档案馆总建筑面积档案库房建筑面积(平方米)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_90" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">91、档案馆总建筑面积后库面积(平方米)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_91" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">92、档案馆总建筑面积技术用房建筑面积(平方米)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_92" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">93、档案馆总建筑面积对外服务用房建筑面积(平方米)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_93" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">94、馆内设备电子计算机服务器(台)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_94" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">95、馆内设备电子计算机微机(台)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_95" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">96、馆内设备复印机(台)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_96" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">97、馆内设备空调机集中式(台)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_97" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">98、馆内设备空调机分散式(台)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_98" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">99、馆内设备去湿机(台)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_99" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">100、馆内设备消毒设备(台)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_100" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">101、馆内设备数字化扫描仪(台)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_101" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">102、馆藏数字档案归档电子文件电子文件(件)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_102" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">103、馆藏数字档案归档电子文件电子文件(GB)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_103" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">104、馆藏数字档案归档电子文件数码照片(张)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_104" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">105、馆藏数字档案归档电子文件数码照片(GB)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_105" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">106、馆藏数字档案归档电子文件数字录音、数字录像(小时)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_106" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">107、馆藏数字档案归档电子文件数字录音、数字录像(GB)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_107" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">108、馆藏数字档案馆藏档案数字化副本规划管理类已完成数量(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_108" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">109、馆藏数字档案馆藏档案数字化副本规划管理类已完成数量(幅)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_109" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">110、馆藏数字档案馆藏档案数字化副本规划管理类已完成数量(GB)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_110" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">111、馆藏数字档案馆藏档案数字化副本规划管理类未完成数量(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_111" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">112、馆藏数字档案馆藏档案数字化副本工程竣工类档案已完成数量员(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_112" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">113、馆藏数字档案馆藏档案数字化副本工程竣工类档案已完成数量(幅)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_113" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">114、馆藏数字档案馆藏档案数字化副本工程竣工类档案已完成数量(GB)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_114" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">115、馆藏数字档案馆藏档案数字化副本工程竣工类档案未完成数量(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_115" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">116、馆藏数字档案馆藏档案数字化副本其它类档案已完成数量(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_116" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">117、馆藏数字档案馆藏档案数字化副本其它类档案已完成数量(幅)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_117" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">118、馆藏数字档案馆藏档案数字化副本其它类档案已完成数量(GB)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_118" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">119、馆藏数字档案馆藏档案数字化副本其它类档案未完成数量(卷)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_119" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">120、馆藏数字档案数码照片(张)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_120" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">121、馆藏数字档案数码照片(GB)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_121" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">122、馆藏数字档案数字录音、录像(小时)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_122" style="width:260px;" />
				</td>
            </tr>
            <tr>
                <td width="520px" align="right">123、馆藏数字档案数字录音、录像(GB)：</td>
                <td>
                	<input class="easyui-numberbox" type="text" id="code_123" style="width:260px;" />
				</td>
            </tr>
            <tr>
            	<td align="center" colspan="2">
            		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="javascript:commit();">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="javascript:$('#dialog').dialog('close');">取消</a>
            	</td>
            </tr>
       	</table>
   	</form>	
</div>
</body>
</html>
