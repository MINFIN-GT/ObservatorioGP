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

import dao.ProgramaDAO;
import dao.ProgramaDAO.VectorValoresFinancieros;
import dao.ProgramaDAO.AnioEjecucion;
import dao.ProgramaDAO.Programa;
import utilities.Utils;

@WebServlet("/SPrograma")
public class SPrograma extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SPrograma() {
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
		
		if(accion.equals("getProgramas")){
			ArrayList<Programa> lstprogramas = ProgramaDAO.getProgramas(entidad, unidadEjecutora, programa);
			
			String subprogramas = new GsonBuilder().serializeNulls().create().toJson(lstprogramas);
			response_text = String.join(" ", "\"subprogramas\": ", subprogramas);
			response_text = String.join(" ","{\"success\": true,", response_text, "}");
		}else if(accion.equals("getInfoMensual")){
			ArrayList<AnioEjecucion> lstejecucionfisicafinanciera= ProgramaDAO.getEjecucionFisicaFinancieraPonderadaMensual(entidad, unidadEjecutora, programa);
			ArrayList<VectorValoresFinancieros> lstejecucionfinanciera= ProgramaDAO.getEjecucionFinancieraMensual(entidad, unidadEjecutora, programa);
			
			String informacionMensual = new GsonBuilder().serializeNulls().create().toJson(lstejecucionfisicafinanciera);
			String informacionFinancieraMensual =  new GsonBuilder().serializeNulls().create().toJson(lstejecucionfinanciera);
			
			//response_text = String.join(" ", "\"informacionFisicaMensual\": ", informacionFisicaMensual);
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
