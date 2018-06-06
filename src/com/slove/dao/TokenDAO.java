package com.slove.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import com.slove.dao.operate.Modify;
import com.slove.dao.operate.Select;
import com.slove.dao.operate.SetParameter;
import com.slove.entity.DiscussEntity;
import com.slove.entity.TokenEntity;
import com.slove.util.Const;
import com.slove.util.Tools;

public class TokenDAO {
	private final static String table_token = "token";
	
	private final static String col_id = "id";
	private final static String col_infoid = "InfoID";
	private final static String col_token = "Token";	
	private final static String col_createTime = "CreateTime";
	private final static String col_lastTime = "LastTime";

	public TokenEntity[] queryAll() {
		TokenEntity[] entitys = null;
		try {

			String sql = "select " + col_id + "," + col_infoid + "," + col_token + "," + col_createTime + ","
					+ col_lastTime + " from "+ table_token +" order by id desc";

			Select select = new Select();
			List list = select.selectRS(sql);
			entitys = new TokenEntity[list.size()];
			for (int i = 0; i < list.size(); i++) {
				String id = String.valueOf(((Map) list.get(i)).get(col_id));
				String infoid = String.valueOf(((Map) list.get(i)).get(col_infoid));
				String token = String.valueOf(((Map) list.get(i)).get(col_token));
				String lastTime= String.valueOf(((Map) list.get(i)).get(col_lastTime));			
				String createTime = String.valueOf(((Map) list.get(i)).get(col_createTime));

				TokenEntity entity = new TokenEntity();
				entity.setId(Integer.valueOf(id));
				entity.setInfoID(infoid);
				entity.setToken(token);
				entity.setCreateTime(createTime);
				entity.setLastTime(lastTime);

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
	public int insertToken(final TokenEntity entity){
		int result = -1;
		try {
			String sql = "insert into "+ table_token + "(" + col_infoid + "," + col_token + ","
					+ col_createTime + "," + col_lastTime + ") values(?,?,?,?)";
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					preparedStatement.setString(1, entity.getInfoID());
					preparedStatement.setString(2, entity.getToken());
					preparedStatement.setString(3, entity.getCreateTime());
					preparedStatement.setString(4, entity.getLastTime());
				}
			}
			Modify modify = new Modify();

			int id = modify.exec(sql, new SetParam());
			if (id >= 1) {
				result = id;
			}
		} catch (Exception e) {
			// TODO: handle exception
			Tools.writeException(e);
		}
		return result;
	}
	
	/**
	 * 更新token
	 * @param entity
	 * @param type 1：为更新token最后使用时间  0:更新token
	 * @return
	 */
	public int updateToken(final TokenEntity entity, final int type) {
		int result = -1;
		try {
			String infoid = entity.getInfoID();
			if (!Tools.isEmpty(infoid)) {
				TokenEntity queryEntity = queryTokenByID(infoid);
				if (queryEntity!=null) {
					String sql = "";
					if (type==1) {
						sql = "update "+ table_token + " set " + col_lastTime + "='" + entity.getLastTime() + "' where " + col_infoid + "='" + infoid + "'";
					} else {
						sql = "update "+ table_token + " set " + col_token + "='" + entity.getToken() + "', "
								+ col_createTime + "='" + entity.getCreateTime() + "', "
								+ col_lastTime + "='" + entity.getLastTime() 
								+ "' where " + col_infoid + "='" + infoid + "'";
					}
					Modify modify = new Modify();
					int id = modify.exec(sql);
					if (id >= 1) {
						result = id;
					}
				} else {
					result = insertToken(entity);
				}
			}		
		} catch (Exception e) {
			// TODO: handle exception
			Tools.writeException(e);
		}
		return result;
	}

	/**
	 * 根据讨论ID查询
	 * @param id
	 * @return
	 */
	public TokenEntity queryTokenByID(final String infoid) {
		TokenEntity entity = null;
		try {
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
				}
			}
			String sql = "select * from " + table_token + " where "+col_infoid+"="+infoid;
			Select select = new Select();
			List list = select.selectRS(sql,new SetParam());			
			if (list!=null&&list.size()!=0) {
				int i=0;
				String id = String.valueOf(((Map) list.get(i)).get(col_id));
				String token = String.valueOf(((Map) list.get(i)).get(col_token));
				String createTime = String.valueOf(((Map) list.get(i)).get(col_createTime));
				String lastTime = String.valueOf(((Map) list.get(i)).get(col_lastTime));

				entity = new TokenEntity();
				entity.setId(Integer.parseInt(id));
				entity.setInfoID(infoid);
				entity.setToken(token);
				entity.setCreateTime(createTime);
				entity.setLastTime(lastTime);
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
	 * 删除项
	 * @param id
	 * @return
	 */
	public int deleteTokenByID(int id) {
		int status = Const.STATUS_DELETE_ERROR;
		try {
			String sql = "delete from " + table_token + " where id=" + id; 
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
