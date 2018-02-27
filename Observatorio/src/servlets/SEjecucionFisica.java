package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dao.EjecucionFisicaDAO;
import utilities.CLogger;
import utilities.Utils;

@WebServlet("/SEjecucionFisica")
public class SEjecucionFisica extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	class stejecucionfisica{
		Integer codigo_meta;
		String metaDescripcion;
		BigDecimal p_ejecucion_4;
		BigDecimal p_ejecucion_3;
		BigDecimal p_ejecucion_2;
		BigDecimal p_ejecucion_1;
		BigDecimal p_ejecucion;
		Integer entidad;
		Integer unidadEjecutora;
		Integer programa;
		Integer subPrograma;
		Integer actividad;
		Integer obra;
		Integer ejercicio;
	}
	
	class stejecucionfisciamensual{
		Integer ejercicio;
		Integer mes;
		BigDecimal p_ejecucion;
	}
	
	class stvectorvalores{
		Integer ejercicio;
		Integer mes;
		BigDecimal ejecucion;
		BigDecimal modificacion;
		BigDecimal cantidad;
	}
	
    public SEjecucionFisica() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = request.getReader();
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		};
		Map<String, String> map = gson.fromJson(sb.toString(), type);
		String accion = map.get("accion");
		String response_text="";
		
		Integer entidad = Utils.String2Int(map.get("entidad"));
		Integer unidadEjecutora = Utils.String2Int(map.get("unidadEjecutora"));
		Integer programa = Utils.String2Int(map.get("programa"));
		Integer subPrograma = Utils.String2Int(map.get("subPrograma"));
		Integer actividad = Utils.String2Int(map.get("actividad"));
		Integer codigo_meta = Utils.String2Int(map.get("codigo_meta")) != 0 ? Utils.String2Int(map.get("codigo_meta")) : null;
		
		if(accion.equals("getEjecucionFisica")){
			try{
				
				ResultSet info = EjecucionFisicaDAO.getEjecucionFisica(entidad,unidadEjecutora,programa,subPrograma,actividad);
				List<stejecucionfisica> lstejecucionfisica = new ArrayList<stejecucionfisica>();
				
				while(info.next()){
					stejecucionfisica temp = new stejecucionfisica();
					temp.codigo_meta = info.getInt("codigo_meta");
					temp.entidad = info.getInt("entidad");
					temp.unidadEjecutora = info.getInt("unidad_ejecutora");
					temp.programa = info.getInt("programa");
					temp.subPrograma = info.getInt("subprograma");
					temp.actividad = info.getInt("actividad");
					temp.obra = info.getInt("obra");
					temp.ejercicio = info.getInt("ejercicio");
					temp.p_ejecucion_4 = info.getBigDecimal("p_ejecucion_4").multiply(new BigDecimal(100));
					temp.p_ejecucion_3 = info.getBigDecimal("p_ejecucion_3").multiply(new BigDecimal(100));
					temp.p_ejecucion_2 = info.getBigDecimal("p_ejecucion_2").multiply(new BigDecimal(100));
					temp.p_ejecucion_1 = info.getBigDecimal("p_ejecucion_1").multiply(new BigDecimal(100));
					temp.p_ejecucion = info.getBigDecimal("p_ejecucion").multiply(new BigDecimal(100));
					temp.metaDescripcion = EjecucionFisicaDAO.getDescripcionProducto(temp.entidad, temp.unidadEjecutora, temp.programa, temp.subPrograma, temp.actividad, temp.obra, temp.ejercicio, temp.codigo_meta);
					lstejecucionfisica.add(temp);
				}
				
				String ejecucion_fisica = new GsonBuilder().serializeNulls().create().toJson(lstejecucionfisica);
				response_text = String.join(" ", "\"ejecucionFisica\": ", ejecucion_fisica);
				response_text = String.join(" ","{\"success\": true,", response_text, "}");
			}catch(Exception e){
				CLogger.write("1", SEjecucionFisica.class, e);
			}
		}else if(accion.equals("getEjecucionFisicaMensual")){
			try{
				ResultSet info = EjecucionFisicaDAO.getEjecucionFisicaMensual(entidad,unidadEjecutora,programa,subPrograma,actividad, codigo_meta);
				List<stejecucionfisciamensual> lstejecucionfisicamensual = new ArrayList<stejecucionfisciamensual>();
				
				while(info.next()){
					stejecucionfisciamensual temp = new stejecucionfisciamensual();
					temp.ejercicio = info.getInt("ejercicio");
					temp.mes = info.getInt("mes");
					temp.p_ejecucion = info.getBigDecimal("p_ejecucion").multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
					lstejecucionfisicamensual.add(temp);
				}
				
				String ejecucion_fisica = new GsonBuilder().serializeNulls().create().toJson(lstejecucionfisicamensual);
				response_text = String.join(" ", "\"ejecucionFisicaMensual\": ", ejecucion_fisica);
				response_text = String.join(" ","{\"success\": true,", response_text, "}");
			}catch(Exception e){
				CLogger.write("2", SEjecucionFisica.class, e);
			}
		}else if(accion.equals("getVectoresValores")){
			try{
				ResultSet info = EjecucionFisicaDAO.getVectorValores(entidad,unidadEjecutora,programa,subPrograma,actividad, codigo_meta);
				List<stvectorvalores> lstvectorvalores = new ArrayList<stvectorvalores>();
				while(info.next()){
					stvectorvalores temp = new stvectorvalores();
					temp.ejercicio = info.getInt("ejercicio");
					temp.mes = info.getInt("mes");
					temp.ejecucion = info.getBigDecimal("ejecucion");
					temp.modificacion = info.getBigDecimal("modificacion");
					temp.cantidad = info.getBigDecimal("cantidad");
					lstvectorvalores.add(temp);
				}
				
				String ejecucion_fisica = new GsonBuilder().serializeNulls().create().toJson(lstvectorvalores);
				response_text = String.join(" ", "\"vectorValores\": ", ejecucion_fisica);
				response_text = String.join(" ","{\"success\": true,", response_text, "}");
			}catch(Exception e){
				CLogger.write("3", SEjecucionFisica.class, e);
			}
		}
		
		response.setHeader("Content-Encoding", "gzip");
		response.setCharacterEncoding("UTF-8");

        OutputStream output = response.getOutputStream();
		GZIPOutputStream gz = new GZIPOutputStream(output);
        gz.write(response_text.getBytes("UTF-8"));
        gz.close();
        output.close();
	}
}
