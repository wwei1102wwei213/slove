package com.slove.entity;

import java.io.Serializable;

public class InfoEntity implements Serializable{
	
	private int id;
	private String username,password,gender;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "InfoEntity [id=" + id + ", username=" + username + ", password=" + password + ", gender=" + gender
				+ "]";
	}
	
	

}
