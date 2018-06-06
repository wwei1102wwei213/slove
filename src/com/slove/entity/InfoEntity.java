package com.slove.entity;

import java.io.Serializable;

import com.google.gson.Gson;
import com.slove.util.Tools;

public class InfoEntity implements Serializable{
	
	private int id,Label,Coin;
	private String NickName,Password,Gender,Token,Icon,CreateTime,Birthday,UnionID,PhoneNum,Location;	
	
	public InfoEntity() {
		super();
	}	
	
	public InfoEntity(int id, int label, int coin, String nickName, String password, String gender, String token,
			String icon, String createTime, String birthday, String unionID, String phoneNum, String location) {
		super();
		this.id = id;
		Label = label;
		Coin = coin;
		NickName = nickName;
		Password = password;
		Gender = gender;
		Token = token;
		Icon = icon;
		CreateTime = createTime;
		Birthday = birthday;
		UnionID = unionID;
		PhoneNum = phoneNum;
		Location = location;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	/**
	 * @return the label
	 */
	public int getLabel() {
		return Label;
	}


	/**
	 * @param label the label to set
	 */
	public void setLabel(int label) {
		Label = label;
	}


	/**
	 * @return the coin
	 */
	public int getCoin() {
		return Coin;
	}


	/**
	 * @param coin the coin to set
	 */
	public void setCoin(int coin) {
		Coin = coin;
	}


	/**
	 * @return the nickName
	 */
	public String getNickName() {
		if (Tools.isEmpty(NickName)) {
			NickName = "";
		}
		return NickName;
	}


	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		NickName = nickName;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		if (Tools.isEmpty(Password)) {
			Password = "";
		}
		return Password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}


	/**
	 * @return the gender
	 */
	public String getGender() {
		if (Tools.isEmpty(Gender)) {
			Gender = "";
		}
		return Gender;
	}


	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		Gender = gender;
	}


	/**
	 * @return the token
	 */
	public String getToken() {
		if (Tools.isEmpty(Token)) {
			Token = "";
		}
		return Token;
	}


	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		Token = token;
	}


	/**
	 * @return the icon
	 */
	public String getIcon() {
		if (Tools.isEmpty(Icon)) {
			Icon = "";
		}
		return Icon;
	}


	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		Icon = icon;
	}


	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		if (Tools.isEmpty(CreateTime)) {
			CreateTime = "";
		}
		return CreateTime;
	}


	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}


	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		if (Tools.isEmpty(Birthday)) {
			Birthday = "";
		}
		return Birthday;
	}


	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		Birthday = birthday;
	}


	/**
	 * @return the unionID
	 */
	public String getUnionID() {
		if (Tools.isEmpty(UnionID)) {
			UnionID = "";
		}
		return UnionID;
	}


	/**
	 * @param unionID the unionID to set
	 */
	public void setUnionID(String unionID) {
		UnionID = unionID;
	}


	/**
	 * @return the phoneNum
	 */
	public String getPhoneNum() {
		if (Tools.isEmpty(PhoneNum)) {
			PhoneNum = "";
		}
		return PhoneNum;
	}


	/**
	 * @param phoneNum the phoneNum to set
	 */
	public void setPhoneNum(String phoneNum) {
		PhoneNum = phoneNum;
	}


	/**
	 * @return the location
	 */
	public String getLocation() {
		if (Tools.isEmpty(Location)) {
			Location = "";
		}
		return Location;
	}


	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		Location = location;
	}


	@Override
	public String toString() {
		String result = "";
		try {
			InfoEntity entity = new InfoEntity(id, Label, Coin, NickName, Password, Gender, Token, Icon, CreateTime, Birthday, UnionID, PhoneNum, Location);
			result = new Gson().toJson(entity);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	
	

}
