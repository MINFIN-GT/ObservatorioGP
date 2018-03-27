package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.joda.time.DateTime;
import utilities.CLogger;
import utilities.CMemsql;

public class ResultadoDAO {
	public class Resultado{
		Integer id;
		String nombre_resultado;
		ArrayList<Integer> ejercicios;
		ArrayList<Double[]> ejercicio_data;
	}
	
	public static ArrayList<Resultado> getResultados(String tipo_resultado){
		ArrayList<Resultado> ret = new ArrayList<Resultado>();
		String query = "";
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "SELECT ejercicio,", 
						"       nombre_corto, ", 
						"       SUM(financiero_asignado) financiero_asignado,", 
						"       SUM(financiero_vigente_m1) financiero_vigente_m1,", 
						"       SUM(financiero_vigente_m2) financiero_vigente_m2,", 
						"       SUM(financiero_vigente_m3) financiero_vigente_m3,", 
						"       SUM(financiero_vigente_m4) financiero_vigente_m4,", 
						"       SUM(financiero_vigente_m5) financiero_vigente_m5,", 
						"       SUM(financiero_vigente_m6) financiero_vigente_m6,", 
						"       SUM(financiero_vigente_m7) financiero_vigente_m7,", 
						"       SUM(financiero_vigente_m8) financiero_vigente_m8,", 
						"       SUM(financiero_vigente_m9) financiero_vigente_m9,", 
						"       SUM(financiero_vigente_m10) financiero_vigente_m10,", 
						"       SUM(financiero_vigente_m11) financiero_vigente_m11,", 
						"       SUM(financiero_vigente_m12) financiero_vigente_m12,", 
						"       SUM(financiero_ejecutado_m1) financiero_ejecutado_m1,", 
						"       SUM(financiero_ejecutado_m2) financiero_ejecutado_m2,", 
						"       SUM(financiero_ejecutado_m3) financiero_ejecutado_m3,", 
						"       SUM(financiero_ejecutado_m4) financiero_ejecutado_m4,", 
						"       SUM(financiero_ejecutado_m5) financiero_ejecutado_m5,", 
						"       SUM(financiero_ejecutado_m6) financiero_ejecutado_m6,", 
						"       SUM(financiero_ejecutado_m7) financiero_ejecutado_m7,", 
						"       SUM(financiero_ejecutado_m8) financiero_ejecutado_m8,", 
						"       SUM(financiero_ejecutado_m9) financiero_ejecutado_m9,", 
						"       SUM(financiero_ejecutado_m10) financiero_ejecutado_m10,", 
						"       SUM(financiero_ejecutado_m11) financiero_ejecutado_m11,", 
						"       SUM(financiero_ejecutado_m12) financiero_ejecutado_m12,", 
						"       (SUM(financiero_ejecutado_m1) / SUM(financiero_vigente_m1)) p_financiero_m1,", 
						"       (SUM(financiero_ejecutado_m2) / SUM(financiero_vigente_m2)) p_financiero_m2,", 
						"       (SUM(financiero_ejecutado_m3) / SUM(financiero_vigente_m3)) p_financiero_m3,", 
						"       (SUM(financiero_ejecutado_m4) / SUM(financiero_vigente_m4)) p_financiero_m4,", 
						"       (SUM(financiero_ejecutado_m5) / SUM(financiero_vigente_m5)) p_financiero_m5,", 
						"       (SUM(financiero_ejecutado_m6) / SUM(financiero_vigente_m6)) p_financiero_m6,", 
						"       (SUM(financiero_ejecutado_m7) / SUM(financiero_vigente_m7)) p_financiero_m7,", 
						"       (SUM(financiero_ejecutado_m8) / SUM(financiero_vigente_m8)) p_financiero_m8,", 
						"       (SUM(financiero_ejecutado_m9) / SUM(financiero_vigente_m9)) p_financiero_m9,", 
						"       (SUM(financiero_ejecutado_m10) / SUM(financiero_vigente_m10)) p_financiero_m10,", 
						"       (SUM(financiero_ejecutado_m11) / SUM(financiero_vigente_m11)) p_financiero_m11,", 
						"       (SUM(financiero_ejecutado_m12) / SUM(financiero_vigente_m12)) p_financiero_m12,", 
						"       (SUM(p_fisico_m1 * financiero_vigente_m1) / SUM(financiero_vigente_m1)) pp_fisico_m1,", 
						"       (SUM(p_fisico_m2 * financiero_vigente_m2) / SUM(financiero_vigente_m2)) pp_fisico_m2,", 
						"       (SUM(p_fisico_m3 * financiero_vigente_m3) / SUM(financiero_vigente_m3)) pp_fisico_m3,", 
						"       (SUM(p_fisico_m4 * financiero_vigente_m4) / SUM(financiero_vigente_m4)) pp_fisico_m4,", 
						"       (SUM(p_fisico_m5 * financiero_vigente_m5) / SUM(financiero_vigente_m5)) pp_fisico_m5,", 
						"       (SUM(p_fisico_m6 * financiero_vigente_m6) / SUM(financiero_vigente_m6)) pp_fisico_m6,", 
						"       (SUM(p_fisico_m7 * financiero_vigente_m7) / SUM(financiero_vigente_m7)) pp_fisico_m7,", 
						"       (SUM(p_fisico_m8 * financiero_vigente_m8) / SUM(financiero_vigente_m8)) pp_fisico_m8,", 
						"       (SUM(p_fisico_m9 * financiero_vigente_m9) / SUM(financiero_vigente_m9)) pp_fisico_m9,", 
						"       (SUM(p_fisico_m10 * financiero_vigente_m10) / SUM(financiero_vigente_m10)) pp_fisico_m10,", 
						"       (SUM(p_fisico_m11 * financiero_vigente_m11) / SUM(financiero_vigente_m11)) pp_fisico_m11,", 
						"       (SUM(p_fisico_m12 * financiero_vigente_m12) / SUM(financiero_vigente_m12)) pp_fisico_m12", 
						"FROM (SELECT ejercicio, entidad, unidad_ejecutora, unidad_ejecutora_nombre, programa, programa_nombre, subprograma, proyecto, ", 
						"             obra, actividad, actividad_obra_nombre, nombre_corto,", 
						"             AVG(financiero_asignado) financiero_asignado,", 
						"             AVG(financiero_ejecutado_m1) financiero_ejecutado_m1,", 
						"             AVG(financiero_ejecutado_m2) financiero_ejecutado_m2,", 
						"             AVG(financiero_ejecutado_m3) financiero_ejecutado_m3,", 
						"             AVG(financiero_ejecutado_m4) financiero_ejecutado_m4,", 
						"             AVG(financiero_ejecutado_m5) financiero_ejecutado_m5,", 
						"             AVG(financiero_ejecutado_m6) financiero_ejecutado_m6,", 
						"             AVG(financiero_ejecutado_m6) financiero_ejecutado_m7,", 
						"             AVG(financiero_ejecutado_m7) financiero_ejecutado_m8,", 
						"             AVG(financiero_ejecutado_m8) financiero_ejecutado_m9,", 
						"             AVG(financiero_ejecutado_m9) financiero_ejecutado_m10,", 
						"             AVG(financiero_ejecutado_m10) financiero_ejecutado_m11,", 
						"             AVG(financiero_ejecutado_m12) financiero_ejecutado_m12,", 
						"             AVG(financiero_vigente_m1) financiero_vigente_m1,", 
						"             AVG(financiero_vigente_m2) financiero_vigente_m2,", 
						"             AVG(financiero_vigente_m3) financiero_vigente_m3,", 
						"             AVG(financiero_vigente_m4) financiero_vigente_m4,", 
						"             AVG(financiero_vigente_m5) financiero_vigente_m5,", 
						"             AVG(financiero_vigente_m6) financiero_vigente_m6,", 
						"             AVG(financiero_vigente_m7) financiero_vigente_m7,", 
						"             AVG(financiero_vigente_m8) financiero_vigente_m8,", 
						"             AVG(financiero_vigente_m9) financiero_vigente_m9,", 
						"             AVG(financiero_vigente_m10) financiero_vigente_m10,", 
						"             AVG(financiero_vigente_m11) financiero_vigente_m11,", 
						"             AVG(financiero_vigente_m12) financiero_vigente_m12,", 
						"             AVG(financiero_ejecutado_m1) / ifnull(AVG(IF (financiero_vigente_m1 > 0,financiero_vigente_m1,1)),1) p_financiero_m1,", 
						"             AVG(financiero_ejecutado_m2) / ifnull(AVG(IF (financiero_vigente_m2 > 0,financiero_vigente_m2,1)),1) p_financiero_m2,", 
						"             AVG(financiero_ejecutado_m3) / ifnull(AVG(IF (financiero_vigente_m3 > 0,financiero_vigente_m3,1)),1) p_financiero_m3,", 
						"             AVG(financiero_ejecutado_m4) / ifnull(AVG(IF (financiero_vigente_m4 > 0,financiero_vigente_m4,1)),1) p_financiero_m4,", 
						"             AVG(financiero_ejecutado_m5) / ifnull(AVG(IF (financiero_vigente_m5 > 0,financiero_vigente_m5,1)),1) p_financiero_m5,", 
						"             AVG(financiero_ejecutado_m6) / ifnull(AVG(IF (financiero_vigente_m6 > 0,financiero_vigente_m6,1)),1) p_financiero_m6,", 
						"             AVG(financiero_ejecutado_m7) / ifnull(AVG(IF (financiero_vigente_m7 > 0,financiero_vigente_m7,1)),1) p_financiero_m7,", 
						"             AVG(financiero_ejecutado_m8) / ifnull(AVG(IF (financiero_vigente_m8 > 0,financiero_vigente_m8,1)),1) p_financiero_m8,", 
						"             AVG(financiero_ejecutado_m9) / ifnull(AVG(IF (financiero_vigente_m9 > 0,financiero_vigente_m9,1)),1) p_financiero_m9,", 
						"             AVG(financiero_ejecutado_m10) / ifnull(AVG(IF (financiero_vigente_m10 > 0,financiero_vigente_m10,1)),1) p_financiero_m10,", 
						"             AVG(financiero_ejecutado_m11) / ifnull(AVG(IF (financiero_vigente_m11 > 0,financiero_vigente_m11,1)),1) p_financiero_m11,", 
						"             AVG(financiero_ejecutado_m12) / ifnull(AVG(IF (financiero_vigente_m12 > 0,financiero_vigente_m12,1)),1) p_financiero_m12,", 
						"             (AVG(IFNULL(fisico_ejecutado_m1, IF (fisico_asignado + IFNULL(fisico_modificacion_m1,0) <> 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m1,0) <> 0,fisico_asignado + IFNULL(fisico_modificacion_m1,0),1))) p_fisico_m1,", 
						"             (AVG(IFNULL(fisico_ejecutado_m2, IF (fisico_asignado + IFNULL(fisico_modificacion_m2,0) <> 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m2,0) <> 0,fisico_asignado + IFNULL(fisico_modificacion_m2,0),1))) p_fisico_m2,", 
						"             (AVG(IFNULL(fisico_ejecutado_m3, IF (fisico_asignado + IFNULL(fisico_modificacion_m3,0) <> 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m3,0) <> 0,fisico_asignado + IFNULL(fisico_modificacion_m3,0),1))) p_fisico_m3,", 
						"             (AVG(IFNULL(fisico_ejecutado_m4, IF (fisico_asignado + IFNULL(fisico_modificacion_m4,0) <> 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m4,0) <> 0,fisico_asignado + IFNULL(fisico_modificacion_m4,0),1))) p_fisico_m4,", 
						"             (AVG(IFNULL(fisico_ejecutado_m5, IF (fisico_asignado + IFNULL(fisico_modificacion_m5,0) <> 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m5,0) <> 0,fisico_asignado + IFNULL(fisico_modificacion_m5,0),1))) p_fisico_m5,", 
						"             (AVG(IFNULL(fisico_ejecutado_m6, IF (fisico_asignado + IFNULL(fisico_modificacion_m6,0) <> 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m6,0) <> 0,fisico_asignado + IFNULL(fisico_modificacion_m6,0),1))) p_fisico_m6,", 
						"             (AVG(IFNULL(fisico_ejecutado_m7, IF (fisico_asignado + IFNULL(fisico_modificacion_m7,0) <> 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m7,0) <> 0,fisico_asignado + IFNULL(fisico_modificacion_m7,0),1))) p_fisico_m7,", 
						"             (AVG(IFNULL(fisico_ejecutado_m8, IF (fisico_asignado + IFNULL(fisico_modificacion_m8,0) <> 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m8,0) <> 0,fisico_asignado + IFNULL(fisico_modificacion_m8,0),1))) p_fisico_m8,", 
						"             (AVG(IFNULL(fisico_ejecutado_m9, IF (fisico_asignado + IFNULL(fisico_modificacion_m9,0) <> 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m9,0) <> 0,fisico_asignado + IFNULL(fisico_modificacion_m9,0),1))) p_fisico_m9,", 
						"             (AVG(IFNULL(fisico_ejecutado_m10, IF (fisico_asignado + IFNULL(fisico_modificacion_m10,0) <> 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m10,0) <> 0,fisico_asignado + IFNULL(fisico_modificacion_m10,0),1))) p_fisico_m10,", 
						"             (AVG(IFNULL(fisico_ejecutado_m11, IF (fisico_asignado + IFNULL(fisico_modificacion_m11,0) <> 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m11,0) <> 0,fisico_asignado + IFNULL(fisico_modificacion_m11,0),1))) p_fisico_m11,", 
						"             (AVG(IFNULL(fisico_ejecutado_m12, IF (fisico_asignado + IFNULL(fisico_modificacion_m12,0) <> 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m12,0) <> 0,fisico_asignado + IFNULL(fisico_modificacion_m12,0),1))) p_fisico_m12", 
						"      FROM mv_financiera_fisica", 
						"      WHERE   tipo_resultado = ?",  
						"      GROUP BY entidad, unidad_ejecutora, programa, subprograma, proyecto, actividad, obra, nombre_corto, ejercicio) t1", 
						"GROUP BY ejercicio, nombre_corto");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setString(1, tipo_resultado);
				
