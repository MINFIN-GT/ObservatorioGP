package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.joda.time.DateTime;

import utilities.CLogger;
import utilities.CMemsql;

public class ProductoDAO {
	
	public class Producto{
		Integer id;
		Integer id_obra;
		String nombre_producto;
		ArrayList<Integer> ejercicios;
		ArrayList<Double[]> ejercicio_data;
 	}
	
	public static ArrayList<Producto> getEjecucionFisica(Integer entidad, Integer unidad_ejecutora, Integer programa, Integer subprograma, Integer proyecto, Integer actividad, Integer obra, String tipo_resultado){
		String query = "";
		ArrayList<Producto> ret = new ArrayList<Producto>();
		
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "SELECT mff.ejercicio,", 
						"       mff.entidad,", 
						"       mff.programa,", 
						"       mff.subprograma,", 
						"       mff.proyecto,", 
						"       mff.obra,", 
						"       mff.actividad,", 
						"       mff.codigo_meta,", 
						"       m.descripcion,", 
						"       AVG(IFNULL (IF((mff.fisico_asignado + ifnull (mff.fisico_modificacion_m1,0)) > 0,mff.fisico_ejecutado_m1,0),IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m1,0) <> 0,0,NULL)) / IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m1,0) <> 0,mff.fisico_asignado + ifnull (mff.fisico_modificacion_m1,0),1)) p_fisico_m1,", 
						"       AVG(IFNULL (IF((mff.fisico_asignado + ifnull (mff.fisico_modificacion_m2,0)) > 0,mff.fisico_ejecutado_m2,0),IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m2,0) <> 0,0,NULL)) / IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m2,0) <> 0,mff.fisico_asignado + ifnull (mff.fisico_modificacion_m2,0),1)) p_fisico_m2,", 
						"       AVG(IFNULL (IF((mff.fisico_asignado + ifnull (mff.fisico_modificacion_m3,0)) > 0,mff.fisico_ejecutado_m3,0),IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m3,0) <> 0,0,NULL)) / IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m3,0) <> 0,mff.fisico_asignado + ifnull (mff.fisico_modificacion_m3,0),1)) p_fisico_m3,", 
						"       AVG(IFNULL (IF((mff.fisico_asignado + ifnull (mff.fisico_modificacion_m4,0)) > 0,mff.fisico_ejecutado_m4,0),IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m4,0) <> 0,0,NULL)) / IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m4,0) <> 0,mff.fisico_asignado + ifnull (mff.fisico_modificacion_m4,0),1)) p_fisico_m4,", 
						"       AVG(IFNULL (IF((mff.fisico_asignado + ifnull (mff.fisico_modificacion_m5,0)) > 0,mff.fisico_ejecutado_m5,0),IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m5,0) <> 0,0,NULL)) / IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m5,0) <> 0,mff.fisico_asignado + ifnull (mff.fisico_modificacion_m5,0),1)) p_fisico_m5,", 
						"       AVG(IFNULL (IF((mff.fisico_asignado + ifnull (mff.fisico_modificacion_m6,0)) > 0,mff.fisico_ejecutado_m6,0),IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m6,0) <> 0,0,NULL)) / IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m6,0) <> 0,mff.fisico_asignado + ifnull (mff.fisico_modificacion_m6,0),1)) p_fisico_m6,", 
						"       AVG(IFNULL (IF((mff.fisico_asignado + ifnull (mff.fisico_modificacion_m7,0)) > 0,mff.fisico_ejecutado_m7,0),IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m7,0) <> 0,0,NULL)) / IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m7,0) <> 0,mff.fisico_asignado + ifnull (mff.fisico_modificacion_m7,0),1)) p_fisico_m7,", 
						"       AVG(IFNULL (IF((mff.fisico_asignado + ifnull (mff.fisico_modificacion_m8,0)) > 0,mff.fisico_ejecutado_m8,0),IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m8,0) <> 0,0,NULL)) / IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m8,0) <> 0,mff.fisico_asignado + ifnull (mff.fisico_modificacion_m8,0),1)) p_fisico_m8,", 
						"       AVG(IFNULL (IF((mff.fisico_asignado + ifnull (mff.fisico_modificacion_m9,0)) > 0,mff.fisico_ejecutado_m9,0),IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m9,0) <> 0,0,NULL)) / IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m9,0) <> 0,mff.fisico_asignado + ifnull (mff.fisico_modificacion_m9,0),1)) p_fisico_m9,", 
						"       AVG(IFNULL (IF((mff.fisico_asignado + ifnull (mff.fisico_modificacion_m10,0)) > 0,mff.fisico_ejecutado_m10,0),IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m10,0) <> 0,0,NULL)) / IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m10,0) <> 0,mff.fisico_asignado + ifnull (mff.fisico_modificacion_m10,0),1)) p_fisico_m10,", 
						"       AVG(IFNULL (IF((mff.fisico_asignado + ifnull (mff.fisico_modificacion_m11,0)) > 0,mff.fisico_ejecutado_m11,0),IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m11,0) <> 0,0,NULL)) / IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m11,0) <> 0,mff.fisico_asignado + ifnull (mff.fisico_modificacion_m11,0),1)) p_fisico_m11,", 
						"       AVG(IFNULL (IF((mff.fisico_asignado + ifnull (mff.fisico_modificacion_m12,0)) > 0,mff.fisico_ejecutado_m12,0),IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m12,0) <> 0,0,NULL)) / IF (mff.fisico_asignado + ifnull (mff.fisico_modificacion_m12,0) <> 0,mff.fisico_asignado + ifnull (mff.fisico_modificacion_m12,0),1)) p_fisico_m12,",
						"       AVG(mff.fisico_asignado) fisico_asignado,", 
						"       AVG(mff.fisico_ejecutado_m1) fisico_ejecutado_m1,", 
						"       AVG(mff.fisico_ejecutado_m2) fisico_ejecutado_m2,", 
						"       AVG(mff.fisico_ejecutado_m3) fisico_ejecutado_m3,", 
						"       AVG(mff.fisico_ejecutado_m4) fisico_ejecutado_m4,", 
						"       AVG(mff.fisico_ejecutado_m5) fisico_ejecutado_m5,", 
						"       AVG(mff.fisico_ejecutado_m6) fisico_ejecutado_m6,", 
						"       AVG(mff.fisico_ejecutado_m7) fisico_ejecutado_m7,", 
						"       AVG(mff.fisico_ejecutado_m8) fisico_ejecutado_m8,", 
						"       AVG(mff.fisico_ejecutado_m9) fisico_ejecutado_m9,", 
						"       AVG(mff.fisico_ejecutado_m10) fisico_ejecutado_m10,", 
						"       AVG(mff.fisico_ejecutado_m11) fisico_ejecutado_m11,", 
						"       AVG(mff.fisico_ejecutado_m12) fisico_ejecutado_m12,", 
						"       AVG(ifnull (mff.fisico_modificacion_m1,0)) fisico_modificacion_m1,", 
						"       AVG(ifnull (mff.fisico_modificacion_m2,0)) fisico_modificacion_m2,", 
						"       AVG(ifnull (mff.fisico_modificacion_m3,0)) fisico_modificacion_m3,", 
						"       AVG(ifnull (mff.fisico_modificacion_m4,0)) fisico_modificacion_m4,", 
						"       AVG(ifnull (mff.fisico_modificacion_m5,0)) fisico_modificacion_m5,", 
						"       AVG(ifnull (mff.fisico_modificacion_m6,0)) fisico_modificacion_m6,", 
						"       AVG(ifnull (mff.fisico_modificacion_m7,0)) fisico_modificacion_m7,", 
						"       AVG(ifnull (mff.fisico_modificacion_m8,0)) fisico_modificacion_m8,", 
						"       AVG(ifnull (mff.fisico_modificacion_m9,0)) fisico_modificacion_m9,", 
						"       AVG(ifnull (mff.fisico_modificacion_m10,0)) fisico_modificacion_m10,", 
						"       AVG(ifnull (mff.fisico_modificacion_m11,0)) fisico_modificacion_m11,", 
						"       AVG(ifnull (mff.fisico_modificacion_m12,0)) fisico_modificacion_m12",
						"FROM mv_financiera_fisica mff, sf_meta m", 
						"WHERE mff.entidad = ?", 
						"AND   mff.programa = ?", 
						"AND   mff.subprograma = ?",
						"AND   mff.actividad = ?",
						"AND  mff.obra=?",
						"AND m.ejercicio = mff.ejercicio ",
						"AND m.entidad = mff.entidad",
						"AND m.unidad_ejecutora = mff.unidad_ejecutora ",
						"AND m.programa = mff.programa",
						"AND m.subprograma = mff.subprograma",
						"AND m.proyecto = mff.proyecto ",
						"AND m.actividad = mff.actividad",
						"AND m.obra = mff.obra",
						tipo_resultado.length() > 0 ? "AND   mff.tipo_resultado = ?" : "AND  mff.unidad_ejecutora = ? AND mff.proyecto = ?", 
						"AND   m.codigo_meta=mff.codigo_meta", 
						"GROUP BY mff.entidad," + (tipo_resultado.length() == 0 ? "mff.unidad_ejecutora,": "") + "mff.programa, mff.subprograma, mff.proyecto,mff.actividad, mff.obra, mff.codigo_meta,m.descripcion, mff.ejercicio");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				
				int a=1;

