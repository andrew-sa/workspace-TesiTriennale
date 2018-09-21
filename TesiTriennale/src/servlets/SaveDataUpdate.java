package servlets;

import java.io.IOException;
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
import manager.data.loading.LoadPovertyData;
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
		try
		{
			SourceDAO sourceDAO = new SourceDAO();
			ArrayList<Source> sources = sourceDAO.read();
			LoadPovertyData loadPovertyData = new LoadPovertyData();
			boolean updateExecuted = false;
			for (Source s: sources)
			{
				String updated = request.getParameter(s.getName());
				if (replaceIfMissing(updated, REPLACEMENT).equals(updated))
				{
//					System.out.println(updated);
					if (updated.equals(TO_UPDATE))
					{
						switch (s.getName())
						{
							case USADataWrapper.SOURCE:
							{
								loadPovertyData.loadUSAData();
								sourceDAO.update(s);
								updateExecuted = true;
								break;
							}
							case EUDataWrapper.SOURCE:
							{
								loadPovertyData.loadEUData();
								sourceDAO.update(s);
								updateExecuted = true;
								break;
							}
							case WorldBankDataWrapper.SOURCE:
							{
								loadPovertyData.loadWorldBankData();
								sourceDAO.update(s);
								updateExecuted = true;
								break;
							}
							default:
								break;
						}
					}
				}
			}
			if (updateExecuted == true)
			{
				loadPovertyData.loadRegionsData();
			}
			response.getWriter().write("OK");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			response.getWriter().write("ERROR");
		}
		catch (UnirestException e)
		{
			e.printStackTrace();
			response.getWriter().write("ERROR");
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
