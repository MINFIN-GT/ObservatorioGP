package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.joda.time.DateTime;

import utilities.CLogger;
import utilities.CMemsql;

public class DeudaDAO {
	
	public class Deuda{
		public int entdiad_id;
		public String entidad_nombre;
		public ArrayList<Double[]> ejercicio_data;
		public ArrayList<Integer> ejercicios;
		public int nivel;
	}
	
	public static ArrayList<Deuda> getDeuda(int nivel){
		ArrayList<Deuda> ret = new ArrayList<Deuda>();
		String query = "";
		switch(nivel){
			case 1: 
				query = "select ejercicio,tipo_deuda, tipo_deuda_nombre, 1 nivel, " +
					"sum(asignado) asignado, " +
					"sum(ejecutado_m1) ejecutado_m1, " +
					"sum(ejecutado_m2) ejecutado_m2, " +
					"sum(ejecutado_m3) ejecutado_m3, " +
					"sum(ejecutado_m4) ejecutado_m4, " +
					"sum(ejecutado_m5) ejecutado_m5, " +
					"sum(ejecutado_m6) ejecutado_m6, " +
					"sum(ejecutado_m7) ejecutado_m7, " +
					"sum(ejecutado_m8) ejecutado_m8, " +
					"sum(ejecutado_m9) ejecutado_m9, " +
					"sum(ejecutado_m10) ejecutado_m10, " +
					"sum(ejecutado_m11) ejecutado_m11, " +
					"sum(ejecutado_m12) ejecutado_m12, " +
					"sum(vigente_m1) vigente_m1, " +
					"sum(vigente_m2) vigente_m2, " +
					"sum(vigente_m3) vigente_m3, " +
					"sum(vigente_m4) vigente_m4, " +
					"sum(vigente_m5) vigente_m5, " +
					"sum(vigente_m6) vigente_m6, " +
					"sum(vigente_m7) vigente_m7, " +
					"sum(vigente_m8) vigente_m8, " +
					"sum(vigente_m9) vigente_m9, " +
					"sum(vigente_m10) vigente_m10, " +
					"sum(vigente_m11) vigente_m11, " +
					"sum(vigente_m12) vigente_m12 " +
					"from mv_deuda " +
					"where ejercicio >= ? "+
					"group by tipo_deuda, tipo_deuda_nombre, ejercicio " +
					"order by tipo_deuda, ejercicio";
				break;
			case 2: 
				query="select ejercicio,tipo_deuda, tipo_deuda_nombre, clasificacion, clasificacion_nombre, " +
						"programa, programa_nombre, actividad, actividad_obra_nombre, renglon, renglon_nombre, " +
						"sum(asignado) asignado, " +
						"sum(ejecutado_m1) ejecutado_m1, " +
						"sum(ejecutado_m2) ejecutado_m2, " +
						"sum(ejecutado_m3) ejecutado_m3, " +
						"sum(ejecutado_m4) ejecutado_m4, " +
						"sum(ejecutado_m5) ejecutado_m5, " +
						"sum(ejecutado_m6) ejecutado_m6, " +
						"sum(ejecutado_m7) ejecutado_m7, " +
						"sum(ejecutado_m8) ejecutado_m8, " +
						"sum(ejecutado_m9) ejecutado_m9, " +
						"sum(ejecutado_m10) ejecutado_m10, " +
						"sum(ejecutado_m11) ejecutado_m11, " +
						"sum(ejecutado_m12) ejecutado_m12, " +
						"sum(vigente_m1) vigente_m1, " +
						"sum(vigente_m2) vigente_m2, " +
						"sum(vigente_m3) vigente_m3, " +
						"sum(vigente_m4) vigente_m4, " +
						"sum(vigente_m5) vigente_m5, " +
						"sum(vigente_m6) vigente_m6, " +
						"sum(vigente_m7) vigente_m7, " +
						"sum(vigente_m8) vigente_m8, " +
						"sum(vigente_m9) vigente_m9, " +
						"sum(vigente_m10) vigente_m10, " +
						"sum(vigente_m11) vigente_m11, " +
						"sum(vigente_m12) vigente_m12 " +
						"from mv_deuda " +
						"where ejercicio >= ? "+
						"group by tipo_deuda, tipo_deuda_nombre, clasificacion, clasificacion_nombre, programa, programa_nombre, " +
						"actividad, actividad_obra_nombre, ejercicio " +
						"order by tipo_deuda, clasificacion, programa, actividad, renglon, ejercicio";
				break;
		}
		try{
			if(CMemsql.connect()){
				DateTime date = DateTime.now();
				PreparedStatement pstm = CMemsql.getConnection().prepareStatement(query);
				pstm.setInt(1, date.getYear()-4);
				ResultSet rs = pstm.executeQuery();
				int entidad_actual = -1;
				Deuda deuda=null;
				Deuda deuda_renglon=null;
				Double[] datos = null;
				Double[] totales = new Double[25];
				int tipo_deuda = 0;
				int clasificacion = 0;
				int programa = 0; 
				int actividad = 0;
				int renglon = 0;
				for(int i=0; i<25; i++)
					totales[i] = 0.0d;
				while(rs.next()){
					if(nivel==1){
						if(rs.getInt(2)!=entidad_actual){
							if(deuda!=null)
								ret.add(deuda);
							deuda = (new DeudaDAO()).new Deuda();
							deuda.entdiad_id = rs.getInt(2);
							deuda.entidad_nombre = rs.getString(3);
							deuda.nivel = rs.getInt(4);
							deuda.ejercicio_data = new ArrayList<Double[]>();
							deuda.ejercicios = new ArrayList<Integer>();
							entidad_actual = deuda.entdiad_id;
						}
						deuda.ejercicios.add(rs.getInt(1));
						datos = new Double[25];
						for(int i=0; i<25; i++)
							datos[i] = rs.getDouble(i+5);
						deuda.ejercicio_data.add(datos);
					}
					else if(nivel==2){
						if(tipo_deuda!=rs.getInt(2)){
							deuda = (new DeudaDAO()).new Deuda();
							deuda.entdiad_id = rs.getInt(2);
							deuda.entidad_nombre = rs.getString(3);
							deuda.nivel = 1;
							deuda.ejercicio_data = new ArrayList<Double[]>();
							deuda.ejercicios = new ArrayList<Integer>();
							tipo_deuda = deuda.entdiad_id;
							ret.add(deuda);
						}
						if(clasificacion!=rs.getInt(4)){
							deuda = (new DeudaDAO()).new Deuda();
							deuda.entdiad_id = rs.getInt(4);
							deuda.entidad_nombre = rs.getString(5);
							deuda.nivel = 2;
							deuda.ejercicio_data = new ArrayList<Double[]>();
							deuda.ejercicios = new ArrayList<Integer>();
							clasificacion = deuda.entdiad_id;
							ret.add(deuda);
						}
						if(programa!=rs.getInt(6)){
							deuda = (new DeudaDAO()).new Deuda();
							deuda.entdiad_id = rs.getInt(6);
							deuda.entidad_nombre = rs.getString(7);
							deuda.nivel = 3;
							deuda.ejercicio_data = new ArrayList<Double[]>();
							deuda.ejercicios = new ArrayList<Integer>();
							programa = deuda.entdiad_id;
							ret.add(deuda);
						}
						if(actividad!=rs.getInt(8)){
							deuda = (new DeudaDAO()).new Deuda();
							deuda.entdiad_id = rs.getInt(8);
							deuda.entidad_nombre = rs.getString(9);
							deuda.nivel = 4;
							deuda.ejercicio_data = new ArrayList<Double[]>();
							deuda.ejercicios = new ArrayList<Integer>();
							actividad = deuda.entdiad_id;
							ret.add(deuda);
						}
						if(renglon!=rs.getInt(10)){
							deuda_renglon = (new DeudaDAO()).new Deuda();
							deuda_renglon.entdiad_id = rs.getInt(10);
							deuda_renglon.entidad_nombre = rs.getString(11);
							deuda_renglon.nivel = 5;
							deuda_renglon.ejercicio_data = new ArrayList<Double[]>();
							deuda_renglon.ejercicios = new ArrayList<Integer>();
							renglon = deuda_renglon.entdiad_id;
						}
						deuda_renglon.ejercicios.add(rs.getInt(1));
						datos = new Double[25];
						for(int i=0; i<25; i++)
							datos[i] = rs.getDouble(i+13);
						deuda_renglon.ejercicio_data.add(datos);
					}
				}
				CMemsql.close();
				if(deuda!=null)
					ret.add(deuda);
			}
			return ret;
		}
		catch(Throwable e){
			CLogger.write("1", DeudaDAO.class, e);
		}
		finally{
			CMemsql.close();
		}
		return ret;
	}
}
