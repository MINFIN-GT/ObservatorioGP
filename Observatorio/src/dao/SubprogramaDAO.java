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
	
//	public static ArrayList<> getEjecucionFisicaFinancieraPonderadaMensual(Integer entidad, Integer unidadEjecutora, Integer programa, Integer subPrograma){
//		try{
//			
//		}catch(Exception e){
//			
//		}
//	}
}
