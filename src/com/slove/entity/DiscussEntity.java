package com.slove.entity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class DiscussEntity {

	private int id,label;
	private String type,title,content,labelName,createTime,replyTime,author;	
	public DiscussEntity(){}
	public DiscussEntity(int id, int label, String type, String title, String content, String labelName,
			String createTime, String replyTime, String author) {
		this.id = id;
		this.label = label;
		this.type = type;
		this.title = title;
		this.content = content;
		this.labelName = labelName;
		this.createTime = createTime;
		this.replyTime = replyTime;
		this.author = author;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLabel() {
		return label;
	}
	public void setLabel(int label) {
		this.label = label;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Override
	public String toString() {
		String result = "";
		try {
			DiscussEntity entity = new DiscussEntity(id, label, type, title, content, labelName, createTime, replyTime, author);
			result = new Gson().toJson(entity);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	
	
	
	
}