				ResultSet rs = pstmt.executeQuery();
				
				String resultado_actual = "";
				Resultado temp = null;
				while(rs.next()){
					if(resultado_actual != rs.getString("nombre_corto")){
						if(temp != null)
							ret.add(temp);
						temp = (new ResultadoDAO()).new Resultado();
						temp.id = 0;
						temp.nombre_resultado = rs.getString("nombre_corto");
						int year = DateTime.now().getYear();
						temp.ejercicios = new ArrayList<Integer>();
						temp.ejercicio_data = new ArrayList<Double[]>();
						for(int i=0;i<5;i++){
							temp.ejercicios.add(i+(year-4));
							temp.ejercicio_data.add(new Double[49]);
							for(int z=0;z<49;z++)
								temp.ejercicio_data.get(temp.ejercicio_data.size()-1)[z]=0.0d;
						}
						resultado_actual = temp.nombre_resultado;
					}
					
					Double[] datos = new Double[49];
					for(int i=0; i<49;i++){
						datos[i] = rs.getDouble(i+3);
					}
					temp.ejercicio_data.set(rs.getInt("ejercicio")-DateTime.now().getYear()+4,datos);
					temp.nombre_resultado = rs.getString("nombre_corto");
				}
				
				if(temp != null)
					ret.add(temp);
			}
			
			CMemsql.close();
			return ret;
		}catch(Exception e){
			CLogger.write("1", ResultadoDAO.class, e);
			CMemsql.close();
		}
		return ret;
	}
}
