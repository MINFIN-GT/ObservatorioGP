package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.joda.time.DateTime;

import utilities.CLogger;
import utilities.CMemsql;

public class SaludDAO {
	public class Hospital{
		String nombre;
		Integer treeLevel;
		Double asignado;
		Double vigente;
		Double ejecutado;
		Integer[] ejercicios;
		ArrayList<Double[]> data_ejercicio;
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
				DateTime init_year = new DateTime().minusYears(4);
				int departamento_actual = 0;
				int hospital_actual = 0;
				Hospital temp=null;
				while(rs.next()){
					if(rs.getInt("codigo_departamento")!=departamento_actual){
						Hospital depto = (new SaludDAO()).new Hospital();
						depto.treeLevel = 0;
						depto.nombre = rs.getString("nombre_departamento");
						ret.add(depto);
					}
					if(rs.getInt("unidad_ejecutora")!=hospital_actual){
						if(temp!=null)
							ret.add(temp);
						temp = (new SaludDAO()).new Hospital();
						temp.nombre = rs.getString("nombre");
						temp.treeLevel = rs.getInt("nivel");
						temp.ejercicios = new Integer[5];
						temp.data_ejercicio = new ArrayList<Double[]>(5);
					}
					temp.ejercicios[rs.getInt("ejercicio")-init_year.getYear()]=rs.getInt("ejercicio");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[0] = rs.getDouble("asignado");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[0] = rs.getDouble("vigente");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[0] = rs.getDouble("ejecucion");
				}
				if(temp!=null)
					ret.add(temp);
			}
			
			CMemsql.close();
			return ret;
		}catch(Exception e){
			CLogger.write("1", SaludDAO.class, e);
			CMemsql.close();
			return null;
		}
	}
}
