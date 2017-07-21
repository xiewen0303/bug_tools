/*
 * JSP generated by Resin-3.1.12 (built Mon, 29 Aug 2011 03:22:08 PDT)
 */

package _jsp._task;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.sogou.qadev.service.cynthia.util.XMLUtil;
import com.sogou.qadev.service.cynthia.bean.Key;
import com.sogou.qadev.service.cynthia.bean.UUID;
import com.sogou.qadev.service.cynthia.factory.DataAccessFactory;
import com.sogou.qadev.service.cynthia.service.DataAccessSession;
import com.sogou.qadev.service.cynthia.util.ConfigUtil;
import com.sogou.qadev.service.cynthia.service.DataManager;
import com.sogou.qadev.service.cynthia.bean.Data;
import com.sogou.qadev.service.cynthia.bean.Template;
import com.sogou.qadev.service.cynthia.bean.Field;
import com.sogou.qadev.service.cynthia.bean.Option;
import com.sogou.qadev.service.cynthia.bean.Flow;
import com.sogou.qadev.service.cynthia.bean.Stat;
import com.sogou.qadev.service.cynthia.bean.Action;
import com.sogou.qadev.service.cynthia.bean.Role;
import com.sogou.qadev.service.cynthia.bean.Attachment;
import com.sogou.qadev.service.cynthia.bean.Field.Type;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import java.util.*;
import com.sogou.qadev.service.cynthia.bean.Option.Forbidden;

public class _queryusertemplates__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;



  static DataAccessFactory daf = DataAccessFactory.getInstance();

  String checkXML(String xml, String encode) throws Exception{
  	byte[] byteArray = xml.getBytes(encode);
  	for(int i = 0; i < byteArray.length; i++){
  		if(byteArray[i] == 0x1a || byteArray[i] == 0x19||byteArray[i] == 0x1||byteArray[i]==0x4){
  			byteArray[i] = 78;
  		}
  	}
  	return new String(byteArray, encode);
  }

  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    javax.servlet.http.HttpSession session = request.getSession(true);
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    javax.servlet.ServletContext application = _jsp_application;
    com.caucho.jsp.PageContextImpl pageContext = _jsp_application.getJspApplicationContext().allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);
    javax.servlet.jsp.PageContext _jsp_parentContext = pageContext;
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    response.setContentType("text/xml; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
    try {
      out.write(_jsp_string0, 0, _jsp_string0.length);
      
	response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
	response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
	
	out.clear();

	Long keyId = (Long)session.getAttribute("kid");
	Key key = (Key)session.getAttribute("key");

	if(keyId == null || keyId <= 0 || key == null){
		response.sendRedirect(ConfigUtil.getCynthiaWebRoot());
		return;
	}
	
	DataAccessSession das = daf.createDataAccessSession(key.getUsername(), keyId);
	
	String xml = null;
		
	System.out.println(key.getUsername());
	
	Template[] templateArray  = DataManager.getInstance().queryUserTemplates(key.getUsername());
		
	StringBuffer xmlb = new StringBuffer(64);
	xmlb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		
	xmlb.append("<root>");
		
	xmlb.append("<isError>false</isError>");
		
	if(templateArray.length == 0){
		xmlb.append("<templates/>");
	}else{
		xmlb.append("<templates>");
	
		for(Template template : templateArray){
			xmlb.append("<template>");	
				
			xmlb.append("<id>").append(template.getId()).append("</id>");
			xmlb.append("<name>").append(XMLUtil.toSafeXMLString(template.getName())).append("</name>");
			xmlb.append("<templateType>").append(XMLUtil.toSafeXMLString(template.getTemplateTypeId().getValue())).append("</templateType>");	
			xmlb.append("</template>");
		}
		
		xmlb.append("</templates>");
	}
	
	xmlb.append("</root>");
	xml = xmlb.toString();
	out.println(xml);

      out.write('\n');
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      _jsp_application.getJspApplicationContext().freePageContext(pageContext);
    }
  }

  private java.util.ArrayList _caucho_depends = new java.util.ArrayList();

  public java.util.ArrayList _caucho_getDependList()
  {
    return _caucho_depends;
  }

  public void _caucho_addDepend(com.caucho.vfs.PersistentDependency depend)
  {
    super._caucho_addDepend(depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  public boolean _caucho_isModified()
  {
    if (_caucho_isDead)
      return true;
    if (com.caucho.server.util.CauchoSystem.getVersionId() != 7170261747151080670L)
      return true;
    for (int i = _caucho_depends.size() - 1; i >= 0; i--) {
      com.caucho.vfs.Dependency depend;
      depend = (com.caucho.vfs.Dependency) _caucho_depends.get(i);
      if (depend.isModified())
        return true;
    }
    return false;
  }

  public long _caucho_lastModified()
  {
    return 0;
  }

  public java.util.HashMap<String,java.lang.reflect.Method> _caucho_getFunctionMap()
  {
    return _jsp_functionMap;
  }

  public void init(ServletConfig config)
    throws ServletException
  {
    com.caucho.server.webapp.WebApp webApp
      = (com.caucho.server.webapp.WebApp) config.getServletContext();
    super.init(config);
    com.caucho.jsp.TaglibManager manager = webApp.getJspApplicationContext().getTaglibManager();
    com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.PageContextImpl(webApp, this);
  }

  public void destroy()
  {
      _caucho_isDead = true;
      super.destroy();
  }

  public void init(com.caucho.vfs.Path appDir)
    throws javax.servlet.ServletException
  {
    com.caucho.vfs.Path resinHome = com.caucho.server.util.CauchoSystem.getResinHome();
    com.caucho.vfs.MergePath mergePath = new com.caucho.vfs.MergePath();
    mergePath.addMergePath(appDir);
    mergePath.addMergePath(resinHome);
    com.caucho.loader.DynamicClassLoader loader;
    loader = (com.caucho.loader.DynamicClassLoader) getClass().getClassLoader();
    String resourcePath = loader.getResourcePathSpecificFirst();
    mergePath.addClassPath(resourcePath);
    com.caucho.vfs.Depend depend;
    depend = new com.caucho.vfs.Depend(appDir.lookup("task/queryUserTemplates.jsp"), 4105392871002904707L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  private final static char []_jsp_string0;
  static {
    _jsp_string0 = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n".toCharArray();
  }
}