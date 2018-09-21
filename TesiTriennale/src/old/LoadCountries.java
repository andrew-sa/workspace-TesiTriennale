package old;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import manager.data.dao.CountryDAO;
import manager.data.extraction.CountryCodeWrapper;
import manager.data.model.Country;
import manager.data.transformation.CountryCodeTrasformer;

public class LoadCountries {

	public static void main(String[] args) throws IOException, SQLException {
		CountryCodeWrapper wrapper = new CountryCodeWrapper();
		ArrayList<Country> countries = wrapper.extractData();
		CountryCodeTrasformer trasformer = new CountryCodeTrasformer();
		trasformer.addKosovo(countries);
		CountryDAO countryDAO = new CountryDAO();
		countryDAO.save(countries);
	}

}
