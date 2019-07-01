<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<title>区县档案统计</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/style.css" />
<script type="text/javascript" src="${ctx}/resources/js/json.org/json.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/uca/statistics/org_archives_statistics.js"></script>
</head>
<body>
	<div style="margin: 0 auto;padding: 10px;">
	      统计日期：<input id="sd" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;-&nbsp;
		<input id="ed" type="text" class="easyui-datebox" style="width:120px" editable="false">&nbsp;
	  	<a href="javascript:toSearch();" class="easyui-button" data-options="iconCls:'icon-search',plain:true">查询</a>&nbsp;
		<a href="javascript:clearForm();" class="easyui-button" data-options="iconCls:'icon-remove',plain:true">重置</a>
	</div>
	<div style="margin: 0 auto;padding: 10px;">
	<c:if test="${object == null}">
		暂无统计数据
	</c:if>
	<c:if test="${object != null}">
		<table>
			<tr>
				<td align="right">1、定编(人)：</td>
				<td>${object.code_1 }</td>
			</tr>
			<tr>
				<td align="right">2、现有全部专职人员(人)：</td>
				<td>${object.code_2 }</td>
			</tr>
			<tr>
				<td align="right">3、现有全部专职人员女性(人)：</td>
				<td>${object.code_3 }</td>
			</tr>
			<tr>
				<td align="right">4、现有全部专职人员情况年龄50岁及以上(人)：</td>
				<td>${object.code_4 }</td>
			</tr>
			<tr>
				<td align="right">5、现有全部专职人员情况年龄35—49岁(人)：</td>
				<td>${object.code_5 }</td>
			</tr>
			<tr>
				<td align="right">6、现有全部专职人员情况年龄34岁及以下(人)：</td>
				<td>${object.code_6 }</td>
			</tr>
			<tr>
				<td align="right">7、现有全部专职人员情况文化程度博士研究(人)：</td>
				<td>${object.code_7 }</td>
			</tr>
			<tr>
				<td align="right">8、现有全部专职人员情况文化程度硕士研究生(人)：</td>
				<td>${object.code_8 }</td>
			</tr>
			<tr>
				<td align="right">9、现有全部专职人员情况文化程度研究生班研究生(人)：</td>
				<td>${object.code_9 }</td>
			</tr>
			<tr>
				<td align="right">10、现有全部专职人员情况文化程度双学士(人)：</td>
				<td>${object.code_10 }</td>
			</tr>
			<tr>
				<td align="right">11、现有全部专职人员情况文化程度大学本科(人)：</td>
				<td>${object.code_11 }</td>
			</tr>
			<tr>
				<td align="right">12、现有全部专职人员情况文化程度大专(人)：</td>
				<td>${object.code_12 }</td>
			</tr>
			<tr>
				<td align="right">13、现有全部专职人员情况文化程度中专(人)：</td>
				<td>${object.code_13 }</td>
			</tr>
			<tr>
				<td align="right">14、现有全部专职人员情况文化程度高中(人)：</td>
				<td>${object.code_14 }</td>
			</tr>
			<tr>
				<td align="right">15、现有全部专职人员情况文化程度初中及以下(人)：</td>
				<td>${object.code_15 }</td>
			</tr>
			<tr>
				<td align="right">16、现有全部专职人员情况档案专业程度博士研究生(人)：</td>
				<td>${object.code_16 }</td>
			</tr>
			<tr>
				<td align="right">17、现有全部专职人员情况档案专业程度硕士研究生(人)：</td>
				<td>${object.code_17 }</td>
			</tr>
			<tr>
				<td align="right">18、现有全部专职人员情况档案专业程度研究生班研究生(人)：</td>
				<td>${object.code_18 }</td>
			</tr>
			<tr>
				<td align="right">19、现有全部专职人员情况档案专业程度大学本科(人)：</td>
				<td>${object.code_19 }</td>
			</tr>
			<tr>
				<td align="right">20、现有全部专职人员情况档案专业程度大专(人)：</td>
				<td>${object.code_20 }</td>
			</tr>
			<tr>
				<td align="right">21、现有全部专职人员情况档案专业程度中专(人)：</td>
				<td>${object.code_21 }</td>
			</tr>
			<tr>
				<td align="right">22、现有全部专职人员情况档案专业程度职业高中(人)：</td>
				<td>${object.code_22 }</td>
			</tr>
			<tr>
				<td align="right">23、现有全部专职人员情况档案干部专业技术职务研究馆员(人)：</td>
				<td>${object.code_23 }</td>
			</tr>
			<tr>
				<td align="right">24、现有全部专职人员情况档案干部专业技术职务副研究馆员(人)：</td>
				<td>${object.code_24 }</td>
			</tr>
			<tr>
				<td align="right">25、现有全部专职人员情况档案干部专业技术职务馆员(人)：</td>
				<td>${object.code_25 }</td>
			</tr>
			<tr>
				<td align="right">26、现有全部专职人员情况档案干部专业技术职务助理馆员(人)：</td>
				<td>${object.code_26 }</td>
			</tr>
			<tr>
				<td align="right">27、现有全部专职人员情况档案干部专业技术职务管理员(人)：</td>
				<td>${object.code_27 }</td>
			</tr>
			<tr>
				<td align="right">28、现有全部专职人员情况接受在职培训教育(人)：</td>
				<td>${object.code_28 }</td>
			</tr>
			<tr>
	            <td align="right">29、馆藏档案案卷规划管理类/工程竣工类/其他类(卷)：</td>
	            <td>${object.code_29 }</td>
	        </tr>
			<tr>
				<td align="right">30、馆藏档案案卷案卷排架长度(米)：</td>
				<td>${object.code_30 }</td>
			</tr>
			<tr>
	            <td align="right">31、馆藏档案案卷以件为保管单位档案(件)：</td>
	            <td>${object.code_31 }</td>
	        </tr>
			<tr>
				<td align="right">32、馆藏档案案卷以件为保管单位档案排架长度(米)：</td>
				<td>${object.code_32 }</td>
			</tr>
			<tr>
				<td align="right">33、馆藏档案案卷未整理零散文件排架长度(米)：</td>
				<td>${object.code_33 }</td>
			</tr>
			<tr>
				<td align="right">34、馆藏档案案卷录音磁带、录像磁带、影片档案(盘)：</td>
				<td>${object.code_34 }</td>
			</tr>
			<tr>
				<td align="right">35、馆藏档案案卷照片档案(张)：</td>
				<td>${object.code_35 }</td>
			</tr>
			<tr>
				<td align="right">36、馆藏档案案卷实物档案(件)：</td>
				<td>${object.code_36 }</td>
			</tr>
			<tr>
				<td align="right">37、馆藏资料(册)：</td>
				<td>${object.code_37 }</td>
			</tr>
			<tr>
				<td align="right">38_1、本年进馆档案接收档案案卷规划管理类(卷)：</td>
				<td>${object.code_38_1 }</td>
			</tr>
			<tr>
				<td align="right">38_2、本年进馆档案接收档案案卷规划工程竣工类(卷)：</td>
				<td>${object.code_38_2 }</td>
			</tr>
			<tr>
				<td align="right">38_3、本年进馆档案接收档案案卷规划其他类(卷)：</td>
				<td>${object.code_38_3 }</td>
			</tr>
			<tr>
				<td align="right">39、本年进馆档案接收档案以件为保管单位档案(件)：</td>
				<td>${object.code_39 }</td>
			</tr>
			<tr>
				<td align="right">40、本年进馆档案接收档案录音磁带、录像磁带、影片档案(盘)：</td>
				<td>${object.code_40 }</td>
			</tr>
			<tr>
				<td align="right">41、本年进馆档案接收档案照片档案(张)：</td>
				<td>${object.code_41 }</td>
			</tr>
			<tr>
				<td align="right">42、本年进馆档案征集档案案卷(卷)：</td>
				<td>${object.code_42 }</td>
			</tr>
			<tr>
				<td align="right">43、本年进馆档案征集档案以件为保管单位档案(件)：</td>
				<td>${object.code_43 }</td>
			</tr>
			<tr>
				<td align="right">44、本年进馆档案征集档案录音磁带、录像磁带、影片档案(盘)：</td>
				<td>${object.code_44 }</td>
			</tr>
			<tr>
				<td align="right">45、本年进馆档案征集档案照片档案(张)：</td>
				<td>${object.code_45 }</td>
			</tr>
			<tr>
	            <td align="right">46、馆藏档案的历史分期建国前档案(卷)：</td>
	            <td>${object.code_46 }</td>
	        </tr>
	        <tr>
	            <td align="right">47、馆藏档案的历史分期建国前档案(件)：</td>
	            <td>${object.code_47 }</td>
	        </tr>
			<tr>
				<td align="right">48、馆藏档案的历史分期建国前档案明清以前档案(件)：</td>
				<td>${object.code_48 }</td>
			</tr>
			<tr>
				<td align="right">49、馆藏档案的历史分期建国前档案明清档案(卷)：</td>
				<td>${object.code_49 }</td>
			</tr>
			<tr>
				<td align="right">50、馆藏档案的历史分期建国前档案明清档案(件)：</td>
				<td>${object.code_50 }</td>
			</tr>
			<tr>
				<td align="right">51、馆藏档案的历史分期建国前档案民国档案(卷)：</td>
				<td>${object.code_51 }</td>
			</tr>
			<tr>
				<td align="right">52、馆藏档案的历史分期建国前档案民国档案(件)：</td>
				<td>${object.code_52 }</td>
			</tr>
			<tr>
				<td align="right">53、馆藏档案的历史分期建国前档案革命历史档案(卷)：</td>
				<td>${object.code_53 }</td>
			</tr>
			<tr>
				<td align="right">54、馆藏档案的历史分期建国前档案革命历史档案(件)：</td>
				<td>${object.code_54 }</td>
			</tr>
			<tr>
				<td align="right">55、现馆藏档案的历史分期建国后档案(卷)：</td>
				<td>${object.code_55 }</td>
			</tr>
			<tr>
				<td align="right">56、馆藏档案的历史分期建国后档案(件)：</td>
				<td>${object.code_56 }</td>
			</tr>
			<tr>
				<td align="right">57、档案编目情况手工目录案卷目录(本)：</td>
				<td>${object.code_57 }</td>
			</tr>
			<tr>
				<td align="right">58、档案编目情况手工目录全引目录(本)：</td>
				<td>${object.code_58 }</td>
			</tr>
			<tr>
				<td align="right">59、档案编目情况手工目录归档文件目录(本)：</td>
				<td>${object.code_59 }</td>
			</tr>
			<tr>
				<td align="right">60、档案编目情况手工目录专题目录簿式(本)：</td>
				<td>${object.code_60 }</td>
			</tr>
			<tr>
				<td align="right">61、档案编目情况手工目录专题目录卡片式(张)：</td>
				<td>${object.code_61 }</td>
			</tr>
			<tr>
				<td align="right">62、档案编目情况手工目录重要文件目录簿式(本)：</td>
				<td>${object.code_62 }</td>
			</tr>
			<tr>
				<td align="right">63、档案编目情况手工目录重要文件目录卡片式(张)：</td>
				<td>${object.code_63 }</td>
			</tr>
			<tr>
				<td align="right">64、档案编目情况机读目录案卷级(条/万条)：</td>
				<td>${object.code_64 }</td>
			</tr>
			<tr>
				<td align="right">65、档案编目情况机读目录文件级(条/万条)：</td>
				<td>${object.code_65 }</td>
			</tr>
			<tr>
				<td align="right">66、档案编目情况机读目录文件级(条/万条)：</td>
				<td>${object.code_66 }</td>
			</tr>
			<tr>
				<td align="right">67、本年利用档案人次(人次)：</td>
				<td>${object.code_67 }</td>
			</tr>
			<tr>
				<td align="right">68、本年利用档案人次其中：台港澳同胞(人次)：</td>
				<td>${object.code_68 }</td>
			</tr>
			<tr>
				<td align="right">69、本年利用档案人次外国人(人次)：</td>
				<td>${object.code_69 }</td>
			</tr>
			<tr>
	            <td align="right">70、本年利用档案(卷次)：</td>
	            <td>${object.code_70 }</td>
        	</tr>
	        <tr>
	            <td align="right">71、本年利用档案(件次)：</td>
	            <td>${object.code_71 }</td>
	        </tr>
			<tr>
				<td align="right">72、本年利用档案所属时期建国前档案(卷次)：</td>
				<td>${object.code_72 }</td>
			</tr>
			<tr>
				<td align="right">73、本年利用档案所属时期建国前档案(件次)：</td>
				<td>${object.code_73 }</td>
			</tr>
			<tr>
				<td align="right">74、本年利用档案所属时期建国后档案(卷次)：</td>
				<td>${object.code_74 }</td>
			</tr>
			<tr>
				<td align="right">75、本年利用档案所属时期建国后档案(件次)：</td>
				<td>${object.code_75 }</td>
			</tr>
			<tr>
				<td align="right">76、本年利用档案利用者类别单位(卷次)：</td>
				<td>${object.code_76 }</td>
			</tr>
			<tr>
				<td align="right">77、本年利用档案利用者类别单位(件次)：</td>
				<td>${object.code_77 }</td>
			</tr>
			<tr>
				<td align="right">78、本年利用档案利用者类别个人(卷次)：</td>
				<td>${object.code_78 }</td>
			</tr>
			<tr>
				<td align="right">79、本年利用档案利用者类别个人(件次)：</td>
				<td>${object.code_79 }</td>
			</tr>
			<tr>
				<td align="right">80、本年利用资料(人次)：</td>
				<td>${object.code_80 }</td>
			</tr>
			<tr>
				<td align="right">81、本年利用资料(册次)：</td>
				<td>${object.code_81 }</td>
			</tr>
			<tr>
				<td align="right">82、本年举办档案展览(个)：</td>
				<td>${object.code_82 }</td>
			</tr>
			<tr>
				<td align="right">83、本年举办档案展览基本陈列(个)：</td>
				<td>${object.code_83 }</td>
			</tr>
			<tr>
				<td align="right">84、本年参观档案展览人次(人次)：</td>
				<td>${object.code_84 }</td>
			</tr>
			<tr>
				<td align="right">85、本年编研档案资料公开出版(种)：</td>
				<td>${object.code_85 }</td>
			</tr>
			<tr>
				<td align="right">86、本年编研档案资料公开出版(万字)：</td>
				<td>${object.code_86 }</td>
			</tr>
			<tr>
				<td align="right">87、本年编研档案资料内部参考(种)：</td>
				<td>${object.code_87 }</td>
			</tr>
			<tr>
				<td align="right">88、本年编研档案资料内部参考(万字)：</td>
				<td>${object.code_88 }</td>
			</tr>
			<tr>
				<td align="right">89、档案馆总建筑面积(平方米)：</td>
				<td>${object.code_89 }</td>
			</tr>
			<tr>
				<td align="right">90、档案馆总建筑面积档案库房建筑面积(平方米)：</td>
				<td>${object.code_90 }</td>
			</tr>
			<tr>
				<td align="right">91、档案馆总建筑面积后库面积(平方米)：</td>
				<td>${object.code_91 }</td>
			</tr>
			<tr>
				<td align="right">92、档案馆总建筑面积技术用房建筑面积(平方米)：</td>
				<td>${object.code_92 }</td>
			</tr>
			<tr>
				<td align="right">93、档案馆总建筑面积对外服务用房建筑面积(平方米)：</td>
				<td>${object.code_93 }</td>
			</tr>
			<tr>
				<td align="right">94、馆内设备电子计算机服务器(台)：</td>
				<td>${object.code_94 }</td>
			</tr>
			<tr>
				<td align="right">95、馆内设备电子计算机微机(台)：</td>
				<td>${object.code_95 }</td>
			</tr>
			<tr>
				<td align="right">96、馆内设备复印机(台)：</td>
				<td>${object.code_96 }</td>
			</tr>
			<tr>
				<td align="right">97、馆内设备空调机集中式(台)：</td>
				<td>${object.code_97 }</td>
			</tr>
			<tr>
				<td align="right">98、馆内设备空调机分散式(台)：</td>
				<td>${object.code_98 }</td>
			</tr>
			<tr>
				<td align="right">99、馆内设备去湿机(台)：</td>
				<td>${object.code_99 }</td>
			</tr>
			<tr>
				<td align="right">100、馆内设备消毒设备(台)：</td>
				<td>${object.code_100 }</td>
			</tr>
			<tr>
				<td align="right">101、馆内设备数字化扫描仪(台)：</td>
				<td>${object.code_101 }</td>
			</tr>
			<tr>
				<td align="right">102、馆藏数字档案归档电子文件电子文件(件)：</td>
				<td>${object.code_102 }</td>
			</tr>
			<tr>
				<td align="right">103、馆藏数字档案归档电子文件电子文件(GB)：</td>
				<td>${object.code_103 }</td>
			</tr>
			<tr>
				<td align="right">104、馆藏数字档案归档电子文件数码照片(张)：</td>
				<td>${object.code_104 }</td>
			</tr>
			<tr>
				<td align="right">105、馆藏数字档案归档电子文件数码照片(GB)：</td>
				<td>${object.code_105 }</td>
			</tr>
			<tr>
				<td align="right">106、馆藏数字档案归档电子文件数字录音、数字录像(小时)：</td>
				<td>${object.code_106 }</td>
			</tr>
			<tr>
				<td align="right">107、馆藏数字档案归档电子文件数字录音、数字录像(GB)：</td>
				<td>${object.code_107 }</td>
			</tr>
			<tr>
				<td align="right">108、馆藏数字档案馆藏档案数字化副本规划管理类已完成数量(卷)：</td>
				<td>${object.code_108 }</td>
			</tr>
			<tr>
				<td align="right">109、馆藏数字档案馆藏档案数字化副本规划管理类已完成数量(幅)：</td>
				<td>${object.code_109 }</td>
			</tr>
			<tr>
				<td align="right">110、馆藏数字档案馆藏档案数字化副本规划管理类已完成数量(GB)：</td>
				<td>${object.code_110 }</td>
			</tr>
			<tr>
				<td align="right">111、馆藏数字档案馆藏档案数字化副本规划管理类未完成数量(卷)：</td>
				<td>${object.code_111 }</td>
			</tr>
			<tr>
				<td align="right">112、馆藏数字档案馆藏档案数字化副本工程竣工类档案已完成数量员(卷)：</td>
				<td>${object.code_112 }</td>
			</tr>
			<tr>
				<td align="right">113、馆藏数字档案馆藏档案数字化副本工程竣工类档案已完成数量(幅)：</td>
				<td>${object.code_113 }</td>
			</tr>
			<tr>
				<td align="right">114、馆藏数字档案馆藏档案数字化副本工程竣工类档案已完成数量(GB)：</td>
				<td>${object.code_114 }</td>
			</tr>
			<tr>
				<td align="right">115、馆藏数字档案馆藏档案数字化副本工程竣工类档案未完成数量(卷)：</td>
				<td>${object.code_115 }</td>
			</tr>
			<tr>
				<td align="right">116、馆藏数字档案馆藏档案数字化副本其它类档案已完成数量(卷)：</td>
				<td>${object.code_116 }</td>
			</tr>
			<tr>
				<td align="right">117、馆藏数字档案馆藏档案数字化副本其它类档案已完成数量(幅)：</td>
				<td>${object.code_117 }</td>
			</tr>
			<tr>
				<td align="right">118、馆藏数字档案馆藏档案数字化副本其它类档案已完成数量(GB)：</td>
				<td>${object.code_118 }</td>
			</tr>
			<tr>
				<td align="right">119、馆藏数字档案馆藏档案数字化副本其它类档案未完成数量(卷)：</td>
				<td>${object.code_119 }</td>
			</tr>
			<tr>
				<td align="right">120、馆藏数字档案数码照片(张)：</td>
				<td>${object.code_120 }</td>
			</tr>
			<tr>
				<td align="right">121、馆藏数字档案数码照片(GB)：</td>
				<td>${object.code_121 }</td>
			</tr>
			<tr>
				<td align="right">122、馆藏数字档案数字录音、录像(小时)：</td>
				<td>${object.code_122 }</td>
			</tr>
			<tr>
				<td align="right">123、馆藏数字档案数字录音、录像(GB)：</td>
				<td>${object.code_123 }</td>
			</tr>
		</table>
		</c:if>
	</div>
</body>
</html>
