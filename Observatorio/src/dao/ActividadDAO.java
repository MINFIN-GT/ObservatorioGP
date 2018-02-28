package dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import utilities.CLogger;
import utilities.CMemsql;

public class ActividadDAO {
	public class Actividad{
		Integer id;
		Integer ejercicio;
		String descripcionActividad;
		BigDecimal p_ejecucion_4;
		BigDecimal p_ejecucion_3;
		BigDecimal p_ejecucion_2;
		BigDecimal p_ejecucion_1;
		BigDecimal p_ejecucion;
		BigDecimal p_avance_4;
		BigDecimal p_avance_3;
		BigDecimal p_avance_2;
		BigDecimal p_avance_1;
		BigDecimal p_avance;
	}
	
	public class EjecucionFisicaFinanciera{
		Integer ejercicio;
		Ejecucion ejecucionFisicaFinanciera;
	}
	
	class Ejecucion{
		PorcentajeEjecucion financiera;
		PorcentajeEjecucion fisica;
	}
	
	class PorcentajeEjecucion{
		BigDecimal[] mes = new BigDecimal[12];
	}
	
	public static ArrayList<Actividad> getActividades(Integer entidad, Integer unidadEjecutora, Integer programa, Integer subPrograma){
		ArrayList<Actividad> ret = new ArrayList<Actividad>();
		String query = "";
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select ejercicio, actividad, actividad_obra_nombre,",
					"sum(case when ejercicio = YEAR(curdate()) -4 then p_ejecucion_financiera_m12 else 0 end) p_ejecucion_fin_4,",
					"sum(case when ejercicio = YEAR(curdate()) -3 then p_ejecucion_financiera_m12 else 0 end) p_ejecucion_fin_3,",
					"sum(case when ejercicio = YEAR(curdate()) -2 then p_ejecucion_financiera_m12 else 0 end) p_ejecucion_fin_2,",
					"sum(case when ejercicio = YEAR(curdate()) -1 then p_ejecucion_financiera_m12 else 0 end) p_ejecucion_fin_1,",
					"sum(case when ejercicio = YEAR(curdate()) then p_ejecucion_financiera_m12 else 0 end) p_ejecucion_fin,",
					"sum(case when ejercicio = YEAR(curdate()) -4 then p_avance_fisico_m12 else 0 end) p_avance_fin_4,",
					"sum(case when ejercicio = YEAR(curdate()) -3 then p_avance_fisico_m12 else 0 end) p_avance_fin_3,",
					"sum(case when ejercicio = YEAR(curdate()) -2 then p_avance_fisico_m12 else 0 end) p_avance_fin_2,",
					"sum(case when ejercicio = YEAR(curdate()) -1 then p_avance_fisico_m12 else 0 end) p_avance_fin_1,",
					"sum(case when ejercicio = YEAR(curdate()) then p_avance_fisico_m12 else 0 end) p_avance_fin",
					"from mv_financiera_fisica where entidad=? and", 
					"unidad_ejecutora=? and programa=? and subprograma=? and proyecto=0",
					"group by actividad, actividad_obra_nombre");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidadEjecutora);
				pstmt.setInt(3, programa);
				pstmt.setInt(4, subPrograma);
				
				ResultSet rs = CMemsql.runPreparedStatement(pstmt);
				
				while(rs.next()){
					Actividad temp = (new ActividadDAO()).new Actividad();
					temp.ejercicio = rs.getInt("ejercicio");
					temp.id = rs.getInt("actividad");
					temp.descripcionActividad = rs.getString("actividad_obra_nombre");
					temp.p_ejecucion_4 = rs.getBigDecimal("p_ejecucion_fin_4").multiply(new BigDecimal(100));
					temp.p_ejecucion_3 = rs.getBigDecimal("p_ejecucion_fin_3").multiply(new BigDecimal(100));
					temp.p_ejecucion_2 = rs.getBigDecimal("p_ejecucion_fin_2").multiply(new BigDecimal(100));
					temp.p_ejecucion_1 = rs.getBigDecimal("p_ejecucion_fin_1").multiply(new BigDecimal(100));
					temp.p_ejecucion = rs.getBigDecimal("p_ejecucion_fin").multiply(new BigDecimal(100));
					temp.p_avance_4 = rs.getBigDecimal("p_avance_fin_4").multiply(new BigDecimal(100));
					temp.p_avance_3 = rs.getBigDecimal("p_avance_fin_3").multiply(new BigDecimal(100));
					temp.p_avance_2 = rs.getBigDecimal("p_avance_fin_2").multiply(new BigDecimal(100));
					temp.p_avance_1 = rs.getBigDecimal("p_avance_fin_1").multiply(new BigDecimal(100));
					temp.p_avance = rs.getBigDecimal("p_avance_fin").multiply(new BigDecimal(100));
					ret.add(temp);
				}
			}	
			
			return ret;
		}catch(Exception e){
			CLogger.write("1", ActividadDAO.class, e);
			return ret;
		}
	}
	
	public static ArrayList<EjecucionFisicaFinanciera> getEjecucionFisicaFinancieraMensual(Integer entidad, Integer unidadEjecutora, Integer programa, Integer subPrograma, Integer actividad){
		ArrayList<EjecucionFisicaFinanciera> ret = new ArrayList<EjecucionFisicaFinanciera>();
		String query = "";
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select ejercicio, avg(p_ejecucion_financiera_m1) as p_fin_1, avg(p_ejecucion_financiera_m2) as p_fin_2,", 
					"avg(p_ejecucion_financiera_m3) as p_fin_3, avg(p_ejecucion_financiera_m4) as p_fin_4,", 
					"avg(p_ejecucion_financiera_m5) as p_fin_5, avg(p_ejecucion_financiera_m6) as p_fin_6,", 
					"avg(p_ejecucion_financiera_m7) as p_fin_7, avg(p_ejecucion_financiera_m8) as p_fin_8,", 
					"avg(p_ejecucion_financiera_m9) as p_fin_9, avg(p_ejecucion_financiera_m10) as p_fin_10,", 
					"avg(p_ejecucion_financiera_m11) as p_fin_11, avg(p_ejecucion_financiera_m12) as p_fin_12,",
					"avg(p_avance_fisico_m1) as p_fis_1, avg(p_avance_fisico_m2) as p_fis_2,", 
					"avg(p_avance_fisico_m3) as p_fis_3, avg(p_avance_fisico_m4) as p_fis_4,", 
					"avg(p_avance_fisico_m5) as p_fis_5, avg(p_avance_fisico_m6) as p_fis_6,", 
					"avg(p_avance_fisico_m7) as p_fis_7, avg(p_avance_fisico_m8) as p_fis_8,", 
					"avg(p_avance_fisico_m9) as p_fis_9, avg(p_avance_fisico_m10) as p_fis_10,", 
					"avg(p_avance_fisico_m11) as p_fis_11, avg(p_avance_fisico_m12) as p_fis_12",
					"from mv_financiera_fisica where entidad=? and unidad_ejecutora=? and programa=?",
					"and subprograma=? and proyecto=0",
					actividad != null ? "and actividad=?" : "",
					"group by ejercicio",
					"order by ejercicio");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidadEjecutora);
				pstmt.setInt(3, programa);
				pstmt.setInt(4, subPrograma);
				if(actividad != null)
					pstmt.setInt(5, actividad);
				
				ResultSet rs = CMemsql.runPreparedStatement(pstmt);
				
				while(rs.next()){
					EjecucionFisicaFinanciera temp = (new ActividadDAO()).new EjecucionFisicaFinanciera();
					PorcentajeEjecucion financiera = (new ActividadDAO()).new PorcentajeEjecucion();
					PorcentajeEjecucion fisica = (new ActividadDAO()).new PorcentajeEjecucion();
					
					Ejecucion ejecucion = (new ActividadDAO()).new Ejecucion();
					
					BigDecimal[] mesFin = new BigDecimal[12];
					BigDecimal[] mesFis = new BigDecimal[12];
					
					temp.ejercicio = rs.getInt("ejercicio");
					
					mesFin[0] = rs.getBigDecimal("p_fin_1");
					mesFin[1] = rs.getBigDecimal("p_fin_2");
					mesFin[2] = rs.getBigDecimal("p_fin_3");
					mesFin[3] = rs.getBigDecimal("p_fin_4");
					mesFin[4] = rs.getBigDecimal("p_fin_5");
					mesFin[5] = rs.getBigDecimal("p_fin_6");
					mesFin[6] = rs.getBigDecimal("p_fin_7");
					mesFin[7] = rs.getBigDecimal("p_fin_8");
					mesFin[8] = rs.getBigDecimal("p_fin_9");
					mesFin[9] = rs.getBigDecimal("p_fin_10");
					mesFin[10] = rs.getBigDecimal("p_fin_11");
					mesFin[11] = rs.getBigDecimal("p_fin_12");
					financiera.mes = mesFin;
					
					mesFis[0] = rs.getBigDecimal("p_fis_1");
					mesFis[1] = rs.getBigDecimal("p_fis_2");
					mesFis[2] = rs.getBigDecimal("p_fis_3");
					mesFis[3] = rs.getBigDecimal("p_fis_4");
					mesFis[4] = rs.getBigDecimal("p_fis_5");
					mesFis[5] = rs.getBigDecimal("p_fis_6");
					mesFis[6] = rs.getBigDecimal("p_fis_7");
					mesFis[7] = rs.getBigDecimal("p_fis_8");
					mesFis[8] = rs.getBigDecimal("p_fis_9");
					mesFis[9] = rs.getBigDecimal("p_fis_10");
					mesFis[10] = rs.getBigDecimal("p_fis_11");
					mesFis[11] = rs.getBigDecimal("p_fis_12");
					fisica.mes = mesFis;
					
					ejecucion.financiera = financiera;
					ejecucion.fisica = fisica;
					
					temp.ejecucionFisicaFinanciera = ejecucion;
					ret.add(temp);
				}
			}
			return ret;
		}catch(Exception e){
			CLogger.write("1", ActividadDAO.class, e);
			return ret;
		}
	} 
}