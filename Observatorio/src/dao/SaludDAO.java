package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.joda.time.DateTime;

import utilities.CLogger;
import utilities.CMemsql;

public class SaludDAO {
	public class Hospital{
		Integer codigo;
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
				Double u_asignado=0.0d, d_asignado=0.0d;
				Double u_vigente=0.0d, d_vigente=0.0d;
				Double u_ejecutado=0.0d, d_ejecutado=0.0d;
				Hospital temp=null;
				while(rs.next()){
					if(rs.getInt("codigo_departamento")!=departamento_actual){
						Hospital depto = (new SaludDAO()).new Hospital();
						depto.treeLevel = 0;
						depto.nombre = rs.getString("nombre_departamento");
						depto.asignado = d_asignado;
						depto.vigente = d_vigente;
						depto.ejecutado = d_ejecutado;
						ret.add(depto);
						d_asignado=d_vigente=d_ejecutado=0.0d;
					}
					if(rs.getInt("unidad_ejecutora")!=hospital_actual){
						if(temp!=null){
							ret.add(temp);
							int pos_hospital_anterior = ret.size()-1;
							while(ret.get(pos_hospital_anterior).codigo==hospital_actual)
								pos_hospital_anterior--;
							pos_hospital_anterior++;
							ret.get(pos_hospital_anterior+1).asignado = ret.get(pos_hospital_anterior+2).asignado;
							ret.get(pos_hospital_anterior+1).asignado += ret.get(pos_hospital_anterior+3).asignado;
							ret.get(pos_hospital_anterior+1).asignado += ret.get(pos_hospital_anterior+4).asignado;
							ret.get(pos_hospital_anterior+1).vigente = ret.get(pos_hospital_anterior+2).vigente;
							ret.get(pos_hospital_anterior+1).vigente += ret.get(pos_hospital_anterior+3).vigente;
							ret.get(pos_hospital_anterior+1).vigente += ret.get(pos_hospital_anterior+4).vigente;
							ret.get(pos_hospital_anterior+1).ejecutado = ret.get(pos_hospital_anterior+2).ejecutado;
							ret.get(pos_hospital_anterior+1).ejecutado += ret.get(pos_hospital_anterior+3).ejecutado;
							ret.get(pos_hospital_anterior+1).ejecutado += ret.get(pos_hospital_anterior+4).asignado;
						}
						temp = (new SaludDAO()).new Hospital();
						temp.codigo = rs.getInt("unidad_ejecutora");
						temp.nombre = rs.getString("nombre");
						temp.treeLevel = 1;
						temp.ejercicios = new Integer[5];
						temp.data_ejercicio = new ArrayList<Double[]>();
						for(int i=0; i<5; i++)
							temp.data_ejercicio.add(new Double[3]);
						hospital_actual = temp.codigo;
					}
					temp.codigo = rs.getInt("rubro");
					temp.nombre = rs.getString("nombre_rubro");
					temp.treeLevel = rs.getInt("nivel") + 1;
					temp.ejercicios[rs.getInt("ejercicio")-init_year.getYear()]=rs.getInt("ejercicio");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[0] = rs.getDouble("asignado");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[1] = rs.getDouble("vigente");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[2] = rs.getDouble("ejecucion");
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
