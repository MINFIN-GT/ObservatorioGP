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
		String descripcionActividad;
		BigDecimal asignado4;
		BigDecimal asignado3;
		BigDecimal asignado2;
		BigDecimal asignado1;
		BigDecimal asignado;
		BigDecimal vigente4;
		BigDecimal vigente3;
		BigDecimal vigente2;
		BigDecimal vigente1;
		BigDecimal vigente;
		BigDecimal ejecutado4;
		BigDecimal ejecutado3;
		BigDecimal ejecutado2;
		BigDecimal ejecutado1;
		BigDecimal ejecutado;
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
	
	public class VectorValoresFisicos{
		Integer ejercicio;
		Integer mes;
		BigDecimal p_ejecucion;
	}
	
	public class VectorValoresFinancieros{
		Integer ejercicio;
		BigDecimal[] mes = new BigDecimal[12]; 
	}
	
	public static ArrayList<Actividad> getActividades(Integer entidad, Integer unidadEjecutora, Integer programa, Integer subPrograma){
		ArrayList<Actividad> ret = new ArrayList<Actividad>();
		String query = "";
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select mff.actividad, mff.actividad_obra_nombre,",
					"sum(case when mff.ejercicio = YEAR(curdate()) -4 then mff.financiero_asignado else 0 end) asignado_4,", 
					"sum(case when mff.ejercicio = YEAR(curdate()) -3 then mff.financiero_asignado else 0 end) asignado_3,", 
					"sum(case when mff.ejercicio = YEAR(curdate()) -2 then mff.financiero_asignado else 0 end) asignado_2,", 
					"sum(case when mff.ejercicio = YEAR(curdate()) -1 then mff.financiero_asignado else 0 end) asignado_1,",
					"sum(case when mff.ejercicio = YEAR(curdate()) then mff.financiero_asignado else 0 end) asignado,",
					"sum(case when mff.ejercicio = YEAR(curdate()) -4 then (mff.financiero_vigente_m12) else 0 end) vigente_4,", 
					"sum(case when mff.ejercicio = YEAR(curdate()) -3 then (mff.financiero_vigente_m12) else 0 end) vigente_3,", 
					"sum(case when mff.ejercicio = YEAR(curdate()) -2 then (mff.financiero_vigente_m12) else 0 end) vigente_2,", 
					"sum(case when mff.ejercicio = YEAR(curdate()) -1 then (mff.financiero_vigente_m12) else 0 end) vigente_1,", 
					"sum(case when mff.ejercicio = YEAR(curdate()) then (mff.financiero_vigente_m12) else 0 end) vigente,",
					"sum(case when mff.ejercicio = YEAR(curdate()) -4 then (mff.financiero_ejecutado_m12) else 0 end) ejecutado_4,", 
					"sum(case when mff.ejercicio = YEAR(curdate()) -3 then (mff.financiero_ejecutado_m12) else 0 end) ejecutado_3,", 
					"sum(case when mff.ejercicio = YEAR(curdate()) -2 then (mff.financiero_ejecutado_m12) else 0 end) ejecutado_2,",
					"sum(case when mff.ejercicio = YEAR(curdate()) -1 then (mff.financiero_ejecutado_m12) else 0 end) ejecutado_1,", 
					"sum(case when mff.ejercicio = YEAR(curdate()) then (mff.financiero_ejecutado_m12) else 0 end) ejecutado,",
					"sum(case when mff.ejercicio = YEAR(curdate()) -4 then (mff.financiero_ejecutado_m12 / mff.financiero_vigente_m12) else 0 end) p_ejecucion_fin_4,", 
					"sum(case when mff.ejercicio = YEAR(curdate()) -3 then (mff.financiero_ejecutado_m12 / mff.financiero_vigente_m12) else 0 end) p_ejecucion_fin_3,", 
					"sum(case when mff.ejercicio = YEAR(curdate()) -2 then (mff.financiero_ejecutado_m12 / mff.financiero_vigente_m12) else 0 end) p_ejecucion_fin_2,", 
					"sum(case when mff.ejercicio = YEAR(curdate()) -1 then (mff.financiero_ejecutado_m12 / mff.financiero_vigente_m12) else 0 end) p_ejecucion_fin_1,", 
					"sum(case when mff.ejercicio = YEAR(curdate()) then (mff.financiero_ejecutado_m12 / mff.financiero_vigente_m12) else 0 end) p_ejecucion_fin,", 
					"avg(case when mff.ejercicio = YEAR(curdate()) -4 then (t2.p_avance_f) else 0 end) p_avance_fis_4,", 
					"avg(case when mff.ejercicio = YEAR(curdate()) -3 then (t2.p_avance_f) else 0 end) p_avance_fis_3,",
					"avg(case when mff.ejercicio = YEAR(curdate()) -2 then (t2.p_avance_f) else 0 end) p_avance_fis_2,", 
					"avg(case when mff.ejercicio = YEAR(curdate()) -1 then (t2.p_avance_f) else 0 end) p_avance_fis_1,", 
					"avg(case when mff.ejercicio = YEAR(curdate()) then (t2.p_avance_f) else 0 end) p_avance_fis",
					"from mv_financiera_fisica mff",
					"left join",  
					"(select ejercicio, actividad, avg(t.p_ejecucion_f) p_avance_f from (",
					"select actividad, ejercicio, (sum(ejecucion) / (avg(cantidad) +sum(modificacion))) p_ejecucion_f",
					"from mv_ejecucion_fisica",
					"where entidad=? and unidad_ejecutora=? and programa=? and subprograma=? and proyecto=0",
					"group by actividad, ejercicio) t",
					"group by t.ejercicio, t.actividad) t2 on t2.actividad=mff.actividad",
					"where mff.ejercicio=t2.ejercicio and entidad=? and unidad_ejecutora=? and programa=? and subprograma=? and proyecto=0",
					"group by mff.actividad, mff.actividad_obra_nombre");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidadEjecutora);
				pstmt.setInt(3, programa);
				pstmt.setInt(4, subPrograma);
				pstmt.setInt(5, entidad);
				pstmt.setInt(6, unidadEjecutora);
				pstmt.setInt(7, programa);
				pstmt.setInt(8, subPrograma);
				
				ResultSet rs = CMemsql.runPreparedStatement(pstmt);
				
				while(rs.next()){
					Actividad temp = (new ActividadDAO()).new Actividad();
					temp.id = rs.getInt("actividad");
					temp.descripcionActividad = rs.getString("actividad_obra_nombre");
					temp.asignado4 = rs.getBigDecimal("asignado_4");
					temp.asignado3 = rs.getBigDecimal("asignado_3");
					temp.asignado2 = rs.getBigDecimal("asignado_2");
					temp.asignado1 = rs.getBigDecimal("asignado_1");
					temp.asignado = rs.getBigDecimal("asignado");
					temp.vigente4 = rs.getBigDecimal("vigente_4");
					temp.vigente3 = rs.getBigDecimal("vigente_3");
					temp.vigente2 = rs.getBigDecimal("vigente_2");
					temp.vigente1 = rs.getBigDecimal("vigente_1");
					temp.vigente = rs.getBigDecimal("vigente");
					temp.ejecutado4 = rs.getBigDecimal("ejecutado_4");
					temp.ejecutado3 = rs.getBigDecimal("ejecutado_3");
					temp.ejecutado2 = rs.getBigDecimal("ejecutado_2");
					temp.ejecutado1 = rs.getBigDecimal("ejecutado_1");
					temp.ejecutado = rs.getBigDecimal("ejecutado");
					temp.p_ejecucion_4 = rs.getBigDecimal("p_ejecucion_fin_4") != null ? rs.getBigDecimal("p_ejecucion_fin_4").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.p_ejecucion_3 = rs.getBigDecimal("p_ejecucion_fin_3") != null ? rs.getBigDecimal("p_ejecucion_fin_3").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.p_ejecucion_2 = rs.getBigDecimal("p_ejecucion_fin_2") != null ? rs.getBigDecimal("p_ejecucion_fin_2").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.p_ejecucion_1 = rs.getBigDecimal("p_ejecucion_fin_1") != null ? rs.getBigDecimal("p_ejecucion_fin_1").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.p_ejecucion = rs.getBigDecimal("p_ejecucion_fin") != null ? rs.getBigDecimal("p_ejecucion_fin").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.p_avance_4 = rs.getBigDecimal("p_avance_fis_4") != null ? rs.getBigDecimal("p_avance_fis_4").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.p_avance_3 = rs.getBigDecimal("p_avance_fis_3") != null ? rs.getBigDecimal("p_avance_fis_3").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.p_avance_2 = rs.getBigDecimal("p_avance_fis_2") != null ? rs.getBigDecimal("p_avance_fis_2").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.p_avance_1 = rs.getBigDecimal("p_avance_fis_1") != null ? rs.getBigDecimal("p_avance_fis_1").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.p_avance = rs.getBigDecimal("p_avance_fis") != null ? rs.getBigDecimal("p_avance_fis").multiply(new BigDecimal(100)) : new BigDecimal(0);
					ret.add(temp);
				}
			}	
			
			return ret;
		}catch(Exception e){
			CLogger.write("1", ActividadDAO.class, e);
			return null;
		}
	}
	
	public static ArrayList<VectorValoresFisicos> getEjecucionFisicaMensual(Integer entidad, Integer unidadEjecutora, Integer programa, Integer subPrograma, Integer actividad){
		ArrayList<VectorValoresFisicos> ret = new ArrayList<VectorValoresFisicos>();
		String query = "";
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select t1.ejercicio, t1.mes, avg(t1.p_ejecucionf) p_ejecucionf ",
					"from mv_financiera_fisica mff", 
					"left join(", 
					"select mef.ejercicio, mef.mes, mef.actividad, IFNULL(sum(mef.ejecucion) / (NULLIF(IFNULL(avg(mef.cantidad) +sum(mef.modificacion),0),1)),0) p_ejecucionf", 
					"from mv_ejecucion_fisica mef", 
					"where mef.entidad=? and mef.unidad_ejecutora=? and mef.programa=? and mef.subprograma=? and mef.proyecto=0 and mef.mes>0",
					actividad != null ? "and mef.actividad=?" : "", 
					"group by ejercicio, mes, actividad) t1 on mff.actividad=t1.actividad and mff.ejercicio=t1.ejercicio", 
					"where mff.entidad=? and mff.unidad_ejecutora=? and mff.programa=? and mff.subprograma=? and mff.proyecto=0", 
					"group by mff.ejercicio, t1.mes order by mff.ejercicio, t1.mes");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidadEjecutora);
				pstmt.setInt(3, programa);
				pstmt.setInt(4, subPrograma);
				if(actividad != null)
					pstmt.setInt(5, actividad);
				
				pstmt.setInt(actividad != null ? 6 : 5, entidad);
				pstmt.setInt(actividad != null ? 7 : 6, unidadEjecutora);
				pstmt.setInt(actividad != null ? 8: 7, programa);
				pstmt.setInt(actividad != null ? 9: 8, subPrograma);
				
				ResultSet rs = CMemsql.runPreparedStatement(pstmt);
				
				while(rs.next()){
					VectorValoresFisicos temp = (new ActividadDAO()).new VectorValoresFisicos();
					temp.ejercicio = rs.getInt("ejercicio");
					temp.mes = rs.getInt("mes");
					temp.p_ejecucion = rs.getBigDecimal("p_ejecucionf");
					ret.add(temp);
				}
			}
			return ret;
		}catch(Exception e){
			CLogger.write("1", ActividadDAO.class, e);
			return ret;
		}
	} 
	
	public static ArrayList<VectorValoresFinancieros> getEjecucionFinancieraMensual(Integer entidad, Integer unidadEjecutora, Integer programa, Integer subPrograma, Integer actividad){
		ArrayList<VectorValoresFinancieros> ret = new ArrayList<VectorValoresFinancieros>();
		String query = "";
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select mff.ejercicio, ",
					"(mff.financiero_ejecutado_m1 / mff.financiero_vigente_m1) p_ejecucion_fin_1,",
					"(mff.financiero_ejecutado_m2 / mff.financiero_vigente_m2) p_ejecucion_fin_2,",
					"(mff.financiero_ejecutado_m3 / mff.financiero_vigente_m3) p_ejecucion_fin_3,",
					"(mff.financiero_ejecutado_m4 / mff.financiero_vigente_m4) p_ejecucion_fin_4,",
					"(mff.financiero_ejecutado_m5 / mff.financiero_vigente_m5) p_ejecucion_fin_5,",
					"(mff.financiero_ejecutado_m6 / mff.financiero_vigente_m6) p_ejecucion_fin_6,",
					"(mff.financiero_ejecutado_m7 / mff.financiero_vigente_m7) p_ejecucion_fin_7,",
					"(mff.financiero_ejecutado_m8 / mff.financiero_vigente_m8) p_ejecucion_fin_8,",
					"(mff.financiero_ejecutado_m9 / mff.financiero_vigente_m9) p_ejecucion_fin_9,",
					"(mff.financiero_ejecutado_m10 / mff.financiero_vigente_m10) p_ejecucion_fin_10,",
					"(mff.financiero_ejecutado_m11 / mff.financiero_vigente_m11) p_ejecucion_fin_11,",
					"(mff.financiero_ejecutado_m12 / mff.financiero_vigente_m12) p_ejecucion_fin_12",
					"from mv_financiera_fisica mff",
					"where mff.entidad=? and mff.unidad_ejecutora=? and mff.programa=? and mff.subprograma=? and mff.proyecto=0",
					actividad != null ? "and mff.actividad=?" : "",
					"group by mff.ejercicio",
					"order by mff.ejercicio");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidadEjecutora);
				pstmt.setInt(3, programa);
				pstmt.setInt(4, subPrograma);
				if(actividad != null)
					pstmt.setInt(5, actividad);
				
				ResultSet rs = CMemsql.runPreparedStatement(pstmt);
				
				while(rs.next()){
					VectorValoresFinancieros temp = (new ActividadDAO()).new VectorValoresFinancieros();
					temp.ejercicio = rs.getInt("ejercicio");
					temp.mes[0] = rs.getBigDecimal("p_ejecucion_fin_1");
					temp.mes[1] = rs.getBigDecimal("p_ejecucion_fin_2");
					temp.mes[2] = rs.getBigDecimal("p_ejecucion_fin_3");
					temp.mes[3] = rs.getBigDecimal("p_ejecucion_fin_4");
					temp.mes[4] = rs.getBigDecimal("p_ejecucion_fin_5");
					temp.mes[5] = rs.getBigDecimal("p_ejecucion_fin_6");
					temp.mes[6] = rs.getBigDecimal("p_ejecucion_fin_7");
					temp.mes[7] = rs.getBigDecimal("p_ejecucion_fin_8");
					temp.mes[8] = rs.getBigDecimal("p_ejecucion_fin_9");
					temp.mes[9] = rs.getBigDecimal("p_ejecucion_fin_10");
					temp.mes[10] = rs.getBigDecimal("p_ejecucion_fin_11");
					temp.mes[11] = rs.getBigDecimal("p_ejecucion_fin_12");
					ret.add(temp);
				}
			}
			
			return ret;
		}
		catch(Exception e){
			CLogger.write("1", ActividadDAO.class, e);
			return ret;
		}
	}
}