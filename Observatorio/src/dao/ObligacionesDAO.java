package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import utilities.CLogger;
import utilities.CMemsql;

public class ObligacionesDAO {
	
	public class Obligacion{
		public int entdiad_id;
		public String entidad_nombre;
		public Double d2014;
		public Double d2015;
		public Double d2016;
		public Double d2017;
	}
	
	public static ArrayList<Obligacion> getObligaciones(){
		ArrayList<Obligacion> ret = new ArrayList<Obligacion>();
		String query = "SELECT * FROM mv_obligaciones ORDER BY entidad_nombre";
		try{
			if(CMemsql.connect()){
				PreparedStatement pstm = CMemsql.getConnection().prepareStatement(query);
				ResultSet rs = pstm.executeQuery();
				while(rs.next()){
					Obligacion obligacion = (new ObligacionesDAO()).new Obligacion();
					obligacion.entdiad_id = rs.getInt("entidad_id");
					obligacion.entidad_nombre = rs.getString("entidad_nombre");
					obligacion.d2014 = rs.getDouble("d2014");
					obligacion.d2015 = rs.getDouble("d2015");
					obligacion.d2016 = rs.getDouble("d2016");
					obligacion.d2017 = rs.getDouble("d2017");
					ret.add(obligacion);
				}
				CMemsql.close();
			}
			return ret;
		}
		catch(Throwable e){
			CLogger.write("1", ObligacionesDAO.class, e);
		}
		finally{
			CMemsql.close();
		}
		return ret;
	}
}
