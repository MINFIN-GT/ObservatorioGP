package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import org.joda.time.DateTime;

import utilities.CLogger;
import utilities.CMemsql;

public class SaludDAO {
	public class Hospital{
		Integer codigo;
		String nombre;
		Integer treeLevel;
		Integer[] ejercicios;
		ArrayList<Double[]> data_ejercicio;
	}
	
	public class Centros{
		Integer codigo;
		String nombre;
		Integer treeLevel;
		Integer[] ejercicios;
		ArrayList<Double[]> data_ejercicio;
	}
	
	public class Puestos{
		Integer codigo;
		String nombre;
		Integer treeLevel;
		Integer[] ejercicios;
		ArrayList<Double[]> data_ejercicio;
	}
	
	public class Historia{
		ArrayList<ArrayList<elementoBubble>> data_ejercicio = new ArrayList<>();
	}
	
	class elementoBubble{
		Integer x;
		Integer y;
		Integer r;
	}
	
	public static ArrayList<Hospital> getInfoHospitales(){
		ArrayList<Hospital> ret = new ArrayList<Hospital>();
		String query = "";
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select * from mv_hospitales", 
						"order by codigo_departamento, unidad_ejecutora, orden, ejercicio;");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();
				DateTime init_year = new DateTime().minusYears(4);
				int departamento_actual = 0;
				int hospital_actual = 0;
				int rubro_actual = 0;
				Double[] u_asignado= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] d_asignado= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] u_vigente= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] d_vigente= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] u_ejecutado= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] d_ejecutado= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Hospital temp=null;
				Hospital depto=null;
				while(rs.next()){
					if(rs.getInt("codigo_departamento")!=departamento_actual){
						if(ret.size()>1){
							int pos_depto_anterior=ret.size()-1;
							while(ret.get(pos_depto_anterior).treeLevel<3 && pos_depto_anterior>0)
								pos_depto_anterior--;
							for(int i=0; i<5; i++){
								ret.get(pos_depto_anterior).data_ejercicio.get(i)[0] = d_asignado[i];
								ret.get(pos_depto_anterior).data_ejercicio.get(i)[1] = d_vigente[i];
								ret.get(pos_depto_anterior).data_ejercicio.get(i)[2] = d_ejecutado[i];
							}
						}
						depto = (new SaludDAO()).new Hospital();
						depto.treeLevel = 3;
						depto.codigo = rs.getInt("codigo_departamento");
						depto.nombre = rs.getString("nombre_departamento");
						depto.ejercicios = new Integer[5];
						depto.data_ejercicio = new ArrayList<Double[]>();
						for(int i=0; i<5; i++)
							depto.data_ejercicio.add(new Double[3]);
						for(int i=0; i<5; i++){
							d_asignado[i]=0.0d;
							d_vigente[i]=0.0d;
							d_ejecutado[i]=0.0d;
						}
						departamento_actual = depto.codigo;
					}
					if(rs.getInt("unidad_ejecutora")!=hospital_actual){
						if(temp!=null){
							ret.add(temp);
							if(depto!=null){
								ret.add(depto);
								depto=null;
							}
							if(ret.size()>1){
								int pos_hospital_anterior = (ret.get(ret.size()-1).treeLevel==3) ? ret.size() - 12 : ret.size() - 11;
								if(pos_hospital_anterior>0){
									for(int i=0; i<5; i++){
										for(int j=0; j<3; j++){
											ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] = ret.get(pos_hospital_anterior+2).data_ejercicio.get(i)[j];
											ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] += ret.get(pos_hospital_anterior+3).data_ejercicio.get(i)[j];
											ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] += ret.get(pos_hospital_anterior+4).data_ejercicio.get(i)[j];
										}
									}
									for(int i=0; i<5; i++){
										ret.get(pos_hospital_anterior).data_ejercicio.get(i)[0] = u_asignado[i];
										ret.get(pos_hospital_anterior).data_ejercicio.get(i)[1] = u_vigente[i];
										ret.get(pos_hospital_anterior).data_ejercicio.get(i)[2] = u_ejecutado[i];
										ret.get(pos_hospital_anterior).ejercicios[i] = ret.get(pos_hospital_anterior+1).ejercicios[i];
	 								}
									for(int i=0; i<5; i++){
										u_asignado[i]=0.0d;
										u_vigente[i]=0.0d;
										u_ejecutado[i]=0.0d;
									}
								}
							}
						}
						else{
							ret.add(depto);
							depto = null;
						}
						temp = (new SaludDAO()).new Hospital();
						temp.codigo = rs.getInt("unidad_ejecutora");
						temp.nombre = rs.getString("nombre");
						temp.treeLevel = 2;
						temp.ejercicios = new Integer[5];
						temp.data_ejercicio = new ArrayList<Double[]>();
						for(int i=0; i<5; i++)
							temp.data_ejercicio.add(new Double[3]);
						hospital_actual = temp.codigo;
					}
					if(rubro_actual!=rs.getInt("rubro")){
						if(temp!=null)
							ret.add(temp);
						temp = (new SaludDAO()).new Hospital();
						temp.codigo = rs.getInt("rubro");
						temp.nombre = rs.getString("nombre_rubro");
						temp.treeLevel = (temp.codigo > 7 ) ? 0 : 1;
						temp.ejercicios = new Integer[5];
						temp.data_ejercicio = new ArrayList<Double[]>();
						for(int i=0; i<5; i++)
							temp.data_ejercicio.add(new Double[3]);
						rubro_actual=temp.codigo;
					}
					temp.ejercicios[rs.getInt("ejercicio")-init_year.getYear()]=rs.getInt("ejercicio");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[0] = rs.getDouble("asignado");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[1] = rs.getDouble("vigente");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[2] = rs.getDouble("ejecucion");
					u_asignado[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("asignado");
					u_vigente[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("vigente");
					u_ejecutado[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("ejecucion");
					d_asignado[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("asignado");
					d_vigente[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("vigente");
					d_ejecutado[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("ejecucion");
				}
				if(temp!=null){
					ret.add(temp);
					if(ret.size()>1){
						int pos_hospital_anterior = (ret.get(ret.size()-1).treeLevel==3) ? ret.size() - 12 : ret.size() - 11;
						if(pos_hospital_anterior>0){
							for(int i=0; i<5; i++){
								for(int j=0; j<3; j++){
									ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] = ret.get(pos_hospital_anterior+2).data_ejercicio.get(i)[j];
									ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] += ret.get(pos_hospital_anterior+3).data_ejercicio.get(i)[j];
									ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] += ret.get(pos_hospital_anterior+4).data_ejercicio.get(i)[j];
								}
							}
							for(int i=0; i<5; i++){
								ret.get(pos_hospital_anterior).data_ejercicio.get(i)[0] = u_asignado[i];
								ret.get(pos_hospital_anterior).data_ejercicio.get(i)[1] = u_vigente[i];
								ret.get(pos_hospital_anterior).data_ejercicio.get(i)[2] = u_ejecutado[i];
								ret.get(pos_hospital_anterior).ejercicios[i] = ret.get(pos_hospital_anterior+1).ejercicios[i];
								}
							for(int i=0; i<5; i++){
								u_asignado[i]=0.0d;
								u_vigente[i]=0.0d;
								u_ejecutado[i]=0.0d;
							}
						}
						int pos_depto_anterior=ret.size()-1;
						while(ret.get(pos_depto_anterior).treeLevel<3 && pos_depto_anterior>0)
							pos_depto_anterior--;
						for(int i=0; i<5; i++){
							ret.get(pos_depto_anterior).data_ejercicio.get(i)[0] = d_asignado[i];
							ret.get(pos_depto_anterior).data_ejercicio.get(i)[1] = d_vigente[i];
							ret.get(pos_depto_anterior).data_ejercicio.get(i)[2] = d_ejecutado[i];
						}
					}
				}
			}
			CMemsql.close();
			return ret;
		}catch(Exception e){
			CLogger.write("1", SaludDAO.class, e);
		}finally{
			CMemsql.close();
		}
		return ret;
	}
	
	public static ArrayList<Centros> getInfoCentros(){
		ArrayList<Centros> ret = new ArrayList<Centros>();
		String query = "";
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select * from mv_centros_salud", 
						"order by codigo_departamento, codigo_municipio, orden, ejercicio");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();
				DateTime init_year = new DateTime().minusYears(4);
				int departamento_actual = 0;
				int centro_salud_actual = 0;
				int rubro_actual = 0;
				Double[] u_asignado= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] d_asignado= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] u_vigente= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] d_vigente= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] u_ejecutado= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] d_ejecutado= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Centros temp=null;
				Centros depto=null;
				while(rs.next()){
					if(rs.getInt("codigo_departamento")!=departamento_actual){
						if(ret.size()>1){
							int pos_depto_anterior=ret.size()-1;
							while(ret.get(pos_depto_anterior).treeLevel<3 && pos_depto_anterior>0)
								pos_depto_anterior--;
							for(int i=0; i<5; i++){
								ret.get(pos_depto_anterior).data_ejercicio.get(i)[0] = d_asignado[i];
								ret.get(pos_depto_anterior).data_ejercicio.get(i)[1] = d_vigente[i];
								ret.get(pos_depto_anterior).data_ejercicio.get(i)[2] = d_ejecutado[i];
							}
						}
						depto = (new SaludDAO()).new Centros();
						depto.treeLevel = 3;
						depto.codigo = rs.getInt("codigo_departamento");
						depto.nombre = rs.getString("nombre_departamento");
						depto.ejercicios = new Integer[5];
						depto.data_ejercicio = new ArrayList<Double[]>();
						for(int i=0; i<5; i++)
							depto.data_ejercicio.add(new Double[3]);
						for(int i=0; i<5; i++){
							d_asignado[i]=0.0d;
							d_vigente[i]=0.0d;
							d_ejecutado[i]=0.0d;
						}
						departamento_actual = depto.codigo;
					}
					if(rs.getInt("codigo_municipio")!=centro_salud_actual){
						if(temp!=null){
							ret.add(temp);
							if(depto!=null){
								ret.add(depto);
								depto=null;
							}
							if(ret.size()>1){
								int pos_centro_salud_anterior = (ret.get(ret.size()-1).treeLevel==3) ? ret.size() - 12 : ret.size() - 11;
								if(pos_centro_salud_anterior>0){
									for(int i=0; i<5; i++){
										for(int j=0; j<3; j++){
											ret.get(pos_centro_salud_anterior+1).data_ejercicio.get(i)[j] = ret.get(pos_centro_salud_anterior+2).data_ejercicio.get(i)[j];
											ret.get(pos_centro_salud_anterior+1).data_ejercicio.get(i)[j] += ret.get(pos_centro_salud_anterior+3).data_ejercicio.get(i)[j];
											ret.get(pos_centro_salud_anterior+1).data_ejercicio.get(i)[j] += ret.get(pos_centro_salud_anterior+4).data_ejercicio.get(i)[j];
										}
									}
									for(int i=0; i<5; i++){
										ret.get(pos_centro_salud_anterior).data_ejercicio.get(i)[0] = u_asignado[i];
										ret.get(pos_centro_salud_anterior).data_ejercicio.get(i)[1] = u_vigente[i];
										ret.get(pos_centro_salud_anterior).data_ejercicio.get(i)[2] = u_ejecutado[i];
										ret.get(pos_centro_salud_anterior).ejercicios[i] = ret.get(pos_centro_salud_anterior+1).ejercicios[i];
	 								}
									for(int i=0; i<5; i++){
										u_asignado[i]=0.0d;
										u_vigente[i]=0.0d;
										u_ejecutado[i]=0.0d;
									}
								}
							}
						}
						else{
							ret.add(depto);
							depto = null;
						}
						temp = (new SaludDAO()).new Centros();
						temp.codigo = rs.getInt("codigo_municipio");
						temp.nombre = rs.getString("nombre_municipio");
						temp.treeLevel = 2;
						temp.ejercicios = new Integer[5];
						temp.data_ejercicio = new ArrayList<Double[]>();
						for(int i=0; i<5; i++)
							temp.data_ejercicio.add(new Double[3]);
						centro_salud_actual = temp.codigo;
					}
					if(rubro_actual!=rs.getInt("rubro")){
						if(temp!=null)
							ret.add(temp);
						temp = (new SaludDAO()).new Centros();
						temp.codigo = rs.getInt("rubro");
						temp.nombre = rs.getString("nombre_rubro");
						temp.treeLevel = (temp.codigo > 7 ) ? 0 : 1;
						temp.ejercicios = new Integer[5];
						temp.data_ejercicio = new ArrayList<Double[]>();
						for(int i=0; i<5; i++)
							temp.data_ejercicio.add(new Double[3]);
						rubro_actual=temp.codigo;
					}
					temp.ejercicios[rs.getInt("ejercicio")-init_year.getYear()]=rs.getInt("ejercicio");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[0] = rs.getDouble("asignado");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[1] = rs.getDouble("vigente");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[2] = rs.getDouble("ejecucion");
					u_asignado[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("asignado");
					u_vigente[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("vigente");
					u_ejecutado[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("ejecucion");
					d_asignado[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("asignado");
					d_vigente[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("vigente");
					d_ejecutado[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("ejecucion");
				}
				if(temp!=null){
					ret.add(temp);
					if(ret.size()>1){
						int pos_hospital_anterior = (ret.get(ret.size()-1).treeLevel==3) ? ret.size() - 12 : ret.size() - 11;
						if(pos_hospital_anterior>0){
							for(int i=0; i<5; i++){
								for(int j=0; j<3; j++){
									ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] = ret.get(pos_hospital_anterior+2).data_ejercicio.get(i)[j];
									ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] += ret.get(pos_hospital_anterior+3).data_ejercicio.get(i)[j];
									ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] += ret.get(pos_hospital_anterior+4).data_ejercicio.get(i)[j];
								}
							}
							for(int i=0; i<5; i++){
								ret.get(pos_hospital_anterior).data_ejercicio.get(i)[0] = u_asignado[i];
								ret.get(pos_hospital_anterior).data_ejercicio.get(i)[1] = u_vigente[i];
								ret.get(pos_hospital_anterior).data_ejercicio.get(i)[2] = u_ejecutado[i];
								ret.get(pos_hospital_anterior).ejercicios[i] = ret.get(pos_hospital_anterior+1).ejercicios[i];
								}
							for(int i=0; i<5; i++){
								u_asignado[i]=0.0d;
								u_vigente[i]=0.0d;
								u_ejecutado[i]=0.0d;
							}
						}
						int pos_depto_anterior=ret.size()-1;
						while(ret.get(pos_depto_anterior).treeLevel<3 && pos_depto_anterior>0)
							pos_depto_anterior--;
						for(int i=0; i<5; i++){
							ret.get(pos_depto_anterior).data_ejercicio.get(i)[0] = d_asignado[i];
							ret.get(pos_depto_anterior).data_ejercicio.get(i)[1] = d_vigente[i];
							ret.get(pos_depto_anterior).data_ejercicio.get(i)[2] = d_ejecutado[i];
						}
					}
				}
			}
			CMemsql.close();
			return ret;
		}catch(Exception e){
			CLogger.write("2", SaludDAO.class, e);
		}finally{
			CMemsql.close();
		}
		return ret;
	}
	
	public static ArrayList<Puestos> getInfoPuestos(){
		ArrayList<Puestos> ret = new ArrayList<Puestos>();
		String query = "";
		try{
			if(CMemsql.connect()){
				query = String.join(" ", "select * from mv_puestos_salud", 
						"order by codigo_departamento, codigo_municipio, orden, ejercicio");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();
				DateTime init_year = new DateTime().minusYears(4);
				int departamento_actual = 0;
				int puesto_salud_actual = 0;
				int rubro_actual = 0;
				Double[] u_asignado= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] d_asignado= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] u_vigente= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] d_vigente= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] u_ejecutado= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] d_ejecutado= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Puestos temp=null;
				Puestos depto=null;
				while(rs.next()){
					if(rs.getInt("codigo_departamento")!=departamento_actual){
						if(ret.size()>1){
							int pos_depto_anterior=ret.size()-1;
							while(ret.get(pos_depto_anterior).treeLevel<3 && pos_depto_anterior>0)
								pos_depto_anterior--;
							for(int i=0; i<5; i++){
								ret.get(pos_depto_anterior).data_ejercicio.get(i)[0] = d_asignado[i];
								ret.get(pos_depto_anterior).data_ejercicio.get(i)[1] = d_vigente[i];
								ret.get(pos_depto_anterior).data_ejercicio.get(i)[2] = d_ejecutado[i];
							}
						}
						depto = (new SaludDAO()).new Puestos();
						depto.treeLevel = 3;
						depto.codigo = rs.getInt("codigo_departamento");
						depto.nombre = rs.getString("nombre_departamento");
						depto.ejercicios = new Integer[5];
						depto.data_ejercicio = new ArrayList<Double[]>();
						for(int i=0; i<5; i++)
							depto.data_ejercicio.add(new Double[3]);
						for(int i=0; i<5; i++){
							d_asignado[i]=0.0d;
							d_vigente[i]=0.0d;
							d_ejecutado[i]=0.0d;
						}
						departamento_actual = depto.codigo;
					}
					if(rs.getInt("codigo_municipio")!=puesto_salud_actual){
						if(temp!=null){
							ret.add(temp);
							if(depto!=null){
								ret.add(depto);
								depto=null;
							}
							if(ret.size()>1){
								int pos_centro_salud_anterior = (ret.get(ret.size()-1).treeLevel==3) ? ret.size() - 12 : ret.size() - 11;
								if(pos_centro_salud_anterior>0){
									for(int i=0; i<5; i++){
										for(int j=0; j<3; j++){
											ret.get(pos_centro_salud_anterior+1).data_ejercicio.get(i)[j] = ret.get(pos_centro_salud_anterior+2).data_ejercicio.get(i)[j];
											ret.get(pos_centro_salud_anterior+1).data_ejercicio.get(i)[j] += ret.get(pos_centro_salud_anterior+3).data_ejercicio.get(i)[j];
											ret.get(pos_centro_salud_anterior+1).data_ejercicio.get(i)[j] += ret.get(pos_centro_salud_anterior+4).data_ejercicio.get(i)[j];
										}
									}
									for(int i=0; i<5; i++){
										ret.get(pos_centro_salud_anterior).data_ejercicio.get(i)[0] = u_asignado[i];
										ret.get(pos_centro_salud_anterior).data_ejercicio.get(i)[1] = u_vigente[i];
										ret.get(pos_centro_salud_anterior).data_ejercicio.get(i)[2] = u_ejecutado[i];
										ret.get(pos_centro_salud_anterior).ejercicios[i] = ret.get(pos_centro_salud_anterior+1).ejercicios[i];
	 								}
									for(int i=0; i<5; i++){
										u_asignado[i]=0.0d;
										u_vigente[i]=0.0d;
										u_ejecutado[i]=0.0d;
									}
								}
							}
						}
						else{
							ret.add(depto);
							depto = null;
						}
						temp = (new SaludDAO()).new Puestos();
						temp.codigo = rs.getInt("codigo_municipio");
						temp.nombre = rs.getString("nombre_municipio");
						temp.treeLevel = 2;
						temp.ejercicios = new Integer[5];
						temp.data_ejercicio = new ArrayList<Double[]>();
						for(int i=0; i<5; i++)
							temp.data_ejercicio.add(new Double[3]);
						puesto_salud_actual = temp.codigo;
					}
					if(rubro_actual!=rs.getInt("rubro")){
						if(temp!=null)
							ret.add(temp);
						temp = (new SaludDAO()).new Puestos();
						temp.codigo = rs.getInt("rubro");
						temp.nombre = rs.getString("nombre_rubro");
						temp.treeLevel = (temp.codigo > 7 ) ? 0 : 1;
						temp.ejercicios = new Integer[5];
						temp.data_ejercicio = new ArrayList<Double[]>();
						for(int i=0; i<5; i++)
							temp.data_ejercicio.add(new Double[3]);
						rubro_actual=temp.codigo;
					}
					temp.ejercicios[rs.getInt("ejercicio")-init_year.getYear()]=rs.getInt("ejercicio");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[0] = rs.getDouble("asignado");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[1] = rs.getDouble("vigente");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[2] = rs.getDouble("ejecucion");
					u_asignado[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("asignado");
					u_vigente[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("vigente");
					u_ejecutado[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("ejecucion");
					d_asignado[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("asignado");
					d_vigente[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("vigente");
					d_ejecutado[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("ejecucion");
				}
				if(temp!=null){
					ret.add(temp);
					if(ret.size()>1){
						int pos_hospital_anterior = (ret.get(ret.size()-1).treeLevel==3) ? ret.size() - 12 : ret.size() - 11;
						if(pos_hospital_anterior>0){
							for(int i=0; i<5; i++){
								for(int j=0; j<3; j++){
									ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] = ret.get(pos_hospital_anterior+2).data_ejercicio.get(i)[j];
									ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] += ret.get(pos_hospital_anterior+3).data_ejercicio.get(i)[j];
									ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] += ret.get(pos_hospital_anterior+4).data_ejercicio.get(i)[j];
								}
							}
							for(int i=0; i<5; i++){
								ret.get(pos_hospital_anterior).data_ejercicio.get(i)[0] = u_asignado[i];
								ret.get(pos_hospital_anterior).data_ejercicio.get(i)[1] = u_vigente[i];
								ret.get(pos_hospital_anterior).data_ejercicio.get(i)[2] = u_ejecutado[i];
								ret.get(pos_hospital_anterior).ejercicios[i] = ret.get(pos_hospital_anterior+1).ejercicios[i];
								}
							for(int i=0; i<5; i++){
								u_asignado[i]=0.0d;
								u_vigente[i]=0.0d;
								u_ejecutado[i]=0.0d;
							}
						}
						int pos_depto_anterior=ret.size()-1;
						while(ret.get(pos_depto_anterior).treeLevel<3 && pos_depto_anterior>0)
							pos_depto_anterior--;
						for(int i=0; i<5; i++){
							ret.get(pos_depto_anterior).data_ejercicio.get(i)[0] = d_asignado[i];
							ret.get(pos_depto_anterior).data_ejercicio.get(i)[1] = d_vigente[i];
							ret.get(pos_depto_anterior).data_ejercicio.get(i)[2] = d_ejecutado[i];
						}
					}
				}
			}
			CMemsql.close();
			return ret;
		}catch(Exception e){
			CLogger.write("3", SaludDAO.class, e);
		}finally{
			CMemsql.close();
		}
		return ret;
	}
	
	public static ArrayList<Hospital> getNivelAtencion(int nivel){
		ArrayList<Hospital> ret = new ArrayList<Hospital>();
		String query = "";
		try{
			if(CMemsql.connect()){
				String table="";
				switch(nivel){
					case 1: table="mv_hospitales"; break;
					case 2: table="mv_centros_salud"; break;
					case 3: table="mv_puestos_salud"; break;
				}
				query = String.join(" ", "select ejercicio,rubro, nombre_rubro,orden, sum(asignado) asignado, sum(vigente) vigente, sum(ejecucion) ejecucion " + 
						"from "+table+" " + 
						"group by ejercicio,rubro, nombre_rubro,orden " + 
						"order by orden, rubro, nombre_rubro, ejercicio");
				
				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();
				DateTime init_year = new DateTime().minusYears(4);
				int rubro_actual = 0;
				Double[] t_asignado= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] t_vigente= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Double[] t_ejecutado= new Double[]{0.0d, 0.0d,0.0d, 0.0d,0.0d};
				Hospital temp=null;
				while(rs.next()){
					if(rubro_actual!=rs.getInt("rubro")){
						if(temp!=null)
							ret.add(temp);
						temp = (new SaludDAO()).new Hospital();
						temp.codigo = rs.getInt("rubro");
						temp.nombre = rs.getString("nombre_rubro");
						temp.treeLevel = (temp.codigo > 7 ) ? 0 : 1;
						temp.ejercicios = new Integer[5];
						temp.data_ejercicio = new ArrayList<Double[]>();
						for(int i=0; i<5; i++)
							temp.data_ejercicio.add(new Double[3]);
						rubro_actual=temp.codigo;
					}
					temp.ejercicios[rs.getInt("ejercicio")-init_year.getYear()]=rs.getInt("ejercicio");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[0] = rs.getDouble("asignado");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[1] = rs.getDouble("vigente");
					temp.data_ejercicio.get(rs.getInt("ejercicio")-init_year.getYear())[2] = rs.getDouble("ejecucion");
					t_asignado[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("asignado");
					t_vigente[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("vigente");
					t_ejecutado[rs.getInt("ejercicio")-init_year.getYear()] += rs.getDouble("ejecucion");
				}
				ret.add(temp);
			}
			return ret;
		}catch(Exception e){
			CLogger.write("4", SaludDAO.class, e);
		}
		finally{
			CMemsql.close();
		}
		return ret;
	}
	
	public static ArrayList<Historia> getHistoria(){
		ArrayList<Historia> ret = new ArrayList<Historia>();
		String query = "";
		try{
//			if(CMemsql.connect()){
				query = String.join(" ", "");
				
//				PreparedStatement pstmt = CMemsql.getConnection().prepareStatement(query);
//				ResultSet rs = pstmt.executeQuery();
				
//				while(rs.next()){
//					
//				}
				
				for(int j=0; j<18; j++){
					Historia hist = (new SaludDAO()).new Historia();
					for(int i=0; i<50; i++){
						Random x = new Random();
						Random y = new Random();
						Random r = new Random();
						elementoBubble temp = (new SaludDAO()).new elementoBubble();
						ArrayList<elementoBubble> lsteb = new ArrayList<elementoBubble>();
						temp.x = x.nextInt(50);
						temp.y = y.nextInt(50);
						temp.r = r.nextInt(50);
						lsteb.add(temp);
						hist.data_ejercicio.add(lsteb);
					}
					ret.add(hist);
				}
				
//			}
			return ret;
		}catch(Exception e){
			CLogger.write("5", SaludDAO.class, e);
		}finally{
			//CMemsql.close();
		}
		return ret;
	}
}
