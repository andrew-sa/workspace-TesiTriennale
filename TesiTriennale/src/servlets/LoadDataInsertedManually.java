package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

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
import manager.data.model.Data;
import manager.data.model.GDPPerCapitaData;
import manager.data.model.NetMigrationData;
import manager.data.model.PopulationData;
import manager.data.model.PovertyData;

/**
 * Servlet implementation class LoadDataInsertedManually
 */
@WebServlet("/load_data_inserted_manually")
public class LoadDataInsertedManually extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadDataInsertedManually() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();
		CountryPopulationDataDAO countryPopulationDataDAO = new CountryPopulationDataDAO();
		CountryPovertyDataDAO countryPovertyDataDAO = new CountryPovertyDataDAO();
		CountryNetMigrationDataDAO countryNetMigrationDataDAO = new CountryNetMigrationDataDAO();
		CountryGDPPerCapitaDataDAO countryGDPPerCapitaDataDAO = new CountryGDPPerCapitaDataDAO();
		JSONArray array = new JSONArray();
		try
		{
			ArrayList<CountryPopulationData> dataPop = countryPopulationDataDAO.readManualInsertions();
			for (Data d: dataPop)
			{
				JSONObject data = new JSONObject();
				data.put("country", d.getName());
				data.put("year", d.getYear());
				data.put("value", d.getValue());
				data.put("dataType", PopulationData.DATA_TYPE);
				array.put(data);
			}
			
			ArrayList<CountryPovertyData> dataPov = countryPovertyDataDAO.readManualInsertions();
			for (Data d: dataPov)
			{
				JSONObject data = new JSONObject();
				data.put("country", d.getName());
				data.put("year", d.getYear());
				data.put("value", d.getValue());
				data.put("dataType", PovertyData.DATA_TYPE);
				array.put(data);
			}
			
			ArrayList<CountryNetMigrationData> dataNet = countryNetMigrationDataDAO.readManualInsertions();
			for (Data d: dataNet)
			{
				JSONObject data = new JSONObject();
				data.put("country", d.getName());
				data.put("year", d.getYear());
				data.put("value", d.getValue());
				data.put("dataType", NetMigrationData.DATA_TYPE);
				array.put(data);
			}
			
			ArrayList<CountryGDPPerCapitaData> dataGDP = countryGDPPerCapitaDataDAO.readManualInsertions();
			for (Data d: dataGDP)
			{
				JSONObject data = new JSONObject();
				data.put("country", d.getName());
				data.put("year", d.getYear());
				data.put("value", d.getValue());
				data.put("dataType", GDPPerCapitaData.DATA_TYPE);
				array.put(data);
			}
			
//			System.out.println(array.toString());
			printWriter.write(array.toString());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		};
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	
//	public static void main(String[] args) throws ServletException, IOException {
//		(new LoadDataInsertedManually()).doGet(null, null);
//	}

}
