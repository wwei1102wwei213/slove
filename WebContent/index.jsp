<%@ page contentType="text/xml;charset=UTF-8"%>

<%
	try {
	//接收客户端传过来是一个form
	//接收数据1
		String word = request.getParameter("word");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
//调biz,调dao访问Mysql
//List<Entity>
//for{ sb.append('')}
		if ("android".equals(word)) {

			sb.append("<data>");
			sb.append("<mp3Url>android.mp3</mp3Url>");
			sb.append("</data>");
		} else {
				sb.append("<data>");
			sb.append("<mp3Url>other.mp3</mp3Url>");
			sb.append("</data>");
		}

		String xmlStr = sb.toString();
		if (xmlStr != null && !"".equals(xmlStr)) {

			response.setContentType("text/xml;charset=UTF-8");
			response.getWriter().write(xmlStr);
			response.flushBuffer();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition","inline;filename=2.xml");
		}
	} catch (Exception e) {

	} finally {
	}
%>


