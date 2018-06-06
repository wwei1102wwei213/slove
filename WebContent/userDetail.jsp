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
		String params = request.getParameter("params");
		Map<String, String> map = Tools.paramsToMap(params);		
		String infoid = map.get("InfoID");
		if (Tools.isEmptyOrZero(infoid)) {
			status = Const.STATUS_LOGIN_ERROR;
			msg = Const.STATUS_LOGIN_ERROR_MSG + "isEmptyOrZero infoid";
		} else {
			infoid = Tools.FileToUtf8(infoid);
			InfoDAO dao = new InfoDAO();
			entity = dao.queryDetailByID(infoid);
			if (entity == null) {
				status = Const.STATUS_LOGIN_ERROR;
				msg = Const.STATUS_LOGIN_ERROR_MSG + "entity == null infoid";
			}
		}
	} catch (Exception e) {
		status = Const.STATUS_LOGIN_ERROR;
		msg = Const.STATUS_LOGIN_ERROR_MSG + ",infoid===>" + e.getMessage();
	} finally {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\":\"" + status + "\",");
		buffer.append("\"msg\":\"" + msg + "\",");
		if (Const.STATUS_OK == status) {
			buffer.append("\"data\":");
			buffer.append(entity.toString());			
		}
		buffer.append("}");
		out.write(buffer.toString());
	}
%>


