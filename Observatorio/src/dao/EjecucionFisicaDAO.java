package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import utilities.CMemsql;

public class EjecucionFisicaDAO {
	
	public static ResultSet getEjecucionFisica(Integer entidad, Integer unidad_ejecutora, Integer programa, Integer sub_programa, Integer actividad, Integer obra){
		String query = "";
		ResultSet result = null;
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "SELECT mef.codigo_meta, mef.entidad, mef.unidad_ejecutora, mef.programa, mef.subprograma, mef.actividad, mef.obra, mef.ejercicio,",
						"sum(case when mef.ejercicio = YEAR(CURDATE()) -4 then ifnull(ejecucion,0) else 0 end) /", 
						"IFNULL(NULLIF(sum(case when mef.ejercicio = YEAR(CURDATE()) -4 then ifnull(mef.cantidad,0) else 0 end) + sum(case when mef.ejercicio = YEAR(CURDATE()) -4 then ifnull(mef.modificacion,0) else 0 end),0),1) p_ejecucion_4,",
						"sum(case when mef.ejercicio = YEAR(CURDATE()) -3 then ifnull(mef.ejecucion,0) else 0 end) /", 
						"IFNULL(NULLIF(sum(case when mef.ejercicio = YEAR(CURDATE()) -3 then ifnull(mef.cantidad,0) else 0 end) + sum(case when mef.ejercicio = YEAR(CURDATE()) -3 then ifnull(mef.modificacion,0) else 0 end),0),1) p_ejecucion_3,",
						"sum(case when mef.ejercicio = YEAR(CURDATE()) -2 then ifnull(mef.ejecucion,0) else 0 end) /", 
						"IFNULL(NULLIF(sum(case when mef.ejercicio = YEAR(CURDATE()) -2 then ifnull(mef.cantidad,0) else 0 end) + sum(case when mef.ejercicio = YEAR(CURDATE()) -2 then ifnull(mef.modificacion,0) else 0 end),0),1) p_ejecucion_2,",
						"sum(case when mef.ejercicio = YEAR(CURDATE()) -1 then ifnull(mef.ejecucion,0) else 0 end) /", 
						"IFNULL(NULLIF(sum(case when mef.ejercicio = YEAR(CURDATE()) -1 then ifnull(mef.cantidad,0) else 0 end) + sum(case when mef.ejercicio = YEAR(CURDATE()) -1 then ifnull(mef.modificacion,0) else 0 end),0),1) p_ejecucion_1,",
						"sum(case when mef.ejercicio = YEAR(CURDATE()) then ifnull(mef.ejecucion,0) else 0 end) /", 
						"IFNULL(NULLIF(sum(case when mef.ejercicio = YEAR(CURDATE()) then ifnull(mef.cantidad,0) else 0 end) + sum(case when mef.ejercicio = YEAR(CURDATE()) then ifnull(mef.modificacion,0) else 0 end),0),1) p_ejecucion",
						"FROM mv_ejecucion_fisica mef", 
						"WHERE mef.proyecto=0 and mef.entidad=?  and mef.unidad_ejecutora=? and ",
						"mef.programa=?  and mef.subprograma=?  and mef.actividad=? and mef.obra=?", 
						"GROUP BY mef.codigo_meta, mef.meta_descripcion");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidad_ejecutora);
				pstmt.setInt(3, programa);
				pstmt.setInt(4, sub_programa);
				pstmt.setInt(5, actividad);
				pstmt.setInt(6, obra);
				
				result = CMemsql.runPreparedStatement(pstmt);
			}
			return result;
		}catch(Exception e){
			return result;
		}
	}
	
	public static String getDescripcionProducto(Integer entidad, Integer unidad_ejecutora, Integer programa, Integer sub_programa, Integer actividad, Integer obra, Integer ejercicio, Integer codigo_meta){
		String query = "";
		String result = "";
		
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select descripcion", 
						"from sf_meta",
						"where entidad=? and unidad_ejecutora=? and programa=?",  
						"and subprograma=? and proyecto=0 and actividad=? and obra=? and ejercicio=?",
						"and codigo_meta=?");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidad_ejecutora);
				pstmt.setInt(3, programa);
				pstmt.setInt(4, sub_programa);
				pstmt.setInt(5, actividad);
				pstmt.setInt(6, obra);
				pstmt.setInt(7, ejercicio);
				pstmt.setInt(8, codigo_meta);
				ResultSet ret = CMemsql.runPreparedStatement(pstmt);
				
				if(ret.next()){
					result = ret.getString("descripcion");
				}
			}
			
			return result;
		}catch(Exception e){
			return null;
		}
	}
	
	public static ResultSet getEjecucionFisicaMensual(Integer entidad, Integer unidad_ejecutora, Integer programa, Integer sub_programa, Integer actividad, Integer obra, Integer codigo_meta){
		ResultSet result = null;
		String query = "";
		
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "SELECT mef.ejercicio, mef.mes,",
						"sum(IFNULL(mef.ejecucion,0)) / IFNULL(NULLIF(sum(ifnull(mef.cantidad,0)) + sum(ifnull(mef.modificacion,0)),0),1) p_ejecucion",
						"FROM mv_ejecucion_fisica mef",
						"WHERE mef.proyecto=0 and mef.entidad=? and mef.unidad_ejecutora=? and", 
						"mef.programa=? and mef.subprograma=? and mef.actividad=? and mef.obra=?",
						codigo_meta != null ? "and mef.codigo_meta=?" : "", 
						"GROUP BY mef.ejercicio, mef.mes");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidad_ejecutora);
				pstmt.setInt(3, programa);
				pstmt.setInt(4, sub_programa);
				pstmt.setInt(5, actividad);
				pstmt.setInt(6, obra);
				if(codigo_meta != null)
					pstmt.setInt(7, codigo_meta);
				
				result = CMemsql.runPreparedStatement(pstmt);
			}
			
			return result;
		}catch(Exception e){
			return null;
		}
	}
	
	public static ResultSet getVectorValores(Integer entidad, Integer unidad_ejecutora, Integer programa, Integer sub_programa, Integer actividad, Integer obra, Integer codigo_meta){
		ResultSet result = null;
		String query = "";
		
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "SELECT mef.ejercicio, mef.mes, sum(cantidad) cantidad,",
					"sum(ejecucion) ejecucion, sum(modificacion) modificacion",
					"FROM mv_ejecucion_fisica mef",
					"WHERE mef.proyecto=0 and mef.entidad=? and mef.unidad_ejecutora=? and", 
					"mef.programa=? and mef.subprograma=? and mef.actividad=? and mef.obra=?",
					codigo_meta != null ? "and mef.codigo_meta=?" : "", 
					"GROUP BY mef.ejercicio, mef.mes");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidad_ejecutora);
				pstmt.setInt(3, programa);
				pstmt.setInt(4, sub_programa);
				pstmt.setInt(5, actividad);
				pstmt.setInt(6, obra);
				if(codigo_meta != null)
					pstmt.setInt(7, codigo_meta);
				
				result = CMemsql.runPreparedStatement(pstmt);
			}
			
			return result;
		}catch(Exception e){
			return null;
		}
	}
}
