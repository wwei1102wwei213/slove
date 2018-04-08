<%@page import="com.slove.dao.TopicDAO"%>
<%@page import="com.slove.util.Tools"%>

<%@page import="com.slove.entity.TopicEntity"%>
<%@page import="com.slove.dao.UserDAO"%>
<%@page import="com.slove.entity.UserEntity"%>
<%@page import="com.slove.util.Const"%>
<%@ page contentType="text/html; charset=UTF-8"%><%@page
	import="java.util.*"%>
<%
	int status = Const.STATUS_OK;
	String msg = Const.STATUS_OK_MSG;
	TopicEntity[] topicEntitys = null;
	boolean userIsExist = false;
	try {

		String username = request.getParameter("username");
		username=Tools.FileToUtf8(username);

		
		if ("dog".equals(username)) {
			status = Const.STATUS_WITHOUT_RELEASE;
			msg = Const.STATUS_WITHOUT_RELEASE_MSG;
		} else {
			status=Const.STATUS_OK;
			msg=Const.STATUS_OK_MSG;
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
			String changeLog = "增加了二维码扫描功能\n修改了某些机型登录失败问题";
			buffer.append("\"version\":\"9.0\",");
			buffer.append("\"changeLog\":\"" + changeLog + "\",");
			buffer.append("\"apkUrl\":\"http://192.168.0.101:8080/allRunServer/allRunSample.apk\"");

		
		}
		buffer.append("}");

		out.write(buffer.toString());
		}
%>