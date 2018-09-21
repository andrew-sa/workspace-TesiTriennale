package manager.data.extraction;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import manager.data.model.News;

public class GoogleLatestNewsWrapper {

	public GoogleLatestNewsWrapper() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<News> extractData(String country) throws IOException
	{
		ArrayList<News> countryNews = new ArrayList<>();
		final String entryPoint = "https://www.google.com/search";
		final String language = "hl=en";
		final String toSearch = "q=" + country + " poverty";
		final String section = "tbm=nws";
		Document doc = Jsoup.connect(entryPoint + "?" + language + "&" + toSearch + "&" + section).get();
//		System.out.println(doc.baseUri());
		Element rootSearch = doc.getElementById("search");
		Elements results = rootSearch.getElementsByClass("g");
		for (int i = 0; i < results.size(); i++)
		{
			Element container = results.get(i).selectFirst("div.gG0TJc");
			String URL = container.selectFirst("a").attr("href");
			String title = container.selectFirst("a").text();
			String info = container.selectFirst("div.slp").text();
			String description = container.selectFirst("div.st").text();
//			System.out.println(i + ": " + URL + "\n" + title + "\n" + info + "\n" + description + "\n");
			countryNews.add(new News(title, info, URL, description));
		}
		return countryNews;
	}
	
//	private String cleanNewsURL(String URL)
//	{
//		String cleanedURL = URL.replace("https://www.google.com/url?q=", "");
////		System.out.println(cleanedURL);
//		int index = cleanedURL.indexOf("&amp;sa");
////		System.out.println(index);
//		if (index != -1)
//		{
//			cleanedURL = cleanedURL.substring(0, index);
////			System.out.println(cleanedURL);
//		}
//		return cleanedURL;
//	}
	
//	private String cleanDescription(String description)
//	{
//		return description.replace("\n", " ");
//	}
	
//	public static void main(String[] args) throws IOException {
//		(new GoogleLatestNewsWrapper()).extractData("France");
//	}

}
