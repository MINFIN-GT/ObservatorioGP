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

import dao.SaludDAO;
import dao.SaludDAO.Centros;
import dao.SaludDAO.Hospital;
import dao.SaludDAO.Puestos;

@WebServlet("/SSalud")
public class SSalud extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SSalud() {
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
		
		if(accion.equals("getHospitales")){
			ArrayList<Hospital> lsthospitales = SaludDAO.getInfoHospitales();
			String hospitales = new GsonBuilder().serializeNulls().create().toJson(lsthospitales);
			response_text = String.join(" ", "\"hospitales\": ", hospitales);
			response_text = String.join(" ","{\"success\": true,", response_text, "}");
		}else if(accion.equals("getCentros")){
			ArrayList<Centros> lstcentros = SaludDAO.getInfoCentros();
			String centros = new GsonBuilder().serializeNulls().create().toJson(lstcentros);
			response_text = String.join(" ", "\"centros\": ", centros);
			response_text = String.join(" ","{\"success\": true,", response_text, "}");
		}else if(accion.equals("getPuestos")){
			ArrayList<Puestos> lstpuestos = SaludDAO.getInfoPuestos();
			String puestos = new GsonBuilder().serializeNulls().create().toJson(lstpuestos);
			response_text = String.join(" ", "\"puestos\": ", puestos);
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
