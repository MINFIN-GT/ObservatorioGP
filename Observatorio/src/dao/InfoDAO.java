package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pojo.TipoInfo;
import utilities.CLogger;
import utilities.CMemsql;

public class InfoDAO {
	
	public static ArrayList<TipoInfo> getTipoResultado(String tipo_resultado){
		ArrayList<TipoInfo> ret = new ArrayList<TipoInfo>();
		String query = "";
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select ", 
						"  case month(CURRENT_TIMESTAMP())", 
						"    when 1 then sum(financiero_ejecutado_m1)", 
						"    when 2 then sum(financiero_ejecutado_m2)", 
						"    when 3 then sum(financiero_ejecutado_m3)", 
						"    when 4 then sum(financiero_ejecutado_m4)", 
						"    when 5 then sum(financiero_ejecutado_m5)", 
						"    when 6 then sum(financiero_ejecutado_m6)", 
						"    when 7 then sum(financiero_ejecutado_m7)", 
						"    when 8 then sum(financiero_ejecutado_m8)", 
						"    when 9 then sum(financiero_ejecutado_m9)", 
						"    when 10 then sum(financiero_ejecutado_m10)", 
						"    when 11 then sum(financiero_ejecutado_m11)", 
						"    when 12 then sum(financiero_ejecutado_m12)", 
						"  end ejecutado,", 
						"  case month(CURRENT_TIMESTAMP())", 
						"    when 1 then sum(financiero_vigente_m1)", 
						"    when 2 then sum(financiero_vigente_m2)", 
						"    when 3 then sum(financiero_vigente_m3)", 
						"    when 4 then sum(financiero_vigente_m4)", 
						"    when 5 then sum(financiero_vigente_m5)", 
						"    when 6 then sum(financiero_vigente_m6)", 
						"    when 7 then sum(financiero_vigente_m7)", 
						"    when 8 then sum(financiero_vigente_m8)", 
						"    when 9 then sum(financiero_vigente_m9)", 
						"    when 10 then sum(financiero_vigente_m10)", 
						"    when 11 then sum(financiero_vigente_m11)", 
						"    when 12 then sum(financiero_vigente_m12)", 
						"  end vigente,", 
						"  case month(CURRENT_TIMESTAMP())", 
						"    when 1 then sum(financiero_ejecutado_m1)/sum(financiero_vigente_m1)", 
						"    when 2 then sum(financiero_ejecutado_m2)/sum(financiero_vigente_m2)", 
						"    when 3 then sum(financiero_ejecutado_m3)/sum(financiero_vigente_m3)", 
						"    when 4 then sum(financiero_ejecutado_m4)/sum(financiero_vigente_m4)", 
						"    when 5 then sum(financiero_ejecutado_m5)/sum(financiero_vigente_m5)", 
						"    when 6 then sum(financiero_ejecutado_m6)/sum(financiero_vigente_m6)", 
						"    when 7 then sum(financiero_ejecutado_m7)/sum(financiero_vigente_m7)", 
						"    when 8 then sum(financiero_ejecutado_m8)/sum(financiero_vigente_m8)", 
						"    when 9 then sum(financiero_ejecutado_m9)/sum(financiero_vigente_m9)", 
						"    when 10 then sum(financiero_ejecutado_m10)/sum(financiero_vigente_m10)", 
						"    when 11 then sum(financiero_ejecutado_m11)/sum(financiero_vigente_m11)", 
						"    when 12 then sum(financiero_ejecutado_m12)/sum(financiero_vigente_m12)", 
						"  end p_financiero,", 
						"  case month(CURRENT_TIMESTAMP())", 
						"    when 1 then sum(p_fisico_m1 * financiero_vigente_m1) / sum(financiero_vigente_m1)", 
						"    when 2 then sum(p_fisico_m2 * financiero_vigente_m2) / sum(financiero_vigente_m2)", 
						"    when 3 then sum(p_fisico_m3 * financiero_vigente_m3) / sum(financiero_vigente_m3)", 
						"    when 4 then sum(p_fisico_m4 * financiero_vigente_m4) / sum(financiero_vigente_m4)", 
						"    when 5 then sum(p_fisico_m5 * financiero_vigente_m5) / sum(financiero_vigente_m5)", 
						"    when 6 then sum(p_fisico_m6 * financiero_vigente_m6) / sum(financiero_vigente_m6)", 
						"    when 7 then sum(p_fisico_m7 * financiero_vigente_m7) / sum(financiero_vigente_m7)", 
						"    when 8 then sum(p_fisico_m8 * financiero_vigente_m8) / sum(financiero_vigente_m8)", 
						"    when 9 then sum(p_fisico_m9 * financiero_vigente_m9) / sum(financiero_vigente_m9)", 
						"    when 10 then sum(p_fisico_m10 * financiero_vigente_m10) / sum(financiero_vigente_m10)", 
						"    when 11 then sum(p_fisico_m11 * financiero_vigente_m11) / sum(financiero_vigente_m11)", 
						"    when 12 then sum(p_fisico_m12 * financiero_vigente_m12) / sum(financiero_vigente_m12)", 
						"  end pp_fisico,",
						"  count(tipo_resultado) cantidad_resultados",
						"	from (  ", 
						"	  select ejercicio, entidad, entidad_nombre, unidad_ejecutora, unidad_ejecutora_nombre, programa, programa_nombre, subprograma, proyecto, obra, actividad, actividad_obra_nombre, tipo_resultado, ", 
						"	  avg(financiero_asignado) financiero_asignado,  ", 
						"	  avg(financiero_ejecutado_m1) financiero_ejecutado_m1,  ", 
						"	  avg(financiero_ejecutado_m2) financiero_ejecutado_m2,  ", 
						"	  avg(financiero_ejecutado_m3) financiero_ejecutado_m3,  ", 
						"	  avg(financiero_ejecutado_m4) financiero_ejecutado_m4,  ", 
						"	  avg(financiero_ejecutado_m5) financiero_ejecutado_m5,  ", 
						"	  avg(financiero_ejecutado_m6) financiero_ejecutado_m6,  ", 
						"	  avg(financiero_ejecutado_m6) financiero_ejecutado_m7,  ", 
						"	  avg(financiero_ejecutado_m7) financiero_ejecutado_m8,  ", 
						"	  avg(financiero_ejecutado_m8) financiero_ejecutado_m9,  ", 
						"	  avg(financiero_ejecutado_m9) financiero_ejecutado_m10,  ", 
						"	  avg(financiero_ejecutado_m10) financiero_ejecutado_m11,  ", 
						"	  avg(financiero_ejecutado_m12) financiero_ejecutado_m12,  ", 
						"	  avg(financiero_vigente_m1) financiero_vigente_m1,  ", 
						"	  avg(financiero_vigente_m2) financiero_vigente_m2,  ", 
						"	  avg(financiero_vigente_m3) financiero_vigente_m3,  ", 
						"	  avg(financiero_vigente_m4) financiero_vigente_m4,  ", 
						"	  avg(financiero_vigente_m5) financiero_vigente_m5,  ", 
						"	  avg(financiero_vigente_m6) financiero_vigente_m6,  ", 
						"	  avg(financiero_vigente_m7) financiero_vigente_m7,  ", 
						"	  avg(financiero_vigente_m8) financiero_vigente_m8,  ", 
						"	  avg(financiero_vigente_m9) financiero_vigente_m9,  ", 
						"	  avg(financiero_vigente_m10) financiero_vigente_m10,  ", 
						"	  avg(financiero_vigente_m11) financiero_vigente_m11,  ", 
						"	  avg(financiero_vigente_m12) financiero_vigente_m12,  ", 
						"	  avg(financiero_ejecutado_m1)/ifnull(avg(if(financiero_vigente_m1>0,financiero_vigente_m1,1)),1) p_financiero_m1,  ", 
						"	  avg(financiero_ejecutado_m2)/ifnull(avg(if(financiero_vigente_m2>0,financiero_vigente_m2,1)),1) p_financiero_m2,  ", 
						"	  avg(financiero_ejecutado_m3)/ifnull(avg(if(financiero_vigente_m3>0,financiero_vigente_m3,1)),1) p_financiero_m3,  ", 
						"	  avg(financiero_ejecutado_m4)/ifnull(avg(if(financiero_vigente_m4>0,financiero_vigente_m4,1)),1) p_financiero_m4,  ", 
						"	  avg(financiero_ejecutado_m5)/ifnull(avg(if(financiero_vigente_m5>0,financiero_vigente_m5,1)),1) p_financiero_m5,  ", 
						"	  avg(financiero_ejecutado_m6)/ifnull(avg(if(financiero_vigente_m6>0,financiero_vigente_m6,1)),1) p_financiero_m6,  ", 
						"	  avg(financiero_ejecutado_m7)/ifnull(avg(if(financiero_vigente_m7>0,financiero_vigente_m7,1)),1) p_financiero_m7,  ", 
						"	  avg(financiero_ejecutado_m8)/ifnull(avg(if(financiero_vigente_m8>0,financiero_vigente_m8,1)),1) p_financiero_m8,  ", 
						"	  avg(financiero_ejecutado_m9)/ifnull(avg(if(financiero_vigente_m9>0,financiero_vigente_m9,1)),1) p_financiero_m9,  ", 
						"	  avg(financiero_ejecutado_m10)/ifnull(avg(if(financiero_vigente_m10>0,financiero_vigente_m10,1)),1) p_financiero_m10,  ", 
						"	  avg(financiero_ejecutado_m11)/ifnull(avg(if(financiero_vigente_m11>0,financiero_vigente_m11,1)),1) p_financiero_m11,  ", 
						"	  avg(financiero_ejecutado_m12)/ifnull(avg(if(financiero_vigente_m12>0,financiero_vigente_m12,1)),1) p_financiero_m12,  ", 
						"	(AVG(IFNULL(fisico_ejecutado_m1, IF (fisico_asignado + IFNULL(fisico_modificacion_m1,0) > 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m1,0) > 0,fisico_asignado + IFNULL(fisico_modificacion_m1,0),1))) p_fisico_m1,  ", 
						"	(AVG(IFNULL(fisico_ejecutado_m2, IF (fisico_asignado + IFNULL(fisico_modificacion_m2,0) > 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m2,0) > 0,fisico_asignado + IFNULL(fisico_modificacion_m2,0),1))) p_fisico_m2,  ", 
						"	(AVG(IFNULL(fisico_ejecutado_m3, IF (fisico_asignado + IFNULL(fisico_modificacion_m3,0) > 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m3,0) > 0,fisico_asignado + IFNULL(fisico_modificacion_m3,0),1))) p_fisico_m3,  ", 
						"	(AVG(IFNULL(fisico_ejecutado_m4, IF (fisico_asignado + IFNULL(fisico_modificacion_m4,0) > 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m4,0) > 0,fisico_asignado + IFNULL(fisico_modificacion_m4,0),1))) p_fisico_m4,  ", 
						"	(AVG(IFNULL(fisico_ejecutado_m5, IF (fisico_asignado + IFNULL(fisico_modificacion_m5,0) > 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m5,0) > 0,fisico_asignado + IFNULL(fisico_modificacion_m5,0),1))) p_fisico_m5,  ", 
						"	(AVG(IFNULL(fisico_ejecutado_m6, IF (fisico_asignado + IFNULL(fisico_modificacion_m6,0) > 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m6,0) > 0,fisico_asignado + IFNULL(fisico_modificacion_m6,0),1))) p_fisico_m6,  ", 
						"	(AVG(IFNULL(fisico_ejecutado_m7, IF (fisico_asignado + IFNULL(fisico_modificacion_m7,0) > 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m7,0) > 0,fisico_asignado + IFNULL(fisico_modificacion_m7,0),1))) p_fisico_m7,  ", 
						"	(AVG(IFNULL(fisico_ejecutado_m8, IF (fisico_asignado + IFNULL(fisico_modificacion_m8,0) > 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m8,0) > 0,fisico_asignado + IFNULL(fisico_modificacion_m8,0),1))) p_fisico_m8,  ", 
						"	(AVG(IFNULL(fisico_ejecutado_m9, IF (fisico_asignado + IFNULL(fisico_modificacion_m9,0) > 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m9,0) > 0,fisico_asignado + IFNULL(fisico_modificacion_m9,0),1))) p_fisico_m9,  ", 
						"	(AVG(IFNULL(fisico_ejecutado_m10, IF (fisico_asignado + IFNULL(fisico_modificacion_m10,0) > 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m10,0) > 0,fisico_asignado + IFNULL(fisico_modificacion_m10,0),1))) p_fisico_m10,  ", 
						"	(AVG(IFNULL(fisico_ejecutado_m11, IF (fisico_asignado + IFNULL(fisico_modificacion_m11,0) > 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m11,0) > 0,fisico_asignado + IFNULL(fisico_modificacion_m11,0),1))) p_fisico_m11,  ", 
						"	(AVG(IFNULL(fisico_ejecutado_m12, IF (fisico_asignado + IFNULL(fisico_modificacion_m12,0) > 0, 0, NULL)) / IF (fisico_asignado + IFNULL(fisico_modificacion_m12,0) > 0,fisico_asignado + IFNULL(fisico_modificacion_m12,0),1))) p_fisico_m12  ", 
						"	  from mv_financiera_fisica   ", 
						"	  where tipo_resultado=? and ejercicio=year(current_timestamp()) ", 
						(tipo_resultado.equals("Otros") ? " AND entidad not in (11130018,11130019) " : ""),
						"	  group by entidad, unidad_ejecutora, programa, subprograma, proyecto, actividad, obra,ejercicio  ", 
						"	) t1  ", 
						"	group by ejercicio, tipo_resultado;");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setString(1, tipo_resultado);
				
