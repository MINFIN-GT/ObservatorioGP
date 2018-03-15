package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import pojo.Lastupdate;
import utilities.CLogger;
import utilities.CMemsql;

public class LastupdateDAO {
	
	public static Lastupdate getLastupdate(String dashboard_name){
		Lastupdate update=null;
		if(CMemsql.connect()){
			Connection conn = CMemsql.getConnection();
			try{
				PreparedStatement pstm1 =  conn.prepareStatement("select * from update_log where dashboard_name=?");
				pstm1.setString(1, dashboard_name);
				ResultSet results = pstm1.executeQuery();	
				if (results.next()){
					update = new Lastupdate(results.getString("dashboard_name"), results.getTimestamp("last_update"));
				}
				results.close();
				pstm1.close();
			}
			catch(Exception e){
				CLogger.write("1", LastupdateDAO.class, e);
			}
			finally{
				CMemsql.close();
			}
		}
		return update;
	}
}
