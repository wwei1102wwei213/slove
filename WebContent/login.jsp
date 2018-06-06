<%@page import="com.slove.entity.InfoEntity"%><%@page import="com.slove.entity.TokenEntity"%><%@page import="com.slove.util.Tools"%><%@page import="com.slove.dao.InfoDAO"%>
<%@page import="com.slove.dao.TokenDAO"%><%@page import="com.slove.util.Const"%><%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%><%@ page import="java.io.*"%>
<%
	int status = Const.STATUS_OK;
	String msg = Const.STATUS_OK_MSG;
	boolean infoIsExist = false;
	InfoEntity entity = null;
	try {
		String params = request.getParameter("params");
		Map<String, String> map = Tools.paramsToMap(params);		
		String PhoneNum = map.get("PhoneNum");
		String Code = map.get("Code");
		if (Tools.isEmpty(PhoneNum) || Tools.isEmpty(Code)) {
			status = Const.STATUS_LOGIN_ERROR;
			msg = "手机号或验证码为空";
		} else {			
			PhoneNum = Tools.FileToUtf8(PhoneNum);
			Code = Tools.FileToUtf8(Code);
			InfoDAO dao = new InfoDAO();
			//验证验证码
			if (Code.length()!=6) {
				status = Const.STATUS_LOGIN_ERROR;
				msg = "验证码格式错误";
			} else {
				entity = dao.queryDetailByOther(PhoneNum, 2);
				//没有该用户,生成新用户
				if (entity == null) {
					InfoEntity newEntity = new InfoEntity();
					newEntity.setPhoneNum(PhoneNum);
					newEntity.setNickName("蜉蝣"+PhoneNum.substring(PhoneNum.length()-4, PhoneNum.length()));
					entity = dao.insertInfoForResult(newEntity, 2);
				}
				//没有该用户,且生成新用户失败
				if (entity == null) {
					status = Const.STATUS_LOGIN_ERROR;
					msg = "登陆异常，请稍后重试";
				//已存在用户
				} else {
					String createTime = System.currentTimeMillis()/1000+"";
					String token = Tools.buildToken(PhoneNum, "PhoneNum", createTime);
					if (Tools.isEmpty(token)) {
						status = Const.STATUS_TOKEN_ERROR;
						msg = Const.STATUS_TOKEN_ERROR_MSG;
					} else {
						TokenDAO tokenDao = new TokenDAO();
						TokenEntity tokenEntity = new TokenEntity();
						tokenEntity.setInfoID(entity.getId()+"");
						tokenEntity.setToken(token);
						tokenEntity.setCreateTime(createTime);
						tokenEntity.setLastTime(createTime);
						int update = tokenDao.updateToken(tokenEntity, 0);
						if (update>0) {
							entity.setToken(token);
						} else {
							status = Const.STATUS_TOKEN_ERROR;
							msg = "登录异常，Token写入失败！";
						}
					}
				}
			}			
		}
	} catch (Exception e) {
		status = Const.STATUS_LOGIN_ERROR;
		msg = Const.STATUS_LOGIN_ERROR_MSG;
	} finally {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\":\"" + status + "\",");
		buffer.append("\"msg\":\"" + msg + "\",");
		if (Const.STATUS_OK == status) {
			buffer.append("\"data\":");
			buffer.append(entity.toString());
		}
		buffer.append("}");
		out.write(buffer.toString().trim());
	}%>