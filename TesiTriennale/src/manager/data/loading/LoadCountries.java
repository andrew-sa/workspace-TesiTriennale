package manager.data.loading;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mashape.unirest.http.exceptions.UnirestException;

import manager.data.dao.CountryDAO;
import manager.data.extraction.CountryAdditionalFieldsWrapper;
import manager.data.extraction.CountryCodeWrapper;
import manager.data.model.Country;
import manager.data.transformation.CountryCodeTrasformer;

public class LoadCountries {
	
	public LoadCountries() {
		// TODO Auto-generated constructor stub
	}
	
	public void loadCountries() throws IOException, UnirestException, SQLException
	{
		CountryCodeWrapper wrapper = new CountryCodeWrapper();
		ArrayList<Country> countries = wrapper.extractData();
		CountryCodeTrasformer trasformer = new CountryCodeTrasformer();
		trasformer.addKosovo(countries);
		CountryAdditionalFieldsWrapper wrapperAdditionalFields = new CountryAdditionalFieldsWrapper();
		for (Country c: countries)
		{
			wrapperAdditionalFields.extractData(c);
		}
		CountryDAO countryDAO = new CountryDAO();
		countryDAO.delete();
		countryDAO.save(countries);
	}

	public static void main(String[] args) throws IOException, SQLException, UnirestException {
		(new LoadCountries()).loadCountries();
	}

}
