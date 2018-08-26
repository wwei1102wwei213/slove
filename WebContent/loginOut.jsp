<%@page import="com.slove.entity.InfoEntity"%><%@page import="com.slove.entity.TokenEntity"%><%@page import="com.slove.util.Tools"%><%@page import="com.slove.dao.InfoDAO"%>
<%@page import="com.slove.dao.TokenDAO"%><%@page import="com.slove.util.Const"%><%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%><%@ page import="java.io.*"%><%@ page import="java.lang.*"%>
<%
	int status = Const.STATUS_OK;
	String msg = Const.STATUS_OK_MSG;
	boolean infoIsExist = false;
	InfoEntity entity = null;
	try {
		String params = request.getParameter("params");
		Map<String, String> map = Tools.paramsToMap(params);		
		String UserID = map.get("UserID");
		String Token = map.get("Token");
		if (Tools.isEmpty(Token) || Tools.isEmpty(UserID)) {
			status = -2;
			msg = "用户id或Token为空";
		} else {			
			Token = Tools.FileToUtf8(Token);
			UserID = Tools.FileToUtf8(UserID);
			TokenDAO tokenDao = new TokenDAO();
			int result = tokenDao.deleteTokenByID(Integer.parseInt(UserID));
			if (result!=0) {
				status = -1;
				msg = "删除Token失败";
			}						
		}
	} catch (Exception e) {
		status = Const.STATUS_LOGIN_ERROR;
		msg = Const.STATUS_LOGIN_ERROR_MSG;
	} finally {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\":" + status + ",");
		buffer.append("\"msg\":\"" + msg + "\",");
		if (Const.STATUS_OK == status) {
			buffer.append("\"data\":");
			buffer.append("{}");
		}
		buffer.append("}");
		out.write(buffer.toString().trim());
	}%>