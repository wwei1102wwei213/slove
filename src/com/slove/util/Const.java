package com.slove.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
public class Const {

	public static final int STATUS_OK=0;
	public static final String STATUS_OK_MSG="成功";

	public static final int STATUS_PARAMS_ERROR = 111;
	public static final String STATUS_PARAMS_ERROR_MSG="参数错误";

	public static final int STATUS_SQL_RESULT_ERROR = 112;
	public static final String STATUS_SQL_RESULT_ERROR_MSG="sql结果错误";

	public static final int STATUS_SERVER_ERROR =113;
	public static final String STATUS_SERVER_ERROR_MSG="服务器代码执行出错";

	public static final int STATUS_DELETE_ERROR = 11;
	public static final String STATUS_DELETE_ERROR_MSG="删除失败";	

	public static final int STATUS_LOGIN_ERROR =102;
	public static final String STATUS_LOGIN_ERROR_MSG="账户登录不成功，账号或者密码错误！";
	public static final int STATUS_TOKEN_ERROR =103;
	public static final String STATUS_TOKEN_ERROR_MSG="登录异常，Token生成失败！";

	public static final int STATUS_REGISTER_ERROR =201;
	public static final String STATUS_REGISTER_ERROR_MSG="用户已经存在";
	public static final int STATUS_WITHOUT_RELEASE =301;
	public static final String STATUS_WITHOUT_RELEASE_MSG="没有新版本";

	public static final String STATUS_MSG_PARAMS_ERROR = "参数错误";

	public static String getChinaCode(String phoneNum){
		String url = "https://milu.mcinno.com/api/send/phone_code?type=999&phone_num="+phoneNum;

		String result = "";
		try {
			String r = get(url);
			System.out.println("r:"+r);
			if (r!=null&&!r.equals("")){
				int index = r.lastIndexOf("\"code\":\"");
				if (index!=-1)
					result = r.substring(index+8, index+14);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	/**
	 *  private static final MediaType MEDIA_TYPE_JSON =
            MediaType.parse("application/json; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_X_WWW =
            MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_NORM =
            MediaType.parse("application/x-www-form-urlencoded");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_STREAM =
            MediaType.parse("application/octet-stream;charset=utf-8");
    private static final MediaType MEDIA_TYPE_MARKDOWN =
            MediaType.parse("text/x-markdown; charset=utf-8");//mdiatype 这个需要和服务端保持一致*/
	public static String uploadPostMethod(String path, String phoneNum) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("lost_platform", "android");
		conn.setRequestProperty("lost_appid", "2001");
		conn.setRequestProperty("lost_os_version", "19");
		conn.setRequestProperty("lost_package_name", "candy");
		conn.setRequestProperty("lost_app_version", "1.0.0");
		conn.setRequestProperty("lost_device_id", "12345679");
		conn.setRequestProperty("lost_system_language", "CN");
		conn.setRequestProperty("lost_choose_language", "CN");
		System.out.println("req:" + path);
		byte[] body = new byte[]{};        
		conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(body.length));
		OutputStream os = conn.getOutputStream();
		os.write(body);
		os.flush();
		os.close();
		String req = "";
		System.out.println("req:" + conn.getResponseCode());
		if (conn.getResponseCode() == 200) {

			BufferedReader in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				req += line;
			}
			System.out.println("req:" + req);

			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return req.trim();
		}
		return null;
	}

	public static String get(String url) {  
		BufferedReader in = null;  
		try {  
			URL realUrl = new URL(url);  
			System.out.println("req:" + url);
			// 打开和URL之间的连接  
			URLConnection conn = realUrl.openConnection();  
			// 设置通用的请求属性  
			conn.setRequestProperty("accept", "*/*");  
			conn.setRequestProperty("connection", "Keep-Alive");  
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
			conn.setRequestProperty("lost_platform", "android");
			conn.setRequestProperty("lost_appid", "2001");
			conn.setRequestProperty("lost_os_version", "19");
			conn.setRequestProperty("lost_package_name", "candy");
			conn.setRequestProperty("lost_app_version", "1.0.0");
			conn.setRequestProperty("lost_device_id", "12345679");
			conn.setRequestProperty("lost_system_language", "CN");
			conn.setRequestProperty("lost_choose_language", "CN");
			conn.setConnectTimeout(5000);  
			conn.setReadTimeout(5000);  
			// 建立实际的连接  
			conn.connect();  
			// 定义 BufferedReader输入流来读取URL的响应  
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
			StringBuffer sb = new StringBuffer();  
			String line;  
			while ((line = in.readLine()) != null) {  
				sb.append(line);  
			}  
			return sb.toString();  
		} catch (Exception e) {  
			System.out.println("req:" + e.getMessage());
		}  
		// 使用finally块来关闭输入流  
		finally {  
			try {  
				if (in != null) {  
					in.close();  
				}  
			} catch (Exception e2) {  
				e2.printStackTrace();  
			}  
		}  
		return null;  
	}  

	/*POST /sms/1/text/single HTTP/1.1
	Host: api.infobip.com
	Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
	Content-Type: application/json
	Accept: application/json*/
	public static String jsonPost(String strURL, Map<String, String> params) {  
		try {  
			URL url = new URL(strURL);// 创建连接  
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
			connection.setDoOutput(true);  
//			connection.setDoInput(true);  
//			connection.setUseCaches(false);  
//			connection.setInstanceFollowRedirects(true);  
			connection.setRequestMethod("POST"); // 设置请求方式  
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
			connection.setRequestProperty("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ=="); // 设置发送数据的格式  
			connection.connect();  
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码  
			out.append(new Gson().toJson(params));  
			out.flush();  
			out.close();  
			int code = connection.getResponseCode();  
			InputStream is = null;  
			if (code == 200) {  
				is = connection.getInputStream();  
			} else {  
				is = connection.getErrorStream();  
			}  

			// 读取响应  
			int length = (int) connection.getContentLength();// 获取长度  
			if (length != -1) {  
				byte[] data = new byte[length];  
				byte[] temp = new byte[512];  
				int readLen = 0;  
				int destPos = 0;  
				while ((readLen = is.read(temp)) > 0) {  
					System.arraycopy(temp, 0, data, destPos, readLen);  
					destPos += readLen;  
				}  
				String result = new String(data, "UTF-8"); // utf-8编码  
				return result;  
			}  

		} catch (IOException e) {  
			System.out.println(e.getMessage());
		}  
		return "error"; // 自定义错误信息  
	}  

	private static String getXid(String regpara,String source){   
		StringBuilder builder = new StringBuilder();   
		Pattern p = Pattern.compile("\"code\":\"(0-9)\""); // Regex for the value of the key  
		Matcher m = p.matcher(source);     
		if( m.find() )  
		{   
			return   m.group(1);  

		} else{  
			return null;  
		}   
	} 

	/*{  
   "from":"InfoSMS",
   "to":"41793026727",
   "text":"Test SMS."
}*/
	public static void main(String[] args){
		String url = "http://api.infobip.com//sms/1/text/single";
		Map<String, String> map = new HashMap<>();
		map.put("from", "InfoSMS");
		map.put("to", "8613723701704");
		map.put("text", "Test SMS");
		
		//		String num = "13723701704";
		String r = jsonPost(url, map);
		System.out.println("r:"+r);
	}
}
