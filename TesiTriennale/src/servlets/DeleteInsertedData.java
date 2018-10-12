package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import manager.data.dao.CountryGDPPerCapitaDataDAO;
import manager.data.dao.CountryNetMigrationDataDAO;
import manager.data.dao.CountryPopulationDataDAO;
import manager.data.dao.CountryPovertyDataDAO;
import manager.data.model.CountryGDPPerCapitaData;
import manager.data.model.CountryNetMigrationData;
import manager.data.model.CountryPopulationData;
import manager.data.model.CountryPovertyData;
import manager.data.model.GDPPerCapitaData;
import manager.data.model.NetMigrationData;
import manager.data.model.PopulationData;
import manager.data.model.PovertyData;

/**
 * Servlet implementation class DeleteInsertedData
 */
@WebServlet("/delete_inserted_data")
public class DeleteInsertedData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteInsertedData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		response.getWriter().write("The GET method is not supported<br><br><a href=\"home.html\"><button type=\"button\">Back to Home</button></a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();
		CountryPopulationDataDAO countryPopulationDataDAO = new CountryPopulationDataDAO();
		CountryPovertyDataDAO countryPovertyDataDAO = new CountryPovertyDataDAO();
		CountryNetMigrationDataDAO countryNetMigrationDataDAO = new CountryNetMigrationDataDAO();
		CountryGDPPerCapitaDataDAO countryGDPPerCapitaDataDAO = new CountryGDPPerCapitaDataDAO();
		String dataSTR = request.getParameter("data");
		JSONArray dataArray = new JSONArray(dataSTR);
		for (int i = 0; i < dataArray.length(); i++)
		{
			JSONObject singleDataObject = dataArray.getJSONObject(i);
			String country = singleDataObject.getString("countryCode");
			String dataType = singleDataObject.getString("dataType");
			String year = singleDataObject.getString("year");
			
			if (i > 0)
			{
				printWriter.print("<br>");
				printWriter.flush();
			}
			
			try
			{
				switch (dataType)
				{
					case PopulationData.DATA_TYPE:
					{
						CountryPopulationData data = new CountryPopulationData(country, year);
						countryPopulationDataDAO.delete(data, printWriter);
						break;
					}
					case PovertyData.DATA_TYPE:
					{
						CountryPovertyData data = new CountryPovertyData(country, year);
						countryPovertyDataDAO.delete(data, printWriter);
						break;
					}
					case NetMigrationData.DATA_TYPE:
					{
						CountryNetMigrationData data = new CountryNetMigrationData(country, year);
						countryNetMigrationDataDAO.delete(data, printWriter);
						break;
					}
					case GDPPerCapitaData.DATA_TYPE:
					{
						CountryGDPPerCapitaData data = new CountryGDPPerCapitaData(country, year);
						countryGDPPerCapitaDataDAO.delete(data, printWriter);
						break;
					}
					default:
						break;
				}
			}
			catch (SQLException e)
			{
				printWriter.print("ERROR DURING INTERACTION WITH THE DATABASE");
				printWriter.print("<br>");
				printWriter.flush();
				e.printStackTrace();
			}
		}
	}
	
}