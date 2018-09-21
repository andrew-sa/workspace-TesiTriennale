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
import manager.data.model.GDPPerCapitaData;
import manager.data.model.PopulationData;
import manager.data.model.PovertyData;

/**
 * Servlet implementation class LoadAnimatedChartData
 */
@WebServlet("/load_data_for_animated_chart")
public class LoadAnimatedChartData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadAnimatedChartData() {
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
			CountryDAO countryDAO= new CountryDAO();
			CountryPovertyDataDAO povertyDataDAO = new CountryPovertyDataDAO();
			CountryPopulationDataDAO populationDataDAO = new CountryPopulationDataDAO();
			CountryGDPPerCapitaDataDAO gdpPerCapitaDAO = new CountryGDPPerCapitaDataDAO();
			
			ArrayList<String> years = povertyDataDAO.readAvailableYearsFrom(FIRST_YEAR);
			ArrayList<Country> countries = countryDAO.readAllAvailableForPovertyData();
			
			JSONObject result = new JSONObject();
			JSONObject boundaries = new JSONObject();
			JSONObject dataWrapper = new JSONObject();
			
			boundaries.put("population", getBoundaryValues(PopulationData.DATA_TYPE));
			boundaries.put("poverty", getBoundaryValues(PovertyData.DATA_TYPE));
			boundaries.put("gdppercapita", getBoundaryValues(GDPPerCapitaData.DATA_TYPE));
			
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
//			System.out.println(result.toString());
//			System.out.println(result.length());
			result.put("boundaries", boundaries);
			result.put("data", dataWrapper);
			response.getWriter().write(result.toString());
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
	
	private JSONObject getBoundaryValues(String dataType) throws SQLException, TupleNotFoundException
	{
		JSONObject boundaryValues = new JSONObject();
		BoundaryValueDAO boundaryValueDAO = new BoundaryValueDAO();
		double min = boundaryValueDAO.readMinForDataType(dataType);
		double max = boundaryValueDAO.readMaxForDataType(dataType);
		boundaryValues.put("min", min);
		boundaryValues.put("max", max);
		return boundaryValues;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	
//	public static void main(String[] args) throws ServletException, IOException {
//		(new LoadAnimatedChartData()).doGet(null, null);
//	}
	
	private static final String FIRST_YEAR = "1995";

}
