package com.slove.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.slove.dao.operate.Modify;
import com.slove.dao.operate.Select;
import com.slove.dao.operate.SetParameter;
import com.slove.entity.InfoEntity;
import com.slove.entity.UserEntity;
import com.slove.util.Tools;

public class InfoDAO {
	
	private final static String table_info = "test";

	private final static String col_id = "id";
	private final static String col_username = "username";
	private final static String col_password = "password";	
	private final static String col_gender = "gender";
	
	public boolean checkUserIsExist(final String username) {
		try {
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					preparedStatement.setString(1, username);					
				}
			}
			String sql = "select * from "+table_info
					+ " where "+col_username+"=? ";

			Select select = new Select();
			List list = select.selectRS(sql,new SetParam());
			
			if (list.size()>=1)
			{
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Tools.writeException(e);
		}
		return false;
	}
	
	/**
	 * 
	 * @param infoEntity
	 * @return Ö÷¼ü -1±íÊ¾Ê§°Ü
	 */
	public int insertInfo(final InfoEntity infoEntity) {
		int infoId = -1;
		try {
			String sql = "insert into "+table_info + "(" + col_username + ","
					+ col_password + "," + col_gender + ") values(?,?,?)";
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					preparedStatement.setString(1, infoEntity.getUsername());
					preparedStatement.setString(2, infoEntity.getPassword());
					preparedStatement.setString(3, infoEntity.getGender());

				}
			}
			Modify modify = new Modify();

			int count = modify.exec(sql, new SetParam());
			if (count >= 1) {

				try {
					class SetSelectParam implements SetParameter {
						public void set(PreparedStatement preparedStatement)
								throws Exception {
							preparedStatement.setString(1, infoEntity.getUsername());
						}
					}
					sql = "select id from "+ table_info + " where " + col_username + "=? ";

					Select select = new Select();
					List list = select.selectRS(sql, new SetSelectParam());
					infoId = Integer.parseInt(String.valueOf(((Map) list.get(0))
							.get(col_id)));

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Tools.writeException(e);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			Tools.writeException(e);
		}

		return infoId;
	}

	
	public InfoEntity queryDetailByUsername(final String username,final String md5Password) {
		InfoEntity userEntity = null;
		try {
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					preparedStatement.setString(1, username);
					preparedStatement.setString(2, md5Password);					

				}
			}
			String sql = "select * from " + table_info
					+ " where "+col_username+"=? and "+col_password+"=? ";

			Select select = new Select();
			List list = select.selectRS(sql,new SetParam());
			
			if (list!=null&&list.size()!=0) {
				int i=0;
				String id = String.valueOf(((Map) list.get(i)).get(col_id));
				String gender = String.valueOf(((Map) list.get(i)).get(col_gender));

				userEntity = new InfoEntity();
				userEntity.setId(Integer.parseInt(id));
				userEntity.setUsername(username);
				userEntity.setPassword(md5Password);
				userEntity.setGender(gender);
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Tools.writeException(e);
		}
		return userEntity;	
	}
	
	public InfoEntity queryDetailByID(final String infoid) {
		InfoEntity userEntity = null;
		try {
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					
				}
			}
			String sql = "select * from " + table_info + " where "+col_id+"="+infoid;

			Select select = new Select();
			List list = select.selectRS(sql,new SetParam());			
			if (list!=null&&list.size()!=0) {
				int i=0;
				String id = String.valueOf(((Map) list.get(i)).get(col_id));
				String username = String.valueOf(((Map) list.get(i)).get(col_username));
				String password = String.valueOf(((Map) list.get(i)).get(col_password));
				String gender = String.valueOf(((Map) list.get(i)).get(col_gender));

				userEntity = new InfoEntity();
				userEntity.setId(Integer.parseInt(id));
				userEntity.setUsername(username);
				userEntity.setPassword(password);
				userEntity.setGender(gender);
			} else {
				userEntity = new InfoEntity();
				userEntity.setId(11);
			}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			userEntity = new InfoEntity();
			userEntity.setId(22);
			e.printStackTrace();
			Tools.writeException(e);
		}
		return userEntity;	
	}
	
}