				pstmt.setInt(a++, entidad);
				pstmt.setInt(a++, programa);
				pstmt.setInt(a++, subprograma);
				pstmt.setInt(a++, actividad);
				pstmt.setInt(a++, obra);
				
				if(tipo_resultado.length() > 0)
					pstmt.setString(a++, tipo_resultado);
				else{
					pstmt.setInt(a++, unidad_ejecutora);	
					pstmt.setInt(a++, proyecto);
				}
				
				ResultSet rs = pstmt.executeQuery();				

				int producto_actual = -1;
				Producto temp = null;
				while(rs.next()){
					if(producto_actual != rs.getInt("codigo_meta")){
						if(temp != null)
							ret.add(temp);
						temp = (new ProductoDAO()).new Producto();
						temp.id = rs.getInt("codigo_meta");
						temp.id_obra = rs.getInt("obra");
						temp.nombre_producto = rs.getString("descripcion");
						int year = DateTime.now().getYear();
						temp.ejercicios = new ArrayList<Integer>();
						temp.ejercicio_data = new ArrayList<Double[]>();
						for(int i=0;i<5;i++){
							temp.ejercicios.add(i+(year-4));
							temp.ejercicio_data.add(new Double[37]);
							for(int z=0;z<37;z++)
								temp.ejercicio_data.get(temp.ejercicio_data.size()-1)[z]=0.0d;
						}
						producto_actual = temp.id;						
					}
					
					Double[] datos = new Double[37];
					for(int i=0; i<37;i++){
						datos[i] = rs.getDouble(i+10);
					}
					temp.ejercicio_data.set(rs.getInt("ejercicio")-DateTime.now().getYear()+4,datos);
					temp.nombre_producto = rs.getString("descripcion");
				}
				if(temp != null)
					ret.add(temp);
			}
				
			CMemsql.close();
			return ret;
		}catch(Exception e){
			CLogger.write("1", ProductoDAO.class, e);
			CMemsql.close();
			return ret;
		}
	}
}
