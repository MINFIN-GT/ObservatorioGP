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
				query = String.join(" ", "select ejercicio, entidad, unidad_ejecutora, programa, subprograma, proyecto, obra, actividad, actividad_obra_nombre,", 
						"avg(financiero_asignado) financiero_asignado,", 
						"avg(financiero_ejecutado_m1) financiero_ejecutado_m1,", 
						"avg(financiero_ejecutado_m2) financiero_ejecutado_m2,", 
						"avg(financiero_ejecutado_m3) financiero_ejecutado_m3,", 
						"avg(financiero_ejecutado_m4) financiero_ejecutado_m4,", 
						"avg(financiero_ejecutado_m5) financiero_ejecutado_m5,", 
						"avg(financiero_ejecutado_m6) financiero_ejecutado_m6,", 
						"avg(financiero_ejecutado_m7) financiero_ejecutado_m7,", 
						"avg(financiero_ejecutado_m8) financiero_ejecutado_m8,", 
						"avg(financiero_ejecutado_m9) financiero_ejecutado_m9,", 
						"avg(financiero_ejecutado_m10) financiero_ejecutado_m10,", 
						"avg(financiero_ejecutado_m11) financiero_ejecutado_m11,",
						"avg(financiero_ejecutado_m12) financiero_ejecutado_m12,",
						"avg(financiero_vigente_m1) financiero_vigente_m1,", 
						"avg(financiero_vigente_m2) financiero_vigente_m2,", 
						"avg(financiero_vigente_m3) financiero_vigente_m3,", 
						"avg(financiero_vigente_m4) financiero_vigente_m4,", 
						"avg(financiero_vigente_m5) financiero_vigente_m5,",
						"avg(financiero_vigente_m6) financiero_vigente_m6,",
						"avg(financiero_vigente_m7) financiero_vigente_m7,", 
						"avg(financiero_vigente_m8) financiero_vigente_m8,", 
						"avg(financiero_vigente_m9) financiero_vigente_m9,", 
						"avg(financiero_vigente_m10) financiero_vigente_m10,", 
						"avg(financiero_vigente_m11) financiero_vigente_m11,", 
						"avg(financiero_vigente_m12) financiero_vigente_m12,", 
						"avg(financiero_ejecutado_m1)/ifnull(avg(if(financiero_vigente_m1>0,financiero_vigente_m1,1)),1) p_financiero_m1,",
						"avg(financiero_ejecutado_m2)/ifnull(avg(if(financiero_vigente_m2>0,financiero_vigente_m2,1)),1) p_financiero_m2,",
						"avg(financiero_ejecutado_m3)/ifnull(avg(if(financiero_vigente_m3>0,financiero_vigente_m3,1)),1) p_financiero_m3,",
						"avg(financiero_ejecutado_m4)/ifnull(avg(if(financiero_vigente_m4>0,financiero_vigente_m4,1)),1) p_financiero_m4,",
						"avg(financiero_ejecutado_m5)/ifnull(avg(if(financiero_vigente_m5>0,financiero_vigente_m5,1)),1) p_financiero_m5,",
						"avg(financiero_ejecutado_m6)/ifnull(avg(if(financiero_vigente_m6>0,financiero_vigente_m6,1)),1) p_financiero_m6,",
						"avg(financiero_ejecutado_m7)/ifnull(avg(if(financiero_vigente_m7>0,financiero_vigente_m7,1)),1) p_financiero_m7,",
						"avg(financiero_ejecutado_m8)/ifnull(avg(if(financiero_vigente_m8>0,financiero_vigente_m8,1)),1) p_financiero_m8,",
						"avg(financiero_ejecutado_m9)/ifnull(avg(if(financiero_vigente_m9>0,financiero_vigente_m9,1)),1) p_financiero_m9,",
						"avg(financiero_ejecutado_m10)/ifnull(avg(if(financiero_vigente_m10>0,financiero_vigente_m10,1)),1) p_financiero_m10,",
						"avg(financiero_ejecutado_m11)/ifnull(avg(if(financiero_vigente_m11>0,financiero_vigente_m11,1)),1) p_financiero_m11,",
						"avg(financiero_ejecutado_m12)/ifnull(avg(if(financiero_vigente_m12>0,financiero_vigente_m12,1)),1) p_financiero_m12,",
						"avg(fisico_ejecutado_m1/ifnull(if(fisico_asignado+fisico_modificacion_m1>0,fisico_asignado+fisico_modificacion_m1,1),1)) p_fisico_m1,",
						"avg(fisico_ejecutado_m2/ifnull(if(fisico_asignado+fisico_modificacion_m2>0,fisico_asignado+fisico_modificacion_m2,1),1)) p_fisico_m2,",
						"avg(fisico_ejecutado_m3/ifnull(if(fisico_asignado+fisico_modificacion_m3>0,fisico_asignado+fisico_modificacion_m3,1),1)) p_fisico_m3,",
						"avg(fisico_ejecutado_m4/ifnull(if(fisico_asignado+fisico_modificacion_m4>0,fisico_asignado+fisico_modificacion_m4,1),1)) p_fisico_m4,",
						"avg(fisico_ejecutado_m5/ifnull(if(fisico_asignado+fisico_modificacion_m5>0,fisico_asignado+fisico_modificacion_m5,1),1)) p_fisico_m5,",
						"avg(fisico_ejecutado_m6/ifnull(if(fisico_asignado+fisico_modificacion_m6>0,fisico_asignado+fisico_modificacion_m6,1),1)) p_fisico_m6,",
						"avg(fisico_ejecutado_m7/ifnull(if(fisico_asignado+fisico_modificacion_m7>0,fisico_asignado+fisico_modificacion_m7,1),1)) p_fisico_m7,",
						"avg(fisico_ejecutado_m8/ifnull(if(fisico_asignado+fisico_modificacion_m8>0,fisico_asignado+fisico_modificacion_m8,1),1)) p_fisico_m8,",
						"avg(fisico_ejecutado_m9/ifnull(if(fisico_asignado+fisico_modificacion_m9>0,fisico_asignado+fisico_modificacion_m9,1),1)) p_fisico_m9,",
						"avg(fisico_ejecutado_m10/ifnull(if(fisico_asignado+fisico_modificacion_m10>0,fisico_asignado+fisico_modificacion_m10,1),1)) p_fisico_m10,",
						"avg(fisico_ejecutado_m11/ifnull(if(fisico_asignado+fisico_modificacion_m11>0,fisico_asignado+fisico_modificacion_m11,1),1)) p_fisico_m11,",
						"avg(fisico_ejecutado_m12/ifnull(if(fisico_asignado+fisico_modificacion_m12>0,fisico_asignado+fisico_modificacion_m12,1),1)) p_fisico_m12", 
						"from mv_financiera_fisica ", 
						"where entidad=? and unidad_ejecutora=? and programa=? and subprograma=? and proyecto=0 and tipo_resultado=?", 
						"group by entidad, unidad_ejecutora, programa, subprograma, proyecto, actividad, ejercicio", 
						"order by entidad, unidad_ejecutora, programa, subprograma, proyecto, actividad, ejercicio;");
				
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
							temp.ejercicio_data.add(new Double[49]);
							for(int z=0;z<49;z++)
								temp.ejercicio_data.get(temp.ejercicio_data.size()-1)[z]=0.0d;
						}
						actividad_actual = temp.id;						
					}
					
					Double[] datos = new Double[49];
					for(int i=0; i<49;i++){
						datos[i] = rs.getDouble(i+10);
					}
					temp.ejercicio_data.set(rs.getInt("ejercicio")-DateTime.now().getYear()+4,datos);
					temp.nombre_actividad = rs.getString("actividad_obra_nombre");
				}
				if(temp != null)
					ret.add(temp);
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