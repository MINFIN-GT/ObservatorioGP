package dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

import utilities.CLogger;
import utilities.CMemsql;

public class ProductoDAO {
	
	public class EjecucionFisica{
		Integer codigo_meta;
		String metaDescripcion;
		BigDecimal p_ejecucion_4;
		BigDecimal p_ejecucion_3;
		BigDecimal p_ejecucion_2;
		BigDecimal p_ejecucion_1;
		BigDecimal p_ejecucion;
	}
	
	public class VectorValores{
		Integer ejercicio;
		Integer mes;
		BigDecimal ejecucion;
		BigDecimal modificacion;
		BigDecimal cantidad;
	}
	
	public static ArrayList<EjecucionFisica> getEjecucionFisica(Integer entidad, Integer unidadEjecutora, Integer programa, Integer subPrograma, Integer actividad){
		String query = "";
		ArrayList<EjecucionFisica> ret = new ArrayList<EjecucionFisica>();
		int anio = Calendar.getInstance().get(Calendar.YEAR);
		
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "SELECT mef.codigo_meta,",
					"sum(case when mef.ejercicio = YEAR(CURDATE()) -4 then ifnull(ejecucion,0) else 0 end) / (IFNULL(NULLIF(avg(case when mef.ejercicio = YEAR(CURDATE()) -4 then mef.cantidad else null end) + sum(case when mef.ejercicio = YEAR(CURDATE()) -4 then ifnull(mef.modificacion,0) else 0 end),0),1)) p_ejecucion_4,",
					"sum(case when mef.ejercicio = YEAR(CURDATE()) -3 then ifnull(mef.ejecucion,0) else 0 end) / (IFNULL(NULLIF(avg(case when mef.ejercicio = YEAR(CURDATE()) -3 then mef.cantidad else null end) + sum(case when mef.ejercicio = YEAR(CURDATE()) -3 then ifnull(mef.modificacion,0) else 0 end),0),1)) p_ejecucion_3,",
					"sum(case when mef.ejercicio = YEAR(CURDATE()) -2 then ifnull(mef.ejecucion,0) else 0 end) / (IFNULL(NULLIF(avg(case when mef.ejercicio = YEAR(CURDATE()) -2 then mef.cantidad else null end) + sum(case when mef.ejercicio = YEAR(CURDATE()) -2 then ifnull(mef.modificacion,0) else 0 end),0),1)) p_ejecucion_2,",
					"sum(case when mef.ejercicio = YEAR(CURDATE()) -1 then ifnull(mef.ejecucion,0) else 0 end) / (IFNULL(NULLIF(avg(case when mef.ejercicio = YEAR(CURDATE()) -1 then mef.cantidad else null end) + sum(case when mef.ejercicio = YEAR(CURDATE()) -1 then ifnull(mef.modificacion,0) else 0 end),0),1)) p_ejecucion_1,",
					"sum(case when mef.ejercicio = YEAR(CURDATE()) then ifnull(mef.ejecucion,0) else 0 end) / (IFNULL(NULLIF(avg(case when mef.ejercicio = YEAR(CURDATE()) then mef.cantidad else null end) + sum(case when mef.ejercicio = YEAR(CURDATE()) then ifnull(mef.modificacion,0) else 0 end),0),1)) p_ejecucion",
					"FROM mv_ejecucion_fisica mef", 
					"WHERE mef.proyecto=0 and mef.entidad=? and mef.unidad_ejecutora=? and", 
					"mef.programa=? and mef.subprograma=? and mef.actividad=? and mef.obra=0", 
					"GROUP BY mef.codigo_meta");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidadEjecutora);
				pstmt.setInt(3, programa);
				pstmt.setInt(4, subPrograma);
				pstmt.setInt(5, actividad);
				
				ResultSet rs = CMemsql.runPreparedStatement(pstmt);
				
				while(rs.next()){
					EjecucionFisica temp = (new ProductoDAO()).new EjecucionFisica();
					temp.codigo_meta = rs.getInt("codigo_meta");
					temp.p_ejecucion_4 = rs.getBigDecimal("p_ejecucion_4").multiply(new BigDecimal(100));
					temp.p_ejecucion_3 = rs.getBigDecimal("p_ejecucion_3").multiply(new BigDecimal(100));
					temp.p_ejecucion_2 = rs.getBigDecimal("p_ejecucion_2").multiply(new BigDecimal(100));
					temp.p_ejecucion_1 = rs.getBigDecimal("p_ejecucion_1").multiply(new BigDecimal(100));
					temp.p_ejecucion = rs.getBigDecimal("p_ejecucion").multiply(new BigDecimal(100));
					temp.metaDescripcion = getDescripcionProducto(entidad, unidadEjecutora, programa, subPrograma, actividad, anio, temp.codigo_meta);
					ret.add(temp);
				}
				
			}
			return ret;
		}catch(Exception e){
			CLogger.write("1", ProductoDAO.class, e);
			return ret;
		}
	}
	
	public static String getDescripcionProducto(Integer entidad, Integer unidad_ejecutora, Integer programa, Integer sub_programa, Integer actividad, Integer ejercicio, Integer codigo_meta){
		String query = "";
		String result = "";
		
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select descripcion", 
						"from sf_meta",
						"where entidad=? and unidad_ejecutora=? and programa=?",  
						"and subprograma=? and proyecto=0 and actividad=? and obra=0 and ejercicio=?",
						"and codigo_meta=?");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidad_ejecutora);
				pstmt.setInt(3, programa);
				pstmt.setInt(4, sub_programa);
				pstmt.setInt(5, actividad);
				pstmt.setInt(6, ejercicio);
				pstmt.setInt(7, codigo_meta);
				ResultSet ret = CMemsql.runPreparedStatement(pstmt);
				
				if(ret.next()){
					result = ret.getString("descripcion");
				}
			}
			
			return result;
		}catch(Exception e){
			CLogger.write("2", ProductoDAO.class, e);
			return null;
		}
	}
	
	public static ArrayList<VectorValores> getVectorValores(Integer entidad, Integer unidad_ejecutora, Integer programa, Integer sub_programa, Integer actividad, Integer codigo_meta){
		ArrayList<VectorValores> ret = new ArrayList<VectorValores>();
		String query = "";
		
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "SELECT mef.ejercicio, mef.mes, sum(cantidad) cantidad,",
					"sum(ejecucion) ejecucion, sum(modificacion) modificacion",
					"FROM mv_ejecucion_fisica mef",
					"WHERE mef.proyecto=0 and mef.entidad=? and mef.unidad_ejecutora=? and", 
					"mef.programa=? and mef.subprograma=? and mef.actividad=? and mef.obra=0",
					codigo_meta != null ? "and mef.codigo_meta=?" : "", 
					"GROUP BY mef.ejercicio, mef.mes");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidad_ejecutora);
				pstmt.setInt(3, programa);
				pstmt.setInt(4, sub_programa);
				pstmt.setInt(5, actividad);
				if(codigo_meta != null)
					pstmt.setInt(6, codigo_meta);
				
				ResultSet rs = CMemsql.runPreparedStatement(pstmt);
				while(rs.next()){
					VectorValores temp = (new ProductoDAO()).new VectorValores();
					temp.ejercicio = rs.getInt("ejercicio");
					temp.mes = rs.getInt("mes");
					temp.ejecucion = rs.getBigDecimal("ejecucion");
					temp.modificacion = rs.getBigDecimal("modificacion");
					temp.cantidad = rs.getBigDecimal("cantidad");
					ret.add(temp);
				}
			}
			
			return ret;
		}catch(Exception e){
			CLogger.write("3", ProductoDAO.class, e);
			return ret;
		}
	}
}
