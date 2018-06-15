package com.slove.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import com.slove.dao.operate.Modify;
import com.slove.dao.operate.Select;
import com.slove.dao.operate.SetParameter;
import com.slove.entity.TokenEntity;
import com.slove.util.Const;
import com.slove.util.Tools;

public class SmsCodeDao {
	
	public String jsonPost(String params) {  
		String result = "error";
		try {  
			URL url = new URL("http://api.infobip.com/sms/1/text/single");// 创建连接  
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
			connection.setDoOutput(true);  
			connection.setDoInput(true);  
			connection.setUseCaches(false);  
			connection.setInstanceFollowRedirects(true);  
			connection.setRequestMethod("POST"); // 设置请求方式  
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
			connection.setRequestProperty("Authorization", "Basic TWljcm9jdWJlMTIzOm1pY3JvY3ViZTEyMw=="); // 设置发送数据的格式  
			connection.connect();  
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码  
			out.append(params);  
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
				result = new String(data, "UTF-8"); // utf-8编码  
			}  
			try {
				if (is!=null) {
					is.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		} catch (IOException e) {  

		}  
		return result;  // 自定义错误信息  
	} 
	
}
