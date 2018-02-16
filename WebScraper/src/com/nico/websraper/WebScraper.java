package com.nico.websraper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WebScraper {
	private static Logger log = Logger.getLogger(MainScraper.class);
	private List<Product> products = null;
	private int counter = 0;

	private WebScrapperConfig config;
	
	public WebScraper(WebScrapperConfig config){
		this.config = config;
	}
	
	public List<Product> scrap(){
		try {
			products = new ArrayList<Product>();
			parseLinks(config.getBaseUrl(), config.getBaseUrl(), new HashSet<String>());
			FileWriter file = new FileWriter("D:\\temp\\product.txt");
			for (Product product : products) {
				log.info(product);
				file.write(product.toString()+"\r\n");
			}
			file.close();
		} catch (IOException e) {
			log.error("Error:",e);
		}
		return products;
	}
	
	private void parseLinks(String baseUrl, String url, Set<String> urls) throws IOException {
		try {
			if(!isValid(url))
				return;
			Document doc = Jsoup.connect(url).get();
			//log.debug(counter++);
			urls.add(url);
			if (isProductPage(doc)) {
				//log.info("Product description:" + url);
				String brand = getOwnTextSafe(lookFor(doc.select("body").first(), config.getBrandPattern().split(","), 0, false));
				Element modelElement = lookFor(doc.select("body").first(), config.getModelPattern().split(","), 0, true);
				String model = getOwnTextSafe(modelElement);
				String price = getOwnTextSafe(lookFor(doc.select("body").first(), config.getPricePattern().split(","), 0, false));
				String category = getOwnTextSafe(lookFor(doc.select("body").first(), "a|class|product_family".split(","), 0, false));
				//log.info("Brand:" + brand);
				//log.info("Model:" + model);
				//log.info("Price:" + price);
				//log.info("Category:" + category);
				Product product = new Product(config.getName(), brand, model, price, category, url);
				products.add(product);
				log.info(product.toString());
				return;
			}
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String absHref = link.attr("abs:href");
				if (urls.contains(absHref) || !absHref.contains(baseUrl))
					continue;
				try{
					parseLinks(baseUrl, absHref, urls);
				}catch(Exception ex){
					log.error("Error:",ex);
				}
			}
			doc = null;
		} catch (HttpStatusException ex) {
			log.error("Error:",ex);
		}
	}

	private String getOwnTextSafe(Element modelElement) {
		return modelElement!=null?modelElement.ownText():"";
	}

	private boolean isValid(String url) {
		for (String exclusion : config.getExclusions()) {
			if(url.contains(exclusion))
				return false;
		}
		return true;
	}

	private boolean isProductPage(Document doc) {
		Elements links = doc.select(config.getProductPageTag());
		for (Element link : links) {
			if (link.text().contains(config.getProductPageContent()))
				return true;
		}
		return false;
	}
	
	/**
	 * look for an element in the document thanks to the pattern.
	 * <i>patterns</i> is a comma separated list of pattern.
	 * A pattern is a:
	 * - a tag type
	 * - a list of clauses. A clause is an attribute name and an attribute value
	 * 
	 *  <tag type>|<attribute name1>|<attribute value1>|<attribute name2>|<attribute value2>...
	 * @param doc
	 * @param pattern
	 * @return
	 */
	public static Element lookFor(Element element, String[] patterns, int index, boolean topElement) {
		if(patterns==null) return null;
		String pattern = patterns[index];
		String[] split = pattern.split("\\|");
		String tagName = split[0];
		Elements elementsByTag = element.getElementsByTag(tagName);
		for (Element child : elementsByTag) {
			boolean check = true;
			// check clauses
			for(int i=1;i<split.length;i+=2){
				if(!child.hasAttr(split[i]) || !child.attr(split[i]).equals(split[i+1])){
					check = false;
					break;
				}
			}
			if(check){
				if(index==patterns.length-1)
					return child;
				Element lookFor = lookFor(child, patterns, index+1, topElement);
				if(lookFor!=null)
					return topElement?child:lookFor;
			}
		}
		return null;
	}
}
