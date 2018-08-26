package com.slove.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import com.slove.dao.operate.Modify;
import com.slove.dao.operate.Select;
import com.slove.dao.operate.SetParameter;
import com.slove.entity.InfoEntity;
import com.slove.util.Tools;

public class ChatDao {

	private final static String table_info = "ofuser";

	private final static String col_id = "username";
	
	/**
	 * 根据条件查询用户是否存在
	 * @param other 查询参数 
	 * @return
	 */
	public boolean checkUserIsExist(final String other) {
		try {
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement) throws Exception {
									
				}
			}
			String sql = "select * from "+table_info
					+ " where " + col_id + "="+other;
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
	 * 更新用户信息
	 * @param entity
	 * @param type 
	 * @return
	 */
	public int updateInfo(final InfoEntity entity, final int type) {
		int result = -1;
		/*try {
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
		}*/
		return result;
	}
	
	
}
