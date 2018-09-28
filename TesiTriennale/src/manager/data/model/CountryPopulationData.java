package manager.data.model;

public class CountryPopulationData extends Data implements PopulationData {
	
	public CountryPopulationData() {
		super();
	}

	public CountryPopulationData(String country, String year, double value) {
		super(country, year, value);
	}
	
	public CountryPopulationData(String country, String year, double value, String source) {
		super(country, year, value);
		this.source = source;
	}
	
	public CountryPopulationData(String country, String year, double value, String source, boolean calculated) {
		super(country, year, value);
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
