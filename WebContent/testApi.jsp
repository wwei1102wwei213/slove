<%@ page contentType="text/html; charset=UTF-8"%>
<%
	try {

		String InfoID = request.getParameter("InfoID");
		if ("admin".equals(InfoID)) {
			out.print("connect succeed");
		} else {
			out.print("connect failure");
		}

	} catch (Exception e) {

	} finally {
	}
%>