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
	DiscussEntity[] entities = null;
	try {
		String params = request.getParameter("params");
		Map<String, String> map = Tools.paramsToMap(params);
		String LastID = map.get("LastID");
		String pagesize = map.get("pagesize");
		String label = map.get("label");
		String author = map.get("author");
		String type = map.get("type");
		String order = map.get("order");
		String InfoID = map.get("InfoID");
		if (Tools.isEmptyOrZero(InfoID)) {
			status = Const.STATUS_LOGIN_ERROR;
			msg = Const.STATUS_MSG_PARAMS_ERROR + ", InfoID is empty!";
		} else {
			LastID = Tools.FileToUtf8(LastID);
			int limitId = 0;
			if (!Tools.isNull(LastID)){
				limitId = Integer.parseInt(LastID);
			}
			pagesize = Tools.FileToUtf8(pagesize);
			int limitSize = 0;
			if (!Tools.isNull(pagesize)){
				limitSize = Integer.parseInt(pagesize);
			}
			label = Tools.FileToUtf8(label);
			int queryLabel = 0;
			if (!Tools.isNull(label)){
				queryLabel = Integer.parseInt(label);
			}
			author = Tools.FileToUtf8(author);
			type = Tools.FileToUtf8(type);
			order = Tools.FileToUtf8(order);
			int orderType = 0;
			if (!Tools.isNull(order)){
				orderType = Integer.parseInt(order);
			}
			DiscussDao dao = new DiscussDao();
			entities = dao.queryDiscussByAll(type, queryLabel, author, limitSize, limitId, orderType);
			
		}
	} catch (Exception e) {
		status = Const.STATUS_LOGIN_ERROR;
		msg = Const.STATUS_LOGIN_ERROR_MSG + ", " + e.getMessage();
	} finally {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\":" + status + ",");
		buffer.append("\"msg\":\"" + msg + "\",");
		buffer.append("\"data\":[");
		if (Const.STATUS_OK == status) {
			if (entities != null && entities.length>0) {
				for (int i=0;i<entities.length;i++){
					buffer.append(entities[i].toString());
					if (i!=entities.length-1) buffer.append(",");
				}
			} 
		}
		buffer.append("]");
		buffer.append("}");
		out.write(buffer.toString());
	}
%>


