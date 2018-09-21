package manager.data.extraction;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import manager.data.model.Country;

public class CountryCodeWrapper {

	public CountryCodeWrapper() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Country> extractData() throws IOException
	{
		ArrayList<Country> countries = new ArrayList<>();
		final String url = "https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2";
		Document doc = Jsoup.connect(url).get();
		Element tableBody = doc.getElementById("IT").parent().parent();
		Elements tuple = tableBody.children();
//		System.out.println(tuple.get(0));
		tuple.remove(0); //Remove the row that contain the names of the columns
		for (Element e: tuple)
		{
			String code = e.child(0).text();
//			if (code.equals("AX") || code.equals("CI") || code.equals("RE"))
//			{
//				System.out.println(e);
//			}
			String name = e.child(1).getElementsByTag("a").get(0).attr("title");
//			System.out.println("[" + code + ", " + name + "]");
			countries.add(new Country(code, name));
		}
		return countries;
	}
	
//	public static void main(String[] args) throws IOException {
//		(new CountryCodeWrapper()).extractData();
//	}

}
