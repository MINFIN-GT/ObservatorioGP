package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
				while(rs.next()){
					if(rs.getInt("codigo_departamento")!=departamento_actual){
						Hospital depto = (new SaludDAO()).new Hospital();
						depto.treeLevel = 0;
						depto.codigo = rs.getInt("codigo_departamento");
						depto.nombre = rs.getString("nombre_departamento");
						depto.ejercicios = new Integer[5];
						depto.data_ejercicio = new ArrayList<Double[]>();
						for(int i=0; i<5; i++)
							depto.data_ejercicio.add(new Double[3]);
						ret.add(depto);
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
							if(ret.size()>1){
								int pos_hospital_anterior = (ret.get(ret.size()-1).treeLevel==0) ? ret.size() - 12 : ret.size() - 11;
								if(pos_hospital_anterior>0){
									for(int i=0; i<5; i++){
										for(int j=0; j<3; j++){
											ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] = ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j];
											ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] += ret.get(pos_hospital_anterior+2).data_ejercicio.get(i)[j];
											ret.get(pos_hospital_anterior+1).data_ejercicio.get(i)[j] += ret.get(pos_hospital_anterior+3).data_ejercicio.get(i)[j];
										}
									}
									for(int i=1; i<=10; i++){
										for(int j=0; j<5; j++){
											u_asignado[j]+=ret.get(pos_hospital_anterior+i).data_ejercicio.get(j)[0];
											u_vigente[j]+=ret.get(pos_hospital_anterior+i).data_ejercicio.get(j)[1];
											u_ejecutado[j]+=ret.get(pos_hospital_anterior+i).data_ejercicio.get(j)[2];
										}
										i=(i==1) ? 4 : i;
									}
									for(int i=0; i<5; i++){
										ret.get(pos_hospital_anterior).data_ejercicio.get(i)[0] = u_asignado[i];
										ret.get(pos_hospital_anterior).data_ejercicio.get(i)[1] = u_vigente[i];
										ret.get(pos_hospital_anterior).data_ejercicio.get(i)[2] = u_ejecutado[i];
										d_asignado[i] += u_asignado[i];
										d_vigente[i] += u_vigente[i];
										d_ejecutado[i] += u_ejecutado[i];
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
						temp = (new SaludDAO()).new Hospital();
						temp.codigo = rs.getInt("unidad_ejecutora");
						temp.nombre = rs.getString("nombre");
						temp.treeLevel = 1;
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
						temp.treeLevel = rs.getInt("nivel") + 1;
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
				}
				if(temp!=null)
					ret.add(temp);
			}
			
			CMemsql.close();
			return ret;
		}catch(Exception e){
			CLogger.write("1", SaludDAO.class, e);
			CMemsql.close();
			return null;
		}
	}
}
