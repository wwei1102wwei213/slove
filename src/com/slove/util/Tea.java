package com.slove.util;

import java.nio.ByteBuffer;

public class Tea {
	public static int fn = 0;//�ڽ��ܷ���˷��ص�����ʱ��fn��Ҫ��0
	private final static int[] KEY = new int[]{//���ܽ������õ�KEY
			0x1223311, 0x889922, 0x33333333, 0x44444444
	};

	public static void setKey(int[] key){
		KEY[0] = key[0];
		KEY[1] = key[1];
		KEY[2] = key[2];
		KEY[3] = key[3];
	}

	public static int[] getKey(){
		return KEY;
	}

	private static long BYTE_1 = 0xFFL;
	private static long BYTE_2 = 0xFF00L;
	private static long BYTE_3 = 0xFF0000L;
	private static long BYTE_4 = 0xFF000000L;

	public static Long UIFILTER = Long.decode("0xffffffff");
	//����
	private static byte[] encrypt(byte[] content, int offset, int[] key, int times){//timesΪ��������
		long[] tempInt = byteToInt(content, offset);
		long y = Long.decode(tempInt[0]+""), z = Long.decode(tempInt[1]+""),sum = 0,  i;
		long delta=Long.decode("0x9e3779b9"); //�����㷨��׼����ֵ
		int a = key[0], b = key[1], c = key[2], d = key[3];
		for (i = 0; i < 16; i++) {
			sum += delta;
			sum &= UIFILTER;
			y += ((z << 4) + a) ^ (z + sum) ^ ((z >> 5) + b);
			y &= UIFILTER;
			z += ((y << 4) + c) ^ (y + sum) ^ ((y >> 5) + d);
			z &= UIFILTER;
		}
		return LongToByte(new long[]{y,z}, 0);
	}

	//����
	private static byte[] decrypt(byte[] encryptContent, int offset, int[] key, int times){

		long[] tempInt = byteToInt(encryptContent, offset);
		long y = Long.decode(tempInt[0]+""), z = Long.decode(tempInt[1]+""),sum = Long.decode("0xE3779B90"),  i;
		long delta=Long.decode("0x9e3779b9"); //�����㷨��׼����ֵ
		int a = key[0], b = key[1], c = key[2], d = key[3];
		for(i = 0; i < times; i++) {
			z += (~(((y << 4) + c) ^ (y + sum) ^ ((y >> 5) + d)) + 1);
			z &= UIFILTER;
			y += (~(((z << 4) + a) ^ (z + sum) ^ ((z >> 5) + b)) + 1);
			y &= UIFILTER;
			sum += ((~delta) + 1);
			sum &= UIFILTER;
		}
		return LongToByte(new long[]{y,z}, 0);
	}
	//byte[]������ת��int[]������
	private static long[] byteToInt(byte[] content, int offset){
		if(content==null||content.length==0){
			return new long[]{};
		}
		long[] result = new long[content.length >> 2];
		for(int i = 0, j = 0; j < content.length; i++, j += 4){
			result[i] = (content[j]) & BYTE_1| (content[j + 1]) << 8 & BYTE_2|
					(content[j + 2]) << 16 &BYTE_3 | (content[j + 3]) << 24 & BYTE_4;
		}
		return result;
	}

	private static byte[] LongToByte(long[] content, int offset){
		if(content==null||content.length==0){
			return new byte[]{};
		}
		byte[] result = new byte[content.length << 2];//����2��n�η� == ����nλ �� content.length * 4 == content.length << 2
		for(int i = 0, j = offset; j < result.length; i++, j += 4){
			result[j + 3] = (byte) ((content[i] >> 24) & UIFILTER);
			result[j + 2] = (byte) ((content[i] >> 16) & UIFILTER);
			result[j + 1] = (byte) ((content[i] >> 8) & UIFILTER);
			result[j] = (byte) ((content[i]) & UIFILTER);
		}
		return result;
	}

	//ͨ��TEA�㷨������Ϣ
	public static String encryptByTea(String info){
		if(info==null||info.length()==0){
			return "";
		}
		byte[] temp = info.getBytes();
		int n = 8 - temp.length % 8;//��temp��λ������8�ı���,��Ҫ����λ��

		byte[] encryptStr = new byte[temp.length + n];
		System.arraycopy(temp, 0, encryptStr, 0, temp.length);
		ByteBuffer eBuffer = ByteBuffer.allocate(encryptStr.length);
		byte[] temparray = new byte[8];
		for(int offset = 0; offset < encryptStr.length; offset += 8){
			System.arraycopy(encryptStr, offset, temparray,0 , 8);
			byte[] tempEncrpt = encrypt(temparray, 0, KEY, 16);
			eBuffer.put(tempEncrpt);
		}
		byte[] result = eBuffer.array();
		eBuffer.clear();
		return toHexAscii(result);
	}

	//ͨ��TEA�㷨������Ϣ
	public static  String decryptByTea(String dec){
		if(dec==null||dec.length()==0){
			return "";
		}
		byte[] secretInfo = change(dec);
		byte[] decryptStr = null;
		ByteBuffer tempBuffer = ByteBuffer.allocate(secretInfo.length);
		byte[] temparray = new byte[8];
		for(int offset = 0; offset < secretInfo.length; offset += 8){
			System.arraycopy(secretInfo, offset, temparray, 0, 8);
			decryptStr = decrypt(temparray, 0, KEY, 16);
			tempBuffer.put(decryptStr);
		}
		byte[] tempDecrypt = tempBuffer.array();
		tempBuffer.clear();
		String s = new String(tempDecrypt, 0, tempDecrypt.length);
		return s.trim();
	}

	/*16�����ַ���תbyte[]*/
	public static byte[] change(String inputStr) {
		byte[] result = new byte[inputStr.length() / 2];
		for (int i = 0; i < inputStr.length() / 2; ++i)
			result[i] = (byte)(Integer.parseInt(inputStr.substring(i * 2, i * 2 +2), 16) & 0xff);
		return result;
	}

	/*byte[]ת16�����ַ���*/
	public static String toHexAscii(byte[] data){
		String[] temp = new String[data.length];
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<data.length;i++){
			int t = (int)data[i];
			temp[i] = Integer.toHexString(t & 0xFF).toUpperCase();
			if(temp[i].length()==1) temp[i] = "0" + temp[i];
			sb.append(temp[i]);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		/*String str = "1231324";
		String ec = encryptByTea(str);
		System.out.println(ec);
		System.out.println(decryptByTea(ec));*/

		String str = "http://df.sdf.sad?df=kj&sddf.,~!@#%$";
		String ec = Tools.encodeBase64AndURL(str);
		System.out.println(ec);
		System.out.println(Tools.decodeURLAndBase64(ec));

	}
}
