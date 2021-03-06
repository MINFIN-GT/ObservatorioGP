package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.sql.Connection;
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

import dao.InfoDAO;
import pojo.TipoInfo;
import utilities.CLogger;
import utilities.CMemsql;
import utilities.Utils;

@WebServlet("/SInfo")
public class SInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SInfo() {
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
		
		String tipo_resultado = Utils.String2Int(map.get("tipo_resultado")) == 1 ? "Estrátegico" : (Utils.String2Int(map.get("tipo_resultado")) == 2 ? "Institucional" : "Otros");
		Connection conn=null;
		if (accion.equals("getTipoResultado")){
			try{
				CMemsql.connect();
				conn = CMemsql.getConnection();
				TipoInfo lsttiporesultado = InfoDAO.getTipoResultado(conn,tipo_resultado);
				String tipoResultado = new GsonBuilder().serializeNulls().create().toJson(lsttiporesultado);
				response_text = String.join(" ", "\"tiporesultado\": ", tipoResultado);
				response_text = String.join(" ","{\"success\": true,", response_text, "}");
			}catch(Exception e){
				CLogger.write("1", SInfo.class, e);
			}
			finally{
				try{
					conn.close();
				}
				catch(Exception e){
					
				}
			}
		}
		else if(accion.equals("getDeuda")){
			try{
				CMemsql.connect();
				conn = CMemsql.getConnection();
				TipoInfo deuda = InfoDAO.getDeuda(conn);
				String sdeuda = new GsonBuilder().serializeNulls().create().toJson(deuda);
				response_text = String.join(" ", "\"tiporesultado\": ", sdeuda);
				response_text = String.join(" ","{\"success\": true,", response_text, "}");
			}catch(Exception e){
				CLogger.write("2", SInfo.class, e);
			}
			finally{
				try{
					conn.close();
				}
				catch(Exception e){
					
				}
			}
		}
		else if(accion.equals("getObligaciones")){
			try{
				CMemsql.connect();
				conn = CMemsql.getConnection();
				TipoInfo obligaciones = InfoDAO.getObligaciones(conn);
				String sobligaciones = new GsonBuilder().serializeNulls().create().toJson(obligaciones);
				response_text = String.join(" ", "\"tiporesultado\": ", sobligaciones);
				response_text = String.join(" ","{\"success\": true,", response_text, "}");
			}catch(Exception e){
				CLogger.write("3", SInfo.class, e);
			}
			finally{
				try{
					conn.close();
				}
				catch(Exception e){
					
				}
			}
		}
		else if(accion.equals("getAll")){
			try{
				CMemsql.connect();
				conn = CMemsql.getConnection();
				TipoInfo estrategico = InfoDAO.getTipoResultado(conn,"Estrátegico");
				String tipoResultado = new GsonBuilder().serializeNulls().create().toJson(estrategico);
				response_text = String.join(" ", "\"resultados_estrategicos\": ", tipoResultado);
				TipoInfo institucional = InfoDAO.getTipoResultado(conn,"Institucional");
				tipoResultado = new GsonBuilder().serializeNulls().create().toJson(institucional);
				response_text = String.join(" ", response_text,", \"resultados_institucionales\": ", tipoResultado);
				TipoInfo otros = InfoDAO.getTipoResultado(conn,"Otros");
				tipoResultado = new GsonBuilder().serializeNulls().create().toJson(otros);
				response_text = String.join(" ", response_text,", \"resultados_otros\": ", tipoResultado);
				TipoInfo deuda = InfoDAO.getDeuda(conn);
				String sdeuda = new GsonBuilder().serializeNulls().create().toJson(deuda);
				response_text = String.join(" ",response_text, ", \"deuda\": ", sdeuda);
				TipoInfo obligaciones = InfoDAO.getObligaciones(conn);
				String sobligaciones = new GsonBuilder().serializeNulls().create().toJson(obligaciones);
				response_text = String.join(" ", response_text, ", \"obligaciones\": ", sobligaciones);
				response_text = String.join(" ","{\"success\": true,", response_text, "}");
			}
			catch(Exception e){
				CLogger.write("4", SInfo.class, e);
			}
			finally{
				try{
					conn.close();
				}
				catch(Exception e){
					
				}
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
