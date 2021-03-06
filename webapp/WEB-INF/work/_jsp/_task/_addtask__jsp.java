/*
 * JSP generated by Resin-3.1.12 (built Mon, 29 Aug 2011 03:22:08 PDT)
 */

package _jsp._task;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.sogou.qadev.service.cynthia.service.ErrorManager.ErrorType;
import com.sogou.qadev.service.cynthia.service.ErrorManager;
import com.sogou.qadev.service.cynthia.util.XMLUtil;
import com.sogou.qadev.service.cynthia.service.DataAccessSession.ErrorCode;
import com.sogou.qadev.service.cynthia.bean.DataAccessAction;
import com.sogou.qadev.service.cynthia.bean.Field.DataType;
import com.sogou.qadev.service.cynthia.bean.UUID;
import com.sogou.qadev.service.cynthia.bean.Data;
import com.sogou.qadev.service.cynthia.bean.Template;
import com.sogou.qadev.service.cynthia.bean.Field;
import com.sogou.qadev.service.cynthia.bean.Field.Type;
import com.sogou.qadev.service.cynthia.factory.DataAccessFactory;
import com.sogou.qadev.service.cynthia.service.DataAccessSession;
import com.sogou.qadev.service.cynthia.util.ConfigUtil;
import com.sogou.qadev.service.cynthia.bean.Pair;
import com.sogou.qadev.service.cynthia.bean.Key;
import java.util.Enumeration;
import java.util.Map;
import java.util.LinkedHashMap;
import com.sogou.qadev.service.cynthia.util.Date;

