<%@page import="com.slove.util.Tools"%>
<%@page import="com.slove.dao.SmsCodeDao"%><%@page import="com.slove.util.Const"%><%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%><%@ page import="java.io.*"%>
<%
	int status = Const.STATUS_OK;
	String msg = Const.STATUS_OK_MSG;
	boolean infoIsExist = false;
	String mCode = "";
	try {
		String params = request.getParameter("params");
		Map<String, String> map = Tools.paramsToMap(params);
		String PhoneNum = map.get("phone");		
		if (Tools.isEmpty(PhoneNum)) {
			status = -1;
			msg = "phone is empty!";
		} else {			
			PhoneNum = Tools.FileToUtf8(PhoneNum);			
			SmsCodeDao dao = new SmsCodeDao();
			/* StringBuffer buf = new StringBuffer();
			buf.append("{\"from\":\"InfoSMS\", \"to\":\"");
			buf.append(PhoneNum);
			buf.append("\", \"text\":\"[RACANDY]verification:dsfdf");
			buf.append("111111");
			buf.append("\"}");
			mCode = dao.jsonPost(buf.toString()); */				
			mCode = dao.getRadmonCode();				
		}
	} catch (Exception e) {
		status = -2;
		msg = "Exception PhoneNum";
	} finally {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\":" + status + ",");
		buffer.append("\"msg\":\"" + msg + "\",");
		if (Const.STATUS_OK == status) {
			buffer.append("\"code\":\"");
			buffer.append(mCode+"\"");
		}
		buffer.append("}");
		out.write(buffer.toString().trim());
	}%>