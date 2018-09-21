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
		return super.toString() + "[source=" + source + "]";
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
	
	private String source;

}
