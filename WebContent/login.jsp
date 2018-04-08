<%@page import="com.slove.entity.InfoEntity"%>
<%@page import="com.slove.util.Tools"%>
<%@page import="com.slove.dao.InfoDAO"%>
<%@page import="com.slove.util.Const"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%
	int status = Const.STATUS_OK;
	String msg = Const.STATUS_OK_MSG;
	boolean infoIsExist = false;
	InfoEntity entity = null;
	try {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (Tools.isEmpty(username) || Tools.isEmpty(password)) {
			status = Const.STATUS_LOGIN_ERROR;
			msg = Const.STATUS_LOGIN_ERROR_MSG;
		} else {
			username = Tools.FileToUtf8(username);
			password = Tools.FileToUtf8(password);
			InfoDAO dao = new InfoDAO();
			entity = dao.queryDetailByUsername(username, password);
			if (entity == null) {
				status = Const.STATUS_LOGIN_ERROR;
				msg = Const.STATUS_LOGIN_ERROR_MSG;
			}
		}
	} catch (Exception e) {
		status = Const.STATUS_LOGIN_ERROR;
		msg = Const.STATUS_LOGIN_ERROR_MSG;
	} finally {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\":\"" + status + "\",");
		buffer.append("\"msg\":\"" + msg + "\",");
		if (Const.STATUS_OK == status) {
			buffer.append("\"data\":\"{");
			buffer.append("\"InfoID\":\"" + entity.getId() + "\",");
			buffer.append("\"UserName\":\"" + entity.getUsername() + "\",");
			buffer.append("\"Password\":\"" + entity.getPassword() + "\",");
			buffer.append("\"gender\":\"" + entity.getGender() + "\"");
			buffer.append("}");
		}
		buffer.append("}");
		out.write(buffer.toString());
	}
%>


