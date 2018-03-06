package dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.joda.time.DateTime;

import utilities.CLogger;
import utilities.CMemsql;

public class ActividadDAO {
	public class Actividad{
		Integer id;
		String nombre_actividad;
		ArrayList<Integer> ejercicios;
		ArrayList<Double[]> ejercicio_data;
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
	
	public static ArrayList<Actividad> getActividades(Integer entidad, Integer unidadEjecutora, Integer programa, Integer subPrograma, String tipo_resultaldo){
		ArrayList<Actividad> ret = new ArrayList<Actividad>();
		String query = "";
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select * ", 
						"from mv_financiera_fisica", 
						"where entidad=? and unidad_ejecutora=? and programa=? and subprograma=? and proyecto=0 and tipo_resultado=?", 
						"order by entidad, unidad_ejecutora, programa, subprograma, proyecto, actividad, obra, ejercicio");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				pstmt.setInt(1, entidad);
				pstmt.setInt(2, unidadEjecutora);
				pstmt.setInt(3, programa);
				pstmt.setInt(4, subPrograma);
				pstmt.setString(5, tipo_resultaldo);
				
				ResultSet rs = CMemsql.runPreparedStatement(pstmt);
				
				int actividad_actual = -1;
				Actividad temp = null;
				while(rs.next()){
					if(actividad_actual != rs.getInt("actividad")){
						if(temp != null)
							ret.add(temp);
						temp = (new ActividadDAO()).new Actividad();
						temp.id = rs.getInt("actividad");
						temp.nombre_actividad = rs.getString("actividad_obra_nombre");
						int year = DateTime.now().getYear();
						temp.ejercicios = new ArrayList<Integer>();
						temp.ejercicio_data = new ArrayList<Double[]>();
						for(int i=0;i<5;i++){
							temp.ejercicios.add(i+(year-4));
							temp.ejercicio_data.add(new Double[50]);
							for(int z=0;z<50;z++)
								temp.ejercicio_data.get(temp.ejercicio_data.size()-1)[z]=0.0d;
						}
						actividad_actual = temp.id;						
					}
					
					Double[] datos = new Double[50];
					for(int i=0; i<50;i++){
						datos[i] = rs.getDouble(i+15);
					}
					temp.ejercicio_data.set(rs.getInt("ejercicio")-DateTime.now().getYear()+4,datos);
					temp.nombre_actividad = rs.getString("actividad_obra_nombre");
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