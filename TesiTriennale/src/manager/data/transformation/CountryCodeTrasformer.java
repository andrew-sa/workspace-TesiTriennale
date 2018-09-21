package manager.data.transformation;

import java.util.ArrayList;

import manager.data.model.Country;

public class CountryCodeTrasformer {

	public CountryCodeTrasformer() {
		
	}
	
	public void addKosovo(ArrayList<Country> countries)
	{
		if (!countries.contains(KOSOVO))
		{
			countries.add(KOSOVO);
		}
	}
	
	private static final Country KOSOVO = new Country("XK", "Kosovo");

}
