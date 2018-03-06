package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
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

import dao.ActividadDAO;
import dao.ActividadDAO.Actividad;
import dao.ActividadDAO.VectorValoresFinancieros;
import dao.ActividadDAO.VectorValoresFisicos;
import utilities.Utils;

@WebServlet("/SActividad")
public class SActividad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SActividad() {
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
		Integer actividad = Utils.String2Int(map.get("actividad")) != 0 ? Utils.String2Int(map.get("actividad")) : null;
		String tipo_resultado = Utils.String2Int(map.get("tipo_resultado")) == 1 ? "Estrátegico" : (Utils.String2Int(map.get("tipo_resultado")) == 2 ? "Institucional" : "Otros");
		
		if(accion.equals("getActividades")){
			ArrayList<Actividad> lstactividades = ActividadDAO.getActividades(entidad, unidadEjecutora, programa, subPrograma, tipo_resultado);
			String actividades = new GsonBuilder().serializeNulls().create().toJson(lstactividades);
			response_text = String.join(" ", "\"actividades\": ", actividades);
			response_text = String.join(" ","{\"success\": true,", response_text, "}");
		}else if(accion.equals("getInfoMensual")){
			ArrayList<VectorValoresFisicos> lstejecucionfisica= ActividadDAO.getEjecucionFisicaMensual(entidad, unidadEjecutora, programa, subPrograma, actividad);
			ArrayList<VectorValoresFinancieros> lstejecucionfinanciera= ActividadDAO.getEjecucionFinancieraMensual(entidad, unidadEjecutora, programa, subPrograma, actividad);
			String informacionFisicaMensual = new GsonBuilder().serializeNulls().create().toJson(lstejecucionfisica);
			String informacionFinancieraMensual =  new GsonBuilder().serializeNulls().create().toJson(lstejecucionfinanciera);
			response_text = String.join(" ", "\"informacionFisicaMensual\": ", informacionFisicaMensual);
			response_text = String.join(" ", "\"informacionFinancieraMensual\" :", informacionFinancieraMensual, "," +response_text);
			response_text = String.join(" ","{\"success\": true,", response_text, "}");
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
