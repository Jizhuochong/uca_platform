package org.apache.jsp.WEB_002dINF.jsp.error;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.util.*;
import java.util.*;
import java.util.*;
import java.util.*;

public final class error_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(5);
    _jspx_dependants.add("/WEB-INF/jsp/common/easy_top.jsp");
    _jspx_dependants.add("/WEB-INF/jsp/common/taglibs.jsp");
    _jspx_dependants.add("/WEB-INF/jsp/common/js_script_for_base_modules.jsp");
    _jspx_dependants.add("/WEB-INF/jsp/uca/_helpers/easyui_extension_combobox_clear.jsp");
    _jspx_dependants.add("/WEB-INF/jsp/common/foot.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_spring_message_code_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_set_var_value_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_spring_message_code_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_c_set_var_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_spring_message_code_nobody.release();
    _jspx_tagPool_c_set_var_value_nobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("\t<head>\r\n");
      out.write("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("\t\t");
      if (_jspx_meth_c_set_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery/jquery-1.11.1.min.js\"></script>\r\n");
      out.write("\t\t<script type=\"text/javascript\">var rfPath = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("';</script>\r\n");
      out.write("\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t\t//全局的ajax访问，处理ajax清求时sesion超时\r\n");
      out.write("\t        $.ajaxSetup({ \r\n");
      out.write("\t            contentType:\"application/x-www-form-urlencoded;charset=utf-8\", \r\n");
      out.write("\t            complete:function(XMLHttpRequest,textStatus){\r\n");
      out.write("                         if(textStatus==\"parsererror\"){\r\n");
      out.write("                        \t window.top.location.reload(\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/toErmLogin\");\r\n");
      out.write("                         }\r\n");
      out.write("\t            }\r\n");
      out.write("\t        });\r\n");
      out.write("\t\t</script>");
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- jquery-easyui-1.3.5 -->\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- jquery-easyui-1.4 -->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery-easyui-1.4/themes/default/easyui.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery-easyui-1.4/themes/icon.css\">\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery-easyui-1.4/jquery.easyui.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- easyui扩展 -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery-extensions-master/release/jquery.jdirk.min.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery-extensions-master/icons/icon-all.min.css\">\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery-extensions-master/release/jeasyui.icons.all.min.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery-extensions-master/release/jeasyui.extensions.min.css\">\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery-extensions-master/release/jeasyui.extensions.all.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- jquery & easyui helpers -->\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/uca/_helpers/EnumsHelper.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/uca/_helpers/FilePathHelper.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/resources/js/uca/_helpers/MaskJqueryPlugin.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write('\r');
      out.write('\n');

	String comboClearBg = "#E0ECF9 url(" + "\'"
			+ request.getContextPath() + "/resources/images/wrong.jpg"
			+ "\'" + ") no-repeat 3px 4px";

      out.write("\r\n");
      out.write("\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write(".combo-clear {\r\n");
      out.write("\tbackground: ");
      out.print(comboClearBg);
      out.write(";\r\n");
      out.write("\twidth: 18px;\r\n");
      out.write("\theight: 20px;\r\n");
      out.write("\toverflow: hidden;\r\n");
      out.write("\tdisplay: inline-block;\r\n");
      out.write("\tvertical-align: top;\r\n");
      out.write("\tcursor: pointer;\r\n");
      out.write("\topacity: 0.6;\r\n");
      out.write("\tfilter: alpha(opacity = 60);\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tcomboboxClearButtonExtension = function(Combo) {\r\n");
      out.write("\t\t// Combo为combobox element\r\n");
      out.write("\t\t// combo为其父级div element\r\n");
      out.write("\t\tvar combo = Combo.parent();\r\n");
      out.write("\r\n");
      out.write("\t\t// showClear default is true\r\n");
      out.write("\t\tvar showClear = Combo.combo(\"options\").showClear;\r\n");
      out.write("\t\tif (typeof (showClear) == 'undefined') {\r\n");
      out.write("\t\t\tshowClear = true;\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\tvar clear = Combo.parent().find(\".combo-clear\");\r\n");
      out.write("\t\tvar text = Combo.parent().find(\".textbox.combo\");\r\n");
      out.write("\r\n");
      out.write("\t\tif (!clear.hasClass(\"combo-clear\")) {\r\n");
      out.write("\t\t\tif (showClear) {\r\n");
      out.write("\t\t\t\t// 在combobox左侧添加一个图标\r\n");
      out.write("\t\t\t\ttext.prepend(\"<span class=\\\"combo-clear\\\"></span>\");\r\n");
      out.write("\r\n");
      out.write("\t\t\t\tclear = combo.find(\".combo-clear\");\r\n");
      out.write("\t\t\t\tclear.bind(\"click.combo\", function() {\r\n");
      out.write("\t\t\t\t\tvar v = Combo.combobox(\"getValue\");\r\n");
      out.write("\t\t\t\t\tCombo.combobox(\"clear\");\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\tvar onClear = Combo.combo(\"options\").onClear;\r\n");
      out.write("\t\t\t\t\tif (typeof (onClear) == \"function\") {\r\n");
      out.write("\t\t\t\t\t\tonClear(v, Combo);\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t} else if (!showClear) {\r\n");
      out.write("\t\t\tcombo.find(\"span:has(.combo-clear)\").remove();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("</script>");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- others -->\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t//禁止windows自带的右键点击事件\r\n");
      out.write("\tdocument.oncontextmenu = function() {\r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t}; \r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("    <title>错误页面提示</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("    <div align=\"center\" style=\"padding-top:180px;padding-bottom:220px;\">\r\n");
      out.write("    <div class=\"easyui-panel\" title=\"错误页面提示\" style=\"position:relative;width:520px;height:80px;padding:10px;\">\r\n");
      out.write("       \t<h2>\r\n");
      out.write("\t\t\t<font color=\"red\">\r\n");
      out.write("\t\t\t\t<span id=\"errorMsg\">\r\n");
      out.write("\t\t\t\t\t");
      if (_jspx_meth_spring_message_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t</span>\r\n");
      out.write("\t\t\t</font>\r\n");
      out.write("\t\t</h2>\r\n");
      out.write("    </div>\r\n");
      out.write("    </div>\r\n");
      out.write("\t<div>&nbsp;</div>\r\n");
      out.write("\r\n");
      out.write("\t\t<div align=\"center\">Powered by &copy;</div>\r\n");
      out.write("\t</body>\r\n");
      out.write("</html>");
      out.write('\r');
      out.write('\n');
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_set_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_0.setPageContext(_jspx_page_context);
    _jspx_th_c_set_0.setParent(null);
    _jspx_th_c_set_0.setVar("ctx");
    _jspx_th_c_set_0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    int _jspx_eval_c_set_0 = _jspx_th_c_set_0.doStartTag();
    if (_jspx_th_c_set_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
      return true;
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
    return false;
  }

  private boolean _jspx_meth_spring_message_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  spring:message
    org.springframework.web.servlet.tags.MessageTag _jspx_th_spring_message_0 = (org.springframework.web.servlet.tags.MessageTag) _jspx_tagPool_spring_message_code_nobody.get(org.springframework.web.servlet.tags.MessageTag.class);
    _jspx_th_spring_message_0.setPageContext(_jspx_page_context);
    _jspx_th_spring_message_0.setParent(null);
    _jspx_th_spring_message_0.setCode("1004");
    int[] _jspx_push_body_count_spring_message_0 = new int[] { 0 };
    try {
      int _jspx_eval_spring_message_0 = _jspx_th_spring_message_0.doStartTag();
      if (_jspx_th_spring_message_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_spring_message_0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_spring_message_0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_spring_message_0.doFinally();
      _jspx_tagPool_spring_message_code_nobody.reuse(_jspx_th_spring_message_0);
    }
    return false;
  }
}
