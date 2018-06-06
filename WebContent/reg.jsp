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
	
	try {
		String params = request.getParameter("params");
		Map<String, String> map = Tools.paramsToMap(params);
		String nickname = map.get("NickName");
		String birthday = map.get("Birthday");
		String gender = map.get("Gender");
		String UnionID = map.get("UnionID");
		String Icon = map.get("Icon");
		nickname = Tools.FileToUtf8(nickname);
		UnionID = Tools.FileToUtf8(UnionID);
		gender = Tools.FileToUtf8(gender);
		Icon = Tools.FileToUtf8(Icon);
		gender = Tools.FileToUtf8(gender);

		InfoDAO infoDAO = new InfoDAO();
		infoIsExist = infoDAO.checkUserIsExist(UnionID, 1);
		if (infoIsExist) {
			status = Const.STATUS_REGISTER_ERROR;
			msg = Const.STATUS_REGISTER_ERROR_MSG;
		} else {
			/* if (password.length() < 6) {
				status = Const.STATUS_REGISTER_ERROR;
				msg = Const.STATUS_REGISTER_ERROR_MSG;
			} else {
				InfoEntity infoEntity = new InfoEntity();
				infoEntity.setUsername(username);
				infoEntity.setPassword(password);
				infoEntity.setGender(gender);
				infoId = infoDAO.insertInfo(infoEntity);
			} */
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
			/* buffer.append("\"InfoID\":\"" + infoId + "\",");
			buffer.append("\"UserName\":\"" + username + "\",");
			buffer.append("\"Password\":\"" + password + "\",");
			buffer.append("\"gender\":\"" + gender + "\""); */
			buffer.append("}");
		}
		buffer.append("}");
		out.write(buffer.toString().trim());
	}
%>