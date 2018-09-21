package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import manager.data.dao.CountryPovertyDataDAO;
import manager.data.model.CountryPovertyData;

@WebServlet("/load_last_values")
public class LoadLastValueByCountry extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoadLastValueByCountry() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		try
		{
			JSONArray valuesJSON = new JSONArray();
			CountryPovertyDataDAO povertyDataDAO = new CountryPovertyDataDAO();
			ArrayList<CountryPovertyData> data = povertyDataDAO.readLastValueByCountry();
			for (CountryPovertyData pd: data)
			{
				JSONObject dataJSON = new JSONObject();
				dataJSON.put("country", pd.getName());
				dataJSON.put("value", pd.getValue());
				valuesJSON.put(dataJSON);
			}
			response.getWriter().write(valuesJSON.toString());
			System.out.println(valuesJSON.toString());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	
//	public static void main(String[] args) throws ServletException, IOException {
//		LoadLastValueByCountry s = new LoadLastValueByCountry();
//		s.doGet(null, null);
//	}

}
