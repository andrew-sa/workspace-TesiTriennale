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

import exceptions.TupleNotFoundException;
import manager.data.dao.CountryDAO;
import manager.data.dao.CountryPovertyDataDAO;
import manager.data.model.Country;
import manager.data.model.CountryPovertyData;

/**
 * Servlet implementation class LoadCountriesData
 */
@WebServlet("/load_country_poverty_data")
public class LoadCountryPovertyData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadCountryPovertyData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
//		String countriesToLoad = request.getParameter("countries");
//		if (!replaceIfMissing(countriesToLoad, REPLACEMENT).equals(REPLACEMENT))
//		{
//			try
//			{
//				JSONArray array = new JSONArray();
//				String[] countries = countriesToLoad.split(TOKEN);
//				PovertyDataDAO povertyDataDAO = new PovertyDataDAO();
//				for (int i = 0; i < countries.length; i++)
//				{
//					ArrayList<PovertyData> data = povertyDataDAO.readValuesOfTheCountry(new Country(countries[i], null));
//					JSONObject country = new JSONObject();
//					country.put("code", countries[i]);
////					JSON
//				}
//			}
//			catch (SQLException e)
//			{
//				e.printStackTrace();
//			}
//		}
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		if (!replaceIfMissing(code, REPLACEMENT).equals(REPLACEMENT)/* && !replaceIfMissing(name, REPLACEMENT).equals(REPLACEMENT)*/)
		{
			try
			{
				Country country;
				if (replaceIfMissing(name, REPLACEMENT).equals(REPLACEMENT))
				{
					CountryDAO countryDAO = new CountryDAO();
					country = countryDAO.read(code);
				}
				else
				{
					country = new Country(code, name);
				}
				JSONObject result = new JSONObject();
				JSONArray array = new JSONArray();
				CountryPovertyDataDAO povertyDataDAO = new CountryPovertyDataDAO();
				ArrayList<CountryPovertyData> povertyData = povertyDataDAO.readValuesOfTheCountry(country);
				for (CountryPovertyData pd: povertyData)
				{
					JSONObject data = new JSONObject();
					data.put("year", pd.getYear());
					data.put("value", pd.getValue());
					array.put(data);
				}
				result.put("name", country.getName());
				result.put("data", array);
				response.getWriter().write(result.toString());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			catch (TupleNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
	
	private static final String REPLACEMENT = "$";
//	private static final String TOKEN = "-";

}
