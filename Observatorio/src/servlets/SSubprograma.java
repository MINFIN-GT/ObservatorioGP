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

import dao.SubprogramaDAO;
import dao.SubprogramaDAO.Subprograma;
import utilities.Utils;

@WebServlet("/SSubprograma")
public class SSubprograma extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SSubprograma() {
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
		Integer subPrograma = Utils.String2Int(map.get("subPrograma")) != 0 ? Utils.String2Int(map.get("subPrograma")) : null;
		
		if(accion.equals("getSubProgramas")){
			ArrayList<Subprograma> lstsubprogramas = SubprogramaDAO.getSubProgramas(entidad, unidadEjecutora, programa);
			
			String subprogramas = new GsonBuilder().serializeNulls().create().toJson(lstsubprogramas);
			response_text = String.join(" ", "\"subprogramas\": ", subprogramas);
			response_text = String.join(" ","{\"success\": true,", response_text, "}");
		}else if(accion.equals("getInfoMensual")){
//			ArrayList<> lstejecucionfisicafinanciera= SubprogramaDAO.getEjecucionFisicaFinancieraPonderadaMensual(entidad, unidadEjecutora, programa, subPrograma);
			
//			String informacionMensual = new GsonBuilder().serializeNulls().create().toJson(lstejecucionfisicafinanciera);
//			response_text = String.join(" ", "\"informacionMensual\": ", informacionMensual);
//			response_text = String.join(" ","{\"success\": true,", response_text, "}");
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
