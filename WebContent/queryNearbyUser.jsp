<%@page import="com.slove.dao.UserDAO"%>
<%@page import="com.slove.entity.UserEntity"%>
<%@page import="com.slove.util.Const"%>
<%@page import="com.slove.util.Tools"%>

<%@ page contentType="text/html; charset=UTF-8"%><%@page
	import="java.util.*"%>
<%
	int status = Const.STATUS_OK;
	String msg = Const.STATUS_OK_MSG;
	UserEntity[] userEntitys = null;
	boolean userIsExist = false;
	try {

		String username = request.getParameter("username");
		String md5password = request.getParameter("md5password");
		username=Tools.FileToUtf8(username);

		md5password=Tools.FileToUtf8(md5password);
		UserDAO userDAO = new UserDAO();
		userIsExist = userDAO.checkUserIsExist(username, md5password);
		if (!userIsExist) {
			status = Const.STATUS_LOGIN_ERROR;
			msg = Const.STATUS_LOGIN_ERROR_MSG;
		} else {
			userEntitys = userDAO.queryAll();
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
			buffer.append("\"data\":[");
			for (int i = 0; i < userEntitys.length; i++) {
				UserEntity userEntity = userEntitys[i];
				buffer.append("{");
				buffer.append("\"id\":" + userEntity.getId() + ",");
				buffer.append("\"username\":\""
						+ userEntity.getUsername() + "\",");
				buffer.append("\"md5password\":\""
						+ userEntity.getMd5password() + "\",");
				buffer.append("\"nickname\":\""
						+ userEntity.getNickname() + "\",");
				buffer.append("\"gender\":\"" + userEntity.getGender()
						+ "\",");
				buffer.append("\"iconUrl\":\""
						+ userEntity.getIconUrl() + "\",");
				buffer.append("\"latitude\":"
						+ userEntity.getLatitude() + ",");
				buffer.append("\"longitude\":"
						+ userEntity.getLongitude() + ",");
				buffer.append("\"intro\":\"" + userEntity.getIntro()
						+ "\",");
				buffer.append("\"regTime\":" + userEntity.getRegTime());
				buffer.append("}");
				if (i < userEntitys.length - 1) {
					buffer.append(",");
				}
			}
			buffer.append("]");
		}
		buffer.append("}");
		out.write(buffer.toString());
	}
%>