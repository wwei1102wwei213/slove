package com.slove.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import com.slove.dao.operate.Select;
import com.slove.dao.operate.SetParameter;
import com.slove.entity.ConfigEntity;
import com.slove.util.Tools;

public class ConfigDao {
	private final static String table_config = "config";

	private final static String col_id = "id";
	private final static String col_Update = "Update";
	private final static String col_Summary = "Summary";
	private final static String col_Url = "Url";
	private final static String col_VersionName = "VersionName";
	
	
	public ConfigEntity queryConfig() {
		final int id = 0;
		ConfigEntity entity = null;
		try {
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {}
			}
			String sql = "select * from " + table_config + " where "+col_id+"="+id;
			Select select = new Select();
			List list = select.selectRS(sql,new SetParam());			
			if (list!=null&&list.size()!=0) {
				int i=0;
				String Update = String.valueOf(((Map) list.get(i)).get(col_Update));
				String Summary = String.valueOf(((Map) list.get(i)).get(col_Summary));
				String Url = String.valueOf(((Map) list.get(i)).get(col_Url));
				String VersionNum = String.valueOf(((Map) list.get(i)).get(col_VersionName));

				entity = new ConfigEntity();
				entity.setUpdate(Integer.parseInt(Update));
				entity.setSummary(Summary);
				entity.setUrl(Url);
				entity.setVersionNum(VersionNum);
			} else {

			}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Tools.writeException(e);
		}
		return entity;	
	}
}
