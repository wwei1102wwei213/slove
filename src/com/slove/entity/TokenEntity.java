package com.slove.entity;

import com.google.gson.Gson;

public class TokenEntity {

	private int id;
	
	private String InfoID,Token,CreateTime,LastTime;

	public TokenEntity() {
		super();
	}

	public TokenEntity(int id, String infoID, String token, String createTime, String lastTime) {
		super();
		this.id = id;
		InfoID = infoID;
		Token = token;
		CreateTime = createTime;
		LastTime = lastTime;
	}
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public String getInfoID() {
		return InfoID;
	}

	public void setInfoID(String infoID) {
		InfoID = infoID;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getLastTime() {
		return LastTime;
	}

	public void setLastTime(String lastTime) {
		LastTime = lastTime;
	}
	
	@Override
	public String toString() {
		String result = "";
		try {
			TokenEntity entity = new TokenEntity(id, InfoID, Token, CreateTime, LastTime);
			result = new Gson().toJson(entity);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
}
