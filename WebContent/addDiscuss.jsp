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
	try {
		String params = request.getParameter("params");
		Map<String, String> map = Tools.paramsToMap(params);
		String labelName = map.get("labelName");
		String title = map.get("title");
		String label = map.get("label");
		String type = map.get("type");
		String content = map.get("content");
		String InfoID = map.get("InfoID");
		String Images = map.get("images");
		if (Tools.isEmptyOrZero(InfoID) || Tools.isNull(title) || Tools.isNull(content)) {
			status = Const.STATUS_PARAMS_ERROR;
			msg = Const.STATUS_PARAMS_ERROR_MSG + ", params is empty for someone!";
		} else {
			labelName = Tools.FileToUtf8(labelName);
			title = Tools.FileToUtf8(title);
			content = Tools.FileToUtf8(content);
			type = Tools.FileToUtf8(type);
			label = Tools.FileToUtf8(label);
			InfoID = Tools.FileToUtf8(InfoID);
			Images = Tools.FileToUtf8(Images);
			DiscussEntity entity = new DiscussEntity();
			int infoId = Integer.parseInt(InfoID);			
			int intLabel = 0;
			if (!Tools.isNull(label)){
				intLabel = Integer.parseInt(label);
			}
			entity.setAuthor(InfoID);
			entity.setType(Tools.isNull(type)?"0":type);
			entity.setTitle(title);
			entity.setContent(content);
			entity.setLabel(intLabel);
			entity.setLabelName(labelName);
			String time = Tools.getTimeStrNow();
			entity.setCreateTime(time);
			entity.setReplyTime(time);
			entity.setImages(Images);
			DiscussDao dao = new DiscussDao();
			int id = dao.insertDiscuss(entity);
			if (id<1){
				status = Const.STATUS_SQL_RESULT_ERROR;
				msg = Const.STATUS_SQL_RESULT_ERROR_MSG + ", insert id = "+id;
			}
		}
	} catch (Exception e) {
		status = Const.STATUS_SERVER_ERROR;
		msg = Const.STATUS_SERVER_ERROR_MSG + ", " + e.getMessage();
	} finally {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\":" + status + ",");
		buffer.append("\"msg\":\"" + msg + "\"");		
		buffer.append("}");
		out.write(buffer.toString());
	}
%>


