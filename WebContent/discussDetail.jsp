<%@page import="com.slove.entity.DiscussEntity"%>
<%@page import="com.slove.util.Tools"%>
<%@page import="com.slove.dao.DiscussDao"%>
<%@page import="com.slove.util.Const"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%
	int status = Const.STATUS_OK;
	String msg = Const.STATUS_OK_MSG;
	boolean infoIsExist = false;
	DiscussEntity entity = null;
	try {
		String id = request.getParameter("id");
		if (Tools.isEmptyOrZero(id)) {
			status = Const.STATUS_LOGIN_ERROR;
			msg = Const.STATUS_MSG_PARAMS_ERROR + ", id is empty!";
		} else {
			id = Tools.FileToUtf8(id);
			DiscussDao dao = new DiscussDao();
			entity = dao.queryDiscussByID(Integer.parseInt(id));
			if (entity == null) {
			}
		}
	} catch (Exception e) {
		status = Const.STATUS_LOGIN_ERROR;
		msg = Const.STATUS_LOGIN_ERROR_MSG + ", " + e.getMessage();
	} finally {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\":\"" + status + "\",");
		buffer.append("\"msg\":\"" + msg + "\",");
		if (Const.STATUS_OK == status) {
			if (entity != null) {
				buffer.append("\"data\":");
				buffer.append(entity.toString());
			} else {
				buffer.append("\"data\":{}");
			}
		}
		buffer.append("}");
		out.write(buffer.toString());
	}
%>


