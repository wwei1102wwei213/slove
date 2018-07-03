package com.slove.entity;

import com.google.gson.Gson;

public class ConfigEntity {
	
	private int Update;
	private String Summary,Url,VersionNum;
	
	
	
	public ConfigEntity() {
		super();
	}
	public ConfigEntity(int update, String summary, String url, String versionNum) {
		super();
		Update = update;
		Summary = summary;
		Url = url;
		VersionNum = versionNum;
	}
	/**
	 * @return the update
	 */
	public int getUpdate() {
		return Update;
	}
	/**
	 * @param update the update to set
	 */
	public void setUpdate(int update) {
		Update = update;
	}
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return Summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		Summary = summary;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return Url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		Url = url;
	}
	/**
	 * @return the versionNum
	 */
	public String getVersionNum() {
		return VersionNum;
	}
	/**
	 * @param versionNum the versionNum to set
	 */
	public void setVersionNum(String versionNum) {
		VersionNum = versionNum;
	}
	
	@Override
	public String toString() {
		String result = "";
		try {
			ConfigEntity entity = new ConfigEntity(Update, Summary, Url, VersionNum);
			result = new Gson().toJson(entity);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
}
