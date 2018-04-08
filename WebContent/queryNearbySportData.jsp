<%@page import="com.slove.entity.TraceEntity"%>
<%@page import="com.slove.dao.TraceDAO"%>
<%@page import="com.slove.dao.SportDAO"%>
<%@page import="com.slove.entity.SportEntity"%>
<%@page import="com.slove.dao.TopicDAO"%>
<%@page import="com.slove.entity.TopicEntity"%>
<%@page import="com.slove.dao.UserDAO"%>
<%@page import="com.slove.entity.UserEntity"%>
<%@page import="com.slove.util.Const"%>
<%@page import="com.slove.util.Tools"%>

<%@ page contentType="text/html; charset=UTF-8"%><%@page
	import="java.util.*"%>
<%
	int status = Const.STATUS_OK;
	String msg = Const.STATUS_OK_MSG;
	SportEntity[] sportEntities = null;
	boolean userIsExist = false;
	try {

		String username = request.getParameter("username");
		String md5password = request.getParameter("md5password");
		username=Tools.FileToUtf8(username);
		md5password=Tools.FileToUtf8(md5password);
		
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");

		UserDAO userDAO = new UserDAO();
		userIsExist = userDAO.checkUserIsExist(username, md5password);
		if (!userIsExist) {
			status = Const.STATUS_LOGIN_ERROR;
			msg = Const.STATUS_LOGIN_ERROR_MSG;
		} else {
			SportDAO sportDAO=new SportDAO(); 
			sportEntities = sportDAO.queryNearby(Double.parseDouble(latitude), Double.parseDouble(longitude));
		}

	} catch (Exception e) {//myPic = null;
		e.printStackTrace();

	} finally {
		//servlet/ApkUpdateServlet输出的中文也是乱码，android 上不乱码
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\":\"" + status + "\",");
		buffer.append("\"msg\":\"" + msg + "\",");
		if (status == Const.STATUS_OK) {
			TraceDAO traceDAO=new TraceDAO();
			buffer.append("\"sportData\":[");			
			for (int i = 0; i < sportEntities.length; i++) {
				SportEntity sportEntity = sportEntities[i];
				buffer.append("{");
				buffer.append("\"id\":" + sportEntity.getId() + ",");
				buffer.append("\"username\":\"" + sportEntity.getUsername() + "\",");
				buffer.append("\"sportType\":\"" + sportEntity.getSportType() + "\",");
				TraceEntity[] traceEntities=traceDAO.queryBySportId(sportEntity.getId());
				buffer.append("\"traceData\":[");
				if (traceEntities!=null && traceEntities.length>=1)
				{
					for (int t=0;t<traceEntities.length;t++)
					{
						TraceEntity traceEntity=traceEntities[t];
						buffer.append("{");
						buffer.append("\"id\":" + traceEntity.getId() + ",");
						buffer.append("\"sportId\":" + traceEntity.getSportId() + ",");
						buffer.append("\"sportTime\":" + traceEntity.getSportTime() + ",");
						buffer.append("\"latitude\":" + traceEntity.getLatitude() + ",");
						buffer.append("\"longitude\":" + traceEntity.getLongitude());
						buffer.append("}");
						if (i < traceEntities.length - 1) {
							buffer.append(",");
						}
					}
				}
				buffer.append("]");
				buffer.append("}");
				if (i < sportEntities.length - 1) {
					buffer.append(",");
				}
			}
		}
		buffer.append("]");
		buffer.append("}");
		out.write(buffer.toString());
	}
%>