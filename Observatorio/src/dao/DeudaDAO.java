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
						"actividad, actividad_obra_nombre, renglon, renglon_nombre, ejercicio " +
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
					int tipo_deuda_t = tipo_deuda;
					int clasificacion_t = clasificacion;
					int programa_t = programa; 
					int actividad_t = actividad;
					int renglon_t = renglon;
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
							tipo_deuda_t = deuda.entdiad_id;
							if(deuda_renglon!=null){
								ret.add(deuda_renglon);
								deuda_renglon=null;
								calcularPadre(ret, ret.size()-1);
							}
							ret.add(deuda);
						}
						if(clasificacion!=rs.getInt(4) || tipo_deuda!=rs.getInt(2)){
							deuda = (new DeudaDAO()).new Deuda();
							deuda.entdiad_id = rs.getInt(4);
							deuda.entidad_nombre = rs.getString(5);
							deuda.nivel = 2;
							deuda.ejercicio_data = new ArrayList<Double[]>();
							deuda.ejercicios = new ArrayList<Integer>();
							clasificacion_t = deuda.entdiad_id;
							if(deuda_renglon!=null){
								ret.add(deuda_renglon);
								deuda_renglon=null;
								calcularPadre(ret, ret.size()-1);
							}
							ret.add(deuda);
						}
						if(programa!=rs.getInt(6) || clasificacion!=rs.getInt(4) || tipo_deuda!=rs.getInt(2)){
							deuda = (new DeudaDAO()).new Deuda();
							deuda.entdiad_id = rs.getInt(6);
							deuda.entidad_nombre = deuda.entdiad_id+" "+rs.getString(7);
							deuda.nivel = 3;
							deuda.ejercicio_data = new ArrayList<Double[]>();
							deuda.ejercicios = new ArrayList<Integer>();
							programa_t = deuda.entdiad_id;
							if(deuda_renglon!=null){
								ret.add(deuda_renglon);
								deuda_renglon=null;
								calcularPadre(ret, ret.size()-1);
							}
							ret.add(deuda);
						}
						if(actividad!=rs.getInt(8) || programa!=rs.getInt(6) || clasificacion!=rs.getInt(4) || tipo_deuda!=rs.getInt(2)){
							deuda = (new DeudaDAO()).new Deuda();
							deuda.entdiad_id = rs.getInt(8);
							deuda.entidad_nombre = deuda.entdiad_id+" "+rs.getString(9);
							deuda.nivel = 4;
							deuda.ejercicio_data = new ArrayList<Double[]>();
							deuda.ejercicios = new ArrayList<Integer>();
							actividad_t = deuda.entdiad_id;
							if(deuda_renglon!=null){
								ret.add(deuda_renglon);
								deuda_renglon=null;
								calcularPadre(ret, ret.size()-1);
							}
							ret.add(deuda);
						}
						if(renglon!=rs.getInt(10) || actividad!=rs.getInt(8) || programa!=rs.getInt(6) || clasificacion!=rs.getInt(4) || tipo_deuda!=rs.getInt(2)){
							if(deuda_renglon!=null && deuda_renglon.entdiad_id!=rs.getInt(10)){
								ret.add(deuda_renglon);
								deuda_renglon=null;
							}
							deuda_renglon = (new DeudaDAO()).new Deuda();
							deuda_renglon.entdiad_id = rs.getInt(10);
							deuda_renglon.entidad_nombre = deuda_renglon.entdiad_id + " " +rs.getString(11);
							deuda_renglon.nivel = 5;
							deuda_renglon.ejercicio_data = new ArrayList<Double[]>();
							deuda_renglon.ejercicios = new ArrayList<Integer>();
							renglon_t = deuda_renglon.entdiad_id;
						}
						while(deuda_renglon.ejercicios.size()<rs.getInt(1)-DateTime.now().getYear()+4+1)
							deuda_renglon.ejercicios.add(0);
						deuda_renglon.ejercicios.set(rs.getInt(1)-DateTime.now().getYear()+4,rs.getInt(1));
						datos = new Double[25];
						for(int i=0; i<25; i++)
							datos[i] = rs.getDouble(i+12);
						while(deuda_renglon.ejercicio_data.size()<rs.getInt(1)-DateTime.now().getYear()+4+1){
							deuda_renglon.ejercicio_data.add(new Double[25]);
							for(int count=0; count<25; count++)
								deuda_renglon.ejercicio_data.get(deuda_renglon.ejercicio_data.size()-1)[count]=0.0d;
						}
						deuda_renglon.ejercicio_data.set(rs.getInt(1)-DateTime.now().getYear()+4,datos);
						tipo_deuda = tipo_deuda_t;
						clasificacion = clasificacion_t;
						programa = programa_t; 
						actividad = actividad_t;
						renglon = renglon_t;
					}
				}
				if(nivel==1){
					if(deuda!=null)
						ret.add(deuda);
				}
				else if(nivel==2){
					if(deuda_renglon!=null){
						ret.add(deuda_renglon);
						deuda_renglon=null;
						calcularPadre(ret, ret.size()-1);
					}
					CalcularTotales(3, ret);
					CalcularTotales(2, ret);
					CalcularTotales(1, ret);
				}
				CMemsql.close();
			}
			return ret;
		}
		catch(Throwable e){
			CLogger.write("1", DeudaDAO.class, e);
			CMemsql.close();
		}
		return ret;
	}
	
	private static void calcularPadre(ArrayList<Deuda> deuda, int pos){
		Deuda actual = deuda.get(pos);
		int nivel = actual.nivel;
		ArrayList<Double[]> totales = new ArrayList<Double[]>();
		ArrayList<Integer> ejercicios = new ArrayList<Integer>();
		for(int i=0; i<actual.ejercicios.size(); i++){
			totales.add(new Double[25]);
			for(int j=0; j<25; j++)
				totales.get(totales.size()-1)[j] = 0.0d;
		}
		while(actual.nivel==nivel && pos>1){
			for(int i=0; i<actual.ejercicios.size(); i++){
				for(int j=0; j<25; j++){
					totales.get(i)[j] += actual.ejercicio_data.get(i)[j];
				}
			}
			pos--;
			if(ejercicios.size()<actual.ejercicios.size()){
				ejercicios.clear();
				ejercicios.addAll(actual.ejercicios);
			}
			actual = deuda.get(pos);
		}
		if(deuda.get(pos).ejercicio_data==null || deuda.get(pos).ejercicio_data.size()==0){
			for(int z=0; z<totales.size();z++)
				deuda.get(pos).ejercicio_data.add(totales.get(z).clone());
		}
		else{
			for(int i=0; i<ejercicios.size(); i++){
				for(int j=0; j<25; j++){
					deuda.get(pos).ejercicio_data.get(i)[j] += totales.get(i)[j];
				}
			}
		}	
		deuda.get(pos).ejercicios = ejercicios;
	}
	
	public static void CalcularTotales(int nivel,ArrayList<Deuda> deuda){
		for(int i=0;i<deuda.size(); i++){
			if(deuda.get(i).nivel==nivel){
				int temp_pos=i+1;
				while(temp_pos<deuda.size()-1 && deuda.get(temp_pos).nivel>deuda.get(i).nivel){
					if(deuda.get(temp_pos).nivel == deuda.get(i).nivel+1){
						if(deuda.get(i).ejercicio_data==null || deuda.get(i).ejercicio_data.size()==0){
							for(int z=0; z<deuda.get(temp_pos).ejercicio_data.size();z++)
								deuda.get(i).ejercicio_data.add(deuda.get(temp_pos).ejercicio_data.get(z).clone());
							for(int z=0; z<deuda.get(temp_pos).ejercicios.size();z++)
								deuda.get(i).ejercicios.add(deuda.get(temp_pos).ejercicios.get(z));
						}
						else{
							for(int k=0; k<deuda.get(temp_pos).ejercicios.size(); k++){
								while(deuda.get(i).ejercicio_data.size()<=k){
									deuda.get(i).ejercicio_data.add(new Double[25]);
									for(int count=0;count<25; count++)
										deuda.get(i).ejercicio_data.get(deuda.get(i).ejercicio_data.size()-1)[count]=0.0d;
								}	
								for(int j=0; j<25; j++){
									deuda.get(i).ejercicio_data.get(k)[j] += deuda.get(temp_pos).ejercicio_data.get(k)[j];
								}
							}
							if(deuda.get(i).ejercicios.size()==0){
								for(int z=0; z<deuda.get(temp_pos).ejercicios.size();z++)
									deuda.get(i).ejercicios.add(deuda.get(temp_pos).ejercicios.get(z));
							}
								
						}
					}
					temp_pos++;
				}
			}
		}
	}
}
