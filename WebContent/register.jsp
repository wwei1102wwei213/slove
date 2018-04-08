<%@page import="com.slove.util.Tools"%>
<%@page import="com.slove.dao.UserDAO"%>
<%@page import="com.slove.entity.UserEntity"%>
<%@page import="com.slove.util.Const"%>
<%@ page contentType="text/html; charset=UTF-8"%><%@ page
	language="java" import="com.great.fileUpload.upBean"%><%@ page
	language="java" import="com.great.fileUpload.files"%><%@ page
	language="java" import="com.great.fileUpload.file"%><%@page
	import="java.util.*"%><%@ page import="java.io.*"%>
<%

	String status = "0", msg = Const.STATUS_OK_MSG, iconUrl = "";
	try {
		String imageSaveRooot = "userIcon";
		String username;
%><jsp:useBean id="myUpload" scope="page"
	class="com.great.fileUpload.upBean" />
<%

	myUpload.initialize(pageContext);
		myUpload.setAllowedExtList("gif,jpeg,jpg,png,GIF,JPEG,JPG,PNG"); // set supported extends file name	
		myUpload.setIsCover(true); // cover the same name file

		myUpload.upload(); // get all upload file data

		files myFiles = myUpload.getFiles();

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
				//

				username = myUpload.getRequest().getParameter(
						"username");
				//username = new String(username.getBytes(), "utf-8");

				String md5password = myUpload.getRequest()
						.getParameter("md5password");
				username=Tools.FileToUtf8(username);

				md5password=Tools.FileToUtf8(md5password);
				
				String nickname = myUpload.getRequest().getParameter(
						"nickname");
				nickname=Tools.FileToUtf8(nickname);
				
				String gender = myUpload.getRequest().getParameter(
						"gender");
				gender=Tools.FileToUtf8(gender);
				
				iconUrl = "/slove/" + imageSaveRooot + "/"
						+ fileName;

				String latitude = myUpload.getRequest().getParameter(
						"latitude");
				if (Tools.isNull(latitude))
				{
					latitude="0.000000";
				}
				String longitude = myUpload.getRequest().getParameter(
						"longitude");
				if (Tools.isNull(longitude))
				{
					longitude="0.000000";
				}
				String intro = myUpload.getRequest().getParameter(
						"intro");
				intro=Tools.FileToUtf8(intro);
				long regTime = System.currentTimeMillis();
				UserEntity userEntity = new UserEntity();
				userEntity.setUsername(username);
				userEntity.setMd5password(md5password);
				userEntity.setNickname(nickname);
				userEntity.setGender(gender);
				userEntity.setIconUrl(iconUrl);
				userEntity.setLatitude(Double.parseDouble(latitude));
				userEntity.setLongitude(Double.parseDouble(longitude));
				userEntity.setIntro(intro);
				userEntity.setRegTime(regTime);

				UserDAO userDAO = new UserDAO();
				userDAO.register(userEntity);
				status = String.valueOf(Const.STATUS_OK);

			}
		}

	} catch (Exception e) {//myPic = null;
		e.printStackTrace();

	} finally {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\":\"" + status + "\",");
		buffer.append("\"msg\":\"" + msg + "\",");
		if (Const.STATUS_OK == Integer.parseInt(status)) {
			buffer.append("\"iconUrl\":\"" + iconUrl + "\"");
		}
		buffer.append("}");
		out.write(buffer.toString());
	}
%>