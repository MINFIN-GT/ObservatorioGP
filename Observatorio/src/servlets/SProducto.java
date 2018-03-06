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

import dao.ProductoDAO;
import dao.ProductoDAO.EjecucionFisica;
import dao.ProductoDAO.VectorValores;
import utilities.CLogger;
import utilities.Utils;

@WebServlet("/SProducto")
public class SProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	public SProducto() {
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
		String tipo_resultado = Utils.String2Int(map.get("tipo_resultado")) == 1 ? "Estrátegico" : (Utils.String2Int(map.get("tipo_resultado")) == 2 ? "Institucional" : "Otros");
		
		if(accion.equals("getEjecucionFisica")){
			try{
				
				ArrayList<EjecucionFisica> lstejecucionfisica = ProductoDAO.getEjecucionFisica(entidad,unidadEjecutora,programa,subPrograma,actividad, tipo_resultado);
				
				String ejecucion_fisica = new GsonBuilder().serializeNulls().create().toJson(lstejecucionfisica);
				response_text = String.join(" ", "\"ejecucionFisica\": ", ejecucion_fisica);
				response_text = String.join(" ","{\"success\": true,", response_text, "}");
			}catch(Exception e){
				CLogger.write("1", SProducto.class, e);
			}
		}else if(accion.equals("getVectoresValores")){
			try{
				ArrayList<VectorValores> lstvectorvalores = ProductoDAO.getVectorValores(entidad,unidadEjecutora,programa,subPrograma,actividad, codigo_meta);
				
				String ejecucion_fisica = new GsonBuilder().serializeNulls().create().toJson(lstvectorvalores);
				response_text = String.join(" ", "\"vectorValores\": ", ejecucion_fisica);
				response_text = String.join(" ","{\"success\": true,", response_text, "}");
			}catch(Exception e){
				CLogger.write("3", SProducto.class, e);
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
