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

	/**
	 * 新增讨论项
	 * @param entity
	 * @return
	 */
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
	public DiscussEntity[] queryDiscussByLabel(final int queryLabel, int limitSize, int limitId, int orderType) {
		DiscussEntity[] entities = null;
		try {
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {

				}
			}
			String sql = "select * from " + table_discuss + " where ";
			if (limitId>0) {
				sql += (orderType==1?col_replyTime:col_id) + "<" + limitId + " and ";
			} 
			sql += col_label + "&" + queryLabel + "=" + queryLabel + " order by ";
			sql += (orderType==1?col_replyTime:col_id) + " desc";
			if (limitSize<=0||limitSize>20) {
				limitSize = 20;
			} 
			sql += " limit " + limitSize;
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
	public DiscussEntity[] queryDiscussByAuthor(final String queryAuthor, int limitSize, int limitId, int orderType) {
		DiscussEntity[] entities = null;
		try {
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					preparedStatement.setString(4, queryAuthor);
				}
			}
			String sql = "select * from " + table_discuss + " where ";
			if (limitId>0) {
				sql += (orderType==1?col_replyTime:col_id) + "<" + limitId + " and ";
			} 
			sql += col_author + "=? order by ";
			sql += (orderType==1?col_replyTime:col_id) + " desc";
			if (limitSize<=0||limitSize>20) {
				limitSize = 20;
			} 
			sql += " limit " + limitSize;
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
	 * 根据条件查询并分页
	 * @param queryType   类型
	 * @param queryLabel  标签
	 * @param queryAuthor 作者
	 * @param limitSize   分页条数
	 * @param limitId	       上次分页的最小ID
	 * @param orderType   排序类型  1为回复时间排序 0默认排序
	 * @return
	 */
	public DiscussEntity[] queryDiscussByAll(final String queryType, final int queryLabel, final String queryAuthor, 
			int limitSize, int limitId, int orderType) {
		DiscussEntity[] entities = null;
		try {
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {

				}
			}
			String sql = "select * from " + table_discuss + " where ";
			if (limitId>0) {
				sql += (orderType==1?col_replyTime:col_id) + "<" + limitId + " and ";
			} 
			if (!Tools.isNull(queryType)) {
				sql += col_type + "='"+queryType+"' and ";
			}
			if (!Tools.isNull(queryAuthor)) {
				sql += col_author + "='"+queryAuthor+"' and ";
			}
			if (queryLabel>0) {
				sql += col_label + "&" + queryLabel + "=" + queryLabel;
			}
			if (sql.endsWith(" and ")) {
				sql = sql.substring(0, sql.length()-5);
			}
			sql += " order by " + (orderType==1?col_replyTime:col_id) + " desc";		
			if (limitSize<=0||limitSize>20) {
				limitSize = 20;
			} 
			sql += " limit " + limitSize;
			Select select = new Select();
			List list = select.selectRS(sql,new SetParam());			
			if (list!=null&&list.size()!=0) {
				entities = new DiscussEntity[list.size()];
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
	 * 删除项
	 * @param id
	 * @return
	 */
	public int deleteDiscussByID(int id) {
		int status = Const.STATUS_DELETE_ERROR;
		try {
			String sql = "delete from " + table_discuss + " where id=" + id; 
			Modify modify = new Modify();
			int flag = modify.exec(sql);
			if (flag!=-2) {
				status = Const.STATUS_OK;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Tools.writeException(e);
		}
		return status;
	}
}
