package manager.data.model;

public class CountryWikipedia extends Country {

	public CountryWikipedia() {
		
	}
	
	public CountryWikipedia(String code, String name, String imageURL, String description) {
		super(code, name);
		this.imageURL = imageURL;
		this.description = description;
	}
	
	public boolean equals(Object otherObject)
	{
		if (!super.equals(otherObject))
		{
			return false;
		}
		CountryWikipedia other = (CountryWikipedia) otherObject;
		return imageURL.equals(other.imageURL) && description.equals(other.description);
	}

	public String toString()
	{
		return super.toString() + "[imageURL=" + imageURL + ", description=" + description + "]";
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	private String imageURL;
	private String description;

}
