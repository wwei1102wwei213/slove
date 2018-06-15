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
			URL url = new URL("http://api.infobip.com/sms/1/text/single");// ��������  
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
			connection.setDoOutput(true);  
			connection.setDoInput(true);  
			connection.setUseCaches(false);  
			connection.setInstanceFollowRedirects(true);  
			connection.setRequestMethod("POST"); // ��������ʽ  
			connection.setRequestProperty("Accept", "application/json"); // ���ý������ݵĸ�ʽ  
			connection.setRequestProperty("Content-Type", "application/json"); // ���÷������ݵĸ�ʽ  
			connection.setRequestProperty("Authorization", "Basic TWljcm9jdWJlMTIzOm1pY3JvY3ViZTEyMw=="); // ���÷������ݵĸ�ʽ  
			connection.connect();  
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8����  
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

			// ��ȡ��Ӧ  
			int length = (int) connection.getContentLength();// ��ȡ����  
			if (length != -1) {  
				byte[] data = new byte[length];  
				byte[] temp = new byte[512];  
				int readLen = 0;  
				int destPos = 0;  
				while ((readLen = is.read(temp)) > 0) {  
					System.arraycopy(temp, 0, data, destPos, readLen);  
					destPos += readLen;  
				}  
				result = new String(data, "UTF-8"); // utf-8����  
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
		return result;  // �Զ��������Ϣ  
	} 
	
}
