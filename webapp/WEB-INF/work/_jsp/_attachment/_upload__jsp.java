/*
 * JSP generated by Resin-3.1.12 (built Mon, 29 Aug 2011 03:22:08 PDT)
 */

package _jsp._attachment;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.alibaba.fastjson.JSONObject;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Iterator;
import java.util.List;
import com.alibaba.fastjson.JSONArray;
import java.util.Map;
import java.util.HashMap;
import com.sogou.qadev.service.cynthia.bean.Attachment;
import java.io.FileInputStream;
import java.io.File;
import com.sogou.qadev.service.cynthia.factory.DataAccessFactory;
import com.sogou.qadev.service.cynthia.service.DataAccessSession;
import com.sogou.qadev.service.cynthia.util.ConfigUtil;
import com.sogou.qadev.service.cynthia.bean.Key;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;

public class _upload__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;


  	private String getError(String message) {
  		JSONObject obj = new JSONObject();
  		obj.put("error", 1);
  		obj.put("message", message);
  		return obj.toJSONString();
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
    response.setContentType("text/html; charset=UTF-8");
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
	
	response.setContentType("text/html; charset=UTF-8");
	
	if(!ServletFileUpload.isMultipartContent(request)){
		out.println(getError("\u8bf7\u9009\u62e9\u6587\u4ef6\u3002"));
		return;
	}
	
	//\u521b\u5efa\u6587\u4ef6\u5939
	FileItemFactory factory = new DiskFileItemFactory();
	ServletFileUpload upload = new ServletFileUpload(factory);
	upload.setHeaderEncoding("UTF-8");
	List items = upload.parseRequest(request);
	Iterator itr = items.iterator();
	Map<String,String> fileIdNameMap = new HashMap<String,String>();
	while (itr.hasNext()) {
		FileItem item = (FileItem) itr.next();
		String fileName = item.getName();
		if(fileName == null)
			continue;
		fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
		long fileSize = item.getSize();
		File file = File.createTempFile("attachment_", ".attachment");
		File uploadedFile = new File(file.getParent(), file.getName());
		item.write(uploadedFile);
		byte[] bytes = new byte[(int)file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(bytes);
	    Attachment attachment = das.createAttachment(fileName, bytes);
		fileIdNameMap.put(attachment.getId().getValue(),attachment.getName());
		fis.close();
		file.delete();
		uploadedFile.delete();
	}

	out.println(JSONArray.toJSONString(fileIdNameMap));
	

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
    depend = new com.caucho.vfs.Depend(appDir.lookup("attachment/upload.jsp"), 8596785704894520497L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  private final static char []_jsp_string0;
  static {
    _jsp_string0 = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n".toCharArray();
  }
}
