package dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import utilities.CLogger;
import utilities.CMemsql;

public class SubprogramaDAO {
	public class Subprograma{
		Integer subprograma;
		String subprogramaNombre;
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
		BigDecimal ep4;
		BigDecimal ep3;
		BigDecimal ep2;
		BigDecimal ep1;
		BigDecimal ep;
		BigDecimal ef4;
		BigDecimal ef3;
		BigDecimal ef2;
		BigDecimal ef1;
		BigDecimal ef;
	}
	
	public class AnioEjecucion{
		Integer ejercicio;
		Mensualidad mensualidad;
	}
	
	class Mensualidad{
		BigDecimal asignado;
		BigDecimal[] vigente = new BigDecimal[12];
		BigDecimal[] ejecutado = new BigDecimal[12];
		BigDecimal[] p_ejecutado_fin = new BigDecimal[12];
		BigDecimal[] p_avance_fis = new BigDecimal[12];
	}
	
	public static ArrayList<Subprograma> getSubProgramas(Integer entidad, Integer unidadEjecutora, Integer programa){
		ArrayList<Subprograma> ret = new ArrayList<Subprograma>();
		String query = "";
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select subprograma, subprograma_nombre,",
					"sum(case when ejercicio = YEAR(curdate()) -4 then asignado else 0 end) asignado_4,",
					"sum(case when ejercicio = YEAR(curdate()) -3 then asignado else 0 end) asignado_3,",
					"sum(case when ejercicio = YEAR(curdate()) -2 then asignado else 0 end) asignado_2,",
					"sum(case when ejercicio = YEAR(curdate()) -1 then asignado else 0 end) asignado_1,",
					"sum(case when ejercicio = YEAR(curdate()) then asignado else 0 end) asignado,",
					"sum(case when ejercicio = YEAR(curdate()) -4 then (vigente_m1 + vigente_m2 + vigente_m3 + vigente_m4 + vigente_m5 + vigente_m6 + vigente_m7 + vigente_m8 + vigente_m9 + vigente_m10 + vigente_m11 + vigente_m12) else 0 end) vigente_4,",
					"sum(case when ejercicio = YEAR(curdate()) -3 then (vigente_m1 + vigente_m2 + vigente_m3 + vigente_m4 + vigente_m5 + vigente_m6 + vigente_m7 + vigente_m8 + vigente_m9 + vigente_m10 + vigente_m11 + vigente_m12) else 0 end) vigente_3,",
					"sum(case when ejercicio = YEAR(curdate()) -2 then (vigente_m1 + vigente_m2 + vigente_m3 + vigente_m4 + vigente_m5 + vigente_m6 + vigente_m7 + vigente_m8 + vigente_m9 + vigente_m10 + vigente_m11 + vigente_m12) else 0 end) vigente_2,",
					"sum(case when ejercicio = YEAR(curdate()) -1 then (vigente_m1 + vigente_m2 + vigente_m3 + vigente_m4 + vigente_m5 + vigente_m6 + vigente_m7 + vigente_m8 + vigente_m9 + vigente_m10 + vigente_m11 + vigente_m12) else 0 end) vigente_1,",
					"sum(case when ejercicio = YEAR(curdate()) then (vigente_m1 + vigente_m2 + vigente_m3 + vigente_m4 + vigente_m5 + vigente_m6 + vigente_m7 + vigente_m8 + vigente_m9 + vigente_m10 + vigente_m11 + vigente_m12) else 0 end) vigente,",
					"sum(case when ejercicio = YEAR(curdate()) -4 then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end) ejecutado_4,",
					"sum(case when ejercicio = YEAR(curdate()) -3 then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end) ejecutado_3,",
					"sum(case when ejercicio = YEAR(curdate()) -2 then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end) ejecutado_2,",
					"sum(case when ejercicio = YEAR(curdate()) -1 then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end) ejecutado_1,",
					"sum(case when ejercicio = YEAR(curdate()) then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end) ejecutado,",
					"(sum(case when ejercicio = YEAR(curdate()) -4 then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end) /",
					"sum(case when ejercicio = YEAR(curdate()) -4 then (vigente_m1 + vigente_m2 + vigente_m3 + vigente_m4 + vigente_m5 + vigente_m6 + vigente_m7 + vigente_m8 + vigente_m9 + vigente_m10 + vigente_m11 + vigente_m12) else 0 end)) EP_4,",
					"(sum(case when ejercicio = YEAR(curdate()) -3 then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end) /",
					"sum(case when ejercicio = YEAR(curdate()) -3 then (vigente_m1 + vigente_m2 + vigente_m3 + vigente_m4 + vigente_m5 + vigente_m6 + vigente_m7 + vigente_m8 + vigente_m9 + vigente_m10 + vigente_m11 + vigente_m12) else 0 end)) EP_3,",
					"(sum(case when ejercicio = YEAR(curdate()) -2 then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end) /",
					"sum(case when ejercicio = YEAR(curdate()) -2 then (vigente_m1 + vigente_m2 + vigente_m3 + vigente_m4 + vigente_m5 + vigente_m6 + vigente_m7 + vigente_m8 + vigente_m9 + vigente_m10 + vigente_m11 + vigente_m12) else 0 end)) EP_2,",
					"(sum(case when ejercicio = YEAR(curdate()) -1 then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end) /",
					"sum(case when ejercicio = YEAR(curdate()) -1 then (vigente_m1 + vigente_m2 + vigente_m3 + vigente_m4 + vigente_m5 + vigente_m6 + vigente_m7 + vigente_m8 + vigente_m9 + vigente_m10 + vigente_m11 + vigente_m12) else 0 end)) EP_1,",
					"(sum(case when ejercicio = YEAR(curdate()) then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end) /",
					"sum(case when ejercicio = YEAR(curdate()) then (vigente_m1 + vigente_m2 + vigente_m3 + vigente_m4 + vigente_m5 + vigente_m6 + vigente_m7 + vigente_m8 + vigente_m9 + vigente_m10 + vigente_m11 + vigente_m12) else 0 end)) EP,",
					"(sum(case when ejercicio = YEAR(curdate()) -4 then ((ejecutado_m1 * p_avance_fisico_m1) + (ejecutado_m2 * p_avance_fisico_m2) + (ejecutado_m3 * p_avance_fisico_m3) + (ejecutado_m4 * p_avance_fisico_m4) + (ejecutado_m5 * p_avance_fisico_m6) + (ejecutado_m2 * p_avance_fisico_m6) + (ejecutado_m7 * p_avance_fisico_m7) + (ejecutado_m8 * p_avance_fisico_m8) + (ejecutado_m9 * p_avance_fisico_m9) + (ejecutado_m10 * p_avance_fisico_m10) + (ejecutado_m11 * p_avance_fisico_m11) + (ejecutado_m2 * p_avance_fisico_m12)) else 0 end)/",
					"sum(case when ejercicio = YEAR(curdate()) -4 then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end)) PF_4,",
					"(sum(case when ejercicio = YEAR(curdate()) -3 then ((ejecutado_m1 * p_avance_fisico_m1) + (ejecutado_m2 * p_avance_fisico_m2) + (ejecutado_m3 * p_avance_fisico_m3) + (ejecutado_m4 * p_avance_fisico_m4) + (ejecutado_m5 * p_avance_fisico_m6) + (ejecutado_m2 * p_avance_fisico_m6) + (ejecutado_m7 * p_avance_fisico_m7) + (ejecutado_m8 * p_avance_fisico_m8) + (ejecutado_m9 * p_avance_fisico_m9) + (ejecutado_m10 * p_avance_fisico_m10) + (ejecutado_m11 * p_avance_fisico_m11) + (ejecutado_m2 * p_avance_fisico_m12)) else 0 end)/",
					"sum(case when ejercicio = YEAR(curdate()) -3 then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end)) PF_3,",
					"(sum(case when ejercicio = YEAR(curdate()) -2 then ((ejecutado_m1 * p_avance_fisico_m1) + (ejecutado_m2 * p_avance_fisico_m2) + (ejecutado_m3 * p_avance_fisico_m3) + (ejecutado_m4 * p_avance_fisico_m4) + (ejecutado_m5 * p_avance_fisico_m6) + (ejecutado_m2 * p_avance_fisico_m6) + (ejecutado_m7 * p_avance_fisico_m7) + (ejecutado_m8 * p_avance_fisico_m8) + (ejecutado_m9 * p_avance_fisico_m9) + (ejecutado_m10 * p_avance_fisico_m10) + (ejecutado_m11 * p_avance_fisico_m11) + (ejecutado_m2 * p_avance_fisico_m12)) else 0 end)/",
					"sum(case when ejercicio = YEAR(curdate()) -2 then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end)) PF_2,",
					"(sum(case when ejercicio = YEAR(curdate()) -1 then ((ejecutado_m1 * p_avance_fisico_m1) + (ejecutado_m2 * p_avance_fisico_m2) + (ejecutado_m3 * p_avance_fisico_m3) + (ejecutado_m4 * p_avance_fisico_m4) + (ejecutado_m5 * p_avance_fisico_m6) + (ejecutado_m2 * p_avance_fisico_m6) + (ejecutado_m7 * p_avance_fisico_m7) + (ejecutado_m8 * p_avance_fisico_m8) + (ejecutado_m9 * p_avance_fisico_m9) + (ejecutado_m10 * p_avance_fisico_m10) + (ejecutado_m11 * p_avance_fisico_m11) + (ejecutado_m2 * p_avance_fisico_m12)) else 0 end)/",
					"sum(case when ejercicio = YEAR(curdate()) -1 then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end)) PF_1,",
					"(sum(case when ejercicio = YEAR(curdate()) then ((ejecutado_m1 * p_avance_fisico_m1) + (ejecutado_m2 * p_avance_fisico_m2) + (ejecutado_m3 * p_avance_fisico_m3) + (ejecutado_m4 * p_avance_fisico_m4) + (ejecutado_m5 * p_avance_fisico_m6) + (ejecutado_m2 * p_avance_fisico_m6) + (ejecutado_m7 * p_avance_fisico_m7) + (ejecutado_m8 * p_avance_fisico_m8) + (ejecutado_m9 * p_avance_fisico_m9) + (ejecutado_m10 * p_avance_fisico_m10) + (ejecutado_m11 * p_avance_fisico_m11) + (ejecutado_m2 * p_avance_fisico_m12)) else 0 end)/",
					"sum(case when ejercicio = YEAR(curdate()) then (ejecutado_m1 + ejecutado_m2 + ejecutado_m3 + ejecutado_m4 + ejecutado_m5 + ejecutado_m6 + ejecutado_m7 + ejecutado_m8 + ejecutado_m9 + ejecutado_m10 + ejecutado_m11 + ejecutado_m12) else 0 end)) PF",
					"from mv_financiera_fisica where entidad=? and unidad_ejecutora=? and programa=? and proyecto=0",
					"group by subprograma, subprograma_nombre");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidadEjecutora);
				pstmt.setInt(3, programa);
				
				ResultSet rs = CMemsql.runPreparedStatement(pstmt);
				
				while(rs.next()){
					Subprograma temp = (new SubprogramaDAO()).new Subprograma();
					temp.subprograma = rs.getInt("subprograma");
					temp.subprogramaNombre = rs.getString("subprograma_nombre");
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
					temp.ep4 = rs.getBigDecimal("EP_4") != null ? rs.getBigDecimal("EP_4").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.ep3 = rs.getBigDecimal("EP_3") != null ? rs.getBigDecimal("EP_3").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.ep2 = rs.getBigDecimal("EP_2") != null ? rs.getBigDecimal("EP_2").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.ep1 = rs.getBigDecimal("EP_1") != null ? rs.getBigDecimal("EP_1").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.ep = rs.getBigDecimal("EP") != null ? rs.getBigDecimal("EP").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.ef4 = rs.getBigDecimal("PF_4") != null ? rs.getBigDecimal("PF_4").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.ef3 = rs.getBigDecimal("PF_3") != null ? rs.getBigDecimal("PF_3").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.ef2 = rs.getBigDecimal("PF_2") != null ? rs.getBigDecimal("PF_2").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.ef1 = rs.getBigDecimal("PF_1") != null ? rs.getBigDecimal("PF_1").multiply(new BigDecimal(100)) : new BigDecimal(0);
					temp.ef = rs.getBigDecimal("PF") != null ? rs.getBigDecimal("PF").multiply(new BigDecimal(100)) : new BigDecimal(0);
					ret.add(temp);
				}
			}
			return ret;
		}catch(Exception e){
			CLogger.write("1", Subprograma.class, e);
			return ret;
		}
	}
	
	public static ArrayList<AnioEjecucion> getEjecucionFisicaFinancieraPonderadaMensual(Integer entidad, Integer unidadEjecutora, Integer programa, Integer subPrograma){
		ArrayList<AnioEjecucion> ret = new ArrayList<AnioEjecucion>();
		String query = "";
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select ejercicio,",
					"(sum(asignado)) asignado,",
					"sum(vigente_m1) vigente_m1, sum(vigente_m2) vigente_m2, sum(vigente_m3) vigente_m3, sum(vigente_m4) vigente_m4, sum(vigente_m5) vigente_m5, sum(vigente_m6) vigente_m6,",
					"sum(vigente_m7) vigente_m7, sum(vigente_m8) vigente_m8, sum(vigente_m9) vigente_m9, sum(vigente_m10) vigente_m10, sum(vigente_m11) vigente_m11, sum(vigente_m12) vigente_m12,",
					"sum(ejecutado_m1) ejecutado_m1, sum(ejecutado_m2) ejecutado_m2, sum(ejecutado_m3) ejecutado_m3, sum(ejecutado_m4) ejecutado_m4, sum(ejecutado_m5) ejecutado_m5, sum(ejecutado_m6) ejecutado_m6,", 
					"sum(ejecutado_m7) ejecutado_m7, sum(ejecutado_m8) ejecutado_m8, sum(ejecutado_m9) ejecutado_m9, sum(ejecutado_m10) ejecutado_m10, sum(ejecutado_m11) ejecutado_m11, sum(ejecutado_m12) ejecutado_m12,",
					"avg(p_ejecucion_financiera_m1) pe1, avg(p_ejecucion_financiera_m2) pe2, avg(p_ejecucion_financiera_m3) pe3, avg(p_ejecucion_financiera_m4) pe4, avg(p_ejecucion_financiera_m5) pe5, avg(p_ejecucion_financiera_m6) pe6,",
					"avg(p_ejecucion_financiera_m7) pe7, avg(p_ejecucion_financiera_m8) pe8, avg(p_ejecucion_financiera_m9) pe9, avg(p_ejecucion_financiera_m10) pe10, avg(p_ejecucion_financiera_m11) pe11, avg(p_ejecucion_financiera_m12) pe12,",
					"avg(p_avance_fisico_m1) pf1, avg(p_avance_fisico_m2) pf2, avg(p_avance_fisico_m3) pf3, avg(p_avance_fisico_m4) pf4, avg(p_avance_fisico_m5) pf5, avg(p_avance_fisico_m6) pf6,", 
					"avg(p_avance_fisico_m7) pf7, avg(p_avance_fisico_m8) pf8, avg(p_avance_fisico_m9) pf9, avg(p_avance_fisico_m10) pf10, avg(p_avance_fisico_m11) pf11, avg(p_avance_fisico_m12) pf12",
					"from mv_financiera_fisica",
					"where entidad=? and unidad_ejecutora=? and programa=?",
					subPrograma != null ? "and subprograma=?" : "",
					"group by ejercicio",
					"order by ejercicio;");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidadEjecutora);
				pstmt.setInt(3, programa);
				if(subPrograma != null)
					pstmt.setInt(4, subPrograma);
				
				ResultSet rs = CMemsql.runPreparedStatement(pstmt);
				
				while(rs.next()){
					AnioEjecucion tempAnio = (new SubprogramaDAO()).new AnioEjecucion();
					Mensualidad tempmes = (new SubprogramaDAO()).new Mensualidad();
					
					tempAnio.ejercicio = rs.getInt("ejercicio");
					tempmes.asignado = rs.getBigDecimal("asignado");
					
					tempmes.vigente[0] = rs.getBigDecimal("vigente_m1"); tempmes.vigente[1] = rs.getBigDecimal("vigente_m2");
					tempmes.vigente[2] = rs.getBigDecimal("vigente_m3"); tempmes.vigente[3] = rs.getBigDecimal("vigente_m4");
					tempmes.vigente[4] = rs.getBigDecimal("vigente_m5"); tempmes.vigente[5] = rs.getBigDecimal("vigente_m6");
					tempmes.vigente[6] = rs.getBigDecimal("vigente_m7"); tempmes.vigente[7] = rs.getBigDecimal("vigente_m8");
					tempmes.vigente[8] = rs.getBigDecimal("vigente_m9"); tempmes.vigente[9] = rs.getBigDecimal("vigente_m10");
					tempmes.vigente[10] = rs.getBigDecimal("vigente_m11"); tempmes.vigente[11] = rs.getBigDecimal("vigente_m12");
					
					tempmes.ejecutado[0] = rs.getBigDecimal("ejecutado_m1"); tempmes.ejecutado[1] = rs.getBigDecimal("ejecutado_m2");
					tempmes.ejecutado[2] = rs.getBigDecimal("ejecutado_m3"); tempmes.ejecutado[3] = rs.getBigDecimal("ejecutado_m4");
					tempmes.ejecutado[4] = rs.getBigDecimal("ejecutado_m5"); tempmes.ejecutado[5] = rs.getBigDecimal("ejecutado_m6");
					tempmes.ejecutado[6] = rs.getBigDecimal("ejecutado_m7"); tempmes.ejecutado[7] = rs.getBigDecimal("ejecutado_m8");
					tempmes.ejecutado[8] = rs.getBigDecimal("ejecutado_m9"); tempmes.ejecutado[9] = rs.getBigDecimal("ejecutado_m10");
					tempmes.ejecutado[10] = rs.getBigDecimal("ejecutado_m11"); tempmes.ejecutado[11] = rs.getBigDecimal("ejecutado_m12");
					
					tempmes.p_ejecutado_fin[0] = rs.getBigDecimal("pe1"); tempmes.p_ejecutado_fin[1] = rs.getBigDecimal("pe2");
					tempmes.p_ejecutado_fin[2] = rs.getBigDecimal("pe3"); tempmes.p_ejecutado_fin[3] = rs.getBigDecimal("pe4");
					tempmes.p_ejecutado_fin[4] = rs.getBigDecimal("pe5"); tempmes.p_ejecutado_fin[5] = rs.getBigDecimal("pe6");
					tempmes.p_ejecutado_fin[6] = rs.getBigDecimal("pe7"); tempmes.p_ejecutado_fin[7] = rs.getBigDecimal("pe8");
					tempmes.p_ejecutado_fin[8] = rs.getBigDecimal("pe9"); tempmes.p_ejecutado_fin[9] = rs.getBigDecimal("pe10");
					tempmes.p_ejecutado_fin[10] = rs.getBigDecimal("pe11"); tempmes.p_ejecutado_fin[11] = rs.getBigDecimal("pe12");
					
					tempmes.p_avance_fis[0] = rs.getBigDecimal("pf1"); tempmes.p_avance_fis[1] = rs.getBigDecimal("pf2");
					tempmes.p_avance_fis[2] = rs.getBigDecimal("pf3"); tempmes.p_avance_fis[3] = rs.getBigDecimal("pf4");
					tempmes.p_avance_fis[4] = rs.getBigDecimal("pf5"); tempmes.p_avance_fis[5] = rs.getBigDecimal("pf6");
					tempmes.p_avance_fis[6] = rs.getBigDecimal("pf7"); tempmes.p_avance_fis[7] = rs.getBigDecimal("pf8");
					tempmes.p_avance_fis[8] = rs.getBigDecimal("pf9"); tempmes.p_avance_fis[9] = rs.getBigDecimal("pf10");
					tempmes.p_avance_fis[10] = rs.getBigDecimal("pf11"); tempmes.p_avance_fis[11] = rs.getBigDecimal("pf12");
					
					tempAnio.mensualidad = tempmes;
					ret.add(tempAnio);
				}
			}
			return ret;
		}catch(Exception e){
			CLogger.write("2", Subprograma.class, e);
			return ret;
		}
	}
}
