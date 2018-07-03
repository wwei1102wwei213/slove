<%@page import="com.slove.entity.InfoEntity"%><%@page import="com.slove.entity.TokenEntity"%><%@page import="com.slove.util.Tools"%><%@page import="com.slove.dao.InfoDAO"%>
<%@page import="com.slove.dao.TokenDAO"%><%@page import="com.slove.entity.ConfigEntity"%><%@page import="com.slove.dao.ConfigDao"%>
<%@page import="com.slove.util.Const"%><%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%><%@ page import="java.io.*"%>
<%
	int status = Const.STATUS_OK;
	String msg = Const.STATUS_OK_MSG;
	int check = 0;
	boolean infoIsExist = false;
	ConfigEntity configEntity = null;
	try {
		String params = request.getParameter("params");
		Map<String, String> map = Tools.paramsToMap(params);		
		String Token = map.get("Token");
		String UserID = map.get("UserID");
		String vm = map.get("VersionName");
		if (Tools.isEmpty(Token) || Tools.isEmpty(UserID)) {
			check = 1;
			msg = "用户或token为空，跳转登录";
		} else {			
			Token = Tools.FileToUtf8(Token);
			UserID = Tools.FileToUtf8(UserID);
			InfoDAO dao = new InfoDAO();
			TokenDAO tokenDao = new TokenDAO();
			TokenEntity tokenEntity = tokenDao.queryTokenByID(UserID);
			if (tokenEntity==null||Tools.isEmpty(tokenEntity.getToken())||!Token.equals(tokenEntity.getToken())) {
				check = 1;
				msg = "用户token不匹配";
			} 			
		}
		if (!Tools.isEmpty(vm)) {
			configEntity = new ConfigDao().queryConfig();
		}
	} catch (Exception e) {
		status = Const.STATUS_LOGIN_ERROR;
		msg = Const.STATUS_LOGIN_ERROR_MSG;
	} finally {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\":\"" + status + "\",");
		buffer.append("\"check\":" + status + ",");
		buffer.append("\"msg\":\"" + msg + "\",");
		if (Const.STATUS_OK == status) {
			buffer.append("\"data\":");
			if (configEntity==null) {
				buffer.append("{}");
			} else {
				buffer.append(configEntity.toString());
			}			
		}
		buffer.append("}");
		out.write(buffer.toString().trim());
	}%>