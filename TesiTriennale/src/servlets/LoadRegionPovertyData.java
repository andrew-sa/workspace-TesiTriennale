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
import manager.data.dao.RegionPovertyDataDAO;
import manager.data.model.Country;
import manager.data.model.CountryPovertyData;
import manager.data.model.RegionPovertyData;

/**
 * Servlet implementation class LoadRegionPovertyData
 */
@WebServlet("/load_region_poverty_data")
public class LoadRegionPovertyData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadRegionPovertyData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		String region = request.getParameter("region");
		if (!replaceIfMissing(region, REPLACEMENT).equals(REPLACEMENT))
		{
			try
			{
				JSONObject result = new JSONObject();
				JSONArray array = new JSONArray();
				RegionPovertyDataDAO povertyDataDAO = new RegionPovertyDataDAO();
				ArrayList<RegionPovertyData> povertyData = povertyDataDAO.read(region);
				for (RegionPovertyData pd: povertyData)
				{
					JSONObject data = new JSONObject();
					data.put("year", pd.getYear());
					data.put("value", pd.getValue());
					array.put(data);
				}
				result.put("name", region);
				result.put("data", array);
				response.getWriter().write(result.toString());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
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
	
	private static final String REPLACEMENT = "$";

}
