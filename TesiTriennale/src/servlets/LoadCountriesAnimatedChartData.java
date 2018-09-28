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
import org.json.JSONException;
import org.json.JSONObject;

import exceptions.TupleNotFoundException;
import manager.data.dao.BoundaryValueDAO;
import manager.data.dao.CountryDAO;
import manager.data.dao.CountryGDPPerCapitaDataDAO;
import manager.data.dao.CountryPopulationDataDAO;
import manager.data.dao.CountryPovertyDataDAO;
import manager.data.model.Country;

/**
 * Servlet implementation class LoadAnimatedChartData
 */
@WebServlet("/load_countries_animated_data")
public class LoadCountriesAnimatedChartData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadCountriesAnimatedChartData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		String codes = request.getParameter("codes");
		if (replaceIfMissing(codes, REPLACEMENT).equals(codes))
		{
			System.out.println(request.getQueryString());
			String[] countryCodes = codes.split(TOKEN);
			try
			{
				CountryDAO countryDAO = new CountryDAO();
				CountryPovertyDataDAO povertyDataDAO = new CountryPovertyDataDAO();
				CountryPopulationDataDAO populationDataDAO = new CountryPopulationDataDAO();
				CountryGDPPerCapitaDataDAO gdpPerCapitaDAO = new CountryGDPPerCapitaDataDAO();
				
				ArrayList<String> years = povertyDataDAO.readAvailableYearsFrom(FIRST_YEAR);
	//			ArrayList<Country> countries = countryDAO.readAllAvailableForPovertyData();
				
				JSONObject dataWrapper = new JSONObject();
				
				ArrayList<Country> countries = new ArrayList<>();
				for (int i = 0; i < countryCodes.length; i++)
				{
					countries.add(countryDAO.read(countryCodes[i]));
				}
				
				for (String year: years)
				{
					JSONArray currentYearData = new JSONArray();
					for (Country c: countries)
					{
						try
						{
							double poverty = povertyDataDAO.readValueByCountryAndYear(c, year);
							double gdpPerCapita = gdpPerCapitaDAO.readValueByCountryAndYear(c, year);
							double population = populationDataDAO.readValueByCountryAndYear(c, year);
							System.out.println(poverty + ", " + gdpPerCapita + ", " + population);
							JSONObject data = new JSONObject();
							data.put("code", c.getCode());
							data.put("name", c.getName());
							data.put("poverty", poverty);
							data.put("gdppercapita", gdpPerCapita);
							data.put("population", population);
							currentYearData.put(data);
						}
						catch (TupleNotFoundException e)
						{
							System.err.println("NOT AVAILABLE: " + c.getCode() + ", " + year);
	//						e.printStackTrace();
						}
					}
					if (currentYearData.length() > 0)
					{
						dataWrapper.put(year, currentYearData);
					}
				}
				response.getWriter().write(dataWrapper.toString());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			catch (JSONException e)
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
	
//	public static void main(String[] args) throws ServletException, IOException {
//		(new LoadAnimatedChartData()).doGet(null, null);
//	}
	
	private static final String REPLACEMENT = "$";
	private static final String FIRST_YEAR = "1995";
	private static final String TOKEN = "-";

}
