<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>

<%-- for css backgroud --%>
<%
	String comboClearBg = "#E0ECF9 url(" + "\'"
			+ request.getContextPath() + "/resources/images/wrong.jpg"
			+ "\'" + ") no-repeat 3px 4px";
%>

<style type="text/css">
.combo-clear {
	background: <%=comboClearBg%>;
	width: 18px;
	height: 20px;
	overflow: hidden;
	display: inline-block;
	vertical-align: top;
	cursor: pointer;
	opacity: 0.6;
	filter: alpha(opacity = 60);
}
</style>

<%-- 为easyui combobox增加一个自带的clear按钮,用来消除已选数据 --%>
<%-- 使用参数 showClear 来控制显示与否, 默认为true --%>
<%-- 包含一个 onClear 事件 --%>
<%-- 不支持comboTree --%>
<script type="text/javascript">
	comboboxClearButtonExtension = function(Combo) {
		// Combo为combobox element
		// combo为其父级div element
		var combo = Combo.parent();

		// showClear default is true
		var showClear = Combo.combo("options").showClear;
		if (typeof (showClear) == 'undefined') {
			showClear = true;
		}

		var clear = Combo.parent().find(".combo-clear");
		var text = Combo.parent().find(".textbox.combo");

		if (!clear.hasClass("combo-clear")) {
			if (showClear) {
				// 在combobox左侧添加一个图标
				text.prepend("<span class=\"combo-clear\"></span>");

				clear = combo.find(".combo-clear");
				clear.bind("click.combo", function() {
					var v = Combo.combobox("getValue");
					Combo.combobox("clear");

					var onClear = Combo.combo("options").onClear;
					if (typeof (onClear) == "function") {
						onClear(v, Combo);
					}
				});
			}
		} else if (!showClear) {
			combo.find("span:has(.combo-clear)").remove();
		}
	}
</script>