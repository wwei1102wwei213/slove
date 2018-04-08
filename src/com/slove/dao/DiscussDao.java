package com.slove.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import com.slove.dao.operate.Modify;
import com.slove.dao.operate.Select;
import com.slove.dao.operate.SetParameter;
import com.slove.entity.DiscussEntity;
import com.slove.entity.InfoEntity;
import com.slove.util.Const;
import com.slove.util.Tools;

public class DiscussDao {
	
	private final static String table_discuss = "discuss";

	private final static String col_id = "id";
	private final static String col_type = "type";
	private final static String col_title = "title";
	private final static String col_content = "content";
	private final static String col_author = "author";
	private final static String col_label = "label";
	private final static String col_labelName = "labelName";
	private final static String col_createTime = "createTime";
	private final static String col_replyTime = "replyTime";

	public DiscussEntity[] queryAll() {
		DiscussEntity[] entitys = null;
		try {

			String sql = "select " + col_id + "," + col_type + "," + col_title + ","
					+ col_content + "," + col_author + "," + col_label+ ","
					+ col_labelName + "," + col_createTime+ "," + col_replyTime
					+ " from "+ table_discuss +" order by id desc";

			Select select = new Select();
			List list = select.selectRS(sql);
			entitys = new DiscussEntity[list.size()];
			for (int i = 0; i < list.size(); i++) {
				String id = String.valueOf(((Map) list.get(i)).get(col_id));
				String type = String.valueOf(((Map) list.get(i)).get(col_type));
				String title = String.valueOf(((Map) list.get(i)).get(col_title));
				String content = String.valueOf(((Map) list.get(i)).get(col_content));
				String author = String.valueOf(((Map) list.get(i)).get(col_author));
				String label = String.valueOf(((Map) list.get(i)).get(col_label));
				String labelName = String.valueOf(((Map) list.get(i)).get(col_labelName));
				String createTime = String.valueOf(((Map) list.get(i)).get(col_createTime));
				String replyTime = String.valueOf(((Map) list.get(i)).get(col_replyTime));

				DiscussEntity entity = new DiscussEntity();
				entity.setId(Integer.parseInt(id));
				entity.setType(type);
				entity.setContent(content);
				entity.setAuthor(author);
				entity.setTitle(title);
				entity.setLabel(Integer.parseInt(label));
				entity.setLabelName(labelName);
				entity.setCreateTime(createTime);
				entity.setReplyTime(replyTime);

				entitys[i] = entity;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Tools.writeException(e);
		}

		return entitys;
	}
	
	public int insertDiscuss(final DiscussEntity entity){
		int discussId = -1;
		try {
			String sql = "insert into "+ table_discuss + "(" + col_type + "," + col_title + ","
					+ col_content + "," + col_author + "," + col_label
					+ "," + col_labelName + "," + col_createTime + ","
					+ col_replyTime + ") values(?,?,?,?,?,?,?,?)";
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					preparedStatement.setString(1, entity.getType());
					preparedStatement.setString(2, entity.getTitle());
					preparedStatement.setString(3, entity.getContent());
					preparedStatement.setString(4, entity.getAuthor());
					preparedStatement.setInt(5, entity.getLabel());
					preparedStatement.setString(6, entity.getLabelName());
					preparedStatement.setString(7, entity.getCreateTime());
					preparedStatement.setString(8, entity.getReplyTime());

				}
			}
			Modify modify = new Modify();

			int id = modify.exec(sql, new SetParam());
			if (id >= 1) {
				discussId = id;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return discussId;
	}
	
	/**
	 * 根据讨论ID查询
	 * @param id
	 * @return
	 */
	public DiscussEntity queryDiscussByID(final int id) {
		DiscussEntity entity = null;
		try {
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					
				}
			}
			String sql = "select * from " + table_discuss + " where "+col_id+"="+id;
			Select select = new Select();
			List list = select.selectRS(sql,new SetParam());			
			if (list!=null&&list.size()!=0) {
				int i=0;
				String DiscussID = String.valueOf(((Map) list.get(i)).get(col_id));
				String type = String.valueOf(((Map) list.get(i)).get(col_type));
				String title = String.valueOf(((Map) list.get(i)).get(col_title));
				String content = String.valueOf(((Map) list.get(i)).get(col_content));
				String author = String.valueOf(((Map) list.get(i)).get(col_author));
				String label = String.valueOf(((Map) list.get(i)).get(col_label));
				String labelName = String.valueOf(((Map) list.get(i)).get(col_labelName));
				String createTime = String.valueOf(((Map) list.get(i)).get(col_createTime));
				String replyTime = String.valueOf(((Map) list.get(i)).get(col_replyTime));

				entity = new DiscussEntity();
				entity.setId(Integer.parseInt(DiscussID));
				entity.setType(type);
				entity.setContent(content);
				entity.setAuthor(author);
				entity.setTitle(title);
				entity.setLabel(Integer.parseInt(label));
				entity.setLabelName(labelName);
				entity.setCreateTime(createTime);
				entity.setReplyTime(replyTime);
			} else {
				
			}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Tools.writeException(e);
		}
		return entity;	
	}
	
	/**
	 * 根据标签查询
	 * @param queryLabel
	 * @return
	 */
	public DiscussEntity[] queryDiscussByLabel(final int queryLabel) {
		DiscussEntity[] entities = null;
		try {
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					
				}
			}
			String sql = "select * from " + table_discuss + " where " + col_label + "&" + queryLabel + "=" + queryLabel + " order by "+col_id+" desc";
			Select select = new Select();
			List list = select.selectRS(sql,new SetParam());			
			if (list!=null&&list.size()!=0) {				
				for (int i=0;i<list.size();i++) {
					String DiscussID = String.valueOf(((Map) list.get(i)).get(col_id));
					String type = String.valueOf(((Map) list.get(i)).get(col_type));
					String title = String.valueOf(((Map) list.get(i)).get(col_title));
					String content = String.valueOf(((Map) list.get(i)).get(col_content));
					String author = String.valueOf(((Map) list.get(i)).get(col_author));
					String label = String.valueOf(((Map) list.get(i)).get(col_label));
					String labelName = String.valueOf(((Map) list.get(i)).get(col_labelName));
					String createTime = String.valueOf(((Map) list.get(i)).get(col_createTime));
					String replyTime = String.valueOf(((Map) list.get(i)).get(col_replyTime));

					DiscussEntity entity = new DiscussEntity();
					entity.setId(Integer.parseInt(DiscussID));
					entity.setType(type);
					entity.setContent(content);
					entity.setAuthor(author);
					entity.setTitle(title);
					entity.setLabel(Integer.parseInt(label));
					entity.setLabelName(labelName);
					entity.setCreateTime(createTime);
					entity.setReplyTime(replyTime);
					
					entities[i] = entity;
				}
				
			} else {
				
			}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Tools.writeException(e);
		}
		return entities;
	}
	
	
	/**
	 * 根据作者ID查询
	 * @param queryAuthor
	 * @return
	 */
	public DiscussEntity[] queryDiscussByAuthor(final String queryAuthor) {
		DiscussEntity[] entities = null;
		try {
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					preparedStatement.setString(4, queryAuthor);
				}
			}
			String sql = "select * from " + table_discuss + " where " + col_author + "=? order by "+col_id+" desc";
			Select select = new Select();
			List list = select.selectRS(sql,new SetParam());			
			if (list!=null&&list.size()!=0) {				
				for (int i=0;i<list.size();i++) {
					String DiscussID = String.valueOf(((Map) list.get(i)).get(col_id));
					String type = String.valueOf(((Map) list.get(i)).get(col_type));
					String title = String.valueOf(((Map) list.get(i)).get(col_title));
					String content = String.valueOf(((Map) list.get(i)).get(col_content));
					String author = String.valueOf(((Map) list.get(i)).get(col_author));
					String label = String.valueOf(((Map) list.get(i)).get(col_label));
					String labelName = String.valueOf(((Map) list.get(i)).get(col_labelName));
					String createTime = String.valueOf(((Map) list.get(i)).get(col_createTime));
					String replyTime = String.valueOf(((Map) list.get(i)).get(col_replyTime));
					
					DiscussEntity entity = new DiscussEntity();
					entity.setId(Integer.parseInt(DiscussID));
					entity.setType(type);
					entity.setContent(content);
					entity.setAuthor(author);
					entity.setTitle(title);
					entity.setLabel(Integer.parseInt(label));
					entity.setLabelName(labelName);
					entity.setCreateTime(createTime);
					entity.setReplyTime(replyTime);
					
					entities[i] = entity;
				}
				
			} else {
				
			}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Tools.writeException(e);
		}
		return entities;
	}

	//+" order by id desc";
	/*public int addTopic(final TopicEntity topicEntity) {
		int statusCode = Const.STATUS_SERVER_ERROR;
		try {
			String sql = "insert into topic" + "(" + col_username + ","
					+ col_content + "," + col_imageUrl + "," + col_address
					+ "," + col_latitude + "," + col_longitude + ","
					+ col_createTime + ") values(?,?,?,?,?,?,?)";
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					preparedStatement.setString(1, topicEntity.getUsername());
					preparedStatement.setString(2, topicEntity.getContent());
					preparedStatement.setString(3, topicEntity.getImageUrl());
					preparedStatement.setString(4, topicEntity.getAddress());
					preparedStatement.setDouble(5, topicEntity.getLatitude());
					preparedStatement.setDouble(6, topicEntity.getLongitude());
					preparedStatement.setLong(7, topicEntity.getCreateTime());

				}
			}
			Modify modify = new Modify();

			int id = modify.exec(sql, new SetParam());
			if (id >= 1) {
				statusCode = Const.STATUS_OK;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			statusCode = Const.STATUS_SERVER_ERROR;
			e.printStackTrace();
			Tools.writeException(e);
		}

		return statusCode;
	}*/

}
