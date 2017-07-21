/*
 * JSP generated by Resin-3.1.12 (built Mon, 29 Aug 2011 03:22:08 PDT)
 */

package _jsp._admin._template;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.sogou.qadev.service.cynthia.service.ProjectInvolveManager;
import com.sogou.qadev.service.cynthia.service.DataManager;
import com.sogou.qadev.service.cynthia.service.ConfigManager;
import java.util.LinkedHashMap;
import com.sogou.qadev.service.cynthia.bean.UserInfo;
import java.util.Map;
import com.sogou.qadev.service.cynthia.dao.FlowAccessSessionMySQL;
import com.sogou.qadev.service.cynthia.util.ConfigUtil;
import com.sogou.qadev.service.cynthia.util.ArrayUtil;
import com.sogou.qadev.service.cynthia.service.DataAccessSession;
import com.sogou.qadev.service.cynthia.factory.DataAccessFactory;
import com.sogou.qadev.service.cynthia.bean.Template;
import com.sogou.qadev.service.cynthia.bean.TemplateType;
import com.sogou.qadev.service.cynthia.bean.Flow;
import com.sogou.qadev.service.cynthia.bean.Key;
import com.sogou.qadev.service.cynthia.util.XMLUtil;
import java.util.TreeMap;
import java.util.Arrays;

public class _get_0initinfo_0xml__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  
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

	Key key = (Key)session.getAttribute("key");
	Long keyId = (Long)session.getAttribute("kid");

	if(keyId == null || keyId <= 0 || key == null){
		response.sendRedirect(ConfigUtil.getCynthiaWebRoot());
		return;
	}

	DataAccessSession das = DataAccessFactory.getInstance().createDataAccessSession(key.getUsername(), keyId);
	
	StringBuffer xmlb = new StringBuffer(64);
	xmlb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	xmlb.append("<root>");
	xmlb.append("<isError>false</isError>");
	
	UserInfo userInfo = das.queryUserInfoByUserName(key.getUsername());
	if(userInfo != null){
		xmlb.append("<userRole>"+userInfo.getUserRole()+"</userRole>");
	}
	
	//set templates
	Map<String, Template> templateMap = new LinkedHashMap<String, Template>();
	
	Template[] templateArray = null;
	
	if(ConfigManager.getProjectInvolved()){
		templateArray = DataManager.getInstance().queryUserTemplates(key.getUsername());
	}else{
		templateArray = das.queryAllTemplates();
	}
	
	if(templateArray != null)
	{
		for(Template template : templateArray)
		{
			if (null == template.getName() || ( template.getTemplateConfig().isProjectInvolve() && !ConfigManager.getProjectInvolved() )){
				continue;
			}
			templateMap.put(template.getId().getValue(), template);
		}
	}
	
	if(templateMap.size() == 0)
		xmlb.append("<templates/>");
	else
	{
		xmlb.append("<templates>");
		
		for(Template template : templateMap.values())
		{
			xmlb.append("<template>");
			xmlb.append("<id>").append(template.getId()).append("</id>");
			xmlb.append("<name>").append(XMLUtil.toSafeXMLString(template.getName())).append("</name>");
			xmlb.append("<templateTypeId>").append(template.getTemplateTypeId()).append("</templateTypeId>");
			xmlb.append("<flowId>").append(template.getFlowId()).append("</flowId>");
			xmlb.append("<isProTemplate>").append(String.valueOf(template.getTemplateConfig().isProjectInvolve())).append("</isProTemplate>");
			xmlb.append("<isFocused>").append("true").append("</isFocused>");
			xmlb.append("<isNew>").append("true").append("</isNew>");
			
			xmlb.append("</template>");
		}
		
		xmlb.append("</templates>");
	}
	
	templateArray = null;
	templateMap = null;
	//set templateTypes
	TemplateType[] templateTypeArray = das.queryAllTemplateTypes();
	if(templateTypeArray == null || templateTypeArray.length == 0)
		xmlb.append("<templateTypes/>");
	else
	{
		xmlb.append("<templateTypes>");
		
		for(TemplateType templateType : templateTypeArray)
		{
			xmlb.append("<templateType>");
			xmlb.append("<id>").append(templateType.getId()).append("</id>");
			xmlb.append("<name>").append(XMLUtil.toSafeXMLString(templateType.getName())).append("</name>");
			xmlb.append("</templateType>");
		}
		
		xmlb.append("</templateTypes>");
	}
	
	templateTypeArray = null;
	
	xmlb.append("<flows>");
	
	for(Flow flow : das.queryAllFlows(key.getUsername()))
	{
		xmlb.append("<flow>");
		xmlb.append("<id>").append(flow.getId().getValue()).append("</id>");
		xmlb.append("<name>").append(XMLUtil.toSafeXMLString(flow.getName())).append("</name>");
		xmlb.append("<isProFlow>").append(String.valueOf(flow.isProFlow())).append("</isProFlow>");
		xmlb.append("</flow>");
	}
	xmlb.append("</flows>");
	xmlb.append("</root>");
	out.println(xmlb.toString());

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
    depend = new com.caucho.vfs.Depend(appDir.lookup("admin/template/get_InitInfo_xml.jsp"), -5947002376381419639L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  private final static char []_jsp_string0;
  static {
    _jsp_string0 = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n".toCharArray();
  }
}