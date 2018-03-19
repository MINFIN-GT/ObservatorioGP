package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import utilities.CLogger;
import utilities.CMemsql;

public class HospitalesDAO {
	public class Hospital{
		Integer ejercicio;
		Integer unidad_ejecutora;
		String nombre_unidad_ejecutora;
		Integer rubro;
		String nombre_rubro;
		Integer orden;
		Integer nivel;
		Integer codigo_departamento;
		String nombre_departamento;
		Double asignado;
		Double vigente;
		Double ejecutado;
		Integer treeLevel;
	}
	
	public static ArrayList<Hospital> getInfoHospitales(){
		ArrayList<Hospital> ret = new ArrayList<Hospital>();
		String query = "";
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select * from mv_hospitales", 
						"order by codigo_departamento, unidad_ejecutora, orden, ejercicio;");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();
				
				int orden_actual = 0;
				while(rs.next()){
					Hospital temp = (new HospitalesDAO()).new Hospital();
					
				}
			}
			
			CMemsql.close();
			return ret;
		}catch(Exception e){
			CLogger.write("1", HospitalesDAO.class, e);
			CMemsql.close();
			return null;
		}
	}
}
