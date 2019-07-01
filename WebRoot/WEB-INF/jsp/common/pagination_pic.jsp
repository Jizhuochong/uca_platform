<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<link rel="stylesheet"
	href="${ctx}/resources/css/jpages-0.7/jPages_style.css">
<link rel="stylesheet"
	href="${ctx}/resources/css/jpages-0.7/jPages.css">
<link rel="stylesheet"
	href="${ctx}/resources/css/jpages-0.7/animate.css">
<link rel="stylesheet"
	href="${ctx}/resources/css/jpages-0.7/github.css">
<%--   <script type="text/javascript" src="${ctx}/resources/js/jquery-1.9.1/jquery-1.9.1.min.js"></script> --%>
<%--   <script type="text/javascript" src="${ctx}/resources/js/jpages-0.7/js/jquery-1.8.2.min.js"></script> --%>
<script type="text/javascript"
	src="${ctx}/resources/js/jpages-0.7/highlight.pack.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jpages-0.7/tabifier.js"></script>
<script src="${ctx}/resources/js/jpages-0.7/js.js"></script>
<script src="${ctx}/resources/js/jpages-0.7/jPages.js"></script>
<!-- <script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-28718218-1']);
  _gaq.push(['_trackPageview']);
  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
  </script> -->
<script>
	/* when document is ready */
	$(function() {
		/* initiate plugin */
		$(".selectAll").click(function() {
			if ($(this).prop("checked")) {
				$(".selectAll").prop("checked", true);
				$("div.holder").jPages('selectAll');
			} else {
				$("div.holder").jPages('reverseAll');
				$(".selectAll").prop("checked", false);
			}
		});
	});
</script>