package manager.data.model;

public class CountryGDPPerCapitaData extends Data implements GDPPerCapitaData {

	public CountryGDPPerCapitaData() {
			
		}
	
	public CountryGDPPerCapitaData(String name, String year) {
		super(name, year);
	}

	public CountryGDPPerCapitaData(String name, String year, double value) {
		super(name, year, value);
	}
	
	public CountryGDPPerCapitaData(String name, String year, double value, String source) {
		super(name, year, value);
		this.source = source;
	}
	
	public CountryGDPPerCapitaData(String name, String year, double value, String source, boolean calculated) {
		super(name, year, value);
		this.source = source;
		this.calculated = calculated;
	}

	public boolean equals(Object otherObject)
	{
		if (!super.equals(otherObject))
		{
			return false;
		}
		return true;
	}
	
	public String toString()
	{
		return super.toString() + "[source=" + source + ", calculated=" + calculated + "]";
	}
	
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
	/**
	 * @return the calculated
	 */
	public boolean isCalculated() {
		return calculated;
	}

	/**
	 * @param calculated the calculated to set
	 */
	public void setCalculated(boolean calculated) {
		this.calculated = calculated;
	}

	private String source;
	private boolean calculated;
	
}
