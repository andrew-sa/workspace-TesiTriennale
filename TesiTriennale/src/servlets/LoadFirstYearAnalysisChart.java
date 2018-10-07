package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import manager.data.dao.CountryGDPPerCapitaDataDAO;
import manager.data.dao.CountryNetMigrationDataDAO;
import manager.data.dao.CountryPovertyDataDAO;

/**
 * Servlet implementation class LoadFirstYearAnalysisChart
 */
@WebServlet("/load_first_year")
public class LoadFirstYearAnalysisChart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadFirstYearAnalysisChart() {
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
			JSONObject result = new JSONObject();
			CountryPovertyDataDAO povertyDataDAO = new CountryPovertyDataDAO();
			CountryNetMigrationDataDAO netMigrationDataDAO = new CountryNetMigrationDataDAO();
			CountryGDPPerCapitaDataDAO gdpPerCapitaDataDAO = new CountryGDPPerCapitaDataDAO();
			result.put("poverty", povertyDataDAO.readFirstYear());
			result.put("netmigration", netMigrationDataDAO.readFirstYear());
			result.put("gdppercapita", gdpPerCapitaDataDAO.readFirstYear());
//			System.out.println(result.toString());
			response.getWriter().write(result.toString());
		}
		catch (JSONException | SQLException e)
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
	
//	public static void main(String[] args) throws ServletException, IOException {
//		(new LoadFirstYearAnalysisChart()).doGet(null, null);
//	}

}