				ResultSet rs = CMemsql.runPreparedStatement(pstmt);
				
				while(rs.next()){
					TipoInfo temp = new TipoInfo();
					temp.ejecutado = rs.getDouble("ejecutado");
					temp.vigente = rs.getDouble("vigente");
					temp.p_presupuestario = rs.getDouble("p_financiero");
					temp.p_fisico = rs.getDouble("pp_fisico");
					temp.cantidad = rs.getInt("cantidad_resultados");
					ret.add(temp);
				}
				CMemsql.close();
			}
			
			return ret;
		}catch(Exception e){
			CLogger.write("1", InfoDAO.class, e);
			return ret;
		}
	}
	
	public static TipoInfo getDeuda(){
		TipoInfo ret = null;
		String query = "";
		try{
			if(CMemsql.connect()){
				query = "select sum(ejecutado) ejecutado, sum(vigente) vigente " + 
						"from ( " + 
						"select avg(case month(current_timestamp) " + 
						"		when 1 then financiero_ejecutado_m1 " + 
						"		when 2 then financiero_ejecutado_m2 " + 
						"		when 3 then financiero_ejecutado_m3 " + 
						"		when 4 then financiero_ejecutado_m4 " + 
						"		when 5 then financiero_ejecutado_m5 " + 
						"		when 6 then financiero_ejecutado_m6 " + 
						"		when 7 then financiero_ejecutado_m7 " + 
						"		when 8 then financiero_ejecutado_m8 " + 
						"		when 9 then financiero_ejecutado_m9 " + 
						"		when 10 then financiero_ejecutado_m10 " + 
						"		when 11 then financiero_ejecutado_m11 " + 
						"		when 12 then financiero_ejecutado_m12 " + 
						"		end ) ejecutado, " + 
						"		avg(case month(current_timestamp)  " + 
						"			when 1 then financiero_vigente_m1 " + 
						"			when 2 then financiero_vigente_m2 " + 
						"			when 3 then financiero_vigente_m3 " + 
						"			when 4 then financiero_vigente_m4 " + 
						"			when 5 then financiero_vigente_m5 " + 
						"			when 6 then financiero_vigente_m6 " + 
						"			when 7 then financiero_vigente_m7 " + 
						"			when 8 then financiero_vigente_m8 " + 
						"			when 9 then financiero_vigente_m9 " + 
						"			when 10 then financiero_vigente_m10 " + 
						"			when 11 then financiero_vigente_m11 " + 
						"			when 12 then financiero_vigente_m12 " + 
						"			end " + 
						"		) vigente " + 
						"from mv_financiera_fisica " + 
						"where ejercicio = year(current_timestamp) " + 
						"and entidad = 11130019 " + 
						"group by entidad, unidad_ejecutora, programa, subprograma, proyecto, actividad, obra " + 
						") t1";
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				
				ResultSet rs = CMemsql.runPreparedStatement(pstmt);
				
				if(rs.next()){
					ret = new TipoInfo();
					ret.ejecutado = rs.getDouble("ejecutado");
					ret.vigente = rs.getDouble("vigente");
					ret.p_presupuestario = ret.ejecutado/ret.vigente;
					ret.p_fisico = 0.0d;
					ret.cantidad = 0;
					
				}
			}
		}
		catch(Exception e){
			CLogger.write("2", InfoDAO.class, e);
			return ret;
		}
		return ret;
	}
	
	public static TipoInfo getObligaciones(){
		TipoInfo ret = null;
		String query = "";
		try{
			if(CMemsql.connect()){
				query = "select sum(ejecutado) ejecutado, sum(vigente) vigente " + 
						"from ( " + 
						"select avg(case month(current_timestamp) " + 
						"		when 1 then financiero_ejecutado_m1 " + 
						"		when 2 then financiero_ejecutado_m2 " + 
						"		when 3 then financiero_ejecutado_m3 " + 
						"		when 4 then financiero_ejecutado_m4 " + 
						"		when 5 then financiero_ejecutado_m5 " + 
						"		when 6 then financiero_ejecutado_m6 " + 
						"		when 7 then financiero_ejecutado_m7 " + 
						"		when 8 then financiero_ejecutado_m8 " + 
						"		when 9 then financiero_ejecutado_m9 " + 
						"		when 10 then financiero_ejecutado_m10 " + 
						"		when 11 then financiero_ejecutado_m11 " + 
						"		when 12 then financiero_ejecutado_m12 " + 
						"		end ) ejecutado, " + 
						"		avg(case month(current_timestamp)  " + 
						"			when 1 then financiero_vigente_m1 " + 
						"			when 2 then financiero_vigente_m2 " + 
						"			when 3 then financiero_vigente_m3 " + 
						"			when 4 then financiero_vigente_m4 " + 
						"			when 5 then financiero_vigente_m5 " + 
						"			when 6 then financiero_vigente_m6 " + 
						"			when 7 then financiero_vigente_m7 " + 
						"			when 8 then financiero_vigente_m8 " + 
						"			when 9 then financiero_vigente_m9 " + 
						"			when 10 then financiero_vigente_m10 " + 
						"			when 11 then financiero_vigente_m11 " + 
						"			when 12 then financiero_vigente_m12 " + 
						"			end " + 
						"		) vigente " + 
						"from mv_financiera_fisica " + 
						"where ejercicio = year(current_timestamp) " + 
						"and entidad = 11130018 " + 
						"group by entidad, unidad_ejecutora, programa, subprograma, proyecto, actividad, obra " + 
						") t1";
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				
				ResultSet rs = CMemsql.runPreparedStatement(pstmt);
				
				if(rs.next()){
					ret = new TipoInfo();
					ret.ejecutado = rs.getDouble("ejecutado");
					ret.vigente = rs.getDouble("vigente");
					ret.p_presupuestario = ret.ejecutado/ret.vigente;
					ret.p_fisico = 0.0d;
					ret.cantidad = 0;
					
				}
			}
		}
		catch(Exception e){
			CLogger.write("3", InfoDAO.class, e);
			return ret;
		}
		return ret;
	}
}
