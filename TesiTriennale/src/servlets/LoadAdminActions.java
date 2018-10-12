package servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;

import manager.data.dao.SourceDAO;
import manager.data.extraction.EUDataWrapper;
import manager.data.extraction.USADataWrapper;
import manager.data.extraction.WorldBankDataWrapper;
import manager.data.model.Source;

/**
 * Servlet implementation class AdminActions
 */
@WebServlet("/admin_actions")
public class LoadAdminActions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadAdminActions() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		String password = request.getParameter("password");
//		String password = "tesi";
		JSONArray result = new JSONArray();
		if (replaceIfMissing(password, REPLACEMENT).equals(password) && password.equals(PASSWORD))
		{
			try
			{
				SourceDAO sourceDAO = new SourceDAO();
				
				ArrayList<String> names = sourceDAO.readNames();
				JSONArray namesArray = new JSONArray();
				for (int i = 0; i < names.size(); i++)
				{
					namesArray.put(names.get(i));
				}
				result.put(namesArray);
				
				ArrayList<String> dataTypes = sourceDAO.readDataTypes();
				JSONArray dataTypesArray = new JSONArray();
				for (int i = 0; i < dataTypes.size(); i++)
				{
					dataTypesArray.put(dataTypes.get(i));
				}
				result.put(dataTypesArray);
				
				ArrayList<Source> sources = sourceDAO.read();
				USADataWrapper usaWrapper = new USADataWrapper();
				EUDataWrapper euWrapper = new EUDataWrapper();
				WorldBankDataWrapper worldBankWrapper = new WorldBankDataWrapper();
				
				JSONArray array = new JSONArray();
				for (Source s: sources)
				{
					JSONObject sourceOBJ = new JSONObject();
					sourceOBJ.put("name", s.getName());
					sourceOBJ.put("dataType", s.getDataType());
					Date updateDate = null;
					switch (s.getName())
					{
						case USADataWrapper.SOURCE:
							updateDate = usaWrapper.lastUpdateDate(s.getDataType());
							break;
						case EUDataWrapper.SOURCE:
							updateDate = euWrapper.lastUpdateDate(s.getDataType());
							break;
						case WorldBankDataWrapper.SOURCE:
							updateDate = worldBankWrapper.lastUpdateDate(s.getDataType());
							break;
						default:
							break;
					}
					if (updateDate != null && !s.getDate().after(updateDate))
					{
						sourceOBJ.put("updated", false);
					}
					else
					{
						sourceOBJ.put("updated", true);
					}
					array.put(sourceOBJ);
				}
				result.put(array);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			catch (UnirestException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		response.getWriter().write(result.toString());
//		System.out.println(result.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	private String replaceIfMissing(String orig, String replacement)
	{
		if (orig == null || orig.trim().equals(""))
			return replacement;
		else
			return orig;
	}
	
//	public static void main(String[] args) throws ServletException, IOException {
//		(new LoadAdminActions()).doGet(null, null);
//	}

	private static final String PASSWORD = "tesi";
	private static final String REPLACEMENT = "$";
}
