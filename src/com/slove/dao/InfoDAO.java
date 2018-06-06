package com.slove.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.slove.dao.operate.Modify;
import com.slove.dao.operate.Select;
import com.slove.dao.operate.SetParameter;
import com.slove.entity.InfoEntity;
import com.slove.entity.TokenEntity;
import com.slove.entity.UserEntity;
import com.slove.util.Tools;

public class InfoDAO {
	
	private final static String table_info = "info";

	private final static String col_id = "id";
	private final static String col_nickname = "NickName";
	private final static String col_password = "Password";	
	private final static String col_gender = "Gender";
	private final static String col_Token = "Token";
	private final static String col_Icon = "Icon";
	private final static String col_CreateTime = "CreateTime";
	private final static String col_Birthday = "Birthday";
	private final static String col_UnionID = "UnionID";
	private final static String col_PhoneNum = "PhoneNum";
	private final static String col_Label = "Label";
	private final static String col_Coin = "Coin";
	private final static String col_Location = "Location";
	
	/**
	 * 根据条件查询用户是否存在
	 * @param other 查询参数 
	 * @param type 1：UnionID  2:PhoneNum
	 * @return
	 */
	public boolean checkUserIsExist(final String other, final int type) {
		try {
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement) throws Exception {
					preparedStatement.setString(type==1?8:9, other);					
				}
			}
			String sql = "select * from "+table_info
					+ " where "+(type==1?col_UnionID:col_PhoneNum)+"=? ";
			Select select = new Select();
			List list = select.selectRS(sql,new SetParam());			
			if (list.size()>=1){
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
	 * @return 主键 -1表示失败
	 */
	public InfoEntity insertInfoForResult(final InfoEntity infoEntity, int infoType) {
		InfoEntity entity = null;
		try {
			int result = insertInfo(infoEntity);
			if (result>0) {
				String temp = infoType==1?infoEntity.getUnionID():infoEntity.getPhoneNum();
				entity = queryDetailByOther(temp, infoType);
			} 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Tools.writeException(e);
		}

		return entity;
	}
	
	/**
	 * 
	 * @param infoEntity
	 * @return 主键 -1表示失败
	 */
	public int insertInfo(final InfoEntity infoEntity) {
		int infoId = -1;
		try {
			String sql = "insert into "+table_info + "(" + col_nickname + ","
					+ col_password + "," + col_gender +"," + col_Token +"," + col_Icon +"," 
					+ col_CreateTime +"," + col_Birthday +"," + col_UnionID + "," + col_PhoneNum +"," 
					+ col_Label + "," + col_Coin +"," + col_Location +") values(?,?,?,?,?,?,?,?,?,?,?,?)";
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					preparedStatement.setString(1, infoEntity.getNickName());
					preparedStatement.setString(2, infoEntity.getPassword());
					preparedStatement.setString(3, infoEntity.getGender());
					preparedStatement.setString(4, infoEntity.getToken());
					preparedStatement.setString(5, infoEntity.getIcon());
					preparedStatement.setString(6, infoEntity.getCreateTime());
					preparedStatement.setString(7, infoEntity.getBirthday());
					preparedStatement.setString(8, infoEntity.getUnionID());
					preparedStatement.setString(9, infoEntity.getPhoneNum());
					preparedStatement.setInt(10, infoEntity.getLabel());
					preparedStatement.setInt(11, infoEntity.getCoin());
					preparedStatement.setString(12, infoEntity.getLocation());
				}
			}
			Modify modify = new Modify();
			int id = modify.exec(sql, new SetParam());
			if (id >= 1) {
				infoId = id;
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
					+ " where "+col_nickname+"=? and "+col_password+"=?";
			Select select = new Select();
			List list = select.selectRS(sql,new SetParam());
			
			if (list!=null&&list.size()!=0) {
				int i=0;
				String id = String.valueOf(((Map) list.get(i)).get(col_id));
				String gender = String.valueOf(((Map) list.get(i)).get(col_gender));

				userEntity = new InfoEntity();
				userEntity.setId(Integer.parseInt(id));
//				userEntity.setUsername(username);
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
			if (list!=null&&list.size()>0) {
				String id = String.valueOf(((Map) list.get(0)).get(col_id));
				String username = String.valueOf(((Map) list.get(0)).get(col_nickname));
				String password = String.valueOf(((Map) list.get(0)).get(col_password));
				String gender = String.valueOf(((Map) list.get(0)).get(col_gender));
				String Token = String.valueOf(((Map) list.get(0)).get(col_Token));
				String Icon = String.valueOf(((Map) list.get(0)).get(col_Icon));
				String CreateTime = String.valueOf(((Map) list.get(0)).get(col_CreateTime));
				String Birthday = String.valueOf(((Map) list.get(0)).get(col_Birthday));
				String UnionID = String.valueOf(((Map) list.get(0)).get(col_UnionID));
				String PhoneNum = String.valueOf(((Map) list.get(0)).get(col_PhoneNum));
				String Label = String.valueOf(((Map) list.get(0)).get(col_Label));
				String Coin = String.valueOf(((Map) list.get(0)).get(col_Coin));
				String Location = String.valueOf(((Map) list.get(0)).get(col_Location));

				userEntity = new InfoEntity();
				userEntity.setId(Integer.parseInt(id));
				userEntity.setNickName(username);
				userEntity.setPassword(password);
				userEntity.setGender(gender);
				userEntity.setToken(Token);
				userEntity.setIcon(Icon);
				userEntity.setCreateTime(CreateTime);
				userEntity.setBirthday(Birthday);
				userEntity.setUnionID(UnionID);
				userEntity.setPhoneNum(PhoneNum);
				userEntity.setLabel(Integer.parseInt(Label));
				userEntity.setCoin(Integer.parseInt(Coin));
				userEntity.setLocation(Location);
			} else {
				Tools.writeLog("==========>查询用户信息失败，数据库没找到对应行id:"+infoid);
			}		
		} catch (Exception e) {
			e.printStackTrace();
			Tools.writeException(e);
			Tools.writeLog("==========>查询用户信息失败，其他异常 ， id:"+infoid);
		}
		return userEntity;	
	}
	
	/**
	 * 根据其他唯一值查询
	 * @param other 查询参数
	 * @param type 1：UnionID  2:PhoneNum
	 * @return
	 */
	public InfoEntity queryDetailByOther(final String other, final int type) {
		InfoEntity userEntity = null;
		try {
			/*class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					preparedStatement.setString(type==1?8:9, other);
				}
			}*/
			String sql = "select * from " + table_info + " where "+(type==1?col_UnionID:col_PhoneNum)+"='"+other+"'";
				
			Select select = new Select();
			List list = select.selectRS(sql);			
			if (list!=null&&list.size()>0) {
				String id = String.valueOf(((Map) list.get(0)).get(col_id));
				String username = String.valueOf(((Map) list.get(0)).get(col_nickname));
				String password = String.valueOf(((Map) list.get(0)).get(col_password));
				String gender = String.valueOf(((Map) list.get(0)).get(col_gender));
				String Token = String.valueOf(((Map) list.get(0)).get(col_Token));
				String Icon = String.valueOf(((Map) list.get(0)).get(col_Icon));
				String CreateTime = String.valueOf(((Map) list.get(0)).get(col_CreateTime));
				String Birthday = String.valueOf(((Map) list.get(0)).get(col_Birthday));
				String UnionID = String.valueOf(((Map) list.get(0)).get(col_UnionID));
				String PhoneNum = String.valueOf(((Map) list.get(0)).get(col_PhoneNum));
				String Label = String.valueOf(((Map) list.get(0)).get(col_Label));
				String Coin = String.valueOf(((Map) list.get(0)).get(col_Coin));
				String Location = String.valueOf(((Map) list.get(0)).get(col_Location));

				userEntity = new InfoEntity();
				userEntity.setId(Integer.parseInt(id));
				userEntity.setNickName(username);
				userEntity.setPassword(password);
				userEntity.setGender(gender);
				userEntity.setToken(Token);
				userEntity.setIcon(Icon);
				userEntity.setCreateTime(CreateTime);
				userEntity.setBirthday(Birthday);
				userEntity.setUnionID(UnionID);
				userEntity.setPhoneNum(PhoneNum);
				userEntity.setLabel(Integer.parseInt(Label));
				userEntity.setCoin(Integer.parseInt(Coin));
				userEntity.setLocation(Location);
			} else {
//				Tools.writeLog("==========>查询用户信息失败，数据库没找到对应行 ，other:"+other);
			}		
		} catch (Exception e) {
			e.printStackTrace();
			Tools.writeException(e);
			Tools.writeLog("==========>查询用户信息失败，其他异常 ， other:"+other);
		}
		return userEntity;	
	}
	
	/**
	 * 更新用户信息
	 * @param entity
	 * @param type 
	 * @return
	 */
	public int updateInfo(final InfoEntity entity, final int type) {
		int result = -1;
		try {
			int infoid = entity.getId();
			if (infoid>0) {
				InfoEntity queryEntity = queryDetailByID(infoid+"");
				if (queryEntity!=null) {
					String sql = "update "+ table_info + " set ";
					if (!Tools.isEmpty(entity.getNickName())) {
						sql += col_nickname + "='" + entity.getNickName() + "', ";
					}
					if (!Tools.isEmpty(entity.getBirthday())) {
						sql += col_Birthday + "='" + entity.getBirthday() + "', ";
					}
					if (!Tools.isEmpty(entity.getGender())) {
						sql += col_gender + "='" + entity.getGender() + "', ";
					}
					if (!Tools.isEmpty(entity.getIcon())) {
						sql += col_Icon + "='" + entity.getIcon() + "', ";
					}
					if (sql.endsWith("', ")) {
						sql = sql.substring(0, sql.length()-2);
					}
					sql += " where " + col_id + "=" + infoid;
					Modify modify = new Modify();
					int id = modify.exec(sql);
					if (id >= 1) {
						result = id;
					}
				} 
			}		
		} catch (Exception e) {
			// TODO: handle exception
			Tools.writeException(e);
		}
		return result;
	}
	
}
