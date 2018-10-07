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

import manager.data.dao.CountryDAO;
import manager.data.model.Country;

/**
 * Servlet implementation class LoadCountries
 */
@WebServlet("/load_countries")
public class LoadCountries extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadCountries() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		try
		{
			CountryDAO countryDAO = new CountryDAO();
			ArrayList<String> regions = countryDAO.readRegions();
			ArrayList<Country> countries = countryDAO.readAllAvailableForAnalysisChart();
			System.out.println(countries.size());
			JSONArray regionsArray = new JSONArray();
			for (String r: regions)
			{
				regionsArray.put(r);
			}
			JSONArray countriesArray = new JSONArray();
			for (Country c: countries)
			{
				JSONObject countryJSON = new JSONObject();
				countryJSON.put("code", c.getCode());
				countryJSON.put("name", c.getName());
				countryJSON.put("region", c.getRegion());
				countriesArray.put(countryJSON);
			}
			JSONArray array = new JSONArray();
			array.put(regionsArray);
			array.put(countriesArray);
			response.getWriter().write(array.toString());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}
