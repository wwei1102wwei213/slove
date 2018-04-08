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
	int infoId = 0;
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String gender = request.getParameter("gender");
	try {

		username = Tools.FileToUtf8(username);
		password = Tools.FileToUtf8(password);
		gender = Tools.FileToUtf8(gender);

		InfoDAO infoDAO = new InfoDAO();
		infoIsExist = infoDAO.checkUserIsExist(username);
		if (infoIsExist) {
			status = Const.STATUS_REGISTER_ERROR;
			msg = Const.STATUS_REGISTER_ERROR_MSG;
		} else {
			if (password.length() < 6) {
				status = Const.STATUS_REGISTER_ERROR;
				msg = Const.STATUS_REGISTER_ERROR_MSG;
			} else {
				InfoEntity infoEntity = new InfoEntity();
				infoEntity.setUsername(username);
				infoEntity.setPassword(password);
				infoEntity.setGender(gender);
				infoId = infoDAO.insertInfo(infoEntity);
			}
		}

	} catch (Exception e) {
		status = Const.STATUS_REGISTER_ERROR;
		msg = Const.STATUS_REGISTER_ERROR_MSG;
	} finally {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\":\"" + status + "\",");
		buffer.append("\"msg\":\"" + msg + "\",");
		if (Const.STATUS_OK == status) {
			buffer.append("\"data\":\"{");
			buffer.append("\"InfoID\":\"" + infoId + "\",");
			buffer.append("\"UserName\":\"" + username + "\",");
			buffer.append("\"Password\":\"" + password + "\",");
			buffer.append("\"gender\":\"" + gender + "\"");
			buffer.append("}");
		}
		buffer.append("}");
		out.write(buffer.toString().trim());
	}
%>