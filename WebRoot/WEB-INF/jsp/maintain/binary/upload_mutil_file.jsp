<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/top.jsp" %>
		<link href="${ctx}/resources/uploadify-v3.2/uploadify.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/resources/uploadify-v3.2/jquery.uploadify.min.js"></script>
		<script type="text/javascript">
		$(function() {
			$("#fileItem").uploadify({
				'auto' : false,//文件选择完成后，是否自动上传
                'multi': true,//设置为true将允许多文件上传
			    'method' : 'post',//和后台交互的方式：post/get
			    'swf' : '${ctx}/resources/uploadify-v3.2/uploadify.swf',//[必须设置]swf的路径
			    'uploader' : '${ctx}/fileupload/uploadifyMutil',//[必须设置]上传文件触发的url
			    'height' : 20,//上传按钮的高
			    'width' : 60,//上传按钮的宽
			    'fileTypeDesc' : '只允许上传txt,word文件',//允许上传的文件类型的描述，在弹出的文件选择框里会显示,在浏览窗口底部的文件类型下拉菜单中显示的文本
			    'fileTypeExts' : '*.txt;*.doc;*.docs',//允许上传的文件类型，限制弹出文件选择框里能选择的文件
			    'fileSizeLimit' : 0,//文件的极限大小，以字节为单位，0为不限制。1MB:1*1024*1024
			    'buttonText' : '选择文件',//上传按钮的文字
			    'queueID' : 'fileQueue',//显示上传文件队列的元素id，可以简单用一个div来显示，上传文件页面中，你想要用来作为文件队列的元素的id, 默认为false  自动生成,  不带#
			    'progressData' : 'percentage',// 'percentage' 'speed' 'all'//队列中显示文件上传进度的方式：all-上传速度+百分比，percentage-百分比，speed-上传速度
			    'removeCompleted' : false,//上传成功后的文件，是否在队列中自动删除
			    'removeTimeout' : 3,
			    'queueSizeLimit' : 5,//队列中允许的最大文件数目
			    'onFallback' : function() {//检测FLASH失败调用
					alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
				},
				'onUploadComplete' : function(file) {//队列中的每个文件上传完成时触发一次
			    },
				'onUploadStart' : function(file) {//上传开始时触发（每个文件触发一次）
				},
				'onSelectError' : function(file,errorCode,errorMsg) {//当文件选定发生错误时触发，返回一个错误，选择文件的时候触发　　
					var msgText = "上传失败！\n\n";
					switch (errorCode) {
						case -100:
							msgText += "上传的文件数量已经超出系统限制的"+ $('#fileItem').uploadify('settings','queueSizeLimit') + "个文件！";
							break;
						case -110:
							msgText += "文件大小超出系统限制的"+ $('#fileItem').uploadify('settings','fileSizeLimit')+ "KB大小！";
							break;
						case -120:
							msgText += "文件大小为0，不可上传！";
							break;
						case -130:
							msgText += "文件类型不正确，可上传的文件类型为"+ $('#fileItem').uploadify('settings','fileTypeExts')+"！";
							break;
						default:
							msgText += "错误代码:"+errorCode+"\n"+errorMsg;
					}
					alert(msgText);
				},
				'onUploadError' : function(file,errorCode,errorMsg,errorString) {//当文件上传完成但是返回错误时触发
					var msgText = "上传失败！\n\n";
					switch (errorCode) {
						case -200:
							msgText += "HTTP 错误！\n"+errorMsg;
							break;
						case -210:
							msgText += "上传失败遗失，请重新操作！";
							break;
						case -220:
							msgText += "系统输入输出流发生异常！\n"+errorMsg;
							break;
						case -230:
							msgText += "安全性错误！\n"+errorMsg;
							break;
						case -240:
							msgText += "上传文件数量最多"+ $('#fileItem').uploadify('settings','queueSizeLimit') + "个！";
							break;
						case -250:
							msgText += "上传失败！\n"+errorMsg;
							break;
						case -260:
							msgText += "找不到指定文件的ID，请重新操作！";
							break;
						case -270:
							msgText += "参数错误！";
							break;
						case -280:
							msgText += file.name+"\n文件上传被取消！";
							break;
						case -290:
							msgText += file.name+"\n文件上传被停止！";
							break;
						default:
							msgText += "文件:"+file.name+"\n错误代码:"+errorCode+"\n"+errorMsg+"\n"+errorString;
							break;
					}
					alert(msgText);
				},
                'onUploadSuccess' : function (file, data, response) {//上传完成时触发（每个文件触发一次），上传成功后执行，服务器返回相应信息到data里
                	$('#' + file.id).find('.data').html(' 上传完毕');// 单个文件上传成功时的处理函数
                },
                'onQueueComplete':function(stats){//当队列中的所有文件全部完成上传时触发
                	alert("成功上传文件！");
				}
			});
		});
		</script>
	</head>
	<body>
		<div class="mn mt1">
		    <div class="ct">
				<div class="sh">
					<div class="mt3">
				        <div id="fileQueue"></div>
				        <br />
						<input type="file" name="fileItem" id="fileItem" />
						<p />
						<a href="javascript:$('#fileItem').uploadify('upload','*')">上传</a>&nbsp;|
						<a href="<c:url value="/binary/index" />">返回</a>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>