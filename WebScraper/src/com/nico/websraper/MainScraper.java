package com.nico.websraper;

import java.io.IOException;

import org.apache.log4j.Logger;

public class MainScraper {
	private static Logger log = Logger.getLogger(MainScraper.class);

	public static void main(String[] args) throws IOException {
		WebScrapperConfig darty = new WebScrapperConfig("Darty", 
				"https://www.darty.com",
				"a|id|darty_product_brand", 
				"span,a|id|darty_product_brand",
				"span|class|darty_prix darty_mediumbig", 
				"a|class|product_family", "h2", "Description");
	
		darty.getExclusions().add("/solde");
		darty.getExclusions().add("selection");
		darty.getExclusions().add("/services/");
		darty.getExclusions().add("/extra/");
		darty.getExclusions().add(".pdf");
		darty.getExclusions().add("informations_legales");
		darty.getExclusions().add("espace_client");
		darty.getExclusions().add("darty-et-vous");
		log.info("Darty");
		new WebScraper(darty).scrap();
		
		WebScrapperConfig boulanger = new WebScrapperConfig("Boulanger", "https://www.boulanger.com",
				"span|itemprop|brand,span|itemprop|name", 
				"h1|itemprop|name,a,span|itemprop|brand,span|itemprop|name",
				"p,span|class|exponent", 
				"", 
				"th", 
				"Caractéristiques principales");
		log.info("Boulanger");
		boulanger.getExclusions().add("/solde");
		boulanger.getExclusions().add("selection");
		boulanger.getExclusions().add("/services/");
		boulanger.getExclusions().add("/extra/");
		new WebScraper(boulanger).scrap();
		
	
		//parseDartyLinks("https://www.darty.com", "https://www.darty.com"/*+"/nav/achat/gros_electromenager/lave-vaisselle/lave-vaisselle/bosch_sms46iw08e.html"*/, new HashSet<String>());
		//parseBoulangerLinks("https://www.boulanger.com", "https://www.boulanger.com"/*+"/ref/1089501"*/, new HashSet<String>());
	}
	/*
	private static void parseBoulangerLinks(String baseUrl, String url, Set<String> urls) throws IOException {
		try {
			//log.info(url);
			Document doc = Jsoup.connect(url).get();
			urls.add(url);
			if (productPageBoulanger(doc)) {
				log.info("Product description:" + url);
				log.info("Brand:" + lookFor(doc.select("body").first(), "span|itemprop|brand,span|itemprop|name".split(","), 0, false).ownText());
				Element modelElement = lookFor(doc.select("body").first(), "h1|itemprop|name,a,span|itemprop|brand,span|itemprop|name".split(","), 0, true);
				log.info("Model:" + modelElement.ownText());
				log.info("Price:" + lookFor(doc.select("body").first(), "p,span|class|exponent".split(","), 0, false).ownText());
				return;
			}
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String absHref = link.attr("abs:href");
				if (urls.contains(absHref) || !absHref.contains(baseUrl))
					continue;
				parseBoulangerLinks(baseUrl, absHref, urls);
			}
		} catch (HttpStatusException ex) {
			log.error(ex);
		}
	}

	private static void parseDartyLinks(String baseUrl, String url, Set<String> urls) throws IOException {
		try {
			
			log.info(url);
			if(url.contains("/solde") || url.contains("selection") || url.contains("/services/") || url.contains("/extra/"))
				return;
			Document doc = Jsoup.connect(url).get();
			urls.add(url);
			if (productPage(doc)) {
				log.info("Product description:" + url);
				log.info("Brand:" + lookFor(doc.select("body").first(), "a|id|darty_product_brand".split(","), 0, false).ownText());
				log.info("Model:" + lookFor(doc.select("body").first(), "span,a|id|darty_product_brand".split(","), 0, true).ownText());
				log.info("Price:" + lookFor(doc.select("body").first(), "span|class|darty_prix darty_mediumbig".split(","), 0, true).ownText());
				log.info("Price:" + lookFor(doc.select("body").first(), "span|class|darty_prix darty_mediumbig,span|class|darty_cents".split(","), 0, false).ownText());
				log.info("Category:" + lookFor(doc.select("body").first(), "a|class|product_family".split(","), 0, false).ownText());
				return;
			}
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String absHref = link.attr("abs:href");
				if (urls.contains(absHref) || !absHref.contains(baseUrl))
					continue;
				parseDartyLinks(baseUrl, absHref, urls);
			}
		} catch (HttpStatusException ex) {
			log.error(ex);
		}
	}

	private static boolean productPageBoulanger(Document doc) throws IOException {
		Elements links = doc.select("th");
		for (Element link : links) {
			if (link.text().contains("Caractéristiques principales"))
				return true;
		}
		return false;
	}
	private static boolean productPage(Document doc) throws IOException {
		Elements links = doc.select("h2");
		for (Element link : links) {
			if (link.text().contains("Description"))
				return true;
		}
		return false;
	}*/
}
