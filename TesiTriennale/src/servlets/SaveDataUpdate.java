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

import com.mashape.unirest.http.exceptions.UnirestException;

import manager.data.dao.SourceDAO;
import manager.data.extraction.EUDataWrapper;
import manager.data.extraction.USADataWrapper;
import manager.data.extraction.WorldBankDataWrapper;
import manager.data.loading.LoadGDPPerCapitaData;
import manager.data.loading.LoadNetMigrationsData;
import manager.data.loading.LoadPopulationData;
import manager.data.loading.LoadPovertyData;
import manager.data.model.CompareToOrder;
import manager.data.model.GDPPerCapitaData;
import manager.data.model.NetMigrationData;
import manager.data.model.PopulationData;
import manager.data.model.PovertyData;
import manager.data.model.Source;

/**
 * Servlet implementation class SaveDataUpdate
 */
@WebServlet("/update_data")
public class SaveDataUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveDataUpdate() {
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
		try
		{
			SourceDAO sourceDAO = new SourceDAO();
			ArrayList<Source> sources = sourceDAO.read();
			LoadPopulationData loadPopulationData = new LoadPopulationData();
			LoadPovertyData loadPovertyData = new LoadPovertyData();
			LoadNetMigrationsData loadNetMigrationData = new LoadNetMigrationsData();
			LoadGDPPerCapitaData loadGDPPerCapitaData = new LoadGDPPerCapitaData();
//			boolean updateExecuted = false;
			
//			sources.add(3, sources.get(8));
//			System.out.println(sources);
			sources.sort(new CompareToOrder());
//			System.out.println(sources);
			for (Source s: sources)
			{
//				System.out.println(s);
				String updated = request.getParameter(s.getName() + s.getDataType());
				if (replaceIfMissing(updated, REPLACEMENT).equals(updated))
				{
//					System.out.println(updated);
					if (updated.equals(TO_UPDATE))
					{
						System.out.println("Updating: " + s.toString());
						switch (s.getName())
						{
							case USADataWrapper.SOURCE:
							{
								switch (s.getDataType())
								{
									case PopulationData.DATA_TYPE:
										loadPopulationData.loadUSAData(printWriter);
										break;
									case PovertyData.DATA_TYPE:
										loadPovertyData.loadUSAData(printWriter);
										break;
									default:
										break;
								}
//								sourceDAO.update(s);
//								updateExecuted = true;
								break;
							}
							case EUDataWrapper.SOURCE:
							{
								switch (s.getDataType())
								{
									case PopulationData.DATA_TYPE:
										loadPopulationData.loadEUData(printWriter);
										break;
									case PovertyData.DATA_TYPE:
										loadPovertyData.loadEUData(printWriter);
										break;
									case NetMigrationData.DATA_TYPE:
										loadNetMigrationData.loadEUData(printWriter);
									default:
										break;
								}
//								sourceDAO.update(s);
//								updateExecuted = true;
								break;
							}
							case WorldBankDataWrapper.SOURCE:
							{
								switch (s.getDataType())
								{
									case PopulationData.DATA_TYPE:
										loadPopulationData.loadWorldBankData(printWriter);
										break;
									case PovertyData.DATA_TYPE:
										loadPovertyData.loadWorldBankData(printWriter);
										break;
									case NetMigrationData.DATA_TYPE:
										loadNetMigrationData.loadWorldBankData(printWriter);
									case GDPPerCapitaData.DATA_TYPE:
										loadGDPPerCapitaData.loadWorldBankData(printWriter);
									default:
										break;
								}
//								sourceDAO.update(s);
//								updateExecuted = true;
								break;
							}
							default:
								break;
						}
					}
				}
			}
//			if (updateExecuted == true)
//			{
//				loadPovertyData.loadRegionsData();
//			}
			printWriter.println("OK");
			printWriter.flush();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			printWriter.println("ERROR");
			printWriter.flush();
		}
		catch (UnirestException e)
		{
			e.printStackTrace();
			printWriter.println("ERROR");
			printWriter.flush();
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
	private static final String TO_UPDATE = "false";
	
}
