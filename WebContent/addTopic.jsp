<%@page import="com.slove.dao.TopicDAO"%>
<%@page import="com.slove.entity.TopicEntity"%>
<%@page import="com.slove.dao.UserDAO"%>
<%@page import="com.slove.entity.UserEntity"%>
<%@page import="com.slove.util.Const"%>
<%@page import="com.slove.util.Tools"%>
<%@ page contentType="text/html; charset=UTF-8"%><%@ page
	language="java" import="com.great.fileUpload.upBean"%><%@ page
	language="java" import="com.great.fileUpload.files"%><%@ page
	language="java" import="com.great.fileUpload.file"%><%@page
	import="java.util.*"%><%@ page import="java.io.*"%>
<jsp:useBean id="myUpload" scope="page"
	class="com.great.fileUpload.upBean" />
<%
	//request.setCharacterEncoding("UTF-8");

	int status = Const.STATUS_OK;
	String msg = Const.STATUS_OK_MSG, imageUrl = "";
	boolean userIsExist = false;
	try {
		myUpload.initialize(pageContext);
		myUpload.setAllowedExtList("gif,jpeg,jpg,png,GIF,JPEG,JPG,PNG"); // set supported extends file name	
		myUpload.setIsCover(true); // cover the same name file

		myUpload.upload(); // get all upload file data

		files myFiles = myUpload.getFiles();

		String username = myUpload.getRequest()
				.getParameter("username");
		username = Tools.FileToUtf8(username);

		String md5password = myUpload.getRequest().getParameter(
				"md5password");
		md5password = Tools.FileToUtf8(md5password);

		String content = myUpload.getRequest().getParameter("content");
		content = Tools.FileToUtf8(content);

		String address = myUpload.getRequest().getParameter("address");
		address = Tools.FileToUtf8(address);

		String latitude = myUpload.getRequest()
				.getParameter("latitude");
		if (Tools.isNull(latitude)) {
			latitude = "0.000000";
		}
		String longitude = myUpload.getRequest().getParameter(
				"longitude");
		if (Tools.isNull(longitude)) {
			longitude = "0.000000";
		}
		UserDAO userDAO = new UserDAO();
		userIsExist = userDAO.checkUserIsExist(username, md5password);
		if (!userIsExist) {
			status = Const.STATUS_LOGIN_ERROR;
			msg = Const.STATUS_LOGIN_ERROR_MSG;
		} else {

			String imageSaveRooot = "topicImage";

			int nFileCount = myFiles.getCount();
			if (nFileCount < 1) {
				msg = "no data";
			} else {

				String path = application.getRealPath("/");
				path = path + imageSaveRooot;
				File filePath = new File(path);
				if (!filePath.exists()) {
					filePath.mkdirs();
				}
				filePath = null;
				//out.print(path);
				//path不能有中文，或太长
				//out.print("path=" + path+"</br>");
				myUpload.setRealPath(path);
				com.great.fileUpload.file file = myFiles.getFile(0);
				if (file.getSize() > 1024 * 1024 * 10) {
					msg = "file size must low 10MB,your file size is"
							+ file.getSize();

				} else {
					file.saveAs();
					String fileName = file.getName();

					imageUrl = "/allRunServer/" + imageSaveRooot + "/"
							+ fileName;

					long createTime = System.currentTimeMillis();
					TopicEntity topicEntity = new TopicEntity();
					topicEntity.setUsername(username);
					topicEntity.setContent(content);
					topicEntity.setImageUrl(imageUrl);
					topicEntity.setAddress(address);
					topicEntity.setLatitude(Double
							.parseDouble(latitude));
					topicEntity.setLongitude(Double
							.parseDouble(longitude));
					topicEntity.setCreateTime(createTime);

					TopicDAO topicDAO = new TopicDAO();
					topicDAO.addTopic(topicEntity);
					status = Const.STATUS_OK;

				}
			}
		}
	} catch (Exception e) {//myPic = null;
		e.printStackTrace();

	} finally {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\":\"" + status + "\",");
		buffer.append("\"msg\":\"" + msg + "\",");
		if (Const.STATUS_OK == status) {
			buffer.append("\"imageUrl\":\"" + imageUrl + "\"");
		}
		buffer.append("}");
		out.write(buffer.toString());
	}
%>