public class _addtask__jsp extends com.caucho.jsp.JavaPage
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
	
	Long keyId = (Long)session.getAttribute("kid");
	Key key = (Key)session.getAttribute("key");

	if(keyId == null || keyId <= 0 || key == null){
		response.sendRedirect(ConfigUtil.getCynthiaWebRoot());
		return;
	}
	
	//\u83b7\u53d6templateId
	UUID templateId = DataAccessFactory.getInstance().createUUID(request.getParameter("templateId"));
	//\u83b7\u53d6actionId
	UUID actionId = DataAccessFactory.getInstance().createUUID(request.getParameter("actionId"));
	//\u83b7\u53d6statusId
	UUID statusId = DataAccessFactory.getInstance().createUUID(request.getParameter("statusId"));
	//\u83b7\u53d6title
	String title = request.getParameter("title");
	//\u83b7\u53d6description
	String description = request.getParameter("description");
	//\u83b7\u53d6assignUser
	String assignUser = request.getParameter("assignUser");
	//\u83b7\u53d6actionComment
	String actionComment = request.getParameter("actionComment");
	
	DataAccessSession das = DataAccessFactory.getInstance().createDataAccessSession(key.getUsername(), keyId);
	
	Data data = das.addData(templateId);
	if(data == null){
		out.println(ErrorManager.getErrorXml(ErrorType.database_update_error));
		return;
	}
	
	Map<String, Pair<Object, Object>> baseValueMap = new LinkedHashMap<String, Pair<Object, Object>>();
	Map<UUID, Pair<Object, Object>> extValueMap = new LinkedHashMap<UUID, Pair<Object, Object>>();
	
	data.setTitle(title);
	baseValueMap.put("title", new Pair<Object, Object>(null, title));
	
	if(description != null && description.length() == 0){
		description = null;
	}
	
	if(description != null){
		data.setDescription(description);
		baseValueMap.put("description", new Pair<Object, Object>(null, description));
	}
	
	data.setAssignUsername(assignUser);
	baseValueMap.put("assignUser", new Pair<Object, Object>(null, assignUser));
	
	data.setStatusId(statusId);
	baseValueMap.put("statusId", new Pair<Object, Object>(null, statusId));
	
	data.setObject("logCreateUser", key.getUsername());
	data.setObject("logActionId", actionId);
	
	if(actionComment != null && actionComment.length() == 0){
		actionComment = null;
	}
	
	if(actionComment != null){
		data.setObject("logActionComment", actionComment);
	}
	
	Template template = das.queryTemplate(templateId);
	if(template == null){
		out.println(ErrorManager.getErrorXml(ErrorType.template_not_found));
		return;
	}
	
	//\u83b7\u53d6fieldValues
	Enumeration enumeration = request.getParameterNames();
	while(enumeration.hasMoreElements()){
			String param = (String)enumeration.nextElement();
			if(param.startsWith("field")){	
		UUID fieldId = DataAccessFactory.getInstance().createUUID(param.substring(5));
		
		Field field = template.getField(fieldId);
		if(field == null){
			continue;
		}
		
		String fieldValue = request.getParameter(param);
		if(fieldValue != null && fieldValue.length() == 0){
			fieldValue = null;
		}
		
		if(fieldValue == null){
			continue;
		}
		
		if(field.getType().equals(Type.t_selection)){
			if(field.getDataType().equals(DataType.dt_single)){
		UUID optionId = DataAccessFactory.getInstance().createUUID(fieldValue);
		
		data.setSingleSelection(fieldId, optionId);
		extValueMap.put(fieldId, new Pair<Object, Object>(null, optionId));
			}
			else if(field.getDataType().equals(DataType.dt_multiple)){
		String[] optionIdStrArray = fieldValue.split(",");
		UUID[] optionIdArray = new UUID[optionIdStrArray.length];
		for(int i = 0; i < optionIdArray.length; i++){
			optionIdArray[i] = DataAccessFactory.getInstance().createUUID(optionIdStrArray[i]);
		}
		
		data.setMultiSelection(fieldId, optionIdArray);
		extValueMap.put(fieldId, new Pair<Object, Object>(null, optionIdArray));
			}
		}
		else if(field.getType().equals(Type.t_reference)){
			if(field.getDataType().equals(DataType.dt_single)){
		UUID referenceId = DataAccessFactory.getInstance().createUUID(fieldValue);
		
		data.setSingleReference(fieldId, referenceId);
		extValueMap.put(fieldId, new Pair<Object, Object>(null, referenceId));
			}
			else if(field.getDataType().equals(DataType.dt_multiple)){
		String[] referenceIdStrArray = fieldValue.split(",");
		UUID[] referenceIdArray = new UUID[referenceIdStrArray.length];
		for(int i = 0; i < referenceIdArray.length; i++){
			referenceIdArray[i] = DataAccessFactory.getInstance().createUUID(referenceIdStrArray[i]);
		}
		
		data.setMultiReference(fieldId, referenceIdArray);
		extValueMap.put(fieldId, new Pair<Object, Object>(null, referenceIdArray));
			}
		}
		else if(field.getType().equals(Type.t_attachment)){
			String[] attachmentIdStrArray = fieldValue.split(",");
			UUID[] attachmentIdArray = new UUID[attachmentIdStrArray.length];
			for(int i = 0; i < attachmentIdArray.length; i++){
		attachmentIdArray[i] = DataAccessFactory.getInstance().createUUID(attachmentIdStrArray[i]);
			}
			
			data.setAttachments(fieldId, attachmentIdArray);
			extValueMap.put(fieldId, new Pair<Object, Object>(null, attachmentIdArray));
		}
		else if(field.getType().equals(Type.t_input)){
			if(field.getDataType().equals(DataType.dt_integer)){
		Integer newInteger = null;
		try{
			newInteger = Integer.valueOf(fieldValue);
		}
		catch(Exception e){}
		
		if(newInteger != null){
			data.setInteger(fieldId, newInteger);
			extValueMap.put(fieldId, new Pair<Object, Object>(null, newInteger));
		}
			}
			if(field.getDataType().equals(DataType.dt_long)){
		Long newLong = null;
		try{
			newLong = Long.valueOf(fieldValue);
		}
		catch(Exception e){}
		
		if(newLong != null){
			data.setLong(fieldId, newLong);
			extValueMap.put(fieldId, new Pair<Object, Object>(null, newLong));
		}
			}
			if(field.getDataType().equals(DataType.dt_float)){
		Float newFloat = null;
		try{
			newFloat = Float.valueOf(fieldValue);
		}
		catch(Exception e){}
		
		if(newFloat != null){
			data.setFloat(fieldId, newFloat);
			extValueMap.put(fieldId, new Pair<Object, Object>(null, newFloat));
		}
			}
			if(field.getDataType().equals(DataType.dt_double)){
		Double newDouble = null;
		try{
			newDouble = Double.valueOf(fieldValue);
		}
		catch(Exception e){}
		
		if(newDouble != null){
			data.setDouble(fieldId, newDouble);
			extValueMap.put(fieldId, new Pair<Object, Object>(null, newDouble));
		}
			}
			if(field.getDataType().equals(DataType.dt_string) || field.getDataType().equals(DataType.dt_text) || field.getDataType().equals(DataType.dt_editor)){
		data.setString(fieldId, fieldValue);
		extValueMap.put(fieldId, new Pair<Object, Object>(null, fieldValue));
			}
			if(field.getDataType().equals(DataType.dt_timestamp)){
		Date newDate = null;
		try{
			newDate = Date.valueOf(fieldValue);
		}
		catch(Exception e){}
		
		if(newDate != null){
			data.setDate(fieldId, newDate);
			extValueMap.put(fieldId, new Pair<Object, Object>(null, newDate));
		}
			}
		}		
			}
	}
	
	data.setObject("logBaseValueMap", baseValueMap);
	data.setObject("logExtValueMap", extValueMap);
	
	long statAddTaskTime = System.currentTimeMillis();
	Pair<ErrorCode, String> result = das.modifyData(data);
	long endAddTaskTime = System.currentTimeMillis();
	long spendTime = endAddTaskTime-statAddTaskTime;
	
	if(result.getFirst().equals(ErrorCode.success)){
		try{
	ErrorCode eCode = das.commitTranscation();
	if(!eCode.equals(ErrorCode.success))
		throw new Exception("error");
	else{
		das.updateCache(DataAccessAction.update, data.getId().getValue(),data);
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><isError>false</isError>";
		xml += "<taskId>" + data.getId() + "</taskId><taskName>" +XMLUtil.toSafeXMLString(data.getTitle())+ "</taskName></root>";
		out.println(xml);
	}
		}catch(Exception e)
		{
	das.rollbackTranscation();
	System.err.println("\u6570\u636e\u63d0\u4ea4\u5931\u8d25");
	e.printStackTrace();
	out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><isError>true</isError>");
	out.println(result.getSecond());
	out.println("</root>");
		}
	}
	else{
		try{
	System.err.println("\u6570\u636e\u4fee\u6539\u5931\u8d25");
	das.rollbackTranscation();
		}catch(Exception e)
		{
	e.printStackTrace();
		}
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><isError>true</isError>");
		out.println(result.getSecond());
		out.println("</root>");
	}

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
    depend = new com.caucho.vfs.Depend(appDir.lookup("task/addTask.jsp"), -6627600566789743025L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  private final static char []_jsp_string0;
  static {
    _jsp_string0 = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n".toCharArray();
  }
}
