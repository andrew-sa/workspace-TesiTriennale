package manager.data.model;

public class News {

	public News() {
		
	}

	public News(String title, String info, String URL, String description) {
		this.title = title;
		this.info = info;
		this.URL = URL;
		this.description = description;
	}
	
	public boolean equals(Object otherObject)
	{
		if (otherObject == null)
			return false;
		if (getClass() != otherObject.getClass())
			return false;
		News other = (News) otherObject;
		return title.equals(other.title) && info.equals(other.info) && URL.equals(other.URL) && description.equals(other.description);
	}

	public String toString() {
		return getClass().getName() + "[title=" + title + ", info=" + info + ", URL=" + URL + ", description=" + description + "]";
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the subtitle
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param subtitle the subtitle to set
	 */
	public void setInfo(String subtitle) {
		this.info = subtitle;
	}

	/**
	 * @return the uRL
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * @param uRL the uRL to set
	 */
	public void setURL(String uRL) {
		URL = uRL;
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

	private String title;
	private String info;
	private String URL;
	private String description;
	
}
