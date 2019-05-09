package net.rutgers.rutgers.cochanelib;

import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class CochraneLibraryData {
	public static void main(String[] args) {
		Item item = new Item();
		
		// final String url = "http://www.cochranelibrary.com/home/topic-and-review-group-list.html?page=topic";
		// HttpClient client = HttpClientBuilder.create().build();
		// HttpResponse response = client.execute(request);
		WebClient client = new WebClient();
		client.getOptions().setJavaScriptEnabled(false);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setUseInsecureSSL(true);
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("CochaneLibrary.txt"));
			// Taking the URL from the command line
			HtmlPage page = client.getPage(args[0]);
			URL nextUrl = new URL(((HtmlElement) page  
	                .getFirstByXPath("//li[@class='browse-by-list-item']/a"))
	                .getAttribute("href"));
			HtmlPage page1 = client.getPage(nextUrl);
			List<HtmlElement> mainlist = (List<HtmlElement>) page1.getByXPath("//div[@class='search-results-item-body']");
			for(HtmlElement eachlist : mainlist) {
				HtmlDivision div = ((HtmlDivision) eachlist.getFirstByXPath(".//div[@class='search-result-authors']/div"));
				HtmlButton btn = ((HtmlButton) page.getFirstByXPath(".//li[@class='browse-by-list-item']/a/button"));
				HtmlAnchor anchor =( (HtmlAnchor) eachlist.getFirstByXPath(".//h3[@class='result-title']/a"));
				HtmlDivision date = ((HtmlDivision) eachlist.getFirstByXPath(".//div[@class='search-result-metadata-block']/div[@class='search-result-metadata-item']/div[@class='search-result-date']/div"));
				writer.write("https://www.cochranelibrary.com" + anchor.getAttribute("href")+ "|");
				System.out.println("URl: "+"https://www.cochranelibrary.com" + anchor.getAttribute("href"));
				writer.write(div.asText() + "|");
				System.out.println("AUTHOR: "+div.asText());
				writer.write(btn.asText()+ "|");
				System.out.println("TOPIC: "+btn.asText());
				writer.write(anchor.asText()+ "|");
				System.out.println("TITLE: "+anchor.asText());
				writer.write(date.asText()+ "|");
				System.out.println("DATE: "+ date.asText());
				writer.write("\n");
			}
			writer.close();
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
