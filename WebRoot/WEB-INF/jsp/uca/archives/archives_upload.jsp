<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/easy_top.jsp" %>
<%@ include file="/WEB-INF/jsp/common/js_script_for_base_modules.jsp" %>
<script type="text/javascript" src="${ctx}/resources/js/archives/archives_upload.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/ajaxfileupload.js"></script>
<title>档案上传</title>
<style type="text/css">
.tab1{border:#92CD9F solid 1px;border-width:1px 0 0 1px;}
.tab1 th{line-height: 22px;background: #F0FAEF;background-image: none;border-bottom: #92CD9F solid 1px;border-right: 1px solid #92cd9f;}
.tab1 td{border:#92CD9F solid 1px;border-width:0 1px 1px 0;padding:3px 5px;}
.ct {width: 700px;margin: 45px auto 70px auto;line-height: 21px;font-family:"宋体", Arial;font-size:12px;background:#fff;color:#000000;}
</style>
</head>
<body>
	<div id="edit_dialog" class="easyui-dialog" maximizable="true" resizable="true" 
    	style="width:600px;height:350px;padding:10px;top:40px;left:160px;">
    	<form id="form_edit" method="post" enctype="multipart/form-data">
    		<input type="hidden" id="archivesId" name="archivesId"/>
    		<input type="hidden" id="checkStatus" name="checkStatus"/>
    		<input type="hidden" id="type" name="type" value="${po.type }"/>
            <table>
                <tr>
                    <td width="90px" align="left">电子档案：</td>
                    <td>
					<label class="span85 fl tr">附件</label><a href="###" id="fileDown" name="fileDown"></a><img src="${ctx}/resources/images/loading.gif" id="loading" style="display:none;"/>
					<input type="file" class="span315" id="file" name="file" /><input type="button" value="上传" onclick="ajaxFileUpload()"/>
					<input type="hidden" value="" id="fileUrl" name="fileUrl">
					<input type="hidden" value="" id="sourceFileName" name="sourceFileName">
					</td>
                </tr>
                <tr>
                    <td width="90px" align="left">经办人：</td>
                    <td>
                    	<input class="easyui-combobox" id="handingPersonId" name="handingPersonId" data-options="valueField:'userId',textField:'userName'" style="width:180px;">
                   	</td>
                </tr>
                <tr>
                    <td colspan="2" align="center">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
							<input type="button" class="easyui-button" onclick="commit(0)" value="保存" />
							<input type="button" class="easyui-button" onclick="commit(1)" value="提交审核">
                    </td>
                </tr>
            </table>
        </form>	
    </div>
</body>
</html>