<%@page import="com.slove.dao.TraceDAO"%>
<%@page import="com.slove.util.Tools"%>

<%@page import="com.slove.entity.TraceEntity"%>
<%@page import="com.slove.entity.SportEntity"%>
<%@page import="com.slove.dao.SportDAO"%>
<%@page import="com.slove.dao.UserDAO"%>
<%@page import="com.slove.entity.UserEntity"%>
<%@page import="com.slove.util.Const"%>
<%@ page contentType="text/html; charset=UTF-8"%><%@page
	import="java.util.*"%>
<%
	int status = Const.STATUS_OK;
	String msg = Const.STATUS_OK_MSG;
	UserEntity userEntity = null;
	boolean userIsExist = false;
	try {

		String username = request.getParameter("username");
		String md5password = request.getParameter("md5password");
		username=Tools.FileToUtf8(username);

		md5password=Tools.FileToUtf8(md5password);
		
		String sportType = request.getParameter("sportType");
		String data = request.getParameter("data");

		UserDAO userDAO = new UserDAO();
		userIsExist = userDAO.checkUserIsExist(username, md5password);
		if (!userIsExist) {
			status = Const.STATUS_LOGIN_ERROR;
			msg = Const.STATUS_LOGIN_ERROR_MSG;
		} else {
		
			SportEntity sportEntity=new SportEntity();
			sportEntity.setUsername(username);
			sportEntity.setSportType(sportType);
			SportDAO sportDAO=new SportDAO();
			int sportId=sportDAO.addSport(sportEntity);
			try{
				String[] array=data.split("\\@");
				TraceDAO traceDAO=new TraceDAO();
				for(int i=0;i<array.length;i++)
				{
					String[] trace=array[i].split("\\|");
					String sportTime=trace[0];
					String latitude=trace[1];
					String longitude=trace[2];
					
					TraceEntity traceEntity=new TraceEntity();
					traceEntity.setSportId(sportId);
					traceEntity.setSportTime(Double.parseDouble(sportTime));
					traceEntity.setLatitude(Double.parseDouble(latitude));
					traceEntity.setLongitude(Double.parseDouble(longitude));

					traceDAO.addTrace(traceEntity, sportId);
				}
			}catch(Exception e)
			{
				status=Const.STATUS_SERVER_ERROR;
				msg=Const.STATUS_SERVER_ERROR_MSG;
			}
			
			
		}

	} catch (Exception e) {//myPic = null;
		status=Const.STATUS_SERVER_ERROR;
		msg=Const.STATUS_SERVER_ERROR_MSG;

	} finally {
		//servlet/ApkUpdateServlet输出的中文也是乱码，android 上不乱码
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\":\"" + status + "\",");
		buffer.append("\"msg\":\"" + msg + "\"");		
		buffer.append("}");
		out.write(buffer.toString());
	}
%>